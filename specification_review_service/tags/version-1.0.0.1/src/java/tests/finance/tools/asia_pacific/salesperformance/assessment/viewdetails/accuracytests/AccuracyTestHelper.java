/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */



package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.accuracytests;

import junit.framework.TestCase;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;

/**
 * <p>
 * Utility class meant to facilitate testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class AccuracyTestHelper extends TestCase {
    /**
     * Creates table file path.
     */
    private static final String CREATE_TABLE_PATH = "test_files/accuracy/ddl.sql";

    /**
     * Drops table file path.
     */
    private static final String DROP_TABLE_PATH = "test_files/accuracy/drop.sql";

    /**
     * Inserts data into table file path.
     */
    private static final String ISNERT_DATA_PATH = "test_files/accuracy/insert.sql";

    /**
     * Separator for sql statement.
     */
    private static final String SQL_SEPARATOR = ";";

    /**
     * <p>
     * Prevents instantiation of this class.
     * </p>
     */
    private AccuracyTestHelper() {}

    /**
     * <p>
     * Reads given file.
     * </p>
     *
     * @param path
     *            the path to the file to read
     * @return the contents of the file
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static String readFile(String path) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));

            String line;
            StringBuffer sb = new StringBuffer();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Sets up the database.
     *
     * @param con
     *            the connection to use.
     * @throws SQLException
     *             to JUnit
     * @throws IOException
     *             to JUnit
     */
    public static void setUpDataBase(Connection con) throws SQLException, IOException {
        executeSqlFile(con, CREATE_TABLE_PATH);

        executeSqlFile(con, ISNERT_DATA_PATH);
    }

    /**
     * Clears the database.
     *
     * @param con
     *            the connection to use.
     * @throws SQLException
     *             to JUnit
     * @throws IOException
     *             to JUnit
     */
    public static void clearDataBase(Connection con) throws SQLException, IOException {
        executeSqlFile(con, DROP_TABLE_PATH);
    }

    /**
     * <p>
     * Method to set the value of a private field in the specified instance using
     * reflection.
     * </p>
     *
     * @param fieldName
     *            the name of the field
     * @param instance
     *            the instance to set the value for
     * @param value
     *            the value to set
     * @param type
     *            the type of the instance to get the field from
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public static void setFieldValue(String fieldName, Object instance, Object value, Class type) throws Exception {
        Field field = (type == null)
                      ? instance.getClass().getDeclaredField(fieldName) : type.getDeclaredField(fieldName);

        field.setAccessible(true);
        field.set(instance, value);
        field.setAccessible(false);
    }

    /**
     * <p>
     * Helper method to get a data source to be used in the tests.
     * </p>
     *
     * @return the data source
     * @throws Exception to JUnit
     */
    public static BasicDataSource getDataSource() throws Exception {
        Properties properties = loadProperties("test_files/accuracy/db.properties");

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(properties.getProperty("driverManagerClassName"));
        dataSource.setUrl(properties.getProperty("dbUrl"));
        dataSource.setUsername(properties.getProperty("dbUser"));
        dataSource.setPassword(properties.getProperty("dbPassword"));

        return dataSource;
    }

    /**
     * Executes the sql file.
     *
     * @param con
     *            the connection to use.
     * @param sqlPath
     *            the sql file path.
     * @throws SQLException
     *             to JUnit
     * @throws IOException
     *             to JUnit
     */
    static void executeSqlFile(Connection con, String sqlPath) throws SQLException, IOException {
        Statement st = con.createStatement();

        String[] sqls = readFile(sqlPath).split(SQL_SEPARATOR);

        for (int i = 0; i < sqls.length; i++) {
            if (sqls[i].trim().length() != 0) {
                st.addBatch(sqls[i]);
            }
        }

        st.executeBatch();
    }

    /**
     * Loads the configuration file given by configFile into a Properties object.
     *
     * @param configFile
     *            the path of the configuration file.
     * @return the loaded Properties object
     * @throws Exception
     *             to JUnit
     */
    public static Properties loadProperties(String configFile) throws Exception {
        // Create an empty Properties
        Properties prop = new Properties();

        InputStream in = null;

        try {
            // Create an input stream from the given configFile
            in = new FileInputStream(configFile);

            // Load the properties
            prop.load(in);
        } finally {
            // Close the input stream
            if (in != null) {
                in.close();
            }
        }

        return prop;
    }

    /**
     * Create a new db connection to return.
     *
     * @param properties
     *            the properties file to use
     * @return the db connection.
     * @throws Exception
     *             to JUnit
     */
    public static Connection createConnection(Properties properties) throws Exception {
        String dbUrl = properties.getProperty("dbUrl");
        String dbUser = properties.getProperty("dbUser");
        String dbPassword = properties.getProperty("dbPassword");
        String dbClass = properties.getProperty("driverManagerClassName");

        Class.forName(dbClass);

        if ((dbUser != null) && (dbUser.trim().length() != 0)) {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } else {
            return DriverManager.getConnection(dbUrl);
        }
    }

    /**
     * Closes the database connection.
     *
     * @param connection
     *            the database connection to close
     * @throws Exception
     *             to JUnit
     */
    public static void closeConnection(Connection connection) throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Executes the specified sql query.
     *
     * @param con
     *            the connection to use
     * @param sql
     *            the query string
     * @return the calling of the result set's next method
     * @throws Exception
     *             to JUnit
     */
    static boolean executeSql(Connection con, String sql) throws Exception {
        Statement stmt = null;

        try {
            stmt = con.createStatement();

            return stmt.executeQuery(sql).next();
        } finally {
            stmt.close();
        }
    }
}
