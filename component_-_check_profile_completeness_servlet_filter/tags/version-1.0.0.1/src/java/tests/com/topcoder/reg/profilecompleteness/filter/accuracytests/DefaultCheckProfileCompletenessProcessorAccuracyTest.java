/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.accuracytests;

import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.topcoder.reg.profilecompleteness.filter.impl.DefaultCheckProfileCompletenessProcessor;
import com.topcoder.reg.profilecompleteness.filter.impl.ProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.BaseProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.WikiProfileCompletenessChecker;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.BaseUserIdRetriever;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.OnlineReviewUserIdRetriever;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;


/**
 * <p>
 * Accuracy test for {@link DefaultCheckProfileCompletenessProcessor}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class DefaultCheckProfileCompletenessProcessorAccuracyTest {
    /**
     * The DefaultCheckProfileCompletenessProcessor instance to test against.
     */
    private DefaultCheckProfileCompletenessProcessor processor;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DefaultCheckProfileCompletenessProcessorAccuracyTest.class);
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
        Log log = new BasicLogFactory().createLog("test");
        processor = new DefaultCheckProfileCompletenessProcessor();

        processor.setRequestURISessionKey("myKey");

        processor.setLog(log);
        processor.setMatchUriPatterns(Arrays.asList(".*register.*"));

        BaseUserIdRetriever retriever = new OnlineReviewUserIdRetriever();
        retriever.setUserSessionKey("key");
        retriever.setLog(log);
        processor.setUserIdRetriever(retriever);

        BaseProfileCompletenessChecker checker = new WikiProfileCompletenessChecker();
        checker.setLog(log);
        processor.setProfileCompletenessCheckers(Arrays.asList((ProfileCompletenessChecker) checker));
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
        processor = null;
    }

    /**
     * <p>
     * Tests DefaultCheckProfileCompletenessProcessor constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("DefaultCheckProfileCompletenessProcessor is not instantiated", processor);
    }

    /**
     * <p>
     * Tests DefaultCheckProfileCompletenessProcessor#process method when the user already has complete profile.
     * </p>
     * <p>
     * Expects the chain continues to the next filter in the chain.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testProcess1() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest("POST", "/register");
        request.getSession().setAttribute("key", new Long(2));

        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);

        UserDAO dao = Mockito.mock(UserDAO.class);
        Mockito.doReturn(AccuracyTestHelper.createUser()).when(dao).find(2L);
        processor.setUserDAO(dao);
        processor.process(request, response, chain);

        // verify if continue to the next filter if any
        Mockito.verify(chain).doFilter(request, response);
    }

    /**
     * <p>
     * Tests DefaultCheckProfileCompletenessProcessor#process method when the user does not have complete profile.
     * </p>
     * <p>
     * Expects the request is redirected to complete data URI.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testProcess2() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest("POST", "/register");
        request.getSession().setAttribute("key", new Long(2));

        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);

        UserDAO dao = Mockito.mock(UserDAO.class);
        Mockito.doReturn(new User()).when(dao).find(2L);
        processor.setUserDAO(dao);

        String completeURI = "/fill";
        processor.setCompleteDataURIs(Arrays.asList(completeURI));

        processor.process(request, response, chain);

        // verify if requestURI stored in the session
        Object requestURISessionKey = request.getSession().getAttribute("myKey");
        Assert.assertEquals("Incorrect requestURI", request.getRequestURI(), requestURISessionKey);

        String forwardedURI = ((MockHttpServletResponse) response).getForwardedUrl();

        // verify if it was forwarded
        Assert.assertEquals("Page is not redirected to complete page", completeURI, forwardedURI);
    }

    /**
     * <p>
     * Tests DefaultCheckProfileCompletenessProcessor#process method when the request URI does not match with any configured pattern.
     * </p>
     * <p>
     * Expects the chain continues to the next filter in the chain.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testProcess3() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest("POST", "/contact");
        request.getSession().setAttribute("key", new Long(2));

        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);

        processor.process(request, response, chain);

        // verify if continue to the next filter if any
        Mockito.verify(chain).doFilter(request, response);
    }

    /**
     * <p>
     * Tests DefaultCheckProfileCompletenessProcessor#process method when the user id cannot be retrieved.
     * </p>
     * <p>
     * Expects the chain continues to the next filter in the chain.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testProcess4() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest("POST", "/register");

        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);

        processor.process(request, response, chain);

        // verify if continue to the next filter if any
        Mockito.verify(chain).doFilter(request, response);
    }
}
