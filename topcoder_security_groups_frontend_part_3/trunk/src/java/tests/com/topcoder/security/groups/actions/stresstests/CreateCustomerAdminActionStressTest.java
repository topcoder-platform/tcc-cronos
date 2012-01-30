/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stresstests;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.topcoder.security.groups.actions.CreateCustomerAdminAction;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.CustomerAdministrator;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * Stress tests for the {@link CreateCustomerAdminAction}.
 *
 * @author BlackMagic
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ CreateCustomerAdminActionStressTest.class, ServletActionContext.class })
public class CreateCustomerAdminActionStressTest {
    /**
     * Represents the instance used for test.
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
        return new JUnit4TestAdapter(AccessAuditingInfoActionStressTest.class);
    }
    /**
     * Initializes the test environment.
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new CreateCustomerAdminAction();
        instance.setClientId(1L);
        instance.setHandle("test");

        IMocksControl control = EasyMock.createControl();
        UserService userService = control.createMock(UserService.class);
        instance.setUserService(userService);
        userService.search(EasyMock.anyObject(String.class));
        List<UserDTO> users = new ArrayList<UserDTO>();
        users.add(new UserDTO());
        EasyMock.expectLastCall().andReturn(users).anyTimes();
        ClientService clientService = control.createMock(ClientService.class);
        instance.setClientService(clientService);
        clientService.get(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(new Client()).anyTimes();
        CustomerAdministratorService customerAdministratorService
            = control.createMock(CustomerAdministratorService.class);
        instance.setCustomerAdministratorService(customerAdministratorService);
        customerAdministratorService.add(EasyMock.anyObject(CustomerAdministrator.class));
        EasyMock.expectLastCall().andReturn(1L).anyTimes();
        GroupAuditService auditService = control.createMock(GroupAuditService.class);
        auditService.add(EasyMock.anyObject(GroupAuditRecord.class));
        EasyMock.expectLastCall().andReturn(1L).anyTimes();
        instance.setAuditService(auditService);
        HttpServletRequest request = control.createMock(HttpServletRequest.class);
        PowerMock.mockStatic(ServletActionContext.class);
        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request)
            .anyTimes();
        EasyMock.expect(request.getRemoteAddr()).andReturn("192.168.0.1").anyTimes();
        PowerMock.replay(ServletActionContext.class);

        control.replay();
    }
    /**
     * Stress test for {@link CreateCustomerAdminAction#execute()}.
     * @throws Exception to JUnit.
     */
    @Test
    public void test_execute() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; ++i) {
            instance.execute();
        }
        long elapse = System.currentTimeMillis() - start;
        System.out.println("Execute execute() for " + StressTests.TIMES
            + " times, elapse " + elapse + "ms");
    }
}
