/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * @author orange_cloud
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Returns all stress tests together.
     * @return all stress tests in single suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ConcurrentTest.suite());
        return suite;
    }
}
