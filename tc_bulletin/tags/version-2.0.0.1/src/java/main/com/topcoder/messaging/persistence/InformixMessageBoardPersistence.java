/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging.persistence;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;

import com.topcoder.messaging.EntityNotFoundException;
import com.topcoder.messaging.ErrorMessagesCache;
import com.topcoder.messaging.Helper;
import com.topcoder.messaging.Message;
import com.topcoder.messaging.MessageAttribute;
import com.topcoder.messaging.MessageBoardConfigurationException;
import com.topcoder.messaging.MessageBoardPersistence;
import com.topcoder.messaging.MessageBoardPersistenceException;
import com.topcoder.messaging.Response;
import com.topcoder.messaging.Thread;

import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import java.io.IOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This class implements MessageBoardPersistence interface and uses Informix 7.3 as persistent
 * storage. For id generation it uses ID Generator component. To get a number of rows starting from
 * a specified index this class uses row id function of Informix. To obtain a connection to the
 * database, this class uses Connection Factory component. For the methods that modify the database,
 * transactions are used. The structure of the database is provided in tc_bulletin.ddl file.
 * </p>
 * <p>
 * Thread safe: This class can be considered as thread safe since DBConnectionFactory
 * implementations and IDGenerator implementations are expected to be thread safe and transactions
 * are employed for methods that modify the database. Concurrency issues may appear if the database
 * is accessed outside of instances of this class.
 * </p>
 * 
 * @author DanLazar, yqw
 * @version 2.0
 */
public class InformixMessageBoardPersistence implements MessageBoardPersistence {
    /**
     * The sql for inserting the thread to the database.
     */
    private static final String INSERT_THREAD = "insert into threads(id, user_key) values (?,?)";

    /**
     * The sql command : delete the thread by thread id.
     */
    private static final String DELETE_THREAD_BY_THREAD_ID = "delete from threads where id = ? ";

    /**
     * The sql command : select user key by thread id.
     */
    private static final String SELECT_USER_KEY_BY_THREAD_ID = "select user_key from threads where id = ? ";

    /**
     * The sql command : select all thread id from table threads.
     */
    private static final String SELECT_THREAD_ID_FROM_THREADS = "select id from threads";

    /**
     * The sql command : select thread id by user key.
     */
    private static final String SELECT_THREAD_ID_BY_USER_KEY = "select id from threads where user_key = ?";

    /**
     * The sql command : select thread by thread id.
     */
    private static final String SELECT_THREAD_BY_THREAD_ID = "select * from threads where id = ?";

    /**
     * The sql for inserting the message to the database.
     */
    private static final String INSERT_MESSAGE = "insert into messages(id, name,date,message,thread_id) values (?,?,?,?,?)";

    /**
     * The sql command : count the message number by thread id.
     */
    private static final String COUNT_MESSAGE_BY_THREAD_ID = "select count(id) from messages where thread_id = ? ";

    /**
     * The sql command : select message id by thread id.
     */
    private static final String SELECT_MESSAGE_ID_BY_THREAD_ID = "select id from messages where thread_id = ? ";

    /**
     * The sql command : select message by thread id and row id.
     */
    private static final String SELECT_MESSAGE_BY_THREAD_ID_AND_ROWID = "select * from messages where thread_id=? and rowid >= ?";

    /**
     * The sql command : select message by message id.
     */
    private static final String SELECT_MESSAGE_BY_MESSAGE_ID = "select * from messages where id = ?";

    /**
     * The sql command : select message by thread id.
     */
    private static final String SELECT_MESSAGE_BY_THREAD_ID = "select * from messages where thread_id=?";

    /**
     * The sql command : update the message
     */
    private static final String UPDATE_MESSAGE = "update messages set name = ?,date=?,message=? where id=?";

    /**
     * The sql command : delete the message by message id.
     */
    private static final String DELETE_MESSAGE_BY_MESSAGE_ID = "delete from messages where id=?";

    /**
     * The sql command : delete the message by thread id
     */
    private static final String DELETE_MESSAGE_BY_THREAD_ID = "delete from messages where thread_id=?";

    /**
     * The sql for inserting the attribute to the database.
     */
    private static final String INSERT_ATTRIBUTE = "insert into attributes(message_id, name,value) values (?,?,?)";

    /**
     * The sql command : select attribute by message id.
     */
    private static final String SELECT_ATTRIBUTE_BY_MESSAGE_ID = "select * from attributes where message_id=?";

    /**
     * The sql command : delete the attribute by message id.
     */
    private static final String DELETE_ATTRIBUTE_BY_MESSAGE_ID = "delete from attributes where message_id=?";

    /**
     * The sql command : delete the attribute by thread id.
     */
    private static final String DELETE_ATTRIBUTE_BY_THREAD_ID = "delete from attributes where message_id in ("
            + "select id from messages where messages.thread_id = ?)";

    /**
     * The sql for inserting the message to the database.
     */
    private static final String INSERT_RESPONSE = "insert into responses(id, name,date,message,message_id) values (?,?,?,?,?)";

    /**
     * The sql command : update the response.
     */
    private static final String UPDATE_RESPONSE = "update responses set name = ?,date=?,message=? where id=?";

    /**
     * The sql command : select responses by message id.
     */
    private static final String SELECT_RESPONSE_BY_MESSAGE_ID = "select * from responses where message_id=?";

    /**
     * The sql command : select responses by response id.
     */
    private static final String SELECT_RESPONSE_BY_RESPONSE_ID = "select * from responses where id = ?";

    /**
     * The sql command : delete the response by message id.
     */
    private static final String DELETE_RESPONSE_BY_MESSAGE_ID = "delete from responses where message_id=?";

    /**
     * The sql command : delete the response by thread id.
     */
    private static final String DELETE_RESPONSE_BY_THREAD_ID = "delete from responses where message_id in ("
            + "select message_id from messages where messages.thread_id = ?)";

    /**
     * the connection_name key in the configuration.
     */
    private static final String CONNECTIONNAMEKEY = "connection_name";

    /**
     * the id_generator_name key in the configuration.
     */
    private static final String IDGNAMEKEY = "id_generator_name";

