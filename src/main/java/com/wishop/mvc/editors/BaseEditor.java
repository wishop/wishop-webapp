package com.wishop.mvc.editors;

import java.io.Serializable;

public interface BaseEditor<T, ID extends Serializable> {
	/**
	 * Sets the property value by parsing a given String. 
	 * @throws java.lang.IllegalArgumentException if either the String is badly formatted or 
	 * if this kind of property can't be expressed as text
	 * @param text
	 */
	public void setAsText(String text);
}
