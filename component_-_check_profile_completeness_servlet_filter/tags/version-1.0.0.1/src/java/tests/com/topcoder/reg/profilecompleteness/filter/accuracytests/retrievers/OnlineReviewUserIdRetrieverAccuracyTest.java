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

import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.OnlineReviewUserIdRetriever;
import com.topcoder.util.log.basic.BasicLogFactory;


/**
 * <p>
 * Accuracy test for {@link OnlineReviewUserIdRetriever}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class OnlineReviewUserIdRetrieverAccuracyTest {
    /**
     * The OnlineReviewUserIdRetriever instance to test against.
     */
    private OnlineReviewUserIdRetriever retriever;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(OnlineReviewUserIdRetrieverAccuracyTest.class);
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
        retriever = new OnlineReviewUserIdRetriever();
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
     * Tests OnlineReviewUserIdRetriever constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("OnlineReviewUserIdRetriever is not instantiated", retriever);
    }

    /**
     * <p>
     * Tests {@link OnlineReviewUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when a
     * Long instance is stored in user session key attribute.
     * </p>
     * <p>
     * Expects the correct user id is returned.
     * </p>
     * .
     */
    @Test
    public void testGetUserId1() {
        Long id = new Long(3);
        retriever.setUserSessionKey("key");
        HttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("key", id);

        Long userId = retriever.getUserId(request);

        // verify it
        Assert.assertNotNull("The user id should exist", userId);
        Assert.assertEquals("Incorrect user id", id, userId);
    }

    /**
     * <p>
     * Tests {@link OnlineReviewUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when a
     * Long instance is not stored in user session key attribute.
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
     * Tests {@link OnlineReviewUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when
     * another type of instance is stored in user session key attribute.
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
