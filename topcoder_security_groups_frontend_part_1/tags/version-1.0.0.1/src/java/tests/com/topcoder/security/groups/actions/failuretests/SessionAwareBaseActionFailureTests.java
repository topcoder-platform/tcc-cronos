/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.SecurityGroupsActionConfigurationException;
import com.topcoder.security.groups.actions.SessionAwareBaseAction;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for SessionAwareBaseAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class SessionAwareBaseActionFailureTests extends TestCase {

    /**
     * <p>
     * The SessionAwareBaseAction instance for testing.
     * </p>
     */
    private SessionAwareBaseAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    @SuppressWarnings("serial")
    protected void setUp() {
        instance = new SessionAwareBaseAction(){};
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
        return new TestSuite(SessionAwareBaseActionFailureTests.class);
    }

    /**
     * <p>
     * Tests DeleteGroupAction#checkInit() for failure.
     * It tests the case that when session is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_Nullsession() {
        instance.setSession(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DeleteGroupAction#checkInit() for failure.
     * It tests the case that when session is empty and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_Emptysession() {
        instance.setSession(new HashMap<String, Object>());
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DeleteGroupAction#checkInit() for failure.
     * It tests the case that when session contains null value and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullValueInsession() {
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("key", null);
        instance.setSession(session);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }
}