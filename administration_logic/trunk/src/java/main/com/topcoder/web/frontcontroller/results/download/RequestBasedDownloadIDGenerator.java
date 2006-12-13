
package com.topcoder.web.frontcontroller.results.download;
import com.topcoder.web.frontcontroller.results.DownloadIDGenerationException;
import org.w3c.dom.*;

/**
 * <p>
 * This class provides an implementation of DownloadIDGenerator and the implementation is based on simply
 * reading the value of the configured request parameter for the current request and returning the value
 * obtained as the value for the download ID. This class has its only instance member which is immutable 
 * and hence the class is thread-safe.
 * </p>
 * 
 * 
 */
public class RequestBasedDownloadIDGenerator implements com.topcoder.web.frontcontroller.results.DownloadIDGenerator {

/**
 * <p>Represents the parameter name in the HttpServletRequest whose value in the incoming request will provide
 * the value for the id for the DownloadData for the request. This instance member is initialized in the constructor
 * with a non-null, non-empty String value and is immutable later on.
 * </p>
 * 
 */
    private final String paramName;

/**
 * <p>
 * Constructor. Simply initializes the instance member "paramName" from the value read from the passed "element".
 * (The value will be read from the value of the attribute "name" from the sub-element "request-param" from the 
 * passed element.)
 * </p>
 * 
 * 
 * @param element the DOM Element whose sub-element's "name" attribute will specify the value for the paramName.
 * @throws IllegalArgumentException: if the parameter "element" is null or if there is an exception or required entities are found to be missing in the passed Element
 */
    public  RequestBasedDownloadIDGenerator(org.w3c.dom.Element element) {        
    this.paramName = null;
    } 

/**
 * <p>
 * Returns the DownloadData for the current request specified by the ActionContext.
 * 
 * Implementation Hint:
 * ----------------------------------
 * String value = context.getRequest().getParameter(paramName);
 * if(value==null){
 *   throw DownloadIDGenerationException with proper message.
 * }
 * return value;
 * 
 * </p>
 * 
 * 
 * @param context the ActionContext for the current Request
 * @return the DownloadData for the current request specified by the ActionContext.
 * @throws IllegalArgumentException: if the parameter "context" is null
 * @throws DownloadIDGenerationException: if the request does not contain a value for the specified parameter name or there is ServletException when retrieving the value
 */
    public String getDownloadIDForRequest(com.topcoder.web.frontcontroller.ActionContext context) throws com.topcoder.web.frontcontroller.results.DownloadIDGenerationException {        
        // your code here
        return null;
    } 

 }
