/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.failuretests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.cronos.timetracker.entry.time.RejectReason;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;


/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * <p>
 * Since 2.0, the database tables have been updated.
 * </p>
 *
 * @author oodinary
 * @author kr00tki
 * @version 2.0
 * @since 1.1
 *
 */
public final class FailureTestHelper {
    /**
     * The id of the company.
     *
     * @since 2.0
     */
    public static final int COMPANY_ID = 10;
    /**
     * <p>
     * The private constructor prevents the creation of a new instance.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * Clears the content in database.
     * </p>
     *
     * @param connection the database connection used to access database.
     *
     * @throws SQLException if database error occurs.
     */
    public static void clearDatabase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("DELETE FROM comp_rej_reason;");
            statement.executeUpdate("DELETE FROM comp_task_type;");
            statement.executeUpdate("DELETE FROM time_reject_reason;");
            statement.executeUpdate("DELETE FROM reject_reason;");
            statement.executeUpdate("DELETE FROM time_entry;");
            statement.executeUpdate("DELETE FROM time_status;");
            statement.executeUpdate("DELETE FROM task_type;");
            statement.executeUpdate("DELETE FROM company;");

        } finally {
            statement.close();
        }
    }


    /**
     * Inserts the given RejectReason into the reject_reason table.
     *
     * @param rejectReason the RejectReason instance holding the inforation to store into the database
     * @param conn the connection instance to connect the database
     * @param companyId the id of the company to which this reject reason belongs.
     * @throws SQLException if any SQL error occurs.
     */
    public static void insertRejectReasons(RejectReason rejectReason, Connection conn, int companyId) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO reject_reason(reject_reason_id, description, " +
                    "creation_user, creation_date, modification_user, modification_date, active) VALUES " +
                    "(?,?,?,?,?,?,?)");

            pstmt.setInt(1, rejectReason.getPrimaryId());
            pstmt.setString(2, rejectReason.getDescription());
            pstmt.setString(3, rejectReason.getCreationUser());
            pstmt.setDate(4, new java.sql.Date(rejectReason.getCreationDate().getTime()));
            pstmt.setString(5, rejectReason.getModificationUser());
            pstmt.setDate(6, new java.sql.Date(rejectReason.getModificationDate().getTime()));
            pstmt.setInt(7, 1);

            pstmt.executeUpdate();
            pstmt.close();

            pstmt  = conn.prepareStatement("INSERT INTO comp_rej_reason(company_id, reject_reason_id, creation_date," +
                    "creation_user, modification_date, modification_user) VALUES (?, ?, CURRENT, 'system', " +
                    "CURRENT, 'system')");
            pstmt.setInt(1, companyId);
            pstmt.setInt(2, rejectReason.getPrimaryId());

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Insert a company row with given id.
     *
     * @param companyId the id of company.
     * @param conn the database connection.
     * @throws Exception to JUnit.
     */
    public static void insertCompany(int companyId, Connection conn) throws Exception {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO company (company_id, name, passcode, " +
                    "creation_date, creation_user, modification_date, modification_user) VALUES " +
                    "(?, ?, ?, CURRENT, 'system', CURRENT, 'system')");

            pstmt.setInt(1, companyId);
            pstmt.setString(2, "TopCoder");
            pstmt.setString(3, "passcode" + companyId);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }

    }


    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @param namespace the namespace to load the DBConnectionFactory configuration
     * @param conname the connection name to fetch the connection
     *
     * @return the connection created from DBConnectionFactory
     *
     * @throws Exception any exception during the create connection process
     */
    public static Connection getConnection(String namespace, String conname) throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        return factory.createConnection(conname);
    }


    /**
     * <p>
     * Clears the configuration used in tests.
     * </p>
     *
     * @throws ConfigManagerException if error occurs when clearing the configuration.
     */
    public static void clearConfiguration() throws ConfigManagerException {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

   }
