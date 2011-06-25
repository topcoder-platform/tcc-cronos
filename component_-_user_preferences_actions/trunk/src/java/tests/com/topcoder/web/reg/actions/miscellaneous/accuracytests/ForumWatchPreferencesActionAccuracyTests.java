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
import com.jivesoftware.forum.action.UserSettingsAction;
import com.jivesoftware.util.CronTimer;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.reg.actions.miscellaneous.ForumWatchPreferencesAction;

/**
 * <p>
 * Accuracy Unit test cases for ForumWatchPreferencesAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ForumWatchPreferencesActionAccuracyTests extends BaseAccuracyTest {
    /**
     * <p>ForumWatchPreferencesAction instance for testing.</p>
     */
    private ForumWatchPreferencesAction instance;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/forumwatch");
        instance = (ForumWatchPreferencesAction) proxy.getAction();
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
        return new TestSuite(ForumWatchPreferencesActionAccuracyTests.class);
    }

    /**
     * <p>Tests ctor ForumWatchPreferencesAction#ForumWatchPreferencesAction() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create ForumWatchPreferencesAction instance.", instance);
    }

    /**
     * <p>Tests ForumWatchPreferencesAction#setWatchFrequency(int) for accuracy.</p>
     */
    public void testSetWatchFrequency() {
        instance.setWatchFrequency(1);
        assertEquals("Failed to setWatchFrequency correctly.", 1, instance.getWatchFrequency());
    }

    /**
     * <p>Tests ForumWatchPreferencesAction#setMarkWatchesRead(String) for accuracy.</p>
     */
    public void testSetMarkWatchesRead() {
        instance.setMarkWatchesRead("markWatchesRead");
        assertEquals("Failed to setMarkWatchesRead correctly.", "markWatchesRead", instance.getMarkWatchesRead());
    }

    /**
     * <p>Tests ForumWatchPreferencesAction#setAutoWatchNewTopics(String) for accuracy.</p>
     */
    public void testSetAutoWatchNewTopics() {
        instance.setAutoWatchNewTopics("autoWatchNewTopics");
        assertEquals("Failed to setAutoWatchNewTopics correctly.", "autoWatchNewTopics",
            instance.getAutoWatchNewTopics());
    }

    /**
     * <p>Tests ForumWatchPreferencesAction#setAutoWatchReplies(String) for accuracy.</p>
     */
    public void testSetAutoWatchReplies() {
        instance.setAutoWatchReplies("autoWatchReplies");
        assertEquals("Failed to setAutoWatchReplies correctly.", "autoWatchReplies", instance.getAutoWatchReplies());
    }

    /**
     * <p>Tests ForumWatchPreferencesAction#execute() for accuracy.</p>
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
        instance.setWatchFrequency(UserSettingsAction.FREQUENCY_ONCE_A_DAY);

        assertEquals("Failed to execute correctly.", ActionSupport.SUCCESS, instance.execute());
        verify(instance.getAuditDao(), times(1)).audit(any(AuditRecord.class));
        verify(instance.getForumFactory().getWatchManager(), times(1)).setBatchTimer(any(User.class),
            any(CronTimer.class));
    }

}