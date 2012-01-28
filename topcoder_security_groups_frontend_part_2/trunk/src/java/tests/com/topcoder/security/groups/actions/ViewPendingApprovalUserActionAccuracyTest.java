/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.topcoder.security.groups.actions.ViewPendingApprovalUserAction;
import com.topcoder.security.groups.actions.accuracytests.MockGroupAuditService;
import com.topcoder.security.groups.actions.accuracytests.MockGroupInvitationService;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Accuracy test for ViewPendingApprovalUserAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewPendingApprovalUserActionAccuracyTest {
    /**
     * Represents the instance of ViewPendingApprovalUserAction used in test.
     */
    private ViewPendingApprovalUserAction instance;

    /**
     * Sets up for each test.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ViewPendingApprovalUserAction();

        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        instance.setCriteria(criteria);
        instance.setClientId(1L);
        instance.setPage(7);
        instance.setPageSize(3);
    }

    /**
     * Tears down for each test.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
        MockGroupInvitationService.RECORDS.clear();
        MockGroupAuditService.RECORDS.clear();
    }

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ViewPendingApprovalUserActionAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for ViewPendingApprovalUserAction(). The instance should be
     * created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("The instance should be created.", instance);
    }

    /**
     * <p>
     * Accuracy test for execute method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute() throws Exception {
        GroupInvitationService groupInvitationService = new MockGroupInvitationService();
        groupInvitationService.addInvitation(groupInvitationService.getInvitation(101));
        instance.setGroupInvitationService(groupInvitationService);

        UserService userService = EasyMock.createMock(UserService.class);
        EasyMock.expect(userService.get(EasyMock.anyLong())).andReturn(new UserDTO());
        instance.setUserService(userService);

        EasyMock.replay(userService);
        assertEquals("The return value is incorrect.", Action.SUCCESS, instance.execute());
        EasyMock.verify(userService);
    }

    /**
     * <p>
     * Accuracy test for execute method, when inviteeHandle is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate1() throws Exception {
        instance.getCriteria().setInviteeHandle(" ");
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.inviteeHandle"));
    }

    /**
     * <p>
     * Accuracy test for execute method, when inviteeHandle is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate2() throws Exception {
        instance.getCriteria().setInviteeHandle("1234567890123456789012345678901234567890123456");
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.inviteeHandle"));
    }

    /**
     * <p>
     * Accuracy test for execute method, when inviteeEmail is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate3() throws Exception {
        instance.getCriteria().setInviteeEmail("  ");
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.inviteeEmail"));
    }

    /**
     * <p>
     * Accuracy test for execute method, when inviteeEmail is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate4() throws Exception {
        instance.getCriteria().setInviteeEmail("1234567890123456789012345678901234567890123456");
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.inviteeEmail"));
    }

    /**
     * <p>
     * Accuracy test for execute method, when inviteeEmail is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate5() throws Exception {
        instance.getCriteria().setInviteeEmail("test@");
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.inviteeEmail"));
    }

    /**
     * <p>
     * Accuracy test for execute method, when sendDateFrom/sendDateTo is
     * invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate6() throws Exception {
        instance.getCriteria().setSentDateFrom(new Date());
        instance.getCriteria().setSentDateTo(new Date(0));
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.sendDate"));
    }

    /**
     * <p>
     * Accuracy test for execute method, when page is negative.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate8() throws Exception {
        instance.setPage(-1);
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for execute method, when pageSize is negative.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate9() throws Exception {
        instance.setPageSize(-2);
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for checkInit(). No exception should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCheckInit() throws Exception {
        instance.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
        instance.setAuthorizationService(EasyMock.createNiceMock(AuthorizationService.class));

        instance.setUserService(EasyMock.createNiceMock(UserService.class));
        instance.setClientService(EasyMock.createNiceMock(ClientService.class));
        instance.setCustomerAdministratorService(EasyMock
            .createNiceMock(CustomerAdministratorService.class));
        instance.setGroupInvitationService(EasyMock.createNiceMock(GroupInvitationService.class));

        instance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for input(). When the user is not administrator.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testInput_NotAdmin() throws Exception {
        ClientService clientService = EasyMock.createNiceMock(ClientService.class);
        List<Client> allClients = new ArrayList<Client>();
        Client c = new Client();
        c.setId(1);
        allClients.add(c);

        Client c2 = new Client();
        c2.setId(2);
        allClients.add(c2);
        EasyMock.expect(clientService.getAllClients()).andReturn(allClients);
        instance.setClientService(clientService);

        AuthorizationService authService = EasyMock.createNiceMock(AuthorizationService.class);
        instance.setAuthorizationService(authService);
        EasyMock.expect(authService.isAdministrator(1L)).andReturn(false).anyTimes();

        CustomerAdministratorService caService = EasyMock
            .createMock(CustomerAdministratorService.class);
        Client c1 = new Client();
        c1.setId(1);
        EasyMock.expect(caService.getCustomersForAdministrator(1L)).andReturn(Arrays.asList(c1))
            .anyTimes();

        instance.setCustomerAdministratorService(caService);

        EasyMock.replay(authService, clientService, caService);
        assertEquals("The result is incorrect.", Action.INPUT, instance.input());
        EasyMock.verify(authService, clientService, caService);

        assertEquals("Wrong clients", 1, instance.getClients().size());
        assertEquals("Wrong clients", 1, instance.getClients().get(0).getId());
    }

    /**
     * <p>
     * Accuracy test for input(). When the user is not administrator.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testInput_Admin() throws Exception {
        ClientService clientService = EasyMock.createNiceMock(ClientService.class);
        List<Client> allClients = new ArrayList<Client>();
        Client c = new Client();
        allClients.add(c);
        EasyMock.expect(clientService.getAllClients()).andReturn(allClients);
        instance.setClientService(clientService);

        AuthorizationService authService = EasyMock.createNiceMock(AuthorizationService.class);
        instance.setAuthorizationService(authService);
        EasyMock.expect(authService.isAdministrator(1L)).andReturn(true).anyTimes();

        EasyMock.replay(authService);
        EasyMock.replay(clientService);
        assertEquals("The result is incorrect.", Action.INPUT, instance.input());
        EasyMock.verify(clientService);
        EasyMock.verify(authService);
    }
}