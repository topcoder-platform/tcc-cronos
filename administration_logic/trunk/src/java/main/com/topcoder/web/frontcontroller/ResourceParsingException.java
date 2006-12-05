
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;

/**
 * <p> ResourceParsingException is thrown from ResourceParser interface and its implementations if the resource files are not in correct format. </p>
 * 
 */
public class ResourceParsingException extends com.topcoder.util.errorhandling.BaseException {

/**
 * <p>Create exception with message string.</p>
 * 
 * 
 * @param message error message string.
 */
    public  ResourceParsingException(String message) {        
        // your code here
    } 

/**
 * <p>Create exception with message string and inner cause.</p>
 * 
 * 
 * @param message error message string.
 * @param cause inner exception cause.
 */
    public  ResourceParsingException(String message, Throwable cause) {        
        // your code here
    } 
 }
