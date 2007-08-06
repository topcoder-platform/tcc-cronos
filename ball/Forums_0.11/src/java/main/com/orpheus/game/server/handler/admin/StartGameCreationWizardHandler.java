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
import java.util.Map;
import java.util.HashMap;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public class StartGameCreationWizardHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the game ID from.</p>
     */
    protected static final String GAME_CREATION_TYPE_PARAM_NAME_CONFIG = "game_creation_type_param_key";

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> ID of game creation type to a <code>String</code> providing
     * the logical name for the respective view to be presented to administrator to start game creation in accordance
     * with selected creation type.</p>
     */
    private final Map types2views;

    /**
     * <p>Constructs new <code>StartGameCreationWizardHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *      &lt;game_id_param_key&gt;gameId&lt;/game_id_param_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public StartGameCreationWizardHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_CREATION_TYPE_PARAM_NAME_CONFIG, true);
        this.types2views = new HashMap();
        String[] creationTypes = getElement(element, "creation-types", true).split(",");
        String[] creationViews = getElement(element, "creation-type-wizard-entries", true).split(",");
        if (creationTypes.length == creationViews.length) {
            for (int i = 0; i < creationViews.length; i++) {
                this.types2views.put(creationTypes[i], creationViews[i]);
            }
        } else {
            throw new IllegalArgumentException("The creation types do not match the creation views");
        }
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
            String creationType = request.getParameter(getString(GAME_CREATION_TYPE_PARAM_NAME_CONFIG));
            if (this.types2views.containsKey(creationType)) {
                return (String) this.types2views.get(creationType);
            } else {
                throw new HandlerExecutionException("The game creation type [" + creationType +"] is not supported");
            }
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not launch the game creation wizard", e);
        }
    }
}
