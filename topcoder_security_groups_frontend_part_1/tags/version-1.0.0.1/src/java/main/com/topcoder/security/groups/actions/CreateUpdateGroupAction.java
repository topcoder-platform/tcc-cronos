/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.BillingAccountService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.ProjectDTO;

/**
 * <p>
 * CreateUpdateGroupAction is used to provide common fields for the UpdateGroupAction and CreateGroupAction, it
 * also provide common action methods such as enter group details for the update/create group action. It will log
 * events and errors. It will audit the methods.
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
public abstract class CreateUpdateGroupAction extends SessionAwareBaseAction {

    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = CreateUpdateGroupAction.class.getName();

    /**
     * Purpose: groupId is used to represents the group id. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Positive after set
     */
    private long groupId;

    /**
     * Purpose: selectedClientId is used to represents the selected client id. Usage: It's passed as the http input
     * parameter for this action. Legal Values: Positive after set
     */
    private Long selectedClientId;

    /**
     * Purpose: groupMemberId is used to represents the group member id.Usage: It's passed as the http input
     * parameter for this action. Legal Values: Positive after set
     */
    private long groupMemberId;

    /**
     * Purpose: group is used to represents the group. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Not null after set
     */
    private Group group;

    /**
     * Purpose: groupMember is used to represents the group member. Usage: It's passed as the http input parameter
     * for this action. Legal Values: Not null after set
     */
    private GroupMember groupMember;

    /**
     * Purpose: groupMembers is used to represents the group members. Usage: It's passed as the http input
     * parameter for this action. Legal Values: Not null and not empty after set. The list should not contain null
     * value.
     */
    private List<GroupMember> groupMembers;

    /**
     * Purpose: accounts is used to represents the accounts. Usage: It's set by the action methods, and consumed by
     * the front end page. Legal Values: Not null and not empty after set. The list should not contain null value.
     */
    private List<BillingAccount> accounts;

    /**
     * Purpose: clients is used to represents the clients. Usage: It's set by the action methods, and consumed by
     * the front end page. Legal Values: Not null and not empty after set. The list should not contain null value.
     */
    private List<Client> clients;

    /**
     * Purpose: projects is used to represents the projects. Usage: It's set by the action methods, and consumed by
     * the front end page. Legal Values: Not null and not empty after set. The list should not contain null value.
     */
    private List<ProjectDTO> projects;

    /**
     * Purpose: groupService is used to represents the group service. It's required. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private GroupService groupService;

    /**
     * Purpose: groupSessionKey is used to represents the group session key. It's required. Usage: It's injected.
     * Legal Values: Not null and not empty after set.
     */
    private String groupSessionKey;

    /**
     * Purpose: clientService is used to represents the client service. It's required. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private ClientService clientService;

    /**
     * Purpose: customerAdministratorService is used to represents the customer administrator service. It's
     * required. Usage: It's injected. Legal Values: Not null after set
     */
    private CustomerAdministratorService customerAdministratorService;

    /**
     * Purpose:directProjectService is used to represents the direct project service. It's required. Usage: It's
     * injected. Legal Values: Not null after set
     */
    private DirectProjectService directProjectService;

    /**
     * Purpose: groupInvitationService is used to represents the group invitation service. It's required. Usage:
     * It's injected. Legal Values: Not null after set
     */
    private GroupInvitationService groupInvitationService;

    /**
     * Purpose: acceptRejectUrlBase is used to represents the accept reject url base. It's required. Usage: It's
     * injected. Legal Values: Not null and not empty after set.
     */
    private String acceptRejectUrlBase;

    /**
     * Purpose: registrationUrl is used to represents the registration url. It's required. Usage: It's injected.
     * Legal Values: Not null and not empty after set.
     */
    private String registrationUrl;

    /**
     * Purpose: billingAccountService is used to represents the billing account service. It's required. Usage: It's
     * injected. Legal Values: Not null after set
     */
    private BillingAccountService billingAccountService;

    /**
     * Purpose: oldGroupMembersSessionKey is used to represents the old group members session key. It's required.
     * Usage: It's injected. Legal Values: Not null and not empty after set.
     */
    private String oldGroupMembersSessionKey;

    /**
     * Purpose: groupMemberService is used to represents the group member service. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private GroupMemberService groupMemberService;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    protected CreateUpdateGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required fields are injected.
     * </p>
     *
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if one of the following conditions is true:<br>
     *             <li>session is null or empty <li>session contains null values. <li>any of these fields is null:<br>
     *             auditService, authorizationService, clientService, billingAccountService, directProjectService,
     *             groupInvitationService, groupService, customerAdministratorService, groupMemberService<br> <li>
     *             any of these fields is null or empty after trimming:<br>
     *             groupSessionKey, registrationUrl, acceptRejectUrlBase, oldGroupMembersSessionKey
     *
     */
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(groupSessionKey, "groupSessionKey",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(clientService, "clientService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(billingAccountService, "billingAccountService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(directProjectService, "directProjectService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupInvitationService, "groupInvitationService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupService, "groupService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(customerAdministratorService, "customerAdministratorService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(registrationUrl, "registrationUrl",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(acceptRejectUrlBase, "acceptRejectUrlBase",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupMemberService, "groupMemberService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(oldGroupMembersSessionKey,
                "oldGroupMembersSessionKey", SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Input method. Process the user input.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The input result
     */
    public String input() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".input()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // get current user id
            long userId = getCurrentUserId();
            // fill clients for "Customer Name" field
            if (getAuthorizationService().isAdministrator(userId)) {
                clients = clientService.getAllClients();
            } else {
                clients = customerAdministratorService.getCustomersForAdministrator(userId);
            }
            // fill projects for "Projects" field, fill accounts for Billing Accounts field.
            if (selectedClientId != null) {
                projects = directProjectService.getProjectsByClientId(selectedClientId);
                accounts = billingAccountService.getBillingAccountsForClient(selectedClientId);
            }

            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {INPUT });
            return INPUT;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs while processing the user input", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while processing the user input", e));
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
    public String enterGroupDetails() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".enterGroupDetails()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        // This method puts the input group to the session using the configured key groupSessionKey.
        try {
            getSession().put(groupSessionKey, group);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
            return SUCCESS;
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while adding member to the group", e));
        }
    }

    /**
     * <p>
     * Add members method. It will add the group members to the group.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The execution result
     */
    public String addMembers() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".addMembers()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // This method adds group members to the group in the session.
            // the group should be retrieved from the session via the groupSessionKey.
            ((Group) getSession().get(groupSessionKey)).getGroupMembers().addAll(groupMembers);

            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
            return SUCCESS;
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while adding member to the group", e));
        }
    }

    /**
     * <p>
     * Delete member method. It will delete the group member.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The execution result
     */
    public String deleteMember() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".deleteMember()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // This method deletes group member in the group in the session.
            // the group should be retrieved from the session via the groupSessionKey.
            Group group2 = (Group) getSession().get(groupSessionKey);
            group2.getGroupMembers().remove(groupMember);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
            return SUCCESS;
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while deleting member from the group", e));
        }
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
     * Get group member id.
     * </p>
     *
     * @return the group member id
     */
    public long getGroupMemberId() {
        return groupMemberId;
    }

    /**
     * <p>
     * Set group member id.
     * </p>
     *
     * @param groupMemberId
     *            the group member id to set.
     */
    public void setGroupMemberId(long groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    /**
     * <p>
     * Get group member.
     * </p>
     *
     * @return the group member
     */
    public GroupMember getGroupMember() {
        return groupMember;
    }

    /**
     * <p>
     * Set group member.
     * </p>
     *
     * @param groupMember
     *            the group member to set.
     */
    public void setGroupMember(GroupMember groupMember) {
        this.groupMember = groupMember;
    }

    /**
     * <p>
     * Get group members.
     * </p>
     *
     * @return the group members
     */
    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    /**
     * <p>
     * Set group members.
     * </p>
     *
     * @param groupMembers
     *            the group members to set.
     */
    public void setGroupMembers(List<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
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

    /**
     * <p>
     * Get group session key.
     * </p>
     *
     * @return the group session key
     */
    public String getGroupSessionKey() {
        return groupSessionKey;
    }

    /**
     * <p>
     * Set group session key.
     * </p>
     *
     * @param groupSessionKey
     *            the group session key to set.
     */
    public void setGroupSessionKey(String groupSessionKey) {
        this.groupSessionKey = groupSessionKey;
    }

    /**
     * <p>
     * Get clients.
     * </p>
     *
     * @return the clients
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * <p>
     * Set clients.
     * </p>
     *
     * @param clients
     *            the clients to set.
     */
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    /**
     * <p>
     * Get selected client id.
     * </p>
     *
     * @return the selected client id
     */
    public Long getSelectedClientId() {
        return selectedClientId;
    }

    /**
     * <p>
     * Set selected client id.
     * </p>
     *
     * @param selectedClientId
     *            the selected client id to set.
     */
    public void setSelectedClientId(Long selectedClientId) {
        this.selectedClientId = selectedClientId;
    }

    /**
     * <p>
     * Get projects.
     * </p>
     *
     * @return the projects
     */
    public List<ProjectDTO> getProjects() {
        return projects;
    }

    /**
     * <p>
     * Set projects.
     * </p>
     *
     * @param projects
     *            the projects to set.
     */
    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }

    /**
     * <p>
     * Get client service.
     * </p>
     *
     * @return the client service
     */
    public ClientService getClientService() {
        return clientService;
    }

    /**
     * <p>
     * Set client service.
     * </p>
     *
     * @param clientService
     *            the client service to set.
     */
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * <p>
     * Get customer administrator service.
     * </p>
     *
     * @return the customer administrator service
     */
    public CustomerAdministratorService getCustomerAdministratorService() {
        return customerAdministratorService;
    }

    /**
     * <p>
     * Set customer administrator service.
     * </p>
     *
     * @param customerAdministratorService
     *            the customer administrator service to set.
     */
    public void setCustomerAdministratorService(CustomerAdministratorService customerAdministratorService) {
        this.customerAdministratorService = customerAdministratorService;
    }

    /**
     * <p>
     * Get direct project service.
     * </p>
     *
     * @return the direct project service
     */
    public DirectProjectService getDirectProjectService() {
        return directProjectService;
    }

    /**
     * <p>
     * Set direct project service.
     * </p>
     *
     * @param directProjectService
     *            the direct project service to set.
     */
    public void setDirectProjectService(DirectProjectService directProjectService) {
        this.directProjectService = directProjectService;
    }

    /**
     * <p>
     * Get group invitation service.
     * </p>
     *
     * @return the group invitation service
     */
    public GroupInvitationService getGroupInvitationService() {
        return groupInvitationService;
    }

    /**
     * <p>
     * Set group invitation service.
     * </p>
     *
     * @param groupInvitationService
     *            the group invitation service to set.
     */
    public void setGroupInvitationService(GroupInvitationService groupInvitationService) {
        this.groupInvitationService = groupInvitationService;
    }

    /**
     * <p>
     * Get accept reject url base.
     * </p>
     *
     * @return the accept reject url base
     */
    public String getAcceptRejectUrlBase() {
        return acceptRejectUrlBase;
    }

    /**
     * <p>
     * Set accept reject url base.
     * </p>
     *
     * @param acceptRejectUrlBase
     *            the accept reject url base to set.
     */
    public void setAcceptRejectUrlBase(String acceptRejectUrlBase) {
        this.acceptRejectUrlBase = acceptRejectUrlBase;
    }

    /**
     * <p>
     * Get registration url.
     * </p>
     *
     * @return the registration url
     */
    public String getRegistrationUrl() {
        return registrationUrl;
    }

    /**
     * <p>
     * Set registration url.
     * </p>
     *
     * @param registrationUrl
     *            the registration url to set.
     */
    public void setRegistrationUrl(String registrationUrl) {
        this.registrationUrl = registrationUrl;
    }

    /**
     * <p>
     * Get accounts.
     * </p>
     *
     * @return the accounts
     */
    public List<BillingAccount> getAccounts() {
        return accounts;
    }

    /**
     * <p>
     * Set accounts.
     * </p>
     *
     * @param accounts
     *            the accounts to set.
     */
    public void setAccounts(List<BillingAccount> accounts) {
        this.accounts = accounts;
    }

    /**
     * <p>
     * Get billing account service.
     * </p>
     *
     * @return the billing account service
     */
    public BillingAccountService getBillingAccountService() {
        return billingAccountService;
    }

    /**
     * <p>
     * Set billing account service.
     * </p>
     *
     * @param billingAccountService
     *            the billing account service to set.
     */
    public void setBillingAccountService(BillingAccountService billingAccountService) {
        this.billingAccountService = billingAccountService;
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
     * Get old group Members session key.
     * </p>
     *
     * @return the old group members session key
     */
    public String getOldGroupMembersSessionKey() {
        return oldGroupMembersSessionKey;
    }

    /**
     * <p>
     * Set old group Members session key.
     * </p>
     *
     * @param oldGroupMembersSessionKey
     *            the old group members session key to set.
     */
    public void setOldGroupMembersSessionKey(String oldGroupMembersSessionKey) {
        this.oldGroupMembersSessionKey = oldGroupMembersSessionKey;
    }

    /**
     * <p>
     * Get group Member service.
     * </p>
     *
     * @return the group member service
     */
    public GroupMemberService getGroupMemberService() {
        return groupMemberService;
    }

    /**
     * <p>
     * Set group Member service.
     * </p>
     *
     * @param groupMemberService
     *            the group member service to set.
     */
    public void setGroupMemberService(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }
}
