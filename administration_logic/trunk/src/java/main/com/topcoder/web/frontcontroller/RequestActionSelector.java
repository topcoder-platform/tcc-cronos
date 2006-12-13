
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;
/**
 * <p>
 * RequestActionSelector extends the ActionSelector interface and additionally defines the contract to 
 * select an Action to execute based on the incoming HttpServletRequest. This interfaces broadens the scope
 * of the selection, by not just limiting the selection to be done based on the Request URL but on any 
 * other information that can be retrieved from the request. The implementation might do the selection of
 * Action based on the request URL and say request method or any other such information from the incoming
 * HttpServletRequest. Additonally this interface extends the ActonSelector interface to enable support for 
 * Logging and tracing using the Logging Wrapper component.
 * Its implementation must provide a constructor taking no arg in order to be created from FrontController. 
 * Its implementation must be thread-safe.
 * </p>
 * 
 * The interfaces ActionSelector has
 * a "use" relation with Action interface
 * but have not been shown avoid clutter
 * in the diagram
 */
public interface RequestActionSelector extends com.topcoder.web.frontcontroller.ActionSelector {
/**
 * <p>
 * Selects an Acion from the passed set of Actions and returns the Action selected for the request.
 * The implementation is free to decide upon mechanism used for selecting an Action from the 
 * set of Actions for the request.
 * </p>
 * 
 * 
 * @param request the request for which the Action needs to be selected
 * @param actions array of Action from which the action for the request needs to be selected
 * @return 
 * @throws ActionSelectionException if exception occurs when selecting the Action for the Request.
 * @throws IllegalArgumentException: if any of the parameters passed is null
 */
    public com.topcoder.web.frontcontroller.Action select(javax.servlet.http.HttpServletRequest request, Action[] actions);
/**
 * <p>
 * Sets the level of Logging to be performed and the Log instance to be used for performing the Logging.
 * </p
 * 
 * 
 * @param logger the instance of Log to perform the Logging
 * @param loggingLevel the instance of Level to indicate the Level of Logging to be done.
 * @throws IllegalArgumentException: if any of the parameters passed is null
 */
    public void setLogging(com.topcoder.util.log.Log logger, com.topcoder.util.log.Level loggingLevel);
}

