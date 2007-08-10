/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.server.OrpheusActionContext;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A custom {@link Handler} implementation that forces a configured request parameter to have a configured value.
 * This handler relies on the provided {@link ActionContext} being an instance of {@link OrpheusActionContext}.
 * Only single-valued parameters are supported.</p>
 *
 * @version 1.0
 */
public class SetParameterHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the name of the XML configuration element containing the name of the
     * parameter that this handler should set.</p>
     */
    private final static String PARAMETER_NAME_CONFIG = "parameter_name";

    /**
     * <p>A <code>String</code> providing the name of the XML configuration element containing the value of the
     * parameter that this handler should set.</p>
     */
    private final static String PARAMETER_VALUE_CONFIG = "parameter_value";

    /**
     * <p>Initializes a new <code>SetParameterHandler</code> based on the configuration parameters provided by the
     * specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;parameter_name&gt;param&lt;/warning_result&gt;
     *      &lt;parameter_value&gt;value&lt;/warning_result&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public SetParameterHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, PARAMETER_NAME_CONFIG, true);
        readAsString(element, PARAMETER_VALUE_CONFIG, true);
    }

    /**
     * <p>Processes the incoming request. If a user attempting to login to application is currently already logged in
     * then the request is forwarded to a page notifying on that and suggesting to logout first.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        } else if (!(context instanceof OrpheusActionContext)) {
            throw new HandlerExecutionException("This handler requires an OrpheusActionContext");
        } else {
            addRequestParameter((OrpheusActionContext) context);
        }

        return null;
    }

    /**
     * Adds the configured parameter to the context's HTTP request by replacing
     * the request with a wrapped version
     *
     * @param context the <code>OrpheusActionContext</code> to modify.
     */
    private void addRequestParameter(OrpheusActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final String parameterName = getString(PARAMETER_NAME_CONFIG);
        final String parameterValue = getString(PARAMETER_VALUE_CONFIG);

        context.setRequest(new HttpServletRequestWrapper(request) {

            public String getParameter(String name) {
                String[] values = getParameterValues(name);

                return (((values == null) || (values.length == 0)) ? null : values[0]);
            }

            public Enumeration getParameterNames() {
                return Collections.enumeration(getParameterMap().keySet());
            }

            public String[] getParameterValues(String name) {
                return parameterName.equals(name) ? new String[]{parameterValue}
                       : request.getParameterValues(name);
            }

            public Map getParameterMap() {
                Map tempMap = new HashMap(request.getParameterMap());

                tempMap.put(parameterName, new String[]{parameterValue});

                return Collections.unmodifiableMap(tempMap);
            }
        });
    }
}

