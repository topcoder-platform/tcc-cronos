/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Helper class assisting the failure tests.
 *
 * @author GavinWang
 * @version 1.0
 */
final class TestHelper extends TestCase {
    /** 
     * Validation manager namespace for failure tests. 
     */
    public static final String VALIDATION_MANAGER_NAMESPACE = "com.topcoder.apps.screening.applications.specification.ValidationManager";
    
    /** 
     * SubmissionValidatorFactory namespace for failure tests. 
     */
    public static final String SUBMISSION_VALIDATOR_FACTORY_NAMESPACE= "com.topcoder.apps.screening.applications.specification.impl.DefaultSubmissionValidatorFactory";
    
    /** default namespace for XMI handler.*/
    public static final String XMI_HANDLER_NAMESPACE = "com.topcoder.util.xmi.parser.default.handler";
    
    /**
     * Prevents instantiation.
     */
    private TestHelper() {
    }
    
    /**
     * Loads all config namespaces needed by the failure tests.
     *
     * @throws Exception if any error occurs
     */
    public static void loadConfig() throws Exception {
        cleanConfig();

        ConfigManager cm = ConfigManager.getInstance();

        cm.add("failure/config.xml"); 
        cm.add("failure/failure_config.xml"); 
        cm.add(XMI_HANDLER_NAMESPACE, "failure/xmi_handler.xml",
                ConfigManager.CONFIG_XML_FORMAT);
        
    }
    
    /**
     * Cleans all config namespaces needed by this conponent.
     *
     * @throws Exception if any error occurs
     */
    public static void cleanConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            String ns = (String) iter.next();

            if (cm.existsNamespace(ns)) {
                cm.removeNamespace(ns);
            }
        }
    }
}
