package com.wishop.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Wishlist {
	@Id @GeneratedValue
	private int wishlistId;
	@Temporal(TemporalType.DATE)
	private Date dateCreated;
	private String description;
	@ManyToMany
	private Collection<Sku> skuList = new ArrayList<Sku>();
	
	public int getWishlistId() {
		return wishlistId;
	}
	public void setWishlistId(int wishlistId) {
		this.wishlistId = wishlistId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Collection<Sku> getSkuList() {
		return skuList;
	}
	public void setSkuList(Collection<Sku> skuList) {
		this.skuList = skuList;
	}
	
}
