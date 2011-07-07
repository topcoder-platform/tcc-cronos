/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.accuracytests;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessFilter;
import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessProcessor;
import com.topcoder.reg.profilecompleteness.filter.TestHelper;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;


/**
 * <p>
 * Accuracy test for {@link CheckProfileCompletenessFilter}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class CheckProfileCompletenessFilterAccuracyTest {
    /**
     * The CheckProfileCompletenessFilter instance to test against.
     */
    private CheckProfileCompletenessFilter filter;

    /**
     * The FilterConfig instance used for testing.
     */
    private FilterConfig filterConfig;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CheckProfileCompletenessFilterAccuracyTest.class);
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
        filterConfig = new MockFilterConfig();
        ((MockFilterConfig) filterConfig).addInitParameter("configurationFileName",
            "accuracy/applicationContext.xml");

        filter = new CheckProfileCompletenessFilter();
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
        filter = null;
        filterConfig = null;
    }

    /**
     * <p>
     * Tests CheckProfileCompletenessFilter constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("CheckProfileCompletenessFilter is not instantiated", filter);
    }

    /**
     * <p>
     * Tests {@link CheckProfileCompletenessFilter#init(javax.servlet.FilterConfig)}.
     * </p>
     * <p>
     * Expects the filter is configured properly. No exception is thrown by Spring when intializing the filter.
     * </p>
     */
    @Test
    public void testInit() {
        filter.init(filterConfig);
    }

    /**
     * <p>
     * Tests CheckProfileCompletenessFilter#doFilter method when the user already has complete profile.
     * </p>
     * <p>
     * Expects the chain continues to the next filter in the chain.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testDoFilter1() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest("POST", "/register");
        request.getSession().setAttribute("userId", new Long(2));

        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);

        filter.init(filterConfig);
        CheckProfileCompletenessProcessor processor = (CheckProfileCompletenessProcessor) TestHelper.getField(
            filter, "checkProfileCompletenessProcessor");
        UserDAO dao = (UserDAO) TestHelper.getField(processor, "userDAO");
        Mockito.doReturn(AccuracyTestHelper.createUser()).when(dao).find(2L);
        filter.doFilter(request, response, chain);

        // verify if continue to the next filter if any
        Mockito.verify(chain).doFilter(request, response);
    }

    /**
     * <p>
     * Tests CheckProfileCompletenessFilter#doFilter method when the user does not have complete profile.
     * </p>
     * <p>
     * Expects the request is redirected to complete data URI.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testDoFilter2() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest("POST", "/register");
        request.getSession().setAttribute("userId", new Long(2));

        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);

        filter.init(filterConfig);
        CheckProfileCompletenessProcessor processor = (CheckProfileCompletenessProcessor) TestHelper.getField(
            filter, "checkProfileCompletenessProcessor");
        UserDAO dao = (UserDAO) TestHelper.getField(processor, "userDAO");
        Mockito.doReturn(new User()).when(dao).find(2L);

        filter.doFilter(request, response, chain);

        // verify if requestURI stored in the session
        Object requestURISessionKey = request.getSession().getAttribute("myKey");
        Assert.assertEquals("Incorrect requestURI", request.getRequestURI(), requestURISessionKey);

        String forwardedURI = ((MockHttpServletResponse) response).getForwardedUrl();

        // verify if it was forwarded
        Assert.assertEquals("Page is not redirected to complete page", "/fill", forwardedURI);
    }

    /**
     * <p>
     * Tests {@link CheckProfileCompletenessFilter#destroy()} method.
     * </p>
     * <p>
     * Expects no exception is thrown.
     * </p>
     */
    @Test
    public void testDestroy() {
        filter.init(filterConfig);
        filter.destroy();
    }

}
