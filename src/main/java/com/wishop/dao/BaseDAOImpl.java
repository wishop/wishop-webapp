package com.wishop.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.dao.exceptions.HibernateSessionException;
import com.wishop.utils.WishopMessages;

/**
 * Implements the generic CRUD data access operations using Hibernate APIs.
 *
 * @author Paulo Monteiro
 */
@Repository
@Transactional
public abstract class BaseDAOImpl<T, ID extends Serializable> implements BaseDAO<T, ID>, DAOConstants {

    protected static final String OBJECT_ID = "id";
	private Class<T> persistentClass;
    private SessionFactory sessionFactory;
    private static Logger logger = Logger.getLogger(BaseDAOImpl.class);

	public BaseDAOImpl() {
        setPersistentClass();
    }
	
	public BaseDAOImpl(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
		setPersistentClass();
	}

	@SuppressWarnings("unchecked")
	private void setPersistentClass() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

    public Class<T> getPersistentClass() {
        return persistentClass;
    }
    
    public String getPersistentClassToString() {
        return persistentClass.getName();
    }
    
    public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}
    
    public Session getNewSession() {
		return getSessionFactory().openSession();
	}
    
    public void reassociate(Object entity) {
		getSessionFactory().openSession().buildLockRequest(LockOptions.NONE).lock(entity);
	}

    @SuppressWarnings("unchecked")
    public T getById(ID id) {
    	try {
            return (T) getSession().get(getPersistentClass(), id);
        } catch (HibernateException e) {
			throw new HibernateSessionException(WishopMessages.getMessage(DAO_COULD_NOT_FETCH), e.getCause());
        }
    }

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getSession().createCriteria(getPersistentClass()).addOrder(Order.asc(OBJECT_ID)).list();
    }

	@SuppressWarnings("unchecked")
	public List<T> getAll(Long id) {
		Criteria query = getSession().createCriteria(getPersistentClass());
		query.add(Restrictions.ne(ID, id));
		query.addOrder(Order.asc(OBJECT_ID));
		return query.list();
    }
	
	@SuppressWarnings("unchecked")
	public List<T> getAll(int firstResult, int maxResults) {
    	Criteria query = getSession().createCriteria(getPersistentClass());
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		query.addOrder(Order.asc(OBJECT_ID));
		return query.list();
	}

	public void purge(T entity) {
    	getSession().delete(entity);
    	getSession().flush();
    	logAction(DAO_ACTION_PURGE, entity);
    }
    
    public T save(T entity) throws HibernateException
    {
        getSession().save(entity);
        getSession().flush();
        logAction(DAO_ACTION_SAVE, entity);
        return entity;
    }

    public T saveOrUpdate(T entity) {
    	getSession().saveOrUpdate(entity);
    	getSession().flush();
    	logAction(DAO_ACTION_SAVE_OR_UPDATE, entity);
        return entity;
    }
    
    public T update(T entity) throws HibernateException
    {
    	try{
    		getSession().update(entity);
    		logAction(DAO_ACTION_UPDATE, entity);
    	}catch (NonUniqueObjectException e) {
    		getSession().merge(entity);
    		logAction(DAO_ACTION_MERGE, entity);
    	}
    	getSession().flush();
        return entity;
    }

    public T refresh(T entity) throws HibernateException
    {
    	getSession().flush();
        getSession().refresh(entity);
        logAction(DAO_ACTION_REFRESH, entity);
        return entity;
    }
    
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void logAction(String code, T entity)
    {
		Locale defaultLocale = Locale.getDefault();
		logAction(code, entity, defaultLocale);
    }

	public void logAction(String code, T entity, Locale locale)
    {
		logger.debug(WishopMessages.getMessage(code, entity, locale));
    }
	
	public void logAction(String code, ID id)
    {
		Locale defaultLocale = Locale.getDefault();
		logAction(code, id, defaultLocale);
    }

	public void logAction(String code, ID id, Locale locale)
    {
		logger.debug(WishopMessages.getMessage(code, id, locale));
    }
}

