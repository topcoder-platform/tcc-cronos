/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.ObjectTranslator;
import com.orpheus.user.persistence.OrpheusUserProfilePersistence;
import com.orpheus.user.persistence.TranslationException;
import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.UserLogicPersistenceHelper;
import com.orpheus.user.persistence.UserProfileDAO;
import com.orpheus.user.persistence.ejb.UserProfileDTO;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.DuplicatePropertyValidatorException;
import com.topcoder.user.profile.InvalidKeyException;
import com.topcoder.user.profile.InvalidValueException;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.ConfigProfileTypeFactory;
import com.topcoder.user.profile.manager.UnknownProfileTypeException;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * An implementation of the {@link ObjectTranslator} interface which converts
 * <code>UserProfile</code> objects to corresponding {@link UserProfileDTO}
 * instances, and vice-versa. The <code>UserProfile</code> objects are value
 * objects, and are usually used outside the component, while the
 * <code>UserProfileDTO</code> instances are serializable data transfer object
 * that used to transfer user profile information between EJB clients and the
 * {@link UserProfileDAO} class.
 * </p>
 * <p>
 * <code>UserProfileTranslator</code> is used by the
 * {@link OrpheusUserProfilePersistence} class.
 * </p>
 * <p>
 * <b>Configuration:</b><br>
 * This class uses the <code>ConfigProfileTypeFactory</code> to retrieve the
 * configured user profile types to add to the user profile when creating the
 * <code>UserProfile</code> object in the {@link #assembleVO(Object)} method.
 * The construction of this <code>ConfigProfileTypeFactory</code> is performed
 * using the Object Factory and is configurable. The table below lists the
 * configuration properties.
 * </p>
 * <table border="1"> <thead>
 * <tr>
 * <td><b>Property</b></td>
 * <td><b>Description</b></td>
 * <td><b>Required</b></td>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>specNamespace</td>
 * <td>The ObjectFactory configuration namespace specifying the
 * ConfigProfileTypeFactory object creation. This namespace is passed to the
 * <code>ConfigManagerSpecificationFactory</code> class</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>factoryKey</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>ConfigProfileTypeFactory</code> instance</td>
 * <td>Yes</td>
 * </tr>
 * </tbody> </table>
 * <p>
 * <b>Thread-safety:</b><br> This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfile
 * @see UserProfileDTO
 */
public class UserProfileTranslator implements ObjectTranslator {

    /**
     * <p>
     * The factory that is used by the assembleVO(Object) method to obtain
     * configured user profile types to insert into the UserProfile instance
     * that is created.
     * </p>
     * <p>
     * This field is set in the constructor, and is never changed afterwards.
     * It cannot be null.
     * </p>
     */
    private final ConfigProfileTypeFactory configProfileTypeFactory;

    /**
     * <p>
     * Creates a new <code>ObjectProfileTranslator</code> with the specified
     * configuration namespace. The namespace is used to instantiate a
     * <code>ConfigProfileTypeFactory</code> using the Object Factory and the
     * "factoryKey" property specified in the configuration. This
     * <code>ConfigProfileTypeFactory</code> is used to retrieve particular
     * configured user profile types when creating the <code>UserProfile</code>
     * in the {@link #assembleVO(Object)} method.
     * </p>
     * <p>
     * If creating the <code>ConfigProfileTypeFactory</code> fails, an
     * <code>ObjectInstantiationException</code> is thrown.
     * </p>
     *
     * @param namespace the configuration namespace from which to read the
     *        "factoryKey" property which is used to create the
     *        <code>ConfigProfileTypeFactory</code> instance with the Object
     *        Factory
     * @throws IllegalArgumentException if the namespace is <code>null</code>
     *         or a blank string
     * @throws ObjectInstantiationException if creating the
     *         <code>ConfigProfileTypeFactory</code> fails
     */
    public UserProfileTranslator(String namespace) throws ObjectInstantiationException {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(namespace, "namespace");

        // Create the ConfigProfileTypeFactory.
        configProfileTypeFactory
            = (ConfigProfileTypeFactory) UserLogicPersistenceHelper.createObject(namespace, "factoryKey",
                                                                                 ConfigProfileTypeFactory.class);
    }

    /**
     * <p>
     * Assembles and returns a <code>UserProfile</code> object when given a
     * {@link UserProfileDTO} argument. If the argument is not a
     * <code>UserProfileDTO</code> instance, an
     * <code>IllegalArgumentException</code> is thrown. If assembling the
     * <code>UserProfile</code> from the <code>UserProfileDTO</code>
     * argument fails, a <code>TranslationException</code> is thrown.
     * </p>
     * <p>
     * The <code>UserProfileDTO</code> object must contain a {@link Player},
     * {@link Admin} or {@link Sponsor} object. If it does not, a
     * <code>TranslationException</code> is thrown, because the user profile
     * DTO cannot be converted to a value object. If the user profile DTO
     * contains a <code>Player</code> or <code>Sponsor</code> object, it may
     * optionally also contain a {@link ContactInfo} object representing the
     * contact information associated with the player or sponsor.
     * </p>
     *
     * @param dataTransferObject the <code>UserProfileDTO</code> instance from
     *        which to assemble the corresponding <code>UserProfile</code>
     *        object
     * @return the <code>UserProfile</code> corresponding to the given
     *         <code>UserProfileDTO</code> object
     * @throws IllegalArgumentException if the given data transfer object is
     *         <code>null</code>, or is not of type
     *         <code>UserProfileDTO</code>
     * @throws TranslationException if assembling the <code>UserProfile</code>
     *         from the given <code>UserProfileDTO</code> fails
     * @see #assembleDTO(Object)
     */
    public Object assembleVO(Object dataTransferObject) throws TranslationException {
        if (!(dataTransferObject instanceof UserProfileDTO)) {
            throw new IllegalArgumentException("The DTO must be a non-null UserProfileDTO instance");
        }

        UserProfileDTO profileDTO = (UserProfileDTO) dataTransferObject;

        // Create the UserProfile with a temporary ID.
        UserProfile profile = null;
        try {
            profile = new UserProfile(new Long(-1));
        } catch (ConfigManagerException e) {
            throw new TranslationException("Could not create UserProfile object", e);
        }

        // Set the specific user information.
        User user = null;
        if (profileDTO.contains(UserProfileDTO.PLAYER_KEY)) {
            user = setPlayerInformation(profile, profileDTO);
        } else if (profileDTO.contains(UserProfileDTO.ADMIN_KEY)) {
            user = setAdminInformation(profile, profileDTO);
        } else if (profileDTO.contains(UserProfileDTO.SPONSOR_KEY)) {
            user = setSponsorInformation(profile, profileDTO);
        } else {
            // The user is not a player, admin or sponsor.
            throw new TranslationException("No player, admin or sponsor information was found "
                                           + "in the given UserProfileDTO");
        }

        // Add the credentials profile type.
        addProfileType(profile, UserConstants.CREDENTIALS_TYPE_NAME);

        // Set the general user information.
        profile.setIdentifier(new Long(user.getId()));
        setProfileProperty(profile, UserConstants.CREDENTIALS_HANDLE, user.getHandle());
        setProfileProperty(profile, UserConstants.CREDENTIALS_PASSWORD, user.getPassword());
        setProfileProperty(profile, UserConstants.CREDENTIALS_IS_ACTIVE, user.getActive());
        setProfileProperty(profile, BaseProfileType.EMAIL_ADDRESS, user.getEmail());

        return profile;
    }

    /**
     * <p>
     * Retrieves and returns the <code>Player</code> object from the given
     * user profile DTO. This method also adds the
     * <code>UserConstants.PLAYER_TYPE_NAME</code> profile type to the given
     * user profile, and sets the corresponding player properties. If the player
     * has contact information, the <code>ContactInfo</code> object is
     * retrieved from the user profile DTO as well, and the corresponding user
     * profile properties.
     * </p>
     *
     * @param profile the user profile in which to set the player information
     * @param profileDTO the user profile DTO from which to retrieve the
     *        <code>Player</code> object
     * @return the <code>Player</code> object
     * @throws TranslationException if the user profile DTO does not contain a
     *         <code>Player</code> object
     */
    private User setPlayerInformation(UserProfile profile, UserProfileDTO profileDTO) throws TranslationException {
        // Get the Player bean.
        Object bean = profileDTO.get(UserProfileDTO.PLAYER_KEY);
        if (!(bean instanceof Player)) {
            throw new TranslationException("The " + UserProfileDTO.PLAYER_KEY
                    + " key in the UserProfileDTO does not map to a Player object");
        }
        Player player = (Player) bean;

        // Add the player profile type.
        addProfileType(profile, UserConstants.PLAYER_TYPE_NAME);

        // Set the player information.
        setProfileProperty(profile, UserConstants.PLAYER_PAYMENT_PREF, player.getPaymentPref());

        // Set the player contact information if any.
        if (profileDTO.contains(UserProfileDTO.CONTACT_INFO_KEY)) {
            setContactInformation(profile, profileDTO);
        }

        // Set the player preferences information if any.
        if (profileDTO.contains(UserProfileDTO.PREFERENCES_INFO_KEY)) {
            setPreferencesInformation(profile, profileDTO);
        }

        return player;
    }

    /**
     * <p>
     * Retrieves and returns the <code>Admin</code> object from the given user
     * profile DTO. This method also adds the
     * <code>UserConstants.ADMIN_TYPE_NAME</code> profile type to the given
     * user profile.
     * </p>
     *
     * @param profile the user profile in which to set the admin information
     * @param profileDTO the user profile DTO from which to retrieve the
     *        <code>Admin</code> object
     * @return the <code>Admin</code> object
     * @throws TranslationException if the user profile DTO does not contain an
     *         <code>Admin</code> object
     */
    private User setAdminInformation(UserProfile profile, UserProfileDTO profileDTO) throws TranslationException {
        // Get the Admin bean.
        Object bean = profileDTO.get(UserProfileDTO.ADMIN_KEY);
        if (!(bean instanceof Admin)) {
            throw new TranslationException("The " + UserProfileDTO.ADMIN_KEY
                    + " key in the UserProfileDTO does not map to an Admin object");
        }
        Admin admin = (Admin) bean;

        // Add the admin profile type.
        addProfileType(profile, UserConstants.ADMIN_TYPE_NAME);

        return admin;
    }

    /**
     * <p>
     * Retrieves and returns the <code>Sponsor</code> object from the given
     * user profile DTO. This method also adds the
     * <code>UserConstants.SPONSOR_TYPE_NAME</code> profile type to the given
     * user profile, and sets the corresponding sponsor properties. If the
     * sponsor has contact information, the <code>ContactInfo</code> object is
     * retrieved from the user profile DTO as well, and the corresponding user
     * profile properties.
     * </p>
     *
     * @param profile the user profile in which to set the sponsor information
     * @param profileDTO the user profile DTO from which to retrieve the
     *        <code>Sponsor</code> object
     * @return the <code>Sponsor</code> object
     * @throws TranslationException if the user profile DTO does not contain a
     *         <code>Sponsor</code> object
     */
    private User setSponsorInformation(UserProfile profile, UserProfileDTO profileDTO) throws TranslationException {
        // Get the Sponsor bean.
        Object bean = profileDTO.get(UserProfileDTO.SPONSOR_KEY);
        if (!(bean instanceof Sponsor)) {
            throw new TranslationException("The " + UserProfileDTO.SPONSOR_KEY
                    + " key in the UserProfileDTO does not map to a Sponsor object");
        }
        Sponsor sponsor = (Sponsor) bean;

        // Add the sponsor profile type.
        addProfileType(profile, UserConstants.SPONSOR_TYPE_NAME);

        // Set the sponsor information.
        setProfileProperty(profile, UserConstants.SPONSOR_FAX_NUMBER, sponsor.getFax());
        setProfileProperty(profile, UserConstants.SPONSOR_PAYMENT_PREF, sponsor.getPaymentPref());
        setProfileProperty(profile, UserConstants.SPONSOR_APPROVED, sponsor.getApproved());

        // Set the sponsor contact information if any.
        if (profileDTO.contains(UserProfileDTO.CONTACT_INFO_KEY)) {
            setContactInformation(profile, profileDTO);
        }

        return sponsor;
    }

    /**
     * <p>
     * Retrieves the <code>ContactInfo</code> object from the given user
     * profile DTO, adds the <code>UserConstants.ADDRESS_TYPE_NAME</code>
     * profile type to the given user profile, and set the corresponding contact
     * information properties in the user profile.
     * </p>
     *
     * @param profile the user profile in which to set the contact information
     *        information
     * @param profileDTO the user profile DTO from which to retrieve the
     *        <code>ContactInfo</code> object
     * @throws TranslationException if the user profile DTO does not contain an
     *         <code>ContactInfo</code> object
     */
    private void setContactInformation(UserProfile profile, UserProfileDTO profileDTO) throws TranslationException {
        Object bean = profileDTO.get(UserProfileDTO.CONTACT_INFO_KEY);
        if (!(bean instanceof ContactInfo)) {
            throw new TranslationException("The " + UserProfileDTO.CONTACT_INFO_KEY
                    + " key in the UserProfileDTO does not map to a ContactInfo object");
        }

        ContactInfo contactInfo = (ContactInfo) bean;

        // Add the address profile type.
        addProfileType(profile, UserConstants.ADDRESS_TYPE_NAME);

        // Set the address information.
        setProfileProperty(profile, BaseProfileType.FIRST_NAME, contactInfo.getFirstName());
        setProfileProperty(profile, BaseProfileType.LAST_NAME, contactInfo.getLastName());
        setProfileProperty(profile, UserConstants.ADDRESS_STREET_1, contactInfo.getAddress1());
        setProfileProperty(profile, UserConstants.ADDRESS_STREET_2, contactInfo.getAddress2());
        setProfileProperty(profile, UserConstants.ADDRESS_CITY, contactInfo.getCity());
        setProfileProperty(profile, UserConstants.ADDRESS_STATE, contactInfo.getState());
        setProfileProperty(profile, UserConstants.ADDRESS_POSTAL_CODE, contactInfo.getPostalCode());
        setProfileProperty(profile, UserConstants.ADDRESS_PHONE_NUMBER, contactInfo.getTelephone());
        setProfileProperty(profile, UserConstants.ADDRESS_COUNTRY, contactInfo.getCountry());
    }

    /**
     * <p>
     * Uses the <code>ConfigProfileTypeFactory</code> to add the profile type
     * with the specified name to the given user profile.
     * </p>
     *
     * @param profile the user profile to which the profile type with the
     *        specified name should be added
     * @param profileTypeName the name of the profile type to add to the user
     *        profile
     * @throws TranslationException if adding the profile type to the user
     *         profile fails
     */
    private void addProfileType(UserProfile profile, String profileTypeName) throws TranslationException {
        try {
            profile.addProfileType(configProfileTypeFactory.getProfileType(profileTypeName));
        } catch (UnknownProfileTypeException e) {
            throw new TranslationException("Could not add the " + profileTypeName + " profile type to user profile", e);
        } catch (DuplicatePropertyValidatorException e) {
            throw new TranslationException("Could not add the " + profileTypeName + " profile type to user profile", e);
        }
    }

    /**
     * <p>
     * Sets the property with the specified name to the given value in the given
     * user profile.
     * </p>
     *
     * @param profile the user profile in which to set the property
     * @param propertyName the name of the property to set
     * @param propertyValue the value of the property to set
     * @throws TranslationException if setting the property in the user profile
     *         fails
     */
    private void setProfileProperty(UserProfile profile, String propertyName, Object propertyValue)
            throws TranslationException {
        try {
            profile.setProperty(propertyName, propertyValue);
        } catch (InvalidKeyException e) {
            throw new TranslationException("Could not set the " + propertyName + " property in user profile", e);
        } catch (InvalidValueException e) {
            throw new TranslationException("Could not set the " + propertyName + " property in user profile", e);
        }
    }

    /**
     * <p>
     * Assembles and returns a {@link UserProfileDTO} when given a
     * <code>UserProfile</code> argument. If the argument is not a
     * <code>UserProfile</code> instance, an
     * <code>IllegalArgumentException</code> is thrown. If assembling the
     * <code>UserProfileDTO</code> from the <code>UserProfile</code>
     * argument fails, a <code>TranslationException</code> is thrown.
     * </p>
     * <p>
     * The <code>UserProfile</code> object must contain a
     * <code>BaseProfileType.BASE_NAME</code> and
     * {@link UserConstants#CREDENTIALS_TYPE_NAME} profile type, as well as
     * either a {@link UserConstants#PLAYER_TYPE_NAME},
     * {@link UserConstants#ADMIN_TYPE_NAME} or
     * {@link UserConstants#SPONSOR_TYPE_NAME} profile type. If this is not the
     * case, a <code>TranslationException</code> is thrown, because the user
     * profile cannot be converted to a data transfer object. If the user
     * profile contains a <code>UserConstants.PLAYER_TYPE_NAME</code> or
     * <code>UserConstants.SPONSOR_TYPE_NAME</code> profile type, it may
     * optionally also contain an {@link UserConstants#ADDRESS_TYPE_NAME}
     * profile type representing the contact information associated with the
     * player or sponsor.
     * </p>
     *
     * @param valueObject the <code>UserProfile</code> instance from which to
     *        assemble the corresponding <code>UserProfileDTO</code> object
     * @return the <code>UserProfileDTO</code> object corresponding to the
     *         given <code>UserProfile</code> object
     * @throws IllegalArgumentException if the given value object is
     *         <code>null</code>, or is not of type <code>UserProfile</code>
     * @throws TranslationException if assembling the
     *         <code>UserProfileDTO</code> instance from the given
     *         <code>UserProfile</code> object fails
     * @see #assembleVO(Object)
     */
    public Object assembleDTO(Object valueObject) throws TranslationException {
        if (!(valueObject instanceof UserProfile)) {
            throw new IllegalArgumentException("The value object must be a non-null UserProfile instance");
        }

        UserProfile profile = (UserProfile) valueObject;

        // The user profile must contain the base and credentials profile types.
        assertContainsProfileType(profile, BaseProfileType.BASE_NAME);
        assertContainsProfileType(profile, UserConstants.CREDENTIALS_TYPE_NAME);

        UserProfileDTO profileDTO = new UserProfileDTO();

        // Set the specific user information.
        User user = null;
        if (profile.getProfileType(UserConstants.PLAYER_TYPE_NAME) != null) {
            // Player
            user = createPlayer(profile, profileDTO);
        } else if (profile.getProfileType(UserConstants.ADMIN_TYPE_NAME) != null) {
            // Admin.
            user = createAdmin(profile, profileDTO);
        } else if (profile.getProfileType(UserConstants.SPONSOR_TYPE_NAME) != null) {
            // Sponsor
            user = createSponsor(profile, profileDTO);
        } else {
            // The user is not a player, admin or sponsor.
            throw new TranslationException("The user profile does not have a " + UserConstants.PLAYER_TYPE_NAME + ", "
                    + UserConstants.ADMIN_TYPE_NAME + " or " + UserConstants.SPONSOR_TYPE_NAME + " profile type");
        }

        // Set the general user information.
        user.setHandle((String) profile.getProperty(UserConstants.CREDENTIALS_HANDLE));
        user.setEmail((String) profile.getProperty(BaseProfileType.EMAIL_ADDRESS));
        user.setPassword((String) profile.getProperty(UserConstants.CREDENTIALS_PASSWORD));
        user.setActive((String) profile.getProperty(UserConstants.CREDENTIALS_IS_ACTIVE));

        return profileDTO;
    }

    /**
     * <p>
     * Checks that the given user profile contains a user profile type with the
     * specified name. If it does not, a <code>TranslationException</code> is
     * thrown.
     * </p>
     *
     * @param profile the user profile to check
     * @param profileTypeName the name of the profile type to search for in the
     *        user profile
     * @throws TranslationException if the user profile does not contain a user
     *         profile type with the specified name
     */
    private void assertContainsProfileType(UserProfile profile, String profileTypeName) throws TranslationException {
        if (profile.getProfileType(profileTypeName) == null) {
            throw new TranslationException("No " + profileTypeName + " profile type found in user profile");
        }
    }

    /**
     * <p>
     * Creates and returns a <code>Player</code> object from the given user
     * profile. This <code>Player</code> object is also puts in the given user
     * profile DTO. If the user profile contains contact information, a
     * <code>ContactInfo</code> object is created as well, and put in the user
     * profile DTO.
     * </p>
     *
     * @param profile the user profile from which to create the
     *        <code>Player</code> object
     * @param profileDTO the user profile DTO in which the <code>Player</code>
     *        object is put
     * @return the <code>Player</code> object that was created
     * @throws TranslationException if creating the <code>Player</code> object
     *         from the given user profile fails
     */
    private User createPlayer(UserProfile profile, UserProfileDTO profileDTO) throws TranslationException {
        Player player = new Player(((Long) profile.getIdentifier()).longValue());

        // Set the player information.
        player.setPaymentPref((String) profile.getProperty(UserConstants.PLAYER_PAYMENT_PREF));

        // Add the player to the user profile DTO.
        profileDTO.put(UserProfileDTO.PLAYER_KEY, player);

        // Create the player contact informatio if the address profile type
        // exists in the user profile, and add it to the user profile DTO.
        if (profile.getProfileType(UserConstants.ADDRESS_TYPE_NAME) != null) {
            ContactInfo contactInfo = createContactInfo(player.getId(), profile);
            profileDTO.put(UserProfileDTO.CONTACT_INFO_KEY, contactInfo);
        }

        // Create the player preferences information if the preferences profile type
        // exists in the user profile, and add it to the user profile DTO.
        if (profile.getProfileType(UserConstants.PREFERENCES_TYPE_NAME) != null) {
            PlayerPreferencesInfo playerPreferencesInfo = createPreferencesInfo(player.getId(), profile);
            profileDTO.put(UserProfileDTO.PREFERENCES_INFO_KEY, playerPreferencesInfo);
        }

        return player;
    }

    /**
     * <p>
     * Creates and returns an <code>Admin</code> object from the given user
     * profile. This <code>Admin</code> object is also puts in the given user
     * profile DTO.
     * </p>
     *
     * @param profile the user profile from which to create the
     *        <code>Admin</code> object
     * @param profileDTO the user profile DTO in which the <code>Admin</code>
     *        object is put
     * @return the <code>Admin</code> object that was created
     */
    private User createAdmin(UserProfile profile, UserProfileDTO profileDTO) {
        Admin admin = new Admin(((Long) profile.getIdentifier()).longValue());
        profileDTO.put(UserProfileDTO.ADMIN_KEY, admin);
        return admin;
    }

    /**
     * <p>
     * Creates and returns a <code>Sponsor</code> object from the given user
     * profile. This <code>Sponsor</code> object is also puts in the given
     * user profile DTO. If the user profile contains contact information, a
     * <code>ContactInfo</code> object is created as well, and put in the user
     * profile DTO.
     * </p>
     *
     * @param profile the user profile from which to create the
     *        <code>Sponsor</code> object
     * @param profileDTO the user profile DTO in which the <code>Sponsor</code>
     *        object is put
     * @return the <code>Sponsor</code> object that was created
     * @throws TranslationException if creating the <code>Sponsor</code>
     *         object from the given user profile fails
     */
    private User createSponsor(UserProfile profile, UserProfileDTO profileDTO) throws TranslationException {
        Sponsor sponsor = new Sponsor(((Long) profile.getIdentifier()).longValue());

        // Set the sponsor information.
        sponsor.setFax((String) profile.getProperty(UserConstants.SPONSOR_FAX_NUMBER));
        sponsor.setPaymentPref((String) profile.getProperty(UserConstants.SPONSOR_PAYMENT_PREF));
        sponsor.setApproved((String) profile.getProperty(UserConstants.SPONSOR_APPROVED));

        // Add the sponsor to the user profile DTO.
        profileDTO.put(UserProfileDTO.SPONSOR_KEY, sponsor);

        // Create the sponsor contact information if the address profile type
        // exists in the user profile, and add it to the user profile DTO.
        if (profile.getProfileType(UserConstants.ADDRESS_TYPE_NAME) != null) {
            ContactInfo contactInfo = createContactInfo(sponsor.getId(), profile);
            profileDTO.put(UserProfileDTO.CONTACT_INFO_KEY, contactInfo);
        }

        return sponsor;
    }

    /**
     * <p>
     * Creates and returns a <code>ContactInfo</code> object with the specified
     * ID from the given user profile.
     * </p>
     *
     * @param contactInfoId the ID of the <code>ContactInfo</code> object to
     *        create
     * @param profile the user profile from which to create the
     *        <code>ContactInfo</code> object
     * @return the <code>ContactInfo</code> object that was created
     * @throws TranslationException if creating the <code>ContactInfo</code>
     *         object from the given user profile fails
     */
    private ContactInfo createContactInfo(long contactInfoId, UserProfile profile) throws TranslationException {
        ContactInfo contactInfo = new ContactInfo(contactInfoId);
        contactInfo.setFirstName((String) profile.getProperty(BaseProfileType.FIRST_NAME));
        contactInfo.setLastName((String) profile.getProperty(BaseProfileType.LAST_NAME));
        contactInfo.setAddress1((String) profile.getProperty(UserConstants.ADDRESS_STREET_1));
        contactInfo.setAddress2((String) profile.getProperty(UserConstants.ADDRESS_STREET_2));
        contactInfo.setCity((String) profile.getProperty(UserConstants.ADDRESS_CITY));
        contactInfo.setState((String) profile.getProperty(UserConstants.ADDRESS_STATE));
        contactInfo.setPostalCode((String) profile.getProperty(UserConstants.ADDRESS_POSTAL_CODE));
        contactInfo.setTelephone((String) profile.getProperty(UserConstants.ADDRESS_PHONE_NUMBER));
        contactInfo.setCountry((String) profile.getProperty(UserConstants.ADDRESS_COUNTRY));
        return contactInfo;
    }

    /**
     * <p>
     * Creates and returns a <code>PlayerPreferencesInfo</code> object with the specified
     * ID from the given user profile.
     * </p>
     *
     * @param prefsInfoId the ID of the <code>PlayerPreferencesInfo</code> object to
     *        create
     * @param profile the user profile from which to create the
     *        <code>PlayerPreferencesInfo</code> object
     * @return the <code>PlayerPreferencesInfo</code> object that was created
     * @throws TranslationException if creating the <code>PlayerPreferencesInfo</code>
     *         object from the given user profile fails
     */
    private PlayerPreferencesInfo createPreferencesInfo(long prefsInfoId, UserProfile profile) throws TranslationException {
        PlayerPreferencesInfo prefsInfoPlayer = new PlayerPreferencesInfo(prefsInfoId);
        Integer soundOption = (Integer) profile.getProperty(UserConstants.PREFS_SOUND);
        prefsInfoPlayer.setSoundOption(soundOption.intValue());
        Boolean optInFlag = (Boolean) profile.getProperty(UserConstants.PREFS_GENERAL_NOTIFICATION);
        prefsInfoPlayer.setGeneralNotificationsOptIn(optInFlag.booleanValue());
        return prefsInfoPlayer;
    }

    /**
     * <p>
     * Retrieves the <code>PlayerPreferencesInfo</code> object from the given user
     * profile DTO, adds the <code>UserConstants.PREFERENCES_TYPE_NAME</code>
     * profile type to the given user profile, and set the corresponding preferences
     * information properties in the user profile.
     * </p>
     *
     * @param profile the user profile in which to set the preferences information
     *        information
     * @param profileDTO the user profile DTO from which to retrieve the
     *        <code>PlayerPreferencesInfo</code> object
     * @throws TranslationException if the user profile DTO does not contain an
     *         <code>PlayerPreferencesInfo</code> object
     */
    private void setPreferencesInformation(UserProfile profile, UserProfileDTO profileDTO) throws TranslationException {
        Object bean = profileDTO.get(UserProfileDTO.PREFERENCES_INFO_KEY);
        if (!(bean instanceof PlayerPreferencesInfo)) {
            throw new TranslationException("The " + UserProfileDTO.PREFERENCES_INFO_KEY
                    + " key in the UserProfileDTO does not map to a PlayerPreferencesInfo object");
        }

        PlayerPreferencesInfo prefsInfoPlayer = (PlayerPreferencesInfo) bean;

        // Add the preferences profile type.
        addProfileType(profile, UserConstants.PREFERENCES_TYPE_NAME);

        // Set the preferences information.
        setProfileProperty(profile, UserConstants.PREFS_SOUND, new Integer(prefsInfoPlayer.getSoundOption()));
        setProfileProperty(profile, UserConstants.PREFS_GENERAL_NOTIFICATION,
                           Boolean.valueOf(prefsInfoPlayer.getGeneralNotificationsOptIn()));
    }
}
