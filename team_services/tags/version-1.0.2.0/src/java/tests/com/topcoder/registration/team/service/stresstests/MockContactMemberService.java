/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.stresstests;

import com.topcoder.registration.contactmember.service.ContactMemberService;
import com.topcoder.registration.contactmember.service.Message;
import com.topcoder.registration.contactmember.service.SendMessageResult;

/**
 * <p>
 * This is a mock implementation of <code>ContactMemberService</code> interface used
 * in testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContactMemberService implements ContactMemberService {

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public MockContactMemberService() {
    }

    /**
     * <p>
     * Sends the message to the recipients in that message.
     * </p>
     *
     * @param message Message with the message and its recipients
     * @return null
     */
    public SendMessageResult sendMessage(Message message) {
        return null;
    }
}
