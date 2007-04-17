/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.timetracker.entry.time.db.BaseDAOTests;
import com.topcoder.timetracker.entry.time.db.DbBaseFilterFactoryTests;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeDAOTests;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeFilterFactoryTests;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryDAOTests;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryFilterFactoryTests;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryRejectReasonDAOTests;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusDAOTests;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusFilterFactoryTests;
import com.topcoder.timetracker.entry.time.db.SqlTypeTests;
import com.topcoder.timetracker.entry.time.db.UtilTests;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerSessionBeanTests;
import com.topcoder.timetracker.entry.time.ejb.TimeEntryManagerSessionBeanTests;
import com.topcoder.timetracker.entry.time.ejb.TimeStatusManagerSessionBeanTests;

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

        // tests for package com.topcoder.timetracker.entry.time
        suite.addTest(BatchOperationExceptionTests.suite());
        suite.addTest(TimeStatusManagerImplTests.suite());
        suite.addTest(TaskTypeTests.suite());
        suite.addTest(InvalidCompanyExceptionTests.suite());
        suite.addTest(TimeEntryTests.suite());
        suite.addTest(TaskTypeManagerImplTests.suite());
        suite.addTest(ConfigurationExceptionTests.suite());
        suite.addTest(UnrecognizedEntityExceptionTests.suite());
        suite.addTest(TimeStatusTests.suite());
        suite.addTest(ManagerFactoryTests.suite());
        suite.addTest(TimeEntryManagerImplTests.suite());
        suite.addTest(DataAccessExceptionTests.suite());
        suite.addTest(StringMatchTypeTests.suite());

        // tests for package com.topcoder.timetracker.entry.time.db
        suite.addTest(SqlTypeTests.suite());
        suite.addTest(DbTaskTypeDAOTests.suite());
        suite.addTest(UtilTests.suite());
        suite.addTest(DbTimeEntryFilterFactoryTests.suite());
        suite.addTest(DbTimeEntryRejectReasonDAOTests.suite());
        suite.addTest(DbBaseFilterFactoryTests.suite());
        suite.addTest(DbTaskTypeFilterFactoryTests.suite());
        suite.addTest(DbTimeStatusDAOTests.suite());
        suite.addTest(DbTimeEntryDAOTests.suite());
        suite.addTest(BaseDAOTests.suite());
        suite.addTest(DbTimeStatusFilterFactoryTests.suite());

        // tests for package com.topcoder.timetracker.entry.time.ejb
        suite.addTest(TimeEntryManagerSessionBeanTests.suite());
        suite.addTest(TaskTypeManagerSessionBeanTests.suite());
        suite.addTest(TimeStatusManagerSessionBeanTests.suite());

        // tests for demo
        suite.addTest(DemoTests.suite());

        return suite;
    }

}
