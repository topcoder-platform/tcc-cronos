/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * This Struts action allows user to view his invitations. This action will be
 * used by JSP corresponding to customer-view-invitation-status.html from the
 * prototype.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is technically mutable and not thread-safe,
 * however it's expected to be used as Spring bean and thus it will be immutable
 * after initialization, so, since it inherits from thread-safe (under same
 * conditions) base class and utilizes only the thread-safe tools, it's
 * thread-safe.
 * </p>
 * <p>
 * <b>Sample Usage:</b>
 *
 * Spring configuration:
 * <pre>
 * &lt;bean id="baseAction" abstract="true"
 *   class="com.topcoder.security.groups.actions.GroupInvitationSearchBaseAction"&gt;
 *   &lt;property name="logger" ref="logger"/&gt;
 * &lt;/bean&gt;
 * &lt;bean id="ViewInvitationStatusAction" parent="baseAction"
 *  class="com.topcoder.security.groups.actions.ViewInvitationStatusAction"&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * Struts configuration:
 *
 * <pre>
 * &lt;action name="viewInvitationStatusAction" class="ViewInvitationStatusAction"
 *    method="execute"&gt;
 *    &lt;result name="success"&gt;success.jsp&lt;/result&gt;
 *    &lt;result name="input"&gt;view_invitation_status.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * </p>
 * @author gevak, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewInvitationStatusAction extends GroupInvitationSearchBaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = ViewInvitationStatusAction.class
            .getName();
    /**
     * The maximum length of a string.
     */
    private static final int MAX_LENGTH = 45;

    /**
     * Empty default constructor.
     */
    public ViewInvitationStatusAction() {
    }

    /**
     * Searches for user's invitations, supporting pagination.
     *
     * @return SUCCESS to indicate that the operation was successful.
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     *
     */
    public String execute() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // Get current user id
            long userId = getCurrentUserId();
            // Filter invitations of just the current user
            getCriteria().setOwnedUserId(userId);
            // Search invitations using the input fields and the
            // groupInvitationService, put result into the "invitations" output
            // field
            PagedResult<GroupInvitation> pagedResult = getGroupInvitationService()
                    .search(getCriteria(), 0, getPageSize(), getPage());
            setInvitations(pagedResult);
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while executing the action", e));
        }
        LoggingWrapperUtility.logExit(getLogger(), signature,
                new Object[] {SUCCESS });
        return SUCCESS;
    }

    /**
     * Performs complex validation that can't be easily done with declarative
     * XML validation.
     *
     */
    public void validate() {
        final String signature = CLASS_NAME + ".validate()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        super.validate();
        InvitationSearchCriteria criteria = getCriteria();
        if (criteria.getGroupName() != null) {
            if (criteria.getGroupName().trim().length() == 0
                    || criteria.getGroupName().length() > MAX_LENGTH) {
                addFieldError("criteria.groupName",
                        "criteria.groupName can't be empty or length exceed " + MAX_LENGTH + ".");
            }
        }
        if (criteria.getAcceptedDateFrom() != null
                && criteria.getAcceptedDateTo() != null
                && criteria.getAcceptedDateTo().before(
                        criteria.getAcceptedDateFrom())) {
            addFieldError("criteria.acceptedDate",
                    "acceptedDateTo must not before the acceptedDateFrom");
        }
        if (criteria.getSentDateFrom() != null
                && criteria.getSentDateTo() != null
                && criteria.getSentDateTo().before(criteria.getSentDateFrom())) {
            addFieldError("criteria.sendDate",
                    "sendDateTo must not before the sendDateFrom");
        }

        List<GroupPermissionType> permissions = criteria.getPermissions();
        if (permissions == null) {
            addFieldError("criteria.permissions",
                    "criteria.permissions can't be null.");
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
            // if permissions is null, no need to validate it
            return;
        }
        for (GroupPermissionType permission : permissions) {
            if (permission == null) {
                addFieldError("criteria.permissions",
                        "criteria.permissions can't contain null.");
            }
        }
        Set<GroupPermissionType> set = new HashSet<GroupPermissionType>(
                permissions);
        if (set.size() != permissions.size()) {
            addFieldError("criteria.permissions",
                    "criteria.permissions can't contain equals element.");
        }


        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }
}
