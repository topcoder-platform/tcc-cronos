package com.topcoder.service.review.specification;

import com.topcoder.service.specreview.SpecReviewServiceFault;
import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

public class SpecificationReviewServiceException extends BaseCriticalException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * Represents the faultInfo, Java type that goes as soapenv:Fault detail element.
     * </p>
     */
    private SpecReviewServiceFault faultInfo;

    /**
     * <p>
     * Represents the faultMessage. It is retrieved from SpecReviewServiceFault.
     * </p>
     */
    private String faultMessage;

    /**
     * <p>
     * Constructor with error message.
     * </p>
     * 
     * @param message
     *            the error message
     */
    public SpecificationReviewServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     * 
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     */
    public SpecificationReviewServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Constructor with error message and exception data
     * </p>
     * 
     * @param message
     *            the error message
     * @param data
     *            the exception data
     */
    public SpecificationReviewServiceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Constructor with error message and inner cause and exception data
     * </p>
     * 
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param data
     *            the exception data
     */
    public SpecificationReviewServiceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }

    /**
     * <p>
     * Construct the exception. Call the super constructor,set the fault info and set the faultMessage from
     * the faultInfo.
     * </p>
     * 
     * @param message
     *            the message
     * @param faultInfo
     *            the fault info which contains the faultMessage
     */
    public SpecificationReviewServiceException(String message, SpecReviewServiceFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.faultMessage = (faultInfo == null) ? null : faultInfo.getFaultMessage();
    }

    /**
     * <p>
     * Construct the exception. Call the super constructor,set the fault info and set the faultMessage from
     * the faultInfo.
     * </p>
     * 
     * @param message
     *            the message
     * @param faultInfo
     *            the fault info which contains the faultMessage
     * @param cause
     *            the cause
     */
    public SpecificationReviewServiceException(String message, SpecReviewServiceFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
        this.faultMessage = (faultInfo == null) ? null : faultInfo.getFaultMessage();
    }

    /**
     * <p>
     * Constructor with error message.
     * </p>
     * 
     * @param message
     *            the error message
     * @param faultMessage
     *            the competition's id not found
     */
    public SpecificationReviewServiceException(String message, String faultMessage) {
        super(message);
        this.faultMessage = faultMessage;
        this.faultInfo = null;
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     * 
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param faultMessage
     *            the competition's id not found
     */
    public SpecificationReviewServiceException(String message, Throwable cause, String faultMessage) {
        super(message, cause);
        this.faultMessage = faultMessage;
        this.faultInfo = null;
    }

    /**
     * <p>
     * Constructor with error message and exception data
     * </p>
     * 
     * @param message
     *            the error message
     * @param data
     *            the exception data
     * @param faultMessage
     *            the competition's id not found
     */
    public SpecificationReviewServiceException(String message, ExceptionData data, String faultMessage) {
        super(message, data);
        this.faultMessage = faultMessage;
        this.faultInfo = null;
    }

    /**
     * <p>
     * Constructor with error message and inner cause and exception data
     * </p>
     * 
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param data
     *            the exception data
     * @param faultMessage
     *            the competition's id not found
     */
    public SpecificationReviewServiceException(String message, Throwable cause, ExceptionData data,
        String faultMessage) {
        super(message, cause, data);
        this.faultMessage = faultMessage;
        this.faultInfo = null;
    }

    /**
     * <p>
     * Return the faultMessage
     * </p>
     * 
     * @return the faultMessage
     */
    public String getFaultMessage() {
        return faultMessage;
    }

    /**
     * <p>
     * Return the fault bean. This method is necessary for the serialization/deserialization. it returns null
     * if the constructors without fault bean are used.
     * </p>
     * 
     * @return returns fault bean
     */
    public SpecReviewServiceFault getFaultInfo() {
        return faultInfo;
    }

}
