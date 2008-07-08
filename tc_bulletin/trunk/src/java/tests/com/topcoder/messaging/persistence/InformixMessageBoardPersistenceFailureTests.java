/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging.persistence;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.messaging.EntityNotFoundException;
import com.topcoder.messaging.Message;
import com.topcoder.messaging.Response;
import com.topcoder.messaging.TestHelper;
import com.topcoder.messaging.Thread;

import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

import java.util.Date;


/**
 * The failure test for the class {@link InformixMessageBoardPersistence}.
 *
 * @author yqw
 * @version 2.0
 */
public class InformixMessageBoardPersistenceFailureTests extends TestCase {
    /**
     * The InformixMessageBoardPersistence insatnce for test.
     */
    private InformixMessageBoardPersistence instance;

    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    protected void setUp() throws Exception {
        instance = new InformixMessageBoardPersistence("test_files/config.properties", "InformixMessageBoardPersistence");
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
     * {@link InformixMessageBoardPersistence#InformixMessageBoardPersistence(DBConnectionFactory,
     * String, com.topcoder.util.idgenerator.IDGenerator)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_1_Factory_Null() throws Exception {
        try {
            DBConnectionFactory factory = null;
            IDGenerator iDGenerator = IDGeneratorFactory.getIDGenerator("tcbulletin");
            String connectionName = "tcbulletin";
            new InformixMessageBoardPersistence(factory, connectionName, iDGenerator);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#InformixMessageBoardPersistence(DBConnectionFactory,
     * String, com.topcoder.util.idgenerator.IDGenerator)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_1_connectionName_Null() throws Exception {
        try {
            ConfigurationFileManager cfm = new ConfigurationFileManager("test_files/config.properties");
            ConfigurationObject config = cfm.getConfiguration("InformixMessageBoardPersistence");
            config = config.getChild("InformixMessageBoardPersistence");

            DBConnectionFactory factory = new DBConnectionFactoryImpl(config);
            IDGenerator iDGenerator = IDGeneratorFactory.getIDGenerator("tcbulletin");
            String connectionName = null;
            new InformixMessageBoardPersistence(factory, connectionName, iDGenerator);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#InformixMessageBoardPersistence(DBConnectionFactory,
     * String, com.topcoder.util.idgenerator.IDGenerator)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_1_connectionName_Empty() throws Exception {
        try {
            ConfigurationFileManager cfm = new ConfigurationFileManager("test_files/config.properties");
            ConfigurationObject config = cfm.getConfiguration("InformixMessageBoardPersistence");
            config = config.getChild("InformixMessageBoardPersistence");

            DBConnectionFactory factory = new DBConnectionFactoryImpl(config);
            IDGenerator iDGenerator = IDGeneratorFactory.getIDGenerator("tcbulletin");
            String connectionName = "   ";
            new InformixMessageBoardPersistence(factory, connectionName, iDGenerator);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#InformixMessageBoardPersistence(DBConnectionFactory,
     * String, com.topcoder.util.idgenerator.IDGenerator)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_1_iDGenerator_Null() throws Exception {
        try {
            ConfigurationFileManager cfm = new ConfigurationFileManager("test_files/config.properties");
            ConfigurationObject config = cfm.getConfiguration("InformixMessageBoardPersistence");
            config = config.getChild("InformixMessageBoardPersistence");

            DBConnectionFactory factory = new DBConnectionFactoryImpl(config);
            IDGenerator iDGenerator = null;
            String connectionName = "tcbulletin";
            new InformixMessageBoardPersistence(factory, connectionName, iDGenerator);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#InformixMessageBoardPersistence(String, String)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_2_file_null() throws Exception {
        try {
            String file = null;
            String namespace = "InformixMessageBoardPersistence";
            new InformixMessageBoardPersistence(file, namespace);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#InformixMessageBoardPersistence(String, String)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_2_file_empty() throws Exception {
        try {
            String file = "   ";
            String namespace = "InformixMessageBoardPersistence";
            new InformixMessageBoardPersistence(file, namespace);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#InformixMessageBoardPersistence(String, String)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_2_namespace_null() throws Exception {
        try {
            String file = "test_files/config.properties";
            String namespace = null;
            new InformixMessageBoardPersistence(file, namespace);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#InformixMessageBoardPersistence(String, String)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCtor_2_namespace_empty() throws Exception {
        try {
            String file = "test_files/config.properties";
            String namespace = "  ";
            new InformixMessageBoardPersistence(file, namespace);
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

            Message message1 = new Message("message1", new Date(), "some message");
            // set the id. it will cause error.
            message1.setId(200);

            Message message2 = new Message("message2", new Date(), "some message");
            Response response1 = new Response("response1", new Date(), "some message");
            Response response2 = new Response("response2", new Date(), "some message");
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
            instance.getMessages(-2, 2);
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
    public void testGetMessages_2_count() throws Exception {
        try {
            instance.getMessages(2, -2);
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
            TestHelper.insertDB();

            int from = TestHelper.getRowidFrom(2);
            instance.getMessages("  ", from, 2);
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
            instance.getMessages(null, 2);
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
    public void testGetMessages_4_count() throws Exception {
        try {
            instance.getMessages("userKey", -22);
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
            Message message3 = new Message("new post message", new Date(), "some message");
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
            Response response = new Response("new post response", new Date(), "some message");
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
