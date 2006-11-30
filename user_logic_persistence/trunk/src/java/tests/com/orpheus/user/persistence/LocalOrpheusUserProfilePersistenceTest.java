/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.orpheus.user.persistence.impl.UserProfileTranslator;
import com.topcoder.user.profile.manager.UserProfileManagerException;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * Tests the LocalOrpheusUserProfilePersistence class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class LocalOrpheusUserProfilePersistenceTest extends LocalOrpheusClientTestBase {

    /**
     * <p>
     * A test LocalOrpheusUserProfilePersistence configuration file containing
     * valid configuration.
     * </p>
     */
    private static final String VALID_CONFIG_FILE = "test_conf/valid_userprofileclient_config.xml";

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
     * A test LocalOrpheusUserProfilePersistence configuration file containing
     * invalid configuration.
     * </p>
     */
    private static final String INVALID_CONFIG_FILE = "test_conf/invalid_userprofileclient_config.xml";

    /**
     * <p>
     * The prefix for the test LocalOrpheusUserProfilePersistence configuration
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
     * The LocalOrpheusUserProfilePersistence instance to test.
     * </p>
     */
    private LocalOrpheusUserProfilePersistence persistence = null;

    /**
     * <p>
     * The test case helper to which all of the test methods, except those
     * testing the constructor, will be delegated to.
     * </p>
     */
    private OrpheusUserProfilePersistenceTestHelper testCaseHelper = null;

    /**
     * <p>
     * Creates a new OrpheusUserProfilePersistenceTest instance.
     * </p>
     */
    public LocalOrpheusUserProfilePersistenceTest() {
        super(NAMESPACE_PREFIX, NAMESPACE_PREFIX + ".local", VALID_CONFIG_FILE);
    }

    /**
     * <p>
     * Loads the valid test Object Factory, LocalOrpheusUserProfilePersistence
     * and UserProfileTranslator configuration namespaces into the Configuration
     * Manager, and creates the test LocalOrpheusUserProfilePersistence
     * instance.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        // Load the configuration.
        super.setUp();
        ConfigHelper.loadConfig(VALID_TRANSLATOR_CONFIG_FILE, TRANSLATOR_NAMESPACE_PREFIX + ".valid");
        ConfigHelper.loadConfig(VALID_TRANSLATOR_CONFIG_FILE, TRANSLATOR_NAMESPACE_PREFIX + ".objFactory");

        // Create the LocalOrpheusUserProfilePersistence instance.
        persistence = new LocalOrpheusUserProfilePersistence(NAMESPACE_PREFIX + ".local");

        // Create the test case helper.
        testCaseHelper = new OrpheusUserProfilePersistenceTestHelper(persistence);
    }

    /**
     * <p>
     * Unloads the valid test Object Factory,
     * LocalOrpheusUserProfilePersistence and UserProfileTranslator
     * configuration namespaces from the Configuration Manager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        ConfigHelper.unloadConfig(TRANSLATOR_NAMESPACE_PREFIX + ".valid");
        ConfigHelper.unloadConfig(TRANSLATOR_NAMESPACE_PREFIX + ".objFactory");
    }

    /**
     * <p>
     * Tests the LocalOrpheusUserProfilePersistence(String namespace)
     * constructor. The newly created instance should not be null.
     * </p>
     */
    public void testCtorWithValidArg() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The LocalOrpheusUserProfilePersistence instance should not be null", persistence);
    }

    /**
     * <p>
     * Tests that the LocalOrpheusUserProfilePersistence(String namespace)
     * contructor throws an IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithNullArg() throws Exception {
        try {
            new LocalOrpheusUserProfilePersistence(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the LocalOrpheusUserProfilePersistence(String namespace)
     * contructor throws an IllegalArgumentException when the argument is an
     * empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyArg() throws Exception {
        try {
            new LocalOrpheusUserProfilePersistence(" ");
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Performs the LocalOrpheusUserProfilePersistence(String namespace)
     * constructor test with invalid configuration. A
     * UserProfileManagerException is expected to be thrown.
     * </p>
     *
     * @param namespace the invalid configuration namespace
     * @throws ConfigManagerException if loading the invalid configuration
     *         namespace fails
     */
    protected void performCtorTestWithInvalidConfig(String namespace) throws ConfigManagerException {
        // Load the invalid configuration.
        ConfigHelper.loadConfig(INVALID_CONFIG_FILE, namespace);

        try {
            new LocalOrpheusUserProfilePersistence(namespace);
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
     * Tests the insertProfile(UserProfile profile) method with a valid player
     * profile with no contact information associated with it. The
     * retrieveProfile(long id) method should not throw an
     * UnknownProfileException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidPlayerArg() throws Exception {
        testCaseHelper.testInsertProfileWithValidPlayerArg();
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
        testCaseHelper.testInsertProfileWithValidPlayerArgContainingContactInfo();
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
        testCaseHelper.testInsertProfileWithValidAdminArg();
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
        testCaseHelper.testInsertProfileWithValidSponsorArg();
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
        testCaseHelper.testInsertProfileWithValidSponsorArgContainingContactInfo();
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
        testCaseHelper.testInsertProfileWithNullArg();
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
        testCaseHelper.testInsertProfileWithDuplicateArg();
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
        testCaseHelper.testUpdateProfileWithValidPlayerArgContainingNoContactInfo();
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
        testCaseHelper.testUpdateProfileWithValidPlayerArgContainingContactInfo1();
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
        testCaseHelper.testUpdateProfileWithValidPlayerArgContainingContactInfo2();
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
        testCaseHelper.testUpdateProfileWithValidAdminArg();
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
        testCaseHelper.testUpdateProfileWithValidSponsorArgContainingNoContactInfo();
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
        testCaseHelper.testUpdateProfileWithValidSponsorArgContainingContactInfo1();
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
        testCaseHelper.testUpdateProfileWithValidSponsorArgContainingContactInfo2();
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
        testCaseHelper.testUpdateProfileWithNullArg();
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
        testCaseHelper.testUpdateProfileWithArgNotInPersistence();
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
        testCaseHelper.testRetrieveProfileWithValidPlayerIdArg();
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
        testCaseHelper.testRetrieveProfileWithValidPlayerIdArgWithContactInfo();
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
        testCaseHelper.testRetrieveProfileWithValidAdminIdArg();
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
        testCaseHelper.testRetrieveProfileWithValidSponsorIdArg();
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
        testCaseHelper.testRetrieveProfileWithValidSponsorIdArgWithContactInfo();
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
        testCaseHelper.testRetrieveProfileWithArgNotInPersistence();
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
        testCaseHelper.testDeleteProfileWithValidPlayerIdArg();
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
        testCaseHelper.testDeleteProfileWithValidPlayerIdArgWithContactInfo();
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
        testCaseHelper.testDeleteProfileWithValidAdminIdArg();
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
        testCaseHelper.testDeleteProfileWithValidSponsorIdArg();
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
        testCaseHelper.testDeleteProfileWithValidSponsorIdArgWithContactInfo();
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
        testCaseHelper.testDeleteProfileWithArgNotInPersistence();
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
        testCaseHelper.testFindProfilesWithValidArgMatchingProfiles1();
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
        testCaseHelper.testFindProfilesWithValidArgMatchingProfiles2();
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
        testCaseHelper.testFindProfilesWithValidArgMatchingProfiles3();
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
        testCaseHelper.testFindProfilesWithValidArgMatchingProfiles4();
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
        testCaseHelper.testFindProfilesWithValidEmptyArg();
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
        testCaseHelper.testFindProfilesWithValidArgNoMatchingProfiles();
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
        testCaseHelper.testFindProfilesWithNullArg();
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
        testCaseHelper.testFindProfilesWithArgContainingNullKeys();
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
        testCaseHelper.testFindProfilesWithArgContainingNonStringKeys();
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
        testCaseHelper.testFindProfilesWithArgContainingEmptyKeys();
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
        testCaseHelper.testFindProfilesWithArgContainingInvalidKey();
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
        testCaseHelper.testRetrieveAllProfiles();
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(LocalOrpheusUserProfilePersistenceTest.class);
    }

}
