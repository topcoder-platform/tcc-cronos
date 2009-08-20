/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;

/**
 * Represents the entity class for payment type. Currently supported types are:
 * Paypal and TC Purchase order.
 *
 * @author Margarita
 * @version 1.0
 * @since BUGR-1076
 */
public class PaymentType implements Serializable {
    /**
     * Represents the payment type id.
     */
    private Long paymentTypeId;

    /**
     * Represents the description of payment type.
     */
    private String description;

    /**
     * Default constructor.
     */
    public PaymentType() {
        // empty
    }

    /**
     * Returns the paymentType.
     *
     * @return the paymentType.
     */
    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    /**
     * Updates the paymentType with the specified value.
     *
     * @param paymentType the paymentType to set.
     */
    public void setPaymentTypeId(Long paymentType) {
        this.paymentTypeId = paymentType;
    }

    /**
     * Returns the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description with the specified value.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Compares this object with the passed object for equality. Only the id
     * will be compared.
     *
     * @param obj the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PaymentType) {
            return (getPaymentTypeId() == ((PaymentType) obj).getPaymentTypeId());
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent
     * with this class's {@link #equals(Object)} method.
     *
     * @return a hash code for this {@code PaymentType}
     */
    @Override
    public int hashCode() {
        return paymentTypeId.hashCode();
    }
}
