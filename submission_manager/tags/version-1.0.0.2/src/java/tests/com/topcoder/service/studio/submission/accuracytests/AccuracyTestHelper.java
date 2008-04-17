/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission.accuracytests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.topcoder.configuration.persistence.ConfigurationFileManager;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;


/**
 * The helper class for the accuracy test.
 *
 * @author KLW
 * @version 1.0
  */
public class AccuracyTestHelper {
    /**
     * The DBConnectionFactory for test.
     */
    private static DBConnectionFactory dbConnectionFactory;
    /**
     * initialize the data base for the accuracy test.
     *
     * @throws Exception any exception will throw to JUnit.
     */
    public static void initDatabase() throws Exception { 
        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(
                "test_files/accuracy/accuracytests.properties");

        dbConnectionFactory = new DBConnectionFactoryImpl(configurationFileManager.getConfiguration(
                    "InformixDBConnectionFactory"));
        Connection conn = dbConnectionFactory.createConnection();
        try {
            executeSqlFile(conn,"test_files/accuracy/clean.sql");
            executeSqlFile(conn,"test_files/accuracy/prepare.sql");
        }finally{
            closeConnection(conn);
        }
    }
    /**
     * Creates the connection to the database.
     * @return the connection to the data base.
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{
        return dbConnectionFactory.createConnection();
    }
    /**
     * clear the data base.
     * @throws Exception all exception throw to the database.
     */
    public static void clearDatabase() throws Exception{
        Connection conn = dbConnectionFactory.createConnection();
        try {
            executeSqlFile(conn,"test_files/accuracy/clean.sql");
        }finally{
            closeConnection(conn);
        }
    }
    /**
     * <p>
     * Closes the given Connection instance.
     * </p>
     *
     * @param con the given Connection instance to close.
     */
    private static void closeConnection(Connection con) {
        try {
            if ((con != null) && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }
    /**
     * <p>
     * Executes the sql scripts in the given sql file.
     * </p>
     *
     * @param connection Connection instance to access the database
     * @param sqlPath the path of the sql file to execute
     *
     * @throws SQLException if exception occurs during database operation
     * @throws IOException if fails to read the sql file
     */
    private static void executeSqlFile(Connection connection, String sqlPath)
        throws SQLException, IOException {
        String[] sqlStatements = loadSqlFile(sqlPath);

        Statement stmt = null;

        try {
            stmt = connection.createStatement();

            for (int i = 0; i < sqlStatements.length; i++) {
                if (sqlStatements[i].trim().length() != 0) {
                    stmt.executeUpdate(sqlStatements[i]);
                }
            }
        } finally {
            closeStatement(stmt);
        }
    }
    /**
     * <p>
     * Executes the sql scripts in the given sql file.
     * </p>
     *
     * @param connection Connection instance to access the database
     * @param sql the sql to execute
     *
     * @throws SQLException if exception occurs during database operation
     * @throws IOException if fails to read the sql file
     */
    public static void executeSql(String sql) throws Exception{
        Connection conn = dbConnectionFactory.createConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
        }
    }
    
    /**
     * <p>
     * Closes the given PreparedStatement instance.
     * </p>
     *
     * @param state the given Statement instance to close.
     */
    private static void closeStatement(Statement state) {
        try {
            if (state != null) {
                state.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }
    /**
     * <p>
     * This method return the sql scripts from the given sql file.
     * </p>
     *
     * @param path the path of the sql file
     * @return the sql scripts
     *
     * @throws IOException if fails to read the sql file
     */
    private static String[] loadSqlFile(String path) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(path));

        try {
            String line = reader.readLine();

            while (line != null) {
               // line = line.trim();
                   
                if ((line.trim().length() != 0)) {
                    sb.append(line);
                }


                line = reader.readLine();
            }

            return sb.toString().split(";");
        } finally {
            reader.close();
        }
    }
}
