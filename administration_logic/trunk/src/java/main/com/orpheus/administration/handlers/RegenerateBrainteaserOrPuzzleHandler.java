/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Element;

import com.orpheus.administration.AdministrationException;
import com.orpheus.administration.AdministrationManager;
import com.orpheus.administration.Helper;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Provides an abstract base handler implementation that (re)generates brain
 * teasers or puzzle for a given slot id. The slot id is accepted as request
 * parameter of configurable name. If the slot has already started hosting (and
 * perhaps even finished), then the handler returns configurable result string
 * as failure result. Otherwise it (re)generates the slot's brainteasers/puzzle
 * using AdministrationManager instance retrieved from application context
 * attribute.<br/> Whether to regenerate brain teaser or puzzle is determined
 * by the return value of generatePuzzle() abstract method. If return value is
 * true, then the handler will generate puzzle, else it will generate brain
 * teasers.<br/> In case of failure, such as an error when making remote calls,
 * the execute method will return a configurable result name and set a
 * HandlerResult instance as a request attribute so that an appropriate response
 * or user message can be generated. In case of success, the execute() method
 * will return null.<br/> For configuration details on this handler, please see
 * Section 3.2.11 of Comp Spec.<br/> Thread-Safety: This class is immutable and
 * hence thread-safe as is required of Handler implementations.
 * 
 * @author bose_java, KKD
 * @version 1.0
 */
abstract class RegenerateBrainteaserOrPuzzleHandler implements Handler {

    /**
     * This holds the JNDI name to use to look up the GameDataHome service.<br/>
     * This variable is initialized in the constructor and does not change after
     * that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String gameDataJndiName;

    /**
     * This holds the name of the request parameter which will contain the slot
     * id for which to regenerate brainteasers. The value should be able to be
     * parsed into a long value.<br/> It is initialized in the constructor and
     * does not change after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String slotIdRequestParamName;

    /**
     * This holds the name of the application context attribute which will hold
     * the AdministrationManager instance to use to regenerate brainteasers.<br/>
     * It is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     * 
     */
    private final String adminMgrAttrName;

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
     * Creates a RegenerateBrainteaserOrPuzzleHandler instance configured from
     * the given xml element. It will initialize the instance variables. It will
     * throw an IllegalArgumentException if configuration details are missing in
     * the handlerElement argument.
     * 
     * Example xml structure:
     * 
     * <pre>
     *      &lt;handler type=&quot;regenerateBrainteaser&quot;&gt;
     * 
     *      &lt;!--  JNDI name to use to lookup the GameDataHome interface --&gt;
     *      &lt;game-data-jndi-name&gt;GameDataHome&lt;/ game-data-jndi-name&gt;
     * 
     *      &lt;!--  name of request parameter which will contain the slot  id --&gt;
     *      &lt;slot-id-request-param&gt;slotId&lt;/slot-id-request-param&gt;
     * 
     *      &lt;!--  name of application context attribute which will have AdministrationManager instance--&gt;
     *      &lt;admin-mgr-app-attr&gt;adminMgr&lt;/ admin-mgr-app-attr &gt;
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
    RegenerateBrainteaserOrPuzzleHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);
        slotIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/slot-id-request-param");
        gameDataJndiName = Helper.getValue(handlerElement,
                "/handler/game-data-jndi-name");
        adminMgrAttrName = Helper.getValue(handlerElement,
                "/handler/admin-mgr-app-attr");
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * <p>
     * This method (re)generates brain teasers for a given slot id. The slot id
     * is accepted as request parameter of configurable name. If the slot has
     * already started hosting (and perhaps even finished), then the handler
     * returns configurable result string as failure result. Otherwise it
     * (re)generates the slot's brainteasers using AdministrationManager
     * instance retrieved from application context attribute.
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
        if (gameData == null) {
            return failedResult;
        }
        // Get slot id from request parameter
        String slotIdString = Helper.getRequestParameter(request,
                slotIdRequestParamName, failRequestAttrName);
        if (slotIdString == null) {
            return failedResult;
        }
        // Parse the slotId into a long value
        Long slotId = Helper.parseLong(request, slotIdString, "slotId",
                failRequestAttrName);
        if (slotId == null) {
            return failedResult;
        }
        // Get the hosting slot
        HostingSlot slot;
        try {
            slot = gameData.getSlot(slotId.longValue());
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to set slot via slotId:" + slotId,
                    failRequestAttrName, e);
            return failedResult;
        }
        if (!Helper.checkSlotForStartedOrFinished(slot, request,
                failRequestAttrName)) {
            return failedResult;
        }

        HttpSession session = Helper.getSession(request);
        // Get the AdministrationManager instance from application context
        AdministrationManager adminMgr = (AdministrationManager) session
                .getServletContext().getAttribute(adminMgrAttrName);
        if (generatePuzzle()) {
            // generate puzzle
            try {
                adminMgr.regeneratePuzzle(slotId.longValue());
            } catch (AdministrationException e) {
                Helper.processFailureExceptionOccur(request,
                        "failed to regenerate puzzle", failRequestAttrName, e);
                return failedResult;
            }
        } else {
            // Regenerate brain teasers
            try {
                adminMgr.regenerateBrainTeaser(slotId.longValue());
            } catch (AdministrationException e) {
                Helper.processFailureExceptionOccur(request,
                        "failed to regenerate brain teaser",
                        failRequestAttrName, e);
                return failedResult;
            }
        }
        // return null for successful execution.
        return null;
    }

    /**
     * This method must be implemented by the sub class to denote whether to
     * generate a puzzle or brainteaser. Must return true for puzzle, false for
     * brainteaser.
     * 
     * 
     * @return true for puzzle, false for brainteaser.
     */
    abstract boolean generatePuzzle();
}
