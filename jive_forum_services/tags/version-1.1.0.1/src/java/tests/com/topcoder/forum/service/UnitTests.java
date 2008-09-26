/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.forum.service.ejb.ServiceConfigurationExceptionUnitTest;
import com.topcoder.forum.service.ejb.bean.JiveForumServiceBeanUnitTest;
import com.topcoder.forum.service.ejb.mock.MockJiveForumServiceTest;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>Gets the suite of all the unit test cases.</p>
     *
     * @return the suite of all the unit test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(CategoryConfigurationUnitTest.suite());
        suite.addTest(CategoryTypeUnitTest.suite());
        suite.addTest(EntityTypeUnitTest.suite());
        suite.addTest(ForumEntityNotFoundExceptionUnitTest.suite());
        suite.addTest(JiveForumManagementExceptionUnitTest.suite());
        suite.addTest(JiveForumManagerUnitTest.suite());
        suite.addTest(UserNotFoundExceptionUnitTest.suite());
        suite.addTest(UserRoleUnitTest.suite());

        suite.addTest(HelperUnitTest.suite());
        suite.addTest(TCAuthTokenUnitTest.suite());

        suite.addTest(ServiceConfigurationExceptionUnitTest.suite());

        suite.addTest(JiveForumServiceBeanUnitTest.suite());

        suite.addTest(MockJiveForumServiceTest.suite());
        return suite;
    }
}
