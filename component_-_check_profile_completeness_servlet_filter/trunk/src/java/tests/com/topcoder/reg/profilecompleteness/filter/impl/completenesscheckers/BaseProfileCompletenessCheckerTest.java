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
import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;

/**
 * Unit tests for <code>BaseProfileCompletenessChecker</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class BaseProfileCompletenessCheckerTest extends TestCase {

    /**
     * Represents the <code>BaseProfileCompletenessChecker</code> instance used to test against.
     */
    private BaseProfileCompletenessChecker t;

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
        t = new BaseProfileCompletenessChecker() {
        };
        log = new MockLog();
        user = createBasicCompleteUser();
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
        log = null;
        user = null;
    }

    /**
     * Accuracy test for constructor <code>BaseProfileCompletenessChecker()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
        assertNull(t.getLog());
    }

    /**
     * Accuracy test for <code>checkInitialization()</code>.
     *
     * No exception thrown.
     */
    public void testCheckInitialization_Accuracy() {
        t.checkInitialization();
        // no exception thrown
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return true if the user profile is complete.
     *
     * @throws Exception
     *             to jUnit, to indicate an error
     */
    public void testIsProfileComplete_Accuracy1() throws Exception {
        t.setLog(log);
        assertEquals(true, t.isProfileComplete(user));

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {BaseProfileCompletenessChecker.isProfileComplete}]"));
        assertTrue(logMessage
            .contains("[Exiting method {BaseProfileCompletenessChecker.isProfileComplete}]"));
        assertTrue(logMessage.contains("[Output parameter {true}]"));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit, to indicate an error
     */
    public void testIsProfileComplete_Accuracy2() throws Exception {
        t.setLog(log);
        user.setFirstName(null);
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit, to indicate an error
     */
    public void testIsProfileComplete_Accuracy3() throws Exception {
        t.setLog(log);
        user.setLastName(null);
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit, to indicate an error
     */
    public void testIsProfileComplete_Accuracy4() throws Exception {
        t.setLog(log);
        user.setAddresses(new HashSet<Address>()); // empty
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit, to indicate an error
     */
    public void testIsProfileComplete_Accuracy5() throws Exception {
        t.setLog(log);
        user.setPhoneNumbers(new HashSet<Phone>()); // empty
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit, to indicate an error
     */
    public void testIsProfileComplete_Accuracy6() throws Exception {
        t.setLog(log);
        Address address = new Address();
        address.setAddress1(null);
        user.setAddresses(toSet(address));
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit, to indicate an error
     */
    public void testIsProfileComplete_Accuracy7() throws Exception {
        t.setLog(log);
        Address address = new Address();
        address.setAddress1("add1");
        address.setCity(null);
        user.setAddresses(toSet(address));
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit, to indicate an error
     */
    public void testIsProfileComplete_Accuracy8() throws Exception {
        t.setLog(log);
        Address address = new Address();
        address.setAddress1("add1");
        address.setCity("city");
        address.setCountry(null);
        user.setAddresses(toSet(address));
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Failure test for method <code>isProfileComplete(User user)</code>.
     *
     * If user is null, <code>IllegalArgumentException</code> will be thrown.
     *
     * @throws Exception
     *             to jUnit, to indicate an error
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
     * Accuracy test for <code>isCompetitor(User user)</code>.
     *
     * It should return true, if the user is a competitor.
     */
    public void testIsCompetitor_Accuracy1() {
        t.setLog(log);

        // mock a securityGroup
        SecurityGroup securityGroup = mock(SecurityGroup.class);
        // set the type to "competition"
        RegistrationType type = new RegistrationType(RegistrationType.COMPETITION_ID);
        when(securityGroup.getRegistrationTypes()).thenReturn(toSet(type));

        UserGroup group = new UserGroup();
        group.setSecurityGroup(securityGroup);
        user.getSecurityGroups().add(group);

        boolean result = t.isCompetitor(user);
        assertEquals(true, result);

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {BaseProfileCompletenessChecker.isCompetitor}]"));
        assertTrue(logMessage
            .contains("[Exiting method {BaseProfileCompletenessChecker.isCompetitor}]"));
    }

    /**
     * Accuracy test for <code>isCompetitor(User user)</code>.
     *
     * It should return false, if the user is not a competitor.
     */
    public void testIsCompetitor_Accuracy2() {
        t.setLog(log);

        // mock a securityGroup
        SecurityGroup securityGroup = mock(SecurityGroup.class);
        RegistrationType type = new RegistrationType(RegistrationType.CORPORATE_ID);
        when(securityGroup.getRegistrationTypes()).thenReturn(toSet(type));

        UserGroup group = new UserGroup();
        group.setSecurityGroup(securityGroup);
        user.getSecurityGroups().add(group);

        boolean result = t.isCompetitor(user);
        assertEquals(false, result);
    }

    /**
     * Accuracy test for <code>isCustomer(User user)</code>.
     */
    public void testIsCustomer_Accuracy1() {
        t.setLog(log);

        // mock a securityGroup
        SecurityGroup securityGroup = mock(SecurityGroup.class);
        RegistrationType type = new RegistrationType(RegistrationType.CORPORATE_ID);
        when(securityGroup.getRegistrationTypes()).thenReturn(toSet(type));

        UserGroup group = new UserGroup();
        group.setSecurityGroup(securityGroup);
        user.getSecurityGroups().add(group);

        boolean result = t.isCustomer(user);
        assertEquals(true, result);

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {BaseProfileCompletenessChecker.isCustomer}]"));
        assertTrue(logMessage
            .contains("[Exiting method {BaseProfileCompletenessChecker.isCustomer}]"));
    }

    /**
     * Accuracy test for <code>isCustomer(User user)</code>.
     */
    public void testIsCustomer_Accuracy2() {
        t.setLog(log);

        // mock a securityGroup
        SecurityGroup securityGroup = mock(SecurityGroup.class);
        RegistrationType type = new RegistrationType(RegistrationType.TEACHER_ID);
        when(securityGroup.getRegistrationTypes()).thenReturn(toSet(type));

        UserGroup group = new UserGroup();
        group.setSecurityGroup(securityGroup);
        user.getSecurityGroups().add(group);

        boolean result = t.isCustomer(user);
        assertEquals(false, result);
    }

    /**
     * Accuracy test for <code>getLog()</code>.
     *
     * Field log should be set and got correctly.
     */
    public void testGetLog_Accuracy() {
        t.setLog(log);
        assertEquals(this.log, t.getLog());
    }

    /**
     * Accuracy test for <code>setLog(Log log)</code>.
     *
     * Field log should be set and got correctly.
     */
    public void testSetLog_Accuracy() {
        t.setLog(log);
        assertEquals(this.log, t.getLog());
    }
}
