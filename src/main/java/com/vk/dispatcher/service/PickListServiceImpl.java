package com.vk.dispatcher.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vk.dispatcher.model.ItemMaster;
import com.vk.dispatcher.model.PickList;
import com.vk.dispatcher.model.PickListItems;
import com.vk.dispatcher.repository.ItemMasterRepository;
import com.vk.dispatcher.repository.PickListItemsRepository;
import com.vk.dispatcher.repository.PickListRepository;

@Service("pickListService")
public class PickListServiceImpl implements PickListService{

	@Autowired
	private PickListItemsRepository pickListItemRepository;
	
	@Autowired
	private PickListRepository pickListRepository;
	
	@Autowired
	private ItemMasterRepository itemMasterRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void savePickListItems(PickListItems pickListItem) {
		ItemMaster itemMaster=itemMasterRepository.findBySku(pickListItem.getSku());
		pickListItem.setPicklist(pickListRepository.findByCode(pickListItem.getPkCode()));
		pickListItem.setCreated(new Date());
		if(itemMaster!=null){
			pickListItem.setAsin(itemMaster.getAsin());
		}
		pickListItemRepository.save(pickListItem);
	}

	@Override
	public void savePickList(PickList pickList) {
		pickListRepository.save(pickList);
	}

	@Override
	public List<PickList> findAll() {
		return pickListRepository.findAll();
	}

	@Override
	public List<PickList> findByPurchaseOrderCode(String poCode,int isOpen) {
		return pickListRepository.findByPurchaseOrderCode(poCode,isOpen);
	}

	@Override
	public PickList findByCode(String code) {
		return pickListRepository.findByCode(code);
	}

	@Override
	public int markNotFound(String pk, String sku,String qty) {
		Query q=em.createNativeQuery("update pick_list_items set not_found=1 where sku=:sku and code=:pk limit "+qty);
		q.setParameter("sku", sku);
		q.setParameter("pk", pk);
		int res=q.executeUpdate();
		return res;
	}

	

}
