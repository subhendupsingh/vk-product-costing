package com.vk.dispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vk.dispatcher.service.CartonService;
import com.vk.dispatcher.service.PickListService;

@Controller
public class PickListController {

	@Autowired
	private PickListService pickListService;
	
	@Autowired
	private CartonService cartonService;
	
	@RequestMapping(value="picklist",method=RequestMethod.GET)
	public ModelAndView view(@RequestParam("po") String po){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("pickListList",pickListService.findByPurchaseOrderCode(po,1));
		modelAndView.addObject("pickListListClosed",pickListService.findByPurchaseOrderCode(po,0));
		modelAndView.setViewName("picklist");
		modelAndView.addObject("po",po);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value={"picklist/close"},method=RequestMethod.POST)
	public String closePickList(@RequestParam("pk") String pk){
		return cartonService.closePickList(pk);
		
	}
	
	@ResponseBody
	@RequestMapping(value={"picklist/notfound"},method=RequestMethod.POST)
	public String markNotFound(@RequestParam("pk") String pk,@RequestParam("sku")String sku,@RequestParam("quantity") String qty){
		int res= pickListService.markNotFound(pk, sku, qty);
		return "";
	}
}
