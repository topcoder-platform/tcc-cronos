/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;


import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is used to indicate any error that occurs in ClientManager's business methods, for example, an error
 * thrown by underlying ClientDAO implementation will be caught and wrapped in this exception and re-thrown. It extends
 * from ManagerException.
 * </p>
 *
 * <p>
 * It has a field that can be used to carry a Client instance when the exception is constructed, user can retrieve the
 * client by related getter, it helps user diagnose the exception.
 * </p>
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public class ClientManagerException extends ManagerException {
    /**
     * Represents a Client instance. Typically, when an operation fails, the client entity it operates on will be
     * stored in this field to let the user diagnose the error.
     *
     * It's set in the constructor, can be null and has a getter.
     */
    private final Client client;

    /**
     * Represents a ClientStatus instance. Typically, when an operation fails, the client status entity it operates on
     * will be stored in this field to let the user diagnose the error.
     *
     * It's set in the constructor, can be null and has a getter.
     */
    private final ClientStatus clientStatus;

    /**
     * Constructs an instance of this exception with specified Client and ClientStatus object.
     *
     * @param client
     *            the Client instance associated with this error, can be null
     * @param clientStatus
     *            the ClientStatus associated with this error, can be null
     */
    public ClientManagerException(Client client, ClientStatus clientStatus) {
        this.client = client;
        this.clientStatus = clientStatus;
    }

    /**
     * Constructs an instance of this exception with given error message and Client and ClientStatus object.
     *
     * @param message
     *            the error message
     * @param client
     *            the Client instance associated with this error, can be null
     * @param clientStatus
     *            the ClientStatus associated with this error, can be null
     *
     */
    public ClientManagerException(String message, Client client, ClientStatus clientStatus) {
        super(message);

        this.client = client;
        this.clientStatus = clientStatus;
    }

    /**
     * Constructs an instance of this exception with given error message, inner cause and Client and ClientStatus
     * object.
     *
     * @param message
     *            the error message
     * @param cause
     *            the inner error
     * @param client
     *            the Client instance associated with this error, can be null
     * @param clientStatus
     *            the ClientStatus associated with this error, can be null
     */
    public ClientManagerException(String message, Throwable cause, Client client, ClientStatus clientStatus) {
        super(message, cause);
        this.client = client;
        this.clientStatus = clientStatus;
    }

    /**
     * Constructs an instance of this exception with given error message, inner cause, exception data and Client and
     * ClientStatus object.
     *
     *
     * @param message
     *            the error message
     * @param cause
     *            the inner error
     * @param client
     *            the Client instance associated with this error, can be null
     * @param clientStatus
     *            associated with this error, can be null
     * @param exceptionData
     *            an object carrying the details of this exception
     */
    public ClientManagerException(String message, Throwable cause, ExceptionData exceptionData, Client client,
            ClientStatus clientStatus) {
        super(message, cause, exceptionData);

        this.client = client;
        this.clientStatus = clientStatus;
    }

    /**
     * Gets the Client instance related to this exception.
     *
     * @return a Client instance, can be null
     */
    public Client getClient() {
        return client;
    }

    /**
     * Gets the ClientStatus instance related to this exception.
     *
     * @return a ClientStatus instance, can be null
     */
    public ClientStatus getClientStatus() {
        return clientStatus;
    }
}
