/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.cronos.im.messenger.AskForChatMessage;
import com.cronos.im.messenger.PresenceMessage;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.database.statustracker.Status;
import com.topcoder.chat.session.ChatSessionEventListener;
import com.topcoder.chat.session.ChatSessionManager;
import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.ChatUserProfileManager;
import com.topcoder.chat.user.profile.ChatUserProfilePersistenceException;
import com.topcoder.chat.user.profile.ProfileKeyManagerPersistenceException;
import com.topcoder.chat.user.profile.ProfileKeyNotFoundException;
import com.topcoder.chat.user.profile.ProfileNotFoundException;
import com.topcoder.chat.user.profile.UnrecognizedDataSourceTypeException;
import com.topcoder.util.log.Level;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class implements ChatSessionEventListener. It is used to deal with the change event of user session. Here's its
 * logic:
 * </p>
 * <p>
 * When a user is requested, post the Ask For Chat Message directly to the user.
 * </p>
 * <p>
 * When a user is added to session, and if the session status is OPEN, register a session message pool for the user.
 * Post Presence Message of other users to this user, and post Presence Message of this user to other users.
 * </p>
 * <p>
 * When a user is removed from session, and if the session status is OPEN, unregister the session message pool for the
 * user. Post Presence Message of this user to other users. For any session status, if the removed user is the last user
 * of the session, change the session status to CLOSE.
 * </p>
 * <p>
 * Thread-safety: This class is immutable and thread safe.
 * </p>
 * 
 * 
 * @author justforplay, TCSDEVELOPER
 * @version 1.0
 */
public class UserSessionEventListener implements ChatSessionEventListener {

    /**
     * <p>
     * Represents the default namespace to load the acknowledgeTime configuration if namespace is not provided in the
     * constructor.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.im.logic.UserSessionEventListener";

    /**
     * <p>
     * Represents the ChatSessionManager where this event listener belongs to. It is not null.
     * </p>
     */
    private final ChatSessionManager chatSessionManager;

    /**
     * <p>
     * Represents the ChatSessionManager where this event listener belongs to. It is not null.
     * </p>
     */
    private final ChatUserProfileManager chatUserProfileManager;

    /**
     * <p>
     * Messagr used to send messages in this class. It is not null.
     * </p>
     */
    private final Messenger messenger;

    /**
     * <p>
     * Chat session status tracker used to get the session status or change the session status. It is not null.
     * </p>
     */
    private final ChatSessionStatusTracker chatSessionStatusTracker;

    /**
     * <p>
     * Represents the logger instance of this event listener. If it is null, then no logging is done.
     * </p>
     */
    private final IMLogger logger;

    /**
     * <p>
     * Represents the time in milliseconds allowed for a responder to accetp the request.
     * </p>
     */
    private final long acknowledgeTime;

    /**
     * <p>
     * Constructor create UserSessionEventListener with given parameters.
     * </p>
     * 
     * 
     * @param sessionManager ChatSessionManager instance where this event listener belongs to. Can't be null.
     * @param messenger message pool instance used to send messages. Can't be null.
     * @param sessionStatusTracker chat session status tracker used to retrieve/change the session status. Can't be
     *            null.
     * @param acknowledgeTime acknowledge time value in the AskForChatMessage.
     * @param logger logger instance used to log the operations. Can be null.
     * @param userManager TODO
     * @exception IllegalArgumentException if any of the arguments except the logger is null, or the acknowledge is
     *                negative.
     */
    public UserSessionEventListener(ChatSessionManager sessionManager, Messenger messenger,
        ChatSessionStatusTracker sessionStatusTracker, long acknowledgeTime, IMLogger logger, ChatUserProfileManager userManager) {
        IMHelper.checkNull(sessionManager, "sessionManager");
        IMHelper.checkNull(messenger, "messenger");
        IMHelper.checkNull(sessionStatusTracker, "sessionStatusTracker");
        if (acknowledgeTime < 0) {
            throw new IllegalArgumentException("Acknowledge time can not be negative.");
        }

        this.acknowledgeTime = acknowledgeTime;
        this.chatSessionManager = sessionManager;
        this.chatUserProfileManager = userManager;
        this.chatSessionStatusTracker = sessionStatusTracker;
        this.logger = logger;
        this.messenger = messenger;
    }

