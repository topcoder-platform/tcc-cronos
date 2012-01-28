/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stresstests;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.topcoder.security.groups.actions.ViewInvitationStatusAction;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
/**
 * Stress tests for the {@link ViewInvitationStatusAction}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
public class ViewInvitationStatusActionStressTest extends TestCase {
    /**
     * Represents the instance used for test.
     */
    private ViewInvitationStatusAction instance;
    /**
     * Initializes the test environment.
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
        instance = new ViewInvitationStatusAction();
        GroupInvitation groupInvitation = new GroupInvitation();
        groupInvitation.setStatus(InvitationStatus.APPROVAL_ACCEPTED);
        groupInvitation.setGroupMember(new GroupMember());
        IMocksControl control = EasyMock.createControl();
        GroupInvitationService groupInvitationService = control.createMock(GroupInvitationService.class);
        instance.setGroupInvitationService(groupInvitationService);
        groupInvitationService.search(EasyMock.anyObject(InvitationSearchCriteria.class), EasyMock.anyLong(),
            EasyMock.anyInt(), EasyMock.anyInt());
        EasyMock.expectLastCall().andReturn(new PagedResult<GroupInvitation>()).anyTimes();
        control.replay();
    }
    /**
     * Stress test for {@link ViewInvitationStatusAction#execute()}.
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
