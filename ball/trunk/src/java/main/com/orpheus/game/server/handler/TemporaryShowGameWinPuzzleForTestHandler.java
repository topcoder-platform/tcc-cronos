/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.server.GamePlayInfo;
import com.orpheus.game.server.OrpheusFunctions;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>A custom {@link Handler} implementation to be used in conjunction with <code>PuzzleRenderingHandler</code> for
 * rendering a puzzle to be presented to player for solving. This handler prepares the data necessary for <code>
 * PuzzleRenderingHandler</code> to locate and render the desired puzzle as currently there is no such handler
 * provided by any of existing components.</p>
 *
 * @author isv
 * @version 1.0
 */
public class TemporaryShowGameWinPuzzleForTestHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>Constructs new <code>TemporaryShowGameWinPuzzleForTestHandler</code> instance initialized based on the
     * configuration parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;puzzle_id_request_attribute_key&gt;puzzleId&lt;/puzzle_id_request_attribute_key&gt;
     *      &lt;media_type_request_attribute_key&gt;mediaType&lt;/media_type_request_attribute_key&gt;
     *      &lt;media_type&gt;DHTML&lt;/media_type&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public TemporaryShowGameWinPuzzleForTestHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, PUZZLE_ID_ATTR_NAME_CONFIG, true);
        readAsString(element, MEDIA_TYPE_ATTR_NAME_CONFIG, true);
        readAsString(element, MEDIA_TYPE_VALUE_CONFIG, true);
        readAsString(element, PUZZLE_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_PLAY_ATTR_NAME_CONFIG, true);
        readAsString(element, PUZZLE_NAME, true);
        readAsString(element, URL_PATTERN_SUFFIX, true);
    }

    /**
     * <p>Processes the incoming request. If a current session is associated with authenticated player then the hosting
     * slot is located based on the current game and domain (identified by request paramaters) and the brainteaser ID
     * and a media type are put to request to be used later by <code>PuzzleRenderingHandler</code>.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if an unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }

        try {
            // Ensure that session exists
            HttpServletRequest request = context.getRequest();
            request.getSession(true);
            
            // Get puzzle ID from request parameter
            // Put the ID of a puzzle and media type to request to be used by subsequent PuzzleRenderingHandler
            long puzzleId = getLong(PUZZLE_ID_PARAM_NAME_CONFIG, request);
            request.setAttribute(getString(PUZZLE_ID_ATTR_NAME_CONFIG), new Long(puzzleId));
            request.setAttribute(getString(MEDIA_TYPE_ATTR_NAME_CONFIG), getString(MEDIA_TYPE_VALUE_CONFIG));

            long[] puzzleIDs = {11450,11451,11452,11453};  
            // 11450-53 (ST), 46-49 (J) - populate from PracticePuzzleSupport.java
            // Once this is created, add DB field in prod. Ensure that selection
            // between 4 puzzles is correct, then add all new images and DB entries,
            // update links in FAQ.
            request.setAttribute("puzzleIDs", puzzleIDs);
            request.setAttribute("selPuzzleID", puzzleId);
            request.setAttribute("urlPatternSuffix", getString(URL_PATTERN_SUFFIX));
            
            // Get the current game play statistics for a player
            // Record the time when the player had started to solve the puzzle and put the time left to solve the
            // puzzle to request
            GamePlayInfo gamePlayInfo = getGamePlayInfo(context);
            gamePlayInfo.recordPracticePuzzleStart(puzzleId, new Date());
            request.setAttribute("timeLeft", new Integer(OrpheusFunctions.getSolvePuzzlePeriod()));

            // Put name of the puzzle to session so they can be retrieved later
            request.setAttribute("puzzleName", getString(PUZZLE_NAME));
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not prepare the brainteaser data for rendering", e);
        }
    }
}
