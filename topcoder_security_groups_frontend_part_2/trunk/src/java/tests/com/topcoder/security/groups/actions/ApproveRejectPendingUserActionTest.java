/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.ServletActionContext;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * Unit tests for the {@link ApproveRejectPendingUserAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest( { ApproveRejectPendingUserAction.class,
        ServletActionContext.class })
public class ApproveRejectPendingUserActionTest {
    /** Represents the instance used to test again. */
    private ApproveRejectPendingUserAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ApproveRejectPendingUserAction();
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
        return new JUnit4TestAdapter(ApproveRejectPendingUserActionTest.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ApproveRejectPendingUserAction#ApproveRejectPendingUserAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testApproveRejectPendingUserAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ApproveRejectPendingUserAction</code>
     * subclasses <code>GroupInvitationAwareBaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "ApproveRejectPendingUserAction does not subclass GroupInvitationAwareBaseAction.",
                ApproveRejectPendingUserAction.class.getSuperclass() == GroupInvitationAwareBaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ApproveRejectPendingUserAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute1() throws Exception {
        testInstance.setApproved(true);
        testInstance.setInvitationIds(Arrays.asList(1L));

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
        GroupMember member = new GroupMember();
        invitation.setGroupMember(member);
        EasyMock.expect(
                giService.getInvitation(EasyMock.capture(new Capture<Long>())))
                .andReturn(invitation).anyTimes();
        giService.updateInvitation(EasyMock
                .capture(new Capture<GroupInvitation>()));
        EasyMock.expectLastCall().anyTimes();
        testInstance.setGroupInvitationService(giService);

        GroupAuditService auditService = EasyMock
                .createNiceMock(GroupAuditService.class);
        Capture<GroupAuditRecord> g = new Capture<GroupAuditRecord>();
        EasyMock.expect(auditService.add(EasyMock.capture(g))).andReturn(1L);
        testInstance.setAuditService(auditService);

        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress")
                .anyTimes();

        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request)
                .anyTimes();

        GroupMemberService gmService = EasyMock
                .createMock(GroupMemberService.class);
        gmService.update(member);
        EasyMock.expectLastCall();
        testInstance.setGroupMemberService(gmService);

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(request);
        EasyMock.replay(giService);
        EasyMock.replay(auditService);
        testInstance.execute();
        EasyMock.verify(auditService);
        EasyMock.verify(giService);
        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);
    }

    /**
     * <p>
     * Accuracy test for {@link ApproveRejectPendingUserAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute2() throws Exception {
        testInstance.setApproved(false);
        testInstance.setInvitationIds(Arrays.asList(1L));

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
        GroupMember member = new GroupMember();
        invitation.setGroupMember(member);
        EasyMock.expect(
                giService.getInvitation(EasyMock.capture(new Capture<Long>())))
                .andReturn(invitation).anyTimes();
        giService.updateInvitation(EasyMock
                .capture(new Capture<GroupInvitation>()));
        EasyMock.expectLastCall().anyTimes();
        testInstance.setGroupInvitationService(giService);

        GroupAuditService auditService = EasyMock
                .createNiceMock(GroupAuditService.class);
        Capture<GroupAuditRecord> g = new Capture<GroupAuditRecord>();
        EasyMock.expect(auditService.add(EasyMock.capture(g))).andReturn(1L);
        testInstance.setAuditService(auditService);

        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress")
                .anyTimes();

        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request)
                .anyTimes();

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(request);
        EasyMock.replay(giService);
        EasyMock.replay(auditService);
        testInstance.execute();
        EasyMock.verify(auditService);
        EasyMock.verify(giService);
        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);
    }

    /**
     * <p>
     * Failure test for {@link ApproveRejectPendingUserAction#execute()}.
     * </p>
     * <p>
     * Fail to update invitation, SecurityGroupsActionException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail1() throws Exception {
        testInstance.setApproved(false);
        testInstance.setInvitationIds(Arrays.asList(1L));

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
        GroupMember member = new GroupMember();
        invitation.setGroupMember(member);
        EasyMock.expect(
                giService.getInvitation(EasyMock.capture(new Capture<Long>())))
                .andReturn(invitation).anyTimes();
        giService.updateInvitation(EasyMock
                .capture(new Capture<GroupInvitation>()));
        EasyMock.expectLastCall().andThrow(new EntityNotFoundException("test"));
        testInstance.setGroupInvitationService(giService);

        GroupAuditService auditService = EasyMock
                .createNiceMock(GroupAuditService.class);
        Capture<GroupAuditRecord> g = new Capture<GroupAuditRecord>();
        EasyMock.expect(auditService.add(EasyMock.capture(g))).andReturn(1L);
        testInstance.setAuditService(auditService);

        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress")
                .anyTimes();

        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request)
                .anyTimes();

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(request);
        EasyMock.replay(giService);
        EasyMock.replay(auditService);
        testInstance.execute();
        EasyMock.verify(auditService);
        EasyMock.verify(giService);
        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);
    }

    /**
     * <p>
     * Failure test for {@link ApproveRejectPendingUserAction#execute()}.
     * </p>
     * <p>
     * Fail to update invitation, SecurityGroupsActionException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail2() throws Exception {
        testInstance.setApproved(false);
        testInstance.setInvitationIds(Arrays.asList(1L));

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
        GroupMember member = new GroupMember();
        invitation.setGroupMember(member);
        EasyMock.expect(
                giService.getInvitation(EasyMock.capture(new Capture<Long>())))
                .andReturn(invitation).anyTimes();
        giService.updateInvitation(EasyMock
                .capture(new Capture<GroupInvitation>()));
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("test"));
        testInstance.setGroupInvitationService(giService);

        GroupAuditService auditService = EasyMock
                .createNiceMock(GroupAuditService.class);
        Capture<GroupAuditRecord> g = new Capture<GroupAuditRecord>();
        EasyMock.expect(auditService.add(EasyMock.capture(g))).andReturn(1L);
        testInstance.setAuditService(auditService);

        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress")
                .anyTimes();

        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request)
                .anyTimes();

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(request);
        EasyMock.replay(giService);
        EasyMock.replay(auditService);
        testInstance.execute();
        EasyMock.verify(auditService);
        EasyMock.verify(giService);
        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);
    }

    /**
     * <p>
     * Failure test for {@link ApproveRejectPendingUserAction#execute()}.
     * </p>
     * <p>
     * Invitation retrieved is null, SecurityGroupsActionException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail3() throws Exception {
        testInstance.setApproved(false);
        testInstance.setInvitationIds(Arrays.asList(1L));

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);

        EasyMock.expect(
                giService.getInvitation(EasyMock.capture(new Capture<Long>())))
                .andReturn(null).anyTimes();
        giService.updateInvitation(EasyMock
                .capture(new Capture<GroupInvitation>()));
        EasyMock.expectLastCall().anyTimes();
        testInstance.setGroupInvitationService(giService);

        GroupAuditService auditService = EasyMock
                .createNiceMock(GroupAuditService.class);
        Capture<GroupAuditRecord> g = new Capture<GroupAuditRecord>();
        EasyMock.expect(auditService.add(EasyMock.capture(g))).andReturn(1L);
        testInstance.setAuditService(auditService);

        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress")
                .anyTimes();

        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request)
                .anyTimes();

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(request);
        EasyMock.replay(giService);
        EasyMock.replay(auditService);
        testInstance.execute();
        EasyMock.verify(auditService);
        EasyMock.verify(giService);
        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);
    }

    /**
     * <p>
     * Accuracy test for {@link ApproveRejectPendingUserAction#checkInit()}.
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
        testInstance.setGroupMemberService(EasyMock
                .createNiceMock(GroupMemberService.class));
        testInstance.setGroupInvitationService(EasyMock
                .createNiceMock(GroupInvitationService.class));
        testInstance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link ApproveRejectPendingUserAction#checkInit()}.
     * </p>
     * <p>
     * GroupMemberService is null, SecurityGroupsActionConfigurationException
     * will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void test_checkInit_fail() throws Exception {
        testInstance.setAuditService(EasyMock
                .createNiceMock(GroupAuditService.class));
        testInstance.setAuthorizationService(EasyMock
                .createNiceMock(AuthorizationService.class));
        testInstance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for {@link ApproveRejectPendingUserAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testValidate1() throws Exception {
        testInstance.validate();
        assertEquals("Should be 1", 1, testInstance.getFieldErrors().size());
    }
    /**
     * <p>
     * Accuracy test for {@link ApproveRejectPendingUserAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testValidate2() throws Exception {
        testInstance.setInvitationIds(Collections.EMPTY_LIST);
        testInstance.validate();
        assertEquals("Should be 1", 1, testInstance.getFieldErrors().size());
    }
    /**
     * <p>
     * Accuracy test for {@link ApproveRejectPendingUserAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testValidate3() throws Exception {
        List<Long>  list = new ArrayList<Long>();
        list.add(null);
        testInstance.setInvitationIds(list);
        testInstance.validate();
        assertEquals("Should be 1", 1, testInstance.getFieldErrors().size());
    }
    /**
     * <p>
     * Accuracy test for {@link ApproveRejectPendingUserAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testValidate4() throws Exception {
        List<Long>  list = new ArrayList<Long>();
        list.add(1L);
        list.add(1L);
        testInstance.validate();
        assertEquals("Should be 1", 1, testInstance.getFieldErrors().size());
    }
    /**
     * <p>
     * Accuracy test for
     * {@link ApproveRejectPendingUserAction#setInvitationIds()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setInvitationIds() throws Exception {
        List<Long> ids = Arrays.asList(1L);
        testInstance.setInvitationIds(ids);
        assertEquals("Should be equal", ids, TestHelper.getField(testInstance,
                "invitationIds"));
    }

    /**
     * <p>
     * Accuracy test for {@link ApproveRejectPendingUserAction#setApproved()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setApproved() throws Exception {
        testInstance.setApproved(false);
        assertEquals("Should be false", false, TestHelper.getField(
                testInstance, "approved"));
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ApproveRejectPendingUserAction#setRejectReason()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setRejectReason() throws Exception {
        testInstance.setRejectReason("error");
        assertEquals("Should be error", "error", TestHelper.getField(
                testInstance, "rejectReason"));
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ApproveRejectPendingUserAction#setGroupMemberService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setGroupMemberService() throws Exception {
        GroupMemberService groupMemberService = EasyMock
                .createMock(GroupMemberService.class);
        testInstance.setGroupMemberService(groupMemberService);
        assertEquals("Should be equals", groupMemberService, TestHelper
                .getField(testInstance, "groupMemberService"));
    }
}