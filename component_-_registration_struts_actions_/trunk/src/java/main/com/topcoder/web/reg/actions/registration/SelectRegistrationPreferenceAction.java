/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.dao.SecurityGroupDAO;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.RegistrationActionConfigurationException;

/**
 * <p>
 * This action will update the user's registration types based on input
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * <p>
 * <b>Sample Configuration:</b>
 *
 * <pre>
 *     &lt;bean id="selectRegistrationPreferenceAction"
 *      class="com.topcoder.web.reg.actions.registration.SelectRegistrationPreferenceAction"
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
 *
 *         &lt;property name="userRegistrationTypes"&gt;
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
public class SelectRegistrationPreferenceAction extends EmailSendingAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = SelectRegistrationPreferenceAction.class.getName();

    /**
     * <p>
     * This is the SecurityGroupDAO instance used to get security groups.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value.
     * </p>
     * <p>
     * This field will be injected by the container (expected to be done only once), and is not expected to
     * change afterward.
     * </p>
     */
    private SecurityGroupDAO securityGroupDAO;

    /**
     * <p>
     * Represents the set of all registration types that the user has selected. It is set in the setter. It
     * can be retrieved in the getter. It may have any value. This field will be set during the request phase
     * by the container, and will not change afterwards
     * </p>
     */
    private Set<RegistrationType> userRegistrationTypes;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public SelectRegistrationPreferenceAction() {
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
    protected void checkInitialization() {
        super.checkInitialization();
        ValidationUtility.checkNotNull(securityGroupDAO, "securityGroupDAO",
            RegistrationActionConfigurationException.class);
    }

    /**
     * <p>
     * validates input.
     * </p>
     */
    @Override
    public void validate() {
        // Get all registration types:
        Set<RegistrationType> allRegistrationTypes = new HashSet<RegistrationType>(getRegistrationTypeDAO()
            .getRegistrationTypes());
        // For each registrationType:RegistrationType in userRegistrationTypes
        for (RegistrationType registrationType : userRegistrationTypes) {
            // If registrationType is not in allRegistrationTypes
            if (!allRegistrationTypes.contains(registrationType)) {
                addFieldError(registrationType.getName(), getText("registration.type.illegal"));
                break;
            }

        }
    }

    /**
     * <p>
     * This action will update the user's registration types based on input.
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
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"userRegistrationTypes"},
            new Object[] {userRegistrationTypes});

        try {

            // Get authentication object:
            WebAuthentication authentication = getAuthentication();
            // Get user:
            com.topcoder.shared.security.User activeUser = authentication.getActiveUser();
            User user = getUserDAO().find(activeUser.getId());
            ValidationUtility.checkNotNull(user, "user with id[" + activeUser.getId() + "]",
                TCWebException.class);

            // Add security for the user
            addUserToGroups(securityGroupDAO, user, userRegistrationTypes);

            // Update user:
            getUserDAO().saveOrUpdate(user);

            // Build XML template data:
            StringBuilder sb = new StringBuilder();
            sb.append("<DATA>");
            sb.append("<HANDLE>").append(user.getHandle()).append("</HANDLE>");
            for (RegistrationType type : user.getRegistrationTypes()) {
                sb.append("<USER_REGISTRATION_TYPE>").append(type.getName())
                    .append("</USER_REGISTRATION_TYPE>");
            }
            sb.append("</DATA>");

            String templateData = sb.toString();

            // Send the email:
            Email primaryEmail = user.getPrimaryEmailAddress();
            ValidationUtility.checkNotNull(primaryEmail, "user's primary email", TCWebException.class);
            sendEmail(primaryEmail.getAddress(), templateData);

            // Audit user action:
            auditAction("select registration preference", activeUser.getUserName());

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
     * Getter method for userRegistrationTypes, simply return the value of the namesake field.
     * </p>
     *
     * @return the userRegistrationTypes
     */
    public Set<RegistrationType> getUserRegistrationTypes() {
        return userRegistrationTypes;
    }

    /**
     * <p>
     * Setter method for the userRegistrationTypes, simply set the value to the namesake field.
     * </p>
     *
     * @param userRegistrationTypes
     *            the userRegistrationTypes to set
     */
    public void setUserRegistrationTypes(Set<RegistrationType> userRegistrationTypes) {
        this.userRegistrationTypes = userRegistrationTypes;
    }

    /**
     * <p>
     * Add the user to the security groups with given registration types. The user is also added to anonymous
     * and users groups by default.
     * </p>
     *
     * @param securityGroupDAO
     *            the SecurityGroupDAO used to retrieve security groups
     * @param user
     *            the user
     * @param userRegistrationTypes
     *            the user's registration types
     * @throws TCWebException
     *             if anonymous or users group cannot be found
     */
    private static void addUserToGroups(SecurityGroupDAO securityGroupDAO, User user,
                    Set<RegistrationType> userRegistrationTypes) throws TCWebException {
        // Set registrationTypes to user
        List<SecurityGroup> groups = securityGroupDAO.getSecurityGroups(userRegistrationTypes);
        Set<UserGroup> userGroups = new HashSet<UserGroup>();
        for (SecurityGroup group : groups) {
            userGroups.add(createUserGroup(user, group));
        }

        // Add user to "Anonymous" and "Users" groups
        groups = securityGroupDAO.findAll();

        boolean anonGroupFound = false;
        boolean usersGroupFound = false;
        for (SecurityGroup g : groups) {
            String desc = g.getDescription();
            if (desc.equals("Anonymous")) {
                userGroups.add(createUserGroup(user, g));
                anonGroupFound = true;
            } else if (desc.equals("Users")) {
                userGroups.add(createUserGroup(user, g));
                usersGroupFound = true;
            }
        }

        if (!anonGroupFound) {
            throw new TCWebException("Anonymous group cannot be found.");
        }
        if (!usersGroupFound) {
            throw new TCWebException("Users group cannot be found.");
        }
        user.setSecurityGroups(userGroups);
    }

    /**
     * <p>
     * Creates a UserGroup instance with given user and security group.
     * </p>
     *
     * @param user
     *            the user
     * @param group
     *            the security group
     * @return the created UserGroup instance
     */
    private static UserGroup createUserGroup(User user, SecurityGroup group) {
        UserGroup userGroup = new UserGroup();
        userGroup.setSecurityGroup(group);
        userGroup.setUser(user);
        userGroup.setSecurityStatusId(SecurityGroup.ACTIVE);
        return userGroup;
    }

}
