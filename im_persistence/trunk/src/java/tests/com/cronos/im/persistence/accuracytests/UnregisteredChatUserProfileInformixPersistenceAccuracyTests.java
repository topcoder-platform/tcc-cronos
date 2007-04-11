/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cronos.im.persistence.RegisteredChatUserProfileInformixPersistence;
import com.cronos.im.persistence.UnregisteredChatUserProfileInformixPersistence;
import com.cronos.im.persistence.UserDefinedAttributeNames;
import com.topcoder.chat.user.profile.ChatUserProfile;

/**
 * <p>
 * Accuracy test for <code>{@link UnregisteredChatUserProfileInformixPersistence}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class UnregisteredChatUserProfileInformixPersistenceAccuracyTests extends BaseTestCase {

    /**
     * <p>
     * Represents the UnregisteredChatUserProfileInformixPersistence instance used in tests.
     * </p>
     */
    private UnregisteredChatUserProfileInformixPersistence unregisteredChatUserProfileInformixPersistence;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        unregisteredChatUserProfileInformixPersistence = new UnregisteredChatUserProfileInformixPersistence();
    }

    /**
     * <p>
     * Accuracy test for
     * <code>{@link UnregisteredChatUserProfileInformixPersistence#UnunregisteredChatUserProfileInformixPersistence()}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUnunregisteredChatUserProfileInformixPersistence1Accuracy() throws Exception {
        unregisteredChatUserProfileInformixPersistence = new UnregisteredChatUserProfileInformixPersistence();

        assertNotNull(unregisteredChatUserProfileInformixPersistence);
    }

    /**
     * <p>
     * Accuracy test for
     * <code>{@link UnregisteredChatUserProfileInformixPersistence#UnunregisteredChatUserProfileInformixPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUnunregisteredChatUserProfileInformixPersistence2Accuracy() throws Exception {
        unregisteredChatUserProfileInformixPersistence = new UnregisteredChatUserProfileInformixPersistence(
            UnregisteredChatUserProfileInformixPersistence.DEFAULT_NAMESPACE);

        assertNotNull(unregisteredChatUserProfileInformixPersistence);
    }

    /**
     * <p>
     * Accuracy test for
     * <code>{@link UnregisteredChatUserProfileInformixPersistence#UnunregisteredChatUserProfileInformixPersistence(DBConnectionFactory, String, ObjectValidator)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
	 /*
    public void testUnunregisteredChatUserProfileInformixPersistence3Accuracy() throws Exception {
        DBConnectionFactory dbConnectionFactory = new DBConnectionFactoryImpl();

        // case 1
        unregisteredChatUserProfileInformixPersistence = new UnregisteredChatUserProfileInformixPersistence(
            dbConnectionFactory, "Informix", null);
        assertNotNull(unregisteredChatUserProfileInformixPersistence);

        // case 2
        unregisteredChatUserProfileInformixPersistence = new UnregisteredChatUserProfileInformixPersistence(
            dbConnectionFactory, "Informix", new NullValidator());
        assertNotNull(unregisteredChatUserProfileInformixPersistence);
    }*/

    /**
     * <p>
     * Accuracy test for
     * <code>{@link UnregisteredChatUserProfileInformixPersistence#createProfile(ChatUserProfile)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProfileAccuracy() throws Exception {
        ChatUserProfile profile = new ChatUserProfile("5", "type");
        profile.setProperty(UserDefinedAttributeNames.FIRST_NAME, "Jen");
        profile.setProperty(UserDefinedAttributeNames.LAST_NAME, "Jones");
        profile.setProperty(UserDefinedAttributeNames.EMAIL, "jjones@topcoder.com");
        profile.setProperty(UserDefinedAttributeNames.TITLE, "Project Manager");
        profile.setProperty(UserDefinedAttributeNames.COMPANY, "TopCoder");

        unregisteredChatUserProfileInformixPersistence.createProfile(profile);

        Statement stmt = getConnection().createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM client WHERE client_id = 5");
        assertTrue(rs.next());

        assertEquals("incorrect first name.", "Jen", rs.getString("first_name"));
        assertEquals("incorrect last name.", "Jones", rs.getString("last_name"));
        assertEquals("incorrect email.", "jjones@topcoder.com", rs.getString("email"));
        assertEquals("incorrect title.", "Project Manager", rs.getString("title"));
        assertEquals("incorrect company.", "TopCoder", rs.getString("company"));
    }

    /**
     * <p>
     * Accuracy test for <code>{@link UnregisteredChatUserProfileInformixPersistence#getProfile(String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProfileAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();

        stmt.executeUpdate("INSERT INTO client (client_id, first_name, last_name, company, title, email,"
            + "create_date, create_user, modify_date, modify_user) VALUES"
            + "(1, \"Joe\", \"Pesci\", \"Nabisco\", \"coder\", \"jpesci@me.com\", CURRENT, USER, CURRENT, USER)");

        stmt.close();

        ChatUserProfile profile = unregisteredChatUserProfileInformixPersistence.getProfile("1");
        assertEquals("incorrect first name.", "Joe", profile.getPropertyValue(UserDefinedAttributeNames.FIRST_NAME)[0]);
        assertEquals("incorrect last name.", "Pesci", profile.getPropertyValue(UserDefinedAttributeNames.LAST_NAME)[0]);
        assertEquals("incorrect email.", "jpesci@me.com", profile.getPropertyValue(UserDefinedAttributeNames.EMAIL)[0]);
        assertEquals("incorrect title.", "coder", profile.getPropertyValue(UserDefinedAttributeNames.TITLE)[0]);
        assertEquals("incorrect company.", "Nabisco", profile.getPropertyValue(UserDefinedAttributeNames.COMPANY)[0]);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link UnregisteredChatUserProfileInformixPersistence#getProfiles(String[])}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProfilesAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();

        stmt.executeUpdate("INSERT INTO client (client_id, first_name, last_name, company, title, email,"
            + "create_date, create_user, modify_date, modify_user) VALUES"
            + "(1, \"Joe\", \"Pesci\", \"Nabisco\", \"coder\", \"jpesci@me.com\", CURRENT, USER, CURRENT, USER)");

        stmt.close();

        ChatUserProfile[] profiles = unregisteredChatUserProfileInformixPersistence
            .getProfiles(new String[] {"1", "2"});
        assertNotNull(profiles);
        assertEquals("should have 2 elements.", 2, profiles.length);
        assertNotNull(profiles[0]);
        assertNull(profiles[1]);
        assertEquals("incorrect first name.", "Joe",
            profiles[0].getPropertyValue(UserDefinedAttributeNames.FIRST_NAME)[0]);
        assertEquals("incorrect last name.", "Pesci",
            profiles[0].getPropertyValue(UserDefinedAttributeNames.LAST_NAME)[0]);
        assertEquals("incorrect email.", "jpesci@me.com",
            profiles[0].getPropertyValue(UserDefinedAttributeNames.EMAIL)[0]);
        assertEquals("incorrect title.", "coder", profiles[0].getPropertyValue(UserDefinedAttributeNames.TITLE)[0]);
        assertEquals("incorrect company.", "Nabisco",
            profiles[0].getPropertyValue(UserDefinedAttributeNames.COMPANY)[0]);
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
        // prepare data.
        Statement stmt = getConnection().createStatement();

        stmt.executeUpdate("INSERT INTO client (client_id, first_name, last_name, company, title, email,"
            + "create_date, create_user, modify_date, modify_user) VALUES"
            + "(1, \"Joe\", \"Pesci\", \"Nabisco\", \"coder\", \"jpesci@me.com\", CURRENT, USER, CURRENT, USER)");
        stmt.executeUpdate("INSERT INTO client (client_id, first_name, last_name, company, title, email,"
            + "create_date, create_user, modify_date, modify_user) VALUES"
            + "(2, \"Kim\", \"John\", \"MicroSoft\", \"coder\", \"kjohn@me.com\", CURRENT, USER, CURRENT, USER)");
        stmt.executeUpdate("INSERT INTO client (client_id, first_name, last_name, company, title, email,"
            + "create_date, create_user, modify_date, modify_user) VALUES"
            + "(3, \"Alain\", \"Rock\", \"Sun\", \"architect\", \"arock@me.com\", CURRENT, USER, CURRENT, USER)");
        stmt.executeUpdate("INSERT INTO client (client_id, first_name, last_name, company, title, email,"
            + "create_date, create_user, modify_date, modify_user) VALUES"
            + "(4, \"Mary\", \"Magden\", \"Bits\", \"designer\", \"mmagden@me.com\", CURRENT, USER, CURRENT, USER)");

        stmt.close();

        Map criteria = new HashMap();
        List first = new ArrayList();
        first.add("Alain");
        first.add("Kim");
        criteria.put(UserDefinedAttributeNames.FIRST_NAME, first);
        criteria.put(UserDefinedAttributeNames.TITLE, "architect");

        ChatUserProfile[] chatUserProfiles = unregisteredChatUserProfileInformixPersistence.searchProfiles(criteria,
            new String[] {"1", "2", "3", "4"});

        assertNotNull(chatUserProfiles);
        assertEquals("the array size should be 1.", 1, chatUserProfiles.length);
        assertNotNull(chatUserProfiles[0]);
        assertEquals("incorrect first name", "Alain", chatUserProfiles[0]
            .getPropertyValue(UserDefinedAttributeNames.FIRST_NAME)[0]);
        assertEquals("incorrect last name", "Rock", chatUserProfiles[0]
            .getPropertyValue(UserDefinedAttributeNames.LAST_NAME)[0]);
        assertEquals("incorrect company.", "Sun", chatUserProfiles[0]
            .getPropertyValue(UserDefinedAttributeNames.COMPANY)[0]);
        assertEquals("incorrect email", "arock@me.com", chatUserProfiles[0]
            .getPropertyValue(UserDefinedAttributeNames.EMAIL)[0]);

        // case 2
        criteria.put(UserDefinedAttributeNames.TITLE, "designer");
        chatUserProfiles = unregisteredChatUserProfileInformixPersistence.searchProfiles(criteria, new String[] {"1",
            "2", "3", "4"});
        assertNotNull(chatUserProfiles);
        assertTrue("should be empty", chatUserProfiles.length == 0);
    }
}
