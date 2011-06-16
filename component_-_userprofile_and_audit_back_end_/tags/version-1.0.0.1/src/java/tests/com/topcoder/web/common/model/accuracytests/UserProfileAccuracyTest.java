/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.topcoder.web.common.model.DemographicAnswer;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.Event;
import com.topcoder.web.common.model.EventRegistration;
import com.topcoder.web.common.model.EventType;
import com.topcoder.web.common.model.Notification;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.Preference;
import com.topcoder.web.common.model.Response;
import com.topcoder.web.common.model.School;
import com.topcoder.web.common.model.SchoolAssociationType;
import com.topcoder.web.common.model.SecretQuestion;
import com.topcoder.web.common.model.TermsOfUse;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserPreference;
import com.topcoder.web.common.model.UserPreference.Identifier;
import com.topcoder.web.common.model.UserProfile;
import com.topcoder.web.common.model.UserSchool;
import com.topcoder.web.common.model.comp.UserContestPrize;
import com.topcoder.web.common.model.educ.Professor;
import com.topcoder.web.common.voting.RankBallot;

/**
 * <p>
 * This class contains Accuracy test for User.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class UserProfileAccuracyTest {

    /**
     * <p>
     * Represents User instance for testing.
     * </p>
     */
    private User user;

    /**
     * <p>
     * Represents UserProfile instance for testing.
     * </p>
     */
    private UserProfile userProfile;

    /**
     * <p>
     * Creates TestSuite that aggregates all tests for class under test.
     * </p>
     * @return TestSuite that aggregates all tests for class under test.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserProfileAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        user = new User();
        user.setId(1L);
        userProfile = new UserProfile();
        user.setUserProfile(userProfile);
        userProfile.setUser(user);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        user = null;
    }

    /**
     * <p>
     * Tests {@link UserProfile#setId(Long)} and {@link UserProfile#getId()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetId() {
        Long id = 1L;
        userProfile.setId(id);
        assertEquals("Values should be equal.", id, userProfile.getId());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setUser(User)} and {@link UserProfile#getUser()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetUserProfile() {
        userProfile.setUser(user);
        assertEquals("Values should be equal.", user, userProfile.getUser());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setFirstName(String)} and {@link UserProfile#getFirstName()} with valid arguments
     * passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetFirstName() {
        String firstName = "firstName";
        userProfile.setFirstName(firstName);
        assertEquals("Values should be equal.", firstName, userProfile.getFirstName());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setMiddleName(String)} and {@link UserProfile#getMiddleName()} with valid arguments
     * passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetMiddleName() {
        String middleName = "middleName";
        userProfile.setMiddleName(middleName);
        assertEquals("Values should be equal.", middleName, userProfile.getMiddleName());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setLastName(String)} and {@link UserProfile#getLastName()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetLastName() {
        String lastName = "lastName";
        userProfile.setLastName(lastName);
        assertEquals("Values should be equal.", lastName, userProfile.getLastName());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setAddresses(Set)} and {@link UserProfile#getAddresses()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddresses() {
        Set < Address > addresses = new HashSet < Address >();
        userProfile.setAddresses(addresses);
        assertEquals("Values should be equal.", addresses, userProfile.getAddresses());
    }

    /**
     * <p>
     * Tests {@link UserProfile#addAddress(Address)} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testAddAddress() {
        Set < Address > addresses = new HashSet < Address >();
        userProfile.setAddresses(addresses);
        Address address = new Address();
        userProfile.addAddress(address);
        assertTrue("Value should present.", userProfile.getAddresses().contains(address));
    }

    /**
     * <p>
     * Tests {@link UserProfile#getHomeAddress()}.
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetHomeAddress() {
        Set < Address > addresses = new HashSet < Address >();
        userProfile.setAddresses(addresses);
        Address address = new Address();
        address.setAddressTypeId(Address.HOME_TYPE_ID);
        userProfile.addAddress(address);
        assertEquals("Values should be equal.", address, userProfile.getHomeAddress());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setPhoneNumbers(Set)} and {@link UserProfile#getPhoneNumbers()} with valid arguments
     * passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetPhoneNumbers() {
        Set < Phone > phoneNumbers = new HashSet < Phone >();
        userProfile.setPhoneNumbers(phoneNumbers);
        assertEquals("Values should be equal.", phoneNumbers, userProfile.getPhoneNumbers());
    }

    /**
     * <p>
     * Tests {@link UserProfile#addPhoneNumber(Phone)} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testAddPhoneNumber() {
        Set < Phone > phoneNumbers = new HashSet < Phone >();
        userProfile.setPhoneNumbers(phoneNumbers);
        Phone phone = new Phone();
        userProfile.addPhoneNumber(phone);
        assertTrue("Values should be added.", userProfile.getPhoneNumbers().contains(phone));
    }

    /**
     * <p>
     * Tests {@link UserProfile#getPrimaryPhoneNumber()}.
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetPrimaryPhoneNumber() {
        Set < Phone > phoneNumbers = new HashSet < Phone >();
        userProfile.setPhoneNumbers(phoneNumbers);
        Phone phone = new Phone();
        phone.setPrimary(true);
        userProfile.addPhoneNumber(phone);
        assertEquals("Values should be equal.", phone, userProfile.getPrimaryPhoneNumber());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setTimeZone(com.topcoder.web.common.model.TimeZone)} and
     * {@link UserProfile#getTimeZone()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetTimeZone() {
        TimeZone timeZone = new TimeZone();
        userProfile.setTimeZone(timeZone);
        assertEquals("Values should be equal.", timeZone, userProfile.getTimeZone());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setNotifications(Set)},
     * {@link UserProfile#addNotification(com.topcoder.web.common.model.Notification)},
     * {@link UserProfile#removeNotification(Notification)} and {@link UserProfile#getNotifications()} with valid
     * arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddRemoveNotifications() {
        Set < Notification > notifications = new HashSet < Notification >();
        userProfile.setNotifications(notifications);
        Notification notification = new Notification();
        notifications.add(notification);
        assertTrue("Values should be present.", userProfile.getNotifications().contains(notification));
        userProfile.removeNotification(notification);
        assertFalse("Values should not be present.", userProfile.getNotifications().contains(notification));
    }

    /**
     * <p>
     * Tests {@link UserProfile#setUserPreferences(Set)},
     * {@link UserProfile#addUserPreference(com.topcoder.web.common.model.UserPreference)},
     * {@link UserProfile#getUserPreferences()} and {@link UserProfile#getUserPreference(Integer)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddUserPreference() {
        Integer id = 1;
        Set < UserPreference > userPreferences = new HashSet < UserPreference >();
        userProfile.setUserPreferences(userPreferences);
        UserPreference userPreference = new UserPreference();
        Preference preference = new Preference();
        preference.setId(id);
        Identifier identifier = new Identifier(user, preference);
        userPreference.setId(identifier);
        userProfile.addUserPreference(userPreference);
        assertTrue("Values should be present.", userProfile.getUserPreferences().contains(userPreference));
        assertEquals("Values should be equal.", userPreference, userProfile.getUserPreference(id));
    }

    /**
     * <p>
     * Tests {@link UserProfile#setDemographicResponses(Set)},
     * {@link UserProfile#addDemographicResponse(com.topcoder.web.common.model.DemographicResponse)},
     * {@link UserProfile#removeDemographicResponse(com.topcoder.web.common.model.DemographicResponse)},
     * {@link UserProfile#clearDemographicResponses()} and {@link UserProfile#getDemographicResponses()} with valid
     * arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddRemoveClearDemographicResponses() {
        Set < DemographicResponse > demographicResponses = new HashSet < DemographicResponse >();
        userProfile.setDemographicResponses(demographicResponses);
        DemographicResponse response = new DemographicResponse();
        DemographicQuestion question = new DemographicQuestion();
        question.setId(1L);
        response.setQuestion(question);
        userProfile.addDemographicResponse(response);
        assertTrue("Values should be present.", userProfile.getDemographicResponses().contains(response));
        userProfile.removeDemographicResponse(response);
        assertFalse("Values should not be present.", userProfile.getDemographicResponses().contains(response));
        userProfile.addDemographicResponse(response);
        userProfile.clearDemographicResponses();
        assertFalse("Values should not be present.", userProfile.getDemographicResponses().contains(response));
    }

    /**
     * <p>
     * Tests {@link UserProfile#setContact(com.topcoder.web.common.model.Contact)} and {@link UserProfile#getContact()}
     * with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetContact() {
        Contact contact = new Contact();
        userProfile.setContact(contact);
        assertEquals("Values should be equal.", contact, userProfile.getContact());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setTerms(Set)}, {@link UserProfile#addTerms(com.topcoder.web.common.model.TermsOfUse)},
     * {@link UserProfile#hasTerms(Integer)} and {@link UserProfile#getTerms()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddHasTerms() {
        Integer id = 1;
        Set < TermsOfUse > terms = new HashSet < TermsOfUse >();
        userProfile.setTerms(terms);
        TermsOfUse term = new TermsOfUse();
        term.setId(id);
        userProfile.addTerms(term);
        assertTrue("Values should be present.", userProfile.getTerms().contains(term));
        assertTrue("Values should be present.", userProfile.hasTerms(id));
    }

    /**
     * <p>
     * Tests {@link UserProfile#setEventRegistrations(Set)},
     * {@link UserProfile#addEventRegistration(com.topcoder.web.common.model.EventRegistration)},
     * {@link UserProfile#addEventRegistration(com.topcoder.web.common.model.Event, java.util.List, Boolean)},
     * {@link UserProfile#addEventRegistration(com.topcoder.web.common.model.Event, java.util.List, Boolean, String)},
     * {@link UserProfile#getEventRegistration(com.topcoder.web.common.model.Event)} and
     * {@link UserProfile#getEventRegistrations()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddEventRegistration() {
        Long id = 1L;
        Long userId = 2L;
        Event event = new Event(id, new EventType());
        user.setId(userId);
        com.topcoder.web.common.model.EventRegistration.Identifier identifier = new com.topcoder.web.common.model.EventRegistration.Identifier(
                user, event);
        Set < EventRegistration > eventRegistrations = new HashSet < EventRegistration >();
        userProfile.setEventRegistrations(eventRegistrations);
        EventRegistration registration = new EventRegistration();
        registration.setId(identifier);
        userProfile.addEventRegistration(registration);
        assertTrue("Values should be present.", userProfile.getEventRegistrations().contains(registration));
        assertEquals("Values should be equal.", registration, userProfile.getEventRegistration(event));
        userProfile.setId(3L);
        userProfile.setEventRegistrations(eventRegistrations);
        userProfile.addEventRegistration(event, new ArrayList < Response >(), true);
        assertTrue("Values should be present.", userProfile.getEventRegistrations().contains(registration));
    }

    /**
     * <p>
     * Tests {@link UserProfile#setSecretQuestion(com.topcoder.web.common.model.SecretQuestion)} and
     * {@link UserProfile#getSecretQuestion()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetSecretQuestion() {
        SecretQuestion secretQuestion = new SecretQuestion();
        userProfile.setSecretQuestion(secretQuestion);
        assertEquals("Values should be equal.", secretQuestion, userProfile.getSecretQuestion());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setResponses(Set)}, {@link UserProfile#addResponses(java.util.List)},
     * {@link UserProfile#addResponse(Response)}, {@link UserProfile#removeResponse(Response)} and
     * {@link UserProfile#getResponses()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddRemoveResponses() {
        Set < Response > responses = new HashSet < Response >();
        Response response = new Response();
        userProfile.setResponses(responses);
        userProfile.addResponse(response);
        assertTrue("Values should be present.", userProfile.getResponses().contains(response));
        userProfile.removeResponse(response);
        assertFalse("Values should not be present.", userProfile.getResponses().contains(response));
        userProfile.addResponses(Arrays.asList(response));
        assertTrue("Values should be present.", userProfile.getResponses().contains(response));
    }

    /**
     * <p>
     * Tests {@link UserProfile#setBallots(Set)},
     * {@link UserProfile#addBallot(com.topcoder.web.common.voting.RankBallot)} and {@link UserProfile#getBallots()}
     * with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddBallots() {
        Set < RankBallot > ballots = new HashSet < RankBallot >();
        RankBallot ballot = new RankBallot();
        userProfile.setBallots(ballots);
        userProfile.addBallot(ballot);
        assertTrue("Values should be present.", userProfile.getBallots().contains(ballot));
    }

    /**
     * <p>
     * Tests {@link UserProfile#setCompPrizes(Set)},
     * {@link UserProfile#addCompPrize(com.topcoder.web.common.model.comp.UserContestPrize)} and
     * {@link UserProfile#getCompPrizes()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddCompPrizes() {
        Set < UserContestPrize > compContestPrizes = new HashSet < UserContestPrize >();
        UserContestPrize contestPrize = new UserContestPrize();
        userProfile.setCompPrizes(compContestPrizes);
        userProfile.addCompPrize(contestPrize);
        assertTrue("Values should be present.", userProfile.getCompPrizes().contains(contestPrize));
    }

    /**
     * <p>
     * Tests {@link UserProfile#setProfessor(com.topcoder.web.common.model.educ.Professor)} and
     * {@link UserProfile#getProfessor()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetProfessor() {
        Professor professor = new Professor();
        userProfile.setProfessor(professor);
        assertEquals("Values should be equal.", professor, userProfile.getProfessor());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setSchools(Set)},
     * {@link UserProfile#addSchool(com.topcoder.web.common.model.UserSchool)}, {@link UserProfile#getSchools()},
     * {@link UserProfile#getPrimaryStudentSchool()}, {@link UserProfile#getPrimaryTeachingSchool()} and
     * {@link UserProfile#getSchool(Long, Integer)} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddSchool() {
        Long schoolId = 1L;
        Set < UserSchool > schools = new HashSet < UserSchool >();
        UserSchool userSchool = new UserSchool();
        userSchool.setId(schoolId);
        userSchool.setPrimary(true);
        SchoolAssociationType schoolAssociationType = new SchoolAssociationType();
        Integer schoolAssociationTypeId = SchoolAssociationType.STUDENT;
        schoolAssociationType.setId(schoolAssociationTypeId);
        userSchool.setAssociationType(schoolAssociationType);
        School school = new School();
        school.setId(schoolId);
        userSchool.setSchool(school);
        userProfile.setSchools(schools);
        userProfile.addSchool(userSchool);
        assertTrue("Values should be present.", userProfile.getSchools().contains(userSchool));
        assertEquals("Values should be equal.", userSchool, userProfile.getSchool(schoolId, schoolAssociationTypeId));
        assertEquals("Values should be equal.", userSchool, userProfile.getPrimaryStudentSchool());
        userSchool.setId(schoolId);
        userSchool.setPrimary(true);
        schoolAssociationTypeId = SchoolAssociationType.TEACHER;
        schoolAssociationType.setId(schoolAssociationTypeId);
        userSchool.setAssociationType(schoolAssociationType);
        userProfile.addSchool(userSchool);
        assertEquals("Values should be equal.", userSchool, userProfile.getPrimaryTeachingSchool());
    }

    /**
     * <p>
     * Tests {@link UserProfile#setCreatedSchools(Set)}, {@link UserProfile#addCreatedSchool(School)} and
     * {@link UserProfile#getCreatedSchools()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddCreatedSchools() {
        Set < School > createdSchools = new HashSet < School >();
        School school = new School();
        userProfile.setCreatedSchools(createdSchools);
        userProfile.addCreatedSchool(school);
        assertTrue("Values should be present.", userProfile.getCreatedSchools().contains(school));
    }

    /**
     * <p>
     * Tests {@link UserProfile#setTransientResponses(java.util.List)},
     * {@link UserProfile#removeTransientResponse(DemographicResponse)} and {@link UserProfile#getTransientResponses()}
     * with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetRemoveTransientResponses() {
        List < DemographicResponse > transientResponses = new ArrayList < DemographicResponse >();
        DemographicResponse response = new DemographicResponse();
        response.setUser(user);
        DemographicQuestion question = new DemographicQuestion();
        question.setId(1L);
        response.setQuestion(question);
        DemographicAnswer answer = new DemographicAnswer();
        answer.setId(1L);
        response.setAnswer(answer);
        transientResponses.add(response);
        userProfile.setTransientResponses(transientResponses);
        assertTrue("Values should be present.", userProfile.getTransientResponses().contains(response));
        userProfile.removeTransientResponse(response);
        assertFalse("Values should not be present.", userProfile.getTransientResponses().contains(response));
    }

    /**
     * <p>
     * Tests {@link UserProfile#setCoder(com.topcoder.web.common.model.Coder)} and {@link UserProfile#getCoder()} with
     * valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetCoder() {
        Coder coder = new Coder();
        userProfile.setCoder(coder);
        assertEquals("Values should be equal.", coder, userProfile.getCoder());
    }

    /**
     * <p>
     * Tests {@link UserProfile#isShowEarningsEnabled()}.
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testIsShowEarningsEnabled() {
        Set < UserPreference > userPreferences = new HashSet < UserPreference >();
        userProfile.setUserPreferences(userPreferences);
        assertFalse("Values should be false.", userProfile.isShowEarningsEnabled());
        UserPreference userPreference = new UserPreference();
        Preference preference = new Preference();
        preference.setId(Preference.SHOW_EARNINGS_PREFERENCE_ID);
        Identifier identifier = new Identifier(user, preference);
        userPreference.setId(identifier);
        userPreference.setValue("show");
        userProfile.addUserPreference(userPreference);
        userProfile.addUserPreference(userPreference);
        assertTrue("Values should be true.", userProfile.isShowEarningsEnabled());
    }

    /**
     * <p>
     * Tests {@link UserProfile#isMemberContactEnabled().
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testIsMemberContactEnabled() {
        Set < UserPreference > userPreferences = new HashSet < UserPreference >();
        userProfile.setUserPreferences(userPreferences);
        assertFalse("Values should be false.", userProfile.isMemberContactEnabled());
        UserPreference userPreference = new UserPreference();
        Preference preference = new Preference();
        preference.setId(Preference.MEMBER_CONTACT_PREFERENCE_ID);
        Identifier identifier = new Identifier(user, preference);
        userPreference.setId(identifier);
        userPreference.setValue("yes");
        userProfile.addUserPreference(userPreference);
        userProfile.addUserPreference(userPreference);
        assertTrue("Values should be true.", userProfile.isMemberContactEnabled());
    }
}
