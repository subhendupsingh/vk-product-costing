package com.vk.dispatcher.service;

import java.util.List;

import com.vk.dispatcher.model.Carton;
import com.vk.dispatcher.model.CartonItem;

public interface CartonService {
	public Carton saveCarton(Carton carton);
	public int getMaxCartonNo(String pk,String po);
	public List<Carton> findByPickListAndPurchaseOrder(String pk,String po);
	public CartonItem saveCartonItem(CartonItem cartonItem);
	public Carton find(int id);
	public String closePickList(String pkCode);
}
