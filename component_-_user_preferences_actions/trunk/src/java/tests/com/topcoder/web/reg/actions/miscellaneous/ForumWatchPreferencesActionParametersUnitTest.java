/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;
import com.jivesoftware.base.UserNotFoundException;
import com.jivesoftware.forum.ForumFactory;
import com.opensymphony.xwork2.ActionProxy;

/**
 * <p>
 * This class contains Unit tests for ForumWatchPreferencesAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ForumWatchPreferencesActionParametersUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents ForumWatchPreferencesAction action for testing.
     * </p>
     */
    private ForumWatchPreferencesAction action;

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
    private com.topcoder.web.common.model.User loggedInUser;

    /**
     * <p>
     * Represents logged in forum user for testing.
     * </p>
     */
    private User forumUser;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        proxy = getActionProxy("/forumwatch");
        action = (ForumWatchPreferencesAction) proxy.getAction();
        setBasePreferencesActionDAOs(action);
        // put valid class for basic authentication session key
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), createAuthentication());
        loggedInUser = createUser(1L, "First", "Second", "handle", true);
        when(action.getUserDao().find(1L)).thenReturn(loggedInUser);
        action.setEmailBodyTemplateFileName(EMAIL_BODY_TEMPLATE_NAME);
        ForumFactory forumFactory = getForumFactory();
        forumUser = createForumUser();
        prepareForumFactory(forumFactory);
        action.setForumFactory(forumFactory);
        when(forumFactory.getUserManager().getUser(anyLong())).thenReturn(forumUser);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        action.setWatchFrequency(-1);
        action.clearFieldErrors();
        action = null;
        proxy = null;
        loggedInUser = null;
        forumUser = null;
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#getWatchFrequency() method.
     * </p>
     * <p>
     * watchFrequency should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetWatchFrequency() {
        int watchFrequency = 1;
        action.setWatchFrequency(watchFrequency);
        assertEquals("getWatchFrequency() doesn't work properly.", watchFrequency, action.getWatchFrequency());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#setWatchFrequency(int) method.
     * </p>
     * <p>
     * watchFrequency should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetWatchFrequency() {
        int watchFrequency = 1;
        action.setWatchFrequency(watchFrequency);
        assertEquals("setWatchFrequency() doesn't work properly.", watchFrequency, action.getWatchFrequency());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#getMarkWatchesRead() method.
     * </p>
     * <p>
     * markWatchesRead should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetMarkWatchesRead() {
        String markWatchesRead = "test";
        action.setMarkWatchesRead(markWatchesRead);
        assertSame("getMarkWatchesRead() doesn't work properly.", markWatchesRead, action.getMarkWatchesRead());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#setMarkWatchesRead(String) method.
     * </p>
     * <p>
     * markWatchesRead should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetMarkWatchesRead() {
        String markWatchesRead = "test";
        action.setMarkWatchesRead(markWatchesRead);
        assertSame("setMarkWatchesRead() doesn't work properly.", markWatchesRead, action.getMarkWatchesRead());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#getAutoWatchNewTopics() method.
     * </p>
     * <p>
     * autoWatchNewTopics should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAutoWatchNewTopics() {
        String autoWatchNewTopics = "test";
        action.setAutoWatchNewTopics(autoWatchNewTopics);
        assertSame("getAutoWatchNewTopics() doesn't work properly.", autoWatchNewTopics,
                action.getAutoWatchNewTopics());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#setAutoWatchNewTopics(String) method.
     * </p>
     * <p>
     * autoWatchNewTopics should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetAutoWatchNewTopics() {
        String autoWatchNewTopics = "test";
        action.setAutoWatchNewTopics(autoWatchNewTopics);
        assertSame("setAutoWatchNewTopics() doesn't work properly.", autoWatchNewTopics,
                action.getAutoWatchNewTopics());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#getAutoWatchReplies() method.
     * </p>
     * <p>
     * autoWatchReplies should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAutoWatchReplies() {
        String autoWatchReplies = "test";
        action.setAutoWatchReplies(autoWatchReplies);
        assertSame("getAutoWatchReplies() doesn't work properly.", autoWatchReplies, action.getAutoWatchReplies());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#setAutoWatchReplies(String) method.
     * </p>
     * <p>
     * autoWatchReplies should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetAutoWatchReplies() {
        String autoWatchReplies = "test";
        action.setAutoWatchReplies(autoWatchReplies);
        assertSame("setAutoWatchReplies() doesn't work properly.", autoWatchReplies, action.getAutoWatchReplies());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#execute() with no user in session.
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
     * Tests ForumWatchPreferencesAction#execute() with UserNotFoundException occurred in underlying manager.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_UserNotFound() throws Exception {
        when(action.getForumFactory().getUserManager().getUser(anyLong())).thenThrow(
                new UserNotFoundException("just for testing."));
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
     * Tests ForumWatchPreferencesAction#execute() with UnauthorizedException occurred in underlying user.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_UnauthorizedException() throws Exception {
        // prepare mock user
        forumUser = mock(User.class);
        when(action.getForumFactory().getUserManager().getUser(anyLong())).thenReturn(forumUser);
        doThrow(new UnauthorizedException("just for testing.")).when(forumUser).setProperty(anyString(), anyString());
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
     * Tests ForumWatchPreferencesAction#execute() with email send fail.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Preview_EmailFail() throws Exception {
        action.setAction(null);
        action.prepare();
        action.setEmailSendFlag(true);
        action.setEmailBodyTemplateFileName("Unknown");
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#execute() with no backed up user in session.
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

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#validate() method with invalid watchFrequency.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Invalid_WatchFrequency() throws Exception {
        action.prepare();
        action.setWatchFrequency(Integer.MIN_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#validate() method with null autoWatchNewTopics.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_AutoWatchNewTopics() throws Exception {
        action.prepare();
        action.setAutoWatchNewTopics(null);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#validate() method with null autoWatchReplies.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_AutoWatchReplies() throws Exception {
        action.prepare();
        action.setAutoWatchReplies(null);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#validate() method with null markWatchesRead.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_MarkWatchesRead() throws Exception {
        action.prepare();
        action.setMarkWatchesRead(null);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#validate() method with empty autoWatchNewTopics.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_AutoWatchNewTopics() throws Exception {
        action.prepare();
        action.setAutoWatchNewTopics("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#validate() method with empty autoWatchReplies.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_AutoWatchReplies() throws Exception {
        action.prepare();
        action.setAutoWatchReplies("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#validate() method with empty markWatchesRead.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_MarkWatchesRead() throws Exception {
        action.prepare();
        action.setMarkWatchesRead("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#validate() method with all invalid fields.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_AllErrors() throws Exception {
        action = new ForumWatchPreferencesAction();
        action.setLogger(getLog());
        action.setWatchFrequency(Integer.MIN_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 4, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumWatchPreferencesAction#prepare() with no user in forum database.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_NoForumUser() throws Exception {
        when(action.getForumFactory().getUserManager().getUser(anyLong())).thenReturn(null);
        try {
            action.prepare();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }
}
