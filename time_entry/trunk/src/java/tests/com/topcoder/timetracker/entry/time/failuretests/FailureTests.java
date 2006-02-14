/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
 package com.topcoder.timetracker.entry.time.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        //suite.addTest(XXX.suite());
        suite.addTestSuite(TaskTypeDAOFailureTests.class);
        suite.addTestSuite(TimeEntryDAOFailureTests.class);
        suite.addTestSuite(TimeStatusDAOFailureTests.class);
        suite.addTestSuite(DAOFactoryFailureTests.class);
        return suite;
    }
    

}
