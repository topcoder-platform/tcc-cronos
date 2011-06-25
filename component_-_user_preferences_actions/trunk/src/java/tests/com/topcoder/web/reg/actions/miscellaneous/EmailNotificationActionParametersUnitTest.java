/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.common.model.Notification;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This class contains Unit tests for EmailNotificationAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EmailNotificationActionParametersUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents EmailNotificationAction action for testing.
     * </p>
     */
    private EmailNotificationAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/email");
        action = (EmailNotificationAction) proxy.getAction();
        setBasePreferencesActionDAOs(action);
        // put valid class for basic authentication session key
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), createAuthentication());
        User user = createUser(1L, "First", "Last", "tc_handle", true);
        when(action.getUserDao().find(anyLong())).thenReturn(user);
        setUserNotifications(action.getLoggedInUser());
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
     * Tests EmailNotificationAction#getNotifications() method.
     * </p>
     * <p>
     * notifications should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetNotifications() {
        Set < Notification > notifications = new HashSet < Notification >();
        action.setNotifications(notifications);
        assertSame("getNotifications() doesn't work properly.", notifications, action.getNotifications());
    }

    /**
     * <p>
     * Tests EmailNotificationAction#setNotifications(Set) method.
     * </p>
     * <p>
     * notifications should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetNotifications() {
        Set < Notification > notifications = new HashSet < Notification >();
        action.setNotifications(notifications);
        assertSame("setNotifications() doesn't work properly.", notifications, action.getNotifications());
    }

    /**
     * <p>
     * Tests EmailNotificationAction#validate() method with null notification names.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_NotificatioName() throws Exception {
        action.prepare();
        action.clearFieldErrors();
        Set < Notification > preferences = action.getNotifications();
        for (Notification notification : preferences) {
            notification.setName(null);
        }
        action.validate();
        assertEquals("Fields error should be added.", 2, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests EmailNotificationAction#validate() method with empty notification names.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_NotificatioName() throws Exception {
        action.prepare();
        action.clearFieldErrors();
        Set < Notification > preferences = action.getNotifications();
        for (Notification notification : preferences) {
            notification.setName("");
        }
        action.validate();
        assertEquals("Fields error should be added.", 2, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests EmailNotificationAction#validate() method with null notification statuses.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_NotificatioStatus() throws Exception {
        action.prepare();
        action.clearFieldErrors();
        Set < Notification > preferences = action.getNotifications();
        for (Notification notification : preferences) {
            notification.setName("Notification");
            notification.setStatus(null);
        }
        action.validate();
        assertEquals("Fields error should be added.", 2, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests EmailNotificationAction#validate() method with empty notification statuses.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_NotificatioStatus() throws Exception {
        action.prepare();
        action.clearFieldErrors();
        Set < Notification > preferences = action.getNotifications();
        for (Notification notification : preferences) {
            notification.setName("Notification");
            notification.setStatus("");
        }
        action.validate();
        assertEquals("Fields error should be added.", 2, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests EmailNotificationAction#prepare() with logged in user that has invalid state.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Submit_UserISE() throws Exception {
        action.prepare();
        // make user invalid state
        User user = action.getLoggedInUser();
        user.setUserProfile(null);
        when(action.getUserDao().find(anyLong())).thenReturn(user);
        try {
            action.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailNotificationAction#execute() with logged in user that has invalid state.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_UserISE() throws Exception {
        action.prepare();
        // make user invalid state
        User user = action.getLoggedInUser();
        user.setUserProfile(null);
        when(action.getUserDao().find(anyLong())).thenReturn(user);
        action.setAction(SUBMIT_ACTION);
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailNotificationAction#execute() with no user in session.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_NoUser() throws Exception {
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), null);
        action.setAction(SUBMIT_ACTION);
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailNotificationAction#execute() with email send fail.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_EmailFail() throws Exception {
        action.prepare();
        action.setEmailSendFlag(true);
        action.setEmailBodyTemplateFileName("Unknown");
        action.setAction(SUBMIT_ACTION);
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailNotificationAction#execute() with no backed up user in session.
     * </p>
     * <p>
     * PreferencesActionDiscardException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Discard_NoUser() throws Exception {
        action.setAction(DISCARD_ACTION);
        try {
            action.execute();
            fail("PreferencesActionDiscardException exception is expected.");
        } catch (PreferencesActionDiscardException e) {
            // expected
        }
    }
}
