package com.wishop.service;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.dao.BaseDAO;
import com.wishop.model.exceptions.WishopException;
import com.wishop.utils.WishopMessages;

@Service
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public abstract class BaseServiceImpl<X extends BaseDAO<T, Long>, T> implements BaseService<X, T>, ServiceConstants
{

	private Logger logger = Logger.getLogger(BaseServiceImpl.class);
	private X dao;

	public BaseServiceImpl(X dao) {
		this.setDao(dao);
	}
	
	public X getDao() {
		return this.dao;
	}

	public void setDao(X dao) {
		this.dao = dao;
	}
	
	public List<T> getAll() {
		return getDao().getAll();
	}
	
	public List<T> getAll(Long id) {
		return getDao().getAll(id);
	}

	public List<T> getAll(boolean deleted) {
		return getDao().getAll(deleted);
	}

	public T getById(Long id) {
		return getDao().getById(id);
	}

	public T getById(Integer id) {
		return getDao().getById(id);
	}

	public T getById(String id) {
		return getDao().getById(id);
	}

	@Transactional(readOnly=false)
	public void save(T entity) throws WishopException {
		this.dao.save(entity);
		logAction(SERVICE_ACTION_SAVE, entity);
	}
	
	@Transactional(readOnly=false)
	public void saveOrUpdate(T entity) {
		this.getDao().saveOrUpdate(entity);
		logAction(SERVICE_ACTION_SAVE_OR_UPDATE, entity);
	}
	
	@Transactional(readOnly=false)
	public void update(T entity) throws WishopException {
		this.getDao().update(entity);
		logAction(SERVICE_ACTION_UPDATE, entity);
	}
	
	@Transactional(readOnly=false)
	public void delete(T entity, boolean deleted) {
		this.getDao().delete(entity, deleted);
		logAction(SERVICE_ACTION_DELETE, entity);
	}
	
	@Transactional(readOnly=false)
	public void purge(T entity) throws WishopException {
		this.getDao().purge(entity);
		logAction(SERVICE_ACTION_PURGE, entity);
	}
	
	public void logAction(String code, T entity)
    {
		Locale defaultLocale = Locale.getDefault();
		logAction(code, entity, defaultLocale);
    }
	
	public void logAction(String code, Long id)
    {
		Locale defaultLocale = Locale.getDefault();
		logAction(code, id, defaultLocale);
    }
	
	public void logAction(String code, T entity, Locale locale)
    {
		logger.info(WishopMessages.getMessage(code, entity, locale));
    }
	
	public void logAction(String code, Long id, Locale locale)
    {
		logger.info(WishopMessages.getMessage(code, id, locale));
    }
}
