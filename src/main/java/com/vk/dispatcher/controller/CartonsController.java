package com.vk.dispatcher.controller;

import java.util.Date;
import java.util.List;

import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vk.dispatcher.model.Carton;
import com.vk.dispatcher.model.PickList;
import com.vk.dispatcher.service.CartonService;
import com.vk.dispatcher.service.PickListService;

@Controller
public class CartonsController {
	
	@Autowired
	private CartonService cartonService;
	
	@Autowired
	private PickListService pickListService;

	@RequestMapping(value={"/cartons"},method=RequestMethod.GET)
	public ModelAndView openCartonsPage(@RequestParam("po") String po,@RequestParam("pk") String pk){
		ModelAndView modelAndView=new ModelAndView();
		List<Carton> cartonsList=cartonService.findByPickListAndPurchaseOrder(pk, po);
		modelAndView.addObject("cartonsList",cartonsList);
		modelAndView.addObject("po",po);
		modelAndView.addObject("pk",pk);
		modelAndView.setViewName("cartons");
		cartonService.closePickList("PK578");
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value={"/cartons"},method=RequestMethod.POST)
	public String openCarton(@RequestParam("po") String po,@RequestParam("pk") String pk){
		try{
			int cartonNo=1;
			PickList pickList=pickListService.findByCode(pk);
			Carton carton=new Carton();
			try{
				cartonNo=cartonService.getMaxCartonNo(pk, po);
			}catch(AopInvocationException e){
				cartonNo=0;
				System.out.println("#####No carton for this picklist has been added yet so adding the first carton####");
			}
			carton.setCreated(new Date());
			carton.setPicklist(pickList);
			carton.setPo(po);
			carton.setIsOpen(1);
			int cartonNext=cartonNo+1;
			carton.setSerialNumber(cartonNext);
			Carton savedCarton=cartonService.saveCarton(carton);
			return "{\"number\":\""+savedCarton.getSerialNumber()+"\",\"id\":\""+savedCarton.getId()+"\"}";
		}catch(Exception e){
			e.printStackTrace();
			return "failure";
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/cartons/close"},method=RequestMethod.POST)
	public String closeCarton(@RequestParam("id") int id,@RequestParam("length") Double length,
			@RequestParam("width") Double width,@RequestParam("height") Double height,@RequestParam("weight")
	Double weight){
		try{
			Carton carton=cartonService.find(id);
			carton.setLength(length);
			carton.setWidth(width);
			carton.setHeight(height);
			carton.setWeight(weight);
			carton.setIsOpen(0);
			cartonService.saveCarton(carton);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "failure";
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/cartons/reopen"},method=RequestMethod.POST)
	public String reopenCarton(@RequestParam("id") int id){
		try{
			Carton carton=cartonService.find(id);
			carton.setIsOpen(1);
			cartonService.saveCarton(carton);
			return "{\"number\":\""+carton.getSerialNumber()+"\",\"id\":\""+carton.getId()+"\",\"length\":\""+carton.getLength()+"\",\"width\":\""+carton.getWidth()+"\",\"height\":\""+carton.getHeight()+"\",\"weight\":\""+carton.getWeight()+"\"}";
		}catch(Exception e){
			e.printStackTrace();
			return "failure";
		}
	}
	
}
