package com.wishop.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//@Table (name="USER_DETAILS") //if we want to rename the table
@Entity
public class UserDetails {
	
	//@Column(name="USER_ID") //if we wanted rename the column
	//@Transient //will ask hibernate to ignore this as a column
	@Id @GeneratedValue(strategy=GenerationType.AUTO) // here we're asking hibernate to generate the id with auto strategy
	private int userId; // this is a surrogate key (as opposted to natural key)
	private String email;
	@Temporal(TemporalType.DATE) //Annotation to say we want to record just the DATE as opposed to full timestamp
	private Date joinedDate;
	@Embedded
	private Address address;
	@OneToMany
	private Collection<Wishlist> listOfWishlists = new ArrayList<Wishlist>();

	public Collection<Wishlist> getListOfWishlists() {
		return listOfWishlists;
	}
	public void setListOfWishlists(Collection<Wishlist> listOfWishlists) {
		this.listOfWishlists = listOfWishlists;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getJoinedDate() {
		return joinedDate;
	}
	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

}
