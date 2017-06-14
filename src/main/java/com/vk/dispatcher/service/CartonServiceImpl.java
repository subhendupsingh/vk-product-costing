package com.vk.dispatcher.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vk.dispatcher.model.Carton;
import com.vk.dispatcher.model.CartonItem;
import com.vk.dispatcher.model.PickList;
import com.vk.dispatcher.repository.CartonItemRepository;
import com.vk.dispatcher.repository.CartonRepository;
import com.vk.dispatcher.repository.PickListRepository;

@Service("cartonService")
public class CartonServiceImpl implements CartonService{

	@Autowired
	private CartonRepository cartonRepository;
	
	@Autowired
	private CartonItemRepository cartonItemRepository;
	
	@Autowired
	private PickListRepository pickListRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Carton saveCarton(Carton carton) {
		return cartonRepository.save(carton);
	}

	@Override
	public int getMaxCartonNo(String pk, String po) {
		return cartonRepository.getMaxCartonNo(pk, po);
	}

	@Override
	public List<Carton> findByPickListAndPurchaseOrder(String pk,String po) {
		return cartonRepository.findByPickListAndPurchaseOrder(pk,po);
	}

	@Override
	public CartonItem saveCartonItem(CartonItem cartonItem) {
		return cartonItemRepository.save(cartonItem);
	}

	@Override
	public Carton find(int id) {
		return cartonRepository.findOne(id);
	}

	@Override
	public String closePickList(String pkCode) {
		StringBuilder sb=new StringBuilder();
		List<Carton> openCartonsList=cartonRepository.findByPickListAndIsOpen(pkCode, 1);
		if(openCartonsList!=null && !openCartonsList.isEmpty()){
			sb.append("Close all the cartons before closing the picklist.<br>\n");
		}
		Query q=em.createNativeQuery("SELECT t2.asin,IFNULL(t1.c1,0) AS carton,IFNULL(t2.p1,0) AS picklist FROM (SELECT ASIN,COUNT(ASIN) AS c1 FROM carton_item WHERE carton_id IN (SELECT id FROM carton WHERE CODE = :pk1) GROUP BY ASIN )  t1 RIGHT JOIN (SELECT ASIN,COUNT(ASIN) AS p1 FROM pick_list_items WHERE CODE = :pk2 GROUP BY ASIN )  t2 ON t1.asin = t2.asin WHERE IFNULL(t1.c1,0)<>IFNULL(t2.p1,0)");
		q.setParameter("pk1", pkCode);
		q.setParameter("pk2", pkCode);
		List<Object[]> rows=q.getResultList();
		if(rows!=null && !rows.isEmpty()){
			sb.append("<table class='table table-bordered'><tr>");
			sb.append("<th>ASIN</th>");
			sb.append("<th>PICKLIST</th>");
			sb.append("<th>PACKED</th></tr>");
			for(int i=0;i<rows.size();i++){
				sb.append("<tr>");
				Object[] o=rows.get(i);
				sb.append("<td>"+o[0]+"</td>");
				sb.append("<td>"+o[2]+"</td>");
				sb.append("<td>"+o[1]+"</td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
		}
		
		if(sb.length()==0){
			sb.append("success");
			PickList pickList=pickListRepository.findOne(pkCode);
			pickList.setIsOpen(0);
			pickListRepository.save(pickList);
		}
		
		return sb.toString();
	}

}
