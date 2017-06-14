package com.vk.dispatcher.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class PickListItems {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Transient
	private String pkCode;
	private String sku;
	private String shelf;
	private String createdBy;
	@ManyToOne
	@JoinColumn(name="code")
	private PickList picklist;
	private Date created;
	private String asin;
	private int notFound;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getShelf() {
		return shelf;
	}
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public PickList getPicklist() {
		return picklist;
	}
	public void setPicklist(PickList picklist) {
		this.picklist = picklist;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPkCode() {
		return pkCode;
	}
	public void setPkCode(String pkCode) {
		this.pkCode = pkCode;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public int getNotFound() {
		return notFound;
	}
	public void setNotFound(int notFound) {
		this.notFound = notFound;
	}
	
	

}
