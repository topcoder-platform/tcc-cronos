
package com.topcoder.timetracker.common;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p><strong>Usage:</strong> This exception is thrown when a problem occurs when performing operations with the PaymentTerms in the persistence.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><strong>extends </strong>CommonManagementException.</li>
 * </ul>
 * <p><strong>Thread-safety:</strong> Exceptions are thread-safe.</p>
 * 
 */
public class PaymentTermDAOException extends CommonManagementException {

/**
 * <p><strong>Usage:</strong> Constructor with error message.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>super(message);</em></li>
 * </ul>
 * 
 * 
 * @param message the error message
 */
    public  PaymentTermDAOException(String message) {        
        // your code here
    } 

/**
 * <p><strong>Usage:</strong> Constructor with error cause.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>super(cause);</em></li>
 * </ul>
 * 
 * 
 * @param cause the cause of this exception
 */
    public  PaymentTermDAOException(Throwable cause) {        
        // your code here
    } 

/**
 * <p><strong>Usage:</strong> Constructor with error message and inner cause.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><em>super(message, cause);</em></li>
 * </ul>
 * 
 * 
 * @param message the error message
 * @param cause the cause of this exception
 * @throws IllegalArgumentException if the message is null or an empty String.
 */
    public  PaymentTermDAOException(String message, Throwable cause) {        
            super(message, cause);
           // Utils.checkString(message, "message", false);
            this.problemRejectEmail = problemRejectEmail;
    } 
 }
