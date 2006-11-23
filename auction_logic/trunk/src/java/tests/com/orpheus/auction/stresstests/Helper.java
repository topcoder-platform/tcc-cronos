/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.stresstests;

import com.topcoder.util.config.ConfigManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;


import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Defines helper methods used in unit tests of this component.
 * @author waits
 * @version 1.0
 */
public final class Helper {
    /** config file for the component. */
    public static final String CONFIG_FILE = "stress_testcases/Auction_Logic.xml";

    /**
     * This private constructor prevents the creation of a new instance.
     */
    private Helper() {
    }


    /**
     * <p>
     * Add the config file.
     * </p>
     *
     * @param fileName the config file to add
     *
     * @throws Exception into JUnit
     */
    public static void addConfigFile(String fileName) throws Exception {
        ConfigManager.getInstance().add(fileName);
    }

    /**
     * This method clears all the namespaces from ConfigManager.
     *
     * @throws Exception if any error occurs when clearing ConfigManager
     */
    public static void clearConfigManager() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Parses the file from the specified location and returns a dom element.
     *
     * @param filename the file name.
     *
     * @return a dom element.
     */
    static Element getDomElement(String filename) {
        try {
            // Create a builder factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);

            // Create the builder and parse the file
            Document document = factory.newDocumentBuilder().parse(new File(filename));

            return document.getDocumentElement();
        } catch (Exception e) {
            // ignore
        }

        return null;
    }
}
