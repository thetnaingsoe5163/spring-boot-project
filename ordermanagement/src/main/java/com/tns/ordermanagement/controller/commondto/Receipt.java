package com.tns.ordermanagement.controller.commondto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tns.ordermanagement.model.entity.OrderTransaction;

import lombok.Data;

@Data
public class Receipt {
	
	private UUID trxId;
	private List<ReceiptItem> items;

	public static Receipt convert(OrderTransaction order) {
		var r = new Receipt();
		r.setTrxId(order.getId());
		
		var list = new ArrayList<ReceiptItem>();
		for(var i : order.getItems()) {
			list.add(ReceiptItem.convert(i));
		}
		r.setItems(list);
		
		return r;
	}
	
	public BigDecimal allTotalAmount() {
		return items.stream().map(i -> i.totalPrice()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
	}
}
