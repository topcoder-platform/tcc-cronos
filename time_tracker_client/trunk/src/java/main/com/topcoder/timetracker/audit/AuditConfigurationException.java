
package com.topcoder.timetracker.audit;

/**
 * An exception thrown by classes within this component whenever there are problems in initialization from configuration, such as missing or invalid configuration parameters.
 * 
 * @poseidon-object-id [I104345eam110f6ab224fmm5a1c]
 */
public class AuditConfigurationException extends com.topcoder.util.errorhandling.BaseException {

/**
 * Message-only constructor, taking the reason why the exception has been thrown.
 * 
 * @poseidon-object-id [I104345eam110f6ab224fmm59fa]
 * @param message Readable description telling why the exception was thrown.
 */
    public  AuditConfigurationException(String message) {        
        // your code here
    } 

/**
 * Constructor taking two arguments - the reason why the exception has been thrown, and the initial cause that triggered it.
 * 
 * @poseidon-object-id [I104345eam110f6ab224fmm59a6]
 * @param message Readable description telling why the exception was thrown.
 * @param cause Throwable cause that was caught and triggered this exception to be thrown.
 */
    public  AuditConfigurationException(String message, Throwable cause) {        
        // your code here
    } 
 }
