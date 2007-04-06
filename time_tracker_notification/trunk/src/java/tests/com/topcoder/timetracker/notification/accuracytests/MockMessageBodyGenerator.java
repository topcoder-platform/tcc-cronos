/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import com.topcoder.timetracker.notification.send.MessageBodyGenerator;

/**
 * <p>
 * This is a mock implementation of <code>ContactManager</code> and it is used to help accuracy tests.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class MockMessageBodyGenerator implements MessageBodyGenerator {
    /**
     * <p>
     * Generates a message for the given contact name and message.
     * </p>
     *
     * @param contactName the contact name
     * @param msg the message
     *
     * @return a message for the given contact name and message.
     */
    public String generateMessage(String contactName, String msg) {
        return "The contact name is " + contactName + ", The message is " + msg;
    }
}