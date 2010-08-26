/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails;

import junit.framework.TestCase;

import org.apache.commons.dbcp.BasicDataSource;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.persist.
    TransactionAssessmentViewDetailsDaoImpl;
import finance.tools.asia_pacific.salesperformance.base.persist.DataAccess;
import finance.tools.asia_pacific.salesperformance.base.util.Configuration;

/**
 * <p>
 * Demonstrates usage of the Transaction Assessment View Details component.
 * </p>
 *
 * @author caru, akinwale
 * @version 1.0
 */
public class DemoTests extends TestCase {
    /**
     * <p>
     * Setup for each unit test in the test case.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        Configuration.setResourceFile("config");

        BasicDataSource dataSource = TestHelper.getDataSource();
        TestHelper.setFieldValue("dataSource", null, dataSource, DataAccess.class);
        TestHelper.clearTestData();
    }

    /**
     * <p>
     * Cleanup after each unit test in the test case.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void tearDown() throws Exception {
        super.tearDown();
        TestHelper.clearTestData();
    }

    /**
     * <p>
     * Demonstrates use of the main {@link TransactionAssessmentViewDetailsManager} class.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testDemo() throws Exception {
        // Create a new instance
        TransactionAssessmentViewDetailsManager manager = new TransactionAssessmentViewDetailsManager();

        // Retrieve transaction summaries by eligible employee incentive element keys
        TransactionSummary[] summaries;
        summaries = manager.retrieveTransactionsByEmployeeIncentiveElementKeys(new long[] {101, 102});

        // Retrieve transaction summaries by transaction IDs
        summaries = manager.retrieveTransactionsByTransactionNumbers(new short[] {1, 2});

        // Retrieve transaction summaries by incentive element keys with the transactions details list populated
        summaries = manager.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(new long[] {103, 104});

        // Retrieve transaction summaries by transaction IDs with the transaction details list populated
        summaries = manager.retrieveTransactionsByTransactionNumbers(new short[] {3, 4});

        // Retrieve a transaction summary with the transaction details populated
        TransactionSummary transaction = new TransactionSummary();
        transaction.setTransactionID(1);
        transaction.setAchievementPostingMonth("01");
        transaction.setTransactionSummarizationSequenceNumber((short) 1);
        TransactionSummary transactionWithDetails = manager.retrieveTransactionDetails(transaction);

        // Get the DAO field
        TransactionAssessmentViewDetailsDao dao = manager.getDao();

        // Set the DAO field
        manager.setDao(new TransactionAssessmentViewDetailsDaoImpl());
    }
}