    /**
     * <p>
     * Constructor create UserSessionEventListener with given parameters. The acknowledgeTime is initialized from the
     * configuration with property "acknowledge_time" in the default namespace.
     * </p>
     * 
     * @param sessionManager ChatSessionManager instance where this event listener belongs to. Can't be null.
     * @param messenger message pool instance used to send messages. Can't be null.
     * @param sessionStatusTracker chat session status tracker used to retrieve/change the session status. Can't be
     *            null.
     * @param logger logger instance used to log the operations. Can be null.
     * @exception IllegalArgumentException if any of the arguments except the logger is null.
     * @throws IMConfigurationException if the configuration is incorrect.
     */
    public UserSessionEventListener(ChatSessionManager sessionManager, Messenger messenger,
        ChatSessionStatusTracker sessionStatusTracker, IMLogger logger, ChatUserProfileManager userManager) throws IMConfigurationException {
        this(sessionManager, messenger, sessionStatusTracker, logger, DEFAULT_NAMESPACE, userManager);
    }

    /**
     * <p>
     * Constructor create UserSessionEventListener with given parameters. The acknowledgeTime is initialized from the
     * configuration with property "acknowledge_time" in the given namespace.
     * </p>
     * 
     * 
     * @param sessionManager ChatSessionManager instance where this event listener belongs to. Can't be null.
     * @param messenger message pool instance used to send messages. Can't be null.
     * @param sessionStatusTracker chat session status tracker used to retrieve/change the session status. Can't be
     *            null.
     * @param logger logger instance used to log the operations. Can be null.
     * @param namespace namespace used to load the acknowledge time value. Can't be null or empty.
     * @exception IllegalArgumentException if any of the arguments except the logger is null, or the namespace is empty.
     * @throws IMConfigurationException if the configuration is incorrect.
     */
    public UserSessionEventListener(ChatSessionManager sessionManager, Messenger messenger,
        ChatSessionStatusTracker sessionStatusTracker, IMLogger logger, String namespace, ChatUserProfileManager userManager)
        throws IMConfigurationException {
        this(sessionManager, messenger, sessionStatusTracker, getAckTime(namespace), logger, userManager);
    }

    /**
     * Get acknowledge time from given namespace.
     * 
     * @param namespace namespace to get acknowledage time
     * @return acknowledge time from given namespace
     * @throws IMConfigurationException if it fail to get the time
     */
    private static long getAckTime(String namespace) throws IMConfigurationException {
        IMHelper.checkString(namespace, "namespace");
        long res;
        try {
            res = Long.parseLong(IMHelper.getRequiredConfigString(namespace, "acknowledge_time"));
        } catch (NumberFormatException e) {
            throw new IMConfigurationException("Invalid integer string.", e);
        }
        if (res < 0) {
            throw new IMConfigurationException("Acknowledge time can not be negative.");
        }
        return res;
    }

    /**
     * <p>
     * Sends ask for chat message to the user when the user is requested.
     * </p>
     * <p>
     * Any exception except IllegalArgumentException are ignored.
     * </p>
     * 
     * @param session ChatSession instance, null impossible
     * @param user user id, any possible long value
     * @throws IllegalArgumentException if the session is null.
     */
    public void userRequested(ChatSession session, long user) {
        IMHelper.checkNull(session, "session");
        logger.log(Level.DEBUG, "requested session [" + session.getId() + "],  user  [" + user + "].");

        try {
            AskForChatMessage msg = new AskForChatMessage();
            // timestamp is initialized in the constructor automatically.
            // set chat session id.
            msg.setChatSessionId(session.getId());
            // set the sender of the message to be system.
            
            long createdUser = session.getCreatedUser();
            Map properties = getProperties(createdUser);
            msg.setAuthorProfileProperties(properties);
            msg.setRequestUserId(createdUser);
            
            msg.setSender(new Long(-1));
            // set session creation time.
            msg.setSessionCreationTime(new Date());
            // set acknowledge time.
            msg.setAcknowledgeTime(new Date(System.currentTimeMillis() + acknowledgeTime));

            IMHelper.postMessage(messenger, msg, user, logger);            
        } catch (Exception e) {
            if (logger != null) {
                e.printStackTrace();
                logger.log(Level.ERROR, "Fail to handle the user requested event: " + e.getMessage());
            }
        }
    }

