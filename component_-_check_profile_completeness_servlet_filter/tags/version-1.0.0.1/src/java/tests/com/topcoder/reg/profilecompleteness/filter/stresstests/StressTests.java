/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Returns stress tests together.
     * @return all stress tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ProfileCompletenessCheckersTest.suite());
        return suite;
    }
}
