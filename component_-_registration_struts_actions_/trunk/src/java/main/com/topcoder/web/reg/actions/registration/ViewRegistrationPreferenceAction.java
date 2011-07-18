/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.WebAuthentication;

/**
 * <p>
 * This action will provide all available preferences as well as the ones already selected by the user
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * <p>
 * <b>Sample Configuration:</b>
 *
 * <pre>
 *     &lt;bean id="viewRegistrationPreferenceAction"
 *      class="com.topcoder.web.reg.actions.registration.ViewRegistrationPreferenceAction"
 *         init-method="checkInitialization"&gt;
 *         &lt;property name="logger" ref="logger" /&gt;
 *         &lt;property name="userDAO" ref="userDAO" /&gt;
 *         &lt;property name="auditDAO" ref="auditDAO" /&gt;
 *         &lt;property name="registrationTypeDAO" ref="registrationTypeDAO" /&gt;
 *         &lt;property name="servletRequest" ref="servletRequest" /&gt;
 *         &lt;property name="authenticationSessionKey" value="webAuthentication" /&gt;
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
public class ViewRegistrationPreferenceAction extends BaseRegistrationAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = ViewRegistrationPreferenceAction.class.getName();

    /**
     * <p>
     * Represents the set of all registration types. It is set in the setter. It can be retrieved in the
     * getter. It may have any value. This field will be set by the execute method.
     * </p>
     */
    private Set<RegistrationType> allRegistrationTypes;

    /**
     * <p>
     * Represents the set of all registration types that the user has selected. It is set in the setter. It
     * can be retrieved in the getter. It may have any value. This field will be set by the execute method.
     * </p>
     */
    private Set<RegistrationType> userRegistrationTypes;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public ViewRegistrationPreferenceAction() {
        // Empty
    }

    /**
     * <p>
     * This action will provide all available preferences as well as the ones already selected by the user.
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
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"allRegistrationTypes",
            "userRegistrationTypes"}, new Object[] {allRegistrationTypes, userRegistrationTypes});

        try {
            // Get authentication object
            WebAuthentication authentication = getAuthentication();
            // Get user:
            com.topcoder.shared.security.User activeUser = authentication.getActiveUser();
            User user = getUserDAO().find(activeUser.getId());
            ValidationUtility.checkNotNull(user, "user with id[" + activeUser.getId() + "]",
                TCWebException.class);

            // Get all registration types:
            allRegistrationTypes = new HashSet<RegistrationType>(getRegistrationTypeDAO()
                .getRegistrationTypes());
            // Get user registration types:
            userRegistrationTypes = user.getRegistrationTypes();

            // Audit user action:
            auditAction("view registration preference", activeUser.getUserName());

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
     * Getter method for allRegistrationTypes, simply return the value of the namesake field.
     * </p>
     *
     * @return the allRegistrationTypes
     */
    public Set<RegistrationType> getAllRegistrationTypes() {
        return allRegistrationTypes;
    }

    /**
     * <p>
     * Setter method for the allRegistrationTypes, simply set the value to the namesake field.
     * </p>
     *
     * @param allRegistrationTypes
     *            the allRegistrationTypes to set
     */
    public void setAllRegistrationTypes(Set<RegistrationType> allRegistrationTypes) {
        this.allRegistrationTypes = allRegistrationTypes;
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

}
