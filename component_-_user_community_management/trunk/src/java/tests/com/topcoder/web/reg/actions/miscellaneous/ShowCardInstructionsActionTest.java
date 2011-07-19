/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.TCWebException;

/**
 * <p> Unit test case of {@link ShowCardInstructionsAction}. </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class ShowCardInstructionsActionTest extends TestCase {
    /**
     * <p>
     * The ShowCardInstructionsAction instance to test.
     * </p>
     * */
    private ShowCardInstructionsAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ShowCardInstructionsAction();
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authenticationkey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authenticationkey", new MockBasicAuthentication());
        instance.setSession(session);
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
        TestSuite suite = new TestSuite(ShowCardInstructionsActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor ShowCardInstructionsAction.
     * It verifies instance is correctly created.
     * </p>
     * @throws Exception to Junit
     */
    public void test_ShowCardInstructionsAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
    }

    /**
     * <p>
     * Accuracy test for method execute.
     *
     * The result should be correct.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute() throws Exception {
        CardHelper.setUnlocked(100, true);
        String res = instance.execute();
        assertEquals("Incorrect result", "success", res);
        assertTrue("Incorrect result", instance.isCardUnlocked());
        CardHelper.setUnlocked(100, false);
    }

    /**
     * <p>
     * Failure test for method execute.
     *
     * Expects the TCWebException if the authentication is not injected.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute2() throws Exception {
        instance.setAuthenticationSessionKey("notexist");
        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (TCWebException e) {
            // pass
        }
    }
    /**
     * <p>
     * Test method for {@link ShowCardInstructionsAction#isCardUnlocked()}. It verifies the returned value
     * is correct.
     * </p>
     */
    public void test_isCardUnlocked() {
        assertFalse("Incorrect result", instance.isCardUnlocked());
    }
}
