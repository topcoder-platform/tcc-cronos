/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.orpheus.user.persistence.ConfigHelper;
import com.orpheus.user.persistence.DuplicateEntryException;
import com.orpheus.user.persistence.EntryNotFoundException;
import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.PersistenceException;
import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.UserProfileDAO;
import com.orpheus.user.persistence.ejb.UserProfileDTO;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * Tests the SQLServerUserProfileDAO class. The testing is performed within an
 * application server container, because the SQLServerUserProfileDAO class needs
 * a bound JNDI reference to the DataSource when it is created.
 * </p>
 * <p>
 * <b>Note:</b> This test case assumes that the database does not contain user
 * profiles with ID's in the range 9223372036854775760 to 9223372036854775807,
 * inclusive. Please ensure that this is the case. Otherwise, some unit tests
 * may fail.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class SQLServerUserProfileDAOTest extends DAOTestBase {

    /**
     * <p>
     * A sample user profile ID to use during the unit tests. A very large value
     * is used to avoid conflicts with existing data in the database.
     * </p>
     */
    private static final long USER_ID = 9223372036854775760L;

    /**
     * <p>
     * The SQLServerUserProfileDAO instance to test.
     * </p>
     */
    private SQLServerUserProfileDAO dao = null;

    /**
     * <p>
     * Creates the test SQLServerUserProfileDAO instance.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        // Load the configuration.
        super.setUp();

        dao = new SQLServerUserProfileDAO(SQLServerUserProfileDAOTest.VALID_NAMESPACE_WITH_CONN_NAME);
    }

    /**
     * <p>
     * Tests the SQLServerUserProfileDAO(String namespace) constructor with a
     * valid argument where the corresponding configuration contains the
     * optional "name" property. The newly created instance should not be null.
     * </p>
     */
    public void testCtorWithValidArg1() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The SQLServerUserProfileDAO instance should not be null", dao);
    }

    /**
     * <p>
     * Tests the SQLServerUserProfileDAO(String namespace) constructor with a
     * valid argument where the corresponding configuration does not contain the
     * optional "name" property. The newly created instance should not be null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithValidArg2() throws Exception {
        dao = new SQLServerUserProfileDAO(SQLServerUserProfileDAOTest.VALID_NAMESPACE_WITHOUT_CONN_NAME);
        assertNotNull("The SQLServerUserProfileDAO instance should not be null", dao);
    }

    /**
     * <p>
     * Tests that the SQLServerUserProfileDAO(String namespace) constructor
     * throws an IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithNullArg() throws Exception {
        try {
            new SQLServerUserProfileDAO(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the SQLServerUserProfileDAO(String namespace) constructor
     * throws an IllegalArgumentException when the argument is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyArg() throws Exception {
        try {
            new SQLServerUserProfileDAO(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Performs the SQLServerUserProfileDAO(String namespace) constructor test
     * with invalid configuration. An ObjectInstantiationException is expected
     * to be thrown.
     * </p>
     *
     * @param namespace the namespace containing the invalid configuration
     * @throws ConfigManagerException if loading the invalid configuration
     *         namespace fails
     */
    protected void performCtorTestWithInvalidConfig(String namespace) throws ConfigManagerException {
        // Load the invalid configuration.
        ConfigHelper.loadConfig(INVALID_CONFIG_FILE, namespace);

        try {
            new SQLServerUserProfileDAO(namespace);
            fail("ObjectInstantiationException should be thrown: invalid config namespace: " + namespace);
        } catch (ObjectInstantiationException e) {
            // Success.
        } finally {
            // Unload the invalid configuration.
            ConfigHelper.unloadConfig(namespace);
        }
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfileDTO profile) method with a valid
     * player profile with no contact information associated with it. The
     * retrieveProfile(long id) method should not throw an
     * EntryNotFoundException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidPlayerArg() throws Exception {
        performInsertProfileTestWithValidArg(USER_ID, createPlayerProfile(USER_ID, false));
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfileDTO profile) method with a valid
     * player profile with contact information associated with it. The
     * retrieveProfile(long id) method should not throw an
     * EntryNotFoundException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidPlayerArgContainingContactInfo() throws Exception {
        performInsertProfileTestWithValidArg(USER_ID, createPlayerProfile(USER_ID, true));
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfileDTO profile) method with a valid admin
     * profile. The retrieveProfile(long id) method should not throw an
     * EntryNotFoundException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidAdminArg() throws Exception {
        performInsertProfileTestWithValidArg(USER_ID, createAdminProfile(USER_ID));
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfileDTO profile) method with a valid
     * sponsor profile with no contact information associated with it. The
     * retrieveProfile(long id) method should not throw an
     * EntryNotFoundException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidSponsorArg() throws Exception {
        performInsertProfileTestWithValidArg(USER_ID, createSponsorProfile(USER_ID, false));
    }

    /**
     * <p>
     * Tests the insertProfile(UserProfileDTO profile) method with a valid
     * sponsor profile with contact information associated with it. The
     * retrieveProfile(long id) method should not throw an
     * EntryNotFoundException after the insert operation.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithValidSponsorArgContainingContactInfo() throws Exception {
        performInsertProfileTestWithValidArg(USER_ID, createSponsorProfile(USER_ID, true));
    }

    /**
     * <p>
     * Performs the insertProfile(UserProfileDTO profile) method test with a
     * valid argument. After the method has been invoked, calling
     * retrieveProfile(long id) with the inserted user profile ID should not
     * result in an EntryNotFoundException being thrown.
     * </p>
     *
     * @param id the ID of the user profile to insert
     * @param profile the user profile to insert
     * @throws PersistenceException if the test fails due to a persistence error
     */
    private void performInsertProfileTestWithValidArg(long id, UserProfileDTO profile) throws PersistenceException {
        // Insert the profile.
        dao.insertProfile(profile);

        try {
            // Check that the profile was inserted.
            dao.retrieveProfile(id);

            // Clean-up by deleting the inserted profile.
            dao.deleteProfile(id);
        } catch (EntryNotFoundException e) {
            fail("The user profile was not inserted successfully");
        }
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithNullArg() throws Exception {
        try {
            dao.insertProfile(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the argument does not contain a Player,
     * Admin or Sponsor object.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithArgContainingNoUserObject() throws Exception {
        try {
            dao.insertProfile(new UserProfileDTO());
            fail("IllegalArgumentException should be thrown: UserProfileDTO does not contain a User object");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the argument contains an invalid player
     * mapping. In this test, the UserProfileDTO.PLAYER_KEY maps to an Admin
     * object.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithArgContainingInvalidPlayerMapping() throws Exception {
        UserProfileDTO profile = new UserProfileDTO();
        profile.put(UserProfileDTO.PLAYER_KEY, new Admin(0));

        try {
            dao.insertProfile(profile);
            fail("IllegalArgumentException should be thrown: invalid UserProfileDTO player mapping");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the argument contains an invalid admin
     * mapping. In this test, the UserProfileDTO.ADMIN_KEY maps to a Player
     * object.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithArgContainingInvalidAdminMapping() throws Exception {
        UserProfileDTO profile = new UserProfileDTO();
        profile.put(UserProfileDTO.ADMIN_KEY, new Player(0));

        try {
            dao.insertProfile(profile);
            fail("IllegalArgumentException should be thrown: invalid UserProfileDTO admin mapping");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the argument contains an invalid sponsor
     * mapping. In this test, the UserProfileDTO.SPONSOR_KEY maps to an Admin
     * object.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithArgContainingInvalidSponsorMapping() throws Exception {
        UserProfileDTO profile = new UserProfileDTO();
        profile.put(UserProfileDTO.SPONSOR_KEY, new Admin(0));

        try {
            dao.insertProfile(profile);
            fail("IllegalArgumentException should be thrown: invalid UserProfileDTO sponsor mapping");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the argument contains an invalid contact
     * info mapping. In this test, the UserProfileDTO.CONTACT_INFO_KEY maps to
     * an Admin object.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithArgContainingInvalidContactInfoMapping() throws Exception {
        UserProfileDTO profile = new UserProfileDTO();
        profile.put(UserProfileDTO.PLAYER_KEY, new Player(0));
        profile.put(UserProfileDTO.CONTACT_INFO_KEY, new Admin(0));

        try {
            dao.insertProfile(profile);
            fail("IllegalArgumentException should be thrown: invalid UserProfileDTO contact info mapping");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the insertProfile(UserProfileDTO profile) method throws a
     * DuplicateEntryException when the user profile corresponding to the
     * argument does already exists in the persistent store. The return value of
     * the DuplicateEntryException.getIdentifier() method should be equal to the
     * ID of the argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInsertProfileWithDuplicateArg() throws Exception {
        // Insert the profile.
        dao.insertProfile(createAdminProfile(USER_ID));

        // Try to insert the profile again.
        try {
            dao.insertProfile(createAdminProfile(USER_ID));
            fail("DuplicateEntryException should be thrown");
        } catch (DuplicateEntryException e) {
            assertEquals("The duplicate entry identifier is incorrect", new Long(USER_ID), e.getIdentifier());
            // Success.
        } finally {
            // Clean-up by deleting the profile.
            dao.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfileDTO profile) with a user profile DTO
     * argument containing only a Player object (no contact information). The
     * return value of the retrieveProfile(long id) method should be equal to
     * the method argument when given the ID of the updated player.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidPlayerArgContainingNoContactInfo() throws Exception {
        // Insert a player profile with no contact information.
        UserProfileDTO profile = createPlayerProfile(USER_ID, false);
        dao.insertProfile(profile);

        // Update some of the player's information.
        Player player = (Player) profile.get(UserProfileDTO.PLAYER_KEY);
        player.setEmail("updatedemail@somehost.domain");
        player.setPassword("pwdupdate");
        player.setPaymentPref(null);
        player.setActive("false");

        try {
            // Try to update the profile.
            dao.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfileDTO profile2 = dao.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.PLAYER_TYPE_NAME, false);
        } finally {
            // Clean-up by deleting the inserted profile.
            dao.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfileDTO profile) with a user profile DTO
     * argument containing a Player and a ContactInfo object. The method is
     * called when an entry corresponding to the ContactInfo object already
     * exists in the database. After the method is called, the return value of
     * the retrieveProfile(long id) method should be equal to the method
     * argument when given the ID of the updated player.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidPlayerArgContainingContactInfo1() throws Exception {
        // Insert a player profile with contact information.
        UserProfileDTO profile = createPlayerProfile(USER_ID, true);
        dao.insertProfile(profile);

        // Update some player information.
        Player player = (Player) profile.get(UserProfileDTO.PLAYER_KEY);
        player.setPassword("pwdupdate");
        player.setPaymentPref(null);

        // Update some contact information.
        ContactInfo contactInfo = (ContactInfo) profile.get(UserProfileDTO.CONTACT_INFO_KEY);
        contactInfo.setCity("New Updated City");
        contactInfo.setLastName("Update");
        contactInfo.setAddress2("Address 2 Update");

        try {
            // Try to update the profile.
            dao.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfileDTO profile2 = dao.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.PLAYER_TYPE_NAME, true);
        } finally {
            // Clean-up by deleting the inserted profile.
            dao.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfileDTO profile) with a user profile DTO
     * argument containing a Player and a ContactInfo object. The method is
     * called when no entry corresponding to the ContactInfo object exists in
     * the database. Therefore, the update operation should insert the contact
     * information, rather than simply update it. After the method is called,
     * the return value of the retrieveProfile(long id) method should be equal
     * to the method argument when given the ID of the updated player.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidPlayerArgContainingContactInfo2() throws Exception {
        // Insert a player profile with no contact information.
        UserProfileDTO profile = createPlayerProfile(USER_ID, false);
        dao.insertProfile(profile);

        // Update some player information.
        Player player = (Player) profile.get(UserProfileDTO.PLAYER_KEY);
        player.setPassword("pwdupdate");
        player.setPaymentPref(null);

        // Add contact information to the player profile.
        addContactInfo(player.getId(), profile);

        try {
            // Try to update the profile.
            dao.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfileDTO profile2 = dao.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.PLAYER_TYPE_NAME, true);
        } finally {
            // Clean-up by deleting the inserted profile.
            dao.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfileDTO profile) with a user profile DTO
     * argument containing an Admin object. The return value of the
     * retrieveProfile(long id) method should be equal to the method argument
     * when given the ID of the updated admin.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidAdminArg() throws Exception {
        // Insert an admin profile.
        UserProfileDTO profile = createAdminProfile(USER_ID);
        dao.insertProfile(profile);

        // Update some admin information.
        Admin admin = (Admin) profile.get(UserProfileDTO.ADMIN_KEY);
        admin.setEmail("updatedemail@somehost.domain");
        admin.setPassword("pwdupdate");
        admin.setActive("false");

        try {
            // Try to update the admin profile.
            dao.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfileDTO profile2 = dao.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.ADMIN_TYPE_NAME, false);
        } finally {
            // Clean-up by deleting the inserted profile.
            dao.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfileDTO profile) with a user profile DTO
     * argument containing only a Sponsor object (no contact information). The
     * return value of the retrieveProfile(long id) method should be equal to
     * the method argument when given the ID of the updated sponsor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidSponsorArgContainingNoContactInfo() throws Exception {
        // Insert a sponsor profile with no contact information.
        UserProfileDTO profile = createSponsorProfile(USER_ID, false);
        dao.insertProfile(profile);

        // Update some sponsor information.
        Sponsor sponsor = (Sponsor) profile.get(UserProfileDTO.SPONSOR_KEY);
        sponsor.setEmail("updatedemail@somehost.domain");
        sponsor.setPassword("pwdupdate");
        sponsor.setFax("987645321");
        sponsor.setPaymentPref(null);
        sponsor.setActive("false");
        sponsor.setApproved(Sponsor.APPROVED_UNDECIDED);

        try {
            // Try to update the profile.
            dao.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfileDTO profile2 = dao.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.SPONSOR_TYPE_NAME, false);
        } finally {
            // Clean-up by deleting the inserted profile.
            dao.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfileDTO profile) with a user profile DTO
     * argument containing a Sponsor and a ContactInfo object. The method is
     * called when an entry corresponding to the ContactInfo object already
     * exists in the database. After the method is called, the return value of
     * the retrieveProfile(long id) method should be equal to the method
     * argument when given the ID of the updated sponsor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidSponsorArgContainingContactInfo1() throws Exception {
        // Insert a sponsor profile with contact information.
        UserProfileDTO profile = createSponsorProfile(USER_ID, true);
        dao.insertProfile(profile);

        // Update some sponsor information.
        Sponsor sponsor = (Sponsor) profile.get(UserProfileDTO.SPONSOR_KEY);
        sponsor.setEmail("updatedemail@somehost.domain");
        sponsor.setPassword("pwdupdate");
        sponsor.setFax("987645321");
        sponsor.setPaymentPref(null);
        sponsor.setActive("false");
        sponsor.setApproved(Sponsor.APPROVED_UNDECIDED);

        // Update some contact information.
        ContactInfo contactInfo = (ContactInfo) profile.get(UserProfileDTO.CONTACT_INFO_KEY);
        contactInfo.setCity("New Updated City");
        contactInfo.setLastName("Update");
        contactInfo.setAddress2("Address 2 Update");

        try {
            // Try to update the profile.
            dao.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfileDTO profile2 = dao.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.SPONSOR_TYPE_NAME, true);
        } finally {
            // Clean-up by deleting the inserted profile.
            dao.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests the updateProfile(UserProfileDTO profile) with a user profile DTO
     * argument containing a Sponsor and a ContactInfo object. The method is
     * called when no entry corresponding to the ContactInfo object exists in
     * the database. Therefore, the update operation should insert the contact
     * information, rather than simply update it. After the method is called,
     * the return value of the retrieveProfile(long id) method should be equal
     * to the method argument when given the ID of the updated sponsor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithValidSponsorArgContainingContactInfo2() throws Exception {
        // Insert a sponsor profile with no contact information.
        UserProfileDTO profile = createSponsorProfile(USER_ID, false);
        dao.insertProfile(profile);

        // Update some sponsor information.
        Sponsor sponsor = (Sponsor) profile.get(UserProfileDTO.SPONSOR_KEY);
        sponsor.setEmail("updatedemail@somehost.domain");
        sponsor.setPassword("pwdupdate");
        sponsor.setFax("987645321");
        sponsor.setPaymentPref(null);
        sponsor.setActive("false");
        sponsor.setApproved(Sponsor.APPROVED_UNDECIDED);

        // Add contact information to the player profile.
        addContactInfo(sponsor.getId(), profile);

        try {
            // Try to update the profile.
            dao.updateProfile(profile);

            // Check that the profile was updated successfully.
            UserProfileDTO profile2 = dao.retrieveProfile(USER_ID);
            compareUserProfiles(profile, profile2, UserConstants.SPONSOR_TYPE_NAME, true);
        } finally {
            // Clean-up by deleting the inserted profile.
            dao.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithNullArg() throws Exception {
        try {
            dao.updateProfile(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the argument does not contain a Player,
     * Admin or Sponsor object.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithArgContainingNoUserObject() throws Exception {
        try {
            dao.updateProfile(new UserProfileDTO());
            fail("IllegalArgumentException should be thrown: UserProfileDTO does not contain a User object");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the UserProfileDTO.PLAYER_KEY key does not
     * map to a Player object.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithArgContainingInvalidPlayerMapping() throws Exception {
        performUpdateProfileWithArgContainingInvalidMapping(UserProfileDTO.PLAYER_KEY);
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the UserProfileDTO.ADMIN_KEY key does not
     * map to an Admin object.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithArgContainingInvalidAdminMapping() throws Exception {
        performUpdateProfileWithArgContainingInvalidMapping(UserProfileDTO.ADMIN_KEY);
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the UserProfileDTO.SPONSOR_KEY key does not
     * map to a Sponsor object.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithArgContainingInvalidSponsorMapping() throws Exception {
        performUpdateProfileWithArgContainingInvalidMapping(UserProfileDTO.SPONSOR_KEY);
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfileDTO profile) method throws an
     * IllegalArgumentException when the UserProfileDTO.CONTACT_INFO_KEY key
     * does not map to a ContactInfo object.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithArgContainingInvalidContactInfoMapping() throws Exception {
        performUpdateProfileWithArgContainingInvalidMapping(UserProfileDTO.CONTACT_INFO_KEY);
    }

    /**
     * <p>
     * Performs the updateProfile(UserProfileDTO profile) method test when the
     * given invalidProfileKey maps to the wrong object in the user profile DTO.
     * </p>
     *
     * @param invalidProfileKey the key which maps to the wrong object
     * @throws PersistenceException if the test fails due to a persistence error
     */
    private void performUpdateProfileWithArgContainingInvalidMapping(String invalidProfileKey)
            throws PersistenceException {
        UserProfileDTO profile = createSponsorProfile(USER_ID, true);

        // Make sure the profile exists in the persistent store.
        dao.insertProfile(profile);

        // Set the invalid mapping.
        profile.put(invalidProfileKey, new Long(0));

        // Try to update the profile.
        try {
            dao.updateProfile(profile);
            fail("IllegalArgumentException should be thrown: invalid UserProfileDTO " + invalidProfileKey + " mapping");
        } catch (IllegalArgumentException e) {
            // Success.
        } finally {
            // Clean-up by deleting the inserted profile.
            dao.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Tests that the updateProfile(UserProfileDTO profile) method throws an
     * EntryNotFoundException when the user profile corresponding to the
     * argument does not exist in the persistent store. The return value of the
     * EntryNotFoundException.getIdentifier() method should be equal to the ID
     * of the argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProfileWithArgNotInPersistence() throws Exception {
        // Try to update the nonexistent record.
        try {
            dao.updateProfile(createSponsorProfile(USER_ID, false));
            fail("EntryNotFoundException should be thrown");
        } catch (EntryNotFoundException e) {
            assertEquals("The missing entry ID is incorrect", new Long(USER_ID), e.getIdentifier());
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
     * @throws PersistenceException if the test fails due to a persistence error
     */
    private void performRetrieveProfileTestWithValidArg(String userTypeName, boolean addContactInfo)
            throws PersistenceException {
        // Create the profile to retrieve.
        UserProfileDTO profile = null;
        if (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)) {
            profile = createPlayerProfile(USER_ID, addContactInfo);
        } else if (userTypeName.equals(UserConstants.ADMIN_TYPE_NAME)) {
            profile = createAdminProfile(USER_ID);
        } else if (userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME)) {
            profile = createSponsorProfile(USER_ID, addContactInfo);
        }

        // Make sure the profile is in the persistent store.
        dao.insertProfile(profile);

        // Retrieve the profile
        UserProfileDTO profile2 = dao.retrieveProfile(USER_ID);

        // Check that the returned profile is equal to the inserted one.
        try {
            compareUserProfiles(profile, profile2, userTypeName, addContactInfo);
        } finally {
            // Clean-up by deleting the inserted profile.
            dao.deleteProfile(USER_ID);
        }
    }

    /**
     * <p>
     * Checks that the two given UserProfileDTO instances are equal. If they do
     * not contain the same information, a JUnit assertion is generated. The
     * userTypeName argument specifies what type of user profile the two
     * UserProfileDTO instances represent - whether they are player, admin or
     * sponsor profiles. The hasContactInfo argument specifies whether the
     * UserProfileDTO instances contain ContactInfo objects.
     * </p>
     *
     * @param profile1 the UserProfileDTO instance to compare to profile2
     * @param profile2 the UserProfileDTO instance to compare to profile1
     * @param userTypeName the type of user profile the two UserProfileDTO
     *        instances represent
     * @param hasContactInfo whether the UserProfileDTO instances contain
     *        ContactInfo objects
     */
    private void compareUserProfiles(UserProfileDTO profile1, UserProfileDTO profile2, String userTypeName,
            boolean hasContactInfo) {
        User user1 = null;
        User user2 = null;
        if (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)) {
            // Check players.
            Player player1 = (Player) profile1.get(UserProfileDTO.PLAYER_KEY);
            Player player2 = (Player) profile1.get(UserProfileDTO.PLAYER_KEY);
            assertEquals("The payment pref is incorrect", player1.getPaymentPref(), player2.getPaymentPref());
            if (hasContactInfo) {
                compareContactInfos((ContactInfo) profile1.get(UserProfileDTO.CONTACT_INFO_KEY),
                                    (ContactInfo) profile2.get(UserProfileDTO.CONTACT_INFO_KEY));
            }
            user1 = player1;
            user2 = player2;
        } else if (userTypeName.equals(UserConstants.ADMIN_TYPE_NAME)) {
            // Check admins.
            Admin admin1 = (Admin) profile1.get(UserProfileDTO.ADMIN_KEY);
            Admin admin2 = (Admin) profile1.get(UserProfileDTO.ADMIN_KEY);
            user1 = admin1;
            user2 = admin2;
        } else if (userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME)) {
            // Check sponsors.
            Sponsor sponsor1 = (Sponsor) profile1.get(UserProfileDTO.SPONSOR_KEY);
            Sponsor sponsor2 = (Sponsor) profile1.get(UserProfileDTO.SPONSOR_KEY);
            assertEquals("The fax is incorrect", sponsor1.getFax(), sponsor2.getFax());
            assertEquals("The payment pref is incorrect", sponsor1.getPaymentPref(), sponsor2.getPaymentPref());
            assertEquals("The approved flag is incorrect", sponsor1.getApproved(), sponsor2.getApproved());
            if (hasContactInfo) {
                compareContactInfos((ContactInfo) profile1.get(UserProfileDTO.CONTACT_INFO_KEY),
                                    (ContactInfo) profile2.get(UserProfileDTO.CONTACT_INFO_KEY));
            }
            user1 = sponsor1;
            user2 = sponsor2;
        }

        // Check general user information.
        assertEquals("The user ID is incorrect", user1.getId(), user2.getId());
        assertEquals("The handle is incorrect", user1.getHandle(), user2.getHandle());
        assertEquals("The email is incorrect", user1.getEmail(), user2.getEmail());
        assertEquals("The password is incorrect", user1.getPassword(), user2.getPassword());
        assertEquals("The active flag is incorrect", user1.getActive(), user2.getActive());
    }

    /**
     * <p>
     * Checks that the fields of the two given ContactInfo objects are equal. If
     * any field is not the same, a JUnit assertion is generated.
     * </p>
     *
     * @param info1 the ContactInfo object to compare to info2
     * @param info2 the ContactInfo object to compare to info1
     */
    private void compareContactInfos(ContactInfo info1, ContactInfo info2) {
        assertEquals("The contact info ID is incorrect", info1.getId(), info2.getId());
        assertEquals("The address1 field is incorrect", info1.getAddress1(), info2.getAddress1());
        assertEquals("The address2 field is incorrect", info1.getAddress2(), info2.getAddress2());
        assertEquals("The city is incorrect", info1.getCity(), info2.getCity());
        assertEquals("The first name is incorrect", info1.getFirstName(), info2.getFirstName());
        assertEquals("The last name is incorrect", info1.getLastName(), info2.getLastName());
        assertEquals("The postal code is incorrect", info1.getPostalCode(), info2.getPostalCode());
        assertEquals("The state is incorrect", info1.getState(), info2.getState());
        assertEquals("The telephone is incorrect", info1.getTelephone(), info2.getTelephone());
    }

    /**
     * <p>
     * Tests that the retrieveProfile(long id) method throws an
     * EntryNotFoundException when the user profile corresponding to the
     * argument does not exist in the persistent store. The return value of the
     * EntryNotFoundException.getIdentifier() method should be equal to the
     * argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProfileWithArgNotInPersistence() throws Exception {
        // Try to retrieve the nonexistent record.
        try {
            dao.retrieveProfile(USER_ID);
            fail("EntryNotFoundException should be thrown");
        } catch (EntryNotFoundException e) {
            assertEquals("The missing entry ID is incorrect", new Long(USER_ID), e.getIdentifier());
            // Success.
        }
    }

    /**
     * <p>
     * Tests the deleteProfile(long id) method with an argument corresponding to
     * a player profile present in the persistent store, but which does not have
     * any contact information associated with it. After the delete operation,
     * invoking the retrieveProfile(long id) method with the method argument
     * should result in an EntryNotFoundException being thrown.
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
     * an EntryNotFoundException being thrown.
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
     * argument should result in an EntryNotFoundException being thrown.
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
     * argument should result in an EntryNotFoundException being thrown.
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
     * an EntryNotFoundException being thrown.
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
     * retrieveProfile(long id) method should throw an EntryNotFoundException.
     * </p>
     *
     * @param userTypeName the user profile type name of the profile to test
     *        (player, sponsor or admin)
     * @param addContactInfo whether the user profile should have contact
     *        information associated with it (only applies to player and sponsor
     *        profiles)
     * @throws PersistenceException if the test fails due to a persistence error
     */
    private void performDeleteProfileTestWithValidArg(String userTypeName, boolean addContactInfo)
            throws PersistenceException {
        // Create the profile to delete.
        UserProfileDTO profile = null;
        if (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)) {
            profile = createPlayerProfile(USER_ID, addContactInfo);
        } else if (userTypeName.equals(UserConstants.ADMIN_TYPE_NAME)) {
            profile = createAdminProfile(USER_ID);
        } else if (userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME)) {
            profile = createSponsorProfile(USER_ID, addContactInfo);
        }

        // Insert the profile to delete.
        dao.insertProfile(profile);

        // Delete the profile
        dao.deleteProfile(USER_ID);

        // Check that the profile has been deleted.
        try {
            dao.retrieveProfile(USER_ID);
            fail("The profile was not deleted successfully");
        } catch (EntryNotFoundException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the deleteProfile(long id) method throws an
     * EntryNotFoundException when the user profile corresponding to the
     * argument does not exist in the persistent store. The return value of the
     * EntryNotFoundException.getIdentifier() method should be equal to the
     * argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProfileWithArgNotInPersistence() throws Exception {
        // Try to delete the nonexistant record.
        try {
            dao.deleteProfile(USER_ID);
            fail("EntryNotFoundException should be thrown");
        } catch (EntryNotFoundException e) {
            assertEquals("The missing entry ID is incorrect", new Long(USER_ID), e.getIdentifier());
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
            UserProfileDTO profile = null;
            User user = null;
            if (i % 2 == 0) {
                profile = createAdminProfile(id);
                user = (User) profile.get(UserProfileDTO.ADMIN_KEY);
            } else if (i % 3 == 0) {
                profile = createPlayerProfile(id, false);
                user = (User) profile.get(UserProfileDTO.PLAYER_KEY);
            } else {
                profile = createSponsorProfile(id, true);
                user = (User) profile.get(UserProfileDTO.SPONSOR_KEY);
            }
            user.setActive(active);

            dao.insertProfile(profile);
        }

        // Find all non-active user profiles.
        Map criteria = new HashMap();
        criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, active);

        try {
            UserProfileDTO[] profiles = dao.findProfiles(criteria);

            // At least numMatchingProfiles profiles should have been returned.
            // There may be more that were not created by this unit test.
            assertTrue("The number of profiles found is incorrect", profiles.length >= numMatchingProfiles);

            // Iterate through all the returned profiles and check that they are
            // correct.
            for (int i = 0; i < profiles.length; i++) {
                User user = null;
                if (profiles[i].contains(UserProfileDTO.PLAYER_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.PLAYER_KEY);
                } else if (profiles[i].contains(UserProfileDTO.ADMIN_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.ADMIN_KEY);
                } else if (profiles[i].contains(UserProfileDTO.SPONSOR_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.SPONSOR_KEY);
                } else {
                    fail("Bad user profile returned");
                }

                assertEquals("The active flag is incorrect", active, user.getActive());
            }
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            int numProfiles = numRandomProfiles + numMatchingProfiles;
            for (int i = 0; i < numProfiles; i++) {
                dao.deleteProfile(idOffset + i);
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
            UserProfileDTO profile = createPlayerProfile(idOffset + numRandomProfiles + i, true);
            ((Player) profile.get(UserProfileDTO.PLAYER_KEY)).setPaymentPref(paymentPref);
            dao.insertProfile(profile);
        }

        // Find all active players profiles with "Wire transfer" payment
        // preference.
        Map criteria = new HashMap();
        criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, active);
        criteria.put(UserConstants.PLAYER_PAYMENT_PREF, paymentPref);

        try {
            UserProfileDTO[] profiles = dao.findProfiles(criteria);

            // At least numMatchingProfiles profiles should have been returned.
            // There may be more that were not created by this unit test.
            assertTrue("The number of profiles found is incorrect", profiles.length >= numMatchingProfiles);

            // Iterate through all the returned profiles and check that they are
            // correct.
            for (int i = 0; i < profiles.length; i++) {
                Player player = (Player) profiles[i].get(UserProfileDTO.PLAYER_KEY);
                assertEquals("The active flag is incorrect", active, player.getActive());
                assertEquals("The payment pref of the player is incorrect", paymentPref, player.getPaymentPref());
            }
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            int numProfiles = numRandomProfiles + numMatchingProfiles;
            for (int i = 0; i < numProfiles; i++) {
                dao.deleteProfile(idOffset + i);
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

            UserProfileDTO profile = null;
            User user = null;
            if (i % 3 == 0) {
                profile = createPlayerProfile(id, true);
                user = (User) profile.get(UserProfileDTO.PLAYER_KEY);
            } else {
                profile = createSponsorProfile(id, true);
                user = (User) profile.get(UserProfileDTO.SPONSOR_KEY);
            }

            user.setHandle(handle);
            user.setActive(active);

            ContactInfo contactInfo = (ContactInfo) profile.get(UserProfileDTO.CONTACT_INFO_KEY);
            contactInfo.setCity(city);
            contactInfo.setState(state);

            dao.insertProfile(profile);
        }

        // Find all non-active players and sponsors profiles in Los Angeles,
        // California that have the handle, "joeuser".
        Map criteria = new HashMap();
        criteria.put(UserConstants.CREDENTIALS_IS_ACTIVE, active);
        criteria.put(UserConstants.CREDENTIALS_HANDLE, handle);
        criteria.put(UserConstants.ADDRESS_CITY, city);
        criteria.put(UserConstants.ADDRESS_STATE, state);

        try {
            UserProfileDTO[] profiles = dao.findProfiles(criteria);

            // At least numMatchingProfiles profiles should have been returned.
            // There may be more that were not created by this unit test.
            assertTrue("The number of profiles found is incorrect", profiles.length >= numMatchingProfiles);

            // Iterate through all the returned profiles and check that they are
            // correct.
            for (int i = 0; i < profiles.length; i++) {
                User user = null;
                if (profiles[i].contains(UserProfileDTO.PLAYER_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.PLAYER_KEY);
                } else if (profiles[i].contains(UserProfileDTO.SPONSOR_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.SPONSOR_KEY);
                } else {
                    fail("Incorrect user profile returned");
                }
                ContactInfo contactInfo = (ContactInfo) profiles[i].get(UserProfileDTO.CONTACT_INFO_KEY);

                assertEquals("The handle is incorrect", handle, user.getHandle());
                assertEquals("The active flag is incorrect", active, user.getActive());
                assertEquals("The city is incorrect", city, contactInfo.getCity());
                assertEquals("The state is incorrect", state, contactInfo.getState());
            }
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            int numProfiles = numRandomProfiles + numMatchingProfiles;
            for (int i = 0; i < numProfiles; i++) {
                dao.deleteProfile(idOffset + i);
            }
        }
    }

    /**
     * <p>
     * Tests the findProfiles(Map criteria) method with criteria to find all
     * sponsors who live in Los Angeles, California with a null fax number and
     * payment preference, and whose approved status is still undecided. The
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

        String fax = null;
        String paymentPref = null;
        String approved = Sponsor.APPROVED_UNDECIDED;
        String city = "Los Angeles";
        String state = "California";

        // Insert a bunch of matching profiles.
        for (int i = 0; i < numMatchingProfiles; i++) {
            long id = idOffset + numRandomProfiles + i;

            UserProfileDTO profile = createSponsorProfile(id, true);

            Sponsor sponsor = (Sponsor) profile.get(UserProfileDTO.SPONSOR_KEY);
            sponsor.setFax(fax);
            sponsor.setPaymentPref(paymentPref);
            sponsor.setApproved(approved);

            ContactInfo contactInfo = (ContactInfo) profile.get(UserProfileDTO.CONTACT_INFO_KEY);
            contactInfo.setCity(city);
            contactInfo.setState(state);

            dao.insertProfile(profile);
        }

        // Find all sponsors in Los Angeles, California with a null fax number
        // and payment preference, and whose approved status is still undecided.
        Map criteria = new HashMap();
        criteria.put(UserConstants.SPONSOR_FAX_NUMBER, fax);
        criteria.put(UserConstants.SPONSOR_PAYMENT_PREF, paymentPref);
        criteria.put(UserConstants.SPONSOR_APPROVED, approved);
        criteria.put(UserConstants.ADDRESS_CITY, city);
        criteria.put(UserConstants.ADDRESS_STATE, state);

        try {
            UserProfileDTO[] profiles = dao.findProfiles(criteria);

            // At least numMatchingProfiles profiles should have been returned.
            // There may be more that were not created by this unit test.
            assertTrue("The number of profiles found is incorrect: " + profiles.length,
                       profiles.length >= numMatchingProfiles);

            // Iterate through all the returned profiles and check that they are
            // correct.
            for (int i = 0; i < profiles.length; i++) {
                Sponsor sponsor = (Sponsor) profiles[i].get(UserProfileDTO.SPONSOR_KEY);
                ContactInfo contactInfo = (ContactInfo) profiles[i].get(UserProfileDTO.CONTACT_INFO_KEY);

                assertEquals("The fax number is incorrect", fax, sponsor.getFax());
                assertEquals("The payment preference is incorrect", paymentPref, sponsor.getPaymentPref());
                assertEquals("The approved flag is incorrect", approved, sponsor.getApproved());
                assertEquals("The city is incorrect", city, contactInfo.getCity());
                assertEquals("The state is incorrect", state, contactInfo.getState());
            }
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            int numProfiles = numRandomProfiles + numMatchingProfiles;
            for (int i = 0; i < numProfiles; i++) {
                dao.deleteProfile(idOffset + i);
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
            UserProfileDTO[] profiles = dao.findProfiles(new HashMap());

            // At least numProfiles profiles should have been returned. There
            // may be more that were not created by this unit test.
            assertTrue("The number of profiles found is incorrect", profiles.length >= numProfiles);

            // Iterate through all the returned profiles and check if the
            // numProfiles profiles are there.
            int numReturned = 0;
            for (int i = 0; i < profiles.length; i++) {
                // Get the User object contained in the user profile.
                User user = null;
                if (profiles[i].contains(UserProfileDTO.PLAYER_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.PLAYER_KEY);
                } else if (profiles[i].contains(UserProfileDTO.ADMIN_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.ADMIN_KEY);
                } else if (profiles[i].contains(UserProfileDTO.SPONSOR_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.SPONSOR_KEY);
                } else {
                    fail("Bad user profile returned");
                }
                if (user.getId() >= idOffset && user.getId() <= idOffset + (numProfiles - 1)) {
                    numReturned++;
                }
            }
            assertEquals("Not all the profiles were returned", numProfiles, numReturned);
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            for (int i = 0; i < numProfiles; i++) {
                dao.deleteProfile(idOffset + i);
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
            UserProfileDTO[] profiles = dao.findProfiles(criteria);

            assertEquals("No profiles should have been found", 0, profiles.length);
        } finally {
            // Clean-up by deleting the inserted profiles.
            for (int i = 0; i < numProfiles; i++) {
                dao.deleteProfile(idOffset + i);
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
            dao.findProfiles(null);
            fail("IllegalArgumentException should be thrown: null argument");
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
            dao.findProfiles(criteria);
            fail("IllegalArgumentException should be thrown: map argument has null keys");
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
            dao.findProfiles(criteria);
            fail("IllegalArgumentException should be thrown: map argument has non-String keys");
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
            dao.findProfiles(criteria);
            fail("IllegalArgumentException should be thrown: map argument has empty string keys");
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
            dao.findProfiles(criteria);
            fail("IllegalArgumentException should be thrown: no table column name mapping to criterion");
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
        UserProfileDTO[] profiles = dao.retrieveAllProfiles();

        try {
            // At least numProfiles profiles should have been returned. There
            // may
            // be more that were not created by this unit test.
            assertTrue("The number of profiles is incorrect", profiles.length >= numProfiles);

            // Iterate through all the returned profiles and check if the
            // numProfiles profiles are there.
            int numReturned = 0;
            for (int i = 0; i < profiles.length; i++) {
                // Get the User object contained in the user profile.
                User user = null;
                if (profiles[i].contains(UserProfileDTO.PLAYER_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.PLAYER_KEY);
                } else if (profiles[i].contains(UserProfileDTO.ADMIN_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.ADMIN_KEY);
                } else if (profiles[i].contains(UserProfileDTO.SPONSOR_KEY)) {
                    user = (User) profiles[i].get(UserProfileDTO.SPONSOR_KEY);
                } else {
                    fail("Bad user profile returned");
                }

                if (user.getId() >= idOffset && user.getId() <= idOffset + (numProfiles - 1)) {
                    numReturned++;
                }
            }
            assertEquals("Not all the profiles were returned", numProfiles, numReturned);
        } finally {
            // Clean-up by removing all the profiles inserted at the start of
            // the test.
            for (int i = 0; i < numProfiles; i++) {
                dao.deleteProfile(idOffset + i);
            }
        }
    }

    /**
     * <p>
     * Tests that SQLServerUserProfileDAO implements the PendingConfirmationDAO
     * interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("SQLServerUserProfileDAO should implement the UserProfileDAO interface",
                   dao instanceof UserProfileDAO);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(SQLServerUserProfileDAOTest.class);
    }

    /**
     * <p>
     * Populates the database with the specified number of user profiles,
     * starting at the given ID.
     * </p>
     *
     * @param numProfiles the number of user profiles to insert into the
     *        database
     * @param idOffset the ID of the first user profile to insert into the
     *        database (the last ID will be idOffset + numProfiles)
     * @throws PersistenceException if populating the database fails
     */
    private void populateDatabase(int numProfiles, long idOffset) throws PersistenceException {
        for (int i = 0; i < numProfiles; i++) {
            UserProfileDTO profile = null;
            long id = idOffset + i;
            if (i % 2 == 0) {
                profile = createAdminProfile(id);
            } else if (i % 3 == 0) {
                profile = createPlayerProfile(id, false);
            } else {
                profile = createSponsorProfile(id, true);
            }

            dao.insertProfile(profile);
        }
    }

    /**
     * <p>
     * Creates a user profile DTO containing a Player object with the specified
     * ID that can be used for testing purposes. If the addContactInfo parameter
     * is true, then the ContactInfo object associated with the Player object is
     * created as well.
     * </p>
     *
     * @param id the ID of the player user profile
     * @return the player user profile with the specified ID
     * @param addContactInfo whether the contact information object associated
     *        with the player should be created as well
     */
    private UserProfileDTO createPlayerProfile(long id, boolean addContactInfo) {
        Player player = new Player(id);
        setUserInfo(player);

        UserProfileDTO profile = new UserProfileDTO();
        profile.put(UserProfileDTO.PLAYER_KEY, player);

        if (addContactInfo) {
            addContactInfo(player.getId(), profile);
        }

        return profile;
    }

    /**
     * <p>
     * Creates a user profile DTO containing an Admin object with the specified
     * ID that can be used for testing purposes.
     * </p>
     *
     * @param id the ID of the admin user profile
     * @return the admin user profile with the specified ID
     */
    private UserProfileDTO createAdminProfile(long id) {
        Admin admin = new Admin(id);
        setUserInfo(admin);

        UserProfileDTO profile = new UserProfileDTO();
        profile.put(UserProfileDTO.ADMIN_KEY, admin);

        return profile;
    }

    /**
     * <p>
     * Creates a user profile DTO containing a Sponsor object with the specified
     * ID that can be used for testing purposes. If the addContactInfo parameter
     * is true, then the ContactInfo object associated with the Sponsor object
     * is created as well.
     * </p>
     *
     * @param id the ID of the sponsor user profile
     * @return the sponsor user profile with the specified ID
     * @param addContactInfo whether the contact information object associated
     *        with the sponsor should be created as well
     */
    private UserProfileDTO createSponsorProfile(long id, boolean addContactInfo) {
        Sponsor sponsor = new Sponsor(id);
        sponsor.setFax("837-8957-1891");
        sponsor.setPaymentPref("Bank deposit");
        sponsor.setApproved(Sponsor.APPROVED_FALSE);
        setUserInfo(sponsor);

        UserProfileDTO profile = new UserProfileDTO();
        profile.put(UserProfileDTO.SPONSOR_KEY, sponsor);

        if (addContactInfo) {
            addContactInfo(sponsor.getId(), profile);
        }

        return profile;
    }

    /**
     * <p>
     * Sets the fields in the given User object to test values.
     * </p>
     *
     * @param user the User object whose fields should be set
     */
    private void setUserInfo(User user) {
        user.setHandle("tcsdeveloper");
        user.setEmail("tcsdeveloper@topcodersoftware.com");
        user.setPassword("repolevedsct");
        user.setActive("true");
    }

    /**
     * <p>
     * Creates a ContactInfo object with the specified ID that can be used for
     * testing purposes, and adds it to the given user profile DTO.
     * </p>
     *
     * @param id the ID of the contact information
     * @param profile the user profile DTO to which the ContactInfo object
     *        should be added
     */
    private void addContactInfo(long id, UserProfileDTO profile) {
        ContactInfo contactInfo = new ContactInfo(id);
        contactInfo.setFirstName("TopCoder");
        contactInfo.setLastName("Developer");
        contactInfo.setAddress1("123 Some Road");
        contactInfo.setCity("Mycity");
        contactInfo.setState("Mystate");
        contactInfo.setPostalCode("8713");
        contactInfo.setTelephone("981-3472-8475");

        profile.put(UserProfileDTO.CONTACT_INFO_KEY, contactInfo);
    }

}
