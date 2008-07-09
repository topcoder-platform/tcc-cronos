/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import java.io.File;
import java.util.Date;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.messaging.persistence.InformixMessageBoardPersistence;

import junit.framework.TestCase;

/**
 * The accuracy test for the class {@link MessageBoard}.
 * 
 * @author yqw
 * @version 1.0
 */
public class MessageBoardFailureTests extends TestCase {
    /**
     * The MessageBoard for test.
     */
    private MessageBoard instance;

    /**
     * sets up the test environment.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    protected void setUp() throws Exception {
        MessageBoardPersistence persistence = new InformixMessageBoardPersistence(
                "test_files/config.properties",
                "InformixMessageBoardPersistence");
        instance = new MessageBoard(persistence);
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
     * test the method
     * {@link MessageBoard#MessageBoard(com.topcoder.configuration.ConfigurationObject)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_1() throws Exception {
        // build the XMLFilePersistence
        XMLFilePersistence xmlFilePersistence = new XMLFilePersistence();

        // load the ConfigurationObject from the input file
        ConfigurationObject cfgObject = xmlFilePersistence.loadFile("test",
                new File("test_files/MessageBoard.xml"));
        cfgObject = cfgObject.getChild("MessageBoard");
        try {
            instance = new MessageBoard(cfgObject);
            fail("MessageBoardConfigurationException should be thrown.");
        } catch (MessageBoardConfigurationException e) {
            // good
        }
    }

    /**
     * test the method {@link MessageBoard#MessageBoard(MessageBoardPersistence)}.
     */
    public void testCtor_2() {
        try {
            instance = new MessageBoard((MessageBoardPersistence) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link MessageBoard#MessageBoard(com.topcoder.configuration.ConfigurationObject)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_3() throws Exception {
        MessageBoardPersistence persistence = new InformixMessageBoardPersistence(
                "test_files/config.properties",
                "InformixMessageBoardPersistence");
        try {
            instance = new MessageBoard(persistence, -100, 1000);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link MessageBoard#MessageBoard(com.topcoder.configuration.ConfigurationObject)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_4() throws Exception {
        MessageBoardPersistence persistence = new InformixMessageBoardPersistence(
                "test_files/config.properties",
                "InformixMessageBoardPersistence");
        try {
            instance = new MessageBoard(persistence, 100, -21000);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#createThread(com.topcoder.messaging.Thread)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCreateThread_1() throws Exception {
        try {
            Thread thread = new Thread();
            String userKey = "userKey1";
            thread.setUserKey(userKey);

            Message message1 = new Message("message1", new Date(),
                    "some message");
            // set the id. it will cause error.
            message1.setId(200);

            Message message2 = new Message("message2", new Date(),
                    "some message");
            Response response1 = new Response("response1", new Date(),
                    "some message");
            Response response2 = new Response("response2", new Date(),
                    "some message");
            message1.setResponse(response1);
            message2.setResponse(response2);
            thread.addMessage(message1);
            thread.addMessage(message2);
            instance.createThread(thread);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#createThread(com.topcoder.messaging.Thread)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testUpdateThread_Null() throws Exception {
        try {
            instance.updateThread(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#createThread(com.topcoder.messaging.Thread)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testUpdateThread() throws Exception {
        try {
            Thread thread = new Thread();
            // /it will cause error.
            thread.setId(-3);
            instance.updateThread(thread);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#createThread(com.topcoder.messaging.Thread)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testDeleteThread() throws Exception {
        try {
            // delete it.
            instance.deleteThread(-3);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getThread(long)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetThread_Nagative() throws Exception {
        try {
            instance.getThread(-3);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getThread(long)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetThread_Not_Found() throws Exception {
        try {
            instance.getThread(5);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(long, int, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_id() throws Exception {
        try {
            instance.getMessages(-3, 200, 2);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(long, int, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_count() throws Exception {
        try {
            instance.getMessages(3, 200, -2);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(long, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_2_id() throws Exception {
        try {
            instance.getMessages(-2);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(String, int, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_3_null() throws Exception {
        try {
            instance.getMessages(null, 200, 2);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(String, int, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_3_empty() throws Exception {
        try {
            instance.getMessages("  ", 200, 2);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(String, int, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_3_count() throws Exception {
        try {
            instance.getMessages("userkey", 200, -2);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(String, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_4_null() throws Exception {
        try {
            instance.getMessages(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#updateMessage(Message)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testUpdateMessage_null() throws Exception {
        try {
            instance.updateMessage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#removeMessage(long)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testRemoveMessage() throws Exception {
        try {
            instance.removeMessage(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#removeAllMessages(long)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testRemoveAllMessages() throws Exception {
        try {
            instance.removeAllMessages(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#postMessage(long, Message)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testPostMessage() throws Exception {
        try {
            Message message3 = new Message("new post message", new Date(),
                    "some message");
            instance.postMessage(-5, message3);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#postMessage(long, Message)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testPostResponse() throws Exception {
        try {
            Response response = new Response("new post response", new Date(),
                    "some message");
            instance.postResponse(-3, response);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#removeResponse(long)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testRemoveResponse() throws Exception {
        try {
            instance.removeResponse(-3);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#updateResponse(Response)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void updateResponse() throws Exception {
        try {
            instance.updateResponse(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
