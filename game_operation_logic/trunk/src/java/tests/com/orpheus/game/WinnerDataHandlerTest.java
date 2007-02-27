/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.user.profile.UserProfile;

import com.topcoder.user.profile.manager.MockUserProfileManager;
import com.topcoder.user.profile.manager.ProfileTypeFactory;
import com.topcoder.user.profile.manager.UserProfileManager;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import com.topcoder.web.frontcontroller.ActionContext;

import com.topcoder.web.user.LoginHandler;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import java.util.HashMap;
import java.util.Map;


/**
 * TODO DOCUMENT ME!
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class WinnerDataHandlerTest extends TestCase {
    /**
     * Represents TODO DOCUMENT ME!
     */
    private ActionContext context;

    /**
     * Represents TODO DOCUMENT ME!
     */
    private MockHttpRequest request;

    /**
     * Represents TODO DOCUMENT ME!
     */
    private MockHttpResponse response;

    /**
     * Represents TODO DOCUMENT ME!
     */
    private MockHttpSession session;

    /**
     * Represents TODO DOCUMENT ME!
     */
    private MockServletContext servletContext;

    /**
     * Represents TODO DOCUMENT ME!
     */
    private WinnerDataHandler handler;

    /**
     * Test execute(ActionContext).
     */
    public void testExecute() throws Exception {
        handler.execute(context);
    }

    /**
     * Test WinnerDataHandler(Element) with valid Element.
     */
    public void testWinnerDataHandlerElement() throws Exception {
        Element element = DocumentHelper.getDocument("/WinnerDataHandler.xml", "config", "valie_config");
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
        UserProfileManager manager = new MockUserProfileManager();
        UserProfile profile = manager.getUserProfile(1);

        String key;

        try { // try to get key for logged-in UserProfile from config manager
            key = ConfigManager.getInstance().getString(
                    LoginHandler.class.getName(), "profile_session_key");
            if (key == null || key.trim().length() == 0) {
                key = "user_profile";
            }
        } catch (UnknownNamespaceException e) {
            key = "user_profile";
        }

        profilesMap.put("first_name", "firstName");
        profilesMap.put("email_address", "email");
        handler = new WinnerDataHandler(manager, profileTypeNames, profilesMap,
                ProfileTypeFactory.getInstance("com.topcoder.user.profile.manager"));

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        session.setAttribute(key, profile);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setParameter("firstName", "tom");
        request.setParameter("email", "tom@email.com");
        
        JNDIHelper.initJNDI();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }

}
