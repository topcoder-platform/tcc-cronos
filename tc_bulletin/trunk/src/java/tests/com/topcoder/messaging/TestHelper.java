/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * The helper class for test.
 * 
 * @author yqw
 * @version 1.0
 */
public class TestHelper {
    /**
     * <p>
     * Execute the sql script against the database.
     * </p>
     * 
     * @param filename
     *            The file name.
     * 
     * @throws Exception
     *             to JUnit.
     */
    private static void executeScriptFile(String filename) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader bufferedReader = null;

        try {
            ConfigurationFileManager cfm = new ConfigurationFileManager(
                    "test_files/config.properties");
            ConfigurationObject config = cfm
                    .getConfiguration("InformixMessageBoardPersistence");
            config = config.getChild("InformixMessageBoardPersistence");
            DBConnectionFactory factory = new DBConnectionFactoryImpl(config);
            conn = factory.createConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String sql = null;
            String path = new File(filename).getCanonicalPath();
            bufferedReader = new BufferedReader(new FileReader(path));

            while (null != (sql = bufferedReader.readLine())) {
                if (sql.trim().length() > 0) {
                    stmt.execute(sql);
                }
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();

            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }

            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
    }

    /**
     * insert the data for test.
     * 
     * @throws Exception
     *             any exception throw to JUNit.
     */
    public static void insertDB() throws Exception {
        executeScriptFile("test_files/clean.sql");
        executeScriptFile("test_files/insert.sql");
        // set the message of the message and responses.
        ConfigurationFileManager cfm = new ConfigurationFileManager(
                "test_files/config.properties");
        ConfigurationObject config = cfm
                .getConfiguration("InformixMessageBoardPersistence");
        config = config.getChild("InformixMessageBoardPersistence");
        DBConnectionFactory factory = new DBConnectionFactoryImpl(config);
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = factory.createConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update messages set message=?");
            pstmt.setString(1, "some message");
            pstmt.execute();
            pstmt = conn.prepareStatement("update responses set message=?");
            pstmt.setString(1, "some message");
            pstmt.execute();
            conn.commit();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    /**
     * get the row id
     * @throws Exception
     *             any exception throw to JUNit.
     */
    public static int getRowidFrom(int id) throws Exception {
        ConfigurationFileManager cfm = new ConfigurationFileManager(
                "test_files/config.properties");
        ConfigurationObject config = cfm
                .getConfiguration("InformixMessageBoardPersistence");
        config = config.getChild("InformixMessageBoardPersistence");
        DBConnectionFactory factory = new DBConnectionFactoryImpl(config);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = factory.createConnection();
            pstmt = conn.prepareStatement("select rowid from messages where id=?");
            pstmt.setLong(1,id);
            pstmt.execute();
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * clear the database.
     * 
     * @throws Exception
     *             any exception throw to JUNit.
     */
    public static void clearDB() throws Exception {
        executeScriptFile("test_files/clean.sql");
    }

}
