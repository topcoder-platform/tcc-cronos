
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;
/**
 * <p>
 * This interface extends the ResourceParser interface and provides the mechanism to perform logging and 
 * tracing using the Logging Wrapper 1.2 component. The method defined by this interface will be called 
 * immediately after an instance of the implementation of this interface is created by the FrontController.
 * This allows the Logging operation to be performed before, during and after the processing/parsing of the
 * resource files. As with the base interface, the implementations are not required to be thread-safe.
 * </p>
 * 
 */
public interface LogSupportResourceParser extends com.topcoder.web.frontcontroller.ResourceParser {
/**
 * <p>
 * Sets the level of Logging to be performed and the Log instance to be used for performing the Logging.
 * </p>
 * 
 * 
 * @param logger the instance of Log to perform the Logging
 * @param loggingLevel the instance of Level to indicate the Level of Logging to be done.
 * @throws IllegalArgumentException: if any of the parameters passed is null
 */
    public void setLogging(com.topcoder.util.log.Log logger, com.topcoder.util.log.Level loggingLevel);
}


