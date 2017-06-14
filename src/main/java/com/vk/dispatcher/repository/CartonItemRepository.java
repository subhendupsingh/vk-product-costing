package com.vk.dispatcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vk.dispatcher.model.CartonItem;

@Repository("cartonItemRepository")
public interface CartonItemRepository extends JpaRepository<CartonItem, Integer>{

}
