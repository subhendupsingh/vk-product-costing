package com.vk.dispatcher.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CartonItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String asin;
	@ManyToOne
	private Carton carton;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public Carton getCarton() {
		return carton;
	}
	public void setCarton(Carton carton) {
		this.carton = carton;
	}
	
	
}
