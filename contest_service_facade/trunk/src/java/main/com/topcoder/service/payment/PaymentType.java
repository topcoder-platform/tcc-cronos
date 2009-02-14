package com.topcoder.service.payment;

/**
 * <p>
 * An ENUM which captures different type of payments to be processed. Current
 * implementation just supports two payment types viz: a) PayPal Credit Card. b)
 * TC Purchase order.
 * </p>
 * 
 * @author TCSDEVELOPER
 * 
 */
public enum PaymentType {
	/**
	 * Pay Pal Credit Card
	 */
	PayPalCreditCard,
	
	/**
	 * TopCoder Pre Approved Purchase Order. 
	 */
	TCPurchaseOrder;
}
