/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.orpheus.user.persistence.failuretests;

import com.orpheus.user.persistence.OrpheusUserProfilePersistence;
import com.orpheus.user.persistence.PersistenceException;
import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.ejb.UserProfileDTO;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.DuplicateProfileException;
import com.topcoder.user.profile.manager.ProfileManagerConfigurationException;
import com.topcoder.user.profile.manager.UnknownProfileException;
import com.topcoder.user.profile.manager.UserProfileManagerException;

import java.util.Map;


/**
 * Dummy OrpheusUserProfilePersistence class.
 *
 * @author crackme
 * @version 1.0
 */
public class DummyOrpheusUserProfilePersistence extends OrpheusUserProfilePersistence {
    /**
     * Creates a new DummyOrpheusUserProfilePersistence object.
     *
     * @param namespace DOCUMENT ME!
     *
     * @throws ProfileManagerConfigurationException DOCUMENT ME!
     */
    public DummyOrpheusUserProfilePersistence(String namespace)
        throws ProfileManagerConfigurationException {
        super(namespace);
    }

    /**
     * DOCUMENT ME!
     *
     * @param profile DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected void ejbInsertProfile(UserProfileDTO profile)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param profile DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected void ejbUpdateProfile(UserProfileDTO profile)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param id DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected UserProfileDTO ejbRetrieveProfile(long id)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param id DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected void ejbDeleteProfile(long id) throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param criteria DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected UserProfileDTO[] ejbFindProfiles(Map criteria)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected UserProfileDTO[] ejbRetrieveAllProfiles()
        throws PersistenceException {
        return null;
    }

    /**
     * <p>
     * Inserts the given user profile into the persistent store. If the persistent store already
     * contains a user profile with the same identifier as that of the given user profile, a
     * <code>DuplicateProfileException</code> is thrown.
     * </p>
     * 
     * <p>
     * Once the user profile has been inserted successfully, it is cached within this class in
     * order to reduce the number of retrieval requests made to the EJB session bean and to
     * improve response times.
     * </p>
     * 
     * <p>
     * Any errors which may occur while inserting the user profile will be logged using the ERROR
     * log level.
     * </p>
     *
     * @param profile the user profile to insert
     *
     * @throws IllegalArgumentException if the user profile is <code>null</code>
     * @throws DuplicateProfileException if the persistent store contains a user profile with the
     *         same identifier as that of the given user profile
     * @throws UserProfileManagerException if inserting the user profile into the persistent store
     *         fails
     */
    public void insertProfile(UserProfile profile) throws UserProfileManagerException {
        super.insertProfile(profile);
    }

    /**
     * <p>
     * Updates the given user profile in the persistent store. If the user profile does not exist
     * in the persistent store, an <code>UnknownProfileException</code> is thrown.
     * </p>
     * 
     * <p>
     * Once the user profile has been updated successfully, it is cached within this class in order
     * to reduce the number of retrieval requests made to the EJB session bean and to improve
     * response times.
     * </p>
     * 
     * <p>
     * Any errors which may occur while updating the user profile will be logged using the ERROR
     * log level.
     * </p>
     *
     * @param profile the user profile to update
     *
     * @throws IllegalArgumentException if the user profile is <code>null</code>
     * @throws UnknownProfileException if the user profile does not exist in the persistent store
     * @throws UserProfileManagerException if updating the user profile in the persistent store
     *         fails
     */
    public void updateProfile(UserProfile profile) throws UserProfileManagerException {
        super.updateProfile(profile);
    }

    /**
     * <p>
     * Retrieves all the user profiles from the persistent store that match the given criteria, and
     * returns them in an array. A user profile must match all the criteria for it to be included
     * in the returned array. If no user profiles match the criteria, an empty array is returned.
     * If the given criteria map is empty, all the user profiles are retrieved.
     * </p>
     * 
     * <p>
     * The keys in the criteria map must be one of the user profile property constants defined in
     * the {@link UserConstants} interface (those that are not named <code>XXX_TYPE_NAME</code>).
     * If a key is not equal to one of the property constants, an
     * <code>IllegalArgumentException</code> is thrown. Note that this means that the user's email
     * address, first name or last name cannot be used as search critiera, because they do not
     * correspond to any property constants defined in the <code>UserConstants</code> interface.
     * </p>
     * 
     * <p>
     * The values in the criteria map should be <code>String</code>, because that is how this
     * component stores data in its data transfer objects. However, any <code>Object</code>
     * instance compatible with the corresponding persistent store field may be used. The criteria
     * values will be compared to the corresponding persistent store fields, and, if equal, the
     * user profile containing those fields will be included in the returned array. As such, the
     * criteria map values may be <code>null</code> if the persistent store allows for
     * <code>null</code> values in the corresponding fields.
     * </p>
     *
     * @param criteria the criteria to use when searching for user profiles in the persistent store
     *        to retrieve
     *
     * @return a <code>UserProfile[]</code> array containing all the user profiles in the
     *         persistent store that match the given criteria; an empty array if there are no user
     *         profiles in the persistent store that match the given criteria; or, if the criteria
     *         map is empty, a <code>UserProfile[]</code> array containing all the user profiles
     *         in the persistent store
     *
     * @throws IllegalArgumentException if the criteria map is <code>null</code>, if it contains
     *         keys that are <code>null</code>, non-<code>String</code> instances or blank
     *         strings, of if the keys are not equal to one of the property constants defined in
     *         the {@link UserConstants} interface
     * @throws UserProfileManagerException if retrieving the user profile from the persistent store
     *         that match the given criteria fails
     *
     * @see #retrieveProfile(long)
     * @see #retrieveAllProfiles()
     */
    public UserProfile[] findProfiles(Map criteria) throws UserProfileManagerException {
        return super.findProfiles(criteria);
    }
}
