package com.tns.ordermanagement.controller.guest;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tns.ordermanagement.controller.guest.dto.OrderForm;
import com.tns.ordermanagement.controller.guest.dto.OrderItem;
import com.tns.ordermanagement.service.OrderNotificationService;
import com.tns.ordermanagement.service.SaleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("guest/order")
@SessionAttributes("orderForm")
@RequiredArgsConstructor
public class OrderController {

	private final SaleService saleService;
	private final OrderNotificationService orderNotiService;
	
	@PostMapping
	String submitOrder(@ModelAttribute("orderForm") OrderForm form) {
		var id = saleService.submit(form);
		var item = saleService.findSaleById(id);
		orderNotiService.notifyNewOrder(item);
		form.clear();
		return "redirect:/";
	}
	
	@PostMapping("add")
	@ResponseBody
	Integer addItem(
			@ModelAttribute("orderItem") OrderItem item, 
			@ModelAttribute("orderForm") OrderForm form) {
		
		form.add(item);
		return form.getTotalQuantity();
	}
	
	@PostMapping("remove")
	String removeItem(@ModelAttribute("orderForm") OrderForm form, ModelMap model) {
		var list = new ArrayList<>(form.getItems().stream().filter(i -> !i.isDeleted()).toList());
		form.setItems(list);
		return "redirect:/guest/order/details";
	}
	
	@GetMapping("details")
	String showOrderDetails(@ModelAttribute("orderForm") OrderForm form, ModelMap model) {
		model.put("form", form);
		return "guest/order-details";
	}
	
	@ModelAttribute("orderForm")
	OrderForm getOrderForm() {
		return new OrderForm();
	}
	
	@ModelAttribute("orderItem")
	OrderItem getOrderItem() {
		return new OrderItem();
	}
}
