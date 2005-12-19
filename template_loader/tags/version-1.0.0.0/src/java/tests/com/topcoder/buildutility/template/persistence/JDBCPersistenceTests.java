/**
 * Copyright (c) 2004, TopCoder Inc. All rights reserved.
 */
package com.topcoder.buildutility.template.persistence;

import junit.framework.TestCase;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;

import com.topcoder.buildutility.template.TemplateHierarchy;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.ConfigurationException;
import com.topcoder.buildutility.template.UnknownTemplateHierarchyException;
import com.topcoder.buildutility.template.PersistenceException;
import com.topcoder.buildutility.template.TemplateHierarchyPersistence;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * <p>Unit test cases for JDBCPersistence.</p>
 *
 * @author oldbig
 *
 * @version 1.0
 */
public class JDBCPersistenceTests extends TestCase {


    /**
     * A configuration namespace used for testing.
     */
    private static final String UNITTEST_NAMESPACE = "com.topcoder.buildutility.template.unittests";

    /**
     * A configuration namespace used for testing.
     */
    private static final String CONFIG_FILE = "config.xml";

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
        removeNamespace(UNITTEST_NAMESPACE);

        // load the configuration for testing
        configManager.add(CONFIG_FILE);

        // create the JDBCPersistence instance
        persistence = new JDBCPersistence();

