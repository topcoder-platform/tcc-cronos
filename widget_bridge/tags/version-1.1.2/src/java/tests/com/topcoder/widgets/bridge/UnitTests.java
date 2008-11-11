/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Aggregates all Unit Tests.
     * </p>
     *
     * @return TestSuite an aggregation of all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // add AjaxBridgeSevlet
        suite.addTestSuite(AjaxBridgeServletTest.class);
        suite.addTestSuite(AjaxBridgeServletUploadedDocumentTest.class);

        return suite;
    }
}
