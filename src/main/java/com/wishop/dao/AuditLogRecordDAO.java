package com.wishop.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.wishop.model.AuditLogRecord;

/**
 * Class responsible for persisting the AuditLogRecord
 * @author pmonteiro
 * @param <ID>
 */
@Repository
public interface AuditLogRecordDAO<ID extends Serializable> {
	
	/**
	 * Return the SessionFactory bean
	 * @return AuditLogRecordDAO
	 */
	public SessionFactory getSessionFactory();
	
	/**
	 * Method responsible for persisting the AuditLogRecord
	 * @param entity
	 */
	public AuditLogRecord<ID> save(AuditLogRecord<ID> entity);
	
	/**
	 * Method responsible for logging the actions
	 * @param service
	 * @param entity
	 */
    public void logAction(AuditLogRecord<ID> entity);
}
