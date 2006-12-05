/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Element;

import com.orpheus.administration.Helper;
import com.orpheus.administration.entities.SponsorDomain;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.GameData;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManagerException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * Provides a handler implementation that loads overview data about all sponsors
 * pending approval. The data loaded are the user profile instance for each
 * sponsor and the domain objects associated with each sponsor. The name of the
 * request attribute in which these are stored is configurable.<br/> In case of
 * failure, such as an error when making remote calls, the execute method will
 * return a configurable result name and set a HandlerResult instance as a
 * request attribute so that an appropriate response or user message can be
 * generated. In case of success, the execute() method will return null.<br/>
 * For configuration details on this handler, please see Section 3.2.2 of Comp
 * Spec.<br/> Thread-Safety: This class is thread-safe as is required of
 * Handler implementations. To achieve this, it synchronizes over the
 * userProfileManager instance var in the execute() method.
 * 
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class PendingSponsorHandler implements Handler {

    /**
     * This holds the UserProfileManager instance which will be used to search
     * for pending sponsors.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null.<br/>
     * 
     */
    private final com.topcoder.user.profile.manager.UserProfileManager userProfileManager;

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
     * will be used to search for domains for each pending sponsor.<br/>
     * GameDataHome is defined in com.orpheus.game.persistence package.<br/>
     * This variable is initialized in the constructor and does not change after
     * that.<br/> It will never be null or empty.<br/>
     * 
     */
    private final String gameDataJndiName;

    /**
     * This holds the name of the request attribute to which array of
     * SponsorDomain representing pending sponsors and associated domains should
     * be assigned to, in case of successful execution.<br/> It is initialized
     * in the constructor and does not change after that.<br/> It will never be
     * null or empty.<br/>
     * 
     */
    private final String sponsorDomainRequestAttrName;

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
     * Creates a PendingSponsorHandler instance configured from the given xml
     * element. It will initialize the userProfileManager instance and other
     * instance variables. It will throw an IllegalArgumentException if
     * configuration details are missing in the handlerElement argument.
     * 
     * Example xml structure:
     * 
     * <pre>
     *      &lt;handler type=&quot;pendingSponsor&quot;&gt;
     *      &lt;!--  Namespace to use when instantiating ConfigManagerSpecificationFactory --&gt;  &lt;object-factory-ns&gt;objFactoryNS&lt;/object-factory-ns&gt;
     * 
     *      &lt;!--  JNDI name to use to lookup the GameDataHome interface --&gt;
     *      &lt;game-data-jndi-name&gt;GameDataHome&lt;/ game-data-jndi-name&gt;
     * 
     *      &lt;!--  name of request attribute to store pending sponsors and associated domains in --&gt;
     *      &lt;sponsor-domain-request-attribute&gt;SponsorDomains&lt;/sponsor-domain-request-attribute&gt;
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
    public PendingSponsorHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);
        objFactoryNS = Helper.getValue(handlerElement,
                "/handler/object-factory-ns");
        userProfileManager = Helper.createUserProfileManager(objFactoryNS);
        gameDataJndiName = Helper.getValue(handlerElement,
                "/handler/game-data-jndi-name");
        sponsorDomainRequestAttrName = Helper.getValue(handlerElement,
                "/handler/sponsor-domain-request-attribute");
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * <p>
     * This method will first use UserProfileManager to search for sponsors
     * pending approval. Then it uses GameData to find the domains for each
     * pending sponsor. The SponsorDomain data structure is used to represent
     * one pending sponsor and its associated domains. Thus an array of
     * SponsorDomain instances will be set in the request attribute to represent
     * all sponsors pending approval and their associated domains.<br/> If
     * there are no pending sponsors, this method will set an array of zero
     * length as request attribute.
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

        // Search for pending sponsors
        Map searchParams = new HashMap();
        searchParams.put("IS_APPROVED", null);
        // Search for the pending sponsors
        UserProfile[] sponsors = null;
        synchronized (userProfileManager) {
            try {
                sponsors = userProfileManager.searchUserProfiles(searchParams);
            } catch (UserProfileManagerException e) {
                Helper.processFailureExceptionOccur(request,
                        "Failed to retrieve user profiles",
                        failRequestAttrName, e);
                return failedResult;
            }
        }
        SponsorDomain[] sponsorDomains = new SponsorDomain[sponsors.length];
        if (sponsors.length != 0) {
            // Create the GameData EJB instance
            GameData gameData = Helper.getGameData(gameDataJndiName, request,
                    failRequestAttrName);
            if (gameData == null) {
                return failedResult;
            }
            for (int i = 0; i < sponsors.length; i++) {
                Domain[] domains;
                try {
                    domains = gameData
                            .findDomainsForSponsor(((Long) sponsors[i]
                                    .getIdentifier()).longValue());
                } catch (Exception e) {
                    Helper.processFailureExceptionOccur(request,
                            "Failed to find domains for sponsor",
                            failRequestAttrName, e);
                    return failedResult;
                }
                sponsorDomains[i] = new SponsorDomain(sponsors[i], domains);
            }
        }
        // Set the sponsor and domains as request attribute
        request.setAttribute(sponsorDomainRequestAttrName, sponsorDomains);
        // return null for successful execution.
        return null;
    }
}
