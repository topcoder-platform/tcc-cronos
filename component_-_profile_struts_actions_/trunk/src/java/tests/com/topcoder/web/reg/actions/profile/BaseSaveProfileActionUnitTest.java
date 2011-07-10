/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for BaseSaveProfileAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseSaveProfileActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents action name for testing.
     * </p>
     */
    private static final String ACTION_NAME = "/baseSaveProfileAction";

    /**
     * <p>
     * Represents BaseSaveProfileAction instance for testing.
     * </p>
     */
    private BaseSaveProfileAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = (BaseSaveProfileAction) getBean("emailSendingProfileAction");
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
     * Tests BaseSaveProfileAction constructor.
     * </p>
     * <p>
     * BaseSaveProfileAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("BaseSaveProfileAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests BaseSaveProfileAction#execute() method with valid fields.
     * </p>
     * <p>
     * Action should be executed successfully. Mail should be sent. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseSaveProfileAction) actionProxy.getAction();
        action.setServletRequest(MockFactory.createServletRequest());
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        com.topcoder.web.common.model.User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        assertEquals("Action should be executed successfully.", ActionSupport.SUCCESS, action.execute());
        // check mail manually
    }

    /**
     * <p>
     * Tests BaseSaveProfileAction#execute() method with valid fields.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_NoMail() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseSaveProfileAction) actionProxy.getAction();
        action.setServletRequest(MockFactory.createServletRequest());
        action.setSendEmail(false);
        long id = 1L;
        WebAuthentication authentication =
                MockFactory.createWebAuthentication(MockFactory.createSecurityUser(id, "user", "password"));
        action.setSession(new HashMap<String, Object>());
        action.getSession().put(action.getAuthenticationSessionKey(), authentication);
        com.topcoder.web.common.model.User user = MockFactory.createUser(id, "first name", "last name", "handle");
        when(action.getUserDAO().find(id)).thenReturn(user);
        assertEquals("Action should be executed successfully.", ActionSupport.SUCCESS, action.execute());
        // check that mail is not sent manually
    }

    /**
     * <p>
     * Tests BaseSaveProfileAction#execute() method with valid fields, but authentication is missing in session.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_NoAuthentication() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseSaveProfileAction) actionProxy.getAction();
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
     * Tests BaseSaveProfileAction#execute() method with valid fields, but user is not found for given id.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_UserNotFound() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        action = (BaseSaveProfileAction) actionProxy.getAction();
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
     * Tests BaseSaveProfileAction#performAdditionalTasks() method with valid fields.
     * </p>
     * <p>
     * Additional tasks should be performed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPerformAdditionalTasks() throws Exception {
        ActionProxy actionProxy = getActionProxy(ACTION_NAME);
        long id = 1L;
        action = (BaseSaveProfileAction) actionProxy.getAction();
        com.topcoder.web.common.model.User user = MockFactory.createUser(id, "first name", "last name", "handle");
        action.performAdditionalTasks(user);
    }

    /**
     * <p>
     * Tests BaseSaveProfileAction#getSavedUser() method.
     * </p>
     * <p>
     * savedUser should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetSavedUser() {
        com.topcoder.web.common.model.User savedUser = mock(com.topcoder.web.common.model.User.class);
        action.setSavedUser(savedUser);
        assertSame("getSavedUser() doesn't work properly.", savedUser, action.getSavedUser());
    }

    /**
     * <p>
     * Tests BaseSaveProfileAction#setSavedUser(User) method.
     * </p>
     * <p>
     * savedUser should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetSavedUser() {
        com.topcoder.web.common.model.User savedUser = mock(com.topcoder.web.common.model.User.class);
        action.setSavedUser(savedUser);
        assertSame("setSavedUser() doesn't work properly.", savedUser, action.getSavedUser());
    }
}
