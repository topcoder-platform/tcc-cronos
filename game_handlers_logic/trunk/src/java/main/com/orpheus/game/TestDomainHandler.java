/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import org.w3c.dom.Element;

import com.orpheus.game.persistence.Game;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;


/**
 * <p>
 * <code>TestDomainsHandler</code> class implements the <code>Handler</code> interface from the
 * <code>FrontController</code> component. It determines in which active games the specified domain is a current or
 * past host for the current user, creates an array containing the corresponding <code>Game</code> objects, and
 * assigns the array to a request attribute of configurable name.
 *
 * To follow the <code>Handler</code> implementation's contract in order to make it work properly with
 * <code>FrontController</code> component, two constructors are defined, one takes the configurable parameters, and
 * the other takes an Element object which contains all configurable parameters.
 *
 * Thread-safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author Standlove, mittu
 * @version 1.0
 */
public class TestDomainHandler implements Handler {

    /**
     * <p>
     * Represents the key used to get the domain name from request parameter in execute method. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String domainNameParamKey;

    /**
     * <p>
     * Represents the attribute key used to store a <code>Game</code> array into the request's attribute Map in
     * execute method. It is initialized in constructor and never changed afterwards. It must be non-null, non-empty
     * string.
     * </p>
     */
    private final String gamesKey;

    /**
     * <p>
     * Represents the result code to return in the execute method if the user is not logged-in. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String notLoggedInResultCode;

    /**
     * <p>
     * Constructor with configurable arguments. Simply assign the arguments to corresponding variables.
     * </p>
     *
     * @param domainNameParamKey
     *            the key used to get the domain name from request parameter in execute method.
     * @param gamesKey
     *            the attribute key used to store a Game array into the request's attribute Map in execute method.
     * @param notLoggedInResultCode
     *            the result code to return in the execute method if the user is not logged-in.
     * @throws IllegalArgumentException
     *             if any argument is null or empty string.
     */
    public TestDomainHandler(String domainNameParamKey, String gamesKey, String notLoggedInResultCode) {
        ImplementationHelper.checkStringValid(domainNameParamKey, "domainNameParamKey");
        ImplementationHelper.checkStringValid(gamesKey, "gamesKey");
        ImplementationHelper.checkStringValid(notLoggedInResultCode, "notLoggedInResultCode");
        this.domainNameParamKey = domainNameParamKey;
        this.gamesKey = gamesKey;
        this.notLoggedInResultCode = notLoggedInResultCode;
    }

    /**
     * <p>
     * Constructor with an xml Element object. The constructor will extract necessary configurable values from this xml
     * element.
     *
     * Here is what the xml element likes:
     *
     * <pre>
     *    &lt;handler type=&quot;x&quot;&gt;
     *     &lt;domain_name_param_key&gt;domainName&lt;/domain_name_param_key&gt;
     *     &lt;games_key&gt;games&lt;/games_key&gt;
     *     &lt;not_logged_in_result_code&gt;not_logged_in&lt;/not_logged_in_result_code&gt;
     *    &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     * @param element
     *            the xml Element to extract the configurable values.
     * @throws IllegalArgumentException
     *             if the argument is null or any value mentioned is missing or empty string.
     *
     */
    public TestDomainHandler(Element element) {
        ImplementationHelper.checkObjectValid(element, "element");
        domainNameParamKey = ImplementationHelper.getElement(element, "domain_name_param_key");
        gamesKey = ImplementationHelper.getElement(element, "games_key");
        notLoggedInResultCode = ImplementationHelper.getElement(element, "not_logged_in_result_code");
    }

    /**
     * <p>
     * It determines in which active games the specified domain is a current or past host for the current user, creates
     * an array containing the corresponding <code>Game</code> objects, and assigns the array to a request attribute
     * of configurable name. The <code>LoginHandler</code> will be used to get logged-in user's details, and a proper
     * resultCode is returned if user is not logged in, null is returned if user is logged-in, and the games for this
     * user are loaded successfully (it is ok if the games array is of zero length). Otherwise,
     * <code>HandlerExecutionException</code> is thrown.
     *
     * @param context
     *            the ActionContext object.
     * @return a proper result code if user is not logged in, return null otherwise.
     * @throws IllegalArgumentException
     *             if the given argument is null.
     * @throws HandlerExecutionException
     *             if fail to load games from specified domain for the current user, or the UserProfile's identifier is
     *             not Long object, or the domainName is missing or invalid in request parameter.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ImplementationHelper.checkObjectValid(context, "context");
        GameDataHelper helper = GameDataHelper.getInstance();
        try {
            // gets the user profile from the LoginHandler.
            UserProfile userProfile = LoginHandler.getAuthenticatedUser(context.getRequest().getSession(true));
            // if no profile, return the result code.
            if (userProfile == null) {
                return notLoggedInResultCode;
            }
            long profileId = ((Long) userProfile.getIdentifier()).longValue();
            // parses the domain name from the request parameter.
            String domainName = context.getRequest().getParameter(domainNameParamKey);
            if (domainName == null || domainName.trim().length() == 0) {
                throw new HandlerExecutionException("Failed to execute test domain handler, invalid domain name");
            }
            // finds the games using the helper.
            Game[] games = helper.findGamesByDomain(domainName, profileId);
            // save the games array to the request attribute.
            context.getRequest().setAttribute(gamesKey, games);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to execute test domain handler.", castException);
        } catch (IllegalArgumentException exception) {
            // session is null.
            return notLoggedInResultCode;
        }
        return null;
    }
}
