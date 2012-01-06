/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.Date;
import java.util.List;

import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * Helper class for this component.
 *
 * @author woodjhon, hanshuai
 */
final class Helper {

    /**
     * Represent the invitation url.
     */
    private static final String INVITATION_URL = "/?invitationId=";

    /**
     * Represent the accept true url.
     */
    private static final String ACCEPTED_TURE_URL = "&accepted=true";

    /**
     * Represent the accept false url.
     */
    private static final String ACCEPTED_FALSE_URL = "&accepted=false";

    /**
     * Represent the max length of string.
     */
    private static final int MAXLENGTH = 45;

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private Helper() {
        // Empty
    }

    /**
     * Get the current user name.
     *
     * @return the user name.
     */
    public static String getUserName() {
        TCSubject subject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        TCPrincipal principal = (TCPrincipal) subject.getPrincipals().iterator().next();
        return principal.getName();
    }

    /**
     * <p>
     * send invitations to the members.
     * </p>
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to send invitations.
     * @param members
     *            the members
     * @throws SecurityGroupException
     *             send invitation fails.
     */
    public static void sendInvitations(CreateUpdateGroupAction instance, List<GroupMember> members)
        throws SecurityGroupException {
        GroupInvitationService groupInvitationService = instance.getGroupInvitationService();
        String prefix = instance.getAcceptRejectUrlBase() + INVITATION_URL;
        for (GroupMember member : members) {
            // create invitation
            GroupInvitation invitation = new GroupInvitation();
            invitation.setGroupMember(member);
            invitation.setStatus(InvitationStatus.PENDING);
            invitation.setSentOn(new Date());
            // save invitation
            groupInvitationService.addInvitation(invitation);
            // construct URLs
            String urlPrefix = prefix + invitation.getId();
            String acceptUrl = urlPrefix + ACCEPTED_TURE_URL;
            String rejectUrl = urlPrefix + ACCEPTED_FALSE_URL;
            // send invitation

            groupInvitationService.sendInvitation(invitation, instance.getRegistrationUrl(), acceptUrl, rejectUrl);
        }
    }

    /**
     * Check the string has max 45 chars and not empty.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to check.
     * @param string
     *            the string
     * @param fieldName
     *            the field name
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    private static void check(CreateUpdateGroupAction instance, String string, String fieldName) {
        if (string != null) {
            string = string.trim();
        }
        if (string == null || string.length() == 0) {
            instance.addFieldError(fieldName, instance.getText(fieldName + ".missing"));
            throw new SecurityGroupsActionValidationException(fieldName + " is required");
        }
        if (string.length() > MAXLENGTH) {
            instance.addFieldError(fieldName, instance.getText(fieldName + ".length"));
            throw new SecurityGroupsActionValidationException(fieldName + "'s length is bigger than 45");
        }
    }

    /**
     * Check the object is null.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to check.
     * @param object
     *            the object
     * @param fieldName
     *            the field name
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    private static void checkNull(CreateUpdateGroupAction instance, Object object, String fieldName) {
        if (object == null) {
            instance.addFieldError(fieldName, instance.getText(fieldName + ".missing"));
            throw new SecurityGroupsActionValidationException(fieldName + " is required");
        }
    }

    /**
     * Check the list is null or empty.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to check.
     * @param ls
     *            the list
     * @param fieldName
     *            the field name
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    private static void checkNullNorEmpty(CreateUpdateGroupAction instance, List<?> ls, String fieldName) {
        checkNull(instance, ls, fieldName);
        if (ls.size() == 0) {
            instance.addFieldError(fieldName, instance.getText(fieldName + ".missing"));
            throw new SecurityGroupsActionValidationException(fieldName + " is required");
        }
    }

    /**
     * validate the group.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to validate.
     * @param group
     *            the group
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    public static void validate(CreateUpdateGroupAction instance, Group group) {
        check(instance, group.getName(), "group.name");
        checkNull(instance, group.getDefaultPermission(), "group.defaultPermission");
        check(instance, group.getClient().getName(), "group.client.name");
        checkNullNorEmpty(instance, group.getBillingAccounts(), "group.billingAccounts");
        checkNullNorEmpty(instance, group.getDirectProjects(), "group.directProjects");
        checkNullNorEmpty(instance, group.getRestrictions(), "group.restrictions");
    }

    /**
     * validate the group member.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to validate.
     * @param member
     *            the group member
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    private static void validate(CreateUpdateGroupAction instance, GroupMember member) {
        if (!member.isUseGroupDefault()) {
            checkNull(instance, member.getSpecificPermission(), "member.specificPermission");
        }
    }

    /**
     * validate the group members.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to validate.
     * @param members
     *            the group members
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    public static void validate(CreateUpdateGroupAction instance, List<GroupMember> members) {
        for (GroupMember member : members) {
            validate(instance, member);
        }
    }
}
