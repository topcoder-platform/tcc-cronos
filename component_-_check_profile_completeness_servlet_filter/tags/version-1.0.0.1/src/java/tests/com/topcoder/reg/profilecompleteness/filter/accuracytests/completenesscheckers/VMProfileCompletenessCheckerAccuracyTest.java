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
import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.VMProfileCompletenessChecker;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.Company;
import com.topcoder.web.common.model.Contact;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;
import com.topcoder.web.common.model.UserSecurityKey;


/**
 * <p>
 * Accuracy test for {@link VMProfileCompletenessChecker}.
 * </p>
 *
 * @author gets0ul
 * @version 1.0
 */
public class VMProfileCompletenessCheckerAccuracyTest {
    /**
     * The VMProfileCompletenessChecker instance to test against.
     */
    private VMProfileCompletenessChecker checker;

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
        return new JUnit4TestAdapter(VMProfileCompletenessCheckerAccuracyTest.class);
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
        checker = new VMProfileCompletenessChecker();
        checker.setLog(new BasicLogFactory().createLog("test"));
        user = AccuracyTestHelper.createUser();

        Coder coder = new Coder();
        coder.setCoderType(new CoderType());
        coder.setCompCountry(new Country());
        user.setCoder(coder);

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

        user.setUserSecurityKey(new UserSecurityKey());
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
     * Tests VMProfileCompletenessChecker constructor.
     * </p>
     * <p>
     * Instance should be created. No exception is expected.
     * </p>
     */
    @Test
    public void testCtor() {
        Assert.assertNotNull("VMProfileCompletenessChecker is not instantiated", checker);
    }

    /**
     * <p>
     * Tests {@link VMProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method when
     * user as customer is complete.
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
        // set as customer
        setRegistrationType(user, RegistrationType.CORPORATE_ID);
        Assert.assertTrue("User should be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link VMProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method when
     * user as competitor is complete.
     * </p>
     * <p>
     * Expects true is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete2() throws Exception {
        // set as competitor
        setRegistrationType(user, RegistrationType.COMPETITION_ID);
        Assert.assertTrue("User should be complete", checker.isProfileComplete(user));
    }

    /**
     * Sets the registration type of user whether as customer or competitor.
     *
     * @param user
     *            the user to set
     * @param typeId
     *            whether RegistrationType.CORPORATE_ID or RegistrationType.COMPETITION_ID
     */
    private void setRegistrationType(User user, Integer typeId) {
        SecurityGroup securityGroup = Mockito.mock(SecurityGroup.class);
        Mockito.when(securityGroup.getRegistrationTypes()).thenReturn(
            new HashSet<RegistrationType>(Arrays.asList(new RegistrationType(typeId))));

        UserGroup group = new UserGroup();
        group.setSecurityGroup(securityGroup);

        user.getSecurityGroups().add(group);
    }

    /**
     * <p>
     * Tests {@link VMProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method when
     * user is not complete in the superclass.
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
        user.setFirstName(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link VMProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method when
     * user is customer and contact is null.
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
        user.setContact(null);
        setRegistrationType(user, RegistrationType.CORPORATE_ID);
        Assert.assertFalse("User should not be complete",
            checker.isProfileComplete(AccuracyTestHelper.createUser()));
    }

    /**
     * <p>
     * Tests {@link VMProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method when
     * user is customer and company is null.
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
        setRegistrationType(user, RegistrationType.CORPORATE_ID);
        user.getContact().setCompany(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link VMProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method when
     * user is customer and company name is null.
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
        setRegistrationType(user, RegistrationType.CORPORATE_ID);
        user.getContact().getCompany().setName(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link VMProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method when
     * user is customer and demographic responses is null.
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
        setRegistrationType(user, RegistrationType.CORPORATE_ID);
        user.setDemographicResponses(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link VMProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method when
     * user is customer and demographic responses is empty.
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
        setRegistrationType(user, RegistrationType.CORPORATE_ID);
        user.clearDemographicResponses();
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link VMProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method when
     * user is customer and time zone is null.
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
        setRegistrationType(user, RegistrationType.CORPORATE_ID);
        user.setTimeZone(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link VMProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method when
     * user is competitor and coder is null.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete10() throws Exception {
        setRegistrationType(user, RegistrationType.COMPETITION_ID);
        Assert.assertFalse("User should not be complete",
            checker.isProfileComplete(AccuracyTestHelper.createUser()));
    }

    /**
     * <p>
     * Tests {@link CopilotProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when user is competitor and coder type is null.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete11() throws Exception {
        setRegistrationType(user, RegistrationType.COMPETITION_ID);
        user.getCoder().setCoderType(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }

    /**
     * <p>
     * Tests {@link CopilotProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)} method
     * when user security key is null.
     * </p>
     * <p>
     * Expects false is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testIsProfileComplete12() throws Exception {
        user.setUserSecurityKey(null);
        Assert.assertFalse("User should not be complete", checker.isProfileComplete(user));
    }
}
