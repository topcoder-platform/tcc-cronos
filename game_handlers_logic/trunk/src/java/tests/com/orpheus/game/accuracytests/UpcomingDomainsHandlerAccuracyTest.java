/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;

import com.orpheus.game.UpcomingDomainsHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.orpheus.game.persistence.Domain;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for UpcomingDomainsHandler class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UpcomingDomainsHandlerAccuracyTest extends TestCase {
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
     * Test ctor UpcomingDomainsHandler(String gameIdParamKey, String domainsKey),
     * an instance with the given parameters should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception  {        
        UpcomingDomainsHandler handler = new UpcomingDomainsHandler(ID_KEY, DOMAIN_KEY);
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(UpcomingDomainsHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", DOMAIN_KEY, 
                AccuracyHelper.getFieldValue(UpcomingDomainsHandler.class, "domainsKey", handler));
    }
    
    /**
     * Test ctor UpcomingDomainsHandler(Element element),
     * an instance with the given element should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception  {        
        UpcomingDomainsHandler handler = new UpcomingDomainsHandler(
                AccuracyHelper.getDomElement("UpcomingDomainsHandler.xml"));
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(UpcomingDomainsHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", DOMAIN_KEY, 
                AccuracyHelper.getFieldValue(UpcomingDomainsHandler.class, "domainsKey", handler));
    }

    /**
     * Test execute(ActionContext context),
     * empty domain array should be returned.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute_Empty() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setParameter(ID_KEY, "1");
        ActionContext context = new ActionContext(wrapper, response);
        
        assertNull("failed to execute handler", new UpcomingDomainsHandler(ID_KEY, DOMAIN_KEY).execute(context));
        Domain[] domains = (Domain[]) wrapper.getAttribute(DOMAIN_KEY);
        assertEquals("failed to execute handler", 0, domains.length);
    }

    /**
     * Test execute(ActionContext context),
     * the domain upcoming should be returned.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setParameter(ID_KEY, "2");
        ActionContext context = new ActionContext(wrapper, response);
        
        assertNull("failed to execute handler", new UpcomingDomainsHandler(ID_KEY, DOMAIN_KEY).execute(context));
        Domain[] domains = (Domain[]) wrapper.getAttribute(DOMAIN_KEY);
        assertEquals("failed to execute handler", 3, domains.length);
        assertEquals("failed to execute handler", 7, domains[0].getId().longValue());
        assertEquals("failed to execute handler", 8, domains[1].getId().longValue());
        assertEquals("failed to execute handler", 9, domains[2].getId().longValue());
    }
}
