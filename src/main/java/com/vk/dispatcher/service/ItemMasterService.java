package com.vk.dispatcher.service;

import com.vk.dispatcher.model.ItemMaster;

public interface ItemMasterService {
	
	public void saveItemMaster(ItemMaster itemMaster);
	public void findBySku(String sku);
}
