
package com.orpheus.game;

/**
 * This exception will be throw by GameOperationLogicUtility#initialize block if failed to load the configured values or they are invalid.
 * 
 */
public class GameDataException extends com.topcoder.util.errorhandling.BaseRuntimeException {

/**
 * Create the exception with msg. call supper(msg)
 * 
 * @param msg the error message
 */
    public  GameDataException(String msg) {        
        // your code here
    } 

/**
 * Create the exception with error message and cause exception, call super(msg,cause)
 * 
 * @param msg the error message
 * @param cause the cause exception
 */
    public  GameDataException(String msg, Throwable cause) {        
        // your code here
    } 
 }
