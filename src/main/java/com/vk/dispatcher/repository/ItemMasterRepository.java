package com.vk.dispatcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vk.dispatcher.model.ItemMaster;

@Repository("itemMasterRepository")
public interface ItemMasterRepository extends JpaRepository<ItemMaster, String>{
	
		@Query("select im from ItemMaster im where im.sku=?")
		public ItemMaster findBySku(String sku);
	
}
