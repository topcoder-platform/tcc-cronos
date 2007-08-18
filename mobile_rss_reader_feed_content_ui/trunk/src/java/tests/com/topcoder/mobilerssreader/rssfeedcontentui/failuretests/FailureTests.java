/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui.failuretests;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author iRabbit
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Creates a test suite containing all J2MEUnit tests.
     *
     * @return the test suite
     */
    public Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(new ReadFeedEntryFormFailureTest().suite());
        suite.addTest(new ReplyFeedEntryFormFailureTest().suite());
        suite.addTest(new ViewFeedListFailureTest().suite());

        return suite;
    }
}
