/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;

import javax.security.auth.callback.NameCallback;

/**
 * Mockup namecallback. Only for testing purpose.
 *
 * @author  CaDenza
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockNameCallback extends NameCallback {

    /**
     * Default constructor.
     *
     * @param prompt
     *      the prompt used to request the name.
     * @param defaultName
     *      the name to be used as the default name displayed with the prompt.
     * @param loginUser
     *      The username handle.
     */
    public MockNameCallback(String prompt, String defaultName, String loginUser) {
        super(prompt, defaultName);
        super.setName(loginUser);
    }
}
