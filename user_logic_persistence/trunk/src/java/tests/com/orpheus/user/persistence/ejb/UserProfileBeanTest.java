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
 * Tests the UserProfileBean session bean. The testing is performed
 * within an application server container, even though this test case does not
 * call the session bean through its local and remote interfaces. This is done
 * because the UserProfileDAO, which the session bean uses, needs a bound JNDI
 * reference to the DataSource when it is created.
 * </p>
 * <p>
 * <b>Note:</b> This test case only tests the constructor, the interface and
 * the failure conditions related to null arguments. The testing of the actual
 * business logic is performed indirectly via the client class test cases.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class UserProfileBeanTest extends ServletTestCase {

    /**
     * <p>
     * The UserProfileBean instance to test.
     * </p>
     */
    private UserProfileBean bean = null;

    /**
     * <p>
     * Loads the test configuration namespaces into the ConfigManager, and
     * creates the test UserProfileBean instance.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadCacheConfig();

        bean = new UserProfileBean();
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
     * Tests the UserProfileBean() constructor. The newly created instance
     * should not be null.
     * </p>
     */
    public void testCtor() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The UserProfileBean instance should not be null", bean);
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithNullArg() throws Exception {
        try {
            bean.insertProfile(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithNullArg() throws Exception {
        try {
            bean.updateProfile(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the findProfiles(Map criteria) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithNullArg() throws Exception {
        try {
            bean.findProfiles(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that UserProfileBean implements the SessionBean interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("UserProfileBean should implement the SessionBean interface", bean instanceof SessionBean);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(UserProfileBeanTest.class);
    }

}
