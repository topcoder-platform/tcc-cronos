/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import java.util.List;


/**
 * <p>
 * This interface defines the contract for implementations that will manage threads, messages and
 * responses into a persistent storage. The interface defines various methods that allows a user to
 * create threads, to post messages and responses, to retrieve threads and responses, to update
 * threads, messages and responses, to remove threads messages and responses.
 * </p>
 * <p>
 * Thread safe: Implementations are not required to be thread safe.
 * </p>
 *
 * @author DanLazar, yqw
 * @version 2.0
 */
public interface MessageBoardPersistence {
    /**
     * Creates a new thread in persistence.
     *
     * @param thread
     *            the thread to be created
     * @return the thread with its id set
     *
     * @throws IllegalArgumentException
     *             if arg is null or if its id >=0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     */
    public Thread createThread(Thread thread) throws MessageBoardPersistenceException;

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
    public void updateThread(Thread thread) throws MessageBoardPersistenceException, EntityNotFoundException;

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
    public void deleteThread(long threadID) throws MessageBoardPersistenceException, EntityNotFoundException;

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
    public Thread getThread(long threadID) throws MessageBoardPersistenceException, EntityNotFoundException;

    /**
     * Gets the thread with the given user key from persistence.
     *
     * @param userKey
     *            the user key
     * @return the thread identified by user key
     *
     * @throws IllegalArgumentException
     *             if user key is null or empty string
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no thread with the given user key in persistence
     */
    public Thread getThread(String userKey) throws MessageBoardPersistenceException, EntityNotFoundException;

    /**
     * Gets all threads from persistence.
     *
     * @throws IllegalArgumentException
     *             if id < 0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @return a list of threads or an empty list if there are no threads
     */
    public List<Thread> getAllThreads() throws MessageBoardPersistenceException;

    /**
     * Gets messages from the thread indicated by thread id. Messages will be returned starting from
     * the thread with index = "from" argument; count indicates how many messages will be returned.
     * If the remaining messages are less than count then only those messages will be returned.
     *
     * @param threadID
     *            thread id
     * @param count
     *            number of messages
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
        throws MessageBoardPersistenceException;

    /**
     * Gets messages from the thread indicated by thread id. It will return only the first messages
     * indicated by count argument. If count is greater than the total number of messages of the
     * thread than all the messages of the thread will be returned.
     *
     * @throws IllegalArgumentException
     *             if id < 0 or count < 1
     *
     * @param threadID
     *            thread id
     * @param count
     *            number of messages to be returned
     * @return a list of messages or an empty list if there are no messages
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     */
    public List<Message> getMessages(long threadID, int count)
        throws MessageBoardPersistenceException;

    /**
     * Gets messages from the thread indicated by thread user key. Messages will be returned
     * starting from the thread with index = "from" argument; count indicates how many messages will
     * be returned. If the remaining messages are less than count then only those messages will be
     * returned.
     *
     * @param count
     *            number of messages
     * @param userKey
     *            user key
     * @param from
     *            starting index
     * @return a list of messages or an empty list if there are no more messages from the given
     *         index
     *
     * @throws IllegalArgumentException
     *             if userKey is null or empty string
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     */
    public List<Message> getMessages(String userKey, int from, int count)
        throws MessageBoardPersistenceException;

    /**
     * Gets messages from the thread indicated by user key. It will return only the first messages
     * indicated by count argument. If count is greater than the total number of messages of the
     * thread than all the messages of the thread will be returned.
     *
     * @param count
     *            number of messages to be returned
     * @param userKey
     *            user key
     * @return a list of messages or an empty list if there are no messages
     *
     * @throws IllegalArgumentException
     *             if id < 0 or count < 1
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     */
    public List<Message> getMessages(String userKey, int count)
        throws MessageBoardPersistenceException;

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
    public void updateMessage(Message message) throws MessageBoardPersistenceException, EntityNotFoundException;

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
    public void removeMessage(long messageID) throws MessageBoardPersistenceException, EntityNotFoundException;

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
    public void removeAllMessages(long threadID) throws MessageBoardPersistenceException, EntityNotFoundException;

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
     *             if message id >=0 or thread id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no thread with the given id
     */
    public Message postMessage(long threadID, Message message)
        throws MessageBoardPersistenceException, EntityNotFoundException;

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
        throws MessageBoardPersistenceException, EntityNotFoundException;

    /**
     * This methods removes the response for the message indicated by id from persistence.
     *
     * @param messsageID
     *            message id
     *
     * @throws IllegalArgumentException
     *             if message id <0
     * @throws MessageBoardPersistenceException
     *             if errors occurs when accessing the persistence
     * @throws EntityNotFoundException
     *             if there is no message with the given id
     */
    public void removeResponse(long messsageID) throws MessageBoardPersistenceException, EntityNotFoundException;

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
    public void updateResponse(Response response) throws MessageBoardPersistenceException, EntityNotFoundException;
}