        // build the hierarchies for testing
        DBHelper.buildHierarchies();

    }

    /**
     * Tear down environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {

        // clear all tables in the database
        DBHelper.clearTables();

        // remove configuration before testing
        removeNamespace(JDBCPersistence.DEFAULT_DB_FACTORY_NAMESPACE);
        removeNamespace(UNITTEST_NAMESPACE);
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
     * The default namespace does not exist, ConfigurationException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence1() throws Exception {

        // remove the default namespace
        removeNamespace(JDBCPersistence.DEFAULT_DB_FACTORY_NAMESPACE);

        try {
            // the default namespace does not exist, ConfigurationException should be thrown.
            new JDBCPersistence();
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor - JDBCPersistence().
     *
     * Tests whether JDBCPersistence could be instantiate properly.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence2() throws Exception {
        persistence = new JDBCPersistence();
        assertNotNull("Unable to instantiate JDBCPersistence.", persistence);
        assertEquals("The JDBCPersistence instance was not properly created",
            "singleton", persistence.getTemplateHierarchy("singleton").getName());
    }

    /**
     * Test the constructor - JDBCPersistence(String connectionName, DBConnectionFactory dbFactory).
     *
     * Tests whether the null connectionName is set properly.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence3() throws Exception {

        persistence = new JDBCPersistence(null, new DBConnectionFactoryImpl(UNITTEST_NAMESPACE));

        try {

            // connectionName is null and there no default connection, PersistenceException should be thrown.
            persistence.getTemplateHierarchy("bar");
            fail("PersistenceException should be thrown");
        } catch (PersistenceException e) {
            // success
        }

    }


    /**
     * Test the constructor - JDBCPersistence(String connectionName, DBConnectionFactory dbFactory).
     *
     * The dbFactory is null, NullPointerException should be thrown.
     */
    public void testJDBCPersistence4() {

        try {
            // The connectionName is null, NullPointerException should be thrown.
            new JDBCPersistence("foo", null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * Test the constructor - JDBCPersistence(String connectionName, DBConnectionFactory dbFactory).
     *
     * The connectionName is empty, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence5() throws Exception {

        try {
            // The connectionName is empty, IllegalArgumentException should be thrown.
            new JDBCPersistence(" ", new DBConnectionFactoryImpl(UNITTEST_NAMESPACE));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }


    /**
     * Test the constructor - JDBCPersistence(String connectionName, DBConnectionFactory dbFactory).
     *
     * The connectionName is invalid, PersistenceException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence6() throws Exception {

        persistence = new JDBCPersistence("foo", new DBConnectionFactoryImpl(UNITTEST_NAMESPACE));

        try {
            persistence.getTemplateHierarchy("bar");
            // The connectionName is invalid, PersistenceException should be thrown.
            fail("PersistenceException should be thrown");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test the constructor - JDBCPersistence(String connectionName, DBConnectionFactory dbFactory).
     *
     * Tests whether JDBCPersistence could be instantiate properly.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence7() throws Exception {
        persistence = new JDBCPersistence("TestConnection", new DBConnectionFactoryImpl(UNITTEST_NAMESPACE));

        // Tests whether JDBCPersistence could be instantiate properly.
        assertNotNull("Unable to instantiate JDBCPersistence.", persistence);
        assertEquals("The JDBCPersistence instance was not properly created",
            "singleton", persistence.getTemplateHierarchy("singleton").getName());
    }


    /**
     * Test the constructor - JDBCPersistence(Property property).
     *
     * Tests whether JDBCPersistence could be instantiate properly.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence8() throws Exception {
        Property configProperty = configManager.getPropertyObject(UNITTEST_NAMESPACE, "persistence.config");
        // create the JDBCPersistence with a valid Property
        persistence = new JDBCPersistence(configProperty);

        // Tests whether JDBCPersistence could be instantiate properly.
        assertNotNull("Unable to instantiate JDBCPersistence.", persistence);
        assertEquals("The JDBCPersistence instance was not properly created",
            "singleton", persistence.getTemplateHierarchy("singleton").getName());
    }

    /**
     * Test the constructor - JDBCPersistence(Property property).
     *
     * Tests whether JDBCPersistence could be instantiate properly.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence9() throws Exception {

        Property configProperty = configManager.getPropertyObject(UNITTEST_NAMESPACE, "persistence");

        // if some properties is missing, the default namespace could be used.
        persistence = new JDBCPersistence(configProperty);

        // Tests whether JDBCPersistence could be instantiate properly.
        assertNotNull("Unable to instantiate JDBCPersistence.", persistence);
        assertEquals("The JDBCPersistence instance was not properly created",
            "singleton", persistence.getTemplateHierarchy("singleton").getName());
    }

    /**
     * Test the constructor - JDBCPersistence(Property property).
     *
     * The property is null, NullPointerException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistence10() throws Exception {

        try {
            // The property is null, NullPointerException should be thrown.
            new JDBCPersistence(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * Test the getTemplateHierarchy method.
     *
     * The name is null, NullPointerException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy1() throws Exception {

        try {
            // The property is null, NullPointerException should be thrown.
            persistence.getTemplateHierarchy(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * Test the getTemplateHierarchy method.
     *
     * The name is empty, IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy2() throws Exception {

        try {
            // The name is empty, IllegalArgumentException should be thrown.
            persistence.getTemplateHierarchy(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the getTemplateHierarchy method.
     *
     * The name dose not exist, UnknownTemplateHierarchyException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy3() throws Exception {

        try {
            // The name is empty, UnknownTemplateHierarchyException should be thrown.
            persistence.getTemplateHierarchy("foo");
            fail("UnknownTemplateHierarchyException should be thrown");
        } catch (UnknownTemplateHierarchyException e) {
            // success
        }
    }

    /**
     * Test the getTemplateHierarchy method.
     *
     * Tests loading a general hierarchy.
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy4() throws Exception {

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
        templates.put("Start", Arrays.asList(new String[] {"Start"}));
        templates.put("Java", Arrays.asList(new String[] {"Java"}));
        templates.put(".NET", Arrays.asList(new String[] {".NET"}));
        templates.put("WebServer", Arrays.asList(new String[] {"WebServer"}));
        templates.put("Weblogic", Arrays.asList(new String[] {"Weblogic"}));
        templates.put("Debug", Arrays.asList(new String[] {"Debug"}));
        templates.put("DebugOn", Arrays.asList(new String[] {"DebugOn"}));
        templates.put("DebugOff", Arrays.asList(new String[] {"DebugOff"}));

        validateTemplateHierarchy(root, childNumbers, relationship, templates);
    }

    /**
     * Test the getTemplateHierarchy method.
     *
     * Tests loading a template hierarchy has no children and no template
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy5() throws Exception {

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
    public void testGetTemplateHierarchy6() throws Exception {

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
        templates.put("ten_templates", Arrays.asList(
            new String[] {"template0", "template1", "template2", "template3", "template4",
                          "template5", "template6", "template7", "template8", "template9"}));

        validateTemplateHierarchy(root, childNumbers, relationship, templates);
    }


    /**
     * Test the getTemplateHierarchy method.
     *
     * Tests loading a hierarchy like a binary tree.
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy7() throws Exception {

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
        templates.put("root", Arrays.asList(new String[] {"root_1", "root_2", "root_3"}));
        templates.put("level_1_A", new ArrayList());
        templates.put("level_1_B", Arrays.asList(new String[] {"level_1_B_1", "level_1_B_2"}));
        templates.put("level_2_A", new ArrayList());
        templates.put("level_2_B", Arrays.asList(new String[] {"level_2_B_1"}));
        templates.put("level_2_C", new ArrayList());
        templates.put("level_2_D", Arrays.asList(
            new String[] {"level_2_D_1", "level_2_D_2", "level_2_D_3", "level_2_D_4"}));

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
        TemplateHierarchy children[] = node.getChildren();
        assertEquals(name + "node's number of children is incorrect",
            ((Integer) childNumbers.get(name)).intValue(), children.length);
        for (int i = 0; i < children.length; ++i) {
            String parentName = (String) relationship.get(children[i].getName());
            assertEquals(name + " node's relationship is incorrect", parentName, node.getName());
            validateTemplateHierarchy(children[i], childNumbers, relationship, templates);
        }

    }

    /**
     * Test the getTemplateHierarchy method.
     *
     * Loads a bad TemplateHierarchy, PersistenceException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateHierarchy8() throws Exception {

        try {
            // Loads a bad TemplateHierarchy, PersistenceException should be thrown.
            persistence.getTemplateHierarchy("bad");
            fail("PersistenceException should be thrown");
        } catch (PersistenceException e) {
            // success
        }
    }

}
