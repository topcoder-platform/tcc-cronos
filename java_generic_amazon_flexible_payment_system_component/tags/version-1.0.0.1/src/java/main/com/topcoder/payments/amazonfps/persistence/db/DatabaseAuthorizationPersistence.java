/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.persistence.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.commons.utils.JDBCUtility;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.AuthorizationNotFoundException;
import com.topcoder.payments.amazonfps.Helper;
import com.topcoder.payments.amazonfps.model.Authorization;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistence;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistenceException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;

/**
 * <p>
 * The {@code DatabaseAuthorizationPersistence} class is an implementation of {@code AuthorizationPersistence} that
 * manages authorization data in database persistence using <i>JDBC</i> and <i>DB Connection Factory</i> component.
 * This class uses <i>Logging Wrapper</i> component to log errors and debug information.
 * </p>
 *
 * <strong>Thread Safety: </strong> This class is mutable, but thread safe when {@code configure()} method is
 * called just once right after instantiation and entities passed to it are used by the caller in thread safe manner.
 * It uses thread safe {@code DBConnectionFactory} and {@code Log} instances.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public class DatabaseAuthorizationPersistence extends BaseDatabasePersistence implements AuthorizationPersistence {
    /**
     * Represents the class name.
     */
    private static final String CLASS_NAME = DatabaseAuthorizationPersistence.class.getName();

    /**
     * Constant for authorizationIdGeneratorName parameter name.
     */
    private static final String AUTHORIZATION_ID_GENERATOR_NAME_PARAMETER = "authorizationIdGeneratorName";

    /**
     * SQL for authorization create operation.
     */
    private static final String CREATE_AUTHORIZATION_SQL =
            "INSERT INTO authorizations (id, multiple_use, token_id, amount_left, fixed_amount, cancelled) "
          + "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * SQL for authorization update operation.
     */
    private static final String UPDATE_AUTHORIZATION_SQL =
            "UPDATE authorizations SET multiple_use = ?, token_id = ?, amount_left = ?, fixed_amount = ?, "
          + "cancelled = ? WHERE id = ?";

    /**
     * SQL for authorization retrieving operation.
     */
    private static final String GET_AUTHORIZATION_SQL =
            "SELECT multiple_use, token_id, amount_left, fixed_amount, cancelled FROM authorizations WHERE id = ?";

    /**
     * SQL for retrieving all authorizations.
     */
    private static final String GET_ALL_AUTHORIZATIONS_SQL =
            "SELECT id, multiple_use, token_id, amount_left, fixed_amount, cancelled FROM authorizations";


    /**
     * The generator of authorization IDs to be used in persistence. It is initialized in {@code configure()} and
     * never changed after that (assuming that {@code configure()} is called just once right after construction).
     * Cannot be {@code null} after initialization. It is used in {@code createAuthorization()} method.
     */
    private IDGenerator authorizationIdGenerator;

    /**
     * Constructs new {@code DatabaseAuthorizationPersistence} instance.
     */
    public DatabaseAuthorizationPersistence() {
        // Empty
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param configuration
     *            the configuration object
     *
     * @throws IllegalArgumentException
     *            if configuration is {@code null}
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *            if some error occurred when initializing an instance using the given configuration
     */
    @Override
    public void configure(ConfigurationObject configuration) {
        super.configure(configuration); // this also makes a check that configuration is not null

        try {
            // prepare id generator for authorizations
            String authorizationIdGeneratorName = Helper.getProperty(configuration,
                    AUTHORIZATION_ID_GENERATOR_NAME_PARAMETER, true);

            authorizationIdGenerator = IDGeneratorFactory.getIDGenerator(authorizationIdGeneratorName);

        } catch (IDGenerationException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "Error occurred while retrieving ID sequence configuration", e);
        }
    }

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
    @Override
    public void createAuthorization(Authorization authorization) throws AuthorizationPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".createAuthorization(Authorization authorization)";

        // log entrance and validate input parameters
        LoggingWrapperUtility.logEntrance(getLog(), signature,
                new String[] {"authorization"},
                new Object[] {String.valueOf(authorization)});

        try {
            ParameterCheckUtility.checkNotNull(authorization, "authorization");
            ValidationUtility.checkNotNull(authorizationIdGenerator, "authorizationIdGenerator",
                    IllegalStateException.class);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        }

        // create new authorization record in persistence
        Connection connection = null;
        try {
            long authorizationId = authorizationIdGenerator.getNextID();
            connection = createConnection();
            JDBCUtility.executeUpdate(connection, CREATE_AUTHORIZATION_SQL,
                    new int[] {
                        Types.DECIMAL,
                        Types.DECIMAL,
                        Types.VARCHAR,
                        Types.DECIMAL,
                        Types.DECIMAL,
                        Types.DECIMAL},
                    new Object[] {
                        authorizationId,
                        authorization.isMultipleUseAuthorization(),
                        authorization.getTokenId(),
                        authorization.getAuthorizedAmountLeft(),
                        authorization.getAuthorizedFixedFutureAmount(),
                        authorization.isCancelled()},
                    AuthorizationPersistenceException.class);

            // set authorization id to model object and log exit
            authorization.setId(authorizationId);
            LoggingWrapperUtility.logExit(getLog(), signature, null, entranceTimestamp);
        } catch (IDGenerationException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new AuthorizationPersistenceException(
                    "Failed to generate authorization ID", e), true, Level.ERROR);
        } catch (DBConnectionException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new AuthorizationPersistenceException(
                    "Failed to create database connection", e), true, Level.ERROR);
        } catch (AuthorizationPersistenceException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } finally {
            Helper.closeConnection(connection, getLog(), signature);
        }
    }

    /**
     * Updates the authorization parameters in persistence. The {@code Authorization} instance are not updated by
     * this method.
     *
     * @param authorization
     *              the {@code Authorization} instance which defines updated authorization parameters
     *
     * @throws IllegalArgumentException
     *              if authorization is {@code null} or {@code authorization.getId() <= 0}
     * @throws IllegalStateException
     *              if persistence instance was not initialized with {@code configure()} method
     * @throws AuthorizationNotFoundException
     *              if authorization with the specified ID doesn't exist
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the persistence
     */
    @Override
    public void updateAuthorization(Authorization authorization)
        throws AuthorizationNotFoundException, AuthorizationPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".updateAuthorization(Authorization authorization)";

        // log entrance and validate input parameters
        LoggingWrapperUtility.logEntrance(getLog(), signature,
                new String[] {"authorization"},
                new Object[] {String.valueOf(authorization)});

        try {
            ParameterCheckUtility.checkNotNull(authorization, "authorization");
            ValidationUtility.checkPositive(authorization.getId(), "authorization id", IllegalArgumentException.class);
            ValidationUtility.checkNotNull(authorizationIdGenerator, "authorizationIdGenerator",
                    IllegalStateException.class);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        }

        // update authorization record in persistence
        Connection connection = null;
        try {
            connection = createConnection();
            int result = JDBCUtility.executeUpdate(connection, UPDATE_AUTHORIZATION_SQL,
                new int[] {
                    Types.DECIMAL,
                    Types.VARCHAR,
                    Types.DECIMAL,
                    Types.DECIMAL,
                    Types.DECIMAL,
                    Types.DECIMAL},
                new Object[] {
                    authorization.isMultipleUseAuthorization(),
                    authorization.getTokenId(),
                    authorization.getAuthorizedAmountLeft(),
                    authorization.getAuthorizedFixedFutureAmount(),
                    authorization.isCancelled(),
                    authorization.getId()},
                AuthorizationPersistenceException.class);

            // check that record was updated
            if (result == 0) {
                throw LoggingWrapperUtility.logException(getLog(), signature, new AuthorizationNotFoundException(
                        "Authorization was not found", authorization.getId()), true, Level.ERROR);
            }
            // log exit
            LoggingWrapperUtility.logExit(getLog(), signature, null, entranceTimestamp);
        } catch (DBConnectionException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new AuthorizationPersistenceException(
                    "Failed to create database connection", e), true, Level.ERROR);
        } catch (AuthorizationPersistenceException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } finally {
            Helper.closeConnection(connection, getLog(), signature);
        }
    }

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
    @Override
    public Authorization getAuthorization(long authorizationId) throws AuthorizationPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".getAuthorization(long authorizationId)";

        // log entrance and validate input parameters
        LoggingWrapperUtility.logEntrance(getLog(), signature,
                new String[] {"authorizationId"}, new Object[] {authorizationId});

        try {
            ParameterCheckUtility.checkPositive(authorizationId, "authorizationId");
            ValidationUtility.checkNotNull(authorizationIdGenerator, "authorizationIdGenerator",
                    IllegalStateException.class);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        }

        // retrieve authorization record from persistence
        Connection connection = null;
        try {
            connection = createConnection();
            Object[][] result = JDBCUtility.executeQuery(connection, GET_AUTHORIZATION_SQL,
                    new int[] {Types.DECIMAL},
                    new Object[] {authorizationId},
                    new Class<?>[] {Boolean.class, String.class, Double.class, Double.class, Boolean.class},
                    AuthorizationPersistenceException.class);

            Authorization authorization = null;
            if (result.length > 0) {
                final int amountIndex = 2;
                final int fixedFutureAmountIndex = 3;
                final int cancelledIndex = 4;

                authorization = new Authorization();
                authorization.setId(authorizationId);
                authorization.setMultipleUseAuthorization((Boolean) result[0][0]);
                authorization.setTokenId((String) result[0][1]);
                authorization.setAuthorizedAmountLeft(BigDecimal.valueOf((Double) result[0][amountIndex]));
                Double fixedFutureAmount = (Double) result[0][fixedFutureAmountIndex];
                if (fixedFutureAmount != null) {
                    authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(fixedFutureAmount));
                }
                authorization.setCancelled((Boolean) result[0][cancelledIndex]);
            }
            // log exit
            LoggingWrapperUtility.logExit(getLog(), signature, new Object[] {authorization}, entranceTimestamp);
            return authorization;
        } catch (DBConnectionException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new AuthorizationPersistenceException(
                    "Failed to create database connection", e), true, Level.ERROR);
        } catch (AuthorizationPersistenceException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } finally {
            Helper.closeConnection(connection, getLog(), signature);
        }
    }

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
    @Override
    public List<Authorization> getAllAuthorizations() throws AuthorizationPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".getAllAuthorizations()";

        LoggingWrapperUtility.logEntrance(getLog(), signature, null, null);

        Connection connection = null;
        try {
            ValidationUtility.checkNotNull(authorizationIdGenerator, "authorizationIdGenerator",
                    IllegalStateException.class);

            connection = createConnection();
            Object[][] result = JDBCUtility.executeQuery(connection, GET_ALL_AUTHORIZATIONS_SQL,
                    new int[0], null,
                    new Class<?>[] {Long.class, Boolean.class, String.class, Double.class, Double.class, Boolean.class},
                    AuthorizationPersistenceException.class);

            final int multiUseIndex = 1;
            final int tokenIdIndex = 2;
            final int amountIndex = 3;
            final int fixedFutureAmountIndex = 4;
            final int cancelledIndex = 5;

            List<Authorization> authorizations = new ArrayList<Authorization>();
            for (Object[] row : result) {
                Authorization authorization = new Authorization();

                authorization.setId((Long) row[0]);
                authorization.setMultipleUseAuthorization((Boolean) row[multiUseIndex]);
                authorization.setTokenId((String) row[tokenIdIndex]);
                authorization.setAuthorizedAmountLeft(BigDecimal.valueOf((Double) row[amountIndex]));
                Double fixedFutureAmount = (Double) row[fixedFutureAmountIndex];
                if (fixedFutureAmount != null) {
                    authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(fixedFutureAmount));
                }
                authorization.setCancelled((Boolean) row[cancelledIndex]);

                authorizations.add(authorization);
            }

            LoggingWrapperUtility.logExit(getLog(), signature, new Object[] {Helper.toString(authorizations)},
                    entranceTimestamp);
            return authorizations;
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } catch (DBConnectionException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, new AuthorizationPersistenceException(
                    "Failed to create database connection", e), true, Level.ERROR);
        } catch (AuthorizationPersistenceException e) {
            throw LoggingWrapperUtility.logException(getLog(), signature, e, true, Level.ERROR);
        } finally {
            Helper.closeConnection(connection, getLog(), signature);
        }
    }
}
