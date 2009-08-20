/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * This is a helper class for database operation.
 *
 * @author TCSDEVELOPER
 * @version 1.3
 */
public final class DBUtil {
    /**
     * <p>
     * Represents the <code>DBConnectionFactory</code> instance for testing.
     * </p>
     */
    private static DBConnectionFactory factory;

    /**
     * Private ctor.
     */
    private DBUtil() {
        // empty.
    }

    /**
     * Clears the database.
     *
     * @throws Exception to junit
     */
    public static void clearDatabase() throws Exception {

        executeScriptFile("/delete_contest_table.sql");
    }

    /**
     * Initializes the database.
     *
     * @throws Exception to junit
     */
    public static void initDatabase() throws Exception {

        executeScriptFile("/init.sql");
    }

    /**
     * Gets DBConnectionFactory instance.
     *
     * @return DBConnectionFactory instance.
     *
     * @throws Exception to junit
     */
    public static DBConnectionFactory getDBConnectionFactory() throws Exception {
        if (factory == null) {
            ConfigManager cm = ConfigManager.getInstance();

            String file = URI.create(
                DBUtil.class.getResource("/InformixDBConnectionFactory.xml").getPath()).getPath();
            if (!cm.existsNamespace("unittests")) {
                cm.add(new File(file).getCanonicalPath());
            }

            factory =  new DBConnectionFactoryImpl("unittests");
        }
        return factory;
    }

    /**
     * <p>
     * Executes the sql script against the database.
     * </p>
     *
     * @param filename the file name.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public static void executeScriptFile(String filename) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader bufferedReader = null;

        try {
            conn = getDBConnectionFactory().createConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String sql = null;
            bufferedReader = new BufferedReader(
                new InputStreamReader(DBUtil.class.getResourceAsStream(filename)));

            while (null != (sql = bufferedReader.readLine())) {
                try {
                    stmt.executeUpdate(sql);
                } catch (SQLException e) {
                    System.out.println("bad sql=" + sql);
                    // ignore.
                }
            }

            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            conn.rollback();
            // ignore.
        } finally {
            closeStatement(stmt);
            closeConnection(conn);

            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
    }
/*
    *//**
     * Execute the sql statements in a file.
     *
     * @param filename the sql file.
     * @throws Exception to JUnit.
     *//*
    private static void executeSqlFile(String filename) throws Exception {
        InputStream in = new FileInputStream(filename);

        byte[] buf = new byte[1024];
        int len = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        String content;

        try {
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

            content = out.toString();
        } finally {
            in.close();
            out.close();
        }

        List<String> sqls = new ArrayList<String>();
        int lastIndex = 0;

        // parse the sqls
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }

        Connection conn = factory.createConnection();

        try {
            for (int i = 0; i < sqls.size(); i++) {
                doSQLUpdate((String) sqls.get(i), conn);
            }
        } finally {
            closeConnection(conn);
        }
    }*/
/*
    *//**
     * Does the update operation to the database.
     *
     * @param sql the SQL statement to be executed.
     * @param conn the database connection.
     * @throws Exception to JUnit.
     *//*
    private static void doSQLUpdate(String sql, Connection conn) throws Exception {
        PreparedStatement ps = null;

        try {
            // creates the prepared statement
            ps = conn.prepareStatement(sql);

            // do the update
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("bad sql=" + sql);

            // ignore.
        } finally {
            // close the resources
            closeStatement(ps);
        }
    }
*/
    /**
     * <p>
     * Closes the connection. It will be used in finally block.
     * </p>
     *
     * @param conn the database connection.
     */
    static void closeConnection(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Close the result set. It will be used in finally block.
     * </p>
     *
     * @param rs the result set.
     */
    static void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Close the statement. It will be used in finally block.
     * </p>
     *
     * @param stmt the statement.
     */
    static void closeStatement(Statement stmt) {
        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
