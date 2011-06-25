/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import com.jivesoftware.base.User;
import com.jivesoftware.base.UserNotFoundException;
import com.jivesoftware.forum.ForumFactory;
import com.jivesoftware.forum.ForumMessage;
import com.jivesoftware.forum.action.util.Paginator;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.ejb.messagehistory.MessageHistoryLocal;

/**
 * <p>
 * This class contains Unit tests for MyForumPostHistoryAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MyForumPostHistoryActionParametersUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents MyForumPostHistoryAction action for testing.
     * </p>
     */
    private MyForumPostHistoryAction action;

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
        proxy = getActionProxy("/forumhistory");
        action = (MyForumPostHistoryAction) proxy.getAction();
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
     * Tests MyForumPostHistoryAction#execute() with no user in session.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_NoUser() throws Exception {
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), null);
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#execute() with UserNotFoundException occurred in underlying manager.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_UserNotFound() throws Exception {
        when(action.getForumFactory().getUserManager().getUser(anyLong())).thenThrow(
                new UserNotFoundException("just for testing."));
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#validate() method with not valid integer sortOrder.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_NotInteger_SortOrder() throws Exception {
        action = new MyForumPostHistoryAction();
        action.setLogger(getLog());
        action.validate();
        action.setSortOrder("Unknown");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#validate() method with not valid integer sortField.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_NotInteger_SortField() throws Exception {
        action = new MyForumPostHistoryAction();
        action.setLogger(getLog());
        action.validate();
        action.setSortField("Unknown");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#validate() method with all invalid fields.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_AllErrors() throws Exception {
        action = new MyForumPostHistoryAction();
        action.setLogger(getLog());
        action.setSortField("Unknown");
        action.setSortOrder("Unknown");
        action.validate();
        assertEquals("Fields error should be added.", 2, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#getStartIndex() method.
     * </p>
     * <p>
     * startIndex should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetStartIndex() {
        int startIndex = 1;
        action.setStartIndex(startIndex);
        assertEquals("getStartIndex() doesn't work properly.", startIndex, action.getStartIndex());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#setStartIndex(int) method.
     * </p>
     * <p>
     * startIndex should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetStartIndex() {
        int startIndex = 1;
        action.setStartIndex(startIndex);
        assertEquals("setStartIndex() doesn't work properly.", startIndex, action.getStartIndex());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#getRange() method.
     * </p>
     * <p>
     * range should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetRange() {
        int range = 1;
        action.setRange(range);
        assertEquals("getRange() doesn't work properly.", range, action.getRange());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#setRange(int) method.
     * </p>
     * <p>
     * range should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetRange() {
        int range = 1;
        action.setRange(range);
        assertEquals("setRange() doesn't work properly.", range, action.getRange());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#getSortField() method.
     * </p>
     * <p>
     * sortField should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetSortField() {
        String sortField = "test";
        action.setSortField(sortField);
        assertSame("getSortField() doesn't work properly.", sortField, action.getSortField());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#setSortField(String) method.
     * </p>
     * <p>
     * sortField should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetSortField() {
        String sortField = "test";
        action.setSortField(sortField);
        assertSame("setSortField() doesn't work properly.", sortField, action.getSortField());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#getSortOrder() method.
     * </p>
     * <p>
     * sortOrder should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetSortOrder() {
        String sortOrder = "test";
        action.setSortOrder(sortOrder);
        assertSame("getSortOrder() doesn't work properly.", sortOrder, action.getSortOrder());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#setSortOrder(String) method.
     * </p>
     * <p>
     * sortOrder should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetSortOrder() {
        String sortOrder = "test";
        action.setSortOrder(sortOrder);
        assertSame("setSortOrder() doesn't work properly.", sortOrder, action.getSortOrder());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#getHistoryUser() method.
     * </p>
     * <p>
     * historyUser should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetHistoryUser() {
        User historyUser = mock(User.class);
        action.setHistoryUser(historyUser);
        assertSame("getHistoryUser() doesn't work properly.", historyUser, action.getHistoryUser());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#setHistoryUser(User) method.
     * </p>
     * <p>
     * historyUser should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetHistoryUser() {
        User historyUser = mock(User.class);
        action.setHistoryUser(historyUser);
        assertSame("setHistoryUser() doesn't work properly.", historyUser, action.getHistoryUser());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#getMessages() method.
     * </p>
     * <p>
     * messages should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetMessages() {
        Iterator < ForumMessage > messages = mock(Iterator.class);
        action.setMessages(messages);
        assertSame("getMessages() doesn't work properly.", messages, action.getMessages());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#setMessages(Iterator) method.
     * </p>
     * <p>
     * messages should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetMessages() {
        Iterator < ForumMessage > messages = mock(Iterator.class);
        action.setMessages(messages);
        assertSame("setMessages() doesn't work properly.", messages, action.getMessages());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#getPaginator() method.
     * </p>
     * <p>
     * paginator should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetPaginator() {
        assertNull("paginator should be null.", action.getPaginator());
        Paginator paginator = mock(Paginator.class);
        action.setPaginator(paginator);
        assertSame("getPaginator() doesn't work properly.", paginator, action.getPaginator());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#setPaginator(Paginator) method.
     * </p>
     * <p>
     * paginator should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPaginator() {
        Paginator paginator = mock(Paginator.class);
        action.setPaginator(paginator);
        assertSame("setPaginator() doesn't work properly.", paginator, action.getPaginator());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#getMessageHistoryBean() method.
     * </p>
     * <p>
     * messageHistoryBean should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetMessageHistoryBean() {
        MessageHistoryLocal messageHistoryBean = mock(MessageHistoryLocal.class);
        action.setMessageHistoryBean(messageHistoryBean);
        assertSame("getMessageHistoryBean() doesn't work properly.", messageHistoryBean,
                action.getMessageHistoryBean());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#setMessageHistoryBean(MessageHistoryLocal) method.
     * </p>
     * <p>
     * messageHistoryBean should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetMessageHistoryBean() {
        MessageHistoryLocal messageHistoryBean = mock(MessageHistoryLocal.class);
        action.setMessageHistoryBean(messageHistoryBean);
        assertSame("setMessageHistoryBean() doesn't work properly.", messageHistoryBean,
                action.getMessageHistoryBean());
    }
}
