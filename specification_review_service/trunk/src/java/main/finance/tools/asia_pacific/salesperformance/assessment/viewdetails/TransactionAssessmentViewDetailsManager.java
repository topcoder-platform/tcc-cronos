/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.
    TransactionAssessmentViewDetailsConfigurationException;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.
    TransactionAssessmentViewDetailsDaoException;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception.
    TransactionAssessmentViewDetailsManagerException;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.persist.
    TransactionAssessmentViewDetailsDaoImpl;
import finance.tools.asia_pacific.salesperformance.base.exception.FMSCriticalException;
import finance.tools.asia_pacific.salesperformance.base.util.Configuration;

/**
 * <p>
 * This class manage the retrieval of transactions and transaction details.
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
 * Thread Safety: This class is mutable and not thread safe. If its configuration setters
 * are used only before business usage, then this class can be used in a thread safe
 * manner, because it relies on DAO thread safety.
 * </p>
 *
 * <p>
 * <strong>Sample Code Usage</strong> <code>
 * // Create a new instance
 * TransactionAssessmentViewDetailsManager manager = new TransactionAssessmentViewDetailsManager();
 *
 * // Retrieve transaction summaries by eligible employee incentive element keys
 * TransactionSummary[] summaries;
 * summaries = manager.retrieveTransactionsByEmployeeIncentiveElementKeys(new long[] {101, 102});
 *
 * // Retrieve transaction summaries by transaction IDs
 * summaries = manager.retrieveTransactionsByTransactionNumbers(new short[] {1, 2});
 *
 * // Retrieve transaction summaries by incentive element keys with the transactions details list populated
 * summaries = manager.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(new long[] {103, 104});
 *
 * // Retrieve transaction summaries by transaction IDs with the transaction details list populated
 * summaries = manager.retrieveTransactionsByTransactionNumbers(new short[] {3, 4});
 *
 * // Retrieve a transaction summary with the transaction details populated
 * TransactionSummary transaction = new TransactionSummary();
 * transaction.setTransactionID(1);
 * transaction.setAchievementPostingMonth("01");
 * transaction.setTransactionSummarizationSequenceNumber((short) 1);
 * TransactionSummary transactionWithDetails = manager.retrieveTransactionDetails(transaction);
 *
 * // Get the DAO field
 * TransactionAssessmentViewDetailsDao dao = manager.getDao();
 *
 * // Set the DAO field
 * manager.setDao(new TransactionAssessmentViewDetailsDaoImpl());
 * </code>
 * </p>
 *
 * <p>
 * <strong>Sample Configuration</strong> <code>
 * driverClassName=com.ibm.db2.jcc.DB2Driver
 * db.url=jdbc:db2://localhost:50000/TCDB:currentSchema=TOPCODER;
 * db.userid=topcoder
 * db.password=topcoder
 * daoClassName=finance.tools.asia_pacific.salesperformance.assessment.viewdetails.persist.
 *     TransactionAssessmentViewDetailsDaoImpl
 * </code>
 * </p>
 *
 * @author caru, akinwale
 * @version 1.0
 */
public class TransactionAssessmentViewDetailsManager {
    /**
     * <p>
     * The class name to be used for logging.
     * </p>
     */
    private static final String CLASS_NAME = TransactionAssessmentViewDetailsManager.class.getName();

    /**
     * <p>
     * Logger instance. Initialized to default logger for this class, cannot be null,
     * never changes. Used by business methods.
     * </p>
     */
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    /**
     * <p>
     * Represents the configuration key for the DAO class name.
     * </p>
     */
    private static final String DAO_CLASS_KEY = "daoClassName";

    /**
     * <p>
     * Represents the default DAO class if a DAO class is not configured.
     * </p>
     */
    private static final String DEFAULT_DAO_CLASS = TransactionAssessmentViewDetailsDaoImpl.class.getName();

    /**
     * <p>
     * The DAO instance to which all operations are delegated. Initialized in constructor,
     * can change, can never be null. Accessed by getter and setter.
     * </p>
     */
    private TransactionAssessmentViewDetailsDao dao;

