/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.messaging.accuracytests;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.messaging.Message;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the accuracy test cases to test the
 * <code>Thread</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestThread extends TestCase {

    /**
     * <p>
     * Represents the instance of thread to be used for testing.
     * </p>
     */
    private com.topcoder.messaging.Thread thread = new com.topcoder.messaging.Thread();

    /**
     * <p>
     * This method tests the default constructor to set the fields to null.
     * </p>
     */
    public void testDefaultCtor() {
        com.topcoder.messaging.Thread thread = new com.topcoder.messaging.Thread();
        assertNotNull("messages not set propery by the constructor", thread.getMessages());
        assertEquals("messages not set propery by the constructor", 0, thread.getMessages().size());
        assertNull("user key not set propery by the constructor", thread.getUserKey());
    }

    /**
     * <p>
     * This method tests the default constructor to set the fields to null.
     * </p>
     */
    public void testDefaultCtor1() {
        com.topcoder.messaging.Thread thread = new com.topcoder.messaging.Thread("some user key");
        assertNotNull("messages not set propery by the constructor", thread.getMessages());
        assertEquals("messages not set propery by the constructor", 0, thread.getMessages().size());
        assertEquals("user key not set propery by the constructor", "some user key", thread.getUserKey());
    }

    /**
     * <p>
     * This method tests the default constructor to set the fields to null.
     * </p>
     */
    public void testDefaultCtor2() {
        com.topcoder.messaging.Thread thread = new com.topcoder.messaging.Thread(1, "some user key");
        assertEquals("Id not set propery by the constructor", 1, thread.getId());
        assertNotNull("messages not set propery by the constructor", thread.getMessages());
        assertEquals("messages not set propery by the constructor", 0, thread.getMessages().size());
        assertEquals("user key not set propery by the constructor", "some user key", thread.getUserKey());
    }

    /**
     * <p>
     * This method tests the default constructor to set the fields to null.
     * </p>
     */
    public void testDefaultCtor3() {
        Set<Message> messages = new HashSet<Message>();
        messages.add(new Message());
        com.topcoder.messaging.Thread thread = new com.topcoder.messaging.Thread("some user key", messages);
        assertEquals("Id not set propery by the constructor", -1, thread.getId());
        assertNotNull("messages not set propery by the constructor", thread.getMessages());
        assertEquals("messages not set propery by the constructor", 1, thread.getMessages().size());
        assertEquals("user key not set propery by the constructor", "some user key", thread.getUserKey());
    }

    /**
     * <p>
     * This method tests the default constructor to set the fields to null.
     * </p>
     */
    public void testDefaultCtor4() {
        Set<Message> messages = new HashSet<Message>();
        messages.add(new Message());
        com.topcoder.messaging.Thread thread = new com.topcoder.messaging.Thread(1, "some user key", messages);
        assertEquals("Id not set propery by the constructor", 1, thread.getId());
        assertNotNull("messages not set propery by the constructor", thread.getMessages());
        assertEquals("messages not set propery by the constructor", 1, thread.getMessages().size());
        assertEquals("user key not set propery by the constructor", "some user key", thread.getUserKey());
    }

    /**
     * <p>
     * This method tests the getter and getter of messages to be proper.
     * </p>
     */
    public void testGetSetMessages() {
        Set<Message> messages = new HashSet<Message>();
        messages.add(new Message());
        this.thread.setMessages(messages);
        
        assertEquals("getter/setter of messages not proper", messages, thread.getMessages());
    }

    /**
     * <p>
     * This method tests the getMessage() to be proper.
     * </p>
     */
    public void testGetMessage() {
        Set<Message> messages = new HashSet<Message>();
        Message message1 = new Message(1, "some name", new Date(), "some message");
        messages.add(message1);
        messages.add(new Message(2, "some name2", new Date(), "some message2"));
        this.thread.setMessages(messages);
        
        assertEquals("getMessage() not proper", message1, thread.getMessage(1));
        assertNull("getMessage() not proper", thread.getMessage(3));
    }

    /**
     * <p>
     * This method tests the addMessage() to be proper.
     * </p>
     */
    public void testAddMessage() {
        Message message1 = new Message(1, "some name", new Date(), "some message");
        assertTrue("addMessage() not proper", this.thread.addMessage(message1));
        assertEquals("addMessage() not proper", message1, thread.getMessage(1));

        assertFalse("addMessage() not proper", this.thread.addMessage(message1));
    }

    /**
     * <p>
     * This method tests the removeMessage() to be proper.
     * </p>
     */
    public void testRemoveMessage() {
        Message message1 = new Message(1, "some name", new Date(), "some message");
        this.thread.addMessage(message1);

        assertEquals("removeMessage() not proper", message1, this.thread.removeMessage(1));
        assertNull("removeMessage() not proper", this.thread.getMessage(1));
        assertNull("removeMessage() not proper", this.thread.removeMessage(1));
    }

    /**
     * <p>
     * This method tests the removeMessage() to be proper.
     * </p>
     */
    public void testRemoveMessageMessageArg() {
        Message message1 = new Message(1, "some name", new Date(), "some message");
        this.thread.addMessage(message1);

        assertEquals("removeMessage() not proper", message1, this.thread.removeMessage(message1));
        assertNull("removeMessage() not proper", this.thread.getMessage(1));
        assertNull("removeMessage() not proper", this.thread.removeMessage(message1));
    }

    /**
     * <p>
     * This method tests the clearMessages() to be proper.
     * </p>
     */
    public void testClearMessages() {
        Message message1 = new Message(1, "some name", new Date(), "some message");
        this.thread.addMessage(message1);
        assertNotNull("addMessage() not proper", this.thread.getMessage(1));

        this.thread.clearMessages();
        assertEquals("clearMessages() not proper", 0, this.thread.getMessages().size());
    }

    /**
     * <p>
     * This method tests the containsMessage() to be proper.
     * </p>
     */
    public void testContainsMessage() {
        Message message1 = new Message(1, "some name", new Date(), "some message");
        this.thread.addMessage(message1);
        assertNotNull("addMessage() not proper", this.thread.getMessage(1));

        assertTrue("containsMessage() not proper", this.thread.containsMessage(message1));
        assertFalse("containsMessage() not proper", this.thread.containsMessage(new Message()));
    }

    /**
     * <p>
     * This method tests the setter and getter of the ser key to be proper.
     * </p>
     */
    public void testGetSetUserKey() {
        this.thread.setUserKey("some user key");
        assertEquals("getter/setter of user key not proper", "some user key", this.thread.getUserKey());
    }

}
