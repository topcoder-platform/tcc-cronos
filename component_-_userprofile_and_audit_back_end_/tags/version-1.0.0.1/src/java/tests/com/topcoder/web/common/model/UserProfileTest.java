/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.web.common.model.comp.UserContestPrize;
import com.topcoder.web.common.model.educ.Professor;
import com.topcoder.web.common.voting.RankBallot;

import junit.framework.TestCase;

/**
 * Unit tests for {@link UserProfile} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserProfileTest extends TestCase {
    /**
     * The UserProfile instance used for testing.
     */
    private UserProfile instance;

    /**
     * Sets up the test environment.
     */
    public void setUp() {
        instance = TestHelper.getUserProfile();
        User user = new User();
        user.setId(100l);
        instance.setUser(user);
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test case for {@link UserProfile#getFirstName()} and {@link UserProfile#setFirstName(String)} methods.
     */
    public void testSetGetFirstName() {
        assertEquals("Error getting first name", TestHelper.getUserProfile().getFirstName(), instance.getFirstName());

        String firstName = "first";
        instance.setFirstName(firstName);
        assertEquals("Error setting first name", firstName, instance.getFirstName());
    }

    /**
     * Accuracy test case for {@link UserProfile#getMiddleName()} and {@link UserProfile#setMiddleName(String)} methods.
     */
    public void testSetGetMiddleName() {
        assertEquals("Error getting Middle name", TestHelper.getUserProfile().getMiddleName(),
                instance.getMiddleName());

        String middleName = "Middle";
        instance.setMiddleName(middleName);
        assertEquals("Error setting Middle name", middleName, instance.getMiddleName());
    }

    /**
     * Accuracy test case for {@link UserProfile#getLastName()} and {@link UserProfile#setLastName(String)} methods.
     */
    public void testSetGetLastName() {
        assertEquals("Error getting Last name", TestHelper.getUserProfile().getLastName(), instance.getLastName());

        String lastName = "Last";
        instance.setLastName(lastName);
        assertEquals("Error setting Last name", lastName, instance.getLastName());
    }

    /**
     * Accuracy test case for {@link UserProfile#setAddresses(Set)} and {@link UserProfile#getAddresses()} methods.
     */
    public void testSetGetAddresses() {
        Set < Address > addresses = new HashSet < Address >();
        instance.setAddresses(addresses);
        assertEquals("Error setting getting addresses", addresses, instance.getAddresses());
    }

    /**
     * Accuracy test case for {@link UserProfile#addAddress(Address)} method.
     */
    public void testAddAddress() {
        instance.addAddress(new Address());
        assertEquals("Error adding address", 1, instance.getAddresses().size());
    }

    /**
     * Failure test case for {@link UserProfile#addAddress(Address)} method.
     */
    public void testAddAddressFail() {
        try {
            instance.addAddress(null);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy,Failure tests for {@link UserProfile#getHomeAddress()} method.
     */
    public void testGetHomeAddress() {
        instance.setAddresses(TestHelper.getAddresses());
        assertNotNull("Error getting home address", instance.getHomeAddress());
    }

    /**
     * Test cases for {@link UserProfile#getPhoneNumbers()} and {@link UserProfile#setPhoneNumbers(Set)} methods.
     */
    public void testTestGetPhoneNumbers() {
        instance.setPhoneNumbers(new HashSet < Phone >());
        assertEquals("Error getting phone numbers", 0, instance.getPhoneNumbers().size());
    }

    /**
     * Test case for {@link UserProfile#getPrimaryPhoneNumber()} method.
     */
    public void testGetPrimaryPhoneNumber() {
        assertNull("Error getting primary phone number", instance.getPrimaryPhoneNumber());
        instance.setPhoneNumbers(TestHelper.getPhoneNumbers());
        assertNotNull("Error getting primary phone number", instance.getPrimaryPhoneNumber());
    }

    /**
     * Test case for {@link UserProfile#addPhoneNumber(Phone)} method.
     */
    public void testAddPhoneNumber() {
        instance.addPhoneNumber(new Phone());
        assertEquals("Error adding the phone number", 1, instance.getPhoneNumbers().size());

        try {
            instance.addPhoneNumber(null);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test case for {@link UserProfileProfile#setTimeZone(TimeZone)} and {@link UserProfile#getTimeZone()} methods.
     */
    public void testSetGetTimezone() {
        TimeZone time = new TimeZone();
        instance.setTimeZone(time);
        assertEquals("Error setting getting TimeZone", time, instance.getTimeZone());
    }

    /**
     * Test cases for {@link UserProfile#getNotifications()} and {@link UserProfile#setNotifications(Set)} methods.
     */
    public void testSetGetNotifications() {
        instance.setNotifications(new HashSet < Notification >());
        assertNotNull("Error setting getting Notifications", instance.getNotifications());
        assertEquals("Error setting getting notifications", 0, instance.getNotifications().size());
    }

    /**
     * Test cases for {@link UserProfile#addNotification(Notification)} method.
     */
    public void testAddNotification() {
        Notification notification = new Notification();
        instance.addNotification(notification);
        assertEquals("Error adding Notification", 1, instance.getNotifications().size());

        try {
            instance.addNotification(null);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test case for {@link UserProfile#removeNotification(Notification)} method.
     */
    public void testRemoveNotification() {
        Notification notification = new Notification();
        notification.setSort(1);
        instance.addNotification(notification);
        assertEquals("Error adding Notification", 1, instance.getNotifications().size());

        instance.removeNotification(notification);
        assertEquals("Error adding Notification", 0, instance.getNotifications().size());

        try {
            instance.removeNotification(null);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test case for {@link UserProfile#setUserPreferences(Set)} and {@link UserProfile#getUserPreferences()} methods.
     */
    public void testSetGetUserPreferences() {
        instance.setUserPreferences(new HashSet < UserPreference >());
        assertNotNull("Error setting getting UserProfilePreferences", instance.getUserPreferences());
        assertEquals("Error setting getting UserProfilePreferences", 0, instance.getUserPreferences().size());
    }

    /**
     * Test case for {@link UserProfile#addUserPreference(UserPreference)} and
     * {@link UserProfile#getUserPreference(Integer)} methods.
     */
    public void testAddGetUserProfilePreference() {
        UserPreference userProfilePref = new UserPreference();
        instance.setId(100l);
        Preference preference = new Preference();
        preference.setId(100);
        UserPreference.Identifier identifier = new UserPreference.Identifier(instance.getUser(), preference);
        userProfilePref.setId(identifier);

        instance.addUserPreference(userProfilePref);
        assertEquals("Error adding UserProfilePreference", 1, instance.getUserPreferences().size());

        UserPreference userProfilePref1 = instance.getUserPreference(100);
        assertEquals("Error getting UserProfilePreference", userProfilePref, userProfilePref1);
    }

    /**
     * Test cases for {@link UserProfile#getDemographicResponses()} and {@link UserProfile#setDemographicResponses(Set)}
     * methods.
     */
    public void testSetGetDemographicResponses() {
        instance.setDemographicResponses(new HashSet < DemographicResponse >());
        assertNotNull("Error setting getting DemographicResponses", instance.getDemographicResponses());
        assertEquals("Error setting getting DemographicResponses", 0, instance.getDemographicResponses().size());
    }

    /**
     * Test cases for {@link UserProfile#addDemographicResponse(DemographicResponse)} and
     * {@link UserProfile#removeDemographicResponse(DemographicResponse)} methods.
     */
    public void testAddRemoveDemographicResponse() {
        DemographicResponse demographicResponse = new DemographicResponse();
        instance.setId(100l);
        DemographicQuestion question = new DemographicQuestion();
        question.setId(100l);
        question.setSelectable(DemographicQuestion.FREE_FORM);
        DemographicAnswer answer = new DemographicAnswer();
        answer.setId(100l);
        answer.setQuestion(question);
        DemographicResponse.Identifier identifier = new DemographicResponse.Identifier(instance.getUser(), question,
                answer);
        demographicResponse.setId(identifier);
        demographicResponse.setQuestion(question);
        instance.addDemographicResponse(demographicResponse);
        assertEquals("Error adding DemographicResponse", 1, instance.getDemographicResponses().size());

        instance.removeDemographicResponse(demographicResponse);
        assertEquals("Error removing DemographicResponse", 0, instance.getDemographicResponses().size());
        try {
            instance.addDemographicResponse(null);
            fail("Exception should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test case for {@link UserProfile#clearDemographicResponses()} method.
     */
    public void testClearDemographicResponses() {
        instance.clearDemographicResponses();
        assertEquals("Error clearing DemographicResponse", 0, instance.getDemographicResponses().size());
    }

    /**
     * Accuracy test case for {@link UserProfile#setContact(Contact) and {@link UserProfile#getContact()} methods.
     */
    public void testSetGetContact() {
        Contact contact = new Contact();
        instance.setContact(contact);
        assertEquals("Error setting getting values", contact, instance.getContact());
    }

    /**
     * Accuracy test case for {@link UserProfile#setTermes(Set)} and {@link UserProfile#getTermes()} methods.
     */
    public void testSetGetTermes() {
        Set < TermsOfUse > terms = new HashSet < TermsOfUse >();
        instance.setTerms(terms);
        assertEquals("Error setting getting Termes", terms, instance.getTerms());
    }

    /**
     * Test case for {@link UserProfile#addTerms(TermsOfUse)} and {@link UserProfile#hasTerms(Integer)} methods.
     */
    public void testAddHasTerms() {
        TermsOfUse terms = new TermsOfUse();
        terms.setId(100);
        instance.addTerms(terms);
        assertEquals("Error adding Terms", 1, instance.getTerms().size());

        assertTrue("Error checking for terms", instance.hasTerms(100));
    }

    /**
     * Test cases for {@link UserProfile#getEventRegistrations()} and {@link UserProfile#setEventRegistrations(Set)}
     * methods.
     */
    public void testSetGetEventRegistrations() {
        instance.setEventRegistrations(new HashSet < EventRegistration >());
        assertNotNull("Error setting getting EventRegistrations", instance.getEventRegistrations());
        assertEquals("Error setting getting EventRegistrations", 0, instance.getEventRegistrations().size());
    }

    /**
     * Test case for {@link UserProfile#addEventRegistration(EventRegistration)} method.
     */
    public void testAddGetEventRegistration() {
        EventRegistration eventRegistration = new EventRegistration();
        instance.getUser().setId(100l);
        Event event = new Event();
        event.setId(100l);
        EventRegistration.Identifier identifier = new EventRegistration.Identifier(instance.getUser(), event);
        eventRegistration.setId(identifier);
        instance.addEventRegistration(eventRegistration);
        assertEquals("Error adding event registration", 1, instance.getEventRegistrations().size());

        EventRegistration eveReg = instance.getEventRegistration(event);
        assertEquals("Error getting EventRegistration", eveReg, eventRegistration);
    }

    /**
     * Test case for {@link UserProfile#addEventRegistration(Event, java.util.List, Boolean, String)} method.
     */
    public void testAddEventRegistration1() {
        instance.setId(100l);
        Event event = new Event();
        event.setId(100l);
        instance.addEventRegistration(event, new ArrayList < Response >(), true, null);
        assertEquals("Error adding event registration", 1, instance.getEventRegistrations().size());
    }

    /**
     * Test case for {@link UserProfile#addEventRegistration(Event, java.util.List, Boolean)} method.
     */
    public void testAddEventRegistration2() {
        instance.setId(100l);
        Event event = new Event();
        event.setId(100l);
        instance.addEventRegistration(event, new ArrayList < Response >(), true);
        assertEquals("Error adding event registration", 1, instance.getEventRegistrations().size());
    }

    /**
     * Accuracy test case for {@link UserProfile#setSecretQuestion(SecretQuestion) and
     * {@link UserProfile#getSecretQuestion()} methods.
     */
    public void testSetGetSecretQuestion() {
        SecretQuestion secretQuestion = new SecretQuestion();
        instance.setSecretQuestion(secretQuestion);
        assertEquals("Error setting getting values", secretQuestion, instance.getSecretQuestion());
    }

    /**
     * Test cases for {@link UserProfile#getResponses()} and {@link UserProfile#setResponses(Set)} methods.
     */
    public void testSetGetResponses() {
        instance.setResponses(new HashSet < Response >());
        assertNotNull("Error setting getting Responses", instance.getResponses());
        assertEquals("Error setting getting Responses", 0, instance.getResponses().size());
    }

    /**
     * Test case for {@link UserProfile#addResponse(Response)} and {@link UserProfile#addResponses(List)} and
     * {@link UserProfile#removeResponse(Response)} methods.
     */
    public void testAddResponse() {
        Response response = new Response();
        instance.addResponse(response);
        assertEquals("error adding response", 1, instance.getResponses().size());

        List < Response > responses = new ArrayList < Response >();
        responses.add(new Response());
        instance.addResponses(responses);
        assertEquals("error adding responses", 2, instance.getResponses().size());

        instance.removeResponse(response);
        assertEquals("error removing response", 1, instance.getResponses().size());
    }

    /**
     * Test case for {@link UserProfile#getBallots()} and {@link UserProfile#setBallots(Set)} methods.
     */
    public void testSetGetBallots() {
        instance.setBallots(new HashSet < RankBallot >());
        assertEquals("Error setting getting Ballot", 0, instance.getBallots().size());
    }

    /**
     * Test case for {@link UserProfile#addBallot(RankBallot)} method.
     */
    public void testAddBallot() {
        RankBallot ballot = new RankBallot();
        instance.addBallot(ballot);
        assertEquals("Error adding ballot", 1, instance.getBallots().size());
    }

    /**
     * Test cases for {@link UserProfile#getCompPrizes()} and {@link UserProfile#setCompPrizes(Set)} methods.
     */
    public void testSetGetUserProfileContestPrizes() {
        instance.setCompPrizes(new HashSet < UserContestPrize >());
        assertNotNull("Error setting getting UserProfileContestPrizes", instance.getCompPrizes());
        assertEquals("Error setting getting UserProfileContestPrizes", 0, instance.getCompPrizes().size());
    }

    /**
     * Test case for {@link UserProfile#addCompPrize(UserProfileContestPrize)} method.
     */
    public void testAddCompPrize() {
        instance.addCompPrize(new UserContestPrize());
        assertNotNull("Error adding UserProfileContestPrizes", instance.getCompPrizes());
        assertEquals("Error adding UserProfileContestPrizes", 1, instance.getCompPrizes().size());
    }

    /**
     * Test case for {@link UserProfile#setProfessor(Professor)} and {@link UserProfile#getProfessor()} methods.
     */
    public void testSetGetProfessor() {
        Professor professor = new Professor();
        instance.setProfessor(professor);
        assertEquals("Error setting getting professor", professor, instance.getProfessor());
    }

    /**
     * Test cases for {@link UserProfile#getSchools()} and {@link UserProfile#setSchools(Set)} methods.
     */
    public void testSetGetUserProfileSchools() {
        instance.setSchools(new HashSet < UserSchool >());
        assertNotNull("Error setting getting UserProfileSchools", instance.getSchools());
        assertEquals("Error setting getting UserProfileSchools", 0, instance.getSchools().size());
    }

    /**
     * Test case for {@link UserProfile#getSchool(Long, Integer)} method.
     */
    public void testGetSchool() {
        UserSchool school = new UserSchool();
        school.setId(100l);
        school.setPrimary(true);
        SchoolAssociationType assc = new SchoolAssociationType();
        assc.setId(100);
        school.setAssociationType(assc);

        School sch = new School();
        sch.setId(100l);
        school.setSchool(sch);

        instance.addSchool(school);
        assertEquals("Error getting school", school, instance.getSchool(100l, 100));
    }

    /**
     * Test case for {@link UserProfile#getPrimaryTeachingSchool()} method.
     */
    public void testGetPrimarySchool() {
        UserSchool school = new UserSchool();
        school.setId(100l);
        school.setPrimary(true);
        SchoolAssociationType assc = new SchoolAssociationType();
        assc.setId(SchoolAssociationType.TEACHER);
        school.setAssociationType(assc);

        School sch = new School();
        sch.setId(100l);
        school.setSchool(sch);

        instance.addSchool(school);
        assertEquals("Error getting school", school, instance.getPrimaryTeachingSchool());
    }

    /**
     * Test case for {@link UserProfile#getPrimaryStudentSchool()} method.
     */
    public void testGetPrimarySchool1() {
        UserSchool school = new UserSchool();
        school.setId(100l);
        school.setPrimary(true);
        SchoolAssociationType assc = new SchoolAssociationType();
        assc.setId(SchoolAssociationType.STUDENT);
        school.setAssociationType(assc);

        School sch = new School();
        sch.setId(100l);
        school.setSchool(sch);

        instance.addSchool(school);
        assertEquals("Error getting school", school, instance.getPrimaryStudentSchool());
    }

    /**
     * Test cases for {@link UserProfile#getCreatedSchools()} and {@link UserProfile#setCreatedSchools(Set)} methods.
     */
    public void testSetGetcreatedSchools() {
        instance.setCreatedSchools(new HashSet < School >());
        assertNotNull("Error setting getting UserProfileSchools", instance.getCreatedSchools());
        assertEquals("Error setting getting UserProfileSchools", 0, instance.getCreatedSchools().size());
    }

    /**
     * Test case for {@link UserProfile#addCreatedSchool(School)}.
     */
    public void testAddCreatedSchool() {
        School sch = new School();
        sch.setId(100l);
        instance.addCreatedSchool(sch);
        assertEquals("Error setting getting UserProfileSchools", 1, instance.getCreatedSchools().size());
    }

    /**
     * Test cases for {@link UserProfile#getTransientResponses()} and {@link UserProfile#setTransientResponses(List)}
     * methods.
     */
    public void testSetGetTransientResponses() {
        instance.setTransientResponses(new ArrayList < DemographicResponse >());
        assertNotNull("Error setting getting DemographicResponses", instance.getTransientResponses());
        assertEquals("Error setting getting DemographicResponses", 0, instance.getTransientResponses().size());
    }

    /**
     * Test case for {@link UserProfile#removeDemographicResponse(DemographicResponse)} method.
     */
    public void testRemoveTransientResponse() {
        List < DemographicResponse > responses = new ArrayList < DemographicResponse >();
        DemographicResponse res = new DemographicResponse();
        instance.setId(100l);
        DemographicQuestion ques = new DemographicQuestion();
        ques.setId(100l);
        ques.setSelectable(DemographicQuestion.FREE_FORM);
        res.setQuestion(ques);
        DemographicAnswer ans = new DemographicAnswer();
        ans.setId(100l);
        ans.setQuestion(ques);
        DemographicResponse.Identifier identifier = new DemographicResponse.Identifier(instance.getUser(), ques, ans);
        res.setId(identifier);
        res.setUser(instance.getUser());
        responses.add(res);
        instance.setTransientResponses(responses);
        instance.removeDemographicResponse(res);
        assertEquals("Error setting getting DemographicResponses", 1, instance.getTransientResponses().size());
    }

    /**
     * Test case for {@link UserProfile#getCoder()} and {@link UserProfile#setCoder(Coder)} methods.
     */
    public void testSetGetCoder() {
        Coder coder = new Coder();
        instance.setCoder(coder);
        assertEquals("Error setting getting Coder", coder, instance.getCoder());
    }

    /**
     * Test case for {@link UserProfile#isShowEarningsEnabled()} method.
     */
    public void testIsShowEarningsEnabled() {
        assertFalse("Error getting the flag", instance.isShowEarningsEnabled());
    }

    /**
     * Test case for {@link UserProfile#isMemberContactEnabled()} method.
     */
    public void testIsMemberContactEnabled() {
        assertFalse("Error getting the flag", instance.isMemberContactEnabled());
    }
}
