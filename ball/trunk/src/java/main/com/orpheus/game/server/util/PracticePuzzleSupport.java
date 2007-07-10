/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * <p>Support class retrieving practice puzzle information from the database.</p>
 *
 * @author mtong
 * @version 1.0
 */
public class PracticePuzzleSupport {

    /**
     * <p>A <code>String</code> providing the configuration namespace to be used for initializing the instances of this
     * class.</p>
     */
    public static final String NAMESPACE = PracticePuzzleSupport.class.getName();

    /**
     * <p>A <code>String</code> providng the SQL command to be used for obtaining all practice puzzle IDs of a 
     * certain type (jigsaw, sliding tile, etc.).</p>
     */
    private static final String SELECT_PRACTICE_IDS_SQL = "SELECT id FROM puzzle WHERE name = ? AND practice = 1";

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
     * <p>Constructs new <code>PracticePuzzleSupport</code> instance initialized based on the
     * configuration parameters provided by the {@link #NAMESPACE} configuration namespace.</p>
     *
     * @throws UnknownNamespaceException if the {@link #NAMESPACE} configuration namespace is not loaded.
     * @throws IllegalReferenceException if the configuration is invalid.
     * @throws SpecificationConfigurationException if the configuration is invalid.
     * @throws InvalidClassSpecificationException if the configuration is invalid.
     */
    public PracticePuzzleSupport() throws UnknownNamespaceException, IllegalReferenceException,
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
     * <p>Updates the <code>ANY_USER</code> database table to reflect either successful or unsuccessful confirmation of
     * a user account matching the specified email address.</p>
     *
     * @param puzzleType a <code>String</code> with the puzzle type to obtain IDs for ("jigsaw", "sliding-tile", etc.).
     * @throws InvalidAddressException if the operation fails for unrecoverable reason.
     */
    public int[] selectPracticeIDs(String puzzleType) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = getConnection();
            stmt = connection.prepareStatement(SELECT_PRACTICE_IDS_SQL);
            stmt.setString(1, puzzleType);
            ResultSet rs = stmt.executeQuery();

            ArrayList idList = new ArrayList();
            while (rs.next()) {
                idList.add(new Integer(rs.getInt(0)));
            }
            int[] ids = new int[idList.size()];
            for (int i=0; i<ids.length; i++) {
                ids[i] = ((Integer)idList.get(i)).intValue();
            }
            return ids;
        } catch (DBConnectionException e) {
            System.out.println("Could not successfully obtain puzzle IDs for type [" + puzzleType + "]. " +
                    "Could not connect to database successfully.");
            return new int[0];
        } catch (SQLException e) {
            System.out.println("Could not successfully obtain puzzle IDs for type [" + puzzleType + "]. " +
                    "Could not alter database successfully.");
            return new int[0];
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
