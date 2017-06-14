package com.vk.dispatcher.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="purchase_order")
public class PurchaseOrder {
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Id
	@Column(name="purchase_order_code")
	private String code;
	private Date orderDate;
	@Transient
	private String warehouseCode;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="warehouse_code")
	private Warehouse warehouse;
	private int quantity;
	private int isOpen;
	private int isAppointment;
	private int isPod;
	private Date created;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	public int getIsAppointment() {
		return isAppointment;
	}
	public void setIsAppointment(int isAppointment) {
		this.isAppointment = isAppointment;
	}
	public int getIsPod() {
		return isPod;
	}
	public void setIsPod(int isPod) {
		this.isPod = isPod;
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
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	
	
}
