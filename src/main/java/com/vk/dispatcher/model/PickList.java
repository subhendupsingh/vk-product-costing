package com.vk.dispatcher.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PickList {
	
	@Id
	private String code;
	private Date created;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="purchase_order_code")
	private PurchaseOrder purchaseOrder;
	@OneToMany(mappedBy="picklist",fetch=FetchType.LAZY)
	private Set<PickListItems> pickListItems;
	@OneToMany(mappedBy="picklist",fetch=FetchType.LAZY)
	private List<Carton> cartons;
	private int isOpen;
	
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public Set<PickListItems> getPickListItems() {
		return pickListItems;
	}
	public void setPickListItems(Set<PickListItems> pickListItems) {
		this.pickListItems = pickListItems;
	}
	public List<Carton> getCartons() {
		return cartons;
	}
	public void setCartons(List<Carton> cartons) {
		this.cartons = cartons;
	}
	
}
