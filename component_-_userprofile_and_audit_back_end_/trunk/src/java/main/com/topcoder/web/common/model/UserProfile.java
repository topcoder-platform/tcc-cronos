/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.model.comp.UserContestPrize;
import com.topcoder.web.common.model.educ.Professor;
import com.topcoder.web.common.voting.RankBallot;

/**
 * <p>
 * This class is a container for user profile information. It is a POJO with some additional method that simplify access
 * to the stored properties.
 * </p>
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class UserProfile extends Base {

    /**
     * Generated Serial version id.
     */
    private static final long serialVersionUID = 4262892891974848983L;

    /**
     * The ID of the user profile. Can be any value. Has getter and setter.
     */
    private Long id;
    /**
     * The first name of the user. Can be any value. Has getter and setter.
     */
    private String firstName;
    /**
     * The middle name of the user. Can be any value. Has getter and setter.
     */
    private String middleName;
    /**
     * The last name of the user. Can be any value. Has getter and setter.
     */
    private String lastName;
    /**
     * The addresses of the user. Can be any value, can contain any values. Has getter and setter. Is initialized in the
     * constructor with empty set. Is used in addAddress() and getHomeAddress().
     */
    private Set < Address > addresses;
    /**
     * The phone numbers of the user. Can be any value, can contain any values. Has getter and setter. Is initialized in
     * the constructor with empty set. Is used in getPrimaryPhoneNumber() and addPhoneNumber().
     */
    private Set < Phone > phoneNumbers;
    /**
     * The time zone of the user. Can be any value. Has getter and setter.
     */
    private TimeZone timeZone;
    /**
     * The notifications details. Can be any value, can contain any values. Has getter and setter. Is initialized in the
     * constructor with an empty set. Is used in addNotification() and removeNotification().
     */
    private Set < Notification > notifications;
    /**
     * The user preferences. Can be any value, can contain any values. Has getter and setter. Is initialized in the
     * constructor with an empty set. Is used in addUserPreference() and getUserPreference().
     */
    private Set < UserPreference > userPreferences;
    /**
     * The demographic responses of the user. Can be any value, can contain any values. Has getter and setter. Is
     * initialized in the constructor with an empty set. Is used in addDemographicResponse(),
     * removeDemographicResponse() and clearDemographicResponses().
     */
    private Set < DemographicResponse > demographicResponses;
    /**
     * The contact of the user. Can be any value. Has getter and setter.
     */
    private Contact contact;
    /**
     * The terms of use for this user. Can be any value, can contain any values. Has getter and setter. Is initialized
     * in the constructor with an empty set. Is used in addTerms() and hasTerms().
     */
    private Set < TermsOfUse > terms;
    /**
     * The event registrations of the user. Can be any value, can contain any values. Has getter and setter. Is
     * initialized in the constructor with an empty set. Is used in addEventRegistration() and getEventRegistration().
     */
    private Set < EventRegistration > eventRegistrations;
    /**
     * The secret question of the user. Can be any value. Has getter and setter.
     */
    private SecretQuestion secretQuestion;
    /**
     * The responses of the user. Can be any value, can contain any values. Has getter and setter. Is initialized in the
     * constructor with an empty set. Is used in addResponse(), addResponses() and removeResponse().
     */
    private Set < Response > responses;
    /**
     * The ballots of the user. Can be any value, can contain any values. Has getter and setter. Is initialized in the
     * constructor with an empty set. Is used in addBallot().
     */
    private Set < RankBallot > ballots;
    /**
     * The contest prizes of the user. Can be any value, can contain any values. Has getter and setter. Is initialized
     * in the constructor with an empty set. Is used in addCompPrizes().
     */
    private Set < UserContestPrize > compPrizes;
    /**
     * The professor of the user. Can be any value. Has getter and setter.
     */
    private Professor professor;
    /**
     * The schools of the user. Can be any value, can contain any values. Has getter and setter. Is initialized in the
     * constructor with an empty set. Is used in getSchool(), getPrimarySchool() and addSchool().
     */
    private Set < UserSchool > schools;
    /**
     * The created schools of the user. Can be any value, can contain any values. Has getter and setter. Is initialized
     * in the constructor with an empty set. Is used in addCreatedSchool().
     */
    private Set < School > createdSchools;
    /**
     * The transient demographic responses for the user. Can be any value, can contain any values. Has getter and
     * setter. Is initialized in the constructor with an empty list. Is used in removeTransientResponse(). Changes: -
     * Specified generic parameter for the type.
     */
    private List < DemographicResponse > transientResponses;
    /**
     * The coder data of the user. Can be any value. Has getter and setter.
     */
    private Coder coder;

    /**
     * The owner of this profile. Can be any value. Has getter and setter.
     */
    private User user;

    /**
     * <p>
     * Creates an UserProfile instance.
     */
    public UserProfile() {
        addresses = new HashSet < Address >();
        phoneNumbers = new HashSet < Phone >();
        demographicResponses = new HashSet < DemographicResponse >();
        notifications = new TreeSet < Notification >();
        transientResponses = new ArrayList < DemographicResponse >();
        userPreferences = new HashSet < UserPreference >();
        terms = new HashSet < TermsOfUse >();
        responses = new HashSet < Response >();
        ballots = new HashSet < RankBallot >();
        eventRegistrations = new HashSet < EventRegistration >();
        compPrizes = new HashSet < UserContestPrize >();
        schools = new HashSet < UserSchool >();
        createdSchools = new HashSet < School >();
    }

    /**
     * <p>
     * Retrieves the ID of the user profile.
     * </p>
     * @return the ID of the user profile
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the ID of the user profile.
     * </p>
     * @param id
     *            the ID of the user profile
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>
     * Retrieves the first name of the user.
     * </p>
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * <p>
     * Sets the first name of the user.
     * </p>
     * @param firstName
     *            the first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * <p>
     * Retrieves the middle name of the user.
     * </p>
     * @return the middle name of the user
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * <p>
     * Sets the middle name of the user.
     * </p>
     * @param middleName
     *            the middle name of the user
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * <p>
     * Retrieves the last name of the user.
     * </p>
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * <p>
     * Sets the last name of the user.
     * </p>
     * @param lastName
     *            the last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * <p>
     * Retrieves the addresses of the user.
     * </p>
     * @return the addresses of the user (null if not specified)
     */
    public Set < Address > getAddresses() {
        return addresses;
    }

    /**
     * Sets the addresses of the user.
     * @param addresses
     *            the addresses of the user
     */
    public void setAddresses(Set < Address > addresses) {
        this.addresses = addresses;
    }

    /**
     * Adds address of the user to the profile.
     * @throws IllegalArgumentException
     *             if address is null
     * @throws IllegalStateException
     *             if addresses is null
     * @param address
     *            the address of the user
     */
    public void addAddress(Address address) {
        ParameterCheckUtility.checkNotNull(address, "address");
        ValidationUtility.checkNotNull(addresses, "addresses", IllegalStateException.class);
        addresses.add(address);
    }

    /**
     * Retrieves the home address of the user.
     * @throws IllegalStateException
     *             if addresses is null
     * @return the retrieved home address (null if not found)
     */
    public Address getHomeAddress() {
        ValidationUtility.checkNotNull(addresses, "addresses", IllegalStateException.class);
        Address a = null;
        if (getAddresses().size() > 0) {
            boolean found = false;
            for (Iterator < Address > it = getAddresses().iterator(); it.hasNext() && !found;) {
                a = it.next();
                found = Address.HOME_TYPE_ID.equals(a.getAddressTypeId());
            }
        }
        return a;
    }

    /**
     * Retrieves the phone numbers of the user.
     * @return the phone numbers of the user (null if not specified)
     */
    public Set < Phone > getPhoneNumbers() {
        return (phoneNumbers == null ? null : Collections.unmodifiableSet(phoneNumbers));
    }

    /**
     * Sets the phone numbers of the user.
     * @param phoneNumbers
     *            the phone numbers of the user
     */
    public void setPhoneNumbers(Set < Phone > phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    /**
     * Get the user's primary phone number.
     * @throws IllegalStateException
     *             if phoneNumbers is null
     * @return the user's primary phone number or null if they don't have one
     */
    public Phone getPrimaryPhoneNumber() {
        ValidationUtility.checkNotNull(phoneNumbers, "phoneNumbers", IllegalStateException.class);
        Phone p = null;
        boolean found = false;
        for (Iterator < Phone > it = getPhoneNumbers().iterator(); it.hasNext() && !found;) {
            p = it.next();
            found = p.isPrimary();
        }
        if (found) {
            return p;
        }
        return null;
    }

    /**
     * Adds the phone number for the user.
     * @throws IllegalArgumentException
     *             if phone is null
     * @throws IllegalStateException
     *             if phoneNumbers is null
     * @param phone
     *            the phone number of the user
     */
    public void addPhoneNumber(Phone phone) {
        ParameterCheckUtility.checkNotNull(phone, "phone");
        ValidationUtility.checkNotNull(phoneNumbers, "phoneNumbers", IllegalStateException.class);
        phoneNumbers.add(phone);
    }

    /**
     * Retrieves the time zone of the user.
     * @return the time zone of the user
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the time zone of the user.
     * @param timeZone
     *            the time zone of the user
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Retrieves the notifications details.
     * @return the notifications details (null if not specified)
     */
    public Set < Notification > getNotifications() {
        return (notifications == null ? null : Collections.unmodifiableSet(notifications));
    }

    /**
     * Sets the notifications details.
     * @param notifications
     *            the notifications details
     */
    public void setNotifications(Set < Notification > notifications) {
        this.notifications = notifications;
    }

    /**
     * Adds notification for the user.
     * @throws IllegalArgumentException
     *             if notification is null
     * @throws IllegalStateException
     *             if notifications is null
     * @param notification
     *            the notification to be added
     */
    public void addNotification(Notification notification) {
        ParameterCheckUtility.checkNotNull(notification, "notification");
        ValidationUtility.checkNotNull(notifications, "notifications", IllegalStateException.class);
        notifications.add(notification);
    }

    /**
     * Removes the notification for the user.
     * @throws IllegalArgumentException
     *             if notification is null
     * @throws IllegalStateException
     *             if notifications is null
     * @param notification
     *            the notification parameters
     */
    public void removeNotification(Notification notification) {
        ParameterCheckUtility.checkNotNull(notification, "notification");
        ValidationUtility.checkNotNull(notifications, "notifications", IllegalStateException.class);
        notifications.remove(notification);
    }

    /**
     * Retrieves the user preferences.
     * @return the user preferences (null if not specified)
     */
    public Set < UserPreference > getUserPreferences() {
        return (userPreferences == null ? null : Collections.unmodifiableSet(userPreferences));
    }

    /**
     * Sets the user preferences.
     * @param userPreferences
     *            the user preferences
     */
    public void setUserPreferences(Set < UserPreference > userPreferences) {
        this.userPreferences = userPreferences;
    }

    /**
     * Adds user preference.
     * @throws IllegalArgumentException
     *             if userPreference is null
     * @throws IllegalStateException
     *             if userPreferences is null
     * @param userPreference
     *            the user preference
     */
    public void addUserPreference(UserPreference userPreference) {
        ParameterCheckUtility.checkNotNull(userPreference, "userPreference");
        ValidationUtility.checkNotNull(userPreferences, "userPreferences", IllegalStateException.class);
        userPreferences.add(userPreference);
    }

    /**
     * Retrieves the user preference with the specified ID.
     * @throws IllegalStateException
     *             if userPreferences is null
     * @param preferenceId
     *            the ID of the preference
     * @return the retrieved user preference (null if not found)
     */
    public UserPreference getUserPreference(Integer preferenceId) {
        ValidationUtility.checkNotNull(userPreferences, "userPreferences", IllegalStateException.class);
        for (UserPreference up : userPreferences) {
            if (up.getId() != null && up.getId().getPreference() != null
                    && up.getId().getPreference().getId().equals(preferenceId)) {
                return up;
            }
        }
        return null;
    }

    /**
     * Retrieves the demographic responses.
     * @return the demographic responses (null if not specified)
     */
    public Set < DemographicResponse > getDemographicResponses() {
        return (demographicResponses == null ? null : Collections.unmodifiableSet(demographicResponses));
    }

    /**
     * Sets the demographic responses.
     * @param demographicResponses
     *            the demographic responses
     */
    public void setDemographicResponses(Set < DemographicResponse > demographicResponses) {
        this.demographicResponses = demographicResponses;
    }

    /**
     * Adds demographic response.
     * @throws IllegalArgumentException
     *             if response is null
     * @throws IllegalStateException
     *             if demographicResponses is null
     * @param response
     *            the demographic response
     */
    public void addDemographicResponse(DemographicResponse response) {
        ParameterCheckUtility.checkNotNull(response, "response");
        ValidationUtility.checkNotNull(demographicResponses, "demographicResponses", IllegalStateException.class);
        demographicResponses.add(response);
    }

    /**
     * Removes the demographic response.
     * @throws IllegalArgumentException
     *             if response is null
     * @throws IllegalStateException
     *             if demographicResponses is null
     * @param response
     *            the demographic response
     */
    public void removeDemographicResponse(DemographicResponse response) {
        ParameterCheckUtility.checkNotNull(response, "response");
        ValidationUtility.checkNotNull(demographicResponses, "demographicResponses", IllegalStateException.class);
        demographicResponses.remove(response);
    }

    /**
     * Clears all demographic responses.
     * @throws IllegalStateException
     *             if demographicResponses is null
     */
    public void clearDemographicResponses() {
        ValidationUtility.checkNotNull(demographicResponses, "demographicResponses", IllegalStateException.class);
        demographicResponses.clear();
    }

    /**
     * Retrieves the contact of the user.
     * @return the contact of the user
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Sets the contact of the user.
     * @param contact
     *            the contact of the user
     */
    public void setContact(Contact contact) {
        this.contact = contact;
        if (contact != null) {
            contact.setUser(user);
        }
    }

    /**
     * Retrieves the terms of use for this user.
     * @return the terms of use for this user (null if not specified)
     */
    public Set < TermsOfUse > getTerms() {
        return (terms == null ? null : Collections.unmodifiableSet(terms));
    }

    /**
     * Sets the terms of use for this user.
     * @param terms
     *            the terms of use for this user
     */
    public void setTerms(Set < TermsOfUse > terms) {
        this.terms = terms;
    }

    /**
     * Adds terms for the user.
     * @throws IllegalArgumentException
     *             if termsOfUse is null
     * @throws IllegalStateException
     *             if terms is null
     * @param termsOfUse
     *            the terms of use to be added
     */
    public void addTerms(TermsOfUse termsOfUse) {
        ParameterCheckUtility.checkNotNull(termsOfUse, "termsOfUse");
        ValidationUtility.checkNotNull(terms, "terms", IllegalStateException.class);
        terms.add(termsOfUse);
    }

    /**
     * Checks whether the user agreed to the terms of use with the specified ID.
     * @throws IllegalStateException
     *             if terms is null
     * @param termsId
     *            the ID of the terms of use
     * @return true if the user agreed to the terms of use with the specified ID, false otherwise
     */
    public boolean hasTerms(Integer termsId) {
        ValidationUtility.checkNotNull(terms, "terms", IllegalStateException.class);
        boolean found = false;
        TermsOfUse t;
        for (Iterator < TermsOfUse > it = getTerms().iterator(); it.hasNext() && !found;) {
            t = it.next();
            if (log.isDebugEnabled()) {
                log.debug("terms: " + t.getId());
            }
            found = termsId.equals(t.getId());
        }
        return found;
    }

    /**
     * Retrieves the event registrations of the user.
     * @return the event registrations of the user (null if not specified)
     */
    public Set < EventRegistration > getEventRegistrations() {
        return (eventRegistrations == null ? null : Collections.unmodifiableSet(eventRegistrations));
    }

    /**
     * Sets the event registrations of the user.
     * @param eventRegistrations
     *            the event registrations of the user
     */
    public void setEventRegistrations(Set < EventRegistration > eventRegistrations) {
        this.eventRegistrations = eventRegistrations;
    }

    /**
     * Adds event registration for the user.
     * @throws IllegalArgumentException
     *             if eventRegistration is null
     * @throws IllegalStateException
     *             if eventRegistrations is null
     * @param eventRegistration
     *            the event registration
     */
    public void addEventRegistration(EventRegistration eventRegistration) {
        ParameterCheckUtility.checkNotNull(eventRegistration, "eventRegistration");
        ValidationUtility.checkNotNull(eventRegistrations, "eventRegistrations", IllegalStateException.class);
        eventRegistrations.add(eventRegistration);
    }

    /**
     * Retrieves the event registration for the specified event.
     * @throws IllegalArgumentException
     *             if event is null
     * @throws IllegalStateException
     *             if eventRegistrations is null
     * @param event
     *            the event for which event registration must be retrieved
     * @return the retrieved event registration (null if not found)
     */
    public EventRegistration getEventRegistration(Event event) {
        ParameterCheckUtility.checkNotNull(event, "event");
        ValidationUtility.checkNotNull(eventRegistrations, "eventRegistrations", IllegalStateException.class);
        for (EventRegistration curr : eventRegistrations) {
            if (curr.getId() != null && curr.getId().getEvent() != null
                    && curr.getId().getEvent().getId() == event.getId()) {
                return curr;
            }
        }
        return null;
    }

    /**
     * Adds the event registration for the user.
     * @throws IllegalArgumentException
     *             if event is null
     * @throws IllegalStateException
     *             if eventRegistrations is null or (responses != null and this.responses == null)
     * @param event
     *            the event
     * @param notes
     *            the event notes
     * @param eligible
     *            the eligibility flag
     * @param responses
     *            the user responses
     */
    public void addEventRegistration(Event event, List < Response > responses, Boolean eligible, String notes) {
        ParameterCheckUtility.checkNotNull(event, "event");
        ValidationUtility.checkNotNull(eventRegistrations, "eventRegistrations", IllegalStateException.class);
        if (responses != null && this.responses == null) {
            throw new IllegalStateException("cannot add Response objects to null set");
        }
        EventRegistration er = new EventRegistration();
        er.getId().setUser(user);
        er.getId().setEvent(event);
        er.setEligible(eligible);
        if (notes != null) {
            er.setNotes(notes);
        }

        addEventRegistration(er);

        if (event.getTerms() != null) {
            addTerms(event.getTerms());
        }

        if (responses != null) {
            addResponses(responses);
        }
    }

    /**
     * Adds the event registration for the user.
     * @throws IllegalArgumentException
     *             if event is null
     * @throws IllegalStateException
     *             if eventRegistrations is null or (responses != null and this.responses == null)
     * @param event
     *            the event
     * @param eligible
     *            the eligibility flag
     * @param responses
     *            the user responses
     */
    public void addEventRegistration(Event event, List < Response > responses, Boolean eligible) {
        addEventRegistration(event, responses, eligible, null);
    }

    /**
     * Retrieves the secret question of the user.
     * @return the secret question of the user
     */
    public SecretQuestion getSecretQuestion() {
        return secretQuestion;
    }

    /**
     * Sets the secret question of the user.
     * @param secretQuestion
     *            the secret question of the user
     */
    public void setSecretQuestion(SecretQuestion secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    /**
     * Adds the user response.
     * @throws IllegalArgumentException
     *             if response is null
     * @throws IllegalStateException
     *             if responses is null
     * @param response
     *            the response to be added
     */
    public void addResponse(Response response) {
        ParameterCheckUtility.checkNotNull(response, "response");
        ValidationUtility.checkNotNull(responses, "responses", IllegalStateException.class);
        responses.add(response);
    }

    /**
     * Adds the given user responses.
     * @throws IllegalArgumentException
     *             if responses is null or contains null
     * @throws IllegalStateException
     *             if this.responses is null
     * @param responses
     *            the responses to be added
     */
    public void addResponses(List < Response > responses) {
        ParameterCheckUtility.checkNotNull(responses, "responses");
        ParameterCheckUtility.checkNotNullElements(responses, "responses elements");
        ValidationUtility.checkNotNull(this.responses, "responses", IllegalStateException.class);
        this.responses.addAll(responses);
    }

    /**
     * Retrieves the responses of the user.
     * @return the responses of the user (null if not specified)
     */
    public Set < Response > getResponses() {
        return (responses == null ? null : Collections.unmodifiableSet(responses));
    }

    /**
     * Sets the responses of the user.
     * @param responses
     *            the responses of the user
     */
    public void setResponses(Set < Response > responses) {
        this.responses = responses;
    }

    /**
     * Removes the user response.
     * @throws IllegalArgumentException
     *             if response is null
     * @throws IllegalStateException
     *             if responses is null
     * @param response
     *            the response to be removed
     */
    public void removeResponse(Response response) {
        ParameterCheckUtility.checkNotNull(response, "response");
        ValidationUtility.checkNotNull(responses, "responses", IllegalStateException.class);
        responses.remove(response);
    }

    /**
     * Retrieves the ballots of the user.
     * @return the ballots of the user (null if not specified)
     */
    public Set < RankBallot > getBallots() {
        return (ballots == null ? null : Collections.unmodifiableSet(ballots));
    }

    /**
     * Sets the ballots of the user.
     * @param ballots
     *            the ballots of the user
     */
    public void setBallots(Set < RankBallot > ballots) {
        this.ballots = ballots;
    }

    /**
     * Adds the user ballot.
     * @throws IllegalArgumentException
     *             if ballot is null
     * @throws IllegalStateException
     *             if ballots is null
     * @param ballot
     *            the rank ballot of the user
     */
    public void addBallot(RankBallot ballot) {
        ParameterCheckUtility.checkNotNull(ballot, "ballot");
        ValidationUtility.checkNotNull(ballots, "ballots", IllegalStateException.class);
        ballots.add(ballot);
    }

    /**
     * Retrieves the contest prizes of the user.
     * @return the contest prizes of the user (null if not specified)
     */
    public Set < UserContestPrize > getCompPrizes() {
        return (compPrizes == null ? null : Collections.unmodifiableSet(compPrizes));
    }

    /**
     * Sets the contest prizes of the user.
     * @param compContestPrizes
     *            the contest prizes of the user
     */
    public void setCompPrizes(Set < UserContestPrize > compContestPrizes) {
        this.compPrizes = compContestPrizes;
    }

    /**
     * Adds the contest prize of the user.
     * @throws IllegalArgumentException
     *             if prize is null
     * @throws IllegalStateException
     *             if compPrizes is null
     * @param prize
     *            the user contest prize to be added
     */
    public void addCompPrize(UserContestPrize prize) {
        ParameterCheckUtility.checkNotNull(prize, "prize");
        ValidationUtility.checkNotNull(compPrizes, "compPrizes", IllegalStateException.class);
        compPrizes.add(prize);
    }

    /**
     * Retrieves the professor of the user.
     * @return the professor of the user
     */
    public Professor getProfessor() {
        return professor;
    }

    /**
     * Sets the professor of the user.
     * @param professor
     *            the professor of the user
     */
    public void setProfessor(Professor professor) {
        this.professor = professor;
        if (professor != null) {
            professor.setUser(user);
        }
    }

    /**
     * Retrieves the schools of the user.
     * @return the schools of the user (null if not specified)
     */
    public Set < UserSchool > getSchools() {
        return schools;
    }

    /**
     * Sets the schools of the user.
     * @param schools
     *            the schools of the user
     */
    public void setSchools(Set < UserSchool > schools) {
        this.schools = schools;
    }

    /**
     * If this user is associated with the given school, return that association.
     * @throws IllegalArgumentException
     *             if schoolId or schoolAssociationTypeId is null
     * @throws IllegalStateException
     *             if schools is null
     * @param schoolId
     *            the ID of the school to check for
     * @param schoolAssociationTypeId
     *            the association that we're looking for
     * @return the association between this user and that school (null if not found)
     */
    public UserSchool getSchool(Long schoolId, Integer schoolAssociationTypeId) {
        ParameterCheckUtility.checkNotNull(schoolId, "schoolId");
        ParameterCheckUtility.checkNotNull(schoolAssociationTypeId, "schoolAssociationTypeId");
        ValidationUtility.checkNotNull(schools, "schools", IllegalStateException.class);
        for (UserSchool school : schools) {
            if (school.getSchool() != null && school.getSchool().getId() != null
                    && school.getSchool().getId().equals(schoolId) && school.getAssociationType() != null
                    && school.getAssociationType().getId().equals(schoolAssociationTypeId)) {
                return school;
            }
        }
        return null;
    }

    /**
     * Retrieves the primary school of the user.
     * @throws IllegalStateException
     *             if schools is null
     * @param schoolAssociationTypeId
     *            the ID of the school association type
     * @return the retrieved primary school (null if not found)
     */
    private UserSchool getPrimarySchool(Integer schoolAssociationTypeId) {
        ValidationUtility.checkNotNull(schools, "schools", IllegalStateException.class);
        for (UserSchool school : schools) {
            if (school.isPrimary() && school.getAssociationType() != null
                    && school.getAssociationType().getId() != null
                    && school.getAssociationType().getId().equals(schoolAssociationTypeId)) {
                return school;
            }
        }
        return null;
    }

    /**
     * Retrieves the primary teaching school of the user.
     * @throws IllegalStateException
     *             if schools is null
     * @return the retrieved primary teaching school (null if not specified)
     */
    public UserSchool getPrimaryTeachingSchool() {
        return getPrimarySchool(SchoolAssociationType.TEACHER);
    }

    /**
     * Retrieves the primary student school of the user.
     * @throws IllegalStateException
     *             if schools is null
     * @return the retrieved primary student school (null if not specified)
     */
    public UserSchool getPrimaryStudentSchool() {
        return getPrimarySchool(SchoolAssociationType.STUDENT);
    }

    /**
     * Add a school for the user. If this school is the primary, then mark existing primary school as non-primary. This
     * method will also associate this user with the passed in school. If the the user already has a record for this
     * school, then update it.
     * @throws IllegalArgumentException
     *             if school is null
     * @throws IllegalStateException
     *             if schools is null
     * @param school
     *            the school that this user is being associated with
     */
    public void addSchool(UserSchool school) {
        ParameterCheckUtility.checkNotNull(school, "school");
        ValidationUtility.checkNotNull(schools, "schools", IllegalStateException.class);
        if (school.isPrimary()) {
            for (UserSchool s : schools) {
                if (s.isPrimary() && s.getAssociationType() != null
                        && s.getAssociationType().equals(school.getAssociationType()) && school.getSchool() != null
                        && !school.getSchool().equals(s.getSchool())) {
                    s.setPrimary(false);
                }

            }
        }
        school.setUser(user);
        schools.add(school);
    }

    /**
     * Retrieves the created schools of the user.
     * @return the created schools of the user (null if not specified)
     */
    public Set < School > getCreatedSchools() {
        return createdSchools;
    }

    /**
     * Sets the created schools of the user.
     * @param createdSchools
     *            the created schools of the user
     */
    public void setCreatedSchools(Set < School > createdSchools) {
        this.createdSchools = createdSchools;
    }

    /**
     * Adds the created school for the user.
     * @throws IllegalArgumentException
     *             if school is null
     * @throws IllegalStateException
     *             if createdSchools is null
     * @param school
     *            the school to be added
     */
    public void addCreatedSchool(School school) {
        ParameterCheckUtility.checkNotNull(school, "school");
        ValidationUtility.checkNotNull(createdSchools, "createdSchools", IllegalStateException.class);
        createdSchools.add(school);
        school.setUser(user);
    }

    /**
     * Retrieves the transient demographic responses for the user.
     * @return the transient demographic responses for the user (null if not specified)
     */
    public List < DemographicResponse > getTransientResponses() {
        return transientResponses;
    }

    /**
     * Sets the transient demographic responses for the user.
     * @param transientResponses
     *            the transient demographic responses for the user
     */
    public void setTransientResponses(List < DemographicResponse > transientResponses) {
        this.transientResponses = transientResponses;
    }

    /**
     * Removes the transient response.
     * @throws IllegalArgumentException
     *             if response is null
     * @throws IllegalStateException
     *             if transientResponses is null
     * @param response
     *            the demographic response
     */
    public void removeTransientResponse(DemographicResponse response) {
        ParameterCheckUtility.checkNotNull(response, "response");
        ValidationUtility.checkNotNull(transientResponses, "transientResponses", IllegalStateException.class);
        transientResponses.remove(response);
    }

    /**
     * Retrieves the coder data of the user.
     * @return the coder data of the user
     */
    public Coder getCoder() {
        return coder;
    }

    /**
     * Sets the coder data of the user.
     * @param coder
     *            the coder data of the user
     */
    public void setCoder(Coder coder) {
        this.coder = coder;
        coder.setUser(user);
    }

    /**
     * Checks whether the earnings of the user can be showed publicly.
     * @throws IllegalStateException
     *             if userPreferences is null
     * @return true if the earnings of the user can be showed publicly, false otherwise
     */
    public boolean isShowEarningsEnabled() {
        UserPreference up = getUserPreference(Preference.SHOW_EARNINGS_PREFERENCE_ID);
        return (up != null && "show".equals(up.getValue()));
    }

    /**
     * Checks whether the member contact is enabled for the user.
     * @throws IllegalStateException
     *             if userPreferences is null
     * @return true if the member contact is enabled for the user, false otherwise
     */
    public boolean isMemberContactEnabled() {
        UserPreference up = getUserPreference(Preference.MEMBER_CONTACT_PREFERENCE_ID);
        return (up != null && "yes".equals(up.getValue()));
    }

    /**
     * Sets the User instance.
     * @param user
     *            the user to be set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the User instance.
     * @return the User instance
     */
    public User getUser() {
        return user;
    }

}
