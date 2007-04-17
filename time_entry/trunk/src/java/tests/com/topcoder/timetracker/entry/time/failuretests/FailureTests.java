/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.timetracker.entry.time.failuretests.db.BaseDAOFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.db.DbBaseFilterFactoryFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.db.DbTaskTypeDAOFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.db.DbTaskTypeFilterFactoryFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.db.DbTimeEntryDAOFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.db.DbTimeEntryFilterFactoryFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.db.DbTimeEntryRejectReasonDAOFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.db.DbTimeStatusDAOFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.db.DbTimeStatusFilterFactoryFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.ejb.TaskTypeManagerSessionBeanFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.ejb.TimeEntryManagerSessionBeanFailureTests;
import com.topcoder.timetracker.entry.time.failuretests.ejb.TimeStatusManagerSessionBeanFailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all failure test cases of this component.</p>
 *
 * @author KLW
 * @version 3.2
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * all the failure test for this component.
     * </p>
     *
     * @return all the failure test for this component.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //failure test for the package : com.topcoder.timetracker.entry.time
        suite.addTestSuite(ManagerFactoryFailureTests.class);
        suite.addTestSuite(TaskTypeFailureTests.class);
        suite.addTestSuite(TaskTypeManagerImplFailureTests.class);
        suite.addTestSuite(TimeEntryFailureTests.class);
        suite.addTestSuite(TimeStatusFailureTests.class);
        suite.addTestSuite(TimeStatusManagerImplFailureTests.class);

        //failure test for the package : com.topcoder.timetracker.entry.time.db
        suite.addTestSuite(BaseDAOFailureTests.class);
        suite.addTestSuite(DbBaseFilterFactoryFailureTests.class);
        suite.addTestSuite(DbTaskTypeDAOFailureTests.class);
        suite.addTestSuite(DbTaskTypeFilterFactoryFailureTests.class);
        suite.addTestSuite(DbTimeEntryDAOFailureTests.class);
        suite.addTestSuite(DbTimeEntryFilterFactoryFailureTests.class);
        suite.addTestSuite(DbTimeEntryRejectReasonDAOFailureTests.class);
        suite.addTestSuite(DbTimeStatusDAOFailureTests.class);
        suite.addTestSuite(DbTimeStatusFilterFactoryFailureTests.class);

        //failure test for the package : com.topcoder.timetracker.entry.time.ejb
        suite.addTestSuite(TaskTypeManagerSessionBeanFailureTests.class);
        suite.addTestSuite(TimeEntryManagerSessionBeanFailureTests.class);
        suite.addTestSuite(TimeStatusManagerSessionBeanFailureTests.class);

        return suite;
    }
}
