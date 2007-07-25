/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.impl;

import com.topcoder.registration.contactmember.service.ContactMemberService;
import com.topcoder.registration.contactmember.service.Message;
import com.topcoder.registration.contactmember.service.SendMessageResult;

/**
 * <p>
 * This is a mock implementation of <code>ContactMemberService</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContactMemberService implements ContactMemberService {

    /**
     * <p>
     * Constructs an instance.
     * </p>
     */
    public MockContactMemberService() {
    }

    /**
     * <p>
     * Sends the message to the recipients in that message.
     * </p>
     * @return SendMessageResult with the breakdown which recipients were successfully sent to, and
     *         which were not
     * @param message
     *            Message with the message and its recipients
     * @throws IllegalArgumentException
     *             If message is null
     */
    public SendMessageResult sendMessage(Message message) {
        return null;
    }
}
