/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.accuracytests.retrievers;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.jivesoftware.base.AuthToken;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.ForumUserIdRetriever;
import com.topcoder.util.log.basic.BasicLogFactory;


/**
 * <p>
 * Accuracy test for {@link ForumUserIdRetriever}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class ForumUserIdRetrieverAccuracyTest {
    /**
     * The ForumUserIdRetriever instance to test against.
     */
    private ForumUserIdRetriever retriever;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ForumUserIdRetrieverAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Before
    public void setUp() throws Exception {
        retriever = new ForumUserIdRetriever();
        retriever.setLog(new BasicLogFactory().createLog("test"));
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @After
    public void tearDown() throws Exception {
        retriever = null;
    }

    /**
     * <p>
     * Tests ForumUserIdRetriever constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("ForumUserIdRetriever is not instantiated", retriever);
    }

    /**
     * <p>
     * Tests {@link ForumUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when an AuthToken
     * instance is stored in user session key attribute.
     * </p>
     * <p>
     * Expects the correct user id is returned.
     * </p>
     */
    @Test
    public void testGetUserId1() {
        retriever.setUserSessionKey("key");

        // create an AuthToken
        AuthToken authToken = new AuthToken() {
            public boolean isAnonymous() {
                return true;
            }

            public long getUserID() {
                return 2;
            }
        };

        HttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("key", authToken);

        Long userId = retriever.getUserId(request);

        // verify it
        Assert.assertNotNull("The user id should exist", userId);
        Assert.assertEquals("Incorrect user id", authToken.getUserID(), userId.longValue());
    }

    /**
     * <p>
     * Tests {@link ForumUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when an AuthToken
     * instance is not stored in user session key attribute.
     * </p>
     * <p>
     * Expects null is returned.
     * </p>
     */
    @Test
    public void testGetUserId2() {
        retriever.setUserSessionKey("key");

        HttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("key", null);

        Long userId = retriever.getUserId(request);

        // verify it
        Assert.assertNull("The user id should not exist", userId);
    }

    /**
     * <p>
     * Tests {@link ForumUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when another type
     * of instance is stored in user session key attribute.
     * </p>
     * <p>
     * Expects null is returned.
     * </p>
     */
    @Test
    public void testGetUserId3() {
        retriever.setUserSessionKey("key");

        HttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("key", new Integer(1));

        Long userId = retriever.getUserId(request);

        // verify it
        Assert.assertNull("The user id should not exist", userId);
    }
}