    private Map getProperties(long createdUser) throws ProfileNotFoundException, ProfileKeyNotFoundException, ProfileKeyManagerPersistenceException, ChatUserProfilePersistenceException, UnrecognizedDataSourceTypeException {
        ChatUserProfile profile = chatUserProfileManager.getProfile(createdUser);
        Map properties = new HashMap();
        String[] propertyNames = profile.getPropertyNames();
        for (int i = 0; i < propertyNames.length; i++) {
            String propertyName = propertyNames[i];
            properties.put(propertyName, profile.getPropertyValue(propertyName));
        }
        return properties;
    }

    /**
     * <p>
     * This method deals with the event that a user is added to the chat session.
     * </p>
     * <p>
     * The user is registered to the message pool.
     * </p>
     * <p>
     * Presence message of this user is sent to the other users in this session.
     * </p>
     * <p>
     * Any exception except IllegalArgumentException are ignored.
     * </p>
     * 
     * @param session ChatSession instance, null impossible
     * @param user user id, any possible long value
     * @throws IllegalArgumentException if the session is null.
     */
    public void userAdded(ChatSession session, long user) {
        IMHelper.checkNull(session, "session");
        logger.log(Level.DEBUG, "userAdded session [" + session.getId() + "],  user  [" + user + "].");
        
        try {
            if (chatSessionStatusTracker.getStatus(session.getId()).getId() == IMHelper.SESSION_STATUS_OPEN) {
                messenger.getMessagePool().register(user, session.getId());
                if (logger != null) {
                    logger.log(Level.INFO, "Register MessagePool for User", new String[] {"User - " + user,
                        "Session - " + session.getId()});
                }

                // post user presence message to other users in this session.
                PresenceMessage presenceMsg = new PresenceMessage();
                // set presence to be true.
                presenceMsg.setPresent(true);
                // set sender to be the current user.
                presenceMsg.setSender(new Long(user));
                Map properties = getProperties(user);
                presenceMsg.setChatProfileProperties(properties);
                presenceMsg.setChatSessionId(session.getId());
                // timestamp is initialized in the constructor automatically.
                // send message to others in the session.
                IMHelper.postMessageToOthers(messenger, presenceMsg, session, logger);
                if (logger != null) {
                    logger.log(Level.INFO, "Send PresenceMessage of this User to Others", new String[] {
                        "User - " + user, "Session - " + session.getId()});
                }

                // send presence message of other users in this session to the newly added user.
                long[] users = session.getActiveUsers();
                for (int i = 0; i < users.length; i++) {
                    if (users[i] != user) {
                        presenceMsg = new PresenceMessage();
                        // set presence to be true.
                        presenceMsg.setPresent(true);
                        // set sender to be the current userId.
                        properties = getProperties(users[i]);
                        presenceMsg.setChatProfileProperties(properties);
                        presenceMsg.setSender(new Long(users[i]));
                        presenceMsg.setChatSessionId(session.getId());
                        // send message to user.
                        IMHelper.postMessage(messenger, presenceMsg, user, session.getId(), logger);
                    }
                }
                
                if (logger != null) {
                    logger.log(Level.INFO, "Send PresenceMessage of Other Users to this User", new String[] {
                        "User - " + user, "Session - " + session.getId()});
                }
            }
        } catch (Exception e) {
            if (logger != null) {
                e.printStackTrace();
                logger.log(Level.ERROR, "Fail to handle the user added event: " + e.getMessage());
            }
        }
    }

