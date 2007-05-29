/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import java.sql.ResultSet;
import java.sql.Statement;

import com.cronos.im.persistence.InformixProfileKeyManager;
import com.topcoder.chat.user.profile.ProfileKey;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.datavalidator.NullValidator;
import com.topcoder.util.datavalidator.ObjectValidator;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * Accuracy test for <code>{@link InformixProfileKeyManager}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class InformixProfileKeyManagerAccuracyTests extends BaseTestCase {

    /**
     * <p>
     * Represents the InformixProfileKeyManager instance used in tests.
     * </p>
     */
    private InformixProfileKeyManager informixProfileKeyManager;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        informixProfileKeyManager = new InformixProfileKeyManager();
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixProfileKeyManager#InformixProfileKeyManager()}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixProfileKeyManager1Accuracy() throws Exception {
        informixProfileKeyManager = new InformixProfileKeyManager();

        assertNotNull(informixProfileKeyManager);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixProfileKeyManager#InformixProfileKeyManager(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixProfileKeyManager2Accuracy() throws Exception {
        informixProfileKeyManager = new InformixProfileKeyManager(InformixProfileKeyManager.DEFAULT_NAMESPACE);

        assertNotNull(informixProfileKeyManager);
    }

    /**
     * <p>
     * Accuracy test for
     * <code>{@link InformixProfileKeyManager#InformixProfileKeyManager(DBConnectionFactory, String, IDGenerator, ObjectValidator)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixProfileKeyManager3Accuracy() throws Exception {
        DBConnectionFactory dbConnectionFactory = new DBConnectionFactoryImpl();

        IDGenerator idGenerator = IDGeneratorFactory.getIDGenerator("accuracy");

        // case 1
        informixProfileKeyManager = new InformixProfileKeyManager(dbConnectionFactory, "Informix", idGenerator, null);
        assertNotNull(informixProfileKeyManager);

        // case 2
        informixProfileKeyManager = new InformixProfileKeyManager(dbConnectionFactory, "Informix", idGenerator,
            new NullValidator());
        assertNotNull(informixProfileKeyManager);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixProfileKeyManager#createProfileKey(ProfileKey)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProfileKeyAccuracy() throws Exception {
        ProfileKey profileKey = new ProfileKey("tc1", InformixProfileKeyManager.TYPE_REGISTERED);
        informixProfileKeyManager.createProfileKey(profileKey);
        profileKey = new ProfileKey("tc2", InformixProfileKeyManager.TYPE_UNREGISTERED);
        profileKey = informixProfileKeyManager.createProfileKey(profileKey);

        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt
            .executeQuery("select count(user_id) from all_user where username=\"tc1\" AND registered_flag='Y'");
        assertTrue(rs.next());
        assertTrue(rs.getLong(1) == 1);

        rs.close();

        rs = stmt.executeQuery("select count(user_id) from all_user where username=\"" + profileKey.getUsername()
                               + "\" AND registered_flag='N'");
        assertTrue(rs.next());
        assertTrue(rs.getLong(1) == 1);
        rs.close();
        stmt.close();
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixProfileKeyManager#deleteProfileKey(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProfileKeyAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();
        int updated = stmt.executeUpdate("INSERT INTO all_user(user_id, registered_flag, username, create_date,"
            + "create_user, modify_date, modify_user) VALUES (1, 'Y', \"tc\", CURRENT, USER, CURRENT, USER)");

        assertTrue(updated == 1);

        informixProfileKeyManager.deleteProfileKey(1);

        ResultSet rs = stmt.executeQuery("select user_id from all_user where user_id = 1");
        assertFalse(rs.next());

        rs.close();
        stmt.close();
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixProfileKeyManager#getProfileKey(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProfileKeyAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();
        int updated = stmt.executeUpdate("INSERT INTO all_user(user_id, registered_flag, username, create_date,"
            + "create_user, modify_date, modify_user) VALUES (1, 'Y', \"tc\", CURRENT, USER, CURRENT, USER)");

        stmt.close();
        assertTrue(updated == 1);

        ProfileKey profileKey = informixProfileKeyManager.getProfileKey(1);

        assertNotNull(profileKey);
        assertEquals("incorrect use name", "tc", profileKey.getUsername());
        assertEquals("incorrect type", InformixProfileKeyManager.TYPE_REGISTERED, profileKey.getType());
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixProfileKeyManager#getProfileKey(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProfileKeyByUserNameAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate("INSERT INTO all_user(user_id, registered_flag, username, create_date,"
            + "create_user, modify_date, modify_user) VALUES (1, 'Y', \"tc1\", CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO all_user(user_id, registered_flag, username, create_date,"
            + "create_user, modify_date, modify_user) VALUES (2, 'N', \"tc2\", CURRENT, USER, CURRENT, USER)");

        stmt.close();

        ProfileKey profileKey = informixProfileKeyManager.getProfileKey("tc1",
            InformixProfileKeyManager.TYPE_REGISTERED);
        assertNotNull(profileKey);
        assertEquals("incorrect id.", 1, profileKey.getId());

        profileKey = informixProfileKeyManager.getProfileKey("tc2", InformixProfileKeyManager.TYPE_UNREGISTERED);
        assertNotNull(profileKey);
        assertEquals("incorrect id.", 2, profileKey.getId());
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixProfileKeyManager#getProfileKeys(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProfileKeysAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();

        for (int i = 1; i < 10; i++) {
            stmt.executeUpdate("INSERT INTO all_user(user_id, registered_flag, username, create_date,"
                + "create_user, modify_date, modify_user) VALUES (" + i + ", 'Y', \"tc" + i
                + "\", CURRENT, USER, CURRENT, USER)");
        }

        stmt.close();

        ProfileKey[] profileKeys = informixProfileKeyManager.getProfileKeys(new long[] {11, 12});
        assertNull("should return null", profileKeys);

        profileKeys = informixProfileKeyManager.getProfileKeys(new long[] {1, 3, 5, 7, 9});

        assertNotNull(profileKeys);
        assertEquals("incorrect size.", 5, profileKeys.length);
        for (int i = 0; i < profileKeys.length; i++) {
            assertNotNull(profileKeys[i]);
        }

        profileKeys = informixProfileKeyManager.getProfileKeys(new long[] {1, 3, 11, 7, 9});

        assertNotNull(profileKeys);
        assertEquals("incorrect size.", 5, profileKeys.length);
        assertNull("should be null.", profileKeys[2]);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixProfileKeyManager#getProfileKeys(String[], String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProfileKeysByUserNamesAndTypeAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();

        for (int i = 1; i < 6; i++) {
            stmt.executeUpdate("INSERT INTO all_user(user_id, registered_flag, username, create_date,"
                + "create_user, modify_date, modify_user) VALUES (" + i + ", '" + (i % 2 == 0 ? 'Y' : 'N') + "', \"tc"
                + i + "\", CURRENT, USER, CURRENT, USER)");
        }

        stmt.close();

        ProfileKey[] profileKeies = informixProfileKeyManager.getProfileKeys(new String[] {"tc1", "tc2", "tc3", "tc4",
            "tc5"}, InformixProfileKeyManager.TYPE_REGISTERED);
        assertNotNull(profileKeies);
        assertEquals("incorrect size.", 5, profileKeies.length);
        for (int i = 0; i < profileKeies.length; i++) {
            if (i % 2 == 0) {
                assertNull(profileKeies[i]);
            } else {
                assertNotNull(profileKeies[i]);
            }
        }
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixProfileKeyManager#getProfileKeys(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProfileKeysByTypeAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();

        for (int i = 1; i < 6; i++) {
            stmt.executeUpdate("INSERT INTO all_user(user_id, registered_flag, username, create_date,"
                + "create_user, modify_date, modify_user) VALUES (" + i + ", '" + (i % 2 == 0 ? 'Y' : 'N') + "', \"tc"
                + i + "\", CURRENT, USER, CURRENT, USER)");
        }

        stmt.close();

        ProfileKey[] profileKeies = informixProfileKeyManager.getProfileKeys(InformixProfileKeyManager.TYPE_REGISTERED);
        assertNotNull(profileKeies);
        assertEquals("incorrect size.", 2, profileKeies.length);
        for (int i = 0; i < profileKeies.length; i++) {
            assertTrue(profileKeies[i].getId() % 2 == 0);
        }
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixProfileKeyManager#getProfileKeys(String[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProfileKeysByTypesAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();

        for (int i = 1; i < 6; i++) {
            stmt.executeUpdate("INSERT INTO all_user(user_id, registered_flag, username, create_date,"
                + "create_user, modify_date, modify_user) VALUES (" + i + ", '" + (i % 2 == 0 ? 'Y' : 'N') + "', \"tc"
                + i + "\", CURRENT, USER, CURRENT, USER)");
        }

        stmt.close();

        ProfileKey[][] profileKeies = informixProfileKeyManager.getProfileKeys(new String[] {
            InformixProfileKeyManager.TYPE_REGISTERED, InformixProfileKeyManager.TYPE_UNREGISTERED});
        assertNotNull(profileKeies);
        assertEquals(2, profileKeies.length);
        assertEquals("incorrect size.", 2, profileKeies[0].length);
        for (int i = 0; i < profileKeies.length; i++) {
            assertTrue(profileKeies[0][i].getId() % 2 == 0);
        }

        assertEquals("incorrect size.", 3, profileKeies[1].length);
        for (int i = 0; i < profileKeies.length; i++) {
            assertTrue(profileKeies[1][i].getId() % 2 == 1);
        }
    }
}
