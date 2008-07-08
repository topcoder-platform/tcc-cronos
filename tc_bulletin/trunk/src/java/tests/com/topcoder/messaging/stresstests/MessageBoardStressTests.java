/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging.stresstests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.XMLFilePersistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.messaging.Message;
import com.topcoder.messaging.MessageAttribute;
import com.topcoder.messaging.MessageBoard;
import com.topcoder.messaging.Response;
import com.topcoder.messaging.Thread;
import com.topcoder.messaging.persistence.InformixMessageBoardPersistence;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.List;


/**
 * The stress test for this component. because the MessageBoard just wrapper the persistence class.
 * So we test the persistence via MessageBoard.
 *
 * @author KLW
 * @version 2.0
 */
public class MessageBoardStressTests extends TestCase {
    private static final int MAX = 10;

    /**
     * The message board for test.
     */
    private MessageBoard messageBoard;

    /**
     * set up the test environment.
     *
     * @throws Exception
     *             any exception throw to JUnit.
     */
    public void setUp() throws Exception {
        // build the XMLFilePersistence
        XMLFilePersistence xmlFilePersistence = new XMLFilePersistence();

        // load the ConfigurationObject from the input file
        ConfigurationObject cfgObject = xmlFilePersistence.loadFile("test", new File("test_files/MessageBoard.xml"));
        cfgObject = cfgObject.getChild("MessageBoard");
        cfgObject.setPropertyValue("max_message_size", 1000);
        cfgObject.setPropertyValue("message_count", 200);
        messageBoard = new MessageBoard(cfgObject);
    }

    /**
     * tear down the test environment.
     *
     * @throws Exception
     *             any exception throw to JUnit.
     */
    public void tearDown() throws Exception {
        messageBoard = null;
        clear();
    }

