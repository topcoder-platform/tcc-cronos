/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.stresstests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;

import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.MessageTracker;
import com.cronos.im.messenger.impl.ChatMessageTrackerImpl;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * Stress test for <code>{@link ChatMessageTrackerImpl}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class ChatMessageTrackerImplStressTests extends TestCase {

    /**
     * <p>
     * Represents the database connection factory.
     * </p>
     */
    private DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the database connection name.
     * </p>
     */
    private String connectionName;

    /**
     * <p>
     * Represents the <code>{@link ChatMessageTrackerImpl}</code> instance.
     * </p>
     */
    private MessageTracker messageTracker;

    /**
     * <p>
     * Represents the user.
     * </p>
     */
    private String user;

    /**
     * <p>
     * Represents the chat message for tracking.
     * </p>
     */
    private ChatMessage chatMessage;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        // load namespace.
        StressTestHelper.clearNamespaces();
        StressTestHelper.loadNamespaces();

        connectionFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        connectionName = "informix_connect";
        user = "TopCoder";

        messageTracker = new ChatMessageTrackerImpl(connectionFactory, connectionName, user);

        chatMessage = new ChatMessage();
        chatMessage.setChatText("Stress");

        Connection conn = connectionFactory.createConnection(connectionName);
        StressTestHelper.clearTables(conn);
        StressTestHelper.closeConnection(conn);
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        StressTestHelper.clearNamespaces();

        Connection conn = connectionFactory.createConnection(connectionName);
        StressTestHelper.clearTables(conn);
        StressTestHelper.closeConnection(conn);
    }

    /**
     * <p>
     * Stress test for
     * <code>{@link ChatMessageTrackerImpl#track(com.topcoder.chat.message.pool.Message, long[], long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testTrackerOneUser100Times() throws Exception {
        Connection conn = connectionFactory.createConnection(connectionName);

        // prepare data
        Statement stmt = conn.createStatement();
        stmt
            .executeUpdate("INSERT INTO all_user (user_id, registered_flag, username, create_date, create_user, modify_date, modify_user) "
                + "VALUES (0, 'Y', 'tc1', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO session_mode (session_mode_id, name, description, create_date, create_user, modify_date, modify_user)"
                + " VALUES (1, 'sm1', 'one', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO session (session_id, session_mode_id, create_user_id, create_date, create_user, modify_date, modify_user)"
                + " VALUES (0, 1, 0, CURRENT, USER, CURRENT, USER)");

        stmt
            .executeUpdate("INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, exit_date, create_date, create_user, modify_date, modify_user)"
                + " VALUES (1, 0, 0, CURRENT, NULL, CURRENT, USER, CURRENT, USER)");

        int count = 100;

        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            messageTracker.track(chatMessage, new long[] {0}, 0);
        }

        long end = System.currentTimeMillis();

        // verify the result.
        ResultSet rs = stmt.executeQuery("select count(*) from session_user_message WHERE session_user_id = 1");

        assertTrue(rs.next());
        assertEquals("the number of record is incorrect.", count, rs.getInt(1));

        System.out.println("Run ChatMessageTrackerImpl#track for 1 user " + count + " times takes " + (end - start)
            + "ms.");
        StressTestHelper.closeStatement(stmt);
        StressTestHelper.closeConnection(conn);
    }

    /**
     * <p>
     * Stress test for
     * <code>{@link ChatMessageTrackerImpl#track(com.topcoder.chat.message.pool.Message, long[], long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testTracker10User10Times() throws Exception {
        Connection conn = connectionFactory.createConnection(connectionName);

        // prepare data
        Statement stmt = conn.createStatement();

        for (int i = 1; i <= 10; i++) {
            stmt
                .executeUpdate("INSERT INTO all_user (user_id, registered_flag, username, create_date, create_user, modify_date, modify_user) "
                    + "VALUES (" + i + ", 'Y', 'tc " + i + "', CURRENT, USER, CURRENT, USER)");
        }

        stmt
            .executeUpdate("INSERT INTO session_mode (session_mode_id, name, description, create_date, create_user, modify_date, modify_user)"
                + " VALUES (1, 'sm1', 'one', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO session (session_id, session_mode_id, create_user_id, create_date, create_user, modify_date, modify_user)"
                + " VALUES (0, 1, 1, CURRENT, USER, CURRENT, USER)");

        for (int i = 1; i <= 10; i++) {
            stmt
                .executeUpdate("INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, exit_date, create_date, create_user, modify_date, modify_user)"
                    + " VALUES (" + i + ", 0, " + i + ", CURRENT, NULL, CURRENT, USER, CURRENT, USER)");
        }
        int count = 10;

        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            messageTracker.track(chatMessage, new long[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 0);
        }

        long end = System.currentTimeMillis();

        // verify the result.
        for (int i = 1; i <= 10; i++) {
            ResultSet rs = stmt.executeQuery("select count(*) from session_user_message WHERE session_user_id = " + i);

            assertTrue(rs.next());
            assertEquals("the number of record is incorrect.", count, rs.getInt(1));
        }

        System.out.println("Run ChatMessageTrackerImpl#track for 10 user " + count + " times takes " + (end - start)
            + "ms.");
        StressTestHelper.closeStatement(stmt);
        StressTestHelper.closeConnection(conn);
    }
}
