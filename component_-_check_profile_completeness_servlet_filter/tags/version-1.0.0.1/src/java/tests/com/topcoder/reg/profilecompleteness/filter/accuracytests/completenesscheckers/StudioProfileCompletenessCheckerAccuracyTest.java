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
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.StudioProfileCompletenessChecker;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.User;


/**
 * <p>
 * Accuracy test for {@link StudioProfileCompletenessChecker}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class StudioProfileCompletenessCheckerAccuracyTest {
    /**
     * The StudioProfileCompletenessChecker instance to test against.
     */
    private StudioProfileCompletenessChecker checker;

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
        return new JUnit4TestAdapter(StudioProfileCompletenessCheckerAccuracyTest.class);
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
        checker = new StudioProfileCompletenessChecker();
        checker.setLog(new BasicLogFactory().createLog("test"));
        user = AccuracyTestHelper.createUser();
        Coder coder = new Coder();
        coder.setCoderType(new CoderType());
        coder.setCompCountry(new Country());
        user.setCoder(coder);
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
     * Tests StudioProfileCompletenessChecker constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("StudioProfileCompletenessChecker is not instantiated", checker);
    }

    /**
     * <p>
     * Tests {@link StudioProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
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
     * Tests {@link StudioProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
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

    /**
     * <p>
     * Tests {@link StudioProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when coder is null.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete3() throws Exception {
        Assert.assertFalse("User should not be complete",
            checker.isProfileComplete(AccuracyTestHelper.createUser()));
    }

    /**
     * <p>
     * Tests {@link StudioProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when coder type is null.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete4() throws Exception {
        user.getCoder().setCoderType(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link StudioProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when country is null.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete5() throws Exception {
        user.getCoder().setCompCountry(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }
}
