/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import com.topcoder.clients.model.ClientStatus;

/**
 * <p>
 * Mock class for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientStatusService {
    /**
     * <p>
     * Defines the operation that performs the creation of the given client status in the
     * persistence.
     * </p>
     *
     * @param status
     *            the client status that should be created. Should not be null.
     * @return the client status created in the persistence.
     * @throws IllegalArgumentException
     *             if the client status is null.
     * @throws ClientStatusServiceException
     *             if any error occurs while performing this operation.
     */
    public ClientStatus createClientStatus(ClientStatus status) throws ClientStatusServiceException {
        return status;
    }

    /**
     * <p>
     * Defines the operation that performs the update of the given client status in the persistence.
     * </p>
     *
     * @param status
     *            the client status that should be updated. Should not be null.
     * @return the client status updated in the persistence.
     * @throws IllegalArgumentException
     *             if the client status is null.
     * @throws ClientStatusServiceException
     *             if any error occurs while performing this operation.
     */
    public ClientStatus updateClientStatus(ClientStatus status) throws ClientStatusServiceException {
        return status;
    }

    /**
     * <p>
     * Defines the operation that performs the deletion of the given client status from the
     * persistence.
     * </p>
     *
     * @param status
     *            the client status that should be deleted. Should not be null.
     * @return the client status deleted from the persistence.
     * @throws IllegalArgumentException
     *             if the client status is null.
     * @throws ClientStatusServiceException
     *             if any error occurs while performing this operation.
     */
    public ClientStatus deleteClientStatus(ClientStatus status) throws ClientStatusServiceException {
        return status;
    }
}
