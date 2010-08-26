/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
import junit.framework.TestCase;

import org.apache.commons.dbcp.BasicDataSource;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionDetails;
import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.model.UserRole;

/**
 * <p>
 * Utility methods to be used for testing.
 * </p>
 *
 * @author akinwale
 * @version 1.0
 */
public final class TestHelper {
    /**
     * <p>
     * Represents the SQL query to insert transaction details data into the database table.
     * </p>
     */
    private static final String INSERT_TRANS_DETAILS_SQL = "INSERT INTO FMSV2_O_ABS_TXN_CUST ("
        + "ACCT_YR, CKR_CUST_APSK, CUST_NAME, CTRYNUM, CUSTNUM, CHANID, CHAN_DESC, FIN_BMDIV, BMDIV_NAME, "
        + "RECG_ACHVT_AMT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL query to insert transaction summary data into the database
     * table.
     * </p>
     */
    private static final String INSERT_TRANS_SQL = "INSERT INTO FMSV2_O_ABS_TXN_SUM2 ("
        + "TXN_ID, ACH_POSTING_MTH, TXN_SUM_SEQ_NO, EE_IE_APSK, ACCT_YR, ACH_EFF_MTH, AR_INDC, CKR_CUST_USE3, "
        + "CLIENT_NAME, CONTRACTNUM, INV_NBR, LINK_TXN_ID, RECG_ACHVT_AMT, ASSESSOR_CTY, ASSESSOR_SERIAL_NO, "
        + "ASSESSOR_ROLE, ASSESS_STATUS, NEGATIVE_ASSESS_REQ_CD, PART_PERC, LAST_UPT_CTY, LAST_UPT_ID, "
        + "LAST_UPT_TIME, ASSESS_COMMENT_TEXT, ASSESS_OVERRIDE_TEXT, CKR_CUST_APSK, CHANID, FIN_BMDIV, ASSESS_DATE) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP, ?, ?, ?, ?, ?, "
        + "'2010-01-01')";

    /**
     * <p>
     * Represents the Connection object.
     * </p>
     */
    private static Connection connection;

    /**
     * <p>
     * Represents the Properties instance which contains the configuration.
     * </p>
     */
    private static Properties properties;

    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     */
    private TestHelper() {

    }

