/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stresstests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.topcoder.security.groups.actions.ViewPendingApprovalUserAction;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.UserDTO;
/**
 * Stress tests for the {@link ViewPendingApprovalUserAction}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
public class ViewPendingApprovalUserActionStressTest extends TestCase {
    /**
     * Represents the instance used for test.
     */
    private ViewPendingApprovalUserAction instance;
    /**
     * Initializes the test environment.
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
        instance = new ViewPendingApprovalUserAction();
        instance.setClientId(1L);
        GroupInvitation groupInvitation = new GroupInvitation();
        groupInvitation.setStatus(InvitationStatus.APPROVAL_ACCEPTED);
        groupInvitation.setGroupMember(new GroupMember());
        IMocksControl control = EasyMock.createControl();
        UserService userService = control.createMock(UserService.class);
        instance.setUserService(userService);
        ClientService clientService = control.createMock(ClientService.class);
        instance.setClientService(clientService);
        CustomerAdministratorService customerAdministratorService
            = control.createMock(CustomerAdministratorService.class);
        instance.setCustomerAdministratorService(customerAdministratorService);
        instance.setCriteria(new InvitationSearchCriteria());
        GroupInvitationService groupInvitationService = control.createMock(GroupInvitationService.class);
        instance.setGroupInvitationService(groupInvitationService);
        PagedResult<GroupInvitation> result = new PagedResult<GroupInvitation>();
        List<GroupInvitation> values = new ArrayList<GroupInvitation>();
        values.add(new GroupInvitation());
        values.get(0).setGroupMember(new GroupMember());
        result.setValues(values);
        groupInvitationService.search(EasyMock.anyObject(InvitationSearchCriteria.class), EasyMock.anyLong(),
            EasyMock.anyInt(), EasyMock.anyInt());
        EasyMock.expectLastCall().andReturn(result).anyTimes();
        userService.get(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(new UserDTO()).anyTimes();
        control.replay();
    }
    /**
     * Stress test for {@link ViewPendingApprovalUserAction#execute()}.
     * @throws Exception to JUnit.
     */
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
