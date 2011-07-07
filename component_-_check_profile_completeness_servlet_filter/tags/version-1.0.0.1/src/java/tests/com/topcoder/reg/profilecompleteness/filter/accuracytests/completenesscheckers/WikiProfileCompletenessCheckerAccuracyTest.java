/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.reg.profilecompleteness.filter.accuracytests.AccuracyTestHelper;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.WikiProfileCompletenessChecker;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.web.common.model.User;


/**
 * <p>
 * Accuracy test for {@link WikiProfileCompletenessChecker}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class WikiProfileCompletenessCheckerAccuracyTest {
    /**
     * The WikiProfileCompletenessChecker instance to test against.
     */
    private WikiProfileCompletenessChecker checker;

    /**
     * The User instance used for testing.
     */
    private User user;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(WikiProfileCompletenessCheckerAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Before
    public void setUp() throws Exception {
        checker = new WikiProfileCompletenessChecker();
        checker.setLog(new BasicLogFactory().createLog("test"));
        user = AccuracyTestHelper.createUser();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @After
    public void tearDown() throws Exception {
        checker = null;
        user = null;
    }

    /**
     * <p>
     * Tests WikiProfileCompletenessChecker constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("WikiProfileCompletenessChecker is not instantiated", checker);
    }

    /**
     * <p>
     * Tests {@link WikiProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when user is complete.
     * </p>
     * <p>
     * Expects true is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete1() throws Exception {
        Assert.assertTrue("User should be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link WikiProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when user is not complete in the superclass.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete2() throws Exception {
        user.setFirstName(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }
}
