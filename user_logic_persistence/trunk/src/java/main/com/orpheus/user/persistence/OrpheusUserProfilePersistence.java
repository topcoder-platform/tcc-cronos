/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.util.Map;

import com.orpheus.user.persistence.ejb.UserProfileDTO;
import com.orpheus.user.persistence.impl.UserProfileTranslator;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.DuplicateProfileException;
import com.topcoder.user.profile.manager.ProfileManagerConfigurationException;
import com.topcoder.user.profile.manager.UnknownProfileException;
import com.topcoder.user.profile.manager.UserProfileManagerException;
import com.topcoder.user.profile.manager.persistence.UserProfilePersistence;
import com.topcoder.util.cache.Cache;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * An implementation of the <code>UserProfilePersistence</code> interface
 * which uses an EJB session bean to provide persistent storage of user profile
 * information. It supports all operations defined in the interface, except the
 * {@link #commit()} operation, as all changes are sent to the persistent store
 * immediately.
 * </p>
 * <p>
 * While this class performs the majority of the work required to persist user
 * profiles, it does not actually interact with the EJB session bean directly.
 * Rather, the calls to the session bean are deferred by invoking the
 * corresponding <code>ejbXXX()</code> template methods from within the
 * persistence operations. These methods are abstract and protected, and are
 * meant to be implemented by subclasses. By doing so, the subclasses can decide
 * what EJB session bean to call and whether the local or remote interface to
 * the session bean should be used.
 * </p>
 * <p>
 * Two subclasses are provided in this component:
 * {@link LocalOrpheusUserProfilePersistence} and
 * {@link RemoteOrpheusUserProfilePersistence}. The
 * <code>LocalOrpheusUserProfilePersistence</code> uses the local interface to
 * the EJB session bean, and should be used when the application is running in
 * the same execution environment (JVM) as the session bean. The
 * <code>RemoteOrpheusUserProfilePersistence</code> class, on the other hand,
 * uses the remote interface to the EJB session, allowing the application to
 * interact with session bean remotely from a different execution environment
 * (JVM).
 * </p>
 * <p>
 * Since the <code>UserProfile</code> objects are not serializable, this class
 * first converts them internally to corresponding {@link UserProfileDTO}
 * instances in order to facilitate the transfer of user profile information to
 * and from the EJB session bean. An {@link ObjectTranslator} is used to perform
 * this conversion. The name of the <code>ObjectTranslator</code>
 * implementation class to use should be specified in this class's configuration
 * (see the <b>Configuration</b> section below). Applications may use the
 * {@link UserProfileTranslator} class provided in this component.
 * </p>
 * <p>
 * User profiles are cached within this class using a <code>Cache</code>
 * instance in order to reduce the number of requests made to the session bean
 * and to improve response times. The manner in which the profiles are cached
 * (including the caching strategy, cache size, etc) can be configured in the
 * Simple Cache configuration namespace. Please consult the Simple Cache
 * component specification for information on how to configure the cache.
 * </p>
 * <p>
 * <b>Logging Activity:</b><br>
 * If any errors occur while performing the persistence operations, they will be
 * logged using the ERROR log level.
 * </p>
 * <p>
 * <b>Configuration:</b><br>
 * In order to use this class, it needs to be configured in the configuration
 * namespace provided to the {@link #OrpheusUserProfilePersistence(String)}
 * constructor. The configuration parameters are listed in the table below.
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
 * <code>ObjectTranslator</code> and <code>Cache</code> object creation.
 * This namespace is passed to the
 * <code>ConfigManagerSpecificationFactory</code> class</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>translatorKey</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>ObjectTranslator</code> instance</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>cacheKey</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>Cache</code> instance</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>log</td>
 * <td>The name of the logger to use when logging errors</td>
 * <td>Yes</td>
 * </tr>
 * </tbody> </table>
 * <p>
 * <b>Thread-safety:</b><br>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 */
public abstract class OrpheusUserProfilePersistence implements UserProfilePersistence {

    /**
     * <p>
     * The object translator which is used to convert UserProfile objects to
     * UserProfileDTO instances, and vice-versa.
     * </p>
     * <p>
     * This field is set in the constructor, and can never be changed
     * afterwards. It cannot be null.
     * </p>
     */
    private final ObjectTranslator objectTranslator;

    /**
     * <p>
     * A cache of UserProfileDTO objects. The objects are indexed in the
     * by the corresponding user profile identifiers. The cache size,
     * replacement policy and other caching options are set in the Simple Cache
     * configuration namespace.
     * </p>
     * <p>
     * This field is created in the constructor, and can never be changed
     * afterwards. It cannot be null.
     * </p>
     */
    private final Cache cache;

    /**
     * <p>
     * The logger which is used to report persistence errors.
     * </p>
     * <p>
     * This field is set in the constructor, and can never be changed
     * afterwards. It cannot be null.
     * </p>
     */
    private final Log log;

    /**
     * <p>
     * Creates a new <code>OrpheusUserProfilePersistence</code> instance using
     * the specified configuration namespace.
     * </p>
     * <p>
     * The configuration namespace is used to load the {@link ObjectTranslator},
     * which is used to convert <code>UserProfile</code> objects to
     * serializable {@link UserProfileDTO} instances that are transported to the
     * persistence layer, and vice-versa. It is also used to load the
     * <code>Cache</code>, which is used to cache user profiles within this
     * class, and the name of the logger which will be used to log persistence
     * errors. If an error occurs reading the configuration information, or
     * while instantiating the <code>ObjectTranslator</code> and
     * <code>Cache</code> objects, a
     * <code>ProfileManagerConfigurationException</code> is thrown. Please
     * consult the class documentation for more information on the configuration
     * parameters.
     * </p>
     *
     * @param namespace the configuration namespace from which to read
     *        configuration information
     * @throws IllegalArgumentException if the configuration namespace is
     *         <code>null</code> or a blank string
     * @throws ProfileManagerConfigurationException if an error occurs reading
     *         from the configuration namespace or while instantiating the
     *         <code>ObjectTranslator</code> and <code>Cache</code> objects
     */
    public OrpheusUserProfilePersistence(String namespace) throws ProfileManagerConfigurationException {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(namespace, "namespace");

        // Create the ObjectTranslator and Cache.
        try {
            objectTranslator = (ObjectTranslator) UserLogicPersistenceHelper.createObject(namespace, "translatorKey",
                                                                                          ObjectTranslator.class);

            cache = (Cache) UserLogicPersistenceHelper.createObject(namespace, "cacheKey", Cache.class);

            // Create the Log.
            String logName = UserLogicPersistenceHelper.getConfigProperty(namespace, "log", true);
            log = LogFactory.getLog(logName);
        } catch (ObjectInstantiationException e) {
            // Thrown by UserLogicPersistenceHelper methods.
            // Since wrapping the ObjectInstantiationException would be
            // redundant, simply use its message and cause.
            throw new ProfileManagerConfigurationException(e.getMessage(), e.getCause());
        }
    }

    /**
     * <p>
     * Inserts the given user profile into the persistent store. If the
     * persistent store already contains a user profile with the same identifier
     * as that of the given user profile, a
     * <code>DuplicateProfileException</code> is thrown.
     * </p>
     * <p>
     * Once the user profile has been inserted successfully, it is cached within
     * this class in order to reduce the number of retrieval requests made to
     * the EJB session bean and to improve response times.
     * </p>
     * <p>
     * Any errors which may occur while inserting the user profile will be
     * logged using the ERROR log level.
     * </p>
     *
     * @param profile the user profile to insert
     * @throws IllegalArgumentException if the user profile is <code>null</code>
     * @throws DuplicateProfileException if the persistent store contains a user
     *         profile with the same identifier as that of the given user
     *         profile
     * @throws UserProfileManagerException if inserting the user profile into
     *         the persistent store fails
     */
    public void insertProfile(UserProfile profile) throws UserProfileManagerException {
        UserLogicPersistenceHelper.assertArgumentNotNull(profile, "user profile");

        // Check if the confirmation message is already in the cache.
        if (cache.get(profile.getIdentifier()) != null) {
            throw new DuplicateProfileException("A user profile with ID, " + profile.getIdentifier()
                                                + ", already exists in the persistent store",
                                                getProfileIdAsLong(profile));
        }

        try {
            // Translate the user profile to its corresponding DTO, and insert.
            UserProfileDTO profileDTO = (UserProfileDTO) objectTranslator.assembleDTO(profile);
            ejbInsertProfile(profileDTO);

            // Put the inserted profile in cache.
            cache.put(profile.getIdentifier(), profileDTO);
        } catch (TranslationException e) {
            log.log(Level.ERROR, "Error assembling UserProfileDTO from given UserProfile object: " + e);
            throw new UserProfileManagerException("Error assembling UserProfileDTO from given UserProfile object", e);
        } catch (DuplicateEntryException e) {
            log.log(Level.ERROR, "Failed to insert user profile with ID, " + profile.getIdentifier() + ": " + e);
            throw new DuplicateProfileException(e.getMessage(), getProfileIdAsLong(profile));
        } catch (PersistenceException e) {
            log.log(Level.ERROR, "Failed to insert user profile with ID, " + profile.getIdentifier() + ": " + e);
            throw new UserProfileManagerException("Failed to insert user profile with ID, " + profile.getIdentifier(),
                                                  e);
        }
    }

    /**
     * <p>
     * Updates the given user profile in the persistent store. If the user
     * profile does not exist in the persistent store, an
     * <code>UnknownProfileException</code> is thrown.
     * </p>
     * <p>
     * Once the user profile has been updated successfully, it is cached within
     * this class in order to reduce the number of retrieval requests made to
     * the EJB session bean and to improve response times.
     * </p>
     * <p>
     * Any errors which may occur while updating the user profile will be logged
     * using the ERROR log level.
     * </p>
     *
     * @param profile the user profile to update
     * @throws IllegalArgumentException if the user profile is <code>null</code>
     * @throws UnknownProfileException if the user profile does not exist in the
     *         persistent store
     * @throws UserProfileManagerException if updating the user profile in the
     *         persistent store fails
     */
    public void updateProfile(UserProfile profile) throws UserProfileManagerException {
        UserLogicPersistenceHelper.assertArgumentNotNull(profile, "user profile");

        try {
            UserProfileDTO profileDTO = (UserProfileDTO) objectTranslator.assembleDTO(profile);
            ejbUpdateProfile(profileDTO);

            // Put the update user profile in the cache.
            cache.put(profile.getIdentifier(), profileDTO);
        } catch (TranslationException e) {
            log.log(Level.ERROR, "Error assembling UserProfileDTO from given UserProfile object: " + e);
            throw new UserProfileManagerException("Error assembling UserProfileDTO from given UserProfile object", e);
        } catch (EntryNotFoundException e) {
            log.log(Level.ERROR, "Failed to update user profile with ID, " + profile.getIdentifier() + ": " + e);
            throw new UnknownProfileException(e.getMessage(), getProfileIdAsLong(profile));
        } catch (PersistenceException e) {
            log.log(Level.ERROR, "Failed to update user profile with ID, " + profile.getIdentifier() + ": " + e);
            throw new UserProfileManagerException("Failed to update user profile with ID, " + profile.getIdentifier(),
                                                  e);
        }
    }

    /**
     * <p>
     * Retrieves the user profile with the specified identifier from the
     * persistent store. If no such user profile could be found, an
     * <code>UnknownProfileException</code> is thrown.
     * </p>
     * <p>
     * Once the user profile has been retrieved, it is cached within this class
     * in order to reduce the number of retrieval requests made to the EJB
     * session bean and to improve response times.
     * </p>
     * <p>
     * Any errors which may occur while retrieving the user profile will be
     * logged using the ERROR log level.
     * </p>
     *
     * @param id the identifier of the user profile to retrieve
     * @return the user profile with the specified identifier
     * @throws UnknownProfileException if no user profile with the specified
     *         identifier exists in the persistent store
     * @throws UserProfileManagerException if retrieving the user profile from
     *         the persistent store fails
     * @see #findProfiles(Map)
     * @see #retrieveAllProfiles()
     */
    public UserProfile retrieveProfile(long id) throws UserProfileManagerException {
        try {
            // If the user profile is in the cache, return it.
            // Otherwise, retrieve it from persistent storage.
            Long cacheKey = new Long(id);
            UserProfileDTO profileDTO = (UserProfileDTO) cache.get(cacheKey);
            if (profileDTO == null) {
                profileDTO = ejbRetrieveProfile(id);
                cache.put(cacheKey, profileDTO);
            }

            return (UserProfile) objectTranslator.assembleVO(profileDTO);
        } catch (EntryNotFoundException e) {
            log.log(Level.ERROR, "Failed to retrieve user profile with ID, " + id + ": " + e);
            throw new UnknownProfileException(e.getMessage(), id);
        } catch (PersistenceException e) {
            log.log(Level.ERROR, "Failed to retrieve user profile with ID, " + id + ": " + e);
            throw new UserProfileManagerException("Failed to retrieve user profile with ID, " + id, e);
        } catch (TranslationException e) {
            log.log(Level.ERROR, "Error assembling UserProfile object from retrieved UserProfileDTO: " + e);
            throw new UserProfileManagerException("Error assembling UserProfile object from retrieved UserProfileDTO",
                                                  e);
        }
    }

    /**
     * <p>
     * Deletes the user profile with the specified identifier from the
     * persistent store. If no such user profile could be found, an
     * <code>UnknownProfileException</code> is thrown.
     * </p>
     * <p>
     * If the user profile is cached within this class, it is deleted from the
     * cache as well.
     * </p>
     * <p>
     * Any errors which may occur while deleting the user profile will be logged
     * using the ERROR log level.
     * </p>
     *
     * @param id the identifier of the user profile to delete
     * @throws UnknownProfileException if no user profile with the specified
     *         identifier exists in the persistent store
     * @throws UserProfileManagerException if deleting the user profile from the
     *         persistent store fails
     */
    public void deleteProfile(long id) throws UserProfileManagerException {
        // Delete the user profile from the cache.
        cache.remove(new Long(id));

        // Delete the user profile from persistent storage.
        try {
            ejbDeleteProfile(id);
        } catch (EntryNotFoundException e) {
            log.log(Level.ERROR, "Failed to delete user profile with ID, " + id + ": " + e);
            throw new UnknownProfileException(e.getMessage(), id);
        } catch (PersistenceException e) {
            log.log(Level.ERROR, "Failed to delete user profile with ID, " + id + ": " + e);
            throw new UserProfileManagerException("Failed to delete user profile with ID, " + id, e);
        }
    }

    /**
     * <p>
     * Retrieves all the user profiles from the persistent store that match the
     * given criteria, and returns them in an array. A user profile must match
     * all the criteria for it to be included in the returned array. If no user
     * profiles match the criteria, an empty array is returned. If the given
     * criteria map is empty, all the user profiles are retrieved.
     * </p>
     * <p>
     * The keys in the criteria map must be one of the user profile property
     * constants defined in the {@link UserConstants} interface (those that are
     * not named <code>XXX_TYPE_NAME</code>). If a key is not equal to one of
     * the property constants, an <code>IllegalArgumentException</code> is
     * thrown. Note that this means that the user's email address, first name or
     * last name cannot be used as search criteria, because they do not
     * correspond to any property constants defined in the
     * <code>UserConstants</code> interface.
     * </p>
     * <p>
     * The values in the criteria map should be <code>String</code>, because
     * that is how this component stores data in its data transfer objects.
     * However, any <code>Object</code> instance compatible with the
     * corresponding persistent store field may be used. The criteria values
     * will be compared to the corresponding persistent store fields, and, if
     * equal, the user profile containing those fields will be included in the
     * returned array. As such, the criteria map values may be <code>null</code>
     * if the persistent store allows for <code>null</code> values in the
     * corresponding fields.
     * </p>
     * <p>
     * Examples on how to use this method are shown in the code snippet below.
     * </p>
     * <pre>
     * // Create the search criteria map.
     * Map criteria = new HashMap();
     *
     * // Find the user profiles of all the active users.
     * criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, Boolean.TRUE);
     * UserProfileDTO[] profiles = findProfiles(criteria);
     *
     * // Find the user profiles of all the active players that have &quot;Wire transfer&quot;
     * // set as their payment preference.
     * criteria.put(UserConstants.PLAYER_PAYMENT_PREF, &quot;Wire transfer&quot;);
     * profiles = findProfiles(criteria);
     *
     * // Find the user profiles of all the non-active players and sponsors who live in
     * // Los Angeles, California.
     * criteria.clear();
     * criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, Boolean.FALSE);
     * criteria.put(UserConstants.ADDRESS_CITY, &quot;Los Angeles&quot;);
     * criteria.put(UserConstants.ADDRESS_STATE, &quot;California&quot;);
     * profiles = findProfiles(criteria);
     *
     * // Find the user profiles of all the approved sponsors in California.
     * criteria.clear();
     * criteria.put(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_TRUE);
     * criteria.put(UserConstants.ADDRESS_STATE, &quot;California&quot;);
     *
     * // An empty criteria map retrieves all the user profiles.
     * criteria.clear();
     * profiles = findProfiles(criteria);
     *
     * // Assuming no user profile contains the handle, &quot;joeuser&quot;, the following
     * // will return an empty array.
     * criteria.clear();
     * criteria.put(UserConstants.CREDENTIALS_HANDLE, &quot;joeuser&quot;);
     * profiles = findProfiles(criteria);
     *
     * // Results in an IllegalArgumentException being thrown, because &quot;InvalidCriteria&quot;
     * // does not correspond to a user profile property constant defined in the UserConstants
     * // interface.
     * criteria.put(&quot;InvalidCriteria&quot;, &quot;Some value&quot;);
     * profiles = findProfiles(criteria);
     * </pre>
     * <p>
     * Once the user profiles have been found, they are cached within this class
     * in order to reduce the number of retrieval requests made to the EJB
     * session bean and to improve response times.
     * </p>
     * <p>
     * Any errors which may occur while finding the user profiles will be logged
     * using the ERROR log level.
     * </p>
     *
     * @param criteria the criteria to use when searching for user profiles in
     *        the persistent store to retrieve
     * @return a <code>UserProfile[]</code> array containing all the user
     *         profiles in the persistent store that match the given criteria;
     *         an empty array if there are no user profiles in the persistent
     *         store that match the given criteria; or, if the criteria map is
     *         empty, a <code>UserProfile[]</code> array containing all the
     *         user profiles in the persistent store
     * @throws IllegalArgumentException if the criteria map is <code>null</code>,
     *         if it contains keys that are <code>null</code>, non-<code>String</code>
     *         instances or blank strings, of if the keys are not equal to one
     *         of the property constants defined in the {@link UserConstants}
     *         interface
     * @throws UserProfileManagerException if retrieving the user profile from
     *         the persistent store that match the given criteria fails
     * @see #retrieveProfile(long)
     * @see #retrieveAllProfiles()
     */
    public UserProfile[] findProfiles(Map criteria) throws UserProfileManagerException {
        UserLogicPersistenceHelper.assertArgumentNotNull(criteria, "search criteria map");

        try {
            // Get the user profiles.
            UserProfileDTO[] profileDTOs = ejbFindProfiles(criteria);

            // Translate the profile DTO's to UserProfile objects.
            UserProfile[] profiles = new UserProfile[profileDTOs.length];
            for (int i = 0; i < profileDTOs.length; i++) {
                profiles[i] = (UserProfile) objectTranslator.assembleVO(profileDTOs[i]);
            }

            // Put the user profiles in the cache.
            for (int i = 0; i < profiles.length; i++) {
                cache.put(profiles[i].getIdentifier(), profileDTOs[i]);
            }

            return profiles;
        } catch (PersistenceException e) {
            log.log(Level.ERROR, "Failed to find user profiles: " + e);
            throw new UserProfileManagerException("Failed to find user profiles", e);
        } catch (TranslationException e) {
            log.log(Level.ERROR, "Error assembling UserProfile from retrieved UserProfileDTO: " + e);
            throw new UserProfileManagerException("Error assembling UserProfile from retrieved UserProfileDTO", e);
        }
    }

    /**
     * <p>
     * Retrieves all the user profiles from the persistent store as an array. If
     * no user profiles exist in the persistent store, an empty array is
     * returned.
     * </p>
     * <p>
     * Once the user profiles have been retrieved, they are cached within this
     * class in order to reduce the number of retrieval requests made to the EJB
     * session bean and to improve response times.
     * </p>
     * <p>
     * Any errors which may occur while retrieving all the user profiles will be
     * logged using the ERROR log level.
     * </p>
     *
     * @return a <code>UserProfile[]</code> array containing all the user
     *         profiles in the persistent store, or an empty array if there are
     *         no user profiles
     * @throws UserProfileManagerException if retrieving all the user profiles
     *         from the persistent store fails
     * @see #retrieveProfile(long)
     * @see #findProfiles(Map)
     */
    public UserProfile[] retrieveAllProfiles() throws UserProfileManagerException {
        try {
            // Get the user profiles.
            UserProfileDTO[] profileDTOs = ejbRetrieveAllProfiles();

            // Translate the profile DTO's to UserProfile objects.
            UserProfile[] profiles = new UserProfile[profileDTOs.length];
            for (int i = 0; i < profileDTOs.length; i++) {
                profiles[i] = (UserProfile) objectTranslator.assembleVO(profileDTOs[i]);
            }

            // Put the user profiles in the cache.
            for (int i = 0; i < profiles.length; i++) {
                cache.put(profiles[i].getIdentifier(), profileDTOs[i]);
            }

            return profiles;
        } catch (PersistenceException e) {
            log.log(Level.ERROR, "Failed to retrieve all user profiles: " + e);
            throw new UserProfileManagerException("Failed to retrieve all user profiles", e);
        } catch (TranslationException e) {
            log.log(Level.ERROR, "Error assembling UserProfile from retrieved UserProfileDTO: " + e);
            throw new UserProfileManagerException("Error assembling UserProfile from retrieved UserProfileDTO", e);
        }
    }

    /**
     * <p>
     * Transfers any cached changes to the persistent store. Since all changes
     * are written to the persist store immediately, this method does nothing.
     * </p>
     */
    public void commit() {
        // Does nothing.
    }

    /**
     * <p>
     * Implemented by subclasses to insert the given user profile into the
     * persistent store. If the persistent store already contains a user profile
     * with the same identifier as that of the given user profile, a
     * <code>DuplicateEntryException</code> is thrown.
     * </p>
     *
     * @param profile a data transfer object (DTO) representing the user profile
     *        to insert
     * @throws IllegalArgumentException if the user profile DTO is
     *         <code>null</code> or contains an invalid key to object mapping
     * @throws DuplicateEntryException if the persistent store contains a user
     *         profile with the same identifier as that of the given user
     *         profile
     * @throws PersistenceException if inserting the user profile into the
     *         persistent store fails
     */
    protected abstract void ejbInsertProfile(UserProfileDTO profile) throws PersistenceException;

    /**
     * <p>
     * Implemented by subclasses to update the given user profile in the
     * persistent store. If the user profile does not exist in the persistent
     * store, an <code>EntryNotFoundException</code> is thrown.
     * </p>
     *
     * @param profile a data transfer object (DTO) representing the user profile
     *        to update
     * @throws IllegalArgumentException if the user profile DTO is
     *         <code>null</code> or contains an invalid key to object mapping
     * @throws EntryNotFoundException if the user profile does not exist in the
     *         persistent store
     * @throws PersistenceException if updating the user profile in the
     *         persistent store fails
     */
    protected abstract void ejbUpdateProfile(UserProfileDTO profile) throws PersistenceException;

    /**
     * <p>
     * Implemented by subclasses to retrieve the user profile with the specified
     * identifier from the persistent store. If no such user profile could be
     * found, an <code>EntryNotFoundException</code> is thrown.
     * </p>
     *
     * @param id the identifier of the user profile to retrieve
     * @return a <code>UserProfileDTO</code> representing the user profile
     *         with the specified identifier
     * @throws EntryNotFoundException if no user profile with the specified
     *         identifier exists in the persistent store
     * @throws PersistenceException if retrieving the user profile from the
     *         persistent store fails
     */
    protected abstract UserProfileDTO ejbRetrieveProfile(long id) throws PersistenceException;

    /**
     * <p>
     * Implemented by subclasses to delete the user profile with the specified
     * identifier from the persistent store. If no such user profile could be
     * found, an <code>EntryNotFoundException</code> is thrown.
     * </p>
     *
     * @param id the identifier of the user profile to delete
     * @throws EntryNotFoundException if no user profile with the specified
     *         identifier exists in the persistent store
     * @throws PersistenceException if deleting the user profile from the
     *         persistent store fails
     */
    protected abstract void ejbDeleteProfile(long id) throws PersistenceException;

    /**
     * <p>
     * Implemented by subclasses to retrieve all the user profiles from the
     * persistent store that match the given criteria, and returns them in an
     * array. A user profile must match all the criteria for it to be included
     * in the returned array. If no user profiles match the criteria, an empty
     * array is returned. If the given criteria map is empty, all the user
     * profiles are retrieved.
     * </p>
     *
     * @param criteria the criteria to use when searching for user profiles in
     *        the persistent store to retrieve
     * @return a <code>UserProfile[]</code> array containing all the user
     *         profiles in the persistent store that match the given criteria;
     *         an empty array if there are no user profiles in the persistent
     *         store that match the given criteria; or, if the criteria map is
     *         empty, a <code>UserProfile[]</code> array containing all the
     *         user profiles in the persistent store
     * @throws IllegalArgumentException if the criteria map contains keys that are
     *         <code>null</code>, non-<code>String</code> instances or blank strings,
     *         or if the keys are not equal to one of the property constants defined
     *         in the {@link UserConstants) interface
     * @throws PersistenceException if retrieving the user profile from the
     *         persistent store that match the given criteria fails
     */
    protected abstract UserProfileDTO[] ejbFindProfiles(Map criteria) throws PersistenceException;

    /**
     * <p>
     * Implemented by subclasses to retrieve all the user profiles from the
     * persistent store as an array. If no user profiles exist in the persistent
     * store, an empty array is returned.
     * </p>
     *
     * @return a <code>UserProfileDTO[]</code> array containing all the user
     *         profiles in the persistent store, or an empty array if there are
     *         no user profiles
     * @throws PersistenceException if retrieving all the user profiles from the
     *         persistent store fails
     */
    protected abstract UserProfileDTO[] ejbRetrieveAllProfiles() throws PersistenceException;

    /**
     * <p>
     * Returns the user profile identifier (an <code>Object</code> instance)
     * as a <code>long</code> value. This method is called in order to pass
     * the user profile ID to the <code>DuplicateProfileException</code>
     * constructor, which expects a <code>long</code> ID. If the user profile
     * ID is a <code>Number</code> or <code>String</code> instance, then
     * the corresponding <code>long</code> value is returned. If the ID
     * is of any other type, or if the string ID cannot be converted to a
     * <code>long</code> value, then -1 is returned.
     * </p>
     *
     * @param profile the user profile whose ID should be returned
     * @return the user profile ID, or -1 if the ID cannot be converted to a
     *         <code>long</code> value
     */
    private long getProfileIdAsLong(UserProfile profile) {
        Object id = profile.getIdentifier();

        if (id instanceof Number) {
            return ((Number) id).longValue();
        } else if (id instanceof String) {
            try {
                return Long.parseLong((String) id);
            } catch (NumberFormatException e) {
                // Ignore, -1 will be returned.
            }
        }

        return -1;
    }

}
