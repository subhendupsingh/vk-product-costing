package com.vk.dispatcher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vk.dispatcher.model.PurchaseOrder;

@Repository("purchaseOrderRepository")
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {
	
	public List<PurchaseOrder> findByIsOpen(int flag);
	public PurchaseOrder findByCode(String code);
}
