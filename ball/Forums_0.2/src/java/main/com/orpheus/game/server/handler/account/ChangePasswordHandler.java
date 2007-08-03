/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.account;

import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.util.datavalidator.registration.http.HttpValidationManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;

import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>A custom handler implementation of {@link Handler} interface which is responsible for changing the password of 
 * an account.</p>
 *
 * @author mktong
 * @version 1.0
 */
public class ChangePasswordHandler extends AbstractGameServerHandler implements Handler {

    private final UserProfileManager userProfileManager;
    
    public static final String CURRENT_PASSWORD_PARAM_NAME_CONFIG = "current_password_param";
    public static final String NEW_PASSWORD_PARAM_NAME_CONFIG = "new_password_param";
    public static final String NEW_PASSWORD_CONFIRM_PARAM_NAME_CONFIG = "new_password_confirm_param";
    
    /**
     * <p>Constructs new <code>ChangePasswordHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *       &lt;current_password_param&gt;currPwd&lt;/current_password_param&gt;
     *       &lt;new_password_param&gt;newPwd&lt;/new_password_param&gt;
     *       &lt;new_password_confirm_param&gt;newPwdConfirm&lt;/new_password_confirm_param&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public ChangePasswordHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, CURRENT_PASSWORD_PARAM_NAME_CONFIG, true);
        readAsString(element, NEW_PASSWORD_PARAM_NAME_CONFIG, true);
        readAsString(element, NEW_PASSWORD_CONFIRM_PARAM_NAME_CONFIG, true);
        this.userProfileManager = getUserProfileManager(element);
    }

    /**
     * <p>Validates password form fields, saving the new password if possible.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
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
            
            if ("save".equals(form_status)) {
                HttpValidationManager validationManager = (HttpValidationManager)HttpValidationManager.getInstance();
                boolean isValid = validationManager.validate(request, "player_change_password_validation_rules");
                //UserProfile profile = userProfileManager.getUserProfile(playerId);
                UserProfile profile = LoginHandler.getAuthenticatedUser(request.getSession());
                String password = (String) profile.getProperty(UserConstants.CREDENTIALS_PASSWORD);
                
                String currPassword = request.getParameter(getString(CURRENT_PASSWORD_PARAM_NAME_CONFIG));
                String newPassword = request.getParameter(getString(NEW_PASSWORD_PARAM_NAME_CONFIG));
                
                if (isValid && password.equals(currPassword)) {
                    System.out.println("*** Updating password for user " + profile.getProperty(UserConstants.CREDENTIALS_HANDLE));
                    profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, newPassword);
                    userProfileManager.updateUserProfile(profile);
                    request.setAttribute("success", String.valueOf(true));
                } else if (!password.equals(currPassword)) {
                    request.setAttribute(getString(CURRENT_PASSWORD_PARAM_NAME_CONFIG)+".error", 
                            "Current password is incorrect.");
                }
            }
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not change account password due to unexpected error", e);
        }
    }
}