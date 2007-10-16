/*
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax;

import com.topcoder.service.ServiceEngine;
import com.topcoder.service.Category;
import com.topcoder.util.config.ConfigManager;

import org.w3c.dom.Element;
import javax.servlet.http.HttpServletRequest;
import com.topcoder.chat.user.profile.ChatUserProfile;

/**
 * Helper class for the unit tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {
    /**
     * The path of configuration files for this test case.
     */
    private static final String[] CONFIG_FILES = new String[] { "IMAjaxSuportUtility.xml",
            "Logging.xml", "ObjectFactory.xml", "RequestHandlerManager.xml" };

    /**
     * The path of configuration files for this test case.
     */
    private static final String[] CONFIG_FILES2 = new String[] { "IMAjaxSuportUtility2.xml",
            "Logging.xml", "ObjectFactory.xml", "RequestHandlerManager.xml" };

    /**
     * The namespaces in the above configuration files.
     */
    private static final String[] NAMESPACES = new String[] { "com.cronos.im.ajax.IMAjaxSupportUtility",
            "com.topcoder.util.log", "com.cronos.im.ajax.objectfactory",
            "com.cronos.im.ajax.RequestHandlerManager", "com.cronos.im.ajax.RequestHandlerManager.Invalid1",
            "com.cronos.im.ajax.RequestHandlerManager.Invalid2",
            "com.cronos.im.ajax.RequestHandlerManager.Invalid3",
            "com.cronos.im.ajax.RequestHandlerManager.Invalid4",
            "com.cronos.im.ajax.RequestHandlerManager.Invalid5",
            "com.cronos.im.ajax.RequestHandlerManager.Invalid6" };

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    public static void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (int loop = 0; loop < CONFIG_FILES.length; loop++) {
            cm.add(CONFIG_FILES[loop]);
        }
    }

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    public static void setUp2() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (int loop = 0; loop < CONFIG_FILES2.length; loop++) {
            cm.add(CONFIG_FILES2[loop]);
        }
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    public static void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (int loop = 0; loop < NAMESPACES.length; loop++) {
            if (cm.existsNamespace(NAMESPACES[loop])) {
                cm.removeNamespace(NAMESPACES[loop]);
            }
        }
    }

    /**
     * Create an instance of HttpServletRequest with the given request XML.
     *
     * @return an instance of HttpServletRequest for the tests.
     * @throws Exception
     *             if any error occurs
     */
    public static HttpServletRequest createHttpServletRequest() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        ChatUserProfile profile = new ChatUserProfile("name", "type");
        profile.addProperty("role", "client");
        profile.addProperty("role", "manager");
        req.getSession().setAttribute(IMAjaxSupportUtility.getUserProfileSessionKey(), profile);
        req.getSession().setAttribute("selectedCategories", new Category[0]);
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getIMMessengerKey(),
                new MockMessenger());
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getChatSessionManagerKey(),
                new MockChatSessionManager());
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getServiceEngineKey(),
                new ServiceEngine());
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getChatStatusTrackerKey(),
                new MockChatStatusTracker());
        req.getSession()
                .setAttribute(IMAjaxSupportUtility.getCategorySessionKey(), new Category(123, "name"));
        return req;
    }

    /**
     * Parse the given xml string to Element instance.
     *
     * @param xml
     *            the XML content to parse to Element instance
     * @return Element instance of the given XML
     *
     * @throws Exception
     *             if any error occurs
     */
    public static Element getElementFromString(String xml) throws Exception {
        return IMHelper.getElementFromString(xml);
    }

}
