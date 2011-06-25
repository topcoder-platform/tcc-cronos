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

import com.jivesoftware.forum.ForumFactory;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.miscellaneous.ForumGeneralPreferencesAction;

/**
 * <p>
 * Accuracy Unit test cases for ForumGeneralPreferencesAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ForumGeneralPreferencesActionAccuracyTests extends BaseAccuracyTest {
    /**
     * <p>ForumGeneralPreferencesAction instance for testing.</p>
     */
    private ForumGeneralPreferencesAction instance;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/forumgeneral");
        instance = (ForumGeneralPreferencesAction) proxy.getAction();
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
        return new TestSuite(ForumGeneralPreferencesActionAccuracyTests.class);
    }

    /**
     * <p>Tests ctor ForumGeneralPreferencesAction#ForumGeneralPreferencesAction() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create ForumGeneralPreferencesAction instance.", instance);
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setForumsPerPage(int) for accuracy.</p>
     */
    public void testSetForumsPerPage() {
        instance.setForumsPerPage(1);
        assertEquals("Failed to setForumsPerPage correctly.", 1, instance.getForumsPerPage());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setThreadsPerPage(int) for accuracy.</p>
     */
    public void testSetThreadsPerPage() {
        instance.setThreadsPerPage(1);
        assertEquals("Failed to setThreadsPerPage correctly.", 1, instance.getThreadsPerPage());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setMessagesPerPage(int) for accuracy.</p>
     */
    public void testSetMessagesPerPage() {
        instance.setMessagesPerPage(1);
        assertEquals("Failed to setMessagesPerPage correctly.", 1, instance.getMessagesPerPage());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setMessagesPerHistoryPage(int) for accuracy.</p>
     */
    public void testSetMessagesPerHistoryPage() {
        instance.setMessagesPerHistoryPage(1);
        assertEquals("Failed to setMessagesPerHistoryPage correctly.", 1, instance.getMessagesPerHistoryPage());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setResultsPerSearchPage(int) for accuracy.</p>
     */
    public void testSetResultsPerSearchPage() {
        instance.setResultsPerSearchPage(1);
        assertEquals("Failed to setResultsPerSearchPage correctly.", 1, instance.getResultsPerSearchPage());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setThreadMode(String) for accuracy.</p>
     */
    public void testSetThreadMode() {
        instance.setThreadMode("threadMode");
        assertEquals("Failed to setThreadMode correctly.", "threadMode", instance.getThreadMode());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setFlatMode(String) for accuracy.</p>
     */
    public void testSetFlatMode() {
        instance.setFlatMode("flatMode");
        assertEquals("Failed to setFlatMode correctly.", "flatMode", instance.getFlatMode());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setShowPrevNextThreads(String) for accuracy.</p>
     */
    public void testSetShowPrevNextThreads() {
        instance.setShowPrevNextThreads("showPrevNextThreads");
        assertEquals("Failed to setShowPrevNextThreads correctly.", "showPrevNextThreads",
            instance.getShowPrevNextThreads());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setDisplayMemberPhoto(String) for accuracy.</p>
     */
    public void testSetDisplayMemberPhoto() {
        instance.setDisplayMemberPhoto("displayMemberPhoto");
        assertEquals("Failed to setDisplayMemberPhoto correctly.", "displayMemberPhoto",
            instance.getDisplayMemberPhoto());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setDisplayAllMemberPhotos(String) for accuracy.</p>
     */
    public void testSetDisplayAllMemberPhotos() {
        instance.setDisplayAllMemberPhotos("displayAllMemberPhotos");
        assertEquals("Failed to setDisplayAllMemberPhotos correctly.", "displayAllMemberPhotos",
            instance.getDisplayAllMemberPhotos());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setCollapseRead(String) for accuracy.</p>
     */
    public void testSetCollapseRead() {
        instance.setCollapseRead("collapseRead");
        assertEquals("Failed to setCollapseRead correctly.", "collapseRead", instance.getCollapseRead());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setCollapseReadDays(String) for accuracy.</p>
     */
    public void testSetCollapseReadDays() {
        instance.setCollapseReadDays("collapseReadDays");
        assertEquals("Failed to setCollapseReadDays correctly.", "collapseReadDays", instance.getCollapseReadDays());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setCollapseReadPosts(String) for accuracy.</p>
     */
    public void testSetCollapseReadPosts() {
        instance.setCollapseReadPosts("collapseReadPosts");
        assertEquals("Failed to setCollapseReadPosts correctly.", "collapseReadPosts", instance.getCollapseReadPosts());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#setCollapseReadShowReplied(String) for accuracy.</p>
     */
    public void testSetCollapseReadShowReplied() {
        instance.setCollapseReadShowReplied("collapseReadShowReplied");
        assertEquals("Failed to setCollapseReadShowReplied correctly.", "collapseReadShowReplied",
            instance.getCollapseReadShowReplied());
    }

    /**
     * <p>Tests ForumGeneralPreferencesAction#execute() for accuracy.</p>
     * @throws Exception to JUnit
     */
    public void testExecute() throws Exception {
        setBasePreferencesActionDAOs(instance);
        putKeyValueToSession(instance.getBasicAuthenticationSessionKey(), createAuthentication());
        User loggedInUser = createUser(1L, "First", "Second", "handle");
        when(instance.getUserDao().find(1L)).thenReturn(loggedInUser);
        ForumFactory forumFactory = getForumFactory();
        prepareForumFactory(forumFactory);
        instance.setForumFactory(forumFactory);
        instance.setAction(SUBMIT_ACTION);

        assertEquals("Failed to execute correctly.", ActionSupport.SUCCESS, instance.execute());
        verify(instance.getAuditDao(), times(1)).audit(any(AuditRecord.class));
    }

}