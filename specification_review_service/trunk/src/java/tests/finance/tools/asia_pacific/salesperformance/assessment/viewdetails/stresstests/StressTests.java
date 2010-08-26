/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * 
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Aggregate all stress cases.
     * 
     * @return the aggregated cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(TransactionSummaryToJsonConverterTests.class);
        suite.addTestSuite(TransactionAssessmentViewDetailsDaoImplTests.class);
        suite.addTestSuite(TransactionAssessmentViewDetailsManagerTests.class);
        return suite;
    }
}