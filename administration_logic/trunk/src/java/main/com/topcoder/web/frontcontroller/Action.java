
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.ActionExecutionException;
import com.topcoder.web.frontcontroller.NoSuchResultException;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;
/**
 * <p> Action interface defines an execute method to process user request and write back response. The action url pattern is used to match the request uri, and the action will be executed if they match each other. The name attribute is only used to describe the action. For each resource file (action resource file), one Action object will be created, which is used to handle all user requests matched to this action. So its implementation must be thread-safe. Custom implementation is expected to extend the DefaultAction class for convenience rather than implement this interface directly. It is recommended to consider to implement Handler and Result interface first to add the functionality you want. </p>
 * 
 */
public interface Action {

/**
 * <p> Process the user request and write back response. The HttpServletRequest and HttpServletResponse objects are wrapped in the ActionContext object, and which could also contain some configured attrbutes. </p>
 * 
 * 
 * @param context the ActionContext object.
 * @throws IllegalArgumentException if the context is null.
 * @throws ActionExecutionException if fail to execute the action.
 */
    public void execute(com.topcoder.web.frontcontroller.ActionContext context);
/**
 * <p>Return the action name. </p>
 * 
 * 
 * @return the action name.
 */
    public String getName();
/**
 * <p>Return the action url pattern.</p>
 * 
 * 
 * @return the action url pattern.
 */
    public String getUrlPattern();
}


