/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.dao.MemberContactBlackListDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This class contains Unit tests for EmailNotificationAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EmailNotificationActionUnitTest extends BaseUnitTest {

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
     * Tests EmailNotificationAction constructor.
     * </p>
     * <p>
     * EmailNotificationAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("EmailNotificationAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests EmailNotificationAction#execute() method with valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare() throws Exception {
        // prepare blocked users
        MemberContactBlackListDAO memberContactBlackListDAO = getMemberContactBlackListDao();
        prepareMemberContactBlackListDAO(memberContactBlackListDAO);
        action.prepare();
        assertEquals("Notifications should be set.", 2, action.getNotifications().size());
    }

    /**
     * <p>
     * Tests EmailNotificationAction#execute() method with submit action and valid attributes.
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
        // 3 records should be updated and audited
        verify(action.getAuditDao(), times(1)).audit(any(AuditRecord.class));
        // one user should be updated
        verify(action.getUserDao(), times(1)).saveOrUpdate(any(User.class));
    }

    /**
     * <p>
     * Tests EmailNotificationAction#execute() method with discard action and valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Discard() throws Exception {
        // put backup session
        User user = new User();
        user.setId(2L);
        putKeyValueToSession(action.getBackupSessionKey(), user);
        action.setAction(DISCARD_ACTION);
        // change user preference value
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // one user should be retrieved from backup session key
        verify(action.getUserDao(), times(1)).saveOrUpdate(user);
        // no records should be audited
        verify(action.getAuditDao(), times(0)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests EmailNotificationAction#execute() method with discard action, valid attributes and email true flag.
     * </p>
     * <p>
     * Execute should be ended successfully and email should be sent. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_EmailSend() throws Exception {
        // put backup session
        User user = new User();
        user.setId(2L);
        putKeyValueToSession(action.getBackupSessionKey(), user);
        action.setAction(DISCARD_ACTION);
        action.setEmailSendFlag(true);
        // change user preference value
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // one user should be retrieved from backup session key
        verify(action.getUserDao(), times(1)).saveOrUpdate(user);
        // no records should be audited
        verify(action.getAuditDao(), times(0)).audit(any(AuditRecord.class));
        // check email sent manually
    }

    /**
     * <p>
     * Tests EmailNotificationAction#validate() method valid input argument passed.
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
