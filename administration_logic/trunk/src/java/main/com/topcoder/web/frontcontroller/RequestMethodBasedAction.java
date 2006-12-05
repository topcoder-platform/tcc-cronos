
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;
/**
 * <p>
 * This interface extends the Action interface and specifies which all the request method types are supported by the Action. Also this interfaces provides a method that overrides the execute() method
 * defined by the base interface and has support for logging and tracing during the execution of the various stages in the action using the Logging Wrapper 1.2 component.
 * The implementation of this interface as it with its base interface are required to be thread-safe.
 * </p>
 * 
 * 
 */
public interface RequestMethodBasedAction extends com.topcoder.web.frontcontroller.Action {
/**
 * <p>
 * Returns an array of String elements that define the supported request methods by this Action instance.
 * This method can return a null indicating this action does not support any request types.
 * </p>
 * 
 * 
 * @return an array of String elements that define the supported request methods by this Action instance.
 */
    public String[] getSupportedRequestMethods();
/**
 * <p>
 * Executes the Action as defined in the overloaded method in the base interface and performs the Logging at 
 * various steps in the execution using the instance of Log passed and depending upon the Logging level required
 * as passed in the parameter value.
 * </p>
 * 
 * 
 * @param context the ActionContext object for the current request
 * @param logger the instance of Log to perform the Logging
 * @param loggingLevel the instance of Level to indicate the Level of Logging to be done.
 * @throws ActionExecutionException: if there are problems in executing this Action implementation
 * @throws IllegalArgumentException: if any of the parameters passed is null
 */
    public void execute(com.topcoder.web.frontcontroller.ActionContext context, com.topcoder.util.log.Log logger, com.topcoder.util.log.Level loggingLevel);
}


