/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.administration.Helper;
import com.orpheus.administration.persistence.AdminData;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.GameData;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Provides an abstract base handler implementation that approves or rejects a
 * given domain. The id of the domain and the id of its associated sponsor are
 * accepted as request parameters of configurable name. The execute() method
 * will ensure that the sponsor has neither been approved or rejected and that
 * the sponsor id is associated with the given domain id. If verification fails,
 * it is considered as a fail condition. Else the domain is marked as
 * approved/rejected and persistence updated.<br/> In case of failure, the
 * execute method will return a configurable result name and set a HandlerResult
 * instance as a request attribute so that an appropriate response or user
 * message can be generated. In case of success, the execute() method will
 * return null.<br/> DomainApprovalHandler and DomainRejectionHandler classes
 * provide concrete implementations on whether to approve or reject, in that
 * they provide the value to be set for the isApproved() method of the Domain
 * instance.<br/> For configuration details on this handler, please see Section
 * 3.2.5 of Comp Spec.<br/> Thread-Safety: This class is thread-safe as is
 * required of Handler implementations. To achieve this, it synchronizes over
 * the userProfileManager instance var in the execute() method.
 * 
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
abstract class DomainApprovalRejectionHandler implements Handler {

    /**
     * This holds the UserProfileManager instance which will be used to search
     * for domain sponsor.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null.<br/>
     * 
     */
    private final UserProfileManager userProfileManager;

    /**
     * <p>
     * This holds the name of the namespace to be used when instantiating
     * ConfigManagerSpecificationFactory of the Object Factory component.The
     * Object Factory will be used to instantiate UserProfileManager to a
     * concrete implementation.The Object Factory must contain configuration for
     * &quot;UserProfileManager&quot;.This variable is initialized in the
     * constructor and does not change after that.It will never be null or
     * empty.
     * </p>
     * 
     */
    private final String objFactoryNS;

    /**
     * This holds the JNDI name to use to look up the AdminDataHome service.<br/>
     * AdminDataHome is defined in com.orpheus.administration.persistence
     * package.<br/> This variable is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String adminDataJndiName;

    /**
     * This holds the JNDI name to use to look up the GameDataHome service which
     * will be used to search for domain.<br/> GameDataHome is defined in
     * com.orpheus.game.persistence package.<br/> This variable is initialized
     * in the constructor and does not change after that.<br/> It will never be
     * null or empty.<br/>
     * 
     */
    private final String gameDataJndiName;

    /**
     * This holds the name of the request parameter which will contain the
     * domain id which is to be approved or rejected. The value should be able
     * to be parsed into a long value.<br/> It is initialized in the
     * constructor and does not change after that.<br/> It will never be null
     * or empty.<br/>
     * 
     */
    private final String domainIdRequestParamName;

    /**
     * This holds the name of the request parameter which will contain the
     * sponsor id associated with the domain. The value should be able to be
     * parsed into a long value.<br/> It is initialized in the constructor and
     * does not change after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String sponsorIdRequestParamName;

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
     *      &lt;handler type=&quot;domainApproval&quot;&gt;
     *      &lt;!--  Namespace to use when instantiating ConfigManagerSpecificationFactory --&gt;
     *      &lt;object-factory-ns&gt;objFactoryNS&lt;/object-factory-ns&gt;
     * 
     *      &lt;!--  JNDI name to use to lookup the AdminDataHome interface --&gt;
     *      &lt;admin-data-jndi-name&gt;AdminDataHome&lt;/admin-data-jndi-name&gt;
     * 
     *      &lt;!--  JNDI name to use to lookup the GameDataHome interface --&gt;
     *      &lt;game-data-jndi-name&gt;GameDataHome&lt;/ game-data-jndi-name&gt;
     * 
     *      &lt;!--  name of request parameter which will contain the domain id --&gt;
     *      &lt;domain-id-request-param&gt;DomainId&lt;/domain-id-request-param&gt;
     * 
     *      &lt;!--  name of request parameter which will contain the sponsor id --&gt;
     *      &lt;sponsor-id-request-param&gt;sponsorId&lt;/sponsor-id-request-param&gt;
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
    DomainApprovalRejectionHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);
        gameDataJndiName = Helper.getValue(handlerElement,
                "/handler/game-data-jndi-name");
        adminDataJndiName = Helper.getValue(handlerElement,
                "/handler/admin-data-jndi-name");
        objFactoryNS = Helper.getValue(handlerElement,
                "/handler/object-factory-ns");
        userProfileManager = Helper.createUserProfileManager(objFactoryNS);
        domainIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/domain-id-request-param");
        sponsorIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/sponsor-id-request-param");
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * <p>
     * This method will use GameData to search for the domain and
     * UserProfileManager to search for the associated sponsor. After validation
     * it will use AdminData to mark the domain as approved or rejected. If
     * already approved or rejected, this will return a failed result.
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

        // Get requested domain id from request parameter
        String domainIdString = request.getParameter(domainIdRequestParamName);
        // Parse domainId into a long value
        Long domainId = Helper.parseLong(request, domainIdString, "domainId",
                failRequestAttrName);
        if (domainId == null) {
            return failedResult;
        }
        // Get the domain
        Domain domain;
        try {
            domain = gameData.getDomain(domainId.longValue());
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get domain by domianId:" + domainId,
                    failRequestAttrName, e);
            return failedResult;
        }
        UserProfile sponsor = searchSponsor(request, domain);
        if (sponsor == null) {
            return failedResult;
        }
        String approved = (String) sponsor.getProperty("IS_APPROVED");
        if (approved == null || approved.trim().length() == 0) {
            Helper.processFailureApprovalNotPending(request,
                    failRequestAttrName, domain.getSponsorId());
        }
        // Create the AdminData
        AdminData adminData = null;
        adminData = Helper.getAdminData(adminDataJndiName, request,
                failRequestAttrName);
        if (adminData == null) {
            return failedResult;
        }
        // Update domain
        try {
            adminData
                    .setDomainApproval(domainId.longValue(), getApprovedFlag());
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to set domain approval, the domianId:" + domainId,
                    failRequestAttrName, e);
            return failedResult;
        }

        // return null for successful execution.
        return null;
    }

    /**
     * Search for sponsor using sponsor Id get from request.
     * 
     * @param request
     *            the HttpServletRequest instance
     * @param domain
     *            the Domain instance
     * @return UserProfile instance
     */
    private UserProfile searchSponsor(HttpServletRequest request, Domain domain) {
        // Get sponsor id from request parameter
        String sponsorIdString = request
                .getParameter(sponsorIdRequestParamName);
        // Parse sponsorId into a long value
        Long sponsorId = Helper.parseLong(request, sponsorIdString,
                "sponsorId", failRequestAttrName);
        if (sponsorId == null) {
            return null;
        }
        if (domain.getSponsorId() != sponsorId.longValue()) {
            Helper.processFailureSponsorNotBelongToDomain(request,
                    failRequestAttrName, sponsorId.longValue(), domain.getId()
                            .longValue());
            return null;
        }

        return Helper.getSponsor(domain.getSponsorId(), request,
                userProfileManager, failRequestAttrName);
    }

    /**
     * This method must be implemented by the sub class to denote whether the
     * domain is to be approved or rejected. Must return true for approval,
     * false for rejection.
     * 
     * 
     * @return true for approval, false for rejection.
     */
    abstract boolean getApprovedFlag();
}
