/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import org.hibernate.HibernateException;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.ldap.LDAPClient;
import com.topcoder.security.ldap.LDAPClientException;
import com.topcoder.web.common.StringUtils;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.User;
import com.topcoder.web.tc.Constants;

/**
 * <p>
 * This action will activate the user in the system as well as in LDAP.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * <p>
 * <b>Sample Configuration:</b>
 *
 * <pre>
 * &lt;bean id="activateAccountAction" class="com.topcoder.web.reg.actions.registration.ActivateAccountAction"
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
 *
 *         &lt;property name="session"&gt;
 *             &lt;map&gt;
 *                 &lt;entry key="webAuthentication"&gt;
 *                     &lt;ref bean="webAuthentication" /&gt;
 *                 &lt;/entry&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * </pre>
 *
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class ActivateAccountAction extends EmailSendingAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = ActivateAccountAction.class.getName();

    /**
     * <p>
     * Represents the activation code. It is set in the setter. It can be retrieved in the getter. It may have
     * any value. This field will be set during the request phase by the container, and will not change
     * afterwards.
     * </p>
     */
    private String activationCode;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public ActivateAccountAction() {
        // Empty
    }

    /**
     * <p>
     * This action will activate the user in the system as well as in LDAP.
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
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"activationCode"},
            new Object[] {activationCode});

        LDAPClient ldapClient = null;
        try {
            // Get user:
            long userId = StringUtils.getCoderId(activationCode);
            User user = getUserDAO().find(userId);
            ValidationUtility.checkNotNull(user, "user with id[" + userId + "]", TCWebException.class);

            // Set user to being active in persistence
            user.setStatus(new Character(Constants.ACTIVE_STATI[1]));
            // Update user:
            getUserDAO().saveOrUpdate(user);

            // Set user to being active in LDAP
            // Create new LDAP client:
            ldapClient = new LDAPClient();
            // Connect:
            ldapClient.connect();
            // Set user to active:
            ldapClient.activateTopCoderMemberProfile(userId);

            // Build XML template data:
            String templateData = "<DATA><HANDLE>" + user.getHandle() + "</HANDLE></DATA>";

            // Send the email:
            Email primaryEmail = user.getPrimaryEmailAddress();
            ValidationUtility.checkNotNull(primaryEmail, "user's primary email", TCWebException.class);
            sendEmail(primaryEmail.getAddress(), templateData);

            // Audit user action:
            auditAction("activate account", getAuthentication().getActiveUser().getUserName());

            // Log the exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS});

            return SUCCESS;
        } catch (TCWebException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "HibernateException occurs while accessing to database", e));
        } catch (LDAPClientException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "LDAPClientException occurs while executing action", e));
        } finally {
            if (ldapClient != null) {
                // Disconnect:
                try {
                    ldapClient.disconnect();
                } catch (LDAPClientException e) {
                    // just log it
                    LoggingWrapperUtility.logException(getLogger(), signature, e);
                }
            }
        }
    }

    /**
     * <p>
     * Getter method for activationCode, simply return the value of the namesake field.
     * </p>
     *
     * @return the activationCode
     */
    public String getActivationCode() {
        return activationCode;
    }

    /**
     * <p>
     * Setter method for the activationCode, simply set the value to the namesake field.
     * </p>
     *
     * @param activationCode
     *            the activationCode to set
     */
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

}
