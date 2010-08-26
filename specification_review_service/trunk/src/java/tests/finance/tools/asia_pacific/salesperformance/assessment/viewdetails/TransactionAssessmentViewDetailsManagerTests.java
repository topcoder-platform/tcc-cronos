/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails;

import junit.framework.TestCase;

import org.apache.commons.dbcp.BasicDataSource;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionDetails;
import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.
    TransactionAssessmentViewDetailsConfigurationException;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.
    TransactionAssessmentViewDetailsManagerException;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.persist.
    TransactionAssessmentViewDetailsDaoImpl;
import finance.tools.asia_pacific.salesperformance.base.persist.DataAccess;
import finance.tools.asia_pacific.salesperformance.base.util.Configuration;

/**
 * <p>
 * Unit tests for the {@link TransactionAssessmentViewDetailsManager} class.
 * </p>
 *
 * @author akinwale
 * @version 1.0
 */
public class TransactionAssessmentViewDetailsManagerTests extends TestCase {
    /**
     * <p>
     * The {@link TransactionAssessmentViewDetailsManager} instance to be tested.
     * </p>
     */
    private TransactionAssessmentViewDetailsManager instance;

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
        instance = new TransactionAssessmentViewDetailsManager();

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
     * Tests that the TransactionAssessmentViewDetailsManager() constructor creates a new
     * instance that is not null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should not be null", instance);
        assertTrue("instance not properly initialized",
            instance.getDao() instanceof TransactionAssessmentViewDetailsDaoImpl);
    }

    /**
     * <p>
     * Tests that the TransactionAssessmentViewDetailsManager() constructor creates a new
     * instance that is not null.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testCtor2() throws Exception {
        Configuration.setResourceFile("config2");
        instance = new TransactionAssessmentViewDetailsManager();
        assertNotNull("instance should not be null", instance);
        assertTrue("instance not properly initialized",
            instance.getDao() instanceof TransactionAssessmentViewDetailsDaoImpl);
    }

    /**
     * <p>
     * Tests that the TransactionAssessmentViewDetailsManager() constructor handles a case
     * where the configured DAO class does not implement
     * {@link TransactionAssessmentViewDetailsDao} by throwing
     * {@link TransactionAssessmentViewDetailsConfigurationException}.
     * </p>
     *
     * <p>
     * {@link TransactionAssessmentViewDetailsConfigurationException} is expected.
     * </p>
     */
    public void testCtor_Failure_InvalidDAOClassType() {
        Configuration.setResourceFile("invalid1");
        try {
            new TransactionAssessmentViewDetailsManager();
            fail("TransactionAssessmentViewDetailsConfigurationException was expected");
        } catch (TransactionAssessmentViewDetailsConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the TransactionAssessmentViewDetailsManager() constructor handles a case
     * where the configured DAO class does not exist by throwing
     * {@link TransactionAssessmentViewDetailsConfigurationException}.
     * </p>
     *
     * <p>
     * {@link TransactionAssessmentViewDetailsConfigurationException} is expected.
     * </p>
     */
    public void testCtor_Failure_MissingDAOClass() {
        Configuration.setResourceFile("invalid2");
        try {
            new TransactionAssessmentViewDetailsManager();
            fail("TransactionAssessmentViewDetailsConfigurationException was expected");
        } catch (TransactionAssessmentViewDetailsConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the TransactionAssessmentViewDetailsManager() constructor handles a case
     * where the configured DAO class does not have a parameter-less constructor by
     * throwing {@link TransactionAssessmentViewDetailsConfigurationException}.
     * </p>
     *
     * <p>
     * {@link TransactionAssessmentViewDetailsConfigurationException} is expected.
     * </p>
     */
    public void testCtor_Failure_ClassWithNoParameterlessCtor() {
        Configuration.setResourceFile("invalid3");
        try {
            new TransactionAssessmentViewDetailsManager();
            fail("TransactionAssessmentViewDetailsConfigurationException was expected");
        } catch (TransactionAssessmentViewDetailsConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the TransactionAssessmentViewDetailsManager() constructor handles a case
     * where the configured DAO class has a constructor that throws an exception by
     * throwing {@link TransactionAssessmentViewDetailsConfigurationException}.
     * </p>
     *
     * <p>
     * {@link TransactionAssessmentViewDetailsConfigurationException} is expected.
     * </p>
     */
    public void testCtor_Failure_ClassWithCtorThatThrowsException() {
        Configuration.setResourceFile("invalid4");
        try {
            new TransactionAssessmentViewDetailsManager();
            fail("TransactionAssessmentViewDetailsConfigurationException was expected");
        } catch (TransactionAssessmentViewDetailsConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsByEmployeeIncentiveElementKeys(long[]) method
     * works properly.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsByEmployeeIncentiveElementKeys() throws Exception {
        String error = "retrieveTransactionsByEmployeeIncentiveElementKeys does not work properly";
        TestHelper.insertTransactionSummary(1, "01", (short) 11, 101, "USA", "2010", "CID1", "C1", "BM1", "1");
        TestHelper.insertTransactionSummary(2, "02", (short) 12, 102, "USA", "2010", "CID2", "C2", "BM2", "2");
        TestHelper.insertTransactionSummary(3, "03", (short) 13, 103, "USA", "2010", "CID3", "C3", "BM3", "3");

        long[] keys = {101, 102, 103};
        TransactionSummary[] summaries = instance.retrieveTransactionsByEmployeeIncentiveElementKeys(keys);
        assertEquals(error, 3, summaries.length);
        TestHelper.assertTransactionSummary(
            summaries[0], 1, "01", (short) 11, 101, "USA", "2010", "CID1", "C1", "BM1", "1", error);
        TestHelper.assertTransactionSummary(
            summaries[1], 2, "02", (short) 12, 102, "USA", "2010", "CID2", "C2", "BM2", "2", error);
        TestHelper.assertTransactionSummary(
            summaries[2], 3, "03", (short) 13, 103, "USA", "2010", "CID3", "C3", "BM3", "3", error);
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsByEmployeeIncentiveElementKeys(long[]) method
     * handles a null parameter by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsByEmployeeIncentiveElementKeys_Failure_NullParam() throws Exception {
        try {
            instance.retrieveTransactionsByEmployeeIncentiveElementKeys(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsByEmployeeIncentiveElementKeys(long[]) method
     * handles an empty array parameter by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsByEmployeeIncentiveElementKeys_Failure_EmptyArray() throws Exception {
        try {
            instance.retrieveTransactionsByEmployeeIncentiveElementKeys(new long[0]);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsByEmployeeIncentiveElementKeys(long[]) method
     * handles a case where a critical error occurred by throwing
     * {@link TransactionAssessmentViewDetailsManagerException}.
     * </p>
     *
     * <p>
     * {@link TransactionAssessmentViewDetailsManagerException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsByEmployeeIncentiveElementKeys_Failure_CriticalError() throws Exception {
        try {
            BasicDataSource ds = TestHelper.getDataSource();
            ds.setUsername("invalidTCUsername");
            TestHelper.setFieldValue("dataSource", null, ds, DataAccess.class);
            instance.retrieveTransactionsByEmployeeIncentiveElementKeys(new long[] {1});
            fail("TransactionAssessmentViewDetailsManagerException was expected");
        } catch (TransactionAssessmentViewDetailsManagerException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsByTransactionNumbers(short[]) method works
     * properly.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsByTransactionNumbers() throws Exception {
        String error = "retrieveTransactionsByTransactionNumbers does not work properly";
        TestHelper.insertTransactionSummary(4, "04", (short) 14, 104, "GBR", "2014", "CID4", "C4", "BM4", "4");
        TestHelper.insertTransactionSummary(5, "05", (short) 15, 105, "FRA", "2015", "CID5", "C5", "BM5", "5");
        TestHelper.insertTransactionSummary(6, "06", (short) 16, 106, "DEU", "2016", "CID6", "C6", "BM6", "6");

        short[] keys = {6, 4, 5};
        TransactionSummary[] summaries = instance.retrieveTransactionsByTransactionNumbers(keys);
        assertEquals(error, 3, summaries.length);
        TestHelper.assertTransactionSummary(
            summaries[0], 6, "06", (short) 16, 106, "DEU", "2016", "CID6", "C6", "BM6", "6", error);
        TestHelper.assertTransactionSummary(
            summaries[1], 4, "04", (short) 14, 104, "GBR", "2014", "CID4", "C4", "BM4", "4", error);
        TestHelper.assertTransactionSummary(
            summaries[2], 5, "05", (short) 15, 105, "FRA", "2015", "CID5", "C5", "BM5", "5", error);
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsByTransactionNumbers(short[]) method handles a
     * null parameter by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsByTransactionNumbers_Failure_NullParam() throws Exception {
        try {
            instance.retrieveTransactionsByTransactionNumbers(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsByTransactionNumbers(short[]) method handles an
     * empty array parameter by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsByTransactionNumbers_Failure_EmptyArray() throws Exception {
        try {
            instance.retrieveTransactionsByTransactionNumbers(new short[0]);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsByTransactionNumbers(short[]) method handles a
     * case where a critical error occurred by throwing
     * {@link TransactionAssessmentViewDetailsManagerException}.
     * </p>
     *
     * <p>
     * {@link TransactionAssessmentViewDetailsManagerException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsByTransactionNumbers_Failure_CriticalError() throws Exception {
        try {
            BasicDataSource ds = TestHelper.getDataSource();
            ds.setUsername("invalidTCUsername");
            TestHelper.setFieldValue("dataSource", null, ds, DataAccess.class);
            instance.retrieveTransactionsByTransactionNumbers(new short[] {1});
            fail("TransactionAssessmentViewDetailsManagerException was expected");
        } catch (TransactionAssessmentViewDetailsManagerException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(long[])
     * method works properly.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsDetailsByEmployeeIncentiveElementKeys() throws Exception {
        String error = "retrieveTransactionsDetailsByEmployeeIncentiveElementKeys does not work properly";
        TestHelper.insertTransactionSummary(4, "04", (short) 14, 104, "GBR", "2014", "CID4", "C4", "BM4", "4");
        TestHelper.insertTransactionSummary(5, "05", (short) 15, 105, "FRA", "2015", "CID5", "C5", "BM5", "5");
        TestHelper.insertTransactionSummary(6, "06", (short) 16, 106, "DEU", "2016", "CID6", "C6", "BM6", "6");

        // Insert details for the txn ID 4 and 6
        TestHelper.insertTransactionDetails("2014", "CID4", "C4", "BM4", "GBR", 20, "7");
        TestHelper.insertTransactionDetails("2016", "CID6", "C6", "BM6", "DEU", 25, "8");
        TestHelper.insertTransactionDetails("2016", "CID6", "C6", "BM6", "DEU", 30, "9");

        long[] keys = {104, 105, 106};
        TransactionSummary[] summaries = instance.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(keys);
        TestHelper.assertTransactionSummary(
            summaries[0], 4, "04", (short) 14, 104, "GBR", "2014", "CID4", "C4", "BM4", "4", error);
        assertEquals(error, 1, summaries[0].getTransactionDetails().size());
        TestHelper.assertTransactionDetails((TransactionDetails) summaries[0].getTransactionDetails().get(0),
            "GBR", "2014", "CID4", "C4", "BM4", "7", error, 20);

        TestHelper.assertTransactionSummary(
            summaries[1], 5, "05", (short) 15, 105, "FRA", "2015", "CID5", "C5", "BM5", "5", error);
        assertEquals(error, 0, summaries[1].getTransactionDetails().size());

        TestHelper.assertTransactionSummary(
            summaries[2], 6, "06", (short) 16, 106, "DEU", "2016", "CID6", "C6", "BM6", "6", error);
        assertEquals(error, 2, summaries[2].getTransactionDetails().size());
        TestHelper.assertTransactionDetails((TransactionDetails) summaries[2].getTransactionDetails().get(0),
            "DEU", "2016", "CID6", "C6", "BM6", "8", error, 25);
        TestHelper.assertTransactionDetails((TransactionDetails) summaries[2].getTransactionDetails().get(1),
            "DEU", "2016", "CID6", "C6", "BM6", "9", error, 30);
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(long[])
     * method handles a null parameter by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsDetailsByEmployeeIncentiveElementKeys_Failure_NullParam() throws Exception {
        try {
            instance.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(long[])
     * method handles an empty array parameter by throwing
     * {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsDetailsByEmployeeIncentiveElementKeys_Failure_EmptyArray() throws Exception {
        try {
            instance.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(new long[0]);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(long[])
     * method handles a case where a critical error occurred by throwing
     * {@link TransactionAssessmentViewDetailsManagerException}.
     * </p>
     *
     * <p>
     * {@link TransactionAssessmentViewDetailsManagerException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsDetailsByEmployeeIncentiveElementKeys_Failure_CriticalError() throws Exception {
        try {
            BasicDataSource ds = TestHelper.getDataSource();
            ds.setUsername("invalidTCUsername");
            TestHelper.setFieldValue("dataSource", null, ds, DataAccess.class);
            instance.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(new long[] {1});
            fail("TransactionAssessmentViewDetailsManagerException was expected");
        } catch (TransactionAssessmentViewDetailsManagerException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsDetailsByTransactionNumbers(short[]) method
     * works properly.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsDetailsByTransactionNumbers() throws Exception {
        String error = "retrieveTransactionsDetailsByTransactionNumbers does not work properly";
        TestHelper.insertTransactionSummary(4, "04", (short) 14, 104, "GBR", "2014", "CID4", "C4", "BM4", "4");
        TestHelper.insertTransactionSummary(5, "05", (short) 15, 105, "FRA", "2015", "CID5", "C5", "BM5", "5");
        TestHelper.insertTransactionSummary(6, "06", (short) 16, 106, "DEU", "2016", "CID6", "C6", "BM6", "6");

        // Insert details for the txn ID 4 and 5
        TestHelper.insertTransactionDetails("2014", "CID4", "C4", "BM4", "GBR", 20, "7");
        TestHelper.insertTransactionDetails("2015", "CID5", "C5", "BM5", "FRA", 25, "8");
        TestHelper.insertTransactionDetails("2015", "CID5", "C5", "BM5", "FRA", 30, "9");

        short[] keys = {5, 6, 4};
        TransactionSummary[] summaries = instance.retrieveTransactionsDetailsByTransactionNumbers(keys);
        TestHelper.assertTransactionSummary(
            summaries[0], 5, "05", (short) 15, 105, "FRA", "2015", "CID5", "C5", "BM5", "5", error);
        assertEquals(error, 2, summaries[0].getTransactionDetails().size());
        TestHelper.assertTransactionDetails((TransactionDetails) summaries[0].getTransactionDetails().get(0),
            "FRA", "2015", "CID5", "C5", "BM5", "8", error, 25);
        TestHelper.assertTransactionDetails((TransactionDetails) summaries[0].getTransactionDetails().get(1),
            "FRA", "2015", "CID5", "C5", "BM5", "9", error, 30);

        TestHelper.assertTransactionSummary(
            summaries[1], 6, "06", (short) 16, 106, "DEU", "2016", "CID6", "C6", "BM6", "6", error);
        assertEquals(error, 0, summaries[1].getTransactionDetails().size());

        TestHelper.assertTransactionSummary(
            summaries[2], 4, "04", (short) 14, 104, "GBR", "2014", "CID4", "C4", "BM4", "4", error);
        assertEquals(error, 1, summaries[2].getTransactionDetails().size());
        TestHelper.assertTransactionDetails((TransactionDetails) summaries[2].getTransactionDetails().get(0),
            "GBR", "2014", "CID4", "C4", "BM4", "7", error, 20);
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsDetailsByTransactionNumbers(long[]) method
     * handles a null parameter by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsDetailsByTransactionNumbers_Failure_NullParam() throws Exception {
        try {
            instance.retrieveTransactionsDetailsByTransactionNumbers(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsDetailsByTransactionNumbers(long[]) method
     * handles an empty array parameter by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsDetailsByTransactionNumbers_Failure_EmptyArray() throws Exception {
        try {
            instance.retrieveTransactionsDetailsByTransactionNumbers(new short[0]);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionsDetailsByTransactionNumbers(short[]) method
     * handles a case where a critical error occurred by throwing
     * {@link TransactionAssessmentViewDetailsManagerException}.
     * </p>
     *
     * <p>
     * {@link TransactionAssessmentViewDetailsManagerException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionsDetailsByTransactionNumbers_Failure_CriticalError() throws Exception {
        try {
            BasicDataSource ds = TestHelper.getDataSource();
            ds.setUsername("invalidTCUsername");
            TestHelper.setFieldValue("dataSource", null, ds, DataAccess.class);
            instance.retrieveTransactionsDetailsByTransactionNumbers(new short[] {1});
            fail("TransactionAssessmentViewDetailsManagerException was expected");
        } catch (TransactionAssessmentViewDetailsManagerException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionDetails(TransactionSummary) method works
     * properly.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionDetails() throws Exception {
        String error = "retrieveTransactionsDetails does not work properly";
        TestHelper.insertTransactionSummary(5, "05", (short) 15, 105, "FRA", "2015", "CID5", "C5", "BM5", "5");
        TestHelper.insertTransactionDetails("2015", "CID5", "C5", "BM5", "FRA", 20, "7");
        TestHelper.insertTransactionDetails("2015", "CID5", "C5", "BM5", "FRA", 25, "8");
        TestHelper.insertTransactionDetails("2015", "CID5", "C5", "BM5", "FRA", 30, "9");

        TransactionSummary summary = new TransactionSummary();
        summary.setTransactionID(5);
        summary.setAchievementPostingMonth("05");
        summary.setTransactionSummarizationSequenceNumber((short) 15);

        TransactionSummary result = instance.retrieveTransactionDetails(summary);
        TestHelper.assertTransactionSummary(
            result, 5, "05", (short) 15, 105, "FRA", "2015", "CID5", "C5", "BM5", "5", error);
        assertEquals(error, 3, result.getTransactionDetails().size());
        TestHelper.assertTransactionDetails((TransactionDetails) result.getTransactionDetails().get(0),
            "FRA", "2015", "CID5", "C5", "BM5", "7", error, 20);
        TestHelper.assertTransactionDetails((TransactionDetails) result.getTransactionDetails().get(1),
            "FRA", "2015", "CID5", "C5", "BM5", "8", error, 25);
        TestHelper.assertTransactionDetails((TransactionDetails) result.getTransactionDetails().get(2),
            "FRA", "2015", "CID5", "C5", "BM5", "9", error, 30);
    }

    /**
     * <p>
     * Tests that the retrieveTransactionDetails(TransactionSummary) method handles a null
     * parameter by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionDetails_Failure_NullParam() throws Exception {
        try {
            instance.retrieveTransactionDetails(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionDetails(TransactionSummary) method handles a case
     * where the specified TransactionSummary parameter does not have a transaction ID set
     * by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionDetails_Failure_TransactionIDNotSet() throws Exception {
        try {
            instance.retrieveTransactionDetails(new TransactionSummary());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionDetails(TransactionSummary) method handles a case
     * where the specified TransactionSummary parameter does not have an achievement
     * posting month set by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionDetails_Failure_AchievementPostingMonthNotSet() throws Exception {
        try {
            TransactionSummary summary = new TransactionSummary();
            summary.setTransactionID(5);
            instance.retrieveTransactionDetails(summary);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionDetails(TransactionSummary) method handles a case
     * where the specified TransactionSummary parameter does not have an transaction
     * summarization sequence number set by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionDetails_Failure_TransactionSequenceNumberNotSet() throws Exception {
        try {
            TransactionSummary summary = new TransactionSummary();
            summary.setTransactionID(5);
            summary.setAchievementPostingMonth("05");
            instance.retrieveTransactionDetails(summary);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the retrieveTransactionDetails(TransactionSummary) method handles a case
     * where a critical error occurred by throwing
     * {@link TransactionAssessmentViewDetailsManagerException}.
     * </p>
     *
     * <p>
     * {@link TransactionAssessmentViewDetailsManagerException} is expected.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testRetrieveTransactionDetails_Failure_CriticalError() throws Exception {
        try {
            BasicDataSource ds = TestHelper.getDataSource();
            ds.setUsername("invalidTCUsername");
            TestHelper.setFieldValue("dataSource", null, ds, DataAccess.class);
            TransactionSummary summary = new TransactionSummary();
            summary.setTransactionID(5);
            summary.setAchievementPostingMonth("05");
            summary.setTransactionSummarizationSequenceNumber((short) 15);
            instance.retrieveTransactionDetails(summary);
            fail("TransactionAssessmentViewDetailsManagerException was expected");
        } catch (TransactionAssessmentViewDetailsManagerException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the setDao(TransactionAssessmentViewDetailsDao) method works properly by
     * setting the specified value for the corresponding private field.
     * </p>
     */
    public void testSetDao() {
        TransactionAssessmentViewDetailsDao dao = new TransactionAssessmentViewDetailsDaoImpl();
        instance.setDao(dao);
        assertEquals("setDao does not work properly", dao, instance.getDao());
    }

    /**
     * <p>
     * Tests that the setDao(TransactionAssessmentViewDetailsDao) method handles a null
     * parameter by throwing {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     */
    public void testSetDao_Failure_NullParam() {
        try {
            instance.setDao(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the getDao() method works properly by retrieving the correct value from
     * the corresponding private field.
     * </p>
     */
    public void testGetDao() {
        assertTrue("instance not properly initialized",
            instance.getDao() instanceof TransactionAssessmentViewDetailsDaoImpl);

        TransactionAssessmentViewDetailsDao dao = new TransactionAssessmentViewDetailsDaoImpl();
        instance.setDao(dao);
        assertEquals("getDao does not work properly", dao, instance.getDao());
    }
}
