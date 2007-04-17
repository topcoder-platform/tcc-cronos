/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


/**
 * This class implements DataSource interface. It simply implements the method getConnection using DB Connection
 * Factory component.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectReasonDataSource implements DataSource {
    /**
     * Gets database connection through DB Connection Factory component.
     *
     * @return a database connection, or null if error occurs.
     *
     * @throws SQLException never.
     */
    public Connection getConnection() throws SQLException {
        try {
            return new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
        } catch (DBConnectionException e) {
            return null;
        } catch (ConfigurationException e) {
            return null;
        }
    }

    /**
     * Returns null.
     *
     * @param arg0 not used.
     * @param arg1 not used.
     *
     * @return null.
     *
     * @throws SQLException never.
     */
    public Connection getConnection(String arg0, String arg1)
        throws SQLException {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null.
     *
     * @throws SQLException never.
     */
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    /**
     * Do nothing.
     *
     * @param arg0 not used.
     *
     * @throws SQLException never.
     */
    public void setLogWriter(PrintWriter arg0) throws SQLException {
    }

    /**
     * Do nothing.
     *
     * @param arg0 not used.
     *
     * @throws SQLException never.
     */
    public void setLoginTimeout(int arg0) throws SQLException {
    }

    /**
     * Returns 0.
     *
     * @return 0.
     *
     * @throws SQLException never.
     */
    public int getLoginTimeout() throws SQLException {
        return 0;
    }
}
