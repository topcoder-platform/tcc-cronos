/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.topcoder.clients.model.ClientStatus;

/**
 * <p>
 * This interface represents the <code>ClientStatusService</code> web service endpoint interface. This
 * interface defines the methods available for the <code>ClientStatusService</code> web service: create,
 * update and delete client status.
 * </p>
 * <p>
 * Thread safety: Implementations of this interface should be thread safe. Thread safety should be provided
 * technically or by EJB container.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@WebService(name = "ClientStatusService")
public interface ClientStatusService {
    /**
     * <p>
     * This static final String field represents the 'BEAN_NAME' property of the ClientStatusService web
     * service endpoint interface. Represents the EJB session bean name. It is initialized to a default value:
     * "ClientStatusServiceBean" String during runtime. Accessed directly, it is public. Can not be changed.
     * It is constant.
     * </p>
     */
    public static final String BEAN_NAME = "ClientStatusServiceBean";

    /**
     * <p>
     * Defines the operation that performs the creation of the given client status in the persistence.
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
    @WebMethod
    public ClientStatus createClientStatus(ClientStatus status) throws ClientStatusServiceException;

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
    @WebMethod
    public ClientStatus updateClientStatus(ClientStatus status) throws ClientStatusServiceException;

    /**
     * <p>
     * Defines the operation that performs the deletion of the given client status from the persistence.
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
    @WebMethod
    public ClientStatus deleteClientStatus(ClientStatus status) throws ClientStatusServiceException;
}
