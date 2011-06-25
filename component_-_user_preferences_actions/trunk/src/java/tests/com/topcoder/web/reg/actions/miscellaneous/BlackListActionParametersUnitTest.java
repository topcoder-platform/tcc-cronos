/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.common.dao.MemberContactBlackListDAO;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This class contains Unit tests for BlackListAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BlackListActionParametersUnitTest extends BaseUnitTest {

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
        action.setMemberContactBlackListDao(getMemberContactBlackListDao());
        // put valid class for basic authentication session key
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), createAuthentication());
        loggedInUser = createUser(1L, "First", "Second", "handle", true);
        when(action.getUserDao().find(1L)).thenReturn(loggedInUser);
        prepareMemberContactBlackListDAO(action.getMemberContactBlackListDao());
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
        action.setBlockedUsersParametersKey("blockedUsersKey");
        action = null;
        proxy = null;
        loggedInUser = null;
    }

    /**
     * <p>
     * Tests BlackListAction#getBlockedUsers() method.
     * </p>
     * <p>
     * blockedUsers should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetBlockedUsers() {
        List < User > blockedUsers = new ArrayList < User >();
        action.setBlockedUsers(blockedUsers);
        assertSame("getBlockedUsers() doesn't work properly.", blockedUsers, action.getBlockedUsers());
    }

    /**
     * <p>
     * Tests BlackListAction#setBlockedUsers(List) method.
     * </p>
     * <p>
     * blockedUsers should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetBlockedUsers() {
        List < User > blockedUsers = new ArrayList < User >();
        action.setBlockedUsers(blockedUsers);
        assertSame("setBlockedUsers() doesn't work properly.", blockedUsers, action.getBlockedUsers());
    }

    /**
     * <p>
     * Tests BlackListAction#getMemberContactBlackListDao() method.
     * </p>
     * <p>
     * memberContactBlackListDao should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetMemberContactBlackListDao() {
        MemberContactBlackListDAO memberContactBlackListDao = mock(MemberContactBlackListDAO.class);
        action.setMemberContactBlackListDao(memberContactBlackListDao);
        assertSame("getMemberContactBlackListDao() doesn't work properly.", memberContactBlackListDao,
                action.getMemberContactBlackListDao());
    }

    /**
     * <p>
     * Tests BlackListAction#setMemberContactBlackListDao(MemberContactBlackListDAO) method.
     * </p>
     * <p>
     * memberContactBlackListDao should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetMemberContactBlackListDao() {
        MemberContactBlackListDAO memberContactBlackListDao = mock(MemberContactBlackListDAO.class);
        action.setMemberContactBlackListDao(memberContactBlackListDao);
        assertSame("setMemberContactBlackListDao() doesn't work properly.", memberContactBlackListDao,
                action.getMemberContactBlackListDao());
    }

    /**
     * <p>
     * Tests BlackListAction#getPreviouslyBlockedUsers() method.
     * </p>
     * <p>
     * previouslyBlockedUsers should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetPreviouslyBlockedUsers() {
        List < User > previouslyBlockedUsers = new ArrayList < User >();
        action.setPreviouslyBlockedUsers(previouslyBlockedUsers);
        assertSame("getPreviouslyBlockedUsers() doesn't work properly.", previouslyBlockedUsers,
                action.getPreviouslyBlockedUsers());
    }

    /**
     * <p>
     * Tests BlackListAction#setPreviouslyBlockedUsers(List) method.
     * </p>
     * <p>
     * previouslyBlockedUsers should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPreviouslyBlockedUsers() {
        List < User > previouslyBlockedUsers = new ArrayList < User >();
        action.setPreviouslyBlockedUsers(previouslyBlockedUsers);
        assertSame("setPreviouslyBlockedUsers() doesn't work properly.", previouslyBlockedUsers,
                action.getPreviouslyBlockedUsers());
    }

    /**
     * <p>
     * Tests BlackListAction#getBlockedUsersParametersKey() method.
     * </p>
     * <p>
     * blockedUsersParametersKey should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetBlockedUsersParametersKey() {
        String blockedUsersParametersKey = "test";
        action.setBlockedUsersParametersKey(blockedUsersParametersKey);
        assertSame("getBlockedUsersParametersKey() doesn't work properly.", blockedUsersParametersKey,
                action.getBlockedUsersParametersKey());
    }

    /**
     * <p>
     * Tests BlackListAction#setBlockedUsersParametersKey(String) method.
     * </p>
     * <p>
     * blockedUsersParametersKey should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetBlockedUsersParametersKey() {
        String blockedUsersParametersKey = "test";
        action.setBlockedUsersParametersKey(blockedUsersParametersKey);
        assertSame("setBlockedUsersParametersKey() doesn't work properly.", blockedUsersParametersKey,
                action.getBlockedUsersParametersKey());
    }

    /**
     * <p>
     * Tests BlackListAction#execute() with no user in session.
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
     * Tests BlackListAction#execute() with email send fail.
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
     * Tests BlackListAction#execute() with no backed up user in session.
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
     * Tests BlackListAction#prepare() method with null memberContactBlackListDao attribute.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_MemberContactBlackListDao() throws Exception {
        action.setMemberContactBlackListDao(null);
        try {
            action.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BlackListAction#prepare() method with null blockedUsersParametersKey attribute.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_BlockedUsersParametersKey() throws Exception {
        action.setBlockedUsersParametersKey(null);
        try {
            action.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BlackListAction#prepare() method with empty blockedUsersParametersKey attribute.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Empty_BlockedUsersParametersKey() throws Exception {
        action.setBlockedUsersParametersKey("");
        try {
            action.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BlackListAction#validate() method with null user handle.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_UserHandle() throws Exception {
        request.addParameter(action.getBlockedUsersParametersKey(), new String[] {null, null});
        ServletActionContext.setRequest(request);
        action.prepare();
        List < User > blockedUsers = action.getBlockedUsers();
        blockedUsers.get(0).setHandle(null);
        blockedUsers.get(1).setHandle(null);
        action.validate();
        assertEquals("Fields error should be added.", 2, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests BlackListAction#validate() method with null user handle.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_UserHandle() throws Exception {
        request.addParameter(action.getBlockedUsersParametersKey(), new String[] {"", "handle1"});
        ServletActionContext.setRequest(request);
        action.prepare();
        List < User > blockedUsers = action.getBlockedUsers();
        blockedUsers.get(0).setHandle("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }
}
