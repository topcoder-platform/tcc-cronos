/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAOExceptionUnitTests;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.ClientAssociationHibernateDAOUnitTests;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.ClientUnitTests;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.CompClientPKUnitTests;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.CompClientUnitTests;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.HibernateHelperUnitTests;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.UserClientPKUnitTests;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.UserClientUnitTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all unit tests.
     * </p>
     *
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ClientAssociationServiceExceptionUnitTests.suite());
        suite.addTest(ClientAssociationDAOExceptionUnitTests.suite());

        // entity classes
        suite.addTest(ClientUnitTests.suite());
        suite.addTest(CompClientUnitTests.suite());
        suite.addTest(CompClientPKUnitTests.suite());
        suite.addTest(UserClientPKUnitTests.suite());
        suite.addTest(UserClientUnitTests.suite());
        suite.addTest(HibernateHelperUnitTests.suite());

        suite.addTest(ClientAssociationHibernateDAOUnitTests.suite());

        suite.addTest(ClientAssociationServiceStatelessSessionBeanUnitTests.suite());
        suite.addTest(ClientAssociationServiceInContainerUnitTests.suite());
        suite.addTest(ClientAssociationServiceStatelessSessionBeanFailureTests.suite());

        suite.addTest(Demo.suite());

        return suite;
    }

}
