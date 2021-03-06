package com.wc.shopper.domain;


import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import javax.xml.bind.annotation.XmlRootElement;@XmlRootElement 

@Entity 
public class Item  extends BaseEntity implements java.io.Serializable {


	
	@Column
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column
	private Double price;

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	@Column
	private int stock;

	public int getStock() {
		return this.stock;
	}

	public void setStock(final int stock) {
		this.stock = stock;
	}

	public String toString() {
		String result = "";
		if (name != null && !name.trim().isEmpty())
			result += name;
		if (price != null)
			result += " " + price;
		result += " " + stock;
		return result;
	} }