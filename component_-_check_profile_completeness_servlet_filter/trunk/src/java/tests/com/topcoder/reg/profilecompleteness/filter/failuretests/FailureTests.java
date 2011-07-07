/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>Aggregates all failure tests in this component.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class FailureTests {

    /**
     * Aggregate all failure tests.
     *
     * @return the tests suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(BaseProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(BaseUserIdRetrieverFailureTests.suite());
        suite.addTest(CheckProfileCompletenessFilterFailureTests.suite());
        suite.addTest(CompetitionProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(CopilotProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(DefaultCheckProfileCompletenessProcessorFailureTests.suite());
        suite.addTest(DirectProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(DirectUserIdRetrieverFailureTests.suite());
        suite.addTest(ForumProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(ForumUserIdRetrieverFailureTests.suite());
        suite.addTest(JiraProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(JiraUserIdRetrieverFailureTests.suite());
        suite.addTest(OnlineReviewProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(OnlineReviewUserIdRetrieverFailureTests.suite());
        suite.addTest(StudioProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(StudioUserIdRetrieverFailureTests.suite());
        suite.addTest(SVNProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(TCSiteUserIdRetrieverFailureTests.suite());
        suite.addTest(VMProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(WikiProfileCompletenessCheckerFailureTests.suite());
        suite.addTest(WikiUserIdRetrieverFailureTests.suite());

        return suite;
    }
}
