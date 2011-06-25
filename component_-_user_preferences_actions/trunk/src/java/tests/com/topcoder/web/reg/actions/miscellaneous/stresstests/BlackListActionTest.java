/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.stresstests;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.dao.MemberContactBlackListDAO;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.miscellaneous.BlackListAction;

/**
 * <p>
 * Stress test cases for BlackListAction.
 * </p>
 *
 * @author moon.river
 * @version 1.0
 */
public class BlackListActionTest extends BaseTest {
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
        return new TestSuite(BlackListActionTest.class);
    }

    /**
     * <p>Tests BlackListAction#execute().</p>
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

        // create a long list of blocked users
        List<User> blockedUsers = new ArrayList<User>();
        for (int i = 0; i < 1000; i++) {
            User useri = createUser(5L, "First" + i, "Last" + i, "handle" + i);
            blockedUsers.add(useri);
        }
        instance.setBlockedUsers(blockedUsers);

        long start = System.currentTimeMillis();
        assertEquals("Wrong.", ActionSupport.SUCCESS, instance.execute());
        long end = System.currentTimeMillis();
        System.err.println("Run execute took " + (end - start) + "ms");
    }

}