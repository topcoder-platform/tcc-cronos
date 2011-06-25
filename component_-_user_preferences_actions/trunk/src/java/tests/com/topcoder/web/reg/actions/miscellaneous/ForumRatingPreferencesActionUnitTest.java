/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jivesoftware.base.User;
import com.jivesoftware.forum.ForumFactory;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;

/**
 * <p>
 * This class contains Unit tests for ForumRatingPreferencesAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ForumRatingPreferencesActionUnitTest extends BaseUnitTest {

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
        action = null;
        proxy = null;
        loggedInUser = null;
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction constructor.
     * </p>
     * <p>
     * ForumRatingPreferencesAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("ForumRatingPreferencesAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#execute() method with valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare() throws Exception {
        // prepare
        action.prepare();
        assertNotNull("Value should be set successfully.", action.getRatingCollapseMinCount());
        assertNotNull("Value should be set successfully.", action.getRatingCollapseMinMessages());
        assertNotNull("Value should be set successfully.", action.getRatingCollapseThreshold());
        assertNotNull("Value should be set successfully.", action.getRatingHighlightMinCount());
        assertNotNull("Value should be set successfully.", action.getRatingHighlightThreshold());
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#execute() method with submit action and valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit() throws Exception {
        action.prepare();
        action.setAction(SUBMIT_ACTION);
        // execute
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // 1 record should be updated and audited
        verify(action.getAuditDao(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#execute() method with discard action and valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Discard() throws Exception {
        // put backup session
        User user = createForumUser();
        putKeyValueToSession(action.getBackupSessionKey(), user);
        action.setAction(DISCARD_ACTION);
        // change user preference value
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // no records should be audited
        verify(action.getAuditDao(), times(0)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#execute() method with discard action, valid attributes and email true flag.
     * </p>
     * <p>
     * Execute should be ended successfully and email should be sent. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_EmailSend() throws Exception {
        // put backup session
        User user = createForumUser();
        putKeyValueToSession(action.getBackupSessionKey(), user);
        action.setAction(DISCARD_ACTION);
        action.setEmailSendFlag(true);
        // change user preference value
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // no records should be audited
        verify(action.getAuditDao(), times(0)).audit(any(AuditRecord.class));
        // check email sent manually
    }

    /**
     * <p>
     * Tests ForumRatingPreferencesAction#validate() method valid input argument passed.
     * </p>
     * <p>
     * Validate should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate() throws Exception {
        action.prepare();
        // provide validation
        action.validate();
    }
}
