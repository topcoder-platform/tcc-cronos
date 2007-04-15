/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import com.cronos.im.ajax.IMAjaxSupportServlet;
import com.topcoder.chat.user.profile.ChatUserProfile;


/**
 * Failure test cases for the class IMAjaxSupportServlet.
 *
 * @author waits
 * @version 1.0
 * @since Apr 7, 2007
 */
public class IMAjaxSupportServletFailureTests extends TestCase {
    /** IMAjaxSupportServlet to test. */
    private IMAjaxSupportServlet servlet = null;

    /**
     * The request.
     */
    private HttpServletRequest request = null;
    /**
     * The response.
     */
    private HttpServletResponse response = null;

    /**
     * HttpSession instance.
     */
    private HttpSession session = null;

    /**
     * Setup environment.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        TestHelper.addConfiguration("failure" + java.io.File.separator + "config.xml");
        servlet = new MockIMAjaxSupportServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
    }

    /**
     * Simply call the init method.
     *
     * @throws ServletException into Junit
     */
    public void testInit() throws ServletException {
        servlet.init();
    }

    /**
     * Call servlet.init with invalid configuration.
     *
     */
    private void init_invalidConfiguration(){
    	try {
            servlet.init();
            fail("The object factory does not exist.");
        } catch (ServletException e) {
        	//good
        }
    }

    /**
     * Test the init method, the objectfactory ns invalid, it does not exist.
     * ServletException expected.
     */
    public void testInit_notExistObjectFactoryNS() {
    	init_invalidConfiguration();
    }
    /**
     * Test the init method, the objectfactory ns invalid, the property 'messenger_key' is missing.
     * ServletException expected.
     */
    public void testInit_invalidConfiguration1() {
    	init_invalidConfiguration();
    }
    /**
     * Test the init method, the objectfactory ns invalid, the property 'chat_session_manager_key' is missing.
     * ServletException expected.
     */
    public void testInit_invalidConfiguration2() {
    	init_invalidConfiguration();
    }
    /**
     * Test the init method, the objectfactory ns invalid, the property 'chat_status_tracker_key' is missing.
     * ServletException expected.
     */
    public void testInit_invalidConfiguration3() {
    	init_invalidConfiguration();
    }
    /**
     * Test the init method, the objectfactory ns invalid, the property 'service_engine_key' is value is invalid.
     * ServletException expected.
     */
    public void testInit_invalidConfiguration4() {
    	init_invalidConfiguration();
    }
    /**
     * Test the init method, the 'handler_manager_namespace' is invalid, the ns does not exist.
     * ServletException expected.
     */
    public void testInit_invalidConfiguration5() {
    	init_invalidConfiguration();
    }

