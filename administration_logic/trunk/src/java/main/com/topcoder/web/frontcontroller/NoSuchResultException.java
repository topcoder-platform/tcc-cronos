
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;

/**
 * <p> NoSuchResultException is thrown DefaultAction's execute method if there is no Result object matched with the resultCode returned by the handler in the results Map. </p>
 * 
 */
public class NoSuchResultException extends com.topcoder.web.frontcontroller.ActionExecutionException {

/**
 * <p>Represents the result code. It is initialized in ctor, and never changed later.</p>
 * 
 */
    private final String resultCode;

/**
 * <p>Create exception with message string and result code.</p>
 * 
 * 
 * @param resultCode the result code
 * @param message error message string.
 */
    public  NoSuchResultException(String resultCode, String message) {        
        super( message);
        this.resultCode= resultCode;
    } 

/**
 * <p>Return the result code.</p>
 * 
 * 
 * @return the result code.
 */
    public String getResultCode() {        
        // your code here
        return resultCode;
    } 
 }
