/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import com.opensymphony.xwork2.Action;
import com.topcoder.security.groups.actions.CreateUpdateGroupAction;
import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.BillingAccountService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.dto.ProjectDTO;

/**
 * Accuracy test for {@link CreateUpdateGroupAction}.
 * @author BlackMagic
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CreateUpdateGroupActionAccTest extends TestCase {
    /**
     * Represents the instance used for test.
     */
    private CreateUpdateGroupAction instance;
    /**
     * Represents the {@link IMocksControl} instance used for test.
     */
    private IMocksControl control = EasyMock.createControl();
    /**
     * Represents the {@link GroupService} used for test.
     */
    private GroupService groupService;
    /**
     * Represents the {@link ClientService} instance used for test.
     */
    private ClientService clientService;
    /**
     * Represents the {@link CustomerAdministratorService} instance used for test.
     */
    private CustomerAdministratorService customerAdministratorService;
    /**
     * Represents the {@link DirectProjectService} instance used for test.
     */
    private DirectProjectService directProjectService;
    /**
     * Represents the {@link GroupInvitationService} instance used for test.
     */
    private GroupInvitationService groupInvitationService;
    /**
     * Represents the {@link BillingAccountService} instance used for test.
     */
    private BillingAccountService billingAccountService;
    /**
     * Represents the {@link GroupMemberService} instance used for test.
     */
    private GroupMemberService groupMemberService;
    /**
     * Represents the {@link AuthorizationService} instance used for test.
     */
    private AuthorizationService authorizationService;
    /**
     * Initializes the instance used for test.
     */
    public void setUp() {
        instance = new CreateUpdateGroupAction() {
        };
        groupService = control.createMock(GroupService.class);
        instance.setGroupService(groupService);
        clientService = control.createMock(ClientService.class);
        instance.setClientService(clientService);
        customerAdministratorService = control.createMock(CustomerAdministratorService.class);
        instance.setCustomerAdministratorService(customerAdministratorService);
        directProjectService = control.createMock(DirectProjectService.class);
        instance.setDirectProjectService(directProjectService);
        groupInvitationService = control.createMock(GroupInvitationService.class);
        instance.setGroupInvitationService(groupInvitationService);
        billingAccountService = control.createMock(BillingAccountService.class);
        instance.setBillingAccountService(billingAccountService);
        groupMemberService = control.createMock(GroupMemberService.class);
        instance.setGroupMemberService(groupMemberService);
        authorizationService = control.createMock(AuthorizationService.class);
        instance.setAuthorizationService(authorizationService);
        instance.setSession(new HashMap<String, Object>());
    }
    /**
     * Accuracy test for {@link CreateUpdateGroupAction#input()}.
     * @throws Exception to JUnit.
     */
    public void testInputAccuracy() throws Exception {
        authorizationService.isAdministrator(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(true).times(1);
        List<Client> clients = new ArrayList<Client>();
        clientService.getAllClients();
        EasyMock.expectLastCall().andReturn(clients).times(1);
        control.replay();
        assertEquals("The result should be correct.", Action.INPUT, instance.input());
        assertEquals("The result should be correct.", clients, instance.getClients());
        assertNull("The result should be correct.", instance.getProjects());
        assertNull("The result should be correct.", instance.getAccounts());
    }
    /**
     * Accuracy test for {@link CreateUpdateGroupAction#input()}.
     * @throws Exception to JUnit.
     */
    public void testInputAccuracy2() throws Exception {
        authorizationService.isAdministrator(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(false).times(1);
        List<Client> clients = new ArrayList<Client>();
        customerAdministratorService.getCustomersForAdministrator(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(clients).times(1);
        instance.setSelectedClientId(1L);
        directProjectService.getProjectsByClientId(EasyMock.anyLong());
        List<ProjectDTO> projects = new ArrayList<ProjectDTO>();
        EasyMock.expectLastCall().andReturn(projects).times(1);
        billingAccountService.getBillingAccountsForClient(EasyMock.anyLong());
        List<BillingAccount> accounts = new ArrayList<BillingAccount>();
        EasyMock.expectLastCall().andReturn(accounts).times(1);
        control.replay();
        assertEquals("The result should be correct.", Action.INPUT, instance.input());
        assertEquals("The result should be correct.", clients, instance.getClients());
        assertEquals("The result should be correct.", projects, instance.getProjects());
        assertEquals("The result should be correct.", accounts, instance.getAccounts());
        control.verify();
    }
    /**
     * Accuracy test for {@link CreateUpdateGroupAction#enterGroupDetails()}.
     * @throws Exception to JUnit.
     */
    public void testEnterGroupDetailsAccuracy2() throws Exception {
        instance.setGroupSessionKey("test");
        Group group = new Group();
        instance.setGroup(group);
        instance.enterGroupDetails();
        assertEquals("The result should be correct.", group, instance.getSession().get("test"));
    }
    /**
     * Accuracy test for {@link CreateUpdateGroupAction#addMembers()}.
     * @throws Exception to JUnit.
     */
    public void testaddMembersAccuracy2() throws Exception {
        instance.setGroupSessionKey("test");
        Group group = new Group();
        group.setGroupMembers(new ArrayList<GroupMember>());
        instance.setGroup(group);
        List<GroupMember> groupMembers = new ArrayList<GroupMember>();
        groupMembers.add(new GroupMember());
        instance.setGroupMembers(groupMembers);
        instance.enterGroupDetails();
        instance.addMembers();
        assertEquals("The result should be correct.", group, instance.getSession().get("test"));
        assertEquals("The result should be correct.", 1, group.getGroupMembers().size());
    }
    /**
     * Accuracy test for {@link CreateUpdateGroupAction#deleteMember()}.
     * @throws Exception to JUnit.
     */
    public void testdeleteMemberAccuracy2() throws Exception {
        instance.setGroupSessionKey("test");
        Group group = new Group();
        GroupMember groupMember = new GroupMember();
        group.setGroupMembers(new ArrayList<GroupMember>());
        instance.setGroup(group);
        List<GroupMember> groupMembers = new ArrayList<GroupMember>();
        groupMembers.add(groupMember);
        instance.setGroupMembers(groupMembers);
        instance.enterGroupDetails();
        instance.addMembers();
        assertEquals("The result should be correct.", group, instance.getSession().get("test"));
        assertEquals("The result should be correct.", 1, group.getGroupMembers().size());
        instance.setGroupMember(groupMember);
        instance.deleteMember();
        assertEquals("The result should be correct.", 0, group.getGroupMembers().size());
    }
}
