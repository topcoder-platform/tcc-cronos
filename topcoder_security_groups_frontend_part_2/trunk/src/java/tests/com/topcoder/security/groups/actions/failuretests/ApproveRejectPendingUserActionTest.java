/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import java.util.Arrays;
import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
import org.springframework.mock.web.MockHttpServletResponse;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.security.groups.actions.ApproveRejectPendingUserAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.util.log.LogManager;


/**
 * <p>Failure test case of {@link ApproveRejectPendingUserAction}.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class ApproveRejectPendingUserActionTest extends TestCase {
    /**
     * <p>The ApproveRejectPendingUserAction instance to test.</p>
     */
    private ApproveRejectPendingUserAction instance;
    /**
     * <p>The GroupInvitationService instance to test.</p>
     */
    private GroupInvitationService groupInvitationService;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        instance = new ApproveRejectPendingUserAction();
        groupInvitationService = EasyMock.createMock(GroupInvitationService.class);
        instance.setGroupInvitationService(groupInvitationService);
        instance.setLogger(LogManager.getLog("test"));
        ActionContext ctx = new ActionContext(new HashMap<String, Object>());
        ActionContext.setContext(ctx);
        ServletActionContext.setContext(ctx);
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>Creates a test suite for the tests in this test case.</p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ApproveRejectPendingUserActionTest.class);

        return suite;
    }

    /**
     * <p>Failure test for method ApproveRejectPendingUserAction#execute().</p>
     *
     * @throws Throwable to junit
     */
    public void test_execute1() throws Throwable {
        try {
        	instance.setInvitationIds(Arrays.asList(new Long[]{2l}));
        	EasyMock.expect(groupInvitationService.getInvitation(2l)).andThrow(new SecurityGroupException("entity_not_found"));
        	EasyMock.replay(groupInvitationService);
            instance.execute();
            EasyMock.verify(groupInvitationService);
            fail("exception expected");
        } catch (SecurityGroupsActionException e) {
            // pass
        }
    }

    /**
     * <p>Failure test for method ApproveRejectPendingUserAction#execute().</p>
     *
     * @throws Throwable to junit
     */
    public void test_execute2() throws Throwable {
        try {
        	instance.setInvitationIds(Arrays.asList(new Long[]{2l}));
        	EasyMock.expect(groupInvitationService.getInvitation(2l)).andReturn(null);
        	EasyMock.replay(groupInvitationService);
            instance.execute();
            EasyMock.verify(groupInvitationService);
            fail("exception expected");
        } catch (SecurityGroupsActionException e) {
            // pass
        }
    }

    /**
     * <p>Failure test for method ApproveRejectPendingUserAction#execute().</p>
     *
     * @throws Throwable to junit
     */
    public void test_execute3() throws Throwable {
        try {
        	instance.setInvitationIds(Arrays.asList(new Long[]{2l}));
            groupInvitationService = EasyMock.createNiceMock(GroupInvitationService.class);
            instance.setGroupInvitationService(groupInvitationService);
            GroupInvitation invitation = new GroupInvitation();
            invitation.setStatus(InvitationStatus.APPROVAL_ACCEPTED);
        	EasyMock.expect(groupInvitationService.getInvitation(2l)).andReturn(new GroupInvitation());
        	EasyMock.replay(groupInvitationService);
            instance.execute();
            EasyMock.verify(groupInvitationService);
            fail("exception expected");
        } catch (SecurityGroupsActionException e) {
            // pass
        }
    }
}
