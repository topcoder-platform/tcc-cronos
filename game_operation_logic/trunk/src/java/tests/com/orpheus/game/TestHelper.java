/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.util.config.ConfigManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringBufferInputStream;

import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;


/**
 * The helper class unit tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {
/**
     * The private constructor to make this un-instantiatable.
     */
    private TestHelper() {
        // do nothing
    }

    /**
     * Load the configurations.
     *
     * @throws Exception to invoker
     */
    public static void loadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("com.topcoder.util.config.ConfigManager", "com/topcoder/util/config/ConfigManager.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.add("com.topcoder.naming.jndiutility", "com/topcoder/naming/jndiutility/JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.add("GameOperationLogicUtility.xml");
        cm.add("com.topcoder.user.profile.ConfigProfileType.base",
            "com/topcoder/user/profile/ConfigProfileType.base.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.add("UserProfileManager.xml");
        cm.add("DBConnectionFactory.xml");
        cm.add("ObjectFactory.xml");
    }

    /**
     * Parse the string representation of an xml element.
     *
     * @param xml the string representation of an xml element
     *
     * @return the parsed element
     *
     * @throws Exception to invoker
     */
    public static Element parseElement(String xml) throws Exception {
        // Create a builder factory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);

        Document doc = factory.newDocumentBuilder().parse(new StringBufferInputStream(xml));

        return doc.getDocumentElement();
    }

    /**
     * Unload the configurations.
     *
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
