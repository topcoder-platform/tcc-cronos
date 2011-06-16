/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
import com.topcoder.web.common.model.Email;
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
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.TermsOfUse;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;
import com.topcoder.web.common.model.UserPreference;
import com.topcoder.web.common.model.UserPreference.Identifier;
import com.topcoder.web.common.model.UserProfile;
import com.topcoder.web.common.model.UserSchool;
import com.topcoder.web.common.model.UserSecurityKey;
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
public class UserAccuracyTest {

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
        return new JUnit4TestAdapter(UserAccuracyTest.class);
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
     * Tests {@link User#setId(Long)} and {@link User#getId()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetId() {
        Long id = 1L;
        user.setId(id);
        assertEquals("Values should be equal.", id, user.getId());
    }

    /**
     * <p>
     * Tests {@link User#setHandle(String)} and {@link User#getHandle()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetHandle() {
        String handle = "tc_admin";
        user.setHandle(handle);
        assertEquals("Values should be equal.", handle, user.getHandle());
    }

    /**
     * <p>
     * Tests {@link User#setStatus(Character)} and {@link User#getStatus()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetStatus() {
        Character status = 'U';
        user.setStatus(status);
        assertEquals("Values should be equal.", status, user.getStatus());
    }

    /**
     * <p>
     * Tests {@link User#setActivationCode(String)} and {@link User#getActivationCode()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetActivationCode() {
        String activationCode = "activation_code";
        user.setActivationCode(activationCode);
        assertEquals("Values should be equal.", activationCode, user.getActivationCode());
    }

    /**
     * <p>
     * Tests {@link User#setEmailAddresses(java.util.Set)} and {@link User#getEmailAddresses()} with valid arguments
     * passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetEmailAddresses() {
        Set < Email > emailAddresses = new HashSet < Email >();
        user.setEmailAddresses(emailAddresses);
        assertEquals("Values should be equal.", emailAddresses, user.getEmailAddresses());
    }

    /**
     * <p>
     * Tests {@link User#getPrimaryEmailAddress()} with existed primary email address.
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetPrimaryEmailAddress1() {
        Set < Email > emailAddresses = new HashSet < Email >();
        user.setEmailAddresses(emailAddresses);
        Email e = new Email();
        e.setPrimary(true);
        emailAddresses.add(e);
        assertEquals("Values should be equal.", e, user.getPrimaryEmailAddress());
    }

    /**
     * <p>
     * Tests {@link User#getPrimaryEmailAddress()} with not existed primary email address.
     * </p>
     * <p>
     * null should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetPrimaryEmailAddress2() {
        Set < Email > emailAddresses = new HashSet < Email >();
        user.setEmailAddresses(emailAddresses);
        assertNull("null should be retrieved.", user.getPrimaryEmailAddress());
    }

    /**
     * <p>
     * Tests {@link User#addEmailAddress(Email)} with valid argument passed.
     * </p>
     * <p>
     * Values should be set successfully.
     * </p>
     */
    @Test
    public void testAddEmailAddress() {
        Set < Email > emailAddresses = new HashSet < Email >();
        user.setEmailAddresses(emailAddresses);
        Email e = new Email();
        user.addEmailAddress(e);
        assertTrue("Email should be set successfully.", user.getEmailAddresses().contains(e));
    }

    /**
     * <p>
     * Tests {@link User#setSecurityGroups(Set)} and {@link User#getSecurityGroups()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetSecurityGroups() {
        Set < UserGroup > securityGroups = new HashSet < UserGroup >();
        user.setSecurityGroups(securityGroups);
        assertEquals("Values should be equal.", securityGroups, user.getSecurityGroups());
    }

    /**
     * <p>
     * Tests {@link User#getHandleLower()}.
     * </p>
     * <p>
     * Values retrieved successfully.
     * </p>
     */
    @Test
    public void testGetHandleLower() {
        String handle = "UPPER_CASE_HANDLE";
        user.setHandle(handle);
        assertEquals("Values should be equal.", handle.toLowerCase(), user.getHandleLower());
    }

