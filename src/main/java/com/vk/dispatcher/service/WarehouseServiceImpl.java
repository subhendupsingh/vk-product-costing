package com.vk.dispatcher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vk.dispatcher.model.Warehouse;
import com.vk.dispatcher.repository.WarehouseRepository;

@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService{
	
	@Autowired
	WarehouseRepository warehouseRepositoy;

	@Override
	public void saveWarehouse(Warehouse warehouse) {
		warehouseRepositoy.save(warehouse);
	}

	@Override
	public List<Warehouse> getAllWarehouses() {
		return warehouseRepositoy.findAll();
	}

	@Override
	public Warehouse getWarehouseByCode(String code) {
		return warehouseRepositoy.findByCode(code);
	}

	@Override
	public void deleteWarehouse(String code) {
		warehouseRepositoy.delete(code);
	}

	@Override
	public boolean doesExist(String code) {
		return warehouseRepositoy.exists(code);
	}

}
