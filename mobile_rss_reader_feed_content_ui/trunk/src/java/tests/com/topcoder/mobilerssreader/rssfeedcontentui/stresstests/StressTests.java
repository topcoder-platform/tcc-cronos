/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui.stresstests;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Returns the test suite.
     * @return the test suite.
     */
    public Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new ViewFeedListTest().suite());
        suite.addTest(new ReadFeedEntryFormTest().suite());
        suite.addTest(new ReplyFeedEntryFormTest().suite());
        return suite;
    }
}
