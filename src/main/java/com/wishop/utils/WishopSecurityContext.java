package com.wishop.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.wishop.model.User;


public class WishopSecurityContext {
	
	/**
	* Returns the domain User object for the currently logged in user, or null, if no CmsUser is logged in.
	* @return CmsUser object for the currently logged in user, or null if no CmsUser is logged in.
	*/
	public static User getSessionUser() {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (principal instanceof User) { 
	    	return ((User) principal);
	    } else return null;
	}
	
	/**
	 * Utility method to determine if the current user is logged in/authenticated.
	 * @return if user is logged in
     */
	public static boolean isLoggedIn() {
		return (getSessionUser() != null);
	}

}
