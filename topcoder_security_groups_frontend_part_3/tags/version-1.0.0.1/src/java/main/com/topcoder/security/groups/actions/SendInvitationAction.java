/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * This Struts action allows to send invitations. This action will be used by
 * JSP corresponding to administrator-send-group-invitation.html page from the
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
 * <b>Sample Configuration:</b> The spring configuration is below:
 *
 * <pre>
 *  &lt;bean id="SendInvitationAction"
 *     class="com.topcoder.security.groups.actions.SendInvitationAction"&gt;
 *     &lt;property name="logger" ref="logger"/&gt;
 *     &lt;property name="clientService" ref="clientService"/&gt;
 *     &lt;property name="groupService" ref="groupService"/&gt;
 *     &lt;property name="userService" ref="userService"/&gt;
 *     &lt;property name="customerAdministratorService" ref="customerAdministratorService"/&gt;
 *     &lt;property name="groupMemberService" ref="groupMemberService"/&gt;
 *     &lt;property name="directProjectService" ref="directProjectService"/&gt;
 *  &lt;/bean&gt;
 * </pre>
 *
 * The struts configuration is below:
 *
 * <pre>
 *   &lt;action name="sendInvitationAction" class="SendInvitationAction" method="input"&gt;
 *     &lt;result name="INPUT"&gt;accessAuditingInfo.jsp&lt;/result&gt;
 *   &lt;/action&gt;
 *
 * </pre>
 *
 * </p>
 *
 * @author gevak, progloco
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SendInvitationAction extends BaseAction {

    /**
     * Represent the class name.
     *
     */
    private static final String CLASS_NAME = SendInvitationAction.class
            .getName();
    /**
     * Names of groups to send invitations for. It is used in execute() method.
     * It is injected via the setter with no validation, thus can be any value.
     * When execute() is called, XML validations are applied on this variable.
     * Mutable via setter.
     */
    private List<String> groupNames;

    /**
     * Handles of users to send invitations to. It is used in execute() method.
     * It is injected via the setter with no validation, thus can be any value.
     * When execute() is called, XML validations are applied on this variable.
     * Mutable via setter.
     */
    private List<String> handles;

    /**
     * Groups which can be administrated by current user. It will be used by JSP
     * to render groups dropdown list. Initially null, but will be populated by
     * input() method with non-null list of non-null elements. Has public
     * getter.
     */
    private List<Group> groups;

    /**
     * GroupService used to manage group information. It is used in input() and
     * execute() methods. It is injected via the setter with no validation, thus
     * can be any value. However, <code>checkInit</code> method will ensure that it's
     * not null. Mutable via setter.
     */
    private GroupService groupService;

    /**
     * GroupInvitationService used to create group invitations. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. However, <code>checkInit</code> method will ensure that it's
     * not null. Mutable via setter.
     */
    private GroupInvitationService groupInvitationService;

    /**
     * UserService used to manage user information. It is used in execute()
     * method. It is injected via the setter with no validation, thus can be any
     * value. However, <code>checkInit</code> method will ensure that it's not null.
     * Mutable via setter.
     */
    private UserService userService;

    /**
     * CustomerAdministratorService used to get manage customer administrators.
     * It is used in input() and execute() methods. It is injected via the
     * setter with no validation, thus can be any value. However, <code>checkInit</code>
     * method will ensure that it's not null. Mutable via setter.
     */
    private CustomerAdministratorService customerAdministratorService;

    /**
     * Registration URL which will be added to invitations. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. However, <code>checkInit</code> method will ensure that it's
     * not null and not empty. Mutable via setter.
     */
    private String registrationUrl;

    /**
     * Base part of URL for accepting/rejecting invitations. Hyperlinks to such
     * URL's will be added to invitations. It is used in execute() method. It is
     * injected via the setter with no validation, thus can be any value.
     * However, <code>checkInit</code> method will ensure that it's not null and not
     * empty. Mutable via setter.
     */
    private String acceptRejectUrlBase;

    /**
     * Empty default constructor.
     */
    public SendInvitationAction() {
    }

    /**
     * Populates "groups" parameter, which will be used by JSP to render groups
     * dropdown list.
     *
     * @return INPUT to indicate that the operation was successful.
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     *
     */
    public String input() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".input()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // Get all groups
            GroupSearchCriteria c = new GroupSearchCriteria();
            PagedResult<Group> pageResult = groupService.search(c, 0, 0);
            List<Group> allGroups = pageResult.getValues();
            ValidationUtility.checkNotNullElements(allGroups, "allGroups",
                    SecurityGroupsActionException.class);
            // Get current user id
            long userId = getCurrentUserId();
            // For admin, no need to filter, only filter for non-admin
            if (!getAuthorizationService().isAdministrator(userId)) {
                // filter groups
                groups = new ArrayList<Group>();
                List<Client> clients = customerAdministratorService
                        .getCustomersForAdministrator(userId);
                Set<Long> clientIds = new HashSet<Long>();
                for (Client client : clients) {
                    clientIds.add(client.getId());
                }
                Set<Long> groupIds = new HashSet<Long>(
                        getAuthorizationService()
                                .getGroupIdsOfFullPermissionsUser(userId));
                for (Group group : allGroups) {
                    if (group.getClient() == null) {
                        continue;
                    }
                    if (!(groupIds.contains(group.getId()) && clientIds
                            .contains(group.getClient().getId()))) {
                        groups.add(group);
                    }
                }
            } else {
                groups = allGroups;
            }
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while doing the input", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature,
                new Object[] {INPUT });
        return INPUT;
    }

    /**
     * Sends invitations to the specified handles for specified groups.
     *
     * @return SUCCESS to indicate that the operation was successful.
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     *
     */
    public String execute() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature,
                new String[] {"groupNames", "handles", "groups", "registrationUrl", "acceptRejectUrlBase"},
                new Object[] {groupNames, handles, groups, registrationUrl, acceptRejectUrlBase});
        try {
            // send invitations for each group
            for (String groupName : groupNames) {
                // Find Group of the groupName, using groupService.search
                // method, there should be only one group found:
                GroupSearchCriteria c = new GroupSearchCriteria();
                c.setGroupName(groupName);
                List<Group> foundGroups = groupService.search(c, 0, 0)
                        .getValues();
                HelperUtiliy.checkSingleElementList(foundGroups, "foundGroups");
                Group group = foundGroups.get(0);
                // send invitations for current group to each user
                for (String handle : handles) {
                    // Find User by handle, there must be exactly one
                    List<UserDTO> users = userService.search(handle);
                    HelperUtiliy.checkSingleElementList(users, "users");
                    long userId = users.get(0).getUserId();
                    // Find GroupMember for the current user from
                    // group.groupMembers
                    List<GroupMember> groupMembers = group.getGroupMembers();
                    ValidationUtility
                            .checkNotNull(groupMembers, "groupMembers",
                                    SecurityGroupsActionException.class);
                    ValidationUtility
                            .checkNotNullElements(groupMembers, "groupMembers",
                                    SecurityGroupsActionException.class);
                    GroupMember groupMember = null;
                    for (GroupMember gm : groupMembers) {
                        if (gm.getUserId() == userId) {
                            groupMember = gm;
                            break;
                        }
                    }

                    // if none found, create a new group member for current user
                    if (groupMember == null) {
                        groupMember = new GroupMember();
                        groupMember.setUserId(userId);
                        groupMember.setUseGroupDefault(true);
                        groupMember.setActive(false);
                        groupMember.setGroup(group);
                        group.setGroupMembers(Arrays.asList(groupMember));
                        groupService.update(group);
                        // Perform audit
                        HelperUtiliy.audit(getAuditService(), null,
                                "group ID = " + group.getId() + " name = "
                                        + groupName
                                        + "; new group member user ID = "
                                        + userId + " handle = " + handle);

                    }

                    // create invitation
                    GroupInvitation invitation = new GroupInvitation();
                    invitation.setGroupMember(groupMember);
                    invitation.setStatus(InvitationStatus.PENDING);
                    invitation.setSentOn(new Date());
                    groupInvitationService.addInvitation(invitation);
                    // Perform audit
                    HelperUtiliy.audit(getAuditService(), null,
                            "new invitation group ID = " + group.getId()
                                    + " group name = " + groupName
                                    + " user ID = " + userId
                                    + " user handle = " + handle);
                    // construct URLs
                    String urlPrefix = acceptRejectUrlBase + "/?invitationId="
                            + invitation.getId() + "&accepted=";
                    String acceptUrl = urlPrefix + "true";
                    String rejectUrl = urlPrefix + "false";

                    // send invitation
                    groupInvitationService.sendInvitation(invitation,
                            registrationUrl, acceptUrl, rejectUrl);

                }
            }
        } catch (EntityNotFoundException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while sending invitation", e));
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while sending invitation", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature,
                new Object[] {SUCCESS });
        return SUCCESS;
    }

    /**
     * Checks whether this class was configured by Spring properly.
     *
     * @throws SecurityGroupsActionConfigurationException - if the class was not
     *             configured properly (e.g. when required property was not
     *             injected or property value is invalid).
     *
     */
    @PostConstruct
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupService, "groupService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupInvitationService,
                "groupInvitationService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(userService, "userService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(customerAdministratorService,
                "customerAdministratorService",
                SecurityGroupsActionConfigurationException.class);

        // not null nor empty.
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(registrationUrl,
                "registrationUrl",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(acceptRejectUrlBase,
                "acceptRejectUrlBase",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * Sets Names of groups to send invitations for.
     *
     * @param groupNames Names of groups to send invitations for.
     *
     */
    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    /**
     * Sets Handles of users to send invitations to.
     *
     * @param handles Handles of users to send invitations to.
     *
     */
    public void setHandles(List<String> handles) {
        this.handles = handles;
    }

    /**
     * Gets Groups which can be administrated by current user. It will be used
     * by JSP to render groups dropdown list.
     *
     * @return Groups which can be administrated by current user. It will be
     *         used by JSP to render groups dropdown list.
     *
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Sets GroupService used to manage group information.
     *
     * @param groupService GroupService used to manage group information.
     *
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Sets GroupInvitationService used to create group invitations.
     *
     * @param groupInvitationService GroupInvitationService used to create group
     *            invitations.
     *
     */
    public void setGroupInvitationService(
            GroupInvitationService groupInvitationService) {
        this.groupInvitationService = groupInvitationService;
    }

    /**
     * Sets UserService used to manage user information.
     *
     * @param userService UserService used to manage user information.
     *
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sets CustomerAdministratorService used to get manage customer
     * administrators.
     *
     * @param customerAdministratorService CustomerAdministratorService used to
     *            get manage customer administrators.
     *
     */
    public void setCustomerAdministratorService(
            CustomerAdministratorService customerAdministratorService) {
        this.customerAdministratorService = customerAdministratorService;
    }

    /**
     * Sets Registration URL which will be added to invitations.
     *
     * @param registrationUrl Registration URL which will be added to
     *            invitations.
     *
     */
    public void setRegistrationUrl(String registrationUrl) {
        this.registrationUrl = registrationUrl;
    }

    /**
     * Sets Base part of URL for accepting/rejecting invitations. Hyperlinks to
     * such URL's will be added to invitations.
     *
     * @param acceptRejectUrlBase Base part of URL for accepting/rejecting
     *            invitations. Hyperlinks to such URL's will be added to
     *            invitations.
     *
     */
    public void setAcceptRejectUrlBase(String acceptRejectUrlBase) {
        this.acceptRejectUrlBase = acceptRejectUrlBase;
    }
}
