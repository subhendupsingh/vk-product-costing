package com.vk.dispatcher.service;

import java.util.List;

import com.vk.dispatcher.model.PickList;
import com.vk.dispatcher.model.PickListItems;

public interface PickListService {
	
	public void savePickListItems(PickListItems pickListItem);
	public void savePickList(PickList pickList);
	public List<PickList> findAll();
	public List<PickList> findByPurchaseOrderCode(String poCode,int isOpen);
	public PickList findByCode(String code);
	public int markNotFound(String pk,String sku,String qty);
}
