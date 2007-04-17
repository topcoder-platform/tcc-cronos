/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.informix.jdbc.IfxSqliConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * <p>
 * This is a wrapper of <code>IfxSqliConnect</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class WrappedConnection extends IfxSqliConnect {
    /**
     * <p>
     * Underlying connection.
     * </p>
     */
    private final IfxSqliConnect conn;

    /**
     * <p>
     * Constructor.
     * </p>
     *
     * @param conn Underlying connection.
     * @throws SQLException to JUnit
     */
    public WrappedConnection(IfxSqliConnect conn) throws SQLException {
        this.conn = conn;
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     *
     * @throws SQLException to JUnit
     */
    public void commit() throws SQLException {
        return;
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     *
     * @throws SQLException to JUnit
     */
    public void rollback() throws SQLException {
        return;
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     *
     * @param auto Auto commit mode
     *
     * @throws SQLException to JUnit
     */
    public void setAutoCommit(boolean auto) throws SQLException {
        return;
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     *
     * @throws SQLException to JUnit
     */
    public void close() throws SQLException {
        return;
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     *
     * @param isolation level
     *
     * @throws SQLException to JUnit
     */
    public void setTransactionIsolation(int isolation) throws SQLException {
        return;
    }

    /**
     * <p>
     * Create statement. Delegate to wrapped <code>IfxSqliConnect</code>.
     * </p>
     *
     * @return statement created.
     *
     * @throws SQLException to JUnit
     */
    public Statement createStatement() throws SQLException {
        return this.conn.createStatement();
    }

    /**
     * <p>
     * Prepare statement. Delegate to wrapped <code>IfxSqliConnect</code>.
     * </p>
     *
     * @param query The SQL clause to prepare
     *
     * @return statement prepared.
     *
     * @throws SQLException to JUnit
     */
    public PreparedStatement prepareStatement(String query)
        throws SQLException {
        return this.conn.prepareStatement(query);
    }

    /**
     * <p>
     * Get underlying <code>IfxSqliConnect</code>.
     * </p>
     *
     * @return underlying <code>IfxSqliConnect</code>.
     */
    public Connection getUnderlineConnection() {
        return this.conn;
    }
}
