/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.NavigationException;
import com.topcoder.web.common.TCWebException;

/**
 * <p>
 * Unit test case of {@link UnlockCardAction}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class UnlockCardActionTest extends TestCase {
    /**
     * <p>
     * The UnlockCardAction instance to test.
     * </p>
     * */
    private UnlockCardAction instance;

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
        instance = new UnlockCardAction();
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authenticationkey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authenticationkey", new MockBasicAuthentication());
        instance.setSession(session);
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        instance.setAuditDAO(new MockAuditDao());
        instance.setServletRequest(new MockHttpServletRequest());
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
        TestSuite suite = new TestSuite(UnlockCardActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor UnlockCardAction. It verifies instance is
     * correctly created.
     * </p>
     *
     * @throws Exception
     *             to Junit
     */
    public void test_UnlockCardAction0() throws Exception {
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
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for method checkInit.
     *
     * Expects UserCommunityManagementInitializationException if the auditDao is
     * null.
     * </p>
     *
     * @throws Exception
     *             to Junit
     */
    public void test_checkInit2() throws Exception {
        instance.setAuditDAO(null);
        try {
            instance.checkInit();
            fail("Expects an Exception");
        } catch (UserCommunityManagementInitializationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for method isCardUnlocked. The result should be correct.
     * </p>
     *
     * It verifies the result is correct.
     *
     * @throws Exception
     *             to Junit
     */
    public void test_isCardUnlocked() throws Exception {
        boolean res = instance.isCardUnlocked();
        assertFalse("Incorrect result", res);
    }

    /**
     * <p>
     * Accuracy test for method execute. The result should be correct.
     * </p>
     *
     * It verifies the result is correct.
     *
     * @throws Exception
     *             to Junit
     */
    public void test_execute() throws Exception {
        assertFalse("Initially should be locked", instance.isCardUnlocked());
        String res = instance.execute();
        assertEquals("Incorrect result", "success", res);
        assertTrue("Incorrect result", instance.isCardUnlocked());
    }
    /**
     * <p>
     * Failure test for method execute.
     *
     * Expects TCWebException if data access error occurs.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute2() throws Exception {
        MockDataAccessInt dataAccess = new MockDataAccessInt();
        dataAccess.setErrorFlag(true);
        instance.setDataAccess(dataAccess);
        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (TCWebException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method execute.
     *
     * Expects NavigationException if the member is not rated.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute3() throws Exception {
        MockDataAccessInt dataAccess = new MockDataAccessInt();
        dataAccess.setRows(new Object[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        instance.setDataAccess(dataAccess);
        try {
            instance.execute();
            fail("Expects an NavigationException");
        } catch (NavigationException e) {
            // pass
        }
    }
    /**
     * <p>
     * Test method for {@link setAuditDAO()}. It verifies the field is correct
     * set.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void test_setAuditDAO() throws Exception {
        MockAuditDao value = new MockAuditDao();
        instance.setAuditDAO(value);
        assertEquals("Incorrect value after set a new one", value,
                TestHelper
                        .outject(UnlockCardAction.class, instance, "auditDAO"));
    }

    /**
     * <p>
     * Test method for {@link setAuditDAO()}. It verifies the field is correct
     * set.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void test_setServletRequest() throws Exception {
        MockHttpServletRequest value = new MockHttpServletRequest();
        instance.setServletRequest(value);
        assertEquals("Incorrect value after set a new one", value,
                TestHelper.outject(UnlockCardAction.class, instance, "request"));
    }

    /**
     * <p>
     * Failure test for method execute.
     *
     * Expects the TCWebException if the authentication is not injected.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute12() throws Exception {
        instance.setAuthenticationSessionKey("notexist");
        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (TCWebException e) {
            // pass
        }
    }
}
