/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;

import java.security.Principal;

/**
 * Mockup for custom Principal.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class MockPrincipal implements Principal {

    /**
     * Represents name of principal.
     */
    private String mName;

    /**
     * Constructor with given name argument.
     *
     * @param name
     *      The name of principal.
     */
    public MockPrincipal(String name) {
        mName = name;
    }

    /**
     * Getter for principal name.
     *
     * @return name of principal.
     */
    public String getName() {
        return mName;
    }
}