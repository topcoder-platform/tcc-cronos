/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Mockito.when;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This class shows API usage of this component.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends BaseUnitTest {

    /**
     * <p>
     * Represents EmailNotificationAction action for testing.
     * </p>
     */
    private EmailNotificationAction action;

    /**
     * <p>
     * Represents ActionProxy instance for testing.
     * </p>
     */
    private ActionProxy proxy;

    /**
     * <p>
     * Represents logged in user for testing.
     * </p>
     */
    private User loggedInUser;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        proxy = getActionProxy("/email");
        action = (EmailNotificationAction) proxy.getAction();
        setBasePreferencesActionDAOs(action);
        // put valid class for basic authentication session key
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), createAuthentication());
        loggedInUser = createUser(1L, "First", "Second", "handle", true);
        when(action.getUserDao().find(1L)).thenReturn(loggedInUser);
        setUserNotifications(loggedInUser);
        action.setEmailBodyTemplateFileName(EMAIL_BODY_TEMPLATE_NAME);
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
        proxy = null;
        loggedInUser = null;
    }

    /**
     * <p>
     * Show usage of this component.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDemo() throws Exception {
        // make action prepare
        action.prepare();
        // make submit action
        action.setAction(SUBMIT_ACTION);
        // execute
        System.out.println("Submit action result:" + action.execute());
        // put backup session
        User user = new User();
        user.setId(2L);
        putKeyValueToSession(action.getBackupSessionKey(), user);
        // make discard action
        action.setAction(DISCARD_ACTION);
        action.setEmailSendFlag(true);
        // change user preference value
        System.out.println("Discard action result:" + action.execute());
    }
}
