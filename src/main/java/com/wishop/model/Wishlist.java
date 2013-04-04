package com.wishop.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 * Class that represents the User Wishlist
 * @author Hoang Tran
 *
 */
@Entity
public class Wishlist extends BaseObject<Wishlist, Long> implements BaseConstants {

	private static final long serialVersionUID = 140950113558707126L;

	private String description;
	
	@ManyToMany
	private Collection<Sku> skuList = new ArrayList<Sku>();

	/**
	 * Returns the Wishlist description
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the wishlist description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Method that returns the Sku List
	 * @return Collection - collection of Skus
	 */
	public Collection<Sku> getSkuList() {
		return skuList;
	}
	
	/**
	 * Sets the sku list
	 * @param skuList
	 */
	public void setSkuList(Collection<Sku> skuList) {
		this.skuList = skuList;
	}
	
}
