/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.result;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.FrontControllerHelper;
import com.topcoder.web.frontcontroller.FrontControllerLogger;
import com.topcoder.web.frontcontroller.LogSupportResult;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import com.topcoder.web.registration.parsers.Result;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpSession;

/**
 * <p>A custom {@link Result} implementation to be used for post-processing of the registration of a player to a
 * particular game. The current implementation analyzes the current session and if there is an attribute providing the
 * URL to forward the request to after successful registration of a player to a game then the request is forwarded to
 * that URL otherwise a redirection to <code>Game Registration Confirmation</code> page occurs.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PostGameRegistrationResult implements LogSupportResult {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request/session attribute to bind the game registration post URL.</p>
     */
    public static final String POST_URL_ATTR_CONFIG = "game_registration_post_url_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * path to default <code>Game Registration Confirmation</code> page.</p>
     */
    public static final String GAME_CONFIRMATION_URL_CONFIG = "game_confirmation_url";

    /**
     * <p>A <code>String</code> providing the name of the session attribute which is respected to provide the URL to
     * forward the request to after successful registration.
     */
    private final String postUrlSessionAttrName;

    /**
     * <p>A <code>String</code> providing the URL to forward the request to present the player with game registration
     * confirmation page.</p>
     */
    private final String gameRegistrationConfirmationURL;

    /**
     * <p>Constructs new <code>PostGameRegistrationResult</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_confirmation_url&gt;/WEB-INF/plugin/join-confirm.jsp&lt;/game_confirmation_url&gt;
     *      &lt;game_registration_post_url_key&gt;gameRegistrationPostURL&lt;/game_registration_post_url_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public PostGameRegistrationResult(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        this.postUrlSessionAttrName = getElement(element, POST_URL_ATTR_CONFIG);
        this.gameRegistrationConfirmationURL = getElement(element, GAME_CONFIRMATION_URL_CONFIG);
    }

    /**
     * <p>Executes this result. Determines the next URL to forward the request to based on the value of session
     * attribute identified by name configured under {@link #POST_URL_ATTR_CONFIG} property and forwards the request to
     * that URL. If such a session attribute does not exist then forwards request to a default URL configured under
     * {@link #GAME_CONFIRMATION_URL_CONFIG} property.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @throws ResultExecutionException if an unrecoverable error prevents this result from successful execution.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public void execute(ActionContext context) throws ResultExecutionException {
        execute(context, LogFactory.getLog(), Level.DEBUG);
    }

    /**
     * <p>Executes this result. Determines the next URL to forward the request to based on the value of session
     * attribute identified by name configured under {@link #POST_URL_ATTR_CONFIG} property and forwards the request to
     * that URL. If such a session attribute does not exist then forwards request to a default URL configured under
     * {@link #GAME_CONFIRMATION_URL_CONFIG} property.</p> 
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param actualLogger a <code>Log</code> to log the events to.
     * @param loggingLevel a <code>Level</code> specifying the logging level.
     * @throws ResultExecutionException if an unrecoverable error prevents the handler from successful execution.
     * @throws IllegalArgumentException if specified <code>context</code> is <code>null</code>.
     */
    public void execute(ActionContext context, Log actualLogger,
        Level loggingLevel) throws ResultExecutionException {
        FrontControllerHelper.checkObjectNull(context, "context");

        FrontControllerLogger logger = new FrontControllerLogger(this.getClass().getName(), actualLogger, loggingLevel);
        String method = "execute";
        logger.debug("entrance of method: " + method);
        logger.info(context, actualLogger, loggingLevel);

        try {
            String url;
            HttpSession session = context.getRequest().getSession(false);
            synchronized (session) {
                String postUrl = (String) session.getAttribute(this.postUrlSessionAttrName);
                if (postUrl != null) {
                    url = postUrl;
                } else {
                    url = this.gameRegistrationConfirmationURL;
                }
                session.removeAttribute(this.postUrlSessionAttrName);
            }
            logger.debug("base url: " + url);
            context.getRequest().getRequestDispatcher(url).forward(context.getRequest(), context.getResponse());
        } catch (Exception e) {
            logger.error("execute", e);
            throw new ResultExecutionException("Failed to forward the request to game registration post URL", e);
        }
        logger.debug("exit of method: " + method);
    }

    /**
     * <p>Gets the text value of the specified node (key) from the given element.</p>
     *
     * @param element an <code>Element</code> providing the element to be used.
     * @param key a <code>String</code> providing the tag name for element to be extracted.
     * @return a <code>String</code> providing the text value of the given tag.
     * @throws IllegalArgumentException if the given <code>key</code> is not present or has empty value.
     */
    private static String getElement(Element element, String key) {
        NodeList nodeList;
        nodeList = element.getElementsByTagName(key);
        if (nodeList.getLength() == 0) {
            throw new IllegalArgumentException("Key '" + key + "' is missing in the element");
        }
        if (nodeList.getLength() != 1) {
            throw new IllegalArgumentException("Key '" + key + "' is occurring more than once in the element");
        }
        Node node = nodeList.item(0).getFirstChild();
        if (node == null) {
            throw new IllegalArgumentException("The [" + key + "] element is not found");
        }
        String value = node.getNodeValue();
        if ((value == null) || (value.trim().length() == 0)) {
            throw new IllegalArgumentException("The [" + key + "] element is empty");
        }
        return value;
    }
}
