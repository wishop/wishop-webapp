package com.wishop.dao;

import org.springframework.stereotype.Repository;

import com.wishop.model.Role;

@Repository
public interface RoleDAO  extends BaseDAO<Role, Long> {
	
	/**
	 * Checks to see it the role name is unique
	 * @param name Role name
	 * @return boolean - true/false
	 */
	public boolean isUniqueName(String name);
	
	/**
	 * Checks to see it the role name is unique
	 * @param cmsRole Role object
	 * @return boolean - true/false
	 */
	public boolean isUniqueName(Role cmsRole);
	
	/**
	 * Return a Role based on a name
	 * @param name Role name
	 * @return Role
	 */
	public Role getByName(String name);
}
