/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.profile.AccountInfoTaskChecker;
import com.topcoder.web.reg.actions.profile.ContactInfoTaskChecker;
import com.topcoder.web.reg.actions.profile.DefaultProfileCompletenessRetriever;
import com.topcoder.web.reg.actions.profile.ProfileCompletenessReport;
import com.topcoder.web.reg.actions.profile.ProfileTaskChecker;

/**
 * <p>
 * Accuracy tests for the {@link DefaultProfileCompletenessRetriever}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class DefaultProfileCompletenessRetrieverTest {
    /** Represents the profileTaskCheckers used to test again. */
    private List profileTaskCheckersValue;

    /** Represents the instance used to test again. */
    private DefaultProfileCompletenessRetriever testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new DefaultProfileCompletenessRetriever();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link DefaultProfileCompletenessRetriever#DefaultProfileCompletenessRetriever()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testDefaultProfileCompletenessRetriever() throws Exception {
        new DefaultProfileCompletenessRetriever();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link DefaultProfileCompletenessRetriever#getProfileTaskCheckers()}
     * </p>
     * <p>
     * The value of <code>profileTaskCheckers</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getProfileTaskCheckers() throws Exception {
        assertNull("Old value", testInstance.getProfileTaskCheckers());
    }

    /**
     * <p>
     * Accuracy test {@link DefaultProfileCompletenessRetriever#setProfileTaskCheckers(List)}.
     * </p>
     * <p>
     * The value of <code>profileTaskCheckers</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setProfileTaskCheckers() throws Exception {
        testInstance.setProfileTaskCheckers(profileTaskCheckersValue);
        assertEquals("New value", profileTaskCheckersValue, testInstance.getProfileTaskCheckers());
    }

    /**
     * <p>
     * Accuracy test for {@link DefaultProfileCompletenessRetriever#getProfileCompletenessReport()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getProfileCompletenessReport() throws Exception {
        List<ProfileTaskChecker> profileTaskCheckers = new ArrayList<ProfileTaskChecker>();
        profileTaskCheckers.add(new AccountInfoTaskChecker());
        profileTaskCheckers.add(new ContactInfoTaskChecker());

        testInstance.setProfileTaskCheckers(profileTaskCheckers);
        User user = new User();
        user.setHandle("handle");
        ProfileCompletenessReport report = testInstance.getProfileCompletenessReport(user);
        Assert.assertEquals("2", 2, report.getTaskCompletionStatuses().size());
    }
}