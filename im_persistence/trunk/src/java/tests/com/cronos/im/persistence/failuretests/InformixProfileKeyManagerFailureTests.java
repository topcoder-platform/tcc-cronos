/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.failuretests;

import com.cronos.im.persistence.ConfigurationException;
import com.cronos.im.persistence.InformixProfileKeyManager;
import com.cronos.im.persistence.ProfileKeyValidationException;
import com.topcoder.chat.user.profile.DuplicateProfileKeyException;
import com.topcoder.chat.user.profile.ProfileKey;
import com.topcoder.chat.user.profile.ProfileKeyManagerPersistenceException;
import com.topcoder.chat.user.profile.ProfileKeyNotFoundException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.datavalidator.ObjectValidator;
import com.topcoder.util.idgenerator.IDGenerator;


/**
 * <p>Failure test cases for InformixProfileKeyManager.</p>
 * 
 * @author waits
 * @version 1.0
 */
public class InformixProfileKeyManagerFailureTests extends BasePersistenceSupport {
    /** InformixProfileKeyManager instance to test against. */
    private InformixProfileKeyManager manager = null;

    /**
     * Test the default ctor, the default ns does not exist,ConfigurationException expected.
     */
    public void testDefaultCtor() {
        try {
            new InformixProfileKeyManager();
            fail("The default namespace does not exist.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test the ctor with null value namespace, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testCtor_nullNamespace() throws Exception {
        try {
            new InformixProfileKeyManager(null);
            fail("The namespace is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the ctor with empty value namespace, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testCtor_emptyNamespace() throws Exception {
        try {
            new InformixProfileKeyManager(" ");
            fail("The namespace is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the ctor with not exist namespace, ConfigurationException expected.
     */
    public void testCtor_notExistNamespace() {
        try {
            new InformixProfileKeyManager("notExistNS");
            fail("The namespace pass in does not exist.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Helper class for invalid configration test.
     *
     * @param namespace configuration namespace
     */
    private void invalidConfiguration(String namespace) {
        try {
            new InformixProfileKeyManager(namespace);
            fail("The configuration is invalid.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property
     * 'specification_factory_namespace' is missing. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration1() {
        invalidConfiguration("failure1");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property
     * 'specification_factory_namespace' is invalid. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration2() {
        invalidConfiguration("failure2");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property 'db_connection_factory_key'
     * is missing. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration3() {
        invalidConfiguration("failure3");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property 'db_connection_factory_key'
     * is invalid. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration4() {
        invalidConfiguration("failure4");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property 'connection_name' is missing.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration5() {
        invalidConfiguration("failure5");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property 'connection_name' is invlaid.
     * But the ctor will not throw any exception.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidConfiguration9() throws Exception {
        new InformixProfileKeyManager("failure9");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the optional property 'validtor_key' is invalid.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration7() {
        invalidConfiguration("failure7");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the optional property 'validtor_key' value of the
     * namespace is invalid. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration8() {
        invalidConfiguration("failure8");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the 'id_generator' is invalid.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration10() {
        invalidConfiguration("failure10");
    }

    /**
     * Test the InformixProfileKeyManager(DBConnectionFactory connectionFactory, String connectionName, IDGenerator,
     * com.topcoder.util.datavalidator.ObjectValidator validator) method, with null connectionFactory, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullConnFactory() throws Exception {
        ObjectValidator validator = createObjectValidator();
        IDGenerator idGenerator = createIDGenerator();

        try {
            new InformixProfileKeyManager(null, CONN_NAME, idGenerator, validator);
            fail("The DBConnectionFactory instance is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the InformixProfileKeyManager(DBConnectionFactory connectionFactory, String connectionName,IDGenerator,
     * com.topcoder.util.datavalidator.ObjectValidator validator) method, with null connectionName, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullConnName() throws Exception {
        ObjectValidator validator = createObjectValidator();
        IDGenerator idGenerator = createIDGenerator();
        DBConnectionFactory factory = this.createDBConnectionFactory();

        try {
            new InformixProfileKeyManager(factory, null, idGenerator, validator);
            fail("The connectionName instance is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the InformixProfileKeyManager(DBConnectionFactory connectionFactory, String connectionName,IDGenerator,
     * com.topcoder.util.datavalidator.ObjectValidator validator) method, with empty connectionName, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyConnName() throws Exception {
        ObjectValidator validator = createObjectValidator();
        IDGenerator idGenerator = createIDGenerator();
        DBConnectionFactory factory = this.createDBConnectionFactory();

        try {
            new InformixProfileKeyManager(factory, " ", idGenerator, validator);
            fail("The connectionName instance is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the InformixProfileKeyManager(DBConnectionFactory connectionFactory, String connectionName,IDGenerator,
     * com.topcoder.util.datavalidator.ObjectValidator validator) method, with empty connectionName, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullIDGenerator() throws Exception {
        ObjectValidator validator = createObjectValidator();
        DBConnectionFactory factory = this.createDBConnectionFactory();

        try {
            new InformixProfileKeyManager(factory, "informix", null, validator);
            fail("The IDGenerator instance is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test createProfileKey with null value instance, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCreateProfileKey_nullValue() throws Exception {
        this.manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.createProfileKey(null);
            fail("The ProfileKey is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createProfileKey with unkknown value instance, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCreateProfileKey_unknownType() throws Exception {
        this.manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.createProfileKey(TestHelper.createProfileKey(1, "john", "unknownType"));
            fail("The ProfileKey is of  invalid type.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createProfileKey with invalid instance, ProfileKeyValidationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCreateProfileKey_invalidProfileKey()
        throws Exception {
        this.manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.createProfileKey(TestHelper.createProfileKey(1, "notJohn", "Unregistered"));
            fail("The ProfileKey is of  invalid .");
        } catch (ProfileKeyValidationException e) {
            //good
        }
    }

    /**
     * Test createProfileKey with invalid instance, ProfileKeyValidationException expected.
     *
     * @throws Exception into Junit
     */
    public void testCreateProfileKey_duplicatedProfileKey()
        throws Exception {
        this.manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        ProfileKey key = this.manager.createProfileKey(TestHelper.createProfileKey(1, "john", "Unregistered"));

        try {
            this.manager.createProfileKey(key);
            fail("The ProfileKey is duplicated .");
        } catch (DuplicateProfileKeyException e) {
            //good
        }
    }

    /**
     * Test delete profile key method, the profile does not exist.ProfileKeyNotFoundException expected.
     *
     * @throws Exception into Junit
     */
    public void testDeleteProfileKey() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.deleteProfileKey(1);
            fail("The profile does not exist");
        } catch (ProfileKeyNotFoundException e) {
            //good
        }
    }

    /**
     * Test get profile key method, connection name is invalid,ProfileKeyManagerPersistenceException expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKey_invalidConnectionName()
        throws Exception {
        this.manager = new InformixProfileKeyManager("failure9");

        try {
            this.manager.getProfileKey(1);
            fail("The connection name is invalid");
        } catch (ProfileKeyManagerPersistenceException e) {
            //good
        }
    }

    /**
     * Test getProfileKey method with negative id, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKey_negativeKeyID() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKey(-1);
            fail("The profile does not exist");
        } catch (IllegalArgumentException e) {
            //good
        }

        this.manager.getProfileKey(0);
    }

    /**
     * Test the getProfileKeys with null ids array. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys_nullIdArray() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys((long[]) null);
            fail("The ids array is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the getProfileKeys with empty ids array. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys_emptyIdArray() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys((new long[0]));
            fail("The ids array is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the getProfileKeys with invalid ids array. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys_invalidIdArray() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(new long[] { 1, -1 });
            fail("The ids array is invalid,contains negative id.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKey(String username, String type) method with null username. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKey_nullusername() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKey(null, "Unregistered");
            fail("username is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKey(String username, String type) method with empty username. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKey_emptyUsername() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKey(" ", "Unregistered");
            fail("username is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKey(String username, String type) method with null type. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKey_nulltype() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKey("john", null);
            fail("type is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKey(String username, String type) method with empty type. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKey_emptyType() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKey("john", "");
            fail("type is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKey(String username, String type) method with unknown type. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKey_invalidType() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKey("john", "UnregisteredNot");
            fail("type is unknown.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test getProfileKeys(String[] usernames, String type) with null usernames array. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKyes_nullusernames() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(null, "Unregistered");
            fail("usernames is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test getProfileKeys(String[] usernames, String type) with empty usernames array. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKyes_emptyusernames() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(new String[] {  }, "Unregistered");
            fail("usernames is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test getProfileKeys(String[] usernames, String type) with null element usernames array. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKyes_nullElementusernames()
        throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(new String[] { null }, "Unregistered");
            fail("null element in usernames.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test getProfileKeys(String[] usernames, String type) with empty element usernames array. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKyes_emptyElementusernames()
        throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(new String[] { "" }, "Unregistered");
            fail("empty element in usernames.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKeys(String []usernames, String type) method with null type. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys_nulltype() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(new String[] { "john" }, null);
            fail("type is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKeys(String []usernames, String type) method with empty type. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys_emptyType() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(new String[] { "john" }, "");
            fail("type is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKeys(String []usernames, String type) method with unknown type. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys_invalidType() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(new String[] { "john" }, "UnregisteredNot");
            fail("type is unknown.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKeys(String type) method with unknown type. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys2_invalidType() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys("UnregisteredNot");
            fail("type is unknown.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKeys(String type). No profileKey can not found. ProfileKeyNotFoundException expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys2_withType() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys("Unregistered");
            fail("No profileKey can not found.");
        } catch (ProfileKeyNotFoundException e) {
            //good
        }
    }

    /**
     * Test getProfileKeys(String []type) method with unknown type. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys3_invalidType() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(new String[] { "UnregisteredNot" });
            fail("type is unknown.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKeys(String []type) method with null types. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys3_nullTypes() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys((String[]) null);
            fail("types is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKeys(String []type) method with null type. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys3_nullElementTypes()
        throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(new String[] { null });
            fail("types contains null type.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfileKeys(String []type). No profileKey can not found. ProfileKeyNotFoundException expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfileKeys3_withType() throws Exception {
        manager = new InformixProfileKeyManager(TestHelper.INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE);

        try {
            this.manager.getProfileKeys(new String[] { "Unregistered" });
            fail("No profileKey can not found.");
        } catch (ProfileKeyNotFoundException e) {
            //good
        }
    }
}
