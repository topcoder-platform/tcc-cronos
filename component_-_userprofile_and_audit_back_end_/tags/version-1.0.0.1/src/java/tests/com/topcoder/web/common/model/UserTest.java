/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.web.common.WebConstants;
import com.topcoder.web.common.model.comp.UserContestPrize;
import com.topcoder.web.common.model.educ.Professor;
import com.topcoder.web.common.voting.RankBallot;

import junit.framework.TestCase;

/**
 * Unit test cases for {@link User} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserTest extends TestCase {

    /**
     * A User instance used for testing.
     */
    private User instance;

    /**
     * Sets up the test environment.
     */
    public void setUp() {
        instance = new User();
        instance.setUserProfile(TestHelper.getUserProfile());
        instance.getUserProfile().setUser(instance);
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test for {@link User#User()} constructor.
     */
    public void testUser() {
        assertNotNull("Error instantiating the class", instance);
        assertTrue("Error instantiating the class", instance.getStatus().equals(WebConstants.UNACTIVE_STATI[1]));
        assertNotNull("Error instantiating the class", instance.getEmailAddresses());
        assertNotNull("Error instantiating the class", instance.getSecurityGroups());
    }

    /**
     * Accuracy test case for {@link User#getId()} and {@link User#setId(Long)} methods.
     */
    public void testSetGetId() {
        instance.setId(100l);
        assertEquals("Error setting, getting the value", new Long(100), instance.getId());
    }

    /**
     * Accuracy test case for {@link User#getHandle()} and {@link User#setHandle(String)} methods.
     */
    public void testSetGetHandle() {
        String handle = "Handle";
        instance.setHandle(handle);
        assertEquals("Error setting, getting the value", handle, instance.getHandle());

        assertEquals("Error setting, getting the value", handle.toLowerCase(), instance.getHandleLower());
    }

    /**
     * Accuracy test case for {@link User#setStatus(Character)} and {@link User#getStatus()} methods.
     */
    public void testSetGetStatus() {
        Character ch = new Character('w');
        instance.setStatus(ch);
        assertEquals("Error setting, getting the value", ch, instance.getStatus());
    }

    /**
     * Accuracy test case for {@link User#setActivationCode(String)} and {@link User#getActivationCode()} methods.
     */
    public void testSetGetActivationCode() {
        String code = "actinvation_code";
        instance.setActivationCode(code);
        assertEquals("Error setting, getting the value", code, instance.getActivationCode());
    }

    /**
     * Returns a set of Email objects with one of them primary.
     * @return a set of Email objects
     */
    private Set < Email > getEmailAddresses() {
        Set < Email > emails = new HashSet < Email >();

        Email mail1 = new Email();
        mail1.setAddress("address1");
        mail1.setPrimary(true);

        emails.add(mail1);

        Email mail2 = new Email();
        mail2.setAddress("address2");
        mail2.setPrimary(false);

        emails.add(mail2);
        return emails;
    }

    /**
     * Accuracy test case for {@link User#setEmailAddresses(Set)} and {@link User#getEmailAddresses()} methods.
     */
    public void testSetGetEmailAddresses() {
        instance.setEmailAddresses(getEmailAddresses());
        assertEquals("Error setting getting values", 2, instance.getEmailAddresses().size());

        instance.setEmailAddresses(null);
        assertNull("Error setting email addresses", instance.getEmailAddresses());
    }

    /**
     * Accuracy test case for {@link User#getPrimaryEmailAddress()} method.
     */
    public void testGetPrimaryEmail() {
        assertNull("Error getting primary email address", instance.getPrimaryEmailAddress());
        instance.setEmailAddresses(getEmailAddresses());
        Email mail = instance.getPrimaryEmailAddress();
        assertTrue("Error getting primary email address", "address1".equals(mail.getAddress()));
    }

    /**
     * Failure test case for {@link User#getPrimaryEmailAddress()} method.
     */
    public void testGetPrimaryEmailFail() {
        instance.setEmailAddresses(null);
        try {
            instance.getPrimaryEmailAddress();
            fail("Exception should be thrown");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link User#addEmailAddress(Email)}.
     */
    public void testAddEmailAddress() {
        instance.addEmailAddress(new Email());
        assertEquals("Error adding email address", 1, instance.getEmailAddresses().size());
    }

    /**
     * Failure test case for {@link User#addEmailAddress(Email)()} method.
     */
    public void testAddEmailFail() {
        instance.setEmailAddresses(null);
        try {
            instance.addEmailAddress(new Email());
            fail("Exception should be thrown");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link User#getHandleLower()} method. Expects a null value.
     */
    public void testGetHandleLower() {
        assertNull("Error getting handle lower case", instance.getHandleLower());
    }

    /**
     * Accuracy test case for {@link User#getRegistrationTypes()} method.
     */
    public void testGetRegistrationTypes() {
        assertEquals("Error getting registration types", 0, instance.getRegistrationTypes().size());
    }

    /**
     * Accuracy test cases for {@link User#getSecurityGroups()} and {@link User#getSecurityGroups()} methods.
     */
    public void testSetGetSecurityGroups() {
        Set < UserGroup > securityGroups = new HashSet < UserGroup >();
        securityGroups.add(new UserGroup());

        instance.setSecurityGroups(securityGroups);
        assertEquals("Error setting getting SecurityGroup", 1, instance.getSecurityGroups().size());
    }

    /**
     * Accuracy test case for {@link User#setUserSecurityKey(UserSecurityKey)} and
     * {@link User#setUserSecurityKey(UserSecurityKey)} methods.
     */
    public void testSetGetUserSecurityKey() {
        UserSecurityKey securityKey = new UserSecurityKey();
        instance.setUserSecurityKey(securityKey);
        assertEquals("Error setting getting values", securityKey, instance.getUserSecurityKey());
    }

    /**
     * Accuracy test case for {@link User#hashCode()} method.
     */
    public void testhashCode() {
        assertEquals("Error getting hashcode", 31, instance.hashCode());

        instance.setId(100l);
        assertTrue("Error getting hashcode", instance.hashCode() > 31);
    }

    /**
     * Accuracy test case for {@link User#equals(Object)} method.
     */
    public void testEquals() {
        assertTrue("Error comparing objects", instance.equals(instance));
        assertFalse("Error comparing objects", instance.equals(null));
        assertFalse("Error comparing objects", instance.equals(""));
    }

    /**
     * Accuracy test case for {@link User#setUserProfile(UserProfile) and {@link User#getUserProfile()} methods.
     */
    public void testSetGetUserProfile() {
        UserProfile userProfile = new UserProfile();
        instance.setUserProfile(userProfile);
        assertEquals("Error setting getting values", userProfile, instance.getUserProfile());
    }

    // ----------------------------------------------------------------------------------------------------------------

    /**
     * Accuracy test case for {@link User#getFirstName()} and {@link User#setFirstName(String)} methods.
     */
    public void testSetGetFirstName() {
        assertEquals("Error getting first name", TestHelper.getUserProfile().getFirstName(), instance.getFirstName());

        String firstName = "first";
        instance.setFirstName(firstName);
        assertEquals("Error setting first name", firstName, instance.getFirstName());
    }

    /**
     * Failure test case for {@link User#setFirstName(String)} method. Expects an exception as UserProfile is set to
     * null.
     */
    public void testSetFirstNameFail() {
        instance.setUserProfile(null);
        try {
            instance.setFirstName("");
            fail("Exception should be thrown");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link User#getFirstName()} method. Expects an exception as UserProfile is set to null.
     */
    public void testGetFirstNameFail() {
        instance.setUserProfile(null);
        try {
            instance.getFirstName();
            fail("Exception should be thrown");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link User#getMiddleName()} and {@link User#setMiddleName(String)} methods.
     */
    public void testSetGetMiddleName() {
        assertEquals("Error getting Middle name", TestHelper.getUserProfile().getMiddleName(),
                instance.getMiddleName());

        String middleName = "Middle";
        instance.setMiddleName(middleName);
        assertEquals("Error setting Middle name", middleName, instance.getMiddleName());
    }

    /**
     * Failure test case for {@link User#setMiddleName(String)} method. Expects an exception as UserProfile is set to
     * null.
     */
    public void testSetMiddleNameFail() {
        instance.setUserProfile(null);
        try {
            instance.setMiddleName("");
            fail("Exception should be thrown");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link User#getMiddleName()} method. Expects an exception as UserProfile is set to null.
     */
    public void testGetMiddleNameFail() {
        instance.setUserProfile(null);
        try {
            instance.getMiddleName();
            fail("Exception should be thrown");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link User#getLastName()} and {@link User#setLastName(String)} methods.
     */
    public void testSetGetLastName() {
        assertEquals("Error getting Last name", TestHelper.getUserProfile().getLastName(), instance.getLastName());

        String lastName = "Last";
        instance.setLastName(lastName);
        assertEquals("Error setting Last name", lastName, instance.getLastName());
    }

    /**
     * Failure test case for {@link User#setLastName(String)} method. Expects an exception as UserProfile is set to
     * null.
     */
    public void testSetLastNameFail() {
        instance.setUserProfile(null);
        try {
            instance.setLastName("");
            fail("Exception should be thrown");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Failure test case for {@link User#getLastName()} method. Expects an exception as UserProfile is set to null.
     */
    public void testGetLastNameFail() {
        instance.setUserProfile(null);
        try {
            instance.getLastName();
            fail("Exception should be thrown");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link User#setAddresses(Set)} and {@link User#getAddresses()} methods.
     */
    public void testSetGetAddresses() {
        Set < Address > addresses = new HashSet < Address >();
        instance.setAddresses(addresses);
        assertEquals("Error setting getting addresses", addresses, instance.getAddresses());
    }

    /**
     * Accuracy test case for {@link User#addAddress(Address)} method.
     */
    public void testAddAddress() {
        instance.addAddress(new Address());
        assertEquals("Error adding address", 1, instance.getAddresses().size());
    }

    /**
     * Failure test case for {@link User#addAddress(Address)} method.
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
     * Failure test case for {@link User#addAddress(Address)} method.
     */
    public void testAddAddressFail1() {
        try {
            instance.setUserProfile(null);
            instance.addAddress(new Address());
            fail("Exception should be thrown");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Accuracy,Failure tests for {@link User#getHomeAddress()} method.
     */
    public void testGetHomeAddress() {
        instance.setAddresses(TestHelper.getAddresses());
        assertNotNull("Error getting home address", instance.getHomeAddress());

        instance.setUserProfile(null);
        try {
            instance.getHomeAddress();
            fail("Exception should be thrown");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Test cases for {@link User#getPhoneNumbers()} and {@link User#setPhoneNumbers(Set)} methods.
     */
    public void testTestGetPhoneNumbers() {
        instance.setPhoneNumbers(new HashSet < Phone >());
        assertEquals("Error getting phone numbers", 0, instance.getPhoneNumbers().size());
    }

    /**
     * Test case for {@link User#getPrimaryPhoneNumber()} method.
     */
    public void testGetPrimaryPhoneNumber() {
        assertNull("Error getting primary phone number", instance.getPrimaryPhoneNumber());
        instance.setPhoneNumbers(TestHelper.getPhoneNumbers());
        assertNotNull("Error getting primary phone number", instance.getPrimaryPhoneNumber());
    }

    /**
     * Test case for {@link User#addPhoneNumber(Phone)} method.
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
     * Test case for {@link UserProfile#setTimeZone(TimeZone)} and {@link User#getTimeZone()} methods.
     */
    public void testSetGetTimezone() {
        TimeZone time = new TimeZone();
        instance.setTimeZone(time);
        assertEquals("Error setting getting TimeZone", time, instance.getTimeZone());
    }

    /**
     * Test cases for {@link User#getNotifications()} and {@link User#setNotifications(Set)} methods.
     */
    public void testSetGetNotifications() {
        instance.setNotifications(new HashSet < Notification >());
        assertNotNull("Error setting getting Notifications", instance.getNotifications());
        assertEquals("Error setting getting notifications", 0, instance.getNotifications().size());
    }

    /**
     * Test cases for {@link User#addNotification(Notification)} method.
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
     * Test case for {@link User#removeNotification(Notification)} method.
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
     * Test case for {@link User#setUserPreferences(Set)} and {@link User#getUserPreferences()} methods.
     */
    public void testSetGetUserPreferences() {
        instance.setUserPreferences(new HashSet < UserPreference >());
        assertNotNull("Error setting getting UserPreferences", instance.getUserPreferences());
        assertEquals("Error setting getting UserPreferences", 0, instance.getUserPreferences().size());
    }

    /**
     * Test case for {@link User#addUserPreference(UserPreference)} and {@link User#getUserPreference(Integer)} methods.
     */
    public void testAddGetUserPreference() {
        UserPreference userPref = new UserPreference();
        instance.setId(100l);
        Preference preference = new Preference();
        preference.setId(100);
        UserPreference.Identifier identifier = new UserPreference.Identifier(instance, preference);
        userPref.setId(identifier);

        instance.addUserPreference(userPref);
        assertEquals("Error adding UserPreference", 1, instance.getUserPreferences().size());

        UserPreference userPref1 = instance.getUserPreference(100);
        assertEquals("Error getting UserPreference", userPref, userPref1);
    }

    /**
     * Test cases for {@link User#getDemographicResponses()} and {@link User#setDemographicResponses(Set)} methods.
     */
    public void testSetGetDemographicResponses() {
        instance.setDemographicResponses(new HashSet < DemographicResponse >());
        assertNotNull("Error setting getting DemographicResponses", instance.getDemographicResponses());
        assertEquals("Error setting getting DemographicResponses", 0, instance.getDemographicResponses().size());
    }

    /**
     * Test cases for {@link User#addDemographicResponse(DemographicResponse)} and
     * {@link User#removeDemographicResponse(DemographicResponse)} methods.
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
        DemographicResponse.Identifier identifier = new DemographicResponse.Identifier(instance, question, answer);
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
     * Test case for {@link User#clearDemographicResponses()} method.
     */
    public void testClearDemographicResponses() {
        instance.clearDemographicResponses();
        assertEquals("Error clearing DemographicResponse", 0, instance.getDemographicResponses().size());
    }

    /**
     * Accuracy test case for {@link User#setContact(Contact) and {@link User#getContact()} methods.
     */
    public void testSetGetContact() {
        Contact contact = new Contact();
        instance.setContact(contact);
        assertEquals("Error setting getting values", contact, instance.getContact());
    }

    /**
     * Accuracy test case for {@link User#setTermes(Set)} and {@link User#getTermes()} methods.
     */
    public void testSetGetTermes() {
        Set < TermsOfUse > terms = new HashSet < TermsOfUse >();
        instance.setTerms(terms);
        assertEquals("Error setting getting Termes", terms, instance.getTerms());
    }

    /**
     * Test case for {@link User#addTerms(TermsOfUse)} and {@link User#hasTerms(Integer)} methods.
     */
    public void testAddHasTerms() {
        TermsOfUse terms = new TermsOfUse();
        terms.setId(100);
        instance.addTerms(terms);
        assertEquals("Error adding Terms", 1, instance.getTerms().size());

        assertTrue("Error checking for terms", instance.hasTerms(100));
    }

    /**
     * Test cases for {@link User#getEventRegistrations()} and {@link User#setEventRegistrations(Set)} methods.
     */
    public void testSetGetEventRegistrations() {
        instance.setEventRegistrations(new HashSet < EventRegistration >());
        assertNotNull("Error setting getting EventRegistrations", instance.getEventRegistrations());
        assertEquals("Error setting getting EventRegistrations", 0, instance.getEventRegistrations().size());
    }

    /**
     * Test case for {@link User#addEventRegistration(EventRegistration)} method.
     */
    public void testAddGetEventRegistration() {
        EventRegistration eventRegistration = new EventRegistration();
        instance.setId(100l);
        Event event = new Event();
        event.setId(100l);
        EventRegistration.Identifier identifier = new EventRegistration.Identifier(instance, event);
        eventRegistration.setId(identifier);
        instance.addEventRegistration(eventRegistration);
        assertEquals("Error adding event registration", 1, instance.getEventRegistrations().size());

        EventRegistration eveReg = instance.getEventRegistration(event);
        assertEquals("Error getting EventRegistration", eveReg, eventRegistration);
    }

    /**
     * Test case for {@link User#addEventRegistration(Event, java.util.List, Boolean, String)} method.
     */
    public void testAddEventRegistration1() {
        instance.setId(100l);
        Event event = new Event();
        event.setId(100l);
        instance.addEventRegistration(event, new ArrayList < Response >(), true, null);
        assertEquals("Error adding event registration", 1, instance.getEventRegistrations().size());
    }

    /**
     * Test case for {@link User#addEventRegistration(Event, java.util.List, Boolean)} method.
     */
    public void testAddEventRegistration2() {
        instance.setId(100l);
        Event event = new Event();
        event.setId(100l);
        instance.addEventRegistration(event, new ArrayList < Response >(), true);
        assertEquals("Error adding event registration", 1, instance.getEventRegistrations().size());
    }

    /**
     * Accuracy test case for {@link User#setSecretQuestion(SecretQuestion) and {@link User#getSecretQuestion()}
     * methods.
     */
    public void testSetGetSecretQuestion() {
        SecretQuestion secretQuestion = new SecretQuestion();
        instance.setSecretQuestion(secretQuestion);
        assertEquals("Error setting getting values", secretQuestion, instance.getSecretQuestion());
    }

    /**
     * Test cases for {@link User#getResponses()} and {@link User#setResponses(Set)} methods.
     */
    public void testSetGetResponses() {
        instance.setResponses(new HashSet < Response >());
        assertNotNull("Error setting getting Responses", instance.getResponses());
        assertEquals("Error setting getting Responses", 0, instance.getResponses().size());
    }

    /**
     * Test case for {@link User#addResponse(Response)} and {@link User#addResponses(List)} and
     * {@link User#removeResponse(Response)} methods.
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
     * Test case for {@link User#getBallots()} and {@link User#setBallots(Set)} methods.
     */
    public void testSetGetBallots() {
        instance.setBallots(new HashSet < RankBallot >());
        assertEquals("Error setting getting Ballot", 0, instance.getBallots().size());
    }

    /**
     * Test case for {@link User#addBallot(RankBallot)} method.
     */
    public void testAddBallot() {
        RankBallot ballot = new RankBallot();
        instance.addBallot(ballot);
        assertEquals("Error adding ballot", 1, instance.getBallots().size());
    }

    /**
     * Test cases for {@link User#getCompPrizes()} and {@link User#setCompPrizes(Set)} methods.
     */
    public void testSetGetUserContestPrizes() {
        instance.setCompPrizes(new HashSet < UserContestPrize >());
        assertNotNull("Error setting getting UserContestPrizes", instance.getCompPrizes());
        assertEquals("Error setting getting UserContestPrizes", 0, instance.getCompPrizes().size());
    }

    /**
     * Test case for {@link User#addCompPrize(UserContestPrize)} method.
     */
    public void testAddCompPrize() {
        instance.addCompPrize(new UserContestPrize());
        assertNotNull("Error adding UserContestPrizes", instance.getCompPrizes());
        assertEquals("Error adding UserContestPrizes", 1, instance.getCompPrizes().size());
    }

    /**
     * Test case for {@link User#setProfessor(Professor)} and {@link User#getProfessor()} methods.
     */
    public void testSetGetProfessor() {
        Professor professor = new Professor();
        instance.setProfessor(professor);
        assertEquals("Error setting getting professor", professor, instance.getProfessor());
    }

    /**
     * Test cases for {@link User#getSchools()} and {@link User#setSchools(Set)} methods.
     */
    public void testSetGetUserSchools() {
        instance.setSchools(new HashSet < UserSchool >());
        assertNotNull("Error setting getting UserSchools", instance.getSchools());
        assertEquals("Error setting getting UserSchools", 0, instance.getSchools().size());
    }

    /**
     * Test case for {@link User#getSchool(Long, Integer)} method.
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
     * Test case for {@link User#getPrimaryTeachingSchool()} method.
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
     * Test case for {@link User#getPrimaryStudentSchool()} method.
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
     * Test cases for {@link User#getCreatedSchools()} and {@link User#setCreatedSchools(Set)} methods.
     */
    public void testSetGetcreatedSchools() {
        instance.setCreatedSchools(new HashSet < School >());
        assertNotNull("Error setting getting UserSchools", instance.getCreatedSchools());
        assertEquals("Error setting getting UserSchools", 0, instance.getCreatedSchools().size());
    }

    /**
     * Test case for {@link User#addCreatedSchool(School)}.
     */
    public void testAddCreatedSchool() {
        School sch = new School();
        sch.setId(100l);
        instance.addCreatedSchool(sch);
        assertEquals("Error setting getting UserSchools", 1, instance.getCreatedSchools().size());
    }

    /**
     * Test cases for {@link User#getTransientResponses()} and {@link User#setTransientResponses(List)} methods.
     */
    public void testSetGetTransientResponses() {
        instance.setTransientResponses(new ArrayList < DemographicResponse >());
        assertNotNull("Error setting getting DemographicResponses", instance.getTransientResponses());
        assertEquals("Error setting getting DemographicResponses", 0, instance.getTransientResponses().size());
    }

    /**
     * Test case for {@link User#removeDemographicResponse(DemographicResponse)} method.
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
        DemographicResponse.Identifier identifier = new DemographicResponse.Identifier(instance, ques, ans);
        res.setId(identifier);
        res.setUser(instance);
        responses.add(res);
        instance.setTransientResponses(responses);
        instance.removeDemographicResponse(res);
        assertEquals("Error setting getting DemographicResponses", 1, instance.getTransientResponses().size());
    }

    /**
     * Test case for {@link User#getCoder()} and {@link User#setCoder(Coder)} methods.
     */
    public void testSetGetCoder() {
        Coder coder = new Coder();
        instance.setCoder(coder);
        assertEquals("Error setting getting Coder", coder, instance.getCoder());
    }

    /**
     * Test case for {@link User#isShowEarningsEnabled()} method.
     */
    public void testIsShowEarningsEnabled() {
        assertFalse("Error getting the flag", instance.isShowEarningsEnabled());
    }

    /**
     * Test case for {@link User#isMemberContactEnabled()} method.
     */
    public void testIsMemberContactEnabled() {
        assertFalse("Error getting the flag", instance.isMemberContactEnabled());
    }
}
