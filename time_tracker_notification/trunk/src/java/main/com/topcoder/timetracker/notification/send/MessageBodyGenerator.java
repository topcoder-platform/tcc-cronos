/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.send;


/**
 * <p>
 * This interface defines the contract of generating the email body from the contact name and given message.
 * </p>
 *
 * <p>
 * The implementation is required to be thread safe.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 1.0
 */
public interface MessageBodyGenerator {
    /**
     * Generate the email message body based on the contact name and message.
     *
     * @param contactName the contact name
     * @param msg the message
     *
     * @return the message body
     *
     * @throws IllegalArgumentException if argument is null
     * @throws MessageBodyGeneratorException if any error occurred
     */
    public String generateMessage(String contactName, String msg)
        throws MessageBodyGeneratorException;
}
