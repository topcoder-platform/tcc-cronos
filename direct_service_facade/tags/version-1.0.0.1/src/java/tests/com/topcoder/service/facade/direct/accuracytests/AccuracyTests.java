/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.facade.direct.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AccuracyTestContestPlan.class);
        suite.addTestSuite(AccuracyTestContestPrize.class);
        suite.addTestSuite(AccuracyTestContestReceiptData.class);
        suite.addTestSuite(AccuracyTestContestSchedule.class);
        suite.addTestSuite(AccuracyTestContestScheduleExtension.class);
        suite.addTestSuite(AccuracyTestProjectBudget.class);
        suite.addTestSuite(AccuracyTestSpecReviewState.class);
        suite.addTestSuite(AccuracyTestDirectServiceFacadeBean.class);
        return suite;
    }

}
