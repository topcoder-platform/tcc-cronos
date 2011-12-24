/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.topcoder.payments.amazonfps.Helper;

/**
 * <p>
 * This class is a container for information about a single payment authorization request. It holds information
 * provided by the consuming application.
 * </p>
 *
 * <p>
 * It is a simple JavaBean (POJO) that provides getters and setters for all private attributes and performs no
 * argument validation in the setters.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public class PaymentAuthorizationRequest implements Serializable {
    /**
     * The class's serialVersionUID for the serialization runtime.
     */
    private static final long serialVersionUID = -6763049849577470851L;

    /**
     * The redirection URL to be used when authorization is complete or failed.
     * Can be any value. Has getter and setter.
     */
    private String redirectUrl;

    /**
     * The payment details for the immediate payment to be performed right after the authorization.
     * Can be any value. Has getter and setter.
     */
    private PaymentDetails paymentDetails;

    /**
     * The value indicating whether the future charges must be additionally authorized. If false, authorization
     * is performed for a single payment only. Can be any value. Has getter and setter.
     */
    private boolean futureChargesAuthorizationRequired;

    /**
     * The total charges threshold amount in USD. Must be specified when {@code futureChargesAuthorizationRequired}
     * is {@code true}. This amount should include the first charge and all expected future charges.
     * Can be any value. Has getter and setter.
     */
    private BigDecimal totalChargesThreshold;

    /**
     * The fixed amount for future charges. It is ignored when {@code futureChargesAuthorizationRequired} is
     * {@code false}. When {@code futureChargesAuthorizationRequired} is {@code true} and this property is not
     * specified, variable future charges are expected to be allowed. Can be any value. Has getter and setter.
     */
    private BigDecimal futureChargesFixedAmount;

    /**
     * Constructs new {@code PaymentAuthorizationRequest} instance.
     */
    public PaymentAuthorizationRequest() {
        // Empty
    }

    /**
     * Retrieves the redirect URL to be used when authorization is complete or failed.
     *
     * @return the redirect URL to be used when authorization is complete or failed
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Sets the redirect URL to be used when authorization is complete or failed.
     *
     * @param redirectUrl
     *              the redirect URL to be used when authorization is complete or failed
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    /**
     * Retrieves the payment details for the immediate payment to be performed right after the authorization.
     *
     * @return the payment details for the immediate payment to be performed right after the authorization
     */
    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    /**
     * Sets the payment details for the immediate payment to be performed right after the authorization.
     *
     * @param paymentDetails
     *              the payment details for the immediate payment to be performed right after the authorization
     */
    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    /**
     * Retrieves the value indicating whether the future charges must be additionally authorized.
     *
     * @return the value indicating whether the future charges must be additionally authorized
     */
    public boolean isFutureChargesAuthorizationRequired() {
        return futureChargesAuthorizationRequired;
    }

    /**
     * Sets the value indicating whether the future charges must be additionally authorized.
     *
     * @param futureChargesAuthorizationRequired
     *              the value indicating whether the future charges must be additionally authorized
     */
    public void setFutureChargesAuthorizationRequired(boolean futureChargesAuthorizationRequired) {
        this.futureChargesAuthorizationRequired = futureChargesAuthorizationRequired;
    }

    /**
     * Retrieves the total charges threshold amount in USD.
     *
     * @return the total charges threshold amount in USD
     */
    public BigDecimal getTotalChargesThreshold() {
        return totalChargesThreshold;
    }

    /**
     * Sets the total charges threshold amount in USD.
     *
     * @param totalChargesThreshold
     *              the total charges threshold amount in USD
     */
    public void setTotalChargesThreshold(BigDecimal totalChargesThreshold) {
        this.totalChargesThreshold = totalChargesThreshold;
    }

    /**
     * Retrieves the future charges fixed amount.
     *
     * @return the future charges fixed amount
     */
    public BigDecimal getFutureChargesFixedAmount() {
        return futureChargesFixedAmount;
    }

    /**
     * Sets the future charges fixed amount.
     *
     * @param futureChargesFixedAmount
     *              the future charges fixed amount
     */
    public void setFutureChargesFixedAmount(BigDecimal futureChargesFixedAmount) {
        this.futureChargesFixedAmount = futureChargesFixedAmount;
    }

    /**
     * Converts this object to string presentation.
     *
     * @return string presentation of this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{redirectUrl: ").append(Helper.toString(redirectUrl))
          .append(", paymentDetails: ").append(Helper.toString(paymentDetails))
          .append(", futureChargesAuthorizationRequired: ").append(futureChargesAuthorizationRequired)
          .append(", totalChargesThreshold: ").append(Helper.toString(totalChargesThreshold))
          .append(", futureChargesFixedAmount: ").append(Helper.toString(futureChargesFixedAmount))
          .append("}");
        return sb.toString();
    }
}
