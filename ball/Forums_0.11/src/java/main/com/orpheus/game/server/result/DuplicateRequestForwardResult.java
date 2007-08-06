/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.result;

import com.orpheus.game.server.util.Request;
import com.orpheus.game.server.util.LogHelper;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import com.topcoder.web.frontcontroller.results.ForwardResult;
import org.w3c.dom.Element;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>A custom {@link ForwardResult} implementation which is used for handling the duplicate requests from the clients.
 * The request must be reached to this result only if it is detected that the current request duplicates the original
 * request serviced before and the current request must be responded with same response as original one.</p>
 *
 * @author isv
 * @version 1.0
 */
public class DuplicateRequestForwardResult extends ForwardResult {

    /**
     * <p>A <code>LogHelper</code> providing the functionality for logging the details for desired event.</p>
     */
    private final LogHelper logger;

    /**
     * <p>Constructs new <code>DuplicateRequestForwardResult</code> instance initialized based on the configuration
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
    public DuplicateRequestForwardResult(Element element) {
        super(element);
        this.logger = new LogHelper(element);
    }


    /**
     * <p>Executes this result. Reaching this point indicates that a duplicate request has been received from the client
     * and that request has to be responded with response to original request.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param actualLogger a <code>Log</code> to log the events to.
     * @param loggingLevel a <code>Level</code> specifying the logging level.
     * @throws ResultExecutionException if an unrecoverable error prevents the result from successful execution.
     * @throws IllegalArgumentException if specified <code>context</code> is <code>null</code>.
     */
    public void execute(ActionContext context, Log actualLogger, Level loggingLevel) throws ResultExecutionException {
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession(false);
        this.logger.log(context, actualLogger, loggingLevel);
        if (session != null) {
            synchronized (session) {
                // Obtain the details for the original request which is duplicated by the current request
                Request lastServicedRequest = (Request) session.getAttribute("LAST_SERVICED_REQUEST");
                // Remove all attributes from current request
                Enumeration attrNames = request.getAttributeNames();
                while (attrNames.hasMoreElements()) {
                    String attrName = (String) attrNames.nextElement();
                    request.removeAttribute(attrName);
                }
                // Populate the current(duplicate) request with attributes from last serviced (original) request
                Map attributes = lastServicedRequest.getAttributes();
                Iterator iterator = attributes.entrySet().iterator();
                Map.Entry entry;
                while (iterator.hasNext()) {
                    entry = (Map.Entry) iterator.next();
                    request.setAttribute((String) entry.getKey(), entry.getValue());
                }
                // Forward the current request to URL which the original last serviced request was forwarded to
                String responseUrl = lastServicedRequest.getResponseUrl();
                try {
                    this.logger.log(context, "Forwarding request " + Request.getRequestId(request)
                                             + " which duplicates request " + lastServicedRequest.getRequestId()
                                             + " to URL : " + responseUrl, actualLogger, loggingLevel);
                    RequestDispatcher dispatcher = context.getRequest().getRequestDispatcher(responseUrl);
                    dispatcher.forward(context.getRequest(), context.getResponse());
                } catch (Exception e) {
                    throw new ResultExecutionException("Failed to forward the request to url: " + responseUrl, e);
                }
            }
        }
    }
}
