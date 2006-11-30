/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import com.orpheus.user.persistence.impl.Sponsor;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.ConfigProfileTypeFactory;
import com.topcoder.user.profile.manager.DuplicateProfileException;
import com.topcoder.user.profile.manager.ProfileManagerConfigurationException;
import com.topcoder.user.profile.manager.UnknownProfileException;
import com.topcoder.user.profile.manager.UserProfileManagerException;

/**
 * <p>
 * Used by the LocalOrpheusUserProfilePersistenceTest and
 * RemoteOrpheusUserProfilePersistenceTest test cases to test the
 * LocalOrpheusUserProfilePersistence and RemoteOrpheusUserProfilePersistenc
 * classes, respectively. It contains all the common test methods shared between
 * the two test cases. Those common test methods delegate to the corresponding
 * methods in this class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class OrpheusUserProfilePersistenceTestHelper {

    /**
     * <p>
     * A sample user profile ID to use during the unit tests. A very large value
     * is used to avoid conflicts with existing data in the database.
     * </p>
     */
    private static final long USER_ID = 9223372036854775760L;

    /**
     * <p>
     * A factory for retrieving user profile types when creating test user
     * profiles.
     * </p>
     */
    private ConfigProfileTypeFactory profileTypeFactory = null;

    /**
     * <p>
     * The OrpheusUserProfilePersistence object to tests.
     * </p>
     */
    private OrpheusUserProfilePersistence persistence = null;

    /**
     * <p>
     * Creates a new OrpheusUserProfilePersistenceTestHelper instance that tests
     * the given OrpheusUserProfilePersistence object.
     * </p>
     *
     * @param persistence the OrpheusUserProfilePersistence object to test
     * @throws ProfileManagerConfigurationException if creating the the
     *         ConfigProfileTypeFactory, which this class uses to obtain user
     *         profile types for testing, fails
     */
    public OrpheusUserProfilePersistenceTestHelper(OrpheusUserProfilePersistence persistence)
            throws ProfileManagerConfigurationException {
        this.persistence = persistence;

        // Create the ConfigProfileTypeFactory.
        profileTypeFactory = new ConfigProfileTypeFactory("com.topcoder.util.log");
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfile profile) method with a valid player
     * profile with no contact information associated with it. The
     * retrieveProfile(long id) method should not throw an
     * UnknownProfileException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidPlayerArg() throws Exception {
        performInsertProfileTestWithValidArg(USER_ID, createPlayerProfile(USER_ID, false));
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfile profile) method with a valid player
     * profile with contact information associated with it. The
     * retrieveProfile(long id) method should not throw an
     * UnknownProfileException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidPlayerArgContainingContactInfo() throws Exception {
        performInsertProfileTestWithValidArg(USER_ID, createPlayerProfile(USER_ID, true));
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfile profile) method with a valid admin
     * profile. The retrieveProfile(long id) method should not throw an
     * UnknownProfileException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidAdminArg() throws Exception {
        performInsertProfileTestWithValidArg(USER_ID, createAdminProfile(USER_ID));
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfile profile) method with a valid sponsor
     * profile with no contact information associated with it. The
     * retrieveProfile(long id) method should not throw an
     * UnknownProfileException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidSponsorArg() throws Exception {
        performInsertProfileTestWithValidArg(USER_ID, createSponsorProfile(USER_ID, false));
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfile profile) method with a valid sponsor
     * profile with contact information associated with it. The
     * retrieveProfile(long id) method should not throw an
     * UnknownProfileException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidSponsorArgContainingContactInfo() throws Exception {
        performInsertProfileTestWithValidArg(USER_ID, createSponsorProfile(USER_ID, true));
    }

    /**
     * <p>
     * Performs the insertProfile(UserProfile profile) method test with a valid
     * argument. After the method has been invoked, calling retrieveProfile(long
     * id) with the inserted user profile ID should not result in an
     * UnknownProfileException being thrown.
     * </p>
     *
     * @param id the ID of the user profile to insert
     * @param profile the user profile to insert
     * @throws UserProfileManagerException if the test fails due to a
     *         persistence error
     */
    private void performInsertProfileTestWithValidArg(long id, UserProfile profile) throws UserProfileManagerException {
        // Insert the profile.
        persistence.insertProfile(profile);

        try {
            // Check that the profile was inserted.
            persistence.retrieveProfile(id);

            // Clean-up by deleting the inserted profile.
            persistence.deleteProfile(id);
        } catch (UnknownProfileException e) {
            Assert.fail("The user profile was not inserted successfully");
        }
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfile profile) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithNullArg() throws Exception {
        try {
            persistence.insertProfile(null);
            Assert.fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfile profile) method throws a
     * DuplicateProfileException when the user profile corresponding to the
     * argument does already exists in the persistent store. The return value of
     * the DuplicateProfileException.getDuplicateId() method should be equal to
     * the ID of the argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithDuplicateArg() throws Exception {
        // Insert the profile.
        persistence.insertProfile(createAdminProfile(USER_ID));

        // Try to insert the profile again.
        try {
            persistence.insertProfile(createAdminProfile(USER_ID));
            Assert.fail("DuplicateProfileException should be thrown");
        } catch (DuplicateProfileException e) {
            Assert.assertEquals("The duplicate entry identifier is incorrect", USER_ID, e.getDuplicateId());
            // Success.
        } finally {
            // Clean-up by deleting the profile.
            persistence.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfile profile) with a player user profile
     * containing no contact information. The return value of the
     * retrieveProfile(long id) method should be equal to the method argument
     * when given the ID of the updated player.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidPlayerArgContainingNoContactInfo() throws Exception {
        // Insert a player profile with no contact information.
        UserProfile profile = createPlayerProfile(USER_ID, false);
        persistence.insertProfile(profile);

        // Update some of the player's information.
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "updatedemail@somehost.domain");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "pwdupdate");
        profile.setProperty(UserConstants.PLAYER_PAYMENT_PREF, "Cash");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "false");

        try {
            // Try to update the profile.
            persistence.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfile profile2 = persistence.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.PLAYER_TYPE_NAME, false);
        } finally {
            // Clean-up by deleting the inserted profile.
            persistence.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfile profile) with a player user profile
     * ontaining contact information. The method is called when there is already
     * an entry for the contact information in the persistent store. After the
     * method is called, the return value of the retrieveProfile(long id) method
     * should be equal to the method argument when given the ID of the updated
     * player.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidPlayerArgContainingContactInfo1() throws Exception {
        // Insert a player profile with contact information.
        UserProfile profile = createPlayerProfile(USER_ID, true);
        persistence.insertProfile(profile);

        // Update some player information.
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "pwdupdate");
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "updatedemail@somehost.domain");

        // Update some contact information.
        profile.setProperty(UserConstants.ADDRESS_CITY, "New Updated City");
        profile.setProperty(BaseProfileType.LAST_NAME, "Update");
        profile.setProperty(UserConstants.ADDRESS_STREET_2, "Address 2 Update");

        try {
            // Try to update the profile.
            persistence.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfile profile2 = persistence.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.PLAYER_TYPE_NAME, true);
        } finally {
            // Clean-up by deleting the inserted profile.
            persistence.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfile profile) with a player user profile
     * containing contact information. The method is called when no entry
     * corresponding for the contact information exists in the persistent store.
     * Therefore, the update operation should insert the contact information,
     * rather than simply update it. After the method is called, the return
     * value of the retrieveProfile(long id) method should be equal to the
     * method argument when given the ID of the updated player.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidPlayerArgContainingContactInfo2() throws Exception {
        // Insert a player profile with no contact information.
        UserProfile profile = createPlayerProfile(USER_ID, false);
        persistence.insertProfile(profile);

        // Update some player information.
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "pwdupdate");
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "updatedemail@somehost.domain");

        // Add contact information to the player profile.
        addContactInfo(profile);

        try {
            // Try to update the profile.
            persistence.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfile profile2 = persistence.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.PLAYER_TYPE_NAME, true);
        } finally {
            // Clean-up by deleting the inserted profile.
            persistence.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfile profile) with an admin user profile.
     * The return value of the retrieveProfile(long id) method should be equal
     * to the method argument when given the ID of the updated admin.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidAdminArg() throws Exception {
        // Insert an admin profile.
        UserProfile profile = createAdminProfile(USER_ID);
        persistence.insertProfile(profile);

        // Update some admin information.
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "updatedemail@somehost.domain");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "pwdupdate");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "false");

        try {
            // Try to update the admin profile.
            persistence.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfile profile2 = persistence.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.ADMIN_TYPE_NAME, false);
        } finally {
            // Clean-up by deleting the inserted profile.
            persistence.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfile profile) with a sponsor user profile
     * containing no contact information. The return value of the
     * retrieveProfile(long id) method should be equal to the method argument
     * when given the ID of the updated sponsor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidSponsorArgContainingNoContactInfo() throws Exception {
        // Insert a sponsor profile with no contact information.
        UserProfile profile = createSponsorProfile(USER_ID, false);
        persistence.insertProfile(profile);

        // Update some sponsor information.
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "updatedemail@somehost.domain");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "pwdupdate");
        profile.setProperty(UserConstants.SPONSOR_FAX_NUMBER, "987645321");
        profile.setProperty(UserConstants.SPONSOR_PAYMENT_PREF, "Cheque");
        profile.setProperty(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_TRUE);

        try {
            // Try to update the profile.
            persistence.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfile profile2 = persistence.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.SPONSOR_TYPE_NAME, false);
        } finally {
            // Clean-up by deleting the inserted profile.
            persistence.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfile profile) with a sponsor user profile
     * containing contact information. The method is called when an entry for
     * the contact information already exists in the persistent store. After the
     * method is called, the return value of the retrieveProfile(long id) method
     * should be equal to the method argument when given the ID of the updated
     * sponsor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidSponsorArgContainingContactInfo1() throws Exception {
        // Insert a sponsor profile with contact information.
        UserProfile profile = createSponsorProfile(USER_ID, true);
        persistence.insertProfile(profile);

        // Update some sponsor information.
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "updatedemail@somehost.domain");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "pwdupdate");
        profile.setProperty(UserConstants.SPONSOR_FAX_NUMBER, "987645321");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "false");
        profile.setProperty(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_TRUE);

        // Update some contact information.
        profile.setProperty(UserConstants.ADDRESS_CITY, "New Updated City");
        profile.setProperty(BaseProfileType.LAST_NAME, "Update");
        profile.setProperty(UserConstants.ADDRESS_STREET_2, "Address 2 Update");

        try {
            // Try to update the profile.
            persistence.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfile profile2 = persistence.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.SPONSOR_TYPE_NAME, true);
        } finally {
            // Clean-up by deleting the inserted profile.
            persistence.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfile profile) with a sponsor user profile
     * containing contact information. The method is called when no entry for
     * the contact information exists in the persistent store. Therefore, the
     * update operation should insert the contact information, rather than
     * simply update it. After the method is called, the return value of the
     * retrieveProfile(long id) method should be equal to the method argument
     * when given the ID of the updated sponsor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidSponsorArgContainingContactInfo2() throws Exception {
        // Insert a sponsor profile with no contact information.
        UserProfile profile = createSponsorProfile(USER_ID, false);
        persistence.insertProfile(profile);

        // Update some sponsor information.
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "updatedemail@somehost.domain");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "pwdupdate");
        profile.setProperty(UserConstants.SPONSOR_FAX_NUMBER, "987645321");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "false");
        profile.setProperty(UserConstants.CREDENTIALS_HANDLE, "newhandle");
        profile.setProperty(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_TRUE);

        // Add contact information to the player profile.
        addContactInfo(profile);

        try {
            // Try to update the profile.
            persistence.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfile profile2 = persistence.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.SPONSOR_TYPE_NAME, true);
        } finally {
            // Clean-up by deleting the inserted profile.
            persistence.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfile profile) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithNullArg() throws Exception {
        try {
            persistence.updateProfile(null);
            Assert.fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfile profile) method throws an
     * UnknownProfileException when the user profile corresponding to the
     * argument does not exist in the persistent store. The return value of the
     * UnknownProfileException.getUnknownId() method should be equal to the ID
     * of the argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithArgNotInPersistence() throws Exception {
        // Try to update the nonexistent record.
        try {
            persistence.updateProfile(createSponsorProfile(USER_ID, false));
            Assert.fail("UnknownProfileException should be thrown");
        } catch (UnknownProfileException e) {
            Assert.assertEquals("The missing entry ID is incorrect", USER_ID, e.getUnknownId());
            // Success.
        }
    }

    /**
     * <p>
     * Tests the retrieveProfile(long id) method with an ID corresponding to a
     * player user profile present in the persistent store, but does not have
     * any contact information associated with it. The information in the
     * returned profile should be equal to that in the persistent store.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProfileWithValidPlayerIdArg() throws Exception {
        performRetrieveProfileTestWithValidArg(UserConstants.PLAYER_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the retrieveProfile(long id) method with an ID corresponding to a
     * player user profile present in the persistent store, and which has
     * contact information associated with it. The information in the returned
     * profile should be equal to that in the persistent store.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProfileWithValidPlayerIdArgWithContactInfo() throws Exception {
        performRetrieveProfileTestWithValidArg(UserConstants.PLAYER_TYPE_NAME, true);
    }

    /**
     * <p>
     * Tests the retrieveProfile(long id) method with an ID corresponding to an
     * admin user profile present in the persistent store. The information in
     * the returned profile should be equal to that in the persistent store.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProfileWithValidAdminIdArg() throws Exception {
        performRetrieveProfileTestWithValidArg(UserConstants.ADMIN_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the retrieveProfile(long id) method with an ID corresponding to a
     * sponsor user profile present in the persistent store, but does not have
     * any contact information associated with it. The information in the
     * returned profile should be equal to that in the persistent store.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProfileWithValidSponsorIdArg() throws Exception {
        performRetrieveProfileTestWithValidArg(UserConstants.SPONSOR_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the retrieveProfile(long id) method with an ID corresponding to a
     * sponsor user profile present in the persistent store, and which has
     * contact information associated with it. The information in the returned
     * profile should be equal to that in the persistent store.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProfileWithValidSponsorIdArgWithContactInfo() throws Exception {
        performRetrieveProfileTestWithValidArg(UserConstants.SPONSOR_TYPE_NAME, true);
    }

    /**
     * <p>
     * Performs the retrieveProfile(long id) method test. First a user profile
     * of the type specified by the userTypeName argument (player, admin or
     * sponsor) is inserted into the persistent store. If the userTypeName
     * specifies a player or sponsor profile type and the addContactInfo
     * argument is true, then contact information will be associated with the
     * user profile. The fields of the user profile returned by the
     * retrieveProfile(long id) method is then checked to make sure they are the
     * same as those in the persistent store.
     * </p>
     *
     * @param userTypeName the user profile type name of the profile to test
     *        (player, sponsor or admin)
     * @param addContactInfo whether the user profile should have contact
     *        information associated with it (only applies to player and sponsor
     *        profiles)
     * @throws Exception if the test fails due to a persistence error
     */
    private void performRetrieveProfileTestWithValidArg(String userTypeName, boolean addContactInfo) throws Exception {
        // Create the profile to retrieve.
        UserProfile profile = null;
        if (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)) {
            profile = createPlayerProfile(USER_ID, addContactInfo);
        } else if (userTypeName.equals(UserConstants.ADMIN_TYPE_NAME)) {
            profile = createAdminProfile(USER_ID);
        } else if (userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME)) {
            profile = createSponsorProfile(USER_ID, addContactInfo);
        }

        // Make sure the profile is in the persistent store.
        persistence.insertProfile(profile);

        // Retrieve the profile
        UserProfile profile2 = persistence.retrieveProfile(USER_ID);

        // Check that the returned profile is equal to the inserted one.
        try {
            compareUserProfiles(profile, profile2, userTypeName, addContactInfo);
        } finally {
            // Clean-up by deleting the inserted profile.
            persistence.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Checks that the two given UserProfile instances are equal. If they do not
     * contain the same information, a JUnit assertion is generated. The
     * userTypeName argument specifies what type of user profile the two
     * UserProfile instances represent - whether they are player, admin or
     * sponsor profiles. The hasContactInfo argument specifies whether the
     * UserProfile instances contain contact information.
     * </p>
     *
     * @param profile1 the UserProfile instance to compare to profile2
     * @param profile2 the UserProfile instance to compare to profile1
     * @param userTypeName the type of user profile the two UserProfile
     *        instances represent
     * @param hasContactInfo whether the UserProfile instances contain contact
     *        information
     */
    private void compareUserProfiles(UserProfile profile1, UserProfile profile2, String userTypeName,
            boolean hasContactInfo) {
        if (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)) {
            // Check players.
            Assert.assertEquals("The payment pref is incorrect",
                                profile1.getProperty(UserConstants.PLAYER_PAYMENT_PREF),
                                profile2.getProperty(UserConstants.PLAYER_PAYMENT_PREF));
            if (hasContactInfo) {
                compareContactInfos(profile1, profile2);
            }
        } else if (userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME)) {
            // Check sponsors.
            Assert.assertEquals("The fax is incorrect", profile1.getProperty(UserConstants.SPONSOR_FAX_NUMBER),
                                profile2.getProperty(UserConstants.SPONSOR_FAX_NUMBER));
            Assert.assertEquals("The payment pref is incorrect",
                                profile1.getProperty(UserConstants.SPONSOR_PAYMENT_PREF),
                                profile2.getProperty(UserConstants.SPONSOR_PAYMENT_PREF));
            Assert.assertEquals("The approved flag is incorrect", profile1.getProperty(UserConstants.SPONSOR_APPROVED),
                                profile2.getProperty(UserConstants.SPONSOR_APPROVED));
            if (hasContactInfo) {
                compareContactInfos(profile1, profile2);
            }
        }

        // Check general user information.
        Assert.assertEquals("The user ID is incorrect", profile1.getIdentifier(), profile2.getIdentifier());
        Assert.assertEquals("The handle is incorrect", profile1.getProperty(UserConstants.CREDENTIALS_HANDLE),
                            profile2.getProperty(UserConstants.CREDENTIALS_HANDLE));
        Assert.assertEquals("The email is incorrect", profile1.getProperty(BaseProfileType.EMAIL_ADDRESS),
                            profile2.getProperty(BaseProfileType.EMAIL_ADDRESS));
        Assert.assertEquals("The password is incorrect", profile1.getProperty(UserConstants.CREDENTIALS_PASSWORD),
                            profile2.getProperty(UserConstants.CREDENTIALS_PASSWORD));
        Assert.assertEquals("The active flag is incorrect", profile1.getProperty(UserConstants.CREDENTIALS_IS_ACTIVE),
                            profile2.getProperty(UserConstants.CREDENTIALS_IS_ACTIVE));
    }

    /**
     * <p>
     * Checks that the two given UserProfile instances are have the same contact
     * information. If they are not the same, a JUnit assertion is generated.
     * </p>
     *
     * @param profile1 the UserProfile instance to compare to profile2
     * @param profile2 the UserProfile instance to compare to profile1
     */
    private void compareContactInfos(UserProfile profile1, UserProfile profile2) {
        Assert.assertEquals("The address1 field is incorrect", profile1.getProperty(UserConstants.ADDRESS_STREET_1),
                            profile2.getProperty(UserConstants.ADDRESS_STREET_1));
        Assert.assertEquals("The address2 field is incorrect", profile1.getProperty(UserConstants.ADDRESS_STREET_2),
                            profile2.getProperty(UserConstants.ADDRESS_STREET_2));
        Assert.assertEquals("The city is incorrect", profile1.getProperty(UserConstants.ADDRESS_CITY),
                            profile2.getProperty(UserConstants.ADDRESS_CITY));
        Assert.assertEquals("The first name is incorrect", profile1.getProperty(BaseProfileType.FIRST_NAME),
                            profile2.getProperty(BaseProfileType.FIRST_NAME));
        Assert.assertEquals("The last name is incorrect", profile1.getProperty(BaseProfileType.LAST_NAME),
                            profile2.getProperty(BaseProfileType.LAST_NAME));
        Assert.assertEquals("The postal code is incorrect", profile1.getProperty(UserConstants.ADDRESS_POSTAL_CODE),
                            profile2.getProperty(UserConstants.ADDRESS_POSTAL_CODE));
        Assert.assertEquals("The state is incorrect", profile1.getProperty(UserConstants.ADDRESS_STATE),
                            profile2.getProperty(UserConstants.ADDRESS_STATE));
        Assert.assertEquals("The telephone is incorrect", profile1.getProperty(UserConstants.ADDRESS_PHONE_NUMBER),
                            profile2.getProperty(UserConstants.ADDRESS_PHONE_NUMBER));
    }

    /**
     * <p>
     * Tests that the retrieveProfile(long id) method throws an
     * UnknownProfileException when the user profile corresponding to the
     * argument does not exist in the persistent store. The return value of the
     * UnknownProfileException.getUnknownId() method should be equal to the
     * argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProfileWithArgNotInPersistence() throws Exception {
        // Try to retrieve the nonexistent record.
        try {
            persistence.retrieveProfile(USER_ID);
            Assert.fail("UnknownProfileException should be thrown");
        } catch (UnknownProfileException e) {
            Assert.assertEquals("The unknown profifile ID is incorrect", USER_ID, e.getUnknownId());
            // Success.
        }
    }

    /**
     * <p>
     * Tests the deleteProfile(long id) method with an argument corresponding to
     * a player profile present in the persistent store, but which does not have
     * any contact information associated with it. After the delete operation,
     * invoking the retrieveProfile(long id) method with the method argument
     * should result in an UnknownProfileException being thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProfileWithValidPlayerIdArg() throws Exception {
        performDeleteProfileTestWithValidArg(UserConstants.PLAYER_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the deleteProfile(long id) method with an argument corresponding to
     * a player profile present in the persistent store that has contact
     * information associated with it. After the delete operation, invoking the
     * retrieveProfile(long id) method with the method argument should result in
     * an UnknownProfileException being thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProfileWithValidPlayerIdArgWithContactInfo() throws Exception {
        performDeleteProfileTestWithValidArg(UserConstants.PLAYER_TYPE_NAME, true);
    }

    /**
     * <p>
     * Tests the deleteProfile(long id) method with an argument corresponding to
     * an admin profile present in the persistent store. After the delete
     * operation, invoking the retrieveProfile(long id) method with the method
     * argument should result in an UnknownProfileException being thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProfileWithValidAdminIdArg() throws Exception {
        performDeleteProfileTestWithValidArg(UserConstants.ADMIN_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the deleteProfile(long id) method with an argument corresponding to
     * a sponsor profile present in the persistent store, but which does not
     * have any contact information associated with it. After the delete
     * operation, invoking the retrieveProfile(long id) method with the method
     * argument should result in an UnknownProfileException being thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProfileWithValidSponsorIdArg() throws Exception {
        performDeleteProfileTestWithValidArg(UserConstants.SPONSOR_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the deleteProfile(long id) method with an argument corresponding to
     * a sponsor profile present in the persistent store that has contact
     * information associated with it. After the delete operation, invoking the
     * retrieveProfile(long id) method with the method argument should result in
     * an UnknownProfileException being thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProfileWithValidSponsorIdArgWithContactInfo() throws Exception {
        performDeleteProfileTestWithValidArg(UserConstants.SPONSOR_TYPE_NAME, true);
    }

    /**
     * <p>
     * Performs the deleteProfile(long id) method test. First a user profile of
     * the type specified by the userTypeName argument (player, admin or
     * sponsor) is inserted into the persistent store. If the userTypeName
     * specifies a player or sponsor profile type and the addContactInfo
     * argument is true, then contact information will be associated with the
     * user profile. After the deleteProfile(long id) method is invoked, the
     * retrieveProfile(long id) method should throw an UnknownProfileException.
     * </p>
     *
     * @param userTypeName the user profile type name of the profile to test
     *        (player, sponsor or admin)
     * @param addContactInfo whether the user profile should have contact
     *        information associated with it (only applies to player and sponsor
     *        profiles)
     * @throws Exception if the test fails due to a persistence error
     */
    private void performDeleteProfileTestWithValidArg(String userTypeName, boolean addContactInfo) throws Exception {
        // Create the profile to delete.
        UserProfile profile = null;
        if (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)) {
            profile = createPlayerProfile(USER_ID, addContactInfo);
        } else if (userTypeName.equals(UserConstants.ADMIN_TYPE_NAME)) {
            profile = createAdminProfile(USER_ID);
        } else if (userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME)) {
            profile = createSponsorProfile(USER_ID, addContactInfo);
        }

        // Insert the profile to delete.
        persistence.insertProfile(profile);

        // Delete the profile
        persistence.deleteProfile(USER_ID);

        // Check that the profile has been deleted.
        try {
            persistence.retrieveProfile(USER_ID);
            Assert.fail("The profile was not deleted successfully");
        } catch (UnknownProfileException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the deleteProfile(long id) method throws an
     * UnknownProfileException when the user profile corresponding to the
     * argument does not exist in the persistent store. The return value of the
     * UnknownProfileException.getUnknownId() method should be equal to the
     * argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProfileWithArgNotInPersistence() throws Exception {
        // Try to delete the nonexistant record.
        try {
            persistence.deleteProfile(USER_ID);
            Assert.fail("UnknownProfileException should be thrown");
        } catch (UnknownProfileException e) {
            Assert.assertEquals("The unknown ID is incorrect", USER_ID, e.getUnknownId());
            // Success.
        }
    }

    /**
     * <p>
     * Tests the findProfiles(Map criteria) method with criteria to find all
     * non-active players, sponsors and admins. The returned array is iterated
     * to check that all the user profiles returned at have properties matching
     * the search criteria.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithValidArgMatchingProfiles1() throws Exception {
        int numRandomProfiles = 12;
        int numMatchingProfiles = 31;
        long idOffset = USER_ID;

        // Insert some random (non-matching) profiles into the database.
        populateDatabase(numRandomProfiles, idOffset);

        String active = "false";

        // Insert a bunch of matching user profiles.
        for (int i = 0; i < numMatchingProfiles; i++) {
            long id = idOffset + numRandomProfiles + i;
            UserProfile profile = null;
            if (i % 2 == 0) {
                profile = createAdminProfile(id);
            } else if (i % 3 == 0) {
                profile = createPlayerProfile(id, false);
            } else {
                profile = createSponsorProfile(id, true);
            }
            profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, active);

            persistence.insertProfile(profile);
        }

        // Find all non-active user profiles.
        Map criteria = new HashMap();
        criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, active);

        try {
            UserProfile[] profiles = persistence.findProfiles(criteria);

            // At least numMatchingProfiles profiles should have been returned.
            // There may be more that were not created by this unit test.
            Assert.assertTrue("The number of profiles found is incorrect", profiles.length >= numMatchingProfiles);

            // Iterate through all the returned profiles and check that they are
            // correct.
            for (int i = 0; i < profiles.length; i++) {
                Assert.assertEquals("The active flag is incorrect", active,
                                    profiles[i].getProperty(UserConstants.CREDENTIALS_IS_ACTIVE));
            }
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            int numProfiles = numRandomProfiles + numMatchingProfiles;
            for (int i = 0; i < numProfiles; i++) {
                persistence.deleteProfile(idOffset + i);
            }
        }
    }

    /**
     * <p>
     * Tests the findProfiles(Map criteria) method with criteria to find all
     * active players who have "Wire transfer" set has their payment preference.
     * The returned array is iterated to check that all the user profiles
     * returned at have properties matching the search criteria.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithValidArgMatchingProfiles2() throws Exception {
        int numRandomProfiles = 12;
        int numMatchingProfiles = 31;
        long idOffset = USER_ID;

        // Insert some random (non-matching) profiles into the database.
        populateDatabase(numRandomProfiles, idOffset);

        String active = "true";
        String paymentPref = "Wire transfer";

        // Insert a bunch of matching profiles.
        for (int i = 0; i < numMatchingProfiles; i++) {
            UserProfile profile = createPlayerProfile(idOffset + numRandomProfiles + i, true);
            profile.getProperty(UserConstants.PLAYER_PAYMENT_PREF, paymentPref);
            persistence.insertProfile(profile);
        }

        // Find all active players profiles with "Wire transfer" payment
        // preference.
        Map criteria = new HashMap();
        criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, active);
        criteria.put(UserConstants.PLAYER_PAYMENT_PREF, paymentPref);

        try {
            UserProfile[] profiles = persistence.findProfiles(criteria);

            // At least numMatchingProfiles profiles should have been returned.
            // There may be more that were not created by this unit test.
            Assert.assertTrue("The number of profiles found is incorrect", profiles.length >= numMatchingProfiles);

            // Iterate through all the returned profiles and check that they are
            // correct.
            for (int i = 0; i < profiles.length; i++) {
                Assert.assertEquals("The active flag is incorrect", active,
                                    profiles[i].getProperty(UserConstants.CREDENTIALS_IS_ACTIVE));
                Assert.assertEquals("The payment pref of the player is incorrect", paymentPref,
                                    profiles[i].getProperty(UserConstants.PLAYER_PAYMENT_PREF));
            }
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            int numProfiles = numRandomProfiles + numMatchingProfiles;
            for (int i = 0; i < numProfiles; i++) {
                persistence.deleteProfile(idOffset + i);
            }
        }
    }

    /**
     * <p>
     * Tests the findProfiles(Map criteria) method with criteria to find all
     * non-active players and sponsors in Los Angeles, California whose handle
     * is "joeuser". The returned array is iterated to check that all the user
     * profiles returned at have properties matching the search criteria.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithValidArgMatchingProfiles3() throws Exception {
        int numRandomProfiles = 12;
        int numMatchingProfiles = 31;
        long idOffset = USER_ID;

        // Insert some random (non-matching) profiles into the database.
        populateDatabase(numRandomProfiles, idOffset);

        String active = "false";
        String handle = "joeuser";
        String city = "Los Angeles";
        String state = "California";

        // Insert a bunch of matching profiles.
        for (int i = 0; i < numMatchingProfiles; i++) {
            long id = idOffset + numRandomProfiles + i;

            UserProfile profile = null;
            if (i % 3 == 0) {
                profile = createPlayerProfile(id, true);
            } else {
                profile = createSponsorProfile(id, true);
            }

            profile.setProperty(UserConstants.CREDENTIALS_HANDLE, handle);
            profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, active);
            profile.setProperty(UserConstants.ADDRESS_CITY, city);
            profile.setProperty(UserConstants.ADDRESS_STATE, state);

            persistence.insertProfile(profile);
        }

        // Find all non-active players and sponsors profiles in Los Angeles,
        // California that have the handle, "joeuser".
        Map criteria = new HashMap();
        criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, active);
        criteria.put(UserConstants.CREDENTIALS_HANDLE, handle);
        criteria.put(UserConstants.ADDRESS_CITY, city);
        criteria.put(UserConstants.ADDRESS_STATE, state);

        try {
            UserProfile[] profiles = persistence.findProfiles(criteria);

            // At least numMatchingProfiles profiles should have been returned.
            // There may be more that were not created by this unit test.
            Assert.assertTrue("The number of profiles found is incorrect", profiles.length >= numMatchingProfiles);

            // Iterate through all the returned profiles and check that they are
            // correct.
            for (int i = 0; i < profiles.length; i++) {
                Assert.assertEquals("The handle is incorrect", handle,
                                    profiles[i].getProperty(UserConstants.CREDENTIALS_HANDLE));
                Assert.assertEquals("The active flag is incorrect", active,
                                    profiles[i].getProperty(UserConstants.CREDENTIALS_IS_ACTIVE));
                Assert.assertEquals("The city is incorrect", city, profiles[i].getProperty(UserConstants.ADDRESS_CITY));
                Assert.assertEquals("The state is incorrect", state,
                                    profiles[i].getProperty(UserConstants.ADDRESS_STATE));
            }
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            int numProfiles = numRandomProfiles + numMatchingProfiles;
            for (int i = 0; i < numProfiles; i++) {
                persistence.deleteProfile(idOffset + i);
            }
        }
    }

    /**
     * <p>
     * Tests the findProfiles(Map criteria) method with criteria to find all
     * approved sponsors who live in Los Angeles, California with a fax number
     * of 1234, and who have "Cheque" set as their payment preference. The
     * returned array is iterated to check that all the user profiles returned
     * at have properties matching the search criteria.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithValidArgMatchingProfiles4() throws Exception {
        int numRandomProfiles = 12;
        int numMatchingProfiles = 31;
        long idOffset = USER_ID;

        // Insert some random (non-matching) profiles into the database.
        populateDatabase(numRandomProfiles, idOffset);

        String fax = "1234";
        String paymentPref = "Cheque";
        String approved = Sponsor.APPROVED_TRUE;
        String city = "Los Angeles";
        String state = "California";

        // Insert a bunch of matching profiles.
        for (int i = 0; i < numMatchingProfiles; i++) {
            long id = idOffset + numRandomProfiles + i;

            UserProfile profile = createSponsorProfile(id, true);
            profile.setProperty(UserConstants.SPONSOR_FAX_NUMBER, fax);
            profile.setProperty(UserConstants.SPONSOR_PAYMENT_PREF, paymentPref);
            profile.setProperty(UserConstants.SPONSOR_APPROVED, approved);
            profile.setProperty(UserConstants.ADDRESS_CITY, city);
            profile.setProperty(UserConstants.ADDRESS_STATE, state);

            persistence.insertProfile(profile);
        }

        // Find all approved sponsors in Los Angeles, California who have the
        // fax number, 1234, and who have "Cheque" set as their payment
        // preference.
        Map criteria = new HashMap();
        criteria.put(UserConstants.SPONSOR_FAX_NUMBER, fax);
        criteria.put(UserConstants.SPONSOR_PAYMENT_PREF, paymentPref);
        criteria.put(UserConstants.SPONSOR_APPROVED, approved);
        criteria.put(UserConstants.ADDRESS_CITY, city);
        criteria.put(UserConstants.ADDRESS_STATE, state);

        try {
            UserProfile[] profiles = persistence.findProfiles(criteria);

            // At least numMatchingProfiles profiles should have been returned.
            // There may be more that were not created by this unit test.
            Assert.assertTrue("The number of profiles found is incorrect: " + profiles.length,
                              profiles.length >= numMatchingProfiles);

            // Iterate through all the returned profiles and check that they are
            // correct.
            for (int i = 0; i < profiles.length; i++) {
                Assert.assertEquals("The fax number is incorrect", fax,
                                    profiles[i].getProperty(UserConstants.SPONSOR_FAX_NUMBER));
                Assert.assertEquals("The payment preference is incorrect", paymentPref,
                                    profiles[i].getProperty(UserConstants.SPONSOR_PAYMENT_PREF));
                Assert.assertEquals("The approved flag is incorrect", approved,
                                    profiles[i].getProperty(UserConstants.SPONSOR_APPROVED));
                Assert.assertEquals("The city is incorrect", city, profiles[i].getProperty(UserConstants.ADDRESS_CITY));
                Assert.assertEquals("The state is incorrect", state,
                                    profiles[i].getProperty(UserConstants.ADDRESS_STATE));
            }
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            int numProfiles = numRandomProfiles + numMatchingProfiles;
            for (int i = 0; i < numProfiles; i++) {
                persistence.deleteProfile(idOffset + i);
            }
        }
    }

    /**
     * <p>
     * Tests that the findProfiles(Map criteria) method returns all the user
     * profiles in the database when the argument is an empty map. A number of
     * user profiles are inserted at the start of the test. Then, the
     * findProfiles(Map criteria) method is called, and the returned array is
     * iterated to check that all the user profiles added at the start of the
     * test are in the returned array.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithValidEmptyArg() throws Exception {
        int numProfiles = 47;
        long idOffset = USER_ID;

        populateDatabase(numProfiles, idOffset);

        try {
            // Get all the profiles.
            UserProfile[] profiles = persistence.findProfiles(new HashMap());

            // At least numProfiles profiles should have been returned. There
            // may be more that were not created by this unit test.
            Assert.assertTrue("The number of profiles found is incorrect", profiles.length >= numProfiles);

            // Iterate through all the returned profiles and check if the
            // numProfiles profiles are there.
            int numReturned = 0;
            for (int i = 0; i < profiles.length; i++) {
                long id = ((Long) profiles[i].getIdentifier()).longValue();
                if (id >= idOffset && id <= idOffset + (numProfiles - 1)) {
                    numReturned++;
                }
            }
            Assert.assertEquals("Not all the profiles were returned", numProfiles, numReturned);
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            for (int i = 0; i < numProfiles; i++) {
                persistence.deleteProfile(idOffset + i);
            }
        }
    }

    /**
     * <p>
     * Tests that the findProfiles(Map criteria) method returns an empty array
     * when no user profiles in the database match the given criteria. In this
     * test, a single criterion is used: a "IDontExist" user handle.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithValidArgNoMatchingProfiles() throws Exception {
        int numProfiles = 10;
        long idOffset = USER_ID;

        // Make sure there are some profiles in the database.
        populateDatabase(numProfiles, idOffset);

        // Create the criteria map.
        Map criteria = new HashMap();
        criteria.put(UserConstants.CREDENTIALS_HANDLE, "IDontExist");

        try {
            // Find the profiles.
            UserProfile[] profiles = persistence.findProfiles(criteria);

            Assert.assertEquals("No profiles should have been found", 0, profiles.length);
        } finally {
            // Clean-up by deleting the inserted profiles.
            for (int i = 0; i < numProfiles; i++) {
                persistence.deleteProfile(idOffset + i);
            }
        }
    }

    /**
     * <p>
     * Tests that the findProfile(Map criteria) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithNullArg() throws Exception {
        try {
            persistence.findProfiles(null);
            Assert.fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the findProfiles(Map criteria) method throws an
     * IllegalArgumentException when the argument contains null keys.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithArgContainingNullKeys() throws Exception {
        Map criteria = new HashMap();
        criteria.put(UserConstants.ADDRESS_CITY, "Some City");
        criteria.put(null, "value");
        criteria.put(UserConstants.SPONSOR_APPROVED, "true");

        try {
            persistence.findProfiles(criteria);
            Assert.fail("IllegalArgumentException should be thrown: map argument has null keys");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the findProfiles(Map criteria) method throws an
     * IllegalArgumentException when the argument contains non-String keys.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithArgContainingNonStringKeys() throws Exception {
        Map criteria = new HashMap();
        criteria.put(UserConstants.ADDRESS_CITY, "Some City");
        criteria.put(new Long(0), "value");
        criteria.put(UserConstants.SPONSOR_APPROVED, "true");

        try {
            persistence.findProfiles(criteria);
            Assert.fail("IllegalArgumentException should be thrown: map argument has non-String keys");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the findProfiles(Map criteria) method throws an
     * IllegalArgumentException when the argument contains empty string keys.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithArgContainingEmptyKeys() throws Exception {
        Map criteria = new HashMap();
        criteria.put(UserConstants.ADDRESS_CITY, "Some City");
        criteria.put(" ", "value");
        criteria.put(UserConstants.SPONSOR_APPROVED, "true");

        try {
            persistence.findProfiles(criteria);
            Assert.fail("IllegalArgumentException should be thrown: map argument has empty string keys");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the findProfiles(Map criteria) method throws an
     * IllegalArgumentException when the argument contains a key that does not
     * correspond to any table column name (i.e. is not a property constant
     * defined in the UserConstants interface).
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithArgContainingInvalidKey() throws Exception {
        Map criteria = new HashMap();
        criteria.put(UserConstants.ADDRESS_CITY, "Some City");
        criteria.put("my_table_column", "value");
        criteria.put(UserConstants.SPONSOR_APPROVED, "true");

        try {
            persistence.findProfiles(criteria);
            Assert.fail("IllegalArgumentException should be thrown: no table column name mapping to criterion");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests the retrieveAllProfiles() method. A number of user profiles are
     * inserted at the start of the test. Then, the retrieveAllProfiles() method
     * is called, and the returned array is iterated to check that all the user
     * profiles added at the start of the test are in the returned array.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllProfiles() throws Exception {
        int numProfiles = 47;
        long idOffset = USER_ID;

        // Insert a bunch of user profiles.
        populateDatabase(numProfiles, idOffset);

        // Get all the profiles.
        UserProfile[] profiles = persistence.retrieveAllProfiles();

        try {
            // At least numProfiles profiles should have been returned. There
            // may be more that were not created by this unit test.
            Assert.assertTrue("The number of profiles is incorrect", profiles.length >= numProfiles);

            // Iterate through all the returned profiles and check if the
            // numProfiles profiles are there.
            int numReturned = 0;
            for (int i = 0; i < profiles.length; i++) {
                long id = ((Long) profiles[i].getIdentifier()).longValue();
                if (id >= idOffset && id <= idOffset + (numProfiles - 1)) {
                    numReturned++;
                }
            }
            Assert.assertEquals("Not all the profiles were returned", numProfiles, numReturned);
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            for (int i = 0; i < numProfiles; i++) {
                persistence.deleteProfile(idOffset + i);
            }
        }
    }

    /**
     * <p>
     * Populates the persistent store with the specified number of user
     * profiles, starting at the given ID.
     * </p>
     *
     * @param numProfiles the number of user profiles to insert into the
     *        persistent store
     * @param idOffset the ID of the first user profile to insert into the
     *        persistent store (the last ID will be idOffset + numProfiles)
     * @throws Exception if populating the database fails
     */
    private void populateDatabase(int numProfiles, long idOffset) throws Exception {
        for (int i = 0; i < numProfiles; i++) {
            UserProfile profile = null;
            long id = idOffset + i;
            if (i % 2 == 0) {
                profile = createAdminProfile(id);
            } else if (i % 3 == 0) {
                profile = createPlayerProfile(id, false);
            } else {
                profile = createSponsorProfile(id, true);
            }

            persistence.insertProfile(profile);
        }
    }

    /**
     * <p>
     * Creates a player user profile with the specified ID that can be used for
     * testing purposes. If the addContactInfo parameter is true, then contact
     * information associated with the player is added to the user profile as
     * well.
     * </p>
     *
     * @param id the ID of the player user profile
     * @param addContactInfo whether the contact information associated with the
     *        player should be added
     * @return the player user profile with the specified ID
     * @throws Exception if creating the test player user profile fails
     */
    private UserProfile createPlayerProfile(long id, boolean addContactInfo) throws Exception {
        UserProfile profile = new UserProfile(new Long(id));
        profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.PLAYER_TYPE_NAME));
        profile.setProperty(UserConstants.PLAYER_PAYMENT_PREF, "Wire transfer");
        setUserInfo(profile);
        if (addContactInfo) {
            addContactInfo(profile);
        }
        return profile;
    }

    /**
     * <p>
     * Creates an admin user profile with the specified ID that can be used for
     * testing purposes.
     * </p>
     *
     * @param id the ID of the admin user profile
     * @return the admin user profile with the specified ID
     * @throws Exception if creating the test admin user profile fails
     */
    private UserProfile createAdminProfile(long id) throws Exception {
        UserProfile profile = new UserProfile(new Long(id));
        profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.ADMIN_TYPE_NAME));
        setUserInfo(profile);
        return profile;
    }

    /**
     * <p>
     * Creates a sponsor user profile with the specified ID that can be used for
     * testing purposes. If the addContactInfo parameter is true, then contact
     * information associated with the sponsor is added to the user profile as
     * well.
     * </p>
     *
     * @param id the ID of the sponsor user profile
     * @param addContactInfo whether the contact information associated with the
     *        sponsor should be added
     * @return the sponsor user profile with the specified ID
     * @throws Exception if creating the test sponsor user profile fails
     */
    private UserProfile createSponsorProfile(long id, boolean addContactInfo) throws Exception {
        UserProfile profile = new UserProfile(new Long(id));
        profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.SPONSOR_TYPE_NAME));
        profile.setProperty(UserConstants.SPONSOR_FAX_NUMBER, "83789571891");
        profile.setProperty(UserConstants.SPONSOR_PAYMENT_PREF, "Bank deposit");
        profile.setProperty(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_FALSE);
        setUserInfo(profile);
        if (addContactInfo) {
            addContactInfo(profile);
        }

        return profile;
    }

    /**
     * <p>
     * Sets the user information properties in the given user profile with
     * values that can be used for testing.
     * </p>
     *
     * @param profile the user profile in which the user information should be
     *        set
     * @throws Exception if setting the user information in the user profile
     *         fails
     */
    private void setUserInfo(UserProfile profile) throws Exception {
        profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.CREDENTIALS_TYPE_NAME));
        profile.setProperty(UserConstants.CREDENTIALS_HANDLE, "tcsdeveloper");
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "tcsdeveloper@topcodersoftware.com");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "repolevedsct");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "true");
    }

    /**
     * <p>
     * Adds contact information to the given user profile that can be used for
     * testing purposes.
     * </p>
     *
     * @param profile the user profile to which contact information should be
     *        added
     * @throws Exception if adding the contact information to the user profile
     *         fails
     */
    private void addContactInfo(UserProfile profile) throws Exception {
        profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.ADDRESS_TYPE_NAME));
        profile.setProperty(BaseProfileType.FIRST_NAME, "TopCoder");
        profile.setProperty(BaseProfileType.LAST_NAME, "Developer");
        profile.setProperty(UserConstants.ADDRESS_STREET_1, "123 Some Road");
        profile.setProperty(UserConstants.ADDRESS_STREET_2, "A Suburb");
        profile.setProperty(UserConstants.ADDRESS_CITY, "Mycity");
        profile.setProperty(UserConstants.ADDRESS_STATE, "Mystate");
        profile.setProperty(UserConstants.ADDRESS_POSTAL_CODE, "8713");
        profile.setProperty(UserConstants.ADDRESS_PHONE_NUMBER, "981-3472-8475");
    }

}