    /**
     * The factory used to create a connection to the database. Initialized in constructors and
     * never changed. It cannot be null after initialization. Used in getConnection method to
     * retrieve a connection.
     */
    private final DBConnectionFactory factory;

    /**
     * The connection name used with the factory to obtain a connection to the database. It cannot
     * be null or empty string after initialization. Initialized in constructors and never changed.
     * Used in getConnection to obtain a connection.
     */
    private final String connectionName;

    /**
     * This field is used to generate ids. It will be initialized in constructor and never changed.
     * It cannot be null after initialization. Used in some methods to generate an id for the entity
     * that will be persisted.
     */
    private final IDGenerator idGenerator;

    /**
     * Creates a new instance. Simply set the fields.
     * 
     * @param factory
     *            the connection factory
     * @param connectionName
     *            the connection name
     * @param idGenerator
     *            idGenerator
     * 
     * @throws IllegalArgumentException
     *             if any arg is null or if connectionName is empty string
     */
    public InformixMessageBoardPersistence(DBConnectionFactory factory,
            String connectionName, IDGenerator idGenerator) {
        Helper.checkNull(factory, "factory");
        Helper.checkNullOrEmpty(connectionName, "connectionName");
        Helper.checkNull(idGenerator, "idGenerator");
        this.factory = factory;
        this.connectionName = connectionName;
        this.idGenerator = idGenerator;
    }

    /**
     * Creates a new instance. It will get the configuration from file and then initialize the
     * fields.
     * 
     * @param file
     *            the file
     * @param namespace
     *            the namespace
     * 
     * @throws IllegalArgumentException
     *             if any arg is null or empty string
     * @throws MessageBoardConfigurationException
     *             if cannot create the file manager or extract the configuration from it,or if if
     *             errors occur when reading the configured properties or if any required property
     *             is missing.
     */
    public InformixMessageBoardPersistence(String file, String namespace)
            throws MessageBoardConfigurationException {
        Helper.checkNullOrEmpty(file, "file");
        Helper.checkNullOrEmpty(namespace, "namespace");

        try {
            ConfigurationFileManager cfm = new ConfigurationFileManager(file);

            // get configuration
            ConfigurationObject config = cfm.getConfiguration(namespace);
            config = config.getChild(namespace);
            // get connection name String
            connectionName = Helper.getStringProperty(CONNECTIONNAMEKEY,
                    config, true);
            // create a DBConnectionFactoryImpl and set the field
            factory = new DBConnectionFactoryImpl(config);

            // get id_generator_name and initialize the idGenerator field
            String idGeneratorName = Helper.getStringProperty(IDGNAMEKEY,
                    config, true);
            idGenerator = IDGeneratorFactory.getIDGenerator(idGeneratorName);
        } catch (ConfigurationParserException e) {
            throw new MessageBoardConfigurationException(Helper
                    .getErrorMessage(ErrorMessagesCache.CONFIGURATION, e
                            .getMessage()), e);
        } catch (NamespaceConflictException e) {
            throw new MessageBoardConfigurationException(Helper
                    .getErrorMessage(ErrorMessagesCache.CONFIGURATION, e
                            .getMessage()), e);
        } catch (UnrecognizedFileTypeException e) {
            throw new MessageBoardConfigurationException(Helper
                    .getErrorMessage(ErrorMessagesCache.CONFIGURATION, e
                            .getMessage()), e);
        } catch (IOException e) {
            throw new MessageBoardConfigurationException(Helper
                    .getErrorMessage(ErrorMessagesCache.CONFIGURATION, e
                            .getMessage()), e);
        } catch (UnrecognizedNamespaceException e) {
            throw new MessageBoardConfigurationException(Helper
                    .getErrorMessage(ErrorMessagesCache.CONFIGURATION, e
                            .getMessage()), e);
        } catch (ConfigurationAccessException e) {
            throw new MessageBoardConfigurationException(Helper
                    .getErrorMessage(ErrorMessagesCache.CONFIGURATION, e
                            .getMessage()), e);
        } catch (IDGenerationException e) {
            throw new MessageBoardConfigurationException(Helper
                    .getErrorMessage(ErrorMessagesCache.CONFIGURATION, e
                            .getMessage()), e);
        } catch (UnknownConnectionException e) {
            throw new MessageBoardConfigurationException(Helper
                    .getErrorMessage(ErrorMessagesCache.CONFIGURATION, e
                            .getMessage()), e);
        } catch (ConfigurationException e) {
            throw new MessageBoardConfigurationException(Helper
                    .getErrorMessage(ErrorMessagesCache.CONFIGURATION, e
                            .getMessage()), e);
        }
    }

