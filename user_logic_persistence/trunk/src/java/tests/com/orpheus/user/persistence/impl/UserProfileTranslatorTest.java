/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.orpheus.user.persistence.ConfigHelper;
import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.ObjectTranslator;
import com.orpheus.user.persistence.TranslationException;
import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.ejb.UserProfileDTO;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.ConfigProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.ConfigProfileTypeFactory;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * Tests the UserProfileTranslator class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class UserProfileTranslatorTest extends TestCase {

    /**
     * <p>
     * A test UserProfileTranslator configuration file containing valid
     * configuration.
     * </p>
     */
    private static final String VALID_CONFIG_FILE = "test_conf/valid_userprofiletranslator_config.xml";

    /**
     * <p>
     * A test UserProfileTranslator configuration file containing invalid
     * configuration.
     * </p>
     */
    private static final String INVALID_CONFIG_FILE = "test_conf/invalid_userprofiletranslator_config.xml";

    /**
     * <p>
     * The prefix for the test UserProfileTranslator configuration namespaces.
     * </p>
     */
    private static final String NAMESPACE_PREFIX = UserProfileTranslator.class.getName();

    /**
     * <p>
     * The namespace from which to read valid UserProfileTranslator
     * configuration.
     * </p>
     */
    private static final String VALID_TRANSLATOR_NAMESPACE = NAMESPACE_PREFIX + ".valid";

    /**
     * <p>
     * The namespace from which to read valid Object Factory configuration for
     * the UserProfileTranslator class.
     * </p>
     */
    private static final String VALID_OBJFACTORY_NAMESPACE = NAMESPACE_PREFIX + ".objFactory";

    /**
     * <p>
     * An invalid UserProfileTranslator configuration namespace where the
     * "specNamespace" property is missing.
     * </p>
     */
    private static final String MISSING_SPECNAMESPACE_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".missingSpecNamespace";

    /**
     * <p>
     * An invalid UserProfileTranslator configuration namespace where the
     * "specNamespace" property value is an empty string.
     * </p>
     */
    private static final String EMPTY_SPECNAMESPACE_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".emptySpecNamespace";

    /**
     * <p>
     * An invalid UserProfileTranslator configuration namespace where the
     * "specNamespace" property refers to an unknown Object Factory
     * configuration namespace.
     * </p>
     */
    private static final String INVALID_SPECNAMESPACE_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".invalidSpecNamespace";

    /**
     * <p>
     * An invalid UserProfileTranslator configuration namespace where the
     * "factoryKey" property is missing.
     * </p>
     */
    private static final String MISSING_FACTORYKEY_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".missingFactoryKey";

    /**
     * <p>
     * An invalid UserProfileTranslator configuration namespace where the
     * "factoryKey" property value is an empty string.
     * </p>
     */
    private static final String EMPTY_FACTORYKEY_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".emptyFactoryKey";

    /**
     * <p>
     * An invalid UserProfileTranslator configuration namespace where the
     * "factoryKey" property refers to an unknown Object Factory key.
     * </p>
     */
    private static final String INVALID_FACTORYKEY_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".invalidFactoryKey";

    /**
     * <p>
     * An invalid Object Factory configuration namespace where the
     * ConfigProfileTypeFactory object specification is incorrect/incomplete.
     * </p>
     */
    private static final String INVALID_OBJECT_SPECIFICATION_NAMESPACE = VALID_OBJFACTORY_NAMESPACE
            + ".invalidSpecification";

    /**
     * <p>
     * An invalid Object Factory configuration namespace where the object to
     * create is not of type ConfigProfileTypeFactory.
     * </p>
     */
    private static final String WRONG_CLASS_NAMESPACE = VALID_OBJFACTORY_NAMESPACE + ".wrongClass";

    /**
     * <p>
     * The namespace to pass to the ConfigProfileTypeFactory constructor when
     * creating ConfigProfileTypes for testing purposes.
     * </p>
     */
    private static final String CONFIG_PROFILETYPE_FACTORY_NAMESPACE = "com.topcoder.util.log";

    /**
     * <p>
     * A sample user ID to put in the test user profile.
     * </p>
     */
    private static final long ID = 1828359239;

    /**
     * <p>
     * A sample user handle to put in the test user profile.
     * </p>
     */
    private static final String HANDLE = "tcsdeveloper";

    /**
     * <p>
     * A sample user email address to put in the test user profile.
     * </p>
     */
    private static final String EMAIL = "tcsdeveloper@topcodersoftware.com";

    /**
     * <p>
     * A sample user password to put in the test user profile.
     * </p>
     */
    private static final String PASSWORD = "repolevedsct";

    /**
     * <p>
     * A sample user active flag to put in the test user profile.
     * </p>
     */
    private static final String USER_IS_ACTIVE = "true";

    /**
     * <p>
     * A sample player or sponsor payment preference to put in the test user
     * profile.
     * </p>
     */
    private static final String PAYMENT_PREF = "Cheque";

    /**
     * <p>
     * A sample sponsor fax number to put in the test user profile.
     * </p>
     */
    private static final String FAX = "881-011-1563";

    /**
     * <p>
     * A sample first name to put in the test user profile.
     * </p>
     */
    private static final String FIRSTNAME = "Joe";

    /**
     * <p>
     * A sample last name to put in the test user profile.
     * </p>
     */
    private static final String LASTNAME = "User";

    /**
     * <p>
     * A sample address1 field to put in the test user profile.
     * </p>
     */
    private static final String ADDRESS1 = "71 Cobblestone Road";

    /**
     * <p>
     * A sample address2 field to put in the test user profile.
     * </p>
     */
    private static final String ADDRESS2 = "Rocklands";

    /**
     * <p>
     * A sample city to put in the test user profile.
     * </p>
     */
    private static final String CITY = "Boulder City";

    /**
     * <p>
     * A sample state to put in the test user profile.
     * </p>
     */
    private static final String STATE = "Canyon";

    /**
     * <p>
     * A sample postal code to put in the test user profile.
     * </p>
     */
    private static final String POSTAL_CODE = "45399";

    /**
     * <p>
     * A sample telephone number to put in the test user profile.
     * </p>
     */
    private static final String TELEPHONE = "121-8254-1306";

    /**
     * <p>
     * The UserProfileTranslator instance to test.
     * </p>
     */
    private UserProfileTranslator translator = null;

    /**
     * <p>
     * Loads the test configuration namespaces into the Configuration Manager,
     * and creates the test UserProfileTranslator instance.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        // Load the configuration.
        ConfigHelper.loadConfig(VALID_CONFIG_FILE, VALID_OBJFACTORY_NAMESPACE);
        ConfigHelper.loadConfig(VALID_CONFIG_FILE, VALID_TRANSLATOR_NAMESPACE);
        ConfigHelper.loadLoggerConfig();
        ConfigHelper.loadProfileTypesConfig();

        // Create the UserProfileTranslator instance.
        translator = new UserProfileTranslator(VALID_TRANSLATOR_NAMESPACE);
    }

    /**
     * <p>
     * Unloads the test configuration namespaces from the Configuration Manager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigHelper.unloadConfig(VALID_OBJFACTORY_NAMESPACE);
        ConfigHelper.unloadConfig(VALID_TRANSLATOR_NAMESPACE);
        ConfigHelper.unloadLoggerConfig();
        ConfigHelper.unloadProfileTypesConfig();
    }

    /**
     * <p>
     * Tests the UserProfileTranslator(String namespace) constructor with a
     * valid argument. The newly created instance should not be null.
     * </p>
     */
    public void testCtorWithValidArg() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The UserProfileTranslator instance should not be null", translator);
    }

    /**
     * <p>
     * Tests that the UserProfileTranslator(String namespace) constructor throws
     * an IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithNullArg() throws Exception {
        try {
            new UserProfileTranslator(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the UserProfileTranslator(String namespace) constructor throws
     * an IllegalArgumentException when the argument is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyArg() throws Exception {
        try {
            new UserProfileTranslator(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the UserProfileTranslator(String namespace) constructor throws
     * an ObjectInstantiationException when the "specNamespace" configuration
     * property is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithMissingSpecNamespaceConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(MISSING_SPECNAMESPACE_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the UserProfileTranslator(String namespace) constructor throws
     * an ObjectInstantiationException when the "specNamespace" configuration
     * property value is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptySpecNamespaceConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(EMPTY_SPECNAMESPACE_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the UserProfileTranslator(String namespace) constructor throws
     * an ObjectInstantiationException when the "specNamespace" configuration
     * property refers to an unknown Object Factory configuration namespace.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidSpecNamespaceConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(INVALID_SPECNAMESPACE_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the UserProfileTranslator(String namespace) constructor throws
     * an ObjectInstantiationException when the "factoryKey" configuration
     * property is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithMissingFactoryKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(MISSING_FACTORYKEY_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the UserProfileTranslator(String namespace) constructor throws
     * an ObjectInstantiationException when the "factoryKey" configuration
     * property value is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyFactoryKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(EMPTY_FACTORYKEY_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the UserProfileTranslator(String namespace) constructor throws
     * an ObjectInstantiationException when the "factoryKey" configuration
     * property refers to an unknown Object Factory key.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidFactoryKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(INVALID_FACTORYKEY_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the UserProfileTranslator(String namespace) constructor throws
     * an ObjectInstantiationException when the Object Factory configuration
     * contains an invalid object specification.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidObjectSpecificationConfig() throws Exception {
        performCtorTestWithInvalidConfig(INVALID_OBJECT_SPECIFICATION_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the UserProfileTranslator(String namespace) constructor throws
     * an ObjectInstantiationException when the object to create with the
     * "factoryKey" configuration property refers to an object which is not of
     * type ConfigProfileTypeFactory.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithWrongClassInObjectFactoryConfig() throws Exception {
        performCtorTestWithInvalidConfig(WRONG_CLASS_NAMESPACE);
    }

    /**
     * <p>
     * Performs the UserProfileTranslator(String namespace) constructor test
     * with invalid configuration. The given invalid configuration namespace is
     * loaded into the Configuration Manager. Then, the constructor is called
     * with the given namespace. An ObjectInstantiationException is expected to
     * be thrown.
     * </p>
     * <p>
     * The given configuration namespace is unloaded from the Configuration
     * Manager after the test has been performed. This will occur whether the
     * test passed or failed.
     * </p>
     *
     * @param namespace the invalid configuration namespace to load into the
     *        Configuration Manager prior to running the test
     * @throws ConfigManagerException if loading or unloading configuration
     *         namespace fails
     */
    private void performCtorTestWithInvalidConfig(String namespace) throws ConfigManagerException {
        ConfigHelper.loadConfig(INVALID_CONFIG_FILE, namespace);
        try {
            new UserProfileTranslator(namespace);
            fail("ObjectInstantiationException should be thrown: invalid config namespace: " + namespace);
        } catch (ObjectInstantiationException e) {
            // Success.
        } finally {
            ConfigHelper.unloadConfig(namespace);
        }
    }

    /**
     * <p>
     * Tests the assembleVO(Object dataTransferObject) method with a valid
     * player user profile DTO containing no contact information. The returned
     * UserProfile should contain a all the player information present in the
     * Player object stored by the DTO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithValidPlayerProfileDTO() throws Exception {
        performAssembleVOTestWithValidArg(UserConstants.PLAYER_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the assembleVO(Object dataTransferObject) method with a valid
     * player user profile DTO containing contact information. The returned
     * UserProfile should contain a all the player and contact information
     * present in the Player and ContactInfo objects stored by the DTO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithValidPlayerProfileDTOAndContactInfo() throws Exception {
        performAssembleVOTestWithValidArg(UserConstants.PLAYER_TYPE_NAME, true);
    }

    /**
     * <p>
     * Tests the assembleVO(Object dataTransferObject) method with a valid admin
     * user profile DTO. The returned UserProfile should contain a all the admin
     * information present in the Admin object stored by the DTO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithValidAdminProfileDTO() throws Exception {
        performAssembleVOTestWithValidArg(UserConstants.ADMIN_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the assembleVO(Object dataTransferObject) method with a valid
     * sponsor user profile DTO containing no contact information. The returned
     * UserProfile should contain a all the sponsor information present in the
     * Sponsor object stored by the DTO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithValidSponsorProfileDTO() throws Exception {
        performAssembleVOTestWithValidArg(UserConstants.SPONSOR_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the assembleVO(Object dataTransferObject) method with a valid
     * sponsor user profile DTO containing contact information. The returned
     * UserProfile should contain a all the sponsor and contact information
     * present in the Sponsor and ContactInfo objects stored by the DTO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithValidSponsorProfileDTOAndContactInfo() throws Exception {
        performAssembleVOTestWithValidArg(UserConstants.SPONSOR_TYPE_NAME, true);
    }

    /**
     * <p>
     * Performs the assembleVO(Object dataTransferObject) method test. A player,
     * admin or sponsor user profile is created, depending on the value of the
     * "userTypeName" argument. If the "addContactInfo" argument is true and the
     * user is a player or sponsor, then the contact information is added to the
     * user profile as well.
     * </p>
     * <p>
     * The return value of the assembleVO(Object dataTransferObject) method
     * should contain a player, admin or sponsor profile type, along with all
     * the information in the corresponding Player, Admin or Sponsor objects
     * stored by the DTO. The profile should also contain the base and
     * credentials profile types. If a ContactInfo object is present in the DTO,
     * and the user is either a player or sponsor, then the returned user
     * profile should contain the address profile type as well as all the
     * contact information in that ContactInfo object.
     * </p>
     *
     * @param userTypeName the type of user profile to create - player, admin or
     *        sponsor
     * @param addContactInfo whether the user contact information should be
     *        added to the user profile
     * @throws TranslationException if translating the DTO to the VO fails
     */
    private void performAssembleVOTestWithValidArg(String userTypeName, boolean addContactInfo)
            throws TranslationException {
        // Create the user profile.
        UserProfileDTO profileDTO = new UserProfileDTO();

        User user = null;

        // Add the specific user profile types and information.
        if (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)) {
            // Player
            Player player = new Player(ID);
            user = player;
            player.setPaymentPref(PAYMENT_PREF);
            profileDTO.put(UserProfileDTO.PLAYER_KEY, player);
        } else if (userTypeName.equals(UserConstants.ADMIN_TYPE_NAME)) {
            // Admin
            user = new Admin(ID);
            profileDTO.put(UserProfileDTO.ADMIN_KEY, user);
        } else if (userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME)) {
            // Sponsor
            Sponsor sponsor = new Sponsor(ID);
            user = sponsor;
            sponsor.setFax(FAX);
            sponsor.setPaymentPref(PAYMENT_PREF);
            sponsor.setApproved(Sponsor.APPROVED_TRUE);
            profileDTO.put(UserProfileDTO.SPONSOR_KEY, sponsor);
        }

        // Set the general user information.
        user.setHandle(HANDLE);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setActive(USER_IS_ACTIVE);

        // If addContactInfo is true and the user is a player or sponsor,
        // add the address profile type and contact information to the profile.
        if (addContactInfo
                && (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)
                        || userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME))) {
            ContactInfo contactInfo = new ContactInfo(ID);
            contactInfo.setFirstName(FIRSTNAME);
            contactInfo.setLastName(LASTNAME);
            contactInfo.setAddress1(ADDRESS1);
            contactInfo.setAddress2(ADDRESS2);
            contactInfo.setCity(CITY);
            contactInfo.setState(STATE);
            contactInfo.setPostalCode(POSTAL_CODE);
            contactInfo.setTelephone(TELEPHONE);
            profileDTO.put(UserProfileDTO.CONTACT_INFO_KEY, contactInfo);
        }

        // Translate to a UserProfile.
        UserProfile profile = (UserProfile) translator.assembleVO(profileDTO);

        // Check the specific user information.
        if (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)) {
            // Player
            assertNotNull("The player profile type does not exist in the profile",
                          profile.getProfileType(UserConstants.PLAYER_TYPE_NAME));
            assertEquals("The payment pref is incorrect", PAYMENT_PREF,
                         profile.getProperty(UserConstants.PLAYER_PAYMENT_PREF));
        } else if (userTypeName.equals(UserConstants.ADMIN_TYPE_NAME)) {
            // Admin
            assertNotNull("The admin profile type does not exist in the profile",
                          profile.getProfileType(UserConstants.ADMIN_TYPE_NAME));
        } else if (userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME)) {
            // Sponsor
            assertNotNull("The sponsor profile type does not exist in the profile",
                          profile.getProfileType(UserConstants.SPONSOR_TYPE_NAME));
            assertEquals("The fax is incorrect", FAX, profile.getProperty(UserConstants.SPONSOR_FAX_NUMBER));
            assertEquals("The payment pref is incorrect", PAYMENT_PREF,
                         profile.getProperty(UserConstants.SPONSOR_PAYMENT_PREF));
            assertEquals("The approved flag is incorrect", Sponsor.APPROVED_TRUE,
                         profile.getProperty(UserConstants.SPONSOR_APPROVED));
        } else {
            fail("No player, admin or sponsor profile type was found in user profile");
        }

        assertNotNull("The base profile type does not exist in the profile",
                      profile.getProfileType(BaseProfileType.BASE_NAME));

        // If the user is a player or sponsor, then check the contact
        // information if it exists.
        if (addContactInfo
                && (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)
                        || userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME))) {
            assertNotNull("The address profile type does not exist in the profile",
                          profile.getProfileType(UserConstants.ADDRESS_TYPE_NAME));

            assertEquals("The contact info ID is incorrect", new Long(ID), profile.getIdentifier());
            assertEquals("The first name is incorrect", FIRSTNAME, profile.getProperty(BaseProfileType.FIRST_NAME));
            assertEquals("The last name is incorrect", LASTNAME, profile.getProperty(BaseProfileType.LAST_NAME));
            assertEquals("The address1 field is incorrect", ADDRESS1,
                         profile.getProperty(UserConstants.ADDRESS_STREET_1));
            assertEquals("The address2 field is incorrect", ADDRESS2,
                         profile.getProperty(UserConstants.ADDRESS_STREET_2));
            assertEquals("The city is incorrect", CITY, profile.getProperty(UserConstants.ADDRESS_CITY));
            assertEquals("The state is incorrect", STATE, profile.getProperty(UserConstants.ADDRESS_STATE));
            assertEquals("The postal code is incorrect", POSTAL_CODE,
                         profile.getProperty(UserConstants.ADDRESS_POSTAL_CODE));
            assertEquals("The telephone is incorrect", TELEPHONE,
                         profile.getProperty(UserConstants.ADDRESS_PHONE_NUMBER));
        }

        assertNotNull("The credentials profile type does not exist in the profile",
                      profile.getProfileType(UserConstants.CREDENTIALS_TYPE_NAME));

        // Check the general user information.
        assertEquals("The ID is incorrect", new Long(ID), profile.getIdentifier());
        assertEquals("The handle is incorrect", HANDLE, profile.getProperty(UserConstants.CREDENTIALS_HANDLE));
        assertEquals("The email address is incorrect", EMAIL, profile.getProperty(BaseProfileType.EMAIL_ADDRESS));
        assertEquals("The password is incorrect", PASSWORD, profile.getProperty(UserConstants.CREDENTIALS_PASSWORD));
        assertEquals("The active flag is incorrect", USER_IS_ACTIVE,
                     profile.getProperty(UserConstants.CREDENTIALS_IS_ACTIVE));
    }

    /**
     * <p>
     * Tests that the assembleVO(Object dataTransferObject) method throws an
     * IllegalArgumentException when given a null argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithNullArg() throws Exception {
        try {
            translator.assembleVO(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the assembleVO(Object dataTransferObject) method throws an
     * IllegalArgumentException when the argument is not a UserProfileDTO
     * instance. In this test, a UserProfile instance (the value object) is used
     * as an argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithNonUserProfileDTOArg() throws Exception {
        UserProfile dto = new UserProfile(new Long(0));
        try {
            translator.assembleVO(dto);
            fail("IllegalArgumentException should be thrown: argument is not a UserProfileDTO instance");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the assembleVO(Object dataTransferObject) method throws a
     * TranslationException when the UserProfileDTO argument does not contain
     * any Player, Admin or Sponsor objects.
     * </p>
     */
    public void testAssembleVOWithDTOContainingNoUserObject() {
        try {
            translator.assembleVO(new UserProfileDTO());
            fail("TranslationException should be thrown: no Player, Admin or Sponsor object in DTO");
        } catch (TranslationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the assembleVO(Object dataTransferObject) method throws a
     * TranslationException when the UserProfileDTO.PLAYER_KEY maps to a
     * non-Player object in the UserProfileDTO argument.
     * </p>
     */
    public void testAssembleVOWithDTOContainingInvalidPlayerMapping() {
        UserProfileDTO profileDTO = new UserProfileDTO();
        profileDTO.put(UserProfileDTO.PLAYER_KEY, new Admin(ID));
        try {
            translator.assembleVO(profileDTO);
            fail("TranslationException should be thrown: invalid player mapping");
        } catch (TranslationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the assembleVO(Object dataTransferObject) method throws a
     * TranslationException when the UserProfileDTO.ADMIN_KEY maps to a
     * non-Admin object in the UserProfileDTO argument.
     * </p>
     */
    public void testAssembleVOWithDTOContainingInvalidAdminMapping() {
        UserProfileDTO profileDTO = new UserProfileDTO();
        profileDTO.put(UserProfileDTO.ADMIN_KEY, new Player(ID));
        try {
            translator.assembleVO(profileDTO);
            fail("TranslationException should be thrown: invalid admin mapping");
        } catch (TranslationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the assembleVO(Object dataTransferObject) method throws a
     * TranslationException when the UserProfileDTO.SPONSOR_KEY maps to a
     * non-Sponsor object in the UserProfileDTO argument.
     * </p>
     */
    public void testAssembleVOWithDTOContainingInvalidSponsorMapping() {
        UserProfileDTO profileDTO = new UserProfileDTO();
        profileDTO.put(UserProfileDTO.SPONSOR_KEY, new Admin(ID));
        try {
            translator.assembleVO(profileDTO);
            fail("TranslationException should be thrown: invalid sponsor mapping");
        } catch (TranslationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the assembleVO(Object dataTransferObject) method throws a
     * TranslationException when the UserProfileDTO.CONTACT_INFO_KEY maps to a
     * non-ContactInfo object in the UserProfileDTO argument.
     * </p>
     */
    public void testAssembleVOWithDTOContainingInvalidContactInfoMapping() {
        UserProfileDTO profileDTO = new UserProfileDTO();
        profileDTO.put(UserProfileDTO.PLAYER_KEY, new Player(ID));
        profileDTO.put(UserProfileDTO.CONTACT_INFO_KEY, new Admin(ID));
        try {
            translator.assembleVO(profileDTO);
            fail("TranslationException should be thrown: invalid contact info mapping");
        } catch (TranslationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the assembleVO(Object dataTransferObject) method throws a
     * TranslationException when a ConfigProfileType configuration namespace is
     * missing. In this test, the
     * "com.topcoder.user.profile.ConfigProfileType.credentials" namespace
     * corresponding to the UserConstants.CREDENTIALS_TYPE_NAME profile type, is
     * removed from the Configuration Manager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithMissingConfigProfileType() throws Exception {
        // Unload the credentials profile type namespace.
        String namespace = ConfigProfileType.class.getName() + ".credentials";
        ConfigHelper.unloadConfig(namespace);

        UserProfileDTO profileDTO = new UserProfileDTO();
        profileDTO.put(UserProfileDTO.ADMIN_KEY, new Admin(ID));

        // Try to assemble the VO.
        try {
            translator.assembleVO(profileDTO);
            fail("TranslationException should be thrown: missing ConfigProfileType configuration namespace");
        } catch (TranslationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests the assembleDTO(Object valueObject) with a valid player profile
     * containing no contact information. The returned UserProfileDTO should
     * contain a Player object, which contains all the player information
     * present in the given user profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleDTOWithValidPlayerProfile() throws Exception {
        performAssembleDTOTestWithValidArg(UserConstants.PLAYER_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the assembleDTO(Object valueObject) with a valid player profile
     * containing contact information. The returned UserProfileDTO should
     * contain a Player and and ContactInfo object, which contain all the player
     * and contact information present in the given user profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleDTOWithValidPlayerProfileWithContactInfo() throws Exception {
        performAssembleDTOTestWithValidArg(UserConstants.PLAYER_TYPE_NAME, true);
    }

    /**
     * <p>
     * Tests the assembleDTO(Object valueObject) with a valid admin profile. The
     * returned UserProfileDTO should contain an Admin object, which contains
     * all the admin information present in the given user profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleDTOWithValidAdminProfile() throws Exception {
        performAssembleDTOTestWithValidArg(UserConstants.ADMIN_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the assembleDTO(Object valueObject) with a valid sponsor profile
     * containing no contact information. The returned UserProfileDTO should
     * contain a Sponsor object, which contains all the sponsor information
     * present in the given user profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleDTOWithValidSponsorProfile() throws Exception {
        performAssembleDTOTestWithValidArg(UserConstants.SPONSOR_TYPE_NAME, false);
    }

    /**
     * <p>
     * Tests the assembleDTO(Object valueObject) with a valid player profile
     * containing contact information. The returned UserProfileDTO should
     * contain a Sponsor and ContactInfo object, which contain all the sponsor
     * and contact information information present in the given user profile.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleDTOWithValidSponsorProfileWithContactInfo() throws Exception {
        performAssembleDTOTestWithValidArg(UserConstants.SPONSOR_TYPE_NAME, true);
    }

    /**
     * <p>
     * Performs the assembleDTO(Object valueObject) method test. A player, admin
     * or sponsor user profile is created, depending on the value of the
     * "userTypeName" argument. If the "addContactInfo" argument is true and the
     * user is a player or sponsor, then the contact information is added to the
     * user profile as well.
     * </p>
     * <p>
     * The return value of the assembleDTO(Object valueObject) method should
     * contain a Player, Admin or Sponsor object, depending on whether the user
     * profile represents a player, admin or sponsor. This user object should
     * contain all the relevant information present in the user profile. If
     * contact information is present in the user profile, and the user is
     * either a player or sponsor, then the returned UserProfileDTO should
     * contain a ContactInfo object as well. This ContactInfo object should
     * contain all the relevant contact information found in the user profile.
     * </p>
     *
     * @param userTypeName the type of user profile to create - player, admin or
     *        sponsor
     * @param addContactInfo whether the user contact information should be
     *        added to the user profile
     * @throws Exception if testing the assembleDTO(Object valueObject) method
     *         fails
     */
    private void performAssembleDTOTestWithValidArg(String userTypeName, boolean addContactInfo) throws Exception {
        // Create the user profile.
        UserProfile profile = new UserProfile(new Long(ID));

        // Add the credentials protile type.
        ConfigProfileTypeFactory factory = new ConfigProfileTypeFactory(CONFIG_PROFILETYPE_FACTORY_NAMESPACE);
        profile.addProfileType(factory.getProfileType(UserConstants.CREDENTIALS_TYPE_NAME));

        // Set the general user information.
        profile.setProperty(UserConstants.CREDENTIALS_HANDLE, HANDLE);
        profile.setProperty(BaseProfileType.EMAIL_ADDRESS, EMAIL);
        profile.setProperty(UserConstants.CREDENTIALS_PASSWORD, PASSWORD);
        profile.setProperty(UserConstants.CREDENTIALS_IS_ACTIVE, USER_IS_ACTIVE);

        // Add the specific user profile types and information.
        if (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)) {
            // Player
            profile.addProfileType(factory.getProfileType(UserConstants.PLAYER_TYPE_NAME));
            profile.setProperty(UserConstants.PLAYER_PAYMENT_PREF, PAYMENT_PREF);
        } else if (userTypeName.equals(UserConstants.ADMIN_TYPE_NAME)) {
            // Admin
            profile.addProfileType(factory.getProfileType(UserConstants.ADMIN_TYPE_NAME));
        } else if (userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME)) {
            // Sponsor
            profile.addProfileType(factory.getProfileType(UserConstants.SPONSOR_TYPE_NAME));
            profile.setProperty(UserConstants.SPONSOR_FAX_NUMBER, FAX);
            profile.setProperty(UserConstants.SPONSOR_PAYMENT_PREF, PAYMENT_PREF);
            profile.setProperty(UserConstants.SPONSOR_APPROVED, Sponsor.APPROVED_FALSE);
        }

        // If addContactInfo is true and the user is a player or sponsor,
        // add the address profile type and contact information to the profile.
        if (addContactInfo
                && (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)
                        || userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME))) {
            profile.addProfileType(factory.getProfileType(UserConstants.ADDRESS_TYPE_NAME));
            profile.setProperty(BaseProfileType.FIRST_NAME, FIRSTNAME);
            profile.setProperty(BaseProfileType.LAST_NAME, LASTNAME);
            profile.setProperty(UserConstants.ADDRESS_STREET_1, ADDRESS1);
            profile.setProperty(UserConstants.ADDRESS_STREET_2, ADDRESS2);
            profile.setProperty(UserConstants.ADDRESS_CITY, CITY);
            profile.setProperty(UserConstants.ADDRESS_STATE, STATE);
            profile.setProperty(UserConstants.ADDRESS_POSTAL_CODE, POSTAL_CODE);
            profile.setProperty(UserConstants.ADDRESS_PHONE_NUMBER, TELEPHONE);
        }

        // Translate to a UserProfileDTO.
        UserProfileDTO profileDTO = (UserProfileDTO) translator.assembleDTO(profile);

        // Check the specific user information.
        User user = null;
        if (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)) {
            // Player
            Player player = (Player) profileDTO.get(UserProfileDTO.PLAYER_KEY);
            user = player;
            assertEquals("The payment pref is incorrect", PAYMENT_PREF, player.getPaymentPref());
        } else if (userTypeName.equals(UserConstants.ADMIN_TYPE_NAME)) {
            // Admin
            user = (User) profileDTO.get(UserProfileDTO.ADMIN_KEY);
        } else if (userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME)) {
            // Sponsor
            Sponsor sponsor = (Sponsor) profileDTO.get(UserProfileDTO.SPONSOR_KEY);
            user = sponsor;
            assertEquals("The fax is incorrect", FAX, sponsor.getFax());
            assertEquals("The payment pref is incorrect", PAYMENT_PREF, sponsor.getPaymentPref());
            assertEquals("The approved flag is incorrect", Sponsor.APPROVED_FALSE, sponsor.getApproved());
        } else {
            fail("No Player, Admin or Sponsor object was found in DTO");
        }

        // If the user is a player or sponsor, then check the contact
        // information if it exists.
        if (addContactInfo
                && (userTypeName.equals(UserConstants.PLAYER_TYPE_NAME)
                        || userTypeName.equals(UserConstants.SPONSOR_TYPE_NAME))) {
            assertTrue("There should be contact information", profileDTO.contains(UserProfileDTO.CONTACT_INFO_KEY));

            ContactInfo contactInfo = (ContactInfo) profileDTO.get(UserProfileDTO.CONTACT_INFO_KEY);

            assertEquals("The contact info ID is incorrect", ID, contactInfo.getId());
            assertEquals("The first name is incorrect", FIRSTNAME, contactInfo.getFirstName());
            assertEquals("The last name is incorrect", LASTNAME, contactInfo.getLastName());
            assertEquals("The address1 field is incorrect", ADDRESS1, contactInfo.getAddress1());
            assertEquals("The address2 field is incorrect", ADDRESS2, contactInfo.getAddress2());
            assertEquals("The city is incorrect", CITY, contactInfo.getCity());
            assertEquals("The state is incorrect", STATE, contactInfo.getState());
            assertEquals("The postal code is incorrect", POSTAL_CODE, contactInfo.getPostalCode());
            assertEquals("The telephone is incorrect", TELEPHONE, contactInfo.getTelephone());
        }

        // Check the general user information.
        assertEquals("The ID is incorrect", ID, user.getId());
        assertEquals("The handle is incorrect", HANDLE, user.getHandle());
        assertEquals("The email address is incorrect", EMAIL, user.getEmail());
        assertEquals("The password is incorrect", PASSWORD, user.getPassword());
        assertEquals("The active flag is incorrect", USER_IS_ACTIVE, user.getActive());
    }

    /**
     * <p>
     * Tests that the assembleDTO(Object valueObject) method throws an
     * IllegalArgumentException when given a null argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleDTOWithNullArg() throws Exception {
        try {
            translator.assembleDTO(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the assembleDTO(Object valueObject) method throws an
     * IllegalArgumentException when the argument is not a UserProfile instance.
     * In this test, a UserProfileDTO instance (the data transfer object) is
     * used as an argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleDTOWithNonUserProfileArg() throws Exception {
        UserProfileDTO vo = new UserProfileDTO();
        try {
            translator.assembleDTO(vo);
            fail("IllegalArgumentException should be thrown: argument is not a UserProfile instance");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <P>
     * Tests that the assembleDTO(Object valueObject) method throws a
     * TranslationException when the UserProfile argument does not have a base
     * profile type.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleDTOWithArgWithNoBaseProfileType() throws Exception {
        // Create a user profile, with a credentials and admin profile type,
        // but no base profile type.
        UserProfile profile = new UserProfile(new Long(ID));
        ConfigProfileTypeFactory factory = new ConfigProfileTypeFactory(CONFIG_PROFILETYPE_FACTORY_NAMESPACE);
        profile.addProfileType(factory.getProfileType("credentials"));
        profile.addProfileType(factory.getProfileType("admin"));
        profile.removeProfileType(BaseProfileType.BASE_NAME);

        // Try to assemble the DTO.
        try {
            translator.assembleDTO(profile);
            fail("TranslationException should be thrown: profile does not have a base profile type");
        } catch (TranslationException e) {
            // Success.
        }
    }

    /**
     * <P>
     * Tests that the assembleDTO(Object valueObject) method throws a
     * TranslationException when the UserProfile argument does not have a
     * credentials profile type.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleDTOWithArgWithNoCredentialsProfileType() throws Exception {
        // Create a user profile with a base and admin profile type, but no
        // credentials profile type.
        UserProfile profile = new UserProfile(new Long(ID));
        ConfigProfileTypeFactory factory = new ConfigProfileTypeFactory(CONFIG_PROFILETYPE_FACTORY_NAMESPACE);
        profile.addProfileType(factory.getProfileType("admin"));

        // Try to assemble the DTO.
        try {
            translator.assembleDTO(profile);
            fail("TranslationException should be thrown: profile does not have a credentials profile type");
        } catch (TranslationException e) {
            // Success.
        }
    }

    /**
     * <P>
     * Tests that the assembleDTO(Object valueObject) method throws a
     * TranslationException when the UserProfile argument does not have a
     * player, admin or sponsor profile type.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleDTOWithArgWithNoValidUserProfileType() throws Exception {
        // Create a user profile with a base and credentials profile type, but
        // no player, admin or sponsor profile type.
        UserProfile profile = new UserProfile(new Long(ID));
        ConfigProfileTypeFactory factory = new ConfigProfileTypeFactory(CONFIG_PROFILETYPE_FACTORY_NAMESPACE);
        profile.addProfileType(factory.getProfileType("credentials"));

        // Try to assemble the DTO.
        try {
            translator.assembleDTO(profile);
            fail("TranslationException should be thrown: no player, admin or sponsor profile type in profile");
        } catch (TranslationException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that UserProfileTranslator implements the ObjectTranslator
     * interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("UserProfileTranslator should implement the ObjectTranslator interface",
                   translator instanceof ObjectTranslator);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(UserProfileTranslatorTest.class);
    }

}
