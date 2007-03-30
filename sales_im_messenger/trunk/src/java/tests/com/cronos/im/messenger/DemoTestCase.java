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

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * This class shows how Sales IM Messenger component should be used.
 * This demo will give provide a short introduction on how to:
 * <ul>
 * <li>Create a ChatMessage instance</li>
 * <li>Post a message to a user message pool</li>
 * <li>Post a message to a chat session</li>
 * </ul>
 *
 * @author marius_neo
 * @version 1.0
 */
public class DemoTestCase extends TestCase {
    /**
     * Represents a valid configuration file for loading namespaces requested by <c>FormatterLoader</c> class.
     */
    private final String formatterLoaderValidConfig = "test_files" + File.separator + "formatter_loader_config.xml";
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
        TestHelper.loadXMLConfig(formatterLoaderValidConfig);
        TestHelper.setUpDataBase();

        messagePool = new DefaultMessagePool();

        DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_CONNECTION_FACTORY_NAMESPACE);
        String connName = "informix_connect";
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
     * Shows the API offered by <c>ChatMessage</c> class. In the same time, this method
     * gives you a hint on how to use <c>DateFormatContext</c> API.
     *
     * @throws Exception Should not throw.
     */
    public void testDemoChatMessageUsage() throws Exception {
        ChatMessage chatMessage = new ChatMessage();
        // Set the attributes  for the chat message
        Map chatProfileProperties = new HashMap();
        chatProfileProperties.put("userName", new String[]{"marius_neo"});
        String chatText = "Visit http://www.google.com/finance everyday and you will be very rich.";
        Object sender = new Long(123);
        Date timestamp = new Date();
        Map attributes = new HashMap();
        attributes.put("key1", "value1");
        attributes.put("key2", "value2");
        long chatSessionId = 1234;
        Object[] recipients = new Long[]{new Long(123), new Long(124), new Long(125)};

        // Set the required fields on the message for calling without
        // problems the method toXMLString(DateFormatContext).
        chatMessage.setSender(sender);
        chatMessage.setTimestamp(timestamp);
        for (Iterator i = attributes.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            chatMessage.setAttribute((String) entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < recipients.length; i++) {
            chatMessage.addRecipient(recipients[i]);
        }
        chatMessage.setChatSessionId(chatSessionId);
        chatMessage.setChatText(chatText);
        chatMessage.setChatProfileProperties(chatProfileProperties);

        // Set up context used in retrieving the xml form for the chat message.
        String localeString = Locale.US.toString();
        String timeZoneID = TimeZone.getTimeZone("America/Los_Angeles").getID();
        String dateFormat = "hh:mm:ss a z";

        // Add attributes to the context
        DateFormatContext context = new DateFormatContext();
        context.addAttribute(DateFormatContext.DATE_FORMAT_KEY, dateFormat);
        context.addAttribute(DateFormatContext.TIMEZONE_KEY, timeZoneID);
        context.addAttribute(DateFormatContext.LOCALE_KEY, localeString);

        // Retrieve the xml string representation of the chat message
        String xmlResult = chatMessage.toXMLString(context);
    }

    /**
     * This tests shows the usage of <c>MessengerImpl#postMessage(Message, long)</c> method.
     * After posting the message to the specified user message pool, the user will have
     * a new message received.
     *
     * @throws Exception Should not throw.
     */
    public void testDemoMessengerImplUsage1() throws Exception {
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
    }

    /**
     * This test shows the usage of <c>MessengerImpl#postMessageToAll(Message,ChatSession)</c>.
     * First the message and the chat session will be initialized and then
     * the message will be posted to all the members of the chat session.
     * In this manner, when pulling the messages from their user session message pools,
     * the users will have a new message received.
     *
     * @throws Exception Should not throw.
     */
    public void testDemoMessengerImplUsage2() throws Exception {
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
        System.out.println("There should be only one message in the pool : "
            + (messages.length == 1));
        System.out.println("The messages should be the same : "
            + (message == messages[0]));

        messages = messagePool.pull(mpUserId3, mpSessionId);
        System.out.println("There should be only one message in the pool"
            + (messages.length == 1));
        System.out.println("The messages should be the same"
            + (message == messages[0]));

        messages = messagePool.pull(senderUserId, mpSessionId);
        System.out.println("There should be only one message in the pool"
            + (messages.length == 1));
        System.out.println("The messages should be the same: "
            + (message == messages[0]));
    }
}
