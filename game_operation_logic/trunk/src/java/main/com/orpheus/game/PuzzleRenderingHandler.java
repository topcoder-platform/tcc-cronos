/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;

import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleRenderer;
import com.topcoder.util.puzzle.PuzzleType;
import com.topcoder.util.puzzle.PuzzleTypeSource;
import com.topcoder.util.puzzle.SolutionTester;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import org.w3c.dom.Element;

import java.io.ByteArrayOutputStream;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;


/**
 * A Handler that produces a rendition of a puzzle in String form and attaches it to the request as an attribute of
 * configurable name. The handler will be configured also with the name of a request attribute from which to obtain
 * the ID of the puzzle to render (as a Long), the media type in which to render it (a String), the name of the
 * application context attribute from which to obtain a PuzzleTypeSource, and the base name of a session attribute to
 * which to assign a SolutionTester.
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class PuzzleRenderingHandler implements Handler {
    /** A request attribute key to get the media type in the request attribute. */
    private final String mediaTypeRequestAttributeKey;

    /** A request attribute key to get the puzzle id in the request attribute. */
    private final String puzzleIdRequestAttributeKey;

    /** A request attribute key to keep the rendered puzzle string in the request attribute. */
    private final String puzzleStringRequestAttributeKey;

    /** The base name of the key to get the SolutionTester from the http session. */
    private final String solutionTesterBaseName;

    /**
     * Create the instance with given arguments.
     *
     * @param puzzleIdRequestAttributeKey the puzzle id key
     * @param mediaTypeRequestAttributeKey the media type key
     * @param solutionTesterBaseName the solution tester base name key
     * @param puzzleStringRequestAttributeKey the puzzleStrign key
     *
     * @throws IllegalArgumentException if any argument is null or empty string
     */
    public PuzzleRenderingHandler(String puzzleIdRequestAttributeKey, String mediaTypeRequestAttributeKey,
        String solutionTesterBaseName, String puzzleStringRequestAttributeKey) {
        ParameterCheck.checkNullEmpty("puzzleIdRequestAttributeKey", puzzleIdRequestAttributeKey);
        ParameterCheck.checkNullEmpty("mediaTypeRequestAttributeKey", mediaTypeRequestAttributeKey);
        ParameterCheck.checkNullEmpty("solutionTesterBaseName", solutionTesterBaseName);
        ParameterCheck.checkNullEmpty("puzzleStringRequestAttributeKey", puzzleStringRequestAttributeKey);

        this.puzzleIdRequestAttributeKey = puzzleIdRequestAttributeKey;
        this.mediaTypeRequestAttributeKey = mediaTypeRequestAttributeKey;
        this.solutionTesterBaseName = solutionTesterBaseName;
        this.puzzleStringRequestAttributeKey = puzzleStringRequestAttributeKey;
    }

    /**
     * Create the instance from given xml element. The structure of the element can be found in CS.
     *
     * @param element the xml element to create the handler instance.
     *
     * @throws IllegalArgumentException if element is null or invalid.
     */
    public PuzzleRenderingHandler(Element element) {
        ParameterCheck.checkNull("element", element);

        this.puzzleIdRequestAttributeKey = XMLHelper.getNodeValue(element, "puzzle_id_request_attribute_key", true);
        this.mediaTypeRequestAttributeKey = XMLHelper.getNodeValue(element, "media_type_request_attribute_key", true);
        this.puzzleStringRequestAttributeKey = XMLHelper.getNodeValue(element, "puzzle_string_request_attribute_key",
                true);
        this.solutionTesterBaseName = XMLHelper.getNodeValue(element, "solutiontester_base_name", true);
    }

    /**
     * produces a rendition of a puzzle in String form and attaches it to the request as an attribute of configurable
     * name.
     *
     * @param context the action context
     *
     * @return null always
     *
     * @throws HandlerExecutionException if any other error occurred
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ParameterCheck.checkNull("context", context);

        HttpServletRequest request = context.getRequest();

        Long puzzleId = (Long) request.getAttribute(this.puzzleIdRequestAttributeKey);

        if (puzzleId == null) {
            throw new HandlerExecutionException(puzzleIdRequestAttributeKey + " does not exist in request attributes");
        }

        String mediaType = (String) request.getAttribute(this.mediaTypeRequestAttributeKey);

        if (mediaType == null) {
            throw new HandlerExecutionException(mediaTypeRequestAttributeKey + " does not exist in request attributes");
        }

        try {
            // obtains GameData
            GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();

            PuzzleData puzzleData;
            if (golu.isUseLocalInterface()) {
            	GameDataLocal gameData = golu.getGameDataLocalHome().create();
                puzzleData = gameData.getPuzzle(puzzleId.longValue());
            } else {
            	GameData gameData = golu.getGameDataRemoteHome().create();
                puzzleData = gameData.getPuzzle(puzzleId.longValue());
            }

            String typeName = puzzleData.getAttribute(PuzzleData.PUZZLE_TYPE_ATTRIBUTE);
            PuzzleTypeSource puzzleTypeSource = (PuzzleTypeSource) request.getSession().getServletContext()
                                                                          .getAttribute(golu.getPuzzleTypeSourceKey());
            PuzzleType puzzleType = puzzleTypeSource.getPuzzleType(typeName);
            PuzzleRenderer puzzleRenderer = puzzleType.createRenderer(mediaType);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            SolutionTester solutionTester = puzzleRenderer.renderPuzzle(puzzleData, baos);

            request.getSession().setAttribute(this.solutionTesterBaseName + puzzleId, solutionTester);
            request.setAttribute(this.puzzleStringRequestAttributeKey, baos.toString());

            return null;
        } catch (ConfigManagerException e) {
            throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        } catch (NamingException e) {
            throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        } catch (GameDataException e) {
            throw new HandlerExecutionException("failed to obtain data from GameData", e);
        } catch (RemoteException e) {
            throw new HandlerExecutionException("failed to obtain data from GameData", e);
        } catch (Exception e) {
            throw new HandlerExecutionException("failed to obtain GameData from EJB", e);
        }
    }
}
