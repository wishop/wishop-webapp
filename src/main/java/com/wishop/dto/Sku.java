package com.wishop.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Sku {

	@Id @GeneratedValue
	private int id;
	private String description;
	@ManyToMany
	private Collection<Wishlist> wishlists = new ArrayList<Wishlist>();

	public Collection<Wishlist> getWishlists() {
		return wishlists;
	}
	public void setWishlists(Collection<Wishlist> wishlists) {
		this.wishlists = wishlists;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
