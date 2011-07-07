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
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.DirectProfileCompletenessChecker;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.web.common.model.Company;
import com.topcoder.web.common.model.Contact;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;


/**
 * <p>
 * Accuracy test for {@link DirectProfileCompletenessChecker}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class DirectProfileCompletenessCheckerAccuracyTest {
    /**
     * The DirectProfileCompletenessChecker instance to test against.
     */
    private DirectProfileCompletenessChecker checker;

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
        return new JUnit4TestAdapter(DirectProfileCompletenessCheckerAccuracyTest.class);
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
        checker = new DirectProfileCompletenessChecker();
        checker.setLog(new BasicLogFactory().createLog("test"));
        user = AccuracyTestHelper.createUser();

        // set contact
        Company company = new Company();
        company.setName("company");

        Contact contact = new Contact();
        contact.setCompany(company);
        user.setContact(contact);

        // set demographic responses
        DemographicQuestion question = new DemographicQuestion();
        question.setId(1L);

        DemographicResponse response = new DemographicResponse();
        response.setQuestion(question);

        user.setDemographicResponses(new HashSet<DemographicResponse>(Arrays.asList(response)));
        user.setTimeZone(new TimeZone());

        // set as customer
        SecurityGroup securityGroup = Mockito.mock(SecurityGroup.class);
        Mockito.when(securityGroup.getRegistrationTypes()).thenReturn(
            new HashSet<RegistrationType>(Arrays.asList(new RegistrationType(RegistrationType.CORPORATE_ID))));

        UserGroup group = new UserGroup();
        group.setSecurityGroup(securityGroup);

        user.getSecurityGroups().add(group);
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
     * Tests DirectProfileCompletenessChecker constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("DirectProfileCompletenessChecker is not instantiated", checker);
    }

    /**
     * <p>
     * Tests {@link DirectProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
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
     * Tests {@link DirectProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
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
     * Tests {@link DirectProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when user is not a customer.
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
        user.getSecurityGroups().clear();
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link DirectProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when contact is null.
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
        Assert.assertFalse("User should not be complete",
            checker.isProfileComplete(AccuracyTestHelper.createUser()));
    }

    /**
     * <p>
     * Tests {@link DirectProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when company is null.
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
        user.getContact().setCompany(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link DirectProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when company name is null.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete6() throws Exception {
        user.getContact().getCompany().setName(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link DirectProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when demographic responses is null.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete7() throws Exception {
        user.setDemographicResponses(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link DirectProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when demographic responses is empty.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete8() throws Exception {
        user.clearDemographicResponses();
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link DirectProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when time zone is null.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete9() throws Exception {
        user.setTimeZone(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }
}
