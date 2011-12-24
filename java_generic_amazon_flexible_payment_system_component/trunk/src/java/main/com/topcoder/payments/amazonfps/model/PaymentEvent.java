/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.io.Serializable;

import com.topcoder.payments.amazonfps.Helper;

/**
 * <p>
 * This class is a container for information about a single payment event that is used when notifying the
 * registered subscribers.
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
public class PaymentEvent implements Serializable {
    /**
     * The class's serialVersionUID for the serialization runtime.
     */
    private static final long serialVersionUID = -4280542525081833742L;

    /**
     * The payment event type.
     * Can be any value. Has getter and setter.
     */
    private PaymentEventType eventType;

    /**
     * The payment details provided by the user associated with the event.
     * Can be any value. Has getter and setter.
     */
    private PaymentDetails paymentDetails;

    /**
     * The ID of the authorization associated with the event.
     * Can be any value. Has getter and setter.
     */
    private long authorizationId;

    /**
     * The ID of the payment associated with the event.
     * Can be any value. Has getter and setter.
     */
    private long paymentId;

    /**
     * The error code. It is specified for failure events only.
     * Can be any value. Has getter and setter.
     */
    private String errorCode;

    /**
     * The error type. It is specified for failure events only.
     * Can be any value. Has getter and setter.
     */
    private String errorType;

    /**
     * The error message. It is specified for failure events only.
     * Can be any value. Has getter and setter.
     */
    private String errorMessage;

    /**
     * Constructs new {@code PaymentEvent} instance.
     */
    public PaymentEvent() {
        // Empty
    }

    /**
     * Retrieves the payment event type.
     *
     * @return the payment event type
     */
    public PaymentEventType getEventType() {
        return eventType;
    }

    /**
     * Sets the payment event type.
     *
     * @param eventType
     *              the payment event type
     */
    public void setEventType(PaymentEventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Retrieves the payment details provided by the user associated with the event.
     *
     * @return the payment details provided by the user associated with the event
     */
    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    /**
     * Sets the payment details provided by the user associated with the event.
     *
     * @param paymentDetails
     *              the payment details provided by the user associated with the event
     */
    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    /**
     * Retrieves the ID of the authorization associated with the event.
     *
     * @return the ID of the authorization associated with the event
     */
    public long getAuthorizationId() {
        return authorizationId;
    }

    /**
     * Sets the ID of the authorization associated with the event.
     *
     * @param authorizationId
     *              the ID of the authorization associated with the event
     */
    public void setAuthorizationId(long authorizationId) {
        this.authorizationId = authorizationId;
    }

    /**
     * Retrieves the ID of the payment associated with the event.
     *
     * @return the ID of the payment associated with the event
     */
    public long getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the ID of the payment associated with the event.
     *
     * @param paymentId
     *              the ID of the payment associated with the event
     */
    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * Retrieves the error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code.
     *
     * @param errorCode
     *              the error code
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Retrieves the error type.
     *
     * @return the error type
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * Sets the error type.
     *
     * @param errorType
     *              the error type
     */
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    /**
     * Retrieves the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage
     *              the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Converts this object to string presentation.
     *
     * @return string presentation of this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{eventType: ").append(Helper.toString(eventType))
          .append(", paymentDetails: ").append(Helper.toString(paymentDetails))
          .append(", authorizationId: ").append(authorizationId)
          .append(", paymentId: ").append(paymentId)
          .append(", errorCode: ").append(Helper.toString(errorCode))
          .append(", errorType: ").append(Helper.toString(errorType))
          .append(", errorMessage: ").append(Helper.toString(errorMessage))
          .append("}");
        return sb.toString();
    }
}
