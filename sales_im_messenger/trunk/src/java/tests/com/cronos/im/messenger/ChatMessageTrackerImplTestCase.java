/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import com.cronos.im.messenger.impl.ChatMessageTrackerImpl;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Unit tests for <c>ChatMessageTrackerImpl</c> class.
 *
 * @author marius_neo
 * @version 1.0
 */
public class ChatMessageTrackerImplTestCase extends TestCase {

    /**
     * Instance of <c>ChatMessageTrackerImpl</c> used in tests.
     */
    private ChatMessageTrackerImpl tracker;

    /**
     * Represents the db connection factory instance used as a
     * parameter for the constructor of class <c>ChatMessageTrackerImpl</c>.
     */
    private DBConnectionFactory dbFactory;

    /**
     * Represents the connection name parameter passed to the
     * constructor of the class <c>ChatMessageTrackerImpl</c>.
     */
    private String connName;

    /**
     * Represents the database operator passed as a parameter to the
     * constructor of the class <c>ChatMessageTrackerImpl</c>.
     */
    private String user;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception Propagated to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(TestHelper.DB_CONNECTION_FACTORY_CONFIG_FILE);
        TestHelper.setUpDataBase();

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_CONNECTION_FACTORY_NAMESPACE);
        connName = "informix_connect";
        user = "USER";

        tracker = new ChatMessageTrackerImpl(dbFactory, connName, user);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        dbFactory = null;
        connName = null;
        user = null;
    }

    /**
     * Tests the accuracy for the constructor
     * <c>ChatMessageTrackerImpl(DBConnectionFactory, String, String)</c>.
     */
    public void testConstructor() {
        assertNotNull("tracker was not created", tracker);
    }

    /**
     * Tests the failure for the constructor
     * <c>ChatMessageTrackerImpl(DBConnectionFactory, String, String)</c>
     * when <c>dbFactory</c> argument of the constructor is null.
     */
    public void testConstructorNullDbFactory() {
        try {
            new ChatMessageTrackerImpl(null, connName, user);

            fail("Should throw IllegalArgumentException because of null dbFactory");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the constructor
     * <c>ChatMessageTrackerImpl(DBConnectionFactory, String, String)</c>
     * when <c>connName</c> argument of the constructor is null or empty.
     */
    public void testConstructorInvalidConnName() {
        try {
            new ChatMessageTrackerImpl(dbFactory, "  ", user);

            fail("Should throw IllegalArgumentException because of empty string connName");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the constructor
     * <c>ChatMessageTrackerImpl(DBConnectionFactory, String, String)</c>
     * when <c>user</c> argument of the constructor is null or empty.
     */
    public void testConstructorInvalidUser() {
        try {
            new ChatMessageTrackerImpl(dbFactory, connName, null);

            fail("Should throw IllegalArgumentException because of null user");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        try {
            new ChatMessageTrackerImpl(dbFactory, connName, "   ");

            fail("Should throw IllegalArgumentException because of empty string user");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the accuracy for the method <c>track(Message, long[], long)</c>.
     *
     * @throws Exception Should not throw.
     */
    public void testTrack() throws Exception {
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        // See TestHelper.insertBasicDataToDB(Connection) for details on the data from DB
        long[] userIds = new long[]{0, 1, 2};
        long sessionId = 1;
        long[] sessionUserIds = new long[]{3, 4, 5};
        tracker.track(message, userIds, sessionId);

        Connection conn = null;
        try {
            conn = dbFactory.createConnection(connName);
            String sql = "SELECT session_user_id, message_text FROM session_user_message"
                + " ORDER BY session_user_id ASC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int recordIndex = 0;
            while (rs.next()) {
                long sessionUserId = rs.getLong(1);
                String text = rs.getString(2);
                assertEquals("session user ids should be equal", sessionUserId, sessionUserIds[recordIndex]);
                assertEquals("chat texts should be equal", chatText, text);
                recordIndex++;
            }
            assertEquals("There should be " + userIds.length + " records", userIds.length, recordIndex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                } // Nothing we can do
            }
        }
    }

    /**
     * Tests for failure the method <c>track(Message, long[], long)</c>
     * when <c>msg</c> argument is null.<c>IllegalArgumentException</c>
     * is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testTrackNullMessage() throws Exception {
        long[] userIds = new long[]{0, 1, 2};
        long sessionId = 1;

        try {
            tracker.track(null, userIds, sessionId);

            fail("Should have thrown IllegalArgumentException because of the null message");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    /**
     * Tests for failure the method <c>track(Message, long[], long)</c>
     * when <c>userIds</c> argument is null or empty. <c>IllegalArgumentException</c>
     * is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testTrackNullOrEmptyUserIds() throws Exception {
        long sessionId = 1;
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);

        try {
            tracker.track(message, null, sessionId);

            fail("Should have thrown IllegalArgumentException because of the null userIds");
        } catch (IllegalArgumentException e) {
            // Success
        }

        try {
            tracker.track(message, new long[]{}, sessionId);

            fail("Should have thrown IllegalArgumentException because of the empty userIds array");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    /**
     * Tests for failure the method <c>track(Message, long[], long)</c>
     * when <c>userIds</c> argument contains duplicate elements. <c>IllegalArgumentException</c>
     * is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testTrackNullUserIds() throws Exception {
        long[] userIds = new long[]{0, 1, 0};
        long sessionId = 1;
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);

        try {
            tracker.track(message, userIds, sessionId);

            fail("Should have thrown IllegalArgumentException because of the duplicate elements in userIds array");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    /**
     * Tests for failure the method <c>track(Message, long[], long)</c>
     * when <c>userIds</c> argument contains elements which are inconsistent with the
     * session id. So when there is no sessionUserId found, a <c>MessageTrackerException</c>
     * should be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testTrackMessageTrackerException() throws Exception {
        long[] userIds = new long[]{5};
        long sessionId = 1;
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);

        try {
            tracker.track(message, userIds, sessionId);

            fail("Should have thrown MessageTrackerException because of invalid userId-sessionId association");
        } catch (MessageTrackerException e) {
            // Success
        }
    }
}
