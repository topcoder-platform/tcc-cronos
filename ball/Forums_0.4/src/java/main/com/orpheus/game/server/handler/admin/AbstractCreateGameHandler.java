/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.administration.entities.GameImpl;
import com.orpheus.administration.entities.HostingBlockImpl;
import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecoder;
import com.topcoder.json.object.io.StandardJSONDecoder;
import com.topcoder.json.object.io.JSONDecodingException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public abstract class AbstractCreateGameHandler extends AbstractGameServerHandler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    protected final Context jndiContext;

    /**
     * <p>Constructs new <code>AbstractCreateGameHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>Below is a sample of an XML element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;games_key&gt;games&lt;/games_key&gt;
     *      &lt;auctions_request_attr_key&gt;openAuctions&lt;/auctions_request_attr_key&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or any of required configuration parameters
     *         is missing.
     */
    protected AbstractCreateGameHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, BALL_COLOR_ATTR_NAME_CONFIG, true);
        readAsString(element, KEY_COUNT_ATTR_NAME_CONFIG, true);
        readAsString(element, BLOCK_COUNT_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_START_DATE_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_END_DATE_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_BOUNCE_TYPE_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_PRIZE_TYPE_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_COMPLETION_TYPE_ATTR_NAME_CONFIG, true);
        readAsString(element, BLOCK_INFO_PARAM_NAME_CONFIG, true);
        readAsString(element, MAX_SLOT_TIME_JSON_PROP_CONFIG, true);
        readAsString(element, SLOT_COUNT_JSON_PROP_CONFIG, true);
        readAsInteger(element, MINIMUM_BID_VALUE_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
    }

    protected Game createGameInstance(ActionContext context, boolean fromSession) throws HandlerExecutionException {
        try {
            HttpServletRequest request = context.getRequest();
            HttpSession session = request.getSession(false);

            // Get the ball color id, the key count, the block count
            Long ballColorId = (Long) session.getAttribute(getString(BALL_COLOR_ATTR_NAME_CONFIG));
            Integer keyCount = (Integer) session.getAttribute(getString(KEY_COUNT_ATTR_NAME_CONFIG));
            Integer blockCount = (Integer) session.getAttribute(getString(BLOCK_COUNT_ATTR_NAME_CONFIG));

            // Get the game start and end dates
            Date gameStartDate = (Date) session.getAttribute(getString(GAME_START_DATE_ATTR_NAME_CONFIG));
            Date gameEndDate = (Date) session.getAttribute(getString(GAME_END_DATE_ATTR_NAME_CONFIG));

            // Get the bounce points, prize amounts calculation types along with game completion type
            Integer bounceCalcType = (Integer) session.getAttribute(getString(GAME_BOUNCE_TYPE_ATTR_NAME_CONFIG));
            Integer prizeCalcType = (Integer) session.getAttribute(getString(GAME_PRIZE_TYPE_ATTR_NAME_CONFIG));
            Integer completionType = (Integer) session.getAttribute(getString(GAME_COMPLETION_TYPE_ATTR_NAME_CONFIG));

            // Get the information about the blocks
            JSONObject[] blockObjs = getBlocksData(request, fromSession);
            GameDataEJBAdapter gameData = getGameDataEJBAdapter(this.jndiContext);

            // Get the BallColor object for the ball color id
            BallColor[] ballcolors = gameData.findAllBallColors();
            BallColor ballColor = null;
            for (int i = 0; i < ballcolors.length; i++) {
                if (ballcolors[i].getId().equals(ballColorId)) {
                    ballColor = ballcolors[i];
                    break;
                }
            }
            // Create a game instance
            GameImpl game = new GameImpl();
            game.setBallColor(ballColor);
            game.setKeyCount(keyCount.intValue());
            game.setStartDate(gameStartDate);
            game.setEndDate(gameEndDate);
            game.setBouncePointCalculationType(bounceCalcType.intValue());
            game.setPrizeCalculationType(prizeCalcType.intValue());
            game.setCompletionType(completionType.intValue());
            // Set hosting blocks for the game
            HostingBlock[] hBlocks = new HostingBlock[blockObjs.length];
            // for every [obj] in blockObjs
            for (int i = 0; i < blockObjs.length; i++) {
                HostingBlockImpl newBlock = new HostingBlockImpl();
                newBlock.setMaxHostingTimePerSlot(blockObjs[i].getInt(getString(MAX_SLOT_TIME_JSON_PROP_CONFIG)));
                hBlocks[i] = newBlock;
            }
            game.setBlocks(hBlocks);
            return game;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not create new game instance due to unexpected err", e);
        }
    }

    /**
     * <p>Gets the details for the blocks to be created in context of the new game from the parameters of specified
     * request.</p>
     *
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param fromSession <code>true</code> if block details are to be read from session; <code>false</code> otherwise.
     * @return a <code>JSONObject</code> array providing the details for blocks to be created within the game.
     * @throws JSONDecodingException if the JSON string providing blocks info is not correctly formatted.
     */
    protected final JSONObject[] getBlocksData(HttpServletRequest request, boolean fromSession)
        throws JSONDecodingException {
        // Get the information about the blocks
        String[] blocks;
        if (fromSession) {
            blocks = (String[]) request.getSession(false).getAttribute(getString(BLOCK_INFO_PARAM_NAME_CONFIG));
        } else {
            blocks = request.getParameterValues(getString(BLOCK_INFO_PARAM_NAME_CONFIG));
        }
        JSONObject[] blockObjs = new JSONObject[blocks.length];
        JSONDecoder decoder = new StandardJSONDecoder();
        for (int i = 0; i < blocks.length; i++) {
            blockObjs[i] = (JSONObject) decoder.decode(blocks[i]);
        }
        return blockObjs;
    }
}
