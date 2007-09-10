/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.status.ChatStatusEventListener;
import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.Status;
import com.topcoder.util.log.Level;

/**
 * <p>
 * This class implements ChatStatusEventListener. It is used to deal with the change event of user status.
 * When the user is changed from OFFLINE to ONLINE, a user message pool is registered for this user. When the
 * user is changed to OFFLINE, the user message pool is un-registered.
 * </p>
 * <p>
 * This listener can be plugged into "Chat Status Tracker" component to track the change event of user status.
 * </p>
 * <p>
 * Thread-safety: This class is thread-safe. This class is immutable and the dependencies of this class are
 * also thread-safe.
 * </p>
 * 
 * 
 * @author justforplay, TCSDEVELOPER
 * @version 1.0
 */
public class UserStatusEventListener implements ChatStatusEventListener {

    /**
     * <p>
     * Represents the message pool used to register or un-register the user. It is not null.
     * </p>
     */
    private final MessagePool messagePool;

    /**
     * <p>
     * Represents the logger instance of this event listener. If it is null, then no logging is done.
     * </p>
     */
    private final IMLogger logger;

    /**
     * <p>
     * Constructor to create UserStatusEventListener with given parameters.
     * </p>
     * @param messagePool
     *            message pool instance used to register or un-register the message pool. Can't be null.
     * @param logger
     *            logger instance used to log the operations. Can be null.
     * 
     * 
     * @exception IllegalArgumentException
     *                If the messagePool or userStatusTracker is null.
     */
    public UserStatusEventListener(MessagePool messagePool, IMLogger logger) {
        IMHelper.checkNull(messagePool, "messagePool");

        this.logger = logger;
        this.messagePool = messagePool;
    }

    /**
     * <p>
     * This method deals with the event of "User Status Changed". When the user is changed from OFFLINE to
     * ONLINE, a user message pool is registered for this user. When the user is changed to OFFLINE, the user
     * message pool is un-registered.
     * </p>
     * <p>
     * Logging is performed in this method for two main operation "Register Message Pool for User" and
     * "Un-register Messsage Pool for User" at INFO level.
     * </p>
     * <p>
     * Note: Any exceptions except IllegalArgumentException are ignored, but logging of the error message is
     * still performed.
     * </p>
     * 
     * @param id
     *            id of concrete entity, any possible int value
     * @param entity
     *            Entity instance, null impossible
     * @param oldStatus
     *            old status of entity, Status instance, null is possible
     * @param newStatus
     *            new status of entity, Status instance, null impossible;
     * @throws IllegalArgumentException
     *             if entity or newStatus parameters are null, or entity is not user entity
     */
    public void statusChanged(long id, Entity entity, Status oldStatus, Status newStatus) {
        IMHelper.checkNull(entity, "entity");
        IMHelper.checkNull(newStatus, "newStatus");
        if (entity.getId() != IMHelper.ENTITY_USER) {
            return;
        }
        
        logger.log(Level.DEBUG, "user status changed [" + id + "] to  [" + newStatus.getName() + "].");

        try {
            if (oldStatus == null && newStatus.getId() == IMHelper.USER_STATUS_ONLINE) {
                // 1. If oldStatus is "OFFLINE" and the newStatus is "ONLINE".
                messagePool.register(id);
                if (logger != null) {
                    logger.log(Level.INFO, "Register Message Pool for User", new String[] { "User - " + id });
                }
            } else if ((oldStatus.getId() == IMHelper.USER_STATUS_OFFLINE
                    && newStatus.getId() == IMHelper.USER_STATUS_ONLINE)) {
                    // 1. If oldStatus is "OFFLINE" and the newStatus is "ONLINE".
                    messagePool.register(id);
                    if (logger != null) {
                        logger.log(Level.INFO, "Register Message Pool for User", new String[] { "User - " + id });
                    }
            } else if (newStatus.getId() == IMHelper.USER_STATUS_OFFLINE) {
                // 2. If newStatus is "OFFLINE".
                // retry unregister operation for a few times,
                // since other messages may be posted into the pool
                for (int retry = 0;; retry++) {
                    try {
                        messagePool.unregister(id);
                        break;
                    } catch (Exception e) {
                        if (retry == 5) {
                            throw e;
                        }
                    }
                }
                if (logger != null) {
                    logger.log(Level.INFO, "Un-register Message Pool for User",
                            new String[] { "User - " + id });
                }
            }
        } catch (Exception e) {
            if (logger != null) {
                logger.log(Level.ERROR, "Fail to handle the user status changed event: " + e.getMessage());
            }
        }
    }

    /**
     * <p>
     * Gets the message pool used in this listener.
     * </p>
     * <p>
     * The return value will never be null.
     * </p>
     * 
     * 
     * @return message pool used in this instance
     */
    public MessagePool getMessagePool() {
        return this.messagePool;
    }

}
