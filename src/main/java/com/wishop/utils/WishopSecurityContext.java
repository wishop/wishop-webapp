package com.wishop.utils;

import com.wishop.model.User;


public class WishopSecurityContext {
	
	/**
	* Returns the domain User object for the currently logged in user, or null, if no User is logged in.
	* @return User object for the currently logged in user, or null if no User is logged in.
	*/
	public static User getSessionUser() {
// 		TODO: This will be useful once we have Spring Security in place, for the time being the session user will always be null
//	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	    if (principal instanceof User) { 
//	    	return ((User) principal);
//	    } else 
	    	return null;
	}
	
	/**
	 * Utility method to determine if the current user is logged in/authenticated.
	 * @return if user is logged in
     */
	public static boolean isLoggedIn() {
		return (getSessionUser() != null);
	}

}
