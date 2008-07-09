/*
 * Copyright (C) 008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging.persistence.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure tests for TC Bulletin.
 * 
 * @author superZZ
 * @version 2.0
 */
public class FailureTests extends TestCase {
    /**
     * Runs all failure test
     * 
     * @return instance of Test
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(InformixMessageBoardPersistenceTests.class);
        suite.addTestSuite(MessageBoardTests.class);
        suite.addTestSuite(MessageTests.class);
        suite.addTestSuite(ResponseTests.class);
        suite.addTestSuite(ThreadTests.class);

        return suite;
    }
}
