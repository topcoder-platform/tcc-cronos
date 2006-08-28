/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.login;

import junit.framework.TestCase;

/**
 * Unit tests for <code>Util</code> class.
 *
 * @author maone
 * @version 1.0
 */
public class UtilTest extends TestCase {

    /**
     * Set up. Load the configurations.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestUtil.loadAllConfigurations();
    }

    /**
     * Tear down. Clear the configurations.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestUtil.clearAllConfigurations();

        super.tearDown();
    }

    /**
     * Test <code>getOptionalPropertyString</code> for existing property.
     * <p>
     * It will return the corresponding value.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOptionalPropertyString_Valid1() throws Exception {
        assertEquals("Failed to getOptionalPropertyString.",
                "com.topcoder.util.log.basic.BasicLog",
                Util.getOptionalPropertyString("com.topcoder.util.log", "logClass"));
    }

    /**
     * Test <code>getOptionalPropertyString</code> for non-existing property.
     * <p>
     * It will return null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOptionalPropertyString_Valid2() throws Exception {
        assertNull("Failed to getOptionalPropertyString.",
                Util.getOptionalPropertyString("com.cronos.onlinereview.login.LoginActions", "XXX"));
    }

    /**
     * Test <code>getOptionalPropertyString</code> for invalid namespace.
     * <p>
     * It will throw ConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetOptionalPropertyString_InvalidNamespace() throws Exception {
        try {
            Util.getOptionalPropertyString("this.namespace.does.not.exist", "property");
            fail("Should throw ConfigurationException for invalid namespace.");
        } catch (ConfigurationException e) {
            // pass
        }
    }

    /**
     * Test <code>getRequiredPropertyString</code> for existing property.
     * <p>
     * It will return the corresponding value.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetRequiredPropertyString_Valid1() throws Exception {
        assertEquals("Failed to getOptionalPropertyString.",
                "com.topcoder.util.log.basic.BasicLog",
                Util.getRequiredPropertyString("com.topcoder.util.log", "logClass"));
    }

    /**
     * Test <code>getRequiredPropertyString</code> for non-existing property.
     * <p>
     * It will throw ConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetRequiredPropertyString_Valid2() throws Exception {
        try {
            Util.getRequiredPropertyString("com.cronos.onlinereview.login.LoginActions", "XXX");
            fail("Should throw ConfigurationException for invalid property.");
        } catch (ConfigurationException e) {
            // pass
        }
    }

    /**
     * Test <code>getOptionalPropertyString</code> for invalid namespace.
     * <p>
     * It will throw ConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetRequiredPropertyString_InvalidNamespace() throws Exception {
        try {
            Util.getRequiredPropertyString("this.namespace.does.not.exist", "property");
            fail("Should throw ConfigurationException for invalid namespace.");
        } catch (ConfigurationException e) {
            // pass
        }
    }

    /**
     * Test <code>validateNotNull</code> with non-null value.
     * <p>
     * The given value will be returned.
     * </p>
     */
    public void testValidateNotNull_NotNull() {
        Object ret = Util.validateNotNull(new Integer(1234), "Integer");
        assertEquals("Should return given value.", new Integer(1234), ret);
    }

    /**
     * Test <code>validateNotNull</code> with null value.
     * <p>
     * It should throw IllegalArgumentException.
     * </p>
     */
    public void testValidateNotNull_Null() {
        try {
            Util.validateNotNull(null, "name");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>validateNotNullOrEmpty</code> with non-null or empty value.
     * <p>
     * The given value will be returned.
     * </p>
     */
    public void testValidateNotNullOrEmpty_NotNull() {
        Object ret = Util.validateNotNullOrEmpty("foo", "bar");
        assertEquals("Should return given value.", "foo", ret);
    }

    /**
     * Test <code>validateNotNullOrEmpty</code> with null value.
     * <p>
     * It should throw IllegalArgumentException.
     * </p>
     */
    public void testValidateNotNullOrEmpty_Null() {
        try {
            Util.validateNotNullOrEmpty(null, "name");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>validateNotNullOrEmpty</code> with empty value.
     * <p>
     * It should throw IllegalArgumentException.
     * </p>
     */
    public void testValidateNotNullOrEmpty_Empty() {
        try {
            Util.validateNotNullOrEmpty("  ", "name");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
