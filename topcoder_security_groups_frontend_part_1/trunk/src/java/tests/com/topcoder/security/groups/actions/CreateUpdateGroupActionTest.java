/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.ProjectDTO;

/**
 * <p>
 * Unit tests for class <code>CreateGroupAction</code>.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CreateUpdateGroupActionTest {
    /**
     * <p>
     * Represents the <code>CreateUpdateGroupAction</code> instance used to test against.
     * </p>
     */
    private CreateUpdateGroupAction impl;

    /**
     * <p>
     * Represents the <code>AuthorizationService</code> instance used to test against.
     * </p>
     */
    private AuthorizationService authorizationService;

    /**
     * <p>
     * Represents the <code>ClientService</code> instance used to test against.
     * </p>
     */
    private ClientService clientService;

    /**
     * <p>
     * Represents the <code>CustomerAdministratorService</code> instance used to test against.
     * </p>
     */
    private CustomerAdministratorService customerAdministratorService;

    /**
     * <p>
     * Represents the <code>DirectProjectService</code> instance used to test against.
     * </p>
     */
    private DirectProjectService directProjectService;

    /**
     * <p>
     * Represents the <code>BillingAccountService</code> instance used to test against.
     * </p>
     */
    private BillingAccountService billingAccountService;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CreateUpdateGroupActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new CreateUpdateGroupAction() { };
        authorizationService = EasyMock.createNiceMock(AuthorizationService.class);
        clientService = EasyMock.createNiceMock(ClientService.class);
        customerAdministratorService = EasyMock.createNiceMock(CustomerAdministratorService.class);
        directProjectService = EasyMock.createNiceMock(DirectProjectService.class);
        billingAccountService = EasyMock.createNiceMock(BillingAccountService.class);
        impl.setAuthorizationService(authorizationService);
        impl.setClientService(clientService);
        impl.setCustomerAdministratorService(customerAdministratorService);
        impl.setDirectProjectService(directProjectService);
        impl.setBillingAccountService(billingAccountService);
        impl.setSelectedClientId(null);
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        impl = null;
    }

    /**
     * fill the required field of the instance.
     *
     * @param instance
     *            the instance
     */
    public static void fillField(CreateUpdateGroupAction instance) {
        SessionAwareBaseActionTest.fillField(instance);

        instance.setGroupSessionKey("groupSessionKey");
        instance.setClientService(EasyMock.createNiceMock(ClientService.class));
        instance.setBillingAccountService(EasyMock.createNiceMock(BillingAccountService.class));
        instance.setDirectProjectService(EasyMock.createNiceMock(DirectProjectService.class));
        instance.setGroupInvitationService(EasyMock.createNiceMock(GroupInvitationService.class));
        instance.setGroupService(EasyMock.createNiceMock(GroupService.class));
        instance.setCustomerAdministratorService(EasyMock.createNiceMock(CustomerAdministratorService.class));
        instance.setRegistrationUrl("registrationUrl");
        instance.setAcceptRejectUrlBase("acceptRejectUrlBase");
        instance.setGroupMemberService(EasyMock.createNiceMock(GroupMemberService.class));
        instance.setOldGroupMembersSessionKey("oldGroupMembersSessionKey");
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckInit() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * groupSessionKey is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit2() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setGroupSessionKey(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * groupSessionKey is empty.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit3() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setGroupSessionKey("");
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * clientService is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit4() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setClientService(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * billingAccountService is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit5() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setBillingAccountService(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * directProjectService is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit6() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setDirectProjectService(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * groupInvitationService is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit7() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setGroupInvitationService(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * groupService is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit8() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setGroupService(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * customerAdministratorService is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit9() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setCustomerAdministratorService(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * registrationUrl is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit10() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setRegistrationUrl(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * registrationUrl is empty.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit11() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setRegistrationUrl("");
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * acceptRejectUrlBase is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit12() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setAcceptRejectUrlBase(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * acceptRejectUrlBase is empty.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit13() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setAcceptRejectUrlBase("");
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * groupMemberService is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit14() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setGroupMemberService(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * registrationUrl is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit15() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setRegistrationUrl(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * registrationUrl is empty.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit16() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setRegistrationUrl("");
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * oldGroupMembersSessionKey is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit17() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setOldGroupMembersSessionKey(null);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * oldGroupMembersSessionKey is empty.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit18() {
        impl = new CreateUpdateGroupAction() {
        };
        fillField(impl);
        impl.setOldGroupMembersSessionKey("");
        impl.checkInit();
    }

    /**
     * <p>
     * Accuracy test for the method <code>input()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testInput() throws Exception {
        List<Client> clients = new ArrayList<Client>();
        EasyMock.expect(authorizationService.isAdministrator(EasyMock.anyLong())).andReturn(true);
        EasyMock.expect(clientService.getAllClients()).andReturn(clients);
        EasyMock.replay(authorizationService, clientService);
        assertEquals("The return value should be same as ", "input", impl.input());
        EasyMock.verify(authorizationService, clientService);
        assertEquals("clients should be correct", clients, impl.getClients());
    }

    /**
     * <p>
     * Accuracy test for the method <code>input()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testInput2() throws Exception {
        List<Client> clients = new ArrayList<Client>();
        EasyMock.expect(authorizationService.isAdministrator(EasyMock.anyLong())).andReturn(false);
        EasyMock.expect(customerAdministratorService.getCustomersForAdministrator(EasyMock.anyLong())).andReturn(
                clients);
        EasyMock.replay(authorizationService, customerAdministratorService);
        assertEquals("The return value should be same as ", "input", impl.input());
        EasyMock.verify(authorizationService, customerAdministratorService);
        assertEquals("clients should be correct", clients, impl.getClients());
    }

    /**
     * <p>
     * Accuracy test for the method <code>input()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testInput3() throws Exception {
        List<Client> clients = new ArrayList<Client>();
        List<ProjectDTO> projects = new ArrayList<ProjectDTO>();
        List<BillingAccount> accounts = new ArrayList<BillingAccount>();
        Long selectedClientId = new Long(7);
        impl.setSelectedClientId(selectedClientId);
        EasyMock.expect(authorizationService.isAdministrator(EasyMock.anyLong())).andReturn(false);
        EasyMock.expect(customerAdministratorService.getCustomersForAdministrator(EasyMock.anyLong())).andReturn(
                clients);
        EasyMock.expect(directProjectService.getProjectsByClientId(selectedClientId)).andReturn(projects);
        EasyMock.expect(billingAccountService.getBillingAccountsForClient(selectedClientId)).andReturn(accounts);
        EasyMock.replay(authorizationService, customerAdministratorService, directProjectService,
                billingAccountService);
        assertEquals("The return value should be same as ", "input", impl.input());
        EasyMock.verify(authorizationService, customerAdministratorService, directProjectService,
                billingAccountService);
        assertEquals("clients should be correct", clients, impl.getClients());
        assertEquals("projects should be correct", projects, impl.getProjects());
        assertEquals("accounts should be correct", accounts, impl.getAccounts());
    }

    /**
     * <p>
     * Failure test for the method <code>input()</code>.<br>
     * SecurityGroupException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testInputFailure() throws Exception {
        EasyMock.expect(authorizationService.isAdministrator(EasyMock.anyLong())).andReturn(true);
        EasyMock.expect(clientService.getAllClients()).andThrow(new SecurityGroupException("for test"));
        EasyMock.replay(authorizationService, clientService);
        impl.input();
    }

    /**
     * <p>
     * Failure test for the method <code>input()</code>.<br>
     * RuntimeException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testInputFailure2() throws Exception {
        EasyMock.expect(authorizationService.isAdministrator(EasyMock.anyLong())).andReturn(true);
        EasyMock.expect(clientService.getAllClients()).andThrow(new RuntimeException("for test"));
        EasyMock.replay(authorizationService, clientService);
        impl.input();
    }

    /**
     * <p>
     * Accuracy test for the method <code>enterGroupDetails()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testEnterGroupDetails() throws Exception {
        String groupSessionKey = "groupSessionKey";
        impl.setGroupSessionKey(groupSessionKey);
        impl.setSession(new HashMap<String, Object>());
        Group group = new Group();
        impl.setGroup(group);

        assertEquals("The return value should be same as ", "success", impl.enterGroupDetails());
        assertEquals("group should be correct", group, impl.getSession().get(groupSessionKey));
    }

    /**
     * <p>
     * Failure test for the method <code>enterGroupDetails()</code>.<br>
     * RuntimeException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test(expected = SecurityGroupsActionException.class)
    public void testEnterGroupDetailsFailure() throws Exception {
        String groupSessionKey = "groupSessionKey";
        impl.setGroupSessionKey(groupSessionKey);
        Map<String, Object> session = EasyMock.createNiceMock(Map.class);
        impl.setSession(session);
        Group group = new Group();
        impl.setGroup(group);
        EasyMock.expect(session.put(groupSessionKey, group)).andThrow(new RuntimeException("for test"));
        EasyMock.replay(session);
        impl.enterGroupDetails();
    }

    /**
     * <p>
     * Accuracy test for the method <code>addMembers()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testAddMembers() throws Exception {
        String groupSessionKey = "groupSessionKey";
        impl.setGroupSessionKey(groupSessionKey);
        Map<String, Object> session = new HashMap<String, Object>();
        Group group = new Group();
        group.setGroupMembers(new ArrayList<GroupMember>());
        session.put(groupSessionKey, group);
        impl.setSession(session);
        List<GroupMember> members = new ArrayList<GroupMember>();
        members.add(new GroupMember());
        members.add(new GroupMember());
        impl.setGroupMembers(members);
        assertEquals("The return value should be same as ", "success", impl.addMembers());
        assertEquals("Members should be added", 2, group.getGroupMembers().size());
    }

    /**
     * <p>
     * Failure test for the method <code>addMembers()</code>.<br>
     * groupMembers is null.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testAddMembersFailure() throws Exception {
        String groupSessionKey = "groupSessionKey";
        impl.setGroupSessionKey(groupSessionKey);
        Map<String, Object> session = new HashMap<String, Object>();
        Group group = new Group();
        group.setGroupMembers(new ArrayList<GroupMember>());
        session.put(groupSessionKey, group);
        impl.setSession(session);
        impl.addMembers();
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteMember()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testDeleteMember() throws Exception {
        String groupSessionKey = "groupSessionKey";
        impl.setGroupSessionKey(groupSessionKey);
        Map<String, Object> session = new HashMap<String, Object>();
        Group group = new Group();
        List<GroupMember> members = new ArrayList<GroupMember>();
        GroupMember member1 = new GroupMember();
        member1.setId(1);
        GroupMember member2 = new GroupMember();
        member2.setId(2);
        members.add(member1);
        members.add(member2);
        group.setGroupMembers(members);
        session.put(groupSessionKey, group);
        impl.setSession(session);

        impl.setGroupMember(member1);
        assertEquals("The return value should be same as ", "success", impl.deleteMember());
        assertEquals("Members should be added", 1, group.getGroupMembers().size());
    }

    /**
     * <p>
     * Failure test for the method <code>deleteMember()</code>.<br>
     * group.getGroupMembers is null.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testDeleteMemberFailure() throws Exception {
        String groupSessionKey = "groupSessionKey";
        impl.setGroupSessionKey(groupSessionKey);
        Map<String, Object> session = new HashMap<String, Object>();
        Group group = new Group();
        session.put(groupSessionKey, group);
        impl.setSession(session);

        impl.deleteMember();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>CreateUpdateGroupAction()</code>. Instance should be correctly
     * created.
     * </p>
     */
    @Test
    public void testCtor() {
        impl = new CreateGroupAction();
        assertNotNull(impl);
        assertNull("'group' should be correct.", impl.getGroup());
        assertEquals("'groupMemberId' should be correct.", 0, impl.getGroupMemberId());
        assertNull("'groupMember' should be correct.", impl.getGroupMember());
        assertNull("'groupMembers' should be correct.", impl.getGroupMembers());
        assertNull("'groupService' should be correct.", impl.getGroupService());
        assertNull("'groupSessionKey' should be correct.", impl.getGroupSessionKey());
        assertNull("'clients' should be correct.", impl.getClients());
        assertNull("'selectedClientId' should be correct.", impl.getSelectedClientId());
        assertNull("'projects' should be correct.", impl.getProjects());
        assertNull("'clientService' should be correct.", impl.getClientService());
        assertNull("'customerAdministratorService' should be correct.", impl.getCustomerAdministratorService());
        assertNull("'directProjectService' should be correct.", impl.getDirectProjectService());
        assertNull("'groupInvitationService' should be correct.", impl.getGroupInvitationService());
        assertNull("'acceptRejectUrlBase' should be correct.", impl.getAcceptRejectUrlBase());
        assertNull("'registrationUrl' should be correct.", impl.getRegistrationUrl());
        assertNull("'accounts' should be correct.", impl.getAccounts());
        assertNull("'billingAccountService' should be correct.", impl.getBillingAccountService());
        assertEquals("'groupId' should be correct.", 0, impl.getGroupId());
        assertNull("'oldGroupMembersSessionKey' should be correct.", impl.getOldGroupMembersSessionKey());
        assertNull("'groupMemberService' should be correct.", impl.getGroupMemberService());
    }

    /**
     * <p>
     * <code>CreateUpdateGroupAction</code> should be subclass of <code>superClassName</code>.
     * </p>
     */
    @Test
    public void testInheritance0() {
        assertTrue("CreateUpdateGroupAction should be subclass of SessionAwareBaseAction.",
                CreateUpdateGroupAction.class.getSuperclass() == SessionAwareBaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroup()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroup() {
        Group value = new Group();
        impl.setGroup(value);
        assertEquals("'getGroup' should be correct.", value, impl.getGroup());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroup(Group group)</code>. The value should be properly set.
     * </p>
     */
    @Test
    public void testSetGroup() {
        Group value = new Group();
        impl.setGroup(value);
        assertEquals("'setGroup' should be correct.", value, TestHelper.getField(impl, "group"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupMemberId()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroupMemberId() {
        long value = 42;
        impl.setGroupMemberId(value);
        assertEquals("'getGroupMemberId' should be correct.", value, impl.getGroupMemberId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroupMemberId(long groupMemberId)</code>. The value should be properly
     * set.
     * </p>
     */
    @Test
    public void testSetGroupMemberId() {
        long value = 42;
        impl.setGroupMemberId(value);
        assertEquals("'setGroupMemberId' should be correct.", value, TestHelper.getField(impl, "groupMemberId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupMember()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroupMember() {
        GroupMember value = new GroupMember();
        impl.setGroupMember(value);
        assertEquals("'getGroupMember' should be correct.", value, impl.getGroupMember());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroupMember(GroupMember groupMember)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetGroupMember() {
        GroupMember value = new GroupMember();
        impl.setGroupMember(value);
        assertEquals("'setGroupMember' should be correct.", value, TestHelper.getField(impl, "groupMember"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupMembers()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroupMembers() {
        List<GroupMember> value = new ArrayList<GroupMember>();
        value.add(new GroupMember());
        value.add(new GroupMember());
        impl.setGroupMembers(value);
        assertEquals("'getGroupMembers' should be correct.", value, impl.getGroupMembers());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroupMembers(List<GroupMember> groupMembers)</code>. The value should
     * be properly set.
     * </p>
     */
    @Test
    public void testSetGroupMembers() {
        List<GroupMember> value = new ArrayList<GroupMember>();
        value.add(new GroupMember());
        value.add(new GroupMember());
        impl.setGroupMembers(value);
        assertEquals("'setGroupMembers' should be correct.", value, TestHelper.getField(impl, "groupMembers"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupService()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroupService() {
        GroupService value = EasyMock.createNiceMock(GroupService.class);
        impl.setGroupService(value);
        assertEquals("'getGroupService' should be correct.", value, impl.getGroupService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroupService(GroupService groupService)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetGroupService() {
        GroupService value = EasyMock.createNiceMock(GroupService.class);
        impl.setGroupService(value);
        assertEquals("'setGroupService' should be correct.", value, TestHelper.getField(impl, "groupService"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupSessionKey()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroupSessionKey() {
        String value = "new_string";
        impl.setGroupSessionKey(value);
        assertEquals("'getGroupSessionKey' should be correct.", value, impl.getGroupSessionKey());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroupSessionKey(String groupSessionKey)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetGroupSessionKey() {
        String value = "new_string";
        impl.setGroupSessionKey(value);
        assertEquals("'setGroupSessionKey' should be correct.", value,
                TestHelper.getField(impl, "groupSessionKey"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getClients()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetClients() {
        List<Client> value = new ArrayList<Client>();
        value.add(new Client());
        value.add(new Client());
        impl.setClients(value);
        assertEquals("'getClients' should be correct.", value, impl.getClients());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setClients(List<Client> clients)</code>. The value should be properly
     * set.
     * </p>
     */
    @Test
    public void testSetClients() {
        List<Client> value = new ArrayList<Client>();
        value.add(new Client());
        value.add(new Client());
        impl.setClients(value);
        assertEquals("'setClients' should be correct.", value, TestHelper.getField(impl, "clients"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSelectedClientId()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetSelectedClientId() {
        Long value = new Long(42);
        impl.setSelectedClientId(value);
        assertEquals("'getSelectedClientId' should be correct.", value, impl.getSelectedClientId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSelectedClientId(Long selectedClientId)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetSelectedClientId() {
        Long value = new Long(42);
        impl.setSelectedClientId(value);
        assertEquals("'setSelectedClientId' should be correct.", value,
                TestHelper.getField(impl, "selectedClientId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjects()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetProjects() {
        List<ProjectDTO> value = new ArrayList<ProjectDTO>();
        value.add(new ProjectDTO());
        value.add(new ProjectDTO());
        impl.setProjects(value);
        assertEquals("'getProjects' should be correct.", value, impl.getProjects());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjects(List<ProjectDTO> projects)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetProjects() {
        List<ProjectDTO> value = new ArrayList<ProjectDTO>();
        value.add(new ProjectDTO());
        value.add(new ProjectDTO());
        impl.setProjects(value);
        assertEquals("'setProjects' should be correct.", value, TestHelper.getField(impl, "projects"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getClientService()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetClientService() {
        ClientService value = EasyMock.createNiceMock(ClientService.class);
        impl.setClientService(value);
        assertEquals("'getClientService' should be correct.", value, impl.getClientService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setClientService(ClientService clientService)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetClientService() {
        ClientService value = EasyMock.createNiceMock(ClientService.class);
        impl.setClientService(value);
        assertEquals("'setClientService' should be correct.", value, TestHelper.getField(impl, "clientService"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCustomerAdministratorService()</code>. The value should be properly
     * retrieved.
     * </p>
     */
    @Test
    public void testGetCustomerAdministratorService() {
        CustomerAdministratorService value = EasyMock.createNiceMock(CustomerAdministratorService.class);
        impl.setCustomerAdministratorService(value);
        assertEquals("'getCustomerAdministratorService' should be correct.", value,
                impl.getCustomerAdministratorService());
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>setCustomerAdministratorService(CustomerAdministratorService customerAdministratorService)</code>. The
     * value should be properly set.
     * </p>
     */
    @Test
    public void testSetCustomerAdministratorService() {
        CustomerAdministratorService value = EasyMock.createNiceMock(CustomerAdministratorService.class);
        impl.setCustomerAdministratorService(value);
        assertEquals("'setCustomerAdministratorService' should be correct.", value,
                TestHelper.getField(impl, "customerAdministratorService"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDirectProjectService()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetDirectProjectService() {
        DirectProjectService value = EasyMock.createNiceMock(DirectProjectService.class);
        impl.setDirectProjectService(value);
        assertEquals("'getDirectProjectService' should be correct.", value, impl.getDirectProjectService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDirectProjectService(DirectProjectService directProjectService)</code>
     * . The value should be properly set.
     * </p>
     */
    @Test
    public void testSetDirectProjectService() {
        DirectProjectService value = EasyMock.createNiceMock(DirectProjectService.class);
        impl.setDirectProjectService(value);
        assertEquals("'setDirectProjectService' should be correct.", value,
                TestHelper.getField(impl, "directProjectService"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupInvitationService()</code>. The value should be properly
     * retrieved.
     * </p>
     */
    @Test
    public void testGetGroupInvitationService() {
        GroupInvitationService value = EasyMock.createNiceMock(GroupInvitationService.class);
        impl.setGroupInvitationService(value);
        assertEquals("'getGroupInvitationService' should be correct.", value, impl.getGroupInvitationService());
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>setGroupInvitationService(GroupInvitationService groupInvitationService)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetGroupInvitationService() {
        GroupInvitationService value = EasyMock.createNiceMock(GroupInvitationService.class);
        impl.setGroupInvitationService(value);
        assertEquals("'setGroupInvitationService' should be correct.", value,
                TestHelper.getField(impl, "groupInvitationService"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAcceptRejectUrlBase()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetAcceptRejectUrlBase() {
        String value = "new_string";
        impl.setAcceptRejectUrlBase(value);
        assertEquals("'getAcceptRejectUrlBase' should be correct.", value, impl.getAcceptRejectUrlBase());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAcceptRejectUrlBase(String acceptRejectUrlBase)</code>. The value
     * should be properly set.
     * </p>
     */
    @Test
    public void testSetAcceptRejectUrlBase() {
        String value = "new_string";
        impl.setAcceptRejectUrlBase(value);
        assertEquals("'setAcceptRejectUrlBase' should be correct.", value,
                TestHelper.getField(impl, "acceptRejectUrlBase"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getRegistrationUrl()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetRegistrationUrl() {
        String value = "new_string";
        impl.setRegistrationUrl(value);
        assertEquals("'getRegistrationUrl' should be correct.", value, impl.getRegistrationUrl());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRegistrationUrl(String registrationUrl)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetRegistrationUrl() {
        String value = "new_string";
        impl.setRegistrationUrl(value);
        assertEquals("'setRegistrationUrl' should be correct.", value,
                TestHelper.getField(impl, "registrationUrl"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAccounts()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetAccounts() {
        List<BillingAccount> value = new ArrayList<BillingAccount>();
        value.add(new BillingAccount());
        value.add(new BillingAccount());
        impl.setAccounts(value);
        assertEquals("'getAccounts' should be correct.", value, impl.getAccounts());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAccounts(List<BillingAccount> accounts)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetAccounts() {
        List<BillingAccount> value = new ArrayList<BillingAccount>();
        value.add(new BillingAccount());
        value.add(new BillingAccount());
        impl.setAccounts(value);
        assertEquals("'setAccounts' should be correct.", value, TestHelper.getField(impl, "accounts"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBillingAccountService()</code>. The value should be properly
     * retrieved.
     * </p>
     */
    @Test
    public void testGetBillingAccountService() {
        BillingAccountService value = EasyMock.createNiceMock(BillingAccountService.class);
        impl.setBillingAccountService(value);
        assertEquals("'getBillingAccountService' should be correct.", value, impl.getBillingAccountService());
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>setBillingAccountService(BillingAccountService billingAccountService)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetBillingAccountService() {
        BillingAccountService value = EasyMock.createNiceMock(BillingAccountService.class);
        impl.setBillingAccountService(value);
        assertEquals("'setBillingAccountService' should be correct.", value,
                TestHelper.getField(impl, "billingAccountService"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupId()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroupId() {
        long value = 42;
        impl.setGroupId(value);
        assertEquals("'getGroupId' should be correct.", value, impl.getGroupId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroupId(long groupId)</code>. The value should be properly set.
     * </p>
     */
    @Test
    public void testSetGroupId() {
        long value = 42;
        impl.setGroupId(value);
        assertEquals("'setGroupId' should be correct.", value, TestHelper.getField(impl, "groupId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getOldGroupMembersSessionKey()</code>. The value should be properly
     * retrieved.
     * </p>
     */
    @Test
    public void testGetOldGroupMembersSessionKey() {
        String value = "new_string";
        impl.setOldGroupMembersSessionKey(value);
        assertEquals("'getOldGroupMembersSessionKey' should be correct.", value,
                impl.getOldGroupMembersSessionKey());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setOldGroupMembersSessionKey(String oldGroupMembersSessionKey)</code>.
     * The value should be properly set.
     * </p>
     */
    @Test
    public void testSetOldGroupMembersSessionKey() {
        String value = "new_string";
        impl.setOldGroupMembersSessionKey(value);
        assertEquals("'setOldGroupMembersSessionKey' should be correct.", value,
                TestHelper.getField(impl, "oldGroupMembersSessionKey"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupMemberService()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroupMemberService() {
        GroupMemberService value = EasyMock.createNiceMock(GroupMemberService.class);
        impl.setGroupMemberService(value);
        assertEquals("'getGroupMemberService' should be correct.", value, impl.getGroupMemberService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroupMemberService(GroupMemberService groupMemberService)</code>. The
     * value should be properly set.
     * </p>
     */
    @Test
    public void testSetGroupMemberService() {
        GroupMemberService value = EasyMock.createNiceMock(GroupMemberService.class);
        impl.setGroupMemberService(value);
        assertEquals("'setGroupMemberService' should be correct.", value,
                TestHelper.getField(impl, "groupMemberService"));
    }
}
