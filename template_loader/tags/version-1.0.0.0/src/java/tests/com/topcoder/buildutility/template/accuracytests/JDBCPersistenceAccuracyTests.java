/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.buildutility.template.accuracytests;

import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;
import com.topcoder.buildutility.template.TemplateHierarchyPersistence;
import com.topcoder.buildutility.template.persistence.JDBCPersistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Accuracy tests for TemplateLoader class.
 * @author zjq
 * @version 1.0
 */
public class JDBCPersistenceAccuracyTests extends TestCase {
    /**
     * A configuration namespace used for testing.
     */
    private static final String ACCTEST_NAMESPACE = "com.topcoder.buildutility.template.acctests";

    /**
     * A configuration namespace used for testing.
     */
    private static final String CONFIG_FILE = "acc_defconf.xml";

    /**
     * The singleton ConfigManager instance.
     */
    private ConfigManager configManager = ConfigManager.getInstance();

    /**
     * A TemplateHierarchyPersistence to test.
     */
    private TemplateHierarchyPersistence persistence = null;

    /**
     * Set up environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        // remove configuration before testing
        removeNamespace(JDBCPersistence.DEFAULT_DB_FACTORY_NAMESPACE);
        removeNamespace(ACCTEST_NAMESPACE);

        // load the configuration for testing
        configManager.add(CONFIG_FILE);

        // create the JDBCPersistence instance
        persistence = new JDBCPersistence();

        // build the hierarchies for testing
        Helper.buildHierarchies();
    }

    /**
     * Tear down environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        // clear all tables in the database
        Helper.clearTables();

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
     * Test the constructor - JDBCPersistence().
     *
     * Tests whether JDBCPersistence could be instantiate properly.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence1() throws Exception {
        persistence = new JDBCPersistence();
        assertNotNull("Unable to instantiate JDBCPersistence.", persistence);
        assertEquals("The JDBCPersistence instance was not properly created", "singleton",
            persistence.getTemplateHierarchy("singleton").getName());
    }

    /**
     * Test the constructor - JDBCPersistence(String connectionName, DBConnectionFactory dbFactory).
     *
     * Tests whether JDBCPersistence could be instantiate properly.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence2() throws Exception {
        persistence = new JDBCPersistence("TestConnection", new DBConnectionFactoryImpl(ACCTEST_NAMESPACE));

        // Tests whether JDBCPersistence could be instantiate properly.
        assertNotNull("Unable to instantiate JDBCPersistence.", persistence);
        assertEquals("The JDBCPersistence instance was not properly created", "singleton",
            persistence.getTemplateHierarchy("singleton").getName());
    }

    /**
     * Test the constructor - JDBCPersistence(Property property).
     *
     * Tests whether JDBCPersistence could be instantiate properly.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence3() throws Exception {
        Property configProperty = configManager.getPropertyObject(ACCTEST_NAMESPACE, "persistence.config");

        // create the JDBCPersistence with a valid Property
        persistence = new JDBCPersistence(configProperty);

        // Tests whether JDBCPersistence could be instantiate properly.
        assertNotNull("Unable to instantiate JDBCPersistence.", persistence);
        assertEquals("The JDBCPersistence instance was not properly created", "singleton",
            persistence.getTemplateHierarchy("singleton").getName());
    }

    /**
     * Test the constructor - JDBCPersistence(Property property).
     *
     * Tests whether JDBCPersistence could be instantiate properly.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence4() throws Exception {
        Property configProperty = configManager.getPropertyObject(ACCTEST_NAMESPACE, "persistence");

        // if some properties is missing, the default namespace could be used.
        persistence = new JDBCPersistence(configProperty);

        // Tests whether JDBCPersistence could be instantiate properly.
        assertNotNull("Unable to instantiate JDBCPersistence.", persistence);
        assertEquals("The JDBCPersistence instance was not properly created", "singleton",
            persistence.getTemplateHierarchy("singleton").getName());
    }

    /**
     * Test the getTemplateHierarchy method.
     *
     * Tests loading a general hierarchy.
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy1() throws Exception {
        // Each template hierarchy has only one template, and they use the same name.
        //                        Start
        //                        /    \
        //                      Java   .NET
        //                      /   \
        //               WebServer Weblogic
        //                    /
        //                  Debug
        //                  /   \
        //              DebugOn DebugOff
        // get the top-level template hierarchy
        TemplateHierarchy root = persistence.getTemplateHierarchy("Start");

        // a Map containing the nodes relationship
        // key is the child, and value is its parent
        Map relationship = new HashMap();
        relationship.put("Start", "Start");
        relationship.put("Java", "Start");
        relationship.put(".NET", "Start");
        relationship.put("WebServer", "Java");
        relationship.put("Weblogic", "Java");
        relationship.put("Debug", "WebServer");
        relationship.put("DebugOn", "Debug");
        relationship.put("DebugOff", "Debug");

        // a Map containing the childNumber of each node
        // key is the node name, and value is the number of children
        Map childNumbers = new HashMap();
        childNumbers.put("Start", new Integer(2));
        childNumbers.put("Java", new Integer(2));
        childNumbers.put(".NET", new Integer(0));
        childNumbers.put("WebServer", new Integer(1));
        childNumbers.put("Weblogic", new Integer(0));
        childNumbers.put("Debug", new Integer(2));
        childNumbers.put("DebugOn", new Integer(0));
        childNumbers.put("DebugOff", new Integer(0));

        // a Map containing the templates relationship
        // key is the node name, and value is its templates
        Map templates = new HashMap();
        templates.put("Start", Arrays.asList(new String[] { "Start" }));
        templates.put("Java", Arrays.asList(new String[] { "Java" }));
        templates.put(".NET", Arrays.asList(new String[] { ".NET" }));
        templates.put("WebServer", Arrays.asList(new String[] { "WebServer" }));
        templates.put("Weblogic", Arrays.asList(new String[] { "Weblogic" }));
        templates.put("Debug", Arrays.asList(new String[] { "Debug" }));
        templates.put("DebugOn", Arrays.asList(new String[] { "DebugOn" }));
        templates.put("DebugOff", Arrays.asList(new String[] { "DebugOff" }));

        validateTemplateHierarchy(root, childNumbers, relationship, templates);
    }

    /**
     * Test the getTemplateHierarchy method.
     *
     * Tests loading a template hierarchy has no children and no template
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy2() throws Exception {
        // Loading a template hierarchy has no children and no template
        // get the top-level template hierarchy
        TemplateHierarchy root = persistence.getTemplateHierarchy("singleton");

        // a Map containing the nodes relationship
        // key is the child, and value is its parent
        Map relationship = new HashMap();
        relationship.put("singleton", "singleton");

        // a Map containing the childNumber of each node
        // key is the node name, and value is the number of children
        Map childNumbers = new HashMap();
        childNumbers.put("singleton", new Integer(0));

        // a Map containing the templates relationship
        // key is the node name, and value is its templates
        Map templates = new HashMap();
        templates.put("singleton", new ArrayList());

        validateTemplateHierarchy(root, childNumbers, relationship, templates);
    }

    /**
     * Test the getTemplateHierarchy method.
     *
     * Tests loading a template hierarchy has 10 templates
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy3() throws Exception {
        // Loading a template hierarchy has 10 templates
        // get the top-level template hierarchy
        TemplateHierarchy root = persistence.getTemplateHierarchy("ten_templates");

        // a Map containing the node relationships
        // key is the child, and value is its parent
        Map relationship = new HashMap();
        relationship.put("ten_templates", "ten_templates");

        // a Map containing the child number of each node
        // key is the node name, and value is the number of children
        Map childNumbers = new HashMap();
        childNumbers.put("ten_templates", new Integer(0));

        // a Map containing the templates relationship
        // key is the node name, and value is its templates
        Map templates = new HashMap();
        templates.put("ten_templates",
            Arrays.asList(
                new String[] {
                    "template0", "template1", "template2", "template3", "template4", "template5", "template6",
                    "template7", "template8", "template9"
                }));

        validateTemplateHierarchy(root, childNumbers, relationship, templates);
    }

    /**
     * Test the getTemplateHierarchy method.
     *
     * Tests loading a hierarchy like a binary tree.
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy4() throws Exception {
        //      This hierarchy is like a binary tree
        //
        //          root has 3 templates
        //          level_1_B has 2 templates
        //          level_2_B has 1 templates
        //          level_2_D has 4 templates
        //
        //                       root
        //                      /    \
        //            level_1_A       level_1_B
        //            /    \           /    \
        //     level_2_A level_2_B  level_2_C level_2_D
        // get the top-level template hierarchy
        TemplateHierarchy root = persistence.getTemplateHierarchy("root");

        // a Map containing the nodes relationship
        // key is the child, and value is its parent
        Map relationship = new HashMap();
        relationship.put("root", "root");
        relationship.put("level_1_A", "root");
        relationship.put("level_1_B", "root");
        relationship.put("level_2_A", "level_1_A");
        relationship.put("level_2_B", "level_1_A");
        relationship.put("level_2_C", "level_1_B");
        relationship.put("level_2_D", "level_1_B");

        // a Map containing the childNumber of each node
        // key is the node name, and value is the number of children
        Map childNumbers = new HashMap();
        childNumbers.put("root", new Integer(2));
        childNumbers.put("level_1_A", new Integer(2));
        childNumbers.put("level_1_B", new Integer(2));
        childNumbers.put("level_2_A", new Integer(0));
        childNumbers.put("level_2_B", new Integer(0));
        childNumbers.put("level_2_C", new Integer(0));
        childNumbers.put("level_2_D", new Integer(0));

        // a Map containing the templates relationship
        // key is the node name, and value is its templates
        Map templates = new HashMap();
        templates.put("root", Arrays.asList(new String[] { "root_1", "root_2", "root_3" }));
        templates.put("level_1_A", new ArrayList());
        templates.put("level_1_B", Arrays.asList(new String[] { "level_1_B_1", "level_1_B_2" }));
        templates.put("level_2_A", new ArrayList());
        templates.put("level_2_B", Arrays.asList(new String[] { "level_2_B_1" }));
        templates.put("level_2_C", new ArrayList());
        templates.put("level_2_D",
            Arrays.asList(new String[] { "level_2_D_1", "level_2_D_2", "level_2_D_3", "level_2_D_4" }));

        validateTemplateHierarchy(root, childNumbers, relationship, templates);
    }

    /**
     * Validates whether the given TemplateHierarchy has the specified structure.
     *
     * @param node the TemplateHierarchy node to be validated
     * @param childNumbers a Map containing the child number of each node
     * @param relationship a Map containing the node relationships
     * @param templates a Map containing the templates relationships
     */
    void validateTemplateHierarchy(TemplateHierarchy node, Map childNumbers, Map relationship, Map templates) {
        String name = node.getName();

        List templateList = (List) templates.get(name);
        assertNotNull(name + " node does not exist", templateList);

        // validate whether the templates was expected
        Template[] templateArray = node.getTemplates();
        assertEquals("templates are incorrect", templateList.size(), templateArray.length);

        for (int i = 0; i < templateArray.length; ++i) {
            String templateName = templateArray[i].getName();
            assertTrue(templateName + " template is missing", templateList.contains(templateName));
        }

        // validate whether the relationship was expected
        TemplateHierarchy[] children = node.getChildren();
        assertEquals(name + "node's number of children is incorrect",
            ((Integer) childNumbers.get(name)).intValue(), children.length);

        for (int i = 0; i < children.length; ++i) {
            String parentName = (String) relationship.get(children[i].getName());
            assertEquals(name + " node's relationship is incorrect", parentName, node.getName());
            validateTemplateHierarchy(children[i], childNumbers, relationship, templates);
        }
    }

    /**
     * <p>Helper class provides some useful helper methods.</p>
     *
     * @author zjq
     * @version 1.0
     */
    static class Helper {
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
         * Private constructor to prevent this class be instantiated.
         */
        private Helper() {
            // empty
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

                if (!sql.startsWith("-") && (sql.trim().length() > 0)) {
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
            if ((connection == null) || connection.isClosed()) {
                // create a new Connection if the connection is not initialized or closed.
                DBConnectionFactory factory = new DBConnectionFactoryImpl(JDBCPersistence.DEFAULT_DB_FACTORY_NAMESPACE);
                connection = factory.createConnection();
            }

            return connection;
        }
    }
}
