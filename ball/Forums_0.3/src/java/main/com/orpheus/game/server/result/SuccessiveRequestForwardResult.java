/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.result;

import org.w3c.dom.Element;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.registration.parsers.Result;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;
import com.orpheus.game.server.util.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Date;

/**
 * <p>A custom {@link Result} implementation which is responsible for recording in the session the details of the
 * incoming request as well as the details for the content and response which the request is responded with.</p>
 *
 * @author isv
 * @version 1.0
 */
public class SuccessiveRequestForwardResult extends StatsCollectingResult {

    /**
     * <p>Constructs new <code>SuccessiveRequestForwardResult</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
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
    public SuccessiveRequestForwardResult(Element element) {
        super(element);
    }

    /**
     * <p>Executes this result. </p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param actualLogger a <code>Log</code> to log the events to.
     * @param loggingLevel a <code>Level</code> specifying the logging level.
     * @throws ResultExecutionException if an unrecoverable error prevents the result from successful execution.
     * @throws IllegalArgumentException if specified <code>context</code> is <code>null</code>.
     */
    public void execute(ActionContext context, Log actualLogger, Level loggingLevel) throws ResultExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            synchronized (session) {
                Request currentRequest = (Request) session.getAttribute("CURRENT_REQUEST");
                Date currentRequestArrivalTime = (Date) session.getAttribute("CURRENT_REQUEST_ARRIVAL_TIME");
                if (currentRequest != null) {
                    currentRequest.setServiced(true);
                    currentRequest.setResponseUrl(getForwardBaseUrl());
                    Enumeration attrNames = request.getAttributeNames();
                    while (attrNames.hasMoreElements()) {
                        String attrName = (String) attrNames.nextElement();
                        currentRequest.addAttribute(attrName, request.getAttribute(attrName));
                    }
                    session.removeAttribute("CURRENT_REQUEST");
                    session.removeAttribute("CURRENT_REQUEST_ARRIVAL_TIME");
                    session.setAttribute("LAST_SERVICED_REQUEST", currentRequest);
                    session.setAttribute("LAST_SERVICED_REQUEST_ARRIVAL_TIME", currentRequestArrivalTime);
                }
            }
        }
        super.execute(context, actualLogger, loggingLevel);
    }
}
