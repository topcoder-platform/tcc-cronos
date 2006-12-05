
package com.topcoder.web.frontcontroller.results.urlconstructor;
import java.util.HashMap;

import com.topcoder.web.frontcontroller.results.PartURLConstructionException;
import org.w3c.dom.*;

/**
 * <p>
 * This class implements the PartURLConstructor interface and provides a simplistic mechanism of the creation of the 
 * part of the URL required, based on the parameter values in the incoming request. This class when intialized loads
 * the mapping information using the passed DOM Element. The mapping information specifies which of the incoming 
 * parameter values needs to be used from the request for the construction of the part URL and what will be the
 * new names of the parameter in the part of the URL constructed.
 * This class has a immutable instance member which is intialized in the constructor and hence the class is thread-safe.
 * </p>
 * 
 * 
 */
public class RequestBasedPartURLConstructor implements com.topcoder.web.frontcontroller.results.PartURLConstructor {

/**
 * <p>
 * Represents the mapping from the parameter name in the incoming request to the paramter name to be used 
 * when generating the part of the URL to be returned for the request. For each of the elements in this Map
 * the value from the incoming request is read for the parameter defined by the key and then the value is put in the URL
 * generated corresponding to new parameter name defined by the value of the Map entry.
 * This instance member is initialized in the constructor and filled in with values in the construtor and
 * not modified later on and hence this instance member is immutable.
 * Both the keys and the value in the Map will be a non-empty non-null Strings.
 * </p>
 * 
 */
    private final java.util.Map paramNameMapping = new HashMap();

/**
 * <p>
 * Constructor. Initializes the instance member "paramNameMapping" from the values read from the passed "element".
 * The Element corresponds to the XML element of "constructor-param" as shown in section 1.3.8 of the 
 * component specification. The values from the attributes "name" and "newName" are read from the
 * sub-elements of the passed "element" and the "paramNameMapping" Map is populated with it.
 * </p>
 * 
 * 
 * @param element the Element from the XML DOM which contains the values required for initialization
 * @throws IllegalArgumentException: if the parameter "element" is null or if there is an exception or required entities are found to be missing in the passed Element
 */
    public  RequestBasedPartURLConstructor(org.w3c.dom.Element element) {        
        // your code here
    } 

/**
 * <p>
 * Returns the part of the URL for this Request.
 * 
 * Implementation Hint:
 * ---------------------------------
 * a. String returnURL = "";
 * b. For each entries in the Map, get the key and the value. 
 *    Get the value from the request parameter for the parameter name given by the Key. Say the returned value is "paramValue"
 *    returnURL += value + "=" + paramValue + "&";
 *    
 * c. If returnURL ends with "&", remove the trailing "&".
 * d. Return returnURL.
 * 
 * </p>
 * 
 * 
 * @param context the ActionContext corresponding to the request
 * @return String containing the part of the URL corresponding to this request
 * @throws IllegalArgumentException: if the parameter "context" is null
 * @throws PartURLConstructionException: if there are problems in creating the part URL like missing parameter values in the incoming request.
 */
    public String getURLSuffix(com.topcoder.web.frontcontroller.ActionContext context) throws com.topcoder.web.frontcontroller.results.PartURLConstructionException {        
        // your code here
        return null;
    } 
}