package com.tns.ordermanagement.controller.guest;

import java.util.ArrayList;

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

import com.tns.ordermanagement.controller.guest.dto.OrderForm;
import com.tns.ordermanagement.controller.guest.dto.OrderItem;
import com.tns.ordermanagement.service.OrderNotificationService;
import com.tns.ordermanagement.service.OrderTransactionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("guest/order")
@SessionAttributes({ "orderForm"})
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderTransactionService orderTrxService;	
	private final OrderNotificationService orderNotiService;

	@PostMapping()
	String submitOrder(
			@RequestParam int tableNumber,
			@RequestParam String sessionId, 
			@ModelAttribute("orderForm") OrderForm form) {		
		
		var trx = orderTrxService.submit(form, sessionId, tableNumber);
		var trxDto = orderTrxService.convertOrderTrxDto(trx); 
		System.out.println(trxDto);
		orderNotiService.notifyNewOrder(trxDto);
		form.clear();
		System.out.println("Table Number: %d".formatted(tableNumber));
		System.out.println("Session ID: %s%n".formatted(sessionId));
		System.out.println(form);
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

//	@GetMapping("history")
//	String orderHistory(@ModelAttribute("transactions") Transaction transaction, ModelMap model) {
//		if(transaction.getIds().size() != 0) {
//			var history = saleItemService.findBySaleId(transaction.getIds());
//			model.addAttribute("history", history);
//			System.out.println(history);
//		}
//		return "guest/history";
//	}

	@ModelAttribute("orderForm")
	OrderForm getOrderForm() {
		return new OrderForm();
	}

	@ModelAttribute("orderItem")
	OrderItem getOrderItem() {
		return new OrderItem();
	}
}