    /**
     * <p>
     * Default constructor. Creates a new instance.
     * </p>
     *
     * @throws TransactionAssessmentViewDetailsConfigurationException
     *             if there is any error while initializing the instance field
     */
    public TransactionAssessmentViewDetailsManager() throws TransactionAssessmentViewDetailsConfigurationException {
        String methodName = CLASS_NAME + "#TransactionAssessmentViewDetailsManager";
        long startTime = Helper.logEntry(methodName, null, null, LOGGER);

        try {
            // Get the configured DAO class name
            String daoClass = Configuration.getProperty(DAO_CLASS_KEY, DEFAULT_DAO_CLASS);
            if (daoClass.trim().length() == 0) {
                throw (TransactionAssessmentViewDetailsConfigurationException) Helper.logError(methodName,
                    new TransactionAssessmentViewDetailsConfigurationException(
                        "The configured DAO class name cannot be an empty string",
                        CLASS_NAME, null, methodName, null), LOGGER);
            }

            // Create the DAO instance using reflection
            dao = (TransactionAssessmentViewDetailsDao) createInstance(daoClass, new Class[0], new Object[0]);
        } catch (TransactionAssessmentViewDetailsConfigurationException e) {
            // log and re-throw
            throw (TransactionAssessmentViewDetailsConfigurationException) Helper.logError(methodName, e, LOGGER);
        } catch (ClassCastException e) {
            throw (TransactionAssessmentViewDetailsConfigurationException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsConfigurationException(
                    "The configured DAO instance does not implement the DAO interface",
                    CLASS_NAME, e, methodName, null), LOGGER);
        } catch (FMSCriticalException e) {
            throw (TransactionAssessmentViewDetailsConfigurationException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsConfigurationException(
                    "An error occurred while trying to get the configuration value for [" + DAO_CLASS_KEY + "]",
                    CLASS_NAME, e, methodName, null), LOGGER);
        }

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
     * @throws TransactionAssessmentViewDetailsManagerException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsByEmployeeIncentiveElementKeys(
        long[] eligibleEmployeeIncentiveElementKeys) throws TransactionAssessmentViewDetailsManagerException {
        String methodName = CLASS_NAME + "#retrieveTransactionsByEmployeeIncentiveElementKeys";
        long startTime = Helper.logEntry(methodName, new String[] {"eligibleEmployeeIncentiveElementKeys"},
            new Object[] {eligibleEmployeeIncentiveElementKeys}, LOGGER);

        TransactionSummary[] result;
        try {
            result = dao.retrieveTransactionsByEmployeeIncentiveElementKeys(eligibleEmployeeIncentiveElementKeys);
        } catch (TransactionAssessmentViewDetailsDaoException e) {
            throw (TransactionAssessmentViewDetailsManagerException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsManagerException(
                    "An error occurred while trying to retrieve the transaction summaries",
                    CLASS_NAME, e, methodName, null), LOGGER);
        }

        Helper.logExit(methodName, result, startTime, LOGGER);
        return result;
    }

    /**
     * <p>
     * Retrieve all TransactionSummaries with the given transaction numbers.
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
     * @throws TransactionAssessmentViewDetailsManagerException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsByTransactionNumbers(short[] transactionNumbers)
        throws TransactionAssessmentViewDetailsManagerException {
        String methodName = CLASS_NAME + "#retrieveTransactionsByTransactionNumbers";
        long startTime = Helper.logEntry(methodName, new String[] {"transactionNumbers"},
            new Object[] {transactionNumbers}, LOGGER);

        TransactionSummary[] result;
        try {
            result = dao.retrieveTransactionsByTransactionNumbers(transactionNumbers);
        } catch (TransactionAssessmentViewDetailsDaoException e) {
            throw (TransactionAssessmentViewDetailsManagerException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsManagerException(
                    "An error occurred while trying to retrieve the transaction summaries",
                    CLASS_NAME, e, methodName, null), LOGGER);
        }

        Helper.logExit(methodName, result, startTime, LOGGER);
        return result;
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
     * @throws TransactionAssessmentViewDetailsManagerException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(
        long[] eligibleEmployeeIncentiveElementKeys) throws TransactionAssessmentViewDetailsManagerException {
        String methodName = CLASS_NAME + "#retrieveTransactionsDetailsByEmployeeIncentiveElementKeys";
        long startTime = Helper.logEntry(methodName, new String[] {"eligibleEmployeeIncentiveElementKeys"},
            new Object[] {eligibleEmployeeIncentiveElementKeys}, LOGGER);

        TransactionSummary[] result;
        try {
            result = dao.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(
                eligibleEmployeeIncentiveElementKeys);
        } catch (TransactionAssessmentViewDetailsDaoException e) {
            throw (TransactionAssessmentViewDetailsManagerException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsManagerException(
                    "An error occurred while trying to retrieve the transaction summaries",
                    CLASS_NAME, e, methodName, null), LOGGER);
        }

        Helper.logExit(methodName, result, startTime, LOGGER);
        return result;
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
     * @throws TransactionAssessmentViewDetailsManagerException
     *             if any errors occurs
     */
    public TransactionSummary[] retrieveTransactionsDetailsByTransactionNumbers(short[] transactionNumbers)
        throws TransactionAssessmentViewDetailsManagerException {
        String methodName = CLASS_NAME + "#retrieveTransactionsDetailsByTransactionNumbers";
        long startTime = Helper.logEntry(methodName, new String[] {"transactionNumbers"},
            new Object[] {transactionNumbers}, LOGGER);

        TransactionSummary[] result;
        try {
            result = dao.retrieveTransactionsDetailsByTransactionNumbers(transactionNumbers);
        } catch (TransactionAssessmentViewDetailsDaoException e) {
            throw (TransactionAssessmentViewDetailsManagerException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsManagerException(
                    "An error occurred while trying to retrieve the transaction summaries",
                    CLASS_NAME, e, methodName, null), LOGGER);
        }

        Helper.logExit(methodName, result, startTime, LOGGER);
        return result;
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
     *         transaction is found with the given primary keys.
     *
     * @throws IllegalArgumentException
     *             if the argument is null or if its transactionId,
     *             achievementPostingMonth or transactionSummarizationSequenceNumber has
     *             not been set
     * @throws TransactionAssessmentViewDetailsManagerException
     *             if any errors occurs
     */
    public TransactionSummary retrieveTransactionDetails(TransactionSummary transaction)
        throws TransactionAssessmentViewDetailsManagerException {
        String methodName = CLASS_NAME + "#retrieveTransactionDetails";
        long startTime = Helper.logEntry(methodName, new String[] {"transaction"}, new Object[] {transaction}, LOGGER);

        TransactionSummary result;
        try {
            result = dao.retrieveTransactionDetails(transaction);
        } catch (TransactionAssessmentViewDetailsDaoException e) {
            throw (TransactionAssessmentViewDetailsManagerException) Helper.logError(methodName,
                new TransactionAssessmentViewDetailsManagerException(
                    "An error occurred while trying to retrieve the transaction summaries",
                    CLASS_NAME, e, methodName, null), LOGGER);
        }

        Helper.logExit(methodName, result, startTime, LOGGER);
        return result;
    }

    /**
     * <p>
     * Getter for the view details DAO instance.
     * </p>
     *
     * @return the view details DAO instance
     */
    public TransactionAssessmentViewDetailsDao getDao() {
        return dao;
    }

    /**
     * <p>
     * Setter for the view details DAO instance.
     * </p>
     *
     * @param dao
     *            the view details DAO instance to set
     *
     * @throws IllegalArgumentException
     *             if the argument is null
     */
    public void setDao(TransactionAssessmentViewDetailsDao dao) {
        Helper.checkNull(dao, "dao", null, null);
        this.dao = dao;
    }

    /**
     * <p>
     * This is a helper method that creates an object reflectively by calling its
     * constructor with signature given by ctorTypes and params given by params.
     * </p>
     *
     * @param className
     *            the full class name
     * @param ctorTypes
     *            the constructor signature
     * @param params
     *            the parameters to be passed into the constructor
     *
     * @return the created object
     *
     * @throws TransactionAssessmentViewDetailsConfigurationException
     *             if any error occurs during creating the object
     */
    private static Object createInstance(String className, Class[] ctorTypes, Object[] params)
        throws TransactionAssessmentViewDetailsConfigurationException {
        String methodName = CLASS_NAME + "#createInstance";
        Object object = null;
        try {
            Class klass = Class.forName(className);
            Constructor constructor = klass.getConstructor(ctorTypes);
            object = constructor.newInstance(params);
        } catch (ClassNotFoundException e) {
            throw new TransactionAssessmentViewDetailsConfigurationException(
                "The specified class [" + className + "] could not be found", CLASS_NAME, e, methodName, null);
        } catch (SecurityException e) {
            throw new TransactionAssessmentViewDetailsConfigurationException(
                "A security manager error occurred while trying to create an instance of [" + className + "]",
                CLASS_NAME, e, methodName, null);
        } catch (NoSuchMethodException e) {
            throw new TransactionAssessmentViewDetailsConfigurationException(
                "The default constructor for the class [" + className + "] could not be found",
                CLASS_NAME, e, methodName, null);
        } catch (InstantiationException e) {
            throw new TransactionAssessmentViewDetailsConfigurationException(
                "Could not instantiate the class [" + className + "]", CLASS_NAME, e, methodName, null);
        } catch (IllegalAccessException e) {
            throw new TransactionAssessmentViewDetailsConfigurationException(
                "An error due to illegal access occurred while trying to create an instance of [" + className + "]",
                CLASS_NAME, e, methodName, null);
        } catch (InvocationTargetException e) {
            throw new TransactionAssessmentViewDetailsConfigurationException(
                "An internal error occurred while trying to create an instance of [" + className + "]",
                CLASS_NAME, e, methodName, null);
        }

        return object;
    }
}
