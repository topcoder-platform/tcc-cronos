/**
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.mobilerssreader.rssfeedcontentui.accuracytests;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new ReadFeedEntryFormAccuracyTests().suite());
        suite.addTest(new ReplyFeedEntryFormAccuracyTests().suite());
        suite.addTest(new ViewFeedListAccuracyTests().suite());
        return suite;
    }

}
