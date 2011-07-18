/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.SessionInfo;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.RegistrationActionConfigurationException;

/**
 * <p>
 * This action will resend the activation email to the current user.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * <p>
 * <b>Sample Configuration:</b>
 *
 * <pre>
 *     &lt;bean id="resendAccountActivationEmailAction"
 *      class="com.topcoder.web.reg.actions.registration.ResendAccountActivationEmailAction"
 *         init-method="checkInitialization"&gt;
 *         &lt;property name="logger" ref="logger" /&gt;
 *         &lt;property name="userDAO" ref="userDAO" /&gt;
 *         &lt;property name="auditDAO" ref="auditDAO" /&gt;
 *         &lt;property name="registrationTypeDAO" ref="registrationTypeDAO" /&gt;
 *         &lt;property name="servletRequest" ref="servletRequest" /&gt;
 *         &lt;property name="authenticationSessionKey" value="webAuthentication" /&gt;
 *         &lt;property name="documentGenerator" ref="documentGenerator" /&gt;
 *         &lt;property name="emailSubject" value="emailSubject" /&gt;
 *         &lt;property name="emailSender" value="sender@topcoder.com" /&gt;
 *         &lt;property name="emailBodyTemplateName" value="test_files\valid-simple-comment.txt" /&gt;
 *         &lt;property name="sessionInfoSessionKey" value="sessionInfoSessionKey" /&gt;
 *
 *         &lt;property name="session"&gt;
 *             &lt;map&gt;
 *                 &lt;entry key="webAuthentication"&gt;
 *                     &lt;ref bean="webAuthentication" /&gt;
 *                 &lt;/entry&gt;
 *                 &lt;entry key="sessionInfoSessionKey"&gt;
 *                     &lt;ref bean="sessionInfo" /&gt;
 *                 &lt;/entry&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *
 *         &lt;property name="userRoles"&gt;
 *             &lt;set&gt;
 *                 &lt;ref bean="registrationType1" /&gt;
 *                 &lt;ref bean="registrationType2" /&gt;
 *                 &lt;ref bean="registrationType3" /&gt;
 *             &lt;/set&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * </pre>
 *
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class ResendAccountActivationEmailAction extends EmailSendingAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = ResendAccountActivationEmailAction.class.getName();

    /**
     * <p>
     * This is the set of user roles that will be applied to the new user. It is set in the setter. It can be
     * retrieved in the getter. It may have any value. This field will be injected by the container (expected
     * to be done only once), and is not expected to change afterward.
     * </p>
     */
    private Set<RegistrationType> userRoles;

    /**
     * <p>
     * This is the name of the session key where the SessionInfo instance will be stored. It is set in the
     * setter. It can be retrieved in the getter. It may have any value. This field will be injected by the
     * container (expected to be done only once), and is not expected to change afterward.
     * </p>
     */
    private String sessionInfoSessionKey;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public ResendAccountActivationEmailAction() {
        // Empty
    }

    /**
     * <p>
     * This method checks that all required values have been injected by the system right after construction
     * and injection This is used to obviate the need to check these values each time in the execute method.
     * </p>
     *
     * @throws RegistrationActionConfigurationException
     *             If any required value has not been injected into this class
     */
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();
        ValidationUtility.checkNotNullNorEmpty(userRoles, "userRoles",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNullElements(userRoles, "userRoles",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(sessionInfoSessionKey, "sessionInfoSessionKey",
            RegistrationActionConfigurationException.class);
    }

    /**
     * <p>
     * This action will resend the activation email to the current user.
     * </p>
     *
     * @return a string representing the logical result of the execution
     * @throws TCWebException
     *             If there are any errors in the action
     */
    @Override
    public String execute() throws TCWebException {
        // Log the entry
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"userRoles",
            "sessionInfoSessionKey"}, new Object[] {userRoles, sessionInfoSessionKey});

        try {
            // Get authentication object:
            WebAuthentication authentication = getAuthentication();
            // Get user:
            com.topcoder.shared.security.User activeUser = authentication.getActiveUser();
            User user = getUserDAO().find(activeUser.getId());
            ValidationUtility.checkNotNull(user, "user with id[" + activeUser.getId() + "]",
                TCWebException.class);

            // Get session info:
            SessionInfo sessionInfo = (SessionInfo) getSession().get(sessionInfoSessionKey);
            ValidationUtility.checkNotNull(sessionInfo,
                "sessionInfo with key[" + sessionInfoSessionKey + "]", TCWebException.class);

            // Build XML template data:
            String templateData = Helper.getTemplateData(getRegistrationTypeDAO(), user.getActivationCode(),
                sessionInfo, userRoles);

            // Send the email:
            Email primaryEmail = user.getPrimaryEmailAddress();
            ValidationUtility.checkNotNull(primaryEmail, "user's primary email", TCWebException.class);
            sendEmail(primaryEmail.getAddress(), templateData);

            // Audit user action:
            auditAction("resend account activation", activeUser.getUserName());

            // Log the exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS});

            return SUCCESS;
        } catch (TCWebException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "HibernateException occurs while accessing to database", e));
        }
    }

    /**
     * <p>
     * Getter method for userRoles, simply return the value of the namesake field.
     * </p>
     *
     * @return the userRoles
     */
    public Set<RegistrationType> getUserRoles() {
        return userRoles;
    }

    /**
     * <p>
     * Setter method for the userRoles, simply set the value to the namesake field.
     * </p>
     *
     * @param userRoles
     *            the userRoles to set
     */
    public void setUserRoles(Set<RegistrationType> userRoles) {
        this.userRoles = userRoles;
    }

    /**
     * <p>
     * Getter method for sessionInfoSessionKey, simply return the value of the namesake field.
     * </p>
     *
     * @return the sessionInfoSessionKey
     */
    public String getSessionInfoSessionKey() {
        return sessionInfoSessionKey;
    }

    /**
     * <p>
     * Setter method for the sessionInfoSessionKey, simply set the value to the namesake field.
     * </p>
     *
     * @param sessionInfoSessionKey
     *            the sessionInfoSessionKey to set
     */
    public void setSessionInfoSessionKey(String sessionInfoSessionKey) {
        this.sessionInfoSessionKey = sessionInfoSessionKey;
    }

}
