/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;

import com.topcoder.service.studio.contest.Helper;

/**
 * <p>
 * Represents the entity class for db table <i>payment_status_lu</i>.
 * </p>
 * <p>
 * Currently the three possible statuses are PAID, UNPAID and MARKED_FOR_PURCHASE.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class PaymentStatus implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 1915264309653844927L;

    /**
     * Represents the entity id.
     */
    private Long paymentStatusId;

    /**
     * Represents the description of Status.
     */
    private String description;

    /**
     * Default constructor.
     */
    public PaymentStatus() {
        // empty
    }

    /**
     * Returns the paymentStatusId.
     *
     * @return the paymentStatusId.
     */
    public Long getPaymentStatusId() {
        return paymentStatusId;
    }

    /**
     * Updates the paymentStatusId with the specified value.
     *
     * @param paymentStatusId
     *            the paymentStatusId to set.
     */
    public void setPaymentStatusId(Long paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
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
     * @param description
     *            the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PaymentStatus) {
            return getPaymentStatusId() == ((PaymentStatus) obj).getPaymentStatusId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code PaymentStatus}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(paymentStatusId);
    }
}
