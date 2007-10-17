/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.mobilerssreader.rssfeedcontentui;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestSuite;
import com.topcoder.mobilerssreader.rssfeedcontentui.accuracytests.AccuracyTests;
import com.topcoder.mobilerssreader.rssfeedcontentui.failuretests.FailureTests;
import com.topcoder.mobilerssreader.rssfeedcontentui.stresstests.StressTests;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    public Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(new UnitTests().suite());
        
        //accuracy tests
        suite.addTest(new AccuracyTests().suite());
        
        //failure tests
        suite.addTest(new FailureTests().suite());
        
        //stress tests
        suite.addTest(new StressTests().suite());
        
        return suite;
    }

}
