/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.audit.ejb.AuditDelegateUnitTest;
import com.topcoder.timetracker.audit.ejb.AuditSessionBeanUnitTest;
import com.topcoder.timetracker.audit.persistence.DefaultValuesContainerUnitTest;
import com.topcoder.timetracker.audit.persistence.InformixAuditPersistenceUnitTest;


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
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ApplicationAreaUnitTest.class);
        suite.addTestSuite(AuditConfigurationExceptionUnitTest.class);
        suite.addTestSuite(AuditDetailUnitTest.class);
        suite.addTestSuite(AuditHeaderUnitTest.class);
        suite.addTestSuite(AuditManagerExceptionUnitTest.class);
        suite.addTestSuite(AuditPersistenceExceptionUnitTest.class);
        suite.addTestSuite(TimeTrackerAuditHelperUnitTest.class);

        suite.addTestSuite(AuditDelegateUnitTest.class);
        suite.addTestSuite(AuditSessionBeanUnitTest.class);

        suite.addTestSuite(DefaultValuesContainerUnitTest.class);
        suite.addTestSuite(InformixAuditPersistenceUnitTest.class);

        suite.addTestSuite(DemoTest.class);
        return suite;
    }
}
