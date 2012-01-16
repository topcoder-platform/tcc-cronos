/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * CreateGroupAction is used to perform the create group function. It will log events and errors. It will audit the
 * methods.
 * </p>
 * <b>Configuration</b>
 * <p>
 *
 * <pre>
 *   &lt;bean id=&quot;createGroupAction&quot;
 *   class=&quot;com.topcoder.security.groups.actions.CreateGroupAction&quot;&gt;
 *         &lt;property name=&quot;groupService&quot; ref=&quot;groupService&quot;/&gt;
 *         &lt;property name=&quot;groupSessionKey&quot; value=&quot;groupSessionKey&quot;/&gt;
 *         &lt;property name=&quot;clientService&quot; ref=&quot;clientService&quot;/&gt;
 *         &lt;property name=&quot;customerAdministratorService&quot; ref=&quot;customerAdministratorService&quot;/&gt;
 *         &lt;property name=&quot;directProjectService&quot; ref=&quot;directProjectService&quot;/&gt;
 *         &lt;property name=&quot;groupInvitationService&quot; ref=&quot;groupInvitationService&quot;/&gt;
 *         &lt;property name=&quot;acceptRejectUrlBase&quot; value=&quot;acceptRejectUrlBase&quot;/&gt;
 *         &lt;property name=&quot;registrationUrl&quot; value=&quot;registrationUrl&quot;/&gt;
 *         &lt;property name=&quot;billingAccountService&quot; ref=&quot;billingAccountService&quot;/&gt;
 *         &lt;property name=&quot;logger&quot; ref=&quot;logger&quot;/&gt;
 *         &lt;property name=&quot;auditService&quot; ref=&quot;auditService&quot;/&gt;
 *         &lt;property name=&quot;authorizationService&quot; ref=&quot;authorizationService&quot;/&gt;
 *         &lt;!-- other properties here --&gt;
 *   &lt;/bean&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe. However the struts framework will guarantee
 * that it will be used in the thread safe model.
 * </p>
 *
 * @author woodjhon, hanshuai
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CreateGroupAction extends CreateUpdateGroupAction {

    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = CreateGroupAction.class.getName();

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public CreateGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Create group method. It will back the back end service to create the group.
     * </p>
     *
     * @return The execution result
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     */
    public String createGroup() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".createGroup()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // audit
            GroupAuditRecord record = new GroupAuditRecord();
            record.setHandle(Helper.getUserName());
            record.setPreviousValue(null);
            record.setNewValue(getGroupId() + getGroup().getName());
            getAuditService().add(record);

            getGroupService().add(getGroup());
            Helper.sendInvitations(this, getGroup().getGroupMembers());
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
            return SUCCESS;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs while sending invitation", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while creating group", e));
        }
    }

    /**
     * <p>
     * Performs complex validation that can't be easily done with declarative XML validation.
     * </p>
     *
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    public synchronized void validate() {
        final String signature = CLASS_NAME + ".validate()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            Helper.validate(this, getGroup());
            Helper.validate(this, getGroupMembers());
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (SecurityGroupsActionValidationException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
    }
}
