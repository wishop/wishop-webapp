package com.wishop.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.dao.BaseDAO;
import com.wishop.model.exceptions.WishopException;

@Service
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public abstract class BaseServiceImpl<X extends BaseDAO<T, Long>, T> implements BaseService<X, T>, IServiceInternationalization
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
	}
	
	@Transactional(readOnly=false)
	public void saveOrUpdate(T entity) {
		this.getDao().saveOrUpdate(entity);
	}
	
	@Transactional(readOnly=false)
	public void update(T entity) throws WishopException {
		this.getDao().update(entity);
	}
	
	@Transactional(readOnly=false)
	public void delete(T entity, boolean deleted) {
		this.getDao().delete(entity, deleted);
	}
	
	@Transactional(readOnly=false)
	public void purge(T entity) throws WishopException {
		this.getDao().purge(entity);
	}
}
