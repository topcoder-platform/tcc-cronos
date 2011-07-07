/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.retrievers;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.reg.profilecompleteness.filter.MockLog;
import com.topcoder.shared.security.Persistor;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.security.User;
import com.topcoder.web.common.security.SessionPersistor;

/**
 * Unit tests for <code>WikiUserIdRetriever</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class WikiUserIdRetrieverTest extends TestCase {

    /**
     * Represents the <code>WikiUserIdRetriever</code> instance used to test against.
     */
    private WikiUserIdRetriever t;

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
        t = new WikiUserIdRetriever();
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
     * Accuracy test for constructor <code>WikiUserIdRetriever()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Valid user id should be got, if the underlying <code>LightAuthentication</code> returns valid
     * active user.
     */
    public void testGetUserId_Accuracy1() {
        t.setLog(log);

        Persistor persistor = new SessionPersistor(request.getSession());
        User user = new SimpleUser(2, "user", "password");
        persistor.setObject("user_obj", user);

        Long userId = t.getUserId(request);
        assertNotNull(userId);

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {WikiUserIdRetriever.getUserId}]"));
        assertTrue(logMessage
            .contains("[Exiting method {WikiUserIdRetriever.getUserId}]"));
        assertTrue(logMessage.contains("[Output parameter {2}]"));
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null should be got, if the underlying <code>LightAuthentication</code> returns a null of
     * active user.
     */
    public void testGetUserId_Accuracy2() {
        t.setLog(log);
        request.setAttribute("action", "noUser");
        Long userId = t.getUserId(request);
        assertNull(userId);
    }

    /**
     * Accuracy test for <code>getUserId(HttpServletRequest request)</code>.
     *
     * Null should be got, if the underlying <code>LightAuthentication</code> throws exception.
     */
    public void testGetUserId_Accuracy3() {
        t.setLog(log);
        request.setAttribute("action", "exception");
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
