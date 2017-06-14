package com.vk.dispatcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vk.dispatcher.model.ItemMaster;
import com.vk.dispatcher.repository.ItemMasterRepository;

@Service("itemMasterService")
public class ItemMasterServiceImpl implements ItemMasterService{
	
	@Autowired
	ItemMasterRepository itemMasterRepository;
	
	@Override
	public void saveItemMaster(ItemMaster itemMaster) {
		itemMasterRepository.save(itemMaster);
	}

	@Override
	public void findBySku(String sku) {
		itemMasterRepository.findBySku(sku);
	}

}
