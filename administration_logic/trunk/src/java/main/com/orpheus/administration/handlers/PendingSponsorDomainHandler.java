/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.administration.Helper;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.GameData;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Provides a handler implementation that loads detail data about a given
 * domain. The data loaded are the Domain object for a given domain id which
 * also include the he image details for each associated image, including
 * individual approval status, and the UserProfile instance for the domain's
 * associated sponsor. The names of the request attributes in which these are
 * stored are configurable.<br/> In case of failure, such as an error when
 * making remote calls, the execute method will return a configurable result
 * name and set a HandlerResult instance as a request attribute so that an
 * appropriate response or user message can be generated. In case of success,
 * the execute() method will return null.<br/> For configuration details on
 * this handler, please see Section 3.2.3 of Comp Spec.<br/> Thread-Safety:
 * This class is thread-safe as is required of Handler implementations. To
 * achieve this, it synchronizes over the userProfileManager instance var in the
 * execute() method.
 * 
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
public class PendingSponsorDomainHandler implements Handler {

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
     * domain id for which details are to be loaded. The value should be able to
     * be parsed into a long value.<br/> It is initialized in the constructor
     * and does not change after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String domainIdRequestParamName;

    /**
     * This holds the name of the request attribute to which Domain instance
     * representing pending sponsor should be assigned to, in case of successful
     * execution.<br/> It is initialized in the constructor and does not change
     * after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String domainRequestAttrName;

    /**
     * This holds the name of the request attribute to which UserProfile
     * instance representing domain's sponsor should be assigned to, in case of
     * successful execution.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String sponsorRequestAttrName;

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
     * Creates a PendingSponsorDomainHandler instance configured from the given
     * xml element. It will initialize the userProfileManager instance and other
     * instance variables. It will throw an IllegalArgumentException if
     * configuration details are missing in the handlerElement argument.
     * 
     * Example xml structure:
     * 
     * <pre>
     *      &lt;handler type=&quot;pendingSponsorDomain&quot;&gt;
     *      &lt;!--  Namespace to use when instantiating ConfigManagerSpecificationFactory --&gt;
     *      &lt;object-factory-ns&gt;objFactoryNS&lt;/object-factory-ns&gt;
     * 
     *      &lt;!--  JNDI name to use to lookup the GameDataHome interface --&gt;
     *      &lt;game-data-jndi-name&gt;GameDataHome&lt;/ game-data-jndi-name&gt;
     * 
     *      &lt;!--  name of request parameter which will contain the domain id --&gt;
     *      &lt;domain-id-request-param&gt;DomainId&lt;/domain-id-request-param&gt;
     * 
     *      &lt;!--  name of request attribute to store Domain in --&gt;
     *      &lt;domain-request-attribute&gt;Domain&lt;/domain-request-attribute&gt;
     * 
     *      &lt;!--  name of request attribute to store domain¡¯s associated sponsor in --&gt;
     *      &lt;sponsor- request-attribute&gt;Sponsor&lt;/sponsor-request-attribute&gt;
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
    public PendingSponsorDomainHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);

        objFactoryNS = Helper.getValue(handlerElement,
                "/handler/object-factory-ns");
        userProfileManager = Helper.createUserProfileManager(objFactoryNS);
        gameDataJndiName = Helper.getValue(handlerElement,
                "/handler/game-data-jndi-name");
        domainIdRequestParamName = Helper.getValue(handlerElement,
                "/handler/domain-id-request-param");
        domainRequestAttrName = Helper.getValue(handlerElement,
                "/handler/domain-request-attribute");
        sponsorRequestAttrName = Helper.getValue(handlerElement,
                "/handler/sponsor-request-attribute");
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * <p>
     * This method will first use GameData to find the requested domain. Then it
     * uses UserProfileManager to search for the domain's associated sponsor.
     * The Domain and UserProfile instances will be set as request attributes of
     * configurable name.
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
        GameData gameData = Helper.getGameData(gameDataJndiName, request,
                failRequestAttrName);
        // Get the domain
        String domainIdString = Helper.getRequestParameter(request,
                domainIdRequestParamName, failRequestAttrName);
        if (domainIdString == null) {
            return failedResult;
        }
        // Parse the domainId into a long value
        Long domainId = Helper.parseLong(request, domainIdString, "domainId",
                failRequestAttrName);
        if (domainId == null) {
            return failedResult;
        }
        Domain domain;
        try {
            domain = gameData.getDomain(domainId.longValue());
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get domain via domainId:" + domainId,
                    failRequestAttrName, e);
            return failedResult;
        }
        // Search for domain¡¯s associated sponsor
        UserProfile sponsor = Helper.getSponsor(domain.getSponsorId(), request,
                userProfileManager, failRequestAttrName);

        // Set the domain and sponsor as request attributes
        request.setAttribute(domainRequestAttrName, domain);
        request.setAttribute(sponsorRequestAttrName, sponsor);

        // return null for successful execution.
        return null;
    }

}
