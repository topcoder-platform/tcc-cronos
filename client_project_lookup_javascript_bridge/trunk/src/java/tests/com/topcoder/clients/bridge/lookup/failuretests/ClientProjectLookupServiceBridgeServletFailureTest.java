/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.bridge.lookup.failuretests;

import static org.easymock.EasyMock.*;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.topcoder.clients.bridge.lookup.ClientProjectLookupServiceBridgeServlet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Failure test cases for class ClientProjectLookupServiceBridgeServlet.<br>
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class ClientProjectLookupServiceBridgeServletFailureTest extends TestCase {

    /**
     * A ClientProjectLookupServiceBridgeServletTest instance for testing.
     */
    private ClientProjectLookupServiceBridgeServlet impl;

    /**
     * A ServletConfig instance for testing.
     */
    private ServletConfig config;

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(ClientProjectLookupServiceBridgeServletFailureTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void setUp() throws Exception {
        impl = new ClientProjectLookupServiceBridgeServlet();
        config = createMock(ServletConfig.class);
        expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                "test_files" + File.separator + "config.properties");
        expect(config.getInitParameter("objectFactoryNamespace")).andReturn("objectFactoryNamespace");
        expect(config.getInitParameter("jsonEncoderKey")).andReturn("jsonEncoderOBKey");
        expect(config.getInitParameter("jsonDecoderKey")).andReturn("jsonDecoderOBKey");
        expect(config.getInitParameter("lookupServiceClientKey")).andReturn("lookupServiceClientOBKey");
        expect(config.getInitParameter("clientStatusServiceClientKey")).andReturn(
                "clientStatusServiceClientOBKey");
        expect(config.getInitParameter("projectStatusServiceClientKey")).andReturn(
                "projectStatusServiceClientOBKey");
        expect(config.getInitParameter("loggerName")).andReturn(null);

        replay(config);

        impl.init(config);
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with no ajaxBridgeConfigFile from servlet config.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitWithNotExistParamAjaxBridgeConfigFile() throws Exception {
        try {
            ServletConfig config = createMock(ServletConfig.class);
            expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                    "test_files" + File.separator + "notParsableConfig.properties");
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with a not parsable config.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitWithUnparsableConfig() throws Exception {
        try {
            ServletConfig config = createMock(ServletConfig.class);
            expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                    "test_files" + File.separator + "notExistConfig.properties");
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with Unrecognized File Type config.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitWithUnrecognizedFileType() throws Exception {
        try {
            ServletConfig config = createMock(ServletConfig.class);
            expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                    "test_files" + File.separator + "notExistConfig.pro");
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with Unrecognized Namespace config.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitWithUnrecognizedNamespace() throws Exception {
        try {
            ServletConfig config = createMock(ServletConfig.class);
            expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                    "test_files" + File.separator + "config_unrecognizedNamespace.properties");
            expect(config.getInitParameter("objectFactoryNamespace")).andReturn("objectFactoryNamespace");
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with invalid config.<br>
     * ClassCastException catches, ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitWithClassCastException() throws Exception {
        try {
            ServletConfig config = createMock(ServletConfig.class);
            expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                    "test_files" + File.separator + "invalidConfig.properties");
            expect(config.getInitParameter("objectFactoryNamespace")).andReturn("objectFactoryNamespace");
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with not exist file.<br>
     * ServletException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitWithNotExistFile() throws Exception {
        try {
            ServletConfig config = createMock(ServletConfig.class);
            expect(config.getInitParameter("ajaxBridgeConfigFile")).andReturn(
                    "test_files" + File.separator + "notExistConfig.properties");
            impl.init(config);
            fail("ServletException should be thrown.");
        } catch (ServletException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>init()</code> with null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitWithNull() throws Exception {
        try {
            impl.init(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
