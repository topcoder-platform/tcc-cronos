/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import junit.framework.TestSuite;
import junit.framework.Test;

import com.jivesoftware.base.User;
import com.jivesoftware.forum.ForumFactory;
import com.jivesoftware.forum.ResultFilter;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.reg.actions.miscellaneous.MyForumPostHistoryAction;

/**
 * <p>
 * Accuracy Unit test cases for MyForumPostHistoryAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class MyForumPostHistoryActionAccuracyTests extends BaseAccuracyTest {
    /**
     * <p>MyForumPostHistoryAction instance for testing.</p>
     */
    private MyForumPostHistoryAction instance;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/forumhistory");
        instance = (MyForumPostHistoryAction) proxy.getAction();
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
        return new TestSuite(MyForumPostHistoryActionAccuracyTests.class);
    }

    /**
     * <p>Tests ctor MyForumPostHistoryAction#MyForumPostHistoryAction() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create MyForumPostHistoryAction instance.", instance);
    }

    /**
     * <p>Tests MyForumPostHistoryAction#setSortOrder(String) for accuracy.</p>
     */
    public void testSetSortOrder() {
        instance.setSortOrder("sortOrder");
        assertEquals("Failed to setSortOrder correctly.", "sortOrder", instance.getSortOrder());
    }

    /**
     * <p>Tests MyForumPostHistoryAction#setStartIndex(int) for accuracy.</p>
     */
    public void testSetStartIndex() {
        instance.setStartIndex(1);
        assertEquals("Failed to setStartIndex correctly.", 1, instance.getStartIndex());
    }

    /**
     * <p>Tests MyForumPostHistoryAction#setRange(int) for accuracy.</p>
     */
    public void testSetRange() {
        instance.setRange(1);
        assertEquals("Failed to setRange correctly.", 1, instance.getRange());

    }

    /**
     * <p>Tests MyForumPostHistoryAction#execute() for accuracy.</p>
     * @throws Exception to JUnit
     */
    public void testExecute() throws Exception {
        setBasePreferencesActionDAOs(instance);
        putKeyValueToSession(instance.getBasicAuthenticationSessionKey(), createAuthentication());
        com.topcoder.web.common.model.User loggedInUser = createUser(1L, "First", "Second", "handle");
        when(instance.getUserDao().find(1L)).thenReturn(loggedInUser);
        ForumFactory forumFactory = getForumFactory();
        prepareForumFactory(forumFactory);
        instance.setForumFactory(forumFactory);
        instance.setAction(SUBMIT_ACTION);
        instance.setSortOrder("1");
        instance.setSortField("2");

        assertEquals("Failed to execute correctly.", ActionSupport.SUCCESS, instance.execute());
        verify(instance.getForumFactory(), times(1)).getUserMessages(any(User.class), any(ResultFilter.class));
        verify(instance.getForumFactory(), times(1)).getUserMessageCount(any(User.class), any(ResultFilter.class));
    }

}