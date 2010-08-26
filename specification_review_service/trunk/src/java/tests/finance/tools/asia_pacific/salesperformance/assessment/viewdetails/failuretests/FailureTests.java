/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.failuretests.persist.TransactionAssessmentViewDetailsDaoImplFailureTest;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * The unit test suite.
     * </p>
     *
     * @return the unit test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TransactionAssessmentViewDetailsManagerFailureTest.class);
        suite.addTestSuite(TransactionAssessmentViewDetailsDaoImplFailureTest.class);
        return suite;
    }
}