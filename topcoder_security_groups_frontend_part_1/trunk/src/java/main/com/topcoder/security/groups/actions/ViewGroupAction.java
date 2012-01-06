/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * ViewGroupAction is used to perform the view group function. It will log events and errors. It will audit the
 * methods.
 * </p>
 * <b>Configuration</b>
 * <p>
 * <pre>
 *   &lt;bean id="viewGroupAction"
 *         class="com.topcoder.security.groups.actions.ViewGroupAction">
 *         &lt;property name="groupService" ref="groupService"/>
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
public class ViewGroupAction extends BaseAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = ViewGroupAction.class.getName();

    /**
     * Purpose: groupId is used to represents the group id. Usage: It's injected. Legal Values: Positive after set
     */
    private long groupId;

    /**
     * Purpose: group is used to represents the group. Usage: It's set by the action methods, and consumed by the
     * front end page. Legal Values: Not null after set
     */
    private Group group;

    /**
     * Purpose: groupService is used to represents the group service. It's required. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private GroupService groupService;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public ViewGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required(with config stereotype) fields are injected.
     * </p>
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if any of these fields is null:<br>
     *             auditService, authorizationService, groupService
     */
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupService, "groupService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Execute method. It will get the group by id.
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
            group = groupService.get(groupId);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS});
            return SUCCESS;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs while viewing group", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while viewing group", e));
        }
    }

    /**
     * <p>
     * Get group id.
     * </p>
     *
     * @return the group id
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * <p>
     * Set group id.
     * </p>
     *
     * @param groupId
     *            the group id to set.
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    /**
     * <p>
     * Get group.
     * </p>
     *
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * <p>
     * Set group.
     * </p>
     *
     * @param group
     *            the group to set.
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * <p>
     * Get group service.
     * </p>
     *
     * @return the group service
     */
    public GroupService getGroupService() {
        return groupService;
    }

    /**
     * <p>
     * Set group service.
     * </p>
     *
     * @param groupService
     *            the group service to set.
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}
