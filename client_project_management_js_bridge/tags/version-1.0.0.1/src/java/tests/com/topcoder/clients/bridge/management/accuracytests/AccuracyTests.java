/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.clients.bridge.management.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * Aggregate all Accuracy test cases.
     *
     * @return the suite test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AjaxBridgeServletAccuracyTest.class);
        return suite;
    }

}
