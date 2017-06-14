package com.vk.dispatcher.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vk.dispatcher.model.PurchaseOrder;
import com.vk.dispatcher.model.Warehouse;
import com.vk.dispatcher.repository.PurchaseOrderRepository;
import com.vk.dispatcher.repository.WarehouseRepository;

@Service("purchaseOrderService")
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	WarehouseRepository warehouseRepository;
	
	@Override
	public void savePurchaseOrder(PurchaseOrder purchaseOrder) {
				if(!purchaseOrderRepository.exists(purchaseOrder.getCode())){
				String parts[]=purchaseOrder.getWarehouseCode().split("-");
				Warehouse warehouse=warehouseRepository.findOne(parts[0].trim());
				purchaseOrder.setWarehouse(warehouse);
				purchaseOrder.setCreated(new Date());
				purchaseOrder.setIsOpen(1);
				purchaseOrderRepository.save(purchaseOrder);
		}
	}

	@Override
	public List<PurchaseOrder> getAll() {
		return purchaseOrderRepository.findAll();
	}

	@Override
	public boolean doesExist(String code) {
		return purchaseOrderRepository.exists(code);
	}

	@Override
	public List<PurchaseOrder> findByIsOpen(int flag) {
		return purchaseOrderRepository.findByIsOpen(flag);
	}

	@Override
	public PurchaseOrder findByCode(String code) {
		return purchaseOrderRepository.findByCode(code);
	}

}