    /**
     * Creates a new thread in persistence.
     * 
     * @param thread
     *            the thread to be created
     * @return the thread with its id set
     * @throws IllegalArgumentException
     *             if arg is null or if its id >=0 or if any message id >=0 or if any response id >
     *             0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     */
    public Thread createThread(Thread thread)
            throws MessageBoardPersistenceException {
        // check the thread
        Helper.checkNull(thread, "thread");

        if (thread.getId() >= 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The thread's id is >= 0. id=" + thread.getId()));
        }

        Set<Message> set = thread.getMessages();

        for (Message message : set) {
            if (message.getId() >= 0) {
                throw new IllegalArgumentException(Helper.getErrorMessage(
                        ErrorMessagesCache.INVALID_ARGUMENT,
                        "The message id >= 0. message id = " + message.getId()));
            }

            Response response = message.getResponse();

            if ((response != null) && (response.getId() > 0)) {
                throw new IllegalArgumentException(Helper.getErrorMessage(
                        ErrorMessagesCache.INVALID_ARGUMENT,
                        "The response id > 0. message id = "
                                + message.getResponse().getId()));
            }
        }

        // get connection
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            // start transaction
            conn.setAutoCommit(false);

            // generate an id
            long id = idGenerator.getNextID();
            thread.setId(id);
            // insert into the thread table.
            pstmt = conn.prepareStatement(INSERT_THREAD);
            pstmt.setLong(1, id);
            pstmt.setString(2, thread.getUserKey());
            pstmt.execute();

            // insert a row into messages table and set the message id
            for (Message message : set) {
                long messageId = idGenerator.getNextID();
                message.setId(messageId);
                pstmt = conn.prepareStatement(INSERT_MESSAGE);
                insertMessage(pstmt, message, id);

                // insert the attributes.
                Map<String, MessageAttribute> map = message.getAllAttributes();

                for (String name : map.keySet()) {
                    MessageAttribute attr = map.get(name);
                    pstmt = conn.prepareStatement(INSERT_ATTRIBUTE);
                    insertAttribute(pstmt, attr, messageId);
                }

                // insert the response
                Response response = message.getResponse();

                if (response != null) {
                    long responseID = idGenerator.getNextID();
                    response.setId(responseID);
                    pstmt = conn.prepareStatement(INSERT_RESPONSE);
                    insertResponse(pstmt, response, messageId);
                }
            }

            conn.commit();

            return thread;
        } catch (SQLException e) {
            throw rethrowSQLException(e, conn, true);
        } catch (IDGenerationException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * insert the message into table.
     * 
     * @param pstmt
     *            the connection to the database.
     * @param message
     *            the message to insert.
     * @param threadId
     *            thread id.
     * @throws SQLException
     *             if any error.
     */
    private static void insertMessage(PreparedStatement pstmt, Message message,
            long threadId) throws SQLException {
        int index = 1;
        pstmt.setLong(index++, message.getId());
        pstmt.setString(index++, message.getName());
        pstmt.setDate(index++, new Date(message.getDate().getTime()));
        pstmt.setString(index++, message.getMessage());

        pstmt.setLong(index, threadId);
        pstmt.execute();
    }

    /**
     * insert the attribute into table.
     * 
     * @param pstmt
     *            the connection to the database.
     * @param attr
     *            the MessageAttribute to insert.
     * @param messageId
     *            thread id.
     * @throws SQLException
     *             if any error.
     */
    private static void insertAttribute(PreparedStatement pstmt,
            MessageAttribute attr, long messageId) throws SQLException {
        int index = 1;
        pstmt.setLong(index++, messageId);
        pstmt.setString(index++, attr.getName());
        pstmt.setString(index, attr.getValue());
        pstmt.execute();
    }

    /**
     * insert the attribute into table.
     * 
     * @param pstmt
     *            the connection to the database.
     * @param response
     *            the response to insert.
     * @param messageId
     *            thread id.
     * @throws SQLException
     *             if any error.
     */
    private static void insertResponse(PreparedStatement pstmt,
            Response response, long messageId) throws SQLException {
        int index = 1;
        pstmt.setLong(index++, response.getId());
        pstmt.setString(index++, response.getName());
        pstmt.setDate(index++, new Date(response.getDate().getTime()));
        pstmt.setString(index++, response.getMessage());
        pstmt.setLong(index, messageId);
        pstmt.execute();
    }

    /**
     * Updates a thread in persistence.
     * 
     * @param thread
     *            the thread to be updated
     * 
     * @throws IllegalArgumentException
     *             if arg is null or if its id < 0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if the given entity does not exist
     */
    public void updateThread(Thread thread)
            throws MessageBoardPersistenceException, EntityNotFoundException {
        // check the thread
        Helper.checkNull(thread, "thread");

        if (thread.getId() < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The thread's id is < 0. id=" + thread.getId()));
        }

        long threadId = thread.getId();

        // get connection
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            // start transaction
            conn.setAutoCommit(false);

            Set<Message> set = thread.getMessages();

            for (Message message : set) {
                long messageId = message.getId();

                if (-1 == messageId) {
                    // post the message
                    innerPostMessage(threadId, message, conn);
                }

                if (messageId >= 0) {
                    innerUpdateMessage(message, conn);
                }
            }

            // if messages table contains messages associated with the thread, and these messages
            // ids are not among
            // the ids of thread.messages then for those messages ids call this.removeMessage method
            List<Message> list = innerGetMessages(threadId, Integer.MAX_VALUE,
                    conn);

            for (Message messageInDB : list) {
                boolean remove = true;

                for (Message messageInThread : set) {
                    if (messageInThread.getId() == messageInDB.getId()) {
                        remove = false;

                        break;
                    }
                }

                if (remove) {
                    innerRemoveMessage(messageInDB.getId(), conn);
                }
            }

            conn.commit();
        } catch (SQLException e) {
            throw rethrowSQLException(e, conn, true);
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * Deletes a thread from persistence.
     * 
     * @param threadID
     *            the thread id
     * 
     * @throws IllegalArgumentException
     *             if id < 0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no thread with the given id in persistence
     */
    public void deleteThread(long threadID) throws EntityNotFoundException,
            MessageBoardPersistenceException {
        if (threadID < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The thread's id is < 0. id=" + threadID));
        }

        // get connection
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            // check the thread.
            if (!checkThreadId(threadID, conn)) {
                throw new EntityNotFoundException(Helper.getErrorMessage(
                        ErrorMessagesCache.ENTITY_NOT_FOUND,
                        "The thread with id=" + threadID + " not found."));
            }

            // start transaction
            conn.setAutoCommit(false);
            // get all message ids from messages table for the given thread id
            // determine the number of messages that the thread has
            pstmt = conn.prepareStatement(SELECT_MESSAGE_ID_BY_THREAD_ID);
            pstmt.setLong(1, threadID);
            rs = pstmt.executeQuery();

            List<Long> ids = new ArrayList<Long>();

            while (rs.next()) {
                // we can not delete message in this loop by using this connection.
                // so we get the id first.
                ids.add(rs.getLong(1));
            }

            for (long messageId : ids) {
                innerRemoveMessage(messageId, conn);
            }

            // delete the corresponding row from the threads table
            pstmt = conn.prepareStatement(DELETE_THREAD_BY_THREAD_ID);
            pstmt.setLong(1, threadID);
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw rethrowSQLException(e, conn, true);
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * Gets the thread with the given id from persistence.
     * 
     * @param threadID
     *            the thread id
     * @return the Thread identified by id
     * 
     * @throws IllegalArgumentException
     *             if id < 0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no thread with the given id in persistence
     */
    public Thread getThread(long threadID)
            throws MessageBoardPersistenceException, EntityNotFoundException {
        if (threadID < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The thread's id is < 0. id=" + threadID));
        }

        Connection conn = null;

        try {
            conn = this.getConnection();

            return innerGetThread(threadID, conn);
        } finally {
            close(conn, null, null);
        }
    }

    /**
     * the inner method for get the thread. using a connection, we will not close this after this
     * method.
     * 
     * @param threadID
     *            the thread id
     * @param conn
     *            the connection to the database.
     * @return the Thread identified by id
     * 
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no thread with the given id in persistence
     */
    private Thread innerGetThread(long threadID, Connection conn)
            throws MessageBoardPersistenceException, EntityNotFoundException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (!checkThreadId(threadID, conn)) {
                throw new EntityNotFoundException(Helper.getErrorMessage(
                        ErrorMessagesCache.ENTITY_NOT_FOUND,
                        "The thread with id=" + threadID + " not found."));
            }

            // determine the number of messages that the thread has
            pstmt = conn.prepareStatement(COUNT_MESSAGE_BY_THREAD_ID);
            pstmt.setLong(1, threadID);
            rs = pstmt.executeQuery();
            rs.next();

            int numberOfMessages = rs.getInt(1);
            List<Message> list = innerGetMessages(threadID, numberOfMessages,
                    conn);
            pstmt = conn.prepareStatement(SELECT_USER_KEY_BY_THREAD_ID);
            pstmt.setLong(1, threadID);
            rs = pstmt.executeQuery();
            rs.next();

            String userKey = rs.getString(1);

            // set the thread field.
            Thread thread = new Thread();
            thread.setId(threadID);
            thread.setUserKey(userKey);
            thread.setMessages(new HashSet<Message>(list));

            return thread;
        } catch (SQLException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } finally {
            close(null, pstmt, rs);
        }
    }

    /**
     * Gets the thread with the given user key from persistence.
     * 
     * @param userKey
     *            the user key
     * @return the thread identified by the user key
     * 
     * @throws IllegalArgumentException
     *             if userKey is null or empty string
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no thread with the given id in persistence
     */
    public Thread getThread(String userKey)
            throws MessageBoardPersistenceException, EntityNotFoundException {
        Helper.checkNullOrEmpty(userKey, "userKey");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            // check the thread.
            pstmt = conn.prepareStatement(SELECT_THREAD_ID_BY_USER_KEY);
            pstmt.setString(1, userKey);
            rs = pstmt.executeQuery();

            long threadID = -1;

            if (!rs.next()) {
                throw new EntityNotFoundException(Helper.getErrorMessage(
                        ErrorMessagesCache.ENTITY_NOT_FOUND,
                        "The thread with user_key=" + userKey + " not found."));
            } else {
                threadID = rs.getLong(1);
            }

            // determine the number of messages that the thread has
            pstmt = conn.prepareStatement(COUNT_MESSAGE_BY_THREAD_ID);
            pstmt.setLong(1, threadID);
            rs = pstmt.executeQuery();
            rs.next();

            int numberOfMessages = rs.getInt(1);
            List<Message> list = innerGetMessages(threadID, numberOfMessages,
                    conn);

            // set the thread field.
            Thread thread = new Thread();
            thread.setId(threadID);
            thread.setUserKey(userKey);
            thread.setMessages(new HashSet<Message>(list));

            return thread;
        } catch (SQLException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    /**
     * Gets all threads from persistence.
     * 
     * @return a list of threads or an empty list if there are no threads
     * 
     * @throws IllegalArgumentException
     *             if id < 0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     */
    public List<Thread> getAllThreads() throws MessageBoardPersistenceException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(SELECT_THREAD_ID_FROM_THREADS);
            rs = pstmt.executeQuery();

            List<Thread> list = new ArrayList<Thread>();

            while (rs.next()) {
                long threadId = rs.getLong(1);
                Thread thread = innerGetThread(threadId, conn);
                list.add(thread);
            }

            return list;
        } catch (SQLException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } catch (EntityNotFoundException e) {
            // it will not throw. not execute here.
            return null;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    /**
     * Gets messages from the thread indicated by thread id. Messages will be returned starting from
     * the thread with index = "from" argument; count indicates how many messages will be returned.
     * If the remaining messages are less than count then only those messages will be returned.
     * 
     * @param threadID
     *            thread id
     * @param count
     *            number of messages
     * 
     * @param from
     *            starting index
     * @return a list of messages or an empty list if there are no more messages from the given
     *         index
     * 
     * @throws IllegalArgumentException
     *             if id < 0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     */
    public List<Message> getMessages(long threadID, int from, int count)
            throws MessageBoardPersistenceException {
        if (threadID < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The thread's id is < 0. id=" + threadID));
        }

        if (count < 1) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The count is < 1. count=" + count));
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            conn = this.getConnection();

            // using thread id get the corresponding row from messages table
            pstmt = conn
                    .prepareStatement(SELECT_MESSAGE_BY_THREAD_ID_AND_ROWID);
            pstmt.setLong(1, threadID);
            pstmt.setInt(2, from);
            pstmt.setMaxRows(count);
            rs = pstmt.executeQuery();

            List<Message> list = new ArrayList<Message>();

            while (rs.next()) {
                Message message = getMessage(rs);
                // get the attributes.
                pstmt = conn.prepareStatement(SELECT_ATTRIBUTE_BY_MESSAGE_ID);
                pstmt.setLong(1, message.getId());

                rs2 = pstmt.executeQuery();

                Map<String, MessageAttribute> attributes = new HashMap<String, MessageAttribute>();

                while (rs2.next()) {
                    MessageAttribute attr = getAttribute(rs2);
                    attributes.put(attr.getName(), attr);
                }

                if (!attributes.isEmpty()) {
                    message.setAttributes(attributes);
                }

                // get the response
                pstmt = conn.prepareStatement(SELECT_RESPONSE_BY_MESSAGE_ID);
                pstmt.setLong(1, message.getId());
                rs2 = pstmt.executeQuery();

                if (rs2.next()) {
                    Response r = getResponse(rs2);
                    message.setResponse(r);
                }

                list.add(message);
            }

            return list;
        } catch (SQLException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } finally {
            close(null, null, rs2);
            close(conn, pstmt, rs);
        }
    }

    /**
     * Gets messages from the thread indicated by thread id. It will return only the first messages
     * indicated by count argument. If count is greater than the total number of messages of the
     * thread than all the messages of the thread will be returned.
     * 
     * @param threadID
     *            thread id
     * @param count
     *            number of messages to be returned
     * @return a list of messages or an empty list if there are no messages
     * 
     * @throws IllegalArgumentException
     *             if id < 0 or count < 1
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence.
     */
    public List<Message> getMessages(long threadID, int count)
            throws MessageBoardPersistenceException {
        if (threadID < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The thread's id is < 0. id=" + threadID));
        }

        if (count < 1) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The count is < 1. count=" + count));
        }

        Connection conn = null;

        try {
            conn = this.getConnection();

            return innerGetMessages(threadID, count, conn);
        } finally {
            close(conn, null, null);
        }
    }

