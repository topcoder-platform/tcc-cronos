/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */



package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.accuracytests;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.persist
    .TransactionAssessmentViewDetailsDaoImpl;
import finance.tools.asia_pacific.salesperformance.base.persist.DataAccess;
import finance.tools.asia_pacific.salesperformance.base.util.Configuration;

import junit.framework.TestCase;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;

import java.util.Properties;

/**
 * Accuracy Tests for {@link TransactionAssessmentViewDetailsDaoImpl}.
 *
 * @author morehappiness
 * @version 1.0
 */
public class TransactionAssessmentViewDetailsDaoImplAccuracyTests extends TestCase {
    /**
     * The connection to use.
     */
    private Connection con;

    /**
     * The configuration file path.
     */
    private String configFile;

    /**
     * {TransactionAssessmentViewDetailsDaoImpl} instance for tests.
     */
    private TransactionAssessmentViewDetailsDaoImpl instance;

    /**
     * The properties to use.
     */
    private Properties properties;

    /**
     * Sets up the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        configFile = "test_files/accuracy/db.properties";

        Configuration.setResourceFile("accuracy/config");
        BasicDataSource dataSource = AccuracyTestHelper.getDataSource();

        AccuracyTestHelper.setFieldValue("dataSource", null, dataSource, DataAccess.class);
        instance = new TransactionAssessmentViewDetailsDaoImpl();

        properties = AccuracyTestHelper.loadProperties(configFile);
        con = AccuracyTestHelper.createConnection(properties);
        AccuracyTestHelper.setUpDataBase(con);

    }

    /**
     * Tears down the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        AccuracyTestHelper.clearDataBase(con);
        AccuracyTestHelper.closeConnection(con);
        instance = null;
        properties = null;
        configFile = null;
    }

    /**
     * Test for TransactionAssessmentViewDetailsDaoImpl().
     *
     * @throws Exception to JUnit
     */
    public void test_ctor() throws Exception {
        assertNotNull("Should NOT be null", instance);
    }

    /**
     * Test for retrieveTransactionsByEmployeeIncentiveElementKeys().
     *
     * @throws Exception to JUnit
     */
    public void test_retrieveTransactionsByEmployeeIncentiveElementKeys() throws Exception {
        long[] keys = new long[] { 1, 2 };
        TransactionSummary[] results = instance.retrieveTransactionsByEmployeeIncentiveElementKeys(keys);

        for (int i = 0; i < results.length; i++) {
            assertEquals("Should be the same", keys[i], results[i].getEligibleEmployeeIncentiveElementKey());
        }
    }

    /**
     * Test for retrieveTransactionsByTransactionNumbers().
     *
     * @throws Exception to JUnit
     */
    public void test_retrieveTransactionsByTransactionNumbers() throws Exception {
        short[] keys = new short[] { 1, 2 };
        TransactionSummary[] results = instance.retrieveTransactionsByTransactionNumbers(keys);

        for (int i = 0; i < results.length; i++) {
            assertEquals("Should be the same", keys[i], results[i].getTransactionID());
        }
    }

    /**
     * Test for retrieveTransactionsDetailsByEmployeeIncentiveElementKeys().
     *
     * @throws Exception to JUnit
     */
    public void test_retrieveTransactionsDetailsByEmployeeIncentiveElementKeys() throws Exception {
        long[] keys = new long[] { 1, 2 };
        TransactionSummary[] results = instance.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(keys);

        for (int i = 0; i < results.length; i++) {
            assertEquals("Should be the same", keys[i], results[i].getEligibleEmployeeIncentiveElementKey());
        }
    }

    /**
     * Test for retrieveTransactionsDetailsByTransactionNumbers().
     *
     * @throws Exception to JUnit
     */
    public void test_retrieveTransactionsDetailsByTransactionNumbers() throws Exception {
        short[] keys = new short[] { 1, 2 };
        TransactionSummary[] results = instance.retrieveTransactionsDetailsByTransactionNumbers(keys);

        for (int i = 0; i < results.length; i++) {
            assertEquals("Should be the same", keys[i], results[i].getEligibleEmployeeIncentiveElementKey());
        }
    }

    /**
     * Test for retrieveTransactionDetails().
     *
     * @throws Exception to JUnit
     */
    public void test_retrieveTransactionDetails() throws Exception {
        TransactionSummary transaction = new TransactionSummary();

        transaction.setTransactionID(1);
        transaction.setAchievementPostingMonth("1");
        transaction.setTransactionSummarizationSequenceNumber((short) 1);
        TransactionSummary result = instance.retrieveTransactionDetails(transaction);

        assertEquals("Should be the same", transaction.getTransactionID(), result.getTransactionID());
        assertEquals("Should be the same", transaction.getAchievementPostingMonth(),
                     result.getAchievementPostingMonth());

        assertEquals("Should be the same", transaction.getTransactionSummarizationSequenceNumber(),
                     result.getTransactionSummarizationSequenceNumber());

    }
}
