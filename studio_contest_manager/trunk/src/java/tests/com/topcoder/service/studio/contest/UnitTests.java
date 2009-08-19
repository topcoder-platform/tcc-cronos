/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.bean.ContestManagerBeanUnitTest1;
import com.topcoder.service.studio.contest.bean.ContestManagerBeanUnitTest2;
import com.topcoder.service.studio.contest.bean.ContestManagerBeanUnitTest3;
import com.topcoder.service.studio.contest.bean.ContestManagerBeanUnitTest5;
import com.topcoder.service.studio.contest.bean.HelperTest;
import com.topcoder.service.studio.contest.documentcontentmanagers.FileSystemDocumentContentManagerUnitTest;
import com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManagerUnitTest;
import com.topcoder.service.studio.contest.documentcontentservers.InvalidRequestExceptionUnitTest;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServerUnitTest;
import com.topcoder.service.studio.contest.utils.ContestFilterFactoryTest;
import com.topcoder.service.studio.contest.utils.FilterToSqlConverterTest;

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
     * Gets the suite of all the unit test cases.
     * </p>
     *
     * @return the suite of all the unit test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // 1.3 tests
        suite.addTest(ContestManagerBeanUnitTest5.suite());

        // 1.1 tests
        suite.addTest(ContestFilterFactoryTest.suite());
        suite.addTest(FilterToSqlConverterTest.suite());

        // fix
        suite.addTest(HelperTest.suite());

        // 1.0 tests
        suite.addTest(ContestConfigurationExceptionUnitTest.suite());
        suite.addTest(ContestManagementExceptionUnitTest.suite());

        suite.addTest(ContestStatusTransitionExceptionUnitTest.suite());
        suite.addTest(DocumentContentManagementExceptionUnitTest.suite());

        suite.addTest(EntityAlreadyExistsExceptionUnitTest.suite());
        suite.addTest(EntityNotFoundExceptionUnitTest.suite());

        suite.addTest(ContestManagerBeanUnitTest1.suite());
        suite.addTest(ContestManagerBeanUnitTest2.suite());
        suite.addTest(ContestManagerBeanUnitTest3.suite());

        suite.addTest(InvalidRequestExceptionUnitTest.suite());
        suite.addTest(FileSystemDocumentContentManagerUnitTest.suite());
        suite.addTest(SocketDocumentContentManagerUnitTest.suite());
        suite.addTest(SocketDocumentContentServerUnitTest.suite());

        return suite;
    }
}
