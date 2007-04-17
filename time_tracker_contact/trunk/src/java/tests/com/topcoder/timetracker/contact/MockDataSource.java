/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.informix.jdbc.IfxSqliConnect;

import com.informix.jdbcx.IfxDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mockejb.TransactionManager;


/**
 * <p>
 * This is a mock data source inherited from <code>IfxDataSource</code>.
 * If current transaction is active, it returns a <code>WrappedConnection</code>,
 * otherwise it returns a JDBC <code>IfxSqliConnect</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockDataSource extends IfxDataSource {
    /**
     * <p>
     * Serail Version UID.
     * </p>
     */
    private static final long serialVersionUID = 8384265021467973890L;

    /**
     * <p>
     * The last JDBC connection created.
     * </p>
     */
    private final List lastJDBCConnections = new ArrayList();
    /**
     * <p>
     * Constructor. Simply delegate to super.
     * </p>
     *
     * @throws SQLException to JUnit
     */
    public MockDataSource()
        throws SQLException {
        super();
    }

    /**
     * <p>
     * Close the last JDBC connection created.
     * </p>
     */
    public void closeLastJDBCConnections() {
        if (lastJDBCConnections.size() == 0) {
            return;
        }
        for (int i = 0; i < lastJDBCConnections.size(); i++) {
            Connection lastJDBCConnection = (Connection) lastJDBCConnections.get(i);
            try {
                lastJDBCConnection.close();
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
            lastJDBCConnections.remove(lastJDBCConnection);
            i--;
        }
    }

    /**
     * <p>
     * Gets a JDBC <code>IfxSqliConnect</code>.
     * </p>
     * @return <code>IfxSqliConnect</code>.
     * @throws SQLException to JUnit
     */
    public Connection getJDBCConnection() throws SQLException {
        Connection con = (IfxSqliConnect) super.getConnection();
        lastJDBCConnections.add(con);
        return con;
    }

    /**
     * <p>
     * Get connection. Override the existing method to ensure all work within the local transaction
     * occurs over the same <code>WrappedConnection</code>. If the transaction is not active, return
     * an <code>IfxSqliConnect</code>.
     * </p>
     *
     * @return connection.
     *
     * @throws SQLException to JUnit
     */
    public Connection getConnection() throws SQLException {
        MockTransaction transaction = (MockTransaction) TransactionManager.getUserTransaction();
        if (!transaction.isActive()) {
            return getJDBCConnection();
        }
        WrappedConnection wrappedCon = transaction.getConnection();
        if (wrappedCon != null) {
            return wrappedCon;
        }
        IfxSqliConnect conn = (IfxSqliConnect) super.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        wrappedCon = new WrappedConnection(conn);

        transaction.setConnection(wrappedCon);

        return wrappedCon;
    }
}
