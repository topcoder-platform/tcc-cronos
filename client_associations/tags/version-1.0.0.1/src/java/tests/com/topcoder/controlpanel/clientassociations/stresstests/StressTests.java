/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.controlpanel.clientassociations.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * 
 * @author smallka
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Returns the stress test suite.
     * </p>
     * 
     * @return the stress test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ClientAssociationHibernateDAOStressTest.class);
        suite.addTestSuite(ClientAssociationServiceStatelessSessionBeanStressTest.class);

        return suite;
    }
}
