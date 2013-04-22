package com.wishop.mvc.editors;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * Shared WebBindingInitializer for Wishop custom editors.
 *
 * <p>Alternatively, such init-binder code may be put into
 * {@link org.springframework.web.bind.annotation.InitBinder}
 * annotated methods on the controller classes themselves.
 *
 * @author Paulo Monteiro
 */
public abstract class WishopFormBindingInitializer implements WebBindingInitializer {

	public static final String FORM_STATUS_UPDATE = "UPDATE";
	public static final String FORM_STATUS_SUBMIT = "SUBMIT";

	/**
	 * Registers custom editors to bind String values to Classes
	 * @param dataBinder - used to register custom editors to bind String values to Classes 
	 * @param request
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder, WebRequest request) {
		dataBinder.setDisallowedFields("id");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

}
