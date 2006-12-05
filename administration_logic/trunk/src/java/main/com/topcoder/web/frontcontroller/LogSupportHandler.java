
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;
/**
 * <p>
 * This interface extends the Handler interface and provides the support required for performing the 
 * Logging and tracing using the Logging Wrapper 1.2 component during the various stages of execution of 
 * the Handler. This method overrides the only method defined by the Handler interface with additonal
 * parameters provided to perform the logging.
 * The implementations of this interface are required to be thread-safe as with the base interface.
 * </p>
 * 
 * 
 */
public interface LogSupportHandler extends com.topcoder.web.frontcontroller.Handler {
/**
 * <p>
 * Executes the Handler as defined in the overloaded method in the base interface and performs the Logging at 
 * various steps in the execution using the instance of Log passed and depending upon the Logging level required
 * as passed in the parameter value.
 * </p>
 * 
 * 
 * 
 * @param context the ActionContext object for the current request
 * @param logger the instance of Log to perform the Logging
 * @param level the instance of Level to indicate the Level of Logging to be done.
 * @return null or a non-empty resultCode string.
 * @throws HandlerExecutionException: if there are problems in executing this Handler implementation
 * @throws IllegalArgumentException: if any of the parameters passed is null
 */
    public String execute(com.topcoder.web.frontcontroller.ActionContext context, com.topcoder.util.log.Log logger, com.topcoder.util.log.Level level);
}


