/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service;

import com.topcoder.service.user.UserInfo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.1
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all unit tests. This includes the test suite
     * <code>com.topcoder.service.project.AllTests</code>.
     * </p>
     *
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(UserServiceRemoteTests.suite());
        suite.addTest(ProjectServiceRemoteTests.suite());
        suite.addTest(ProjectServiceDemo.suite());
        suite.addTest(UserServiceDemo.suite());
        suite.addTestSuite(UserInfoTests.class);
        suite.addTestSuite(AddressTests.class);
        suite.addTestSuite(UserTests.class);
        return suite;
    }

}
