/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * Unit tests for the {@link ClientsPrepopulatingBaseAction}.
 * </p>
 *
 * @author progloco
 * @version 1.0
 */
public class ClientsPrepopulatingBaseActionTest {
    /** Represents the instance used to test again. */
    private ClientsPrepopulatingBaseAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() throws Exception {
        testInstance = new ClientsPrepopulatingBaseAction() {
        };
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
        return new JUnit4TestAdapter(ClientsPrepopulatingBaseActionTest.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ClientsPrepopulatingBaseAction#ClientsPrepopulatingBaseAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testClientsPrepopulatingBaseAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ClientsPrepopulatingBaseAction</code>
     * subclasses <code>BaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "ClientsPrepopulatingBaseAction does not subclass BaseAction.",
                ClientsPrepopulatingBaseAction.class.getSuperclass() == BaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ClientsPrepopulatingBaseAction#checkInit()}.
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

        testInstance.setClientService(EasyMock
                .createNiceMock(ClientService.class));
        testInstance.setCustomerAdministratorService(EasyMock
                .createNiceMock(CustomerAdministratorService.class));

        testInstance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for {@link ClientsPrepopulatingBaseAction#input()}.
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
     * Accuracy test for {@link ClientsPrepopulatingBaseAction#input()}.
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
     * Failure test for {@link ClientsPrepopulatingBaseAction#input()}.
     * </p>
     * <p>
     * Client service throw exception, SecurityGroupException should throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_input_fail() throws Exception {
        AuthorizationService authorizationService = EasyMock
                .createNiceMock(AuthorizationService.class);
        testInstance.setAuthorizationService(authorizationService);

        ClientService clientService = EasyMock
                .createNiceMock(ClientService.class);
        List<Client> allClients = new ArrayList<Client>();
        Client c = new Client();
        allClients.add(c);
        EasyMock.expect(clientService.getAllClients()).andThrow(
                new SecurityGroupException("test"));
        testInstance.setClientService(clientService);
        EasyMock.replay(clientService);
        testInstance.input();
        EasyMock.verify(clientService);
    }

    /**
     * <p>
     * Accuracy test for {@link ClientsPrepopulatingBaseAction#getClients()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getClients() throws Exception {
        Assert.assertNull("Should be null", testInstance.getClients());
    }

    /**
     * <p>
     * Accuracy test for {@link ClientsPrepopulatingBaseAction#setClients()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setClients() throws Exception {
        testInstance.setClients(new ArrayList<Client>());
        Assert.assertNotNull("Should not be null", testInstance.getClients());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ClientsPrepopulatingBaseAction#getClientService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getClientService() throws Exception {
        Assert.assertNull("Should be null", testInstance.getClientService());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ClientsPrepopulatingBaseAction#setClientService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setClientService() throws Exception {
        testInstance.setClientService(EasyMock.createMock(ClientService.class));
        Assert.assertNotNull("Should not be null", testInstance
                .getClientService());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ClientsPrepopulatingBaseAction#getCustomerAdministratorService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getCustomerAdministratorService() throws Exception {
        Assert.assertNull("Should be null", testInstance
                .getCustomerAdministratorService());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ClientsPrepopulatingBaseAction#setCustomerAdministratorService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCustomerAdministratorService() throws Exception {
        testInstance.setCustomerAdministratorService(EasyMock
                .createMock(CustomerAdministratorService.class));
        Assert.assertNotNull("Should not be null", testInstance
                .getCustomerAdministratorService());
    }
}