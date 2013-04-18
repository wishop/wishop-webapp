package com.wishop.service;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.dao.AuditLogRecordDAO;
import com.wishop.model.AuditLogRecord;

/**
 * DAO responsible for the AuditLogRecord actions
 * @author pmonteiro
 *
 */
@Service
@Component("auditLogRecordService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class AuditLogRecordServiceImpl<ID extends Serializable> implements AuditLogRecordService<ID>{

	private Logger logger = Logger.getLogger(AuditLogRecordServiceImpl.class);
	private AuditLogRecordDAO<ID> dao;

	@Autowired
	public AuditLogRecordServiceImpl(AuditLogRecordDAO<ID> auditLogRecordDAO) {
		dao = auditLogRecordDAO;
	}
	
	public void logAction(AuditLogRecord<ID> entity)
    {
        logger.info("Service save: " + entity.getClass().getName() +":"+entity.getId());
    }

	public AuditLogRecordDAO<ID> getDao() {
		return this.dao;
	}

	public void save(AuditLogRecord<ID> entity) {
		getDao().save(entity);
		logAction(entity);
	}
}
