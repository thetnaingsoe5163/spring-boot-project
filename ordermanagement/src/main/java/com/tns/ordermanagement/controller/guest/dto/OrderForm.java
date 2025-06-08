package com.tns.ordermanagement.controller.guest.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OrderForm {

	private List<OrderItem> items = new ArrayList<>();

	public void add(OrderItem item) {
		var index = index(item.getId());
		if(index < 0) {
			items.add(item);
		} else {
			var i = items.get(index);
			i.setQuantity(i.getQuantity() + item.getQuantity());
			i.setDetails(i.getDetails().concat("\n").concat(item.getDetails()));
		}
	}
	
	public int getTotalQuantity() {
		return items.stream().map(OrderItem::getQuantity).reduce(0, (a, b) -> a + b);
	}

	private int index(int id) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}
}
