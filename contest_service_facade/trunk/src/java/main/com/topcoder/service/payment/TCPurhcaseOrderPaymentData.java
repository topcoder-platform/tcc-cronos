/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * This class captures the Purchase Order payment data. Purchase order in the current implementation is recognized by
 * po-number only.
 *
 * @author shailendra_80
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcPurhcaseOrderPaymentData", propOrder = { "poNumber"})
public class TCPurhcaseOrderPaymentData extends PaymentData {

    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Purchase order number of the payment.
     */
    private String poNumber;

    /**
     * A do nothing default constructor.
     */
    public TCPurhcaseOrderPaymentData() {

    }

    /**
     * <p>
     * Initializes this instance with given <code>PaymentType</code> and PO Number.
     * </p>
     *
     * @param type
     *            a <code>PaymentType</code> a payment type.
     * @param poNumber
     *            a <code>String</code> purchase order number.
     */
    public TCPurhcaseOrderPaymentData(PaymentType type, String poNumber) {
        super(type);
        this.poNumber = poNumber;
    }

    /**
     * <p>
     * Gets the purchase order number of the payment
     * </p>
     *
     * @return a <code>String</code> the purchase order number of the payment.
     */
    public String getPoNumber() {
        return poNumber;
    }

    /**
     * <p>
     * Sets the purchase order number of the payment
     * </p>
     *
     * @param poNumber
     *            a <code>String</code> the purchase order number for the payment to be processed.
     */
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }
}
