/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;

/**
 * <p>
 *  This interface represents the ClientService web service endpoint interface.
 *  This interface defines the methods available for the ClientService web service:
 *  create, update and delete client, set code name for client and set client status for client.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  Implementations of this interface should be thread safe.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@WebService
public interface ClientService {

    /**
     * This static final String field represents the 'BEAN_NAME' property of
     * the ClientService web service endpoint interface.
     * Represents the EJB session bean name.
     */
    public static final String BEAN_NAME = "ClientServiceBean";

    /**
     * Defines the operation that performs the creation of the given client in the persistence.
     *
     * @param client
     *      the client that should be created. Should not be null.
     * @return the client created in the persistence.
     *
     * @throws IllegalArgumentException
     *      if the client is null.
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ClientServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Client createClient(Client client)
        throws AuthorizationFailedException, ClientServiceException;

    /**
     * Defines the operation that performs the update of the given client in the persistence.
     *
     * @param client
     *      the client that should be updated. Should not be null.
     * @return the client updated in the persistence.
     *
     * @throws IllegalArgumentException
     *      if the client is null.
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ClientServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Client updateClient(Client client)
        throws AuthorizationFailedException, ClientServiceException;

    /**
     * Defines the operation that performs the deletion of the given client from the persistence.
     *
     * @param client
     *      the client that should be deleted. Should not be null.
     * @return the client deleted from the persistence.
     *
     * @throws IllegalArgumentException
     *      if the client is null.
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ClientServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Client deleteClient(Client client)
        throws AuthorizationFailedException, ClientServiceException;

    /**
     * Defines the operation that performs the set
     * of the codeName for the given client in the persistence.
     *
     * @param client
     *      the client for that should be set the codeName. Should not be null.
     * @param name
     *      the codeName that should be set for the client. Should not be null or empty String.
     * @return the client for that the codeName was set in the persistence.
     *
     * @throws IllegalArgumentException
     *      if the client or codeName is null or codeName is empty String.
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ClientServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Client setClientCodeName(Client client, String name)
        throws AuthorizationFailedException, ClientServiceException;

    /**
     * Defines the operation that performs the set of the client status
     * for the given client in the persistence.
     *
     * @param status
     *      the client for that should be set the client status. Should not be null.
     * @param client
     *      the client status that should be set for the client. Should not be null.
     * @return the client for that the client status was set in the persistence.
     *
     * @throws IllegalArgumentException
     *      if the client or client status is null.
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ClientServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Client setClientStatus(Client client, ClientStatus status)
        throws AuthorizationFailedException, ClientServiceException;
}

