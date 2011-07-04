/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.retrievers;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import com.jivesoftware.base.AuthToken;
import com.topcoder.reg.profilecompleteness.filter.MockLog;

/**
 * Unit tests for <code>ForumUserIdRetriever</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class ForumUserIdRetrieverTest extends TestCase {

    /**
     * A valid user session key to used in tests.
     */
    private static final String USER_SESSION_KEY = "user_session_key";

    /**
     * The user id which authToken will return.
     */
    private static final Long USER_ID = 123L;

    /**
     * Represents the <code>ForumUserIdRetriever</code> instance used to test against.
     */
    private ForumUserIdRetriever t;

    /**
     * Represents the <code>Log</code> instance used to test against.
     */
    private MockLog log;

    /**
     * Represents the <code>AuthToken</code> instance used to test against.
     */
    private AuthToken authToken;

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
        t = new ForumUserIdRetriever();
        log = new MockLog();
        request = new MockHttpServletRequest();
        authToken = new AuthToken() {
            public long getUserID() {
                // return the defined user id for checking
                return USER_ID;
            }

            public boolean isAnonymous() {
                return false;
            }
        };
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
        request = null;
        authToken = null;
    }

    /**
     * Accuracy test for constructor <code>ForumUserIdRetriever()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * The correct user id should be got, if the session contains a valid AuthToken.
     */
    public void testGetUserId_Accuracy1() {
        t.setUserSessionKey(USER_SESSION_KEY);
        t.setLog(log);
        request.getSession().setAttribute(USER_SESSION_KEY, authToken);
        Long userId = t.getUserId(request);
        assertEquals(USER_ID, userId);

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {ForumUserIdRetriever.getUserId}]"));
        assertTrue(logMessage
            .contains("[Exiting method {ForumUserIdRetriever.getUserId}]"));
        assertTrue(logMessage.contains("[Output parameter {" + USER_ID + "}]"));
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null should be got, if the session doesn't contain a valid AuthToken.
     */
    public void testGetUserId_Accuracy2() {
        t.setUserSessionKey(USER_SESSION_KEY);
        t.setLog(log);
        request.getSession().setAttribute(USER_SESSION_KEY, null);
        Long userId = t.getUserId(request);
        assertNull(userId);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null should be got, if the session contain an object which is not an instance of AuthToken
     * under the "User session key". No exception thrown.
     */
    public void testGetUserId_Accuracy3() {
        t.setUserSessionKey(USER_SESSION_KEY);
        t.setLog(log);
        // the value is not an instance of AuthToken
        request.getSession().setAttribute(USER_SESSION_KEY, "invalid type");
        Long userId = t.getUserId(request);
        assertNull(userId);
    }

    /**
     * Failure test for method <code>getUserId0(HttpServletRequest request)</code>.
     *
     * If request is null, <code>IllegalArgumentException</code> will be thrown
     */
    public void testGetUserId0_Failure_IllegalArgumentException() {
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
