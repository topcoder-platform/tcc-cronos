/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.persistence;

import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.payments.amazonfps.AuthorizationNotFoundException;
import com.topcoder.payments.amazonfps.model.Authorization;

/**
 * <p>
 * The {@code AuthorizationPersistence} interface represents an authorization persistence. It defines methods for
 * creating, updating and retrieving authorizations in/from persistence.
 * </p>
 *
 * <strong>Thread Safety:</strong> Implementations of this interface are required to be thread safe when the following
 * conditions are met:
 * <ul>
 * <li>{@code configure()} method is called just once right after instantiation</li>
 * <li>entities passed to this interface are used by the caller in thread safe manner.</li>
 * </ul>
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public interface AuthorizationPersistence {
    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param configuration
     *              the configuration object
     *
     * @throws IllegalArgumentException
     *              if configuration is {@code null} (it is not thrown by implementations that don't use any
     *              configuration parameters)
     * @throws com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException
     *              if some error occurred when initializing an instance using the given configuration
     */
    public void configure(ConfigurationObject configuration);

    /**
     * Creates the authorization with the given parameters in persistence. Sets a generated authorization ID to the
     * provided {@code Authorization} instance.
     *
     * @param authorization
     *              the {@code Authorization} instance which defines authorization parameters
     *
     * @throws IllegalArgumentException
     *              if authorization is {@code null}
     * @throws IllegalStateException
     *              if persistence instance was not initialized with {@code configure()} method
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the persistence
     */
    public void createAuthorization(Authorization authorization) throws AuthorizationPersistenceException;

    /**
     * Updates the authorization parameters in persistence. The {@code Authorization} instance are not updated by
     * this method.
     *
     * @param authorization
     *              the {@code Authorization} instance which defines updated authorization parameters
     *
     * @throws IllegalArgumentException
     *              if authorization is {@code null}
     *              or {@code authorization.getId() <= 0}
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     * @throws AuthorizationNotFoundException
     *              if authorization with the specified ID doesn't exist
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the persistence
     */
    public void updateAuthorization(Authorization authorization)
        throws AuthorizationNotFoundException, AuthorizationPersistenceException;

    /**
     * Retrieves the authorization with the specified ID from persistence.
     *
     * @param authorizationId
     *              the ID of the authorization to be retrieved
     *
     * @return the retrieved authorization data ({@code null} if authorization with the specified ID doesn't exist)
     *
     * @throws IllegalArgumentException
     *              if {@code authorizationId <= 0}
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the persistence
     */
    public Authorization getAuthorization(long authorizationId) throws AuthorizationPersistenceException;

    /**
     * Retrieves all authorizations from persistence. Returns an empty list if none found.
     *
     * @return the retrieved authorizations (not {@code null}, doesn't contain {@code null})
     *
     * @throws IllegalStateException
     *              if persistence instance was not initialized properly with {@code configure()} method
     * @throws AuthorizationPersistenceException
     *               if some error occurred when accessing the persistence
     */
    public List<Authorization> getAllAuthorizations() throws AuthorizationPersistenceException;
}
