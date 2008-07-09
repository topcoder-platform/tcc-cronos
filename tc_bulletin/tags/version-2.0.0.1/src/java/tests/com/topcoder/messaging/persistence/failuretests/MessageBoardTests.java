/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging.persistence.failuretests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;

import com.topcoder.messaging.EntityNotFoundException;
import com.topcoder.messaging.Message;
import com.topcoder.messaging.MessageBoard;
import com.topcoder.messaging.MessageBoardConfigurationException;
import com.topcoder.messaging.MessageBoardPersistence;
import com.topcoder.messaging.Thread;
import com.topcoder.messaging.persistence.InformixMessageBoardPersistence;

import junit.framework.TestCase;

import java.io.File;

import java.util.Date;


/**
 * This is the failure test for the class {@link MessageBoard}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MessageBoardTests extends TestCase {
    /**
     * The MessageBoard for test.
     */
    private MessageBoard instance;

    /**
     * Represents the ConfigurationObject used to build MessageBoard.
     */
    private ConfigurationObject cfgObject;

    /**
     * Represents MessageBoardPersistence used for test.
     */
    private MessageBoardPersistence persistence;

    /**
     * Represents Thread used in test.
     */
    private Thread thread;

    /**
     * Represents Message used in test.
     */
    private Message message;

    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    protected void setUp() throws Exception {
        XMLFilePersistence xmlFilePersistence = new XMLFilePersistence();

        cfgObject = xmlFilePersistence.loadFile("test", new File("test_files/failure/MessageBoard.xml"));
        cfgObject = cfgObject.getChild("MessageBoard");
        persistence = new InformixMessageBoardPersistence("test_files/config.properties",
                "InformixMessageBoardPersistence");
        instance = new MessageBoard(persistence);

        message = new Message();
        message.setDate(new Date());

        thread = new Thread();
    }

    /**
     * tearDown the test environment.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Test constructor.
     *
     * -1 maxMessageSize is given. No exception is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testConstructor3_MinusOne_MaxMessageSize()
        throws Exception {
        new MessageBoard(persistence, -1, 100);
    }

    /**
     * test the method {@link MessageBoard#MessageBoard(ConfigurationObject)}.
     *
     * @throws Exception
     *             Exception to Junit.
     */
    public void testCtor_NonLong_Message_Count() throws Exception {
        try {
            instance = new MessageBoard(cfgObject);
            fail("MessageBoardConfigurationException should be thrown.");
        } catch (MessageBoardConfigurationException e) {
        }
    }

    /**
     * test the method {@link MessageBoard#MessageBoard(ConfigurationObject)}.
     *
     * @throws Exception
     *             Exception to Junit.
     */
    public void testCtor_Empty_Error_Messages_Child_Name()
        throws Exception {
        try {
            cfgObject.setPropertyValue("error_messages_child_name", "");
            instance = new MessageBoard(cfgObject);
            fail("MessageBoardConfigurationException should be thrown.");
        } catch (MessageBoardConfigurationException e) {
        }
    }

    /**
     * Tests createThread method. Null thread is given, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testCreateThread_NullThread() throws Exception {
        try {
            instance.createThread(null);
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests createThread method. Message id is positive, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testCreateThread_OverlargeMessage() throws Exception {
        try {
            cfgObject.setPropertyValue("max_message_size", new Integer(1));
            cfgObject.setPropertyValue("message_count", new Integer(1));
            instance = new MessageBoard(cfgObject);
            message.setMessage("11111111111111111111111111");
            thread.addMessage(message);

            instance.createThread(thread);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests updateThread method. Null thread is given, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testUpdateThread_NullThread() throws Exception {
        try {
            instance.updateThread(null);
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests updateThread method. Message is overlarge, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testUpdateThread_OverlargeMessage() throws Exception {
        cfgObject.setPropertyValue("max_message_size", new Integer(1));
        cfgObject.setPropertyValue("message_count", new Integer(1));
        instance = new MessageBoard(cfgObject);

        message.setMessage("11111111111111111111111111");
        thread.addMessage(message);
        thread.setId(1);

        try {
            instance.updateThread(thread);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests deleteThread method. Negative id is given, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testDeleteThread_NegativeId() throws Exception {
        try {
            instance.deleteThread(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests deleteThread method. EntityNotFoundException is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testDeleteThread_EntityNotFound() throws Exception {
        try {
            instance.deleteThread(1000);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
        }
    }

    /**
     * Tests getThread method. Negative id is given, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testGetThread_NegativeId() throws Exception {
        try {
            instance.getThread(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests getThread method. EntityNotFoundException is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testGetThread_EntityNotFound() throws Exception {
        try {
            instance.getThread(1000);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
        }
    }

    /**
     * Tests getMessages method. Negative id is given, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testGetMessages_NegativeId() throws Exception {
        try {
            instance.getMessages(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests removeMessage method. Negative id is given, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testRemoveMessages_NegativeId() throws Exception {
        try {
            instance.removeMessage(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests removeMessage method. EntityNotFoundException is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testRemoveMessage_EntityNotFound() throws Exception {
        try {
            instance.removeMessage(1000);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
        }
    }

    /**
     * Tests removeAllMessages method. EntityNotFoundException is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testRemoveAllMessages_EntityNotFound()
        throws Exception {
        try {
            instance.removeAllMessages(1000);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
        }
    }

    /**
     * Tests removeResponse method. Negative id is given, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testRemoveResponse_NegativeId() throws Exception {
        try {
            instance.removeResponse(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests removeResponse method. EntityNotFoundException is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testRemoveResponse_EntityNotFound() throws Exception {
        try {
            instance.removeResponse(1000);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
        }
    }

    /**
     * Tests postMessage method. Null thread is given, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testPostMessage_NullMessage() throws Exception {
        try {
            instance.postMessage(1, null);
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests postMessage method. Message is overlarge, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testPostMessage_OverlargeMessage() throws Exception {
        cfgObject.setPropertyValue("max_message_size", new Integer(1));
        cfgObject.setPropertyValue("message_count", new Integer(1));
        instance = new MessageBoard(cfgObject);
        message.setMessage("11111111111111111111111111");

        try {
            instance.postMessage(1, message);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests postMessage method. EntityNotFoundException is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testPostMessage_EntityNotFound() throws Exception {
        try {
            instance.postMessage(1000, message);
            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
        }
    }

    /**
     * Tests updateMessage method. Null thread is given, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testUpdateMessage_NullMessage() throws Exception {
        try {
            instance.updateMessage(null);
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests updateMessage method. Message is overlarge, IAE is expected.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testUpdateMessage_OverlargeMessage() throws Exception {
        cfgObject.setPropertyValue("max_message_size", new Integer(1));
        cfgObject.setPropertyValue("message_count", new Integer(1));
        instance = new MessageBoard(cfgObject);
        message.setMessage("11111111111111111111111111");
        message.setId(1);

        try {
            instance.updateMessage(message);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
        }
    }
}
