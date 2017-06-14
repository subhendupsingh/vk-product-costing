package com.vk.dispatcher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vk.dispatcher.model.PickList;
import com.vk.dispatcher.model.PurchaseOrder;

@Repository("pickListRepository")
public interface PickListRepository extends JpaRepository<PickList, String>{
	public PickList findByCode(String code);
	@Query("select pk from PickList pk where pk.purchaseOrder.code=? and pk.isOpen=?")
	public List<PickList> findByPurchaseOrderCode(String poCode,int isOpen);
}
