package com.wishop.service;

import java.io.Serializable;

import com.wishop.dao.AuditLogRecordDAO;
import com.wishop.model.AuditLogRecord;

/**
 * Service responsible for the AuditLogRecord.
 * @author pmonteiro
 * @param <ID>
 */
public interface AuditLogRecordService<ID extends Serializable> {
	
	/**
	 * Return the DAO bean
	 * @return AuditLogRecordDAO
	 */
	public AuditLogRecordDAO<ID> getDao();
	
	/**
	 * Method responsible for persisting the AuditLogRecord.
	 * BaseService service that saves or updates an BaseObject
	 * It calls the correct DAO instance, injected previously by the Spring Framework 
	 * @param entity BaseObject
	 * @param entity
	 */
	public void save(AuditLogRecord<ID> entity);
	
	/**
	 * This returns the latest inserted AuditLogRecord.
	 * Useful for the JUnit tests
	 * @return AuditLogRecord
	 */
	public AuditLogRecord<ID> getLatest();
	
	/**
	 * Method responsible for logging the actions
	 * @param service
	 * @param entity
	 */
    public void logAction(AuditLogRecord<ID> entity);
}