    /**
     * test the method
     * {@link InformixMessageBoardPersistence#createThread(com.topcoder.messaging.Thread)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testCreateThread() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            Thread thread = new Thread();
            String userKey = "userKey" + i;
            thread.setUserKey(userKey);

            Message message1 = new Message("message" + i, new Date(), "some message");
            Message message2 = new Message("message" + (i + 1), new Date(), "some message");
            Response response1 = new Response("response" + i, new Date(), "some message");
            Response response2 = new Response("response" + (i + 1), new Date(), "some message");
            MessageAttribute attr1 = new MessageAttribute("attribute" + i, "value" + i);
            MessageAttribute attr2 = new MessageAttribute("attribute" + (i + 1), "value" + (i + 1));
            message1.addAttribute(attr1);
            message2.addAttribute(attr2);
            message1.setResponse(response1);
            message2.setResponse(response2);
            thread.addMessage(message1);
            thread.addMessage(message2);
            messageBoard.createThread(thread);
            System.out.println("create a new thread with id=" + thread.getId());
        }

        System.out.println("Call createThread for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
            " millis");
    }

    /**
     * <p>
     * Execute the sql script against the database.
     * </p>
     *
     * @param filename
     *            The file name.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void executeScriptFile(String filename)
        throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader bufferedReader = null;

        try {
            ConfigurationFileManager cfm = new ConfigurationFileManager("test_files/config.properties");
            ConfigurationObject config = cfm.getConfiguration("InformixMessageBoardPersistence");
            config = config.getChild("InformixMessageBoardPersistence");

            DBConnectionFactory factory = new DBConnectionFactoryImpl(config);
            conn = factory.createConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String sql = null;
            String path = new File(filename).getCanonicalPath();
            bufferedReader = new BufferedReader(new FileReader(path));

            while (null != (sql = bufferedReader.readLine())) {
                if (sql.trim().length() > 0) {
                    stmt.execute(sql);
                }
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();

            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }

            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
    }

    /**
     * insert the data for test.
     *
     * @throws Exception
     *             any exception throw to JUNit.
     */
    private void insert() throws Exception {
        executeScriptFile("test_files/clean.sql");
        executeScriptFile("test_files/insert.sql");

        // set the message of the message and responses.
        ConfigurationFileManager cfm = new ConfigurationFileManager("test_files/config.properties");
        ConfigurationObject config = cfm.getConfiguration("InformixMessageBoardPersistence");
        config = config.getChild("InformixMessageBoardPersistence");

        DBConnectionFactory factory = new DBConnectionFactoryImpl(config);
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = factory.createConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update messages set message=?");
            pstmt.setString(1, "some message");
            pstmt.execute();
            pstmt = conn.prepareStatement("update responses set message=?");
            pstmt.setString(1, "some message");
            pstmt.execute();
            conn.commit();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * clear the database.
     *
     * @throws Exception
     *             any exception throw to JUNit.
     */
    private void clear() throws Exception {
        executeScriptFile("test_files/clean.sql");
    }
    /**
     * test the method
     * {@link InformixMessageBoardPersistence#updateThread(Thread)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testUpdateThread() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            Thread thread = messageBoard.getThread(1);
            //remove a message
            thread.removeMessage(1);
            //add a new one.
            Message message = new Message("message" + i, new Date(), "some message");
            thread.addMessage(message);
            messageBoard.updateThread(thread);
            System.out.println("update the thread with id="+thread.getId());
        }
        System.out.println("Call updateThread for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test the method {@link InformixMessageBoardPersistence#deleteThread(long)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testDeleteThread() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            Thread thread = new Thread();
            String userKey = "userKey" + i;
            thread.setUserKey(userKey);

            Message message1 = new Message("message" + i, new Date(), "some message");
            Message message2 = new Message("message" + (i + 1), new Date(), "some message");
            Response response1 = new Response("response" + i, new Date(), "some message");
            Response response2 = new Response("response" + (i + 1), new Date(), "some message");
            MessageAttribute attr1 = new MessageAttribute("attribute" + i, "value" + i);
            MessageAttribute attr2 = new MessageAttribute("attribute" + (i + 1), "value" + (i + 1));
            message1.addAttribute(attr1);
            message2.addAttribute(attr2);
            message1.setResponse(response1);
            message2.setResponse(response2);
            thread.addMessage(message1);
            thread.addMessage(message2);
            messageBoard.createThread(thread);
            messageBoard.deleteThread(thread.getId());
            System.out.println("delete the thread with id="+thread.getId());
        }
        System.out.println("Call deleteThread for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test the method {@link InformixMessageBoardPersistence#getThread(long)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetThread() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            messageBoard.getThread(1);
            messageBoard.getThread("userKey1");
        }
        System.out.println("Call GetThread for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test the method {@link InformixMessageBoardPersistence#getThread(long)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetAllThreads() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            messageBoard.getAllThreads();
        }
        System.out.println("Call getAllThreads for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test for get message.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testGetMessage() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            messageBoard.getMessages(1);
            messageBoard.getMessages("userKey2");
            messageBoard.getMessages(2, 1, 50);
            messageBoard.getMessages("userKey2", 1, 50);
        }
        System.out.println("Call GetThread for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test the method
     * {@link InformixMessageBoardPersistence#updateMessage(Message)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testUpdateMessage() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            List<Message> list = messageBoard.getMessages(2);
            for(Message message : list){
                message.setDate(new Date());
                message.setName("new name");
                messageBoard.updateMessage(message);
                System.out.println("update the message with id="+message.getId());
            }
        }
        System.out.println("Call updateMessage for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test the method
     * {@link InformixMessageBoardPersistence#removeMessage(long)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testRemoveMessage() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            Thread thread = messageBoard.getThread(1);
            //add a new one.
            Message message = new Message("message" + i, new Date(), "some message");
            thread.addMessage(message);
            messageBoard.updateThread(thread);
            messageBoard.removeMessage(message.getId());
            System.out.println("remove the message with id="+message.getId());
        }
        System.out.println("Call removeMessage for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test the method
     * {@link InformixMessageBoardPersistence#removeAllMessage(long)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testRemoveAllMessage() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            Thread thread = messageBoard.getThread(1);
            messageBoard.removeAllMessages(thread.getId());
            System.out.println("remove all the messages with thread id="+thread.getId());
            //add a new one.
            Message message = new Message("message" + i, new Date(), "some message");
            thread = messageBoard.getThread(1);
            thread.addMessage(message);
            messageBoard.updateThread(thread);
        }
        System.out.println("Call removeAllMessage for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test the method
     * {@link InformixMessageBoardPersistence#postMessage(long, Message)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testPostMessage() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            Message message = new Message("message" + i, new Date(), "some message");
            messageBoard.postMessage(1, message);
            System.out.println("post a message with id="+message.getId());
        }
        System.out.println("Call postMessage for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test the method
     * {@link InformixMessageBoardPersistence#postResponse(long, Response)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testPostResponse() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            Response response = new Response("new post response", new Date(), "some message");
            messageBoard.postResponse(1, response);
            System.out.println("post a reponse with id="+response.getId());
        }
        System.out.println("Call postResponse for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test the method
     * {@link InformixMessageBoardPersistence#removeResponse(long)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testRemoveResponse() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            Response response = new Response("new post response", new Date(), "some message");
            messageBoard.postResponse(1, response);
            messageBoard.removeResponse(1);
            System.out.println("remove a reponse with id="+response.getId());
        }
        System.out.println("Call removeResponse for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
    /**
     * test the method
     * {@link InformixMessageBoardPersistence#updateResponse(Response)}.
     *
     * @throws Exception
     *             All exception throw to Junit.
     */
    public void testUpdaeResponse() throws Exception {
        insert();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            Response response = new Response("new post response", new Date(), "some message");
            messageBoard.postResponse(1, response);
            response.setMessage("tests");
            messageBoard.updateResponse(response);
            System.out.println("update a reponse with id="+response.getId());
        }
        System.out.println("Call updateResponse for " + MAX + " times, cost : " + (System.currentTimeMillis() - start) +
        " millis");
    }
}
