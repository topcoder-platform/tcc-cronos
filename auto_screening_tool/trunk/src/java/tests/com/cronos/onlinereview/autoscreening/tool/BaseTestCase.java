/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * This is a base (helper) class for test cases in this component.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseTestCase extends TestCase {

    /**
     * Represents the path of the temporary directory.
     */
    public static final String TMP_DIR = "test_files/temp";

    /**
     * Represents the path of the files directory.
     */
    public static final String FILES_DIR = "test_files/files";

    /**
     * Represents the path of the logging wrapper config file.
     */
    public static final String LOGGING_WRAPPER_CONFIG_FILE = "logging_wrapper.xml";

    /**
     * Represents the path of the file upload config file.
     */
    public static final String FILE_UPLOAD_CONFIG_FILE = "file_upload.xml";

    /**
     * Represents the path of the db connection factory config file.
     */
    public static final String DBCONNECTION_CONFIG_FILE = "dbconfig.xml";

    /**
     * Represents the path of the screening_log file.
     */
    public static final String SCREENING_LOG_FILE = "test_files/screening.log";

    /**
     * The namespace of the DBConnectionFactory.
     */
    public static final String DB_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * The connection name defined in the DBConnectionFactory.
     */
    public static final String CONNECTION_NAME = "informix_connection";

    /**
     * <p>
     * The file contains prepare sql statements.
     * </p>
     */
    public static final String PREPARE_FILE = "test_files/prepare.sql";

    /**
     * <p>
     * The file contains clear sql statements.
     * </p>
     */
    public static final String CLEAR_FILE = "test_files/clear.sql";

    /**
     * Represent the connection used to by the testing methods.
     */
    private Connection conn;

    /**
     * Sets up the environments.
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        deleteDir(new File(TMP_DIR));
        new File(TMP_DIR).mkdir();

        // load the configuration
        loadConfig();
        conn = getConnection();

        // clear the previous records first
        clearData();
        // prepare data
        prepareData();
    }

    /**
     * Clears all the test environments.
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        clearData();

        conn.close();
        clearNamespaces();

        deleteDir(new File(TMP_DIR));
        new File(TMP_DIR).mkdir();
    }

    /**
     * Deletes the dir and all files and subdirectories under the dir. If a
     * deletion fails, the method does not stop but continues attempting to
     * delete other files.
     * @param dir
     *            the directory to delete
     * @return true if all deletions were successful.
     */
    protected boolean deleteDir(File dir) {
        boolean success = true;

        // check if dir is a directory
        if (dir.isDirectory()) {

            // try to delete all files and subdirectories under the dir
            File[] subDirs = dir.listFiles();
            for (int i = 0; i < subDirs.length; i++) {
                success = success && deleteDir(subDirs[i]);
            }
        }

        // The directory is now empty so delete it
        return success && dir.delete();
    }

    /**
     * Loads the configuration files to ConfigManager.
     * @throws Exception
     *             to JUnit
     */
    private void loadConfig() throws Exception {

        // load it to the ConfigManager
        ConfigManager cm = ConfigManager.getInstance();

        cm.add(DBCONNECTION_CONFIG_FILE);
        cm.add(LOGGING_WRAPPER_CONFIG_FILE);
        cm.add(FILE_UPLOAD_CONFIG_FILE);
    }

    /**
     * Clears all the namespaces in the ConfigManager.
     * @throws Exception
     *             to JUnit
     */
    public static void clearNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        // iterator through to clear the namespace
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {

            String namespace = (String) it.next();

            if (cm.existsNamespace(namespace)) {

                // removes the namespace that exists
                cm.removeNamespace(namespace);
            }
        }
    }

    /**
     * <p>
     * Clear the data in table.
     * </p>
     * @throws Exception
     *             Exception to JUnit.
     */
    private void clearData() throws Exception {
        executeSqlFile(CLEAR_FILE, conn);
    }

    /**
     * <p>
     * Prepare the data in table.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    private void prepareData() throws Exception {
        executeSqlFile(PREPARE_FILE, conn);
    }

    /**
     * Execute the sql statements in a file.
     * @param filename
     *            the sql file.
     * @param connection
     *            the database connection
     * @throws Exception
     *             to JUnit
     */
    protected void executeSqlFile(String filename, Connection connection) throws Exception {
        InputStream in = new FileInputStream(filename);

        byte[] buf = new byte[1024];
        int len = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
        in.close();

        String content = out.toString();

        List sqls = new ArrayList();
        int lastIndex = 0;

        // parse the sqls
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }

        for (int i = 0; i < sqls.size(); i++) {
            doSQLUpdate((String) sqls.get(i), new Object[] {});
        }
    }

    /**
     * Returns the default connection.
     * @return the default connection.
     * @throws Exception
     *             to JUnit
     */
    protected Connection getConnection() throws Exception {
        // returns the default connection (that is set to be non-auto commit).
        Connection con = getConnectionFactory().createConnection();
        return con;
    }

    /**
     * Returns the connection factory.
     * @return returns the connection factory
     * @throws Exception
     *             to JUnit
     */
    protected DBConnectionFactory getConnectionFactory() throws Exception {
        // gets the DBConnectionFactory.
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        return factory;
    }

    /**
     * Does the update operation to the database.
     * @param sql
     *            the SQL statement to be executed
     * @param values
     *            the values that to set in the statement
     * @throws Exception
     *             to JUnit
     */
    protected void doSQLUpdate(String sql, Object[] values) throws Exception {
        PreparedStatement ps = null;

        try {
            // creates the prepared statement
            ps = conn.prepareStatement(sql);

            // set the values to the prepared statement
            for (int i = 0; i < values.length; i++) {
                setElement(ps, i + 1, values[i]);
            }

            // do the update
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error when executing sql [" + sql + "].", e);
        } finally {
            // close the resources
            closeStatement(ps);
        }
    }

    /**
     * Does the scalar query operation to the database.
     * @param sql
     *            the SQL statement to be executed
     * @param values
     *            the values that to set in the statement
     * @return the query value
     * @throws Exception
     *             to JUnit
     */
    protected int doScalarQuery(String sql, Object[] values) throws Exception {
        PreparedStatement ps = null;

        try {
            // creates the prepared statement
            ps = conn.prepareStatement(sql);

            // set the values to the prepared statement
            for (int i = 0; i < values.length; i++) {
                setElement(ps, i + 1, values[i]);
            }

            // do the query
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new Exception("Error when executing sql [" + sql + "].", e);
        } finally {
            // close the resources
            closeStatement(ps);
        }
    }

    /**
     * Does the query operation to the database.
     * @param sql
     *            the SQL statement to be executed
     * @param values
     *            the values that to set in the statement
     * @return the ResultSet instance of the query
     * @throws Exception
     *             to JUnit
     */
    protected ResultSet doSQLQuery(String sql, Object[] values) throws Exception {
        PreparedStatement ps = null;

        try {
            // creates the prepared statement
            ps = conn.prepareStatement(sql);

            // set the values to the prepared statement
            for (int i = 0; i < values.length; i++) {
                setElement(ps, i + 1, values[i]);
            }

            // do the query
            ResultSet resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new Exception("Error when executing sql [" + sql + "].", e);
        }
    }

    /**
     * Sets the value of a specific index to the prepared statement.
     * @param ps
     *            the prepared statement
     * @param index
     *            the index
     * @param value
     *            the value to be set
     * @throws Exception
     *             to JUnit
     */
    private void setElement(PreparedStatement ps, int index, Object value) throws Exception {
        if (value instanceof Integer) {
            ps.setInt(index, ((Integer) value).intValue());
        } else if (value instanceof Long) {
            ps.setLong(index, ((Long) value).longValue());
        } else if (value instanceof Timestamp) {
            ps.setTimestamp(index, (Timestamp) value);
        } else if (value instanceof String) {
            ps.setString(index, (String) value);
        } else if (value instanceof Date) {
            ps.setDate(index, new java.sql.Date(((Date) value).getTime()));
        } else {
            throw new IllegalArgumentException("The type of the value is not supported");
        }
    }

    /**
     * Closes the connection silently.
     * @param con
     *            the connection to close.
     */
    protected void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes the statement silently.
     * @param stmt
     *            the statement to close.
     */
    protected void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes a result set silently.
     * @param rs
     *            the result set to close
     */
    protected void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
