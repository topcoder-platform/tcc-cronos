/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import org.w3c.dom.Element;

import com.cronos.im.ajax.handler.AbstractRequestHandler;

import junit.framework.TestCase;

/**
 * Base handler test support class.
 *
 * @author waits
 * @since Apr 7, 2007
 * @version 1.0
 */
public abstract class BaseHandlerTestSupport extends TestCase {

	/**
	 * Handler.
	 */
	private AbstractRequestHandler handler = null;
	
	/**
	 * The element to handle.
	 */
	private Element element = null;
	
	/**
	 * Create handler instance.
	 * @return AbstractRequestHandler handler
	 */
	protected abstract AbstractRequestHandler createHandler() ;
	
	/**
	 * Create element for testing.
	 * @return Element
	 * @throws Exception fail to create element
	 */
	protected abstract Element createElement() throws Exception;
	
	/**
	 * Create instances.
	 * @throws Exception into JUnit
	 */
	protected void setUp() throws Exception{
		handler = this.createHandler();
		element = this.createElement();
	}
	 /**
     * Test the handler method with null xmlElement. iae expected.
     * @throws Exception to JUnit
     */
    public void testHanlder_nullXMLElement() throws Exception {
        try {
            handler.handle(null, new MockHttpServletRequest(), new MockHttpServletResponse());
            fail("The xmlElement is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the handle method with null request, iae expected.
     * @throws Exception into Junit
     */
    public void testHandler_nullRequest()throws Exception{
    	try{
    		handler.handle(element, null, new MockHttpServletResponse());
    		fail("The request is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * Test the handle method with null response, iae expected.
     * @throws Exception into Junit
     */
    public void testHandler_nullResponse()throws Exception{
    	try{
    		handler.handle(element, new MockHttpServletRequest(), null);
    		fail("The response is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
}

