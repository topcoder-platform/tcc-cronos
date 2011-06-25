/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jivesoftware.base.JiveConstants;
import com.jivesoftware.base.User;
import com.jivesoftware.forum.ForumFactory;
import com.jivesoftware.forum.ResultFilter;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.forums.ForumConstants;

/**
 * <p>
 * This class contains Unit tests for MyForumPostHistoryAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MyForumPostHistoryActionUnitTest extends BaseUnitTest {

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
        action = null;
        proxy = null;
        loggedInUser = null;
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction constructor.
     * </p>
     * <p>
     * MyForumPostHistoryAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("MyForumPostHistoryAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#execute() method with submit action and valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute() throws Exception {
        action.validate();
        action.setAction(SUBMIT_ACTION);
        // execute
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // 1 record should be updated and audited
        verify(action.getForumFactory(), times(1)).getUserMessages(any(User.class), any(ResultFilter.class));
        verify(action.getForumFactory(), times(1)).getUserMessageCount(any(User.class), any(ResultFilter.class));
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#validate() method valid input argument passed.
     * </p>
     * <p>
     * Validate should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate() throws Exception {
        action.setStartIndex(-1);
        action.setSortField("");
        action.setSortOrder("");
        action.setRange(-1);
        // provide validation
        action.validate();
        assertEquals("startIndex should be set.", 0, action.getStartIndex());
        assertEquals("range should be set.", ForumConstants.DEFAULT_HISTORY_RANGE, action.getRange());
        assertEquals("sortField should be set.", String.valueOf(JiveConstants.MODIFICATION_DATE),
                action.getSortField());
        assertEquals("sortOrder should be set.", String.valueOf(ResultFilter.DESCENDING), action.getSortOrder());
    }

    /**
     * <p>
     * Tests MyForumPostHistoryAction#prepare() method valid input argument passed.
     * </p>
     * <p>
     * Prepare should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare() throws Exception {
        // prepare
        action.prepare();
    }
}
