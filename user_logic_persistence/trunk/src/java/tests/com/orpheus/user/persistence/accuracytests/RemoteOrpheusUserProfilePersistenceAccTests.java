/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests;

import com.orpheus.user.persistence.OrpheusUserProfilePersistence;
import com.orpheus.user.persistence.RemoteOrpheusUserProfilePersistence;
import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.impl.Sponsor;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.ConfigProfileTypeFactory;
import com.topcoder.user.profile.manager.UnknownProfileException;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>RemoteOrpheusUserProfilePersistence</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class RemoteOrpheusUserProfilePersistenceAccTests extends TestCase {

    /**
     * <p>
     * The profile ID used in test cases.
     * </p>
     */
    private static final long TEST_ID = 12345678998765L;

    /**
     * <p>
     * Factory instance to retrieve user profile types.
     * </p>
     */
    private ConfigProfileTypeFactory profileTypeFactory = null;

    /**
     * <p>
     * The LocalOrpheusUserProfilePersistence instance to test.
     * </p>
     */
    protected RemoteOrpheusUserProfilePersistence persistence = null;

    /**
     * <p>
     * Setup for each test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyHelper.clearAllConfigurationNS();

        // load the config files
        AccuracyHelper.loadBaseConfig();
        AccuracyHelper.loadConfiguration("test_conf/accuracy/UserProfileClient.xml");
        AccuracyHelper.loadConfiguration("test_conf/accuracy/UserProfileTranslator.xml");

        // Create the RemoteOrpheusPendingConfirmationStorage instance.
        persistence = new RemoteOrpheusUserProfilePersistence(OrpheusUserProfilePersistence.class.getName()
                + ".remote");

        // initialize the profile factory
        profileTypeFactory = new ConfigProfileTypeFactory("com.topcoder.util.log");
    }

    /**
     * <p>
     * Cleanup for each test cases.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            persistence.deleteProfile(TEST_ID);
        } catch (Exception e) {
            // ignore
        }

        AccuracyHelper.clearAllConfigurationNS();
    }

    /**
     * <p>
     * Accuracy test of the <code>insertProfile()</code> method with an admin profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertAdminProfile() throws Exception {

        UserProfile profile = createAdminProfile(TEST_ID);

        persistence.insertProfile(profile);

        UserProfile result = persistence.retrieveProfile(TEST_ID);

        // check the result
        AccuracyHelper.compareProfiles(profile, result);
    }

    /**
     * <p>
     * Accuracy test of the <code>insertProfile()</code> method with an player profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertPlayerProfile() throws Exception {

        UserProfile profile = createPlayerProfile(TEST_ID);

        persistence.insertProfile(profile);

        UserProfile result = persistence.retrieveProfile(TEST_ID);

        // check the result
        AccuracyHelper.compareProfiles(profile, result);
    }

    /**
     * <p>
     * Accuracy test of the <code>insertProfile()</code> method with an sponsor profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertSponsorProfile() throws Exception {

        UserProfile profile = createSponsorProfile(TEST_ID);

        persistence.insertProfile(profile);

        UserProfile result = persistence.retrieveProfile(TEST_ID);

        // check the result
        AccuracyHelper.compareProfiles(profile, result);
    }

    /**
     * <p>
     * Accuracy test of the <code>deleteProfile()</code> method with an admin profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteAdminProfile() throws Exception {

        UserProfile profile = createAdminProfile(TEST_ID);

        persistence.insertProfile(profile);

        // Delete the profile
        persistence.deleteProfile(TEST_ID);

        // check the result
        try {
            persistence.retrieveProfile(TEST_ID);
            fail("The profile was not deleted.");
        } catch (UnknownProfileException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>deleteProfile()</code> method with a player profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePlayerProfile() throws Exception {

        UserProfile profile = createPlayerProfile(TEST_ID);

        persistence.insertProfile(profile);

        // Delete the profile
        persistence.deleteProfile(TEST_ID);

        // check the result
        try {
            persistence.retrieveProfile(TEST_ID);
            fail("The profile was not deleted.");
        } catch (UnknownProfileException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>deleteProfile()</code> method with a sponsor profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteSponsorProfile() throws Exception {

        UserProfile profile = createSponsorProfile(TEST_ID);

        persistence.insertProfile(profile);

        // Delete the profile
        persistence.deleteProfile(TEST_ID);

        // check the result
        try {
            persistence.retrieveProfile(TEST_ID);
            fail("The profile was not deleted.");
        } catch (UnknownProfileException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>retrieveProfile()</code> method with an admin profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAdminProfile() throws Exception {

        UserProfile profile = createAdminProfile(TEST_ID);

        persistence.insertProfile(profile);

        // Retrieve profile, no error expected
        persistence.retrieveProfile(TEST_ID);
    }

    /**
     * <p>
     * Accuracy test of the <code>retrieveProfile()</code> method with a player profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePlayerProfile() throws Exception {

        UserProfile profile = createPlayerProfile(TEST_ID);

        persistence.insertProfile(profile);

        // Retrieve profile, no error expected
        persistence.retrieveProfile(TEST_ID);
    }

    /**
     * <p>
     * Accuracy test of the <code>retrieveProfile()</code> method with a sponsor profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveSponsorProfile() throws Exception {

        UserProfile profile = createSponsorProfile(TEST_ID);

        persistence.insertProfile(profile);

        // Retrieve profile, no error expected
        persistence.retrieveProfile(TEST_ID);
    }

    /**
     * <p>
     * Accuracy test of the <code>updateProfile()</code> method with an admin profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAdminProfile() throws Exception {

        UserProfile profile = createAdminProfile(TEST_ID);

        persistence.insertProfile(profile);

        // update some data
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "newemail@topcoder.com");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "newpassword");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "false");

        persistence.updateProfile(profile);

        // Retrieve the updated profile
        UserProfile result = persistence.retrieveProfile(TEST_ID);

        // check the result
        AccuracyHelper.compareProfiles(profile, result);
    }

    /**
     * <p>
     * Accuracy test of the <code>updateProfile()</code> method with a player profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePlayerProfile() throws Exception {

        UserProfile profile = createPlayerProfile(TEST_ID);

        persistence.insertProfile(profile);

        // update some data
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "newemail@topcoder.com");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "newpassword");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "false");

        persistence.updateProfile(profile);

        // Retrieve the updated profile
        UserProfile result = persistence.retrieveProfile(TEST_ID);

        // check the result
        AccuracyHelper.compareProfiles(profile, result);
    }

    /**
     * <p>
     * Accuracy test of the <code>updateProfile()</code> method with a sponsor profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateSponsorProfile() throws Exception {

        UserProfile profile = createSponsorProfile(TEST_ID);

        persistence.insertProfile(profile);

        // update some data
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "newemail@topcoder.com");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "newpassword");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "false");

        persistence.updateProfile(profile);

        // Retrieve the updated profile
        UserProfile result = persistence.retrieveProfile(TEST_ID);

        // check the result
        AccuracyHelper.compareProfiles(profile, result);
    }

    /**
     * <p>
     * Creates a player user profile with the given id for testing.
     * </p>
     *
     * @param id the ID of the player user profile.
     *
     * @return the player user profile.
     * @throws Exception to JUnit.
     */
    private UserProfile createPlayerProfile(long id) throws Exception {
        UserProfile profile = new UserProfile(new Long(id));

        profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.PLAYER_TYPE_NAME));
        profile.setProperty(UserConstants.PLAYER_PAYMENT_PREF, "Wire transfer");
        setUserInfo(profile);

        return profile;
    }

    /**
     * <p>
     * Creates an admin user profile with the given id for testing.
     * </p>
     *
     * @param id the ID of the admin user profile.
     * @return the admin user profile.
     * @throws Exception to JUnit.
     */
    private UserProfile createAdminProfile(long id) throws Exception {
        UserProfile profile = new UserProfile(new Long(id));
        profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.ADMIN_TYPE_NAME));
        setUserInfo(profile);

        return profile;
    }

    /**
     * <p>
     * Creates an sponsor user profile with the given id for testing.
     * </p>
     *
     * @param id the ID of the sponsor user profile.
     * @return the sponsor user profile.
     * @throws Exception to JUnit.
     */
    private UserProfile createSponsorProfile(long id) throws Exception {
        UserProfile profile = new UserProfile(new Long(id));
        profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.SPONSOR_TYPE_NAME));
        profile.setProperty(UserConstants.SPONSOR_FAX_NUMBER, "123456789");
        profile.setProperty(UserConstants.SPONSOR_PAYMENT_PREF, "Check");
        profile.setProperty(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_FALSE);
        setUserInfo(profile);

        return profile;
    }

    /**
     * <p>
     * Sets the user information for the given profile.
     * </p>
     *
     * @param profile the user profile to set information.
     * @throws Exception to JUnit.
     */
    private void setUserInfo(UserProfile profile) throws Exception {
        profile.addProfileType(profileTypeFactory.getProfileType(UserConstants.CREDENTIALS_TYPE_NAME));
        profile.setProperty(UserConstants.CREDENTIALS_HANDLE, "tcsdeveloper");
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "tcsdeveloper@topcoder.com");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "1234");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "true");
    }
}
