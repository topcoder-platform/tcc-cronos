package com.topcoder.direct.specreview.services;

import java.lang.*;

/**
 * This exception is thrown from the SpecReviewCommentService interface and its implementations if any error
 * occurs.
 */
public class SpecReviewCommentServiceException extends Exception {

    /**
     * Constructor with error message.
     * 
     * #Param
     * message - the error message.
     * 
     * @param message
     */
    public SpecReviewCommentServiceException(String message) {
    }

    /**
     * Constructor with error message and inner cause.
     * 
     * #Param
     * message - the error message.
     * cause - the inner cause.
     * 
     * @param message
     * @param cause
     */
    public SpecReviewCommentServiceException(String message, Throwable cause) {
    }
}
