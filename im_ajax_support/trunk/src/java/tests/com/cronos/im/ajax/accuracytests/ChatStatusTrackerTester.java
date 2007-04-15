/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import com.topcoder.chat.status.ChatStatusEventListener;
import com.topcoder.chat.status.ChatStatusTracker;

import com.topcoder.database.statustracker.Status;


/**
 * <p>
 * Mock ChatStatusTracker implementation.
 * </p>
 *
 * @author brain_cn
 * @version 1.0
 */
public class ChatStatusTrackerTester implements ChatStatusTracker {
    /**
     * <p>
     * Sets the status of a concrete entity which has the given id to the given status.
     * </p>
     *
     * @param id id of the concrete entity whose status is about to set.
     * @param status the new status to set.
     */
    public void setStatus(long id, Status status) {
    }

    /**
     * <p>
     * Sets the status of a concrete entity which has the given id to the given status. The given userName will be used
     * as the update user when setting.
     * </p>
     *
     * @param id id of the concrete entity whose status is about to set.
     * @param status the new status to set.
     * @param userName the name of the user who is changing the status.
     */
    public void setStatus(long id, Status status, String userName) {
    }

    /**
     * <p>
     * Gets the current status of the entity specified by the given id.
     * </p>
     *
     * @param id id of the entity.
     *
     * @return the <code>Status</code> instance which represents the current status of entity.
     */
    public Status getStatus(long id) {
        return null;
    }

    /**
     * <p>
     * Gets all the current statuses of the entities specified by an array of entity ids.
     * </p>
     *
     * @param ids an array which contains id of entities.
     *
     * @return array an array of Status instances.
     */
    public Status[] getStatuses(long[] ids) {
        return null;
    }

    /**
     * <p>
     * Gets all the entities of the given status. This methods will return an array which contains ids of entities
     * whose current status is the same as given <code>status</code>.
     * </p>
     *
     * @param status the Status instance used for finding.
     *
     * @return an array which contains ids of entities whose current status is the same as given status.
     */
    public long[] findByStatus(Status status) {
        return null;
    }

    /**
     * <p>
     * Adds the specified listener into status tracker to receive notification about status change.
     * </p>
     *
     * @param listener the <code>ChatStatusEventListener</code> instance to add.
     */
    public void addChatStatusEventListener(ChatStatusEventListener listener) {
    }

    /**
     * <p>
     * Removes the specified listener from status tracker. If the listener does not exist, nothing happens.
     * </p>
     *
     * @param listener the <code>ChatStatusEventListener</code> instance to remove.
     */
    public void removeChatStatusEventListener(ChatStatusEventListener listener) {
    }

    /**
     * <p>
     * Gets all the <code>ChatStatusEventListener</code> instances as an array.
     * </p>
     *
     * @return an array of <code>ChatStatusEventListener</code> instances.
     */
    public ChatStatusEventListener[] getChatStatusEventListeners() {
        return null;
    }

    /**
     * <p>
     * Removes all the <code>ChatStatusEventListener</code> instances.
     * </p>
     */
    public void clearChatStatusEventListeners() {
    }
}
