package com.vk.dispatcher.service;

import java.util.List;

import com.vk.dispatcher.model.Warehouse;

public interface WarehouseService {
	
	public void saveWarehouse(Warehouse warehouse);
	public List<Warehouse> getAllWarehouses();
	public Warehouse getWarehouseByCode(String code);
	public void deleteWarehouse(String code);
	public boolean doesExist(String code);
}
