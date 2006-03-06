/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * Helper.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.util.config.ConfigManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

/**
 * <p>
 * This class contains some utility methods, that are used so that to set up and
 * tear down environment.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class Helper {

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private Helper() {
        //empty
    }

    /**
     * <p>
     * Add all necessary config files.
     * </p>
     *
     * @throws Exception exception
     */
    public static void initNamespace() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        //add file with invalid configurations
        manager.add(new File("test_files/unittests/invalid_configs.xml").getAbsolutePath());

        //add file with valid configurationa
        manager.add(new File("test_files/unittests/configs.xml").getAbsolutePath());

        // added by primary reviewer since the testing xmi parser stub of mine require this
        manager.add("com.topcoder.util.xmi.parser.default.handler",
                "unittests/xmi_handler.xml", ConfigManager.CONFIG_XML_FORMAT);
    }

    /**
     * Clear namespaces.
     *
     * @throws Exception exception
     */
    public static void clearNamespace() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();
        Iterator iter = manager.getAllNamespaces();
        while (iter.hasNext()) {
            String ns = (String) iter.next();
            manager.removeNamespace(ns);
        }
    }

    /**
     * <p>
     * This method returns file's content as string.
     * </p>
     *
     * @param fileName fila name
     * @return file content
     * @throws Exception exception
     */
    public static String getFileAsString(String fileName) throws Exception {
        StringBuffer res = new StringBuffer();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String next = br.readLine();
        while (next != null) {
            res.append(next);
            next = br.readLine();
        }
        br.close();
        return res.toString();
    }
}