/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.BaseAction;
import com.topcoder.security.groups.actions.DeleteGroupAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionConfigurationException;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class BaseActionFailureTests extends TestCase {

    /**
     * <p>
     * The BaseAction instance for testing.
     * </p>
     */
    private BaseAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new DeleteGroupAction();
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
        return new TestSuite(BaseActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseAction#checkInit() for failure.
     * It tests the case that when auditService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullAuditService() {
        instance.setAuditService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseAction#checkInit() for failure.
     * It tests the case that when authorizationService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullAuthorizationService() {
        instance.setAuthorizationService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

}