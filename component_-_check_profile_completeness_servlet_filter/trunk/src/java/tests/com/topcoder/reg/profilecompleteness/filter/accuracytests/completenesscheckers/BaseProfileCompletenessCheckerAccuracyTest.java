/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.accuracytests.completenesscheckers;

import java.util.Arrays;
import java.util.HashSet;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.topcoder.reg.profilecompleteness.filter.accuracytests.AccuracyTestHelper;
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.BaseProfileCompletenessChecker;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.basic.BasicLog;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;


/**
 * <p>
 * Accuracy test for {@link BaseProfileCompletenessChecker}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class BaseProfileCompletenessCheckerAccuracyTest {
    /**
     * The BaseProfileCompletenessChecker instance to test against.
     */
    private MockBaseChecker checker;

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
        return new JUnit4TestAdapter(BaseProfileCompletenessCheckerAccuracyTest.class);
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
        checker = new MockBaseChecker();
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
     * Tests BaseProfileCompletenessChecker constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("BaseProfileCompletenessChecker is not instantiated", checker);
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#checkInitialization()} when log is not null.
     * </p>
     * <p>
     * Expects no exception is thrown.
     * </p>
     */
    @Test
    public void testCheckInitialization() {
        checker.checkInitialization();
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when user profile is complete.
     * </p>
     * <p>
     * Expect true is returned.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    @Test
    public void testIsProfileComplete1() throws Exception {
        Assert.assertTrue("Profile should be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when first name is null.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    @Test
    public void testIsProfileComplete2() throws Exception {
        user.setFirstName(null);
        Assert.assertFalse("Profile should be not complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when last name is null.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    @Test
    public void testIsProfileComplete3() throws Exception {
        user.setLastName(null);
        Assert.assertFalse("Profile should be not complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when addresses is null.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    @Test
    public void testIsProfileComplete4() throws Exception {
        user.setAddresses(null);
        Assert.assertFalse("Profile should be not complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when addresses is empty.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    @Test
    public void testIsProfileComplete5() throws Exception {
        user.setAddresses(new HashSet<Address>());
        Assert.assertFalse("Profile should be not complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when address1 is null.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    @Test
    public void testIsProfileComplete6() throws Exception {
        user.getAddresses().iterator().next().setAddress1(null);
        Assert.assertFalse("Profile should be not complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when city is null.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    @Test
    public void testIsProfileComplete7() throws Exception {
        user.getAddresses().iterator().next().setCity(null);
        Assert.assertFalse("Profile should be not complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when country is null.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    @Test
    public void testIsProfileComplete8() throws Exception {
        user.getAddresses().iterator().next().setCountry(null);
        Assert.assertFalse("Profile should be not complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when phoneNumbers is null.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    @Test
    public void testIsProfileComplete9() throws Exception {
        user.setPhoneNumbers(null);
        Assert.assertFalse("Profile should be not complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when phoneNumbers is empty.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    @Test
    public void testIsProfileComplete10() throws Exception {
        user.setPhoneNumbers(new HashSet<Phone>());
        Assert.assertFalse("Profile should be not complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isCompetitor(com.topcoder.web.common.model.User)} when user is a
     * competitor.
     * </p>
     * <p>
     * Expect true is returned.
     * </p>
     */
    @Test
    public void testIsCompetitor1() {
        SecurityGroup securityGroup = Mockito.mock(SecurityGroup.class);
        Mockito.when(securityGroup.getRegistrationTypes()).thenReturn(
            new HashSet<RegistrationType>(Arrays.asList(new RegistrationType(RegistrationType.COMPETITION_ID))));

        UserGroup group = new UserGroup();
        group.setSecurityGroup(securityGroup);

        user.getSecurityGroups().add(group);

        Assert.assertTrue("User should be a competitor", checker.isCompetitor(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isCompetitor(com.topcoder.web.common.model.User)} when user is
     * not a competitor.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     */
    @Test
    public void testIsCompetitor2() {
        Assert.assertFalse("User should not be a competitor", checker.isCompetitor(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isCustomer(com.topcoder.web.common.model.User)} when user is a
     * customer.
     * </p>
     * <p>
     * Expect true is returned.
     * </p>
     */
    @Test
    public void testIsCustomer1() {
        SecurityGroup securityGroup = Mockito.mock(SecurityGroup.class);
        Mockito.when(securityGroup.getRegistrationTypes()).thenReturn(
            new HashSet<RegistrationType>(Arrays.asList(new RegistrationType(RegistrationType.CORPORATE_ID))));

        UserGroup group = new UserGroup();
        group.setSecurityGroup(securityGroup);

        user.getSecurityGroups().add(group);

        Assert.assertTrue("User should be a customer", checker.isCustomer(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#isCustomer(com.topcoder.web.common.model.User)} when user is not
     * a customer.
     * </p>
     * <p>
     * Expect false is returned.
     * </p>
     */
    @Test
    public void testIsCustomer2() {
        Assert.assertFalse("User should not be a customer", checker.isCustomer(user));
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#getLog()}.
     * </p>
     * <p>
     * Expects the correct log is returned.
     * </p>
     */
    @Test
    public void testGetLog() {
        Assert.assertTrue("Incorrect log", checker.getLog() instanceof BasicLog);
    }

    /**
     * <p>
     * Tests {@link BaseProfileCompletenessChecker#setLog(com.topcoder.util.log.Log)}.
     * </p>
     */
    @Test
    public void testSetLog() {
        Log log = new BasicLogFactory().createLog("myLog");
        checker.setLog(log);
        Assert.assertEquals("Incorrect log", log, checker.getLog());
    }

    /**
     * Mock implementation of BaseProfileCompletenessChecker for testing.
     *
     * @author gets0ul
     * @version 1.0
     */
    class MockBaseChecker extends BaseProfileCompletenessChecker {
        /**
         * Checks initialization.
         */
        public void checkInitialization() {
            super.checkInitialization();
        }

        /**
         * Checks whether user is a competitor.
         *
         * @return true if user is a competitor, false otherwise
         */
        protected boolean isCompetitor(User user) {
            return super.isCompetitor(user);
        }

        /**
         * Checks whether user is a customer.
         *
         * @return true if user is a customer, false otherwise
         */
        protected boolean isCustomer(User user) {
            return super.isCustomer(user);
        }

        /**
         * Gets the logger.
         *
         * @return the logger
         */
        protected Log getLog() {
            return super.getLog();
        }

    }
}
