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
import com.topcoder.web.reg.actions.miscellaneous.ForumRatingPreferencesAction;

/**
 * <p>
 * Accuracy Unit test cases for ForumRatingPreferencesAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ForumRatingPreferencesActionAccuracyTests extends BaseAccuracyTest {
    /**
     * <p>ForumRatingPreferencesAction instance for testing.</p>
     */
    private ForumRatingPreferencesAction instance;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/forumrating");
        instance = (ForumRatingPreferencesAction) proxy.getAction();
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
        return new TestSuite(ForumRatingPreferencesActionAccuracyTests.class);
    }

    /**
     * <p>Tests ctor ForumRatingPreferencesAction#ForumRatingPreferencesAction() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create ForumRatingPreferencesAction instance.", instance);
    }

    /**
     * <p>Tests ForumRatingPreferencesAction#setShowRatings(String) for accuracy.</p>
     */
    public void testSetShowRatings() {
        instance.setShowRatings("showRatings");
        assertEquals("Failed to setShowRatings correctly.", "showRatings", instance.getShowRatings());
    }

    /**
     * <p>Tests ForumRatingPreferencesAction#setRatingHighlightThreshold(String) for accuracy.</p>
     */
    public void testSetRatingHighlightThreshold() {
        instance.setRatingHighlightThreshold("ratingHighlightThreshold");
        assertEquals("Failed to setRatingHighlightThreshold correctly.", "ratingHighlightThreshold",
            instance.getRatingHighlightThreshold());
    }

    /**
     * <p>Tests ForumRatingPreferencesAction#setRatingHighlightMinCount(String) for accuracy.</p>
     */
    public void testSetRatingHighlightMinCount() {
        instance.setRatingHighlightMinCount("ratingHighlightMinCount");
        assertEquals("Failed to setRatingHighlightMinCount correctly.", "ratingHighlightMinCount",
            instance.getRatingHighlightMinCount());
    }

    /**
     * <p>Tests ForumRatingPreferencesAction#setRatingCollapseThreshold(String) for accuracy.</p>
     */
    public void testSetRatingCollapseThreshold() {
        instance.setRatingCollapseThreshold("ratingCollapseThreshold");
        assertEquals("Failed to setRatingCollapseThreshold correctly.", "ratingCollapseThreshold",
            instance.getRatingCollapseThreshold());
    }

    /**
     * <p>Tests ForumRatingPreferencesAction#setRatingCollapseMinMessages(String) for accuracy.</p>
     */
    public void testSetRatingCollapseMinMessages() {
        instance.setRatingCollapseMinMessages("ratingCollapseMinMessages");
        assertEquals("Failed to setRatingCollapseMinMessages correctly.", "ratingCollapseMinMessages",
            instance.getRatingCollapseMinMessages());
    }

    /**
     * <p>Tests ForumRatingPreferencesAction#setRatingCollapseMinCount(String) for accuracy.</p>
     */
    public void testSetRatingCollapseMinCount() {
        instance.setRatingCollapseMinCount("ratingCollapseMinCount");
        assertEquals("Failed to setRatingCollapseMinCount correctly.", "ratingCollapseMinCount",
            instance.getRatingCollapseMinCount());
    }

    /**
     * <p>Tests ForumRatingPreferencesAction#execute() for accuracy.</p>
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