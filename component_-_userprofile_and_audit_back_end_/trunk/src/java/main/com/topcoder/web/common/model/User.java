/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.WebConstants;
import com.topcoder.web.common.model.comp.UserContestPrize;
import com.topcoder.web.common.model.educ.Professor;
import com.topcoder.web.common.voting.RankBallot;

/**
 * <p>
 * This class is a container for information about a single user. It is a POJO with some additional method that simplify
 * access to the stored properties. Note that most of the methods simply delegate to the namesake method of the inner
 * UserProfile instance.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 * </p>
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class User extends Base {
    /**
     * Generated Serial version id.
     */
    private static final long serialVersionUID = 1375723336844258151L;

    /**
     * <p>
     * A variable representing a prime number 31.
     * </p>
     */
    private static final int PRIME = 31;

    /**
     * <p>
     * The ID of the user. Can be any value. Has getter and setter. Is used in hashCode() and equals().
     * </p>
     */
    private Long id;
    /**
     * <p>
     * The handle of the user. Can be any value. Has getter and setter. Is used in getHandleLower().
     * </p>
     */
    private String handle;

    /**
     * <p>
     * The status of the user. Can be any value. Has getter and setter. Is set to character representation of "unactive"
     * in constructor.
     * </p>
     */
    private Character status;
    /**
     * <p>
     * The activation code of the user. Can be any value. Has getter and setter.
     * </p>
     */
    private String activationCode;
    /**
     * <p>
     * The email addresses of the user. Can be any value, can contain any values. Has getter and setter. Is initialized
     * in the constructor with an empty set. Is used in getPrimaryEmailAddress() and addEmailAddress().
     * </p>
     */
    private Set < Email > emailAddresses;
    /**
     * <p>
     * The security groups of the user. Can be any value, can contain any values. Has getter and setter. Is initialized
     * in the constructor with an empty set. Is used in getRegistrationTypes().
     * </p>
     */
    private Set < UserGroup > securityGroups;
    /**
     * <p>
     * The user security key. Can be any value. Has getter and setter.
     * </p>
     */
    private UserSecurityKey userSecurityKey;
    /**
     * <p>
     * The value indicating whether the user agreed to site terms. This field is expected to be accessed via EL only.
     * </p>
     */
    private boolean agreedToSiteTerms;
    /**
     * <p>
     * The user profile details. Can be any value. Has getter and setter.
     * </p>
     */
    private UserProfile userProfile;

    /**
     * <p>
     * Creates an instance of User.
     * </p>
     */
    public User() {
        super();
        status = WebConstants.UNACTIVE_STATI[1];
        emailAddresses = new HashSet < Email >();
        securityGroups = new HashSet < UserGroup >();
        userProfile = new UserProfile();
        userProfile.setUser(this);
    }

    /**
     * <p>
     * Sets the ID of the user.
     * </p>
     * @param id
     *            the ID of the user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>
     * Retrieves the ID of the user.
     * </p>
     * @return the ID of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the handle of the user.
     * </p>
     * @param handle
     *            the handle of the user
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * <p>
     * Retrieves the handle of the user.
     * </p>
     * @return the handle of the user
     */
    public String getHandle() {
        return handle;
    }

    /**
     * <p>
     * Sets the status of the user.
     * </p>
     * @param status
     *            the status of the user
     */
    public void setStatus(Character status) {
        this.status = status;
    }

    /**
     * <p>
     * Retrieves the status of the user.
     * </p>
     * @return the status of the user
     */
    public Character getStatus() {
        return status;
    }

    /**
     * <p>
     * Sets the activation code of the user.
     * </p>
     * @param activationCode
     *            the activation code of the user
     */
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    /**
     * <p>
     * Retrieves the activation code of the user.
     * </p>
     * @return the activation code of the user
     */
    public String getActivationCode() {
        return activationCode;
    }

    /**
     * <p>
     * Sets the email addresses of the user.
     * </p>
     * @param emailAddresses
     *            the email addresses of the user
     */
    public void setEmailAddresses(Set < Email > emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    /**
     * <p>
     * Gets the user's primary email address.
     * </p>
     * @throws IllegalStateException
     *             if emailAddresses is null
     * @return the user's primary email address or null if they don't have one
     */
    public Email getPrimaryEmailAddress() {
        ValidationUtility.checkNotNull(emailAddresses, "emailAddresses", IllegalStateException.class);
        Email e = null;
        boolean found = false;
        for (Iterator < Email > it = emailAddresses.iterator(); it.hasNext() && !found;) {
            e = it.next();
            found = e.isPrimary();
        }
        return (found ? e : null);
    }

    /**
     * <p>
     * Adds the email address of the user.
     * </p>
     * @throws IllegalStateException
     *             if emailAddresses is null
     * @param email
     *            the email address to be added
     */
    public void addEmailAddress(Email email) {
        ValidationUtility.checkNotNull(emailAddresses, "emailAddresses", IllegalStateException.class);
        emailAddresses.add(email);
    }

    /**
     * <p>
     * Retrieves the email addresses of the user.
     * </p>
     * @return the email addresses of the user (null if not specified)
     */
    public Set < Email > getEmailAddresses() {
        return (emailAddresses == null ? null : Collections.unmodifiableSet(emailAddresses));
    }

    /**
     * <p>
     * Retrieves the handle of the user in lower case.
     * </p>
     * @return the handle of the user in lower case (null if handle is not specified)
     */
    public String getHandleLower() {
        return (handle == null ? null : handle.toLowerCase());
    }

    /**
     * <p>
     * Retrieves the registration types of the user.
     * </p>
     * @throws IllegalStateException
     *             if securityGroups is null
     * @return the retrieved registration types (not null)
     */
    public Set < RegistrationType > getRegistrationTypes() {
        ValidationUtility.checkNotNull(securityGroups, "securityGroups", IllegalStateException.class);
        Set < RegistrationType > ret = new HashSet < RegistrationType >();
        for (UserGroup group : securityGroups) {
            if (SecurityGroup.ACTIVE.equals(group.getSecurityStatusId())) {
                for (RegistrationType regType : group.getSecurityGroup().getRegistrationTypes()) {
                    ret.add(regType);
                }
            }
        }
        return Collections.unmodifiableSet(ret);
    }

    /**
     * <p>
     * Sets the security groups of the user.
     * </p>
     * @param securityGroups
     *            the security groups of the user
     */
    public void setSecurityGroups(Set < UserGroup > securityGroups) {
        this.securityGroups = securityGroups;
    }

    /**
     * <p>
     * Retrieves the security groups of the user.
     * </p>
     * @return the security groups of the user (null if not specified)
     */
    public Set < UserGroup > getSecurityGroups() {
        return securityGroups;
    }

    /**
     * <p>
     * Sets the user security key.
     * </p>
     * @param userSecurityKey
     *            the user security key
     */
    public void setUserSecurityKey(UserSecurityKey userSecurityKey) {
        this.userSecurityKey = userSecurityKey;
    }

    /**
     * <p>
     * Retrieves the user security key.
     * </p>
     * @return the user security key
     */
    public UserSecurityKey getUserSecurityKey() {
        return userSecurityKey;
    }

    /**
     * <p>
     * Calculates the hash code of this object.
     * </p>
     * @return the calculated hash code
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * <p>
     * Checks whether this user instance is equal to the specified object.
     * </p>
     * @param obj
     *            the object for comparison
     * @return true if this user instance is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || (id == null && ((User) obj).getId() != null)
                || (id != null && !id.equals(((User) obj).getId()))) {
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Sets the user profile details.
     * </p>
     * @param userProfile
     *            the user profile details
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * <p>
     * Retrieves the user profile details.
     * </p>
     * @return the user profile details
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * <p>
     * Retrieves the first name of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the first name of the user
     */
    public String getFirstName() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getFirstName();
    }

    /**
     * <p>
     * Sets the first name of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param firstName
     *            the first name of the user
     */
    public void setFirstName(String firstName) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setFirstName(firstName);
    }

    /**
     * <p>
     * Retrieves the middle name of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the middle name of the user
     */
    public String getMiddleName() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getMiddleName();
    }

    /**
     * <p>
     * Sets the middle name of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param middleName
     *            the middle name of the user
     */
    public void setMiddleName(String middleName) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setMiddleName(middleName);
    }

    /**
     * <p>
     * Retrieves the last name of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the last name of the user
     */
    public String getLastName() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getLastName();
    }

    /**
     * <p>
     * Sets the last name of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param lastName
     *            the last name of the user
     */
    public void setLastName(String lastName) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setLastName(lastName);
    }

    /**
     * <p>
     * Retrieves the addresses of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the addresses of the user (null if not specified)
     */
    public Set < Address > getAddresses() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getAddresses();
    }

    /**
     * <p>
     * Sets the addresses of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param addresses
     *            the addresses of the user
     */
    public void setAddresses(Set < Address > addresses) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setAddresses(addresses);
    }

    /**
     * <p>
     * Adds address of the user to the profile.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.addresses is null
     * @throws IllegalArgumentException
     *             if address is null
     * @param address
     *            the address of the user
     */
    public void addAddress(Address address) {
        ParameterCheckUtility.checkNotNull(address, "address");
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addAddress(address);
    }

    /**
     * <p>
     * Retrieves the home address of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.addresses is null
     * @return the retrieved home address (null if not found)
     */
    public Address getHomeAddress() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getHomeAddress();
    }

    /**
     * <p>
     * Retrieves the phone numbers of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the phone numbers of the user (null if not specified)
     */
    public Set < Phone > getPhoneNumbers() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getPhoneNumbers();
    }

    /**
     * <p>
     * Sets the phone numbers of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param phoneNumbers
     *            the phone numbers of the user
     */
    public void setPhoneNumbers(Set < Phone > phoneNumbers) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setPhoneNumbers(phoneNumbers);
    }

    /**
     * <p>
     * Get the user's primary phone number.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.phoneNumbers is null
     * @return the user's primary phone number or null if they don't have one
     */
    public Phone getPrimaryPhoneNumber() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getPrimaryPhoneNumber();
    }

    /**
     * <p>
     * Adds the phone number for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.phoneNumbers is null
     * @throws IllegalArgumentException
     *             if phone is null
     * @param phone
     *            the phone number of the user
     */
    public void addPhoneNumber(Phone phone) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addPhoneNumber(phone);
    }

    /**
     * <p>
     * Retrieves the time zone of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the time zone of the user
     */
    public TimeZone getTimeZone() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getTimeZone();
    }

    /**
     * <p>
     * Sets the time zone of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param timeZone
     *            the time zone of the user
     */
    public void setTimeZone(TimeZone timeZone) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setTimeZone(timeZone);
    }

    /**
     * <p>
     * Retrieves the notifications details.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the notifications details (null if not specified)
     */
    public Set < Notification > getNotifications() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getNotifications();
    }

    /**
     * <p>
     * Sets the notifications details.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param notifications
     *            the notifications details
     */
    public void setNotifications(Set < Notification > notifications) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setNotifications(notifications);
    }

    /**
     * <p>
     * Adds notification for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.notifications is null
     * @throws IllegalArgumentException
     *             if notification is null
     * @param notification
     *            the notification to be added
     */
    public void addNotification(Notification notification) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addNotification(notification);
    }

    /**
     * <p>
     * Removes the notification for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.notifications is null
     * @throws IllegalArgumentException
     *             if notification is null
     * @param notification
     *            the notification parameters
     */
    public void removeNotification(Notification notification) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.removeNotification(notification);
    }

    /**
     * <p>
     * Retrieves the user preferences.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the user preferences (null if not specified)
     */
    public Set < UserPreference > getUserPreferences() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getUserPreferences();
    }

    /**
     * <p>
     * Sets the user preferences.
     * </p>
     * @param userPreferences
     *            the user preferences
     */
    public void setUserPreferences(Set < UserPreference > userPreferences) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setUserPreferences(userPreferences);
    }

    /**
     * <p>
     * Adds user preference.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.userPreferences is null
     * @throws IllegalArgumentException
     *             if userPreference is null
     * @param userPreference
     *            the user preference
     */
    public void addUserPreference(UserPreference userPreference) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addUserPreference(userPreference);
    }

    /**
     * <p>
     * Retrieves the user preference with the specified ID.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.userPreferences is null
     * @param preferenceId
     *            the ID of the preference
     * @return the retrieved user preference (null if not found)
     */
    public UserPreference getUserPreference(Integer preferenceId) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getUserPreference(preferenceId);
    }

    /**
     * <p>
     * Retrieves the demographic responses.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the demographic responses (null if not specified)
     */
    public Set < DemographicResponse > getDemographicResponses() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getDemographicResponses();
    }

    /**
     * <p>
     * Sets the demographic responses.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param demographicResponses
     *            the demographic responses
     */
    public void setDemographicResponses(Set < DemographicResponse > demographicResponses) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setDemographicResponses(demographicResponses);
    }

    /**
     * <p>
     * Adds demographic response.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.demographicResponses is null
     * @throws IllegalArgumentException
     *             if response is null
     * @param response
     *            the demographic response
     */
    public void addDemographicResponse(DemographicResponse response) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addDemographicResponse(response);
    }

    /**
     * <p>
     * Removes the demographic response.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.demographicResponses is null
     * @throws IllegalArgumentException
     *             if response is null
     * @param response
     *            the demographic response
     */
    public void removeDemographicResponse(DemographicResponse response) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.removeDemographicResponse(response);
    }

    /**
     * <p>
     * Clears all demographic responses.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.demographicResponses is null
     */
    public void clearDemographicResponses() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.clearDemographicResponses();
    }

    /**
     * <p>
     * Retrieves the contact of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the contact of the user
     */
    public Contact getContact() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getContact();
    }

    /**
     * <p>
     * Sets the contact of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param contact
     *            the contact of the user
     */
    public void setContact(Contact contact) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setContact(contact);
    }

    /**
     * <p>
     * Retrieves the terms of use for this user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the terms of use for this user (null if not specified)
     */
    public Set < TermsOfUse > getTerms() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getTerms();
    }

    /**
     * <p>
     * Sets the terms of use for this user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param terms
     *            the terms of use for this user
     */
    public void setTerms(Set < TermsOfUse > terms) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setTerms(terms);
    }

    /**
     * <p>
     * Adds terms for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.terms is null
     * @throws IllegalArgumentException
     *             if termsOfUse is null
     * @param termsOfUse
     *            the terms of use to be added
     */
    public void addTerms(TermsOfUse termsOfUse) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addTerms(termsOfUse);
    }

    /**
     * <p>
     * Checks whether the user agreed to the terms of use with the specified ID.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.terms is null
     * @param termsId
     *            the ID of the terms of use
     * @return true if the user agreed to the terms of use with the specified ID, false otherwise
     */
    public boolean hasTerms(Integer termsId) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.hasTerms(termsId);
    }

    /**
     * <p>
     * Retrieves the event registrations of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the event registrations of the user (null if not specified)
     */
    public Set < EventRegistration > getEventRegistrations() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getEventRegistrations();
    }

    /**
     * <p>
     * Sets the event registrations of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param eventRegistrations
     *            the event registrations of the user
     */
    public void setEventRegistrations(Set < EventRegistration > eventRegistrations) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setEventRegistrations(eventRegistrations);
    }

    /**
     * <p>
     * Adds event registration for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.eventRegistrations is null
     * @throws IllegalArgumentException
     *             if eventRegistration is null
     * @param eventRegistration
     *            the event registration
     */
    public void addEventRegistration(EventRegistration eventRegistration) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addEventRegistration(eventRegistration);
    }

    /**
     * <p>
     * Retrieves the event registration for the specified event.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.eventRegistrations is null
     * @throws IllegalArgumentException
     *             if event is null
     * @param event
     *            the event for which event registration must be retrieved
     * @return the retrieved event registration (null if not found)
     */
    public EventRegistration getEventRegistration(Event event) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getEventRegistration(event);
    }

    /**
     * <p>
     * Adds the event registration for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.eventRegistrations is null or (responses != null and
     *             userProfile.responses == null)
     * @throws IllegalArgumentException
     *             if event is null
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
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addEventRegistration(event, responses, eligible, notes);
    }

    /**
     * <p>
     * Adds the event registration for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.eventRegistrations is null or (responses != null and
     *             userProfile.responses == null)
     * @throws IllegalArgumentException
     *             if event is null
     * @param event
     *            the event
     * @param eligible
     *            the eligibility flag
     * @param responses
     *            the user responses
     */
    public void addEventRegistration(Event event, List < Response > responses, Boolean eligible) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addEventRegistration(event, responses, eligible);
    }

    /**
     * <p>
     * Retrieves the secret question of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the secret question of the user
     */
    public SecretQuestion getSecretQuestion() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getSecretQuestion();
    }

    /**
     * <p>
     * Sets the secret question of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param secretQuestion
     *            the secret question of the user
     */
    public void setSecretQuestion(SecretQuestion secretQuestion) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setSecretQuestion(secretQuestion);
    }

    /**
     * <p>
     * Retrieves the responses of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the responses of the user (null if not specified)
     */
    public Set < Response > getResponses() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getResponses();
    }

    /**
     * <p>
     * Sets the responses of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param responses
     *            the responses of the user
     */
    public void setResponses(Set < Response > responses) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setResponses(responses);
    }

    /**
     * <p>
     * Adds the user response.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.responses is null
     * @throws IllegalArgumentException
     *             if response is null
     * @param response
     *            the response to be added
     */
    public void addResponse(Response response) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addResponse(response);
    }

    /**
     * <p>
     * Adds the given user responses.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.responses is null
     * @throws IllegalArgumentException
     *             if responses is null or contains null
     * @param responses
     *            the responses to be added
     */
    public void addResponses(List < Response > responses) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addResponses(responses);
    }

    /**
     * <p>
     * Removes the user response.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.responses is null
     * @throws IllegalArgumentException
     *             if response is null
     * @param response
     *            the response to be removed
     */
    public void removeResponse(Response response) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.removeResponse(response);
    }

    /**
     * <p>
     * Retrieves the ballots of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the ballots of the user (null if not specified)
     */
    public Set < RankBallot > getBallots() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getBallots();
    }

    /**
     * <p>
     * Sets the ballots of the user.
     * </p>
     * @param ballots
     *            the ballots of the user
     */
    public void setBallots(Set < RankBallot > ballots) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setBallots(ballots);
    }

    /**
     * <p>
     * Adds the user ballot.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.ballots is null
     * @throws IllegalArgumentException
     *             if ballot is null
     * @param ballot
     *            the rank ballot of the user
     */
    public void addBallot(RankBallot ballot) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addBallot(ballot);
    }

    /**
     * <p>
     * Retrieves the contest prizes of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the contest prizes of the user (null if not specified)
     */
    public Set < UserContestPrize > getCompPrizes() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getCompPrizes();
    }

    /**
     * <p>
     * Sets the contest prizes of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param compContestPrizes
     *            the contest prizes of the user
     */
    public void setCompPrizes(Set < UserContestPrize > compContestPrizes) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setCompPrizes(compContestPrizes);
    }

    /**
     * <p>
     * Adds the contest prize of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.compPrizes is null
     * @throws IllegalArgumentException
     *             if prize is null
     * @param prize
     *            the user contest prize to be added
     */
    public void addCompPrize(UserContestPrize prize) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addCompPrize(prize);
    }

    /**
     * <p>
     * Retrieves the professor of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the professor of the user
     */
    public Professor getProfessor() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getProfessor();
    }

    /**
     * <p>
     * Sets the professor of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param professor
     *            the professor of the user
     */
    public void setProfessor(Professor professor) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setProfessor(professor);
    }

    /**
     * <p>
     * Retrieves the schools of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the schools of the user (null if not specified)
     */
    public Set < UserSchool > getSchools() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getSchools();
    }

    /**
     * <p>
     * Sets the schools of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param schools
     *            the schools of the user
     */
    public void setSchools(Set < UserSchool > schools) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setSchools(schools);
    }

    /**
     * <p>
     * If this user is associated with the given school, return that association.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.schools is null
     * @throws IllegalArgumentException
     *             if schoolId or schoolAssociationTypeId is null
     * @param schoolId
     *            the ID of the school to check for
     * @param schoolAssociationTypeId
     *            the association that we're looking for
     * @return the association between this user and that school (null if not found)
     */
    public UserSchool getSchool(Long schoolId, Integer schoolAssociationTypeId) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getSchool(schoolId, schoolAssociationTypeId);
    }

    /**
     * <p>
     * Retrieves the primary teaching school of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.schools is null
     * @return the retrieved primary teaching school (null if not specified)
     */
    public UserSchool getPrimaryTeachingSchool() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getPrimaryTeachingSchool();
    }

    /**
     * <p>
     * Retrieves the primary student school of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.schools is null
     * @return the retrieved primary student school (null if not specified)
     */
    public UserSchool getPrimaryStudentSchool() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getPrimaryStudentSchool();
    }

    /**
     * <p>
     * Add a school for the user. If this school is the primary, then mark existing primary school as non-primary. This
     * method will also associate this user with the passed in school. If the the user already has a record for this
     * school, then update it.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.schools is null
     * @throws IllegalArgumentException
     *             if school is null
     * @param school
     *            the school that this user is being associated with
     */
    public void addSchool(UserSchool school) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addSchool(school);
    }

    /**
     * <p>
     * Retrieves the created schools of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the created schools of the user (null if not specified)
     */
    public Set < School > getCreatedSchools() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getCreatedSchools();
    }

    /**
     * <p>
     * Sets the created schools of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param createdSchools
     *            the created schools of the user
     */
    public void setCreatedSchools(Set < School > createdSchools) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setCreatedSchools(createdSchools);
    }

    /**
     * <p>
     * Adds the created school for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.createdSchools is null
     * @throws IllegalArgumentException
     *             if school is null
     * @param school
     *            the school to be added
     */
    public void addCreatedSchool(School school) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.addCreatedSchool(school);
    }

    /**
     * <p>
     * Retrieves the transient demographic responses for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the transient demographic responses for the user (null if not specified)
     */
    public List < DemographicResponse > getTransientResponses() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getTransientResponses();
    }

    /**
     * <p>
     * Sets the transient demographic responses for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param transientResponses
     *            the transient demographic responses for the user
     */
    public void setTransientResponses(List < DemographicResponse > transientResponses) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setTransientResponses(transientResponses);
    }

    /**
     * <p>
     * Removes the transient response.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.transientResponses is null
     * @throws IllegalArgumentException
     *             if response is null
     * @param response
     *            the demographic response
     */
    public void removeTransientResponse(DemographicResponse response) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.removeTransientResponse(response);
    }

    /**
     * <p>
     * Retrieves the coder data of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @return the coder data of the user
     */
    public Coder getCoder() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.getCoder();
    }

    /**
     * <p>
     * Sets the coder data of the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null
     * @param coder
     *            the coder data of the user
     */
    public void setCoder(Coder coder) {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        userProfile.setCoder(coder);
    }

    /**
     * <p>
     * Checks whether the earnings of the user can be showed publicly.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.userPreferences is null
     * @return true if the earnings of the user can be showed publicly, false otherwise
     */
    public boolean isShowEarningsEnabled() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.isShowEarningsEnabled();
    }

    /**
     * <p>
     * Checks whether the member contact is enabled for the user.
     * </p>
     * @throws IllegalStateException
     *             if userProfile is null or userProfile.userPreferences is null
     * @return true if the member contact is enabled for the user, false otherwise
     */
    public boolean isMemberContactEnabled() {
        ValidationUtility.checkNotNull(userProfile, "userProfile", IllegalStateException.class);
        return userProfile.isMemberContactEnabled();
    }
}
