/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.util.errorhandling.ExceptionUtils;

import java.util.HashSet;
import java.util.Set;


/**
 * <p>
 * This class represents a thread. It extends from BasicEntityData abstract class. It has an id,
 * inherited from the abstract class, a user key and a list of messages that belong to the thread.
 * It provides various methods that allow the user to manage the messages of the thread. The
 * messages can have the id not set (<0) meaning that these messages does not exist in persistence
 * (if the thread is updated in persistence then the new messages will have the ids set).
 * </p>
 * <p>
 * Thread safe: It is not thread safe because it is mutable.
 * </p>
 *
 * @author DanLazar, yqw
 * @version 2.0
 */
public class Thread extends BasicEntityData {
    /**
     * The user key that uniquely identify the thread. It will be initialized in constructors except
     * the default one. It has a setter and a getter. It cannot be set to null or empty string.
     */
    private String userKey;

    /**
     * The set of messages. It cannot be null after initialization but can be empty. The reference
     * is final but the contents can change. It will be initialized in the constructors. It cannot
     * contain null elements.
     */
    private final Set<Message> messages;

    /**
     * Creates a new instance.
     */
    public Thread() {
        messages = new HashSet<Message>();
    }

    /**
     * Creates a new instance.
     *
     * #exception IllegalArgumentException if arg is null or empty string
     *
     * @param userKey
     *            the user key
     * @throws IllegalArgumentException
     *             if arg is null or empty string.
     */
    public Thread(String userKey) {
        ExceptionUtils.checkNullOrEmpty(userKey, null, null, "The ueserKey should not be null or empty.");
        this.userKey = userKey;
        messages = new HashSet<Message>();
    }

    /**
     * Creates a new instance with id and userKey.
     *
     * @param id
     *            the id
     * @param userKey
     *            the user key
     * @throws IllegalArgumentException
     *             if id<0 or userKey is null or empty string
     */
    public Thread(long id, String userKey) {
        super(id);
        ExceptionUtils.checkNullOrEmpty(userKey, null, null, "The ueserKey should not be null or empty.");
        this.userKey = userKey;
        messages = new HashSet<Message>();
    }

    /**
     * Creates a new instance with userKey and messages.
     *
     * @param userKey
     *            the user key
     * @param messages
     *            the messages
     * @throws IllegalArgumentException
     *             if userKey is null or empty string or if messages is null, empty, or if it
     *             contains null elements
     */
    public Thread(String userKey, Set<Message> messages) {
        ExceptionUtils.checkNullOrEmpty(userKey, null, null, "The ueserKey should not be null or empty.");
        checkSet(messages);

        this.userKey = userKey;
        this.messages = messages;
    }

    /**
     * Creates a new instance with id, userKey and messages.
     *
     * @param id
     *            the id
     * @param userKey
     *            the user key
     * @param messages
     *            the messages
     * @throws IllegalArgumentException
     *             if userKey is null or empty string or if messages is null, empty, or if it
     *             contains null elements or if id<0.
     */
    public Thread(long id, String userKey, Set<Message> messages) {
        super(id);
        ExceptionUtils.checkNullOrEmpty(userKey, null, null, "The ueserKey should not be null or empty.");
        checkSet(messages);

        this.userKey = userKey;
        this.messages = messages;
    }

    /**
     * check the set if it is valid.
     *
     * @param messages
     *            the set to check.
     * @throws IllegalArgumentException
     *             if messages is null, empty, or if it contains null elements or if id<0.
     */
    private void checkSet(Set<Message> messages) {
        ExceptionUtils.checkNull(messages, null, null, "The messages set should not be null.");

        if (messages.isEmpty()) {
            throw new IllegalArgumentException("The messages set should not be empty.");
        }

        if (messages.contains(null)) {
            throw new IllegalArgumentException("The messages set should not contains null element.");
        }
    }

    /**
     * Getter for the messages.
     *
     * @return a copy of the messages
     */
    public Set<Message> getMessages() {
        return new HashSet<Message>(messages);
    }

    /**
     * Setter for the messages.
     *
     * @param messages
     *            the messages
     * @throws IllegalArgumentException
     *             if arg is null or empty or if it contains null elements.
     */
    public void setMessages(Set<Message> messages) {
        checkSet(messages);

        this.messages.clear();
        this.messages.addAll(messages);
    }

    /**
     * Returns the Message that has the given id.
     *
     * @param id
     *            the id
     * @return the Message that has the given id, or null if not found.
     * @throws IllegalArgumentException
     *             if id<0.
     */
    public Message getMessage(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("The id is negative. id=" + id);
        }

        for (Message message : messages) {
            if (message.getId() == id) {
                return message;
            }
        }

        return null;
    }

    /**
     * Adds a message.
     *
     * @param message
     *            the message
     * @return true is message was added false otherwise add the message and return true.
     * @throws IllegalArgumentException
     *             if message is null.
     */
    public boolean addMessage(Message message) {
        ExceptionUtils.checkNull(message, null, null, "The message to add should not be null.");

        return messages.add(message);
    }

    /**
     * Removes the message identified by given id.
     *
     * @param id
     *            the id
     * @return the removed message or null if no message was removed
     * @throws IllegalArgumentException
     *             if id<0.
     */
    public Message removeMessage(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("The id is negative. id=" + id);
        }

        Message retrieve = null;

        for (Message message : messages) {
            if (message.getId() == id) {
                retrieve = message;

                break;
            }
        }

        messages.remove(retrieve);

        return retrieve;
    }

    /**
     * Removes the given message from the messages set.
     *
     * @param message
     *            the message to be removed
     * @return the removed message or null if the no message was removed
     * @throws IllegalArgumentException
     *             if message is null.
     */
    public Message removeMessage(Message message) {
        ExceptionUtils.checkNull(message, null, null, "The message to remove should not be null.");

        Message retrieve = null;

        for (Message m : messages) {
            if (m.equals(message)) {
                retrieve = m;

                break;
            }
        }

        messages.remove(retrieve);

        return retrieve;
    }

    /**
     * Clears the messages set.
     */
    public void clearMessages() {
        messages.clear();
    }

    /**
     * Check if the message is contained into the set.
     *
     * @param message
     *            the message
     * @return true if the message is contained, false otherwise.
     * @throws IllegalArgumentException
     *             if message is null.
     */
    public boolean containsMessage(Message message) {
        ExceptionUtils.checkNull(message, null, null, "The message should not be null.");

        return messages.contains(message);
    }

    /**
     * Setter for the userKey.
     *
     * @param userKey
     *            the user key
     * @throws IllegalArgumentException
     *             if userKey is null or empty.
     */
    public void setUserKey(String userKey) {
        ExceptionUtils.checkNullOrEmpty(userKey, null, null, "The ueserKey should not be null or empty.");
        this.userKey = userKey;
    }

    /**
     * Getter for the userKey.
     *
     * @return the user key
     */
    public String getUserKey() {
        return userKey;
    }
}
