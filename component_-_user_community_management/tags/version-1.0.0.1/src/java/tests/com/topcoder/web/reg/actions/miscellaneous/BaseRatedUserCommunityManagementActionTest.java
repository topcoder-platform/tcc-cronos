/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.TCWebException;

/**
 * <p>
 * Unit test case of {@link BaseRatedUserCommunityManagementAction}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class BaseRatedUserCommunityManagementActionTest extends TestCase {
    /**
     * <p>
     * The BaseRatedUserCommunityManagementAction instance to test.
     * </p>
     * */
    private BaseRatedUserCommunityManagementAction instance;
    /**
     * <p>
     * The data access.
     * </p>
     * */
    private MockDataAccessInt dataAccess;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new BaseRatedUserCommunityManagementAction() {
            /**
             * <p>
             * The serial version uid.
             * </p>
             */
            private static final long serialVersionUID = 1L;

        };
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authenticationkey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authenticationkey", new MockBasicAuthentication());
        instance.setSession(session);
        dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        DataAccessConstants.COMMAND = "COMMAND";
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(
                BaseRatedUserCommunityManagementActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor BaseRatedUserCommunityManagementAction. It
     * verifies instance is correctly created.
     * </p>
     *
     * @throws Exception
     *             to Junit
     */
    public void test_BaseRatedUserCommunityManagementAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
    }

    /**
     * <p>
     * Accuracy test for method checkInit.
     *
     * No error occurs if the action is well configured.
     * </p>
     *
     * @throws Exception
     *             to Junit
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
     * Expects UserCommunityManagementInitializationException if the dataAccess
     * is null.
     * </p>
     *
     * @throws Exception
     *             to Junit
     */
    public void test_checkInit2() throws Exception {
        instance.setDataAccess(null);
        try {
            instance.checkInit();
            fail("Expects an Exception");
        } catch (UserCommunityManagementInitializationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#setDataAccess()}
     * . It verifies the field is correct set.
     * </p>
     */
    public void test_setDataAccess() {
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        assertEquals("Incorrect value after set a new one", dataAccess,
                instance.getDataAccess());
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#getDataAccess()}
     * . It verifies the returned value is correct.
     * </p>
     */
    public void test_getDataAccess() {
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        assertEquals("Incorrect value after set a new one", dataAccess,
                instance.getDataAccess());
    }

    /**
     * <p>
     * Accuracy test for method isRated. The result should be correct.
     * </p>
     *
     * It verifies the result is correct.
     *
     * @throws Exception
     *             to Junit
     */
    public void test_isRated() throws Exception {
        boolean res = instance.isRated();
        assertTrue("Incorrect result", res);
    }

    /**
     * <p>
     * Accuracy test for method isRated. The result should be correct.
     * </p>
     *
     * It verifies the result is correct.
     *
     * @throws Exception
     *             to Junit
     */
    public void test_isRated2() throws Exception {
        dataAccess.setRows(new Object[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        boolean res = instance.isRated();
        assertFalse("Incorrect result", res);
    }

    /**
     * <p>
     * Accuracy test for method isRated. The result should be correct.
     * </p>
     *
     * It verifies the result is correct.
     *
     * @throws Exception
     *             to Junit
     */
    public void test_isRated32() throws Exception {
        dataAccess.setRows(new Object[]{12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        boolean res = instance.isRated();
        assertTrue("Incorrect result", res);
    }

    /**
     * <p>
     * Accuracy test for method isRated. The result should be correct.
     * </p>
     *
     * It verifies the result is correct.
     *
     * @throws Exception
     *             to Junit
     */
    public void test_isRated4() throws Exception {
        dataAccess.setRows(new Object[]{0, 45, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        boolean res = instance.isRated();
        assertTrue("Incorrect result", res);
    }
    /**
     * <p>
     * Accuracy test for method isRated. The result should be correct.
     * </p>
     *
     * It verifies the result is correct.
     *
     * @throws Exception
     *             to Junit
     */
    public void test_isRated5() throws Exception {
        dataAccess.setRows(new Object[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        boolean res = instance.isRated();
        assertTrue("Incorrect result", res);
    }
    /**
     * <p>
     * Accuracy test for method isRated. The result should be correct.
     * </p>
     *
     * It verifies the result is correct.
     *
     * @throws Exception
     *             to Junit
     */
    public void test_isRated6() throws Exception {
        dataAccess.setRows(new Object[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        boolean res = instance.isRated();
        assertTrue("Incorrect result", res);
    }
    /**
     * <p>
     * Failure test for method isRated.
     *
     * Expects TCWebException if data access error occurs.
     * </p>
     * @throws Exception to Junit
     */
    public void test_isRated12() throws Exception {
        MockDataAccessInt dataAccess = new MockDataAccessInt();
        dataAccess.setErrorFlag(true);
        instance.setDataAccess(dataAccess);
        try {
            instance.isRated();
            fail("Expects an Exception");
        } catch (TCWebException e) {
            // pass
        }
    }
}
