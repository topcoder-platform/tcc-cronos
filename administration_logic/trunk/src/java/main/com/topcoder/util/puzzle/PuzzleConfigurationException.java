
package com.topcoder.util.puzzle;

/**
 * <p> This exception will signal a configuration issue. This is used when PuzzleTypeResource implementations fail
 * to "boot up" properly. It is thrown by these implementations (usually in a ctor)
 * </p>
 * 
 */
public class PuzzleConfigurationException extends com.topcoder.util.puzzle.PuzzleException {

/**
 * <p>Create a default, blank exception.</p>
 * 
 */
    public  PuzzleConfigurationException() {        
        // your code here
    } 

/**
 * <p>Create an exception with the corresponding message parameter.</p>
 * 
 * 
 * @param message exception message
 */
    public  PuzzleConfigurationException(String message) {        
        // your code here
    } 

/**
 * <p>Create an exception with the corresponding message, and cause set.</p>
 * 
 * 
 * @param message exception message
 * @param cause the underlying cause exception chained to this exception
 */
    public  PuzzleConfigurationException(String message, Throwable cause) {        
        // your code here
    } 
 }
