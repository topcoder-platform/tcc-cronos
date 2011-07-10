/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for BaseDisplayProfileAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseDisplayProfileActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents action name for testing.
     * </p>
     */
    private static final String ACTION_NAME = "/baseDisplayProfileAction";

    /**
     * <p>
     * Represents BaseDisplayProfileAction instance for testing.
     * </p>
     */
    private BaseDisplayProfileAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = (BaseDisplayProfileAction) getBean("baseProfileAction");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        action = null;
    }

    /**
     * <p>
     * Tests BaseDisplayProfileAction constructor.
     * </p>
     * <p>
     * BaseDisplayProfileAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("BaseDisplayProfileAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests BaseDisplayProfileAction#getDisplayedUser() method.
     * </p>
     * <p>
     * displayedUser should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetDisplayedUser() {
        com.topcoder.web.common.model.User displayedUser = mock(com.topcoder.web.common.model.User.class);
        action.setDisplayedUser(displayedUser);
        assertSame("getDisplayedUser() doesn't work properly.", displayedUser, action.getDisplayedUser());
    }

    /**
     * <p>
     * Tests BaseDisplayProfileAction#setDisplayedUser(User) method.
     * </p>
     * <p>
     * displayedUser should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDisplayedUser() {
        com.topcoder.web.common.model.User displayedUser = mock(com.topcoder.web.common.model.User.class);
        action.setDisplayedUser(displayedUser);
        assertSame("setDisplayedUser() doesn't work properly.", displayedUser, action.getDisplayedUser());
    }

    /**
     * <p>
     * Tests BaseDisplayProfileAction#execute() method with valid fields.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseDisplayProfileAction) actionProxy.getAction();
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        com.topcoder.web.common.model.User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        assertEquals("Action should be executed successfully.", ActionSupport.SUCCESS, action.execute());
        verify(action.getAuditDAO(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests BaseDisplayProfileAction#execute() method with valid fields, but authentication is missing in session.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_NoAuthentication() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseDisplayProfileAction) actionProxy.getAction();
        action.setServletRequest(MockFactory.createServletRequest());
        action.setSession(new HashMap<String, Object>());
        long id = 1L;
        com.topcoder.web.common.model.User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        try {
            action.execute();
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseDisplayProfileAction#execute() method with valid fields, but user is not found for given id.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_UserNotFound() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseDisplayProfileAction) actionProxy.getAction();
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        when(action.getUserDAO().find(id)).thenReturn(null);
        try {
            action.execute();
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseDisplayProfileAction#performAdditionalTasks() method with valid fields.
     * </p>
     * <p>
     * Additional tasks should be performed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPerformAdditionalTasks() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        long id = 1L;
        action = (BaseDisplayProfileAction) actionProxy.getAction();
        com.topcoder.web.common.model.User user = MockFactory.createUser(id, "first name", "last name", "handle");
        action.performAdditionalTasks(user);
    }
}
