/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.dao.MemberContactBlackListDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.MemberContactBlackList;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This class contains Unit tests for BlackListAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BlackListActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents BlackListAction action for testing.
     * </p>
     */
    private BlackListAction action;

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
        proxy = getActionProxy("/blacklist");
        action = (BlackListAction) proxy.getAction();
        setBasePreferencesActionDAOs(action);
        action.setEmailBodyTemplateFileName(EMAIL_BODY_TEMPLATE_NAME);
        action.setMemberContactBlackListDao(getMemberContactBlackListDao());
        // put valid class for basic authentication session key
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), createAuthentication());
        loggedInUser = createUser(1L, "First", "Second", "handle", true);
        when(action.getUserDao().find(1L)).thenReturn(loggedInUser);
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
     * Tests BlackListAction constructor.
     * </p>
     * <p>
     * BlackListAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("BlackListAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests BlackListAction#execute() method with valid attributes.
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
        assertEquals("Blocked users should be set.", 2, action.getBlockedUsers().size());
        assertEquals("Previously blocked users should be set.", 1, action.getPreviouslyBlockedUsers().size());
    }

    /**
     * <p>
     * Tests BlackListAction#execute() method with submit action and valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit() throws Exception {
        // prepare blocked users
        MemberContactBlackListDAO memberContactBlackListDAO = getMemberContactBlackListDao();
        prepareMemberContactBlackListDAO(memberContactBlackListDAO);
        UserDAO userDAO = action.getUserDao();
        action.prepare();
        action.setAction(SUBMIT_ACTION);
        // add new 2 block user and remove one
        List < User > blockedUsers = new ArrayList < User >(action.getBlockedUsers());
        User removedUser = blockedUsers.get(0);
        String addedHandle1 = "handle5";
        String addedHandle2 = "handle6";
        User addedUser1 = createUser(5L, "First5", "Last5", addedHandle1, true);
        User addedUser2 = createUser(6L, "First6", "Last6", addedHandle2, true);
        blockedUsers.set(0, addedUser1);
        blockedUsers.add(addedUser2);
        action.setBlockedUsers(blockedUsers);
        // prepare userDao
        when(userDAO.find(removedUser.getHandle(), true)).thenReturn(removedUser);
        when(userDAO.find(addedHandle1, true)).thenReturn(addedUser1);
        when(userDAO.find(addedHandle2, true)).thenReturn(addedUser2);
        // prepare memberContactBlackListDao
        MemberContactBlackList blackList = new MemberContactBlackList();
        blackList.setId(new MemberContactBlackList.Identifier(loggedInUser, removedUser));
        when(memberContactBlackListDAO.find(eq(loggedInUser), any(User.class))).thenReturn(blackList);
        // execute
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // 3 records should be updated and audited
        verify(memberContactBlackListDAO, times(3)).saveOrUpdate(any(MemberContactBlackList.class));
        verify(action.getAuditDao(), times(3)).audit(any(AuditRecord.class));
        // one user should be updated
        verify(action.getUserDao(), times(0)).saveOrUpdate(any(User.class));
    }

    /**
     * <p>
     * Tests BlackListAction#execute() method with discard action and valid attributes.
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
     * Tests BlackListAction#execute() method with discard action, valid attributes and email true flag.
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
     * Tests BlackListAction#validate() method valid input argument passed.
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
