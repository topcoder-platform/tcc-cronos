/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.opensymphony.xwork2.Action;
import com.topcoder.security.groups.actions.ApproveRejectPendingUserAction;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupMemberService;

/**
 * <p>
 * Accuracy test for ApproveRejectPendingUserAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ApproveRejectPendingUserAction.class, ServletActionContext.class })
public class ApproveRejectPendingUserActionAccuracyTest {
    /**
     * Represents the instance of ApproveRejectPendingUserAction used in test.
     */
    private ApproveRejectPendingUserAction instance;

    /**
     * Set up for each test.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ApproveRejectPendingUserAction();
    }

    /**
     * Tear down for each test.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
        MockGroupInvitationService.RECORDS.clear();
        MockGroupAuditService.RECORDS.clear();
    }

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ApproveRejectPendingUserActionAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for ApproveRejectPendingUserAction(). The instance should
     * be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("The instance should be created.", instance);
    }

    /**
     * <p>
     * Accuracy test for {@link ApproveRejectPendingUserAction#execute()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute1() throws Exception {
        doExecute(true);
    }

    /**
     * <p>
     * Accuracy test for {@link ApproveRejectPendingUserAction#execute()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute2() throws Exception {
        doExecute(false);
    }

    /**
     * <p>
     * Accuracy test for checkInit(). When all properties are set, no exception
     * should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCheckInit() throws Exception {
        instance.setAuditService(new MockGroupAuditService());
        instance.setAuthorizationService(EasyMock.createNiceMock(AuthorizationService.class));
        instance.setGroupMemberService(EasyMock.createNiceMock(GroupMemberService.class));
        instance.setGroupInvitationService(new MockGroupInvitationService());
        instance.checkInit();
    }

    /**
     * Do the execute test.
     *
     * @param isAccepted
     *            the invitation is accepted or not.
     * @param id
     *            the invitation id.
     *
     * @throws Exception
     *             to JUnit
     */
    private void doExecute(boolean isAccepted) throws Exception {
        instance.setApproved(isAccepted);
        instance.setInvitationIds(Arrays.asList(101L, 102L));
        instance.setRejectReason("rejected");

        GroupInvitationService groupInvitationService = new MockGroupInvitationService();
        instance.setGroupInvitationService(groupInvitationService);

        GroupAuditService auditService = new MockGroupAuditService();
        instance.setAuditService(auditService);

        GroupMemberService gmService = new MockGroupMemberService();
        instance.setGroupMemberService(gmService);

        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress").anyTimes();
        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request).anyTimes();

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(request);

        assertEquals("The returned value is incorrect.", Action.SUCCESS, instance.execute());

        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);

        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);

        assertEquals("The GroupInvitation should be updated.", 2,
            MockGroupInvitationService.RECORDS.size());
        assertEquals("The GroupInvitation should be updated.", 101,
            MockGroupInvitationService.RECORDS.get(101L).getId());
        assertEquals("The GroupInvitation should be updated.",
            isAccepted ? InvitationStatus.APPROVAL_ACCEPTED : InvitationStatus.APPROVAL_REJECTED,
            MockGroupInvitationService.RECORDS.get(101L).getStatus());

        assertEquals("The GroupInvitation should be updated.",
            isAccepted ? InvitationStatus.APPROVAL_ACCEPTED : InvitationStatus.APPROVAL_REJECTED,
            MockGroupInvitationService.RECORDS.get(102L).getStatus());
        assertEquals("The GroupInvitation should be updated.", 102,
            MockGroupInvitationService.RECORDS.get(102L).getId());

        if (!isAccepted) {
            assertEquals("The GroupInvitation should be updated.", "rejected",
                MockGroupInvitationService.RECORDS.get(101L).getRejectReason());
        } else {
            assertTrue("The execute is incorrect.", MockGroupMemberService.RECORDS.get(101L)
                .isActive());
            assertTrue("The execute is incorrect.", MockGroupMemberService.RECORDS.get(102L)
                .isActive());
            assertTrue(
                "The execute is incorrect.",
                Math.abs(System.currentTimeMillis()
                    - MockGroupMemberService.RECORDS.get(101L).getActivatedOn().getTime()) < 1000);
            assertTrue(
                "The execute is incorrect.",
                Math.abs(System.currentTimeMillis()
                    - MockGroupMemberService.RECORDS.get(102L).getActivatedOn().getTime()) < 1000);
        }

        assertTrue(
            "The GroupInvitation should be updated.",
            Math.abs(System.currentTimeMillis()
                - MockGroupInvitationService.RECORDS.get(101L).getApprovalProcessedOn().getTime()) < 1000);
        assertTrue(
            "The GroupInvitation should be updated.",
            Math.abs(System.currentTimeMillis()
                - MockGroupInvitationService.RECORDS.get(102L).getApprovalProcessedOn().getTime()) < 1000);

        assertEquals("The audit record should be correct.", isAccepted ? 4 : 2,
            MockGroupAuditService.RECORDS.size());
    }
}