package com.vk.productcosting.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vk.productcosting.model.PricingTemplate;
import com.vk.productcosting.service.ItemMasterService;
import com.vk.productcosting.service.PoCheckerService;
import com.vk.productcosting.service.PricingTemplateService;
import com.vk.productcosting.service.PurchaseOrderService;
import com.vk.productcosting.util.Constants;

@Controller
public class UploadController extends BaseController{
	
	@Value("${file.upload.path}")
	private String fileUploadPath;
	
	@Autowired
	private ItemMasterService itemMasterService;
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private PricingTemplateService pricingTemplateService;
	
	@Autowired
	private PoCheckerService poCheckerService;
	
	
	@RequestMapping(value={"/upload"},method=RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes
			,@RequestParam("importType") String importType,@RequestParam(name="templateName",required=false) String templateName){
		String returnView="redirect:/import";
		int count=0;
		if(!importType.isEmpty()){
			
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
	            if(importType.equalsIgnoreCase("im")){
	            	count=saveItemMaster(filePath);
				}else if(importType.equalsIgnoreCase("po")){
					count=savePurchaseOrder(filePath);
				}else if(importType.equalsIgnoreCase("margin")){
					count=savePricingTemplateMargins(filePath,templateName);
					returnView="redirect:/pricingtemplate";
				}
	            
	            redirectAttributes.addFlashAttribute("count",count);
	            redirectAttributes.addFlashAttribute("message",
	                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
        return returnView;
		
	}
	
	@ResponseBody
	@RequestMapping(value={"/uploadCTPO"},method=RequestMethod.POST)
	public String uploadCTPOFile(@RequestParam("file") MultipartFile file){
	
	        try {
	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(fileUploadPath + file.getOriginalFilename());
	            Files.write(path, bytes);
	            String filePath=fileUploadPath+file.getOriginalFilename();
	            
				return saveAndCheckCTPO(filePath);
	            
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        return "failure";
		
	}
	
	
	@RequestMapping(value={"/downloadDocument"},method=RequestMethod.POST)
	public void downloadDocument(@RequestParam("fileName") String fileName,HttpServletRequest request, HttpServletResponse response,
			@RequestParam("filePath") String filePath){
		//response.setContentType("application/vnd.ms-excel");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);
	    try {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        baos = Constants.convertPDFToByteArrayOutputStream(filePath);
	        OutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	}
	
	@RequestMapping(value={"/import"},method=RequestMethod.GET)
	public ModelAndView openImportPage(){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("import");
		return modelAndView;
	}
	
	public String saveAndCheckCTPO(String filePath) {
		return poCheckerService.uploadAndCheckPurchaseOrder(filePath);
	}
	
	public int savePricingTemplateMargins(String filePath,String templateName) {
		PricingTemplate pricingTemplate=new PricingTemplate();
		pricingTemplate.setName(templateName);
		pricingTemplate.setCreated(new Date());
		pricingTemplate.setCreatedBy(getLoggedInUserName());
		return pricingTemplateService.uploadPricingTemplateItems(filePath, pricingTemplateService.savePricingTemplate(pricingTemplate).getId());
	}
	
	public int saveItemMaster(String filePath){
		/*ICsvListReader reader;
		int count=0;
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
		    	itemMaster.setHsn(line.get(32));
		    	if(null!=line.get(34) && !line.get(34).isEmpty()){
		    		itemMaster.setCp(Double.parseDouble(line.get(34)));
		    	}
		    	if(null!=line.get(35) && !line.get(35).isEmpty()){
		    		itemMaster.setGst(Integer.parseInt(line.get(35)));
		    	}
		    	if(null!=line.get(36) && !line.get(36).isEmpty()){
		    		itemMaster.setPhase(Integer.parseInt(line.get(36)));
		    	}
		    	if(null!=line.get(6) && !line.get(6).isEmpty()){
		    		itemMaster.setLength(Double.parseDouble(line.get(6)));
		    	}
		    	if(null!=line.get(7) && !line.get(7).isEmpty()){
		    		itemMaster.setWidth(Double.parseDouble(line.get(7)));
		    	}
		    	if(null!=line.get(8) && !line.get(8).isEmpty()){
		    		itemMaster.setHeight(Double.parseDouble(line.get(8)));
		    	}
		    	if(null!=line.get(9) && !line.get(9).isEmpty()){
		    		itemMaster.setWeight(Double.parseDouble(line.get(9)));
		    	}
		    	itemMaster.setUpdateHistory(1);
		    	itemMasterService.saveItemMaster(itemMaster);
		    	count++;
		    }

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		    return itemMasterService.uploadItemMaster(filePath);
	}
	
	public int savePurchaseOrder(String filePath){
		return purchaseOrderService.uploadPurchaseOrders(filePath);
	
	}
	
	@ResponseBody
	@RequestMapping(value= {"/lastUpdated"},method=RequestMethod.GET)
	public String getLastCreatedDate(@RequestParam("type") String type) {
		return itemMasterService.getLastUpdated(type);
		
	}
	
}
