/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.server.util.Request;
import com.orpheus.game.server.OrpheusFunctions;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>A custom {@link Handler} implementation which is responsible for preventing the duplicate requests from reaching
 * and being processed by server.</p>
 *
 * @author isv
 * @version 1.0
 */
public class DuplicateRequestHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>Constructs new <code>DuplicateRequestHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * <pre>
     *     &lt;handler name=&quot;x&quot type=&quot;y&quot;&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public DuplicateRequestHandler(Element element) {
    }


    public String execute(ActionContext context) throws HandlerExecutionException {
        Date currentTime = new Date();
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            synchronized (session) {
                Request lastServicedRequest = (Request) session.getAttribute("LAST_SERVICED_REQUEST");
                Request currentRequest = new Request(request);
                if (lastServicedRequest != null) {
                    // Check if the current request represents the same request which has been already serviced
                    if (lastServicedRequest.equals(currentRequest)) {
                        // Get the time when the last serviced request arrived and compare it to current time to check
                        // if there are chances that the duplicate request is issued by browser but not initiated by
                        // player
                        Date lastServicedRequestArrivalTime
                            = (Date) session.getAttribute("LAST_SERVICED_REQUEST_ARRIVAL_TIME");
                        if (currentTime.getTime() - lastServicedRequestArrivalTime.getTime()
                            <= OrpheusFunctions.getDuplicateRequestRecognitionInterval()) {
                            session.setAttribute("LAST_SERVICED_REQUEST_ARRIVAL_TIME", currentTime);
                            return "duplicateRequestResult";
                        }
                    }
                }
                // The current request is not a duplicate for the request which has been just serviced before
                // so save request details to session and return null to allow the request to be handled by
                // subsequent handlers in chain
                session.setAttribute("CURRENT_REQUEST", currentRequest);
                session.setAttribute("CURRENT_REQUEST_ARRIVAL_TIME", currentTime);
            }
        }
        return null;
    }
}
