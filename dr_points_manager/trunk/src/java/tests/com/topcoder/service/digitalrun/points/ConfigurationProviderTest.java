/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;

/**
 * <p>
 * Unit test cases for class ConfigurationProvider. All the method are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationProviderTest extends TestCase {

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(ConfigurationProviderTest.class);
    }

    /**
     * <p>
     * Accuracy test for the <code>setConfiguration(ConfigurationObject configuration)</code>
     * method, expects no error occurs.
     * </p>
     */
    public void testSetConfiguration_Accuracy() {
        ConfigurationObject co = new DefaultConfigurationObject("temp");
        ConfigurationProvider.setConfiguration(co);
        assertEquals("The configuation object should be same as ", co, ConfigurationProvider
                .getConfiguration());
    }

    /**
     * <p>
     * Failure test for the <code>setConfiguration(ConfigurationObject configuration)</code>
     * method with the configuration is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetConfiguration_WithNull() {
        try {
            ConfigurationProvider.setConfiguration(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>retrieveConfigurationFromFile()</code> method, expects no error
     * occurs.
     * </p>
     */
    public void testRetrieveConfigurationFromFile_Accuracy() {
        ConfigurationProvider.retrieveConfigurationFromFile();
        assertNotNull("The result should not be null.", ConfigurationProvider.getConfiguration());
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>retrieveConfigurationFromFile(String file, String namespace)</code> method, expects
     * no error occurs.
     * </p>
     */
    public void testRetrieveConfigurationFromFile2_Accuracy() {
        ConfigurationProvider.retrieveConfigurationFromFile(ConfigurationProvider.DEFAULT_FILE,
                ConfigurationProvider.DEFAULT_NAMESPACE);
        assertNotNull("The result should not be null.", ConfigurationProvider.getConfiguration());
    }

    /**
     * <p>
     * Failure test for the
     * <code>retrieveConfigurationFromFile(String file, String namespace)</code> method with the
     * file is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testRetrieveConfigurationFromFile2_WithNullFile() {
        try {
            ConfigurationProvider
                    .retrieveConfigurationFromFile(null, ConfigurationProvider.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>retrieveConfigurationFromFile(String file, String namespace)</code> method with the
     * file is empty.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testRetrieveConfigurationFromFile2_WithEmptyFile() {
        try {
            ConfigurationProvider.retrieveConfigurationFromFile("", ConfigurationProvider.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>retrieveConfigurationFromFile(String file, String namespace)</code> method with the
     * file is trimmed empty.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testRetrieveConfigurationFromFile2_WithTrimmedEmptyFile() {
        try {
            ConfigurationProvider.retrieveConfigurationFromFile("   ",
                    ConfigurationProvider.DEFAULT_NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>retrieveConfigurationFromFile(String file, String namespace)</code> method with the
     * namespace is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testRetrieveConfigurationFromFile2_WithNullNamespace() {
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(ConfigurationProvider.DEFAULT_FILE, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>retrieveConfigurationFromFile(String file, String namespace)</code> method with the
     * namespace is empty.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testRetrieveConfigurationFromFile2_WithEmptyNamespace() {
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(ConfigurationProvider.DEFAULT_FILE, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>retrieveConfigurationFromFile(String file, String namespace)</code> method with the
     * namespace is trimmed empty.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testRetrieveConfigurationFromFile2_WithTrimmedEmptyNamespace() {
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(ConfigurationProvider.DEFAULT_FILE, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>retrieveConfigurationFromFile(String file, String namespace)</code> method with the
     * file is not exist.<br>
     * DigitalRunPointsManagerBeanConfigurationException is expected.
     * </p>
     */
    public void testRetrieveConfigurationFromFile2_WithNotExistFile() {
        try {
            ConfigurationProvider.retrieveConfigurationFromFile("Not Exist",
                    ConfigurationProvider.DEFAULT_NAMESPACE);
            fail("DigitalRunPointsManagerBeanConfigurationException should be thrown.");
        } catch (DigitalRunPointsManagerBeanConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>retrieveConfigurationFromFile(String file, String namespace)</code> method with the
     * namespace is not exist.<br>
     * DigitalRunPointsManagerBeanConfigurationException is expected.
     * </p>
     */
    public void testRetrieveConfigurationFromFile2_WithNotExistNamespacee() {
        try {
            ConfigurationProvider.retrieveConfigurationFromFile(ConfigurationProvider.DEFAULT_FILE,
                    "Not Exist");
            fail("DigitalRunPointsManagerBeanConfigurationException should be thrown.");
        } catch (DigitalRunPointsManagerBeanConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getConfiguration()</code> method, expects no error occurs.
     * </p>
     */
    public void testGetConfiguration_Accuracy() {
        ConfigurationObject co = new DefaultConfigurationObject("temp");
        ConfigurationProvider.setConfiguration(co);
        assertEquals("The configuation object should be same as ", co, ConfigurationProvider
                .getConfiguration());
    }

}
