/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.ejb;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.base.ConfigurationException;
import com.topcoder.timetracker.entry.base.TestHelper;


/**
 * Test case for ConfigHelper.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigHelperTest extends TestCase {
    /** Default namespace used in this test. */
    private static final String NAMESPACE = "config_helper";

    /**
     * Test {@link ConfigHelper#getStringProperty(String, String, boolean)} with valid argument. Should be
     * success.
     *
     * @throws Exception to junit
     */
    public void testGetStringProperty() throws Exception {
        String result = ConfigHelper.getStringProperty(NAMESPACE, "string1", true);
        assertEquals("the result should be", "value", result);
    }

    /**
     * Test {@link ConfigHelper#getStringProperty(String, String, boolean)} with empty property,
     * ConfigurationException should be thrown.
     *
     * @throws Exception to junit
     */
    public void testGetStringPropertyEmpty() throws Exception {
        try {
            ConfigHelper.getStringProperty(NAMESPACE, "empty", false);
            fail("property is empty , ConfigurationException is expected");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * Test {@link ConfigHelper#getStringProperty(String, String, boolean)} with not existent but required
     * property, ConfigurationException should be thrown.
     *
     * @throws Exception to junit
     */
    public void testGetStringPropertyMissingRequired()
        throws Exception {
        try {
            ConfigHelper.getStringProperty(NAMESPACE, "not_existent_property", true);
            fail("property is missing but required, ConfigurationException is expected");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * Test {@link ConfigHelper#getStringProperty(String, String, boolean)} with not existent and not required
     * property, should be success.
     *
     * @throws Exception to junit
     */
    public void testGetStringPropertyNotRequired() throws Exception {
        String result = ConfigHelper.getStringProperty(NAMESPACE, "not_existent_property", false);
        assertNull("the result should be null", result);
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();

        //load the config file
        TestHelper.loadConfig("ConfigHelperTest.xml");
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.clearConfig();
    }
}
