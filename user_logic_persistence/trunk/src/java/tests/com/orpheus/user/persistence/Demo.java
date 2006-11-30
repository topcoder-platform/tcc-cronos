/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.ServletTestCase;

import com.orpheus.user.persistence.impl.Sponsor;
import com.orpheus.user.persistence.impl.UserProfileTranslator;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.ConfigProfileTypeFactory;
import com.topcoder.user.profile.manager.UnknownProfileException;
import com.topcoder.user.profile.manager.UserProfileManagerException;
import com.topcoder.validation.emailconfirmation.ConfirmationMessage;
import com.topcoder.validation.emailconfirmation.InvalidAddressException;

/**
 * <p>
 * Demonstrates the usage of the User Logic Persistence component as shown in
 * section 4.3 in the component specification.
 * </p>
 * <p>
 * This demo only shows the usage of the remote clients, as the usage of the
 * local clients is the same.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 */
public class Demo extends ServletTestCase {

    /**
     * <p>
     * The namespace for the OrpheusPendingConfirmationStorage configuration.
     * </p>
     */
    private static final String PENDINGCONFIRMATION_CLIENT_NAMESPACE
        = OrpheusPendingConfirmationStorage.class.getName() + ".remote";

    /**
     * <p>
     * The Object Factory namespace for the OrpheusPendingConfirmationStorage
     * client.
     * </p>
     */
    private static final String PENDINGCONFIRMATION_OBJFACTORY_NAMESPACE
        = OrpheusPendingConfirmationStorage.class.getName() + ".objFactory";

    /**
     * <p>
     * The configuration file containing the OrpheusPendingConfirmationStorage
     * namespace.
     * </p>
     */
    private static final String PENDINGCONFIRMATION_CLIENT_CONFIG
        = "test_conf/valid_pendingconfirmationclient_config.xml";

    /**
     * <p>
     * A test DAOFactory configuration file containing valid configuration.
     * </p>
     */
    private static final String DAOFACTORY_CONFIG_FILE
        = "test_conf/valid_daofactory_config.xml";

    /**
     * <p>
     * The prefix for the test DAOFactory configuration namespaces.
     * </p>
     */
    private static final String DAOFACTORY_NAMESPACE_PREFIX
        = "com.orpheus.user.persistence.DAOFactory";

    /**
     * <p>
     * The namespace for the OrpheusUserProfilePersistence configuration.
     * </p>
     */
    private static final String USERPROFILE_CLIENT_NAMESPACE
        = OrpheusUserProfilePersistence.class.getName() + ".remote";

    /**
     * <p>
     * The Object Factory namespace for the OrpheusUserProfilePersistence
     * client.
     * </p>
     */
    private static final String USERPROFILE_OBJFACTORY_NAMESPACE
        = OrpheusUserProfilePersistence.class.getName() + ".objFactory";

    /**
     * <p>
     * The configuration file containing the OrpheusUserProfilePersistence
     * namespace.
     * </p>
     */
    private static final String USERPROFILE_CLIENT_CONFIG
        = "test_conf/valid_userprofileclient_config.xml";

    /**
     * <p>
     * A test DAO configuration file containing valid configuration.
     * </p>
     */
    private static final String DAO_CONFIG_FILE = "test_conf/valid_dao_config.xml";

    /**
     * <p>
     * The prefix for the test DAO configuration namespaces.
     * </p>
     */
    private static final String DAO_NAMESPACE_PREFIX = "com.orpheus.user.persistence.impl.DAO";

    /**
     * <p>
     * A test UserProfileTranslator configuration file containing valid
     * configuration.
     * </p>
     */
    private static final String TRANSLATOR_CONFIG_FILE
        = "test_conf/valid_userprofiletranslator_config.xml";

    /**
     * <p>
     * The prefix for the test UserProfileTranslator configuration namespaces.
     * </p>
     */
    private static final String TRANSLATOR_NAMESPACE_PREFIX = UserProfileTranslator.class.getName();

    /**
     * <p>
     * An array of sample confirmation message addresses to use in the
     * OrpheusPendingConfirmationStorage demo.
     * </p>
     */
    private static final String[] MSG_IDS = {
        "me1@tc.com", "me2@tc.com", "me3@tc.com", "inthere@tc.com"
    };

    /**
     * <p>
     * An array of sample user ID's to use in the OrpheusUserProfilePersistence
     * demo.
     * </p>
     */
    private static final long[] USER_IDS = {
        9223372036854775760L, 9223372036854775761L, 9223372036854775762L
    };

