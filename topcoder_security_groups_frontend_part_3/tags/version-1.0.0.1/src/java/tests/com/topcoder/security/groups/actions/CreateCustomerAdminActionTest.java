/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Unit tests for the {@link CreateCustomerAdminAction}.
 * </p>
 *
 * @author progloco
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { CreateCustomerAdminAction.class, ServletActionContext.class })
public class CreateCustomerAdminActionTest {
    /** Represents the instance used to test again. */
    private CreateCustomerAdminAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new CreateCustomerAdminAction();
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
        return new JUnit4TestAdapter(CreateCustomerAdminActionTest.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link CreateCustomerAdminAction#CreateCustomerAdminAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCreateCustomerAdminAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>CreateCustomerAdminAction</code>
     * subclasses <code>ClientsPrepopulatingBaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "CreateCustomerAdminAction does not subclass ClientsPrepopulatingBaseAction.",
                CreateCustomerAdminAction.class.getSuperclass() == ClientsPrepopulatingBaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link CreateCustomerAdminAction#setHandle()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setHandle() throws Exception {
        testInstance.setHandle("handle");
        Assert.assertEquals("Should be 'handle'", "handle", TestHelper
                .getField(testInstance, "handle"));
    }

    /**
     * <p>
     * Accuracy test for {@link CreateCustomerAdminAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute() throws Exception {
        testInstance.setClientId(22L);
        ClientService clientService = EasyMock
                .createNiceMock(ClientService.class);
        EasyMock.expect(clientService.get(22L)).andReturn(new Client());
        testInstance.setClientService(clientService);

        CustomerAdministratorService caService = EasyMock
                .createNiceMock(CustomerAdministratorService.class);
        Capture<CustomerAdministrator> c = new Capture<CustomerAdministrator>();
        EasyMock.expect(caService.add(EasyMock.capture(c))).andReturn(1L);
        testInstance.setCustomerAdministratorService(caService);

        UserService userService = EasyMock.createNiceMock(UserService.class);
        testInstance.setHandle("tc");
        testInstance.setUserService(userService);
        List<UserDTO> users = new ArrayList<UserDTO>();
        users.add(new UserDTO());
        EasyMock.expect(userService.search("tc")).andReturn(users).anyTimes();

        GroupAuditService auditService = EasyMock
                .createNiceMock(GroupAuditService.class);
        Capture<GroupAuditRecord> g = new Capture<GroupAuditRecord>();
        EasyMock.expect(auditService.add(EasyMock.capture(g))).andReturn(1L);
        testInstance.setAuditService(auditService);

        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress");
        EasyMock.replay(request);
        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request);

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(clientService);
        EasyMock.replay(caService);
        EasyMock.replay(userService);
        EasyMock.replay(auditService);
        testInstance.execute();
        EasyMock.verify(auditService);
        EasyMock.verify(userService);
        EasyMock.verify(caService);
        EasyMock.verify(clientService);
        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);
    }

    /**
     * <p>
     * Failure test for {@link CreateCustomerAdminAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail() throws Exception {
        UserService userService = EasyMock.createNiceMock(UserService.class);
        testInstance.setHandle("tc");
        testInstance.setUserService(userService);
        List<UserDTO> users = new ArrayList<UserDTO>();
        EasyMock.expect(userService.search("tc")).andReturn(users);
        EasyMock.replay(userService);
        testInstance.execute();
        EasyMock.verify(userService);
    }

    /**
     * <p>
     * Accuracy test for {@link CreateCustomerAdminAction#checkInit()}.
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

        testInstance.setUserService(EasyMock.createNiceMock(UserService.class));
        testInstance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for {@link CreateCustomerAdminAction#setUserService()}.
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
     * Accuracy test for {@link CreateCustomerAdminAction#setClientId()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setClientId() throws Exception {
        testInstance.setClientId(1000L);
        Assert.assertEquals("Should be equals.", 1000L, TestHelper.getField(
                testInstance, "clientId"));
    }
}