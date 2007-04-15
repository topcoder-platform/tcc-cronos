/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cronos.im.ajax.IMAjaxConfigurationException;
import com.cronos.im.ajax.RequestHandlerManager;

import junit.framework.TestCase;


/**
 * Failure test cases for the class RequestHandlerManager.
 *
 * @author waits
 * @version 1.0
 * @since Apr 6, 2007
 */
public class RequestHandlerManagerFailureTests extends TestCase {
    /** RequestHandlerManager instance to test against. */
    private RequestHandlerManager manager = null;

    /**
     * Setup the environment.
     *
     * @throws Exception into JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.addConfiguration("failure" + java.io.File.separator + "config.xml");
        manager = new RequestHandlerManager();
    }

    /**
     * Test RequestHandlerManager(String namespace) with null namespace,  iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullNamespace() throws Exception {
        try {
            new RequestHandlerManager(null);
            fail("The namespace is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test RequestHandlerManager(String namespace) with empty namespace,  iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyNamespace() throws Exception {
        try {
            new RequestHandlerManager(" ");
            fail("The namespace is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test RequestHandlerManager(String namespace) with not exist namespace,   IMAjaxConfigurationException expected.
     */
    public void testCtor_notExistNamespace() {
        try {
            new RequestHandlerManager("notExist");
            fail("The namespace does not exist.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }

    /**
     * Create RequestHandlerManager with the given namespace.
     * IMAjaxConfigurationException expected.
     * @param namespace given namespace
     */
    private void invalid_configuration(String namespace){
    	try {
            new RequestHandlerManager(namespace);
            fail("The configuration is invalid.");
        } catch (IMAjaxConfigurationException e) {
            //good
        }
    }
    /**
     * Test ctor with invalid configuration, the property
     * 'object_factory_namespace' is missing,
     * IMAjaxConfigurationException expected.
     *
     */
    public void testCtor_invalidConfiguration1() {
    	invalid_configuration("failure1");
    }
    /**
     * Test ctor with invalid configuration, the property
     * 'object_factory_namespace''s value is invalid,
     * IMAjaxConfigurationException expected.
     *
     */
    public void testCtor_invalidConfiguration2() {
    	invalid_configuration("failure2");
    }
    /**
     * Test ctor with invalid configuration, the property
     * 'handler_types''s defined value is not exist in the namespace.
     * IMAjaxConfigurationException expected.
     *
     */
    public void testCtor_invalidConfiguration3() {
    	invalid_configuration("failure3");
    }
    /**
     * Test ctor with invalid configuration, the property
     * 'handler_types' is missing.
     * IMAjaxConfigurationException expected.
     *
     */
    public void testCtor_invalidConfiguration4() {
    	invalid_configuration("failure4");
    }
    /**
     * Test ctor with invalid configuration, the property
     * 'handler_types' 's type is invalid.
     * IMAjaxConfigurationException expected.
     *
     */
    public void testCtor_invalidConfiguration5() {
    	invalid_configuration("failure5");
    }
    /**
     * Test ctor with invalid configuration, the property
     * 'AcceptChatRequest' 's value is invalid.
     * IMAjaxConfigurationException expected.
     *
     */
    public void testCtor_invalidConfiguration6() {
    	invalid_configuration("failure6");
    }
    /**
     * Test ctor with invalid configuration, the property
     * 'object_factory_namespace' value with invalid config.
     * IMAjaxConfigurationException expected.
     *
     */
    public void testCtor_invalidConfiguration7() {
    	invalid_configuration("failure7");
    }


    /**
     * Test the handle with null request, iae expected.
     * @throws Exception into JUnit
     */
    public void testHandle_nullRequest() throws Exception{
    	try{
    		this.manager.handle(null, new MockHttpServletResponse());
    		fail("The request is null.");
    	}catch(IllegalArgumentException e){
    		//good
    	}
    }
    /**
     * Test the handle with null request, iae expected.
     * @throws Exception into JUnit
     */
    public void testHandle_nullResponse() throws Exception{
    	try{
    		this.manager.handle(new MockHttpServletRequest(), null);
    		fail("The response is null.");
    	}catch(IllegalArgumentException e){
    		//good
    	}
    }

    /**
     * Test handle method, the getWriter will cause IOException.
     *
     */
    public void testHandle_IOException() {
    	try{
    		this.manager.handle(new MockHttpServletRequest(), new MockHttpServletResponseInvalid());
    		fail("The response is invalid.");
    	}catch(IOException e){
    		//good
    	}
    	
    }
    /**
     * Test the handle with invalid request, the 'xml_request' parameter is missing.
     *
     * @throws Exception into JUnit
     */
    public void testHandle_invalidRequest() throws Exception{
    	HttpServletRequest request = new MockHttpServletRequest();
    	HttpServletResponse response = new MockHttpServletResponse();
    	
    	((MockHttpServletRequest)request).setParameter("xml_request",
    			"<request type=\"AcceptChatRequest\"><user_id>1</user_id><session_id>2</session_id></request>");
    	try{
    		this.manager.handle(request, response);
    	}catch(Throwable e){
    		e.printStackTrace();
    	}
    }

    /**
     * Clear the environment.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
    }
}
