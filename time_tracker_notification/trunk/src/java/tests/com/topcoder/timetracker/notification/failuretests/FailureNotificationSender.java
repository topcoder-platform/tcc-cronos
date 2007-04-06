/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import com.topcoder.timetracker.notification.NotificationSender;
import com.topcoder.timetracker.notification.NotificationSendingException;

/**
 * <p>
 * mock implementation of NotificationSender, only for testing purpose.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class FailureNotificationSender implements NotificationSender {

    /**
     * <p>
     * mock method.
     * </p>
     */
    public void send(long arg0) throws NotificationSendingException {
    }

}
