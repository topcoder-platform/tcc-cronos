/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.registration.jsptags.RegistrationInfo;
import com.topcoder.user.profile.UserProfile;
import org.w3c.dom.Element;

import javax.servlet.http.HttpSession;

/**
 * <p>A custom handler to be plugged into sponsor registration workflow. The purpose of this handler is to save the
 * {@link UserProfile} created during the sponsor registration workflow to session so it could be re-used later
 * by {@link SponsorRegistrationHandler} since the existing <code>Web Registration</code> component will remove the
 * registration info after the main sponsor account details are saved to persistent data store but such an info is
 * required later to save the details for domains and images submitted by sponsor.</p>
 *
 * @author isv
 * @version 1.0
 */
public class SponsorRegistrationPreHandler implements Handler {

    /**
     * <p>A <code>String</code> representing the name of session attribute used to store <code>RegistrationInfo</code>.
     * </p>
     */
    private static final String REGISTRATION_INFO = "registrationInfo";

    /**
     * <p>A <code>String</code> representing the name of session attribute used to store <code>UserProfile</code>.</p>
     */
    private static final String USER_PROFILE_SESSION_ATTR = "_user_profile";

    /**
     * <p>Constructs new <code>SponsorRegistrationPreHandler</code> instance. This implementation does nothing.</p>
     *
     * @param config an <code>Element</code> providing the configuration parameters for this instance.
     */
    public SponsorRegistrationPreHandler(Element config) {
    }

    /**
     * <p>Processes the incoming request. Obtains the user profile with data collected during the sponsor registration
     * workflow form session and saves it to session so it could be re-used later by the <code>
     * SponsorRegistrationHandler</code>.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpSession session = context.getRequest().getSession(false);
        if (session != null) {
            RegistrationInfo info = (RegistrationInfo) session.getAttribute(REGISTRATION_INFO);
            if (info != null) {
                UserProfile userProfile = info.getUserProfile();
                if (userProfile != null) {
                    session.setAttribute(USER_PROFILE_SESSION_ATTR, userProfile);
                    return null;
                } else {
                    throw new HandlerExecutionException("There is no sponsor profile associated with registration info");
                }
            } else {
                throw new HandlerExecutionException("There is no registration info associated with current user");
            }
        } else {
            throw new HandlerExecutionException("There is no session associated with current user");
        }
    }
}
