package com.tns.ordermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tns.ordermanagement.controller.admin.dto.OrderSubmitForm;
import com.tns.ordermanagement.controller.admin.dto.SaleDto;
import com.tns.ordermanagement.controller.admin.dto.SaleItemDto;
import com.tns.ordermanagement.controller.guest.dto.OrderForm;
import com.tns.ordermanagement.exception.AppBusinessException;
import com.tns.ordermanagement.model.entity.Sale;
import com.tns.ordermanagement.model.entity.SaleItem;
import com.tns.ordermanagement.model.entity.constant.Status;
import com.tns.ordermanagement.model.entity.embeddable.SaleItemPK;
import com.tns.ordermanagement.model.repo.ItemRepo;
import com.tns.ordermanagement.model.repo.SaleItemRepo;
import com.tns.ordermanagement.model.repo.SaleRepo;

import static com.tns.ordermanagement.utils.utilityclass.SafeClass.safeCall;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

	private final SaleRepo saleRepo;
	private final ItemRepo itemRepo; 
	private final SaleItemRepo saleItemRepo;

	public UUID submit(OrderForm form) {
		var saleEntity = new Sale();
		saleEntity = saleRepo.save(saleEntity);
		var saleItemList = new ArrayList<SaleItem>();
		
		var items = form.getItems();
		for(var i : items) {
			var itemEntity = safeCall(itemRepo.findById(i.getItemId()), "Item with ID", i.getItemId());
			
			var saleItemPk = new SaleItemPK();
			saleItemPk.setSaleId(saleEntity.getId());
			saleItemPk.setItemId(itemEntity.getId());
			
			var saleItemEntity = new SaleItem();
			saleItemEntity.setPk(saleItemPk);
			saleItemEntity.setItem(itemEntity);
			saleItemEntity.setQuantity(i.getQuantity());
			saleItemEntity.setSale(saleEntity);
			saleItemEntity.setSalePrice(itemEntity.getUnitPrice());
			
			var details = i.getDetails();
			if(details != null && !details.isBlank()) {
				details.trim();
				saleItemEntity.setDetails(details);
			}
			
			saleItemRepo.save(saleItemEntity);
			saleItemList.add(saleItemEntity);
		}
		
		saleEntity.setSaleItems(saleItemList);
		return saleRepo.save(saleEntity).getId();
	}

	public List<SaleDto> findByStatus(Status pending) {
		return saleRepo.findByStatus(pending).stream().map(SaleDto::new).toList();
	}

	public List<SaleItemDto> findItemsBySaleId(UUID id) {
		return saleRepo.findById(id).map(s -> s.getSaleItems().stream().map(SaleItemDto::new).toList())
				.orElseThrow(() -> new AppBusinessException("Invalid ID"));
	}
	
	public SaleDto findSaleById(UUID id) {
		return saleRepo.findById(id).map(SaleDto::new).orElseThrow(() -> new AppBusinessException("Invalid ID"));
	}

	public void approveOrder(UUID id) {
		saleRepo.findOneById(id).ifPresentOrElse( s -> {
			s.setStatus(Status.Approved);
			saleRepo.save(s);
		}, () -> new AppBusinessException("Invalid ID"));
	}
	
	public void approveOrder(OrderSubmitForm form) {
		var sale = safeCall(saleRepo.findById(form.getId()), "Sale ID", form.getId());
		
		var items = form.getOrderItems();
		
		if(allDeleted(form)) {
			deleteItems(sale);
			saleRepo.delete(sale);
			return;
		}
		
		for(var i : items) {
			
			var pk = new SaleItemPK();
			pk.setSaleId(sale.getId());
			pk.setItemId(i.getItemId());
			var saleItem = safeCall(saleItemRepo.findById(pk), "Sale Item ID", pk);
			
			if(i.isDeleted()) {
				saleItemRepo.deleteById(pk);
				continue;
			}
			
			if(i.isModified()) {
				saleItem.setSalePrice(i.getSalePrice());
				saleItem.setQuantity(i.getQuantity());
				saleItem.setModified(true);
				saleItem.setReason(i.getReason());
			}
			saleItemRepo.save(saleItem);
		}
		
		sale.setStatus(Status.Approved);
		saleRepo.save(sale);
	}
	
	private boolean allDeleted(OrderSubmitForm form) {
		return form.getOrderItems().stream().filter(i -> !i.isDeleted()).count() == 0;
	}
	
	private void deleteItems(Sale sale) {
		saleItemRepo.deleteAll(sale.getSaleItems());
	}
}
