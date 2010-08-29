/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.util;

import java.lang.reflect.Constructor;

import org.apache.commons.dbcp.BasicDataSource;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.TestHelper;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.persist.
    TransactionAssessmentViewDetailsDaoImpl;
import finance.tools.asia_pacific.salesperformance.base.persist.DataAccess;
import finance.tools.asia_pacific.salesperformance.base.util.Configuration;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the {@link TransactionSummaryToJsonConverter} class.
 * </p>
 *
 * @author akinwale
 * @version 1.0
 */
public class TransactionSummaryToJsonConverterTests extends TestCase {
    /**
     * <p>
     * The {@link TransactionAssessmentViewDetailsDaoImpl} instance which will be used to
     * retrieve a transaction summary from the database to be used for testing.
     * </p>
     */
    private TransactionAssessmentViewDetailsDaoImpl dao;

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
        Configuration.setResourceFile("test_files.config");
        dao = new TransactionAssessmentViewDetailsDaoImpl();

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
     * Tests the private constructor using reflection.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testCtor() throws Exception {
        Constructor constructor = TransactionSummaryToJsonConverter.class.getDeclaredConstructor(new Class[0]);
        constructor.setAccessible(true);
        Object instance = constructor.newInstance(new Object[0]);
        constructor.setAccessible(false);

        assertNotNull("instance should not be null", instance);
    }

    /**
     * <p>
     * Tests that the convertTransactionIntoJson(TransactionSummary) method works
     * properly.
     * </p>
     *
     * <p>
     * A TransactionSummary with no transaction details in its transaction details list is
     * tested here.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testConvertTransactionIntoJson_NoTransactionDetails() throws Exception {
        TestHelper.insertTransactionSummary(1, "01", (short) 11, 101, "GBR", "2011", "CID1", "C1", "BM1", "1");
        TransactionSummary summary = dao.retrieveTransactionsByTransactionNumbers(new short[] {1})[0];
        String expected = "{\"transaction\": {\"transactionID\": \"1\", \"achievementPostingMonth\": \"01\", "
            + "\"transactionSummarizationSequenceNumber\": \"11\", \"eligibleEmployeeIncentiveElementKey\": \"101\", "
            + "\"accountYear\": \"2011\", \"achievementEffectiveMonth\": \"01\", \"assessmentIndicator\": \"true\", "
            + "\"clientID\": \"CKR1\", \"clientName\": \"ClientName1\", \"contractNumber\": \"Contract1\", "
            + "\"invoiceNumber\": \"InvNbr1\", \"linkTransactionID\": \"2\", \"recognizedAchievementAmount\": "
            + "\"10.0\", \"assessorCountry\": \"GBR\", \"assessorSerialNumber\": \"ASN1\", \"assessorRole\": "
            + "\"" + summary.getAssessorRole().toString() + "\", "
            + "\"assessmentStatus\": \"B\", \"negativeAssessmentRequiringCode\": \"false\", "
            + "\"assessmentDate\": \"2010-01-01\", \"participationPerc\": \"3.0\", \"updateAction\": \"\", "
            + "\"assessmentCommentText\": \"Comments\", \"assessmentOverrideText\": \"Override\", "
            + "\"clientIDKey\": \"CID1\", \"channelID\": \"C1\", \"businessMeasurementDivisionCode\": \"BM1\", "
            + "\"transactionDetails\": []}}";
        String json = TransactionSummaryToJsonConverter.convertTransactionToJson(summary);
        assertEquals("convertTransactionIntoJson does not work properly", expected, json);
    }

    /**
     * <p>
     * Tests that the convertTransactionIntoJson(TransactionSummary) method works
     * properly.
     * </p>
     *
     * <p>
     * A TransactionSummary with 2 transaction details in its transaction details list is
     * tested here.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testConvertTransactionIntoJson_WithTransactionDetails() throws Exception {
        TransactionSummary transaction = new TransactionSummary();
        transaction.setTransactionID(1);
        transaction.setAchievementPostingMonth("01");
        transaction.setTransactionSummarizationSequenceNumber((short) 11);
        TestHelper.insertTransactionSummary(1, "01", (short) 11, 101, "GBR", "2011", "CID1", "C1", "BM1", "1");
        TestHelper.insertTransactionDetails("2011", "CID1", "C1", "BM1", "GBR", 2000, "5");
        TestHelper.insertTransactionDetails("2011", "CID1", "C1", "BM1", "GBR", 900, "6");

        TransactionSummary summary = dao.retrieveTransactionDetails(transaction);
        summary.setInvoiceNumber(null);
        String expected = "{\"transaction\": {\"transactionID\": \"1\", \"achievementPostingMonth\": \"01\", "
            + "\"transactionSummarizationSequenceNumber\": \"11\", \"eligibleEmployeeIncentiveElementKey\": \"101\", "
            + "\"accountYear\": \"2011\", \"achievementEffectiveMonth\": \"01\", \"assessmentIndicator\": \"true\", "
            + "\"clientID\": \"CKR1\", \"clientName\": \"ClientName1\", \"contractNumber\": \"Contract1\", "
            + "\"invoiceNumber\": \"null\", \"linkTransactionID\": \"2\", \"recognizedAchievementAmount\": "
            + "\"10.0\", \"assessorCountry\": \"GBR\", \"assessorSerialNumber\": \"ASN1\", \"assessorRole\": "
            + "\"" + summary.getAssessorRole().toString() + "\", "
            + "\"assessmentStatus\": \"B\", \"negativeAssessmentRequiringCode\": \"false\", "
            + "\"assessmentDate\": \"2010-01-01\", \"participationPerc\": \"3.0\", \"updateAction\": \"\", "
            + "\"assessmentCommentText\": \"Comments\", \"assessmentOverrideText\": \"Override\", "
            + "\"clientIDKey\": \"CID1\", \"channelID\": \"C1\", \"businessMeasurementDivisionCode\": \"BM1\", "
            + "\"transactionDetails\": [{\"accountYear\": \"2011\", \"clientIDKey\": \"CID1\", "
            + "\"customerName\": \"CustomerName5\", \"customerCountry\": \"GBR\", "
            + "\"customerNumber\": \"CustNum5\", \"channelID\": \"C1\", \"channelDescription\": \"ChannelDesc5\", "
            + "\"businessMeasurementDivisionCode\": \"BM1\", \"brandName\": \"Div5\", "
            + "\"recognizedAchievementAmount\": \"2000.0\"}, {\"accountYear\": \"2011\", "
            + "\"clientIDKey\": \"CID1\", \"customerName\": \"CustomerName6\", \"customerCountry\": \"GBR\", "
            + "\"customerNumber\": \"CustNum6\", \"channelID\": \"C1\", \"channelDescription\": \"ChannelDesc6\", "
            + "\"businessMeasurementDivisionCode\": \"BM1\", \"brandName\": \"Div6\", "
            + "\"recognizedAchievementAmount\": \"900.0\"}]}}";
        String json = TransactionSummaryToJsonConverter.convertTransactionToJson(summary);
        assertEquals("convertTransactionIntoJson does not work properly", expected, json);
    }
}
