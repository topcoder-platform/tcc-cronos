/*
 * Copyright (C) 2006 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;


import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;



import java.io.StringReader;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;


/**
 * Test Helper
 *
 * @since Apr 2, 2007
 */
public class TestHelper {


    /** Namespace for this component's manager. */
    public static final String NAMESPACE = "com.cronos.im.ajax.IMAjaxSupportUtility";

    /**
     * <p>
     * Creates a new instance of <code>TestHelper</code> class. The private constructor prevents the creation of a new
     * instance.
     * </p>
     */
    private TestHelper() {
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
        // Create a new instance of document builder factory.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Create a new document builder.
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        return doc.getDocumentElement();
    }

    /**
     * <p>
     * Add the config file.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public static void addConfiguration(String fileName) throws Exception {
        ConfigManager.getInstance().add(fileName);
    }


    /**
     * This method clears all the namespaces from ConfigManager.
     *
     * @throws Exception if any error occurs when clearing ConfigManager
     */
    public static void clearConfiguration() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Load the property from the given namespace with the given propertyName.
     *
     * @param namespace the namespace
     * @param propertyName the name of property
     *
     * @return Property instance
     *
     * @throws UnknownNamespaceException fail to load the property
     */
    public static Property getProperty(String namespace, String propertyName)
        throws UnknownNamespaceException {
        ConfigManager cm = ConfigManager.getInstance();

        return cm.getPropertyObject(namespace, propertyName);
    }
}
