package com.topcoder.clients.webservices;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;

public class ClientService {

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
    public Client createClient(Client client)
        throws AuthorizationFailedException, ClientServiceException{
            return client;
        }

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
    public Client updateClient(Client client)
        throws AuthorizationFailedException, ClientServiceException{
            return client;
        }

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
    public Client deleteClient(Client client)
        throws AuthorizationFailedException, ClientServiceException{
            return client;
        }

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
    public Client setClientCodeName(Client client, String name)
        throws AuthorizationFailedException, ClientServiceException{
            return client;
        }

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
    public Client setClientStatus(Client client, ClientStatus status)
        throws AuthorizationFailedException, ClientServiceException{
        return client;
    }
}
