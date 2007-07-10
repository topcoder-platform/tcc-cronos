/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.server.GamePlayInfo;
import com.orpheus.game.server.OrpheusFunctions;
import com.topcoder.util.puzzle.SolutionTester;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

/**
 * <p>A custom implementation of {@link Handler} interface which is intended </p>
 * 
 * @author isv
 * @version 1.0
 */
public class TemporaryTestPuzzleSolutionHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>Constructs new <code>PlayerPuzzleSolutionPreHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_param_key&gt;gameId&lt;/game_param_key&gt;
     *      &lt;domain_param_key&gt;domain&lt;/domain_param_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public TemporaryTestPuzzleSolutionHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, PUZZLE_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_PLAY_ATTR_NAME_CONFIG, true);
        readAsString(element, SOLUTION_TESTER_BASE_NAME_VALUE_CONFIG, true);
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
            // Ensure that session exists
            HttpServletRequest request = context.getRequest();
            request.getSession(true);
            
            // Record the current time
            final Date currentTime = new Date();

            // Get game ID, domain and sequence number from request parameters
            long puzzleId = getLong(PUZZLE_ID_PARAM_NAME_CONFIG, request);
            
            GameData gameData = null;
            GameDataLocal gameDataLocal = null;
            GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();
            if (golu.isUseLocalInterface()) {
                gameDataLocal = golu.getGameDataLocalHome().create();
            } else {
                gameData = golu.getGameDataRemoteHome().create();
            }
            String puzzleName = golu.isUseLocalInterface()
                                ? gameDataLocal.getPuzzle(puzzleId).getName() : gameData.getPuzzle(puzzleId).getName();
            if (puzzleName.equals("sliding-tile")) {
                puzzleName = "Sliding Tile Puzzle";
            } else if (puzzleName.equals("jigsaw")) {
                puzzleName = "Jigsaw Puzzle";
            }
            request.setAttribute("puzzleName", puzzleName);
            long[] puzzleIDs = {11450,11451,11452,11453};  // 11450-53 (ST), 46-49 (J) - populate from PracticePuzzleSupport.java
                                                           // Once this is created, add DB field in prod. Ensure that selection
                                                           // between 4 puzzles is correct, then add all new images and DB entries,
                                                           // update links in FAQ.
            request.setAttribute("puzzleIDs", puzzleIDs);
            request.setAttribute("selPuzzleID", String.valueOf(puzzleId));
                    
            SolutionTester tester = (SolutionTester) request.getSession().getAttribute(
                    getString(SOLUTION_TESTER_BASE_NAME_VALUE_CONFIG) + puzzleId);
            if (tester == null) {
                throw new HandlerExecutionException("no SolutionTester is found for puzzle:" + puzzleId);
            }
            boolean isCorrectSolution = tester.testSolution(request.getParameterMap());
            String msg = isCorrectSolution ? "<b><font color=\"green\">Congratulations</font></b>, your solution is correct!" : 
                "<b><font color=\"red\">Sorry</font></b>, your solution is incorrect.";
            
            // Get player's game play info collected so far and record the fact on resolution of hunt target
            GamePlayInfo gamePlayInfo = getGamePlayInfo(context);
            Date puzzleStartTime = gamePlayInfo.getPracticePuzzleStartTime(puzzleId);
            if ((puzzleStartTime == null)
                || (currentTime.getTime() - puzzleStartTime.getTime()
                    > OrpheusFunctions.getSolveWinGamePuzzlePeriod() * 1000L)) {
                long diff = 0;
                if (puzzleStartTime != null) {
                    diff = (currentTime.getTime() - puzzleStartTime.getTime())
                           - OrpheusFunctions.getSolveWinGamePuzzlePeriod() * 1000L;
                }
                
                if (isCorrectSolution) {
                    msg += " However, because your solution is " + diff/1000 + "." + diff%1000 + 
                        "s late, it would not qualify for the game prize, and the Ball would move on. You would still " +
                        " receive the key for this page.";
                } else {
                    msg += " In addition, your solution is " + diff/1000 + "." + diff%1000 + "s late.";
                }
            } else {
                if (isCorrectSolution && puzzleStartTime != null) {
                    long solvedTimeMillis = currentTime.getTime() - puzzleStartTime.getTime();
                    msg += " You solved the puzzle in " + solvedTimeMillis/1000 + "." + solvedTimeMillis%1000 + " seconds.";
                }
            }
            request.setAttribute("message", msg);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not pre handle Test Puzzle Solution request due to unexpected "
                                                + "error", e);
        }
    }
}
