
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import com.topcoder.web.frontcontroller.ActionSelectionException;
import com.topcoder.web.frontcontroller.NoActionForRequestMethodException;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;
/**
 * <p> ActionSelector interface defines the contract to select an Action to execute by matching the request uri of the incoming request. Its implementation must provide a constructor taking no arg in order to be created from FrontController. Its implementation must be thread-safe. </p>
 * 
 * The interfaces ActionSelector has
 * a "use" relation with Action interface
 * but have not been shown avoid clutter
 * in the diagram
 */
public interface ActionSelector {
/**
 * <p>select an Action from the specified array of actions by matching the request uri. requestUri must be non-null, non-empty string, and actions must be non-null, and contain at least one element. </p>
 * 
 * 
 * @return the matched Action object, or null if there is no match.
 * @param requestUri the request uri
 * @param actions an array of action objects to select.
 * @throws IllegalArgumentException if requestUri is null or empty string. or actions is null or empty.
 * @throws ActionSelectionException if exception occurs when matching the uri
 */
    public com.topcoder.web.frontcontroller.Action select(String requestUri,Action[] actions);
}


