/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;

import javax.security.auth.callback.PasswordCallback;

/**
 * The mockup of PasswordCallback.
 *
 * @author  CaDenza
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockPasswordCallback extends PasswordCallback {

    /**
     * Default constructor.
     *
     * @param prompt
     *      the prompt used to request the password
     * @param password
     *      The user password.
     */
    public MockPasswordCallback(String prompt, String password) {
        super(prompt, true);
        super.setPassword(password.toCharArray());
    }
}
