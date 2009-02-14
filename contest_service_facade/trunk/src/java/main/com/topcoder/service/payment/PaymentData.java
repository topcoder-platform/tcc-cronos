package com.topcoder.service.payment;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This is base class for capturing the payment data.
 * 
 * @author TCSDEVELOPER
 * 
 */
@XmlSeeAlso( { CreditCardPaymentData.class, TCPurhcaseOrderPaymentData.class })
public abstract class PaymentData {

	/**
	 * Type of payment (like: Credit Card or Purchase Order)
	 */
	protected PaymentType type;

	/**
	 * Do nothing default constructor.
	 */
	public PaymentData() {
		// do nothing.
	}

	/**
	 * <p>Constructor.</p?
	 * 
	 * @param type
	 *            a <code>PaymentType</code> the type of payment
	 */
	public PaymentData(PaymentType type) {
		super();
		this.type = type;
	}

	/**
	 * <p>
	 * Gets the type of payment.
	 * </p>
	 * 
	 * @return a <code>PaymentType</code> the type of payment
	 */
	public PaymentType getType() {
		return type;
	}

	/**
	 * <p>
	 * Sets the type of payment.
	 * </p>
	 * 
	 * @param type
	 *            a <code>PaymentType</code> the type of payment
	 */
	public void setType(PaymentType type) {
		this.type = type;
	}
}
