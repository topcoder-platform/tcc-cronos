
package com.topcoder.timetracker.common;

/**
 * <p><strong>Usage:</strong> This exception extends the CommonMangementException, and it is thrown if the configured value is invalid, or any required configuration value is missing. It is also used to wrap the exceptions from ConfigManager and IDGenerationException.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><strong>extends </strong>CommonManagementException.</li>
 * </ul>
 * <p><strong>Thread-safety:</strong> Exceptions are thread-safe.</p>
 * 
 */
public class CommonManagerConfigurationException extends com.topcoder.timetracker.common.CommonManagementException {

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
    public  CommonManagerConfigurationException(String message) {        
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
    public  CommonManagerConfigurationException(Throwable cause) {        
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
    public  CommonManagerConfigurationException(String message, Throwable cause) {        
        // your code here
    } 
 }