    /**
     * <p>
     * Tests {@link User#getRegistrationTypes()}.
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetRegistrationTypes() {
        Set < UserGroup > securityGroups = new HashSet < UserGroup >();
        user.setSecurityGroups(securityGroups);
        UserGroup userGroup = new UserGroup();
        userGroup.setSecurityStatusId(SecurityGroup.INACTIVE);
        securityGroups.add(userGroup);
        assertEquals("Values should be equal.", 0, user.getRegistrationTypes().size());
    }

    /**
     * <p>
     * Tests {@link User#setUserSecurityKey(com.topcoder.web.common.model.UserSecurityKey)} and
     * {@link User#getUserSecurityKey()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetUserSecurityKey() {
        UserSecurityKey userSecurityKey = new UserSecurityKey();
        user.setUserSecurityKey(userSecurityKey);
        assertEquals("Values should be equal.", userSecurityKey, user.getUserSecurityKey());
    }

    /**
     * <p>
     * Tests {@link User#hashCode()} with null id.
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testHashCode1() {
        int expected = 31;
        user = new User();
        assertEquals("Values should be equal.", expected, user.hashCode());
    }

    /**
     * <p>
     * Tests {@link User#hashCode()} with not null id.
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testHashCode2() {
        Long id = 1L;
        user.setId(id);
        int expected = 31 + id.hashCode();
        assertEquals("Values should be equal.", expected, user.hashCode());
    }

    /**
     * <p>
     * Tests {@link User#equals(Object)} with same value passed.
     * </p>
     * <p>
     * Values should be compared successfully.
     * </p>
     */
    @Test
    public void testEquals1() {
        assertTrue("Values should be equal.", user.equals(user));
    }

    /**
     * <p>
     * Tests {@link User#equals(Object)} with null value passed.
     * </p>
     * <p>
     * Values should be compared successfully.
     * </p>
     */
    @Test
    public void testEquals2() {
        assertFalse("Values should not be equal.", user.equals(null));
    }

    /**
     * <p>
     * Tests {@link User#equals(Object)} with object of other type passed.
     * </p>
     * <p>
     * Values should be compared successfully.
     * </p>
     */
    @Test
    public void testEquals3() {
        assertFalse("Values should not be equal.", user.equals("user"));
    }

    /**
     * <p>
     * Tests {@link User#equals(Object)} with object with null id passed.
     * </p>
     * <p>
     * Values should be compared successfully.
     * </p>
     */
    @Test
    public void testEquals4() {
        Long id = 1L;
        user.setId(id);
        assertFalse("Values should not be equal.", user.equals(new User()));
    }

    /**
     * <p>
     * Tests {@link User#equals(Object)} with object with null id passed and user has null id.
     * </p>
     * <p>
     * Values should be compared successfully.
     * </p>
     */
    @Test
    public void testEquals5() {
        user.setId(null);
        assertTrue("Values should be equal.", user.equals(new User()));
    }

    /**
     * <p>
     * Tests {@link User#equals(Object)} with object with same id passed.
     * </p>
     * <p>
     * Values should be compared successfully.
     * </p>
     */
    @Test
    public void testEquals6() {
        Long id = 1L;
        user.setId(id);
        User user1 = new User();
        user1.setId(user.getId());
        assertTrue("Values should be equal.", user.equals(user1));
    }

