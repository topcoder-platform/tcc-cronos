/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cronos.im.persistence.RegisteredChatUserProfileInformixPersistence;
import com.cronos.im.persistence.UserDefinedAttributeNames;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>
 * Accuracy test for <code>{@link RegisteredChatUserProfileInformixPersistence}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class RegisteredChatUserProfileInformixPersistenceAccuracyTests extends BaseTestCase {

    /**
     * <p>
     * Represents the RegisteredChatUserProfileInformixPersistence instance used in tests.
     * </p>
     */
    private RegisteredChatUserProfileInformixPersistence registeredChatUserProfileInformixPersistence;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        registeredChatUserProfileInformixPersistence = new RegisteredChatUserProfileInformixPersistence();

        Statement stmt = getConnection().createStatement();
        // add user
        stmt
            .executeUpdate("INSERT INTO user (user_id, first_name, last_name, handle) VALUES (1, \"Ender\", \"Virn\", \"evirn\")");
        stmt
            .executeUpdate("INSERT INTO user (user_id, first_name, last_name, handle) VALUES (2, \"Jim\", \"Mann\", \"jmann\")");
        stmt
            .executeUpdate("INSERT INTO user (user_id, first_name, last_name, handle) VALUES (3, \"Chris\", \"Rack\", \"crack\")");

        // add email
        stmt.executeUpdate("INSERT INTO email (user_id, address) VALUES (1, \"evirn@bread.com\")");
        stmt.executeUpdate("INSERT INTO email (user_id, address) VALUES (2, \"jmann@bread.com\")");
        stmt.executeUpdate("INSERT INTO email (user_id, address) VALUES (3, \"crack@bread.com\")");

        // add company
        stmt.executeUpdate("INSERT INTO company (company_id, company_name) VALUES (1, \"MicroSoft\")");
        stmt.executeUpdate("INSERT INTO company (company_id, company_name) VALUES (2, \"TopCoder\")");
        stmt.executeUpdate("INSERT INTO company (company_id, company_name) VALUES (3, \"Sun\")");
        stmt.executeUpdate("INSERT INTO company (company_id, company_name) VALUES (4, \"Nabisco\")");

        // add contact
        stmt.executeUpdate("INSERT INTO contact (contact_id, company_id, title) VALUES (1, 1, \"coder\")");
        stmt.executeUpdate("INSERT INTO contact (contact_id, company_id, title) VALUES (2, 2, \"designer\")");
        stmt.executeUpdate("INSERT INTO contact (contact_id, company_id, title) VALUES (3, 3, \"architect\")");
        stmt.executeUpdate("INSERT INTO contact (contact_id, company_id, title) VALUES (3, 4, \"Project Manager\")");

        stmt.close();
    }

    /**
     * <p>
     * Accuracy test for
     * <code>{@link RegisteredChatUserProfileInformixPersistence#RegisteredChatUserProfileInformixPersistence()}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRegisteredChatUserProfileInformixPersistence1Accuracy() throws Exception {
        registeredChatUserProfileInformixPersistence = new RegisteredChatUserProfileInformixPersistence();

        assertNotNull(registeredChatUserProfileInformixPersistence);
    }

    /**
     * <p>
     * Accuracy test for
     * <code>{@link RegisteredChatUserProfileInformixPersistence#RegisteredChatUserProfileInformixPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRegisteredChatUserProfileInformixPersistence2Accuracy() throws Exception {
        registeredChatUserProfileInformixPersistence = new RegisteredChatUserProfileInformixPersistence(
            RegisteredChatUserProfileInformixPersistence.DEFAULT_NAMESPACE);

        assertNotNull(registeredChatUserProfileInformixPersistence);
    }

    /**
     * <p>
     * Accuracy test for
     * <code>{@link RegisteredChatUserProfileInformixPersistence#RegisteredChatUserProfileInformixPersistence(DBConnectionFactory, String, ObjectValidator)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRegisteredChatUserProfileInformixPersistence3Accuracy() throws Exception {
        DBConnectionFactory dbConnectionFactory = new DBConnectionFactoryImpl();

        // case 1
        registeredChatUserProfileInformixPersistence = new RegisteredChatUserProfileInformixPersistence(
            dbConnectionFactory, "Informix");
        assertNotNull(registeredChatUserProfileInformixPersistence);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link RegisteredChatUserProfileInformixPersistence#getProfile(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProfileAccuracy() throws Exception {
        ChatUserProfile chatUserProfile = registeredChatUserProfileInformixPersistence.getProfile("crack");

        assertNotNull(chatUserProfile);

        assertEquals("incorrect first name", "Chris", chatUserProfile
            .getPropertyValue(UserDefinedAttributeNames.FIRST_NAME)[0]);
        assertEquals("incorrect last name", "Rack", chatUserProfile
            .getPropertyValue(UserDefinedAttributeNames.LAST_NAME)[0]);
        assertEquals("incorrect email", "crack@bread.com", chatUserProfile
            .getPropertyValue(UserDefinedAttributeNames.EMAIL)[0]);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link RegisteredChatUserProfileInformixPersistence#getProfiles(String[])}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProfilesAccuracy() throws Exception {
        ChatUserProfile[] chatUserProfiles = registeredChatUserProfileInformixPersistence.getProfiles(new String[] {
            "crack", "NotExist"});

        assertNotNull(chatUserProfiles);
        assertTrue("the array size should be 2.", chatUserProfiles.length == 2);
        assertNotNull(chatUserProfiles[0]);
        assertNull(chatUserProfiles[1]);

        assertEquals("incorrect first name", "Chris", chatUserProfiles[0]
            .getPropertyValue(UserDefinedAttributeNames.FIRST_NAME)[0]);
        assertEquals("incorrect last name", "Rack", chatUserProfiles[0]
            .getPropertyValue(UserDefinedAttributeNames.LAST_NAME)[0]);
        assertEquals("incorrect email", "crack@bread.com", chatUserProfiles[0]
            .getPropertyValue(UserDefinedAttributeNames.EMAIL)[0]);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link RegisteredChatUserProfileInformixPersistence#searchProfiles(Map, String[])}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSearchProfilesAccuracy() throws Exception {
        Map criteria = new HashMap();
        List first = new ArrayList();
        first.add("Ender");
        criteria.put(UserDefinedAttributeNames.FIRST_NAME, first);
        criteria.put(UserDefinedAttributeNames.LAST_NAME, "Virn");

        ChatUserProfile[] chatUserProfiles = registeredChatUserProfileInformixPersistence.searchProfiles(criteria,
            new String[] {"evirn", "jmann", "crack"});

        assertNotNull(chatUserProfiles);
        assertEquals("the array size should be 1.", 1, chatUserProfiles.length);
        assertNotNull(chatUserProfiles[0]);
        assertEquals("incorrect first name", "Ender", chatUserProfiles[0]
            .getPropertyValue(UserDefinedAttributeNames.FIRST_NAME)[0]);
        assertEquals("incorrect last name", "Virn", chatUserProfiles[0]
            .getPropertyValue(UserDefinedAttributeNames.LAST_NAME)[0]);
        assertEquals("incorrect email", "evirn@bread.com", chatUserProfiles[0]
            .getPropertyValue(UserDefinedAttributeNames.EMAIL)[0]);
    }

}
