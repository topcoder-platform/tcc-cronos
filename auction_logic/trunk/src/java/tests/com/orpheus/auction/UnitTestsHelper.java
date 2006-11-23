/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Helper class that defines shared helper methods used during testing of this component.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class UnitTestsHelper {

    /**
     * <p>
     * Name of the configuration file.
     * </p>
     */
    static final String CONFIG_FILE = "config.xml";

    /**
     * <p>
     * The Document instance that is being used to create elements.
     * </p>
     */
    private static Document rootDocument = null;

    /**
     * <p>
     * This class couldn't be instantiated.
     * </p>
     */
    private UnitTestsHelper() {
    }

    /**
     * <p>
     * Loads the specified configuration file.
     * </p>
     * @param configFile the configuration file to be loaded.
     * @throws Exception to JUnit.
     */
    static void loadConfig(String configFile) throws Exception {
        clearConfig();

        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add(configFile);
    }

    /**
     * <p>
     * Removes all loaded data from the configuration manager..
     * </p>
     * @throws Exception to JUnit.
     */
    static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator i = configManager.getAllNamespaces(); i.hasNext();) {
            String ns = (String) i.next();

            if (configManager.existsNamespace(ns)) {
                configManager.removeNamespace(ns);
            }
        }
    }

    /**
     * <p>
     * Creates a handler tag with the given type attribute and children.
     * </p>
     * @param type type attribute of the handler tag.
     * @param children child tags of the handler tag.
     * @param values values of the child tags.
     * @return create handler Element.
     * @throws Exception to JUnit.
     */
    static Element createHandlerElement(String type, String[] children, String[] values)
        throws Exception {
        if (rootDocument == null) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            rootDocument = builder.newDocument();
        }

        Element element = rootDocument.createElement("handler");
        element.setAttribute("type", type);

        for (int i = 0; i < children.length; i++) {
            Element child = rootDocument.createElement(children[i]);
            child.appendChild(rootDocument.createTextNode(values[i]));
            element.appendChild(child);
        }
        return element;
    }
}
