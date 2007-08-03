/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.game.server.GamePlayInfo;
import com.orpheus.game.server.OrpheusFunctions;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>A custom {@link Handler} implementation to be used in conjunction with <code>PuzzleRenderingHandler</code> for
 * rendering a puzzle to be presented to administrator for preview. This handler prepares the data necessary for <code>
 * PuzzleRenderingHandler</code> to locate and render the desired puzzle as currently there is no such handler
 * provided by any of existing components.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ViewPuzzleHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>ViewPuzzleHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *      &lt;puzzle_id_request_attribute_key&gt;puzzleId&lt;/puzzle_id_request_attribute_key&gt;
     *      &lt;media_type_request_attribute_key&gt;mediaType&lt;/media_type_request_attribute_key&gt;
     *      &lt;media_type&gt;DHTML&lt;/media_type&gt;
     *      &lt;slot_param_key&gt;slotId&lt;/slot_param_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public ViewPuzzleHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_PLAY_ATTR_NAME_CONFIG, true);
        readAsString(element, PUZZLE_ID_ATTR_NAME_CONFIG, true);
        readAsString(element, MEDIA_TYPE_ATTR_NAME_CONFIG, true);
        readAsString(element, MEDIA_TYPE_VALUE_CONFIG, true);
        readAsString(element, SLOT_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
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
            // Get game ID and domain from request parameters
            long slotId = getLong(SLOT_ID_PARAM_NAME_CONFIG, request);
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);

            // Find a hosting slot for specified game and domain which is not completed by the player yet
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            HostingSlot hostingSlot = gameDataEJBAdapter.getSlot(slotId);

            // Get the ID of a puzzle to be presented to admin and put puzzle id and media type to request for use by
            // PuzzleRenderingHandler
            request.setAttribute(getString(PUZZLE_ID_ATTR_NAME_CONFIG), hostingSlot.getPuzzleId());
            request.setAttribute(getString(MEDIA_TYPE_ATTR_NAME_CONFIG), getString(MEDIA_TYPE_VALUE_CONFIG));

            // Record the time of starting the puzzle
            GamePlayInfo gamePlayInfo = getGamePlayInfo(context);
            synchronized (gamePlayInfo) {
                gamePlayInfo.recordWinGamePuzzleStart(gameId, slotId, new Date());
            }
            request.setAttribute("timeLeft", new Long(OrpheusFunctions.getSolveWinGamePuzzlePeriod()));

            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not prepare the puzzle data for rendering", e);
        }
    }
}
