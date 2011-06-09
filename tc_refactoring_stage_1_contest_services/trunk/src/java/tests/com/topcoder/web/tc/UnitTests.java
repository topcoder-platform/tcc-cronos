/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.tc.action.ActiveContestsManagerActionTest;
import com.topcoder.web.tc.action.BaseJSONParameterActionTest;
import com.topcoder.web.tc.action.CategoriesManagerActionTest;
import com.topcoder.web.tc.action.ContestServicesActionExceptionTest;
import com.topcoder.web.tc.action.ContestStatusManagerActionTest;
import com.topcoder.web.tc.action.PastContestsManagerActionTest;
import com.topcoder.web.tc.dto.ActiveContestDTOTest;
import com.topcoder.web.tc.dto.ActiveContestFilterTest;
import com.topcoder.web.tc.dto.BaseContestDTOTest;
import com.topcoder.web.tc.dto.BaseFilterTest;
import com.topcoder.web.tc.dto.BasePrizeFilterTest;
import com.topcoder.web.tc.dto.ContestStatusDTOTest;
import com.topcoder.web.tc.dto.ContestStatusFilterTest;
import com.topcoder.web.tc.dto.DateIntervalSpecificationTest;
import com.topcoder.web.tc.dto.PastContestDTOTest;
import com.topcoder.web.tc.dto.PastContestFilterTest;
import com.topcoder.web.tc.impl.ActiveContestsManagerImplTest;
import com.topcoder.web.tc.impl.CategoriesManagerImplTest;
import com.topcoder.web.tc.impl.ContestStatusManagerImplTest;
import com.topcoder.web.tc.impl.PastContestsManagerImplTest;
import com.topcoder.web.tc.impl.entity.ContestEligibilityTest;
import com.topcoder.web.tc.impl.entity.LookupEntityTest;
import com.topcoder.web.tc.impl.entity.PhaseTypeTest;
import com.topcoder.web.tc.impl.entity.ProjectCatalogLookupTest;
import com.topcoder.web.tc.impl.entity.ProjectCategoryLookupTest;
import com.topcoder.web.tc.impl.entity.ProjectGroupCategoryLookupTest;
import com.topcoder.web.tc.impl.entity.ProjectInfoTest;
import com.topcoder.web.tc.impl.entity.ProjectPhaseTest;
import com.topcoder.web.tc.impl.entity.ProjectResultTest;
import com.topcoder.web.tc.impl.entity.ProjectTest;
import com.topcoder.web.tc.impl.entity.ResourceInfoTest;
import com.topcoder.web.tc.impl.entity.ResourceSubmissionTest;
import com.topcoder.web.tc.impl.entity.ResourceTest;
import com.topcoder.web.tc.impl.entity.SubmissionTest;
import com.topcoder.web.tc.impl.entity.UploadTest;

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
     * <p>
     * All unit test suite.
     * </p>
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(ActiveContestsManagerExceptionTest.suite());
        suite.addTest(CategoriesManagerExceptionTest.suite());
        suite.addTest(ContestServicesConfigurationExceptionTest.suite());
        suite.addTest(ContestStatusManagerExceptionTest.suite());
        suite.addTest(PastContestsManagerExceptionTest.suite());

        suite.addTest(ActiveContestsManagerActionTest.suite());
        suite.addTest(BaseJSONParameterActionTest.suite());
        suite.addTest(CategoriesManagerActionTest.suite());
        suite.addTest(ContestServicesActionExceptionTest.suite());
        suite.addTest(ContestStatusManagerActionTest.suite());
        suite.addTest(PastContestsManagerActionTest.suite());

        suite.addTest(ActiveContestDTOTest.suite());
        suite.addTest(ActiveContestFilterTest.suite());
        suite.addTest(BaseContestDTOTest.suite());
        suite.addTest(BaseFilterTest.suite());
        suite.addTest(BasePrizeFilterTest.suite());
        suite.addTest(ContestStatusDTOTest.suite());
        suite.addTest(ContestStatusFilterTest.suite());
        suite.addTest(DateIntervalSpecificationTest.suite());
        suite.addTest(PastContestDTOTest.suite());
        suite.addTest(PastContestFilterTest.suite());

        suite.addTest(ActiveContestsManagerImplTest.suite());
        suite.addTest(CategoriesManagerImplTest.suite());
        suite.addTest(ContestStatusManagerImplTest.suite());
        suite.addTest(PastContestsManagerImplTest.suite());

        suite.addTest(ContestEligibilityTest.suite());
        suite.addTest(LookupEntityTest.suite());
        suite.addTest(PhaseTypeTest.suite());
        suite.addTest(ProjectCatalogLookupTest.suite());
        suite.addTest(ProjectCategoryLookupTest.suite());
        suite.addTest(ProjectGroupCategoryLookupTest.suite());
        suite.addTest(ProjectInfoTest.suite());
        suite.addTest(ProjectPhaseTest.suite());
        suite.addTest(ProjectResultTest.suite());
        suite.addTest(ProjectTest.suite());
        suite.addTest(ResourceInfoTest.suite());
        suite.addTest(ResourceSubmissionTest.suite());
        suite.addTest(ResourceTest.suite());
        suite.addTest(SubmissionTest.suite());
        suite.addTest(UploadTest.suite());

        suite.addTest(HelperTest.suite());
        suite.addTest(DemoTest.suite());

        return suite;
    }

}
