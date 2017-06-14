package com.vk.dispatcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vk.dispatcher.model.Warehouse;

@Repository("warehouseRepository")
public interface WarehouseRepository extends JpaRepository<Warehouse, String>{
		Warehouse findByCode(String code);
}
