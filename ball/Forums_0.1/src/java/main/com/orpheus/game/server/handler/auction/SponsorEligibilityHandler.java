/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.auction;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public class SponsorEligibilityHandler extends AbstractAuctionHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the auction ID from.</p>
     */
    protected static final String SPONSOR_PROPERTY_CONFIG = "sponsor_approved_property";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the auction ID from.</p>
     */
    protected static final String SPONSOR_PROPERTY_VALUE_CONFIG = "sponsor_approved_property_value";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the auction ID from.</p>
     */
    protected static final String SPONSOR_UNAPPROVED_RESULT_CONFIG = "sponsor_unapproved_result";

    /**
     * <p>Constructs new <code>SponsorEligibilityHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>Below is a sample of an XML element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;games_key&gt;games&lt;/games_key&gt;
     *      &lt;auctions_request_attr_key&gt;openAuctions&lt;/auctions_request_attr_key&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or any of required configuration parameters
     *         is missing.
     */
    public SponsorEligibilityHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, SPONSOR_PROPERTY_CONFIG, true);
        readAsString(element, SPONSOR_PROPERTY_VALUE_CONFIG, true);
        readAsString(element, SPONSOR_UNAPPROVED_RESULT_CONFIG, true);
    }

    /**
     * <p>Executes this handler when servicing the specified request. Obtains the auctions from the request and locates
     * the list of games which correspond to provided auctions. The list of such games is bound to request for further
     * use.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if a handler execution succeeds; otherwise an exception will be thrown.
     * @throws HandlerExecutionException if an unrecoverable error prevenst the handler from successful execution.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession(false);

        // Get the sponsor profile from the session
        UserProfile sponsor = LoginHandler.getAuthenticatedUser(session);
        if (sponsor == null) {
            throw new HandlerExecutionException("The sponsor is not logged to application");
        }

        // Get the profile property and check against a value specified by configuration
        Object property = sponsor.getProperty(getString(SPONSOR_PROPERTY_CONFIG));
        if (!String.valueOf(property).equals(getString(SPONSOR_PROPERTY_VALUE_CONFIG))) {
            return getString(SPONSOR_UNAPPROVED_RESULT_CONFIG);
        } else {
            return null;
        }
    }
}
