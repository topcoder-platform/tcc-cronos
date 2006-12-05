package com.topcoder.web.frontcontroller;

import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;

/**
 * <p> Handler interface defines the contract to process the user request. The Action object contains a list of handler objects, which will be executed one by one. If the handler's execute method returns null, the Action object will continue to execute the next handler in the chain (if there is no handler left, the success Result will be executed), otherwise, it will use the returned resultCode to pick a Result object to generate the response. We can configure unlimited number of handlers for an Aciton object. Its implementation must be thread-safe, since it is used to handle all incoming user requests for the action it belongs to. </p>
 * 
 */
public interface Handler {
    /**
     * <p>Process the user request. Null should be returned, if it wants Action object to continue to execute the next handler (if there is no handler left, the 'success' Result will be executed). It should return a non-empty resultCode if it want to execute a corresponding Result immediately, and ignore all following handlers. </p>
     * 
     * 
     * @return null or a non-empty resultCode string.
     * @param context the ActionContext object.
     * @throws IllegalArgumentException if the context is null.
     * @throws HandlerExecutionException if fail to execute this handler.
     */
    public String execute(com.topcoder.web.frontcontroller.ActionContext context)
        throws HandlerExecutionException;
}