    /**
     * <p>
     * Loads the test configuration namespaces into the ConfigManager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadConfig(DAOFACTORY_CONFIG_FILE, DAOFACTORY_NAMESPACE_PREFIX);
        ConfigHelper.loadConfig(DAOFACTORY_CONFIG_FILE, DAOFACTORY_NAMESPACE_PREFIX + ".objFactory");
        ConfigHelper.loadConfig(DAO_CONFIG_FILE, DAO_NAMESPACE_PREFIX + ".validWithConnName");
        ConfigHelper.loadConfig(DAO_CONFIG_FILE, DAO_NAMESPACE_PREFIX + ".objFactory");
        ConfigHelper.loadCacheConfig();
        ConfigHelper.loadDBConnectionFactoryConfig();
        ConfigHelper.loadLoggerConfig();
        ConfigHelper.loadProfileTypesConfig();
    }

    /**
     * <p>
     * Unloads the test configuration namespaces from the ConfigManager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigHelper.unloadConfig(DAOFACTORY_NAMESPACE_PREFIX);
        ConfigHelper.unloadConfig(DAOFACTORY_NAMESPACE_PREFIX + ".objFactory");
        ConfigHelper.unloadConfig(DAO_NAMESPACE_PREFIX + ".validWithConnName");
        ConfigHelper.unloadConfig(DAO_NAMESPACE_PREFIX + ".objFactory");
        ConfigHelper.unloadCacheConfig();
        ConfigHelper.unloadDBConnectionFactoryConfig();
        ConfigHelper.unloadLoggerConfig();
        ConfigHelper.unloadProfileTypesConfig();
    }

    /**
     * <p>
     * Performs the OrpheusPendingConfirmationStorage client demo.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testPendingConfirmationClientDemo() throws Exception {
        // Load the configuration namespaces.
        ConfigHelper.loadConfig(PENDINGCONFIRMATION_CLIENT_CONFIG, PENDINGCONFIRMATION_CLIENT_NAMESPACE);
        ConfigHelper.loadConfig(PENDINGCONFIRMATION_CLIENT_CONFIG, PENDINGCONFIRMATION_OBJFACTORY_NAMESPACE);

        // Prepare the demo, by inserting the two non-cached messages into the
        // persistent store.
        OrpheusPendingConfirmationStorage client = new RemoteOrpheusPendingConfirmationStorage(
                PENDINGCONFIRMATION_CLIENT_NAMESPACE);
        client.store(createConfirmationMessage(MSG_IDS[2]));
        client.store(createConfirmationMessage(MSG_IDS[3]));

        try {
            // Run the demo.
            runPendingConfirmationClientDemo();
        } finally {
            // Clean-up by making sure all the inserted messages are deleted.
            for (int i = 0; i < MSG_IDS.length; i++) {
                try {
                    client.delete(MSG_IDS[i]);
                } catch (InvalidAddressException e) {
                    // Ignore.
                }
            }

            // Unload the configuration namespaces.
            ConfigHelper.unloadConfig(PENDINGCONFIRMATION_CLIENT_NAMESPACE);
            ConfigHelper.unloadConfig(PENDINGCONFIRMATION_OBJFACTORY_NAMESPACE);
        }
    }

    /**
     * <p>
     * Demonstrates typical usage of the the OrpheusPendingConfirmationStorage
     * client.
     * </p>
     *
     * @throws ObjectInstantiationException if the client cannot be instantiated
     */
    private void runPendingConfirmationClientDemo() throws ObjectInstantiationException {
        // Create a remote client using a particular configuration namespace.
        OrpheusPendingConfirmationStorage client = new RemoteOrpheusPendingConfirmationStorage(
                PENDINGCONFIRMATION_CLIENT_NAMESPACE);

        // Insert some confirmation messages into the persistent store. After
        // the confirmation messages have been inserted, they are cached within
        // client to improve performance when they are requested.
        ConfirmationMessage msg1 = createConfirmationMessage(MSG_IDS[0]);
        ConfirmationMessage msg2 = createConfirmationMessage(MSG_IDS[1]);
        client.store(msg1);
        client.store(msg2);
        assertTrue("The message, " + MSG_IDS[0] + ", was not stored", client.contains(MSG_IDS[0]));
        assertTrue("The message, " + MSG_IDS[1] + ", was not stored", client.contains(MSG_IDS[1]));

        // Retrieve a cached and non-cached confirmation message. The
        // "me3@tc.com" message comes from the persistent store, as it is not
        // cached within the client. After the retrieval, "me3@tc.com" is
        // cached.
        msg1 = client.retrieve(MSG_IDS[0]);
        ConfirmationMessage msg3 = client.retrieve(MSG_IDS[2]);
        assertNotNull("The message, " + MSG_IDS[0] + ", was not retrieved", msg1);
        assertNotNull("The message, " + MSG_IDS[2] + ", was not retrieved", msg3);

        // Test if a confirmation message with a particular address exists in
        // the persistent store. The "" message is cached within the client, if
        // it is not already cached.
        boolean result1 = client.contains(MSG_IDS[3]); // true
        boolean result2 = client.contains("notinthere@tc.com"); // false
        assertTrue("The message, " + MSG_IDS[3] + ", should be in the persistent store", result1);
        assertFalse("The message, notinthere@tc.com, should not be in the persistent store", result2);

        // Delete a confirmation message. The message is deleted from the cache
        // as well.
        client.delete(MSG_IDS[0]);
        assertFalse("The message was not deleted", client.contains(MSG_IDS[0]));

        // Retrieve the addresses of all the confirmation messages in the
        // persistent store. These come directly from the persistent store;
        // the cache is not used. After the addresses have been retrieved, the
        // corresponding confirmation messages are cached within the client.
        Enumeration allAddresses = client.getAddresses();

        // Check that all the addresses were returned.
        int numReturned = 0;
        while (allAddresses.hasMoreElements()) {
            String address = (String) allAddresses.nextElement();
            for (int i = 1; i < MSG_IDS.length; i++) {
                if (address.equals(MSG_IDS[i])) {
                    numReturned++;
                }
            }
        }
        assertEquals("Not all the addresses were returned", MSG_IDS.length - 1, numReturned);
    }

