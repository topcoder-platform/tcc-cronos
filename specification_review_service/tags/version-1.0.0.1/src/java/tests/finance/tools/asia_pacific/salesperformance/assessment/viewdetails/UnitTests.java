/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.
    TransactionAssessmentViewDetailsConfigurationExceptionTests;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.
    TransactionAssessmentViewDetailsDaoExceptionTests;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.
    TransactionAssessmentViewDetailsManagerExceptionTests;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.persist.
    TransactionAssessmentViewDetailsDaoImplTests;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.util.TransactionSummaryToJsonConverterTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author akinwale
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * The unit test suite.
     * </p>
     *
     * @return the unit test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(HelperTests.class);
        suite.addTestSuite(TransactionAssessmentViewDetailsConfigurationExceptionTests.class);
        suite.addTestSuite(TransactionAssessmentViewDetailsDaoExceptionTests.class);
        suite.addTestSuite(TransactionAssessmentViewDetailsManagerExceptionTests.class);
        suite.addTestSuite(TransactionAssessmentViewDetailsDaoImplTests.class);
        suite.addTestSuite(TransactionAssessmentViewDetailsManagerTests.class);
        suite.addTestSuite(TransactionSummaryToJsonConverterTests.class);
        suite.addTestSuite(DemoTests.class);

        return suite;
    }

}
