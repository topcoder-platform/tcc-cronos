/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessConfigurationException;
import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessFilter;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * <p>Failure tests for {@link CheckProfileCompletenessFilter} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class CheckProfileCompletenessFilterFailureTests {

    /**
     * <p>Represents the instance of the tested class.</p>
     */
    private CheckProfileCompletenessFilter instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CheckProfileCompletenessFilterFailureTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new CheckProfileCompletenessFilter();
    }

    /**
     * <p>Tests {@link CheckProfileCompletenessFilter#CheckProfileCompletenessFilter()} constructor.</p>
     */
    @Test
    public void testCtor() {

        instance = new CheckProfileCompletenessFilter();
        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests {@link CheckProfileCompletenessFilter#init(FilterConfig)} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testInitFailure1() throws Exception {
        MockFilterConfig filterConfig = new MockFilterConfig();

        instance.init(filterConfig);
    }

    /**
     * <p>Tests {@link CheckProfileCompletenessFilter#init(FilterConfig)} method.</p>
     *
     * <p>{@link CheckProfileCompletenessConfigurationException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = CheckProfileCompletenessConfigurationException.class)
    public void testInitFailure2() throws Exception {
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.addInitParameter("configurationFileName", " ");
        instance.init(filterConfig);
    }

    /**
     * <p>Tests {@link CheckProfileCompletenessFilter# doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)} method.</p>
     *
     * <p>No exception is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test
    public void testDoFilter() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.addInitParameter("configurationFileName", "/failuretests/applicationContext.xml");
        instance.init(filterConfig);

        instance.doFilter(request, response, chain);

        assertEquals("Invalid forward url.", "/error.jsp", response.getForwardedUrl());
    }
}
