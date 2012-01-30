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

import com.topcoder.security.groups.actions.SendInvitationAction;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.UserDTO;
/**
 * Stress tests for the {@link SendInvitationAction}.
 *
 * @author BlackMagic
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SendInvitationActionStressTest.class, ServletActionContext.class })
public class SendInvitationActionStressTest {
    /**
     * Represents the instance used for test.
     */
    private SendInvitationAction instance;
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SendInvitationActionStressTest.class);
    }
    /**
     * Initializes the test environment.
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new SendInvitationAction();
        List<String> groupNames = new ArrayList<String>();
        instance.setGroupNames(groupNames);
        groupNames.add("test");
        List<String> handles = new ArrayList<String>();
        instance.setHandles(handles);
        handles.add("handle");

        IMocksControl control = EasyMock.createControl();
        GroupInvitationService groupInvitationService = control.createMock(GroupInvitationService.class);
        instance.setGroupInvitationService(groupInvitationService);
        GroupService groupService = control.createMock(GroupService.class);
        instance.setGroupService(groupService);
        UserService userService = control.createMock(UserService.class);
        instance.setUserService(userService);
        CustomerAdministratorService customerAdministratorService
            = control.createMock(CustomerAdministratorService.class);
        instance.setCustomerAdministratorService(customerAdministratorService);
        instance.setRegistrationUrl("register");
        instance.setAcceptRejectUrlBase("accept");
        groupService.search(EasyMock.anyObject(GroupSearchCriteria.class), EasyMock.anyInt(), EasyMock.anyInt());
        List<Group> groups = new ArrayList<Group>();
        groups.add(new Group());
        groups.get(0).setGroupMembers(new ArrayList<GroupMember>());
        PagedResult<Group> result = new PagedResult<Group>();
        result.setValues(groups);
        EasyMock.expectLastCall().andReturn(result).anyTimes();
        userService.search(EasyMock.anyObject(String.class));
        List<UserDTO> users = new ArrayList<UserDTO>();
        users.add(new UserDTO());
        EasyMock.expectLastCall().andReturn(users).anyTimes();
        groupService.update(EasyMock.anyObject(Group.class));
        EasyMock.expectLastCall().anyTimes();
        GroupAuditService auditService = control.createMock(GroupAuditService.class);
        auditService.add(EasyMock.anyObject(GroupAuditRecord.class));
        EasyMock.expectLastCall().andReturn(1L).anyTimes();
        instance.setAuditService(auditService);
        groupInvitationService.addInvitation(EasyMock.anyObject(GroupInvitation.class));
        EasyMock.expectLastCall().anyTimes();
        groupInvitationService.sendInvitation(EasyMock.anyObject(GroupInvitation.class),
            EasyMock.anyObject(String.class), EasyMock.anyObject(String.class), EasyMock.anyObject(String.class));
        EasyMock.expectLastCall().anyTimes();
        HttpServletRequest request = control.createMock(HttpServletRequest.class);
        PowerMock.mockStatic(ServletActionContext.class);
        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request)
            .anyTimes();
        EasyMock.expect(request.getRemoteAddr()).andReturn("192.168.0.1").anyTimes();
        PowerMock.replay(ServletActionContext.class);

        control.replay();
    }
    /**
     * Stress test for {@link SendInvitationAction#execute()}.
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
