/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jivesoftware.base.UserNotFoundException;
import com.jivesoftware.forum.ForumFactory;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This class contains tests for Helper.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTest extends BaseUnitTest {

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
    private User loggedInUser;

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
        prepareForumFactory(forumFactory);
        action.setForumFactory(forumFactory);
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
    }

    /**
     * <p>
     * Tests Helper#logAndWrapException() with valid argument passed.
     * </p>
     * <p>
     * Exception should be wrapped successfully.
     * </p>
     */
    public void testLogAndWrapException() {
        assertNotNull("Exception should be created successfully", Helper.logAndWrapException(action.getLogger(),
                "testLogAndWrapException()", "just for testing.", new NullPointerException("just for testing."),
                UserPreferencesActionExecutionException.class));
    }

    /**
     * <p>
     * Tests Helper#logAndWrapException() with valid argument passed.
     * </p>
     * <p>
     * Exception should be wrapped successfully.
     * </p>
     */
    public void testLogAndWrapException_NullCause() {
        assertNotNull("Exception should be created successfully", Helper.logAndWrapException(action.getLogger(),
                "testLogAndWrapException()", "just for testing.", null, UserPreferencesActionExecutionException.class));
    }

    /**
     * <p>
     * Tests Helper#checkNotNullNorEmpty() with null argument passed.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testCheckNotNullNorEmpty_Null() {
        Helper.checkNotNullNorEmpty(action.getLogger(), getName(), action, null, "field");
        assertNotNull("Field error should be added.", action.getFieldErrors().get("field"));
    }

    /**
     * <p>
     * Tests Helper#checkNotNullNorEmpty() with empty argument passed.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testCheckNotNullNorEmpty_Empty() {
        Helper.checkNotNullNorEmpty(action.getLogger(), getName(), action, "", "field");
        assertNotNull("Field error should be added.", action.getFieldErrors().get("field"));
    }

    /**
     * <p>
     * Tests Helper#checkIntegerFieldGreater() with not greater argument passed.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     */
    public void testCheckIntegerFieldGreater() {
        Helper.checkIntegerFieldGreater(action.getLogger(), getName(), action, Integer.MIN_VALUE, "field",
                Integer.MAX_VALUE);
        assertNotNull("Field error should be added.", action.getFieldErrors().get("field"));
    }

    /**
     * <p>
     * Tests Helper#getLoggedInForumUser() with valid arguments passed.
     * </p>
     * <p>
     * User should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetLoggedInForumUser() throws Exception {
        com.jivesoftware.base.User user = Helper.getLoggedInForumUser(action, getName());
        assertNotNull("User should be retrieved successfully.", user);
    }

    /**
     * <p>
     * Tests Helper#checkIntegerFieldGreater() with valid argument passed, but user is not found.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetLoggedInForumUser_NoUser1() throws Exception {
        when(action.getForumFactory().getUserManager().getUser(anyLong())).thenReturn(null);
        try {
            Helper.getLoggedInForumUser(action, getName());
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests Helper#checkIntegerFieldGreater() with valid null methodSignature passed and user is not found.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetLoggedInForumUser_NoUser2() throws Exception {
        when(action.getForumFactory().getUserManager().getUser(anyLong())).thenThrow(
                new UserNotFoundException("just for testing."));
        try {
            Helper.getLoggedInForumUser(action, null);
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests Helper#getUserPropertiesStringRepresentation() with valid argument passed.
     * </p>
     * <p>
     * User properties string representation should be created.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetUserPropertiesStringRepresentation() throws Exception {
        assertNotNull("User properties string representation should be created.",
                Helper.getUserPropertiesStringRepresentation(createForumUser()));
    }

    /**
     * <p>
     * Tests Helper#handleDiscardAction() with valid argument passed.
     * </p>
     * <p>
     * Discard action should be handled.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testHandleDiscardAction() throws Exception {
        // put backup session
        User user = new User();
        user.setId(2L);
        putKeyValueToSession(action.getBackupSessionKey(), user);
        Helper.handleDiscardAction(action, getName());
        verify(action.getUserDao(), times(1)).saveOrUpdate(any(User.class));
    }

    /**
     * <p>
     * Tests Helper#handleDiscardForumAction() with valid argument passed.
     * </p>
     * <p>
     * Discard action should be handled.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testHandleDiscardForumAction() throws Exception {
        com.jivesoftware.base.User user = createForumUser();
        putKeyValueToSession(action.getBackupSessionKey(), user);
        Helper.handleDiscardForumAction(action, getName());
        verify(action.getUserDao(), times(0)).saveOrUpdate(any(User.class));
    }
}
