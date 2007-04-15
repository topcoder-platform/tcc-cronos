/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * <p>
 * Helper class for the IM Ajax Support component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class IMHelper {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private IMHelper() {
        // empty
    }

    /**
     * <p>
     * Check whether the given Object is null.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     *
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    public static void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * <p>
     * Check whether the given String is null or empty.
     * </p>
     *
     * @param arg
     *            the String to check
     * @param name
     *            the name of the String argument to check
     *
     * @throws IllegalArgumentException
     *             if the given string is null or empty
     */
    public static void checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * Get required configuration string from given namespace and property.
     *
     * @param ns
     *            the namespace to get configuration string from
     * @param prop
     *            the property name of the configuration value to get
     * @return the configuration value of the given property in the given namespace
     * @throws IMAjaxConfigurationException
     *             if the namespace is unknown or the property is missing or empty string
     */
    static String getRequiredConfigString(String ns, String prop) throws IMAjaxConfigurationException {
        String s;
        // get configuration string from config manager
        try {
            s = ConfigManager.getInstance().getString(ns, prop);
        } catch (UnknownNamespaceException e) {
            throw new IMAjaxConfigurationException("The namespace " + ns + " is unknown.", e);
        }
        // check whether it is missing or empty
        if (s == null || s.trim().length() == 0) {
            throw new IMAjaxConfigurationException("The property " + prop + " is namespace " + ns
                    + " is missing or empty.");
        }
        return s;
    }

    /**
     * Get required configuration strings from given namespace and property.
     *
     * @param ns
     *            the namespace to get configuration strings from
     * @param prop
     *            the property name of the configuration values to get
     * @return the configuration values of the given property in the given namespace
     * @throws IMAjaxConfigurationException
     *             if the namespace is unknown or the property is missing or empty string
     */
    static String[] getRequiredConfigStrings(String ns, String prop) throws IMAjaxConfigurationException {
        String s[];
        // get configuration string from config manager
        try {
            s = ConfigManager.getInstance().getStringArray(ns, prop);
        } catch (UnknownNamespaceException e) {
            throw new IMAjaxConfigurationException("The namespace " + ns + " is unknown.", e);
        }
        // check whether it is missing or empty
        if (s == null || s.length == 0) {
            throw new IMAjaxConfigurationException("The property " + prop + " is namespace " + ns
                    + " is missing or empty.");
        }
        // check whether any element is empty
        for (int i = 0; i < s.length; i++) {
            if (s[i].trim().length() == 0) {
                throw new IMAjaxConfigurationException("The property " + prop + " is namespace " + ns
                        + " contains empty string value.");
            }
        }
        return s;
    }

    /**
     * Get content of the child element.
     *
     * @param e
     *            the current element
     * @param childName
     *            name of the child element to get content for
     * @return content of the child element, or null of none found.
     */
    public static String getSubElementContent(Element e, String childName) {
        for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
            if (n instanceof Element) {
                Element child = (Element) n;
                if (child.getTagName().equals(childName)) {
                    return child.getFirstChild().getNodeValue();
                }
            }
        }
        return null;
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
    static Element getElementFromString(String xml) throws Exception {
        // Create a new instance of document builder factory.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Create a new document builder.
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        return doc.getDocumentElement();
    }

    /**
     * Get time stamp string.
     *
     * @return time stamp string
     */
    public static String getTimeStamp() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH)
                + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":"
                + c.get(Calendar.SECOND);
    }

    /**
     * Get logging header string.
     *
     * @return logging header string
     */
    public static String getLoggingHeader(long userId) {
        StringBuffer logMsgSB = new StringBuffer();
        logMsgSB.append("User ID:");
        logMsgSB.append(userId);
        logMsgSB.append(" timestamp:");
        logMsgSB.append(IMHelper.getTimeStamp());
        return logMsgSB.toString();
    }

}
