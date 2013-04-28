package com.wishop.mvc.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.wishop.model.BaseObject;
import com.wishop.service.BaseUserTest;

/**
 * This it the BaseController Test class for the workbench area of the wishop site. 
 * This provides the testing basis for all CRUD actions.
 * 
 * @author pmonteiro
 *
 */
public abstract class BaseControllerTestImpl extends BaseUserTest implements BaseControllerTest {
	
	protected static final String REPLACE_VAR = "#";

	protected static final int _200 = 200;

	protected static final int _302 = 302;

	protected static final String REDIRECT_URL = "redirect:";

	private static final String VIEW_FORM = "/form";
	private static final String VIEW_SHOW = "/show";

	private static final String REQUEST_LIST = "/list";
	private static final String REQUEST_SHOW = "/show/#";
	private static final String REQUEST_CREATE = "/create";
	private static final String REQUEST_EDIT = "/edit/#";
	private static final String REQUEST_PURGE = "/purge";
	private static final String REQUEST_SAVE = "/save";
	
	@Autowired 
	private WebApplicationContext ctx;
	
	protected MockMvc mockMvc;
	
	private String requestMapping;
	private String fowardedURL;
	
	/**
	 * Interface to provide configuration for a web application. This is read-only while the application is running. <br>
	 * This interface adds a getServletContext method to the generic ApplicationContext interface. <br>
	 * There is a single root context per application, while each servlet in the application 
	 * (including a dispatcher servlet in the MVC framework) has its own child context. <br>
	 * @return WebApplicationContext
	 */
	protected WebApplicationContext getWebApplicationContext() {
		return ctx;
	}
	
	/**
	 * Method that sets up all the configurations needed for the jUnit test
	 */
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	protected MvcResult list() throws Exception {
        MvcResult result = perform(get(getListRequestMapping()), this.fowardedURL, _200);
        assertView(result, getListRequestMapping());
        return result;
	}

	protected MvcResult show() throws Exception{
		MvcResult result = perform(get(getShowRequestMapping(USER_ID_3)), this.fowardedURL, _200);
		assertView(result, getShowResponseMapping(false, NO_USER_ID));
		return result;
	}
	
	protected MvcResult create() throws Exception {
		MvcResult result = perform(get(getCreateRequestMapping()), this.fowardedURL, _200);
		assertView(result, getFormResponseMapping());
		return result;
	}
	
	protected MvcResult edit() throws Exception {
		MvcResult result = perform(get(getEditRequestMapping(USER_ID_3)), this.fowardedURL, _200);
		assertView(result, getFormResponseMapping());
		return result;
	}

	protected MvcResult purge(String attrId, @SuppressWarnings("rawtypes") BaseObject obj) throws Exception {
		MvcResult result = perform(post(getPurgeRequestMapping()).sessionAttr(attrId, obj), null, _302);
		assertView(result, getListResponseMapping(true));
		return result;
	}
	
	protected MvcResult save(String attrId, @SuppressWarnings("rawtypes") BaseObject obj) throws Exception {
		MvcResult result = perform(post(getSaveRequestMapping()).sessionAttr(attrId, obj), null, _302);
		assertView(result, getShowResponseMapping(true, USER_ID_4));
		return result;
	}
	
	public MvcResult saveWithErrors(String attrId, @SuppressWarnings("rawtypes") BaseObject obj) throws Exception {
		MvcResult result = perform(post(getSaveRequestMapping()).sessionAttr(attrId, obj), this.fowardedURL, _200);
		assertView(result, getFormResponseMapping());
		return result;
	}

	protected MvcResult update(String attrId, @SuppressWarnings("rawtypes") BaseObject obj) throws Exception {
		MvcResult result = perform(post(getSaveRequestMapping()).sessionAttr(attrId, obj), null, _302);
		assertView(result, getShowResponseMapping(true, USER_ID_4));
		return result;
	}
	
	public MvcResult updateWithErrors(String attrId, @SuppressWarnings("rawtypes") BaseObject obj) throws Exception {
		MvcResult result = perform(post(getSaveRequestMapping()).sessionAttr(attrId, obj), this.fowardedURL, _200);
		assertView(result, getFormResponseMapping());
		return result;
	}

	/**
	 * Returns the List Request Mapping
	 * @return requestMapping
	 */
	protected String getListRequestMapping() {
		return requestMapping + REQUEST_LIST;
	}
	
