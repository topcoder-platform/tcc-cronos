package com.topcoder.service.payment;

/**
 * This class captures the Purchase Order payment data. Purchase order in the
 * current implementation is recognized by po-number only.
 * 
 * @author TCSDEVELOPER
 * 
 */
public class TCPurhcaseOrderPaymentData extends PaymentData {

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
	 * Initializes this instance with given <code>PaymentType</code> and PO
	 * Number.
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
	 *            a <code>String</code> the purchase order number for the
	 *            payment to be processed.
	 */
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
}
