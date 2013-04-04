package com.wishop.dao.util;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * <p>
 * This class provides a feature that enforces naming standards automatically.
 * All table names should follow the pattern <table_name>
 * </p>
 * 
 * @author pmonteiro
 * 
 */
public class WishopNamingStrategy extends ImprovedNamingStrategy {
	private static final String USER_TABLE_PLURAL = "USERS";
	private static final String USER_TABLE_SINGULAR = "USER";
	private static final long serialVersionUID = 2514439297837969186L;
	
	/**
	 * Because some classnames might have reserved DB keywords, we need to change them accordingly 
	 */
	public String classToTableName(String className) {
		if (USER_TABLE_SINGULAR.equalsIgnoreCase(className)) {
			className = USER_TABLE_PLURAL;
		}
		return super.classToTableName(className);
	}
}
