package com.wishop.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {
	@Id
	private int id;
	private String username;
	
	public int getId() {
		return id;
	}
	public void setId(int userId) {
		this.id = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
