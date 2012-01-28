/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.AcceptRejectGroupInvitationAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.util.log.LogManager;


/**
 * <p>Failure test case of {@link AcceptRejectGroupInvitationAction}.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class AcceptRejectGroupInvitationActionTest extends TestCase {
    /**
     * <p>The AcceptRejectGroupInvitationAction instance to test.</p>
     */
    private AcceptRejectGroupInvitationAction instance;
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
        instance = new AcceptRejectGroupInvitationAction();
        groupInvitationService = EasyMock.createMock(GroupInvitationService.class);
        instance.setGroupInvitationService(groupInvitationService);
        instance.setLogger(LogManager.getLog("test"));
        instance.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
;    }

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
        TestSuite suite = new TestSuite(AcceptRejectGroupInvitationActionTest.class);

        return suite;
    }

    /**
     * <p>Failure test for method AcceptRejectGroupInvitationAction#execute().</p>
     *
     * @throws Throwable to junit
     */
    public void test_execute1() throws Throwable {
        try {
        	instance.setInvitationId(2l);
        	EasyMock.expect(groupInvitationService.getInvitation(2l)).andThrow(new SecurityGroupException("error"));
        	EasyMock.replay(groupInvitationService);
            instance.execute();
            EasyMock.verify(groupInvitationService);
            fail("exception expected");
        } catch (SecurityGroupsActionException e) {
            // pass
        }
    }

    /**
     * <p>Failure test for method AcceptRejectGroupInvitationAction#execute().</p>
     *
     * @throws Throwable to junit
     */
    public void test_execute2() throws Throwable {
        try {
        	instance.setInvitationId(2l);
        	EasyMock.expect(groupInvitationService.getInvitation(2l)).andReturn(null);
        	EasyMock.replay(groupInvitationService);
            instance.execute();
            EasyMock.verify(groupInvitationService);
            fail("exception expected");
        } catch (SecurityGroupsActionException e) {
            // pass
        }
    }
}
