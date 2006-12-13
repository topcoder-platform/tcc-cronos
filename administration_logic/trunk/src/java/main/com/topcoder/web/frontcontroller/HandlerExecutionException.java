
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;

/**
 * <p> HandlerExecutionException is thrown from Handler interface and its implementations' execute method if it fails to process the user request in this handler. This exception is used to wrap the underlying exceptions of different implementations. </p>
 * 
 */
public class HandlerExecutionException extends com.topcoder.web.frontcontroller.ActionExecutionException {

/**
 * <p>Create exception with message string.</p>
 * 
 * 
 * @param message error message string.
 */
    public  HandlerExecutionException(String message) {        
       super(message);
    } 

/**
 * <p>Create exception with message string and inner cause.</p>
 * 
 * 
 * @param message error message string.
 * @param cause inner exception cause.
 */
    public  HandlerExecutionException(String message, Throwable cause) {        
        super(message,cause);
    } 
 }
