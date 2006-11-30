/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.util.Map;

import com.orpheus.user.persistence.ejb.UserProfileDTO;
import com.orpheus.user.persistence.impl.Admin;
import com.topcoder.user.profile.manager.UserProfileManagerException;

/**
 * <p>
 * A mock OrpheusUserProfilePersistence subclass which is used by the
 * OrpheusUserProfilePersistenceTest test case to instantiate and test the
 * abstract OrpheusUserProfilePersistence class. It contains operations that
 * allows the test case to check if the ejbXXX() methods have been called by the
 * OrpheusUserProfilePersistence class when performing certain persistence
 * operations.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class MockOrpheusUserProfilePersistence extends OrpheusUserProfilePersistence {

    /**
     * <p>
     * Indicates whether the ejbInserProfile(UserProfileDTO) method was called.
     * </p>
     */
    private boolean insertMethodWasCalled = false;

    /**
     * <p>
     * Indicates whether the ejbUpdateProfile(UserProfileDTO) method was called.
     * </p>
     */
    private boolean updateMethodWasCalled = false;

    /**
     * <p>
     * Indicates whether the ejbRetrieveProfile(long) method was called.
     * </p>
     */
    private boolean retrieveMethodWasCalled = false;

    /**
     * <p>
     * Indicates whether the ejbDeleteProfile(long) method was called.
     * </p>
     */
    private boolean deleteMethodWasCalled = false;

    /**
     * <p>
     * Indicates whether the ejbFindProfiles(Map) method was called.
     * </p>
     */
    private boolean findMethodWasCalled = false;

    /**
     * <p>
     * Indicates whether the ejbRetrieveAllProfiles() method was called.
     * </p>
     */
    private boolean retrieveAllMethodWasCalled = false;

    /**
     * <p>
     * A mock user profile DTO to return from the ejbRetrieveProfile(long),
     * ejbFindProfiles(Map) and ejbRetrieveAllrofiles() methods.
     * </p>
     */
    private final UserProfileDTO profileDTO;

    /**
     * <p>
     * Creates a new MockOrpheusUserProfilePersistence instance. This
     * constructor simply invokes the base class's constructor.
     * </p>
     *
     * @param namespace the configuration namespace
     * @throws UserProfileManagerException if instantiation failed
     */
    public MockOrpheusUserProfilePersistence(String namespace) throws UserProfileManagerException {
        super(namespace);

        // Create the mock user profile DTO.
        Admin admin = new Admin(0);
        admin.setActive("false");
        admin.setEmail("tcsdeveloper@topcodersoftware.com");
        admin.setHandle("tcsdeveloper");
        admin.setPassword("password");
        profileDTO = new UserProfileDTO();
        profileDTO.put(UserConstants.ADMIN_TYPE_NAME, admin);
    }

    /**
     * <p>
     * Called by the OrpheusUserProfilePersistence.insertProfile(UserProfile)
     * method to insert the user profile. It simply sets an internal flag
     * indicating that it was called. The value of this flag can be checked by a
     * test case using the insertProfileMethodWasCalled() method.
     * </p>
     *
     * @param profile the profile to insert
     */
    protected void ejbInsertProfile(UserProfileDTO profile) {
        insertMethodWasCalled = true;
    }

    /**
     * <p>
     * Called by the OrpheusUserProfilePersistence.updateProfile(UserProfile)
     * method to update the user profile. It simply sets an internal flag
     * indicating that it was called. The value of this flag can be checked by a
     * test case using the updateProfileMethodWasCalled() method.
     * </p>
     *
     * @param profile the profile to update
     */
    protected void ejbUpdateProfile(UserProfileDTO profile) {
        updateMethodWasCalled = true;
    }

    /**
     * <p>
     * Called by the OrpheusUserProfilePersistence.retrieveProfile(long) method
     * to retrieve a user profile. It simply sets an internal flag indicating
     * that it was called. The value of this flag can be checked by a test case
     * using the retrieveProfileMethodWasCalled() method.
     * </p>
     *
     * @param id the ID of the profile to retrieve
     * @return a non-null mock UserProfileDTO instance
     */
    protected UserProfileDTO ejbRetrieveProfile(long id) {
        retrieveMethodWasCalled = true;
        return profileDTO;
    }

    /**
     * <p>
     * Called by the OrpheusUserProfilePersistence.deleteProfile(long) method to
     * delete a user profile. It simply sets an internal flag indicating that it
     * was called. The value of this flag can be checked by a test case using
     * the deleteProfileMethodWasCalled() method.
     * </p>
     *
     * @param id the ID of the profile to delete
     */
    protected void ejbDeleteProfile(long id) {
        deleteMethodWasCalled = true;
    }

    /**
     * <p>
     * Called by the OrpheusUserProfilePersistence.findProfiles(Map) method to
     * find user profiles. It simply sets an internal flag indicating that it
     * was called. The value of this flag can be checked by a test case using
     * the findProfilesMethodWasCalled() method.
     * </p>
     *
     * @param criteria the search criteria
     * @return an array containing a non-null mock UserProfileDTO instance
     */
    protected UserProfileDTO[] ejbFindProfiles(Map criteria) {
        findMethodWasCalled = true;
        return new UserProfileDTO[] {
            profileDTO
        };
    }

    /**
     * <p>
     * Called by the OrpheusUserProfilePersistence.retrieveAllProfiles() method
     * to retrieve all the user profiles. It simply sets an internal flag
     * indicating that it was called. The value of this flag can be checked by a
     * test case using the retrieveAllProfilesMethodWasCalled() method.
     * </p>
     *
     * @return an array containing a non-null mock UserProfileDTO instance
     */
    protected UserProfileDTO[] ejbRetrieveAllProfiles() {
        retrieveAllMethodWasCalled = true;
        return new UserProfileDTO[] {
            profileDTO
        };
    }

    /**
     * <p>
     * Returns whether the ejbInsertProfile(UserProfileDTO) method was called.
     * </p>
     *
     * @return true if the ejbInsertProfile(UserProfileDTO) method was called;
     *         false otherwise
     */
    public boolean insertProfileMethodWasCalled() {
        return insertMethodWasCalled;
    }

    /**
     * <p>
     * Returns whether the ejbUpdateProfile(UserProfileDTO) method was called.
     * </p>
     *
     * @return true if the ejbUpdateProfile(UserProfileDTO) method was called;
     *         false otherwise
     */
    public boolean updateProfileMethodWasCalled() {
        return updateMethodWasCalled;
    }

    /**
     * <p>
     * Returns whether the ejbRetrieveProfile(long) method was called.
     * </p>
     *
     * @return true if the ejbRetrieveProfile(long) method was called; false
     *         otherwise
     */
    public boolean retrieveProfileMethodWasCalled() {
        return retrieveMethodWasCalled;
    }

    /**
     * <p>
     * Returns whether the ejbDeleteProfile(long) method was called.
     * </p>
     *
     * @return true if the ejbDeleteProfile(long) method was called; false
     *         otherwise
     */
    public boolean deleteProfileMethodWasCalled() {
        return deleteMethodWasCalled;
    }

    /**
     * <p>
     * Returns whether the ejbFindProfiles(Map) method was called.
     * </p>
     *
     * @return true if the ejbFindProfiles(Map) method was called; false
     *         otherwise
     */
    public boolean findProfilesMethodWasCalled() {
        return findMethodWasCalled;
    }

    /**
     * <p>
     * Returns whether the ejbRetrieveAllProfiles() method was called.
     * </p>
     *
     * @return true if the ejbRetrieveAllProfiles() method was called; false
     *         otherwise
     */
    public boolean retrieveAllProfilesMethodWasCalled() {
        return retrieveAllMethodWasCalled;
    }

}
