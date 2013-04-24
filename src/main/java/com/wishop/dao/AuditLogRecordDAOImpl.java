package com.wishop.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
	private static Logger logger = Logger.getLogger(AuditLogRecordDAOImpl.class);
	private final static String AUDIT_ID = "id";
	private SessionFactory sessionFactory;

	@Autowired
	public AuditLogRecordDAOImpl(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	/**
	 * Returns the persistence Class
	 * @return 
	 */
    @SuppressWarnings("rawtypes")
	private Class<AuditLogRecord> getPersistentClass() {
        return AuditLogRecord.class;
    }
	
    /**
     * @return saved object
     */
    public AuditLogRecord<ID> save(AuditLogRecord<ID> entity) throws HibernateException
    {
        getSession().save(entity);
        logAction(entity);
        return entity;
    }

    /**
     * Method that returns the current Session
     * @return
     */
	private Session getSession() {
		return getSessionFactory().getCurrentSession();
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

	@SuppressWarnings("unchecked")
	public AuditLogRecord<ID> getLatest() {
		Criteria query = null;
		query = getSession().createCriteria(getPersistentClass());
		query.addOrder(Order.desc(AUDIT_ID));
		List<AuditLogRecord<ID>> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else return null;
	}
}
