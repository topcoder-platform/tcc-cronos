/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.HashMap;

/**
 * <p>Serves as a handler for <code>Silent Post</code> requests issued by <code>PayPal</code> services to notify on
 * transactions with contest payments. Collects the data for orders submitted by <code>PayPal</code> service and
 * provides the methods for getting that data later when validating the contest payments data against fraud.</p>
 *
 * <p>This implementation uses in-memory mechanism for keeping the order data.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PayPalResponseListener {

    /**
     * <p>A <code>ServletContext</code> representing a context which this listener is bound to. Used for logging.</p>
     */
    private ServletContext context = null;

    /**
     * <p>A <code>Map</code> collecting the data for the <code>PayPal</code> orders.</p>
     */
    private Map<String, String[]> paypalOrders = new HashMap<String, String[]>();

    /**
     * <p>Constructs new <code>PayPalResponseListener</code> instance. This implementation does nothing.</p>
     *
     * @param context a <code>ServletContext</code> which this listener is bound to. 
     */
    public PayPalResponseListener(ServletContext context) {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        this.context = context;
        this.context.log("PayPalResponseListener instance is bound to servlet context: " + this.context);
    }

    /**
     * <p>Saves the details for the specified <code>PayPal</code> order to application context. Such details could be
     * used later to prevent fraud when recording contest payments.</p>
     *
     * @param originalSessionId a <code>String</code> providing the ID for the original session for a user who have
     *        originated the payment.
     * @param originalPrincipalName a <code>String</code> providing the username of a user who have originated the
     *        payment.
     * @param originalContestId a <code>String</code> providing the ID of a contest associated with the payment.
     * @param originalPaymentType a <code>String</code> referencing the type of the payment.
     * @param originalPaymentAmount a <code>String</code>
     * @param paypalOrderId a <code>String</code> providing the ID of a PayPal order associated with the payment.
     * @return <code>true</code> if order details have been saved successfully; <code>false</code> otherwise.
     */
    public boolean savePayPalOrder(String originalSessionId, String originalPrincipalName, String originalContestId,
                                   String originalPaymentType, String originalPaymentAmount, String paypalOrderId) {
        synchronized (this.paypalOrders) {
            try {
                this.paypalOrders.put(paypalOrderId, new String[] {originalSessionId, originalPrincipalName,
                                                                   originalContestId, originalPaymentType,
                                                                   originalPaymentAmount});
                this.context.log("Saved data for PayPal order [" + originalSessionId + ", "
                                 + originalPrincipalName + ", " + originalContestId + ", " + originalPaymentType + ","
                                 + originalPaymentAmount + "]");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                this.context.log("Failed to save data for PayPal order [" + originalSessionId + ", "
                                 + originalPrincipalName + ", " + originalContestId + ", " + originalPaymentType + ","
                                 + originalPaymentAmount + "] due to unexpected error", e);
            }
        }
        return false;
    }

    /**
     * <p>Gets the pre-saved data for the specified <code>PayPal</code> order. Such a data could be saved before when
     * Silent Post request from the <code>PayPal</code> service is received.</p>
     *
     * @param payPalOrderId a <code>String</code> providing the <code>PayPal</code> order ID to get registered data for.
     * @return a <code>String</code> array providing the data for the requested <code>PayPal</code> order ID or
     *         <code>null</code> if no data for requested order is found. The elements in returned provide the following
     *         data - session ID, user principal name, contest ID, payment type, payment amount. 
     */
    public String[] getPayPalOrderData(String payPalOrderId) {
        synchronized (this.paypalOrders) {
            return this.paypalOrders.get(payPalOrderId);
        }
    }

    /**
     * <p>Removes the data associated with the specified PayPal order.</p>
     *
     * @param paypalOrderId a <code>String</code> providing the ID of a PayPal order to remove the associated data for.
     */
    public void releasePayPalOrder(String paypalOrderId) {
        synchronized (this.paypalOrders) {
            this.paypalOrders.remove(paypalOrderId);
        }
    }
}
