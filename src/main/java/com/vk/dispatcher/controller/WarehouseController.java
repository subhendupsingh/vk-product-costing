package com.vk.dispatcher.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vk.dispatcher.model.User;
import com.vk.dispatcher.model.Warehouse;
import com.vk.dispatcher.service.WarehouseService;

@Controller
public class WarehouseController {
	
	@Autowired
	WarehouseService warehouseService;
	
	@RequestMapping(value={"/warehouses"}, method = RequestMethod.GET)
	public ModelAndView getAllWarehouses(){
		ModelAndView modelAndView = new ModelAndView();
		List<Warehouse> warehouses=warehouseService.getAllWarehouses();
		Warehouse warehouse=new Warehouse();
		modelAndView.addObject("warehouse", warehouse);
		modelAndView.addObject("warehouseList",warehouses);
		modelAndView.setViewName("warehouses");
		return modelAndView;
	}
	
	@RequestMapping(value = "/warehouses", method = RequestMethod.POST)
	public ModelAndView createWarehouse(@Valid Warehouse warehouse, BindingResult bindingResult) {
			ModelAndView modelAndView = new ModelAndView();
			List<Warehouse> warehouses=warehouseService.getAllWarehouses();
			modelAndView.addObject("warehouseList",warehouses);
			if(!warehouseService.doesExist(warehouse.getCode())){
				warehouseService.saveWarehouse(warehouse);
				modelAndView.addObject("message", "Warehouse has been added!");
				modelAndView.addObject("warehouse", new Warehouse());
				modelAndView.setViewName("warehouses");
			}else{
				modelAndView.addObject("message", "Warehouse already exists!");
				modelAndView.addObject("warehouse", new Warehouse());
				modelAndView.setViewName("warehouses");
			}
			return modelAndView;
	}
	
	@RequestMapping(value = "/warehouses/delete", method = RequestMethod.POST)
	public ModelAndView deleteWarehouse(@RequestParam("code") String code) {
			warehouseService.deleteWarehouse(code);
			ModelAndView modelAndView = new ModelAndView();
			List<Warehouse> warehouses=warehouseService.getAllWarehouses();
			modelAndView.addObject("warehouseList",warehouses);
			modelAndView.addObject("warehouse", new Warehouse());
			modelAndView.addObject("message", "Warehouse deleted!");
			modelAndView.setViewName("warehouses");
			return modelAndView;
	}
}
