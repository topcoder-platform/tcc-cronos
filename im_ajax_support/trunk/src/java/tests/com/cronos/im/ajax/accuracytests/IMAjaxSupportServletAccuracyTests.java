/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import java.lang.reflect.Field;

import javax.servlet.ServletContext;

import com.cronos.im.ajax.IMAjaxSupportServlet;
import com.cronos.im.ajax.IMAjaxSupportUtility;
import com.cronos.im.ajax.RequestHandlerManager;
import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatStatusTracker;
import com.topcoder.service.ServiceEngine;

import junit.framework.TestCase;


/**
 * The test of IMAjaxSupportServlet.
 *
 * @author brain_cn
 * @version 1.0
 */
public class IMAjaxSupportServletAccuracyTests extends TestCase {
    /** The tset IMAjaxSupportServlet for testing. */
    private IMAjaxSupportServlet instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Utility.loadNamespaces();
        instance = new IMAjaxSupportServletTester();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        Utility.releaseNamespaces();
    }

    /**
     * Test method for 'IMAjaxSupportServlet.init()'
     *
     * @throws Exception to JUnit
     */
    public void testInit() throws Exception {
        instance = new IMAjaxSupportServletTester();
        instance.init();
        ServletContext context = instance.getServletContext();
        assertNotNull("The context is not null", context);

        // Verify all attribute are set
        Object attribute = context.getAttribute(IMAjaxSupportUtility.getIMMessengerKey());
        assertNotNull("Miss attribute " + IMAjaxSupportUtility.getIMMessengerKey(), attribute);
        assertTrue("incorrect type", attribute instanceof Messenger);

        attribute = context.getAttribute(IMAjaxSupportUtility.getChatSessionManagerKey());
        assertNotNull("Miss attribute " + IMAjaxSupportUtility.getChatSessionManagerKey(), attribute);
        assertTrue("incorrect type", attribute instanceof ChatSessionManager);

        attribute = context.getAttribute(IMAjaxSupportUtility.getChatStatusTrackerKey());
        assertNotNull("Miss attribute " + IMAjaxSupportUtility.getChatStatusTrackerKey(), attribute);
        assertTrue("incorrect type", attribute instanceof ChatStatusTracker);

        attribute = context.getAttribute(IMAjaxSupportUtility.getServiceEngineKey());
        assertNotNull("Miss attribute " + IMAjaxSupportUtility.getServiceEngineKey(), attribute);
        assertTrue("incorrect type", attribute instanceof ServiceEngine);

        // Verify requestManager
        Field field = IMAjaxSupportServlet.class.getDeclaredField("requestManager");
        field.setAccessible(true);
        Object object = field.get(instance);
        assertNotNull("Miss requestManager field", object);
        assertTrue("incorrect type", object instanceof RequestHandlerManager);
    }

    /**
     * Test method for 'IMAjaxSupportServlet.IMAjaxSupportServlet()'
     */
    public void testIMAjaxSupportServlet() throws Exception {
        assertNotNull("Failed to create instance", instance);
    }

    /**
     * Test method for 'IMAjaxSupportServlet.service(HttpServletRequest, HttpServletResponse)'
     *
     * @throws Exception to JUnit
     */
    public void testServiceHttpServletRequestHttpServletResponse() throws Exception {
        instance.init();
        String inputXml = "<request type=\"ReadManagerUserMessage\"><session_id>1</session_id><chat_text>accuracy chat message</chat_text></request>";
        String output = Utility.handle(inputXml, instance);
        assertEquals("incorrect output", "<response><success>successfully</success><messages></messages></response>", output);
    }
}
