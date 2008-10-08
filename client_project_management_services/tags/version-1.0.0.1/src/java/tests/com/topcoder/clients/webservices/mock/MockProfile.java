/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;

import com.topcoder.user.manager.profiles.BaseProfile;

/**
 * <p>
 * This is a simple extension of <code>BaseProfile</code>. It is just used for tests.
 * </p>
 * @author CaDenza
 * @version 1.0
 */
public class MockProfile extends BaseProfile {

    /**
     * <p>
     * Serial version uid for the Serializable class.
     * </p>
     */
    private static final long serialVersionUID = 8138507218499957664L;

    /**
     * <p>
     * Constructs an instance of this class.
     * </p>
     * @param type
     *        The profile type
     * @throws IllegalArgumentException
     *         If argument is <code>null</code> or empty
     */
    public MockProfile(String type) {
        super(type);
    }
}