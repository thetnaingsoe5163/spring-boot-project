package com.tns.ordermanagement.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.tns.ordermanagement.controller.guest.dto.OrderForm;
import com.tns.ordermanagement.model.entity.Sale;
import com.tns.ordermanagement.model.entity.SaleItem;
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

	public void submit(OrderForm form) {
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
		saleRepo.save(saleEntity);
	}
}
