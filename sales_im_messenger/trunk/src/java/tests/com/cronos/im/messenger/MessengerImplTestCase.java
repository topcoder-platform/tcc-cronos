/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import com.cronos.im.messenger.impl.ChatMessageTrackerImpl;
import com.cronos.im.messenger.impl.MessengerImpl;
import com.cronos.im.messenger.impl.UserIDRetrieverImpl;
import com.topcoder.chat.contact.ChatContactManager;
import com.topcoder.chat.contact.SimpleChatContactManager;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.message.pool.impl.DefaultMessagePool;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.chat.session.GroupSession;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Unit test cases for class <c>MessengerImpl</c>.
 *
 * @author marius_neo
 * @version 1.0
 */
public class MessengerImplTestCase extends TestCase {

    /**
     * Instance of <c>MessagePool</c> used in tests as
     * a parameter of the constructor <c>MessengerImpl(MessagePool, MessageTracker
     * , UserIDRetriever, ChatContactManager)</c>.
     * It is initialized in <c>setUp()</c> method.
     */
    private MessagePool messagePool;

    /**
     * Instance of <c>ChatMessageTrackerImpl</c> used in tests as
     * a parameter of the constructor <c>MessengerImpl(MessagePool, MessageTracker
     * , UserIDRetriever, ChatContactManager)</c>.
     * It is initialized in <c>setUp()</c> method.
     */
    private ChatMessageTrackerImpl tracker;

    /**
     * Concrete instance of <c>UserIDRetriever</c> used in tests as
     * a parameter of the constructor <c>MessengerImpl(MessagePool, MessageTracker
     * , UserIDRetriever, ChatContactManager)</c>.
     * It is initialized in <c>setUp()</c> method.
     */
    private UserIDRetriever retriever;

    /**
     * Instance of <c>ChatContactManager</c> used in tests as
     * a parameter of the constructor <c>MessengerImpl(MessagePool, MessageTracker
     * , UserIDRetriever, ChatContactManager)</c>.
     * It is initialized in <c>setUp()</c> method.
     */
    private ChatContactManager contactManager;

    /**
     * Instance of <c>MesengerImpl</c> class used for testing purposes.
     * It is initialized in <c>setUp()</c> method.
     */
    private MessengerImpl messenger;

    /**
     * Represents the userId for whom is registered a message pool.
     * This will be made in <c>setUp()</c> method.
     */
    private final long mpUserId = 1;

    /**
     * Represents the session id which in combination with mpUserId
     * has a registered message pool. This will be made in <c>setUp()</c> method.
     */
    private final long mpSessionId = 1;

    /**
     * This field is used as a variable for the tracker and also for verifying in tests
     * if they were accurate. It is initialized in <c>setUp()</c> method.
     */
    private DBConnectionFactory dbFactory;

    /**
     * Represents the connection name parameter passed to the
     * constructor of the class <c>ChatMessageTrackerImpl</c>.
     */
    private String connName;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception Propagated to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(TestHelper.DB_CONNECTION_FACTORY_CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.CHAT_SESSION_MANAGER_CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.CHAT_CONTACT_MANAGER_CONFIG_FILE);
        TestHelper.setUpDataBase();

