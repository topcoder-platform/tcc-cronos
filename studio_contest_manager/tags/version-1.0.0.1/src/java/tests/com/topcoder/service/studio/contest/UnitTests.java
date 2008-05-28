/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import com.topcoder.service.studio.contest.bean.ContestManagerBeanUnitTest;
import com.topcoder.service.studio.contest.bean.ContestManagerBeanUnitTest2;
import com.topcoder.service.studio.contest.bean.ContestManagerBeanUnitTest3;
import com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManagerUnitTest;
import com.topcoder.service.studio.contest.documentcontentservers.InvalidRequestExceptionUnitTest;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServerUnitTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>Gets the suite of all the unit test cases.</p>
     *
     * @return the suite of all the unit test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(ContestConfigurationExceptionUnitTest.suite());
        suite.addTest(ContestManagementExceptionUnitTest.suite());

        suite.addTest(ContestStatusTransitionExceptionUnitTest.suite());
        suite.addTest(DocumentContentManagementExceptionUnitTest.suite());

        suite.addTest(EntityAlreadyExistsExceptionUnitTest.suite());
        suite.addTest(EntityNotFoundExceptionUnitTest.suite());

        suite.addTest(ContestManagerBeanUnitTest.suite());
        suite.addTest(ContestManagerBeanUnitTest2.suite());
        suite.addTest(ContestManagerBeanUnitTest3.suite());
        suite.addTest(SocketDocumentContentManagerUnitTest.suite());
        suite.addTest(InvalidRequestExceptionUnitTest.suite());

        suite.addTest(SocketDocumentContentServerUnitTest.suite());
        return suite;
    }
}
