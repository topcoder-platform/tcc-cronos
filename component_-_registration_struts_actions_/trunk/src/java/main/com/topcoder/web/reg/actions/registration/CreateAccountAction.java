/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.hibernate.HibernateException;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.GroupPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.SecurityHelper;
import com.topcoder.web.common.SessionInfo;
import com.topcoder.web.common.StringUtils;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.dao.SecurityGroupDAO;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.reg.RegistrationActionConfigurationException;
import com.topcoder.web.tc.Constants;

/**
 * <p>
 * This action will create the user account. It will create the user in persistence and the security context,
 * where it adds the user to the appropriate groups. It also generates an activation code and sends an
 * activation email to the registrant to finish the registration.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * <p>
 * <b>Sample Configuration:</b>
 *
 * <pre>
 *     &lt;bean id="createAccountAction"
 *      class="com.topcoder.web.reg.actions.registration.CreateAccountAction"
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
 *         &lt;property name="principalMgr" ref="principalMgrRemote" /&gt;
 *         &lt;property name="securityGroupDAO" ref="securityGroupDAO" /&gt;
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
public class CreateAccountAction extends EmailSendingAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = CreateAccountAction.class.getName();

    /**
     * <p>
     * This is the PrincipalMgrRemote instance used to manage the user security info. It is set in the setter.
     * It can be retrieved in the getter. It may have any value. This field will be injected by the container
     * (expected to be done only once), and is not expected to change afterward.
     * </p>
     */
    private PrincipalMgrRemote principalMgr;

    /**
     * <p>
     * This is the SecurityGroupDAO instance used to get security groups. It is set in the setter. It can be
     * retrieved in the getter. It may have any value. This field will be injected by the container (expected
     * to be done only once), and is not expected to change afterward.
     * </p>
     */
    private SecurityGroupDAO securityGroupDAO;

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
     * Represents the User that is registering. It is set in the setter. It can be retrieved in the getter. It
     * may have any value. This field will be set during the request phase by the container, and will not
     * change afterwards.
     * </p>
     */
    private User user;

    /**
     * <p>
     * Represents the CAPTHCA verification code as typed in by the user. It is set in the setter. It can be
     * retrieved in the getter. It may have any value. This field will be set during the request phase by the
     * container, and will not change afterwards.
     * </p>
     */
    private String verificationCode;

    /**
     * <p>
     * Represents the handle of the user that may have referred the registrant to TopCoder. It is set in the
     * setter. It can be retrieved in the getter. It may have any value. This field will be set during the
     * request phase by the container, and will not change afterwards.
     * </p>
     */
    private String referrerHandle;

    /**
     * <p>
     * Represents the password field. It is set in the setter. It can be retrieved in the getter. It may have
     * any value. This field will be set during the request phase by the container, and will not change
     * afterwards.
     * </p>
     */
    private String password;

    /**
     * <p>
     * Represents the password confirmation, used to make sure the password was typed correctly. It is set in
     * the setter. It can be retrieved in the getter. It may have any value. This field will be set during the
     * request phase by the container, and will not change afterwards.
     * </p>
     */
    private String confirmPassword;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public CreateAccountAction() {
        // Empty
    }

    /**
     * <p>
     * This method checks that all required values have been injected by the system right after construction
     * and injection. This is used to obviate the need to check these values each time in the execute method.
     * </p>
     *
     * @throws RegistrationActionConfigurationException
     *             If any required value has not been injected into this class
     */
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();
        ValidationUtility.checkNotNull(principalMgr, "principalMgr",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNull(securityGroupDAO, "securityGroupDAO",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmpty(userRoles, "userRoles",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNullElements(userRoles, "userRoles",
            RegistrationActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(sessionInfoSessionKey, "sessionInfoSessionKey",
            RegistrationActionConfigurationException.class);
    }

    /**
     * <p>
     * validates input.
     * </p>
     */
    @Override
    public void validate() {
        // Verify verification code
        // Get existing code in session:
        Object word = getSession().get(Constants.CAPTCHA_WORD);
        // If this.verificationCode != word
        if (!verificationCode.equals(word)) {
            addFieldError("verificationCode", getText("verificationCode.invalid"));
        }

        // Check that the provided handle is unique
        // Get a user by this handle:
        User existingUser = getUserDAO().find(user.getHandle(), true);
        if (existingUser != null) {
            addFieldError("user.handle", getText("handle.duplicate"));
        }

        // Check that the provided email is unique
        // Get a user by this handle:
        Set<Email> emails = user.getEmailAddresses();
        if (emails != null && !emails.isEmpty()) {
            existingUser = getUserDAO().find(new ArrayList<Email>(emails).get(0).getAddress());
            if (existingUser != null) {
                addFieldError("user.handle", getText("email.duplicate"));
            }
        }

        // Check that the provided referrerHandle exists
        if (referrerHandle != null) {
            // Get a user by this handle:
            existingUser = getUserDAO().find(referrerHandle, true);
            if (existingUser == null) {
                addFieldError("referrerHandle", getText("handle.notfound"));
            }
        }

        // Check that the provided confirmPassword is the same as the password
        if (!confirmPassword.equals(password)) {
            addFieldError("referrerHandle", getText("password.match.incorrect"));
        }
    }

    /**
     * <p>
     * This action will create the user account. It will create the user in persistence and the security
     * context, where it adds the user to the appropriate groups. It also generates an activation code and
     * sends an activation email to the registrant to finish the registration.
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
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"user", "verificationCode",
            "referrerHandle", "password", "confirmPassword"}, new Object[] {user, verificationCode,
            referrerHandle, password, confirmPassword});

        try {
            // Set user email as being the primary email:
            Set<Email> emails = user.getEmailAddresses();
            ValidationUtility.checkNotNullNorEmpty(emails, "user's emails", TCWebException.class);
            new ArrayList<Email>(emails).get(0).setPrimary(Boolean.TRUE);

            // If referrerHandle is provided, add it to the user:
            if (referrerHandle != null) {
                // Get referral user:
                User referralUser = getUserDAO().find(referrerHandle, true);

                // Set ID to user:
                user.getUserProfile().getCoder().getCoderReferral().getReferral()
                    .setId(referralUser.getId().intValue());
            }

            // Create user:
            getUserDAO().saveOrUpdate(user);

            // Add security for the user
            // Create new TCSubject:
            TCSubject subject = new TCSubject(user.getId());
            // Create new UserPrincipal:
            UserPrincipal principal = principalMgr.createUser(user.getId(), user.getHandle(), password,
                subject, DBMS.JTS_OLTP_DATASOURCE_NAME);
            // Get security groups:
            List<SecurityGroup> securityGroups = securityGroupDAO.getSecurityGroups(userRoles);
            // For each securityGroup:SecurityGroup in security groups
            for (SecurityGroup securityGroup : securityGroups) {
                // Add user to group:
                principalMgr.addUserToGroup(
                    principalMgr.getGroup(securityGroup.getId(), DBMS.JTS_OLTP_DATASOURCE_NAME), principal,
                    subject, DBMS.JTS_OLTP_DATASOURCE_NAME);

            }
            // Add user to the anonymous and users groups:
            // Get existing groups:
            @SuppressWarnings("unchecked")
            Collection<GroupPrincipal> groups = principalMgr
                .getGroups(subject, DBMS.JTS_OLTP_DATASOURCE_NAME);
            GroupPrincipal anonGroup = findGroup(groups, "Anonymous");
            GroupPrincipal userGroup = findGroup(groups, "Users");
            principalMgr.addUserToGroup(anonGroup, principal, subject, DBMS.JTS_OLTP_DATASOURCE_NAME);
            principalMgr.addUserToGroup(userGroup, principal, subject, DBMS.JTS_OLTP_DATASOURCE_NAME);

            // refresh the cached object:
            SecurityHelper.getUserSubject(user.getId(), true, DBMS.JTS_OLTP_DATASOURCE_NAME);

            // Generate activation code:
            String activationCode = StringUtils.getActivationCode(user.getId());
            // Set activation code in user:
            user.setActivationCode(activationCode);
            // Update user:
            getUserDAO().saveOrUpdate(user);

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

            // Remember the user:
            // Get authentication object:
            BasicAuthentication authentication = (BasicAuthentication) getAuthentication();
            // Add cookie to remember the user:
            authentication.setCookie(user.getId(), true);

            // Audit user action:
            auditAction("create account", authentication.getActiveUser().getUserName());

            // Log the exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS});

            return SUCCESS;
        } catch (TCWebException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "HibernateException occurs while accessing to database", e));
        } catch (RemoteException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "RemoteException occurs while accessing with principalMgr", e));
        } catch (GeneralSecurityException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "GeneralSecurityException occurs while accessing with principalMgr", e));
        } catch (NamingException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "NamingException occurs while getting user subject", e));
        } catch (CreateException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "CreateException occurs while getting user subject", e));
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "Exception occurs while setting cookie", e));
        }
    }

    /**
     * <p>
     * Find the group with name.
     * </p>
     *
     * @param groups
     *            the groups
     * @param name
     *            the group name
     * @return the found group
     * @throws TCWebException
     *             if group do not exist
     */
    private static GroupPrincipal findGroup(Collection<GroupPrincipal> groups, String name)
        throws TCWebException {
        for (GroupPrincipal group : groups) {
            if (name.equals(group.getName())) {
                return group;
            }
        }
        throw new TCWebException("Can not find group with name[" + name + "]");
    }

    /**
     * <p>
     * Getter method for principalMgr, simply return the value of the namesake field.
     * </p>
     *
     * @return the principalMgr
     */
    public PrincipalMgrRemote getPrincipalMgr() {
        return principalMgr;
    }

    /**
     * <p>
     * Setter method for the principalMgr, simply set the value to the namesake field.
     * </p>
     *
     * @param principalMgr
     *            the principalMgr to set
     */
    public void setPrincipalMgr(PrincipalMgrRemote principalMgr) {
        this.principalMgr = principalMgr;
    }

    /**
     * <p>
     * Getter method for securityGroupDAO, simply return the value of the namesake field.
     * </p>
     *
     * @return the securityGroupDAO
     */
    public SecurityGroupDAO getSecurityGroupDAO() {
        return securityGroupDAO;
    }

    /**
     * <p>
     * Setter method for the securityGroupDAO, simply set the value to the namesake field.
     * </p>
     *
     * @param securityGroupDAO
     *            the securityGroupDAO to set
     */
    public void setSecurityGroupDAO(SecurityGroupDAO securityGroupDAO) {
        this.securityGroupDAO = securityGroupDAO;
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

    /**
     * <p>
     * Getter method for user, simply return the value of the namesake field.
     * </p>
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * <p>
     * Setter method for the user, simply set the value to the namesake field.
     * </p>
     *
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * <p>
     * Getter method for verificationCode, simply return the value of the namesake field.
     * </p>
     *
     * @return the verificationCode
     */
    public String getVerificationCode() {
        return verificationCode;
    }

    /**
     * <p>
     * Setter method for the verificationCode, simply set the value to the namesake field.
     * </p>
     *
     * @param verificationCode
     *            the verificationCode to set
     */
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    /**
     * <p>
     * Getter method for referrerHandle, simply return the value of the namesake field.
     * </p>
     *
     * @return the referrerHandle
     */
    public String getReferrerHandle() {
        return referrerHandle;
    }

    /**
     * <p>
     * Setter method for the referrerHandle, simply set the value to the namesake field.
     * </p>
     *
     * @param referrerHandle
     *            the referrerHandle to set
     */
    public void setReferrerHandle(String referrerHandle) {
        this.referrerHandle = referrerHandle;
    }

    /**
     * <p>
     * Getter method for password, simply return the value of the namesake field.
     * </p>
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>
     * Setter method for the password, simply set the value to the namesake field.
     * </p>
     *
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>
     * Getter method for confirmPassword, simply return the value of the namesake field.
     * </p>
     *
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * <p>
     * Setter method for the confirmPassword, simply set the value to the namesake field.
     * </p>
     *
     * @param confirmPassword
     *            the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
