/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.timetracker.rejectreason.ejb.DAOFactoryTests;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailDAOConfigurationExceptionTests;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailDAOLocalHomeTests;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailManagerTests;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailSessionBeanTests;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonDAOConfigurationExceptionTests;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonDAOLocalHomeTests;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManagerTests;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonSessionBeanTests;
import com.topcoder.timetracker.rejectreason.informix.AuditHelperTests;
import com.topcoder.timetracker.rejectreason.informix.DbRejectEmailDAOAccuracyTests;
import com.topcoder.timetracker.rejectreason.informix.DbRejectEmailDAOFailureTests;
import com.topcoder.timetracker.rejectreason.informix.DbRejectReasonDAOAccuracyTests;
import com.topcoder.timetracker.rejectreason.informix.DbRejectReasonDAOFailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class UnitTests extends TestCase {
    /**
     * Aggregates all Unit test cases.
     *
     * @return the test case aggregates all Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // com.topcoder.timetracker.rejectreason package
        suite.addTestSuite(DemoTests.class);
        suite.addTestSuite(FilterCreationHelperTests.class);
        suite.addTestSuite(RejectEmailDAOExceptionTests.class);
        suite.addTestSuite(RejectEmailNotFoundExceptionTests.class);
        suite.addTestSuite(RejectEmailSearchBuilderTests.class);
        suite.addTestSuite(RejectEmailTests.class);
        suite.addTestSuite(RejectReasonDAOExceptionTests.class);
        suite.addTestSuite(RejectReasonNotFoundExceptionTests.class);
        suite.addTestSuite(RejectReasonSearchBuilderTests.class);
        suite.addTestSuite(RejectReasonTests.class);

        // com.topcoder.timetracker.rejectreason.ejb package
        suite.addTestSuite(DAOFactoryTests.class);
        suite.addTestSuite(RejectEmailDAOConfigurationExceptionTests.class);
        suite.addTestSuite(RejectEmailDAOLocalHomeTests.class);
        suite.addTestSuite(RejectEmailManagerTests.class);
        suite.addTestSuite(RejectEmailSessionBeanTests.class);
        suite.addTestSuite(RejectReasonDAOConfigurationExceptionTests.class);
        suite.addTestSuite(RejectReasonDAOLocalHomeTests.class);
        suite.addTestSuite(RejectReasonManagerTests.class);
        suite.addTestSuite(RejectReasonSessionBeanTests.class);

        // com.topcoder.timetracker.rejectreason.informix package
        suite.addTestSuite(AuditHelperTests.class);
        suite.addTestSuite(DbRejectEmailDAOAccuracyTests.class);
        suite.addTestSuite(DbRejectEmailDAOFailureTests.class);
        suite.addTestSuite(DbRejectReasonDAOAccuracyTests.class);
        suite.addTestSuite(DbRejectReasonDAOFailureTests.class);

        return suite;
    }
}
