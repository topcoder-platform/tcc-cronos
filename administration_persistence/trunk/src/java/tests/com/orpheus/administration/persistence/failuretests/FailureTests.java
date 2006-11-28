/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all failure test cases for the <i>Administration Persistence</i> component.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class FailureTests extends TestCase {
    /**
     * Returns all failure test cases for the <i>Administration Persistence</i> component.
     *
     * @return all failure test cases for the <i>Administration Persistence</i> component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(TestAdminMessageTranslator.class);
        suite.addTestSuite(TestAndFilter.class);
        suite.addTestSuite(TestBetweenFilter.class);
        suite.addTestSuite(TestEqualToFilter.class);
        suite.addTestSuite(TestGreaterThanFilter.class);
        suite.addTestSuite(TestGreaterThanOrEqualToFilter.class);
        suite.addTestSuite(TestInFilter.class);
        suite.addTestSuite(TestLessThanFilter.class);
        suite.addTestSuite(TestLessThanOrEqualToFilter.class);
        suite.addTestSuite(TestLikeFilter.class);
        suite.addTestSuite(TestMessageImpl.class);
        suite.addTestSuite(TestNotFilter.class);
        suite.addTestSuite(TestNullFilter.class);
        suite.addTestSuite(TestOrFilter.class);
        suite.addTestSuite(TestRSSItemTranslator.class);
        suite.addTestSuite(TestRSSSearchCriteriaTranslator.class);
        
        suite.addTestSuite(TestSearchCriteriaDTOImpl.class);
        suite.addTestSuite(TestSQLServerAdminDataDAO.class);
        suite.addTestSuite(TestSQLServerMessageDAO.class);
        return suite;
    }
}
