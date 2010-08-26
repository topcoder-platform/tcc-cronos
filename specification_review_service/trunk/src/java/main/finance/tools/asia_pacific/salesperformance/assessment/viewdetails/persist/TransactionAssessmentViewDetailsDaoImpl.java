/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.websphere.ce.cm.StaleConnectionException;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionDetails;
import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.model.UserRole;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.Helper;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.TransactionAssessmentViewDetailsDao;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.
    TransactionAssessmentViewDetailsDaoException;
import finance.tools.asia_pacific.salesperformance.base.exception.FMSCriticalException;
import finance.tools.asia_pacific.salesperformance.base.persist.DataAccess;

/**
 * <p>
 * This class implements the contract for the transaction assessment view details DAO. It
 * uses {@link DataAccess} abstract class to get the database connection.
 * </p>
 *
 * <p>
 * This includes:
 * <ul>
 * <li>retrieve all transactions for the given employees</li>
 * <li>retrieve all transactions for the given transaction numbers</li>
 * <li>retrieve transaction details of a given transaction.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and thread-safe. No transactions are needed
 * because database usage is read-only.
 * </p>
 *
 * @author caru, akinwale
 * @version 1.0
 */
public class TransactionAssessmentViewDetailsDaoImpl implements TransactionAssessmentViewDetailsDao {
    /**
     * <p>
     * Represents the class name to be used for logging.
     * </p>
     */
    private static final String CLASS_NAME = TransactionAssessmentViewDetailsDaoImpl.class.getName();

    /**
     * <p>
     * Represents the base SQL query to be used for retrieving data from the DB.
     * </p>
     */
    private static final String GET_TRANS_BASE_SQL = "SELECT * FROM FMSV2_O_ABS_TXN_SUM2";

    /**
     * <p>
     * Represents the SQL query to get transaction summary data using the eligible
     * employee incentive element key.
     * </p>
     */
    private static final String GET_TRANS_BY_EEKEY_SQL = GET_TRANS_BASE_SQL + " WHERE EE_IE_APSK = ?";

    /**
     * <p>
     * Represents the SQL query to get transaction summary data using the transaction
     * summarization sequence number.
     * </p>
     */
    private static final String GET_TRANS_BY_TXNID_SQL = GET_TRANS_BASE_SQL + " WHERE TXN_ID = ?";

    /**
     * <p>
     * Represents the SQL query to get transaction summary data using its keys.
     * </p>
     */
    private static final String GET_TRANS_BY_TRANS_SUMM_SQL = GET_TRANS_BASE_SQL
        + " WHERE TXN_ID = ? AND ACH_POSTING_MTH = ? AND TXN_SUM_SEQ_NO = ?";


    /**
     * <p>
     * Represents the SQL query to get transaction details data.
     * </p>
     */
    private static final String GET_TRANS_DETAILS_SQL =
          "SELECT TD.ACCT_YR, TD.CKR_CUST_APSK, TD.CUST_NAME, TD.CTRYNUM, TD.CUSTNUM, TD.CHANID, TD.CHAN_DESC, "
        + "TD.FIN_BMDIV, TD.BMDIV_NAME, TD.RECG_ACHVT_AMT FROM FMSV2_O_ABS_TXN_CUST TD "
        + "JOIN FMSV2_O_ABS_TXN_SUM2 T ON TD.ACCT_YR = T.ACCT_YR AND TD.CKR_CUST_APSK = T.CKR_CUST_APSK "
        + "AND TD.CHANID = T.CHANID AND TD.FIN_BMDIV = T.FIN_BMDIV "
        + "WHERE T.TXN_ID = ? AND T.ACH_POSTING_MTH = ? AND T.TXN_SUM_SEQ_NO = ?";


    /**
     * <p>
     * Logger instance. Initialized to default logger for this class, cannot be null,
     * never changes. Used by business methods.
     * </p>
     */
    private static final Logger LOGGER = Logger.getLogger(TransactionAssessmentViewDetailsDaoImpl.class.getName());

    /**
     * <p>
     * The user role mapping used to map user role names to their corresponding UserRole
     * instances.
     * </p>
     */
    private static final Map USER_ROLE_MAP = new HashMap();
    static {
        USER_ROLE_MAP.put("FLM", UserRole.MANAGER_OF_ELIGIBLE_EMPLOYEE);
        USER_ROLE_MAP.put("SLM", UserRole.SECOND_LINE_MANAGER_OF_ELIGIBLE_EMPLOYEE);
        USER_ROLE_MAP.put("EMP", UserRole.ELIGIBLE_EMPLOYEE);
        USER_ROLE_MAP.put("ADL", UserRole.ASSESS_DELEGATE);
        USER_ROLE_MAP.put("INA", UserRole.INCENTIVE_ANALYST);
        USER_ROLE_MAP.put("PRD", UserRole.PRODUCTION_SUPPORT);
        USER_ROLE_MAP.put("WWT", UserRole.WW_TEAM);
        USER_ROLE_MAP.put("APS", UserRole.ABSOLUTE_PLAN_ADMIN);
    }

