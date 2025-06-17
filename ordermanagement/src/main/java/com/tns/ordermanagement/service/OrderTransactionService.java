package com.tns.ordermanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tns.ordermanagement.controller.admin.dto.OrderSubmitForm;
import com.tns.ordermanagement.controller.admin.dto.OrderTrxDto;
import com.tns.ordermanagement.controller.admin.dto.SaleItemDto;
import com.tns.ordermanagement.controller.commondto.Receipt;
import com.tns.ordermanagement.controller.guest.dto.OrderForm;
import com.tns.ordermanagement.exception.AppBusinessException;
import com.tns.ordermanagement.model.entity.OrderTransaction;
import com.tns.ordermanagement.model.entity.SaleItem;
import com.tns.ordermanagement.model.entity.constant.OrderTransactionStatus;
import com.tns.ordermanagement.model.entity.constant.TableStatus;
import com.tns.ordermanagement.model.entity.embeddable.SaleItemPK;
import com.tns.ordermanagement.model.repo.ItemRepo;
import com.tns.ordermanagement.model.repo.OrderTransactionRepo;
import com.tns.ordermanagement.model.repo.RestaurantTableRepo;
import com.tns.ordermanagement.model.repo.SaleItemRepo;

import static com.tns.ordermanagement.utils.utilityclass.SafeClass.safeCall;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderTransactionService {

	private final OrderTransactionRepo trxRepo;
	private final RestaurantTableRepo tableRepo;
	private final SaleItemRepo saleItemRepo;
	private final ItemRepo itemRepo;

	public OrderTransaction getSessionOrThrow(int tableNumber, UUID sessionId) {
		return trxRepo.findByRestaurantTableIdAndCustomerSessionIdAndStatus(tableNumber, sessionId,
				OrderTransactionStatus.INPROGRESS).orElseGet(() -> {
					var trx = new OrderTransaction();
					var table = safeCall(tableRepo.findOneByTableNumber(tableNumber), "Table Number", tableNumber);

					var status = table.getStatus();
					if (status == TableStatus.Free) {
						trx.setCustomerSessionId(sessionId);
						trx.setStartedAt(LocalDateTime.now());
						trx.setRestaurantTable(table);
						trx.setStatus(OrderTransactionStatus.INPROGRESS);

						trx = trxRepo.save(trx);

						table.setStatus(TableStatus.Occupied);
						tableRepo.save(table);

						return trx;
					}
					throw new AppBusinessException(status.getFullStatus());
				});

	}

	public OrderTransaction submit(OrderForm form, String sessionId, int tableNumber) {

		var trx = getSessionOrThrow(tableNumber, UUID.fromString(sessionId));
		return createSale(trx, form);

	}
	
	public OrderTrxDto convertOrderTrxDto(OrderTransaction trx) {
		return new OrderTrxDto(trx);
	}

	private OrderTransaction createSale(OrderTransaction trx, OrderForm form) {
			
		var saleItemList = trx.getItems();

		var items = form.getItems();
		for (var i : items) {
			var itemEntity = safeCall(itemRepo.findById(i.getItemId()), "Item with ID", i.getItemId());

			var saleItemPk = new SaleItemPK();
			saleItemPk.setTransaction(trx.getId());
			saleItemPk.setItemId(itemEntity.getId());
			
			var saleItem = saleItemRepo.findById(saleItemPk);
			if(saleItem.isPresent()) {
				System.out.println(saleItemPk);
				var item = saleItem.get();
				
				item.setLastQuantity(item.getLastQuantity() + i.getQuantity());
				
				if(item.getDetails() != null) {
					if(i.getDetails() != null && !i.getDetails().isBlank()) {
						item.setDetails(item.getDetails().concat(",").concat(i.getDetails()));
					}
				} else {
					item.setDetails(i.getDetails() != null && !i.getDetails().isBlank() ? i.getDetails() : null);
				}
				
				saleItemRepo.save(item);
				continue;
			}

			var saleItemEntity = new SaleItem();
			saleItemEntity.setPk(saleItemPk);
			saleItemEntity.setItem(itemEntity);
			saleItemEntity.setLastQuantity(i.getQuantity());
			saleItemEntity.setTransaction(trx);
			saleItemEntity.setSalePrice(itemEntity.getUnitPrice());

			var details = i.getDetails();
			if (details != null && !details.isBlank()) {
				saleItemEntity.setDetails(details.trim());
			}

			saleItemList.add(saleItemEntity);
		}
		
		trx.setItems(saleItemList);
		return trxRepo.save(trx);
	}

	public List<OrderTrxDto> findByStatus(OrderTransactionStatus status) {
		return trxRepo.findByStatus(status).stream().map(OrderTrxDto::new).toList();
	}

	public List<SaleItemDto> findIncomingItemsByOrderId(UUID id) {
		var order = safeCall(trxRepo.findById(id), "Order ID", id);
		return order.getItems().stream().filter(i -> i.getLastQuantity() != 0)
				.map(SaleItemDto::new).toList();
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public void approve(OrderSubmitForm form) {		
		
		for(var i : form.getOrderItems()) {
			
			var pk = new SaleItemPK();
			pk.setTransaction(form.getId());
			pk.setItemId(i.getItemId());
			
			var saleItem = safeCall(saleItemRepo.findById(pk), "Sale Item ID", pk);

			saleItem.setPreviousQuantity(saleItem.getPreviousQuantity() + i.getQuantity());
			
			if(i.isModified()) {
				saleItem.setLastQuantity(0);
				
				if(saleItem.getReason() != null) {
					if(i.getReason() != null && !i.getReason().isBlank()) {
						saleItem.getReason().concat(",").concat(i.getReason());
					}
				} else {
					saleItem.setReason(i.getReason() != null && !i.getReason().isBlank() ? i.getReason() : null);
				}
				saleItem.setModified(true);
			} else {
				var quantity = saleItem.getLastQuantity() - i.getQuantity();
				saleItem.setLastQuantity(quantity);
			}
			
			if (saleItem.getPreviousQuantity() == 0 && saleItem.getLastQuantity() == 0) {
				saleItemRepo.delete(saleItem);
			} else {
				saleItemRepo.save(saleItem);
			}
		}
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public OrderTransaction approveWithoutChecking(UUID id) {
		var order = safeCall(trxRepo.findById(id), "Order ID", id);
		
		var items = order.getItems();
		if(!items.isEmpty()) {
			items.forEach(i -> {
				i.setPreviousQuantity(i.getPreviousQuantity() + i.getLastQuantity());
				i.setLastQuantity(0);
			});
		}
		return order;
	}

	@Transactional
	public Receipt payBill(UUID id) {
		var order = approveWithoutChecking(id);
		order.getRestaurantTable().setStatus(TableStatus.Free);
		order.setStatus(OrderTransactionStatus.COMPLETED);
		order.setEndedAt(LocalDateTime.now());
		return Receipt.convert(order);
	}
}
