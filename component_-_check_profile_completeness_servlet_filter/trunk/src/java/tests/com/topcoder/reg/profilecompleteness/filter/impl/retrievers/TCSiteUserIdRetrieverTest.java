/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.retrievers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.reg.profilecompleteness.filter.MockLog;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.security.User;
import com.topcoder.web.common.security.BasicAuthentication;

/**
 * Unit tests for <code>TCSiteUserIdRetriever</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class TCSiteUserIdRetrieverTest extends TestCase {

    /**
     * A valid user session key to used in tests.
     */
    private static final String USER_SESSION_KEY = "user_session_key";

    /**
     * Represents the <code>TCSiteUserIdRetriever</code> instance used to test against.
     */
    private TCSiteUserIdRetriever t;

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
        t = new TCSiteUserIdRetriever();
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
        log = null;
        request = null;
    }

    /**
     * Accuracy test for constructor <code>TCSiteUserIdRetriever()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * The valid user id will be got if the session contains a valid
     * <code>BasicAuthentication</code>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetUserId_Accuracy1() throws Exception {
        t.setLog(log);
        t.setUserSessionKey(USER_SESSION_KEY);

        BasicAuthentication auth = mock(BasicAuthentication.class);
        User user = mock(User.class);
        when(auth.getActiveUser()).thenReturn(user);
        when(user.getId()).thenReturn(1L);
        request.getSession().setAttribute(USER_SESSION_KEY, auth);
        Long userId = t.getUserId(request);
        assertNotNull(userId);

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {TCSiteUserIdRetriever.getUserId}]"));
        assertTrue(logMessage
            .contains("[Exiting method {TCSiteUserIdRetriever.getUserId}]"));
        assertTrue(logMessage.contains("[Output parameter {1}]"));
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null will be got if the session doesn't contain a <code>BasicAuthentication</code>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetUserId_Accuracy2() throws Exception {
        t.setLog(log);
        t.setUserSessionKey(USER_SESSION_KEY);
        // set to null
        request.getSession().setAttribute(USER_SESSION_KEY, null);
        Long userId = t.getUserId(request);
        assertNull(userId);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null will be got if the session contains a <code>BasicAuthentication</code> which will return
     * a anonymous user.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetUserId_Accuracy3() throws Exception {
        t.setLog(log);
        t.setUserSessionKey(USER_SESSION_KEY);
        BasicAuthentication auth = mock(BasicAuthentication.class);
        when(auth.getActiveUser()).thenReturn(SimpleUser.createGuest());
        request.getSession().setAttribute(USER_SESSION_KEY, auth);

        Long userId = t.getUserId(request);
        assertNull(userId);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null will be got if the session contains an object other than
     * <code>BasicAuthentication</code>.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetUserId_Accuracy4() throws Exception {
        t.setLog(log);
        t.setUserSessionKey(USER_SESSION_KEY);
        request.getSession().setAttribute(USER_SESSION_KEY, "not BasicAuthentication");
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
