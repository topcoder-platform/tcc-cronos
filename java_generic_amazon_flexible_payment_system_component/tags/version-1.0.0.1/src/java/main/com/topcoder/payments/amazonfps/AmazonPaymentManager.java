/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import java.util.List;
import java.util.Map;

import com.topcoder.payments.amazonfps.model.Authorization;
import com.topcoder.payments.amazonfps.model.Payment;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationData;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationRequest;
import com.topcoder.payments.amazonfps.model.PaymentDetails;
import com.topcoder.payments.amazonfps.subscribers.PaymentEventSubscriber;

/**
 * <p>
 * This interface represents an Amazon payment manager. It defines methods that make usage of <i>Amazon Co-Branded
 * API</i> easier when performing single-use or multiple-use payment authorization. Also it defines methods for
 * performing reserving, cancelling, settling and refunding payments with use of <i>Amazon FPS Service</i> after the
 * authorization is performed.
 * </p>
 *
 * <strong>Thread Safety:</strong> Implementations of this interface are required to be thread safe when entities
 * passed to them are used by the caller in thread safe manner.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public interface AmazonPaymentManager {
    /**
     * Initiates the payment authorization. Prepares the authorization URL to be used for redirecting the client.
     *
     * @param request
     *            the payment authorization request data
     *
     * @return the prepared payment authorization data (not {@code null})
     *
     * @throws IllegalArgumentException
     *            if request is {@code null} <br/>
     *            or request.getRedirectUrl() is {@code null} or empty <br/>
     *            or request.getPaymentDetails() is {@code null} <br/>
     *            or request.getPaymentDetails().getAmount() is {@code null} or not positive <br/>
     *            or request.getPaymentDetails().getParameters() contains {@code null} key <br/>
     *
     *            or request.isFutureChargesAuthorizationRequired()
     *                and request.getTotalChargesThreshold() is {@code null} <br/>
     *
     *            or request.getTotalChargesThreshold() not {@code null}
     *                and request.getTotalChargesThreshold() is not positive <br/>
     *
     *            or request.getFutureChargesFixedAmount() not {@code null}
     *                and request.getFutureChargesFixedAmount() is not positive
     *
     * @throws AmazonFlexiblePaymentManagementException
     *            if some critical error occurred
     */
    public PaymentAuthorizationData initiatePaymentAuthorization(PaymentAuthorizationRequest request)
        throws AmazonFlexiblePaymentManagementException;

    /**
     * Handles the request from the <i>Amazon Co-Branded Service</i>. Checks the authorization response parameters
     * and performs the reservation or payment if authorization was successful.
     *
     * @param requestParams
     *            the map with HTTP request parameters
     *
     * @throws IllegalArgumentException
     *            if requestParams is {@code null}
     * @throws AmazonFlexiblePaymentManagementException
     *            if some critical error occurred
     */
    public void handleRequestFromCoBrandedService(Map<Object, Object> requestParams)
        throws AmazonFlexiblePaymentManagementException;

    /**
     * Processes the previously authorized payment. This method can be additionally used for making the payment
     * reservation (see PaymentDetails#reservation property).
     *
     * @param authorizationId
     *            the ID of the authorization
     * @param paymentDetails
     *            the payment details
     *
     * @return the generated payment ID
     *
     * @throws IllegalArgumentException
     *            if authorizationId is not positive <br/>
     *            or paymentDetails is {@code null} <br/>
     *            or paymentDetails.getAmount() is {@code null} or not positive <br/>
     *            or paymentDetails.getParameters() contains {@code null} key
     *
     * @throws AuthorizationNotFoundException
     *            if authorization with the specified ID doesn't exist
     * @throws AmazonFlexiblePaymentManagementException
     *            if some other critical error occurred
     */
    public long processAuthorizedPayment(long authorizationId, PaymentDetails paymentDetails)
        throws AmazonFlexiblePaymentManagementException;

    /**
     * Cancels the payment with the specified ID. This ID is returned from initiatePaymentAuthorization() and
     * processAuthorizedPayment() methods. Note that completed payment cannot be cancelled, it should be refunded
     * instead.
     *
     * @param paymentId
     *            the ID of the payment to be cancelled
     *
     * @throws IllegalArgumentException
     *            if paymentId is not positive
     * @throws PaymentNotFoundException
     *            if payment with the specified ID doesn't exist
     * @throws AmazonFlexiblePaymentManagementException
     *            if some critical error occurred
     */
    public void cancelPayment(long paymentId) throws AmazonFlexiblePaymentManagementException;

    /**
     * Settles the previously reserved payment with the specified ID. This ID is returned from
     * initiatePaymentAuthorization() and processAuthorizedPayment() methods.
     *
     * @param paymentId
     *            the ID of the payment to be settled
     *
     * @throws IllegalArgumentException
     *            if paymentId is not positive
     * @throws PaymentNotFoundException
     *            if payment with the specified ID doesn't exist
     * @throws AmazonFlexiblePaymentManagementException
     *            if some critical error occurred
     */
    public void settlePayment(long paymentId) throws AmazonFlexiblePaymentManagementException;

    /**
     * Refunds the previously completed payment with the specified ID. This ID is returned from
     * initiatePaymentAuthorization() and processAuthorizedPayment() methods.
     *
     * @param paymentId
     *            the ID of the payment to be refunded
     *
     * @throws IllegalArgumentException
     *            if paymentId is not positive
     * @throws PaymentNotFoundException
     *            if payment with the specified ID doesn't exist
     * @throws AmazonFlexiblePaymentManagementException
     *            if some critical error occurred
     */
    public void refundPayment(long paymentId) throws AmazonFlexiblePaymentManagementException;

    /**
     * Cancels the authorization with the specified ID. This ID is returned from initiatePaymentAuthorization() method.
     *
     * @param authorizationId
     *            the ID of the authorization to be cancelled
     *
     * @throws IllegalArgumentException
     *            if authorizationId is not positive
     * @throws AuthorizationNotFoundException
     *            if authorization with the specified ID doesn't exist
     * @throws AmazonFlexiblePaymentManagementException
     *            if some other critical error occurred
     */
    public void cancelAuthorization(long authorizationId) throws AmazonFlexiblePaymentManagementException;

    /**
     * Registers the given payment event subscriber so that it can receive payment event notifications from this
     * manager.
     *
     * @param subscriber
     *            the payment event subscriber to be registered
     *
     * @throws IllegalArgumentException
     *            if subscriber is {@code null}
     */
    public void registerPaymentEventSubscriber(PaymentEventSubscriber subscriber);

    /**
     * Retrieves all authorizations. Returns an empty list of none found.
     *
     * @return the retrieved authorizations (not {@code null}, doesn't contain {@code null})
     *
     * @throws AmazonFlexiblePaymentManagementException
     *              if some error occurred when retrieving authorizations
     */
    public List<Authorization> getAllAuthorizations() throws AmazonFlexiblePaymentManagementException;

    /**
     * Retrieves all payments. Returns an empty list if none found.
     *
     * @return the retrieved payments (not {@code null}, doesn't contain {@code null})
     *
     * @throws AmazonFlexiblePaymentManagementException
     *              if some error occurred when retrieving payments
     */
    public List<Payment> getAllPayments() throws AmazonFlexiblePaymentManagementException;

    /**
     * Retrieves all payments associated with authorization that has the specified ID. Returns an empty list if
     * none found.
     *
     * @param authorizationId
     *              the ID of the authorization for which payments should be retrieved
     *
     * @return the payments for the specified authorization (not {@code null}, doesn't contain {@code null})
     *
     * @throws IllegalArgumentException
     *              if {@code authorizationId <= 0}
     * @throws AmazonFlexiblePaymentManagementException
     *              if some error occurred when retrieving payments
     */
    public List<Payment> getPaymentsByAuthorization(long authorizationId)
        throws AmazonFlexiblePaymentManagementException;
}
