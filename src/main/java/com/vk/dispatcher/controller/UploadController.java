package com.vk.dispatcher.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.vk.dispatcher.model.ItemMaster;
import com.vk.dispatcher.model.PickList;
import com.vk.dispatcher.model.PickListItems;
import com.vk.dispatcher.model.PurchaseOrder;
import com.vk.dispatcher.service.ItemMasterService;
import com.vk.dispatcher.service.PickListService;
import com.vk.dispatcher.service.PurchaseOrderService;
import com.vk.dispatcher.util.CellProcessorUtil;

@Controller
public class UploadController {
	
	@Value("${file.upload.path}")
	private String fileUploadPath;
	
	@Autowired
	PurchaseOrderService purchaseOrderService;
	
	@Autowired
	PickListService pickListService;
	
	@Autowired
	ItemMasterService itemMasterService;
	
	@RequestMapping(value={"/upload"},method=RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes
			,@RequestParam("importType") String importType,@RequestParam(name="po",required=false) String po){
		
		String returnView="redirect:/warehouses";
		if(!importType.isEmpty()){
			if(importType.equalsIgnoreCase("picklist")){
				returnView= "redirect:/picklist?po="+po;
			}else if(importType.equalsIgnoreCase("po")){
				returnView="redirect:/po";
			}
			
			if (file.isEmpty()) {
				redirectAttributes.addFlashAttribute("status", "info");
	            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
	            return returnView;
	        }
	
	        try {
	
	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(fileUploadPath + file.getOriginalFilename());
	            Files.write(path, bytes);
	            String filePath=fileUploadPath+file.getOriginalFilename();
	            if(importType.equalsIgnoreCase("picklist")){
					savePicklistItems(filePath,po);
				}else if(importType.equalsIgnoreCase("po")){
					savePurchaseOrders(filePath);
				}else if(importType.equalsIgnoreCase("itemmaster")){
					saveItemMaster(filePath);
				}
	            redirectAttributes.addFlashAttribute("message",
	                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
        return returnView;
		
	}
	
	
	public void savePurchaseOrders(String filePath){
		try(ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(filePath), CsvPreference.STANDARD_PREFERENCE))
        {
            //First column is header
            beanReader.getHeader(true);
            final CellProcessor[] processors = CellProcessorUtil.getPurchaseOrdersProcessors();
 
            PurchaseOrder purchaseOrder;
            while ((purchaseOrder = beanReader.read(PurchaseOrder.class, CellProcessorUtil.getPurchaseOrdersHeader(),processors)) != null) {
            	purchaseOrderService.savePurchaseOrder(purchaseOrder);
            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void savePicklistItems(String filePath,String po){
		
		try(ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(filePath), CsvPreference.STANDARD_PREFERENCE))
        {
            //First column is header
            beanReader.getHeader(true);
            final CellProcessor[] processors = CellProcessorUtil.getPickListProcessors();
 
            PickListItems pickListItem;
            int counter=0;
            while ((pickListItem = beanReader.read(PickListItems.class, CellProcessorUtil.getPickListHeader(),processors)) != null) {
            	if(counter==0){
            		PickList pickList=new PickList();
            		PurchaseOrder purchaseOrder=purchaseOrderService.findByCode(po);
            		pickList.setPurchaseOrder(purchaseOrder);
            		pickList.setCode(pickListItem.getPkCode());
            		pickList.setCreated(new Date());
            		pickList.setIsOpen(1);
            		pickListService.savePickList(pickList);
            	}
            	pickListService.savePickListItems(pickListItem);
            	counter++;
            }
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void saveItemMaster(String filePath){
		ICsvListReader reader;
		try {
			reader = new CsvListReader(new FileReader(filePath), 
			        CsvPreference.STANDARD_PREFERENCE);
			reader.getHeader(true); // skip header


		    List<String> line;
		    while((line = reader.read()) != null){
		    	ItemMaster itemMaster=new ItemMaster();
		    	itemMaster.setSku(line.get(1));
		    	itemMaster.setName(line.get(2));
		    	itemMaster.setAsin(line.get(4));
		    	itemMaster.setImageUrl(line.get(18));
		    	itemMasterService.saveItemMaster(itemMaster);
		    }

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
	}
	
	@RequestMapping(value={"/import"},method=RequestMethod.GET)
	public ModelAndView openImportPage(){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("import");
		return modelAndView;
	}
	
}
