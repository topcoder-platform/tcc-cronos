
package com.topcoder.timetracker.audit;

/**
 * An exception thrown by any persistence plugin implementing the AuditPersistence, whenever there are any problems in persisting information. Two standard constructors are provided, both which take a message detailing why the exception was thrown, and one taking also the exception which caused it, for example if triggered by an SQL or IO Exception.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7b5d]
 */
public class AuditPersistenceException extends com.topcoder.util.errorhandling.BaseException {

/**
 * Message-only constructor, taking the reason why the exception has been thrown.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7b73]
 * @param message Readable description telling why the exception was thrown.
 */
    public  AuditPersistenceException(String message) {        
        // your code here
    } 

/**
 * Constructor taking two arguments - the reason why the exception has been thrown, and the initial cause that triggered it.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7b63]
 * @param message Readable description telling why the exception was thrown.
 * @param cause Throwable cause that was caught and triggered this exception to be thrown.
 */
    public  AuditPersistenceException(String message, Throwable cause) {        
        // your code here
    } 
 }
