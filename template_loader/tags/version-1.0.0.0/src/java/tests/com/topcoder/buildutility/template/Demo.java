/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0 (Unit Test)
 */
package com.topcoder.buildutility.template;

import junit.framework.TestCase;

import com.topcoder.buildutility.template.persistence.JDBCPersistence;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import java.io.InputStream;

/**
 * <p>Component demonstration for Template Loader.</p>
 *
 * <p>The purpose of the Template Loader 1.0 component is to load the details for the
 * top-level template hierarchies from the persistent data store into a runtime memory on
 * demand. The component provides an option to cache the template hierarchies which have been
 * recently loaded from the persistent data store.</p>
 *
 * <p>This demo is separated into three parts.</p>
 *
 * <p>The first part demonstrates the usage of this component.</p>
 * <p>The second part demonstrates the usage of Template and TemplateHierarchy.</p>
 * <p>The third part demonstrates the usage of JDBCPersistence class.</p>
 *
 * @author oldbig
 *
 * @version 1.0
 */
public class Demo extends TestCase {

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

        // build the hierarchies for testing
        UnitTestHelper.buildHierarchies();
    }

    /**
     * Tear down environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {

        // clear all tables in the database
        UnitTestHelper.clearTables();

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
     * Demonstrates the general usage of this component.
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoaderDemo() throws Exception {

        // we could create TemplateLoader instance with a namespace
        TemplateLoader loader = new TemplateLoader(UNITTEST_NAMESPACE);

        // we can also create TemplateLoader instance using the specified
        // TemplateHierarchyPersistence and the file server uri, no caching
        loader = new TemplateLoader(new MockPersistence(), "test_files/");

        // we can also create TemplateLoader instance using the specified cache
        loader = new TemplateLoader(new MockPersistence(), "test_files/", 1000, 10);

        // new we can load the desired TemplateHierarchy
        TemplateHierarchy root = loader.loadTemplateHierarchy("foo");
        assertEquals("the name is incorrect", "foo", root.getName());

        // get a Template of root node
        Template template = root.getTemplates()[0];

        // get an InputStream containg the data of the template
        InputStream input = template.getData();
        input.close();

    }

    /**
     * Demonstrates the general usage of Template and TemplateHierarchy.
     */
    public void testTemplateHierarchyDemo() {

        // create a top-level TemplateHierarchy
        TemplateHierarchy root = new TemplateHierarchy(1, "root", 1);

        // create another TemplateHierarchy
        TemplateHierarchy child = new TemplateHierarchy(2, "child", 1);

        // get the id and parent id
        assertEquals("id is incorrect", 1, root.getId());
        assertEquals("parent id is incorrect", root.getId(), child.getParentId());

        // add the child node to root node
        root.addNestedHierarchy(child);

        // see whether the root has children
        assertTrue("true should be returned", root.hasChildren());

        // get all children of root
        TemplateHierarchy[] children = root.getChildren();
        assertEquals("the length is incorrect", 1, children.length);

        // create a Template
        Template template = new Template(1, "name", "des", "file", "uri");

        // get the attributes
        assertEquals("id is incorrect", 1, template.getId());
        assertEquals("name is incorrect", "name", template.getName());
        assertEquals("description is incorrect", "des", template.getDescription());
        assertEquals("file name is incorrect", "file", template.getFileName());
        assertEquals("uri is incorrect", "uri", template.getUri());

        // add the template to child node
        child.addTemplate(template);

        // see whether the root has templates
        assertTrue("true should be returned", child.hasTemplates());

        // get all templates of child
        Template[] templates = child.getTemplates();
        assertEquals("the length is incorrect", 1, templates.length);

    }


    /**
     * Demonstrates the general usage of JDBCPersistence.
     *
     * @throws Exception to JUnit
     */
    public void testJDBCPersistenceDemo() throws Exception {

        Property property = configManager.getPropertyObject(
            UNITTEST_NAMESPACE, "persistence.config");

        // create a JDBCPersistence using the given configuration property
        TemplateHierarchyPersistence persistence = new JDBCPersistence(property);

        // create a JDBCPersistence using the given DB factory and connection name
        persistence = new JDBCPersistence(
            "InformixConnection", new DBConnectionFactoryImpl(UNITTEST_NAMESPACE));

        // we can also create a JDBCPersistence instance which use the defualte DB
        // factory namespace and connection name
        persistence = new JDBCPersistence();

        // then we could get the top-level TemplateHierarchy using this persistence
        TemplateHierarchy root = persistence.getTemplateHierarchy("root");
        assertEquals("the name is incorrect", "root", root.getName());

    }

}

