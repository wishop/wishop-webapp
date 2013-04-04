package com.wishop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

/**
 * Class used to store the business entity audit information 
 * This information will get stored by AOP
 * @author pmonteiro
 * @param <ID>
 */
@Embeddable
public class AuditInfo<ID extends Serializable> {
	
	private Date creationTimestamp;
    private Date modificationTimestamp;

	@Type(type="long")
    private Serializable creatorUserId;
	
	@Type(type="long")
    private Serializable modifierUserId;
    
	/**
	 * @param creationTimestamp - the creationTimestamp to set
	 */
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	
	/**
	 * @return the creationTimestamp
	 */
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}
	
	/**
	 * @param modificationTimestamp the modificationTimestamp to set
	 */
	public void setModificationTimestamp(Date modificationTimestamp) {
		this.modificationTimestamp = modificationTimestamp;
	}
	
	/**
	 * @return the modificationTimestamp
	 */
	public Date getModificationTimestamp() {
		return modificationTimestamp;
	}
	
	/**
	 * @param creatorUserId the creatorUserId to set
	 */
	public void setCreatorUserId(Serializable creatorUserId) {
		this.creatorUserId = creatorUserId;
	}
	
	/**
	 * @return the creatorUserId
	 */
	@SuppressWarnings("hiding")
	public <ID extends Serializable> Serializable getCreatorUserId() {
		return creatorUserId;
	}
	
	/**
	 * @param modifierUserId the modifierUserId to set
	 */
	public void setModifierUserId(Serializable modifierUserId) {
		this.modifierUserId = modifierUserId;
	}
	
	/**
	 * @return the modifierUserId
	 */
	@SuppressWarnings("hiding")
	public <ID extends Serializable> Serializable getModifierUserId() {
		return modifierUserId;
	}
	
}