    /**
     * <p>
     * Creates and returns a ConfirmationMessage instance with the specified
     * address to use in the demo.
     * </p>
     *
     * @param address the address of the ConfirmationMessage to create
     * @return a ConfirmationMessage instance with the specified address
     */
    private ConfirmationMessage createConfirmationMessage(String address) {
        return new ConfirmationMessage(address, "unlock-code", new Date(), "Message subject", "Message body");
    }

    /**
     * <p>
     * Runs the OrpheusUserProfilePersistence client demo.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUserProfileClientDemo() throws Exception {
        // Load the configuration namespaces.
        ConfigHelper.loadConfig(USERPROFILE_CLIENT_CONFIG, USERPROFILE_CLIENT_NAMESPACE);
        ConfigHelper.loadConfig(USERPROFILE_CLIENT_CONFIG, USERPROFILE_OBJFACTORY_NAMESPACE);
        ConfigHelper.loadConfig(TRANSLATOR_CONFIG_FILE, TRANSLATOR_NAMESPACE_PREFIX + ".valid");
        ConfigHelper.loadConfig(TRANSLATOR_CONFIG_FILE, TRANSLATOR_NAMESPACE_PREFIX + ".objFactory");

        // Prepare the demo, by inserting the two non-cached users into the
        // persistent store.
        OrpheusUserProfilePersistence client = new RemoteOrpheusUserProfilePersistence(USERPROFILE_CLIENT_NAMESPACE);
        client.insertProfile(createUserProfile(USER_IDS[2]));

        try {
            // Run the demo.
            runUserProfileClientDemo();
        } finally {
            // Clean-up by making sure all the inserted users are deleted.
            for (int i = 0; i < USER_IDS.length; i++) {
                try {
                    client.deleteProfile(USER_IDS[i]);
                } catch (UserProfileManagerException e) {
                    // Ignore
                }
            }

            // Unload the configuration namespaces.
            ConfigHelper.unloadConfig(USERPROFILE_CLIENT_NAMESPACE);
            ConfigHelper.unloadConfig(USERPROFILE_OBJFACTORY_NAMESPACE);
            ConfigHelper.unloadConfig(TRANSLATOR_NAMESPACE_PREFIX + ".valid");
            ConfigHelper.unloadConfig(TRANSLATOR_NAMESPACE_PREFIX + ".objFactory");
        }
    }

    /**
     * <p>
     * Demonstrates typical usage of the OrpheusUserProfilePersistence client.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void runUserProfileClientDemo() throws Exception {
        // Create a remote client using a particular configuration namespace.
        OrpheusUserProfilePersistence client = new RemoteOrpheusUserProfilePersistence(USERPROFILE_CLIENT_NAMESPACE);

        // Insert some users into the persistent store. After they have been
        // inserted, they are cached within client to improve performance when
        // they are requested.
        UserProfile user1 = createUserProfile(USER_IDS[0]);
        UserProfile user2 = createUserProfile(USER_IDS[1]);
        client.insertProfile(user1);
        client.insertProfile(user2);
        assertNotNull("The user, " + USER_IDS[0] + ", was not stored", client.retrieveProfile(USER_IDS[0]));
        assertNotNull("The user, " + USER_IDS[1] + ", was not stored", client.retrieveProfile(USER_IDS[1]));

        // Retrieve a cached and non-cached user profile. User 3 message comes
        // from the persistent store, as it is not cached within the client.
        // After the retrieval, User 3 is cached.
        user1 = client.retrieveProfile(USER_IDS[0]);
        UserProfile user3 = client.retrieveProfile(USER_IDS[2]);
        assertNotNull("The user, " + USER_IDS[0] + ", was not retrieved", user1);
        assertNotNull("The user, " + USER_IDS[2] + ", was not retrieved", user3);

        // Update some of User 3's information. After the update, the new
        // information is cached within the client.
        user3.setProperty(UserConstants.CREDENTIALS_PASSWORD, "mynewpassword");
        client.updateProfile(user3);
        user3 = client.retrieveProfile(USER_IDS[2]);
        assertEquals("The user, " + USER_IDS[2] + ", was not updated", "mynewpassword",
                     user3.getProperty(UserConstants.CREDENTIALS_PASSWORD));

        // Delete a user. The user is deleted from the cache as well.
        client.deleteProfile(USER_IDS[0]);
        try {
            client.retrieveProfile(USER_IDS[0]);
            fail("The user, " + USER_IDS[0] + ", was not deleted");
        } catch (UnknownProfileException e) {
            // Ignore.
        }

        // Retrieve all the approved sponsors. These come directly from the
        // persistent store; the cache is not used. After the sponsors have
        // been retrieved, they are cached within the client.
        Map criteria = new HashMap();
        criteria.put(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_TRUE);
        UserProfile[] approvedSponsors = client.findProfiles(criteria);
        checkAllUsersReturned(approvedSponsors, 1, USER_IDS.length);

        // Retrieve all the users from the persistent store. These come
        // directly from the persistent store; the cache is not used. After
        // the users have been retrieved, they are cached within the client.
        UserProfile[] allUsers = client.retrieveAllProfiles();
        checkAllUsersReturned(allUsers, 1, USER_IDS.length);

        // NOTE: There is no need to use client.commit() at any stage, because
        // all changes are written through to the persistent store immediately.
    }

    /**
     * <p>
     * Checks that the given array contains a user profile for each user ID in
     * the USER_IDS array, starting at the specified index in the USER_IDS
     * array, and ending at the specified end index.
     * </p>
     *
     * @param profiles the array of user profiles to check
     * @param start the start index in the USER_IDS array
     * @param end the end index in the USER_IDS array
     */
    private void checkAllUsersReturned(UserProfile[] profiles, int start, int end) {
        int numReturned = 0;
        for (int i = 0; i < profiles.length; i++) {
            for (int j = start; j < end; j++) {
                if (profiles[i].getIdentifier().equals(new Long(USER_IDS[j]))) {
                    numReturned++;
                    break;
                }
            }
        }
        assertEquals("Not all the profiles were returned", end - start, numReturned);
    }

    /**
     * <p>
     * Creates and returns a UserProfile instance with the specified ID to use
     * in the demo.
     * </p>
     *
     * @param id the ID of the user profile to create
     * @return a UserProfile instance with the specified ID
     * @throws Exception if creating the UserProfile fails
     */
    private UserProfile createUserProfile(long id) throws Exception {
        ConfigProfileTypeFactory factory = new ConfigProfileTypeFactory("com.topcoder.util.log");

        UserProfile profile = new UserProfile(new Long(id));
        profile.addProfileType(factory.getProfileType(UserConstants.SPONSOR_TYPE_NAME));
        profile.addProfileType(factory.getProfileType(UserConstants.CREDENTIALS_TYPE_NAME));
        profile.setProperty(UserConstants.CREDENTIALS_HANDLE, "demohandle");
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, "mypassword");
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, "true");
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, "myemail@topcoder.com");
        profile.setProperty(UserConstants.SPONSOR_FAX_NUMBER, "9876543210");
        profile.setProperty(UserConstants.SPONSOR_PAYMENT_PREF, "Credit card");
        profile.setProperty(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_TRUE);

        return profile;
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

}
