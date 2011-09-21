/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.stresstests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 * @author wz12
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
    ActionTest.class
)
public class StressTests extends TestCase {
    /**
     * <p>
     * Test suite.
     * </p>
     *
     * @return the test suite
     */
    public static Test suite() {
        return new JUnit4TestAdapter(StressTests.class);
    }
}
