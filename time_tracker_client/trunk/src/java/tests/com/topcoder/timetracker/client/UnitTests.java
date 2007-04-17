/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.db.ClientColumnNameUnitTests;
import com.topcoder.timetracker.client.db.ClientProjectColumnNameUnitTests;
import com.topcoder.timetracker.client.db.InformixClientDAOUnitTests;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepthUnitTests;
import com.topcoder.timetracker.client.depth.ClientOnlyDepthUnitTests;
import com.topcoder.timetracker.client.depth.ClientProjectDepthUnitTests;
import com.topcoder.timetracker.client.depth.SummaryDepthUnitTests;
import com.topcoder.timetracker.client.ejb.ClientUtilityDelegateUnitTests;
import com.topcoder.timetracker.client.ejb.ClientUtilityFactoryUnitTests;
import com.topcoder.timetracker.client.ejb.ClientUtilitySessionBeanUnitTests;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //suite.addTest(XXX.suite());
        suite.addTestSuite(BatchOperationExceptionUnitTests.class);
        suite.addTestSuite(ClientAuditExceptionUnitTests.class);
        suite.addTestSuite(ClientFilterFactoryUnitTests.class);
        suite.addTestSuite(ClientPersistenceExceptionUnitTests.class);
        suite.addTestSuite(ClientUnitTests.class);
        suite.addTestSuite(ClientUtilityExceptionUnitTests.class);
        suite.addTestSuite(ClientUtilityImplUnitTests.class);
        suite.addTestSuite(ConfigurationExceptionUnitTests.class);
        suite.addTestSuite(DepthUnitTest.class);
        suite.addTestSuite(HelperUnitTests.class);
        suite.addTestSuite(InvalidClientIDExceptionUnitTests.class);
        suite.addTestSuite(InvalidClientPropertyExceptionUnitTests.class);
        suite.addTestSuite(PropertyOperationExceptionUnitTests.class);

        // add the db package
        suite.addTestSuite(ClientColumnNameUnitTests.class);
        suite.addTestSuite(ClientProjectColumnNameUnitTests.class);
        suite.addTestSuite(InformixClientDAOUnitTests.class);

        // add the depth package
        suite.addTestSuite(ClientOnlyDepthUnitTests.class);
        suite.addTestSuite(ClientProjectDepthUnitTests.class);
        suite.addTestSuite(SummaryDepthUnitTests.class);
        suite.addTestSuite(ClientIDOnlyDepthUnitTests.class);

        // add the ejb package
        suite.addTestSuite(ClientUtilityDelegateUnitTests.class);
        suite.addTestSuite(ClientUtilityFactoryUnitTests.class);
        suite.addTestSuite(ClientUtilitySessionBeanUnitTests.class);

        // add the demo
        suite.addTestSuite(DemoTest.class);

        return suite;
    }
}
