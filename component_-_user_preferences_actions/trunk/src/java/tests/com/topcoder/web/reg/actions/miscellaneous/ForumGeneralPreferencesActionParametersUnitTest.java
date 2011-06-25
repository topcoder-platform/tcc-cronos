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
 * This class contains Unit tests for ForumGeneralPreferencesAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ForumGeneralPreferencesActionParametersUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents ForumGeneralPreferencesAction action for testing.
     * </p>
     */
    private ForumGeneralPreferencesAction action;

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
        proxy = getActionProxy("/forumgeneral");
        action = (ForumGeneralPreferencesAction) proxy.getAction();
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
     * Tests ForumGeneralPreferencesAction#getForumsPerPage() method.
     * </p>
     * <p>
     * forumsPerPage should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetForumsPerPage() {
        int forumsPerPage = 1;
        action.setForumsPerPage(forumsPerPage);
        assertEquals("getForumsPerPage() doesn't work properly.", forumsPerPage, action.getForumsPerPage());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setForumsPerPage(int) method.
     * </p>
     * <p>
     * forumsPerPage should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetForumsPerPage() {
        int forumsPerPage = 1;
        action.setForumsPerPage(forumsPerPage);
        assertEquals("setForumsPerPage() doesn't work properly.", forumsPerPage, action.getForumsPerPage());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getThreadsPerPage() method.
     * </p>
     * <p>
     * threadsPerPage should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetThreadsPerPage() {
        int threadsPerPage = 1;
        action.setThreadsPerPage(threadsPerPage);
        assertEquals("getThreadsPerPage() doesn't work properly.", threadsPerPage, action.getThreadsPerPage());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setThreadsPerPage(int) method.
     * </p>
     * <p>
     * threadsPerPage should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetThreadsPerPage() {
        int threadsPerPage = 1;
        action.setThreadsPerPage(threadsPerPage);
        assertEquals("setThreadsPerPage() doesn't work properly.", threadsPerPage, action.getThreadsPerPage());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getMessagesPerPage() method.
     * </p>
     * <p>
     * messagesPerPage should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetMessagesPerPage() {
        int messagesPerPage = 1;
        action.setMessagesPerPage(messagesPerPage);
        assertEquals("getMessagesPerPage() doesn't work properly.", messagesPerPage, action.getMessagesPerPage());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setMessagesPerPage(int) method.
     * </p>
     * <p>
     * messagesPerPage should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetMessagesPerPage() {
        int messagesPerPage = 1;
        action.setMessagesPerPage(messagesPerPage);
        assertEquals("setMessagesPerPage() doesn't work properly.", messagesPerPage, action.getMessagesPerPage());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getMessagesPerHistoryPage() method.
     * </p>
     * <p>
     * messagesPerHistoryPage should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetMessagesPerHistoryPage() {
        int messagesPerHistoryPage = 1;
        action.setMessagesPerHistoryPage(messagesPerHistoryPage);
        assertEquals("getMessagesPerHistoryPage() doesn't work properly.", messagesPerHistoryPage,
                action.getMessagesPerHistoryPage());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setMessagesPerHistoryPage(int) method.
     * </p>
     * <p>
     * messagesPerHistoryPage should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetMessagesPerHistoryPage() {
        int messagesPerHistoryPage = 1;
        action.setMessagesPerHistoryPage(messagesPerHistoryPage);
        assertEquals("setMessagesPerHistoryPage() doesn't work properly.", messagesPerHistoryPage,
                action.getMessagesPerHistoryPage());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getResultsPerSearchPage() method.
     * </p>
     * <p>
     * resultsPerSearchPage should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetResultsPerSearchPage() {
        int resultsPerSearchPage = 1;
        action.setResultsPerSearchPage(resultsPerSearchPage);
        assertEquals("getResultsPerSearchPage() doesn't work properly.", resultsPerSearchPage,
                action.getResultsPerSearchPage());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setResultsPerSearchPage(int) method.
     * </p>
     * <p>
     * resultsPerSearchPage should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetResultsPerSearchPage() {
        int resultsPerSearchPage = 1;
        action.setResultsPerSearchPage(resultsPerSearchPage);
        assertEquals("setResultsPerSearchPage() doesn't work properly.", resultsPerSearchPage,
                action.getResultsPerSearchPage());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getThreadMode() method.
     * </p>
     * <p>
     * threadMode should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetThreadMode() {
        String threadMode = "test";
        action.setThreadMode(threadMode);
        assertSame("getThreadMode() doesn't work properly.", threadMode, action.getThreadMode());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setThreadMode(String) method.
     * </p>
     * <p>
     * threadMode should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetThreadMode() {
        String threadMode = "test";
        action.setThreadMode(threadMode);
        assertSame("setThreadMode() doesn't work properly.", threadMode, action.getThreadMode());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getFlatMode() method.
     * </p>
     * <p>
     * flatMode should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetFlatMode() {
        String flatMode = "test";
        action.setFlatMode(flatMode);
        assertSame("getFlatMode() doesn't work properly.", flatMode, action.getFlatMode());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setFlatMode(String) method.
     * </p>
     * <p>
     * flatMode should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetFlatMode() {
        String flatMode = "test";
        action.setFlatMode(flatMode);
        assertSame("setFlatMode() doesn't work properly.", flatMode, action.getFlatMode());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getShowPrevNextThreads() method.
     * </p>
     * <p>
     * showPrevNextThreads should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetShowPrevNextThreads() {
        String showPrevNextThreads = "test";
        action.setShowPrevNextThreads(showPrevNextThreads);
        assertSame("getShowPrevNextThreads() doesn't work properly.", showPrevNextThreads,
                action.getShowPrevNextThreads());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setShowPrevNextThreads(String) method.
     * </p>
     * <p>
     * showPrevNextThreads should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetShowPrevNextThreads() {
        String showPrevNextThreads = "test";
        action.setShowPrevNextThreads(showPrevNextThreads);
        assertSame("setShowPrevNextThreads() doesn't work properly.", showPrevNextThreads,
                action.getShowPrevNextThreads());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getDisplayMemberPhoto() method.
     * </p>
     * <p>
     * displayMemberPhoto should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetDisplayMemberPhoto() {
        String displayMemberPhoto = "test";
        action.setDisplayMemberPhoto(displayMemberPhoto);
        assertSame("getDisplayMemberPhoto() doesn't work properly.", displayMemberPhoto,
                action.getDisplayMemberPhoto());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setDisplayMemberPhoto(String) method.
     * </p>
     * <p>
     * displayMemberPhoto should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDisplayMemberPhoto() {
        String displayMemberPhoto = "test";
        action.setDisplayMemberPhoto(displayMemberPhoto);
        assertSame("setDisplayMemberPhoto() doesn't work properly.", displayMemberPhoto,
                action.getDisplayMemberPhoto());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getDisplayAllMemberPhotos() method.
     * </p>
     * <p>
     * displayAllMemberPhotos should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetDisplayAllMemberPhotos() {
        String displayAllMemberPhotos = "test";
        action.setDisplayAllMemberPhotos(displayAllMemberPhotos);
        assertSame("getDisplayAllMemberPhotos() doesn't work properly.", displayAllMemberPhotos,
                action.getDisplayAllMemberPhotos());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setDisplayAllMemberPhotos(String) method.
     * </p>
     * <p>
     * displayAllMemberPhotos should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDisplayAllMemberPhotos() {
        String displayAllMemberPhotos = "test";
        action.setDisplayAllMemberPhotos(displayAllMemberPhotos);
        assertSame("setDisplayAllMemberPhotos() doesn't work properly.", displayAllMemberPhotos,
                action.getDisplayAllMemberPhotos());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getCollapseRead() method.
     * </p>
     * <p>
     * collapseRead should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCollapseRead() {
        String collapseRead = "test";
        action.setCollapseRead(collapseRead);
        assertSame("getCollapseRead() doesn't work properly.", collapseRead, action.getCollapseRead());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setCollapseRead(String) method.
     * </p>
     * <p>
     * collapseRead should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCollapseRead() {
        String collapseRead = "test";
        action.setCollapseRead(collapseRead);
        assertSame("setCollapseRead() doesn't work properly.", collapseRead, action.getCollapseRead());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getCollapseReadDays() method.
     * </p>
     * <p>
     * collapseReadDays should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCollapseReadDays() {
        String collapseReadDays = "test";
        action.setCollapseReadDays(collapseReadDays);
        assertSame("getCollapseReadDays() doesn't work properly.", collapseReadDays, action.getCollapseReadDays());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setCollapseReadDays(String) method.
     * </p>
     * <p>
     * collapseReadDays should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCollapseReadDays() {
        String collapseReadDays = "test";
        action.setCollapseReadDays(collapseReadDays);
        assertSame("setCollapseReadDays() doesn't work properly.", collapseReadDays, action.getCollapseReadDays());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getCollapseReadPosts() method.
     * </p>
     * <p>
     * collapseReadPosts should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCollapseReadPosts() {
        String collapseReadPosts = "test";
        action.setCollapseReadPosts(collapseReadPosts);
        assertSame("getCollapseReadPosts() doesn't work properly.", collapseReadPosts, action.getCollapseReadPosts());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setCollapseReadPosts(String) method.
     * </p>
     * <p>
     * collapseReadPosts should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCollapseReadPosts() {
        String collapseReadPosts = "test";
        action.setCollapseReadPosts(collapseReadPosts);
        assertSame("setCollapseReadPosts() doesn't work properly.", collapseReadPosts, action.getCollapseReadPosts());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#getCollapseReadShowReplied() method.
     * </p>
     * <p>
     * collapseReadShowReplied should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCollapseReadShowReplied() {
        String collapseReadShowReplied = "test";
        action.setCollapseReadShowReplied(collapseReadShowReplied);
        assertSame("getCollapseReadShowReplied() doesn't work properly.", collapseReadShowReplied,
                action.getCollapseReadShowReplied());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#setCollapseReadShowReplied(String) method.
     * </p>
     * <p>
     * collapseReadShowReplied should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCollapseReadShowReplied() {
        String collapseReadShowReplied = "test";
        action.setCollapseReadShowReplied(collapseReadShowReplied);
        assertSame("setCollapseReadShowReplied() doesn't work properly.", collapseReadShowReplied,
                action.getCollapseReadShowReplied());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#execute() with no user in session.
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
     * Tests ForumGeneralPreferencesAction#execute() with UserNotFoundException occurred in underlying manager.
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
     * Tests ForumGeneralPreferencesAction#execute() with UnauthorizedException occurred in underlying user.
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
     * Tests ForumGeneralPreferencesAction#execute() with email send fail.
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
     * Tests ForumGeneralPreferencesAction#execute() with no backed up user in session.
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
     * Tests ForumGeneralPreferencesAction#validate() method greater than maximum threadsPerPage.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Greater_ThreadsPerPage() throws Exception {
        action.prepare();
        action.setThreadsPerPage(Integer.MAX_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#validate() method greater than maximum messagesPerHistoryPage.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Greater_MessagesPerHistoryPage() throws Exception {
        action.prepare();
        action.setMessagesPerHistoryPage(Integer.MAX_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#validate() method greater than maximum messagesPerPage.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Greater_MessagesPerPage() throws Exception {
        action.prepare();
        action.setMessagesPerPage(Integer.MAX_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#validate() method greater than maximum forumsPerPage.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Greater_ForumsPerPage() throws Exception {
        action.prepare();
        action.setForumsPerPage(Integer.MAX_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#validate() method greater than maximum resultsPerSearchPage.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Greater_ResultsPerSearchPage() throws Exception {
        action.prepare();
        action.setResultsPerSearchPage(Integer.MAX_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#validate() method less than minimum threadsPerPage.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Less_ThreadsPerPage() throws Exception {
        action.prepare();
        action.setThreadsPerPage(Integer.MIN_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#validate() method less than minimum messagesPerHistoryPage.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Less_MessagesPerHistoryPage() throws Exception {
        action.prepare();
        action.setMessagesPerHistoryPage(Integer.MIN_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#validate() method less than minimum messagesPerPage.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Less_MessagesPerPage() throws Exception {
        action.prepare();
        action.setMessagesPerPage(Integer.MIN_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#validate() method less than minimum forumsPerPage.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Less_ForumsPerPage() throws Exception {
        action.prepare();
        action.setForumsPerPage(Integer.MIN_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#validate() method less than minimum resultsPerSearchPage.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Less_ResultsPerSearchPage() throws Exception {
        action.prepare();
        action.setResultsPerSearchPage(Integer.MIN_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#validate() method with all invalid fields.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_AllErrors() throws Exception {
        action.prepare();
        action.setThreadsPerPage(Integer.MAX_VALUE);
        action.setMessagesPerHistoryPage(Integer.MAX_VALUE);
        action.setMessagesPerPage(Integer.MAX_VALUE);
        action.setForumsPerPage(Integer.MAX_VALUE);
        action.setResultsPerSearchPage(Integer.MAX_VALUE);
        action.validate();
        assertEquals("Fields error should be added.", 5, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ForumGeneralPreferencesAction#prepare() with no user in forum database.
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
