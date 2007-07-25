/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.UserRetrieval;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;

/**
 * <p>
 * Mock implementation of UserRetrieval.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class MockUserRetrieval implements UserRetrieval {
    /**
     * @see com.cronos.onlinereview.external.UserRetrieval#retrieveUser(long)
     */
    public ExternalUser retrieveUser(long id) throws RetrievalException {
        return new ExternalUserImpl(10, "handle", "firstName", "lastName", "handle@topcoder.com");
    }

    /**
     * @see com.cronos.onlinereview.external.UserRetrieval#retrieveUser(java.lang.String)
     */
    public ExternalUser retrieveUser(String handle) throws RetrievalException {
        return null;
    }

    /**
     * @see com.cronos.onlinereview.external.UserRetrieval#retrieveUsers(long[])
     */
    public ExternalUser[] retrieveUsers(long[] ids) throws RetrievalException {
        return null;
    }

    /**
     * @see com.cronos.onlinereview.external.UserRetrieval#retrieveUsers(java.lang.String[])
     */
    public ExternalUser[] retrieveUsers(String[] handles) throws RetrievalException {
        return null;
    }

    /**
     * @see com.cronos.onlinereview.external.UserRetrieval#retrieveUsersIgnoreCase(java.lang.String[])
     */
    public ExternalUser[] retrieveUsersIgnoreCase(String[] handles) throws RetrievalException {
        return null;
    }

    /**
     * @see com.cronos.onlinereview.external.UserRetrieval#retrieveUsersByName(java.lang.String,
     *      java.lang.String)
     */
    public ExternalUser[] retrieveUsersByName(String firstName, String lastName) throws RetrievalException {
        return null;
    }
}
