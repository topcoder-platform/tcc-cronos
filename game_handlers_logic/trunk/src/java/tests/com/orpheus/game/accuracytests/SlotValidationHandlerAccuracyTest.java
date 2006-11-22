/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;

import com.orpheus.game.SlotValidationHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

import junit.framework.TestCase;

/**
 * <p>
 * Accuarcy test for SlotValidationHandler class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SlotValidationHandlerAccuracyTest extends TestCase {
    /**
     * The game key.
     */
    private static String ID_KEY = "game";

    /**
     * The slot id param key.
     */
    private static String SLOT_ID_KEY = "slot";

    /**
     * The slot id param key.
     */
    private static String FAIL_KEY = "failed";

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
     * Test ctor SlotValidationHandler(String gameIdParamKey, String slotIdParamKey, String validationFailedResultCode),
     * an instance with the given parameters should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception  {        
        SlotValidationHandler handler = new SlotValidationHandler(ID_KEY, SLOT_ID_KEY, FAIL_KEY);
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(SlotValidationHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", SLOT_ID_KEY, 
                AccuracyHelper.getFieldValue(SlotValidationHandler.class, "slotIdParamKey", handler));
        assertEquals("The value not set correctly", FAIL_KEY, 
                AccuracyHelper.getFieldValue(SlotValidationHandler.class, "validationFailedResultCode", handler));
    }
    
    /**
     * Test ctor SlotValidationHandler(Element element),
     * an instance with the given element should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception  {        
        SlotValidationHandler handler = new SlotValidationHandler(
                AccuracyHelper.getDomElement("SlotValidationHandler.xml"));
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(SlotValidationHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", SLOT_ID_KEY, 
                AccuracyHelper.getFieldValue(SlotValidationHandler.class, "slotIdParamKey", handler));
        assertEquals("The value not set correctly", FAIL_KEY, 
                AccuracyHelper.getFieldValue(SlotValidationHandler.class, "validationFailedResultCode", handler));
    }

    /**
     * Test execute(ActionContext context),
     * when validate, null returned.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute_SlotNotExisted() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter(ID_KEY, "1");
        wrapper.setParameter(SLOT_ID_KEY, "1000");
        ActionContext context = new ActionContext(wrapper, response);
        
        assertEquals("failed to execute handler", FAIL_KEY, 
                new SlotValidationHandler(ID_KEY, SLOT_ID_KEY, FAIL_KEY).execute(context));
    }

    /**
     * Test execute(ActionContext context),
     * when validate, null returned.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute_Invalid() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter(ID_KEY, "2");
        wrapper.setParameter(SLOT_ID_KEY, "4");
        ActionContext context = new ActionContext(wrapper, response);
        
        assertEquals("failed to execute handler", FAIL_KEY, 
                new SlotValidationHandler(ID_KEY, SLOT_ID_KEY, FAIL_KEY).execute(context));
    }

    /**
     * Test execute(ActionContext context),
     * when validate, null returned.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute_Valid() throws Exception { 
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter(ID_KEY, "3");
        wrapper.setParameter(SLOT_ID_KEY, "7");
        ActionContext context = new ActionContext(wrapper, response);
        
        assertNull("failed to execute handler",
                new SlotValidationHandler(ID_KEY, SLOT_ID_KEY, FAIL_KEY).execute(context));
    }

}
