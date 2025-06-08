package com.tns.ordermanagement.controller.guest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tns.ordermanagement.controller.guest.dto.OrderForm;
import com.tns.ordermanagement.controller.guest.dto.OrderItem;

@Controller
@RequestMapping("guest/order")
@SessionAttributes("orderForm")
public class OrderController {

	@PostMapping
	String submitOrder(@ModelAttribute("orderForm") OrderForm form) {
		System.out.println("Form");
		System.out.println(form);
		return "guest/order-details";
	}
	
	@PostMapping("add")
	String addItem(@ModelAttribute("orderItem") OrderItem item, @ModelAttribute("orderForm") OrderForm form) {
		form.add(item);
		return "redirect:/";
	}
	
	@GetMapping("details")
	String showOrderDetails(@ModelAttribute("orderForm") OrderForm form, ModelMap model) {
		model.put("items", form.getItems());
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
