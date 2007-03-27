
package com.topcoder.timetracker.common;

/**
 * <p><strong>Usage:</strong> This exception extends the BaseException and is thrown from the CommonManager interface. It is also the parent exception class for all the other custom exceptions in this design.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><strong>extends </strong>BaseException.</li>
 * </ul>
 * <p><strong>Thread-safety:</strong> Exceptions are thread-safe.</p>
 * 
 */
public class CommonManagementException extends com.topcoder.util.errorhandling.BaseException {

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
    public  CommonManagementException(String message) {        
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
    public  CommonManagementException(Throwable cause) {        
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
 */
    public  CommonManagementException(String message, Throwable cause) {        
        // your code here
    } 
 }
