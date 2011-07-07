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

import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.TCSiteUserIdRetriever;
import com.topcoder.shared.security.Persistor;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.security.User;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;


/**
 * <p>
 * Accuracy test for {@link TCSiteUserIdRetriever}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class TCSiteUserIdRetrieverAccuracyTest {
    /**
     * The TCSiteUserIdRetriever instance to test against.
     */
    private TCSiteUserIdRetriever retriever;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TCSiteUserIdRetrieverAccuracyTest.class);
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
        retriever = new TCSiteUserIdRetriever();
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
     * Tests TCSiteUserIdRetriever constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("TCSiteUserIdRetriever is not instantiated", retriever);
    }

    /**
     * <p>
     * Tests {@link TCSiteUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when a
     * BasicAuthentication instance is stored in user session key attribute and user is active/logged.
     * </p>
     * <p>
     * Expects the correct user id is returned.
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

        BasicAuthentication auth = new BasicAuthentication(persistor, new SimpleRequest(request), null,
            BasicAuthentication.MAIN_SITE);
        request.getSession().setAttribute("key", auth);
        retriever.setUserSessionKey("key");

        Long userId = retriever.getUserId(request);

        // verify it
        Assert.assertNotNull("The user id should exist for logged user", userId);
        Assert.assertEquals("Incorrect user id", user.getId(), userId.longValue());
    }

    /**
     * <p>
     * Tests {@link TCSiteUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when a
     * BasicAuthentication instance is stored in user session key attribute and user is not active/logged.
     * </p>
     * <p>
     * Expects null is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testGetUserId2() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest();

        BasicAuthentication auth = new BasicAuthentication(new SessionPersistor(request.getSession()),
            new SimpleRequest(request), null, BasicAuthentication.MAIN_SITE);
        request.getSession().setAttribute("key", auth);
        retriever.setUserSessionKey("key");

        Long userId = retriever.getUserId(request);

        // verify it
        Assert.assertNull("The user id should not exist for anonymous user", userId);
    }

    /**
     * <p>
     * Tests {@link TCSiteUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when a
     * BasicAuthentication instance is not stored in user session key attribute.
     * </p>
     * <p>
     * Expects null is returned.
     * </p>
     */
    @Test
    public void testGetUserId3() {
        retriever.setUserSessionKey("key");

        HttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("key", null);

        Long userId = retriever.getUserId(request);

        // verify it
        Assert.assertNull("The user id should not exist", userId);
    }

    /**
     * <p>
     * Tests {@link TCSiteUserIdRetriever#getUserId(javax.servlet.http.HttpServletRequest)} method when another
     * type of instance is stored in user session key attribute.
     * </p>
     * <p>
     * Expects null is returned.
     * </p>
     */
    @Test
    public void testGetUserId4() {
        retriever.setUserSessionKey("key");

        HttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("key", new Integer(1));

        Long userId = retriever.getUserId(request);

        // verify it
        Assert.assertNull("The user id should not exist", userId);
    }

}
