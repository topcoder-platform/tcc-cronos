/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author AK_47
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * failure tests suite.
     *
     * @return the suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ClientDAOBeanFailureTest.suite());
        suite.addTest(ClientStatusDAOBeanFailureTest.suite());
        suite.addTest(CompanyDAOBeanFailureTest.suite());
        suite.addTest(GenericEJB3DAOBeanFailureTest.suite());
        suite.addTest(ProjectDAOBeanFailureTest.suite());
        suite.addTest(ProjectStatusDAOBeanFailureTest.suite());
        suite.addTest(SearchByFilterUtilityImplFailureTest.suite());
        return suite;
    }

}
