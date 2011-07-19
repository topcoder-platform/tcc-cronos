/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.util.log.LogManager;

/**
 * <p> Unit test case of {@link BaseDataAccessUserCommunityManagementAction}. </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class BaseDataAccessUserCommunityManagementActionTest extends TestCase {
    /**
     * <p>
     * The BaseDataAccessUserCommunityManagementAction instance to test.
     * </p>
     * */
    private BaseDataAccessUserCommunityManagementAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new BaseDataAccessUserCommunityManagementAction() {
            /**
            * <p>
            * The serial version uid.
            * </p>
            */
            private static final long serialVersionUID = 1L;

        };
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authenticationkey");
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p> Creates a test suite for the tests in this test case. </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BaseDataAccessUserCommunityManagementActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor BaseDataAccessUserCommunityManagementAction.
     * It verifies instance is correctly created.
     * </p>
     * @throws Exception to Junit
     */
    public void test_BaseDataAccessUserCommunityManagementAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
    }

    /**
     * <p>
     * Accuracy test for method checkInit.
     *
     * No error occurs if the action is well configured.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkInit() throws Exception {
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for method checkInit.
     *
     * Expects UserCommunityManagementInitializationException if the dataAccess is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkInit2() throws Exception {
        try {
            instance.checkInit();
            fail("Expects an Exception");
        } catch (UserCommunityManagementInitializationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#setDataAccess()}. It verifies the field is correct set.
     * </p>
     */
    public void test_setDataAccess() {
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        assertEquals("Incorrect value after set a new one", dataAccess, instance.getDataAccess());
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#getDataAccess()}. It verifies the returned
     * value is correct.
     * </p>
     */
    public void test_getDataAccess() {
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        assertEquals("Incorrect value after set a new one", dataAccess, instance.getDataAccess());
    }
}
