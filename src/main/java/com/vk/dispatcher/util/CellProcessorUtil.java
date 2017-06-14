package com.vk.dispatcher.util;

import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class CellProcessorUtil {
	 public static CellProcessor[] getPurchaseOrdersProcessors() {
	        final CellProcessor[] processors = new CellProcessor[] {
	                new NotNull(), // code
	                null,
	                new NotNull(new ParseDate("dd/MM/yyyy",true)), // order date
	                new NotNull(),
	                new NotNull(new ParseInt()), // quantity,
	                null,
	                null
	        };
	        return processors;
	    }
	 
	 public static String[] getPurchaseOrdersHeader(){
		 return new String[]{"code",null,"orderDate","warehouseCode","quantity",null,null};
	 }
	 
	 public static CellProcessor[] getPickListProcessors() {
	        final CellProcessor[] processors = new CellProcessor[] {
	                new NotNull(), // code
	                null,
	                null,
	                null,
	                null,
	                new NotNull(), // sku
	                null,
	                new NotNull(),//shelf
	                new NotNull(), // createdby
	                null,
	                null,
	                null,
	                null
	        };
	        return processors;
	    }
	 
	 public static String[] getPickListHeader(){
		 return new String[]{"pkCode",null,null,null,null,"sku",null,"shelf","createdBy",null,null,null,null};
	 }
	 
	 public static CellProcessor[] getItemMasterProcessors() {
	        final CellProcessor[] processors = new CellProcessor[] {
	                null,
	                new NotNull(), //sku
	                new NotNull(), //name
	                null,
	                new NotNull(), //asin
	                null, 
	                null,
	                null,
	                null,
	                null,
	                null,
	                null,
	                null,
	                null,
	                null,
	                null,
	                null,
	                new NotNull(), //imageurl
	                null,
	                null,
	                null,
	                null,
	                null,
	                null,
	                null,
	                null,
	                null,
	                null
	        };
	        return processors;
	    }
	 
	 public static String[] getItemMasterHeader(){
		 return new String[]{"pkCode",null,null,null,null,"sku",null,"shelf","createdBy",null,null,null,null};
	 }
}
