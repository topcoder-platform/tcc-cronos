/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.service.digitalrun.points.ConfigurationProvider;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerBeanConfigurationException;

/**
 * Failure test for class ConfigurationProvider.
 *
 * @author extra
 * @version 1.0
 */
public class ConfigurationProviderFailureTests extends TestCase {

    /**
     * Represents the file for test.
     */
    private String file;

    /**
     * Represents the namespace for test.
     */
    private String namespace;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        file = "failuretests/ConfigurationProvider.properties";
        namespace = "ConfigurationProvider";
        super.setUp();
    }

    /**
     * Failure test for method setConfiguration(ConfigurationObject configuration). If configuration is null,
     * IllegalArgumentException expected.
     */
    public void testSetConfiguration_Null_configuration() {
        try {
            ConfigurationProvider.setConfiguration((ConfigurationObject) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrieveConfigurationFromFile(String file, String namespace). If file is null,
     * IllegalArgumentException expected.
     */
    public void testSetConfiguration_Null_file() {
        file = null;
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(file, namespace);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrieveConfigurationFromFile(String file, String namespace). If file is empty string,
     * IllegalArgumentException expected.
     */
    public void testSetConfiguration_Empty_file() {
        file = " ";
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(file, namespace);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrieveConfigurationFromFile(String file, String namespace). If namespace is null,
     * IllegalArgumentException expected.
     */
    public void testSetConfiguration_Null_namespace() {
        namespace = null;
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(file, namespace);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrieveConfigurationFromFile(String file, String namespace). If namespace is empty
     * string, IllegalArgumentException expected.
     */
    public void testSetConfiguration_Empty_namespace() {
        namespace = " ";
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(file, namespace);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrieveConfigurationFromFile(String file, String namespace). If namespace is not exist,
     * DigitalRunPointsManagerBeanConfigurationException expected.
     */
    public void testSetConfiguration_notexist_namespace() {
        namespace = "notexist";
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(file, namespace);
            fail("DigitalRunPointsManagerBeanConfigurationException expected.");
        } catch (DigitalRunPointsManagerBeanConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrieveConfigurationFromFile(String file, String namespace). If file is not exist,
     * DigitalRunPointsManagerBeanConfigurationException expected.
     */
    public void testSetConfiguration_notexist_file() {
        file = "failuretests/notexist.properties";
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(file, namespace);
            fail("DigitalRunPointsManagerBeanConfigurationException expected.");
        } catch (DigitalRunPointsManagerBeanConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method retrieveConfigurationFromFile(String file, String namespace). If file is not exist,
     * DigitalRunPointsManagerBeanConfigurationException expected.
     */
    public void testSetConfiguration_invalidType_file() {
        file = "failuretests/ConfigurationProvider.xml";
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(file, namespace);
            fail("DigitalRunPointsManagerBeanConfigurationException expected.");
        } catch (DigitalRunPointsManagerBeanConfigurationException e) {
            // expected
        }
    }
}
