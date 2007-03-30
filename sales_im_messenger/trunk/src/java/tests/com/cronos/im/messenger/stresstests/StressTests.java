/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Aggregates all stress tests.
     * </p>
     * @return test suite aggregating all stress tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ChatMessageFormatterImplStressTests.class);
        suite.addTestSuite(ChatMessageTrackerImplStressTests.class);
        suite.addTestSuite(MessengerImplStressTests.class);
        return suite;
    }
}
