/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.stresstests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.Messenger;
import com.cronos.im.messenger.PresenceMessage;
import com.cronos.im.messenger.SessionUnavailableMessage;
import com.cronos.im.messenger.impl.ChatMessageTrackerImpl;
import com.cronos.im.messenger.impl.MessengerImpl;
import com.cronos.im.messenger.impl.UserIDRetrieverImpl;
import com.topcoder.chat.contact.SimpleChatContactManager;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.message.pool.impl.DefaultMessagePool;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import junit.framework.TestCase;

/**
 * <p>
 * Stress test for <code>{@link MessengerImpl}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MessengerImplStressTests extends TestCase {

    /**
     * <p>
     * Represents the messenger instance.
     * </p>
     */
    private Messenger messenger;

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
     * Represents the message pool.
     * </p>
     */
    private MessagePool messagePool;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        StressTestHelper.clearNamespaces();
        StressTestHelper.loadNamespaces();

        messagePool = new DefaultMessagePool();

        connectionFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        connectionName = "informix_connect";
        String user = "USER";

        messenger = new MessengerImpl(messagePool, new ChatMessageTrackerImpl(connectionFactory, connectionName, user),
            new UserIDRetrieverImpl(), new SimpleChatContactManager());

        // Please see TestHelper.insertBasicDataToDB for understanding what data exists in the database
        // Register pool for userId 1,2,3
        messagePool.register(1);
        messagePool.register(2);
        messagePool.register(3);
        // Register pool for userId 1,2,3 in sessionId 1
        messagePool.register(1, 1);
        messagePool.register(2, 1);
        messagePool.register(3, 1);

        // prepare data
        Connection conn = connectionFactory.createConnection(connectionName);
        StressTestHelper.clearTables(conn);
        prepareData(conn);

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
        Connection conn = connectionFactory.createConnection(connectionName);
        StressTestHelper.clearTables(conn);
        StressTestHelper.closeConnection(conn);
    }

    /**
     * <p>
     * prepare data for stress testing.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void prepareData(Connection conn) throws Exception {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO all_user (user_id, registered_flag, username, create_date, "
            + "create_user, modify_date, modify_user) VALUES (1, 'Y', 'tc1', CURRENT, USER, CURRENT, USER)");
        stmt.executeUpdate("INSERT INTO all_user (user_id, registered_flag, username, create_date, "
            + "create_user, modify_date, modify_user) VALUES (2, 'Y', 'tc2', CURRENT, USER, CURRENT, USER)");
        stmt.executeUpdate("INSERT INTO all_user (user_id, registered_flag, username, create_date, "
            + "create_user, modify_date, modify_user) VALUES (3, 'Y', 'tc3', CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO session_mode (session_mode_id, name, description, create_date, "
            + "create_user, modify_date, modify_user) VALUES (1, 'sm1', 'one', CURRENT, USER, CURRENT, USER)");
        stmt.executeUpdate("INSERT INTO session (session_id, session_mode_id, create_user_id, create_date, "
            + "create_user, modify_date, modify_user) VALUES (1, 1, 1, CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, "
            + "exit_date, create_date, create_user, modify_date, modify_user) VALUES (1, 1, 1, "
            + "CURRENT, NULL, CURRENT, USER, CURRENT, USER)");
        stmt.executeUpdate("INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, "
            + "exit_date, create_date, create_user, modify_date, modify_user) VALUES (2, 1, 2, "
            + "CURRENT, NULL, CURRENT, USER, CURRENT, USER)");
        stmt.executeUpdate("INSERT INTO session_user (session_user_id, session_id, user_id, enter_date, "
            + "exit_date, create_date, create_user, modify_date, modify_user) VALUES (3, 1, 3, "
            + "CURRENT, NULL, CURRENT, USER, CURRENT, USER)");
    }

    /**
     * <p>
     * Stress test for <code>{@link MessengerImpl#postMessage(Message, long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testPostMessage1() throws Exception {
        int count = 100;

        Message[] messages = new Message[count];

        for (int i = 0; i < count; i++) {
            messages[i] = new SessionUnavailableMessage();
            messages[i].setSender(new Long(2));
            messages[i].addRecipient(new Long(3));
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            messenger.postMessage(messages[i], 1);
        }

        long end = System.currentTimeMillis();

        // verify
        Message[] postedMessages = messagePool.pull(1);

        assertEquals("the number of message is incorrect.", count, postedMessages.length);

        for (int i = 0; i < count; i++) {
            assertEquals("incorrect sender.", new Long(2), postedMessages[i].getSender());
            List recipients = Arrays.asList(postedMessages[i].getAllRecipients());
            assertEquals("there should be two recipients.", 2, recipients.size());
            assertTrue("recipient not found.", recipients.contains(new Long(3)));
            assertTrue("recipient not found.", recipients.contains(new Long(1)));
        }

        System.out.println("Run MessengerImpl#postMessage(Message, long) " + count + " times takes " + (end - start)
            + "ms.");
    }

    /**
     * <p>
     * Stress test for <code>{@link MessengerImpl#postMessage(Message, long, long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testPostMessage2() throws Exception {
        int count = 100;

        ChatMessage[] messages = new ChatMessage[count];

        for (int i = 0; i < count; i++) {
            messages[i] = new ChatMessage();
            messages[i].setChatText("ChatText" + i);
            messages[i].setSender(new Long(2));
            messages[i].addRecipient(new Long(3));
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            messenger.postMessage(messages[i], 1, 1);
        }

        long end = System.currentTimeMillis();

        // verify
        Message[] postedMessages = messagePool.pull(1, 1);

        assertEquals("the number of message is incorrect.", count, postedMessages.length);

        for (int i = 0; i < count; i++) {
            assertEquals("incorrect sender.", new Long(2), postedMessages[i].getSender());
            List recipients = Arrays.asList(postedMessages[i].getAllRecipients());
            assertEquals("there should be two recipients.", 2, recipients.size());
            assertTrue("recipient not found.", recipients.contains(new Long(3)));
            assertTrue("recipient not found.", recipients.contains(new Long(1)));
        }

        // verify the result.
        Connection conn = connectionFactory.createConnection(connectionName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select count(*) from session_user_message WHERE session_user_id = 1");
        assertTrue(rs.next());
        assertEquals("the number of record is incorrect.", count, rs.getInt(1));

        StressTestHelper.closeStatement(stmt);
        StressTestHelper.closeConnection(conn);

        System.out.println("Run MessengerImpl#postMessage(Message, long, long) " + count + " times takes "
            + (end - start) + "ms.");
    }

    /**
     * <p>
     * Stress test for <code>{@link MessengerImpl#postMessageToOthers(Message, ChatSession)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testPostMessageToOther() throws Exception {
        int count = 100;

        ChatSession chatSession = new ChatSession(1) {
            public int getMode() {
                return 0;
            }

            public long getId() {
                return 1;
            }

            public long[] getActiveUsers() {
                return new long[] {1, 2, 3};
            }
        };
        ChatMessage[] messages = new ChatMessage[count];

        for (int i = 0; i < count; i++) {
            messages[i] = new ChatMessage();
            messages[i].setChatText("ChatText" + i);
            messages[i].setSender(new Long(1));
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            messenger.postMessageToOthers(messages[i], chatSession);
        }

        long end = System.currentTimeMillis();

        // verify
        Message[] postedMessages = messagePool.pull(2, 1);

        assertEquals("the number of message is incorrect.", count, postedMessages.length);

        for (int i = 0; i < count; i++) {
            assertEquals("incorrect sender.", new Long(1), postedMessages[i].getSender());
            List recipients = Arrays.asList(postedMessages[i].getAllRecipients());
            assertEquals("there should be two recipients.", 2, recipients.size());
            assertTrue("recipient not found.", recipients.contains(new Long(3)));
            assertTrue("recipient not found.", recipients.contains(new Long(2)));
        }

        postedMessages = messagePool.pull(3, 1);

        assertEquals("the number of message is incorrect.", count, postedMessages.length);

        for (int i = 0; i < count; i++) {
            assertEquals("incorrect sender.", new Long(1), postedMessages[i].getSender());
            List recipients = Arrays.asList(postedMessages[i].getAllRecipients());
            assertEquals("there should be two recipients.", 2, recipients.size());
            assertTrue("recipient not found.", recipients.contains(new Long(3)));
            assertTrue("recipient not found.", recipients.contains(new Long(2)));
        }

        System.out.println("Run MessengerImpl#postMessageToOther(Message, ChatSession) " + count + " times takes "
            + (end - start) + "ms.");
    }

    /**
     * <p>
     * Stress test for <code>{@link MessengerImpl#postMessageToAll(Message, ChatSession)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testPostMessageToAll() throws Exception {
        int count = 100;

        ChatSession chatSession = new ChatSession(1) {
            public int getMode() {
                return 0;
            }

            public long getId() {
                return 1;
            }

            public long[] getActiveUsers() {
                return new long[] {1, 2, 3};
            }
        };
        PresenceMessage[] messages = new PresenceMessage[count];

        for (int i = 0; i < count; i++) {
            messages[i] = new PresenceMessage();
            messages[i].setSender(new Long(1));
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            messenger.postMessageToAll(messages[i], chatSession);
        }

        long end = System.currentTimeMillis();

        // verify
        Message[] postedMessages = messagePool.pull(2, 1);

        assertEquals("the number of message is incorrect.", count, postedMessages.length);

        for (int i = 0; i < count; i++) {
            assertEquals("incorrect sender.", new Long(1), postedMessages[i].getSender());
            List recipients = Arrays.asList(postedMessages[i].getAllRecipients());
            assertEquals("there should be two recipients.", 2, recipients.size());
            assertTrue("recipient not found.", recipients.contains(new Long(3)));
            assertTrue("recipient not found.", recipients.contains(new Long(2)));
        }

        postedMessages = messagePool.pull(3, 1);

        assertEquals("the number of message is incorrect.", count, postedMessages.length);

        for (int i = 0; i < count; i++) {
            assertEquals("incorrect sender.", new Long(1), postedMessages[i].getSender());
            List recipients = Arrays.asList(postedMessages[i].getAllRecipients());
            assertEquals("there should be two recipients.", 2, recipients.size());
            assertTrue("recipient not found.", recipients.contains(new Long(3)));
            assertTrue("recipient not found.", recipients.contains(new Long(2)));
        }

        System.out.println("Run MessengerImpl#postMessageToAll(Message, ChatSession) " + count + " times takes "
            + (end - start) + "ms.");
    }
}
