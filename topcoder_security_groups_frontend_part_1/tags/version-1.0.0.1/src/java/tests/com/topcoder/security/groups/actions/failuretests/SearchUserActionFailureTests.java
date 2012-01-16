/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.SearchUserAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionConfigurationException;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for SearchUserAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class SearchUserActionFailureTests extends TestCase {
    /**
     * <p>
     * The SearchUserAction instance for testing.
     * </p>
     */
    private SearchUserAction instance;

    /**
     * <p>
     * The UserService instance for testing.
     * </p>
     */
    private UserService userService;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new SearchUserAction();
        instance.setHandle("id");
        userService = EasyMock.createNiceMock(UserService.class);
        instance.setUserService(userService);
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
        return new TestSuite(SearchUserActionFailureTests.class);
    }

    /**
     * <p>
     * Tests SearchUserAction#checkInit() for failure.
     * It tests the case that when userService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NulluserService() {
        instance.setUserService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests SearchUserAction#execute() for failure.
     * Expects SecurityGroupsActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecute_SecurityGroupsActionException() throws Exception {
        userService.search("id");
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("error"));
        EasyMock.replay(userService);
        try {
            instance.execute();
            fail("SecurityGroupsActionException expected.");
        } catch (SecurityGroupsActionException e) {
            //good
        }
    }
}