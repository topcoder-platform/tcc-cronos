/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import org.w3c.dom.Element;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;


/**
 * <p>
 * <code>SlotValidationHandler</code> class implements the <code>Handler</code> interface from the
 * <code>FrontController</code> component. It verifies that a specified game is currently active, and that in it the
 * specified slot is the current host. The game and slot IDs to validate are taken from request parameters of
 * configurable name, and a configurable result string will be returned if this validation fails.
 *
 * To follow the <code>Handler</code> implementation's contract in order to make it work properly with
 * <code>FrontController</code> component, two constructors are defined, one takes the configurable parameters, and
 * the other takes an <code>Element</code> object which contains all configurable parameters.
 *
 * Thread-safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author Standlove, mittu
 * @version 1.0
 */
public class SlotValidationHandler implements Handler {

    /**
     * <p>
     * Represents the key used to get the game id from the request parameter in execute method. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String gameIdParamKey;

    /**
     * <p>
     * Represents the key used to get the slot id from the request parameter in execute method. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String slotIdParamKey;

    /**
     * <p>
     * Represents the result code to return in execute method if the slot validation fails. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     *
     */
    private final String validationFailedResultCode;

    /**
     * <p>
     * Constructor with configurable arguments. Simply assign the arguments to corresponding variables.
     * </p>
     *
     * @param gameIdParamKey
     *            the key used to get the game id from the request parameter in execute method.
     * @param slotIdParamKey
     *            the key used to get the slot id from the request parameter in execute method.
     * @param validationFailedResultCode
     *            the result code to return in execute method if the slot validation fails.
     * @throws IllegalArgumentException
     *             if any argument is null or empty string.
     */
    public SlotValidationHandler(String gameIdParamKey, String slotIdParamKey, String validationFailedResultCode) {
        ImplementationHelper.checkStringValid(gameIdParamKey, "gameIdParamKey");
        ImplementationHelper.checkStringValid(slotIdParamKey, "slotIdParamKey");
        ImplementationHelper.checkStringValid(validationFailedResultCode, "validationFailedResultCode");
        this.gameIdParamKey = gameIdParamKey;
        this.slotIdParamKey = slotIdParamKey;
        this.validationFailedResultCode = validationFailedResultCode;
    }

    /**
     * <p>
     * Constructor with an xml Element object. The constructor will extract necessary configurable values from this xml
     * element.
     *
     * Here is what the xml element likes:
     *
     * <pre>
     *  &lt;handler type=&quot;x&quot;&gt;
     *   &lt;game_id_param_key&gt;gameId&lt;/game_id_param_key&gt;
     *   &lt;slot_id_param_key&gt;slotId&lt;/slot_id_param_key&gt;
     *   &lt;validation_failed_result_code&gt;validation_failed&lt;/validation_failed_result_code&gt;
     *  &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     * @param element
     *            the xml Element to extract the configurable values.
     * @throws IllegalArgumentException
     *             if the argument is null or any value mentioned in implementation note is missing or empty string.
     */
    public SlotValidationHandler(Element element) {
        ImplementationHelper.checkObjectValid(element, "element");
        this.gameIdParamKey = ImplementationHelper.getElement(element, "game_id_param_key");
        this.slotIdParamKey = ImplementationHelper.getElement(element, "slot_id_param_key");
        this.validationFailedResultCode = ImplementationHelper.getElement(element, "validation_failed_result_code");
    }

    /**
     * <p>
     * It verifies that a specified game is currently active, and that in it the specified slot is the current host. The
     * game and slot IDs to validate are taken from request parameters of configurable name, and a configurable result
     * string will be returned if this validation fails.
     *
     * This method will return a proper result code if the validation fails, it will return null otherwise.
     * </p>
     *
     * @param context
     *            the ActionContext object.
     * @return a proper result code if validation fails, or null if the validation succeeds.
     * @throws IllegalArgumentException
     *             if the given argument is null.
     * @throws HandlerExecutionException
     *             if the values loaded from request parameter are missing or invalid, or the Game Data persistence
     *             operation fails.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ImplementationHelper.checkObjectValid(context, "context");
        GameDataHelper helper = GameDataHelper.getInstance();
        try {
            // parses the game id and slot id from the request parameters.
            long gameId = Long.parseLong(context.getRequest().getParameter(gameIdParamKey));
            long slotId = Long.parseLong(context.getRequest().getParameter(slotIdParamKey));
            // gets the game using the helper.
            Game game = helper.getGame(gameId);
            // do the validation
            if (game.getStartDate() != null && game.getEndDate() == null) {
                // gets the HostingBlocks
                HostingBlock[] blocks = game.getBlocks();
                for (int i = 0; i < blocks.length; i++) {
                    // gets the HostingSlots for each block.
                    HostingSlot[] slots = blocks[i].getSlots();
                    for (int j = 0; j < slots.length; j++) {
                        // if the slot id matches and current slot's start happened and end not happened yet, success.
                        if (slots[j].getId() != null && slots[j].getId().longValue() == slotId
                                && slots[i].getHostingStart() != null && slots[i].getHostingEnd() == null) {
                            return null;
                        }
                    }
                }
            }
        } catch (NumberFormatException formatException) {
            throw new HandlerExecutionException("Failed to execute slot validation handler.", formatException);
        }
        // all other cases are failure.
        return validationFailedResultCode;
    }
}
