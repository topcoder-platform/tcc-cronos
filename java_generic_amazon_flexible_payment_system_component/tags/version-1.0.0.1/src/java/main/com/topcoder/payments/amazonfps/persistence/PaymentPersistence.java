/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.persistence;

import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.payments.amazonfps.PaymentNotFoundException;
import com.topcoder.payments.amazonfps.model.Payment;
import com.topcoder.payments.amazonfps.model.PaymentOperation;

/**
 * <p>
 * The {@code PaymentPersistence} interface represents a payment persistence. It defines methods for creating,
 * updating and retrieving payments in/from persistence. Additionally it defines a method for auditing payment
 * operations.
 * </p>
 *
 * <strong>Thread Safety:</strong> Implementations of this interface are required to be thread safe when the following
 * conditions are met:
 * <ul>
 * <li>{@code configure()} method is called just once right after instantiation</li>
 * <li>entities passed to this interface are used by the caller in thread safe manner.</li>
 * </ul>
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public interface PaymentPersistence {
    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param configuration
     *              the configuration object
     *
     * @throws IllegalArgumentException
     *              if configuration is {@code null} (it is not thrown by implementations that don't use any
     *              configuration parameters)
     * @throws com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException
     *              if some error occurred when initializing an instance using the given configuration
     */
    public void configure(ConfigurationObject configuration);

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
    public void createPayment(Payment payment) throws PaymentPersistenceException;

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
    public void updatePayment(Payment payment) throws PaymentNotFoundException, PaymentPersistenceException;

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
    public Payment getPayment(long paymentId) throws PaymentPersistenceException;

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
    public List<Payment> getAllPayments() throws PaymentPersistenceException;

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
    public List<Payment> getPaymentsByAuthorization(long authorizationId) throws PaymentPersistenceException;

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
    public void createPaymentOperation(PaymentOperation paymentOperation) throws PaymentPersistenceException;
}
