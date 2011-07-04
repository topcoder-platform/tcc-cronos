/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers;

import static com.topcoder.reg.profilecompleteness.filter.TestHelper.createBasicCompleteUser;
import static com.topcoder.reg.profilecompleteness.filter.TestHelper.toSet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import junit.framework.TestCase;

import com.topcoder.reg.profilecompleteness.filter.MockLog;
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
 * Unit tests for <code>DirectProfileCompletenessChecker</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class DirectProfileCompletenessCheckerTest extends TestCase {

    /**
     * Represents the <code>DirectProfileCompletenessChecker</code> instance used to test against.
     */
    private DirectProfileCompletenessChecker t;

    /**
     * Represents the <code>User</code> instance used to test against.
     */
    private User user;

    /**
     * Represents the <code>Log</code> instance used to test against.
     */
    private MockLog log;

    /**
     * Sets up the fixture. This method is called before a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void setUp() throws Exception {
        t = new DirectProfileCompletenessChecker();
        user = createBasicCompleteUser();
        log = new MockLog();

        // add information
        user.setTimeZone(new TimeZone());
        Company company = new Company();
        company.setName("TC");
        Contact contact = new Contact();
        contact.setCompany(company);
        user.setContact(contact);

        DemographicResponse response = new DemographicResponse();
        response.setResponse("res");
        DemographicQuestion question = new DemographicQuestion();
        question.setId(1L);
        response.setQuestion(question);
        user.setDemographicResponses(toSet(response));

    }

    /**
     * Tears down the fixture. This method is called after a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void tearDown() throws Exception {
        t = null;
        user = null;
        log = null;
    }

    /**
     * Accuracy test for constructor <code>DirectProfileCompletenessChecker()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return true if user profile is complete and is a customer.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy1() throws Exception {
        t.setLog(log);

        // set user as a customer
        setUserAs(user, RegistrationType.CORPORATE_ID);
        assertEquals(true, t.isProfileComplete(user));

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {DirectProfileCompletenessChecker.isProfileComplete}]"));
        assertTrue(logMessage
            .contains("[Exiting method {DirectProfileCompletenessChecker.isProfileComplete}]"));
        assertTrue(logMessage.contains("[Output parameter {true}]"));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if user profile is not a customer.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy2() throws Exception {
        t.setLog(log);

        // not a customer
        setUserAs(user, RegistrationType.TEACHER_ID);
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if user profile is not complete
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy3() throws Exception {
        t.setLog(log);

        // as a customer
        setUserAs(user, RegistrationType.CORPORATE_ID);
        user.setFirstName(null);
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if user profile is not complete
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy4() throws Exception {
        t.setLog(log);

        // as a customer
        setUserAs(user, RegistrationType.CORPORATE_ID);
        user.setContact(null);
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if user profile is not complete
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy5() throws Exception {
        t.setLog(log);

        // as a customer
        setUserAs(user, RegistrationType.CORPORATE_ID);
        user.getContact().setCompany(null);
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if user profile is not complete
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy6() throws Exception {
        t.setLog(log);

        // as a customer
        setUserAs(user, RegistrationType.CORPORATE_ID);
        user.getContact().getCompany().setName(null);
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if user profile is not complete
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy7() throws Exception {
        t.setLog(log);

        // as a customer
        setUserAs(user, RegistrationType.CORPORATE_ID);
        user.setDemographicResponses(null);
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if user profile is not complete
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy8() throws Exception {
        t.setLog(log);

        // as a customer
        setUserAs(user, RegistrationType.CORPORATE_ID);
        user.setDemographicResponses(new HashSet<DemographicResponse>());
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if user profile is not complete
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy9() throws Exception {
        t.setLog(log);

        // as a customer
        setUserAs(user, RegistrationType.CORPORATE_ID);
        user.setTimeZone(null);
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Failure test for method <code>isProfileComplete(User user)</code>.
     *
     * If user is null, <code>IllegalArgumentException</code> will be thrown
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Failure_IllegalArgumentException() throws Exception {
        t.setLog(log);
        try {
            t.isProfileComplete(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Set the user as the specified type for testing.
     *
     * @param user
     *            the user to be set
     * @param regType
     *            the registration type
     */
    private static void setUserAs(User user, Integer regType) {
        // set user as a customer
        SecurityGroup securityGroup = mock(SecurityGroup.class);
        RegistrationType type = new RegistrationType(regType);
        when(securityGroup.getRegistrationTypes()).thenReturn(toSet(type));

        UserGroup group = new UserGroup();
        group.setSecurityGroup(securityGroup);
        user.getSecurityGroups().add(group);
    }

}
