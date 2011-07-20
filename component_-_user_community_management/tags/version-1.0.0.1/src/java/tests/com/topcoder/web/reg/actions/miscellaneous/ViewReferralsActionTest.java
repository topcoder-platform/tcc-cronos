/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.HashMap;
import java.util.List;
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
 * Unit test case of {@link ViewReferralsAction}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class ViewReferralsActionTest extends TestCase {
    /**
     * <p>
     * The ViewReferralsAction instance to test.
     * </p>
     * */
    private ViewReferralsAction instance;

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
        instance = new ViewReferralsAction();
        instance.setLog(LogManager.getLog());
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        instance.setAuthenticationSessionKey("authenticationkey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authenticationkey", new MockBasicAuthentication());
        instance.setSession(session);
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
        TestSuite suite = new TestSuite(ViewReferralsActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor ViewReferralsAction. It verifies instance
     * is correctly created.
     * </p>
     *
     * @throws Exception
     *             to Junit
     */
    public void test_ViewReferralsAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
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
        String res = instance.execute();
        List<ReferralData> ref = instance.getReferrals();
        assertEquals("Incorrect result", "success", res);
        assertEquals("Incorrect result", 1, ref.size());
        assertEquals("Incorrect result", "ivern", ref.get(0).getHandle());
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
     * Test method for {@link RemoveMemberPhotoAction#getReferrals()}. It
     * verifies the returned value is correct.
     * </p>
     */
    public void test_getReferrals() {
        assertNull("Incorrect value after set a new one",
                instance.getReferrals());
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
