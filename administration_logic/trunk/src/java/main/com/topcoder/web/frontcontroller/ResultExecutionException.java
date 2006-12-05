
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;

/**
 * <p> ResultExecutionException is thrown from Result interface and its implementations' execute method if it fails to generate the response. This exception is used to wrap the underlying exceptions of different implementations. </p>
 * 
 */
public class ResultExecutionException extends com.topcoder.web.frontcontroller.ActionExecutionException {

/**
 * <p>Create exception with message string.</p>
 * 
 * 
 * @param message error message string.
 */
    public  ResultExecutionException(String message) {        /** lock-end */
       super(message);
    } /** lock-begin */

/**
 * <p>Create exception with message string and inner cause.</p>
 * 
 * 
 * @param message error message string.
 * @param cause inner exception cause.
 */
    public  ResultExecutionException(String message, Throwable cause) {        /** lock-end */
        super(message, cause);
    } /** lock-begin */
 }
