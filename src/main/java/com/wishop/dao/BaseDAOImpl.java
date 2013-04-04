package com.wishop.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.wishop.dao.exceptions.HibernateSessionException;
import com.wishop.model.BaseObject;

/**
 * Implements the generic CRUD data access operations using Hibernate APIs.
 *
 * @author Paulo Monteiro
 */
@Repository
public abstract class BaseDAOImpl<T, ID extends Serializable> implements BaseDAO<T, ID>, IDaoInternationalization {

    private Class<T> persistentClass;
    private SessionFactory sessionFactory;

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
    

    @SuppressWarnings("unchecked")
    public T getById(ID id) {
    	try {
            return (T) getSessionFactory().getCurrentSession().get(getPersistentClass(), id);
        } catch (HibernateException e) {
			throw new HibernateSessionException("Error fetching user by Id", e.getCause());
        }
    }

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
    	String hql = "FROM " + getPersistentClassToString() + " o WHERE o.deleted = false ORDER BY o.id";
    	Query query = getSessionFactory().getCurrentSession().createQuery(hql);
    	return query.list();
    }
    
    
	@SuppressWarnings("unchecked")
	public List<T> getAll(Long id) {
    	String hql = "FROM " + getPersistentClassToString() + " o WHERE o.id != :id ORDER BY o.id";
    	Query query = getSessionFactory().getCurrentSession().createQuery(hql);
    	query.setParameter("id", id);
    	return query.list();
    }
	
    @SuppressWarnings("unchecked")
	public List<T> getAll(boolean deleted) {
    	String hql = "FROM " + getPersistentClassToString() + " o WHERE o.deleted = :deleted ORDER BY o.id";
    	Query query = getSessionFactory().getCurrentSession().createQuery(hql);
    	query.setBoolean("deleted", deleted);
    	return query.list();
    }

    @SuppressWarnings("unchecked")
	public void purge(T entity) {
    	if(((BaseObject<T,ID>)entity).getAuditInfo().getCreationTimestamp() != null) {
	    	getSessionFactory().getCurrentSession().delete(entity);
    	} else {
    		BaseObject<T,ID> object = (BaseObject<T, ID>) getById(((BaseObject<T,ID>)entity).getId());
    		getSessionFactory().getCurrentSession().delete(object);
    	}
    }
    
    @SuppressWarnings({ "rawtypes" })
	public void delete(T entity, boolean deleted) {
    	if(entity instanceof BaseObject) {
    		if(((BaseObject)entity).getAuditInfo().getCreationTimestamp() != null) {
    			((BaseObject)entity).setDeleted(deleted);
    			getSessionFactory().getCurrentSession().update(entity);
    		} else {
    			String hql = "UPDATE " + getPersistentClassToString() + " o SET o.deleted = :deleted, " +
    					"o.auditInfo.modificationTimestamp = :tsp, o.auditInfo.modifierUserId = :userId  WHERE o.id = :id";
    	    	Query query = getSessionFactory().getCurrentSession().createQuery(hql);
    	    	query.setParameter("id", ((BaseObject)entity).getId());
    	    	query.setParameter("deleted", deleted);
    	    	query.setParameter("tsp", ((BaseObject)entity).getAuditInfo().getModificationTimestamp());
    	    	query.setParameter("userId", ((BaseObject)entity).getAuditInfo().getModifierUserId());
    	    	int result = query.executeUpdate();
    		}
    	} else {
    		throw new HibernateException("Error deleting object");
    	}
    }
    
    public T save(T entity) throws HibernateException
    {
        getSessionFactory().getCurrentSession().save(entity);
        return entity;
    }

    public T saveOrUpdate(T entity) {
    	getSessionFactory().getCurrentSession().saveOrUpdate(entity);
        return entity;
    }
    
    public T update(T entity) throws HibernateException
    {
    	try{
    		getSessionFactory().getCurrentSession().update(entity);
    	}catch (NonUniqueObjectException e) {
    		getSessionFactory().getCurrentSession().merge(entity);	
    	}
        return entity;
    }

    public T refresh(T entity) throws HibernateException
    {
        getSessionFactory().getCurrentSession().refresh(entity);
        return entity;
    }
    
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

