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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wishop.dao.exceptions.HibernateSessionException;
import com.wishop.model.BaseObject;
import com.wishop.utils.WishopMessages;

/**
 * Implements the generic CRUD data access operations using Hibernate APIs.
 *
 * @author Paulo Monteiro
 */
@Repository
public abstract class BaseDAOImpl<T, ID extends Serializable> implements BaseDAO<T, ID>, DAOConstants {

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

	public List<T> getAll() {
   		return getAll(false);
    }

	@SuppressWarnings("unchecked")
	public List<T> getAll(boolean deleted) {
		Criteria query = null;
		query = getSession().createCriteria(getPersistentClass());
		query.add(Restrictions.eq(DELETED, deleted));
    	return query.list();
    }
    
	@SuppressWarnings("unchecked")
	public List<T> getAll(Long id) {
		Criteria query = null;
		query = getSession().createCriteria(getPersistentClass());
		query.add(Restrictions.ne(ID, id));
		return query.list();
    }
	
	public List<T> getAll(int firstResult, int maxResults) {
    	return getAll(firstResult, maxResults, false);
	}
    
    @SuppressWarnings("unchecked")
	public List<T> getAll(int firstResult, int maxResults, boolean deleted) {
    	Criteria query = null;
		query = getSession().createCriteria(getPersistentClass());
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		query.add(Restrictions.eq(DELETED, deleted));
		return query.list();
	}

    @SuppressWarnings("unchecked")
	public void purge(T entity) {
    	if(((BaseObject<T,ID>)entity).getAuditInfo().getCreationTimestamp() != null) {
	    	getSession().delete(entity);
    	} else {
    		BaseObject<T,ID> object = (BaseObject<T, ID>) getById(((BaseObject<T,ID>)entity).getId());
    		getSession().delete(object);
    	}
    	logAction(DAO_ACTION_PURGE, entity);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void delete(T entity, boolean deleted) {
    	if(entity instanceof BaseObject) {
    		if(((BaseObject)entity).getAuditInfo().getCreationTimestamp() != null) {
    			((BaseObject)entity).setDeleted(deleted);
    			getSession().update(entity);
    		} else {
    			String hql = "UPDATE " + getPersistentClassToString() + " o SET o.deleted = :deleted, " +
    					"o.auditInfo.modificationTimestamp = :tsp, o.auditInfo.modifierUserId = :userId  WHERE o.id = :id";
    	    	Query query = getSession().createQuery(hql);
    	    	query.setParameter("id", ((BaseObject)entity).getId());
    	    	query.setParameter("deleted", deleted);
    	    	query.setParameter("tsp", ((BaseObject)entity).getAuditInfo().getModificationTimestamp());
    	    	query.setParameter("userId", ((BaseObject)entity).getAuditInfo().getModifierUserId());
    	    	int result = query.executeUpdate();
    	    	if(result > 0) {
    	    		logAction(DAO_ACTION_DELETE, ((BaseObject<T,ID>)entity).getId());
    	    	} else {
    	    		logAction(DAO_ERROR_DELETING_OBJECT, ((BaseObject<T,ID>)entity).getId());
    	    		throw new HibernateException(WishopMessages.getMessage(DAO_ERROR_DELETING_OBJECT,((BaseObject)entity).getId()));
    	    	}
    		}
    	} else {
    		throw new HibernateException("Error deleting object");
    	}
    }
    
    public T save(T entity) throws HibernateException
    {
        getSession().save(entity);
        logAction(DAO_ACTION_SAVE, entity);
        return entity;
    }

    public T saveOrUpdate(T entity) {
    	getSession().saveOrUpdate(entity);
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
        return entity;
    }

    public T refresh(T entity) throws HibernateException
    {
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

