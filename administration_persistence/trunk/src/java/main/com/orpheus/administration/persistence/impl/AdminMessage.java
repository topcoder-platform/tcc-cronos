/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.topcoder.message.messenger.MessageAPI;

import java.util.Date;

/**
 * <p>Extension of the Messenger Framework's <code>MessageAPI</code> to support admin-message-specific attributes, as
 * defined in the <code>Message</code> interface. All five attributes are mandatory. This class plugs into the
 * <code>OrpheusMessengerPlugin</code>, but is translated to <code>Message</code> there.
 *
 * <p><strong>Thread Safety</strong>: This class is mutable and not thread-safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class AdminMessage extends MessageAPI {
    /**
     * <p>The name of the required <code>String</code> parameter representing the URI that will be the unique
     * identifier in the message.</p>
     */
    public static final String GUID = "guid";

    /**
     * <p>The name of the required <code>String</code> parameter representing the mesaage category.</p>
     */
    public static final String CATEGORY = "category";

    /**
     * <p>The name of the required <code>String</code> parameter representing the type of content of this
     * message.</p>
     */
    public static final String CONTENT_TYPE = "contentType";

    /**
     * <p>The name of the required <code>String</code> parameter representing the content of this message.</p>
     */
    public static final String CONTENT = "content";

    /**
     * <p>The name of the required <code>Date</code> parameter representing the date of this message.</p>
     */
    public static final String TIMESTAMP = "timestamp";

    /**
     * Creates a new <code>AdminMessage</code> with the required parameters.
     */
    public AdminMessage() {
        addRequiredParameter(GUID, String.class);
        addRequiredParameter(CATEGORY, String.class);
        addRequiredParameter(CONTENT_TYPE, String.class);
        addRequiredParameter(CONTENT, String.class);
        addRequiredParameter(TIMESTAMP, Date.class);
    }
}
