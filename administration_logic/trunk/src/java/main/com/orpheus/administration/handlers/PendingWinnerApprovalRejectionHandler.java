/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.administration.Helper;
import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.PendingWinner;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Provides an abstract base handler implementation that approves or rejects a
 * given pending winner. The id of the game and the id of user are accepted as
 * request parameters of configurable name. The execute() method will ensure
 * that that the specified user is currently the first pending winner for the
 * specified game. If verification fails, it is considered as a fail condition.
 * Else the pending winner is marked as approved/rejected and persistence
 * updated.<br/> In case of failure, the execute method will return a
 * configurable result name and set a HandlerResult instance as a request
 * attribute so that an appropriate response or user message can be generated.
 * In case of success, the execute() method will return null.<br/>
 * PendingWinnerApprovalHandler and PendingWinnerRejectionHandler classes
 * provide concrete implementations on whether to approve or reject, in that
 * they implement the getApprovedFlag() method.<br/> For configuration details
 * on this handler, please see Section 3.2.8 of Comp Spec.<br/> Thread-Safety:
 * This class is thread-safe as is required of Handler implementations. This
 * class is immutable and hence thread-safe.
 * 
 * @author bose_java, KKD
 * @version 1.0
 */
abstract class PendingWinnerApprovalRejectionHandler implements Handler {

    /**
     * This holds the JNDI name to use to look up the AdminDataHome service.<br/>
     * AdminDataHome is defined in com.orpheus.administration.persistence
     * package.<br/> This variable is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String adminDataJndiName;

    /**
     * This holds the name of the request parameter which will contain the game
     * id associated with pending winner. The value should be able to be parsed
     * into a long value.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String gameIdRequestParamName;

    /**
     * This holds the name of the request parameter which will contain the user
     * id associated with pending winner. The value should be able to be parsed
     * into a long value.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String userIdRequestParamName;

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
     * Creates a SponsorApprovalHandler instance configured from the given xml
     * element. It will initialize the userProfileManager instance and other
     * instance variables. It will throw an IllegalArgumentException if
     * configuration details are missing in the handlerElement argument.
     * 
     * Example xml structure:
     * 
     * <pre>
     *      &lt;handler type=&quot;pendingWinnerApproval&quot;&gt;
     * 
     *      &lt;!--  JNDI name to use to lookup the AdminDataHome interface --&gt;
     *      &lt;admin-data-jndi-name&gt;AdminDataHome&lt;/admin-data-jndi-name&gt;
     * 
     *      &lt;!--  name of request parameter which will contain the game id --&gt;
     *      &lt;game-id-request-param&gt;gameId&lt;/game-id-request-param&gt;
     * 
     *      &lt;!--  name of request parameter which will contain the user id --&gt;
     *      &lt;user-id-request-param&gt;userId&lt;/user-id-request-param&gt;
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
    PendingWinnerApprovalRejectionHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);
        adminDataJndiName = Helper.getValue(handlerElement,
                "/handler/admin-data-jndi-name");
        gameIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/game-id-request-param");
        userIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/user-id-request-param");
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * <p>
     * This method will use AdminData to search for pending winners. It will
     * verify that the specified user is currently the first pending winner for
     * the specified game. After validation it will use AdminData to mark the
     * winner as approved or rejected. If verification fails, this will return a
     * failed result.
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
        // Create the AdminData EJB instance
        AdminData adminData = Helper.getAdminData(adminDataJndiName, request,
                failRequestAttrName);
        if (adminData == null) {
            return failedResult;
        }
        // Get the pending winners information
        PendingWinner[] pendingWinners;
        try {
            pendingWinners = adminData.getPendingWinners();
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get pending winners", failRequestAttrName, e);
            return failedResult;
        }
        // Get game id from request parameter
        String gameIdString = Helper.getRequestParameter(request,
                gameIdRequestParamName, failRequestAttrName);
        // Get user id from request parameter
        String userIdString = Helper.getRequestParameter(request,
                userIdRequestParamName, failRequestAttrName);
        if (gameIdString == null || userIdString == null) {
            return failedResult;
        }
        // Parse gameId and userId into long values
        Long gameId = Helper.parseLong(request, gameIdString, "gameId",
                failRequestAttrName);
        Long userId = Helper.parseLong(request, userIdString, "userId",
                failRequestAttrName);
        if (gameId == null || userId == null) {
            return failedResult;
        }
        // Verify that the specified user is currently the first pending winner
        // for the specified game
        PendingWinner pendingWinner = null;
        for (int i = 0; i < pendingWinners.length; i++) {
            if (pendingWinners[i].getGameId() == gameId.longValue()) {
                pendingWinner = pendingWinners[i];
                break;
            }
        }
        if (pendingWinner == null) {
            // if meet this, it is failure condition
            Helper.processFailureNoMatchingPendingWinner(request,
                    failRequestAttrName, "No pending winner");
            return failedResult;
        }
        if (pendingWinner.getPlayerId() != userId.longValue()) {
            Helper.processFailureWinnerNotFirst(request, failRequestAttrName,
                    "winner not first");
            return failedResult;
        }

        // now match is fine
        try {
            if (getApprovedFlag()) {
                adminData.approveWinner(pendingWinner, new Date());
            } else {
                adminData.rejectWinner(pendingWinner);
            }
        } catch (Exception e) {
            Helper
                    .processFailureExceptionOccur(request,
                            "Failed to approval/reject winners",
                            failRequestAttrName, e);
            return failedResult;
        }

        // return null for successful execution.
        return null;
    }

    /**
     * This method must be implemented by the sub class to denote whether the
     * pending winner is to be approved or rejected. Must return true for
     * approval, false for rejection.
     * 
     * 
     * @return true for approval, false for rejection.
     */
    abstract boolean getApprovedFlag();
}
