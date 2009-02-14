package com.topcoder.service.payment;

/**
 * <p>
 * This interface defines the contract for processing a payment request.
 * 
 * Payment can be processed either through PayPal, Purchase order or other
 * mechanism. It solely depends on the implementing class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * 
 */
public interface PaymentProcessor {

	/**
	 * <p>
	 * Processes given payment data for the given amount.
	 * 
	 * On success it should return <code>PaymentResult</code> On failure it
	 * throws <code>PaymentException</code>
	 * </p>
	 * 
	 * @param payment
	 *            the payment data that need to be processed.
	 * @param amount
	 *            the amount that need to be charged.
	 * @return a <code>PaymentResult</code> the payment result.
	 * @throws PaymentException
	 *             on payment failure. It captures Error message and Error code
	 *             for the failure case.
	 */
	public PaymentResult process(PaymentData payment, String amount)
			throws PaymentException;
}
