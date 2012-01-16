/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.SecurityGroupsActionConfigurationException;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.actions.ViewGroupAction;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ViewGroupAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class ViewGroupActionFailureTests extends TestCase {
    /**
     * <p>
     * The ViewGroupAction instance for testing.
     * </p>
     */
    private ViewGroupAction instance;

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
        instance = new ViewGroupAction();
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
        return new TestSuite(ViewGroupActionFailureTests.class);
    }

    /**
     * <p>
     * Tests ViewGroupAction#checkInit() for failure.
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
     * Tests ViewGroupAction#execute() for failure.
     * Expects SecurityGroupsActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecute_SecurityGroupsActionException() throws Exception {
        groupService.get(1);
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("error"));
        EasyMock.replay(groupService);
        try {
            instance.execute();
            fail("SecurityGroupsActionException expected.");
        } catch (SecurityGroupsActionException e) {
            //good
        }
    }
}