/*
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.stresstests;

import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.cronos.im.ajax.IMAjaxSupportUtility;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.service.Category;
import com.topcoder.service.ServiceEngine;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Helper class for the stress tests.
 * </p>
 *
 * @author 80x86
 * @version 1.0
 */
class StressTestHelper {
    /**
     * Represents the number of requests to process, used in stress tests.
     */
    static final int MAX_COUNT = 100;

    /**
     * The path of configuration files.
     */
    private static final String[] CONFIGURATION_FILES = new String[] {
            "stress/IMAjaxSuportUtility.xml",
            "stress/Logging.xml", "stress/ObjectFactory.xml",
            "stress/RequestHandlerManager.xml" };

    /**
     * The namespaces in the above configuration files.
     */
    private static final String[] NAMESPACES = new String[] { "com.cronos.im.ajax.IMAjaxSupportUtility",
            "com.topcoder.util.log", "com.cronos.im.ajax.objectfactory", "com.cronos.im.ajax.RequestHandlerManager"};

    /**
     * Sets up the testing environment.
     *
     * @throws Exception
     *             to JUnit
     */
    static void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (int i = 0; i < CONFIGURATION_FILES.length; i++) {
            cm.add(CONFIGURATION_FILES[i]);
        }
    }

    /**
     * Tears down the testing environment.
     *
     * @throws Exception
     *             to JUnit
     */
    static void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (int i = 0; i < NAMESPACES.length; i++) {
            if (cm.existsNamespace(NAMESPACES[i])) {
                cm.removeNamespace(NAMESPACES[i]);
            }
        }
    }

    /**
     * Creates an instance of HttpServletRequest with the given request XML.
     *
     * @return an instance of HttpServletRequest for the tests.
     * @throws Exception
     *             if any error occurs
     */
    static HttpServletRequest createHttpServletRequest() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        ChatUserProfile profile = new ChatUserProfile("name", "type");
        profile.addProperty("role", "client");
        profile.addProperty("role", "manager");

        req.getSession().setAttribute(IMAjaxSupportUtility.getUserProfileSessionKey(), profile);
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getIMMessengerKey(),
                new MockMessenger());
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getChatSessionManagerKey(),
                new MockChatSessionManager());
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getServiceEngineKey(),
                new ServiceEngine());
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getChatStatusTrackerKey(),
                new MockChatStatusTracker());
        req.getSession().setAttribute(IMAjaxSupportUtility.getCategorySessionKey(), new Category(333, "name"));

        return req;
    }

    /**
     * Parses the given xml string to Element instance.
     *
     * @param xml
     *            the XML content to be parsed to Element instance
     * @return Element instance of the given XML
     *
     * @throws Exception
     *             if any error occurs
     */
    static Element getElement(String xml) throws Exception {
        // Create a new instance of document builder factory.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Create a new document builder.
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        return doc.getDocumentElement();
    }
}
