/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.impl;

import com.cronos.im.messenger.AskForChatMessage;
import com.cronos.im.messenger.ChatMessage;
import com.cronos.im.messenger.EnterChatMessage;
import com.cronos.im.messenger.Helper;
import com.cronos.im.messenger.MessageTracker;
import com.cronos.im.messenger.Messenger;
import com.cronos.im.messenger.MessengerException;
import com.cronos.im.messenger.PresenceMessage;
import com.cronos.im.messenger.SessionUnavailableMessage;
import com.cronos.im.messenger.UserIDRetriever;
import com.topcoder.chat.contact.ChatContactManagementException;
import com.topcoder.chat.contact.ChatContactManager;
import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.message.pool.PoolNotRegisteredException;
import com.topcoder.chat.session.ChatSession;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * <p>
 * The MessegnerImpl is responsible for routing the messages to the message pools of the
 * desired recipients. Using the Chat Message Pool component, users will be able to receive
 * the messages by pulling them off. All the chat messages that are post to the user session
 * pool are tracked.
 * </p>
 * <p>
 * <b>Thread safety</b> : This class is thread safe because it is immutable.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class MessengerImpl implements Messenger {
    /**
     * The ID for the system.This constant is used to verify whether the sender user id is for
     * the system and if so, there is no need to check for blocked users in postMessageXXX methods.
     */
    private static final int SYSTEM_ID = -1;

    /**
     * The <c>MessagePool</c> used to hold the user message in either session or user scope.
     * This variable is set in the constructor and never changed after.
     */
    private final MessagePool pool;

    /**
     * The <c>ChatMessageTracker</c> used to track the chat message once it is post in the session.
     * It is set in the constructor, non-null and immutable after being set.
     */
    private final MessageTracker tracker;

    /**
     * The <c>UserIDRetriever</c> used to retriever long user id from the user
     * <c>Object</c> representation({@link Message#getSender()}/{@link Message#getAllRecipients()},
     * the user id (of <c>long</c> type) will be used by ChatContractManager to determine the
     * blocked users.
     * It is set in the constructor, non-null and immutable after set.
     */
    private final UserIDRetriever retriever;

    /**
     * The <c>ChatContactManager</c> is used to find the blocked users for a specified user.
     * It's set in the constructor, non-null and immutable after set.
     */
    private final ChatContactManager contactManager;

    /**
     * Create a new instance of the class.
     *
     * @param pool           The message pool to set.
     * @param tracker        The tracker to set.
     * @param retriever      The user id retriever to set.
     * @param contactManager The ChatContactManager used to retrieved the blocked users.
     * @throws IllegalArgumentException If any of the arguments is null.
     */
    public MessengerImpl(MessagePool pool, MessageTracker tracker
        , UserIDRetriever retriever, ChatContactManager contactManager) {
        Helper.validateNotNull(tracker, "tracker");
        Helper.validateNotNull(retriever, "retriever");
        Helper.validateNotNull(contactManager, "contactManager");
        Helper.validateNotNull(pool, "pool");

        this.pool = pool;
        this.tracker = tracker;
        this.retriever = retriever;
        this.contactManager = contactManager;
    }

    /**
     * <p>
     * Post the message to user message pool.
     * If message is not one of the following types, nothing happens:
     * <ul>
     * <li>Session Unavailable Message</li>
     * <li>Ask For Chat Message</li>
     * <li>Enter Chat Message</li>
     * </ul>
     * If the sender of the message is blocked from sending the message
     * to the user specified by <c>userId</c>, nothing happens.
     * </p>
     *
     * @param msg    The message to post.
     * @param userId The user id representing receiver.
     * @throws IllegalArgumentException If <c>msg</c> argument is null.
     * @throws MessengerException       Wraps any other error that may appear.
     */
    public void postMessage(Message msg, long userId)
        throws MessengerException {
        Helper.validateNotNull(msg, "msg");
        // Verify if the message has a type that can be handled by this method
        if (!(msg instanceof SessionUnavailableMessage
            || msg instanceof AskForChatMessage || msg instanceof EnterChatMessage)) {
            return;
        }

        try {
            long senderUserId = retriever.retrieve(msg.getSender());
            // There is no need to check for blocked users when the message sender is system(-1).
            if (senderUserId != SYSTEM_ID) {
                // Check If the sender of the message is blocked from sending the message to
                // specified user and end method call if so.
                if (contactManager.isBlockedUser(userId, senderUserId)) {
                    return;
                }
            }

            // Set the user with specified <c>userId</c> as one of the recipients of the message
            // if message's recipients set does not contain this user id.
            msg.addRecipient(new Long(userId));

            // Post the message into the message pool of the user.
            pool.push(userId, msg);
        } catch (PoolNotRegisteredException e) {
            throw new MessengerException("The message pool is not registered:", e);
        } catch (ChatContactManagementException e) {
            throw new MessengerException(
                "Exception occurred when trying to see if the sender of the message"
                    + " is blocked by specified user: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new MessengerException("Exception occurred when posting the message: " + e.getMessage(), e);
        }

    }

    /**
     * <p>
     * Post the message to user session message pool.
     * If the message is not one of the following types, nothing happens:
     * <ul>
     * <li>Chat Message</li>
     * <li>Presence Message</li>
     * </ul>
     * If the sender of the message is blocked from sending the message to
     * the user, nothing happens.
     * </p>
     *
     * @param msg       The message to post.
     * @param userId    The user id  representing receiver.
     * @param sessionId The session id the give user belongs to.
     * @throws IllegalArgumentException If argument is null.
     * @throws MessengerException       Wraps any other error that may appear.
     */
    public void postMessage(Message msg, long userId, long sessionId)
        throws MessengerException {
        Helper.validateNotNull(msg, "msg");
        // Verify if the message has a type that can be handled by this method
        if (!(msg instanceof ChatMessage || msg instanceof PresenceMessage)) {
            return;
        }

        try {
            long senderUserId = retriever.retrieve(msg.getSender());
            // There is no need to check for blocked users when the message sender is system(-1).
            if (senderUserId != SYSTEM_ID) {
                // Check if the sender of the message is blocked from sending the message to
                // specified user and end method call if so.
                if (contactManager.isBlockedUser(userId, senderUserId)) {
                    return;
                }
            }
            // Set the user with specified <c>userId</c> as one of the recipients of the message
            // if message's recipients set does not contain this user id.
            msg.addRecipient(new Long(userId));

            // Track the message
            tracker.track(msg, new long[]{userId}, sessionId);

            // Post the message into the the user's session message pool.
            pool.push(userId, sessionId, msg);
        } catch (PoolNotRegisteredException e) {
            throw new MessengerException("The message pool is not registered:", e);
        } catch (ChatContactManagementException e) {
            throw new MessengerException(
                "Exception occurred when trying to see if the sender of the message"
                    + "is blocked by message sender user: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new MessengerException("Exception occurred when posting the message: " + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Post the message to the users in specified chat sessions except the sender of the message.
     * The users are retrieved from <c>session.getActiveUsers()</c>,  except the sender of the msg.
     * Only these messages can route directly to the user’s session message pool:
     * <ul>
     * <li>Chat Message</li>
     * <li>Presence Message</li>
     * </ul>
     * If the message has not one of this types nothing happens.
     * </p>
     *
     * @param msg     The message to post.
     * @param session The chat session.
     * @throws IllegalArgumentException If any of the arguments is null.
     * @throws MessengerException       Wraps any other error that may appear.
     */
    public void postMessageToOthers(Message msg, ChatSession session)
        throws MessengerException {
        Helper.validateNotNull(msg, "msg");
        Helper.validateNotNull(session, "session");
        // Verify if the message has a type that can be handled by this method
        if (!(msg instanceof ChatMessage || msg instanceof PresenceMessage)) {
            return;
        }

        try {
            // If the sender of the message is included between the active users retrieved from session
            // it should be removed. Also if the sender of the message is blocked by any user in the
            // users denoted by <c>userIds</c>, remove the user from the userIds array.
            // If the sender of the message is the system(-1) then there is no need to check if the user
            // is blocked.
            long senderUserId = retriever.retrieve(msg.getSender());
            long[] userIds = session.getActiveUsers();
            // Avoid having duplicate user ids.
            Set userIdsSet = new HashSet();
            for (int i = 0; i < userIds.length; i++) {
                if (userIds[i] != senderUserId
                    && (senderUserId == SYSTEM_ID || !contactManager.isBlockedUser(userIds[i], senderUserId))) {
                    userIdsSet.add(new Long(userIds[i]));
                }
            }
            userIds = new long[userIdsSet.size()];
            long sessionId = session.getId();
            int userIdsIndex = 0;
            for (Iterator userIdsIterator = userIdsSet.iterator(); userIdsIterator.hasNext(); userIdsIndex++) {
                userIds[userIdsIndex] = ((Long) userIdsIterator.next()).longValue();
                // Set the user with specified <c>userId</c> as one of the recipients of the message
                // if message's recipients set does not contain this user id and set the user with
                // specified <c>userIds[userIdsIndex]</c>.
                msg.addRecipient(new Long(userIds[userIdsIndex]));
            }

            if (userIds.length != 0) {
                // Track the message if there are any userIds to track.
                tracker.track(msg, userIds, sessionId);

                // Post the message into the user session message pool for other users.
                pool.push(userIds, sessionId, msg);
            }
        } catch (PoolNotRegisteredException e) {
            throw new MessengerException("The message pool is not registered:", e);
        } catch (ChatContactManagementException e) {
            throw new MessengerException(
                "Exception occurred when trying to see if the sender of the message "
                    + "is blocked by message sender user: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new MessengerException("Exception occurred when posting the message: " + e.getMessage(), e);
        }
    }

    /**
     * Post the message to the users in the specified session. The users are retrieved
     * from <c>session.getActiveUsers()</c>,  plus  the sender of the message if he(she)
     * is not included between those users.
     * Only these messages can route directly to the user’s session message pool:
     * <ul>
     * <li>Chat Message</li>
     * <li>Presence Message</li>
     * </ul>
     * The chat message will be delivered to all users of the session, including the sender.
     * On the other hand, Presence message will be delivered to all users of the session, except the sender.
     *
     * @param msg     the message to post
     * @param session the chat session
     * @throws IllegalArgumentException if argument is null
     * @throws MessengerException       Wraps any other exceptions that may appear.
     */
    public void postMessageToAll(Message msg, ChatSession session)
        throws MessengerException {
        Helper.validateNotNull(msg, "msg");
        Helper.validateNotNull(session, "session");
        // Verify if the message has a type that can be handled by this method
        boolean isPresenceMessage;
        if (msg instanceof ChatMessage) {
            isPresenceMessage = false;
        } else if (msg instanceof PresenceMessage) {
            isPresenceMessage = true;
        } else {
            return;
        }

        try {
            // If the sender of the message is included between the active users retrieved from session
            // and <c>msg</c> is a presence message, then it should be removed
            // (to avoid sending him a presence message). If the sender of
            // the message is blocked by any user in the users denoted by <c>userIds</c>,
            // remove the user from the userIds array.
            long senderUserId = retriever.retrieve(msg.getSender());
            // Denotes whether the sender of the message was found between the active users of the session
            boolean senderUserFound = false;
            long[] userIds = session.getActiveUsers();
            // Avoid having duplicate user ids.
            Set userIdsSet = new HashSet();
            for (int i = 0; i < userIds.length; i++) {
                if (isPresenceMessage) {
                    if (userIds[i] != senderUserId && !contactManager.isBlockedUser(userIds[i], senderUserId)) {
                        userIdsSet.add(new Long(userIds[i]));
                    }
                } else {
                    if (userIds[i] == senderUserId) {
                        senderUserFound = true;
                        userIdsSet.add(new Long(userIds[i]));
                    } else if (!contactManager.isBlockedUser(userIds[i], senderUserId)) {
                        // The user can't block himself.
                        userIdsSet.add(new Long(userIds[i]));
                    }
                }
            }

            // Verify if the message is a ChatMessage and if the sender user was not already included
            // into userIds array.
            if (!isPresenceMessage && !senderUserFound) {
                userIds = new long[userIdsSet.size() + 1];
                // The last value of the set is the senderUserId
                userIds[userIds.length - 1] = senderUserId;
            } else {
                userIds = new long[userIdsSet.size()];
            }

            long sessionId = session.getId();
            Iterator userIdsIterator = userIdsSet.iterator();
            for (int userIdsIndex = 0; userIdsIndex < userIds.length; userIdsIndex++) {
                if (userIdsIterator.hasNext()) {
                    // Set the elements of the array with the values retrieved from userIdsSet
                    userIds[userIdsIndex] = ((Long) userIdsIterator.next()).longValue();
                }
                // Set the user with specified <c>userId</c> as one of the recipients of the message
                // if message's recipients set does not contain this user id and set the user with
                // specified <c>userIds[userIdsIndex]</c>.
                msg.addRecipient(new Long(userIds[userIdsIndex]));
            }

            if (userIds.length != 0) {
                // Track the message.
                tracker.track(msg, userIds, sessionId);

                // Post the message into the user session message pool for all users.
                pool.push(userIds, sessionId, msg);
            }
        } catch (PoolNotRegisteredException e) {
            throw new MessengerException("The message pool is not registered:", e);
        } catch (ChatContactManagementException e) {
            throw new MessengerException(
                "Exception occurred when trying to see if the sender of the message "
                    + "is blocked by message sender user: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new MessengerException("Exception occurred when posting the message: " + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Get the message pool.
     * </p>
     *
     * @return The message pool. It will be non-null.
     */
    public MessagePool getMessagePool() {
        return pool;
    }
}
