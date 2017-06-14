package com.vk.dispatcher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vk.dispatcher.model.Carton;

@Repository("cartonRepository")
public interface CartonRepository extends JpaRepository<Carton, Integer>{
	@Query("select max(c.serialNumber) from Carton c where c.picklist.code=? and c.po=?")
	public int getMaxCartonNo(String pk,String po);
	@Query("select c from Carton c where c.picklist.code=? and c.po=? order by c.created desc")
	public List<Carton> findByPickListAndPurchaseOrder(String pk,String po);
	@Query("select c from Carton c where c.picklist.code=? and c.isOpen=?")
	public List<Carton> findByPickListAndIsOpen(String pk,int isOpen);
}
