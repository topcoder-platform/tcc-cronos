/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.util;

import java.util.Date;
import java.util.List;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionDetails;
import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.model.UserRole;

/**
 * <p>
 * This class provides a static method to convert a {@link TransactionSummary} entity to
 * the corresponding JSON string.
 * </p>
 *
 * <p>
 * Thread Safety: this class is immutable and thread safe, as long as the passed
 * transaction is not modified while converting it.
 * </p>
 *
 * @author caru, akinwale
 * @version 1.0
 */
public final class TransactionSummaryToJsonConverter {
    /**
     * <p>
     * The names of the {@link TransactionSummary} fields to be used when converting a
     * {@link TransactionSummary} instance to a JSON string.
     * </p>
     */
    private static final String[] TRANSACTION_SUMMARY_FIELD_NAMES = {
        "transactionID", "achievementPostingMonth", "transactionSummarizationSequenceNumber",
        "eligibleEmployeeIncentiveElementKey", "accountYear", "achievementEffectiveMonth",
        "assessmentIndicator", "clientID", "clientName", "contractNumber", "invoiceNumber",
        "linkTransactionID", "recognizedAchievementAmount", "assessorCountry", "assessorSerialNumber",
        "assessorRole", "assessmentStatus", "negativeAssessmentRequiringCode", "assessmentDate",
        "participationPerc", "updateAction", "assessmentCommentText", "assessmentOverrideText",
        "clientIDKey", "channelID", "businessMeasurementDivisionCode"
    };

    /**
     * <p>
     * The names of the {@link TransactionDetails} fields to be used when converting a
     * {@link TransactionDetails} instance to a JSON string.
     * </p>
     */
    private static final String[] TRANSACTION_DETAILS_FIELD_NAMES = {
        "accountYear", "clientIDKey", "customerName", "customerCountry", "customerNumber", "channelID",
        "channelDescription", "businessMeasurementDivisionCode", "brandName", "recognizedAchievementAmount"
    };

    /**
     * <p>
     * Private constructor to prevent initialization.
     * </p>
     */
    private TransactionSummaryToJsonConverter() {

    }

    /**
     * <p>
     * Convert a {@link TransactionSummary} entity to the corresponding JSON string.
     * </p>
     *
     * <p>
     * The output string will similar to the following format (without the indentation):
     * <pre>
     * {"transaction": {
     *   "transactionID": "1254",
     *   "achievementPostingMonth": "11",
     *   ...
     *   "transactionDetails": [
     *       {"accountYear": "2009", "clientIDKey": "CKey1", ...},
     *       {"accountYear": "2009", "clientIDKey": "CKey2", ...}
     *        ...
     *   ]
     * }}
     * </pre>
     * </p>
     *
     * @param transaction
     *            the entity to convert
     *
     * @return the JSON string representation
     */
    public static String convertTransactionToJson(TransactionSummary transaction) {
        String[] fieldValues = {
            String.valueOf(transaction.getTransactionID()), getString(transaction.getAchievementPostingMonth()),
            String.valueOf(transaction.getTransactionSummarizationSequenceNumber()),
            String.valueOf(transaction.getEligibleEmployeeIncentiveElementKey()),
            getString(transaction.getAccountYear()), getString(transaction.getAchievementEffectiveMonth()),
            String.valueOf(transaction.isAssessmentIndicator()), getString(transaction.getClientID()),
            getString(transaction.getClientName()), getString(transaction.getContractNumber()),
            getString(transaction.getInvoiceNumber()), String.valueOf(transaction.getLinkTransactionID()),
            String.valueOf(transaction.getRecognizedAchievementAmount()),
            getString(transaction.getAssessorCountry()), getString(transaction.getAssessorSerialNumber()),
            getString(transaction.getAssessorRole()), getString(transaction.getAssessmentStatus()),
            String.valueOf(transaction.isNegativeAssessmentRequiringCode()),
            getString(transaction.getAssessmentDate()), String.valueOf(transaction.getParticipationPerc()),
            getString(transaction.getUpdateAction()), getString(transaction.getAssessmentCommentText()),
            getString(transaction.getAssessmentOverrideText()), getString(transaction.getClientIDKey()),
            getString(transaction.getChannelID()), getString(transaction.getBusinessMeasurementDivisionCode())
        };

        // The transaction details list
        List transactionDetails = transaction.getTransactionDetails();

        // Build the JSON string
        StringBuffer sb = new StringBuffer("{\"transaction\": {");
        for (int i = 0; i < fieldValues.length; i++) {
            sb.append("\"").append(TRANSACTION_SUMMARY_FIELD_NAMES[i]).append("\": \"");
            sb.append(fieldValues[i]).append("\", ");
        }
        sb.append("\"transactionDetails\": [");
        if (transactionDetails != null) {
            for (int i = 0; i < transactionDetails.size(); i++) {
                sb.append(getJSONString((TransactionDetails) transactionDetails.get(i)));
                if (i != transactionDetails.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append("]}}");

        return sb.toString();
    }

    /**
     * <p>
     * Helper method to get a string value to be used in the convertTransactionToJson
     * method.
     * </p>
     *
     * @param value
     *            the value whose string representation should be obtained
     *
     * @return the string representation of the specified value
     */
    private static String getString(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof Date) {
            return ((Date) value).toString();
        } else if (value instanceof UserRole) {
            return value.toString();
        }

        String valueStr = ((String) value).replaceAll("\"", "\\\"");
        return valueStr;
    }

    /**
     * <p>
     * Helper method to get a string value from a char value to be used in the
     * convertTransactionToJson method.
     * </p>
     *
     * @param value
     *            the value whose string representation should be obtained
     *
     * @return the string representation of the specified value or an empty string if it
     *         is character 0
     */
    private static String getString(char value) {
        if (value == (char) 0) {
            return "";
        }

        return String.valueOf(value);
    }

    /**
     * <p>
     * Helper method to get the JSON representation of a {@link TransactionDetails}
     * instance.
     * </p>
     *
     * @param details
     *            the TransactionDetails instance to be converted to a JSON string
     *
     * @return the JSON string based on the specified TransactionDetails instance
     */
    private static String getJSONString(TransactionDetails details) {
        String[] fieldValues = {
            getString(details.getAccountYear()), getString(details.getClientIDKey()),
            getString(details.getCustomerName()), getString(details.getCustomerCountry()),
            getString(details.getCustomerNumber()), getString(details.getChannelID()),
            getString(details.getChannelDescription()), getString(details.getBusinessMeasurementDivisionCode()),
            getString(details.getBrandName()), String.valueOf(details.getRecognizedAchievementAmount())
        };

        StringBuffer sb = new StringBuffer("{");
        for (int i = 0; i < TRANSACTION_DETAILS_FIELD_NAMES.length; i++) {
            sb.append("\"").append(TRANSACTION_DETAILS_FIELD_NAMES[i]).append("\": ");
            sb.append("\"").append(fieldValues[i]).append("\"");
            if (i != TRANSACTION_DETAILS_FIELD_NAMES.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");

        return sb.toString();
    }
}
