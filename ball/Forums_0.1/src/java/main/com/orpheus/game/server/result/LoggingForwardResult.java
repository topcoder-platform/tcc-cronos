/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.result;

import com.orpheus.game.server.util.LogHelper;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import com.topcoder.web.frontcontroller.results.ForwardResult;
import org.w3c.dom.Element;

/**
 * <p>A {@link ForwardResult} which logs the details for the event which has triggered forwarding the request to target
 * URL. The parameters for the logged message are specified during construction and are evaluated each time based on
 * provided context.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LoggingForwardResult extends ForwardResult {

    /**
     * <p>A <code>LogHelper</code> providing the functionality for logging the details for desired event.</p>
     */
    private final LogHelper logger;

    /**
     * <p>Constructs new <code>LoggingForwardResult</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * <pre>
     *     &lt;result name=&quot;x&quot type=&quot;y&quot;&gt;
     *      &lt;log-message&gt;had viewed the Unlocked Sites list for game {0}&lt;/log-message&gt;
     *      &lt;parameters&gt;domain,gameId&lt;/parameters&gt;
     *      &lt;category&gt;WEBSITE&lt;/category&gt;
     *     &lt;/result&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public LoggingForwardResult(Element element) {
        super(element);
        this.logger = new LogHelper(element);
    }

    /**
     * <p>Executes this result.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param actualLogger a <code>Log</code> to log the events to.
     * @param loggingLevel a <code>Level</code> specifying the logging level.
     * @throws ResultExecutionException if an unrecoverable error prevents the result from successful execution.
     * @throws IllegalArgumentException if specified <code>context</code> is <code>null</code>.
     */
    public void execute(ActionContext context, Log actualLogger, Level loggingLevel) throws ResultExecutionException {
        this.logger.log(context, actualLogger, loggingLevel);
        super.execute(context, actualLogger, loggingLevel);
    }

    /**
     * <p>Gets the logger to be used for logging the details for events triggered while executing this result.</p>
     *
     * @return a <code>LogHelper</code> to be used for logging the events. 
     */
    protected LogHelper getLogger() {
        return this.logger;
    }
}
