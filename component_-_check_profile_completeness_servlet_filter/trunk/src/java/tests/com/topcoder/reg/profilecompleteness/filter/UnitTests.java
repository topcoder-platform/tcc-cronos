/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.reg.profilecompleteness.filter.impl.DefaultCheckProfileCompletenessProcessorTest;
import com.topcoder.reg.profilecompleteness.filter.impl.ProfileCompletenessCheckerExceptionTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.BaseProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.CompetitionProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.CopilotProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.DirectProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.ForumProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.JiraProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.OnlineReviewProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.SVNProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.StudioProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.VMProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.WikiProfileCompletenessCheckerTest;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.BaseUserIdRetrieverTest;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.DirectUserIdRetrieverTest;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.ForumUserIdRetrieverTest;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.JiraUserIdRetrieverTest;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.OnlineReviewUserIdRetrieverTest;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.StudioUserIdRetrieverTest;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.TCSiteUserIdRetrieverTest;
import com.topcoder.reg.profilecompleteness.filter.impl.retrievers.WikiUserIdRetrieverTest;

/**
 * This test case aggregates all unit test cases.
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * The unit tests of this components.
     *
     * @return the unit test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CheckProfileCompletenessConfigurationExceptionTest.class);
        suite.addTestSuite(CheckProfileCompletenessProcessorExceptionTest.class);
        suite.addTestSuite(BaseProfileCompletenessCheckerTest.class);
        suite.addTestSuite(CompetitionProfileCompletenessCheckerTest.class);
        suite.addTestSuite(CopilotProfileCompletenessCheckerTest.class);
        suite.addTestSuite(DirectProfileCompletenessCheckerTest.class);
        suite.addTestSuite(ForumProfileCompletenessCheckerTest.class);
        suite.addTestSuite(JiraProfileCompletenessCheckerTest.class);
        suite.addTestSuite(OnlineReviewProfileCompletenessCheckerTest.class);
        suite.addTestSuite(StudioProfileCompletenessCheckerTest.class);
        suite.addTestSuite(SVNProfileCompletenessCheckerTest.class);
        suite.addTestSuite(VMProfileCompletenessCheckerTest.class);
        suite.addTestSuite(WikiProfileCompletenessCheckerTest.class);
        suite.addTestSuite(ProfileCompletenessCheckerExceptionTest.class);
        suite.addTestSuite(BaseUserIdRetrieverTest.class);
        suite.addTestSuite(DirectUserIdRetrieverTest.class);
        suite.addTestSuite(ForumUserIdRetrieverTest.class);
        suite.addTestSuite(JiraUserIdRetrieverTest.class);
        suite.addTestSuite(OnlineReviewUserIdRetrieverTest.class);
        suite.addTestSuite(StudioUserIdRetrieverTest.class);
        suite.addTestSuite(TCSiteUserIdRetrieverTest.class);
        suite.addTestSuite(WikiUserIdRetrieverTest.class);
        suite.addTestSuite(HelperTest.class);
        suite.addTestSuite(DefaultCheckProfileCompletenessProcessorTest.class);
        suite.addTestSuite(CheckProfileCompletenessFilterTest.class);
        suite.addTestSuite(Demo.class);
        return suite;
    }
}
