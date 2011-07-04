/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.retrievers;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.reg.profilecompleteness.filter.MockLog;

/**
 * Unit tests for <code>JiraUserIdRetriever</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class JiraUserIdRetrieverTest extends TestCase {

    /**
     * The predefined user id used for testing.
     */
    private static final Long USER_ID = 123L;

    /**
     * A valid user session key to used in tests.
     */
    private static final String USER_SESSION_KEY = "user_session_key";

    /**
     * Represents the <code>JiraUserIdRetriever</code> instance used to test against.
     */
    private JiraUserIdRetriever t;

    /**
     * Represents the <code>Log</code> instance used to test against.
     */
    private MockLog log;

    /**
     * Represents the <code>HttpServletRequest</code> instance used to test against.
     */
    private HttpServletRequest request;

    /**
     * Sets up the fixture. This method is called before a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void setUp() throws Exception {
        t = new JiraUserIdRetriever();
        log = new MockLog();
        request = new MockHttpServletRequest();
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
    }

    /**
     * Accuracy test for constructor <code>JiraUserIdRetriever()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * The valid user id will be returned if the session contains it.
     */
    public void testGetUserId_Accuracy1() {
        t.setLog(log);
        t.setUserSessionKey(USER_SESSION_KEY);
        request.getSession().setAttribute(USER_SESSION_KEY, USER_ID);
        Long userId = t.getUserId(request);
        assertEquals(USER_ID, userId);

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {JiraUserIdRetriever.getUserId}]"));
        assertTrue(logMessage
            .contains("[Exiting method {JiraUserIdRetriever.getUserId}]"));
        assertTrue(logMessage.contains("[Output parameter {" + USER_ID + "}]"));
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null will be returned if the session doesn't contain a user id.
     */
    public void testGetUserId_Accuracy2() {
        t.setLog(log);
        t.setUserSessionKey(USER_SESSION_KEY);
        // set userId in session to null
        request.getSession().setAttribute(USER_SESSION_KEY, null);
        Long userId = t.getUserId(request);
        assertNull(userId);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null will be returned if the session doesn't contain a valid Long type of user id.
     */
    public void testGetUserId_Accuracy3() {
        t.setLog(log);
        t.setUserSessionKey(USER_SESSION_KEY);
        request.getSession().setAttribute(USER_SESSION_KEY, "this is not Long");
        Long userId = t.getUserId(request);
        assertNull(userId);
    }

    /**
     * Failure test for method <code>getUserId(HttpServletRequest request)</code>.
     *
     * If request is null, <code>IllegalArgumentException</code> will be thrown
     */
    public void testGetUserId_Failure_IllegalArgumentException() {
        t.setLog(log);
        try {
            t.getUserId(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
            System.out.println(e.toString());
        }
    }

}
