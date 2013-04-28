package com.wishop.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Simple JavaBean Workbench controller that is used to respond to <code>Workbench</code> requests.
 * @author Paulo Monteiro
 *
 */
@Controller 
@RequestMapping("/workbench")
public class WorkbenchController {
	
	protected static final String WORKBENCH = "workbench";
	
	@RequestMapping(method = RequestMethod.GET)
	public String workbench(Model model) {
		return WORKBENCH;
	}
	
}