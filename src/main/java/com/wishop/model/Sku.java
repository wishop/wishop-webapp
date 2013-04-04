package com.wishop.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Sku extends BaseObject<Sku, Long> implements BaseConstants  {
	
	private static final long serialVersionUID = -6967066176992536777L;

	private String description;

	@ManyToMany
	private Collection<Wishlist> wishlists = new ArrayList<Wishlist>();

	/**
	 * Returns a list of wishlists that have this sku
	 * @return Collection
	 */
	public Collection<Wishlist> getWishlists() {
		return wishlists;
	}
	
	/**
	 * Sets the Sku on wishlists
	 * @param wishlists
	 */
	public void setWishlists(Collection<Wishlist> wishlists) {
		this.wishlists = wishlists;
	}
	
	/**
	 * Returns the Sku description
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the Sku description
	 * @param description String
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
