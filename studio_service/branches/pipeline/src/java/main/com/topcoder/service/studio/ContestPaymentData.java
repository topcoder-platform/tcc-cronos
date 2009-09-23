/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * It is the DTO class which is used to transfer contest payment data. The
 * information can be null or can be empty, therefore this check is not present
 * in the setters. It's the related to the equivalent ContestPayment entity.
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestPaymentData", propOrder = { "contestId", "paymentStatusId", "price", "paypalOrderId",
        "createDate", "paymentTypeId", "paymentReferenceId" })
public class ContestPaymentData implements Serializable {
    /**
     * Represents contestId id.
     */
    private Long contestId;

    /**
     * Represents contestId id.
     */
    private Long paymentStatusId;

    /**
     * Represents paypalOrderId id.
     */
    private String paypalOrderId;

    /**
     * Represents price id.
     */
    private Double price;

    /**
     * Represents the create date.
     */
    private Date createDate;

    /**
     * Represents the payment type.
     *
     * @since BUGR-1076
     */
    private Long paymentTypeId;

    /**
     * Represents the payment reference id.
     *
     * @since BUGR-1076
     */
    private String paymentReferenceId;

    /**
     * Returns price.
     *
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns paypalOrderId.
     *
     * @return the paypalOrderId
     */
    public String getPaypalOrderId() {
        return paypalOrderId;
    }

    /**
     * Sets paypalOrderId.
     *
     * @param paypalOrderId the paypalOrderId to set
     */
    public void setPaypalOrderId(String paypalOrderId) {
        this.paypalOrderId = paypalOrderId;
    }

    /**
     * Returns contestId.
     *
     * @return the contestId
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * Sets contestId.
     *
     * @param contestId the contestId to set
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * Returns paymentStatusId.
     *
     * @return the paymentStatusId
     */
    public Long getPaymentStatusId() {
        return paymentStatusId;
    }

    /**
     * Sets paymentStatusId.
     *
     * @param paymentStatusId the paymentStatusId to set
     */
    public void setPaymentStatusId(Long paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    /**
     * Returns create date.
     *
     * @return the create date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set create date.
     *
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Updates the payment type with the specified value.
     *
     * @since BUGR-1076
     * @param paymentTypeId the payment order id to set.
     */
    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    /**
     * Returns the paymentType.
     *
     * @since BUGR-1076
     * @return payment type
     */
    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    /**
     * Updates the payment reference id with the specified value.
     *
     * @since BUGR-1076
     * @param paymentReferenceId the payment order id to set.
     */
    public void setPaymentReferenceId(String paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    /**
     * Returns the paymentOrderId.
     *
     * @since BUGR-1076
     * @return payment reference id
     */
    public String getPaymentReferenceId() {
        return paymentReferenceId;
    }
}
