/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;

import com.orpheus.game.UnlockedDomainsHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.orpheus.game.persistence.Domain;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for UnlockedDomainsHandler class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnlockedDomainsHandlerAccuracyTest extends TestCase {

    /**
     * The game key.
     */
    private static String ID_KEY = "id";
    
    /**
     * The not logged key.
     */
    private static String DOMAIN_KEY = "domains";

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
     * Test ctor UnlockedDomainsHandler(String gameIdParamKey, String domainsKey),
     * an instance with the given parameters should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception  {        
        UnlockedDomainsHandler handler = new UnlockedDomainsHandler(ID_KEY, DOMAIN_KEY);
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(UnlockedDomainsHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", DOMAIN_KEY, 
                AccuracyHelper.getFieldValue(UnlockedDomainsHandler.class, "domainsKey", handler));
    }
    
    /**
     * Test ctor UnlockedDomainsHandler(Element element),
     * an instance with the given element should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception  {        
        UnlockedDomainsHandler handler = new UnlockedDomainsHandler(
                AccuracyHelper.getDomElement("UnlockedDomainsHandler.xml"));
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(UnlockedDomainsHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", DOMAIN_KEY, 
                AccuracyHelper.getFieldValue(UnlockedDomainsHandler.class, "domainsKey", handler));
    }

    /**
     * Test ctor UpcomingDomainsHandler(Element element),
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setParameter(ID_KEY, "1");
        ActionContext context = new ActionContext(wrapper, response);
        
        assertNull("failed to execute handler", new UnlockedDomainsHandler(ID_KEY, DOMAIN_KEY).execute(context));
        Domain[] domains = (Domain[]) wrapper.getAttribute(DOMAIN_KEY);
        assertEquals("failed to execute handler", 2, domains.length);
        assertEquals("failed to execute handler", 10, domains[0].getId().longValue());
        assertEquals("failed to execute handler", 11, domains[1].getId().longValue());
    }

}