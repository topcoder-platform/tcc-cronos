/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * The unit test for the class {@link Thread}.
 * @author yqw
 * @version 2.0
 */
public class ThreadTests extends TestCase {
    /**
     * The Thread instance for test.
     */
    private Thread instance;
    /**
     * the id for test.
     */
    private long idTest = 2L;
    /**
     * the set for test.
     */
    private Set<Message> messagesTest = new HashSet<Message>();

    /**
     * sets up the test environment.
     */
    protected void setUp() {
        instance = new Thread();
        messagesTest.add(new Message()); 
    }

    /**
     * tear down the test environment
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * The accuracy test for the default constructor {@link Thread#Thread()}.
     */
    public void testCtor() {
        assertNotNull("The instance should not be null.",instance);
    }

    /**
     * The accuracy  test for the constructor{@link Thread#Thread(String userKey)}.
     */
    public void testCtor1() {
        instance = new Thread("test");
        assertNotNull("The instance should not be null.",instance);
    }

    /**
     * The failure test for the constructor.The userKey is null.IllegalArgumentException should be thrown.
     */
    public void testCtor1_userKey_null() {
        try {
            new Thread(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor.The userKey is empty.IllegalArgumentException should be thrown.
     */
    public void testCtor1_userKey_empty() {
        try {
            new Thread("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy  test for the constructor{@link Thread#Thread(long id, String userKey)}.
     */
    public void testCtor2() {
        instance = new Thread(idTest, "test");
        assertNotNull("The instance should not be null.",instance);
    }

    /**
     * The failure test for the constructor. The id is negative. IllegalArgumentException should be thrown.
     */
    public void testCtor2_id_Negative() {
        try {
            new Thread(-2L, "userKey");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor.The userKey is null.IllegalArgumentException should be thrown.
     */
    public void testCtor2_userKey_null() {
        try {
            new Thread(idTest, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor.The userKey is empty.IllegalArgumentException should be thrown.
     */
    public void testCtor2_userKey_empty() {
        try {
            new Thread(idTest, "");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy  test for the constructor{@link Thread#Thread(String userKey, Set<Message> messages)}.
     */
    public void testCtor3() {
        instance = new Thread("test", messagesTest);
        assertEquals("The userKey is incorrect", "test", instance.getUserKey());
        assertEquals("The messages should be created", messagesTest,
            instance.getMessages());
    }

    /**
     * The failure test for the constructor.The userKey is null.IllegalArgumentException should be thrown.
     */
    public void testCtor3_userKey_null() {
        try {
            new Thread(null, messagesTest);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor.The userKey is empty.IllegalArgumentException should be thrown.
     */
    public void testCtor3_userKey_empty() {
        try {
            new Thread("", messagesTest);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor.The message is null.IllegalArgumentException should be thrown.
     */
    public void testCtor3_messages_is_null() {
        try {
            new Thread("userKey", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor.The message contains null.IllegalArgumentException should be thrown.
     */
    public void testCtor3_messages_contains_null() {
        messagesTest.add(null);

        try {
            new Thread("userKey", messagesTest);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy  test for the constructor{@link Thread#Thread(long id, String userKey, Set<Message> messages)}.
     */
    public void testCtor4() {
        instance = new Thread(idTest, "userKey", messagesTest);
        assertEquals("The id is incorrect", idTest, instance.getId());
        assertEquals("The userKey is incorrect", "userKey",
            instance.getUserKey());
        assertEquals("The messages is incorrect", messagesTest,
            instance.getMessages());
    }

    /**
     * The failure test for the constructor. The id is negative. IllegalArgumentException should be thrown.
     */
    public void testCtor4_id_Negative() {
        try {
            new Thread(-2L, "userKey", messagesTest);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor.The userKey is null.IllegalArgumentException should be thrown.
     */
    public void testCtor4_userKey_null() {
        try {
            new Thread(idTest, null, messagesTest);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor.The userKey is empty.IllegalArgumentException should be thrown.
     */
    public void testCtor4_userKey_empty() {
        try {
            new Thread(idTest, "", messagesTest);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor.The message is null.IllegalArgumentException should be thrown.
     */
    public void testCtor4_messages_is_null() {
        try {
            new Thread(idTest, "userKey", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor.The message contains null.IllegalArgumentException should be thrown.
     */
    public void testCtor4_messages_contains_null() {
        messagesTest.add(null);

        try {
            new Thread(idTest, "userKey", messagesTest);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for the method {@link BasicEntityData#getMessages()}.
     */
    public void testGetMessages() {
        instance.setMessages(messagesTest);
        assertEquals("The Messages is incorrect.", messagesTest,
            instance.getMessages());
    }

    /**
     * The accuracy test for the method {@link Thread#setMessages(Set<Message> messages)}
     */
    public void testSetMessages() {
        instance.setMessages(messagesTest);
        assertEquals("The Messages is incorrect.", messagesTest,
            instance.getMessages());
    }

    /**
     * The failure test for method setMessages(Set<Message> messages).
     * The message is null.IllegalArgumentException should be thrown.
     */
    public void testSetMessages_is_null() {
        try {
            instance.setMessages(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for method
     * setMessages(Set<Message> messages). The message contains null.IllegalArgumentException should be thrown.
     */
    public void testSetMessages_contains_null() {
        messagesTest.add(null);

        try {
            instance.setMessages(messagesTest);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for the method {@link BasicEntityData#getMessage(long id)}.
     */
    public void testGetMessage() {
        Message message = new Message(idTest, "name", new Date(), "mess");
        messagesTest.add(message);
        instance.setMessages(messagesTest);
        assertEquals("The Messages is incorrect.", message,
            instance.getMessage(idTest));
        //if not found
        assertEquals("The Messages is incorrect.", null, instance.getMessage(1L));
    }

    /**
     * The failure test for method getMessages(long id).
     * The message contains null.IllegalArgumentException should be thrown.
     */
    public void testGetMessage_id_Negative() {
        try {
            instance.getMessage(-1L);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for the method {@link Thread#addMessage(Message message)}
     */
    public void testAddMessage() {
        Message message = new Message(idTest, "name", new Date(), "mess");
        assertEquals("The message is not added.", true,
            instance.addMessage(message));
        //The message was added
        assertEquals("The message is not added.", false,
            instance.addMessage(message));
    }

    /**
     * The failure test for method addMessages(Set<Message> messages).
     * The message is null.IllegalArgumentException should be thrown.
     */
    public void testAddMessage_is_null() {
        try {
            instance.addMessage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for the method {@link Thread#removeMessage(long id)}
     */
    public void testRemoveMessage_id() {
        Message message = new Message(idTest, "name", new Date(), "mess");
        messagesTest.add(message);
        instance.setMessages(messagesTest);
        assertEquals("The Message is not removed.", message,
            instance.removeMessage(idTest));
        // not found
        assertEquals("The Message is not removed.", null,
            instance.removeMessage(11L));
    }

    /**
     * The failure test for method removeMessages(Set<Message> messages).
     * The id is negative.IllegalArgumentException should be thrown.
     */
    public void testRemoveMessage_id_Negative() {
        try {
            instance.removeMessage(-1L);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for the method {@link Thread#removeMessage(Message message)}
     */
    public void testRemoveMessage_Message() {
        Message message = new Message(idTest, "name", new Date(), "mess");
        Message message1 = new Message();
        messagesTest.add(message);
        instance.setMessages(messagesTest);
        assertEquals("The Message is not removed.", message,
            instance.removeMessage(message));
        // not found
        assertEquals("The Message is not removed.", null,
            instance.removeMessage(message1));
    }

    /**
     * The failure test for method removeMessage(Message message).
     * The message is null.IllegalArgumentException should be thrown.
     */
    public void testRemoveMessage_Message_null() {
        try {
            instance.removeMessage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for method {@link Thread#clearMessages()}.
     */
    public void testClearMessage() {
        Message message = new Message(idTest, "name", new Date(), "mess");
        messagesTest.add(message);
        instance.setMessages(messagesTest);
        instance.clearMessages();
        assertEquals("The errorMessage is not cleared.", false,
            instance.containsMessage(message));
    }

    /**
     * The accuracy test for method {@link Thread#containsMessage(Message message)}.
     */
    public void testContainsMessage() {
        Message message = new Message(idTest, "name", new Date(), "mess");
        Message message1 = new Message();
        messagesTest.add(message);
        instance.setMessages(messagesTest);
        assertEquals("The errorMessage is not cleared.", true,
            instance.containsMessage(message));
        //if not found
        assertEquals("The errorMessage is not cleared.", false,
            instance.containsMessage(message1));
    }

    /**
     * The failure test for method containsMessage(Message message).
     * The message is null.IllegalArgumentException should be thrown.
     */
    public void testContainsMessage_null() {
        try {
            instance.containsMessage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for the method {@link Thread#setUserKey(String)}
     */
    public void testSetuserKey() {
        instance.setUserKey("test");
        assertEquals("The userKey is incorrect.", "test", instance.getUserKey());
    }

    /**
     * The failure test for method setUserKey(String).
     * The userKey is null.IllegalArgumentException should be thrown.
     */
    public void testSetUserKey_null() {
        try {
            instance.setUserKey(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor.The userKey is empty.IllegalArgumentException should be thrown.
     */
    public void testSetUserKey_empty() {
        try {
            instance.setUserKey("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for the method {@link Thread#getUserKey}
     */
    public void testGetuserKey() {
        instance.setUserKey("test");
        assertEquals("The userKey is incorrect.", "test", instance.getUserKey());
    }
}
