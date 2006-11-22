/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;

import com.orpheus.game.TestTargetObjectHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy Test for TestTargetObjectHandler class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestTargetObjectHandlerAccuracyTest extends TestCase {
    /**
     * The game key.
     */
    private static String ID_KEY = "id";
    
    /**
     * The not logged key.
     */
    private static String DOMAIN_KEY = "domains";
    
    /**
     * The game key.
     */
    private static String SEQ_KEY = "seq";
    
    /**
     * The not logged key.
     */
    private static String TEXT_KEY = "text";
    
    /**
     * The not logged key.
     */
    private static String FAILED_KEY = "failed";
    
    /**
     * The not logged key.
     */
    private static String NOT_LOGGED_KEY = "notlogged";
    
    /**
     * Represents the request.
     */
    private MockControl requestControl; 
    
    /**
     * Represents the response.
     */
    private MockControl responseControl;
    
    /**
     * Set up for each test.
     * 
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception  {
        super.setUp();
        AccuracyHelper.loadConfig();
        AccuracyHelper.deployEJB();
        requestControl = MockControl.createNiceControl(HttpServletRequest.class);
        responseControl = MockControl.createControl(HttpServletResponse.class);
    }
    
    /**
     * Tear down for each test.
     * 
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception  {
        AccuracyHelper.removeAllNamespace();
        super.tearDown();
    }
    
    /**
     * Test ctor TestTargetObjectHandler(Map attributes),
     * an instance with the given attributes should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception  {
        Map map = new Hashtable();
        map.put("gameIdParamKey", ID_KEY);
        map.put("domainNameParamKey", DOMAIN_KEY);
        map.put("notLoggedInResultCode", NOT_LOGGED_KEY);
        map.put("sequenceNumberParamKey", SEQ_KEY);
        map.put("testFailedResultCode", FAILED_KEY);
        map.put("textParamKey", TEXT_KEY);
        
        TestTargetObjectHandler handler = new TestTargetObjectHandler(map);
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", DOMAIN_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "domainNameParamKey", handler));
        assertEquals("The value not set correctly", NOT_LOGGED_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "notLoggedInResultCode", handler));
        assertEquals("The value not set correctly", SEQ_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "sequenceNumberParamKey", handler));
        assertEquals("The value not set correctly", FAILED_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "testFailedResultCode", handler));
        assertEquals("The value not set correctly", TEXT_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "textParamKey", handler));
    }
    
    /**
     * Test ctor TestTargetObjectHandler(Element element),
     * an instance with the given element should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception  {        
        TestTargetObjectHandler handler = new TestTargetObjectHandler(
                AccuracyHelper.getDomElement("TestTargetObjectHandler.xml"));
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", DOMAIN_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "domainNameParamKey", handler));
        assertEquals("The value not set correctly", NOT_LOGGED_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "notLoggedInResultCode", handler));
        assertEquals("The value not set correctly", SEQ_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "sequenceNumberParamKey", handler));
        assertEquals("The value not set correctly", FAILED_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "testFailedResultCode", handler));
        assertEquals("The value not set correctly", TEXT_KEY, 
                AccuracyHelper.getFieldValue(TestTargetObjectHandler.class, "textParamKey", handler));
    }

    /**
     * Test execute(ActionContext context),
     * when session not login in, NOT_LOGGED_KEY is returned.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute_UnAuthenticated() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(true);
        ActionContext context = new ActionContext(wrapper, response);
        
        assertEquals("failed to execute handler", NOT_LOGGED_KEY, 
                new TestTargetObjectHandler(
                        AccuracyHelper.getDomElement("TestTargetObjectHandler.xml")).execute(context));
    }

    /**
     * Test execute(ActionContext context),
     * matched.
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute_Matched() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter(DOMAIN_KEY, "domain");
        wrapper.setParameter(SEQ_KEY, "999");
        wrapper.setParameter(TEXT_KEY, "text");
        wrapper.setParameter(ID_KEY, "999");
        ActionContext context = new ActionContext(wrapper, response);
        
        assertNull("failed to execute handler", new TestTargetObjectHandler(
                AccuracyHelper.getDomElement("TestTargetObjectHandler.xml")).execute(context));
    }

    /**
     * Test execute(ActionContext context),
     * no matched found.
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute_UnMatched() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter(DOMAIN_KEY, "domain");
        wrapper.setParameter(SEQ_KEY, "999");
        wrapper.setParameter(TEXT_KEY, "text");
        wrapper.setParameter(ID_KEY, "2");
        ActionContext context = new ActionContext(wrapper, response);
        
        assertEquals("failed to execute handler", FAILED_KEY, new TestTargetObjectHandler(
                AccuracyHelper.getDomElement("TestTargetObjectHandler.xml")).execute(context));
    }

}