	/**
	 * Returns the <b>list</b> response view mapping
	 * @param httpStatus
	 * @return view - response view name
	 */
	protected String getListResponseMapping(boolean isRedirect) {
		StringBuilder sb =  new StringBuilder();
		if (isRedirect) {
			sb.append(REDIRECT_URL);
		}
		sb.append(requestMapping + REQUEST_LIST);
		return sb.toString();
	}
	
	/**
	 * Returns the <b>show</b> response view mapping
	 * @param httpStatus
	 * @return view - response view name
	 */
	protected String getShowResponseMapping(boolean isRedirect, long userId) {
		StringBuilder sb =  new StringBuilder();
		if (isRedirect) {
			sb.append(REDIRECT_URL);
		}
		
		if (NO_USER_ID == userId) {
			sb.append(requestMapping+REQUEST_SHOW.replaceAll("/#", ""));
		} else { 
			sb.append(requestMapping+REQUEST_SHOW.replaceAll(REPLACE_VAR, String.valueOf(userId)));
		}
		return sb.toString();
	}
	
	/**
	 * Returns the Create Request Mapping
	 * @return requestMapping
	 */
	protected String getCreateRequestMapping() {
		return requestMapping + REQUEST_CREATE;
	}
	
	/**
	 * Returns the Edit Request Mapping
	 * @param objId
	 * @return requestMapping
	 */
	protected String getEditRequestMapping(int objId) {
		
		return requestMapping + REQUEST_EDIT.replaceAll(REPLACE_VAR, String.valueOf(objId));
	}
	
	/**
	 * Returns the Save Request Mapping
	 * @return requestMapping
	 */
	protected String getSaveRequestMapping() {
		return requestMapping + REQUEST_SAVE;
	}
	
	/**
	 * Returns the Show Request Mapping
	 * @param objId
	 * @return requestMapping
	 */
	protected String getShowRequestMapping(int objId) {
		String requestShow = REQUEST_SHOW.replaceAll(REPLACE_VAR, String.valueOf(objId));
		return requestMapping + requestShow;
	}
	
	/**
	 * Returns the Purge Request Mapping
	 * @return requestMapping
	 */
	protected String getPurgeRequestMapping() {
		return requestMapping + REQUEST_PURGE;
	}
	
	/**
	 * Returns the Form Request Mapping
	 * @return requestMapping
	 */
	protected String getFormResponseMapping() {
		return requestMapping + VIEW_FORM;
	}
	
	/**
	 * Returns the Form Request Mapping
	 * @param objId 
	 * @param name 
	 * @return requestMapping
	 */
	protected String getShowResponseMapping(int name, int objId) {
		return requestMapping + VIEW_SHOW.replaceAll(REPLACE_VAR, String.valueOf(objId));
	}
	
	/**
	 * Sets the BaseController implementation request mapping
	 * @param requestMapping
	 */
	protected void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}

	/**
	 * Sets the BaseController implementation fowarded URL. <br> 
	 * This is the resource file that is being used to render the view. 
	 * @param requestMapping
	 */
	protected void setForwardedUrl(String fowardedURL) {
		this.fowardedURL = fowardedURL;
	}
	
	/**
	 * Method that performs/mocks the HTTP Request
	 * @param httpRequest GET/POST request
	 * @return MvcResult 
	 * @throws Exception 
	 */
	protected MvcResult perform(MockHttpServletRequestBuilder httpRequest, String expectedFowardedURL, int httpStatus) throws Exception {
		return mockMvc.perform(httpRequest.accept(MediaType.TEXT_PLAIN))
                .andExpect(getStatus(httpStatus)) //HTTP CODE
                .andExpect(forwardedUrl(expectedFowardedURL)) // Tiles Definition
		        .andDo(print())
		        .andReturn();
	}
	
	/**
	 * Returns a ResultMatcher depending on the expected HTTP status
	 * @param httpStatus - HTTP code status, i.e., 200, 302, 501, 404, etc
	 * @return ResultMatcher
	 */
	private ResultMatcher getStatus(int httpStatus) {
		switch(httpStatus) {
			case _302: return status().isMovedTemporarily();
		}
		return status().isOk();
	}

	/**
	 * Method that asserts the Controller action ModelAndView
	 * @param result MvcResult
	 * @param responseMapping 
	 */
	protected void assertView(MvcResult result, String responseMapping) {
		Assert.assertNotNull(result.getModelAndView());
        Assert.assertTrue(responseMapping.endsWith(result.getModelAndView().getViewName()));
	}
}
