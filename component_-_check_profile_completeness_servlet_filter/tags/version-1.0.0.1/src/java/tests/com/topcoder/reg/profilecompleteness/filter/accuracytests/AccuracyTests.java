/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.BaseProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.CompetitionProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.CopilotProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.DirectProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.ForumProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.JiraProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.OnlineReviewProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.SVNProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.StudioProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.VMProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers.WikiProfileCompletenessCheckerAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.retrievers.DirectUserIdRetrieverAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.retrievers.ForumUserIdRetrieverAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.retrievers.JiraUserIdRetrieverAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.retrievers.OnlineReviewUserIdRetrieverAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.retrievers.StudioUserIdRetrieverAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.retrievers.TCSiteUserIdRetrieverAccuracyTest;
import com.topcoder.reg.profilecompleteness.filter.accuracytests.retrievers.WikiUserIdRetrieverAccuracyTest;


/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * Aggregates all Accuracy test cases for this component.
     * </p>
     *
     * @return TestSuite that aggregates all Accuracy test cases for this component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(CheckProfileCompletenessFilterAccuracyTest.suite());

        suite.addTest(DefaultCheckProfileCompletenessProcessorAccuracyTest.suite());

        suite.addTest(BaseProfileCompletenessCheckerAccuracyTest.suite());
        suite.addTest(CompetitionProfileCompletenessCheckerAccuracyTest.suite());
        suite.addTest(CopilotProfileCompletenessCheckerAccuracyTest.suite());
        suite.addTest(DirectProfileCompletenessCheckerAccuracyTest.suite());
        suite.addTest(ForumProfileCompletenessCheckerAccuracyTest.suite());
        suite.addTest(JiraProfileCompletenessCheckerAccuracyTest.suite());
        suite.addTest(OnlineReviewProfileCompletenessCheckerAccuracyTest.suite());
        suite.addTest(StudioProfileCompletenessCheckerAccuracyTest.suite());
        suite.addTest(SVNProfileCompletenessCheckerAccuracyTest.suite());
        suite.addTest(VMProfileCompletenessCheckerAccuracyTest.suite());
        suite.addTest(WikiProfileCompletenessCheckerAccuracyTest.suite());

        suite.addTest(DirectUserIdRetrieverAccuracyTest.suite());
        suite.addTest(ForumUserIdRetrieverAccuracyTest.suite());
        suite.addTest(JiraUserIdRetrieverAccuracyTest.suite());
        suite.addTest(OnlineReviewUserIdRetrieverAccuracyTest.suite());
        suite.addTest(StudioUserIdRetrieverAccuracyTest.suite());
        suite.addTest(TCSiteUserIdRetrieverAccuracyTest.suite());
        suite.addTest(WikiUserIdRetrieverAccuracyTest.suite());

        return suite;
    }
}
