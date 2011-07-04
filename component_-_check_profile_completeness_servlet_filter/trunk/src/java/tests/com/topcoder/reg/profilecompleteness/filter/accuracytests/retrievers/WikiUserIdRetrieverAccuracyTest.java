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

import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.WikiUserIdRetriever;
import com.topcoder.shared.security.Persistor;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.security.User;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.web.common.security.SessionPersistor;


/**
 * <p>
 * Accuracy test for {@link WikiUserIdRetriever}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class WikiUserIdRetrieverAccuracyTest {
    /**
     * The WikiUserIdRetriever instance to test against.
     */
    private WikiUserIdRetriever retriever;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(WikiUserIdRetrieverAccuracyTest.class);
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
        retriever = new WikiUserIdRetriever();
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
     * Tests WikiUserIdRetriever constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("WikiUserIdRetriever is not instantiated", retriever);
    }

    /**
     * <p>
     * Tests {@link WikiUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} when a user is logged.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testGetUserId1() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest();
        // Create a Persistor so we can persist a "logged" user
        Persistor persistor = new SessionPersistor(request.getSession());
        User user = new SimpleUser(2, "user", "password");
        persistor.setObject("user_obj", user);

        Long userId = retriever.getUserId(request);

        Assert.assertNotNull("The user id should exist for logged user", userId);
        Assert.assertEquals("Incorrect user id", user.getId(), userId.longValue());
    }

    /**
     * <p>
     * Tests {@link WikiUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} when a user is not
     * logged.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testGetUserId2() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest();

        Long userId = retriever.getUserId(request);

        Assert.assertNull("The user id should not exist for anonymous user", userId);
    }
}
