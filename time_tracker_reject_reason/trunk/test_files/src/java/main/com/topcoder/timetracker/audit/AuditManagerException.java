
package com.topcoder.timetracker.audit;

/**
 * An exception thrown by the manager whenever there are any problems in any of its methods throw during their delegation to the persistence layer. Two standard constructors are provided, both which take a message detailing why the exception was thrown, and one taking also the exception which caused it, for example which may be an AuditPersistenceException.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7b2d]
 */
public class AuditManagerException extends com.topcoder.util.errorhandling.BaseException {

/**
 * Message-only constructor, taking the reason why the exception has been thrown.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7b4f]
 * @param message Readable description telling why the exception was thrown.
 */
    public  AuditManagerException(String message) {        
        // your code here
    } 

/**
 * Constructor taking two arguments - the reason why the exception has been thrown, and the initial cause that triggered it.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7b3d]
 * @param message Readable description telling why the exception was thrown.
 * @param cause Throwable cause that was caught and triggered this exception to be thrown.
 */
    public  AuditManagerException(String message, Throwable cause) {        
        // your code here
    } 
 }
