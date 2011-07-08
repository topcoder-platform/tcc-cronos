/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests;

import com.topcoder.web.tc.accuracytests.action.SearchContestsManagerActionAccuracyTest;
import com.topcoder.web.tc.accuracytests.action.UpcomingContestsManagerActionAccuracyTest;
import com.topcoder.web.tc.accuracytests.dto.AbstractContestDTOAccuracyTest;
import com.topcoder.web.tc.accuracytests.dto.AbstractContestsFilterAccuracyTest;
import com.topcoder.web.tc.accuracytests.dto.ContestDTOAccuracyTest;
import com.topcoder.web.tc.accuracytests.dto.ContestNameEntityAccuracyTest;
import com.topcoder.web.tc.accuracytests.dto.ContestsFilterAccuracyTest;
import com.topcoder.web.tc.accuracytests.dto.ReviewOpportunitiesFilterAccuracyTest;
import com.topcoder.web.tc.accuracytests.dto.ReviewOpportunityDTOAccuracyTest;
import com.topcoder.web.tc.accuracytests.dto.UpcomingContestDTOAccuracyTest;
import com.topcoder.web.tc.accuracytests.dto.UpcomingContestsFilterAccuracyTest;
import com.topcoder.web.tc.accuracytests.implement.ReviewOpportunitiesManagerImplAccuracyTest;
import com.topcoder.web.tc.accuracytests.implement.SearchContestsManagerImplAccuracyTest;
import com.topcoder.web.tc.accuracytests.implement.UpcomingContestsManagerImplAccuracyTest;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * Returns a test suite containing all unit test cases.
     * </p>
     *
     * @return a test suite containing all unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(new JUnit4TestAdapter(ContestsServiceManagerExceptionAccuracyTest.class));

        suite.addTest(new JUnit4TestAdapter(AbstractContestDTOAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(AbstractContestsFilterAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(ContestDTOAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(ContestNameEntityAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(ContestsFilterAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(ReviewOpportunitiesFilterAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(ReviewOpportunityDTOAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(UpcomingContestDTOAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(UpcomingContestsFilterAccuracyTest.class));

        suite.addTest(new JUnit4TestAdapter(ReviewOpportunitiesManagerImplAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(UpcomingContestsManagerImplAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(SearchContestsManagerImplAccuracyTest.class));

        suite.addTest(new JUnit4TestAdapter(UpcomingContestsManagerActionAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(ReviewOpportunitiesManagerImplAccuracyTest.class));
        suite.addTest(new JUnit4TestAdapter(SearchContestsManagerActionAccuracyTest.class));

        return suite;
    }
}