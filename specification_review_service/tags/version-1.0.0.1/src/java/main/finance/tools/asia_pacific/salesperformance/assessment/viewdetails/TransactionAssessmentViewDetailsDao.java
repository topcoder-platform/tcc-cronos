/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.
    TransactionAssessmentViewDetailsDaoException;

/**
 * <p>
 * This interface defines the contract for the transaction assessment view details DAO.
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
 * Thread safety Implementations are required to be thread-safe. No transactions are
 * needed because database usage is read-only.
 * </p>
 *
 * @author caru, akinwale
 * @version 1.0
 */
public interface TransactionAssessmentViewDetailsDao {
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
     *             if any argument is not valid
     * @throws TransactionAssessmentViewDetailsDaoException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsByEmployeeIncentiveElementKeys(
        long[] eligibleEmployeeIncentiveElementKeys) throws TransactionAssessmentViewDetailsDaoException;

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
     *             if any argument is not valid
     * @throws TransactionAssessmentViewDetailsDaoException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsByTransactionNumbers(short[] transactionNumbers)
        throws TransactionAssessmentViewDetailsDaoException;

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
     *             if the argument is not valid
     * @throws TransactionAssessmentViewDetailsDaoException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(
        long[] eligibleEmployeeIncentiveElementKeys) throws TransactionAssessmentViewDetailsDaoException;

    /**
     * <p>
     * Retrieve all TransactionSummaries (filled with TransactionDetails list) with the
     * given transaction numbers.
     * </p>
     *
     * @param transactionNumbers
     *            the given transaction numbers, cannot be null or empty, element can be
     *            any value
     * @return all found TransactionSummaries, cannot be null, can be empty, elements
     *         cannot be null
     *
     * @throws IllegalArgumentException
     *             if any argument is not valid
     * @throws TransactionAssessmentViewDetailsDaoException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsDetailsByTransactionNumbers(short[] transactionNumbers)
        throws TransactionAssessmentViewDetailsDaoException;

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
     *             if any argument is not valid
     * @throws TransactionAssessmentViewDetailsDaoException
     *             if any errors occurs
     */
    public TransactionSummary retrieveTransactionDetails(TransactionSummary transaction)
        throws TransactionAssessmentViewDetailsDaoException;
}
