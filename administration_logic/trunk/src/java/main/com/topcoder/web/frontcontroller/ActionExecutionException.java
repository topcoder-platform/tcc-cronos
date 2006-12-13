
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;

/**
 * <p> ActionExecutionException is thrown from Action interface and its implementations' execute method if it fails to process the user request. This exception is used to wrap the underlying exceptions of different implementations. </p>
 * 
 */
public class ActionExecutionException extends com.topcoder.util.errorhandling.BaseException {

/**
 * <p>Create exception with message string.</p>
 * 
 * 
 * @param message error message string.
 */
    public  ActionExecutionException(String message) {        
        // your code here
    } 

/**
 * <p>Create exception with message string and inner cause.</p>
 * 
 * 
 * @param message error message string.
 * @param cause inner exception cause.
 */
    public  ActionExecutionException(String message, Throwable cause) {        
        // your code here
    } 
 }
