/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stresstests;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.topcoder.security.groups.actions.AcceptRejectGroupInvitationAction;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
/**
 * Stress tests for the {@link AcceptRejectGroupInvitationAction}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ AcceptRejectGroupInvitationAction.class, ServletActionContext.class })
public class AcceptRejectGroupInvitationActionStressTest {
    /**
     * Represents the instance used for test.
     */
    private AcceptRejectGroupInvitationAction instance;
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AcceptRejectGroupInvitationActionStressTest.class);
    }
    /**
     * Stress test for {@link AcceptRejectGroupInvitationAction#execute()}.
     * @throws Exception to JUnit.
     */
    @Test
    public void test_execute() throws Exception {
        instance = new AcceptRejectGroupInvitationAction();
        instance.setAccepted(true);
        instance.setInvitationId(1L);
        GroupInvitation groupInvitation = new GroupInvitation();
        IMocksControl control = EasyMock.createControl();
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
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; ++i) {
            instance.execute();
        }
        long elapse = System.currentTimeMillis() - start;
        System.out.println("Execute execute() for " + StressTests.TIMES
            + " times, elapse " + elapse + "ms");
    }
}
