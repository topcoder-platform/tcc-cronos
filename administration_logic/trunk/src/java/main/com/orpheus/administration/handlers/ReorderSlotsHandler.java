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
 * Provides a handler implementation that reorders as-yet unreached slots within
 * the same block by moving one slot to a different position. The handler will
 * receive a game ID, a slot ID, and an offset via request parameters of
 * configurable name, and will move the slot within its block by the specified
 * offset. If the slot has already started hosting (and perhaps even finished),
 * or if the slot is to be moved beyond last position in block, it is treated as
 * fail condition.<br/> In case of failure, the execute method will return a
 * configurable result name and set a HandlerResult instance as a request
 * attribute so that an appropriate response or user message can be generated.
 * In case of success, the execute() method will return null.<br/> For
 * configuration details on this handler, please see Section 3.2.12 of Comp
 * Spec.<br/> Thread-Safety: This class is thread-safe as is required of
 * Handler implementations. To achieve this, it synchronizes over the
 * userProfileManager instance var in the execute() method.
 * 
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
public class ReorderSlotsHandler implements Handler {

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
     * id to re-order. The value should be able to be parsed into a long value.<br/>
     * It is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     * 
     */
    private final String slotIdRequestParamName;

    /**
     * This holds the name of the request parameter which will contain the
     * offset for the slot. The value should be able to be parsed into an int
     * value.<br/> It is initialized in the constructor and does not change
     * after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String offsetRequestParamName;

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
     *      &lt;handler type=&quot;reorderSlots&quot;&gt;
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
     *      &lt;!--  name of request parameter which will contain the new slot offset --&gt;
     *      &lt;offset-request-param&gt;offset&lt;/offset-request-param&gt;
     * 
     *      &lt;!--  name of result to return in case of execution failure --&gt;
     *      &lt;fail-result&gt;Failed&lt;/fail-result&gt;
     * 
     *      &lt;!--  name of request attribute to store HandlerResult in case of failure--&gt;
     *      &lt;fail-request-attribute&gt;Failed&lt;/fail-request-attribute&gt;
     *      &lt;/handler&gt;
     * </pre>
     * 
     * @param handlerElement
     *            the XML element containing configuration for this handler.
     * @throws IllegalArgumentException
     *             if handlerElement is null, or contains invalid data.
     */
    public ReorderSlotsHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);
        gameDataJndiName = Helper.getValue(handlerElement,
                "/handler/game-data-jndi-name");
        gameIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/game-id-request-param");
        slotIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/slot-id-request-param");
        offsetRequestParamName = Helper.getValue(handlerElement,
                "/handler/offset-request-param");
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * <p>
     * This method reorders as-yet unreached slots within the same block by
     * moving one slot to a different position. If the slot has already started
     * hosting (and perhaps even finished), or if the slot is to be moved beyond
     * last position in block, it is treated as fail condition. Otherwise it
     * updates the sequence numbers of all slots in its block.
     * </p>
     * <p>
     * This method will return null in case of success and will return a
     * configurable result name in case of failure such as any exceptions.
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
        // Create the GameData EJB instance
        GameData gameData = Helper.getGameData(gameDataJndiName, request,
                failRequestAttrName);
        // Parse the gameId and slotId into long values
        Long gameId = Helper.parseLong(request, Helper.getRequestParameter(
                request, gameIdRequestParamName, failRequestAttrName),
                "gameId", failRequestAttrName);
        Long slotId = Helper.parseLong(request, Helper.getRequestParameter(
                request, slotIdRequestParamName, failRequestAttrName),
                "slotId", failRequestAttrName);
        if (gameId == null || slotId == null) {
            return failedResult;
        }
        // Get game instance
        Game game;
        try {
            game = gameData.getGame(gameId.longValue());
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to set game via domianId:" + gameId,
                    failRequestAttrName, e);
            return failedResult;
        }
        
        // Search for the HostingBlock [block] which contains the given slot id
        HostingBlock[] hostingBlocks = game.getBlocks();
        HostingSlot slot = null;
        HostingSlot[] slots = null;
        HostingBlock block = null;
        int currentSlotIndex = 0;
        for (int i = 0; i < hostingBlocks.length; i++) {
            slots = hostingBlocks[i].getSlots();
            for (int j = 0; j < slots.length; j++) {
                if (slots[j].getId().equals(slotId)) {
                    currentSlotIndex = j;
                    slot = slots[j];
                    block = hostingBlocks[i];
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
        // Get new slot offset from request parameter
        String offsetString = Helper.getRequestParameter(request,
                offsetRequestParamName, failRequestAttrName);
        if (offsetString == null) {
            return failedResult;
        }
        // Parse offset into an int value
        Integer offset = Helper.parseInteger(request, offsetString, "offset",
                failRequestAttrName);
        if (offset == null) {
            return failedResult;
        }

        return reorder(gameData, offset, currentSlotIndex, block, slots,
                request);
    }

    /**
     * Reorders the slots using given 'offset'.
     * 
     * <strong>Note:</strong> The old Sequence Number is assumed to be
     * ascending, positive and consecutive. But not 1 based.
     * 
     * <pre>
     *  For offset = 2, currentSlotId = 2. This method will change
     *  slotId:               1,  2,  3,  4,  5
     *  old Sequence Number:  1,  2,  3,  4,  5
     *  &lt;strong&gt;to&lt;/strong&gt;
     *  slotId:               1,  2,  3,  4,  5
     *  new Sequence Number:  1,  2,  5,  3,  4
     * </pre>
     * 
     * 
     * @param gameData
     *            the GameData instance
     * @param offset
     *            the offset
     * @param currentSlotIndex
     *            current slot array index
     * @param block
     *            the Blaock
     * @param slots
     *            the slots needed be reordered
     * @param request
     *            the Http Request
     * @return null if successfully reordered, or return failedResult
     */
    private String reorder(GameData gameData, Integer offset,
            int currentSlotIndex, HostingBlock block, HostingSlot[] slots,
            HttpServletRequest request) {
        if (currentSlotIndex + offset.intValue() >= block.getSlots().length) {
            Helper.processFailureCannotMoveSlotBeyondLast(request,
                    failRequestAttrName, "the slot index is:"
                            + currentSlotIndex + " offset is:" + offset);
            return failedResult;
        }
        // collect the updated HostingSlot instances in an array HostingSlot[]
        HostingSlot[] newHostingSlots = new HostingSlot[slots.length];
        // the slot whose array index before currentSlotIndex
        for (int i = 0; i < currentSlotIndex; i++) {
            newHostingSlots[i] = Helper.copySlot(slots[i], slots[i]
                    .getSequenceNumber());
        }
        // the current slot
        newHostingSlots[currentSlotIndex] = Helper.copySlot(
                slots[currentSlotIndex], slots[currentSlotIndex]
                        .getSequenceNumber()
                        + offset.intValue());
        for (int i = currentSlotIndex + 1; i < slots.length; i++) {
            newHostingSlots[i] = Helper.copySlot(slots[i], slots[i]
                    .getSequenceNumber() - 1);
        }

        // Update the slots
        try {
            gameData.updateSlots(newHostingSlots);
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to update slots", failRequestAttrName, e);
            return failedResult;
        }

        // return null in case of successful execution.
        return null;
    }
}
