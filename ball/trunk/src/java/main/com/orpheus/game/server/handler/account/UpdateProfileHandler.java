/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.account;

import java.util.Enumeration;

import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.util.datavalidator.registration.http.HttpValidationManager;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.user.profile.manager.ConfigProfileTypeFactory;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>A custom handler implementation of {@link Handler} interface which is responsible for updating the profile 
 * data of an account.</p>
 *
 * @author mktong
 * @version 1.0
 */
public class UpdateProfileHandler extends AbstractGameServerHandler implements Handler {

    private final UserProfileManager userProfileManager;
    
    public static final String EMAIL_ADDRESS_PARAM_NAME_CONFIG = "email_address_param";
    public static final String FIRST_NAME_PARAM_NAME_CONFIG = "first_name_param";
    public static final String LAST_NAME_PARAM_NAME_CONFIG = "last_name_param";
    public static final String ADDRESS1_PARAM_NAME_CONFIG = "address1_param";
    public static final String ADDRESS2_PARAM_NAME_CONFIG = "address2_param";
    public static final String CITY_PARAM_NAME_CONFIG = "city_param";
    public static final String STATE_PARAM_NAME_CONFIG = "state_param";
    public static final String POSTAL_CODE_PARAM_NAME_CONFIG = "postal_code_param";
    public static final String COUNTRY_PARAM_NAME_CONFIG = "country_param";
    public static final String PAYMENT_METHOD_PARAM_NAME_CONFIG = "payment_method_param";
    public static final String PHONE_PARAM_NAME_CONFIG = "phone_param";
    
    /**
     * <p>Constructs new <code>UserProfileHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *       &lt;email_address_param&gt;email&lt;/email_address_param&gt;
     *       &lt;first_name_param&gt;firstName&lt;/first_name_param&gt;
     *       &lt;last_name_param&gt;lastName&lt;/last_name_param&gt;
     *       &lt;address1_param&gt;address1&lt;/address1_param&gt;
     *       &lt;address2_param&gt;address2&lt;/address2_param&gt;
     *       &lt;city_param&gt;city&lt;/city_param&gt;
     *       &lt;state_param&gt;state&lt;/state_param&gt;
     *       &lt;postal_code_param&gt;postalCode&lt;/postal_code_param&gt;
     *       &lt;country_param&gt;country&lt;/country_param&gt;
     *       &lt;payment_method_param&gt;paymentMethod&lt;/payment_method_param&gt;
     *       &lt;phone_param&gt;phone&lt;/phone_param&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public UpdateProfileHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, EMAIL_ADDRESS_PARAM_NAME_CONFIG, true);
        readAsString(element, FIRST_NAME_PARAM_NAME_CONFIG, true);
        readAsString(element, LAST_NAME_PARAM_NAME_CONFIG, true);
        readAsString(element, ADDRESS1_PARAM_NAME_CONFIG, true);
        readAsString(element, ADDRESS2_PARAM_NAME_CONFIG, true);
        readAsString(element, CITY_PARAM_NAME_CONFIG, true);
        readAsString(element, STATE_PARAM_NAME_CONFIG, true);
        readAsString(element, POSTAL_CODE_PARAM_NAME_CONFIG, true);
        readAsString(element, COUNTRY_PARAM_NAME_CONFIG, true);
        readAsString(element, PAYMENT_METHOD_PARAM_NAME_CONFIG, true);
        readAsString(element, PHONE_PARAM_NAME_CONFIG, true);
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
                HttpValidationManager validationManager = (HttpValidationManager)HttpValidationManager.getInstance();
                boolean isValid = validationManager.validate(request, "player_update_profile_validation_rules");
                
                String emailAddress = request.getParameter(getString(EMAIL_ADDRESS_PARAM_NAME_CONFIG));
                String firstName = request.getParameter(getString(FIRST_NAME_PARAM_NAME_CONFIG));
                String lastName = request.getParameter(getString(LAST_NAME_PARAM_NAME_CONFIG));
                String address1 = request.getParameter(getString(ADDRESS1_PARAM_NAME_CONFIG));
                String address2 = request.getParameter(getString(ADDRESS2_PARAM_NAME_CONFIG));
                String city = request.getParameter(getString(CITY_PARAM_NAME_CONFIG));
                String state = request.getParameter(getString(STATE_PARAM_NAME_CONFIG));
                String postalCode = request.getParameter(getString(POSTAL_CODE_PARAM_NAME_CONFIG));
                String country = request.getParameter(getString(COUNTRY_PARAM_NAME_CONFIG));
                String paymentPref = request.getParameter(getString(PAYMENT_METHOD_PARAM_NAME_CONFIG));
                String phone = request.getParameter(getString(PHONE_PARAM_NAME_CONFIG));
                
                if (isValid) {                    
                    if (profile.getProfileType(UserConstants.ADDRESS_TYPE_NAME) == null) {
                        ConfigProfileTypeFactory profileTypeFactory = new ConfigProfileTypeFactory("com.topcoder.user.profile.manager");
                        profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.ADDRESS_TYPE_NAME));
                    }
                    
                    System.out.println("*** Updating profile for user " + profile.getProperty(UserConstants.CREDENTIALS_HANDLE));
                    profile.setProperty(BaseProfileType.EMAIL_ADDRESS, emailAddress);
                    profile.setProperty(BaseProfileType.FIRST_NAME, firstName);
                    profile.setProperty(BaseProfileType.LAST_NAME, lastName);
                    profile.setProperty(UserConstants.ADDRESS_STREET_1, address1);
                    profile.setProperty(UserConstants.ADDRESS_STREET_2, address2);
                    profile.setProperty(UserConstants.ADDRESS_CITY, city);
                    profile.setProperty(UserConstants.ADDRESS_STATE, state);
                    profile.setProperty(UserConstants.ADDRESS_POSTAL_CODE, postalCode);
                    profile.setProperty(UserConstants.ADDRESS_COUNTRY, country);
                    profile.setProperty(UserConstants.PLAYER_PAYMENT_PREF, paymentPref);
                    profile.setProperty(UserConstants.ADDRESS_PHONE_NUMBER, phone);
                    userProfileManager.updateUserProfile(profile);
                } else {
                    /*
                    System.out.println("*** VALIDATION FAILED: Updating profile for user " + profile.getProperty(UserConstants.CREDENTIALS_HANDLE));
                    Enumeration e = request.getAttributeNames();
                    while (e.hasMoreElements()) {
                        String s = (String)e.nextElement();
                        System.out.println(s + " = " + request.getAttribute(s));
                    }
                    */
                }
            }
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not update profile due to unexpected error", e);
        }
    }
}