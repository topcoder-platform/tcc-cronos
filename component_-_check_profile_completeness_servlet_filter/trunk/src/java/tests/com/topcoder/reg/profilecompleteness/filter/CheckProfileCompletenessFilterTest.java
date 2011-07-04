/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.TestCase;

import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Unit tests for <code>CheckProfileCompletenessFilter</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class CheckProfileCompletenessFilterTest extends TestCase {

    /**
     * Represents the <code>CheckProfileCompletenessFilter</code> instance used to test against.
     */
    private CheckProfileCompletenessFilter t;
    /**
     * Represents the <code>CheckProfileCompletenessFilter</code> used in tests.
     */
    private CheckProfileCompletenessProcessor processor;
    /**
     * Represents the <code>FilterConfig</code> used in tests.
     */
    private MockFilterConfig filterConfig;
    /**
     * Represents the <code>Log</code> used in tests.
     */
    private MockLog log;
    /**
     * Represents the <code>HttpServletRequest</code> used in tests.
     */
    private MockHttpServletRequest request;
    /**
     * Represents the <code>HttpServletResponse</code> used in tests.
     */
    private MockHttpServletResponse response;
    /**
     * Represents the <code>FilterChain</code> used in tests.
     */
    private MockFilterChain chain;

    /**
     * Sets up the fixture. This method is called before a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void setUp() throws Exception {
        t = new CheckProfileCompletenessFilter();
        filterConfig = new MockFilterConfig();
        log = new MockLog();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        chain = new MockFilterChain();
        processor = mock(CheckProfileCompletenessProcessor.class);
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
        filterConfig = null;
        log = null;
        request = null;
        response = null;
        chain = null;
    }

    /**
     * Accuracy test for constructor <code>CheckProfileCompletenessFilter()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
    }

    /**
     * Accuracy test for <code>init(FilterConfig filterConfig)</code>.
     */
    public void testInit_Accuracy() {
        filterConfig.addInitParameter("configurationFileName", "spring.xml");
        t.init(filterConfig);
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException1() {
        filterConfig.addInitParameter("configurationFileName", "non-exist-config.xml");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException2() {
        assertEquals(null, filterConfig.getInitParameter("configurationFileName"));
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException3() {
        filterConfig.addInitParameter("configurationFileName", "  ");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException4() {
        // a directory
        filterConfig.addInitParameter("configurationFileName", "test_files");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException5() {
        // a directory
        filterConfig.addInitParameter("configurationFileName", "invalid/spring0-notxml.xml");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException7() {
        // a directory
        filterConfig.addInitParameter("configurationFileName",
            "invalid/spring2-log-invalidtype.xml");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException8() {
        // a directory
        filterConfig.addInitParameter("configurationFileName",
            "invalid/spring3-log-nonexistclass.xml");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException9() {
        // a directory
        filterConfig.addInitParameter("configurationFileName",
            "invalid/spring4-no-processor.xml");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException10() {
        // a directory
        filterConfig.addInitParameter("configurationFileName",
            "invalid/spring5-invalid-type-processor.xml");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException11() {
        // a directory
        filterConfig.addInitParameter("configurationFileName",
            "invalid/spring6-non-exist-type-processor.xml");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException12() {
        // a directory
        filterConfig.addInitParameter("configurationFileName",
            "invalid/spring7-no-errorPageUri.xml");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException13() {
        // a directory
        filterConfig.addInitParameter("configurationFileName",
            "invalid/spring8-null-errorPageUri.xml");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>init(FilterConfig filterConfig)</code>.
     *
     * If any property was not properly injected,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testInit_Failure_CheckProfileCompletenessConfigurationException14() {
        // a directory
        filterConfig.addInitParameter("configurationFileName",
            "invalid/spring9-empty-errorPageUri.xml");
        try {
            t.init(filterConfig);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Accuracy test for
     * <code>doFilter(ServletRequest request, ServletResponse response, FilterChain chain)</code>.
     *
     * It will call the underlying processor the process the request.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testDoFilter_Accuracy1() throws Exception {
        TestHelper.setField(t, "log", log);
        TestHelper.setField(t, "checkProfileCompletenessProcessor", processor);

        t.doFilter(request, response, chain);

        verify(processor).process(request, response, chain);
        // not forward to error page
        assertEquals(null, response.getForwardedUrl());

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage.contains("Entering method {CheckProfileCompletenessFilter.doFilter}")
            && logMessage.contains("Exiting method {CheckProfileCompletenessFilter.doFilter}"));
    }

    /**
     * Accuracy test for
     * <code>doFilter(ServletRequest request, ServletResponse response, FilterChain chain)</code>.
     *
     * It will forward to error page, if underlying process throws an exception.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testDoFilter_Accuracy2() throws Exception {
        TestHelper.setField(t, "log", log);
        TestHelper.setField(t, "errorPageUri", "/error");
        TestHelper.setField(t, "checkProfileCompletenessProcessor", processor);

        doThrow(new CheckProfileCompletenessProcessorException("test")).when(processor).process(
            request, response, chain);

        t.doFilter(request, response, chain);

        verify(processor).process(request, response, chain);

        // forward to error page
        assertEquals("/error", response.getForwardedUrl());
    }

    /**
     * Accuracy test for <code>destroy()</code>.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testDestroy_Accuracy() throws Exception {
        TestHelper.setField(t, "log", log);
        t.destroy();
        // no exception thrown

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage.contains("Entering method {CheckProfileCompletenessFilter.destroy}")
            && logMessage.contains("Exiting method {CheckProfileCompletenessFilter.destroy}"));
    }
}
