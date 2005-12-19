/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template;

import junit.framework.TestCase;

import com.topcoder.buildutility.template.persistence.JDBCPersistence;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileReader;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import java.sql.Connection;


/**
 * <p>UnitTestHelper class provides some useful helper methods.</p>
 *
 * @author oldbig
 *
 * @copyright (c) 2005, Topcoder, Inc. All Rights Reserved.
 *
 * @version 1.0
 */
class UnitTestHelper {


    /**
     * The name of the templates table.
     */
    private static final String TEMPLATES_TABLE = "templates";

    /**
     * The name of the template hierarchy table.
     */
    private static final String TEMP_HIER_TABLE = "temp_hier";

    /**
     * The name of the template hierarchy mapping table.
     */
    private static final String TEMP_HIER_MAPPING_TABLE = "temp_hier_mapping";

    /**
     * The file containing the script to initialize the database.
     */
    private static final String DB_SCRIPT = "test_files/DBscript.sql";

    /**
     * The database connection instance.
     */
    private static Connection connection = null;


    /**
     * Private constructor to prevent this class be instantiated.
     */
    private UnitTestHelper() {
        // empty
    }

    /**
     * <p>Checks whether the file server uri was set properly.</p>
     *
     * @param template the Template instance to check
     * @throws Exception to JUnit
     */
    static void checkFileServerUriSet(Template template) throws Exception {

        InputStream input = null;
        try {
            // get the input stream
            input = template.getData();

            // read all bytes from the input stream
            byte[] buffer = new byte[10];
            int size = input.read(buffer);

            // check whether the InputStream was properly retrieved
            TestCase.assertEquals("the InputStream is not properly retrieved", "test", new String(buffer, 0, size));

        } finally {
            // finally, we should close the stream if it was open
            if (input != null) {
                input.close();
            }
        }

    }

    /**
     * Build the template hierarchies for testing.
     *
     * @throws Exception to JUnit
     */
    static void buildHierarchies() throws Exception {

        // clear tables before inserting
        clearTables();

        Connection conn = getConnection();

        BufferedReader reader = new BufferedReader(new FileReader(DB_SCRIPT));
        for (;;) {
            String sql = reader.readLine();
            if (sql == null) {
                break;
            }

            if (!sql.startsWith("-") && sql.trim().length() > 0) {
                // ececute each sql insert statement,
                conn.createStatement().executeUpdate(sql);
            }
        }

    }

    /**
     * Clears all tables used by this component.
     *
     * @throws Exception to JUnit
     */
    static void clearTables() throws Exception {
        clearTable(TEMP_HIER_MAPPING_TABLE);
        clearTable(TEMPLATES_TABLE);
        clearTable(TEMP_HIER_TABLE);

    }

    /**
     * Clears all rows in the given table.
     *
     * @param name the table name
     *
     * @throws Exception to JUnit
     */
    private static void clearTable(String name) throws Exception {
        // Clears all rows in the given table.
        getConnection().createStatement().executeUpdate("DELETE FROM " + name);
    }

    /**
     * Get a connection to the database.
     *
     * @return a connection to the database.
     *
     * @throws Exception to JUnit
     */
    private static Connection getConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            // create a new Connection if the connection is not initialized or closed.
            DBConnectionFactory factory = new DBConnectionFactoryImpl(JDBCPersistence.DEFAULT_DB_FACTORY_NAMESPACE);
            connection = factory.createConnection();
        }
        return connection;
    }
}
