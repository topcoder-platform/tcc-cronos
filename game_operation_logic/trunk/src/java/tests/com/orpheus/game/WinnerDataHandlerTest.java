/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.user.profile.manager.MockUserProfileManager;
import com.topcoder.user.profile.manager.ProfileTypeFactory;

import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import java.util.HashMap;
import java.util.Map;


/**
 * Test case for WinnerDataHandler.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class WinnerDataHandlerTest extends TestCase {
    /** Represents the default ActionContext used in this test. */
    private ActionContext context;

    /** Represents the default HttpRequest used in this test. */
    private MockHttpRequest request;

    /** Represents the default HttpResponse used in this test. */
    private MockHttpResponse response;

    /** Represents the default HttpSession  used in this test. */
    private MockHttpSession session;

    /** Represents the default servletContext used in this test. */
    private MockServletContext servletContext;

    /** Represents the default WinnerDataHandler used in this test. */
    private WinnerDataHandler handler;

    /**
     * Test for {@link WinnerDataHandler#execute(ActionContext)}. It should return null.
     *
     * @throws Exception exception thrown to JUnit.
     */
    public void testExecute() throws Exception {
        assertEquals("execute failed.", null, handler.execute(context));
    }

    /**
     * Test WinnerDataHandler(Element) with valid Element.
     *
     * @throws Exception to junit
     */
    public void testWinnerDataHandlerElement() throws Exception {
        Element element = DocumentHelper.getDocument("/WinnerDataHandler.xml", "config", "valid_config");
        WinnerDataHandler handler = new WinnerDataHandler(element);
        handler.execute(context);

        //success
    }

    /**
     * Test WinnerDataHandler(UserProfileManager, String[], Map, ProfileTypeFactory) with valid argument.
     */
    public void testWinnerDataHandlerUserProfileManagerStringArrayMapProfileTypeFactory() {
        assertNotNull("should be instantiated successfully", handler);
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to Junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();

        String[] profileTypeNames = new String[] { "typeA", "typeB" };
        Map profilesMap = new HashMap();
        profilesMap.put("first_name", "firstName");
        profilesMap.put("email_address", "email");
        handler = new WinnerDataHandler(new MockUserProfileManager(), profileTypeNames, profilesMap,
                ProfileTypeFactory.getInstance("com.topcoder.user.profile.manager"));

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setParameter("firstName", "tom");
        request.setParameter("email", "tom@email.com");

        JNDIHelper.initJNDI();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }
}
