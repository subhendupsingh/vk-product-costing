package com.vk.dispatcher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vk.dispatcher.model.PurchaseOrder;
import com.vk.dispatcher.service.PurchaseOrderService;

@Controller
public class PurchaseOrderController {
	
	@Autowired
	PurchaseOrderService purchaseOrderService;
	
	@RequestMapping(value={"/po"},method=RequestMethod.GET)
	public ModelAndView view(){
		ModelAndView modelAndView=new ModelAndView();
		List<PurchaseOrder> openPurchaseOrderList=purchaseOrderService.findByIsOpen(1);
		List<PurchaseOrder> closedPurchaseOrderList=purchaseOrderService.findByIsOpen(0);
		modelAndView.addObject("openPurchaseOrderList",openPurchaseOrderList);
		modelAndView.addObject("closedPurchaseOrderList",closedPurchaseOrderList);
		modelAndView.setViewName("purchaseorder");
		return modelAndView;
	}
}
