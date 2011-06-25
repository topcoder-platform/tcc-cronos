/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestSuite;
import junit.framework.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.dao.MemberContactBlackListDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.MemberContactBlackList;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.miscellaneous.BlackListAction;

/**
 * <p>
 * Accuracy Unit test cases for BlackListAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class BlackListActionAccuracyTests extends BaseAccuracyTest {
    /**
     * <p>BlackListAction instance for testing.</p>
     */
    private BlackListAction instance;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/blacklist");
        instance = (BlackListAction) proxy.getAction();
    }

    /**
     * <p>Tears down test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BlackListActionAccuracyTests.class);
    }

    /**
     * <p>Tests ctor BlackListAction#BlackListAction() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create BlackListAction instance.", instance);
    }

    /**
     * <p>Tests BlackListAction#setBlockedUsers(List) for accuracy.</p>
     */
    public void testSetBlockedUsers() {
        List<User> blockedUsers = new ArrayList<User>();
        User user5 = createUser(5L, "First5", "Last5", "handle5");
        blockedUsers.add(user5);
        instance.setBlockedUsers(blockedUsers);
        assertEquals("Failed to set Blocked Users correctly.", 1, instance.getBlockedUsers().size());
    }

    /**
     * <p>Tests BlackListAction#setMemberContactBlackListDao(MemberContactBlackListDAO) for accuracy.</p>
     */
    public void testSetMemberContactBlackListDao() {
        MemberContactBlackListDAO memberContactBlackListDAO = getMemberContactBlackListDao();
        instance.setMemberContactBlackListDao(memberContactBlackListDAO);
        assertSame("Failed to setMemberContactBlackListDao correctly.", memberContactBlackListDAO,
            instance.getMemberContactBlackListDao());
    }

    /**
     * <p>Tests BlackListAction#setPreviouslyBlockedUsers(List) for accuracy.</p>
     */
    public void testSetPreviouslyBlockedUsers() {
        List<User> previouslyBlockedUsers = new ArrayList<User>();
        User user5 = createUser(5L, "First5", "Last5", "handle5");
        previouslyBlockedUsers.add(user5);
        instance.setPreviouslyBlockedUsers(previouslyBlockedUsers);
        assertEquals("Failed to set Previously Blocked Users correctly.", 1,
            instance.getPreviouslyBlockedUsers().size());

    }

    /**
     * <p>Tests BlackListAction#setBlockedUsersParametersKey(String) for accuracy.</p>
     */
    public void testSetBlockedUsersParametersKey() {
        instance.setBlockedUsersParametersKey("blockedUsersParametersKey");
        assertEquals("Failed to set blocked users parameters key correctly.", "blockedUsersParametersKey",
            instance.getBlockedUsersParametersKey());
    }

    /**
     * <p>Tests BlackListAction#execute() for accuracy.</p>
     * @throws Exception to JUnit
     */
    public void testExecute() throws Exception {
        setBasePreferencesActionDAOs(instance);
        MemberContactBlackListDAO memberContactBlackListDAO = getMemberContactBlackListDao();
        instance.setMemberContactBlackListDao(memberContactBlackListDAO);
        putKeyValueToSession(instance.getBasicAuthenticationSessionKey(), createAuthentication());
        User loggedInUser = createUser(1L, "First", "Second", "handle");
        when(instance.getUserDao().find(1L)).thenReturn(loggedInUser);

        prepareMemberContactBlackListDAO(memberContactBlackListDAO);
        instance.setAction(SUBMIT_ACTION);

        List<User> blockedUsers = new ArrayList<User>();
        User user5 = createUser(5L, "First5", "Last5", "handle5");
        blockedUsers.add(user5);
        instance.setBlockedUsers(blockedUsers);

        assertEquals("Failed to execute correctly.", ActionSupport.SUCCESS, instance.execute());
        verify(memberContactBlackListDAO, times(3)).saveOrUpdate(any(MemberContactBlackList.class));
        verify(instance.getAuditDao(), times(3)).audit(any(AuditRecord.class));
    }

}