    /**
     * Test the service method, the xml_request element is invalid
     *
     */
    public void testService_invalidXMLRequest() throws Exception{
    	//init the servlet
    	this.servlet.init();
    	//set invalid xml_request
    	((MockHttpServletRequest)request).setParameter("xml_request", "<request>");
    	
    	this.servlet.service(request, response);
    	assertTrue("it is not failure.", ((MockHttpServletResponse)response).getContent().indexOf("<response><failure>") > -1);
    }
    /**
     * Test the service method, the xml_request element is invalid
     *
     */
    public void testService_unknownRequest() throws Exception{
    	//init the servlet
    	this.servlet.init();
    	//set invalid xml_request
    	((MockHttpServletRequest)request).setParameter("xml_request", "<request></request>");
    	
    	this.servlet.service(request, response);
    	assertTrue("it is not failure.", ((MockHttpServletResponse)response).getContent().indexOf("<response><failure>") > -1);
    }
    /**
     * Test the service method, the xml_request element is invalid for acceptableChatRequest.
     *
     */
    public void testService_invalidAcceptChatRequest() throws Exception{
    	//init the servlet
    	this.servlet.init();
    	//actually, it needs ChatUserProfile instance.
    	session.setAttribute("user_profile_session_key", new Object());
    	//set invalid xml_request
    	((MockHttpServletRequest)request).setParameter("xml_request", "<request type=\"AcceptChatRequest\"><user_id>1</user_id><session_id>2</session_id></request>");
    	((MockHttpServletRequest)request).setHttpSession(session);
    	
    	this.servlet.service(request, response);
    	String content = ((MockHttpServletResponse)response).getContent();
    	
    	assertTrue("it is not failure.", content.indexOf("<response><failure>") > -1);
    }
    /**
     * Test the service method, the xml_request element is invalid for ChangeManagerStatusRequest.
     *
     */
    public void testService_invalidChangeManagerStatusRequest() throws Exception{
    	//init the servlet
    	this.servlet.init();
    	//actually, it needs ChatUserProfile instance.
    	ChatUserProfile profile = new ChatUserProfile("ivern","developer");
    	profile.setProperty("role", "manager");
    	session.setAttribute("user_profile_session_key", profile);
    	
    	ServletContext context = new MockServletContext();
    	context.setAttribute("chat_status_tracker_key", new MockChatStatusTracker());
    	((MockHttpSession)session).setServletContext(context);
    	
    	//set invalid xml_request
    	((MockHttpServletRequest)request).setParameter("xml_request", "<request type=\"ChangeManagerStatus\"><status>online</status></request>");
    	((MockHttpServletRequest)request).setHttpSession(session);
    	
    	this.servlet.service(request, response);
    	String content = ((MockHttpServletResponse)response).getContent();
    	
    	assertTrue("it is not failure.", content.indexOf("<response><failure>") > -1);
    }
    /**
     * Test the service method, the xml_request element is invalid for PostTextMessage.
     *
     */
    public void testService_invalidPostTextMessageRequest() throws Exception{
    	//init the servlet
    	this.servlet.init();
    	//actually, it needs ChatUserProfile instance.
    	ChatUserProfile profile = new ChatUserProfile("ivern","developer");
    	profile.setProperty("role", "manager");
    	session.setAttribute("user_profile_session_key", profile);
    	
    	ServletContext context = new MockServletContext();
    	context.setAttribute("chat_status_tracker_key", new MockChatStatusTracker());
    	((MockHttpSession)session).setServletContext(context);
    	
    	//set invalid xml_request
    	((MockHttpServletRequest)request).setParameter("xml_request",
    			"<request type=\"PostTextMessage\"><session_id>2</session_id><chat_text>Hello, this is ivern.</chat_text></request>");
    	((MockHttpServletRequest)request).setHttpSession(session);
    	
    	this.servlet.service(request, response);
    	String content = ((MockHttpServletResponse)response).getContent();
    	
    	assertTrue("it is not failure.", content.indexOf("<response><failure>") > -1);
    }
    /**
     * Test the service method, the xml_request element is invalid for ReadClientSessionMessage.
     *
     */
    public void testService_invalidReadClientSessionMessage() throws Exception{
    	//init the servlet
    	this.servlet.init();
    	//actually, it needs ChatUserProfile instance.
    	ChatUserProfile profile = new ChatUserProfile("ivern","developer");
    	profile.setProperty("role", "manager");
    	session.setAttribute("user_profile_session_key", profile);
    	   	
    	//set invalid xml_request
    	((MockHttpServletRequest)request).setParameter("xml_request",
    			"<request type=\"ReadClientSessionMessage\"><session_id>2</session_id></request>");
    	((MockHttpServletRequest)request).setHttpSession(session);
    	
    	this.servlet.service(request, response);
    	String content = ((MockHttpServletResponse)response).getContent();
    	
    	assertTrue("it is not failure.", content.indexOf("<response><failure>") > -1);
    }
    /**
     * Test the service method, the xml_request element is invalid for ReadClientUserMessage.
     *
     */
    public void testService_invalidReadClientUserMessage() throws Exception{
    	//init the servlet
    	this.servlet.init();
    	//actually, it needs ChatUserProfile instance.
    	ChatUserProfile profile = new ChatUserProfile("ivern","developer");
    	profile.setProperty("role", "manager");
    	session.setAttribute("user_profile_session_key", profile);
    	   	
    	//set invalid xml_request
    	((MockHttpServletRequest)request).setParameter("xml_request",
    			"<request type=\"ReadClientUserMessage\"><session_id>2</session_id></request>");
    	((MockHttpServletRequest)request).setHttpSession(session);
    	
    	this.servlet.service(request, response);
    	String content = ((MockHttpServletResponse)response).getContent();
    	
    	assertTrue("it is not failure.", content.indexOf("<response><failure>") > -1);
    }
    /**
     * Test the service method, the xml_request element is invalid for ReadManagerSessionMessage.
     *
     */
    public void testService_invalidReadManagerSessionMessage() throws Exception{
    	//init the servlet
    	this.servlet.init();
    	//actually, it needs ChatUserProfile instance.
    	ChatUserProfile profile = new ChatUserProfile("ivern","developer");
    	profile.setProperty("role", "manager");
    	session.setAttribute("user_profile_session_key", profile);
    	   	
    	ServletContext context = new MockServletContext();
    	context.setAttribute("chat_status_tracker_key", new MockChatStatusTracker());
    	((MockHttpSession)session).setServletContext(context);
    	
    	
    	//set invalid xml_request
    	((MockHttpServletRequest)request).setParameter("xml_request",
    			"<request type=\"ReadManagerSessionMessage\"><session_id>2</session_id></request>");
    	((MockHttpServletRequest)request).setHttpSession(session);
    	
    	this.servlet.service(request, response);
    	String content = ((MockHttpServletResponse)response).getContent();
    	System.out.println(content);
    	assertTrue("it is not failure.", content.indexOf("<response><failure>") > -1);
    }
    /**
     * Test the service method, the xml_request element is invalid for ReadManagerSessionMessage.
     *
     */
    public void testService_invalidReadManagerUserMessage() throws Exception{
    	//init the servlet
    	this.servlet.init();
    	//actually, it needs ChatUserProfile instance.
    	ChatUserProfile profile = new ChatUserProfile("ivern","developer");
    	profile.setProperty("role", "manager");
    	session.setAttribute("user_profile_session_key", profile);
    	
    	ServletContext context = new MockServletContext();
    	context.setAttribute("chat_status_tracker_key", new MockChatStatusTracker());
    	((MockHttpSession)session).setServletContext(context);
    	context.setAttribute("messenger_key", new MockMessenger());
    	
    	//set invalid xml_request
    	((MockHttpServletRequest)request).setParameter("xml_request",
    			"<request type=\"ReadManagerUserMessage\"></request>");
    	((MockHttpServletRequest)request).setHttpSession(session);
    	
    	this.servlet.service(request, response);
    	String content = ((MockHttpServletResponse)response).getContent();
    	
    	assertTrue("it is not failure.", content.indexOf("<response><success>") > -1);
    }
    /**
     * clear the environment.
     *
     * @throws Exception into JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
    }
}
