/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.DeleteGroupAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionConfigurationException;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupService;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for DeleteGroupAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class DeleteGroupActionFailureTests extends TestCase {
    /**
     * <p>
     * The DeleteGroupAction instance for testing.
     * </p>
     */
    private DeleteGroupAction instance;

    /**
     * <p>
     * The GroupService instance for testing.
     * </p>
     */
    private GroupService groupService;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new DeleteGroupAction();
        instance.setGroupId(1);
        groupService = EasyMock.createNiceMock(GroupService.class);
        instance.setGroupService(groupService);
        instance.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
        instance.setAuthorizationService(EasyMock.createNiceMock(AuthorizationService.class));
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DeleteGroupActionFailureTests.class);
    }

    /**
     * <p>
     * Tests DeleteGroupAction#checkInit() for failure.
     * It tests the case that when groupService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullgroupService() {
        instance.setGroupService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DeleteGroupAction#execute() for failure.
     * Expects SecurityGroupsActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecute_SecurityGroupsActionException() throws Exception {
        groupService.delete(1);
        EasyMock.expectLastCall().andThrow(new EntityNotFoundException("error"));
        EasyMock.replay(groupService);
        try {
            instance.execute();
            fail("SecurityGroupsActionException expected.");
        } catch (SecurityGroupsActionException e) {
            //good
        }
    }
}