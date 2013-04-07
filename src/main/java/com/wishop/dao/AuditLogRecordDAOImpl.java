package com.wishop.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wishop.model.AuditLogRecord;

/**
 * DAO responsible to store the AuditLogRecord
 * @author pmonteiro
 * @param <ID>
 */
@Repository
@Component("auditLogRecordDAO")
public class AuditLogRecordDAOImpl<ID extends Serializable> implements AuditLogRecordDAO<ID> {
    private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(AuditLogRecordDAOImpl.class);

	@Autowired
	public AuditLogRecordDAOImpl(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
    /**
     * @return saved object
     */
    public AuditLogRecord<ID> save(AuditLogRecord<ID> entity) throws HibernateException
    {
        getSessionFactory().getCurrentSession().save(entity);
        logAction(entity);
        return entity;
    }
    
    /**
     * Returns the SessionFactory that was injected by the Spring Framework  
     */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Session Factory is injected by the Spring Framework
	 * @param factory SessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
    /**
     * Logs all the actions related to an object.
     * 
     * @param action action performed on this object.
     * @param entity object
     */
    public void logAction(AuditLogRecord<ID> entity)
    {
        logger.info("Object saved: " + entity.getClass() + ":" + entity.getId());
    }
}
