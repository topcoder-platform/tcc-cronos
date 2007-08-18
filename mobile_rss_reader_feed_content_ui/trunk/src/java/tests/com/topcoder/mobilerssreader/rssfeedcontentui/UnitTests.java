/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.mobilerssreader.rssfeedcontentui;

import com.topcoder.mobilerssreader.rssfeedcontentui.accuracytests.AccuracyTests;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * The test suite.
     * 
     * @return test suite.
     */
    public Test suite() {
        final TestSuite suite = new TestSuite();

        // suite.addTest(new ReadFeedEntryFormTest().suite());
        // suite.addTest(new ReplyFeedEntryFormTest().suite());
        // suite.addTest(new ViewFeedListTest().suite());
        suite.addTest(new AccuracyTests().suite());
        return suite;
    }

}
