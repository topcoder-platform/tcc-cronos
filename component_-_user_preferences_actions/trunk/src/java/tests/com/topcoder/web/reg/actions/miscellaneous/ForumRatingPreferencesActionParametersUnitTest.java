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
 * This class contains Unit tests for ForumRatingPreferencesAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ForumRatingPreferencesActionParametersUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents ForumRatingPreferencesAction action for testing.
     * </p>
     */
    private ForumRatingPreferencesAction action;

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
        proxy = getActionProxy("/forumrating");
        action = (ForumRatingPreferencesAction) proxy.getAction();
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
        action.clearFieldErrors();
        action = null;
        proxy = null;
        loggedInUser = null;
        forumUser = null;
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#getShowRatings() method.
     * </p>
     * <p>
     * showRatings should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetShowRatings() {
        String showRatings = "test";
        action.setShowRatings(showRatings);
        assertSame("getShowRatings() doesn't work properly.", showRatings, action.getShowRatings());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#setShowRatings(String) method.
     * </p>
     * <p>
     * showRatings should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetShowRatings() {
        String showRatings = "test";
        action.setShowRatings(showRatings);
        assertSame("setShowRatings() doesn't work properly.", showRatings, action.getShowRatings());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#getRatingHighlightThreshold() method.
     * </p>
     * <p>
     * ratingHighlightThreshold should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetRatingHighlightThreshold() {
        String ratingHighlightThreshold = "test";
        action.setRatingHighlightThreshold(ratingHighlightThreshold);
        assertSame("getRatingHighlightThreshold() doesn't work properly.", ratingHighlightThreshold,
                action.getRatingHighlightThreshold());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#setRatingHighlightThreshold(String) method.
     * </p>
     * <p>
     * ratingHighlightThreshold should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetRatingHighlightThreshold() {
        String ratingHighlightThreshold = "test";
        action.setRatingHighlightThreshold(ratingHighlightThreshold);
        assertSame("setRatingHighlightThreshold() doesn't work properly.", ratingHighlightThreshold,
                action.getRatingHighlightThreshold());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#getRatingHighlightMinCount() method.
     * </p>
     * <p>
     * ratingHighlightMinCount should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetRatingHighlightMinCount() {
        String ratingHighlightMinCount = "test";
        action.setRatingHighlightMinCount(ratingHighlightMinCount);
        assertSame("getRatingHighlightMinCount() doesn't work properly.", ratingHighlightMinCount,
                action.getRatingHighlightMinCount());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#setRatingHighlightMinCount(String) method.
     * </p>
     * <p>
     * ratingHighlightMinCount should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetRatingHighlightMinCount() {
        String ratingHighlightMinCount = "test";
        action.setRatingHighlightMinCount(ratingHighlightMinCount);
        assertSame("setRatingHighlightMinCount() doesn't work properly.", ratingHighlightMinCount,
                action.getRatingHighlightMinCount());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#getRatingCollapseThreshold() method.
     * </p>
     * <p>
     * ratingCollapseThreshold should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetRatingCollapseThreshold() {
        String ratingCollapseThreshold = "test";
        action.setRatingCollapseThreshold(ratingCollapseThreshold);
        assertSame("getRatingCollapseThreshold() doesn't work properly.", ratingCollapseThreshold,
                action.getRatingCollapseThreshold());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#setRatingCollapseThreshold(String) method.
     * </p>
     * <p>
     * ratingCollapseThreshold should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetRatingCollapseThreshold() {
        String ratingCollapseThreshold = "test";
        action.setRatingCollapseThreshold(ratingCollapseThreshold);
        assertSame("setRatingCollapseThreshold() doesn't work properly.", ratingCollapseThreshold,
                action.getRatingCollapseThreshold());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#getRatingCollapseMinMessages() method.
     * </p>
     * <p>
     * ratingCollapseMinMessages should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetRatingCollapseMinMessages() {
        String ratingCollapseMinMessages = "test";
        action.setRatingCollapseMinMessages(ratingCollapseMinMessages);
        assertSame("getRatingCollapseMinMessages() doesn't work properly.", ratingCollapseMinMessages,
                action.getRatingCollapseMinMessages());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#setRatingCollapseMinMessages(String) method.
     * </p>
     * <p>
     * ratingCollapseMinMessages should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetRatingCollapseMinMessages() {
        String ratingCollapseMinMessages = "test";
        action.setRatingCollapseMinMessages(ratingCollapseMinMessages);
        assertSame("setRatingCollapseMinMessages() doesn't work properly.", ratingCollapseMinMessages,
                action.getRatingCollapseMinMessages());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#getRatingCollapseMinCount() method.
     * </p>
     * <p>
     * ratingCollapseMinCount should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetRatingCollapseMinCount() {
        String ratingCollapseMinCount = "test";
        action.setRatingCollapseMinCount(ratingCollapseMinCount);
        assertSame("getRatingCollapseMinCount() doesn't work properly.", ratingCollapseMinCount,
                action.getRatingCollapseMinCount());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#setRatingCollapseMinCount(String) method.
     * </p>
     * <p>
     * ratingCollapseMinCount should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetRatingCollapseMinCount() {
        String ratingCollapseMinCount = "test";
        action.setRatingCollapseMinCount(ratingCollapseMinCount);
        assertSame("setRatingCollapseMinCount() doesn't work properly.", ratingCollapseMinCount,
                action.getRatingCollapseMinCount());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#execute() with no user in session.
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
     * Tests ForumRatingPreferencesAction#execute() with UserNotFoundException occurred in underlying manager.
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
     * Tests ForumRatingPreferencesAction#execute() with UnauthorizedException occurred in underlying user.
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
     * Tests ForumRatingPreferencesAction#execute() with email send fail.
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
     * Tests ForumRatingPreferencesAction#execute() with no backed up user in session.
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
     * Tests ForumRatingPreferencesAction#validate() method with null ratingCollapseMinCount.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_RatingCollapseMinCount() throws Exception {
        action.prepare();
        action.setRatingCollapseMinCount(null);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with null showRating.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_ShowRating() throws Exception {
        action.prepare();
        action.setShowRatings(null);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with null ratingCollapseMinMessages.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_RatingCollapseMinMessages() throws Exception {
        action.prepare();
        action.setRatingCollapseMinMessages(null);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with null ratingCollapseThreshold.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_RatingCollapseThreshold() throws Exception {
        action.prepare();
        action.setRatingCollapseThreshold(null);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with null ratingHighlightMinCount.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_RatingHighlightMinCount() throws Exception {
        action.prepare();
        action.setRatingHighlightMinCount(null);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with null ratingHighlightThreshold.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_RatingHighlightThreshold() throws Exception {
        action.prepare();
        action.setRatingHighlightThreshold(null);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with empty ratingCollapseMinCount.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_RatingCollapseMinCount() throws Exception {
        action.prepare();
        action.setRatingCollapseMinCount("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with empty ratingCollapseMinMessages.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_RatingCollapseMinMessages() throws Exception {
        action.prepare();
        action.setRatingCollapseMinMessages("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with empty ratingCollapseThreshold.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_RatingCollapseThreshold() throws Exception {
        action.prepare();
        action.setRatingCollapseThreshold("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with empty ratingHighlightMinCount.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_RatingHighlightMinCount() throws Exception {
        action.prepare();
        action.setRatingHighlightMinCount("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with empty ratingHighlightThreshold.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_RatingHighlightThreshold() throws Exception {
        action.prepare();
        action.setRatingHighlightThreshold("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with empty showRating.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_ShowRating() throws Exception {
        action.prepare();
        action.setShowRatings("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method with all invalid fields.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_AllErrors() throws Exception {
        action = new ForumRatingPreferencesAction();
        action.setLogger(getLog());
        action.validate();
        assertEquals("Fields error should be added.", 6, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#prepare() with no user in forum database.
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
