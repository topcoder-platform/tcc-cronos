/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.io.Serializable;

import com.topcoder.payments.amazonfps.Helper;

/**
 * <p>
 * This class is a container for information that can be used by the user of this component for performing the
 * payment authorization and initiating future payments.
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
public class PaymentAuthorizationData implements Serializable {
    /**
     * The class's serialVersionUID for the serialization runtime.
     */
    private static final long serialVersionUID = 6808428510300112543L;

    /**
     * The URL to which the client should be redirected to perform the payment authorization. This URL points to
     * <i>Amazon Co-Branded Service</i>. Can be any value. Has getter and setter.
     */
    private String authorizationUrl;

    /**
     * The generated ID for the authorization. It can be used later when performing future authorized payments.
     * Can be any value. Has getter and setter.
     */
    private long authorizationId;

    /**
     * The generated ID of the payment. It can be used later when cancelling, settling or refunding the payment.
     * Can be any value. Has getter and setter.
     */
    private long paymentId;

    /**
     * Constructs new {@code PaymentAuthorizationData} instance.
     */
    public PaymentAuthorizationData() {
        // Empty
    }

    /**
     * Retrieves the URL to which the client should be redirected to perform the payment authorization.
     *
     * @return the URL to which the client should be redirected to perform the payment authorization
     */
    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    /**
     * Sets the URL to which the client should be redirected to perform the payment authorization.
     *
     * @param authorizationUrl
     *              the URL to which the client should be redirected to perform the payment authorization
     */
    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    /**
     * Retrieves the generated ID for the authorization.
     *
     * @return the generated ID for the authorization
     */
    public long getAuthorizationId() {
        return authorizationId;
    }

    /**
     * Sets the generated ID for the authorization.
     *
     * @param authorizationId
     *              the generated ID for the authorization
     */
    public void setAuthorizationId(long authorizationId) {
        this.authorizationId = authorizationId;
    }

    /**
     * Retrieves the generated ID of the payment.
     *
     * @return the generated ID of the payment
     */
    public long getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the generated ID of the payment.
     *
     * @param paymentId
     *              the generated ID of the payment
     */
    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * Converts this object to string presentation.
     *
     * @return string presentation of this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{authorizationUrl: ").append(Helper.toString(authorizationUrl))
          .append(", authorizationId: ").append(authorizationId)
          .append(", paymentId: ").append(paymentId)
          .append("}");
        return sb.toString();
    }
}
