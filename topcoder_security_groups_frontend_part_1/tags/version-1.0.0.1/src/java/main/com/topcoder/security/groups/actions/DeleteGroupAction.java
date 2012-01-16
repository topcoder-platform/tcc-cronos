/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * DeleteGroupAction is used to perform the delete group function. It will log events and errors. It will audit the
 * methods.
 * </p>
 *
 * <b>Configuration</b>
 * <p>
 *
 * <pre>
 *   &lt;bean id="deleteGroupAction" class="com.topcoder.security.groups.actions.DeleteGroupAction">
 *         &lt;property name="groupService" ref="groupService"/>
 *         &lt;property name="logger" ref="logger"/>
 *         &lt;property name="auditService" ref="auditService"/>
 *         &lt;property name="authorizationService" ref="authorizationService"/>
 *
 *         &lt;!-- other properties here -->
 *   &lt;/bean>
 *
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe. However the struts framework will guarantee
 * that it will be used in the thread safe model.
 * </p>
 *
 * @author woodjhon, hanshuai
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DeleteGroupAction extends BaseAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = DeleteGroupAction.class.getName();

    /**
     * Purpose: groupId is used to represents the group id. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Positive after set
     */
    private long groupId;

    /**
     * Purpose: groupService is used to represents the group service. Usage: It's injected. Legal Values: Not null
     * after set
     */
    private GroupService groupService;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public DeleteGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required(with config stereotype) fields are injected.
     * </p>
     *
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
     * Execute method. It will delete the group by id.
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
            // audit
            GroupAuditRecord record = new GroupAuditRecord();
            record.setHandle(Helper.getUserName());
            String name = groupService.get(getGroupId()).getName();
            record.setPreviousValue(getGroupId() + name);
            record.setNewValue(null);
            getAuditService().add(record);

            groupService.delete(groupId);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
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
