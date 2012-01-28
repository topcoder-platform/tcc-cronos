/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.Date;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * This Struts action allows user to accept or reject group invitation. User
 * will be sent with invitation email and that email will contain a hyperlink.
 * This action will be used when user will click on that hyperlink. In case of
 * success, users will be redirected to a JSP corresponding
 * customer-view-invitation-status.html page from the prototype.
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
 *
 * <pre>
 * &lt;bean id="baseAction" abstract="true"
 *   class="com.topcoder.security.groups.actions.GroupInvitationAwareBaseAction"&gt;
 *   &lt;property name="logger" ref="logger"/&gt;
 *   &lt;property name="groupInvitationService" ref="groupInvitationService"/&gt;
 * &lt;/bean&gt;
 * &lt;bean id="AcceptRejectGroupInvitationAction" parent="baseAction"
 *     class="com.topcoder.security.groups.actions.AcceptRejectGroupInvitationAction"&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * Struts configuration:
 *
 * <pre>
 * &lt;action name="acceptRejectGroupInvitationAction" class="AcceptRejectGroupInvitationAction"
 *    method="execute"&gt;
 *    &lt;result name="success"&gt;success.jsp&lt;/result&gt;
 *    &lt;result name="input"&gt;accept_reject_group_invitation.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * </p>
 *
 * @author gevak, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AcceptRejectGroupInvitationAction extends
        GroupInvitationAwareBaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = AcceptRejectGroupInvitationAction.class
            .getName();
    /**
     * Contains ID of invitation, which status is to be updated. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. When execute() is called, XML validations are applied
     * on this variable. Mutable via setter.
     */
    private Long invitationId;

    /**
     * The new invitation status (accepted if true, rejected if false) to be
     * assigned to the specified invitation. It is used in execute() method. It
     * is injected via the setter with no validation, thus can be any value.
     * When execute() is called, XML validations are applied on this variable.
     * Mutable via setter.
     */
    private Boolean accepted;

    /**
     * Empty default constructor.
     */
    public AcceptRejectGroupInvitationAction() {
    }

    /**
     * Updates invitation status of the invitation with ID from "invitationId"
     * input parameter, according to the value of the "accepted" input
     * parameter.
     *
     * @return SUCCESS to indicate that the operation was successful.
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     *
     */
    public String execute() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {
            "invitationId", "accepted" }, new Object[] {invitationId,
                accepted });
        try {
            // Get invitation
            GroupInvitation invitation = getGroupInvitationService()
                    .getInvitation(invitationId);
            ValidationUtility.checkNotNull(invitation, "invitation",
                    SecurityGroupsActionException.class);
            // Set invitation.status according to the value of "accepted" input
            // parameter
            invitation.setStatus(accepted ? InvitationStatus.APPROVAL_PENDING
                    : InvitationStatus.REJECTED);
            // Set invitation.acceptedOrRejectedOn with current time
            invitation.setAcceptedOrRejectedOn(new Date());
            // Update invitation using the groupInvitationService
            getGroupInvitationService().updateInvitation(invitation);
            // Perform audit
            HelperUtility.audit(getAuditService(), null,
                    "group invitation ID = " + invitationId + " status = "
                            + String.valueOf(invitation.getStatus()));
        } catch (EntityNotFoundException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while executing the action", e));
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while executing the action", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature,
                new Object[] {SUCCESS });
        return SUCCESS;
    }

    /**
     * Sets ID of invitations, which status is to be updated.
     *
     * @param invitationId ID of invitations, which status is to be updated.
     *
     */
    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    /**
     * Sets the new invitation status (accepted if true, rejected if false) to
     * be assigned to the specified invitation.
     *
     *
     * @param accepted The new invitation status (accepted if true, rejected if
     *            false) to be assigned to the specified invitation.
     *
     */
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
