/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.timetracker.user.db.DbUserDAOTests;
import com.topcoder.timetracker.user.filterfactory.MappedBaseFilterFactoryTests;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactoryTests;
import com.topcoder.timetracker.user.filterfactory.UtilTests;
import com.topcoder.timetracker.user.j2ee.UserManagerSessionBeanTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
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
        suite.addTest(UserTests.suite());
        suite.addTest(UserManagerImplTests.suite());
        suite.addTest(StatusTests.suite());
        suite.addTest(ConfigurationExceptionTests.suite());
        suite.addTest(UserAuthenticatorTests.suite());
        suite.addTest(BatchOperationExceptionTests.suite());
        suite.addTest(UserManagerFactoryTests.suite());
        suite.addTest(UnrecognizedEntityExceptionTests.suite());
        suite.addTest(DataAccessExceptionTests.suite());
        suite.addTest(StringMatchTypeTests.suite());

        // tests for package com.topcoder.timetracker.user.db
        suite.addTest(DbUserDAOTests.suite());

        // tests for package com.topcoder.timetracker.user.filterfactory
        suite.addTest(MappedBaseFilterFactoryTests.suite());
        suite.addTest(UtilTests.suite());
        suite.addTest(MappedUserFilterFactoryTests.suite());

        // tests for package com.topcoder.timetracker.user.j2ee
        suite.addTest(UserManagerSessionBeanTests.suite());

        // tests for the demo
        suite.addTest(DemoTests.suite());

        return suite;
    }

}
