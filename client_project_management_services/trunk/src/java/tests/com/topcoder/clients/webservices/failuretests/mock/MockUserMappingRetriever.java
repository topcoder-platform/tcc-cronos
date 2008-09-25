/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests.mock;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.usermapping.UserMappingRetrievalException;
import com.topcoder.clients.webservices.usermapping.UserMappingRetriever;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Mock implementation of the UserMappingRetriever interface.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class MockUserMappingRetriever implements UserMappingRetriever {
    /**
     * <p>
     * If set to true, operations will throw UserMappingRetrievalException.
     * </p>
     */
    private static boolean isFail = false;

    /**
     * <p>
     * If set to true, operations will throw UserMappingRetrievalException.
     * </p>
     *
     * @param f flag value
     */
    public static void setFail(boolean f) {
        isFail = f;
    }

    /**
     * <p>
     * Gets clients for given userId.
     * </p>
     *
     * @param userId user ID
     *
     * @return clients for given userId
     *
     * @throws UserMappingRetrievalException if the isFail is true
     */
    public List<Client> getClientsForUserId(long userId)
        throws UserMappingRetrievalException {
        checkFail();

        return new ArrayList<Client>();
    }

    /**
     * <p>
     * Gets projects for given userId.
     * </p>
     *
     * @param userId user ID
     *
     * @return projects for given userId
     *
     * @throws UserMappingRetrievalException if the isFail is true
     */
    public List<Project> getProjectsForUserId(long userId)
        throws UserMappingRetrievalException {
        checkFail();

        return new ArrayList<Project>();
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param client not used
     *
     * @return null
     *
     * @throws UserMappingRetrievalException if the isFail is true
     */
    public List<Long> getValidUsersForClient(Client client)
        throws UserMappingRetrievalException {
        checkFail();

        return new ArrayList<Long>();
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param project not used
     *
     * @return null
     *
     * @throws UserMappingRetrievalException if the isFail is true
     */
    public List<Long> getValidUsersForProject(Project project)
        throws UserMappingRetrievalException {
        checkFail();

        return new ArrayList<Long>();
    }

    /**
     * <p>
     * Check the isFail flag value, if it is true throw UserMappingRetrievalException.
     * </p>
     *
     * @throws UserMappingRetrievalException if isFail is true
     */
    private static void checkFail() throws UserMappingRetrievalException {
        if (isFail) {
            throw new UserMappingRetrievalException("for test.");
        }
    }
}
