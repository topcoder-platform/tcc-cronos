/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0 (Unit Test)
 */
package com.topcoder.buildutility.template;

import junit.framework.TestCase;

import com.topcoder.buildutility.template.persistence.JDBCPersistence;
import com.topcoder.util.config.ConfigManager;


/**
 * <p>Unit test cases for TemplateLoader.</p>
 *
 * @author oldbig
 *
 * @version 1.0
 */
public class TemplateLoaderTests extends TestCase {

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
        removeNamespace(UNITTEST_NAMESPACE);

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
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri).</p>
     * <p>persistence is null, NullPointerException should be thrown.</p>
     */
    public void testTemplateLoader1() {
        try {
            // persistence is null, NullPointerException should be thrown
            new TemplateLoader(null, "foo");
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri).</p>
     * <p>fileServerUri is null, NullPointerException should be thrown.</p>
     */
    public void testTemplateLoader2() {
        try {
            // fileServerUri is null, NullPointerException should be thrown
            new TemplateLoader(new MockPersistence(), null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri).</p>
     * <p>fileServerUri is empty, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplateLoader3() {
        try {
            // fileServerUri is empty, IllegalArgumentException should be thrown
            new TemplateLoader(new MockPersistence(), " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri).</p>
     * <p>Validates whether the persistence was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader4() throws Exception {
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
    public void testTemplateLoader5() throws Exception {
        loader = new TemplateLoader(new MockPersistence(), "test_files/");

        // validates whether the file server uri was set properly
        UnitTestHelper.checkFileServerUriSet(loader.loadTemplateHierarchy("foo").getTemplates()[0]);
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri).</p>
     * <p>Validates whether the cache was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader6() throws Exception {
        loader = new TemplateLoader(new MockPersistence(), "test_files/");

        // the first id should be 1
        assertEquals("the id is incorrect", 1, loader.loadTemplateHierarchy("foo").getId());

        // the cache is NullCache, the next loading will get a new TemplateHierarchy with different id
        assertEquals("the id is incorrect", 2, loader.loadTemplateHierarchy("foo").getId());

    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>persistence is null, NullPointerException should be thrown.</p>
     */
    public void testTemplateLoader7() {
        try {
            // persistence is null, NullPointerException should be thrown
            new TemplateLoader(null, "foo", 1, 2);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>fileServerUri is null, NullPointerException should be thrown.</p>
     */
    public void testTemplateLoader8() {
        try {
            // fileServerUri is null, NullPointerException should be thrown
            new TemplateLoader(new MockPersistence(), null, 1, 2);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>fileServerUri is empty, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplateLoader9() {
        try {
            // fileServerUri is empty, IllegalArgumentException should be thrown
            new TemplateLoader(new MockPersistence(), " ", 1, 2);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>timeout is invalid, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplateLoader10() {
        try {
            // timeout is invalid, IllegalArgumentException should be thrown
            new TemplateLoader(new MockPersistence(), "foo", 0, 2);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>timeout is invalid, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplateLoader11() {
        try {
            // timeout is invalid, IllegalArgumentException should be thrown
            new TemplateLoader(new MockPersistence(), "foo", -1, 2);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>cacheSize is invalid, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplateLoader12() {
        try {
            // cacheSize is invalid, IllegalArgumentException should be thrown
            new TemplateLoader(new MockPersistence(), "foo", 1, 0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>cacheSize is invalid, IllegalArgumentException should be thrown.</p>
     */
    public void testTemplateLoader13() {
        try {
            // cacheSize is invalid, IllegalArgumentException should be thrown
            new TemplateLoader(new MockPersistence(), "foo", 1, -2);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }


    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>Validates whether the persistence was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader14() throws Exception {
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
    public void testTemplateLoader15() throws Exception {
        loader = new TemplateLoader(new MockPersistence(), "test_files/", 100, 100);

        // validates whether the file server uri was set properly
        UnitTestHelper.checkFileServerUriSet(loader.loadTemplateHierarchy("foo").getTemplates()[0]);
    }

    /**
     * <p>Tests the constructor - TemplateLoader(persistence, fileServerUri, timeout, cacheSize).</p>
     * <p>Validates whether the cache was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader16() throws Exception {
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
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>namespace is empty, IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader17() throws Exception {
        try {
            // namespace is empty, IllegalArgumentException should be thrown
            new TemplateLoader(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>namespace is null, NullPointerException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader18() throws Exception {
        try {
            // namespace is null, NullPointerException should be thrown
            new TemplateLoader(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>namespace is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader19() throws Exception {
        try {
            // namespace is invalid, ConfigurationException should be thrown
            new TemplateLoader("foo");
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader20() throws Exception {
        validateInvalidConfigFile("invalid1.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader21() throws Exception {
        validateInvalidConfigFile("invalid2.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader22() throws Exception {
        validateInvalidConfigFile("invalid3.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader23() throws Exception {
        validateInvalidConfigFile("invalid4.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader24() throws Exception {
        validateInvalidConfigFile("invalid5.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader25() throws Exception {
        validateInvalidConfigFile("invalid6.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader26() throws Exception {
        validateInvalidConfigFile("invalid7.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader27() throws Exception {
        validateInvalidConfigFile("invalid8.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader28() throws Exception {
        validateInvalidConfigFile("invalid9.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader29() throws Exception {
        validateInvalidConfigFile("invalid10.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader30() throws Exception {
        validateInvalidConfigFile("invalid11.xml");
    }


    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader31() throws Exception {
        validateInvalidConfigFile("invalid12.xml");
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>The configuration is invalid, ConfigurationException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader32() throws Exception {
        validateInvalidConfigFile("invalid13.xml");
    }

    /**
     * <p>Validates whether the given config file was invalid.</p>
     *
     * @param fileName the name of config file to be validated
     * @throws Exception to JUnit
     */
    private void validateInvalidConfigFile(String fileName) throws Exception {
        removeNamespace(UNITTEST_NAMESPACE);
        configManager.add(fileName);

        try {
            // the given config is invalid, ConfigurationException should be thrown
            new TemplateLoader(UNITTEST_NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>Validates whether the file server uri was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader33() throws Exception {
        removeNamespace(UNITTEST_NAMESPACE);
        configManager.add("valid1.xml");

        // the file server uri should be test_files/
        loader = new TemplateLoader(UNITTEST_NAMESPACE);

        // validates whether the file server uri was set properly
        UnitTestHelper.checkFileServerUriSet(loader.loadTemplateHierarchy("foo").getTemplates()[0]);
    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>Validates whether the cache was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader34() throws Exception {
        removeNamespace(UNITTEST_NAMESPACE);
        configManager.add("valid1.xml");

        // cache property is missing, NullCache should be used
        loader = new TemplateLoader(UNITTEST_NAMESPACE);

        // the first id should be 1
        assertEquals("the id is incorrect", 1, loader.loadTemplateHierarchy("foo").getId());
        // the second id should be 2, since NullCache is used
        assertEquals("the id is incorrect", 2, loader.loadTemplateHierarchy("foo").getId());

    }

    /**
     * <p>Tests the constructor - TemplateLoader(String namespace).</p>
     * <p>Validates whether the cache was set properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testTemplateLoader35() throws Exception {
        removeNamespace(UNITTEST_NAMESPACE);
        configManager.add("valid2.xml");

        // timeout 200 and size 1 should be used to create the cache
        loader = new TemplateLoader(UNITTEST_NAMESPACE);

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
     * <p>Argument is null, NullPointerException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testLoadTemplateHierarchy1() throws Exception {
        try {
            // name is null, NullPointerException should be thrown
            loader.loadTemplateHierarchy(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    /**
     * <p>Tests the loadTemplateHierarchy method.
     * <p>Argument is empty, IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit
     */
    public void testLoadTemplateHierarchy2() throws Exception {
        try {
            // name is empty, IllegalArgumentException should be thrown
            loader.loadTemplateHierarchy(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>Tests the loadTemplateHierarchy method.
     * <p>Validates whether the hierarchy could be retrieved properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testLoadTemplateHierarchy3() throws Exception {
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
    public void testLoadTemplateHierarchy4() throws Exception {

        // prepare database
        UnitTestHelper.buildHierarchies();

        loader = new TemplateLoader(UNITTEST_NAMESPACE);

        // retrieve the "singleton" TemplateHierarchy from database
        assertEquals("the name is incorrect", "singleton", loader.loadTemplateHierarchy("singleton").getName());

        // delete all rows
        UnitTestHelper.clearTables();
    }

}
