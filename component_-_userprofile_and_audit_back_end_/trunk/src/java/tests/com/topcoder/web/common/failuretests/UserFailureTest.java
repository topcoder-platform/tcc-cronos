/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.failuretests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.Contact;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.Event;
import com.topcoder.web.common.model.EventRegistration;
import com.topcoder.web.common.model.Notification;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.Response;
import com.topcoder.web.common.model.School;
import com.topcoder.web.common.model.SecretQuestion;
import com.topcoder.web.common.model.TermsOfUse;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserPreference;
import com.topcoder.web.common.model.UserProfile;
import com.topcoder.web.common.model.UserSchool;
import com.topcoder.web.common.model.comp.UserContestPrize;
import com.topcoder.web.common.model.educ.Professor;
import com.topcoder.web.common.voting.RankBallot;

/**
 * <p>
 * Failure tests for class <code>User</code>.
 * </p>
 * @author stevenfrog
 * @version 1.0
 */
public class UserFailureTest {
    /**
     * <p>
     * Represents the <code>User</code> instance used to test against.
     * </p>
     */
    private User impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserFailureTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     * @throws Exception
     *             to jUnit.
     */
    @Before
    public void setUp() throws Exception {
        impl = new User();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        impl = null;
    }

    /**
     * <p>
     * Failure test for <code>getPrimaryEmailAddress()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetPrimaryEmailAddress_NeedFieldNull() throws Exception {
        impl.setEmailAddresses(null);
        impl.getPrimaryEmailAddress();
    }

    /**
     * <p>
     * Failure test for <code>addEmailAddress()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEmailAddress_NeedFieldNull() throws Exception {
        impl.setEmailAddresses(null);
        impl.addEmailAddress(new Email());
    }

    /**
     * <p>
     * Failure test for <code>getRegistrationTypes()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetRegistrationTypes_NeedFieldNull() throws Exception {
        impl.setSecurityGroups(null);
        impl.getRegistrationTypes();
    }

    /**
     * <p>
     * Failure test for <code>getFirstName()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetFirstName_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getFirstName();
    }

    /**
     * <p>
     * Failure test for <code>setFirstName(String)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetFirstName_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setFirstName("str");
    }

    /**
     * <p>
     * Failure test for <code>getMiddleName()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetMiddleName_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getMiddleName();
    }

    /**
     * <p>
     * Failure test for <code>setMiddleName(String)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetMiddleName_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setMiddleName("str");
    }

    /**
     * <p>
     * Failure test for <code>getLastName()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetLastName_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getLastName();
    }

    /**
     * <p>
     * Failure test for <code>setLastName(String)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetLastName_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setLastName("str");
    }

    /**
     * <p>
     * Failure test for <code>getAddresses()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetAddresses_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getAddresses();
    }

    /**
     * <p>
     * Failure test for <code>setAddresses(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetAddresses_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setAddresses(new HashSet < Address >());
    }

    /**
     * <p>
     * Failure test for <code>addAddress(Address)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddAddress_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < Address > addresses = new HashSet < Address >();
        impl.setAddresses(addresses);
        impl.addAddress(null);
    }

    /**
     * <p>
     * Failure test for <code>addAddress(Address)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddAddress_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setAddresses(null);
        impl.addAddress(new Address());
    }

    /**
     * <p>
     * Failure test for <code>getHomeAddress()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetHomeAddress_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getHomeAddress();
    }

    /**
     * <p>
     * Failure test for <code>getHomeAddress()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetHomeAddress_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setAddresses(null);
        impl.getHomeAddress();
    }

    /**
     * <p>
     * Failure test for <code>getPhoneNumbers()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetPhoneNumbers_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getPhoneNumbers();
    }

    /**
     * <p>
     * Failure test for <code>setPhoneNumbers(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetPhoneNumbers_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setPhoneNumbers(new HashSet < Phone >());
    }

    /**
     * <p>
     * Failure test for <code>getPrimaryPhoneNumber()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetPrimaryPhoneNumber_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setPhoneNumbers(null);
        impl.getPrimaryPhoneNumber();
    }

    /**
     * <p>
     * Failure test for <code>getPrimaryPhoneNumber()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetPrimaryPhoneNumber_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getPrimaryPhoneNumber();
    }

    /**
     * <p>
     * Failure test for <code>addPhoneNumber(Phone)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddPhoneNumber_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < Phone > phones = new HashSet < Phone >();
        impl.setPhoneNumbers(phones);
        impl.addPhoneNumber(null);
    }

    /**
     * <p>
     * Failure test for <code>addPhoneNumber(Phone)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddPhoneNumber_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setPhoneNumbers(null);
        impl.addPhoneNumber(new Phone());
    }

    /**
     * <p>
     * Failure test for <code>addPhoneNumber(Phone)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddPhoneNumber_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addPhoneNumber(new Phone());
    }

    /**
     * <p>
     * Failure test for <code>getTimeZone()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetTimeZone_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getTimeZone();
    }

    /**
     * <p>
     * Failure test for <code>setTimeZone(TimeZone)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetTimeZone_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setTimeZone(new TimeZone());
    }

    /**
     * <p>
     * Failure test for <code>getNotifications()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetNotifications_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getNotifications();
    }

    /**
     * <p>
     * Failure test for <code>setNotifications(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetNotifications_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setNotifications(new HashSet < Notification >());
    }

    /**
     * <p>
     * Failure test for <code>addNotification(Notification)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNotification_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < Notification > notifications = new HashSet < Notification >();
        impl.setNotifications(notifications);
        impl.addNotification(null);
    }

    /**
     * <p>
     * Failure test for <code>addNotification(Notification)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddNotification_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setNotifications(null);
        impl.addNotification(new Notification());
    }

    /**
     * <p>
     * Failure test for <code>addNotification(Notification)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddNotification_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addNotification(new Notification());
    }

    /**
     * <p>
     * Failure test for <code>removeNotification(Notification)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNotification_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < Notification > notifications = new HashSet < Notification >();
        impl.setNotifications(notifications);
        impl.removeNotification(null);
    }

    /**
     * <p>
     * Failure test for <code>removeNotification(Notification)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveNotification_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setNotifications(null);
        impl.removeNotification(new Notification());
    }

    /**
     * <p>
     * Failure test for <code>removeNotification(Notification)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveNotification_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.removeNotification(new Notification());
    }

    /**
     * <p>
     * Failure test for <code>getUserPreferences()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetUserPreferences_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getUserPreferences();
    }

    /**
     * <p>
     * Failure test for <code>setUserPreferences(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetUserPreferences_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setUserPreferences(new HashSet < UserPreference >());
    }

    /**
     * <p>
     * Failure test for <code>addUserPreference(UserPreference)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddUserPreference_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < UserPreference > userPreferences = new HashSet < UserPreference >();
        impl.setUserPreferences(userPreferences);
        impl.addUserPreference(null);
    }

    /**
     * <p>
     * Failure test for <code>addUserPreference(UserPreference)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddUserPreference_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setUserPreferences(null);
        impl.addUserPreference(new UserPreference());
    }

    /**
     * <p>
     * Failure test for <code>addUserPreference(UserPreference)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddUserPreference_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addUserPreference(new UserPreference());
    }

    /**
     * <p>
     * Failure test for <code>getUserPreference()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetUserPreference_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setUserPreferences(null);
        impl.getUserPreference(1);
    }

    /**
     * <p>
     * Failure test for <code>getUserPreference()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetUserPreference_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getUserPreference(1);
    }

    /**
     * <p>
     * Failure test for <code>getDemographicResponses()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetDemographicResponses_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getDemographicResponses();
    }

    /**
     * <p>
     * Failure test for <code>setDemographicResponses(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetDemographicResponses_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setDemographicResponses(new HashSet < DemographicResponse >());
    }

    /**
     * <p>
     * Failure test for <code>addDemographicResponse(DemographicResponse)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddDemographicResponse_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < DemographicResponse > demographicResponses = new HashSet < DemographicResponse >();
        impl.setDemographicResponses(demographicResponses);
        impl.addDemographicResponse(null);
    }

    /**
     * <p>
     * Failure test for <code>addDemographicResponse(DemographicResponse)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddDemographicResponse_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setDemographicResponses(null);
        impl.addDemographicResponse(new DemographicResponse());
    }

    /**
     * <p>
     * Failure test for <code>addDemographicResponse(DemographicResponse)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddDemographicResponse_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addDemographicResponse(new DemographicResponse());
    }

    /**
     * <p>
     * Failure test for <code>removeDemographicResponse(DemographicResponse)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDemographicResponse_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < DemographicResponse > demographicResponses = new HashSet < DemographicResponse >();
        impl.setDemographicResponses(demographicResponses);
        impl.removeDemographicResponse(null);
    }

    /**
     * <p>
     * Failure test for <code>removeDemographicResponse(DemographicResponse)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveDemographicResponse_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setDemographicResponses(null);
        impl.removeDemographicResponse(new DemographicResponse());
    }

    /**
     * <p>
     * Failure test for <code>removeDemographicResponse(DemographicResponse)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveDemographicResponse_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.removeDemographicResponse(new DemographicResponse());
    }

    /**
     * <p>
     * Failure test for <code>clearDemographicResponses()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testClearDemographicResponses_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setDemographicResponses(null);
        impl.clearDemographicResponses();
    }

    /**
     * <p>
     * Failure test for <code>clearDemographicResponses()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testClearDemographicResponses_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.clearDemographicResponses();
    }

    /**
     * <p>
     * Failure test for <code>getContact()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetContact_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getContact();
    }

    /**
     * <p>
     * Failure test for <code>setContact(Contact)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetContact_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setContact(new Contact());
    }

    /**
     * <p>
     * Failure test for <code>getTerms()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetTerms_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getTerms();
    }

    /**
     * <p>
     * Failure test for <code>setTerms(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetTerms_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setTerms(new HashSet < TermsOfUse >());
    }

    /**
     * <p>
     * Failure test for <code>addTerms(TermsOfUse)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddTerms_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < TermsOfUse > terms = new HashSet < TermsOfUse >();
        impl.setTerms(terms);
        impl.addTerms(null);
    }

    /**
     * <p>
     * Failure test for <code>addTerms(TermsOfUse)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddTerms_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setTerms(null);
        impl.addTerms(new TermsOfUse());
    }

    /**
     * <p>
     * Failure test for <code>addTerms(TermsOfUse)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddTerms_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addTerms(new TermsOfUse());
    }

    /**
     * <p>
     * Failure test for <code>hasTerms()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testHasTerms_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setTerms(null);
        impl.hasTerms(1);
    }

    /**
     * <p>
     * Failure test for <code>hasTerms()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testHasTerms_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.hasTerms(1);
    }

    /**
     * <p>
     * Failure test for <code>getEventRegistrations()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetEventRegistrations_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getEventRegistrations();
    }

    /**
     * <p>
     * Failure test for <code>setEventRegistrations(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetEventRegistrations_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setEventRegistrations(new HashSet < EventRegistration >());
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration(EventRegistration)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddEventRegistration_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < EventRegistration > eventRegistrations = new HashSet < EventRegistration >();
        impl.setEventRegistrations(eventRegistrations);
        impl.addEventRegistration(null);
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration(EventRegistration)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEventRegistration_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setEventRegistrations(null);
        impl.addEventRegistration(new EventRegistration());
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration(EventRegistration)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEventRegistration_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addEventRegistration(new EventRegistration());
    }

    /**
     * <p>
     * Failure test for <code>getEventRegistration(EventRegistration)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetEventRegistration_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < EventRegistration > eventRegistrations = new HashSet < EventRegistration >();
        impl.setEventRegistrations(eventRegistrations);
        impl.getEventRegistration(null);
    }

    /**
     * <p>
     * Failure test for <code>getEventRegistration(EventRegistration)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetEventRegistration_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setEventRegistrations(null);
        impl.getEventRegistration(new Event());
    }

    /**
     * <p>
     * Failure test for <code>getEventRegistration(EventRegistration)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetEventRegistration_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getEventRegistration(new Event());
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration()</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddEventRegistration2_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < EventRegistration > eventRegistrations = new HashSet < EventRegistration >();
        impl.setEventRegistrations(eventRegistrations);
        impl.addEventRegistration(null, new ArrayList < Response >(), true, "note");
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEventRegistration2_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setEventRegistrations(null);
        impl.addEventRegistration(new Event(), new ArrayList < Response >(), true, "note");
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEventRegistration2_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addEventRegistration(new Event(), new ArrayList < Response >(), true, "note");
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration()</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddEventRegistration3_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < EventRegistration > eventRegistrations = new HashSet < EventRegistration >();
        impl.setEventRegistrations(eventRegistrations);
        impl.addEventRegistration(null, new ArrayList < Response >(), true);
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEventRegistration3_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setEventRegistrations(null);
        impl.addEventRegistration(new Event(), new ArrayList < Response >(), true);
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEventRegistration3_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addEventRegistration(new Event(), new ArrayList < Response >(), true);
    }

    /**
     * <p>
     * Failure test for <code>getSecretQuestion()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetSecretQuestion_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getSecretQuestion();
    }

    /**
     * <p>
     * Failure test for <code>setSecretQuestion(SecretQuestion)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetSecretQuestion_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setSecretQuestion(new SecretQuestion());
    }

    /**
     * <p>
     * Failure test for <code>getResponses()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetResponses_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getResponses();
    }

    /**
     * <p>
     * Failure test for <code>setResponses(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetResponses_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setResponses(new HashSet < Response >());
    }

    /**
     * <p>
     * Failure test for <code>addResponse(Response)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddResponse_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < Response > responses = new HashSet < Response >();
        impl.setResponses(responses);
        impl.addResponse(null);
    }

    /**
     * <p>
     * Failure test for <code>addResponse(Response)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddResponse_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setResponses(null);
        impl.addResponse(new Response());
    }

    /**
     * <p>
     * Failure test for <code>addResponse(Response)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddResponse_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addResponse(new Response());
    }

    /**
     * <p>
     * Failure test for <code>addResponses(List)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddResponses_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < Response > responses = new HashSet < Response >();
        impl.setResponses(responses);
        impl.addResponses(null);
    }

    /**
     * <p>
     * Failure test for <code>addResponses(List)</code>.<br>
     * The parameter contains null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddResponses_ParamContainsNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < Response > responses = new HashSet < Response >();
        impl.setResponses(responses);
        List < Response > list = new ArrayList < Response >();
        list.add(null);
        impl.addResponses(list);
    }

    /**
     * <p>
     * Failure test for <code>addResponses(List)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddResponses_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setResponses(null);
        impl.addResponses(new ArrayList < Response >());
    }

    /**
     * <p>
     * Failure test for <code>addResponses(List)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAdd_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addResponses(new ArrayList < Response >());
    }

    /**
     * <p>
     * Failure test for <code>removeResponse(Response)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveResponse_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < Response > responses = new HashSet < Response >();
        impl.setResponses(responses);
        impl.removeResponse(null);
    }

    /**
     * <p>
     * Failure test for <code>removeResponse(Response)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveResponse_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setResponses(null);
        impl.removeResponse(new Response());
    }

    /**
     * <p>
     * Failure test for <code>removeResponse(Response)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveResponse_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.removeResponse(new Response());
    }

    /**
     * <p>
     * Failure test for <code>getBallots()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetBallots_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getBallots();
    }

    /**
     * <p>
     * Failure test for <code>setBallots(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetBallots_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setBallots(new HashSet < RankBallot >());
    }

    /**
     * <p>
     * Failure test for <code>addBallot(RankBallot)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddBallot_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < RankBallot > ballots = new HashSet < RankBallot >();
        impl.setBallots(ballots);
        impl.addBallot(null);
    }

    /**
     * <p>
     * Failure test for <code>addBallot(Ballot)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddBallot_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setBallots(null);
        impl.addBallot(new RankBallot());
    }

    /**
     * <p>
     * Failure test for <code>addBallot(Ballot)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddBallot_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addBallot(new RankBallot());
    }

    /**
     * <p>
     * Failure test for <code>getCompPrizes()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetCompPrizes_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getCompPrizes();
    }

    /**
     * <p>
     * Failure test for <code>setCompPrizes(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetCompPrizes_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setCompPrizes(new HashSet < UserContestPrize >());
    }

    /**
     * <p>
     * Failure test for <code>addCompPrize(UserContestPrize)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddCompPrize_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < UserContestPrize > compPrizes = new HashSet < UserContestPrize >();
        impl.setCompPrizes(compPrizes);
        impl.addCompPrize(null);
    }

    /**
     * <p>
     * Failure test for <code>addCompPrize(UserContestPrize)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddCompPrize_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setCompPrizes(null);
        impl.addCompPrize(new UserContestPrize());
    }

    /**
     * <p>
     * Failure test for <code>addCompPrize(CompPrize)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddCompPrize_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addCompPrize(new UserContestPrize());
    }

    /**
     * <p>
     * Failure test for <code>getProfessor()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetProfessor_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getProfessor();
    }

    /**
     * <p>
     * Failure test for <code>setProfessor(Professor)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetProfessor_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setProfessor(new Professor());
    }

    /**
     * <p>
     * Failure test for <code>getSchools()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetSchools_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getSchools();
    }

    /**
     * <p>
     * Failure test for <code>setSchools(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetSchools_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setSchools(new HashSet < UserSchool >());
    }

    /**
     * <p>
     * Failure test for <code>getSchool()</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetSchool_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < UserSchool > schools = new HashSet < UserSchool >();
        impl.setSchools(schools);
        impl.getSchool(null, 2);
    }

    /**
     * <p>
     * Failure test for <code>getSchool()</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetSchool_ParamNull2() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < UserSchool > schools = new HashSet < UserSchool >();
        impl.setSchools(schools);
        impl.getSchool(new Long(1), null);
    }

    /**
     * <p>
     * Failure test for <code>getSchool()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetSchool_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setSchools(null);
        impl.getSchool(new Long(1), 2);
    }

    /**
     * <p>
     * Failure test for <code>getSchool()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetSchool_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getSchool(new Long(1), 2);
    }

    /**
     * <p>
     * Failure test for <code>getPrimaryTeachingSchool()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetPrimaryTeachingSchool_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setSchools(null);
        impl.getPrimaryTeachingSchool();
    }

    /**
     * <p>
     * Failure test for <code>getPrimaryTeachingSchool()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetPrimaryTeachingSchool_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getPrimaryTeachingSchool();
    }

    /**
     * <p>
     * Failure test for <code>getPrimaryStudentSchool()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetPrimaryStudentSchool_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setSchools(null);
        impl.getPrimaryStudentSchool();
    }

    /**
     * <p>
     * Failure test for <code>getPrimaryStudentSchool()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetPrimaryStudentSchool_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getPrimaryStudentSchool();
    }

    /**
     * <p>
     * Failure test for <code>addSchool(UserSchool)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddSchool_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < UserSchool > schools = new HashSet < UserSchool >();
        impl.setSchools(schools);
        impl.addSchool(null);
    }

    /**
     * <p>
     * Failure test for <code>addSchool(SchoolUser)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddSchool_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setSchools(null);
        impl.addSchool(new UserSchool());
    }

    /**
     * <p>
     * Failure test for <code>addSchool(UserSchool)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddSchool_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addSchool(new UserSchool());
    }

    /**
     * <p>
     * Failure test for <code>getCreatedSchools()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetCreatedSchools_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getCreatedSchools();
    }

    /**
     * <p>
     * Failure test for <code>setCreatedSchools(Set)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetCreatedSchools_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setCreatedSchools(new HashSet < School >());
    }

    /**
     * <p>
     * Failure test for <code>addCreatedSchool(School)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddCreatedSchool_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        Set < School > createdSchools = new HashSet < School >();
        impl.setCreatedSchools(createdSchools);
        impl.addCreatedSchool(null);
    }

    /**
     * <p>
     * Failure test for <code>addCreatedSchool(School)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddCreatedSchool_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setCreatedSchools(null);
        impl.addCreatedSchool(new School());
    }

    /**
     * <p>
     * Failure test for <code>addCreatedSchool(School)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddCreatedSchool_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.addCreatedSchool(new School());
    }

    /**
     * <p>
     * Failure test for <code>getTransientResponses()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetTransientResponses_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getTransientResponses();
    }

    /**
     * <p>
     * Failure test for <code>setTransientResponses(List)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetTransientResponses_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setTransientResponses(new ArrayList < DemographicResponse >());
    }

    /**
     * <p>
     * Failure test for <code>removeTransientResponse(DemographicResponse)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveTransientResponse_ParamNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        List < DemographicResponse > transientResponses = new ArrayList < DemographicResponse >();
        impl.setTransientResponses(transientResponses);
        impl.removeTransientResponse(null);
    }

    /**
     * <p>
     * Failure test for <code>removeTransientResponse(DemographicResponse)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveTransientResponse_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setTransientResponses(null);
        impl.removeTransientResponse(new DemographicResponse());
    }

    /**
     * <p>
     * Failure test for <code>removeTransientResponse(DemographicResponse)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveTransientResponse_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.removeTransientResponse(new DemographicResponse());
    }

    /**
     * <p>
     * Failure test for <code>getCoder()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetCoder_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.getCoder();
    }

    /**
     * <p>
     * Failure test for <code>setCoder(Coder)</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testSetCoder_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.setCoder(new Coder());
    }

    /**
     * <p>
     * Failure test for <code>isShowEarningsEnabled()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testIsShowEarningsEnabled_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.isShowEarningsEnabled();
    }

    /**
     * <p>
     * Failure test for <code>isShowEarningsEnabled()</code>.<br>
     * The need field is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testIsShowEarningsEnabled_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setUserPreferences(null);
        impl.isShowEarningsEnabled();
    }

    /**
     * <p>
     * Failure test for <code>isMemberContactEnabled()</code>.<br>
     * The user profile is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testIsMemberContactEnabled_UserProfileNull() throws Exception {
        impl.setUserProfile(null);
        impl.isMemberContactEnabled();
    }

    /**
     * <p>
     * Failure test for <code>isMemberContactEnabled()</code>.<br>
     * The need field is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testIsMemberContactEnabled_NeedFieldNull() throws Exception {
        impl.setUserProfile(new UserProfile());
        impl.setUserPreferences(null);
        impl.isMemberContactEnabled();
    }
}
