/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.timetracker.user.db.DbUserDAOTests;
import com.topcoder.timetracker.user.db.DbUserStatusDAOTest;
import com.topcoder.timetracker.user.db.DbUserTypeDAOTest;
import com.topcoder.timetracker.user.db.DbUtilTest;
import com.topcoder.timetracker.user.filterfactory.MappedBaseFilterFactoryTests;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactoryTests;
import com.topcoder.timetracker.user.filterfactory.UserStatusFilterFactoryTest;
import com.topcoder.timetracker.user.filterfactory.UserTypeFilterFactoryTest;
import com.topcoder.timetracker.user.j2ee.UserManagerDelegateTests;
import com.topcoder.timetracker.user.j2ee.UserManagerSessionBeanTests;
import com.topcoder.timetracker.user.j2ee.UserStatusManagerDelegateTest;
import com.topcoder.timetracker.user.j2ee.UserStatusManagerSessionBeanTest;
import com.topcoder.timetracker.user.j2ee.UserTypeManagerDelegateTest;
import com.topcoder.timetracker.user.j2ee.UserTypeManagerSessionBeanTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all Unit test cases.
     * </p>
     *
     * @return all Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // tests for package com.topcoder.timetracker.user
        suite.addTest(BatchOperationExceptionTests.suite());
        suite.addTest(ConfigurationExceptionTests.suite());
        suite.addTest(DataAccessExceptionTests.suite());
        suite.addTest(StatusTests.suite());
        suite.addTest(StringMatchTypeTests.suite());
        suite.addTest(UnrecognizedEntityExceptionTests.suite());
        suite.addTest(UserAuthenticatorTests.suite());
        suite.addTest(UserManagerFactoryTests.suite());
        suite.addTest(UserManagerImplTests.suite());
        suite.addTest(UserTests.suite());
        suite.addTest(UtilTests.suite());

        // new from version 3.2.1
        suite.addTest(UserStatusManagerImplTest.suite());
        suite.addTest(UserTypeManagerImplTest.suite());
        suite.addTest(UserStatusTest.suite());
        suite.addTest(UserTypeTest.suite());

        // tests for package com.topcoder.timetracker.user.db
        suite.addTest(DbUserDAOTests.suite());

        // new from version 3.2.1
        suite.addTest(DbUserStatusDAOTest.suite());
        suite.addTest(DbUserTypeDAOTest.suite());
        suite.addTest(DbUtilTest.suite());

        // tests for package com.topcoder.timetracker.user.filterfactory
        suite.addTest(MappedBaseFilterFactoryTests.suite());
        suite.addTest(MappedUserFilterFactoryTests.suite());

        // new from version 3.2.1
        suite.addTest(UserStatusFilterFactoryTest.suite());
        suite.addTest(UserTypeFilterFactoryTest.suite());

        // tests for package com.topcoder.timetracker.user.j2ee
        suite.addTest(UserManagerSessionBeanTests.suite());

        // new from version 3.2.1
        suite.addTest(UserManagerDelegateTests.suite());
        suite.addTest(UserStatusManagerSessionBeanTest.suite());
        suite.addTest(UserTypeManagerSessionBeanTest.suite());
        suite.addTest(UserStatusManagerDelegateTest.suite());
        suite.addTest(UserTypeManagerDelegateTest.suite());

        // tests for the demo
        suite.addTest(DemoTests.suite());

        return suite;
    }

}
