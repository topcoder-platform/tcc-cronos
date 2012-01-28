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

import com.topcoder.security.groups.actions.ApproveRejectPendingUserAction;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupMemberService;
/**
 * Stress tests for the {@link ApproveRejectPendingUserAction}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ApproveRejectPendingUserAction.class, ServletActionContext.class })
public class ApproveRejectPendingUserActionStressTest {
    /**
     * Represents the instance used for test.
     */
    private ApproveRejectPendingUserAction instance;
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ApproveRejectPendingUserActionStressTest.class);
    }
    /**
     * Initializes the test environment.
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new ApproveRejectPendingUserAction();
        List<Long> invitationIds = new ArrayList<Long>();
        invitationIds.add(1L);
        instance.setInvitationIds(invitationIds);
        instance.setApproved(true);
        instance.setRejectReason("reject");

        GroupInvitation groupInvitation = new GroupInvitation();
        groupInvitation.setStatus(InvitationStatus.APPROVAL_ACCEPTED);
        groupInvitation.setGroupMember(new GroupMember());
        IMocksControl control = EasyMock.createControl();
        GroupMemberService groupMemberService = control.createMock(GroupMemberService.class);
        instance.setGroupMemberService(groupMemberService);
        groupMemberService.update(EasyMock.anyObject(GroupMember.class));
        EasyMock.expectLastCall().anyTimes();
        GroupInvitationService groupInvitationService = control.createMock(GroupInvitationService.class);
        instance.setGroupInvitationService(groupInvitationService);
        groupInvitationService.getInvitation(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(groupInvitation).anyTimes();
        groupInvitationService.updateInvitation(groupInvitation);
        EasyMock.expectLastCall().anyTimes();
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
     * Stress test for {@link ApproveRejectPendingUserAction#execute()}.
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
