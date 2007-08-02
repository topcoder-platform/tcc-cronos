/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.validation.emailconfirmation.EMailConfirmationCallbackInterface;
import com.topcoder.validation.emailconfirmation.InvalidAddressException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * <p>This is an implementation of {@link EMailConfirmationCallbackInterface} to be utilized in context of <code>Orpheus
 * Game Server</code> for recording the results of user account confirmations. This class is introduced since neither
 * <code>Web Registration</code> component nor other existing components provide a class performing this activity.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusEmailConfirmationCallbackSupport implements EMailConfirmationCallbackInterface {

    /**
     * <p>A <code>String</code> providing the configuration namespace to be used for initializing the instances of this
     * class.</p>
     */
    public static final String NAMESPACE = OrpheusEmailConfirmationCallbackSupport.class.getName();

    /**
     * <p>A <code>String</code> providng the SQL command to be used for altering the <code>ANY_USER</code> database
     * table to reflect the result of user account confirmation.</p>
     */
    private static final String UPDATE_SQL = "UPDATE any_user SET is_active = ? WHERE e_mail = ?";

    /**
     * <p>A <code>String</code> providing the name of pre-configured connection to be used for connecting to target
     * database. If <code>null</code> a default connection will be used.</p>
     */
    private final String connectionName;

    /**
     * <p>A <code>DBConnectionFactory</code> to be used for establishing the connections to target database.</p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>Constructs new <code>OrpheusEmailConfirmationCallbackSupport</code> instance initialized based on the
     * configuration parameters provided by the {@link #NAMESPACE} configuration namespace.</p>
     *
     * @throws UnknownNamespaceException if the {@link #NAMESPACE} configuration namespace is not loaded.
     * @throws IllegalReferenceException if the configuration is invalid.
     * @throws SpecificationConfigurationException if the configuration is invalid.
     * @throws InvalidClassSpecificationException if the configuration is invalid.
     */
    public OrpheusEmailConfirmationCallbackSupport() throws UnknownNamespaceException, IllegalReferenceException,
                                                            SpecificationConfigurationException,
                                                            InvalidClassSpecificationException {
        ConfigManager configManager = ConfigManager.getInstance();
        String objFactorySpecNamespace = configManager.getString(NAMESPACE, "specNamespace");
        String dbFactoryKey = configManager.getString(NAMESPACE, "factoryKey");
        ObjectFactory objectFactory
            = new ObjectFactory(new ConfigManagerSpecificationFactory(objFactorySpecNamespace));
        this.connectionName = configManager.getString(NAMESPACE, "connectionName");
        this.connectionFactory = (DBConnectionFactory) objectFactory.createObject(dbFactoryKey);
    }

    /**
     * <p>Handles the event of a successful confirmation of specified email address.</p>
     *
     * @param address a <code>String</code> providing the e-mail address which has been confirmed.
     * @throws InvalidAddressException if an unrecoverable error prevents from successful execution of this operation.
     * @throws IllegalArgumentException if the given address is <code>null</code>.
     */
    public void processConfirmSuccess(String address) {
        if (address == null) {
            throw new IllegalArgumentException("The parameter [address] is NULL");
        }
        confirmReject(address, true);
    }

    /**
     * <p>Handles the event of a unsuccessful confirmation of specified email address.</p>
     *
     * @param address a <code>String</code> providing the e-mail address which has been confirmed.
     * @throws InvalidAddressException if an unrecoverable error prevents from successful execution of this operation.
     * @throws IllegalArgumentException if the given address is <code>null</code>.
     */
    public void processConfirmFailure(String address) {
        if (address == null) {
            throw new IllegalArgumentException("The parameter [address] is NULL");
        }
        confirmReject(address, false);
    }

    /**
     * <p>Updates the <code>ANY_USER</code> database table to reflect either successful or unsuccessful confirmation of
     * a user account matching the specified email address.</p>
     *
     * @param address a <code>String</code> providing the email address for account to be confirmed or rejected.
     * @param confirmed <code>true</code> if the account matching the specified email address is confirmed successfully;
     *        <code>false</code> otherwise.
     * @throws InvalidAddressException if the operation fails for unrecoverable reason.
     */
    private void confirmReject(String address, boolean confirmed) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = getConnection();
            stmt = connection.prepareStatement(UPDATE_SQL);
            if (confirmed) {
                stmt.setInt(1, 1);
            } else {
                stmt.setInt(1, 0);
            }
            stmt.setString(2, address);
            int updated = stmt.executeUpdate();
            if (updated != 1) {
                rollBack(connection);
                throw new InvalidAddressException("Could not successfully process confirmation result for email ["
                                                  + address + "]. Invalid number of rows have been updated in ANY_USER "
                                                  + "table : " + updated);
            }
            connection.commit();
        } catch (DBConnectionException e) {
            rollBack(connection);
            throw new InvalidAddressException("Could not successfully process confirmation result for email ["
                                              + address + "]. Could not connect to database successfully.", e);
        } catch (SQLException e) {
            rollBack(connection);
            throw new InvalidAddressException("Could not successfully process confirmation result for email ["
                                              + address + "]. Could not alter database successfully.", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * <p>Rolls back the transaction managed by the specified connection.</p>
     *
     * @param connection a <code>Connection</code> managing the transaction to be rolled back.
     * @throws InvalidAddressException if the transaction failed to roll back.
     */
    private void rollBack(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new InvalidAddressException("Could not roll the transaction back", e);
        }
    }

    /**
     * <p>Gets a new connection to target database. The returned connection is configured to have the auto-commit
     * feature switched off and the transaction isolation level preventing the "dirty" reads.</p>
     *
     * @return a <code>Connection</code> providing a new connection to target database.
     * @throws DBConnectionException if a connection could not be established.
     * @throws SQLException if the desired connection properties can not be set.
     */
    private Connection getConnection() throws DBConnectionException, SQLException {
        Connection connection;
        if (this.connectionName == null) {
            connection = this.connectionFactory.createConnection();
        } else {
            connection = this.connectionFactory.createConnection(this.connectionName);
        }
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return connection;
    }
}
