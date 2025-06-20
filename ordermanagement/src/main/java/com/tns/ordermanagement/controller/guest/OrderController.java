package com.tns.ordermanagement.controller.guest;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tns.ordermanagement.controller.guest.dto.OrderForm;
import com.tns.ordermanagement.controller.guest.dto.OrderItem;
import com.tns.ordermanagement.service.NotificationService;
import com.tns.ordermanagement.service.OrderTransactionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("guest/order")
@SessionAttributes({ "orderForm"})
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderTransactionService orderTrxService;	
	private final NotificationService notiService;

	@PostMapping()
	String submitOrder(
			@RequestParam int tableNumber,
			@RequestParam String sessionId, 
			@ModelAttribute("orderForm") OrderForm form,
			RedirectAttributes attr) {		
		
		var trx = orderTrxService.submit(form, sessionId, tableNumber);
		var trxDto = orderTrxService.convertOrderTrxDto(trx);
		var tableDto = orderTrxService.convertTableDto(trx);
		
		notiService.notifyNewOrder(trxDto);
		notiService.notifyTableStatusChanges(tableDto);
		
		form.clear();
		
		return "redirect:/%d".formatted(tableNumber);
	}

	@PostMapping("add")
	@ResponseBody
	Integer addItem(@ModelAttribute("orderItem") OrderItem item, @ModelAttribute("orderForm") OrderForm form) {
		form.add(item);
		return form.getTotalQuantity();
	}

	@PostMapping("remove")
	String removeItem(@ModelAttribute("orderForm") OrderForm form, ModelMap model) {
		var list = new ArrayList<>(form.getItems().stream().filter(i -> !i.isDeleted()).toList());
		form.setItems(list);
		return "redirect:/guest/order/details";
	}

	@GetMapping("details/{tableNumber}")
	String showOrderDetails(
			@PathVariable int tableNumber,
			@ModelAttribute("orderForm") OrderForm form, ModelMap model) {
		model.put("tableNumber", tableNumber);
		model.put("form", form);
		return "guest/order-details";
	}

	@GetMapping("history/{tableNumber}")
	String orderHistory(
			@RequestParam UUID id,
			@PathVariable int tableNumber,
			ModelMap model) {
		
		var orderHistory = orderTrxService.findActiveOrderHistory(id);
		if(orderHistory == null) {
			return "redirect:/%d".formatted(tableNumber);
		}
		model.addAttribute("history", orderHistory);
		return "guest/history";
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
