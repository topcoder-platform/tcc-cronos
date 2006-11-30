/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.ejb;

import javax.ejb.SessionBean;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.ServletTestCase;

import com.orpheus.user.persistence.ConfigHelper;

/**
 * <p>
 * Tests the PendingConfirmationBean session bean. The testing is performed
 * within an application server container, even though this test case does not
 * call the session bean through its local and remote interfaces. This is done
 * because the PendingConfirmationDAO, which the session bean uses, needs a
 * bound JNDI reference to the DataSource when it is created.
 * </p>
 * <p>
 * <b>Note:</b> This test case only tests the constructor, the interface and
 * the failure conditions related to null and empty string arguments. The
 * testing of the actual business logic is performed indirectly via the client
 * class test cases.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class PendingConfirmationBeanTest extends ServletTestCase {

    /**
     * <p>
     * The PendingConfirmationBean instance to test.
     * </p>
     */
    private PendingConfirmationBean bean = null;

    /**
     * <p>
     * Loads the test configuration namespaces into the ConfigManager, and
     * creates the test PendingConfirmationBean instance.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadCacheConfig();

        bean = new PendingConfirmationBean();
    }

    /**
     * <p>
     * Unloads the test configuration namespaces from the ConfigManager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigHelper.unloadCacheConfig();
    }

    /**
     * <p>
     * Tests the PendingConfirmationBean() constructor. The newly created
     * instance should not be null.
     * </p>
     */
    public void testCtor() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The PendingConfirmationBean instance should not be null", bean);
    }

    /**
     * <p>
     * Tests that the store(ConfirmationMessageDTO message) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testStoreWithNullArg() throws Exception {
        try {
            bean.store(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the contains(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContainsWithNullArg() throws Exception {
        try {
            bean.contains(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the contains(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContainsWithEmptyArg() throws Exception {
        try {
            bean.contains(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveWithNullArg() throws Exception {
        try {
            bean.retrieve(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveWithEmptyArg() throws Exception {
        try {
            bean.retrieve(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteWithNullArg() throws Exception {
        try {
            bean.delete(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteWithEmptyArg() throws Exception {
        try {
            bean.delete("  ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that UserProfileDTO implements the Serializable interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("PendingConfirmationBean should implement the SessionBean interface", bean instanceof SessionBean);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(PendingConfirmationBeanTest.class);
    }

}
