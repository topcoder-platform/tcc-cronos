/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.topcoder.payments.amazonfps.Helper;

/**
 * <p>
 * This class is a container for information about a single payment or payment reservation that is expected to be
 * provided by the user of this component.
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
public class PaymentDetails implements Serializable {
    /**
     * The class's serialVersionUID for the serialization runtime.
     */
    private static final long serialVersionUID = 8886149111217008645L;

    /**
     * The map of parameters to be associated with the payment.
     * Can be any value. Can contain any values. Has getter and setter.
     */
    private Map<String, String> parameters;

    /**
     * The payment amount in USD.
     * Can be any value. Has getter and setter.
     */
    private BigDecimal amount;

    /**
     * The value indicating whether the payment reservation should be performed, or payment should be performed
     * completely. Can be any value. Has getter and setter.
     */
    private boolean reservation;

    /**
     * Constructs new {@code PaymentDetails} instance.
     */
    public PaymentDetails() {
        // Empty
    }

    /**
     * <p>
     * Retrieves the map of parameters associated with the payment.
     * </p>
     *
     * <p>
     * NOTE: by default JAXB doesn't support serialization of maps, thus @XmlJavaTypeAdapter annotation is used for
     * this method that suggests usage of {@code MapAdapter} during marshalling and unmarshalling.
     * </p>
     *
     * @return the map of parameters associated with the payment
     */
    @XmlJavaTypeAdapter(MapAdapter.class)
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Sets the map of parameters to be associated with the payment.
     *
     * @param parameters
     *              the map of parameters to be associated with the payment
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     * Retrieves the payment amount in USD.
     *
     * @return the payment amount in USD
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the payment amount in USD.
     *
     * @param amount
     *              the payment amount in USD
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the value indicating whether the payment reservation should be performed, or payment should be
     * performed completely.
     *
     * @return the value indicating whether the payment reservation should be performed, or payment should be
     * performed completely
     */
    public boolean isReservation() {
        return reservation;
    }

    /**
     * Sets the value indicating whether the payment reservation should be performed, or payment should be
     * performed completely.
     *
     * @param reservation
     *              the value indicating whether the payment reservation should be performed, or payment should be
     *              performed completely
     */
    public void setReservation(boolean reservation) {
        this.reservation = reservation;
    }

    /**
     * Converts this object to string presentation.
     *
     * @return string presentation of this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{parameters: ").append(Helper.toString(parameters))
          .append(", amount: ").append(Helper.toString(amount))
          .append(", reservation: ").append(reservation)
          .append("}");
        return sb.toString();
    }
}
