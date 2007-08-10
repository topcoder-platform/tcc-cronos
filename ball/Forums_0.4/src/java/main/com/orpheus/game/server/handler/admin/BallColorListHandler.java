/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;

/**
 * <p>A custom {@link Handler} implementation which retrieves a list of existing ball colors and puts them to request to
 * be re-used by subsequent handlers in chain. </p>
 *
 * @author isv
 * @version 1.0
 */
public class BallColorListHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>BallColorListHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>Here is an example of the xml element:</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;ball_colors_key&gt;colors&lt;/ball_colors_key&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public BallColorListHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, BALL_COLORS_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
    }

    /**
     * <p>Process the user request. Null should be returned, if it wants Action object to continue to execute the next
     * handler (if there is no handler left, the 'success' Result will be executed). It should return a non-empty
     * resultCode if it want to execute a corresponding Result immediately, and ignore all following handlers.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return null or a non-empty result code string.
     * @throws HandlerExecutionException if an unrecoverable error prevenst the handler from successful execution.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            context.getRequest().setAttribute(getString(BALL_COLORS_ATTR_NAME_CONFIG),
                                              gameDataEJBAdapter.findAllBallColors());
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get all ball colors.", e);
        }
    }
}
