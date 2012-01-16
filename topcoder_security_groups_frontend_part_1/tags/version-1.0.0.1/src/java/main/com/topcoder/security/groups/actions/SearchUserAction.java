/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * SearchUserAction is used to perform the search user functionIt will log events and errors. It will audit the
 * methods.
 * </p>
 * <b>Configuration</b>
 * <p>
 * <pre>
 *   &lt;bean id="searchUserAction"
 *         class="com.topcoder.security.groups.actions.SearchUserAction">
 *         &lt;property name="userService" ref="userService"/>
 *         &lt;property name="logger" ref="logger"/>
 *         &lt;property name="auditService" ref="auditService"/>
 *         &lt;property name="authorizationService" ref="authorizationService"/>
 *
 *         &lt;!-- other properties here -->
 *   &lt;/bean>
 *
 * </pre>
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe.
 * However the struts framework will guarantee that it will be used in the thread safe model.
 * </p>
 * @author woodjhon, hanshuai
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SearchUserAction extends BaseAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = SearchUserAction.class.getName();

    /**
     * Purpose: handle is used to represents the handle. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Not null and not empty after set.
     */
    private String handle;

    /**
     * Purpose: users is used to represents the users. Usage: It's set by the action methods, and consumed by the
     * front end page. Legal Values: Not null and not empty after set. The list should not contain null value.
     */
    private List<UserDTO> users;

    /**
     * Purpose: userService is used to represents the user service. It's required. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private UserService userService;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public SearchUserAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required fields are injected.
     * </p>
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if any of these fields is null:<br>
     *             auditService, authorizationService, userService
     */
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(userService, "userService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Execute method. It will search the users by the user handle.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The execution result
     */
    public String execute() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            users = userService.search(handle);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS});
            return SUCCESS;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs while deleting the group", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while deleting the group", e));
        }
    }

    /**
     * <p>
     * Get handle.
     * </p>
     *
     * @return the handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * <p>
     * Set handle.
     * </p>
     *
     * @param handle
     *            the handle to set.
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * <p>
     * Get users.
     * </p>
     *
     * @return the users
     */
    public List<UserDTO> getUsers() {
        return users;
    }

    /**
     * <p>
     * Set users.
     * </p>
     *
     * @param users
     *            the users to set.
     */
    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    /**
     * <p>
     * Get user service.
     * </p>
     *
     * @return the user service
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * <p>
     * Set user service.
     * </p>
     *
     * @param userService
     *            the user service to set.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
