/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stresstests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;

import com.topcoder.security.groups.actions.AccessAuditingInfoAction;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupMemberAccessHistoricalData;
import com.topcoder.security.groups.services.dto.GroupMemberSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.ProjectDTO;
import com.topcoder.security.groups.services.dto.UserDTO;
/**
 * Stress tests for the {@link AccessAuditingInfoAction}.
 *
 * @author BlackMagic
 * @version 1.0
 */
public class AccessAuditingInfoActionStressTest {
    /**
     * Represents the instance used for test.
     */
    private AccessAuditingInfoAction instance;
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
     * Stress test for {@link AccessAuditingInfoAction#execute()}.
     * @throws Exception to JUnit.
     */
    @Test
    public void test_execute() throws Exception {
        instance = new AccessAuditingInfoAction();
        IMocksControl control = EasyMock.createControl();
        GroupMemberService groupMemberService = control.createMock(GroupMemberService.class);
        instance.setGroupMemberService(groupMemberService);
        GroupService groupService = control.createMock(GroupService.class);
        instance.setGroupService(groupService);
        DirectProjectService directProjectService = control.createMock(DirectProjectService.class);
        instance.setDirectProjectService(directProjectService);
        UserService userService = control.createMock(UserService.class);
        instance.setUserService(userService);
        groupMemberService.searchHistoricalData(EasyMock.anyObject(GroupMemberSearchCriteria.class),
            EasyMock.anyInt(), EasyMock.anyInt());
        PagedResult<GroupMemberAccessHistoricalData> result = new PagedResult<GroupMemberAccessHistoricalData>();
        EasyMock.expectLastCall().andReturn(result).anyTimes();
        List<GroupMemberAccessHistoricalData> values = new ArrayList<GroupMemberAccessHistoricalData>();
        values.add(new GroupMemberAccessHistoricalData());
        values.get(0).setDirectProjectIds(new ArrayList<Long>());
        values.get(0).getDirectProjectIds().add(0L);
        directProjectService.get(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(new ProjectDTO()).anyTimes();
        groupService.get(EasyMock.anyLong());
        Group group = new Group();
        EasyMock.expectLastCall().andReturn(group).anyTimes();
        group.setGroupMembers(new ArrayList<GroupMember>());
        group.getGroupMembers().add(new GroupMember());
        userService.get(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(new UserDTO()).anyTimes();

        result.setValues(values);

        control.replay();
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; ++i) {
            instance.execute();
        }
        long elapse = System.currentTimeMillis() - start;
        System.out.println("Execute execute() for " + StressTests.TIMES
            + " times, elapse " + elapse + "ms");
    }
}
