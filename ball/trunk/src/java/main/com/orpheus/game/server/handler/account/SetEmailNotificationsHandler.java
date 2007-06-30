/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.account;

import java.util.Enumeration;

import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.ConfigProfileTypeFactory;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>A custom handler implementation of {@link Handler} interface which is responsible for setting the email
 * notification preferences of an account.</p>
 *
 * @author mktong
 * @version 1.0
 */
public class SetEmailNotificationsHandler extends AbstractGameServerHandler implements Handler {

    private final UserProfileManager userProfileManager;
    
    public static final String EMAIL_GENERAL_NOTIFICATIONS_CONFIG = "email_general_notifications_param";
    
    /**
     * <p>Constructs new <code>SetEmailNotificationsHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *       &lt;email_general_notifications_param&gt;phone&lt;/email_general_notifications_param&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public SetEmailNotificationsHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, EMAIL_GENERAL_NOTIFICATIONS_CONFIG, true);
        this.userProfileManager = getUserProfileManager(element);
    }

    /**
     * <p>Validates password form fields, saving the new password if possible.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if an unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        
        // Check if player is authenticated and get the player ID from profile
        long playerId = getUserId(context);
        
        try {
            HttpServletRequest request = context.getRequest();
            String form_status = request.getParameter("status");
            
            UserProfile profile = userProfileManager.getUserProfile(playerId);
            request.setAttribute("profile", profile);
            
            if ("save".equals(form_status)) {
                String genNotify = request.getParameter(getString(EMAIL_GENERAL_NOTIFICATIONS_CONFIG));
                                
                if (profile.getProfileType(UserConstants.PREFERENCES_TYPE_NAME) == null) {
                    ConfigProfileTypeFactory profileTypeFactory = new ConfigProfileTypeFactory("com.topcoder.user.profile.manager");
                    profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.PREFERENCES_TYPE_NAME));
                    if (profile.getProperty(UserConstants.PREFS_SOUND) == null) {
                        profile.setProperty(UserConstants.PREFS_SOUND, new Integer(1));  // should be set to global default sound
                    }
                }
                
                System.out.println("*** Setting email notification preferences for user " + 
                        profile.getProperty(UserConstants.CREDENTIALS_HANDLE));
                profile.setProperty(UserConstants.PREFS_GENERAL_NOTIFICATION, Boolean.valueOf("on".equals(genNotify)));
                userProfileManager.updateUserProfile(profile);
            }
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not set email notification preferences due to unexpected error", e);
        }
    }
}