    /**
     * inner method for getting all the messages by using the conn.
     * 
     * @param threadID
     *            thread id
     * @param count
     *            number of messages to be returned
     * @return a list of messages or an empty list if there are no messages
     * 
     * @throws IllegalArgumentException
     *             if id < 0 or count < 1
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence.
     */
    private List<Message> innerGetMessages(long threadID, int count,
            Connection conn) throws MessageBoardPersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            // using thread id get the corresponding row from messages table
            pstmt = conn.prepareStatement(SELECT_MESSAGE_BY_THREAD_ID);
            pstmt.setLong(1, threadID);
            pstmt.setMaxRows(count);
            rs = pstmt.executeQuery();

            List<Message> list = new ArrayList<Message>();

            while (rs.next()) {
                Message message = getMessage(rs);

                // get the attributes.
                pstmt = conn.prepareStatement(SELECT_ATTRIBUTE_BY_MESSAGE_ID);
                pstmt.setLong(1, message.getId());

                rs2 = pstmt.executeQuery();

                Map<String, MessageAttribute> attributes = new HashMap<String, MessageAttribute>();

                while (rs2.next()) {
                    MessageAttribute attr = getAttribute(rs2);
                    attributes.put(attr.getName(), attr);
                }

                if (!attributes.isEmpty()) {
                    message.setAttributes(attributes);
                }

                // get the response
                pstmt = conn.prepareStatement(SELECT_RESPONSE_BY_MESSAGE_ID);
                pstmt.setLong(1, message.getId());
                rs2 = pstmt.executeQuery();

                // if it has response
                if (rs2.next()) {
                    Response r = getResponse(rs2);
                    message.setResponse(r);
                }

                list.add(message);
            }

