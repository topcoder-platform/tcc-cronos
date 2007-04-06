/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

/**
 * <p>
 * This interface defines the contract of sending the notification in some way like via email. The implementation is
 * required to be thread safe.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public interface NotificationSender {
    /**
     * Send the notification with given id.
     *
     * @param notificationId the notification id
     *
     * @throws NotificationSendingException if any error occurred
     */
    public void send(long notificationId) throws NotificationSendingException;
}
