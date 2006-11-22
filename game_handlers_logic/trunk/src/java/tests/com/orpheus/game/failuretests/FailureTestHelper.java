/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.topcoder.util.config.ConfigManager;

/**
 * The helper class for failure tests.
 *
 * @author assistant
 * @version 1.0
 */
public class FailureTestHelper {

    /**
     * The private constructor to make this un-instantiatable.
     */
    private FailureTestHelper() {
        // do nothing
    }

    /**
     * Parse the string representation of an xml element.
     * @param xml the string representation of an xml element
     * @return the parsed element
     * @throws Exception to invoker
     */
    public static Element parseElement(String xml) throws Exception, IOException, ParserConfigurationException {
        // Create a builder factory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        Document doc = factory.newDocumentBuilder().parse(new StringBufferInputStream(xml));
        return doc.getDocumentElement();
    }

    /**
     * Load the configurations.
     * @throws Exception to invoker
     */
    public static void loadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("failuretests/failure.xml");
    }

    /**
     * Unload the configurations.
     * @throws Exception to invoker
     */
    public static void unloadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace(it.next().toString());
        }
    }
}
