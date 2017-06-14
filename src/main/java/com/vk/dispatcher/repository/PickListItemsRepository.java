package com.vk.dispatcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vk.dispatcher.model.PickListItems;

@Repository("pickListItemsRepository")
public interface PickListItemsRepository extends JpaRepository<PickListItems, String>{

}
