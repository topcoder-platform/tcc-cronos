/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.persistence.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.commons.utils.JDBCUtility;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.Helper;
import com.topcoder.payments.amazonfps.PaymentNotFoundException;
import com.topcoder.payments.amazonfps.model.Payment;
import com.topcoder.payments.amazonfps.model.PaymentOperation;
import com.topcoder.payments.amazonfps.model.PaymentStatus;
import com.topcoder.payments.amazonfps.persistence.PaymentPersistence;
import com.topcoder.payments.amazonfps.persistence.PaymentPersistenceException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;

/**
 * <p>
 * This class is an implementation of {@code PaymentPersistence} that manages payment and payment operation data
 * in database persistence using JDBC and <i>DB Connection Factory</i> component. This class uses
 * <i>Logging Wrapper</i> component to log errors and debug information.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is mutable, but thread safe when {@code configure()} method is
 * called just once right after instantiation and entities passed to it are used by the caller in thread safe manner.
 * It uses thread safe {@code DBConnectionFactory} and {@code Log} instances.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public class DatabasePaymentPersistence extends BaseDatabasePersistence implements PaymentPersistence {
    /**
     * Represents the class name.
     */
    private static final String CLASS_NAME = DatabasePaymentPersistence.class.getName();

    /**
     * Constant for paymentIdGeneratorName parameter name.
     */
    private static final String PAYMENT_ID_GENERATOR_NAME_PARAMETER = "paymentIdGeneratorName";

    /**
     * Constant for paymentOperationIdGeneratorName parameter name.
     */
    private static final String PAYMENT_OPERATION_ID_GENERATOR_NAME_PARAMETER = "paymentOperationIdGeneratorName";

    /**
     * SQL for payment create operation.
     */
    private static final String CREATE_PAYMENT_SQL =
            "INSERT INTO payments (id, authorization_id, amount, transaction_id, status) VALUES (?, ?, ?, ?, ?)";

    /**
     * SQL for payment parameter create operation.
     */
    private static final String CREATE_PAYMENT_PARAMETER_SQL =
            "INSERT INTO payment_parameters (payment_id, key, value) VALUES (?, ?, ?)";

    /**
     * SQL for payment update operation.
     */
    private static final String UPDATE_PAYMENT_SQL =
            "UPDATE payments SET authorization_id = ?, amount = ?, transaction_id = ?, status = ? WHERE id = ?";

    /**
     * SQL for payment retrieving operation.
     */
    private static final String GET_PAYMENT_SQL =
            "SELECT authorization_id, amount, transaction_id, status FROM payments WHERE id = ?";

    /**
     * SQL for retrieving all payments.
     */
    private static final String GET_ALL_PAYMENTS_SQL =
            "SELECT id, authorization_id, amount, transaction_id, status FROM payments";

    /**
     * SQL for retrieving payments by authorization id.
     */
    private static final String GET_PAYMENTS_BY_AUTHORIZATION_SQL =
            "SELECT id, amount, transaction_id, status FROM payments WHERE authorization_id = ?";

    /**
     * SQL for payment parameters retrieving operation.
     */
    private static final String GET_PAYMENT_PARAMETERS_SQL =
            "SELECT key, value FROM payment_parameters WHERE payment_id = ?";

    /**
     * SQL for payment operation create operation.
     */
    private static final String CREATE_PAYMENT_OPERATION_SQL =
            "INSERT INTO payment_operations (id, payment_id, request_id, type, success) VALUES (?, ?, ?, ?, ?)";


    /**
     * The generator of payment IDs to be used in persistence. It is initialized in configure() and never changed after
     * that (assuming that configure() is called just once right after construction).
     * Cannot be {@code null} after initialization. It is used in createPayment().
     */
    private IDGenerator paymentIdGenerator;

    /**
     * The generator of payment operation IDs to be used in persistence. It is initialized in configure() and never
     * changed after that (assuming that configure() is called just once right after construction).
     * Cannot be {@code null} after initialization. It is used in createPaymentOperation().
     */
    private IDGenerator paymentOperationIdGenerator;

    /**
     * Constructs new {@code DatabasePaymentPersistence} instance.
     */
    public DatabasePaymentPersistence() {
        // Empty
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param configuration
     *            the configuration object
     *
     * @throws IllegalArgumentException
     *            if configuration is {@code null}
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *            if some error occurred when initializing an instance using the given configuration
     */
    @Override
    public void configure(ConfigurationObject configuration) {
        super.configure(configuration); // this also makes a check that configuration is not null

        try {
            // prepare id generator for payments
            String paymentIdGeneratorName = Helper.getProperty(configuration,
                    PAYMENT_ID_GENERATOR_NAME_PARAMETER, true);

            paymentIdGenerator = IDGeneratorFactory.getIDGenerator(paymentIdGeneratorName);

            // prepare id generator for payment operations
            String paymentOperationIdGeneratorName = Helper.getProperty(configuration,
                    PAYMENT_OPERATION_ID_GENERATOR_NAME_PARAMETER, true);

            paymentOperationIdGenerator = IDGeneratorFactory.getIDGenerator(paymentOperationIdGeneratorName);

        } catch (IDGenerationException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "Error occurred while retrieving ID sequence configuration", e);
        }
    }

    /**
     * Creates the payment with the given parameters in persistence. Sets a generated payment ID to the provided
     * {@code Payment} instance.
     *
     * @param payment
     *              the {@code Payment} instance which defines payment parameters
     *
     * @throws IllegalArgumentException
     *              if payment is {@code null}
     *              or {@code payment.getAuthorizationId() <= 0}
     *              or {@code payment.getAmount() == null}
     *              or {@code payment.getAmount().compareTo(BigDecimal.ZERO) <= 0}
     *              or {@code payment.getStatus() == null}
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the persistence
     */
    @Override
    public void createPayment(Payment payment) throws PaymentPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".createPayment(Payment payment)";

        // 1. log entrance and validate input parameters
        LoggingWrapperUtility.logEntrance(getLog(), signature,
                new String[] {"payment"}, new Object[] {String.valueOf(payment)});

        try {
            validateCreatePaymentParameters(payment);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        }

        // 2. create new payment record in persistence
        Connection connection = null;
        try {
            long paymentId = paymentIdGenerator.getNextID();
            connection = createConnection();
            connection.setAutoCommit(false);

            try {
                JDBCUtility.executeUpdate(connection, CREATE_PAYMENT_SQL,
                        new int[] {
                            Types.DECIMAL,
                            Types.DECIMAL,
                            Types.DECIMAL,
                            Types.VARCHAR,
                            Types.VARCHAR},
                        new Object[] {
                            paymentId,
                            payment.getAuthorizationId(),
                            payment.getAmount(),
                            payment.getTransactionId(),
                            payment.getStatus().toString()},
                        PaymentPersistenceException.class);

                if (payment.getParameters() != null) {
                    for (Map.Entry<String, String> entry : payment.getParameters().entrySet()) {
                        JDBCUtility.executeUpdate(connection, CREATE_PAYMENT_PARAMETER_SQL,
                                new int[] {
                                    Types.DECIMAL,
                                    Types.VARCHAR,
                                    Types.VARCHAR},
                                new Object[] {
                                    paymentId,
                                    entry.getKey(),
                                    entry.getValue()},
                                PaymentPersistenceException.class);
                    }
                }
                JDBCUtility.commitTransaction(connection, PaymentPersistenceException.class);
            } catch (PaymentPersistenceException e) {
                try {
                    JDBCUtility.rollbackTransaction(connection, PaymentPersistenceException.class);
                } catch (PaymentPersistenceException e2) { // Just log this exception
                    LoggingWrapperUtility.logException(getLog(), signature, e2, true, Level.ERROR);
                }
                throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
            }

            payment.setId(paymentId);
            LoggingWrapperUtility.logExit(getLog(), signature, null, entranceTimestamp);
        } catch (IDGenerationException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new PaymentPersistenceException(
                    "Failed to generate payment ID", e), true, Level.ERROR);
        } catch (DBConnectionException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new PaymentPersistenceException(
                    "Failed to create database connection", e), true, Level.ERROR);
        } catch (SQLException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new PaymentPersistenceException(
                    "Failed to turn off auto-commit mode", e), true, Level.ERROR);
        } finally {
            Helper.closeConnection(connection, getLog(), signature);
        }
    }

    /**
     * Validates parameters for createPayment() method.
     *
     * @param payment
     *              the {@code Payment} instance which defines payment parameters
     *
     * @throws IllegalArgumentException
     *              if payment is {@code null}
     *              or {@code payment.getAuthorizationId() <= 0}
     *              or {@code payment.getAmount() == null}
     *              or {@code payment.getAmount().compareTo(BigDecimal.ZERO) <= 0}
     *              or {@code payment.getStatus() == null}
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     */
    private void validateCreatePaymentParameters(Payment payment) {
        ParameterCheckUtility.checkNotNull(payment, "payment");
        ValidationUtility.checkPositive(payment.getAuthorizationId(), "payment's authorization id",
                IllegalArgumentException.class);
        ValidationUtility.checkNotNull(payment.getAmount(), "payment's amount",
                IllegalArgumentException.class);
        ValidationUtility.checkPositive(payment.getAmount().compareTo(BigDecimal.ZERO), "payment's amount",
                IllegalArgumentException.class);
        ValidationUtility.checkNotNull(payment.getStatus(), "payment's status",
                IllegalArgumentException.class);
        ValidationUtility.checkNotNull(paymentIdGenerator, "paymentIdGenerator",
                IllegalStateException.class);
    }

    /**
     * Updates the payment parameters in persistence. The {@code Payment} instance are not updated by this method.
     *
     * @param payment
     *              the {@code Payment} instance which defines updated payment parameters
     *
     * @throws IllegalArgumentException
     *              if payment is {@code null}
     *              or {@code payment.getId() <= 0}
     *              or {@code payment.getAuthorizationId() <= 0}
     *              or {@code payment.getAmount() == null}
     *              or {@code payment.getAmount().compareTo(BigDecimal.ZERO) <= 0}
     *              or {@code payment.getStatus() == null}
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     * @throws PaymentNotFoundException
     *              if payment with the specified ID doesn't exist
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the persistence
     */
    @Override
    public void updatePayment(Payment payment) throws PaymentNotFoundException, PaymentPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".updatePayment(Payment payment)";

        // log entrance and validate input parameters
        LoggingWrapperUtility.logEntrance(getLog(), signature,
                new String[] {"payment"},
                new Object[] {String.valueOf(payment)});

        try {
            validateUpdatePaymentParameters(payment);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        }

        // update payment record in persistence
        Connection connection = null;
        try {
            connection = createConnection();
            int result = JDBCUtility.executeUpdate(connection, UPDATE_PAYMENT_SQL,
                new int[] {
                    Types.DECIMAL,
                    Types.DECIMAL,
                    Types.VARCHAR,
                    Types.VARCHAR,
                    Types.DECIMAL},
                new Object[] {
                    payment.getAuthorizationId(),
                    payment.getAmount(),
                    payment.getTransactionId(),
                    payment.getStatus().toString(),
                    payment.getId()},
                PaymentPersistenceException.class);

            // check that record was updated
            if (result == 0) {
                throw LoggingWrapperUtility.logException(getLog(), signature, new PaymentNotFoundException(
                        "Payment is not found", payment.getId()), true, Level.ERROR);
            }
            // log exit
            LoggingWrapperUtility.logExit(getLog(), signature, null, entranceTimestamp);
        } catch (DBConnectionException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new PaymentPersistenceException(
                    "Failed to create database connection", e), true, Level.ERROR);
        } catch (PaymentPersistenceException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } finally {
            Helper.closeConnection(connection, getLog(), signature);
        }
    }

    /**
     * Validates parameters for updatePayment() method.
     *
     * @param payment
     *              the {@code Payment} instance which defines payment parameters
     *
     * @throws IllegalArgumentException
     *              if payment is {@code null}
     *              or {@code payment.getId() <= 0}
     *              or {@code payment.getAuthorizationId() <= 0}
     *              or {@code payment.getAmount() == null}
     *              or {@code payment.getAmount().compareTo(BigDecimal.ZERO) <= 0}
     *              or {@code payment.getStatus() == null}
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     */
    private void validateUpdatePaymentParameters(Payment payment) {
        validateCreatePaymentParameters(payment);

        // updatePayment() has additional check compared to createPayment()
        ValidationUtility.checkPositive(payment.getId(), "payment id",
                IllegalArgumentException.class);
    }

    /**
     * Retrieves the payment with the specified ID from persistence.
     *
     * @param paymentId
     *              the ID of the payment to be retrieved
     *
     * @return the retrieved payment data ({@code null} if payment with the specified ID doesn't exist)
     *
     * @throws IllegalArgumentException
     *              if {@code paymentId <= 0}
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the persistence
     */
    @Override
    public Payment getPayment(long paymentId) throws PaymentPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".getPayment(long paymentId)";

        // log entrance and validate input parameters
        LoggingWrapperUtility.logEntrance(getLog(), signature,
                new String[] {"paymentId"}, new Object[] {paymentId});

        try {
            ParameterCheckUtility.checkPositive(paymentId, "paymentId");
            ValidationUtility.checkNotNull(paymentIdGenerator, "paymentIdGenerator",
                    IllegalStateException.class);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        }

        Connection connection = null;
        try {
            connection = createConnection();
            Object[][] result = JDBCUtility.executeQuery(connection, GET_PAYMENT_SQL,
                    new int[] {Types.DECIMAL},
                    new Object[] {paymentId},
                    new Class<?>[] {Long.class, Double.class, String.class, String.class},
                    PaymentPersistenceException.class);

            Payment payment = null;
            if (result.length > 0) {
                final int statusIndex = 3;

                payment = new Payment();
                payment.setId(paymentId);
                payment.setAuthorizationId((Long) result[0][0]);
                payment.setAmount(BigDecimal.valueOf((Double) result[0][1]));
                payment.setTransactionId((String) result[0][2]);
                payment.setStatus(PaymentStatus.valueOf((String) result[0][statusIndex]));

                payment.setParameters(getPaymentParameters(connection, paymentId));
            }
            // log exit
            LoggingWrapperUtility.logExit(getLog(), signature, new Object[] {payment}, entranceTimestamp);
            return payment;
        } catch (DBConnectionException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new PaymentPersistenceException(
                    "Failed to create database connection", e), true, Level.ERROR);
        } catch (PaymentPersistenceException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } finally {
            Helper.closeConnection(connection, getLog(), signature);
        }
    }

    /**
     * Retrieves all payments from persistence. Returns an empty list if none found.
     *
     * @return the retrieved payments (not {@code null}, doesn't contain {@code null})
     *
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     * @throws PaymentPersistenceException
     *               if some error occurred when accessing the persistence
     */
    @Override
    public List<Payment> getAllPayments() throws PaymentPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".getAllPayments()";

        LoggingWrapperUtility.logEntrance(getLog(), signature, null, null);

        Connection connection = null;
        try {
            ValidationUtility.checkNotNull(paymentIdGenerator, "paymentIdGenerator",
                    IllegalStateException.class);

            connection = createConnection();
            Object[][] result = JDBCUtility.executeQuery(connection, GET_ALL_PAYMENTS_SQL,
                    new int[0], null,
                    new Class<?>[] {Long.class, Long.class, Double.class, String.class, String.class},
                    PaymentPersistenceException.class);

            final int authIdIndex = 1;
            final int amountIndex = 2;
            final int transactionIdIndex = 3;
            final int statusIndex = 4;

            // retrieve payments
            List<Payment> payments = new ArrayList<Payment>();
            for (Object[] row : result) {
                Payment payment = new Payment();

                payment.setId((Long) row[0]);
                payment.setAuthorizationId((Long) row[authIdIndex]);
                payment.setAmount(BigDecimal.valueOf((Double) row[amountIndex]));
                payment.setTransactionId((String) row[transactionIdIndex]);
                payment.setStatus(PaymentStatus.valueOf((String) row[statusIndex]));

                payments.add(payment);
            }

            // retrieve parameters for each payment (we do this in a separate loop for efficiency - at first
            // work with payments table and only then with parameters table).
            for (Payment payment : payments) {
                payment.setParameters(getPaymentParameters(connection, payment.getId()));
            }

            LoggingWrapperUtility.logExit(getLog(), signature, new Object[] {Helper.toString(payments)},
                    entranceTimestamp);
            return payments;
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (DBConnectionException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new PaymentPersistenceException(
                    "Failed to create database connection", e), true, Level.ERROR);
        } catch (PaymentPersistenceException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } finally {
            Helper.closeConnection(connection, getLog(), signature);
        }
    }

    /**
     * Retrieves all payments associated with authorization that has the specified ID from persistence. Returns an
     * empty list if none found.
     *
     * @param authorizationId
     *              the ID of the authorization for which payments should be retrieved
     *
     * @return the payments for the specified authorization (not {@code null}, doesn't contain {@code null})
     *
     * @throws IllegalArgumentException
     *              if {@code authorizationId <= 0}
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the persistence
     */
    @Override
    public List<Payment> getPaymentsByAuthorization(long authorizationId) throws PaymentPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".getPaymentsByAuthorization(long authorizationId)";

        LoggingWrapperUtility.logEntrance(getLog(), signature,
                new String[] {"authorizationId"}, new Object[] {authorizationId});

        Connection connection = null;
        try {
            ParameterCheckUtility.checkPositive(authorizationId, "authorizationId");
            ValidationUtility.checkNotNull(paymentIdGenerator, "paymentIdGenerator",
                    IllegalStateException.class);

            connection = createConnection();
            Object[][] result = JDBCUtility.executeQuery(connection, GET_PAYMENTS_BY_AUTHORIZATION_SQL,
                    new int[] {Types.DECIMAL},
                    new Object[] {authorizationId},
                    new Class<?>[] {Long.class, Double.class, String.class, String.class},
                    PaymentPersistenceException.class);

            final int amountIndex = 1;
            final int transactionIdIndex = 2;
            final int statusIndex = 3;

            // retrieve payments
            List<Payment> payments = new ArrayList<Payment>();
            for (Object[] row : result) {
                Payment payment = new Payment();

                payment.setId((Long) row[0]);
                payment.setAuthorizationId(authorizationId);
                payment.setAmount(BigDecimal.valueOf((Double) row[amountIndex]));
                payment.setTransactionId((String) row[transactionIdIndex]);
                payment.setStatus(PaymentStatus.valueOf((String) row[statusIndex]));

                payments.add(payment);
            }

            // retrieve parameters for each payment (we do this in a separate loop for efficiency - at first
            // work with payments table and only then with parameters table).
            for (Payment payment : payments) {
                payment.setParameters(getPaymentParameters(connection, payment.getId()));
            }

            LoggingWrapperUtility.logExit(getLog(), signature, new Object[] {Helper.toString(payments)},
                    entranceTimestamp);
            return payments;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (DBConnectionException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new PaymentPersistenceException(
                    "Failed to create database connection", e), true, Level.ERROR);
        } finally {
            Helper.closeConnection(connection, getLog(), signature);
        }
    }

    /**
     * Creates the payment operation with the given parameters in persistence. Sets a generated payment operation ID
     * to the provided {@code PaymentOperation} instance.
     *
     * @param paymentOperation
     *              the {@code PaymentOperation} instance which defines payment operation parameters
     *
     * @throws IllegalArgumentException
     *              if paymentOperation is {@code null}
     *              or {@code paymentOperation.getPaymentId() <= 0}
     *              or {@code paymentOperation.getType() == null}
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the persistence
     */
    @Override
    public void createPaymentOperation(PaymentOperation paymentOperation) throws PaymentPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".createPaymentOperation(PaymentOperation paymentOperation)";

        // log entrance and validate input parameters
        LoggingWrapperUtility.logEntrance(getLog(), signature,
                new String[] {"paymentOperation"},
                new Object[] {String.valueOf(paymentOperation)});

        try {
            ParameterCheckUtility.checkNotNull(paymentOperation, "paymentOperation");
            ValidationUtility.checkPositive(paymentOperation.getPaymentId(), "payment operation's payment id",
                    IllegalArgumentException.class);
            ValidationUtility.checkNotNull(paymentOperation.getType(), "payment operation type",
                    IllegalArgumentException.class);
            ValidationUtility.checkNotNull(paymentIdGenerator, "paymentIdGenerator",
                    IllegalStateException.class);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        }

        // create new payment operation record in persistence
        Connection connection = null;
        try {
            long paymentOperationId = paymentOperationIdGenerator.getNextID();
            connection = createConnection();
            JDBCUtility.executeUpdate(connection, CREATE_PAYMENT_OPERATION_SQL,
                    new int[] {
                        Types.DECIMAL,
                        Types.DECIMAL,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.DECIMAL},
                    new Object[] {
                        paymentOperationId,
                        paymentOperation.getPaymentId(),
                        paymentOperation.getRequestId(),
                        paymentOperation.getType().toString(),
                        paymentOperation.isSuccessful()},
                    PaymentPersistenceException.class);

            // set payment operation id to model object and log exit
            paymentOperation.setId(paymentOperationId);
            LoggingWrapperUtility.logExit(getLog(), signature, null, entranceTimestamp);
        } catch (IDGenerationException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new PaymentPersistenceException(
                    "Failed to payment operation ID", e), true, Level.ERROR);
        } catch (DBConnectionException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new PaymentPersistenceException(
                    "Failed to create database connection", e), true, Level.ERROR);
        } catch (PaymentPersistenceException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } finally {
            Helper.closeConnection(connection, getLog(), signature);
        }
    }

    /**
     * Retrieves payment parameters for the payment with the given ID (returns {@code null} if payment has no
     * associated parameters).
     *
     * @param connection
     *              the database connection for payment persistence
     * @param paymentId
     *              the ID of the payment for which payment parameters should be retrieved
     *
     * @return map of payment parameters for the given payment or {@code null} if payment has no associated parameters
     *
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the persistence
     */
    private static Map<String, String> getPaymentParameters(Connection connection, long paymentId)
        throws PaymentPersistenceException {
        Object[][] result = JDBCUtility.executeQuery(connection, GET_PAYMENT_PARAMETERS_SQL,
                new int[] {Types.DECIMAL},
                new Object[] {paymentId},
                new Class<?>[] {String.class, String.class},
                PaymentPersistenceException.class);

        if (result.length > 0) {
            Map<String, String> parameters = new HashMap<String, String>();
            for (Object[] keyValue : result) {
                parameters.put((String) keyValue[0], (String) keyValue[1]);
            }
            return parameters;
        }
        return null;
    }
}
