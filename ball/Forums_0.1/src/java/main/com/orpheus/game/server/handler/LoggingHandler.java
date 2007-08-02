/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.server.util.LogHelper;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.LogSupportHandler;
import org.w3c.dom.Element;

/**
 * <p>A custom {@link Handler} implementation to be used in conjunction with other handlers and performing the logging
 * of the pre-configured messages customized base on the provided context.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LoggingHandler implements LogSupportHandler {

    /**
     * <p>A <code>LogHelper</code> providing the functionality for logging the details for desired event.</p>
     */
    private final LogHelper logger;

    /**
     * <p>Constructs new <code>LoggingHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;log-message&gt;logged out&lt;/log-message&gt;
     *      &lt;category&gt;WEBSITE&lt;/category&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public LoggingHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        this.logger = new LogHelper(element);
    }

    /**
     * <p>Processes the incoming request. Logs the pre-configured message customized based on the specified context.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> always.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        return execute(context, null, null);
    }


    /**
     * <p>Processes the incoming request. Logs the pre-configured message customized based on the specified context.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param logger a <code>Log</code> to log the events to.
     * @param loggingLevel a <code>Level</code> specifying the logging level.
     * @return <code>null</code> always.
     * @throws HandlerExecutionException if an unrecoverable error prevents the handler from successful execution.
     * @throws IllegalArgumentException if specified <code>context</code> is <code>null</code>.
     */
    public String execute(ActionContext context, Log logger, Level loggingLevel) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        this.logger.log(context, logger, loggingLevel);
        return null;
    }
}
