
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;

/**
 * <p> ActionSelectionException is thrown from ActionSelector interface and its implementations' selectAction method if it fails to select an action by request uri. This exception is used to wrap the underlying exceptions of different implementations. </p>
 * 
 */
public class ActionSelectionException extends com.topcoder.util.errorhandling.BaseException {

/**
 * <p>Create exception with message string.</p>
 * 
 * 
 * @param message error message string.
 */
    public  ActionSelectionException(String message) {        
        // your code here
    } 

/**
 * <p>Create exception with message string and inner cause.</p>
 * 
 * 
 * @param message error message string.
 * @param cause inner exception cause.
 */
    public  ActionSelectionException(String message, Throwable cause) {        
        // your code here
    } 
 }
