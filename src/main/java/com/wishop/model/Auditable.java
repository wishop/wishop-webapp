package com.wishop.model;

import java.io.Serializable;

/**
 * This is the contract that all BaseObjects must implement
 * @author pmonteiro
 *
 * @param <ID>
 */
public interface Auditable<ID extends Serializable> {
	
	/**
	 * Return the BaseObject id
	 * @return id
	 */
	public ID getId();
	
	/**
	 * Check if the BaseObject is new. This is specially useful for the controllers
	 * @return boolean
	 */
	public boolean isNew();
	
	/**
	 * Set the Audit information
	 * @param auditInfo
	 */
	public void setAuditInfo(AuditInfo<ID> auditInfo);
	
	/**
	 * Get the Audit Information
	 * @return
	 */
    public AuditInfo<ID> getAuditInfo();
}
