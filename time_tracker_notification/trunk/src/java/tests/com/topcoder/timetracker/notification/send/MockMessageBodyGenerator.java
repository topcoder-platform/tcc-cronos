/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.send;


/**
 * Mock message generator.
 *
 * @author kzhu
 * @version 3.2
 */
public class MockMessageBodyGenerator implements MessageBodyGenerator {
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
        throws MessageBodyGeneratorException {
        return "message";
    }
}