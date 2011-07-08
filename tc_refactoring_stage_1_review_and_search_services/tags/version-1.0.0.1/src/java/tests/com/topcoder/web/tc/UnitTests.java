/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc;

import com.topcoder.web.tc.action.ReviewOpportunitiesManagerActionTest;
import com.topcoder.web.tc.action.SearchContestsManagerActionTest;
import com.topcoder.web.tc.action.UpcomingContestsManagerActionTest;
import com.topcoder.web.tc.dto.AbstractContestDTOTest;
import com.topcoder.web.tc.dto.AbstractContestsFilterTest;
import com.topcoder.web.tc.dto.ContestDTOTest;
import com.topcoder.web.tc.dto.ContestNameEntityTest;
import com.topcoder.web.tc.dto.ContestsFilterTest;
import com.topcoder.web.tc.dto.ReviewOpportunitiesFilterTest;
import com.topcoder.web.tc.dto.ReviewOpportunityDTOTest;
import com.topcoder.web.tc.dto.UpcomingContestDTOTest;
import com.topcoder.web.tc.dto.UpcomingContestsFilterTest;
import com.topcoder.web.tc.implement.AbstractManagerImplTest;
import com.topcoder.web.tc.implement.ReviewOpportunitiesManagerImplTest;
import com.topcoder.web.tc.implement.SearchContestsManagerImplTest;
import com.topcoder.web.tc.implement.UpcomingContestsManagerImplTest;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all unit test cases.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Returns a test suite containing all unit test cases.
     * </p>
     * @return a test suite containing all unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // helper
        suite.addTest(new JUnit4TestAdapter(ServicesHelperTest.class));

        // exceptions
        suite.addTest(new JUnit4TestAdapter(ContestsServiceManagerExceptionTest.class));

        // DTOs & filters
        suite.addTest(new JUnit4TestAdapter(ContestNameEntityTest.class));
        suite.addTest(new JUnit4TestAdapter(AbstractContestDTOTest.class));
        suite.addTest(new JUnit4TestAdapter(ContestDTOTest.class));
        suite.addTest(new JUnit4TestAdapter(ReviewOpportunityDTOTest.class));
        suite.addTest(new JUnit4TestAdapter(UpcomingContestDTOTest.class));
        suite.addTest(new JUnit4TestAdapter(AbstractContestsFilterTest.class));
        suite.addTest(new JUnit4TestAdapter(ContestsFilterTest.class));
        suite.addTest(new JUnit4TestAdapter(ReviewOpportunitiesFilterTest.class));
        suite.addTest(new JUnit4TestAdapter(UpcomingContestsFilterTest.class));

        // managers
        suite.addTest(new JUnit4TestAdapter(AbstractManagerImplTest.class));
        suite.addTest(new JUnit4TestAdapter(SearchContestsManagerImplTest.class));
        suite.addTest(new JUnit4TestAdapter(UpcomingContestsManagerImplTest.class));
        suite.addTest(new JUnit4TestAdapter(ReviewOpportunitiesManagerImplTest.class));

        // actions
        suite.addTestSuite(ReviewOpportunitiesManagerActionTest.class);
        suite.addTestSuite(SearchContestsManagerActionTest.class);
        suite.addTestSuite(UpcomingContestsManagerActionTest.class);

        // demo
        suite.addTest(new JUnit4TestAdapter(DemoTest.class));

        return suite;
    }
}
