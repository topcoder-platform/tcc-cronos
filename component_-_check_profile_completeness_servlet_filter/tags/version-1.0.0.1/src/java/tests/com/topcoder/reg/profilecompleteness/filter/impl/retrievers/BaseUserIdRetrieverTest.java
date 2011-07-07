/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.retrievers;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessConfigurationException;
import com.topcoder.reg.profilecompleteness.filter.MockLog;

/**
 * Unit tests for <code>BaseUserIdRetriever</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class BaseUserIdRetrieverTest extends TestCase {

    /**
     * A valid user session key to used in tests.
     */
    private static final String USER_SESSION_KEY = "user_session_key";

    /**
     * Represents the <code>BaseUserIdRetriever</code> instance used to test against.
     */
    private BaseUserIdRetriever t;

    /**
     * Represents the <code>Log</code> instance used to test against.
     */
    private MockLog log;

    /**
     * Sets up the fixture. This method is called before a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void setUp() throws Exception {
        t = new BaseUserIdRetriever() {
            public Long getUserId(HttpServletRequest request) {
                return 1L;
            }
        };
        log = new MockLog();
    }

    /**
     * Tears down the fixture. This method is called after a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void tearDown() throws Exception {
        t = null;
        log = null;
    }

    /**
     * Accuracy test for constructor <code>BaseUserIdRetriever()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
    }

    /**
     * Accuracy test for <code>checkInitialization()</code>.
     *
     * No exception thrown if the fields are correctly injected.
     */
    public void testCheckInitialization_Accuracy() {
        t.setUserSessionKey(USER_SESSION_KEY);
        t.setLog(log);
        t.checkInitialization();
        // no exception thrown
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException1() {
        t.setUserSessionKey(null); // set to null
        t.setLog(log);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException2() {
        t.setUserSessionKey("  "); // set to empty string
        t.setLog(log);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Accuracy test for <code>getUserSessionKey()</code>.
     *
     * Field "userSessionKey" should be set and got correctly.
     */
    public void testGetUserSessionKey_Accuracy() {
        t.setUserSessionKey(USER_SESSION_KEY);
        assertEquals(USER_SESSION_KEY, t.getUserSessionKey());
    }

    /**
     * Accuracy test for <code>setUserSessionKey(String userSessionKey)</code>.
     *
     * Field "userSessionKey" should be set and got correctly.
     */
    public void testSetUserSessionKey_Accuracy() {
        t.setUserSessionKey(USER_SESSION_KEY);
        assertEquals(USER_SESSION_KEY, t.getUserSessionKey());
    }

    /**
     * Accuracy test for <code>getLog()</code>.
     *
     * Field "log" should be set and got correctly.
     */
    public void testGetLog_Accuracy() {
        t.setLog(log);
        assertSame(log, t.getLog());
    }

    /**
     * Accuracy test for <code>setLog(Log log)</code>.
     *
     * Field "log" should be set and got correctly.
     */
    public void testSetLog_Accuracy() {
        t.setLog(log);
        assertSame(log, t.getLog());
    }
}
