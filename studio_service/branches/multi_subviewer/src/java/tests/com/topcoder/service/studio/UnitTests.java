/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.service.studio.ejb.ContestNotFoundFaultTest;
import com.topcoder.service.studio.ejb.DocumentNotFoundFaultTest;
import com.topcoder.service.studio.ejb.IllegalArgumentWSFaultTest;
import com.topcoder.service.studio.ejb.PersistenceFaultTest;
import com.topcoder.service.studio.ejb.ProjectNotFoundFaultTest;
import com.topcoder.service.studio.ejb.StatusNotAllowedFaultTest;
import com.topcoder.service.studio.ejb.StatusNotFoundFaultTest;
import com.topcoder.service.studio.ejb.StudioServiceBeanTest;
import com.topcoder.service.studio.ejb.UserNotAuthorizedFaultTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
     * Gathers all unit tests together and return.
     *
     * @return all tests in one suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(MilestonePrizeDataUnitTests.class);
        suite.addTestSuite(ContestSpecificationsDataUnitTests.class);
        suite.addTestSuite(ContestMultiRoundInformationDataUnitTests.class);
        suite.addTestSuite(ContestGeneralInfoDataUnitTests.class);
        suite.addTest(SubmissionDataTest.suite());
        suite.addTest(StudioServiceBeanTest.suite());

        suite.addTest(Demo.suite());
        suite.addTest(ContestDataTest.suite());
        suite.addTest(ContestPayloadTest.suite());
        suite.addTest(ContestStatusDataTest.suite());
        suite.addTest(PrizeDataTest.suite());
        suite.addTest(UploadedDocumentTest.suite());
        suite.addTest(ContestNotFoundFaultTest.suite());
        suite.addTest(DocumentNotFoundFaultTest.suite());
        suite.addTest(IllegalArgumentWSFaultTest.suite());
        suite.addTest(PersistenceFaultTest.suite());
        suite.addTest(ProjectNotFoundFaultTest.suite());
        suite.addTest(StatusNotAllowedFaultTest.suite());
        suite.addTest(StatusNotFoundFaultTest.suite());
        suite.addTest(UserNotAuthorizedFaultTest.suite());
        suite.addTest(StudioServiceExceptionTest.suite());
        suite.addTest(ContestNotFoundExceptionTest.suite());
        suite.addTest(DocumentNotFoundExceptionTest.suite());
        suite.addTest(ProjectNotFoundExceptionTest.suite());
        suite.addTest(StatusNotFoundExceptionTest.suite());
        suite.addTest(StatusNotAllowedExceptionTest.suite());
        suite.addTest(UserNotAuthorizedExceptionTest.suite());
        suite.addTest(PersistenceExceptionTest.suite());
        suite.addTest(IllegalArgumentWSExceptionTest.suite());
        suite.addTest(UtilTest.suite());

        return suite;
    }
}