        messagePool = new DefaultMessagePool();

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_CONNECTION_FACTORY_NAMESPACE);
        connName = "informix_connect";
        String user = "USER";
        tracker = new ChatMessageTrackerImpl(dbFactory, connName, user);

        retriever = new UserIDRetrieverImpl();

        contactManager = new SimpleChatContactManager();
        messenger = new MessengerImpl(messagePool, tracker, retriever, contactManager);

        // Please see TestHelper.insertBasicDataToDB for understanding what data exists in the database
        // Register pool for userId 1
        messagePool.register(mpUserId);
        // Register pool for userId 1 in sessionId 1
        messagePool.register(mpUserId, mpSessionId);
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

        messagePool = null;
        tracker = null;
        retriever = null;
        contactManager = null;
    }

    /**
     * Tests the accuracy for the constructor <c>MessengerImpl(MessagePool, MessageTracker
     * , UserIDRetriever, ChatContactManager)</c>.
     */
    public void testCtorAccuracy() {
        assertNotNull("messenger was not created", messenger);
        assertSame("The message pools should be the same", messagePool, messenger.getMessagePool());
    }

    /**
     * Tests for failure the constructor <c>MessengerImpl(MessagePool, MessageTracker
     * , UserIDRetriever, ChatContactManager)</c> when <c>messagePool</c> argument
     * is null.<c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testCtorNullMessagePool() {
        try {
            new MessengerImpl(null, tracker, retriever, contactManager);

            fail("Should have thrown IllegalArgumentException because of the null messagePool argument");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    /**
     * Tests for failure the constructor <c>MessengerImpl(MessagePool, MessageTracker
     * , UserIDRetriever, ChatContactManager)</c> when <c>tracker</c> argument
     * is null.<c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testCtorNullTracker() {
        try {
            new MessengerImpl(messagePool, null, retriever, contactManager);

            fail("Should have thrown IllegalArgumentException because of the null tracker argument");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    /**
     * Tests for failure the constructor <c>MessengerImpl(MessagePool, MessageTracker
     * , UserIDRetriever, ChatContactManager)</c> when <c>retriever</c> argument
     * is null.<c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testCtorNullRetriever() {
        try {
            new MessengerImpl(messagePool, tracker, null, contactManager);

            fail("Should have thrown IllegalArgumentException because of the null retriever argument");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    /**
     * Tests for failure the constructor <c>MessengerImpl(MessagePool, MessageTracker
     * , UserIDRetriever, ChatContactManager)</c> when <c>contactManager</c> argument
     * is null.<c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testCtorNullContactManager() {
        try {
            new MessengerImpl(messagePool, tracker, retriever, null);

            fail("Should have thrown IllegalArgumentException because of the null contactManager argument");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    /**
     * Tests the accuracy for the method <c>postMessage(Message, long)</c>.In this method the <c>userId</c>
     * specified to the method is not contained between the recipients of the message.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongUserIdNotContainedInRecipients() throws Exception {
        // Initialize the message to be used in calling the method tested
        Message message = new SessionUnavailableMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        Long[] recipients = new Long[]{new Long(3), new Long(4)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        messenger.postMessage(message, mpUserId);

        Message[] messages = messagePool.pull(mpUserId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        Object[] newRecipients = message.getAllRecipients();
        boolean found = false;
        for (int i = 0; i < newRecipients.length; i++) {
            if (newRecipients[i].equals(new Long(mpUserId))) {
                found = true;
                break;
            }
        }
        assertTrue("The mpUserId should become a recipient for the message", found);
    }

    /**
     * Tests the accuracy for the method <c>postMessage(Message, long)</c>.In this method the <c>userId</c>
     * specified to the method is contained between the recipients of the message.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongUserIdContainedInRecipients() throws Exception {
        // Initialize the message to be used in calling the method tested
        Message message = new SessionUnavailableMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        Long[] recipients = new Long[]{new Long(mpUserId), new Long(3)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        messenger.postMessage(message, mpUserId);

        Message[] messages = messagePool.pull(mpUserId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        Object[] newRecipients = message.getAllRecipients();
        boolean found = false;
        for (int i = 0; i < newRecipients.length; i++) {
            if (newRecipients[i].equals(new Long(mpUserId))) {
                found = true;
                break;
            }
        }
        assertTrue("The mpUserId should become a recipient for the message", found);
    }


    /**
     * Tests the accuracy for the method <c>postMessage(Message, long)</c>.In this method
     * we'll try to post ChatMessage, PresenceMessage instances to verify that nothing should
     * happen.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongNotSupportedMessageType() throws Exception {
        // Initialize the message to be used in calling the method tested
        Message message = new PresenceMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        Long[] recipients = new Long[]{new Long(3), new Long(4)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        messenger.postMessage(message, mpUserId);

        message = new ChatMessage();
        message.setSender(new Long(senderUserId));
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        messenger.postMessage(message, mpUserId);

        Message[] messages = messagePool.pull(mpUserId);
        assertTrue("There should be no messages in the pool", messages.length == 0);
    }


    /**
     * Tests the accuracy for the method <c>postMessage(Message, long)</c>.In this method the the sender
     * of the message to post is blocked by the user which has the message pool.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongSenderBlocked() throws Exception {
        // Initialize the message to be used in calling the method tested
        Message message = new SessionUnavailableMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        Long[] recipients = new Long[]{new Long(3), new Long(4)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        // Block the sender
        contactManager.addBlockedUser(mpUserId, senderUserId);

        messenger.postMessage(message, mpUserId);

        Message[] messages = messagePool.pull(mpUserId);
        assertTrue("There should be no messages in the pool", messages.length == 0);
    }

    /**
     * Tests the failure for the method <c>postMessage(Message, long)</c>.In this method
     * the message passed to the method is null.
     * <c>IllegalArgumentException</c> is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongNullMessage() throws Exception {
        try {
            messenger.postMessage(null, mpUserId);
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the method <c>postMessage(Message, long)</c>.In this method
     * the message will be posted to an inexistent user message pool.
     * <c>MessengerException</c> is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongUserPoolNotRegistered() throws Exception {
        Message message = new SessionUnavailableMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        Long[] recipients = new Long[]{new Long(3), new Long(4)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        // Send the message to an inexistent user pool
        try {
            messenger.postMessage(message, 1000);

            fail("Should have thrown MessengerException because specified user message pool does not exist");
        } catch (MessengerException e) {
            // Success.
        }
    }


    /**
     * Tests the accuracy for the method <c>postMessage(Message, long, long)</c>.In this method
     * the <c>userId</c> specified to the method is not contained between the recipients of the message.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongLongUserIdNotContainedInRecipients() throws Exception {
        // Initialize the message to be used in calling the method tested
        Message message = new PresenceMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        Long[] recipients = new Long[]{new Long(3), new Long(4)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        messenger.postMessage(message, mpUserId, mpSessionId);

        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        Object[] newRecipients = message.getAllRecipients();
        boolean found = false;
        for (int i = 0; i < newRecipients.length; i++) {
            if (newRecipients[i].equals(new Long(mpUserId))) {
                found = true;
                break;
            }
        }
        assertTrue("The mpUserId should become a recipient for the message", found);
    }

    /**
     * Tests the accuracy for the method <c>postMessage(Message, long,long)</c>.In this method the <c>userId</c>
     * specified to the method is contained between the recipients of the message.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongLongUserIdContainedInRecipients() throws Exception {
        // Initialize the message to be used in calling the method tested
        Message message = new PresenceMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        Long[] recipients = new Long[]{new Long(mpUserId), new Long(3)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        messenger.postMessage(message, mpUserId, mpSessionId);

        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        Object[] newRecipients = message.getAllRecipients();
        boolean found = false;
        for (int i = 0; i < newRecipients.length; i++) {
            if (newRecipients[i].equals(new Long(mpUserId))) {
                found = true;
                break;
            }
        }
        assertTrue("The mpUserId should become a recipient for the message", found);
    }


    /**
     * Tests the accuracy for the method <c>postMessage(Message, long, long)</c>.In this method
     * we'll try to post SessionUnavailableMessage, AskForChatMessage, EnterChatMessage instances
     * to verify that nothing happens.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongLongNotSupportedMessageType() throws Exception {
        // Initialize the message to be used in calling the method tested
        Message message = new SessionUnavailableMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        Long[] recipients = new Long[]{new Long(3), new Long(4)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        messenger.postMessage(message, mpUserId);

        message = new AskForChatMessage();
        message.setSender(new Long(senderUserId));
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        messenger.postMessage(message, mpUserId);

        message = new EnterChatMessage();
        message.setSender(new Long(senderUserId));
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        messenger.postMessage(message, mpUserId, mpSessionId);

        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be no messages in the pool", messages.length == 0);
    }


    /**
     * Tests the accuracy for the method <c>postMessage(Message, long,long)</c>.In this method the the sender
     * of the message to post is blocked by the user which has the message pool.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongLongSenderBlocked() throws Exception {
        // Initialize the message to be used in calling the method tested
        Message message = new SessionUnavailableMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        Long[] recipients = new Long[]{new Long(3), new Long(4)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        // Block the sender
        contactManager.addBlockedUser(mpUserId, senderUserId);

        messenger.postMessage(message, mpUserId, mpSessionId);

        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be no messages in the pool", messages.length == 0);
    }

    /**
     * Tests the failure for the method <c>postMessage(Message, long, long)</c>.In this method
     * the message passed to the method is null.
     * <c>IllegalArgumentException</c> is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongLongNullMessage() throws Exception {
        try {
            messenger.postMessage(null, mpUserId, mpSessionId);
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the method <c>postMessage(Message, long)</c>.In this method
     * the message will be posted to an inexistent user message pool.
     * <c>MessengerException</c> is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageMessageLongLongUserSessionPoolNotRegistered() throws Exception {
        Message message = new PresenceMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        Long[] recipients = new Long[]{new Long(3), new Long(4)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        // Send the message to an inexistent user pool
        try {
            messenger.postMessage(message, 1000, 2000);

            fail("Should have thrown MessengerException because specified user session message pool does not exist");
        } catch (MessengerException e) {
            // Success.
        }
    }

    /**
     * Tests the accuracy for the method <c>postMessageToOthers(Message, ChatSession)</c>.In this
     * method the senderUserId of the <c>message</c> specified to the method is not contained
     * between the active users of the <c>chatSession</c>.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToOthersSenderUserIdNotContainedInActiveUsers() throws Exception {
        // Initialize the message to be used in calling the method tested
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        long mpUserId3 = 3;
        Long[] recipients = new Long[]{new Long(mpUserId3)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        long[] activeUsers = new long[]{mpUserId, mpUserId3};
        // See TestHelper#insertBasicDataToDB(Connection) for the details on
        // which data is contained in DB. {4,1,1} and {6,3,1} pairs are found
        // in session_user table.
        long[] sessionUserIds = new long[]{4, 6};

        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");

        // All the active users to whom will be posted the message need to have a user session
        // message pool. For userId 1 it is set in the setUp() method.
        messagePool.register(mpUserId3);
        messagePool.register(mpUserId3, mpSessionId);

        messenger.postMessageToOthers(message, chatSession);

        // See if the message was posted to user session message pools {mpUserId, mpSessionId}
        // and {mpUserId3, mpSessionId3}
        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        messages = messagePool.pull(mpUserId3, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        Object[] newRecipients = message.getAllRecipients();
        boolean foundmpUserId = false;
        boolean foundmpUserId3 = false;
        for (int i = 0; i < newRecipients.length; i++) {
            if (newRecipients[i].equals(new Long(mpUserId))) {
                foundmpUserId = true;
            }
            if (newRecipients[i].equals(new Long(mpUserId3))) {
                foundmpUserId3 = true;
            }
        }
        assertTrue("The mpUserId should become a recipient for the message", foundmpUserId);
        assertTrue("The mpUserId3 should become a recipient for the message", foundmpUserId3);

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
            assertEquals("There should be " + sessionUserIds.length + " records"
                , sessionUserIds.length, recordIndex);
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
     * Tests the accuracy for the method <c>postMessageToOthers(Message, ChatSession)</c>.In this
     * method the senderUserId of the <c>message</c> specified to the method is contained
     * between the active users of the <c>chatSession</c>.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToOthersSenderIdContainedInActiveUsers() throws Exception {
        // Initialize the message to be used in calling the method tested
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        long mpUserId3 = 3;
        // The senderUserId is contained into the recipients of the message, but the message
        // will not be posted to him, so there is no need to register an extra session user pool of messages.
        Long[] recipients = new Long[]{new Long(mpUserId3), new Long(senderUserId)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        long[] activeUsers = new long[]{mpUserId, mpUserId3};
        // See TestHelper#insertBasicDataToDB(Connection) for the details on
        // which data is contained in DB. {4,1,1} and {6,3,1} pairs are found
        // in session_user table.
        long[] sessionUserIds = new long[]{4, 6};

        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");

        // All the active users to whom will be posted the message need to have a user session
        // message pool. For userId 1 it is set in the setUp() method.
        messagePool.register(mpUserId3);
        messagePool.register(mpUserId3, mpSessionId);

        messenger.postMessageToOthers(message, chatSession);

        // See if the message was posted to user session message pools {mpUserId, mpSessionId}
        // and {mpUserId3, mpSessionId3}
        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        messages = messagePool.pull(mpUserId3, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);


        Object[] newRecipients = message.getAllRecipients();
        boolean foundmpUserId = false;
        boolean foundSenderUserId = false;
        boolean foundmpUserId3 = false;
        for (int i = 0; i < newRecipients.length; i++) {
            if (newRecipients[i].equals(new Long(senderUserId))) {
                foundSenderUserId = true;
            }
            if (newRecipients[i].equals(new Long(mpUserId))) {
                foundmpUserId = true;
            }
            if (newRecipients[i].equals(new Long(mpUserId3))) {
                foundmpUserId3 = true;
            }
        }
        assertTrue("The mpUserId should become a recipient for the message", foundSenderUserId);
        assertTrue("The mpUserId should become a recipient for the message", foundmpUserId);
        assertTrue("The mpUserId3 should become a recipient for the message", foundmpUserId3);

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
            assertEquals("There should be " + sessionUserIds.length + " records"
                , sessionUserIds.length, recordIndex);
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
     * Tests the accuracy for the method <c>postMessageToOthers(Message, ChatSession)</c>.In this method
     * we'll try to post AskForChatMessage, SessionUnavailableMesage, EnterChatMessage instances to
     * verify that nothing happens when calling the method tested.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToOthersNotSupportedMessageType() throws Exception {
        // Initialize the message to be used in calling the method tested
        Message message = new AskForChatMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        // The senderUserId is contained into the recipients of the message, but the message
        // will not be posted to him, so there is no need to register an extra session user pool of messages.
        Long[] recipients = new Long[]{new Long(mpUserId)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        long[] activeUsers = new long[]{mpUserId};

        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");
        messenger.postMessageToOthers(message, chatSession);

        message = new SessionUnavailableMessage();
        message.setSender(new Long(senderUserId));
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        messenger.postMessage(message, mpUserId);

        message = new EnterChatMessage();
        message.setSender(new Long(senderUserId));
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be no messages in the pool", messages.length == 0);
    }

    /**
     * Tests the accuracy for the method <c>postMessageToOthers(Message, ChatSession)</c>.In this
     * method the the sender of the message to post is blocked by the activer user
     * (from chat session specified to the method) which has the message pool.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToOthersSenderBlocked() throws Exception {
        // Initialize the message to be used in calling the method tested
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        long mpUserId3 = 3;
        // The senderUserId is contained into the recipients of the message, but the message
        // will not be posted to him, so there is no need to register an extra session user pool of messages.
        Long[] recipients = new Long[]{new Long(mpUserId3), new Long(senderUserId)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        long[] activeUsers = new long[]{mpUserId};
        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");

        // All the active users to whom will be posted the message need to have a user session
        // message pool. For userId 1 it is set in the setUp() method.
        messagePool.register(mpUserId3);
        messagePool.register(mpUserId3, mpSessionId);

        // Block the sender
        contactManager.addBlockedUser(mpUserId, senderUserId);

        messenger.postMessageToOthers(message, chatSession);

        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be no messages in the pool", messages.length == 0);
    }

    /**
     * Tests the failure for the method <c>postMessageToOthers(Message, ChatSession)</c>.In this method
     * the <c>message</c> passed to the method is null.
     * <c>IllegalArgumentException</c> is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToOthersNullMessage() throws Exception {
        long[] activeUsers = new long[]{mpUserId};
        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");

        try {
            messenger.postMessageToOthers(null, chatSession);
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the method <c>postMessageToOthers(Message, ChatSession)</c>.In this method
     * the <c>chatSession</c> passed to the method is null.
     * <c>IllegalArgumentException</c> is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToOthersNullChatSession() throws Exception {
        // Initialize the message to be used in calling the method tested
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        long mpUserId3 = 3;
        // The senderUserId is contained into the recipients of the message, but the message
        // will not be posted to him, so there is no need to register an extra session user pool of messages.
        Long[] recipients = new Long[]{new Long(mpUserId3), new Long(senderUserId)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        try {
            messenger.postMessageToOthers(message, null);
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the method <c>postMessageToOthers(Message, ChatSession)</c>.In this method
     * the message will be posted to an inexistent user message pool.
     * <c>MessengerException</c> is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToOthersPoolNotRegistered() throws Exception {
        // Initialize the message to be used in calling the method tested
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        long mpUserId3 = 3;
        // The senderUserId is contained into the recipients of the message, but the message
        // will not be posted to him, so there is no need to register an extra session user pool of messages.
        Long[] recipients = new Long[]{new Long(mpUserId3), new Long(senderUserId)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        // Send the message to an inexistent user pool
        long[] activeUsers = new long[]{5};
        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");
        try {
            messenger.postMessageToOthers(message, chatSession);

            fail("Should have thrown MessengerException because specified user message pool does not exist");
        } catch (MessengerException e) {
            // Success.
        }
    }


    /**
     * Tests the accuracy for the method <c>postMessageToAll(Message, ChatSession)</c>.In this
     * method the senderUserId of the <c>message</c> to the method is not contained between
     * the active users of the <c>chatSession</c> specified to the method.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToAllSenderIdNotContainedInActiveUsers() throws Exception {
        // Initialize the message to be used in calling the method tested
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        long mpUserId3 = 3;
        Long[] recipients = new Long[]{new Long(mpUserId3)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        long[] activeUsers = new long[]{mpUserId, mpUserId3};
        // See TestHelper#insertBasicDataToDB(Connection) for the details on
        // which data is contained in DB. {4,1,1}; {5,2,1} and {6,3,1} pairs are found
        // in session_user table.
        long[] sessionUserIds = new long[]{4, 5, 6};

        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");

        // All the active users to whom will be posted the message need to have a user session
        // message pool. For userId 1 it is set in the setUp() method.
        messagePool.register(mpUserId3);
        messagePool.register(mpUserId3, mpSessionId);
        messagePool.register(senderUserId);
        messagePool.register(senderUserId, mpSessionId);

        messenger.postMessageToAll(message, chatSession);

        // See if the message was posted to user session message pools {mpUserId, mpSessionId}
        // , {mpUserId3, mpSessionId} and {senderUserId, mpSessionId}
        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        messages = messagePool.pull(mpUserId3, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        messages = messagePool.pull(senderUserId, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        Object[] newRecipients = message.getAllRecipients();
        boolean foundmpUserId = false;
        boolean foundmpUserId3 = false;
        boolean foundSenderUserId = false;
        for (int i = 0; i < newRecipients.length; i++) {
            if (newRecipients[i].equals(new Long(senderUserId))) {
                foundSenderUserId = true;
            }

            if (newRecipients[i].equals(new Long(mpUserId))) {
                foundmpUserId = true;
            }
            if (newRecipients[i].equals(new Long(mpUserId3))) {
                foundmpUserId3 = true;
            }
        }
        assertTrue("The senderUserId should become a recipient for the message", foundSenderUserId);
        assertTrue("The mpUserId should become a recipient for the message", foundmpUserId);
        assertTrue("The mpUserId3 should become a recipient for the message", foundmpUserId3);

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
            assertEquals("There should be " + sessionUserIds.length + " records"
                , sessionUserIds.length, recordIndex);
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
     * Tests the accuracy for the method <c>postMessageToAll(Message, ChatSession)</c>.In this
     * method the senderUserId of the <c>message</c> to the method is contained between
     * the active users of the <c>chatSession</c> specified to the method.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToAllSenderIdContainedInActiveUsers() throws Exception {
        // Initialize the message to be used in calling the method tested
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        long mpUserId3 = 3;
        Long[] recipients = new Long[]{new Long(mpUserId3)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        long[] activeUsers = new long[]{mpUserId, senderUserId, mpUserId3};
        // See TestHelper#insertBasicDataToDB(Connection) for the details on
        // which data is contained in DB. {4,1,1}; {5,2,1} and {6,3,1} pairs are found
        // in session_user table.
        long[] sessionUserIds = new long[]{4, 5, 6};

        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");

        // All the active users to whom will be posted the message need to have a user session
        // message pool. For userId 1 it is set in the setUp() method.
        messagePool.register(mpUserId3);
        messagePool.register(mpUserId3, mpSessionId);
        messagePool.register(senderUserId);
        messagePool.register(senderUserId, mpSessionId);

        messenger.postMessageToAll(message, chatSession);

        // See if the message was posted to user session message pools {mpUserId, mpSessionId}
        // , {mpUserId3, mpSessionId} and {senderUserId, mpSessionId}
        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        messages = messagePool.pull(mpUserId3, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        messages = messagePool.pull(senderUserId, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        Object[] newRecipients = message.getAllRecipients();
        boolean foundmpUserId = false;
        boolean foundmpUserId3 = false;
        boolean foundSenderUserId = false;
        for (int i = 0; i < newRecipients.length; i++) {
            if (newRecipients[i].equals(new Long(senderUserId))) {
                foundSenderUserId = true;
            }

            if (newRecipients[i].equals(new Long(mpUserId))) {
                foundmpUserId = true;
            }
            if (newRecipients[i].equals(new Long(mpUserId3))) {
                foundmpUserId3 = true;
            }
        }
        assertTrue("The senderUserId should become a recipient for the message", foundSenderUserId);
        assertTrue("The mpUserId should become a recipient for the message", foundmpUserId);
        assertTrue("The mpUserId3 should become a recipient for the message", foundmpUserId3);

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
            assertEquals("There should be " + sessionUserIds.length + " records"
                , sessionUserIds.length, recordIndex);
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
     * Tests the accuracy for the method <c>postMessageToAll(Message, ChatSession)</c>.In this method
     * we'll try to post AskForChatMessage, SessionUnavailableMesage, EnterChatMessage instances to
     * verify that nothing happens when calling the method tested.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToAllNotSupportedMessageType() throws Exception {
        // Initialize the message to be used in calling the method tested
        Message message = new AskForChatMessage();
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        // The senderUserId is contained into the recipients of the message, but the message
        // will not be posted to him, so there is no need to register an extra session user pool of messages.
        Long[] recipients = new Long[]{new Long(mpUserId)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        long[] activeUsers = new long[]{mpUserId};

        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");
        messenger.postMessageToOthers(message, chatSession);

        message = new SessionUnavailableMessage();
        message.setSender(new Long(senderUserId));
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }
        messenger.postMessage(message, mpUserId);

        message = new EnterChatMessage();
        message.setSender(new Long(senderUserId));
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        Message[] messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be no messages in the pool", messages.length == 0);
    }

    /**
     * Tests the accuracy for the method <c>postMessageToAll(Message, ChatSession)</c>.In this
     * method the the sender of the message to post is blocked by the activer user
     * (from chat session specified to the method) which has the message pool.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToAllSenderBlocked() throws Exception {
        // Initialize the message to be used in calling the method tested
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        // The senderUserId is contained into the recipients of the message, but the message
        // will not be posted to him, so there is no need to register an extra session user pool of messages.
        Long[] recipients = new Long[]{new Long(senderUserId)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        long[] activeUsers = new long[]{mpUserId};
        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");

        // Block the sender
        contactManager.addBlockedUser(mpUserId, senderUserId);

        messagePool.register(senderUserId);
        messagePool.register(senderUserId, mpSessionId);

        messenger.postMessageToAll(message, chatSession);

        // See if the message was posted to user session message pool {senderUserId, mpSessionId}
        Message[] messages = messagePool.pull(senderUserId, mpSessionId);
        assertTrue("There should be only one message in the pool", messages.length == 1);
        assertSame("The messages should be the same", message, messages[0]);

        messenger.postMessageToAll(message, chatSession);

        messages = messagePool.pull(mpUserId, mpSessionId);
        assertTrue("There should be no messages in the pool", messages.length == 0);
    }

    /**
     * Tests the failure for the method <c>postMessageToOthers(Message, ChatSession)</c>.In this method
     * the <c>message</c> passed to the method is null.
     * <c>IllegalArgumentException</c> is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToAllNullMessage() throws Exception {
        long[] activeUsers = new long[]{mpUserId};
        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");

        try {
            messenger.postMessageToAll(null, chatSession);
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the method <c>postMessageToAll(Message, ChatSession)</c>.In this method
     * the <c>chatSession</c> passed to the method is null.
     * <c>IllegalArgumentException</c> is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToAllNullChatSession() throws Exception {
        // Initialize the message to be used in calling the method tested
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        long mpUserId3 = 3;
        // The senderUserId is contained into the recipients of the message, but the message
        // will not be posted to him, so there is no need to register an extra session user pool of messages.
        Long[] recipients = new Long[]{new Long(mpUserId3), new Long(senderUserId)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        try {
            messenger.postMessageToAll(message, null);
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the failure for the method <c>postMessageToAll(Message, ChatSession)</c>.In this method
     * the message will be posted to an inexistent user message pool.
     * <c>MessengerException</c> is expected to be thrown.
     *
     * @throws Exception Should not throw.
     */
    public void testPostMessageToAllPoolNotRegistered() throws Exception {
        // Initialize the message to be used in calling the method tested
        ChatMessage message = new ChatMessage();
        String chatText = "Hello TopCoder";
        message.setChatText(chatText);
        long senderUserId = 2;
        message.setSender(new Long(senderUserId));
        long mpUserId3 = 3;
        // The senderUserId is contained into the recipients of the message, but the message
        // will not be posted to him, so there is no need to register an extra session user pool of messages.
        Long[] recipients = new Long[]{new Long(mpUserId3), new Long(senderUserId)};
        for (int i = 0; i < recipients.length; i++) {
            message.addRecipient(recipients[i]);
        }

        // Send the message to an inexistent user pool
        long[] activeUsers = new long[]{5};
        ChatSession chatSession = new GroupSession(mpSessionId, mpUserId, new Date()
            , new long[]{}, new long[]{}, activeUsers, "TopCoder");
        try {
            messenger.postMessageToAll(message, chatSession);

            fail("Should have thrown MessengerException because specified user message pool does not exist");
        } catch (MessengerException e) {
            // Success.
        }
    }

    /**
     * Tests the setter/getter for message pool field of <c>MessengerImpl</c> class for accuracy.
     */
    public void testSetGetMessagePool() {
        messenger.setMessagePool(messagePool);
        MessagePool returnedMessagePool = messenger.getMessagePool();
        assertSame("The message pools should be the same", messagePool, returnedMessagePool);
    }

    /**
     * Tests <c>setMessagePool(MessagePool)</c> method for failure when the argument of
     * the method is null. <c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testSetMessagePoolNull() {
        try {
            messenger.setMessagePool(null);

            fail("IllegalArgumentException should have been thrown because of null parameter");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }
}
