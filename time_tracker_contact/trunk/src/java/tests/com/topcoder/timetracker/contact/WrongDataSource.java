/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import java.sql.Connection;
import java.sql.SQLException;


import com.informix.jdbcx.IfxDataSource;

/**
 * <p>
 * This is a mock data source inherited from <code>IfxDataSource</code>.
 * It always throws SQLException when retrieving connection.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class WrongDataSource extends IfxDataSource {
    /**
     * <p>
     * Serail Version UID.
     * </p>
     */
    private static final long serialVersionUID = 8850036103855063194L;
    /**
     * <p>
     * Constructor. Simply delegate to super.
     * </p>
     *
     * @throws SQLException to JUnit
     */
    public WrongDataSource()
        throws SQLException {
        super();
    }
    /**
     * <p>
     * Always throws SQLException.
     * </p>
     *
     * @return connection.
     *
     * @throws SQLException to JUnit
     */
    public Connection getConnection() throws SQLException {
        throw new SQLException("Not supported");
    }
}
