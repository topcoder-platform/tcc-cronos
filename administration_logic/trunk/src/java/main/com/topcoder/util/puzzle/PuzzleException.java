
package com.topcoder.util.puzzle;

/**
 * <p> A catch-all exception indicating that some unspecified thing failed in the operation of a Puzzle Framework
 * implementation object. </p>
 * 
 * 
 */
public class PuzzleException extends com.topcoder.util.errorhandling.BaseException {

/**
 * <p>Create a default, blank exception.</p>
 * 
 */
    public  PuzzleException() {        
        // your code here
    } 

/**
 * <p>Create an exception with the corresponding message parameter.</p>
 * 
 * 
 * @param message exception message
 */
    public  PuzzleException(String message) {        
        // your code here
    } 

/**
 * <p>Create an exception with the corresponding message, and cause set.</p>
 * 
 * 
 * @param message exception message
 * @param cause the underlying cause exception chained to this exception
 */
    public  PuzzleException(String message, Throwable cause) {        
        // your code here
    } 
 }
