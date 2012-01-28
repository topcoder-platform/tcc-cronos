/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Unit tests for the {@link ViewPendingApprovalUserAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewPendingApprovalUserActionTest {
    /** Represents the instance used to test again. */
    private ViewPendingApprovalUserAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ViewPendingApprovalUserAction();
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
        return new JUnit4TestAdapter(ViewPendingApprovalUserActionTest.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ViewPendingApprovalUserAction#ViewPendingApprovalUserAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testViewPendingApprovalUserAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ViewPendingApprovalUserAction</code>
     * subclasses <code>GroupInvitationSearchBaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "ViewPendingApprovalUserAction does not subclass GroupInvitationSearchBaseAction.",
                ViewPendingApprovalUserAction.class.getSuperclass() == GroupInvitationSearchBaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate1() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria.setInviteeEmail("");

        criteria.setSentDateFrom(new Date(2));
        criteria.setSentDateTo(new Date(1));
        criteria.setAcceptedDateFrom(new Date(2));
        criteria.setAcceptedDateTo(new Date(1));
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 2", 2, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate2() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria
                .setInviteeHandle("aaaaaaaaaxxxxxaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        criteria.setInviteeEmail("");
        criteria.setSentDateFrom(new Date(2));
        criteria.setSentDateTo(new Date(1));
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 3", 3, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate3() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria
                .setInviteeHandle("aaaaaaaaaxxxxxaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        criteria.setInviteeEmail("aaxxx");
        criteria.setSentDateFrom(new Date(2));
        criteria.setSentDateTo(new Date(1));
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 3", 3, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate4() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria.setInviteeHandle("xx");
        criteria.setInviteeEmail("aa@bb.com");
        criteria.setSentDateFrom(new Date(0));
        criteria.setSentDateTo(new Date(1));
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 0", 0, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate5() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria.setInviteeHandle("xx");
        criteria.setInviteeEmail(null);
        criteria.setSentDateFrom(new Date(0));
        criteria.setSentDateTo(new Date(1));
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 0", 0, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        testInstance.setCriteria(criteria);
        testInstance.setClientId(1L);
        testInstance.setPage(2);
        testInstance.setPageSize(3);
        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
        GroupInvitation groupInvitation = new GroupInvitation();
        GroupMember m = new GroupMember();
        m.setUserId(5L);
        groupInvitation.setGroupMember(m);
        PagedResult<GroupInvitation> pagedResult = new PagedResult<GroupInvitation>();
        pagedResult.setValues(Arrays.asList(groupInvitation));

        EasyMock.expect(giService.search(criteria, 1L, 3, 2)).andReturn(
                pagedResult).anyTimes();
        testInstance.setGroupInvitationService(giService);

        UserService userService = EasyMock.createNiceMock(UserService.class);
        EasyMock.expect(userService.get(5L)).andReturn(new UserDTO());
        testInstance.setUserService(userService);

        EasyMock.replay(giService);
        EasyMock.replay(userService);
        testInstance.execute();
        EasyMock.verify(userService);
        EasyMock.verify(giService);
    }

    /**
     * <p>
     * Failure test for {@link ViewPendingApprovalUserAction#execute()}.
     * </p>
     * <p>
     * Fail to do the search, SecurityGroupsActionException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail1() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        testInstance.setCriteria(criteria);
        testInstance.setClientId(1L);
        testInstance.setPage(2);
        testInstance.setPageSize(3);
        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
        GroupInvitation groupInvitation = new GroupInvitation();
        GroupMember m = new GroupMember();
        m.setUserId(5L);
        groupInvitation.setGroupMember(m);
        PagedResult<GroupInvitation> pagedResult = new PagedResult<GroupInvitation>();
        pagedResult.setValues(Arrays.asList(groupInvitation));

        EasyMock.expect(giService.search(criteria, 1L, 3, 2)).andThrow(
                new SecurityGroupException("test"));
        testInstance.setGroupInvitationService(giService);

        EasyMock.replay(giService);
        testInstance.execute();
        EasyMock.verify(giService);
    }

    /**
     * <p>
     * Failure test for {@link ViewPendingApprovalUserAction#execute()}.
     * </p>
     * <p>
     * Fail to do the search, SecurityGroupsActionException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail2() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        testInstance.setCriteria(criteria);
        testInstance.setClientId(1L);
        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
        GroupInvitation groupInvitation = new GroupInvitation();
        GroupMember m = new GroupMember();
        m.setUserId(5L);
        groupInvitation.setGroupMember(m);

        PagedResult<GroupInvitation> pagedResult = new PagedResult<GroupInvitation>();
        pagedResult.setValues(Arrays.asList(invitation));
        Capture<InvitationSearchCriteria> c = new Capture<InvitationSearchCriteria>();
        Capture<Long> l = new Capture<Long>();
        Capture<Integer> i = new Capture<Integer>();
        EasyMock.expect(giService.search(EasyMock.capture(c), EasyMock.capture(l), 
                EasyMock.capture(i), EasyMock.capture(i))).andReturn(pagedResult)
                .anyTimes();
        testInstance.setGroupInvitationService(giService);

        EasyMock.replay(giService);
        testInstance.execute();
        EasyMock.verify(giService);
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#checkInit()}.
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

        testInstance.setUserService(EasyMock.createNiceMock(UserService.class));
        testInstance.setClientService(EasyMock
                .createNiceMock(ClientService.class));
        testInstance.setCustomerAdministratorService(EasyMock
                .createNiceMock(CustomerAdministratorService.class));

        testInstance.setGroupInvitationService(EasyMock
                .createNiceMock(GroupInvitationService.class));

        testInstance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#input()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_input1() throws Exception {
        AuthorizationService authorizationService = EasyMock
                .createNiceMock(AuthorizationService.class);
        testInstance.setAuthorizationService(authorizationService);
        EasyMock.expect(
                authorizationService.getGroupIdsOfFullPermissionsUser(EasyMock
                        .capture(new Capture<Long>()))).andReturn(
                Arrays.asList(1L)).anyTimes();

        ClientService clientService = EasyMock
                .createNiceMock(ClientService.class);
        List<Client> allClients = new ArrayList<Client>();
        Client c = new Client();
        allClients.add(c);
        EasyMock.expect(clientService.getAllClients()).andReturn(allClients);
        testInstance.setClientService(clientService);

        CustomerAdministratorService customerService = EasyMock
                .createNiceMock(CustomerAdministratorService.class);
        Capture<Long> id = new Capture<Long>();
        EasyMock.expect(
                customerService.getCustomersForAdministrator(EasyMock
                        .capture(id))).andReturn(allClients);
        testInstance.setCustomerAdministratorService(customerService);

        EasyMock.replay(authorizationService);
        EasyMock.replay(customerService);
        EasyMock.replay(clientService);
        testInstance.input();
        EasyMock.verify(clientService);
        EasyMock.verify(customerService);
        EasyMock.verify(authorizationService);
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#input()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_input2() throws Exception {
        AuthorizationService authorizationService = EasyMock
                .createNiceMock(AuthorizationService.class);
        EasyMock.expect(
                authorizationService.isAdministrator(EasyMock
                        .capture(new Capture<Long>()))).andReturn(true);
        testInstance.setAuthorizationService(authorizationService);

        ClientService clientService = EasyMock
                .createNiceMock(ClientService.class);
        List<Client> allClients = new ArrayList<Client>();
        Client c = new Client();
        allClients.add(c);
        EasyMock.expect(clientService.getAllClients()).andReturn(allClients);
        testInstance.setClientService(clientService);

        EasyMock.replay(authorizationService);
        EasyMock.replay(clientService);
        testInstance.input();
        EasyMock.verify(clientService);
        EasyMock.verify(authorizationService);
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#setClientId()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setClientId() throws Exception {
        testInstance.setClientId(100L);
        assertEquals("Should be 100", 100L, TestHelper.getField(testInstance,
                "clientId"));
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#getUsers()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getUsers() throws Exception {
        assertNull("Should be null", testInstance.getUsers());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#getClients()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getClients() throws Exception {
        assertNull("Should be null", testInstance.getClients());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewPendingApprovalUserAction#setUserService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setUserService() throws Exception {
        UserService userService = EasyMock.createMock(UserService.class);
        testInstance.setUserService(userService);
        assertEquals("Should be equals", userService, TestHelper.getField(
                testInstance, "userService"));
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ViewPendingApprovalUserAction#setClientService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setClientService() throws Exception {
        ClientService clientService = EasyMock.createMock(ClientService.class);
        testInstance.setClientService(clientService);
        assertEquals("Should be equals", clientService, TestHelper.getField(
                testInstance, "clientService"));
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ViewPendingApprovalUserAction#setCustomerAdministratorService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCustomerAdministratorService() throws Exception {
        CustomerAdministratorService customerAdministratorService = EasyMock
                .createMock(CustomerAdministratorService.class);
        testInstance
                .setCustomerAdministratorService(customerAdministratorService);
        assertEquals("Should be equals", customerAdministratorService,
                TestHelper.getField(testInstance,
                        "customerAdministratorService"));
    }
}