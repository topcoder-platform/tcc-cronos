/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.rmi.RemoteException;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.SlotCompletion;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.puzzle.SolutionTester;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;


/**
 * The component will provide a Handler that tests whether the HTTP request parameters describe a solution to a
 * previously presented puzzle. It determines the IDs of the game and slot to which the puzzle applies from request
 * parameters of configurable name. This class is thread safe since it does not contain any mutable state.
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class PuzzleSolutionHandler implements Handler {
    /** A configurable param name to retrieve the game id from the request parameters. */
    private final String gameIdParamKey;

    /** The result name that will be returned by this handler if the solution is incorrect. */
    private final String incorrectSolutionResult;

    /** The http request attribute name to save the slot completion. */
    private final String slotCompletion;

    /**
     * A configurable param name to retrieve the slot id from the request parameters It's set in the constructor,
     * non-null and non-empty after set.
     */
    private final String slotIdParamKey;

    /**
     * The base name of the key to get the SolutionTester from the http session. The complete name will be baseName +
     * puzzleId.
     */
    private final String solutionTesterBaseName;

    public final static String NEXT_DOMAIN_ATTRIBUTE = "nextDomain";

    /**
     * Create the instance from given arguments.
     *
     * @param gameIdParamKey the game id param key
     * @param slotIdParamKey the slot id param key
     * @param solutionTesterBaseName the solution tester base name
     * @param incorrectSolutionResult the incorrect solution result name
     * @param slotCompletion the http request attribute name to save the slot completion
     *
     * @throws IllegalArgumentException if any argument is null or empty string.
     */
    public PuzzleSolutionHandler(String gameIdParamKey, String slotIdParamKey, String solutionTesterBaseName,
        String incorrectSolutionResult, String slotCompletion) {
        ParameterCheck.checkNullEmpty("gameIdParamKey", gameIdParamKey);
        ParameterCheck.checkNullEmpty("slotIdParamKey", slotIdParamKey);
        ParameterCheck.checkNullEmpty("solutionTesterBaseName", solutionTesterBaseName);
        ParameterCheck.checkNullEmpty("incorrectSolutionResult", incorrectSolutionResult);
        ParameterCheck.checkNullEmpty("slotCompletion", slotCompletion);

        this.gameIdParamKey = gameIdParamKey;
        this.slotIdParamKey = slotIdParamKey;
        this.solutionTesterBaseName = solutionTesterBaseName;
        this.incorrectSolutionResult = incorrectSolutionResult;
        this.slotCompletion = slotCompletion;
    }

    /**
     * Create the instance from given xml element.
     * <pre>Follow is a sample xml:
     *  &lt;handler type=&quot;x&quot;&gt;
     *  &lt;puzzle_id_request_attribute_key&gt;
     *  puzzle_id
     *  &lt;/puzzle_id_request_attribute_key&gt;
     *  &lt;media_type_request_attribute_key&gt;
     *  media_type
     *  &lt;/media_type_request_attribute_key&gt;
     *  &lt;puzzle_string_request_attribute_key&gt;
     *  puzzle_string
     *  &lt;/puzzle_string_request_attribute_key&gt;
     *  &lt;solutiontester_base_name&gt;
     *  base_name
     *  &lt;/solutiontester_base_name&gt;
     *  &lt;incorrect_solution_result&gt;
     *  incorrect_solution_result
     *  &lt;/incorrect_solution_result&gt;
     *  &lt;/handler&gt;
     *  Following is simple explanation of the above XML structure.
     *  The handler's type attribute is required by Front Controller component, it won't be used in this design.
     *  The puzzle_id_param_key node's value represents the http request parameter name to get the puzzle id
     *  The slot_id_param_key node represents the http request parameter name to get the slot id
     *  The solutiontester_base_name node represents the SolutionTester base name.
     *  The slot_completion_request_attribute_key node's value represents the http request attribute name to save
     *  the slot completion.
     *  </pre>
     *
     * @param element the xml element to create the instance
     *
     * @throws IllegalArgumentException if element is null or invalid.
     */
    public PuzzleSolutionHandler(Element element) {
        ParameterCheck.checkNull("element", element);

        this.gameIdParamKey = XMLHelper.getNodeValue(element, "game_id_param_key", true);
        this.slotIdParamKey = XMLHelper.getNodeValue(element, "slot_id_param_key", true);
        this.solutionTesterBaseName = XMLHelper.getNodeValue(element, "solutiontester_base_name", true);
        this.incorrectSolutionResult = XMLHelper.getNodeValue(element, "incorrect_solution_result", true);
        this.slotCompletion = XMLHelper.getNodeValue(element, "slot_completion_request_attribute_key", true);
    }

    /**
     * Tests whether the HTTP request parameters describe a solution to a previously presented puzzle.
     *
     * @param context the action context
     *
     * @return null if execution successfully otherwise a configurable forward name
     *
     * @throws HandlerExecutionException if any other error occurred.
     * @throws IllegalArgumentException if the context is null
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ParameterCheck.checkNull("context", context);

        HttpServletRequest request = context.getRequest();
        UserProfile user = LoginHandler.getAuthenticatedUser(request.getSession());

        if (user == null) {
            throw new HandlerExecutionException("user does not login yet");
        }

        long userId = ((Long) user.getIdentifier()).longValue();
        long gameId = RequestHelper.getLongParameter(request, gameIdParamKey);
        long slotId = RequestHelper.getLongParameter(request, this.slotIdParamKey);

        // obtains GameData
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();

        try {
            if (golu.isUseLocalInterface()) {
                    gameDataLocal = golu.getGameDataLocalHome().create();
            } else {
                gameData = golu.getGameDataRemoteHome().create();
            }

            Long puzzledId = golu.isUseLocalInterface()?gameDataLocal.getSlot(slotId).getPuzzleId():gameData.getSlot(slotId).getPuzzleId();
            SolutionTester tester = (SolutionTester) request.getSession().getAttribute(this.solutionTesterBaseName
                    + puzzledId);
            System.out.println(this.solutionTesterBaseName
                    + puzzledId);

            if (tester == null) {
                throw new HandlerExecutionException("no SolutionTester is found for puzzle:" + puzzledId);
            }

            if (tester.testSolution(request.getParameterMap())) {
                    if (golu.isUseLocalInterface()){
                            gameDataLocal.recordGameCompletion(userId, gameId);
                    }else{
                            gameData.recordGameCompletion(userId, gameId);
                    }

                return null;
            } else {
                GameDataManager gdMgr = (GameDataManager) request.getSession().getServletContext().getAttribute(
                        golu.getGameManagerKey());
                Game game;

                gdMgr.advanceHostingSlot(gameId);

                SlotCompletion completion;
                if (golu.isUseLocalInterface()){
                        completion = gameDataLocal.recordSlotCompletion(userId, slotId, new Date());
                        game = gameDataLocal.getGame(gameId);
                }else{
                        completion = gameData.recordSlotCompletion(userId, slotId, new Date());
                        game = gameData.getGame(gameId);
                }

                // find the next domain
		
		/*
		 * This sequence may be over-engineered, but it accounts for the possibility that hosting slots
		 * will be skipped because of domain inaccessibility, administrative action, or other, unknown
		 * cause, with the skipped slot(s) nevertheless returned by GameData.getGame().
		 */
                HostingBlock[] blocks = game.getBlocks();

                find_block:
                for (int blockIndex = 0; blockIndex < blocks.length; blockIndex++) {
                    HostingBlock block = blocks[blockIndex];
                    HostingSlot[] slots = block.getSlots();

                    find_slot:
                    for (int slotIndex = 0; slotIndex < slots.length; slotIndex++) {
                        HostingSlot slot = slots[slotIndex];

                        if (slot.getId().longValue() == slotId) {
                            // found it
                            int nextSlotIndex = slotIndex + 1;

                            find_next_block:
                            for (int nextBlockIndex = blockIndex; nextBlockIndex < blocks.length; nextBlockIndex++) {
                                HostingSlot[] candidateSlots = blocks[nextBlockIndex].getSlots();

                                /*
                                 * initially we start at the slot after the one just completed; after that
                                 * we start at the first slot in each subsequent block that we have to examine
                                 */
                                find_next_slot:
                                for (; nextSlotIndex < candidateSlots.length; nextSlotIndex++) {
                                    HostingSlot candidateSlot = candidateSlots[nextSlotIndex];

                                    if (candidateSlot.getHostingStart() != null) {
					// this is the one
                                        request.setAttribute(NEXT_DOMAIN_ATTRIBUTE, candidateSlot.getDomain());
                                        break find_block;
                                    }
                                }
                                nextSlotIndex = 0;
                            }
                            throw new HandlerExecutionException("No more slots available");
                        }
                    }
                }
                
                request.setAttribute(this.slotCompletion, completion);

                return this.incorrectSolutionResult;
            }
        } catch (ConfigManagerException e) {
            throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        } catch (NamingException e) {
            throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        } catch (GameDataException e) {
            throw new HandlerExecutionException("failed to obtain data from GameData", e);
        } catch (RemoteException e) {
            throw new HandlerExecutionException("failed to obtain data from GameData", e);
        }catch (Exception e) {
            throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        }
    }
}
