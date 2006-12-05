/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.administration.Helper;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Provides a handler implementation that deletes an as-yet unreached slot from
 * among the future slots. The handler will receive a game ID, a slot ID,via
 * request parameters of configurable name, and will delete the slot. If the
 * slot has already started hosting (and perhaps even finished) it is treated as
 * fail condition.<br/> In case of failure, the execute method will return a
 * configurable result name and set a HandlerResult instance as a request
 * attribute so that an appropriate response or user message can be generated.
 * In case of success, the execute() method will return null.<br/> For
 * configuration details on this handler, please see Section 3.2.13 of Comp
 * Spec.<br/> Thread-Safety: This class is thread-safe as is required of
 * Handler implementations. To achieve this, it synchronizes over the
 * userProfileManager instance var in the execute() method.
 * 
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class DeleteSlotHandler implements Handler {

    /**
     * This holds the JNDI name to use to look up the GameDataHome service.<br/>
     * This variable is initialized in the constructor and does not change after
     * that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String gameDataJndiName;

    /**
     * This holds the name of the request parameter which will contain the game
     * id associated with pending winner. The value should be able to be parsed
     * into a long value.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String gameIdRequestParamName;

    /**
     * This holds the name of the request parameter which will contain the slot
     * id to delete. The value should be able to be parsed into a long value.<br/>
     * It is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     * 
     */
    private final String slotIdRequestParamName;

    /**
     * This holds the name of the result (as configured in Front Controller
     * component) which should execute in case of execution failure.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String failedResult;

    /**
     * This holds the name of the request attribute to which HandlerResult
     * instance should be assigned to, in case of execution failure.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     * 
     */
    private final String failRequestAttrName;

    /**
     * Creates a ReorderSlotsHandler instance configured from the given xml
     * element. It will initialize the instance variables. It will throw an
     * IllegalArgumentException if configuration details are missing in the
     * handlerElement argument.
     * 
     * Example xml structure:
     * 
     * <pre>
     *      &lt;handler type=&quot;deleteSlot&quot;&gt;
     * 
     *      &lt;!--  JNDI name to use to lookup the GameDataHome interface --&gt;
     *      &lt;game-data-jndi-name&gt;GameDataHome&lt;/ game-data-jndi-name&gt;
     * 
     *      &lt;!--  name of request parameter which will contain the game id --&gt;
     *      &lt;game-id-request-param&gt;gameId&lt;/game-id-request-param&gt;
     * 
     *      &lt;!--  name of request parameter which will contain the slot  id --&gt;
     *      &lt;slot-id-request-param&gt;slotId&lt;/slot-id-request-param&gt;
     * 
     *      &lt;!--  name of result to return in case of execution failure --&gt;
     *      &lt;fail-result&gt;Failed&lt;/fail-result&gt;
     * 
     *      &lt;!--  name of request attribute to store HandlerResult in case of failure--&gt;
     *      &lt;fail-request-attribute&gt;Failed&lt;/fail-request-attribute&gt;
     *      &lt;/handler&gt;
     * 
     * </pre>
     * 
     * @param handlerElement
     *            the XML element containing configuration for this handler.
     * @throws IllegalArgumentException
     *             if handlerElement is null, or contains invalid data.
     */
    public DeleteSlotHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);
        gameDataJndiName = Helper.getValue(handlerElement,
                "/handler/game-data-jndi-name");
        gameIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/game-id-request-param");
        slotIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/slot-id-request-param");
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * <p>
     * This method deletes an as-yet unreached slot from among the future slots.
     * If the slot has already started hosting (and perhaps even finished), it
     * is treated as fail condition. Otherwise it delets the slot.<br/> This
     * method will return null in case of success and will return a configurable
     * result name in case of failure such as any exceptions.
     * </p>
     * 
     * @param actionContext
     *            the ActionContext object.
     * 
     * @return null if operation succeeds, otherwise a configurable result name.
     * @throws IllegalArgumentException
     *             if the context is null.
     * @throws HandlerExecutionException
     *             never by this handler.
     */
    public String execute(ActionContext actionContext)
        throws HandlerExecutionException {
        Helper.checkNull(actionContext, "actionContext");
        HttpServletRequest request = actionContext.getRequest();
        GameData gameData = Helper.getGameData(gameDataJndiName, request,
                failRequestAttrName);
        if (gameData == null) {
            return failedResult;
        }

        // Get game id from request parameter
        String gameIdString = Helper.getRequestParameter(request,
                gameIdRequestParamName, failRequestAttrName);
        // Get slot id from request parameter
        String slotIdString = Helper.getRequestParameter(request,
                slotIdRequestParamName, failRequestAttrName);
        // Parse the gameId and slotId into long values
        Long gameId = Helper.parseLong(request, gameIdString, "gameId",
                failRequestAttrName);
        Long slotId = Helper.parseLong(request, slotIdString, "slotId",
                failRequestAttrName);
        if (gameId == null || slotId == null) {
            return failedResult;
        }
        // Get game instance
        Game game;
        try {
            game = gameData.getGame(gameId.longValue());
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get game via gameId:" + gameId,
                    failRequestAttrName, e);
            return failedResult;
        }
        HostingBlock[] hostingBlocks = game.getBlocks();
        HostingSlot slot = null;
        // Search for slot with id == slotId
        for (int i = 0; i < hostingBlocks.length; i++) {
            HostingSlot[] slots = hostingBlocks[i].getSlots();
            for (int j = 0; j < slots.length; j++) {
                if (slots[j].getId().equals(slotId)) {
                    slot = slots[j];
                    break;
                }
            }
            if (slot != null) {
                break;
            }
        }
        if (slot == null) {
            // if meet this, it is failure condition
            return failedResult;
        }
        if (!Helper.checkSlotForStartedOrFinished(slot, request,
                failRequestAttrName)) {
            return failedResult;
        }
        try {
            gameData.deleteSlot(slotId.longValue());
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to delete slot via slotId:" + slotId,
                    failRequestAttrName, e);
            return failedResult;
        }

        // return null for successful execution
        return null;
    }

}