    /**
     * <p>
     * Tests {@link User#setUserProfile(com.topcoder.web.common.model.UserProfile)} and {@link User#getUserProfile()}
     * with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetUserProfile() {
        user.setUserProfile(userProfile);
        assertEquals("Values should be equal.", userProfile, user.getUserProfile());
    }

    /**
     * <p>
     * Tests {@link User#setFirstName(String)} and {@link User#getFirstName()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetFirstName() {
        String firstName = "firstName";
        user.setFirstName(firstName);
        assertEquals("Values should be equal.", firstName, user.getFirstName());
    }

    /**
     * <p>
     * Tests {@link User#setMiddleName(String)} and {@link User#getMiddleName()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetMiddleName() {
        String middleName = "middleName";
        user.setMiddleName(middleName);
        assertEquals("Values should be equal.", middleName, user.getMiddleName());
    }

    /**
     * <p>
     * Tests {@link User#setLastName(String)} and {@link User#getLastName()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetLastName() {
        String lastName = "lastName";
        user.setLastName(lastName);
        assertEquals("Values should be equal.", lastName, user.getLastName());
    }

    /**
     * <p>
     * Tests {@link User#setAddresses(Set)} and {@link User#getAddresses()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddresses() {
        Set < Address > addresses = new HashSet < Address >();
        user.setAddresses(addresses);
        assertEquals("Values should be equal.", addresses, user.getAddresses());
    }

    /**
     * <p>
     * Tests {@link User#addAddress(Address)} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testAddAddress() {
        Set < Address > addresses = new HashSet < Address >();
        user.setAddresses(addresses);
        Address address = new Address();
        user.addAddress(address);
        assertTrue("Value should present.", user.getAddresses().contains(address));
    }

    /**
     * <p>
     * Tests {@link User#getHomeAddress()}.
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetHomeAddress() {
        Set < Address > addresses = new HashSet < Address >();
        user.setAddresses(addresses);
        Address address = new Address();
        address.setAddressTypeId(Address.HOME_TYPE_ID);
        user.addAddress(address);
        assertEquals("Values should be equal.", address, user.getHomeAddress());
    }

    /**
     * <p>
     * Tests {@link User#setPhoneNumbers(Set)} and {@link User#getPhoneNumbers()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetPhoneNumbers() {
        Set < Phone > phoneNumbers = new HashSet < Phone >();
        user.setPhoneNumbers(phoneNumbers);
        assertEquals("Values should be equal.", phoneNumbers, user.getPhoneNumbers());
    }

    /**
     * <p>
     * Tests {@link User#addPhoneNumber(Phone)} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testAddPhoneNumber() {
        Set < Phone > phoneNumbers = new HashSet < Phone >();
        user.setPhoneNumbers(phoneNumbers);
        Phone phone = new Phone();
        user.addPhoneNumber(phone);
        assertTrue("Values should be added.", user.getPhoneNumbers().contains(phone));
    }

    /**
     * <p>
     * Tests {@link User#getPrimaryPhoneNumber()}.
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testGetPrimaryPhoneNumber() {
        Set < Phone > phoneNumbers = new HashSet < Phone >();
        user.setPhoneNumbers(phoneNumbers);
        Phone phone = new Phone();
        phone.setPrimary(true);
        user.addPhoneNumber(phone);
        assertEquals("Values should be equal.", phone, user.getPrimaryPhoneNumber());
    }

    /**
     * <p>
     * Tests {@link User#setTimeZone(com.topcoder.web.common.model.TimeZone)} and {@link User#getTimeZone()} with valid
     * arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetTimeZone() {
        TimeZone timeZone = new TimeZone();
        user.setTimeZone(timeZone);
        assertEquals("Values should be equal.", timeZone, user.getTimeZone());
    }

    /**
     * <p>
     * Tests {@link User#setNotifications(Set)},
     * {@link User#addNotification(com.topcoder.web.common.model.Notification)},
     * {@link User#removeNotification(Notification)} and {@link User#getNotifications()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddRemoveNotifications() {
        Set < Notification > notifications = new HashSet < Notification >();
        user.setNotifications(notifications);
        Notification notification = new Notification();
        notifications.add(notification);
        assertTrue("Values should be present.", user.getNotifications().contains(notification));
        user.removeNotification(notification);
        assertFalse("Values should not be present.", user.getNotifications().contains(notification));
    }

    /**
     * <p>
     * Tests {@link User#setUserPreferences(Set)},
     * {@link User#addUserPreference(com.topcoder.web.common.model.UserPreference)}, {@link User#getUserPreferences()}
     * and {@link User#getUserPreference(Integer)} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddUserPreference() {
        Integer id = 1;
        Long userId = 2L;
        Set < UserPreference > userPreferences = new HashSet < UserPreference >();
        user.setUserPreferences(userPreferences);
        UserPreference userPreference = new UserPreference();
        Preference preference = new Preference();
        preference.setId(id);
        user.setId(userId);
        Identifier identifier = new Identifier(user, preference);
        userPreference.setId(identifier);
        user.addUserPreference(userPreference);
        assertTrue("Values should be present.", user.getUserPreferences().contains(userPreference));
        assertEquals("Values should be equal.", userPreference, user.getUserPreference(id));
    }

    /**
     * <p>
     * Tests {@link User#setDemographicResponses(Set)},
     * {@link User#addDemographicResponse(com.topcoder.web.common.model.DemographicResponse)},
     * {@link User#removeDemographicResponse(com.topcoder.web.common.model.DemographicResponse)},
     * {@link User#clearDemographicResponses()} and {@link User#getDemographicResponses()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddRemoveClearDemographicResponses() {
        Set < DemographicResponse > demographicResponses = new HashSet < DemographicResponse >();
        user.setDemographicResponses(demographicResponses);
        DemographicResponse response = new DemographicResponse();
        DemographicQuestion question = new DemographicQuestion();
        question.setId(1L);
        response.setQuestion(question);
        user.addDemographicResponse(response);
        assertTrue("Values should be present.", user.getDemographicResponses().contains(response));
        user.removeDemographicResponse(response);
        assertFalse("Values should not be present.", user.getDemographicResponses().contains(response));
        user.addDemographicResponse(response);
        user.clearDemographicResponses();
        assertFalse("Values should not be present.", user.getDemographicResponses().contains(response));
    }

    /**
     * <p>
     * Tests {@link User#setContact(com.topcoder.web.common.model.Contact)} and {@link User#getContact()} with valid
     * arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetContact() {
        Contact contact = new Contact();
        user.setContact(contact);
        assertEquals("Values should be equal.", contact, user.getContact());
    }

    /**
     * <p>
     * Tests {@link User#setTerms(Set)}, {@link User#addTerms(com.topcoder.web.common.model.TermsOfUse)},
     * {@link User#hasTerms(Integer)} and {@link User#getTerms()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddHasTerms() {
        Integer id = 1;
        Set < TermsOfUse > terms = new HashSet < TermsOfUse >();
        user.setTerms(terms);
        TermsOfUse term = new TermsOfUse();
        term.setId(id);
        user.addTerms(term);
        assertTrue("Values should be present.", user.getTerms().contains(term));
        assertTrue("Values should be present.", user.hasTerms(id));
    }

    /**
     * <p>
     * Tests {@link User#setEventRegistrations(Set)},
     * {@link User#addEventRegistration(com.topcoder.web.common.model.EventRegistration)},
     * {@link User#addEventRegistration(com.topcoder.web.common.model.Event, java.util.List, Boolean)},
     * {@link User#addEventRegistration(com.topcoder.web.common.model.Event, java.util.List, Boolean, String)},
     * {@link User#getEventRegistration(com.topcoder.web.common.model.Event)} and {@link User#getEventRegistrations()}
     * with valid arguments passed.
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
        user.setEventRegistrations(eventRegistrations);
        EventRegistration registration = new EventRegistration();
        registration.setId(identifier);
        user.addEventRegistration(registration);
        assertTrue("Values should be present.", user.getEventRegistrations().contains(registration));
        assertEquals("Values should be equal.", registration, user.getEventRegistration(event));
        userProfile.setUser(user);
        user.setId(3L);
        user.setEventRegistrations(eventRegistrations);
        user.addEventRegistration(event, new ArrayList < Response >(), true);
        assertTrue("Values should be present.", user.getEventRegistrations().contains(registration));
    }

    /**
     * <p>
     * Tests {@link User#setSecretQuestion(com.topcoder.web.common.model.SecretQuestion)} and
     * {@link User#getSecretQuestion()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetSecretQuestion() {
        SecretQuestion secretQuestion = new SecretQuestion();
        user.setSecretQuestion(secretQuestion);
        assertEquals("Values should be equal.", secretQuestion, user.getSecretQuestion());
    }

    /**
     * <p>
     * Tests {@link User#setResponses(Set)}, {@link User#addResponses(java.util.List)},
     * {@link User#addResponse(Response)}, {@link User#removeResponse(Response)} and {@link User#getResponses()} with
     * valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddRemoveResponses() {
        Set < Response > responses = new HashSet < Response >();
        Response response = new Response();
        user.setResponses(responses);
        user.addResponse(response);
        assertTrue("Values should be present.", user.getResponses().contains(response));
        user.removeResponse(response);
        assertFalse("Values should not be present.", user.getResponses().contains(response));
        user.addResponses(Arrays.asList(response));
        assertTrue("Values should be present.", user.getResponses().contains(response));
    }

    /**
     * <p>
     * Tests {@link User#setBallots(Set)}, {@link User#addBallot(com.topcoder.web.common.voting.RankBallot)} and
     * {@link User#getBallots()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddBallots() {
        Set < RankBallot > ballots = new HashSet < RankBallot >();
        RankBallot ballot = new RankBallot();
        user.setBallots(ballots);
        user.addBallot(ballot);
        assertTrue("Values should be present.", user.getBallots().contains(ballot));
    }

    /**
     * <p>
     * Tests {@link User#setCompPrizes(Set)},
     * {@link User#addCompPrize(com.topcoder.web.common.model.comp.UserContestPrize)} and {@link User#getCompPrizes()}
     * with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddCompPrizes() {
        Set < UserContestPrize > compContestPrizes = new HashSet < UserContestPrize >();
        UserContestPrize contestPrize = new UserContestPrize();
        user.setCompPrizes(compContestPrizes);
        user.addCompPrize(contestPrize);
        assertTrue("Values should be present.", user.getCompPrizes().contains(contestPrize));
    }

    /**
     * <p>
     * Tests {@link User#setProfessor(com.topcoder.web.common.model.educ.Professor)} and {@link User#getProfessor()}
     * with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetProfessor() {
        Professor professor = new Professor();
        user.setProfessor(professor);
        assertEquals("Values should be equal.", professor, user.getProfessor());
    }

    /**
     * <p>
     * Tests {@link User#setSchools(Set)}, {@link User#addSchool(com.topcoder.web.common.model.UserSchool)},
     * {@link User#getSchools()}, {@link User#getPrimaryStudentSchool()}, {@link User#getPrimaryTeachingSchool()} and
     * {@link User#getSchool(Long, Integer)} with valid arguments passed.
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
        user.setSchools(schools);
        user.addSchool(userSchool);
        assertTrue("Values should be present.", user.getSchools().contains(userSchool));
        assertEquals("Values should be equal.", userSchool, user.getSchool(schoolId, schoolAssociationTypeId));
        assertEquals("Values should be equal.", userSchool, user.getPrimaryStudentSchool());
        userSchool.setId(schoolId);
        userSchool.setPrimary(true);
        schoolAssociationTypeId = SchoolAssociationType.TEACHER;
        schoolAssociationType.setId(schoolAssociationTypeId);
        userSchool.setAssociationType(schoolAssociationType);
        user.addSchool(userSchool);
        assertEquals("Values should be equal.", userSchool, user.getPrimaryTeachingSchool());
    }

    /**
     * <p>
     * Tests {@link User#setCreatedSchools(Set)}, {@link User#addCreatedSchool(School)} and
     * {@link User#getCreatedSchools()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetAddCreatedSchools() {
        Set < School > createdSchools = new HashSet < School >();
        School school = new School();
        user.setCreatedSchools(createdSchools);
        user.addCreatedSchool(school);
        assertTrue("Values should be present.", user.getCreatedSchools().contains(school));
    }

    /**
     * <p>
     * Tests {@link User#setTransientResponses(java.util.List)},
     * {@link User#removeTransientResponse(DemographicResponse)} and {@link User#getTransientResponses()} with valid
     * arguments passed.
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
        user.setTransientResponses(transientResponses);
        assertTrue("Values should be present.", user.getTransientResponses().contains(response));
        user.removeTransientResponse(response);
        assertFalse("Values should not be present.", user.getTransientResponses().contains(response));
    }

    /**
     * <p>
     * Tests {@link User#setCoder(com.topcoder.web.common.model.Coder)} and {@link User#getCoder()} with valid arguments
     * passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testGetSetCoder() {
        Coder coder = new Coder();
        user.setCoder(coder);
        assertEquals("Values should be equal.", coder, user.getCoder());
    }

    /**
     * <p>
     * Tests {@link User#isShowEarningsEnabled()}.
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testIsShowEarningsEnabled() {
        Set < UserPreference > userPreferences = new HashSet < UserPreference >();
        userProfile.setUserPreferences(userPreferences);
        assertFalse("Values should be false.", user.isShowEarningsEnabled());
        UserPreference userPreference = new UserPreference();
        Preference preference = new Preference();
        preference.setId(Preference.SHOW_EARNINGS_PREFERENCE_ID);
        Identifier identifier = new Identifier(user, preference);
        userPreference.setId(identifier);
        userPreference.setValue("show");
        user.addUserPreference(userPreference);
        userProfile.addUserPreference(userPreference);
        assertTrue("Values should be true.", user.isShowEarningsEnabled());
    }

    /**
     * <p>
     * Tests {@link User#isMemberContactEnabled().
     * </p>
     * <p>
     * Values should be retrieved successfully.
     * </p>
     */
    @Test
    public void testIsMemberContactEnabled() {
        Set < UserPreference > userPreferences = new HashSet < UserPreference >();
        userProfile.setUserPreferences(userPreferences);
        assertFalse("Values should be false.", user.isMemberContactEnabled());
        UserPreference userPreference = new UserPreference();
        Preference preference = new Preference();
        preference.setId(Preference.MEMBER_CONTACT_PREFERENCE_ID);
        Identifier identifier = new Identifier(user, preference);
        userPreference.setId(identifier);
        userPreference.setValue("yes");
        user.addUserPreference(userPreference);
        userProfile.addUserPreference(userPreference);
        assertTrue("Values should be true.", user.isMemberContactEnabled());
    }
}
