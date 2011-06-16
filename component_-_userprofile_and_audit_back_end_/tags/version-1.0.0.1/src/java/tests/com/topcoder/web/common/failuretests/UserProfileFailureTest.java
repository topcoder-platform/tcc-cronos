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
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.Event;
import com.topcoder.web.common.model.EventRegistration;
import com.topcoder.web.common.model.Notification;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.Response;
import com.topcoder.web.common.model.School;
import com.topcoder.web.common.model.TermsOfUse;
import com.topcoder.web.common.model.UserPreference;
import com.topcoder.web.common.model.UserProfile;
import com.topcoder.web.common.model.UserSchool;
import com.topcoder.web.common.model.comp.UserContestPrize;
import com.topcoder.web.common.voting.RankBallot;

/**
 * <p>
 * Failure tests for class <code>UserProfile</code>.
 * </p>
 * @author stevenfrog
 * @version 1.0
 */
public class UserProfileFailureTest {
    /**
     * <p>
     * Represents the <code>UserProfile</code> instance used to test against.
     * </p>
     */
    private UserProfile impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserProfileFailureTest.class);
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
        impl = new UserProfile();
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
     * Failure test for <code>addAddress(Address)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddAddress_ParamNull() throws Exception {
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
        impl.setAddresses(null);
        impl.addAddress(new Address());
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
        impl.setAddresses(null);
        impl.getHomeAddress();
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
        impl.setPhoneNumbers(null);
        impl.addPhoneNumber(new Phone());
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
        impl.setPhoneNumbers(null);
        impl.getPrimaryPhoneNumber();
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
        impl.setNotifications(null);
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
        impl.setNotifications(null);
        impl.removeNotification(new Notification());
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
        impl.setUserPreferences(null);
        impl.addUserPreference(new UserPreference());
    }

    /**
     * <p>
     * Failure test for <code>getUserPreference(Integer)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetUserPreference_NeedFieldNull() throws Exception {
        impl.setUserPreferences(null);
        impl.getUserPreference(1);
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
        impl.setDemographicResponses(null);
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
        impl.setDemographicResponses(null);
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
        impl.setDemographicResponses(null);
        impl.clearDemographicResponses();
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
        impl.setTerms(null);
        impl.addTerms(new TermsOfUse());
    }

    /**
     * <p>
     * Failure test for <code>hasTerms(Integer)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testHasTerms_NeedFieldNull() throws Exception {
        impl.setTerms(null);
        impl.hasTerms(2);
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
        impl.setEventRegistrations(null);
        impl.addEventRegistration(new EventRegistration());
    }

    /**
     * <p>
     * Failure test for <code>getEventRegistration(Event)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetEventRegistration_ParamNull() throws Exception {
        Set < EventRegistration > eventRegistrations = new HashSet < EventRegistration >();
        impl.setEventRegistrations(eventRegistrations);
        impl.getEventRegistration(null);
    }

    /**
     * <p>
     * Failure test for <code>getEventRegistration(Event)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testGetEventRegistration_NeedFieldNull() throws Exception {
        impl.setEventRegistrations(null);
        impl.getEventRegistration(new Event());
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration(Event, List, Boolean, String)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddEventRegistration2_ParamNull() throws Exception {
        Set < EventRegistration > eventRegistrations = new HashSet < EventRegistration >();
        impl.setEventRegistrations(eventRegistrations);
        impl.addEventRegistration(null, null, true, "note");
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration(Event, List, Boolean, String)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEventRegistration2_NeedFieldNull() throws Exception {
        impl.setEventRegistrations(null);
        impl.addEventRegistration(new Event(), null, true, "note");
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration(Event, List, Boolean, String)</code>.<br>
     * The response is invalid.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEventRegistration2_ResponseInvalid() throws Exception {
        Set < EventRegistration > eventRegistrations = new HashSet < EventRegistration >();
        impl.setEventRegistrations(eventRegistrations);
        impl.setResponses(null);
        impl.addEventRegistration(new Event(), new ArrayList < Response >(), true, "note");
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration(Event, List, Boolean)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddEventRegistration3_ParamNull() throws Exception {
        Set < EventRegistration > eventRegistrations = new HashSet < EventRegistration >();
        impl.setEventRegistrations(eventRegistrations);
        impl.addEventRegistration(null, null, true);
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration(Event, List, Boolean)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEventRegistration3_NeedFieldNull() throws Exception {
        impl.setEventRegistrations(null);
        impl.addEventRegistration(new Event(), null, true);
    }

    /**
     * <p>
     * Failure test for <code>addEventRegistration(Event, List, Boolean)</code>.<br>
     * The response is invalid.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddEventRegistration3_ResponseInvalid() throws Exception {
        Set < EventRegistration > eventRegistrations = new HashSet < EventRegistration >();
        impl.setEventRegistrations(eventRegistrations);
        impl.setResponses(null);
        impl.addEventRegistration(new Event(), new ArrayList < Response >(), true);
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
        impl.setResponses(null);
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
        Set < Response > responses = new HashSet < Response >();
        impl.setResponses(responses);
        List < Response > toAdd = new ArrayList < Response >();
        toAdd.add(null);
        impl.addResponses(toAdd);
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
        impl.setResponses(null);
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
        impl.setResponses(null);
        impl.removeResponse(new Response());
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
        Set < RankBallot > ballots = new HashSet < RankBallot >();
        impl.setBallots(ballots);
        impl.addBallot(null);
    }

    /**
     * <p>
     * Failure test for <code>addBallot(RankBallot)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddBallot_NeedFieldNull() throws Exception {
        impl.setBallots(null);
        impl.addBallot(new RankBallot());
    }

    /**
     * <p>
     * Failure test for <code>addCompPrize(RankCompPrize)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddCompPrize_ParamNull() throws Exception {
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
        impl.setCompPrizes(null);
        impl.addCompPrize(new UserContestPrize());
    }

    /**
     * <p>
     * Failure test for <code>getSchool(Long, Integer)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetSchool_ParamNull() throws Exception {
        Set < UserSchool > schools = new HashSet < UserSchool >();
        impl.setSchools(schools);
        impl.getSchool(null, 1);
    }

    /**
     * <p>
     * Failure test for <code>getSchool(Long, Integer)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetSchool_ParamNull2() throws Exception {
        Set < UserSchool > schools = new HashSet < UserSchool >();
        impl.setSchools(schools);
        impl.getSchool(new Long(1), null);
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
        impl.setSchools(null);
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
        impl.setSchools(null);
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
        Set < UserSchool > schools = new HashSet < UserSchool >();
        impl.setSchools(schools);
        impl.addSchool(null);
    }

    /**
     * <p>
     * Failure test for <code>addSchool(UserSchool)</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testAddSchool_NeedFieldNull() throws Exception {
        impl.setSchools(null);
        impl.addSchool(new UserSchool());
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
        impl.setCreatedSchools(null);
        impl.addCreatedSchool(new School());
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
        impl.setTransientResponses(null);
        impl.removeTransientResponse(new DemographicResponse());
    }

    /**
     * <p>
     * Failure test for <code>isShowEarningsEnabled()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testIsShowEarningsEnabled_NeedFieldNull() throws Exception {
        impl.setUserPreferences(null);
        impl.isShowEarningsEnabled();
    }

    /**
     * <p>
     * Failure test for <code>isMemberContactEnabled()</code>.<br>
     * The need filed is null.<br>
     * Expect IllegalStateException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testIsMemberContactEnabled_NeedFieldNull() throws Exception {
        impl.setUserPreferences(null);
        impl.isMemberContactEnabled();
    }

}
