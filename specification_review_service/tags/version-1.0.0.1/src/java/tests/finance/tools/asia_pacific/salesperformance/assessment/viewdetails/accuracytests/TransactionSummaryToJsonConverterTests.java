/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */



package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.accuracytests;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.util.TransactionSummaryToJsonConverter;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for the {@link TransactionSummaryToJsonConverter} class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class TransactionSummaryToJsonConverterTests extends TestCase {
    /**
     * Sets up the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test for convertTransactionIntoJson().
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void test_convertTransactionIntoJson() throws Exception {
        TransactionSummary transaction = new TransactionSummary();

        transaction.setTransactionID(1);
        transaction.setAchievementPostingMonth("12");
        transaction.setTransactionSummarizationSequenceNumber((short) 11);

        String json = TransactionSummaryToJsonConverter.convertTransactionToJson(transaction);

        assertTrue("Should have the expected value", json.indexOf("1") > 0);
        assertTrue("Should have the expected value", json.indexOf("11") > 0);
        assertTrue("Should have the expected value", json.indexOf("12") > 0);
    }
}
