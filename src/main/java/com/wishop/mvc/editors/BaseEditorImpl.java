package com.wishop.mvc.editors;

import java.beans.PropertyEditorSupport;
import java.io.Serializable;

import com.wishop.model.BaseObject;
import com.wishop.service.BaseService;

/**
 * BaseObject Editor. <br>
 * @author Paulo Monteiro
 *
 */
public abstract class BaseEditorImpl<T, ID extends Serializable> 
	extends PropertyEditorSupport implements BaseEditor<T, ID> {

	private final BaseService<?, BaseObject<?, ID>> baseService;

	public BaseEditorImpl(BaseService<?, BaseObject<?, ID>> baseService) {
		this.baseService = baseService;
	}

	/**
	 * 
	 * @param text - the <b>BaseObject</b> id 
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		for (BaseObject<?, ID> webpage : this.baseService.getAll()) {
			if (webpage.toString().equals(text)) {
				setValue(webpage);
			}
		}
	}
	
	/**
	 * @return the baseService
	 */
	public BaseService<?, BaseObject<?, ID>> getBaseService() {
		return baseService;
	}

}