            return list;
        } catch (SQLException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } finally {
            close(null, null, rs2);
            close(null, pstmt, rs);
        }
    }

    /**
     * Gets messages from the thread indicated by thread user key. Messages will be returned
     * starting from the thread with index = "from" argument; count indicates how many messages will
     * be returned. If the remaining messages are less than count then only those messages will be
     * returned.
     * 
     * @param count
     *            number of messages
     * @param userKey
     *            the user key
     * @param from
     *            starting index
     * @return a list of messages or an empty list if there are no more messages from the given
     *         index
     * 
     * @throws IllegalArgumentException
     *             if userKey is null or empty string or if count < 0.
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     */
    public List<Message> getMessages(String userKey, int from, int count)
            throws MessageBoardPersistenceException {
        Helper.checkNullOrEmpty(userKey, "userKey");

        if (count < 1) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The count is < 1. count=" + count));
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            conn = this.getConnection();
            // get the thread id by user key
            pstmt = conn.prepareStatement(SELECT_THREAD_ID_BY_USER_KEY);
            pstmt.setString(1, userKey);
            rs = pstmt.executeQuery();

            long threadId = -1;

            if (rs.next()) {
                threadId = rs.getLong(1);
            }

            // using thread id get the corresponding row from messages table
            pstmt = conn
                    .prepareStatement(SELECT_MESSAGE_BY_THREAD_ID_AND_ROWID);
            pstmt.setLong(1, threadId);
            pstmt.setInt(2, from);
            pstmt.setMaxRows(count);
            rs = pstmt.executeQuery();

            List<Message> list = new ArrayList<Message>();

            while (rs.next()) {
                Message message = getMessage(rs);
                // get the attributes.
                pstmt = conn.prepareStatement(SELECT_ATTRIBUTE_BY_MESSAGE_ID);
                pstmt.setLong(1, message.getId());

                rs2 = pstmt.executeQuery();

                Map<String, MessageAttribute> attributes = new HashMap<String, MessageAttribute>();

                while (rs2.next()) {
                    MessageAttribute attr = getAttribute(rs2);
                    attributes.put(attr.getName(), attr);
                }

                if (!attributes.isEmpty()) {
                    message.setAttributes(attributes);
                }

                // get the response
                pstmt = conn.prepareStatement(SELECT_RESPONSE_BY_MESSAGE_ID);
                pstmt.setLong(1, message.getId());
                rs2 = pstmt.executeQuery();

                if (rs2.next()) {
                    Response r = getResponse(rs2);
                    message.setResponse(r);
                }

                list.add(message);
            }

            return list;
        } catch (SQLException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } finally {
            close(null, null, rs2);
            close(conn, pstmt, rs);
        }
    }

    /**
     * Gets messages from the thread indicated by user key. It will return only the first messages
     * indicated by count argument. If count is greater than the total number of messages of the
     * thread than all the messages of the thread will be returned.
     * 
     * @param count
     *            number of messages to be returned
     * @param userKey
     *            user key
     * 
     * @return a list of messages or an empty list if there are no messages
     * 
     * @throws IllegalArgumentException
     *             if userKey is null or empty or count < 1
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     */
    public List<Message> getMessages(String userKey, int count)
            throws MessageBoardPersistenceException {
        Helper.checkNull(userKey, "userKey");

        if (count < 1) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The count is < 1. count=" + count));
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            conn = this.getConnection();
            // get the thread id by user key
            pstmt = conn.prepareStatement(SELECT_THREAD_ID_BY_USER_KEY);
            pstmt.setString(1, userKey);
            rs = pstmt.executeQuery();

            long threadId = -1;

            if (rs.next()) {
                threadId = rs.getLong(1);
            }

            // using thread id get the corresponding row from messages table
            pstmt = conn.prepareStatement(SELECT_MESSAGE_BY_THREAD_ID);
            pstmt.setLong(1, threadId);
            pstmt.setMaxRows(count);
            rs = pstmt.executeQuery();

            List<Message> list = new ArrayList<Message>();

            while (rs.next()) {
                Message message = getMessage(rs);
                // get the attributes.
                pstmt = conn.prepareStatement(SELECT_ATTRIBUTE_BY_MESSAGE_ID);
                pstmt.setLong(1, message.getId());

                rs2 = pstmt.executeQuery();

                Map<String, MessageAttribute> attributes = new HashMap<String, MessageAttribute>();

                while (rs2.next()) {
                    MessageAttribute attr = getAttribute(rs2);
                    attributes.put(attr.getName(), attr);
                }

                message.setAttributes(attributes);
                // get the response
                pstmt = conn.prepareStatement(SELECT_RESPONSE_BY_MESSAGE_ID);
                pstmt.setLong(1, message.getId());
                rs2 = pstmt.executeQuery();

                if (rs2.next()) {
                    Response r = getResponse(rs2);
                    message.setResponse(r);
                }

                list.add(message);
            }

            return list;
        } catch (SQLException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } finally {
            close(conn, pstmt, rs2);
            close(conn, pstmt, rs);
        }
    }

    /**
     * close the connection to the database.
     * 
     * @param conn
     *            the connection.
     * @param pstmt
     *            the PreparedStatement
     * @param rs
     *            the ResultSet
     * @throws MessageBoardPersistenceException
     *             if any error.
     */
    private static void close(Connection conn, PreparedStatement pstmt,
            ResultSet rs) throws MessageBoardPersistenceException {
        try {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        }
    }

    /**
     * Get the message form the result set.
     * 
     * @param rs
     *            the result set.
     * @return the message.
     * @throws SQLException
     *             Any exception throw the caller method.
     */
    private Message getMessage(ResultSet rs) throws SQLException {
        int index = 1;
        long id = rs.getLong(index++);
        String name = rs.getString(index++);
        java.util.Date date = new java.util.Date(rs.getDate(index++).getTime());
        String message = rs.getString(index);

        return new Message(id, name, date, message);
    }

    /**
     * Get the message form the result set.
     * 
     * @param rs
     *            the result set.
     * @return the message.
     * @throws SQLException
     *             Any exception throw the caller method.
     */
    private MessageAttribute getAttribute(ResultSet rs) throws SQLException {
        int index = 2;
        String name = rs.getString(index++);
        String value = rs.getString(index);

        return new MessageAttribute(name, value);
    }

    /**
     * Get the message form the result set.
     * 
     * @param rs
     *            the result set.
     * @return the message.
     * @throws SQLException
     *             Any exception throw the caller method.
     */
    private Response getResponse(ResultSet rs) throws SQLException {
        int index = 1;
        long id = rs.getLong(index++);
        String name = rs.getString(index++);
        java.util.Date date = new java.util.Date(rs.getDate(index++).getTime());
        String message = rs.getString(index);

        return new Response(id, name, date, message);
    }

    /**
     * This method will update a message into persistence.
     * 
     * @param message
     *            the message
     * 
     * @throws IllegalArgumentException
     *             if message is null or if message id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if the given entity does not exist
     */
    public void updateMessage(Message message) throws EntityNotFoundException,
            MessageBoardPersistenceException {
        Helper.checkNull(message, "message");

        if (message.getId() < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The message's id is < 0. id=" + message.getId()));
        }

        Connection conn = null;

        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);
            innerUpdateMessage(message, conn);
            conn.commit();
        } catch (SQLException e) {
            rethrowSQLException(e, conn, true);
        } finally {
            close(conn, null, null);
        }
    }

    /**
     * the inner method for updating a message. using a connection, and will not close it.
     * 
     * @param message
     *            the message
     * @param conn
     *            the connection to the database.
     * 
     * @throws IllegalArgumentException
     *             if message is null or if message id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if the given entity does not exist
     * @throws SQLException
     *             if any sql error.
     */
    private void innerUpdateMessage(Message message, Connection conn)
            throws EntityNotFoundException, MessageBoardPersistenceException,
            SQLException {
        PreparedStatement pstmt = null;

        try {
            if (!checkMessageId(message.getId(), conn)) {
                throw new EntityNotFoundException(Helper.getErrorMessage(
                        ErrorMessagesCache.ENTITY_NOT_FOUND,
                        "The message with id=" + message.getId()
                                + " not found."));
            }

            // update the message
            pstmt = conn.prepareStatement(UPDATE_MESSAGE);

            int index = 1;
            pstmt.setString(index++, message.getName());
            pstmt.setDate(index++, new Date(message.getDate().getTime()));
            pstmt.setString(index++, message.getMessage());
            pstmt.setLong(index, message.getId());
            pstmt.execute();
            
            // delete the response
            deleteEntity(message.getId(), conn, DELETE_RESPONSE_BY_MESSAGE_ID);
            // update the response
            if (message.getResponse() != null) {
                long responseId = message.getResponse().getId();

                if (checkResponseId(responseId, conn)) {
                    innerUpdateResponse(message.getResponse(), conn);
                } else {
                    Response response = message.getResponse();
                    // if the response id is not set.
                    if (response.getId() < 0) {
                        long id = idGenerator.getNextID();
                        response.setId(id);
                    }
                    pstmt = conn.prepareStatement(INSERT_RESPONSE);
                    insertResponse(pstmt, response, message.getId());
                }
            }

            // delete the attributes
            deleteEntity(message.getId(), conn, DELETE_ATTRIBUTE_BY_MESSAGE_ID);

            // insert the attributes
            // insert the attributes.
            Map<String, MessageAttribute> map = message.getAllAttributes();

            for (String name : map.keySet()) {
                MessageAttribute attr = map.get(name);
                pstmt = conn.prepareStatement(INSERT_ATTRIBUTE);
                insertAttribute(pstmt, attr, message.getId());
            }
        } catch (IDGenerationException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } finally {
            close(null, pstmt, null);
        }
    }

    /**
     * This method will remove a message from persistence.
     * 
     * @param messageID
     *            the message id
     * 
     * @throws IllegalArgumentException
     *             if message id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if the message is not found
     */
    public void removeMessage(long messageID) throws EntityNotFoundException,
            MessageBoardPersistenceException {
        if (messageID < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The message id is < 0. id=" + messageID));
        }

        Connection conn = null;

        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);
            innerRemoveMessage(messageID, conn);
            conn.commit();
        } catch (SQLException e) {
            rethrowSQLException(e, conn, true);
        } finally {
            close(conn, null, null);
        }
    }

    /**
     * the inner method for removing the message, using a connection, and will not close it.
     * 
     * @param conn
     *            the connection to the date base.
     * 
     * @param messageID
     *            the message id
     * 
     * @throws IllegalArgumentException
     *             if message id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if the message is not found
     * @throws SQLException
     *             if any sql error.
     */
    private void innerRemoveMessage(long messageID, Connection conn)
            throws EntityNotFoundException, MessageBoardPersistenceException,
            SQLException {
        PreparedStatement pstmt = null;

        try {
            if (!checkMessageId(messageID, conn)) {
                throw new EntityNotFoundException(Helper.getErrorMessage(
                        ErrorMessagesCache.ENTITY_NOT_FOUND,
                        "The message with id=" + messageID + " not found."));
            }

            // delete the attributes
            deleteEntity(messageID, conn, DELETE_ATTRIBUTE_BY_MESSAGE_ID);
            // delete the response
            deleteEntity(messageID, conn, DELETE_RESPONSE_BY_MESSAGE_ID);
            // delete the messages
            deleteEntity(messageID, conn, DELETE_MESSAGE_BY_MESSAGE_ID);
        } finally {
            close(null, pstmt, null);
        }
    }

    /**
     * This method will remove all messages from the thread identified by id.
     * 
     * @param threadID
     *            the thread id
     * 
     * @throws IllegalArgumentException
     *             if thread id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no thread with the given id
     */
    public void removeAllMessages(long threadID)
            throws EntityNotFoundException, MessageBoardPersistenceException {
        if (threadID < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The thread's id is < 0. id=" + threadID));
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = this.getConnection();

            if (!checkThreadId(threadID, conn)) {
                throw new EntityNotFoundException(Helper.getErrorMessage(
                        ErrorMessagesCache.ENTITY_NOT_FOUND,
                        "The thread with id=" + threadID + " not found."));
            }

            conn.setAutoCommit(false);
            deleteEntity(threadID, conn, DELETE_ATTRIBUTE_BY_THREAD_ID);

            // delete the response
            deleteEntity(threadID, conn, DELETE_RESPONSE_BY_THREAD_ID);

            // delete the messages
            deleteEntity(threadID, conn, DELETE_MESSAGE_BY_THREAD_ID);
            conn.commit();
        } catch (SQLException e) {
            throw rethrowSQLException(e, conn, true);
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * re-throw the exception and roll back, if rollback is needed.
     * 
     * @param e
     *            the exception
     * @param conn
     *            the connection
     * @param rollback
     *            the flag to rollback.
     * @return MessageBoardPersistenceException wrapper the exception to
     *         MessageBoardPersistenceException.
     */
    private MessageBoardPersistenceException rethrowSQLException(
            SQLException e, Connection conn, boolean rollback)
            throws MessageBoardPersistenceException {
        if (rollback) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                return new MessageBoardPersistenceException(Helper
                        .getErrorMessage(ErrorMessagesCache.PERSISTENCE, e1
                                .getMessage()), e1);
            }
        }

        return new MessageBoardPersistenceException(Helper.getErrorMessage(
                ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
    }

    /**
     * delete the entity by id, using pstmt and the sql command.
     * 
     * @param id
     *            the entity id.
     * @param conn
     *            the Connection to the database
     * @param sql
     *            the sql command.
     * @throws SQLException
     *             if any sql exception re-throw it.
     */
    private void deleteEntity(long id, Connection conn, String sql)
            throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * This methods adds a new message for the given thread into persistence.
     * 
     * @param message
     *            the message
     * @param threadID
     *            the thread id
     * @return the message with the id set
     * 
     * @throws IllegalArgumentException
     *             if message is null or message id >=0 or thread id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no thread with the given id
     */
    public Message postMessage(long threadID, Message message)
            throws MessageBoardPersistenceException, EntityNotFoundException {
        if (threadID < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The thread's id is < 0. id=" + threadID));
        }

        Helper.checkNull(message, "message");

        if (message.getId() >= 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The message's id is >= 0. id=" + message.getId()));
        }

        Connection conn = null;

        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);

            Message result = innerPostMessage(threadID, message, conn);
            conn.commit();

            return result;
        } catch (SQLException e) {
            throw rethrowSQLException(e, conn, true);
        } finally {
            close(conn, null, null);
        }
    }

    /**
     * the inner method for post message, using a connection, and not close it.
     * 
     * @param message
     *            the message
     * @param threadID
     *            the thread id
     * @param conn
     *            the connection to the database.
     * @return the message with the id set
     * 
     * @throws IllegalArgumentException
     *             if message is null or message id >=0 or thread id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no thread with the given id
     * @throws SQLException
     *             if any sql error.
     */
    private Message innerPostMessage(long threadID, Message message,
            Connection conn) throws MessageBoardPersistenceException,
            EntityNotFoundException, SQLException {
        PreparedStatement pstmt = null;

        try {
            if (!checkThreadId(threadID, conn)) {
                throw new EntityNotFoundException(Helper.getErrorMessage(
                        ErrorMessagesCache.ENTITY_NOT_FOUND,
                        "The thread with id = " + threadID + " is not found."));
            }

            long id = idGenerator.getNextID();
            message.setId(id);
            pstmt = conn.prepareStatement(INSERT_MESSAGE);
            insertMessage(pstmt, message, threadID);

            // insert the attributes.
            Map<String, MessageAttribute> map = message.getAllAttributes();

            for (String name : map.keySet()) {
                MessageAttribute attr = map.get(name);
                pstmt = conn.prepareStatement(INSERT_ATTRIBUTE);
                insertAttribute(pstmt, attr, id);
            }

            // insert the response
            Response response = message.getResponse();

            if (response != null) {
                pstmt = conn.prepareStatement(INSERT_RESPONSE);
                insertResponse(pstmt, response, id);
            }

            return message;
        } catch (IDGenerationException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } finally {
            close(null, pstmt, null);
        }
    }

    /**
     * check if the threadId is in the database or not.
     * 
     * @param messageId
     *            the thread id.
     * @param conn
     *            the connection.
     * @return true if is in the database otherwise false.
     * @throws MessageBoardPersistenceException
     *             if any error.
     */
    private boolean checkMessageId(long messageId, Connection conn)
            throws MessageBoardPersistenceException {
        return checkId(messageId, conn, SELECT_MESSAGE_BY_MESSAGE_ID);
    }

    /**
     * check if the threadId is in the database or not.
     * 
     * @param threadId
     *            the thread id.
     * @param conn
     *            the connection.
     * @return true if is in the database otherwise false.
     * @throws MessageBoardPersistenceException
     *             if any error.
     */
    private boolean checkThreadId(long threadId, Connection conn)
            throws MessageBoardPersistenceException {
        return checkId(threadId, conn, SELECT_THREAD_BY_THREAD_ID);
    }

    /**
     * check if the responseId is in the database or not.
     * 
     * @param responseId
     *            the responseId id.
     * @param conn
     *            the connection.
     * @return true if is in the database otherwise false.
     * @throws MessageBoardPersistenceException
     *             if any error.
     */
    private boolean checkResponseId(long responseId, Connection conn)
            throws MessageBoardPersistenceException {
        return checkId(responseId, conn, SELECT_RESPONSE_BY_RESPONSE_ID);
    }

    /**
     * check if the threadId is in the database or not.
     * 
     * @param id
     *            the is to check.
     * @param sql
     *            the sql
     * @param conn
     *            the connection.
     * @return true if is in the database otherwise false.
     * @throws MessageBoardPersistenceException
     *             if any error.
     */
    private boolean checkId(long id, Connection conn, String sql)
            throws MessageBoardPersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw rethrowSQLException(e, conn, false);
        } finally {
            close(null, pstmt, rs);
        }
    }

    /**
     * This methods adds the response for the message indicated by the id into persistence.
     * 
     * @param response
     *            the response
     * @param messageID
     *            message id
     * @return the response with its id set
     * 
     * @throws IllegalArgumentException
     *             if message id <0 or response id >=0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no message with the given id
     */
    public Response postResponse(long messageID, Response response)
            throws MessageBoardPersistenceException, EntityNotFoundException {
        if (messageID < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The message id is < 0. id=" + messageID));
        }

        if (response.getId() > 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The response id is < 0. id=" + response.getId()));
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = this.getConnection();

            if (!checkMessageId(messageID, conn)) {
                throw new EntityNotFoundException(Helper.getErrorMessage(
                        ErrorMessagesCache.ENTITY_NOT_FOUND,
                        "The message with id=" + messageID + " not found."));
            }

            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT_RESPONSE);

            // get the id;
            long id = idGenerator.getNextID();
            response.setId(id);
            insertResponse(pstmt, response, messageID);
            conn.commit();

            return response;
        } catch (SQLException e) {
            throw rethrowSQLException(e, conn, true);
        } catch (IDGenerationException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * This methods removes the response for the message indicated by id from persistence.
     * 
     * @param messageID
     *            message id
     * 
     * @throws IllegalArgumentException
     *             if message id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no message with the given id
     */
    public void removeResponse(long messageID)
            throws MessageBoardPersistenceException, EntityNotFoundException {
        if (messageID < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The message id is < 0. id=" + messageID));
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = this.getConnection();

            if (!checkMessageId(messageID, conn)) {
                throw new EntityNotFoundException(Helper.getErrorMessage(
                        ErrorMessagesCache.ENTITY_NOT_FOUND,
                        "The message with id=" + messageID + " not found."));
            }

            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(DELETE_RESPONSE_BY_MESSAGE_ID);
            pstmt.setLong(1, messageID);
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw rethrowSQLException(e, conn, true);
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * This method will update a response into persistence.
     * 
     * @param response
     *            the response to be updated
     * 
     * @throws IllegalArgumentException
     *             if response is null or if response id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if the given entity does not exist
     */
    public void updateResponse(Response response)
            throws MessageBoardPersistenceException, EntityNotFoundException {
        Helper.checkNull(response, "response");

        if (response.getId() < 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(
                    ErrorMessagesCache.INVALID_ARGUMENT,
                    "The response's id is < 0. id=" + response.getId()));
        }

        Connection conn = null;

        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);
            innerUpdateResponse(response, conn);
            conn.commit();
        } catch (SQLException e) {
            throw rethrowSQLException(e, conn, true);
        } finally {
            close(conn, null, null);
        }
    }

    /**
     * the inner method for updating response, using a connection and will not close it.
     * 
     * @param response
     *            the response to be updated
     * @param conn
     *            the connection to the database.
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if the given entity does not exist
     * @throws SQLException
     *             if any sql error.
     */
    private void innerUpdateResponse(Response response, Connection conn)
            throws MessageBoardPersistenceException, EntityNotFoundException,
            SQLException {
        PreparedStatement pstmt = null;

        try {
            if (!checkResponseId(response.getId(), conn)) {
                throw new EntityNotFoundException(Helper.getErrorMessage(
                        ErrorMessagesCache.ENTITY_NOT_FOUND,
                        "The response with id=" + response.getId()
                                + " not found."));
            }

            pstmt = conn.prepareStatement(UPDATE_RESPONSE);

            int index = 1;
            pstmt.setString(index++, response.getName());
            pstmt.setDate(index++, new Date(response.getDate().getTime()));
            pstmt.setString(index++, response.getMessage());
            pstmt.setLong(index, response.getId());
            pstmt.execute();
        } finally {
            close(null, pstmt, null);
        }
    }

    /**
     * Creates and returns a connection.
     * 
     * @return the created connection
     * 
     * @throws MessageBoardPersistenceException
     *             if errors occur when obtaining the connection
     */
    private Connection getConnection() throws MessageBoardPersistenceException {
        try {
            return factory.createConnection(connectionName);
        } catch (DBConnectionException e) {
            throw new MessageBoardPersistenceException(Helper.getErrorMessage(
                    ErrorMessagesCache.PERSISTENCE, e.getMessage()), e);
        }
    }
}
