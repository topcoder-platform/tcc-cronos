/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.failuretests.persist;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.TransactionAssessmentViewDetailsDaoException;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.failuretests.FailureTestHelper;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.persist.TransactionAssessmentViewDetailsDaoImpl;
import junit.framework.TestCase;

/**
 * Failure test for TransactionAssessmentViewDetailsDaoImpl class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TransactionAssessmentViewDetailsDaoImplFailureTest extends TestCase {
    /**
     * The instance of TransactionAssessmentViewDetailsDaoImpl used in test.
     */
    private TransactionAssessmentViewDetailsDaoImpl instance;

    /**
     * Set up for each test.
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void setUp() throws Exception {
        instance = new TransactionAssessmentViewDetailsDaoImpl();
        FailureTestHelper.setInvalidDataSource();
    }

    /**
     * Test retrieveTransactionsByEmployeeIncentiveElementKeys(long[] eligibleEmployeeIncentiveElementKeys). When
     * eligibleEmployeeIncentiveElementKeys is null.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRetrieveTransactionsByEmployeeIncentiveElementKeys_Null() throws Exception {
        try {
            instance.retrieveTransactionsByEmployeeIncentiveElementKeys(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsByEmployeeIncentiveElementKeys(long[] eligibleEmployeeIncentiveElementKeys). When
     * eligibleEmployeeIncentiveElementKeys is empty.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRetrieveTransactionsByEmployeeIncentiveElementKeys_Empty() throws Exception {
        try {
            instance.retrieveTransactionsByEmployeeIncentiveElementKeys(new long[0]);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsByEmployeeIncentiveElementKeys(long[] eligibleEmployeeIncentiveElementKeys). When failed
     * to connect to database.
     */
    public void testRetrieveTransactionsByEmployeeIncentiveElementKeys_DaoError() {
        try {
            instance.retrieveTransactionsByEmployeeIncentiveElementKeys(new long[] { 10 });
            fail("Cannot go here.");
        } catch (TransactionAssessmentViewDetailsDaoException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsByTransactionNumbers(short[] transactionNumbers). When transactionNumbers is null.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRetrieveTransactionsByTransactionNumbers_Null() throws Exception {
        try {
            instance.retrieveTransactionsByTransactionNumbers(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsByTransactionNumbers(short[] transactionNumbers). When transactionNumbers is empty.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRetrieveTransactionsByTransactionNumbers_Empty() throws Exception {
        try {
            instance.retrieveTransactionsByTransactionNumbers(new short[0]);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsByTransactionNumbers(short[] transactionNumbers). When failed to connect to database.
     */
    public void testRetrieveTransactionsByTransactionNumbers_DaoError() {
        try {
            instance.retrieveTransactionsByTransactionNumbers(new short[] { 10 });
            fail("Cannot go here.");
        } catch (TransactionAssessmentViewDetailsDaoException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(long[] eligibleEmployeeIncentiveElementKeys). When
     * eligibleEmployeeIncentiveElementKeys is null.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRetrieveTransactionsDetailsByEmployeeIncentiveElementKeys_Null() throws Exception {
        try {
            instance.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(long[] eligibleEmployeeIncentiveElementKeys). When
     * eligibleEmployeeIncentiveElementKeys is empty.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRetrieveTransactionsDetailsByEmployeeIncentiveElementKeys_Empty() throws Exception {
        try {
            instance.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(new long[0]);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(long[] eligibleEmployeeIncentiveElementKeys). When
     * failed to connect to database.
     */
    public void testRetrieveTransactionsDetailsByEmployeeIncentiveElementKeys_DaoError() {
        try {
            instance.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(new long[] { 10 });
            fail("Cannot go here.");
        } catch (TransactionAssessmentViewDetailsDaoException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsDetailsByTransactionNumbers(short[] transactionNumbers). When transactionNumbers is
     * null.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRetrieveTransactionsDetailsByTransactionNumbers_Null() throws Exception {
        try {
            instance.retrieveTransactionsDetailsByTransactionNumbers(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsDetailsByTransactionNumbers(short[] transactionNumbers). When transactionNumbers is
     * empty.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRetrieveTransactionsDetailsByTransactionNumbers_Empty() throws Exception {
        try {
            instance.retrieveTransactionsDetailsByTransactionNumbers(new short[0]);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionsDetailsByTransactionNumbers(short[] transactionNumbers). When failed to connect to
     * database.
     */
    public void testRetrieveTransactionsDetailsByTransactionNumbers_DaoError() {
        try {
            instance.retrieveTransactionsDetailsByTransactionNumbers(new short[] { 10 });
            fail("Cannot go here.");
        } catch (TransactionAssessmentViewDetailsDaoException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionDetails(TransactionSummary transaction). When transaction is null.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRetrieveTransactionDetails_TransactionIsNull() throws Exception {
        try {
            instance.retrieveTransactionDetails(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionDetails(TransactionSummary transaction). When transaction's transactionId is not set.
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionDetails_TransactionIdIsNotset() throws Exception {
        TransactionSummary transactionSummary = new TransactionSummary();
        transactionSummary.setAchievementPostingMonth("08");
        transactionSummary.setTransactionSummarizationSequenceNumber((short) 1);

        try {
            instance.retrieveTransactionDetails(transactionSummary);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionDetails(TransactionSummary transaction). When transaction's achievementPostingMonth is
     * not set.
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionDetails_AchievementPostingMonthIsNotset() throws Exception {
        TransactionSummary transactionSummary = new TransactionSummary();
        transactionSummary.setTransactionID(1);
        transactionSummary.setTransactionSummarizationSequenceNumber((short) 1);

        try {
            instance.retrieveTransactionDetails(transactionSummary);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionDetails(TransactionSummary transaction). When transaction's
     * transactionSummarizationSequenceNumber is not set.
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionDetails_TransactionSummarizationSequenceNumberIsNotset() throws Exception {
        TransactionSummary transactionSummary = new TransactionSummary();
        transactionSummary.setTransactionID(1);
        transactionSummary.setAchievementPostingMonth("08");

        try {
            instance.retrieveTransactionDetails(transactionSummary);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test retrieveTransactionDetails(TransactionSummary transaction). When failed to connect to database.
     */
    public void testRetrieveTransactionDetails_DaoError() {
        TransactionSummary transactionSummary = new TransactionSummary();
        transactionSummary.setTransactionID(1);
        transactionSummary.setAchievementPostingMonth("08");
        transactionSummary.setTransactionSummarizationSequenceNumber((short) 1);

        try {
            instance.retrieveTransactionDetails(transactionSummary);
            fail("Cannot go here.");
        } catch (TransactionAssessmentViewDetailsDaoException e) {
            // OK
        }
    }
}