package com.topcoder.service.payment;

/**
 * <p>
 * The exception class that captures any errors or failed transactions that
 * happen on processing the payment through implementing classes.
 * 
 * This exception captures the error code and error message from the underlying
 * implementation of the payment processors.
 * 
 * </p>
 * 
 * @author TCSDEVELOPER
 * 
 */
public class PaymentException extends Exception {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The error code for the failed payment.
	 */
	private String errorCode;

	/**
	 * A do nothing default constructor.
	 */
	public PaymentException() {

	}

	/**
	 * <p>
	 * This constructor wraps the given exception.
	 * </p>
	 * 
	 * @param e
	 *            the given exception.
	 */
	public PaymentException(Exception e) {
		super(e);
	}

	/**
	 * <p>
	 * This constructor initializes this exception instance for given error code
	 * and error message.
	 * </p>
	 * 
	 * @param errorCode
	 *            the error code.
	 * @param errorMessage
	 *            the error message.
	 */
	public PaymentException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	/**
	 * <p>
	 * Gets the error code.
	 * </p>
	 * 
	 * @return the error code.
	 */
	public String getErrorCode() {
		return errorCode;
	}
}