    /**
     * <p>
     * This method deals with the event that a user is removed from the chat session.
     * </p>
     * <p>
     * The user is un-registered from the message pool.
     * </p>
     * <p>
     * Not-presence message of this user is sent to the other users in this session.
     * </p>
     * <p>
     * Any exception except IllegalArgumentException are ignored.
     * </p>
     * 
     * 
     * @param session ChatSession instance, null impossible
     * @param user user id, any possible long value
     * @throws IllegalArgumentException if the session is null.
     */
    public void userRemoved(ChatSession session, long user) {
        IMHelper.checkNull(session, "session");
        logger.log(Level.DEBUG, "userRemoved session [" + session.getId() + "],  user  [" + user + "].");

        try {
            if (chatSessionStatusTracker.getStatus(session.getId()).getId() == IMHelper.SESSION_STATUS_OPEN) {
                // retry unregister operation for a few times,
                // since other messages may be posted into the pool
                for (int retry = 0;; retry++) {
                    try {
                        messenger.getMessagePool().unregister(user, session.getId());
                        break;
                    } catch (Exception e) {
                        if (retry == 5) {
                            throw e;
                        }
                    }
                }
                if (logger != null) {
                    logger.log(Level.INFO, "Un-register MessagePool for User", new String[] {"User - " + user,
                        "Session - " + session.getId()});
                }

                // post user not-presence message to other users.
                PresenceMessage presenceMsg = new PresenceMessage();
                presenceMsg.setPresent(false);
                presenceMsg.setSender(new Long(user));
                Map properties = getProperties(user);
                presenceMsg.setChatProfileProperties(properties);
                presenceMsg.setChatSessionId(session.getId());
                IMHelper.postMessageToOthers(messenger, presenceMsg, session, logger);

                if (logger != null) {
                    logger.log(Level.INFO, "Send Not-PresenceMessage of this User to Others", new String[] {
                        "User - " + user, "Session - " + session.getId()});
                }
            }

            // For all session status, if all users are removed, close the session.

            // Note: the "user requested" is not considered, i.e. the requested user will
            // not block this method to change the status of chat session.
            // For when the user is removed from the requested users, no action
            // is performed to set the session to be CLOSE.
            // Then if we consider the "user requested", when the "user requested"
            // is removed from the session, the session will never change to CLOSE.
            if (session.getActiveUsers().length == 0) {
                // 204 - CLOSE
                Status closeStatus = new Status(IMHelper.SESSION_STATUS_CLOSE);
                chatSessionStatusTracker.setStatus(session.getId(), closeStatus);
                if (logger != null) {
                    logger.log(Level.INFO, "Close the ChatSession", new String[] {"Session - " + session.getId()});
                }
            }
        } catch (Exception e) {
            if (logger != null) {
                e.printStackTrace();
                logger.log(Level.ERROR, "Fail to handle the user removed event: " + e.getMessage());
            }
        }
    }

    /**
     * <p>
     * Gets messenger used to send messengers in this listener.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * 
     * @return message pool instance used in this listener.
     */
    public Messenger getMessenger() {
        return this.messenger;
    }

    /**
     * <p>
     * Gets ChatSessionManager where this event listener belongs to.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * 
     * @return chat profile manager instance.
     */
    public ChatSessionManager getChatSessionManager() {
        return this.chatSessionManager;
    }

    /**
     * <p>
     * Gets chat session status tracker instance used in this event listener.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * 
     * @return chat session status tracker instance used in this event listener.
     */
    public ChatSessionStatusTracker getChatSessionStatusTracker() {
        return this.chatSessionStatusTracker;
    }

    /**
     * <p>
     * Returns acknowledge time value in milliseconds.
     * </p>
     * <p>
     * The returned vlaue will be not-negative.
     * </p>
     * 
     * 
     * @return acknowledge time in milliseconds
     */
    public long getAcknowledgeTime() {
        return this.acknowledgeTime;
    }
}
