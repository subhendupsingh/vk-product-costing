package com.vk.dispatcher.service;

import java.util.List;

import com.vk.dispatcher.model.PurchaseOrder;

public interface PurchaseOrderService {
	public void savePurchaseOrder(PurchaseOrder purchaseOrder);
	public List<PurchaseOrder> getAll();
	public boolean doesExist(String code);
	public List<PurchaseOrder> findByIsOpen(int flag);
	public PurchaseOrder findByCode(String code);
}
