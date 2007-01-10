/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import com.topcoder.message.messenger.MessageAPI;
import com.topcoder.message.messenger.MessageException;
import com.topcoder.message.messenger.MessengerPlugin;

/**
 * <p>
 * Mock implementation of MockMessageAPI, for test purpose.
 * </p>
 *
 * @author catcher
 * @version 1.0
 */
public class MockMessengerPlugin extends MessengerPlugin {

    /**
     * Create
     */
    public MessageAPI createMessage() {
        return new MockMessageAPI();
    }

    /**
     * <p>
     * not implemented.
     * </p>
     *
     * @param message
     *        arg0.
     * @throws MessageException
     *         MessageException
     * @throws Exception
     *         Exception
     */
    public void sendMessage(MessageAPI message) throws MessageException, Exception {

    }

    /**
     * <p>
     * not implemented.
     * </p>
     */
    protected Class getMessageType() {
        return null;
    }

}