    /**
     * <p>
     * Default constructor. Creates a new instance.
     * </p>
     */
    public TransactionAssessmentViewDetailsDaoImpl() {
        String methodName = CLASS_NAME + "#TransactionAssessmentViewDetailsDaoImpl";
        long startTime = Helper.logEntry(methodName, null, null, LOGGER);

        Helper.logExit(methodName, null, startTime, LOGGER);
    }

    /**
     * <p>
     * Retrieve all TransactionSummaries with the given employee incentive element keys.
     * </p>
     *
     * @param eligibleEmployeeIncentiveElementKeys
     *            the given employee incentive element keys, cannot be null or empty,
     *            element can be any value
     *
     * @return all found TransactionSummaries, cannot be null, can be empty, elements
     *         cannot be null
     *
     * @throws IllegalArgumentException
     *             if the argument is null or empty
     * @throws TransactionAssessmentViewDetailsDaoException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsByEmployeeIncentiveElementKeys(
        long[] eligibleEmployeeIncentiveElementKeys) throws TransactionAssessmentViewDetailsDaoException {
        String methodName = CLASS_NAME + "#retrieveTransactionsByEmployeeIncentiveElementKeys";
        long startTime = Helper.logEntry(methodName, new String[] {"eligibleEmployeeIncentiveElementKeys"},
            new Object[] {eligibleEmployeeIncentiveElementKeys}, LOGGER);

        Helper.checkArray(
            eligibleEmployeeIncentiveElementKeys, "eligibleEmployeeIncentiveElementKeys", methodName, LOGGER);

        // The list of TransactionSummary to be converted to an array to return
        List summaryList = new ArrayList();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DataAccess.getConnection();
            stmt = connection.prepareStatement(GET_TRANS_BY_EEKEY_SQL);
            for (int i = 0; i < eligibleEmployeeIncentiveElementKeys.length; i++) {
                stmt.setLong(1, eligibleEmployeeIncentiveElementKeys[i]);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    summaryList.add(getTransactionSummaryFromResultSet(rs));
                }
            }
        } catch (StaleConnectionException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "A stale connection exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } catch (SQLException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "An SQL exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } catch (FMSCriticalException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "A critical exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } finally {
            closeDBObjects(stmt, connection, methodName);
        }

        TransactionSummary[] results =
            (TransactionSummary[]) summaryList.toArray(new TransactionSummary[summaryList.size()]);
        Helper.logExit(methodName, results, startTime, LOGGER);
        return results;
    }

    /**
     * <p>
     * Retrieve all TransactionSummaries with the given transaction number.
     * </p>
     *
     * @param transactionNumbers
     *            the given transaction numbers, cannot be null or empty, element can be
     *            any value
     *
     * @return all found TransactionSummaries, cannot be null, can be empty, elements
     *         cannot be null
     *
     * @throws IllegalArgumentException
     *             if the argument is null or empty
     * @throws TransactionAssessmentViewDetailsDaoException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsByTransactionNumbers(short[] transactionNumbers)
        throws TransactionAssessmentViewDetailsDaoException {
        String methodName = CLASS_NAME + "#retrieveTransactionsByTransactionNumbers";
        long startTime = Helper.logEntry(methodName, new String[] {"transactionNumbers"},
            new Object[] {transactionNumbers}, LOGGER);

        Helper.checkArray(transactionNumbers, "transactionNumbers", methodName, LOGGER);

        // The list of TransactionSummary to be converted to an array to return
        List summaryList = new ArrayList();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DataAccess.getConnection();
            stmt = connection.prepareStatement(GET_TRANS_BY_TXNID_SQL);
            for (int i = 0; i < transactionNumbers.length; i++) {
                stmt.setLong(1, transactionNumbers[i]);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    summaryList.add(getTransactionSummaryFromResultSet(rs));
                }
            }
        } catch (StaleConnectionException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "A stale connection exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } catch (SQLException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "An SQL exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } catch (FMSCriticalException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "A critical exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } finally {
            closeDBObjects(stmt, connection, methodName);
        }

        TransactionSummary[] results =
            (TransactionSummary[]) summaryList.toArray(new TransactionSummary[summaryList.size()]);
        Helper.logExit(methodName, results, startTime, LOGGER);
        return results;
    }

    /**
     * <p>
     * Retrieve all TransactionSummaries (filled with TransactionDetails list) with the
     * given employee incentive element keys.
     * </p>
     *
     * @param eligibleEmployeeIncentiveElementKeys
     *            the given employee incentive element keys, cannot be null or empty,
     *            element can be any value
     *
     * @return all found TransactionSummaries, cannot be null, can be empty, elements
     *         cannot be null
     *
     * @throws IllegalArgumentException
     *             if the argument is null or empty
     * @throws TransactionAssessmentViewDetailsDaoException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(
        long[] eligibleEmployeeIncentiveElementKeys) throws TransactionAssessmentViewDetailsDaoException {
        String methodName = CLASS_NAME + "#retrieveTransactionsDetailsByEmployeeIncentiveElementKeys";
        long startTime = Helper.logEntry(methodName, new String[] {"eligibleEmployeeIncentiveElementKeys"},
            new Object[] {eligibleEmployeeIncentiveElementKeys}, LOGGER);

        Helper.checkArray(
            eligibleEmployeeIncentiveElementKeys, "eligibleEmployeeIncentiveElementKeys", methodName, LOGGER);

        // The list of TransactionSummary to be converted to an array to return
        List summaryList = new ArrayList();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DataAccess.getConnection();
            stmt = connection.prepareStatement(GET_TRANS_BY_EEKEY_SQL);
            for (int i = 0; i < eligibleEmployeeIncentiveElementKeys.length; i++) {
                stmt.setLong(1, eligibleEmployeeIncentiveElementKeys[i]);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    TransactionSummary summary = getTransactionSummaryFromResultSet(rs);
                    summary.setTransactionDetails(getTransactionDetailsForTransaction(summary, connection, methodName));
                    summaryList.add(summary);
                }
            }
        } catch (StaleConnectionException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "A stale connection exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } catch (SQLException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "An SQL exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } catch (FMSCriticalException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "A critical exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } finally {
            closeDBObjects(stmt, connection, methodName);
        }

        TransactionSummary[] results =
            (TransactionSummary[]) summaryList.toArray(new TransactionSummary[summaryList.size()]);
        Helper.logExit(methodName, results, startTime, LOGGER);
        return results;
    }

    /**
     * <p>
     * Retrieve all TransactionSummaries (filled with TransactionDetails list) with the
     * given transaction numbers.
     * </p>
     *
     * @param transactionNumbers
     *            the given transaction numbers, cannot be null or empty, element can be
     *            any value
     *
     * @return all found TransactionSummaries, cannot be null, can be empty, elements
     *         cannot be null
     *
     * @throws IllegalArgumentException
     *             if the argument is null or empty
     * @throws TransactionAssessmentViewDetailsDaoException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsDetailsByTransactionNumbers(short[] transactionNumbers)
        throws TransactionAssessmentViewDetailsDaoException {
        String methodName = CLASS_NAME + "#retrieveTransactionsDetailsByTransactionNumbers";
        long startTime = Helper.logEntry(methodName, new String[] {"transactionNumbers"},
            new Object[] {transactionNumbers}, LOGGER);

        Helper.checkArray(transactionNumbers, "transactionNumbers", methodName, LOGGER);

        // The list of TransactionSummary to be converted to an array to return
        List summaryList = new ArrayList();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DataAccess.getConnection();
            stmt = connection.prepareStatement(GET_TRANS_BY_TXNID_SQL);
            for (int i = 0; i < transactionNumbers.length; i++) {
                stmt.setLong(1, transactionNumbers[i]);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    TransactionSummary summary = getTransactionSummaryFromResultSet(rs);
                    summary.setTransactionDetails(getTransactionDetailsForTransaction(summary, connection, methodName));
                    summaryList.add(summary);
                }
            }
        } catch (StaleConnectionException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "A stale connection exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } catch (SQLException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "An SQL exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } catch (FMSCriticalException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "A critical exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } finally {
            closeDBObjects(stmt, connection, methodName);
        }

        TransactionSummary[] results =
            (TransactionSummary[]) summaryList.toArray(new TransactionSummary[summaryList.size()]);
        Helper.logExit(methodName, results, startTime, LOGGER);
        return results;
    }

    /**
     * <p>
     * Retrieve the details of the given TransactionSummary.
     * </p>
     *
     * @param transaction
     *            the given transaction, cannot be null, its transactionId,
     *            achievementPostingMonth and transactionSummarizationSequenceNumber must
     *            be set
     *
     * @return the TransactionSummary entity filled with details, can be null if no
     *         transaction is found with the given primary keys
     *
     * @throws IllegalArgumentException
     *             if the argument is null or if its transactionId, achievementPostingMonth
     *             or transactionSummarizationSequenceNumber has not been set
     * @throws TransactionAssessmentViewDetailsDaoException
     *             if any errors occurs
     */
    public TransactionSummary retrieveTransactionDetails(TransactionSummary transaction)
        throws TransactionAssessmentViewDetailsDaoException {
        String methodName = CLASS_NAME + "#retrieveTransactionDetails";
        long startTime = Helper.logEntry(methodName, new String[] {"transaction"},
            new Object[] {transaction}, LOGGER);

        Helper.checkNull(transaction, "transaction", methodName, LOGGER);
        Helper.checkPositive(transaction.getTransactionID(), "transaction.transactionID", methodName, LOGGER);
        Helper.checkNullOrEmpty(transaction.getAchievementPostingMonth(),
            "transaction.achievementPostingMonth", methodName, LOGGER);
        Helper.checkPositive(transaction.getTransactionSummarizationSequenceNumber(),
            "transaction.transactionSummarizationSequenceNumber", methodName, LOGGER);

        // The TransactionSummary to return
        TransactionSummary summary = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DataAccess.getConnection();
            stmt = connection.prepareStatement(GET_TRANS_BY_TRANS_SUMM_SQL);
            int paramIndex = 1;
            stmt.setLong(paramIndex, transaction.getTransactionID());
            stmt.setString(++paramIndex, transaction.getAchievementPostingMonth());
            stmt.setLong(++paramIndex, transaction.getTransactionSummarizationSequenceNumber());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                summary = getTransactionSummaryFromResultSet(rs);
                summary.setTransactionDetails(getTransactionDetailsForTransaction(transaction, connection, methodName));
            }
        } catch (StaleConnectionException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "A stale connection exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } catch (SQLException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "An SQL exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } catch (FMSCriticalException e) {
            throw (TransactionAssessmentViewDetailsDaoException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsDaoException(
                    "A critical exception occurred", CLASS_NAME, e, methodName, null), LOGGER);
        } finally {
            closeDBObjects(stmt, connection, methodName);
        }

        Helper.logExit(methodName, summary, startTime, LOGGER);
        return summary;
    }

    /**
     * <p>
     * Helper method to get a trimmed string value from the specified result set based on
     * the column name.
     * </p>
     *
     * @param colName
     *            the column name of the value to retrieve
     * @param rs
     *            the ResultSet to retrieve the value from
     *
     * @return the trimmed string value
     *
     * @throws SQLException
     *             if any error occurred while trying to retrieve the value
     */
    private static String getTrimmedStringFromRs(String colName, ResultSet rs) throws SQLException {
        String value = rs.getString(colName);
        if (value != null) {
            value = value.trim();
        }
        return value;
    }

    /**
     * <p>
     * Helper method to get a list of {@link TransactionDetails} objects for the specified
     * transaction summary based on its keys.
     * </p>
     *
     * @param transaction
     *            the transaction summary to get transaction details for
     * @param connection
     *            the connection object
     * @param methodName
     *            the name of the method to be used for logging
     * @return the list of transaction details
     *
     * @throws SQLException
     *             if any error occurred while trying to retrieve the transaction details
     */
    private static List getTransactionDetailsForTransaction(TransactionSummary transaction, Connection connection,
        String methodName) throws SQLException {
        PreparedStatement stmt = null;
        List transactionDetails = new ArrayList();
        try {
            stmt = connection.prepareStatement(GET_TRANS_DETAILS_SQL);
            int paramIndex = 1;
            stmt.setLong(paramIndex, transaction.getTransactionID());
            stmt.setString(++paramIndex, transaction.getAchievementPostingMonth());
            stmt.setLong(++paramIndex, transaction.getTransactionSummarizationSequenceNumber());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactionDetails.add(getTransactionDetailsFromResultSet(rs));
            }
        } finally {
            closeDBObjects(stmt, null, methodName);
        }

        return transactionDetails;
    }

    /**
     * <p>
     * Helper method to get a {@link TransactionDetails} instance based on the data from
     * the specified ResultSet.
     * </p>
     *
     * @param rs
     *            the result set to get the details data from
     *
     * @return the created transaction details based on the result set
     *
     * @throws SQLException
     *             if any error occurred while trying to get any of the result set data
     */
    private static TransactionDetails getTransactionDetailsFromResultSet(ResultSet rs) throws SQLException {
        TransactionDetails details = new TransactionDetails();
        details.setAccountYear(getTrimmedStringFromRs("ACCT_YR", rs));
        details.setBrandName(getTrimmedStringFromRs("BMDIV_NAME", rs));
        details.setBusinessMeasurementDivisionCode(getTrimmedStringFromRs("FIN_BMDIV", rs));
        details.setChannelDescription(getTrimmedStringFromRs("CHAN_DESC", rs));
        details.setChannelID(getTrimmedStringFromRs("CHANID", rs));
        details.setClientIDKey(getTrimmedStringFromRs("CKR_CUST_APSK", rs));
        details.setCustomerCountry(getTrimmedStringFromRs("CTRYNUM", rs));
        details.setCustomerName(getTrimmedStringFromRs("CUST_NAME", rs));
        details.setCustomerNumber(getTrimmedStringFromRs("CUSTNUM", rs));
        details.setRecognizedAchievementAmount(rs.getDouble("RECG_ACHVT_AMT"));

        return details;
    }

    /**
     * <p>
     * Helper method to get a {@link TransactionSummary} instance based on the data from
     * the specified ResultSet.
     * </p>
     *
     * @param rs
     *            the result set to get the summary data from
     *
     * @return the created transaction summary based on the result set
     *
     * @throws SQLException
     *             if any error occurred while trying to get any of the result set data
     */
    private static TransactionSummary getTransactionSummaryFromResultSet(ResultSet rs) throws SQLException {
        TransactionSummary summary = new TransactionSummary();
        summary.setTransactionID(rs.getLong("TXN_ID"));
        summary.setAchievementPostingMonth(getTrimmedStringFromRs("ACH_POSTING_MTH", rs));
        summary.setTransactionSummarizationSequenceNumber(rs.getShort("TXN_SUM_SEQ_NO"));
        summary.setEligibleEmployeeIncentiveElementKey(rs.getLong("EE_IE_APSK"));
        summary.setAccountYear(rs.getString("ACCT_YR").trim());
        summary.setAchievementEffectiveMonth(getTrimmedStringFromRs("ACH_EFF_MTH", rs));
        summary.setAssessmentIndicator(rs.getBoolean("AR_INDC"));
        summary.setClientID(rs.getString("CKR_CUST_USE3").trim());
        summary.setClientName(rs.getString("CLIENT_NAME"));
        summary.setContractNumber(getTrimmedStringFromRs("CONTRACTNUM", rs));
        summary.setInvoiceNumber(rs.getString("INV_NBR"));
        summary.setLinkTransactionID(rs.getLong("LINK_TXN_ID"));
        summary.setRecognizedAchievementAmount(rs.getDouble("RECG_ACHVT_AMT"));
        summary.setAssessorCountry(getTrimmedStringFromRs("ASSESSOR_CTY", rs));
        summary.setAssessorSerialNumber(getTrimmedStringFromRs("ASSESSOR_SERIAL_NO", rs));
        summary.setAssessorRole((UserRole) USER_ROLE_MAP.get(getTrimmedStringFromRs("ASSESSOR_ROLE", rs)));
        summary.setAssessmentStatus(rs.getString("ASSESS_STATUS").charAt(0));
        summary.setAssessmentDate(rs.getDate("ASSESS_DATE"));
        summary.setParticipationPerc(rs.getDouble("PART_PERC"));
        summary.setLastUpdateID(getTrimmedStringFromRs("LAST_UPT_ID", rs));
        summary.setAssessmentCommentText(getTrimmedStringFromRs("ASSESS_COMMENT_TEXT", rs));
        summary.setAssessmentOverrideText(getTrimmedStringFromRs("ASSESS_OVERRIDE_TEXT", rs));
        summary.setClientIDKey(getTrimmedStringFromRs("CKR_CUST_APSK", rs));
        summary.setChannelID(getTrimmedStringFromRs("CHANID", rs));
        summary.setBusinessMeasurementDivisionCode(getTrimmedStringFromRs("FIN_BMDIV", rs));

        return summary;
    }

    /**
     * <p>
     * Helper method to close the specified DB objects.
     * </p>
     *
     * @param stmt
     *            the PreparedStatement instance to close
     * @param connection
     *            the Connection to close
     * @param methodName
     *            the method name to be used for logging
     */
    private static void closeDBObjects(PreparedStatement stmt, Connection connection, String methodName) {
        // Close the prepared statement
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            Helper.logError(methodName, e, LOGGER);
        }

        // Close the connection
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            Helper.logError(methodName, e, LOGGER);
        }
    }
}
