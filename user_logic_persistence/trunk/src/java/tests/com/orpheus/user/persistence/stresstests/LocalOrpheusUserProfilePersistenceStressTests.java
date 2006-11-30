/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.stresstests;

import org.apache.cactus.ServletTestCase;

import com.orpheus.user.persistence.LocalOrpheusUserProfilePersistence;
import com.orpheus.user.persistence.OrpheusUserProfilePersistence;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.ConfigProfileTypeFactory;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Tests the LocalOrpheusUserProfilePersistence class.
 * </p>
 *
 * @author Thinfox
 * @version 1.0
 */
public class LocalOrpheusUserProfilePersistenceStressTests extends ServletTestCase {
    /**
     * <p>
     * The prefix for the test OrpheusUserProfilePersistence configuration namespaces.
     * </p>
     */
    private static final String NAMESPACE = OrpheusUserProfilePersistence.class.getName()
        + ".local";

    /**
     * <p>
     * A test LocalOrpheusUserProfilePersistence configuration file containing valid configuration.
     * </p>
     */
    private static final String CONFIG_FILE = "test_conf/stress/userprofileclient.xml";

    /**
     * <p>
     * A test UserProfileTranslator configuration file containing valid configuration.
     * </p>
     */
    private static final String TRANSLATOR_CONFIG_FILE = "test_conf/stress/userprofiletranslator.xml";

    /** The number of invocations for stress test. */
    private static final int TOTAL_INVOCATION_COUNT = 50;

    /**
     * <p>
     * A factory for retrieving user profile types when creating test user profiles.
     * </p>
     */
    private ConfigProfileTypeFactory profileTypeFactory = null;

    /**
     * <p>
     * The OrpheusUserProfilePersistence instance to test.
     * </p>
     */
    private OrpheusUserProfilePersistence persistence = null;

    /**
     * <p>
     * Sets the user information properties in the given user profile with values that can be used
     * for testing.
     * </p>
     *
     * @param profile the user profile in which the user information should be set
     * @throws Exception if setting the user information in the user profile fails
     */
    private void setUserInfo(UserProfile profile) throws Exception {
        profile.addProfileType(profileTypeFactory
            .getProfileType(UserConstants.CREDENTIALS_TYPE_NAME));
        profile.setProperty(UserConstants.CREDENTIALS_HANDLE, "tcsdeveloper");
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "tcsdeveloper@topcodersoftware.com");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "repolevedsct");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "true");
    }

    /**
     * <p>
     * Creates an admin user profile with the specified ID that can be used for testing purposes.
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
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        StressTestHelper.loadConfiguration();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add(TRANSLATOR_CONFIG_FILE);
        cm.add(CONFIG_FILE);

        profileTypeFactory = new ConfigProfileTypeFactory("com.topcoder.util.log");

        persistence = new LocalOrpheusUserProfilePersistence(NAMESPACE);

        UserProfile[] profiles = persistence.retrieveAllProfiles();
        for (int i = 0; i < profiles.length; i++) {
            persistence.deleteProfile(((Long) profiles[i].getIdentifier()).longValue());
        }

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            persistence.insertProfile(createAdminProfile(i));
        }
    }

    /**
     * Cleans up the test environment.
     *
     * @throws Exception if error occur while load configuration settings
     */
    public void tearDown() throws Exception {
        UserProfile[] profiles = persistence.retrieveAllProfiles();
        for (int i = 0; i < profiles.length; i++) {
            persistence.deleteProfile(((Long) profiles[i].getIdentifier()).longValue());
        }

        StressTestHelper.cleanConfiguration();
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfile profile) method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInsertProfile() throws Exception {
        UserProfile[] profiles = persistence.retrieveAllProfiles();
        for (int i = 0; i < profiles.length; i++) {
            persistence.deleteProfile(((Long) profiles[i].getIdentifier()).longValue());
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < profiles.length; i++) {
            persistence.insertProfile(profiles[i]);
        }

        System.out.println("Testing insertProfile method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>
     * Tests the retrieveProfile(long id) method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveProfile() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            persistence.retrieveProfile(i);
        }

        System.out.println("Testing retrieveProfile method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");

    }

    /**
     * <p>
     * Tests the updateProfile(UserProfile profile)) method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateProfile() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            persistence.updateProfile(createAdminProfile(i));
        }

        System.out.println("Testing updateProfile method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");

    }

    /**
     * <p>
     * Tests the deleteProfile(long i) method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteProfile() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            persistence.deleteProfile(i);
        }

        System.out.println("Testing deleteProfile method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>
     * Tests the retrieveAllProfiles() method.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveAllProfiles() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            persistence.retrieveAllProfiles();
        }

        System.out.println("Testing retrieveAllProfiles method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }
}
