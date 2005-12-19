/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.buildutility.template.accuracytests;

import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;
import com.topcoder.buildutility.template.TemplateLoader;
import com.topcoder.buildutility.template.persistence.JDBCPersistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;

import java.sql.Connection;


/**
 * Accuracy tests for TemplateLoader class.
 * @author zjq
 * @version 1.0
 */
public class TemplateLoaderAccuracyTests extends TestCase {
    /**
     * A configuration namespace used for testing.
     */
    private static final String ACCTEST_NAMESPACE = "com.topcoder.buildutility.template.acctests";

    /**
     * A configuration namespace used for testing.
     */
    private static final String CONFIG_FILE = "acc_defconf.xml";

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
    private static final String DB_SCRIPT = "test_files/acc_database.sql";

    /**
     * The database connection instance.
     */
    private static Connection connection = null;

    /**
     * The singleton ConfigManager instance.
     */
    private ConfigManager configManager = ConfigManager.getInstance();

    /**
     * A TemplateLoader instance to test.
     */
    private TemplateLoader loader = null;

    /**
     * Set up environment and initializes the TemplateLoader instance for testing.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        // remove configuration before testing
        removeNamespace(JDBCPersistence.DEFAULT_DB_FACTORY_NAMESPACE);
        removeNamespace(ACCTEST_NAMESPACE);

        // load the configuration for testing
        configManager.add(CONFIG_FILE);

        loader = new TemplateLoader(new MockPersistence(), "test_files/");
    }

    /**
     * Tear down environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        // remove configuration before testing
        removeNamespace(JDBCPersistence.DEFAULT_DB_FACTORY_NAMESPACE);
        removeNamespace(ACCTEST_NAMESPACE);
    }

    /**
     * Remove the given namespace form config manager.
     *
     * @param namespace the namespace to remove
     * @throws Exception to JUnit
     */
    private void removeNamespace(String namespace) throws Exception {
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri).</p>
     * <p>Validates whether the persistence was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader1() throws Exception {
        loader = new TemplateLoader(new MockPersistence(), "test_files/");

        // validates whether the persistence was set properly.
        TemplateHierarchy hierarchy = loader.loadTemplateHierarchy("foo");
        assertEquals("the id is incorrect", 1, hierarchy.getId());
        assertEquals("the name is incorrect", "foo", hierarchy.getName());
        assertEquals("the parent id is incorrect", 1, hierarchy.getParentId());
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri).</p>
     * <p>Validates whether the file server uri was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader2() throws Exception {
        loader = new TemplateLoader(new MockPersistence(), "test_files/");

        // validates whether the file server uri was set properly
        checkFileServerUriSet(loader.loadTemplateHierarchy("foo").getTemplates()[0]);
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri).</p>
     * <p>Validates whether the cache was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader3() throws Exception {
        loader = new TemplateLoader(new MockPersistence(), "test_files/");

        // the first id should be 1
        assertEquals("the id is incorrect", 1, loader.loadTemplateHierarchy("foo").getId());

        // the cache is NullCache, the next loading will get a new TemplateHierarchy with different id
        assertEquals("the id is incorrect", 2, loader.loadTemplateHierarchy("foo").getId());
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>Validates whether the persistence was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader4() throws Exception {
        loader = new TemplateLoader(new MockPersistence(), "test_files/", 100, 100);

        // validates whether the persistence was set properly.
        TemplateHierarchy hierarchy = loader.loadTemplateHierarchy("foo");
        assertEquals("the id is incorrect", 1, hierarchy.getId());
        assertEquals("the name is incorrect", "foo", hierarchy.getName());
        assertEquals("the parent id is incorrect", 1, hierarchy.getParentId());
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>Validates whether the file server uri was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader5() throws Exception {
        loader = new TemplateLoader(new MockPersistence(), "test_files/", 100, 100);

        // validates whether the file server uri was set properly
        checkFileServerUriSet(loader.loadTemplateHierarchy("foo").getTemplates()[0]);
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>Validates whether the cache was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader6() throws Exception {
        loader = new TemplateLoader(new MockPersistence(), "test_files/", 200, 1);

        TemplateHierarchy hierarchy = loader.loadTemplateHierarchy("foo");

        // the first id should be 1
        assertEquals("the id is incorrect", 1, hierarchy.getId());

        // the TemplateHierarchy instance in the cache is not expired, so the same instance will be returned.
        assertEquals("should be the same instance", hierarchy, loader.loadTemplateHierarchy("foo"));

        // load another instance
        loader.loadTemplateHierarchy("bar");

        // sleep 500ms
        Thread.currentThread().sleep(500);

        // the TemplateHierarchy instance in the cache is expired now , so a new instance will be returned.
        assertEquals("the id is incorrect", 3, loader.loadTemplateHierarchy("foo").getId());
    }

    /**
     * <p>Tests the loadTemplateHierarchy method.
     * <p>Validates whether the hierarchy could be retrieved properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testLoadTemplateHierarchy1() throws Exception {
        // validates whether the hierarchy could be retrieved properly.
        TemplateHierarchy hierarchy = loader.loadTemplateHierarchy("foo");
        assertEquals("the id is incorrect", 1, hierarchy.getId());
        assertEquals("the name is incorrect", "foo", hierarchy.getName());
        assertEquals("the parent id is incorrect", 1, hierarchy.getParentId());
    }

    /**
     * <p>Tests the loadTemplateHierarchy method.
     * <p>Validates whether the hierarchy could be retrieved properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testLoadTemplateHierarchy2() throws Exception {
        // prepare database
        // clear tables before inserting
        clearTables();

        Connection conn = getConnection();

        BufferedReader reader = new BufferedReader(new FileReader(DB_SCRIPT));

        for (;;) {
            String sql = reader.readLine();

            if (sql == null) {
                break;
            }

            if (!sql.startsWith("-") && (sql.trim().length() > 0)) {
                // ececute each sql insert statement,
                conn.createStatement().executeUpdate(sql);
            }
        }

        loader = new TemplateLoader(ACCTEST_NAMESPACE);

        // retrieve the "singleton" TemplateHierarchy from database
        assertEquals("the name is incorrect", "singleton", loader.loadTemplateHierarchy("singleton").getName());

        // delete all rows
        clearTables();
    }

    /**
     * <p>Checks whether the file server uri was set properly.</p>
     *
     * @param template the Template instance to check
     * @throws Exception to JUnit
     */
    private void checkFileServerUriSet(Template template)
        throws Exception {
        InputStream input = null;

        try {
            // get the input stream
            input = template.getData();

            // read all bytes from the input stream
            byte[] buffer = new byte[10];
            int size = input.read(buffer);

            // check whether the InputStream was properly retrieved
            TestCase.assertEquals("the InputStream is not properly retrieved", "test",
                new String(buffer, 0, size));
        } finally {
            // finally, we should close the stream if it was open
            if (input != null) {
                input.close();
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
        if ((connection == null) || connection.isClosed()) {
            // create a new Connection if the connection is not initialized or closed.
            DBConnectionFactory factory = new DBConnectionFactoryImpl(JDBCPersistence.DEFAULT_DB_FACTORY_NAMESPACE);
            connection = factory.createConnection();
        }

        return connection;
    }
}
