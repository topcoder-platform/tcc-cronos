/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.administration.Helper;
import com.topcoder.user.profile.InvalidKeyException;
import com.topcoder.user.profile.InvalidValueException;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UnknownProfileException;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.user.profile.manager.UserProfileManagerException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Provides an abstract base handler implementation that approves or rejects a
 * given sponsor. The id of the sponsor to approve or reject is accepted as
 * request parameter of configurable name. The execute() method will ensure that
 * the sponsor has neither been approved or rejected. If corresponding sponsor
 * does not exist or if it has already been approved or rejected, it is
 * considered as a fail condition. Else the sponsor is marked as
 * approved/rejected and persistence updated.<br/> In case of failure, the
 * execute method will return a configurable result name and set a HandlerResult
 * instance as a request attribute so that an appropriate response or user
 * message can be generated. In case of success, the execute() method will
 * return null.<br/> SponsorApprovalHandler and SponsorRejectionHandler classes
 * provide concrete implementations on whether to approve or reject, in that
 * they provide the value to be set for the sponsor-is-approved property of the
 * UserProfile instance.<br/> For configuration details on this handler, please
 * see Section 3.2.4 of Comp Spec.<br/> Thread-Safety: This class is
 * thread-safe as is required of Handler implementations. To achieve this, it
 * synchronizes over the userProfileManager instance var in the execute()
 * method.
 * 
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
abstract class SponsorApprovalRejectionHandler implements Handler {

    /**
     * This holds the UserProfileManager instance which will be used to search
     * for sponsor.<br/> It is initialized in the constructor and does not
     * change after that.<br/> It will never be null.<br/>
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
     * This holds the name of the request parameter which will contain the
     * sponsor id which is to be approved or rejected. The value should be able
     * to be parsed into a long value.<br/> It is initialized in the
     * constructor and does not change after that.<br/> It will never be null
     * or empty.<br/>
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
     *      &lt;handler type=&quot;sponsorApproval&quot;&gt;
     *      &lt;!--  Namespace to use when instantiating ConfigManagerSpecificationFactory --&gt;
     *      &lt;object-factory-ns&gt;objFactoryNS&lt;/object-factory-ns&gt;
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
     * </pre>
     * 
     * @param handlerElement
     *            the XML element containing configuration for this handler.
     * @throws IllegalArgumentException
     *             if handlerElement is null, or contains invalid data.
     */
    SponsorApprovalRejectionHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);
        objFactoryNS = Helper.getValue(handlerElement,
                "/handler/object-factory-ns");
        userProfileManager = Helper.createUserProfileManager(objFactoryNS);
        sponsorIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/sponsor-id-request-param");
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * <p>
     * This method will use UserProfileManager to search for the required
     * sponsor and mark it as approved or rejected depending on the return value
     * of getIsApprovedPropertyValue(). If already approved or rejected, this
     * will return a failed result.
     * </p>
     * <p>
     * This method will return null in case of success and will return a
     * configurable result name in case of failure such as any exceptions.
     * </p>
     * Spec.
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
        // Search for sponsor
        // Get sponsor id from request parameter
        String sponsorIdString = Helper.getRequestParameter(request,
                sponsorIdRequestParamName, failRequestAttrName);
        if (sponsorIdString == null) {
            return failedResult;
        }
        // Parse sponsorId into a long value
        Long sponsorId = Helper.parseLong(request, sponsorIdString,
                "sponsorId", failRequestAttrName);
        if (sponsorId == null) {
            return failedResult;
        }
        UserProfile sponsor = Helper.getSponsor(sponsorId.longValue(), request,
                userProfileManager, failRequestAttrName);
        if (sponsor == null) {
            return failedResult;
        }
        if (Helper.checkApproval(sponsor)) {
            Helper.processFailureApprovalNotPending(request,
                    failRequestAttrName, sponsorId.longValue());
            return failedResult;
        } else {
            try {
                sponsor
                        .setProperty("sponsor-is-approved",
                                getIsApprovedPropertyValue());
                synchronized (userProfileManager) {
                    userProfileManager.updateUserProfile(sponsor);
                }
            } catch (InvalidKeyException e) {
                Helper.processFailureExceptionOccur(request,
                        "Failed to set property of sponsor",
                        failRequestAttrName, e);
                return failedResult;
            } catch (InvalidValueException e) {
                Helper.processFailureExceptionOccur(request,
                        "Failed to set property of sponsor",
                        failRequestAttrName, e);
                return failedResult;
            } catch (UnknownProfileException e) {
                Helper.processFailureExceptionOccur(request,
                        "Failed to update sponsor", failRequestAttrName, e);
                return failedResult;
            } catch (UserProfileManagerException e) {
                Helper.processFailureExceptionOccur(request,
                        "Failed to update sponsor", failRequestAttrName, e);
                return failedResult;
            }
        }

        // return null in case of successful execution.
        return null;
    }

    /**
     * This method must be implemented by the sub class to provide the value for
     * the sponsor-is-approved property of the UserProfile instance representing the
     * sponsor.
     * 
     * 
     * @return value for sponsor-is-approved property of UserProfile instance
     *         representing the sponsor.
     */
    abstract String getIsApprovedPropertyValue();
}
