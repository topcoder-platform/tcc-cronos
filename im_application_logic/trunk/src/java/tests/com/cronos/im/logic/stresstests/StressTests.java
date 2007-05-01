/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test case aggregates all stress test cases.
 * 
 * @author kaqi072821
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * Aggregates all stress test cases and returns the test suite for them.
     * </p>
     * 
     * @return the test suite of all stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(IMServiceHandlerStressTest.class);
        suite.addTestSuite(MessagePoolDetectorStressTest.class);
        suite.addTestSuite(SessionStatusEventListenerStressTest.class);
        suite.addTestSuite(UserSessionEventListenerStressTest.class);
        suite.addTestSuite(UserStatusEventListenerStressTest.class);
        return suite;
    }
}