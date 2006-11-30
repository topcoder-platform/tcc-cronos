/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.orpheus.user.persistence.impl.UserProfileTranslator;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.ConfigProfileTypeFactory;
import com.topcoder.user.profile.manager.UserProfileManagerException;
import com.topcoder.user.profile.manager.persistence.UserProfilePersistence;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * Tests the abstract OrpheusUserProfilePersistence class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class OrpheusUserProfilePersistenceTest extends RemoteOrpheusClientTestBase {

    /**
     * <p>
     * A test OrpheusUserProfilePersistence configuration file containing valid
     * configuration.
     * </p>
     */
    private static final String VALID_CONFIG_FILE
        = "test_conf/valid_userprofileclient_config.xml";

    /**
     * <p>
     * A test UserProfileTranslator configuration file containing valid
     * configuration.
     * </p>
     */
    private static final String VALID_TRANSLATOR_CONFIG_FILE
        = "test_conf/valid_userprofiletranslator_config.xml";

    /**
     * <p>
     * A test OrpheusUserProfilePersistence configuration file containing
     * invalid configuration.
     * </p>
     */
    private static final String INVALID_CONFIG_FILE
        = "test_conf/invalid_userprofileclient_config.xml";

    /**
     * <p>
     * The prefix for the test OrpheusUserProfilePersistence configuration
     * namespaces.
     * </p>
     */
    private static final String NAMESPACE_PREFIX = OrpheusUserProfilePersistence.class.getName();

    /**
     * <p>
     * The prefix for the test UserProfileTranslator configuration namespaces.
     * </p>
     */
    private static final String TRANSLATOR_NAMESPACE_PREFIX = UserProfileTranslator.class.getName();

    /**
     * <p>
     * The OrpheusUserProfilePersistence instance to test.
     * </p>
     */
    private OrpheusUserProfilePersistence persistence = null;

    /**
     * <p>
     * Creates a new OrpheusUserProfilePersistenceTest instance.
     * </p>
     */
    public OrpheusUserProfilePersistenceTest() {
        super(NAMESPACE_PREFIX, NAMESPACE_PREFIX + ".remote", VALID_CONFIG_FILE);
    }

    /**
     * <p>
     * Loads the valid test Object Factory, OrpheusUserProfilePersistence and
     * UserProfileTranslator configuration namespaces into the Configuration
     * Manager, and creates the test OrpheusUserProfilePersistence instance.
     * Since the OrpheusUserProfilePersistence class is abstract, a
     * MockOrpheusUserProfilePersistence instance is instantiated instead.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        // Load the configuration.
        super.setUp();
        ConfigHelper.loadConfig(VALID_TRANSLATOR_CONFIG_FILE, TRANSLATOR_NAMESPACE_PREFIX + ".valid");
        ConfigHelper.loadConfig(VALID_TRANSLATOR_CONFIG_FILE, TRANSLATOR_NAMESPACE_PREFIX + ".objFactory");

        // Create the OrpheusUserProfilePersistence instance.
        persistence = new MockOrpheusUserProfilePersistence(NAMESPACE_PREFIX + ".remote");
    }

    /**
     * <p>
     * Unloads the valid test Object Factory, OrpheusUserProfilePersistence and
     * UserProfileTranslator configuration namespaces from the Configuration
     * Manager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        super.tearDown();
        ConfigHelper.unloadConfig(TRANSLATOR_NAMESPACE_PREFIX + ".valid");
        ConfigHelper.unloadConfig(TRANSLATOR_NAMESPACE_PREFIX + ".objFactory");
    }

    /**
     * <p>
     * Tests the OrpheusUserProfilePersistence(String namespace) constructor.
     * Since the OrpheusUserProfilePersistence class is abstract, a
     * MockOrpheusUserProfilePersistence instance is instantiated instead. The
     * newly created instance should not be null.
     * </p>
     */
    public void testCtorWithValidArg() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The OrpheusUserProfilePersistence instance should not be null", persistence);
    }

    /**
     * <p>
     * Tests that the OrpheusUserProfilePersistence(String namespace) contructor
     * throws an IllegalArgumentException when the argument is null. Since the
     * OrpheusUserProfilePersistence class is abstract, the
     * MockOrpheusUserProfilePersistence(String namespace) constructor is
     * invoked instead.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithNullArg() throws Exception {
        try {
            new MockOrpheusUserProfilePersistence(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the OrpheusUserProfilePersistence(String namespace) contructor
     * throws an IllegalArgumentException when the argument is an empty string.
     * Since the OOrpheusUserProfilePersistence class is abstract, the
     * MockOrpheusUserProfilePersistence(String namespace) constructor is
     * invoked instead.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyArg() throws Exception {
        try {
            new MockOrpheusUserProfilePersistence(" ");
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Performs the OrpheusUserProfilePersistence(String namespace) constructor
     * test with invalid configuration. Since the OrpheusUserProfilePersistence
     * class is abstract, the MockOrpheusUserProfilePersistence(String
     * namespace) constructor is invoked instead.
     * </p>
     * <p>
     * The given invalid configuration namespace is loaded into the
     * Configuration Manager. Then, the constructor is called with the given
     * namespace. A UserProfileManagerException is expected to be thrown.
     * </p>
     * <p>
     * The namespace is unloaded from the Configuration Manager after the test
     * has been performed. This will occur whether the test passed or failed.
     * </p>
     *
     * @param namespace the invalid configuration namespace to load into the
     *        Configuration Manager prior to running the test
     * @throws ConfigManagerException if loading or unloading configuration
     *         namespace fails
     */
    protected void performCtorTestWithInvalidConfig(String namespace) throws ConfigManagerException {
        // Load the invalid configuration.
        ConfigHelper.loadConfig(INVALID_CONFIG_FILE, namespace);

        try {
            new MockOrpheusUserProfilePersistence(namespace);
            fail("UserProfileManagerException should be thrown: invalid config namespace: " + namespace);
        } catch (UserProfileManagerException e) {
            // Success.
        } finally {
            // Unload the invalid configuration.
            ConfigHelper.unloadConfig(namespace);
        }
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfile profile) method calls the
     * protected ejbInsertProfile(UserProfileDTO profile) method. This is
     * accomplished by checking that the
     * MockOrpheusUserProfilePersistence.insertProfileMethodWasCalled() method
     * returns true.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidArg() throws Exception {
        UserProfile profile = createUserProfile();

        persistence.insertProfile(profile);

        assertTrue("The ejbInsertProfile(UserProfile) method was not called",
                   ((MockOrpheusUserProfilePersistence) persistence).insertProfileMethodWasCalled());
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
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfile profile) method calls the
     * protected ejbUpdateProfile(UserProfileDTO profile) method. This is
     * accomplished by checking that the
     * MockOrpheusUserProfilePersistence.updateProfileMethodWasCalled() method
     * returns true.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfile() throws Exception {
        UserProfile profile = createUserProfile();

        persistence.updateProfile(profile);

        assertTrue("The ejbUpdateProfile(UserProfile) method was not called",
                   ((MockOrpheusUserProfilePersistence) persistence).updateProfileMethodWasCalled());
    }

    /**
     * <p>
     * Creates a UserProfile instance that can be used for testing purposes.
     * </p>
     *
     * @return a test UserProfile instance
     * @throws Exception if creating the UserProfile instance fails
     */
    private UserProfile createUserProfile() throws Exception {
        UserProfile profile = new UserProfile(new Long(0));

        ConfigProfileTypeFactory factory = new ConfigProfileTypeFactory("com.topcoder.util.log");
        profile.addProfileType(factory.getProfileType(UserConstants.CREDENTIALS_TYPE_NAME));
        profile.addProfileType(factory.getProfileType(UserConstants.ADMIN_TYPE_NAME));

        return profile;
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
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the retrieveProfile(long id) method calls the protected
     * ejbRetrieveProfile(long id) method. This is accomplished by checking that
     * the MockOrpheusUserProfilePersistence.retrieveProfileMethodWasCalled()
     * method returns true.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProfileWithValidArg() throws Exception {
        persistence.retrieveProfile(0);

        assertTrue("The ejbRetrieveProfile(long) method was not called",
                   ((MockOrpheusUserProfilePersistence) persistence).retrieveProfileMethodWasCalled());
    }

    /**
     * <p>
     * Tests that the deleteProfile(long id) method calls the protected
     * ejbDeleteProfile(long id) method. This is accomplished by checking that
     * the MockOrpheusUserProfilePersistence.deleteProfileMethodWasCalled()
     * method returns true.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProfileWithValidARg() throws Exception {
        persistence.deleteProfile(0);

        assertTrue("The ejbDeleteProfile(long) method was not called",
                   ((MockOrpheusUserProfilePersistence) persistence).deleteProfileMethodWasCalled());
    }

    /**
     * <p>
     * Tests that the findProfiles(Map criteria) method calls the protected
     * ejbFindProfiles(Map criteria) method. This is accomplished by checking
     * that the MockOrpheusUserProfilePersistence.findProfilesMethodWasCalled()
     * method returns true.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFindProfilesWithValidArg() throws Exception {
        persistence.findProfiles(new HashMap());

        assertTrue("The ejbFindProfiles(Map) method was not called",
                   ((MockOrpheusUserProfilePersistence) persistence).findProfilesMethodWasCalled());
    }

    /**
     * <p>
     * Tests that the retrieveAllProfiles() method calls the protected
     * ejbRetrieveAllProfiles() method. This is accomplished by checking that
     * the
     * MockOrpheusUserProfilePersistence.retrieveAllProfilesMethodWasCalled()
     * method returns true.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllProfiles() throws Exception {
        persistence.retrieveAllProfiles();

        assertTrue("The ejbRetrieveAllProfiles() method was not called",
                   ((MockOrpheusUserProfilePersistence) persistence).retrieveAllProfilesMethodWasCalled());
    }

    /**
     * <p>
     * Tests that OrpheusUserProfilePersistence implements the
     * UserProfilerPersistence interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("OrpheusUserProfilePersistence should implement the UserProfilePersistence interface",
                   persistence instanceof UserProfilePersistence);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(OrpheusUserProfilePersistenceTest.class);
    }

}
