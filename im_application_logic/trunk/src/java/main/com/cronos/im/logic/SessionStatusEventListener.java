/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.cronos.im.messenger.EnterChatMessage;
import com.cronos.im.messenger.PresenceMessage;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.Status;
import com.topcoder.chat.status.ChatStatusEventListener;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.chat.session.ChatSessionManager;
import com.cronos.im.messenger.Messenger;
import com.topcoder.util.log.Level;

/**
 * <p>
 * This class implements ChatStatusEventListener. It deals with the session status change of chat application.
 * If it changes to OPEN, do the following steps:
 * </p>
 * <p>
 * 1. If it is a 1-1 session or Sales session (which means two users were added and requires acknowledgement),
 * post the Enter Chat Message directly to both users.
 * </p>
 * <p>
 * 2. For all the requested users, post the Ask For Chat Message directly to the user.
 * </p>
 * <p>
 * 3. For all the users, register a session message pool for the user. Post Presence Message of other users to
 * this user, and post Presence Message of this user to other users.
 * </p>
 * <p>
 * Thread-safety: this class is immutable and thread-safe.
 * </p>
 * 
 * 
 * @author justforplay, TCSDEVELOPER
 * @version 1.0
 */
public class SessionStatusEventListener implements ChatStatusEventListener {

    /**
     * <p>
     * Represents the ChatSessionStatusTracker where this event listener belongs to. It is not null.
     * </p>
     */
    private final ChatSessionStatusTracker chatSessionStatusTracker;

    /**
     * <p>
     * Chat session manager instance used to deal with the change of session status. It is not null.
     * </p>
     */
    private final ChatSessionManager chatSessionManager;

    /**
     * <p>
     * Messenger used to send messages in this event listener. It is not null.
     * </p>
     */
    private final Messenger messenger;

    /**
     * <p>
     * Represents the logger instance of this event listener. This variable is used to log the operations in
     * the statusChanged method. If it is null, then no logging is done.
     * </p>
     */
    private final IMLogger logger;

    /**
     * <p>
     * Constructor to create SessionStatusEventListener with given arguments.
     * </p>
     * 
     * @param sessionStatusTracker
     *            ChatSessionStatusTracker instance that this event listener belongs to. Can't be null.
     * @param sessionManager
     *            Instance of chat session manager used to deal with the status change. Can't be null.
     * @param messenger
     *            messenger used to send the messages. Can't be null.
     * @param logger
     *            logger instance used to log the operations. Can be null.
     * @throws IllegalArgumentException
     *             If any arguments except the logger is null.
     */
    public SessionStatusEventListener(ChatSessionStatusTracker sessionStatusTracker,
            ChatSessionManager sessionManager, Messenger messenger, IMLogger logger) {
        IMHelper.checkNull(sessionStatusTracker, "sessionStatusTracker");
        IMHelper.checkNull(sessionManager, "sessionManager");
        IMHelper.checkNull(messenger, "messenger");

        this.chatSessionManager = sessionManager;
        this.chatSessionStatusTracker = sessionStatusTracker;
        this.messenger = messenger;
        this.logger = logger;
    }

    /**
     * <p>
     * This method is used to deal with the change of session status. Here's its logic:
     * </p>
     * <p>
     * 1. If it is a 1-1 session or Sales session (which means two users were added and requires
     * acknowledgement), post the Enter Chat Message directly to both users.
     * </p>
     * <p>
     * 2. For all the users, register a session message pool for the user. Post Presence Message of other
     * users to this user, and post Presence Message of this user to other users.
     * </p>
     * <p>
     * Any exception exception IllegalArgumentException are ignored.
     * </p>
     * 
     * @param id
     *            id of concrete entity, any possible int value
     * @param entity
     *            Entity instance of the session whose status is changed, it is not null
     * @param oldStatus
     *            old status of entity, Status instance, null is possible
     * @param newStatus
     *            new status of entity, Status instance, null impossible
     * @throws IllegalArgumentException
     *             if entity or newStatus parameters are null
     */
    public void statusChanged(long id, Entity entity, Status oldStatus, Status newStatus) {
        IMHelper.checkNull(entity, "entity");
        IMHelper.checkNull(newStatus, "newStatus");
        if (entity.getId() != IMHelper.ENTITY_SESSION) {
            throw new IllegalArgumentException("The entity is not session entity.");
        }

        try {
            ChatSession chatSession = chatSessionManager.getSession(id);
            if (newStatus.getId() == IMHelper.SESSION_STATUS_OPEN) {
                long[] users = chatSession.getUsers();
                if (chatSession.getMode() == ChatSession.ONE_ONE_SESSION
                        || chatSession.getMode() == ChatSession.SALES_SESSION) {
                    EnterChatMessage enterChatMessage = new EnterChatMessage();
                    // set sender to be system.
                    enterChatMessage.setSender(new Long(-1));
                    // timestamp is initialized in the constructor automatically.
                    enterChatMessage.setChatSessionId(chatSession.getId());
                    // Note: messenger.postMessageToAll() doesn't support the EnterChatMessage.
                    for (int i = 0; i < users.length; i++) {
                        messenger.postMessage(enterChatMessage, users[i]);
                    }
                    if (logger != null) {
                        logger.log(Level.INFO, "Send EnterChatMessage to All Users",
                                new String[] { "Session - " + chatSession.getId() });
                    }
                }

                MessagePool messagePool = messenger.getMessagePool();
                // register message pool.
                for (int i = 0; i < users.length; i++) {
                    messagePool.register(users[i], chatSession.getId());
                    if (logger != null) {
                        logger.log(Level.INFO, "Register MessagePool for User", new String[] {
                                "User - " + users[i], "Session - " + chatSession.getId() });
                    }
                }

                // post user presence message to other users.
                for (int i = 0; i < users.length; i++) {
                    PresenceMessage presenceMsg = new PresenceMessage();
                    // set presence to be true.
                    presenceMsg.setPresent(true);
                    // set sender to the current user.
                    presenceMsg.setSender(new Long(users[i]));
                    // timestamp is initialized in the constructor automatically.
                    // send message to others in the session.
                    messenger.postMessageToOthers(presenceMsg, chatSession);
                }
                if (logger != null) {
                    logger.log(Level.INFO, "Send PresenceMessage to Others", new String[] { "Session - "
                            + chatSession.getId() });
                }
            }
        } catch (Exception e) {
            if (logger != null) {
                logger.log(Level.ERROR, "Fail to handle the status change event: " + e.getMessage());
            }
        }
    }

    /**
     * <p>
     * Gets messenger used in this listener.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * 
     * @return messenger used in this event listener.
     *         <p>
     *         </p>
     */
    public Messenger getMessenger() {
        return this.messenger;
    }

    /**
     * <p>
     * Gets the chat session manager instance.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * 
     * @return chat session manager instance.
     */
    public ChatSessionManager getChatSessionManager() {
        return this.chatSessionManager;
    }

    /**
     * <p>
     * Gets ChatSessionStatusTracker that this event listener belongs to.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * 
     * @return chat profile manager used in this listener.
     */
    public ChatSessionStatusTracker getChatSessionStatusTracker() {
        return this.chatSessionStatusTracker;
    }
}
