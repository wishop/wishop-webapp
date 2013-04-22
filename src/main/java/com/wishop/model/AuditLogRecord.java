package com.wishop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 * This class represents the information logged in your audit database table.
 * This class will hold DB actions against the Wishop business entities. This will provide extra logging to the application.
 * @author pmonteiro
 *
 */
@Entity
public class AuditLogRecord<ID extends Serializable> implements Serializable {
	
	private static final long serialVersionUID = -6880037136356951612L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator="generatorName")
	@TableGenerator(name="generatorName", allocationSize=1, table="wishop_keys", pkColumnName="table_name", valueColumnName="next_value")
    protected Long id;
	
	@Column(nullable=false)
	private String message;
	
	@Column(nullable=false)
	private Long entityId;
	
	@Column(nullable=false)
	private Class<? extends Object> entityClass;
	
	@Column(nullable=false)
	private Long userId;
	
	@Column(nullable=false)
	private Date created;
	
	public AuditLogRecord(String message, Long entityId, Class<? extends Object> class1, Long userId) {
		this.setMessage(message);
		this.setEntityId(entityId);
		this.setEntityClass(class1);
		this.setUserId(userId);
		this.created = new Date();
	}
	
	public AuditLogRecord() {
		super();
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * @param entityClass the entityClass to set
	 */
	public void setEntityClass(Class<? extends Object> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * @return the entityClass
	 */
	public Class<? extends Object>  getEntityClass() {
		return entityClass;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}	
}
