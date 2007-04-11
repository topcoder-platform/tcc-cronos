/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.failuretests;

import com.cronos.im.persistence.ChatUserProfileValidationException;
import com.cronos.im.persistence.ConfigurationException;
import com.cronos.im.persistence.UnregisteredChatUserProfileInformixPersistence;
import com.cronos.im.persistence.UserDefinedAttributeNames;

import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.ChatUserProfilePersistenceException;
import com.topcoder.chat.user.profile.DuplicateProfileException;
import com.topcoder.chat.user.profile.ProfileNotFoundException;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.util.datavalidator.ObjectValidator;
import com.topcoder.util.idgenerator.IDGenerator;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Failure test cases for UnregisteredChatUserProfileInformixPersistence.</p>
 *
 * @author waits
 * @version 1.0
 */
public class UnregisteredChatUserProfileInformixPersistenceFailureTests extends BasePersistenceSupport {
    /** UnregisteredChatUserProfileInformixPersistence instance to test against. */
    private UnregisteredChatUserProfileInformixPersistence persistence = null;

    /**
     * Test the default ctor, the namespace does not exist.ConfigurationException expected.
     */
    public void testDefaultCtor_notExistNS() {
        try {
            new UnregisteredChatUserProfileInformixPersistence();
            fail("The namespace pass in does not exist.");
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
            new UnregisteredChatUserProfileInformixPersistence(null);
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
            new UnregisteredChatUserProfileInformixPersistence(" ");
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
            new UnregisteredChatUserProfileInformixPersistence("notExistNS");
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
            new UnregisteredChatUserProfileInformixPersistence(namespace);
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
    public void testCtor_invalidConfiguration6() throws Exception {
        new UnregisteredChatUserProfileInformixPersistence("failure6");
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
     * Test the UnregisteredChatUserProfileInformixPersistence(DBConnectionFactory connectionFactory, String
     * connectionName, com.topcoder.util.datavalidator.ObjectValidator validator) method, with null connectionFactory,
     * iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullConnFactory() throws Exception {
        ObjectValidator validator = createObjectValidator();
        IDGenerator idGenerator = createIDGenerator();
        try {
            new UnregisteredChatUserProfileInformixPersistence(null, CONN_NAME, validator);
            fail("The DBConnectionFactory instance is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the UnregisteredChatUserProfileInformixPersistence(DBConnectionFactory connectionFactory, String
     * connectionName, com.topcoder.util.datavalidator.ObjectValidator validator) method, with null connectionName,
     * iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullConnName() throws Exception {
        ObjectValidator validator = createObjectValidator();
        DBConnectionFactory factory = this.createDBConnectionFactory();
        IDGenerator idGenerator = createIDGenerator();
        try {
            new UnregisteredChatUserProfileInformixPersistence(factory, null, validator);
            fail("The connectionName instance is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the UnregisteredChatUserProfileInformixPersistence(DBConnectionFactory connectionFactory, String
     * connectionName, com.topcoder.util.datavalidator.ObjectValidator validator) method, with empty connectionName,
     * iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyConnName() throws Exception {
        ObjectValidator validator = createObjectValidator();
        DBConnectionFactory factory = this.createDBConnectionFactory();
        IDGenerator idGenerator = createIDGenerator();
        try {
            new UnregisteredChatUserProfileInformixPersistence(factory, " ",validator);
            fail("The connectionName instance is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test createProfile method , the profile is null, IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testCreateProfile() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.createProfile(null);
            fail("This profile to create is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createProfile method , the profile is null, IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testCreateProfile_validationFalse() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        ChatUserProfile profile = new ChatUserProfile(TestHelper.createProfileKey(1, "-1", "Unregistered"));

        try {
            this.persistence.createProfile(profile);
            fail("This profile violates the validation.");
        } catch (ChatUserProfileValidationException e) {
            //good
        }
    }

    /**
     * Test createProfile method , the profile is null, IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testCreateProfile_notCompleteProfile()
        throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        ChatUserProfile profile = new ChatUserProfile(TestHelper.createProfileKey(1, "2", "Unregistered"));

        try {
            this.persistence.createProfile(profile);
            fail("This profile is invalid, can not insert into database.");
        } catch (ChatUserProfilePersistenceException e) {
            //good
        }
    }

    /**
     * Test createProfile method , the profile is null, IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testCreateProfile_duplicatedProfile() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        ChatUserProfile profile = new ChatUserProfile(TestHelper.createProfileKey(1, "2", "Unregistered"));
        profile.setProperty(UserDefinedAttributeNames.FIRST_NAME, "ivern");
        profile.setProperty(UserDefinedAttributeNames.LAST_NAME, "ivern");
        profile.setProperty(UserDefinedAttributeNames.COMPANY, "topcoder");
        profile.setProperty(UserDefinedAttributeNames.TITLE, "pm");
        profile.setProperty(UserDefinedAttributeNames.EMAIL, "ivern@topcoder.com");
        this.persistence.createProfile(profile);

        Statement stmt = null;
        ResultSet rs = null;
        try{
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT client_id FROM client WHERE first_name = 'ivern'");
                rs.next();
                long id = rs.getLong("client_id");
                profile = new ChatUserProfile(TestHelper.createProfileKey(id, ""+id, "Unregistered"));
            profile.setProperty(UserDefinedAttributeNames.FIRST_NAME, "ivern");
            profile.setProperty(UserDefinedAttributeNames.LAST_NAME, "ivern");
            profile.setProperty(UserDefinedAttributeNames.COMPANY, "topcoder");
            profile.setProperty(UserDefinedAttributeNames.TITLE, "pm");
            profile.setProperty(UserDefinedAttributeNames.EMAIL, "ivern@topcoder.com");
        } finally{
                rs.close();
                stmt.close();
        }
        try {
            this.persistence.createProfile(profile);
            fail("This profile is invalid, can not insert into database.");
        } catch (DuplicateProfileException e) {
            //good
        }
    }

    /**
     * Test deleteProfile method ,this operation is not supported. UnsupportedOperationException expected.
     *
     * @throws Exception into Junit
     */
    public void testDeleteProfile() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.deleteProfile(null);
            fail("This method is not supported.");
        } catch (UnsupportedOperationException e) {
            //good
        }
    }

    /**
     * Test updateProfile method ,this operation is not supported. UnsupportedOperationException expected.
     *
     * @throws Exception into Junit
     */
    public void testUpdateProfile() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.updateProfile(null);
            fail("This method is not supported.");
        } catch (UnsupportedOperationException e) {
            //good
        }
    }

    /**
     * Test getProfile method, with null username, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfile_nullusername() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.getProfile(null);
            fail("This username is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfile method, with empty username, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfile_emptyusername() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.getProfile(" ");
            fail("This username is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfile method, with not long type value username, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfile_invalidusername() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.getProfile("john");
            fail("This username should be long type value.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfile method, with empty username, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfile_notexistusername() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.getProfile("1");
            fail("This username does not exist");
        } catch (ProfileNotFoundException e) {
            //good
        }
    }

    /**
     * Test getProfile method, with null usernames, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfiles_nullusernames() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.getProfiles(null);
            fail("This usernames is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfile method, with null element usernames, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfiles_nullusername() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.getProfiles(new String[] { "john", null });
            fail("This username is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfile method, with empty usernames,ProfileNotFoundException expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetProfiles_emptyusernames() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.getProfiles(new String[0]);
            fail("This usernames is empty.");
        } catch (ProfileNotFoundException e) {
            //good
        }
    }

    /**
     * Test getProfile method, with empty usernames, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetProfiles_emptyusername() throws Exception {
        this.persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.getProfiles(new String[] { " " });
            fail("This username is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProfile method, with not exist username,
     *
     * @throws Exception into JUnit
     */
    public void testGetProfiles_notexistusername() throws Exception {
        persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            persistence.getProfiles(new String[] { "john" });
            fail("This username does not exist.");
        } catch (ProfileNotFoundException e) {
            //good
        }
    }

    /**
     * Test searchProfiles(Map criteria, String[] registeredUsers) method. the criteria is null, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testSearchProfiles_nullCriteria() throws Exception {
        persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            this.persistence.searchProfiles(null, new String[] { "john" });
            fail("This criteria is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test searchProfiles(Map criteria, String[] registeredUsers) method. the criteria contains null keys, iae
     * expected.
     *
     * @throws Exception into Junit
     */
    public void testSearchProfiles_nullKeysCriteria() throws Exception {
        persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            Map criteria = new HashMap();
            criteria.put(null, "open");
            this.persistence.searchProfiles(criteria, new String[] { "john" });
            fail("This criteria contains null key.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test searchProfiles(Map criteria, String[] registeredUsers) method. the criteria contains null value, iae
     * expected.
     *
     * @throws Exception into Junit
     */
    public void testSearchProfiles_nullValuesCriteria()
        throws Exception {
        persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            Map criteria = new HashMap();
            criteria.put("status", null);
            this.persistence.searchProfiles(criteria, new String[] { "john" });
            fail("This criteria contains null values.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test searchProfiles(Map criteria, String[] registeredUsers) method. the criteria contains empty value, iae
     * expected.
     *
     * @throws Exception into Junit
     */
    public void testSearchProfiles_emptyValuesCriteria()
        throws Exception {
        persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            Map criteria = new HashMap();
            criteria.put("status", " ");
            this.persistence.searchProfiles(criteria, new String[] { "john" });
            fail("This criteria contains empty values.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test searchProfiles(Map criteria, String[] registeredUsers) method. the criteria contains not string/notString
     * List value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testSearchProfiles_invalidValuesCriteria()
        throws Exception {
        persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            Map criteria = new HashMap();
            criteria.put("status", new Object());
            this.persistence.searchProfiles(criteria, new String[] { "john" });
            fail("This criteria contains invalid values.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test searchProfiles(Map criteria, String[] registeredUsers) method. the criteria contains not string/notString
     * List value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testSearchProfiles_invalidValuesCriteria2()
        throws Exception {
        persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            Map criteria = new HashMap();
            List array = new ArrayList();
            array.add(new Object());
            criteria.put("status", array);
            this.persistence.searchProfiles(criteria, new String[] { "john" });
            fail("This criteria contains invalid values.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test searchProfiles(Map criteria, String[] registeredUsers) method. the criteria contains not string/notString
     * List value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testSearchProfiles_invalidValuesCriteria3()
        throws Exception {
        persistence = new UnregisteredChatUserProfileInformixPersistence(TestHelper.UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE);

        try {
            Map criteria = new HashMap();
            List array = new ArrayList();
            array.add(null);
            criteria.put("status", array);
            this.persistence.searchProfiles(criteria, new String[] { "john" });
            fail("This criteria contains invalid values.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
}
