/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;



/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * The stress test case suite.
     * @return the stress test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(BaseDBRetrievalStressTest.suite());
        suite.addTest(DBProjectRetrievalStressTest.suite());
        suite.addTest(DBUserRetrievalStressTest.suite());

        return suite;
    }
}
