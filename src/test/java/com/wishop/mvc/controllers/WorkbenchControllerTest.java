package com.wishop.mvc.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"classpath:/META-INF/springApp/daoContext-app.xml",
		"classpath:/META-INF/applicationContext.xml",
		"classpath:test-wishop-servlet.xml"})
public class WorkbenchControllerTest {

	private static final String WORKBENCH_VIEW = "/WEB-INF/tiles/workbench/layout.jsp";
	private static final String WORKBENCH_REQUEST = "/workbench";

	@Autowired 
	private WebApplicationContext ctx;
	 
    private MockMvc mockMvc;
 
    @Before 
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }
 
    @Test 
    public void worbenchTest() throws Exception {
    	MockHttpServletRequestBuilder httpRequest = MockMvcRequestBuilders.get(WORKBENCH_REQUEST);
        mockMvc.perform(httpRequest.accept(MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl(WORKBENCH_VIEW));
    } 
}