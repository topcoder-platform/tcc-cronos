/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * Unit tests for the {@link AcceptRejectGroupInvitationAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { AcceptRejectGroupInvitationAction.class, ServletActionContext.class })
public class AcceptRejectGroupInvitationActionTest {
    /** Represents the instance used to test again. */
    private AcceptRejectGroupInvitationAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new AcceptRejectGroupInvitationAction();
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
        return new JUnit4TestAdapter(
                AcceptRejectGroupInvitationActionTest.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link AcceptRejectGroupInvitationAction#AcceptRejectGroupInvitationAction()}
     * .
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testAcceptRejectGroupInvitationAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>AcceptRejectGroupInvitationAction</code>
     * subclasses <code>GroupInvitationAwareBaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "AcceptRejectGroupInvitationAction does not subclass GroupInvitationAwareBaseAction.",
                AcceptRejectGroupInvitationAction.class.getSuperclass() == GroupInvitationAwareBaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link AcceptRejectGroupInvitationAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute() throws Exception {
        testInstance.setAccepted(true);
        testInstance.setInvitationId(1L);

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
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
     * Accuracy test for {@link AcceptRejectGroupInvitationAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute2() throws Exception {
        testInstance.setAccepted(false);
        testInstance.setInvitationId(1L);

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
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
     * Failure test for {@link AcceptRejectGroupInvitationAction#execute()}.
     * </p>
     * <p>
     * Fail to update invitation, SecurityGroupsActionException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail1() throws Exception {
        testInstance.setAccepted(true);
        testInstance.setInvitationId(1L);

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
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
     * Failure test for {@link AcceptRejectGroupInvitationAction#execute()}.
     * </p>
     * <p>
     * Fail to update invitation, SecurityGroupsActionException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail2() throws Exception {
        testInstance.setAccepted(true);
        testInstance.setInvitationId(1L);

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
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
     * Failure test for {@link AcceptRejectGroupInvitationAction#execute()}.
     * </p>
     * <p>
     * Fail to update invitation, SecurityGroupsActionException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail3() throws Exception {
        testInstance.setAccepted(true);
        testInstance.setInvitationId(1L);

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
     * Accuracy test for
     * {@link AcceptRejectGroupInvitationAction#setInvitationId()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setInvitationId() throws Exception {
        long invitationId = 100L;
        testInstance.setInvitationId(invitationId);
        assertEquals("Should be 100", 100L, TestHelper.getField(testInstance,
                "invitationId"));
    }

    /**
     * <p>
     * Accuracy test for {@link AcceptRejectGroupInvitationAction#setAccepted()}
     * .
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setAccepted() throws Exception {
        testInstance.setAccepted(false);
        assertEquals("Should be false", false, TestHelper.getField(
                testInstance, "accepted"));
    }
}