    /**
     * <p>
     * Helper method to get a {@link Properties} instance from the specified filename.
     * </p>
     *
     * @param configFileName
     *            the properties file
     *
     * @return the created Properties instance based on the file contents
     */
    public static Properties getPropertiesFromFile(String configFileName) {
        Properties thisProperties = new Properties();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configFileName);
            thisProperties.load(inputStream);
        } catch (IOException e) {
            TestCase.fail("Could not obtain a Properties instance from the file [" + configFileName + "]");
        } catch (IllegalArgumentException e) {
            TestCase.fail("Malformed Unicode escape sequence found in properties file [" + configFileName + "]");
        } catch (SecurityException e) {
            TestCase.fail("Security exception occurred while trying to load [" + configFileName + "]");
        } finally {
            closeStream(inputStream);
        }

        return thisProperties;
    }

    /**
     * <p>
     * Method to retrieve the connection object.
     * </p>
     *
     * @return the connection object
     *
     * @throws Exception
     *             if any error occurred
     */
    public static Connection getConnection() throws Exception {
        loadConfig();
        if (connection == null) {
            Class.forName(properties.getProperty("driverClassName"));
            connection = DriverManager.getConnection(properties.getProperty("db.url"),
                properties.getProperty("db.userid"), properties.getProperty("db.password"));
        }

        return connection;
    }

    /**
     * <p>
     * Method to load the configuration from a properties file.
     * </p>
     */
    private static void loadConfig() {
        if (properties == null) {
            properties = getPropertiesFromFile("test_files/config.properties");
        }
    }

    /**
     * <p>
     * Helper method to get a data source to be used in the tests.
     * </p>
     *
     * @return the data source
     */
    public static BasicDataSource getDataSource() {
        loadConfig();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getProperty("driverClassName"));
        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUsername(properties.getProperty("db.userid"));
        dataSource.setPassword(properties.getProperty("db.password"));

        return dataSource;
    }

    /**
     * <p>
     * Method to close the connection object.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public static void closeConnection() throws Exception {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * <p>
     * Insert test transaction details data into the database table.
     * </p>
     *
     * @param acctYear
     *            the account year
     * @param clientIDKey
     *            the client ID key
     * @param chanId
     *            the channel ID
     * @param finBmdiv
     *            the business measurement division code
     * @param id
     *            a unique ID to be appended to some field values
     * @param amount
     *            the month-to-month recognized achievement amount
     * @param cty
     *            the country
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public static void insertTransactionDetails(String acctYear, String clientIDKey, String chanId, String finBmdiv,
        String cty, double amount, String id) throws Exception {
        getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(INSERT_TRANS_DETAILS_SQL);
            Object[] params = {
                acctYear, clientIDKey, "CustomerName" + id, cty, "CustNum" + id, chanId,
                "ChannelDesc" + id, finBmdiv, "Div" + id, new Double(amount)
            };
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>
     * Insert test transaction summary data into the database table.
     * </p>
     *
     * @param txnId
     *            the transaction ID
     * @param month
     *            the achievement posting month
     * @param seqNum
     *            the transaction sequence number
     * @param eeKey
     *            the eligible employee incentive element key
     * @param cty
     *            the country
     * @param acctYear
     *            the account year
     * @param clientIDKey
     *            the client ID key
     * @param chanId
     *            the channel ID
     * @param finBmdiv
     *            the business measurement division code
     * @param id
     *            unique identifier to append to certain values
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public static void insertTransactionSummary(long txnId, String month, short seqNum, long eeKey,
        String cty, String acctYear, String clientIDKey, String chanId, String finBmdiv, String id)
        throws Exception {
        getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(INSERT_TRANS_SQL);
            Object[] params = {
                new Long(txnId), month, new Long(seqNum), new Long(eeKey), acctYear, month, "1", "CKR" + id,
                "ClientName" + id, "Contract" + id, "InvNbr" + id, new Long(txnId * 2), new Long(txnId * 10),
                cty, "ASN" + id, "PRD", "B", "C", new Long(txnId * 3), cty, "LU" + id, "Comments", "Override",
                clientIDKey, chanId, finBmdiv
            };
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>
     * Delete test data from the database tables.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public static void clearTestData() throws Exception {
        getConnection();
        PreparedStatement stmt = null;
        String[] sql = {"DELETE FROM FMSV2_O_ABS_TXN_CUST", "DELETE FROM FMSV2_O_ABS_TXN_SUM2"};
        try {
            for (int i = 0; i < sql.length; i++) {
                stmt = connection.prepareStatement(sql[i]);
                stmt.executeUpdate();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>
     * Helper method to close an {@link InputStream}.
     * </p>
     *
     * @param stream
     *            the stream to close
     */
    public static void closeStream(InputStream stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            // ignore and continue
        }
    }

    /**
     * <p>
     * Method to obtain the value of a private field from the specified instance using
     * reflection.
     * </p>
     *
     * @param fieldName
     *            the name of the field
     * @param instance
     *            the instance to get the value from
     *
     * @return the value of the specified field name
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public static Object getFieldValue(String fieldName, Object instance) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object value = field.get(instance);
        field.setAccessible(false);

        return value;
    }

    /**
     * <p>
     * Method to set the value of a private field in the specified instance using
     * reflection.
     * </p>
     *
     * @param fieldName
     *            the name of the field
     * @param instance
     *            the instance to set the value for
     * @param value
     *            the value to set
     * @param type
     *            the type of the instance to get the field from
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public static void setFieldValue(String fieldName, Object instance, Object value, Class type)
        throws Exception {
        Field field = (type == null) ? instance.getClass().getDeclaredField(fieldName)
            : type.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
        field.setAccessible(false);
    }

    /**
     * <p>
     * Method to assert the accuracy of a retrieved transaction summary.
     * </p>
     *
     * @param summary
     *            the transaction summary to check
     * @param txnId
     *            the transaction ID
     * @param month
     *            the month
     * @param seqNum
     *            the transaction sequence number
     * @param eeKey
     *            the eligible employee incentive key
     * @param cty
     *            the country
     * @param acctYear
     *            the account year
     * @param clientIDKey
     *            the client ID key
     * @param chanId
     *            the channel ID
     * @param finBmdiv
     *            the business measurement division code
     * @param id
     *            the unique ID appended to certain fields
     * @param error
     *            the assertion error message to be displayed
     */
    public static void assertTransactionSummary(TransactionSummary summary, long txnId, String month,
        short seqNum, long eeKey, String cty, String acctYear, String clientIDKey, String chanId,
        String finBmdiv, String id, String error) {
        TestCase.assertEquals(error, txnId, summary.getTransactionID());
        TestCase.assertEquals(error, month, summary.getAchievementPostingMonth());
        TestCase.assertEquals(error, seqNum, summary.getTransactionSummarizationSequenceNumber());
        TestCase.assertEquals(error, eeKey, summary.getEligibleEmployeeIncentiveElementKey());
        TestCase.assertEquals(error, acctYear, summary.getAccountYear());
        TestCase.assertEquals(error, month, summary.getAchievementEffectiveMonth());
        TestCase.assertTrue(error, summary.isAssessmentIndicator());
        TestCase.assertEquals(error, "CKR" + id, summary.getClientID());
        TestCase.assertEquals(error, "ClientName" + id, summary.getClientName());
        TestCase.assertEquals(error, "Contract" + id, summary.getContractNumber());
        TestCase.assertEquals(error, "InvNbr" + id, summary.getInvoiceNumber());
        TestCase.assertEquals(error, (txnId * 2), summary.getLinkTransactionID());
        TestCase.assertEquals(error, new Double(txnId * 10), new Double(summary.getRecognizedAchievementAmount()));
        TestCase.assertEquals(error, cty, summary.getAssessorCountry());
        TestCase.assertEquals(error, "ASN" + id, summary.getAssessorSerialNumber());
        TestCase.assertEquals(error, UserRole.PRODUCTION_SUPPORT, summary.getAssessorRole());
        TestCase.assertEquals(error, 'B', summary.getAssessmentStatus());
        TestCase.assertNotNull(error, summary.getAssessmentDate());
        TestCase.assertEquals(error, new Double(txnId * 3), new Double(summary.getParticipationPerc()));
        TestCase.assertEquals(error, "LU" + id, summary.getLastUpdateID());
        TestCase.assertEquals(error, "Comments", summary.getAssessmentCommentText());
        TestCase.assertEquals(error, "Override", summary.getAssessmentOverrideText());
        TestCase.assertEquals(error, clientIDKey, summary.getClientIDKey());
        TestCase.assertEquals(error, chanId, summary.getChannelID());
        TestCase.assertEquals(error, finBmdiv, summary.getBusinessMeasurementDivisionCode());
    }

    /**
     * <p>
     * Method to TestCase.assert the accuracy of a retrieved transaction details object.
     * </p>
     *
     * @param details
     *            the transaction details to check
     * @param cty
     *            the country
     * @param acctYear
     *            the account year
     * @param clientIDKey
     *            the client ID key
     * @param chanId
     *            the channel ID
     * @param finBmdiv
     *            the business measurement division code
     * @param amount
     *            the month-to-month recognized achievement amount
     * @param id
     *            the unique ID appended to certain fields
     * @param error
     *            the assertion error message to be displayed
     */
    public static void assertTransactionDetails(TransactionDetails details, String cty, String acctYear,
        String clientIDKey, String chanId, String finBmdiv, String id, String error, double amount) {
        TestCase.assertEquals(error, acctYear, details.getAccountYear());
        TestCase.assertEquals(error, clientIDKey, details.getClientIDKey());
        TestCase.assertEquals(error, "CustomerName" + id, details.getCustomerName());
        TestCase.assertEquals(error, cty, details.getCustomerCountry());
        TestCase.assertEquals(error, "CustNum" + id, details.getCustomerNumber());
        TestCase.assertEquals(error, chanId, details.getChannelID());
        TestCase.assertEquals(error, "ChannelDesc" + id, details.getChannelDescription());
        TestCase.assertEquals(error, finBmdiv, details.getBusinessMeasurementDivisionCode());
        TestCase.assertEquals(error, "Div" + id, details.getBrandName());
        TestCase.assertEquals(error, new Double(amount), new Double(details.getRecognizedAchievementAmount()));
    }
}
