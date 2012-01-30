/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.ServletActionContext;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.CustomerAdministrator;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Unit tests for the {@link SendInvitationAction}.
 * </p>
 *
 * @author progloco
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { SendInvitationAction.class, ServletActionContext.class })
public class SendInvitationActionTest {
    /** Represents the instance used to test again. */
    private SendInvitationAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new SendInvitationAction();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SendInvitationActionTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#SendInvitationAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testSendInvitationAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>SendInvitationAction</code> subclasses
     * <code>BaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("SendInvitationAction does not subclass BaseAction.",
                SendInvitationAction.class.getSuperclass() == BaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute() throws Exception {
        testInstance.setGroupNames(Arrays.asList("g1"));
        testInstance.setHandles(Arrays.asList("h1"));

        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        PagedResult<Group> pr = new PagedResult<Group>();
        List<Group> foundGroups = new ArrayList<Group>();
        Group group = new Group();
        GroupMember gm = new GroupMember();
        gm.setUserId(200L);
        group.setGroupMembers(Arrays.asList(new GroupMember(), gm));
        foundGroups.add(group);
        pr.setValues(foundGroups);
        Capture<GroupSearchCriteria> criteria = new Capture<GroupSearchCriteria>();
        Capture<Integer> id = new Capture<Integer>();
        EasyMock.expect(
                groupService.search(EasyMock.capture(criteria), EasyMock
                        .capture(id), EasyMock.capture(id))).andReturn(pr);
        testInstance.setGroupService(groupService);

        CustomerAdministratorService caService = EasyMock
                .createNiceMock(CustomerAdministratorService.class);
        Capture<CustomerAdministrator> c = new Capture<CustomerAdministrator>();
        EasyMock.expect(caService.add(EasyMock.capture(c))).andReturn(1L)
                .anyTimes();
        testInstance.setCustomerAdministratorService(caService);

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        giService.addInvitation(null);
        EasyMock.expectLastCall().anyTimes();
        giService.sendInvitation(null, null, null, null);
        EasyMock.expectLastCall().anyTimes();
        testInstance.setGroupInvitationService(giService);

        UserService userService = EasyMock.createNiceMock(UserService.class);
        testInstance.setUserService(userService);
        List<UserDTO> users = new ArrayList<UserDTO>();
        UserDTO user = new UserDTO();
        user.setUserId(100L);
        users.add(user);
        EasyMock.expect(userService.search("h1")).andReturn(users).anyTimes();

        GroupAuditService auditService = EasyMock
                .createNiceMock(GroupAuditService.class);
        Capture<GroupAuditRecord> g = new Capture<GroupAuditRecord>();
        EasyMock.expect(auditService.add(EasyMock.capture(g))).andReturn(1L);
        testInstance.setAuditService(auditService);

        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress")
                .anyTimes();
        EasyMock.replay(request);
        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request)
                .anyTimes();

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(groupService);
        EasyMock.replay(caService);
        EasyMock.replay(userService);
        EasyMock.replay(auditService);
        EasyMock.replay(giService);

        testInstance.execute();
        EasyMock.verify(giService);
        EasyMock.verify(auditService);
        EasyMock.verify(userService);
        EasyMock.verify(caService);
        EasyMock.verify(groupService);
        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute2() throws Exception {
        testInstance.setGroupNames(Arrays.asList("g1"));
        testInstance.setHandles(Arrays.asList("h1"));

        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        PagedResult<Group> pr = new PagedResult<Group>();
        List<Group> foundGroups = new ArrayList<Group>();
        Group group = new Group();
        GroupMember gm = new GroupMember();
        gm.setUserId(100L);
        group.setGroupMembers(Arrays.asList(gm));
        foundGroups.add(group);
        pr.setValues(foundGroups);
        Capture<GroupSearchCriteria> criteria = new Capture<GroupSearchCriteria>();
        Capture<Integer> id = new Capture<Integer>();
        EasyMock.expect(
                groupService.search(EasyMock.capture(criteria), EasyMock
                        .capture(id), EasyMock.capture(id))).andReturn(pr);
        testInstance.setGroupService(groupService);

        CustomerAdministratorService caService = EasyMock
                .createNiceMock(CustomerAdministratorService.class);
        Capture<CustomerAdministrator> c = new Capture<CustomerAdministrator>();
        EasyMock.expect(caService.add(EasyMock.capture(c))).andReturn(1L)
                .anyTimes();
        testInstance.setCustomerAdministratorService(caService);

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        giService.addInvitation(null);
        EasyMock.expectLastCall().anyTimes();
        giService.sendInvitation(null, null, null, null);
        EasyMock.expectLastCall().anyTimes();
        testInstance.setGroupInvitationService(giService);

        UserService userService = EasyMock.createNiceMock(UserService.class);
        testInstance.setUserService(userService);
        List<UserDTO> users = new ArrayList<UserDTO>();
        UserDTO user = new UserDTO();
        user.setUserId(100L);
        users.add(user);
        EasyMock.expect(userService.search("h1")).andReturn(users).anyTimes();

        GroupAuditService auditService = EasyMock
                .createNiceMock(GroupAuditService.class);
        Capture<GroupAuditRecord> g = new Capture<GroupAuditRecord>();
        EasyMock.expect(auditService.add(EasyMock.capture(g))).andReturn(1L);
        testInstance.setAuditService(auditService);

        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress")
                .anyTimes();
        EasyMock.replay(request);
        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request)
                .anyTimes();

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(groupService);
        EasyMock.replay(caService);
        EasyMock.replay(userService);
        EasyMock.replay(auditService);
        EasyMock.replay(giService);

        testInstance.execute();
        EasyMock.verify(giService);
        EasyMock.verify(auditService);
        EasyMock.verify(userService);
        EasyMock.verify(caService);
        EasyMock.verify(groupService);
        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#execute()}.
     * </p>
     * <p>
     * Back end service throw Exception, SecurityGroupsActionException should
     * throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail() throws Exception {
        testInstance.setGroupNames(Arrays.asList("g1"));
        testInstance.setHandles(Arrays.asList("h1"));
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        PagedResult<Group> pr = new PagedResult<Group>();
        List<Group> foundGroups = new ArrayList<Group>();
        Group group = new Group();
        GroupMember gm = new GroupMember();
        gm.setUserId(100L);
        group.setGroupMembers(Arrays.asList(new GroupMember(), gm));
        foundGroups.add(group);
        pr.setValues(foundGroups);
        Capture<GroupSearchCriteria> criteria = new Capture<GroupSearchCriteria>();
        Capture<Integer> id = new Capture<Integer>();
        EasyMock.expect(
                groupService.search(EasyMock.capture(criteria), EasyMock
                        .capture(id), EasyMock.capture(id))).andThrow(
                new SecurityGroupException("test"));
        testInstance.setGroupService(groupService);

        EasyMock.replay(groupService);
        testInstance.execute();
        EasyMock.verify(groupService);
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#execute()}.
     * </p>
     * <p>
     * Back end service throw Exception, SecurityGroupsActionException should
     * throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail2() throws Exception {
        testInstance.setGroupNames(Arrays.asList("g1"));
        testInstance.setHandles(Arrays.asList("h1"));
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        PagedResult<Group> pr = new PagedResult<Group>();
        List<Group> foundGroups = new ArrayList<Group>();
        Group group = new Group();
        GroupMember gm = new GroupMember();
        gm.setUserId(100L);
        group.setGroupMembers(Arrays.asList(new GroupMember(), gm));
        foundGroups.add(group);
        foundGroups.add(new Group());
        pr.setValues(foundGroups);
        Capture<GroupSearchCriteria> criteria = new Capture<GroupSearchCriteria>();
        Capture<Integer> id = new Capture<Integer>();
        EasyMock.expect(
                groupService.search(EasyMock.capture(criteria), EasyMock
                        .capture(id), EasyMock.capture(id))).andReturn(pr);
        testInstance.setGroupService(groupService);

        EasyMock.replay(groupService);
        testInstance.execute();
        EasyMock.verify(groupService);
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_checkInit() throws Exception {
        testInstance.setAuditService(EasyMock
                .createNiceMock(GroupAuditService.class));
        testInstance.setAuthorizationService(EasyMock
                .createNiceMock(AuthorizationService.class));

        testInstance.setCustomerAdministratorService(EasyMock
                .createNiceMock(CustomerAdministratorService.class));
        testInstance.setGroupService(EasyMock
                .createNiceMock(GroupService.class));
        testInstance.setGroupInvitationService(EasyMock
                .createNiceMock(GroupInvitationService.class));
        testInstance.setUserService(EasyMock.createNiceMock(UserService.class));
        testInstance.setRegistrationUrl("registrationUrl");
        testInstance.setAcceptRejectUrlBase("acceptRejectUrlBase");
        testInstance.checkInit();

    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     * <p>
     * RegistrationUrl is empty,SecurityGroupsActionConfigurationException will
     * throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void test_checkInit_fail() throws Exception {
        testInstance.setAuditService(EasyMock
                .createNiceMock(GroupAuditService.class));
        testInstance.setAuthorizationService(EasyMock
                .createNiceMock(AuthorizationService.class));

        testInstance.setCustomerAdministratorService(EasyMock
                .createNiceMock(CustomerAdministratorService.class));
        testInstance.setGroupService(EasyMock
                .createNiceMock(GroupService.class));
        testInstance.setGroupInvitationService(EasyMock
                .createNiceMock(GroupInvitationService.class));
        testInstance.setUserService(EasyMock.createNiceMock(UserService.class));
        testInstance.setRegistrationUrl("  ");
        testInstance.setAcceptRejectUrlBase("acceptRejectUrlBase");
        testInstance.checkInit();

    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#input()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_input() throws Exception {
        AuthorizationService authorizationService = EasyMock
                .createNiceMock(AuthorizationService.class);
        testInstance.setAuthorizationService(authorizationService);
        EasyMock.expect(
                authorizationService.getGroupIdsOfFullPermissionsUser(EasyMock
                        .capture(new Capture<Long>()))).andReturn(
                Arrays.asList(1L));

        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        PagedResult<Group> pr = new PagedResult<Group>();
        List<Group> foundGroups = new ArrayList<Group>();
        Group g1 = new Group();
        Client c1 = new Client();
        c1.setId(100L);
        g1.setClient(c1);
        foundGroups.add(g1);
        Group g2 = new Group();
        foundGroups.add(g2);
        Group g3 = new Group();
        Client c2 = new Client();
        c2.setId(100L);
        g3.setClient(c2);
        foundGroups.add(g3);
        pr.setValues(foundGroups);
        Capture<GroupSearchCriteria> criteria = new Capture<GroupSearchCriteria>();
        Capture<Integer> id = new Capture<Integer>();
        EasyMock.expect(
                groupService.search(EasyMock.capture(criteria), EasyMock
                        .capture(id), EasyMock.capture(id))).andReturn(pr);

        List<Client> allClients = new ArrayList<Client>();
        Client c = new Client();
        allClients.add(c);
        CustomerAdministratorService customerService = EasyMock
                .createNiceMock(CustomerAdministratorService.class);
        Capture<Long> i = new Capture<Long>();
        EasyMock.expect(
                customerService.getCustomersForAdministrator(EasyMock
                        .capture(i))).andReturn(allClients);
        testInstance.setCustomerAdministratorService(customerService);

        testInstance.setGroupService(groupService);

        EasyMock.replay(authorizationService);
        EasyMock.replay(customerService);
        EasyMock.replay(groupService);
        testInstance.input();
        EasyMock.verify(groupService);
        EasyMock.verify(customerService);
        EasyMock.verify(authorizationService);
    }

    /**
     * <p>
     * Accuracy test for {@link ClientsPrepopulatingBaseAction#input()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_input2() throws Exception {
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        PagedResult<Group> pr = new PagedResult<Group>();
        List<Group> foundGroups = new ArrayList<Group>();
        foundGroups.add(new Group());
        pr.setValues(foundGroups);
        Capture<GroupSearchCriteria> criteria = new Capture<GroupSearchCriteria>();
        Capture<Integer> id = new Capture<Integer>();
        EasyMock.expect(
                groupService.search(EasyMock.capture(criteria), EasyMock
                        .capture(id), EasyMock.capture(id))).andReturn(pr);
        testInstance.setGroupService(groupService);

        AuthorizationService authorizationService = EasyMock
                .createNiceMock(AuthorizationService.class);
        EasyMock.expect(
                authorizationService.isAdministrator(EasyMock
                        .capture(new Capture<Long>()))).andReturn(true);
        testInstance.setAuthorizationService(authorizationService);

        EasyMock.replay(authorizationService);
        EasyMock.replay(groupService);
        testInstance.input();
        EasyMock.verify(groupService);
        EasyMock.verify(authorizationService);
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#input()}.
     * </p>
     * <p>
     * Fail to do searching, SecurityGroupsActionException should throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_input_fail_1() throws Exception {
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        PagedResult<Group> pr = new PagedResult<Group>();
        List<Group> foundGroups = new ArrayList<Group>();
        foundGroups.add(new Group());
        pr.setValues(foundGroups);
        Capture<GroupSearchCriteria> criteria = new Capture<GroupSearchCriteria>();
        Capture<Integer> id = new Capture<Integer>();
        EasyMock.expect(
                groupService.search(EasyMock.capture(criteria), EasyMock
                        .capture(id), EasyMock.capture(id))).andThrow(
                new SecurityGroupException("test"));
        testInstance.setGroupService(groupService);
        EasyMock.replay(groupService);
        testInstance.input();
        EasyMock.verify(groupService);
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#input()}.
     * </p>
     * <p>
     * Page result contain null elements, SecurityGroupsActionException should
     * throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_input_fail_2() throws Exception {
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        PagedResult<Group> pr = new PagedResult<Group>();
        pr.setValues(Arrays.asList((Group) (null)));
        Capture<GroupSearchCriteria> criteria = new Capture<GroupSearchCriteria>();
        Capture<Integer> id = new Capture<Integer>();
        EasyMock.expect(
                groupService.search(EasyMock.capture(criteria), EasyMock
                        .capture(id), EasyMock.capture(id))).andReturn(pr);
        testInstance.setGroupService(groupService);
        EasyMock.replay(groupService);
        testInstance.input();
        EasyMock.verify(groupService);
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#getGroups()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getGroups() throws Exception {
        Assert.assertNull("Should be null", testInstance.getGroups());
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#setGroupService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setGroupService() throws Exception {
        testInstance.setGroupService(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "groupService"));
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#setUserService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setUserService() throws Exception {
        testInstance.setUserService(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "userService"));
    }

    /**
     * <p>
     * Accuracy test for
     * {@link SendInvitationAction#setCustomerAdministratorService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCustomerAdministratorService() throws Exception {
        testInstance.setCustomerAdministratorService(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "customerAdministratorService"));
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#setGroupNames()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setGroupNames() throws Exception {
        testInstance.setGroupNames(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "groupNames"));
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#setHandles()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setHandles() throws Exception {
        testInstance.setHandles(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "handles"));
    }

    /**
     * <p>
     * Accuracy test for
     * {@link SendInvitationAction#setGroupInvitationService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setGroupInvitationService() throws Exception {
        testInstance.setGroupInvitationService(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "groupInvitationService"));
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#setRegistrationUrl()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setRegistrationUrl() throws Exception {
        testInstance.setRegistrationUrl(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "registrationUrl"));
    }

    /**
     * <p>
     * Accuracy test for {@link SendInvitationAction#setAcceptRejectUrlBase()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setAcceptRejectUrlBase() throws Exception {
        testInstance.setAcceptRejectUrlBase(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "acceptRejectUrlBase"));
    }
}