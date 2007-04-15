/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.stresstests;

import com.topcoder.database.statustracker.Status;
import com.topcoder.chat.status.ChatStatusTracker;
import com.topcoder.chat.status.ChatStatusEventListener;

/**
 * <p>
 * Mock class for ChatStatusTracker implementation.
 * </p>
 * 
 * @author 80x86
 * @version 1.0
 */
public class MockChatStatusTracker implements ChatStatusTracker {

    /**
     * <p>
     * Sets the status of a concrete entity which has the given id to the given status.
     * </p>
     * 
     * @param id
     *            id of the concrete entity whose status is about to set.
     * @param status
     *            the new status to set.
     * @throws ChatStatusTrackerStatusChangeValidationException
     *             if validation of status change fails.
     * @throws ChatStatusTrackerStatusDuplicationException
     *             if current status is the same as new status to set.
     * @throws ChatStatusTrackerStatusTrackingException
     *             if any error occurs when setting the status.
     * @throws ChatStatusTrackerUnknownStatusException
     *             if setting status is not one of the valid statuses of the concrete entity.
     * @throws IllegalArgumentException
     *             if parameters status is null.
     */
    public void setStatus(long id, Status status) {
    }

    /**
     * <p>
     * Sets the status of a concrete entity which has the given id to the given status. The given userName
     * will be used as the update user when setting.
     * </p>
     * 
     * @param id
     *            id of the concrete entity whose status is about to set.
     * @param status
     *            the new status to set.
     * @param userName
     *            the name of the user who is changing the status.
     * @throws ChatStatusTrackerStatusChangeValidationException
     *             if validation of status change fails.
     * @throws ChatStatusTrackerStatusDuplicationException
     *             if current status is the same as new status to set.
     * @throws ChatStatusTrackerStatusTrackingException
     *             if any error occurs when setting the status.
     * @throws ChatStatusTrackerUnknownStatusException
     *             if the status is not one of the valid statuses of the entity.
     * @throws IllegalArgumentException
     *             if parameters status or userName are null, or if userName is empty string.
     */
    public void setStatus(long id, Status status, String userName) {
    }

    /**
     * <p>
     * Gets the current status of the entity specified by the given id.
     * </p>
     * 
     * @param id
     *            id of the entity.
     * @return the <code>Status</code> instance which represents the current status of entity.
     * @throws ChatStatusTrackerStatusTrackingException
     *             if fails to retrieve current status of the entity specified by the id or the entity does
     *             not have any status.
     */
    public Status getStatus(long id) {
        return null;
    }

    /**
     * <p>
     * Gets all the current statuses of the entities specified by an array of entity ids.
     * </p>
     * 
     * @param ids
     *            an array which contains id of entities.
     * @return array an array of Status instances.
     * @throws ChatStatusTrackerStatusTrackingException
     *             if fails to retrieve current status for some entity ids in <code>ids</code>.
     * @throws IllegalArgumentException
     *             if <code>ids</code> is null.
     */
    public Status[] getStatuses(long[] ids) {
        return null;
    }

    /**
     * <p>
     * Gets all the entities of the given status. This methods will return an array which contains ids of
     * entities whose current status is the same as given <code>status</code>.
     * </p>
     * 
     * @param status
     *            the Status instance used for finding.
     * @return an array which contains ids of entities whose current status is the same as given status.
     * @throws ChatStatusTrackerStatusTrackingException
     *             if error occurs when finding by status.
     * @throws ChatStatusTrackerUnknownStatusException
     *             if current status id is not a valid status for the entity.
     * @throws IllegalArgumentException
     *             if status is null.
     */
    public long[] findByStatus(Status status) {
        return null;
    }

    /**
     * <p>
     * Adds the specified listener into status tracker to receive notification about status change.
     * </p>
     * 
     * @param listener
     *            the <code>ChatStatusEventListener</code> instance to add.
     * @throws IllegalArgumentException
     *             if <code>listener</code> is null
     */
    public void addChatStatusEventListener(ChatStatusEventListener listener) {
    }

    /**
     * <p>
     * Removes the specified listener from status tracker. If the listener does not exist, nothing happens.
     * </p>
     * 
     * @param listener
     *            the <code>ChatStatusEventListener</code> instance to remove.
     * @throws IllegalArgumentException
     *             if <code>listener</code> is null.
     */
    public void removeChatStatusEventListener(ChatStatusEventListener listener) {
    }

    /**
     * <p>
     * Gets all the <code>ChatStatusEventListener</code> instances as an array.
     * </p>
     * 
     * @return an array of <code>ChatStatusEventListener</code> instances.
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
