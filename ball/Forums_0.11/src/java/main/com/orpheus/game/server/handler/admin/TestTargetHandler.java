/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>A custom {@link Handler} implementation to be used for testing if the specified target is valid for specified URL.
 * </p>
 *
 * @author isv
 * @version 1.0
 */
public class TestTargetHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>Constructs new <code>TestTargetHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;text_param_key&gt;text&lt;/text_param_key&gt;
     *      &lt;url_param_key&gt;text&lt;/url_param_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public TestTargetHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, TEXT_PARAM_NAME_CONFIG, true);
        readAsString(element, URL_PARAM_NAME_CONFIG, true);
    }

    /**
     * <p>Processes the incoming request. If a current session is associated with authenticated player then the hosting
     * slot is located based on the current game and domain (identified by request paramaters) and the brainteaser ID
     * and a media type are put to request to be used later by <code>PuzzleRenderingHandler</code>.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            HttpServletRequest request = context.getRequest();
            // Get text and URL for the new target from request parameters
            String newText = normalize(request.getParameter(getString(TEXT_PARAM_NAME_CONFIG)));
            String newURL = request.getParameter(getString(URL_PARAM_NAME_CONFIG)).trim();
            // Verify if new target is accessible on specified page.
            Boolean targetCheckResult = isTargetValid(newText, newURL);
            if (targetCheckResult == null) {
                // Could not retrieve the desired page or could not gather statistics from that page
                request.setAttribute("targetTestResult",
                                     "Could not verify validity of target [" + newText + "] for URL\n[" + newURL + "] "
                                     + "due to unexpected error");
            } else if (!targetCheckResult.booleanValue()) {
                // Target does not exist on the specified page
                request.setAttribute("targetTestResult",
                                     "The target [" +newText + "] is not valid for URL\n[" + newURL + "]");
            }
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not test the target for validity", e);
        }
    }
}
