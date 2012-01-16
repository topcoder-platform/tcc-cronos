/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * UpdateGroupAction is used to perform the update group function. It will log events and errors. It will audit the
 * methods.
 * </p>
 * <b>Configuration</b>
 * <p>
 *
 * <pre>
 *   &lt;bean id=&quot;updateGroupAction&quot;
 *         class=&quot;com.topcoder.security.groups.actions.UpdateGroupAction&quot;&gt;
 *         &lt;property name=&quot;groupService&quot; ref=&quot;groupService&quot;/&gt;
 *         &lt;property name=&quot;groupSessionKey&quot; value=&quot;groupSessionKey&quot;/&gt;
 *         &lt;property name=&quot;groupMemberService&quot; ref=&quot;groupMemberService&quot;/&gt;
 *         &lt;property name=&quot;oldGroupMembersSessionKey&quot; value=&quot;oldGroupMemebersSessionKey&quot;/&gt;
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
public class UpdateGroupAction extends CreateUpdateGroupAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = UpdateGroupAction.class.getName();

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public UpdateGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Update group method. It will call the back end service to update the group.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The execution result
     */
    @SuppressWarnings("unchecked")
    public String updateGroup() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".updateGroup()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // Updates group in the session using groupService.
            Group group = (Group) getSession().get(getGroupSessionKey());

            // audit
            GroupAuditRecord record = new GroupAuditRecord();
            record.setHandle(Helper.getUserName());
            record.setPreviousValue(group.getId() + group.getName());
            record.setNewValue(getGroupId() + getGroup().getName());

            getGroupService().update(group);
            for (GroupMember groupMember : group.getGroupMembers()) {
                getGroupMemberService().update(groupMember);
            }
            // Send invitations to all new group members
            List<GroupMember> oldMembers = (List<GroupMember>) getSession().get(getOldGroupMembersSessionKey());
            List<GroupMember> newMembers = new ArrayList<GroupMember>(group.getGroupMembers());
            newMembers.removeAll(oldMembers);
            Helper.sendInvitations(this, newMembers);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
            return SUCCESS;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs when sending invitation.", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while updating the group", e));
        }
    }

    /**
     * <p>
     * Performs complex validation that can't be easily done with declarative XML validation. throws:
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

    /**
     * <p>
     * Enter group details method. It will put the group in the session.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The execution result
     */
    @Override
    public synchronized String enterGroupDetails() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".enterGroupDetails()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            Group group = getGroupService().get(getGroupId());
            getSession().put(getGroupSessionKey(), group);
            // note that it will replace the old one if oldGroupMemebersSessionKey exists in the session.
            getSession().put(getOldGroupMembersSessionKey(), group.getGroupMembers());
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
            return SUCCESS;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs when updating group", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while updating the group", e));
        }
    }
}
