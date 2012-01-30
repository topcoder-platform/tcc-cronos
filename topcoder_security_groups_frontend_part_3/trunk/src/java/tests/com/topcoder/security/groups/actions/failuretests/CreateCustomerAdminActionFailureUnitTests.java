/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import java.util.Arrays;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.actions.CreateCustomerAdminAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionConfigurationException;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Failure unit tests for the {@link CreateCustomerAdminAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class CreateCustomerAdminActionFailureUnitTests {
    /**
     * <p>
     * private CreateCustomerAdminAction instance used in testing.
     * </p>
     */
    private CreateCustomerAdminAction instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CreateCustomerAdminActionFailureUnitTests.class);
    }

    /**
     * Sets up test environment.
     */
    @Before
    public void setUp() {
        instance = new CreateCustomerAdminAction();
        instance.setAuditService(EasyMock.createMock(GroupAuditService.class));
        instance.setAuthorizationService(EasyMock.createMock(AuthorizationService.class));
        instance.setClientService(EasyMock.createMock(ClientService.class));
        instance.setCustomerAdministratorService(EasyMock.createMock(CustomerAdministratorService.class));
        instance.setUserService(EasyMock.createMock(UserService.class));
    }

    /**
     * Tears down test environment.
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Failure test for {@link CreateCustomerAdminAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testExecuteFail() throws Exception {
        UserService userService = EasyMock.createMock(UserService.class);
        instance.setUserService(userService);
        instance.setHandle("handle");
        EasyMock.expect(userService.search("handle")).andThrow(new SecurityGroupException("error"));
        EasyMock.replay(userService);
        try {
            instance.execute();
        } finally {
            EasyMock.verify(userService);
        }
    }

    /**
     * <p>
     * Failure test for {@link CreateCustomerAdminAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testExecuteFai2() throws Exception {
        UserService userService = EasyMock.createMock(UserService.class);
        instance.setUserService(userService);
        instance.setHandle("handle");
        EasyMock.expect(userService.search("handle")).andReturn(Arrays.asList(new UserDTO(), new UserDTO()));
        EasyMock.replay(userService);
        try {
            instance.execute();
        } finally {
            EasyMock.verify(userService);
        }
    }

    /**
     * <p>
     * Failure test for {@link CreateCustomerAdminAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure() throws Exception {
        instance.setUserService(null);
        instance.checkInit();
    }
}
