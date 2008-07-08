/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;

import com.topcoder.messaging.persistence.InformixMessageBoardPersistence;

import junit.framework.TestCase;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The accuracy test for the class {@link MessageBoard}.
 * 
 * @author yqw
 * @version 1.0
 */
public class MessageBoardAccTests extends TestCase {
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
        TestHelper.clearDB();

        MessageBoardPersistence persistence = new InformixMessageBoardPersistence(
                "test_files/config.properties",
                "InformixMessageBoardPersistence");
        instance = new MessageBoard(persistence, 1000, 1000);
    }

    /**
     * tearDown the test environment.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    protected void tearDown() throws Exception {
        instance = null;
        TestHelper.clearDB();
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
        cfgObject.setPropertyValue("max_message_size", 1000);
        cfgObject.setPropertyValue("message_count", 20);
        instance = new MessageBoard(cfgObject);
        assertNotNull("The instance should not be null.", instance);
    }

    /**
     * test the method {@link MessageBoard#MessageBoard(MessageBoardPersistence)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_2() throws Exception{
        MessageBoardPersistence persistence = new InformixMessageBoardPersistence(
                "test_files/config.properties",
                "InformixMessageBoardPersistence");
        instance = new MessageBoard(persistence);
        assertNotNull("The instance should not be null.", instance);
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
        instance = new MessageBoard(persistence, 100, 1000);
        assertNotNull("The instance should not be null.", instance);
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#createThread(com.topcoder.messaging.Thread)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCreateThread() throws Exception {
        Thread thread = new Thread();
        String userKey = "userKey1";
        thread.setUserKey(userKey);

        Message message1 = new Message("message1", new Date(), "some message");
        Message message2 = new Message("message2", new Date(), "some message");
        Response response1 = new Response("response1", new Date(),
                "some message");
        Response response2 = new Response("response2", new Date(),
                "some message");
        message1.setResponse(response1);
        message2.setResponse(response2);
        thread.addMessage(message1);
        thread.addMessage(message2);

        Thread result = instance.createThread(thread);
        assertTrue("the result id should be set.", result.getId() > 0);
        assertTrue("the message id should be set.", message1.getId() > 0);
        assertTrue("the message id should be set.", message2.getId() > 0);
        assertTrue("the response id should be set.", response1.getId() > 0);
        assertTrue("the response id should be set.", response2.getId() > 0);
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#updateThread(com.topcoder.messaging.Thread)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testUpdateThread() throws Exception {
        Thread thread = new Thread();
        String userKey = "userKey1";
        thread.setUserKey(userKey);

        Message message1 = new Message("message1", new Date(), "some message");
        Message message2 = new Message("message2", new Date(), "some message");
        Response response1 = new Response("response1", new Date(),
                "some message");
        Response response2 = new Response("response2", new Date(),
                "some message");
        message1.setResponse(response1);
        message2.setResponse(response2);
        thread.addMessage(message1);
        thread.addMessage(message2);

        Thread result = instance.createThread(thread);

        // add
        Message message3 = new Message("message1", new Date(), "some message");
        result.addMessage(message3);
        // update
        message2.setName("new message2");
        result.addMessage(message2);
        // remove
        result.removeMessage(message1);
        instance.updateThread(result);
        result = instance.getThread(result.getId());

        Set<Message> set = result.getMessages();
        assertEquals("The size is incorrect.", 2, set.size());
        assertEquals("The message should be updated.", "new message2", result
                .getMessage(message2.getId()).getName());
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#deleteThread(Thread)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testDeleteThread() throws Exception {
        Thread thread = new Thread();
        String userKey = "userKey1";
        thread.setUserKey(userKey);

        Message message1 = new Message("message1", new Date(), "some message");
        Message message2 = new Message("message2", new Date(), "some message");
        Response response1 = new Response("response1", new Date(),
                "some message");
        Response response2 = new Response("response2", new Date(),
                "some message");
        message1.setResponse(response1);
        message2.setResponse(response2);
        thread.addMessage(message1);
        thread.addMessage(message2);

        Thread result = instance.createThread(thread);
        // delete it.
        instance.deleteThread(result.getId());

        // assert it
        try {
            result = instance.getThread(result.getId());
            fail("it should be deleted.");
        } catch (EntityNotFoundException e) {
            // good
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getThread(long)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetThread_1() throws Exception {
        TestHelper.insertDB();

        Thread thread = instance.getThread(1);
        assertNotNull("The thread should not be null.", thread);
        assertEquals("The thread is incorrect.", "userKey1", thread
                .getUserKey());

        assertEquals("The thread is incorrect.", 1, thread.getId());
        assertEquals("The thread is incorrect.", 1, thread.getId());
        assertEquals("The thread is incorrect.", 1, thread.getId());

        Set<Message> set = thread.getMessages();
        assertEquals("The thread is incorrect.", 2, set.size());

        for (Message m : set) {
            if ("message1".equals(m.getName())) {
                assertEquals("The message is incorrect.", 1, m.getId());
            }

            if ("message2".equals(m.getName())) {
                assertEquals("The message is incorrect.", 2, m.getId());
            }
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getThread(long)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetThread_2() throws Exception {
        TestHelper.insertDB();

        Thread thread = instance.getThread("userKey1");
        assertNotNull("The thread should not be null.", thread);
        assertEquals("The thread is incorrect.", "userKey1", thread
                .getUserKey());

        assertEquals("The thread is incorrect.", 1, thread.getId());
        assertEquals("The thread is incorrect.", 1, thread.getId());
        assertEquals("The thread is incorrect.", 1, thread.getId());

        Set<Message> set = thread.getMessages();
        assertEquals("The thread is incorrect.", 2, set.size());

        for (Message m : set) {
            if ("message1".equals(m.getName())) {
                assertEquals("The message is incorrect.", 1, m.getId());
            }

            if ("message2".equals(m.getName())) {
                assertEquals("The message is incorrect.", 2, m.getId());
            }
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getAllThreads()}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetAllThreads() throws Exception {
        TestHelper.insertDB();

        List<Thread> list = instance.getAllThreads();
        assertEquals("The size is incorrect.", 3, list.size());

        for (Thread thread : list) {
            if (thread.getId() == 1) {
                assertNotNull("The thread should not be null.", thread);
                assertEquals("The thread is incorrect.", "userKey1", thread
                        .getUserKey());

                assertEquals("The thread is incorrect.", 1, thread.getId());
                assertEquals("The thread is incorrect.", 1, thread.getId());
                assertEquals("The thread is incorrect.", 1, thread.getId());

                Set<Message> set = thread.getMessages();
                assertEquals("The thread is incorrect.", 2, set.size());

                for (Message m : set) {
                    if ("message1".equals(m.getName())) {
                        assertEquals("The message is incorrect.", 1, m.getId());
                    }

                    if ("message2".equals(m.getName())) {
                        assertEquals("The message is incorrect.", 2, m.getId());
                    }
                }
            }
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(long, int, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_1() throws Exception {
        TestHelper.insertDB();

        int from = TestHelper.getRowidFrom(2);
        List<Message> list = instance.getMessages(2, from, 2);
        assertEquals("The size is incorrect.", 2, list.size());

        for (Message m : list) {
            if ("message3".equals(m.getName())) {
                assertEquals("The message is incorrect.", 3, m.getId());
            } else {
                assertEquals("The message is incorrect.", 4, m.getId());
            }
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(long, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_2() throws Exception {
        TestHelper.insertDB();

        List<Message> list = instance.getMessages(2);
        assertEquals("The size is incorrect.", 2, list.size());

        for (Message m : list) {
            if ("message3".equals(m.getName())) {
                assertEquals("The message is incorrect.", 3, m.getId());
            } else {
                assertEquals("The message is incorrect.", 4, m.getId());
            }
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(String, int, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_3() throws Exception {
        TestHelper.insertDB();

        int from = TestHelper.getRowidFrom(2);
        List<Message> list = instance.getMessages("userKey2", from, 2);
        assertEquals("The size is incorrect.", 2, list.size());

        for (Message m : list) {
            if ("message3".equals(m.getName())) {
                assertEquals("The message is incorrect.", 3, m.getId());
            } else {
                assertEquals("The message is incorrect.", 4, m.getId());
            }
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#getMessages(String, int)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessages_4() throws Exception {
        TestHelper.insertDB();

        List<Message> list = instance.getMessages("userKey2");
        assertEquals("The size is incorrect.", 2, list.size());

        for (Message m : list) {
            if ("message3".equals(m.getName())) {
                assertEquals("The message is incorrect.", 3, m.getId());
            } else {
                assertEquals("The message is incorrect.", 4, m.getId());
            }
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#updateMessage(Message)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testUpdateMessage() throws Exception {
        TestHelper.insertDB();
        Message message = instance.getMessages(1).get(0);
        message.setName("new name");
        instance.updateMessage(message);
        Message result = instance.getMessages(1).get(0);
        assertEquals("The name is incorrect.", result.getName(), "new name");
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#removeMessage(long)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testRemoveMessage() throws Exception {
        TestHelper.insertDB();
        instance.removeMessage(1);
        List<Message> list = instance.getMessages(1);
        assertEquals("The size is incorrect.", 1, list.size());
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#removeAllMessages(long)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testRemoveAllMessages() throws Exception {
        TestHelper.insertDB();
        instance.removeAllMessages(1);
        List<Message> list = instance.getMessages(1);
        assertEquals("The size is incorrect.", 0, list.size());
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#postMessage(long, Message)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testPostMessage() throws Exception {
        Thread thread = new Thread();
        String userKey = "userKey1";
        thread.setUserKey(userKey);

        Message message1 = new Message("message1", new Date(), "some message");
        Message message2 = new Message("message2", new Date(), "some message");
        Response response1 = new Response("response1", new Date(),
                "some message");
        Response response2 = new Response("response2", new Date(),
                "some message");
        message1.setResponse(response1);
        message2.setResponse(response2);
        thread.addMessage(message1);
        thread.addMessage(message2);

        Thread result = instance.createThread(thread);
        Message message3 = new Message("new post message", new Date(),
                "some message");
        instance.postMessage(result.getId(), message3);
        result = instance.getThread(result.getId());
        assertTrue("it should be posted.", message3.getId() > 0);
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#postMessage(long, Message)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testPostResponse() throws Exception {
        TestHelper.insertDB();
        Response response = new Response("new post response", new Date(),
                "some message");
        instance.postResponse(3, response);
        assertTrue("it should be posted.", response.getId() > 0);
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#removeResponse(long)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testRemoveResponse() throws Exception {
        TestHelper.insertDB();
        Response response = new Response("new post response", new Date(),
                "some message");
        instance.postResponse(3, response);
        assertTrue("it should be posted.", response.getId() > 0);
        instance.removeResponse(3);
        List<Message> list = instance.getMessages(3);
        for (Message m : list) {
            if (m.getId() == 3) {
                assertNull("it should be null.", m.getResponse());
            }
        }
    }

    /**
     * test the method {@link InformixMessageBoardPersistence#updateResponse(Response)}.
     * 
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testupdateResponse() throws Exception {
        TestHelper.insertDB();
        Response response = new Response("new post response", new Date(),
                "some message");
        instance.postResponse(3, response);
        assertTrue("it should be posted.", response.getId() > 0);
        response.setName("response updated.");
        instance.updateResponse(response);
        List<Message> list = instance.getMessages(3);
        for (Message m : list) {
            if (m.getId() == 3) {
                assertEquals("The name is incorrect.", "response updated.", m
                        .getResponse().getName());
            }
        }
    }
}
