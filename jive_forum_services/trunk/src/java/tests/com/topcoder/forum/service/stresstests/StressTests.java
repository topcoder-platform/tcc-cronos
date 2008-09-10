/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.forum.service.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class StressTests extends TestCase {

    /**
     * Test suite.
     * 
     * @return the Test.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(MockJiveForumServiceStressTest.class);
        return suite;
    }
}
