package com.wishop.mvc.validators;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.wishop.model.BaseObject;
import com.wishop.mvc.WebConstants;
import com.wishop.service.BaseService;

/**
 * <code>Validator</code> for <code>BaseObject</code> forms.
 *
 * @author Paulo Monteiro
 */
public abstract class BaseValidator<T, ID extends Serializable> implements Validator, WebConstants{

	private final BaseService<?, BaseObject<?, Long>> baseService;
	
	/**
	 * Injects the <b>BaseService</b> through Spring DI
	 * @param BaseService - BaseService
	 */
	@Autowired
	public BaseValidator(BaseService<?, BaseObject<?, Long>> baseService) {
		this.baseService = baseService;
	}
	
	/**
	 * Method to validate a BaseObject object. <br>
	 * @param object - BaseObject
	 * @param errors - List of errors
	 */
	public abstract void validate(Object target, Errors errors);
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return getEntityClass().equals(clazz);
	}
	
	@SuppressWarnings("unchecked")
    public Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

	/**
	 * @return the baseService
	 */
	@SuppressWarnings({"rawtypes" })
	public BaseService getBaseService() {
		return baseService;
	}
	
	/**
	 * Method to validate a BaseObject object before purging. <br />
	 * Useful when the object to be deleted is a foreign key in a another business object.
	 * @param object - BaseObject
	 * @param errors - List of errors
	 */
	public void validatePurge(Object target, Errors errors) {
	}
}
