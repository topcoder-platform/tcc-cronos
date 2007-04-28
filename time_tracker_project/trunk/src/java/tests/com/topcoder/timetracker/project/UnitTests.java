/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.db.BaseDAOTests;
import com.topcoder.timetracker.project.db.DbBaseFilterFactoryTests;
import com.topcoder.timetracker.project.db.DbProjectDAOTests;
import com.topcoder.timetracker.project.db.DbProjectEntryDAOTests;
import com.topcoder.timetracker.project.db.DbProjectFilterFactoryTests;
import com.topcoder.timetracker.project.db.DbProjectManagerDAOTests;
import com.topcoder.timetracker.project.db.DbProjectManagerFilterFactoryTests;
import com.topcoder.timetracker.project.db.DbProjectWorkerDAOTests;
import com.topcoder.timetracker.project.db.DbProjectWorkerFilterFactoryTests;
import com.topcoder.timetracker.project.db.SqlTypeTests;
import com.topcoder.timetracker.project.db.UtilTests;
import com.topcoder.timetracker.project.ejb.ProjectManagerUtilitySessionBeanTests;
import com.topcoder.timetracker.project.ejb.ProjectUtilitySessionBeanTests;
import com.topcoder.timetracker.project.ejb.ProjectWorkerUtilitySessionBeanTests;

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

        // tests for package com.topcoder.timetracker.project
        suite.addTest(ProjectTests.suite());
        suite.addTest(ProjectWorkerTests.suite());
        suite.addTest(InvalidCompanyExceptionTests.suite());
        suite.addTest(DuplicateEntityExceptionTests.suite());
        suite.addTest(ProjectManagerTests.suite());
        suite.addTest(ProjectWorkerUtilityImplTests.suite());
        suite.addTest(EntryTypeTests.suite());
        suite.addTest(ConfigurationExceptionTests.suite());
        suite.addTest(ProjectManagerUtilityImplTests.suite());
        suite.addTest(StringMatchTypeTests.suite());
        suite.addTest(UnrecognizedEntityExceptionTests.suite());
        suite.addTest(ProjectUtilityImplTests.suite());
        suite.addTest(DataAccessExceptionTests.suite());

        // tests for package com.topcoder.timetracker.project.db
        suite.addTest(DbProjectWorkerDAOTests.suite());
        suite.addTest(SqlTypeTests.suite());
        suite.addTest(DbBaseFilterFactoryTests.suite());
        suite.addTest(DbProjectManagerFilterFactoryTests.suite());
        suite.addTest(DbProjectDAOTests.suite());
        suite.addTest(DbProjectFilterFactoryTests.suite());
        suite.addTest(UtilTests.suite());
        suite.addTest(BaseDAOTests.suite());
        suite.addTest(DbProjectEntryDAOTests.suite());
        suite.addTest(DbProjectManagerDAOTests.suite());
        suite.addTest(DbProjectWorkerFilterFactoryTests.suite());

        // tests for package com.topcoder.timetracker.project.ejb
        suite.addTest(ProjectManagerUtilitySessionBeanTests.suite());
        suite.addTest(ProjectUtilitySessionBeanTests.suite());
        suite.addTest(ProjectWorkerUtilitySessionBeanTests.suite());

        // tests for the demo
        suite.addTest(Demo.suite());

        return suite;
    }

}
