/**
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import java.io.StringReader;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.cronos.im.ajax.IMAjaxSupportServlet;
import com.cronos.im.ajax.IMAjaxSupportUtility;
import com.cronos.im.ajax.IMHelper;
import com.cronos.im.ajax.MockHttpServletResponse;
import com.cronos.im.ajax.RequestHandler;
import com.cronos.im.ajax.RequestHandlerManager;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.service.Category;
import com.topcoder.service.ServiceEngine;
import com.topcoder.util.config.ConfigManager;


/**
 * The ConfigHelper is used to setup configuration.
 *
 * @author brain_cn
 * @version 1.0
 */
class Utility {
    /** Represents the configuration file. */
    private static final String CONFIG_FILE = "accuracytests/config.xml";

    /** Represents the all namespaces. */
    private static final String[] NAMESPACES = new String[] {
            "com.cronos.im.ajax.objectfactory", "com.topcoder.util.log", "com.cronos.im.ajax.IMAjaxSupportUtility",
            "com.cronos.im.ajax.RequestHandlerManager"
        };

    /**
     * Loads the namespaces under the default configuration file.
     *
     * @throws Exception to JUnit
     */
    static void loadNamespaces() throws Exception {
        releaseNamespaces();

        ConfigManager config = ConfigManager.getInstance();

        config.add(CONFIG_FILE);
    }

    /**
     * Clears all the namespaces.
     *
     * @throws Exception to JUnit
     */
    static void releaseNamespaces() throws Exception {
        ConfigManager config = ConfigManager.getInstance();

        for (int i = 0; i < NAMESPACES.length; i++) {
            if (config.existsNamespace(NAMESPACES[i])) {
                config.removeNamespace(NAMESPACES[i]);
            }
        }
    }

    /**
     * Create an instance of HttpServletRequest with required attributes.
     *
     * @return an instance of HttpServletRequest for the tests.
     * @throws Exception if any error occurs
     */
    static HttpServletRequest getHttpServletRequest() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        ChatUserProfile profile = new ChatUserProfile("accuray_tester", "tester");
        profile.addProperty("role", "client");
        profile.addProperty("role", "manager");
        req.getSession().setAttribute(IMAjaxSupportUtility.getUserProfileSessionKey(), profile);
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getIMMessengerKey(),
                new MessengerTester());
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getChatSessionManagerKey(),
                new ChatSessionManagerTester());
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getServiceEngineKey(),
                new ServiceEngine());
        req.getSession().getServletContext().setAttribute(IMAjaxSupportUtility.getChatStatusTrackerKey(),
                new ChatStatusTrackerTester());
        req.getSession()
                .setAttribute(IMAjaxSupportUtility.getCategorySessionKey(), new Category(1, "test Category"));
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
    static Element asElement(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        return doc.getDocumentElement();
    }

    /**
     * Get content of the child element.
     *
     * @param e the current element
     * @param childName name of the child element to get content for
     * @return content of the child element, or null of none found.
     */
    static String getElementContent(Element e, String childName) {
        for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
            if (n instanceof Element) {
                Element child = (Element) n;
                if (child.getTagName().equalsIgnoreCase(childName)) {
                    return child.getFirstChild().getNodeValue();
                }
            }
        }
        return null;
    }

    /**
     * Parse the given xml string to Element instance.
     *
     * @param input the XML content of request
     * @param handler the handler to parse XML content of request
     * @return the XML content of response
     *
     * @throws Exception
     *             if any error occurs
     */
    static String handle(String input, RequestHandler handler) throws Exception {
        Element element = asElement(input);
        MockHttpServletResponse res = new MockHttpServletResponse();
        handler.handle(element, getHttpServletRequest(), res);
        return res.getContent();
    }

    /**
     * Parse the given xml string to Element instance.
     *
     * @param input the XML content of request
     * @param handler the handler to parse XML content of request
     * @return the XML content of response
     *
     * @throws Exception
     *             if any error occurs
     */
    static String handle(String input, IMAjaxSupportServlet servlet) throws Exception {
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpServletRequest req = (MockHttpServletRequest) getHttpServletRequest();
        req.setParameter(IMAjaxSupportUtility.getXMLRequestParamKey(), input);
        servlet.service(req, res);
        return res.getContent();
    }

    /**
     * Parse the given xml string to Element instance.
     *
     * @param input the XML content of request
     * @param handler the handler to parse XML content of request
     * @return the XML content of response
     *
     * @throws Exception
     *             if any error occurs
     */
    static String handle(String input, RequestHandlerManager manager) throws Exception {
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpServletRequest req = (MockHttpServletRequest) getHttpServletRequest();
        req.setParameter(IMAjaxSupportUtility.getXMLRequestParamKey(), input);
        manager.handle(req, res);
        return res.getContent();
    }

    /**
     * Print the debug information.
     *
     * @param prompt the prompt info
     * @param out the out info
     */
    static void print(String prompt, String out) {
        System.out.println(prompt + " : " + out);
    }
}
