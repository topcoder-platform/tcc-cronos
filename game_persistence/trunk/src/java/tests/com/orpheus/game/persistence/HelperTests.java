/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import junit.framework.TestCase;


/**
 * Unit Test for Helper class.
 * @author waits
 * @version 1.0
 */
public class HelperTests extends TestCase {
    /**
     * Accuracy test for the method <code>checkNotNullOrContainNullElement(Object[], String)</code> with null byte
     * array. IllegalArgumentException should be thrown.
     */
    public void testCheckNullOrEmptyWithNullByteArray() {
        try {
            Helper.checkNotNullOrContainNullElement((Object[]) null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for the method <code>checkNotNullOrContainNullElement(Object[], String)</code> with empty byte
     * array. IllegalArgumentException should be thrown.
     */
    public void testCheckNullOrEmptyWithNullElement() {
        try {
            Helper.checkNotNullOrContainNullElement(new Object[] { null, null }, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for the method <code>checkNotNullOrEmpty(String, String)</code> with null str.
     * IllegalArgumentException should be thrown.
     */
    public void testCheckNullOrEmptyWithNullStr() {
        try {
            Helper.checkNotNullOrEmpty((String) null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for the method <code>checkNotNullOrEmpty(String, String)</code> with empty str.
     * IllegalArgumentException should be thrown.
     */
    public void testCheckNullOrEmptyWithEmptyStr() {
        try {
            Helper.checkNotNullOrEmpty(" ", "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for the method <code>checkNotNull(Object, String)</code> with null obj. IllegalArgumentException
     * should be thrown.
     */
    public void testCheckNullWithNullObj() {
        try {
            Helper.checkNotNull(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for the method <code>checkNotPositive(Object, String)</code> with not positive value, iae
     * expected.
     */
    public void testCheckNotPositive() {
        try {
            Helper.checkNotPositive(0, "value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for the method <code>checkNegative(Object, String)</code> with negative value, iae expected.
     */
    public void testCheckNegative() {
        try {
            Helper.checkNegative(-1, "value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for the method <code>checkNotNull(Object, String)</code> with non-null obj. No exception should
     * be thrown.
     */
    public void testCheckNullWithNonNullObj() {
        // no exception should be thrown
        Helper.checkNotNull(new Object(), "name");
    }

    /**
     * Test the getString(String namespace, String key, boolean required) method. The namespace does not exist,
     * InstantiationException expected.
     */
    public void testGetString_notExistNamespace() {
        try {
            Helper.getString("notExistNamespace.", "key", true);
            fail("The namespace does not exist.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the getString(String namespace, String key, boolean required) method. The key does not exist,
     * InstantiationException expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetString_notExistKey() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);

        try {
            Helper.getString(TestHelper.DB_FACTORY_NAMESPACE, "notExistKey", true);
            fail("The key does not exist.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the getString(String namespace, String key, boolean required) method. The key does not exist, but it is
     * optional, no exception will be thrown.
     *
     * @throws Exception into JUnit
     */
    public void testGetString_notExistKeyOptional() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);

        Helper.getString(TestHelper.DB_FACTORY_NAMESPACE, "notExistKey", false);
    }

    /**
     * test the getInt method, it is not int,InstantiationException expected.
     *
     * @throws Exception into Junit
     */
    public void testGetInt_notInt() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.addConfigFile("invalidConfig/DAO_Config_invalidKeyLength.xml");

        try {
            Helper.getInt(TestHelper.DAO_NAMESPACE, "keyLength", true);
            fail("The keyLength's value is not integer.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * clear the environement.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfigManager();
    }
}
