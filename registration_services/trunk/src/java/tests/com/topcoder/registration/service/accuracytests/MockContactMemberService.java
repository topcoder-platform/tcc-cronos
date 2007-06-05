/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import com.topcoder.registration.contactmember.service.ContactMemberService;
import com.topcoder.registration.contactmember.service.Message;
import com.topcoder.registration.contactmember.service.SendMessageResult;

/**
 * <p>
 * Mock implementation of ContactMemberService.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class MockContactMemberService implements ContactMemberService {

    /**
     * @see com.topcoder.registration.contactmember.service.ContactMemberService#sendMessage
     *      (com.topcoder.registration.contactmember.service.Message)
     */
    public SendMessageResult sendMessage(Message message) {
        return null;
    }
}
