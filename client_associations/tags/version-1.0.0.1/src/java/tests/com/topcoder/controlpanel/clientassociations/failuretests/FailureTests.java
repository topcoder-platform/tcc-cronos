package com.topcoder.controlpanel.clientassociations.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ClientAssociationHibernateDAOTest.class);
        suite.addTestSuite(ClientAssociationServiceStatelessSessionBeanTest.class);

        return suite;
    }
}
