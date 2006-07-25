
package com.topcoder.management.scorecard.validation;
import com.topcoder.util.errorhandling.BaseException;

/**
 * Represents an exception related to validating scorecard. Inner exception should be provided to give more details about the error. It is used in classes of the validation package. Thread safety: This class is immutable and thread safe.
 *
 */
public class ValidationException extends BaseException {

/**
 * Create a new ValidationException instance with the specified error message.
 * Implementation notes:
 * - Call super(message);
 *
 *
 * @param message the error message of the exception
 */
    public  ValidationException(String message) {
        // your code here
    }

/**
 * Create a new ValidationException instance with the specified error message and inner
 * exception.
 * Implementation notes:
 * - Call super(message, cause);
 *
 *
 * @param message the error message of the exception
 * @param cause the inner exception
 */
    public  ValidationException(String message, Throwable cause) {
        // your code here
    }
 }
