/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.jira.managers.JiraManagerConfigurationException;
import com.topcoder.jira.webservices.client.JiraManagementServiceClient;
import com.topcoder.jira.webservices.delegate.JiraManagerWebServiceDelegate;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

/**
 * Some tests for Util class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UtilTest extends TestCase {
    /**
     * Logger to use in tests.
     */
    private Log log;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(UtilTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        log = LogManager.getLog();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * First test for checkNull method, not null.
     */
    public void testCheckNull1() {
        Util.checkNull("name", "some string");
    }

    /**
     * Second test for checkNull method, null itself. IllegalArgumentException expected.
     */
    public void testCheckNull2() {
        try {
            Util.checkNull("name", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * First test for checkString method, usual string.
     */
    public void testCheckString1() {
        Util.checkString("name", "some string");
    }

    /**
     * Second test for checkString method, null parameter. IllegalArgumentException expected.
     */
    public void testCheckString2() {
        try {
            Util.checkString("name", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Second test for checkString method, empty string. IllegalArgumentException expected.
     */
    public void testCheckString3() {
        try {
            Util.checkString("name", "  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * First test for checkNull method, not null.
     */
    public void testCheckNullWithLog1() {
        Util.checkNull(log, "name", "some string");
    }

    /**
     * Second test for checkNull method, null itself. IllegalArgumentException expected.
     */
    public void testCheckNullWithLog2() {
        try {
            Util.checkNull(log, "name", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * First test for checkString method, usual string.
     */
    public void testCheckStringWithLog1() {
        Util.checkString(log, "name", "some string");
    }

    /**
     * Second test for checkString method, null parameter. IllegalArgumentException expected.
     */
    public void testCheckStringWithLog2() {
        try {
            Util.checkString(log, "name", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Second test for checkString method, empty string. IllegalArgumentException expected.
     */
    public void testCheckStringWithLog3() {
        try {
            Util.checkString(log, "name", "  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests logEnter method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogEnter() throws Exception {
        Util.logEnter(log, "method", "param1", 34);
    }

    /**
     * Tests logEnter method when log is not provided.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogEnterForNoLog() throws Exception {
        Util.logEnter(null, "method", "param1", 34);
    }

    /**
     * Tests logExit(Log, String, String) method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogExit1() throws Exception {
        Util.logExit(log, "method", "result");
    }

    /**
     * Tests logExit(Log, String, String) method when log is not provided.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogExit1ForNoLog() throws Exception {
        Util.logExit(null, "method", "result");
    }

    /**
     * Tests logExit(Log, String) method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogExit2() throws Exception {
        Util.logExit(log, "method");
    }

    /**
     * Tests logExit(Log, String) method when log is not provided.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogExit2ForNoLog() throws Exception {
        Util.logExit(null, "method");
    }

    /**
     * Tests logError method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogError() throws Exception {
        Util.logError(log, new IOException("cause"));
    }

    /**
     * Tests logError method when log is not provided.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogErrorNoLog() throws Exception {
        Util.logError(null, new IOException("cause"));
    }

    /**
     * Tests readConfiguration method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testReadConfiguration() throws Exception {
        ConfigurationObject config = Util.readConfiguration(JiraManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                JiraManagerWebServiceDelegate.DEFAULT_NAMESPACE);
        assertEquals("log name", "my_log", config.getChild("default").getPropertyValue("log_name"));
        assertNotNull("factory config", config.getChild("spec_factory_config"));
    }

    /**
     * Tests readConfiguration method when namespace can't be found. JiraManagerConfigurationException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testReadConfigurationNoNamespace() throws Exception {
        try {
            Util.readConfiguration(JiraManagerWebServiceDelegate.DEFAULT_CONFIG_PATH, "no_such_namespace");
            fail("JiraManagerConfigurationException expected.");
        } catch (JiraManagerConfigurationException ex) {
            // success
        }
    }

    /**
     * Tests readConfiguration method when file can't be found. JiraManagerConfigurationException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testReadConfigurationForNoFile() throws Exception {
        try {
            Util.readConfiguration("a", "any_namespace");
            fail("JiraManagerConfigurationException expected.");
        } catch (JiraManagerConfigurationException ex) {
            // success
        }
    }

    /**
     * Tests getChild method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetChild() throws Exception {
        ConfigurationObject config = Util.readConfiguration(JiraManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                JiraManagerWebServiceDelegate.DEFAULT_NAMESPACE);
        assertNotNull("child found", Util.getChild(config, "spec_factory_config"));
    }

    /**
     * Tests getProperty method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProperty() throws Exception {
        ConfigurationObject config = Util.readConfiguration(JiraManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                JiraManagerWebServiceDelegate.DEFAULT_NAMESPACE);
        config = config.getChild("default");
        assertEquals("property value", "my_log", Util.getProperty(config, "log_name", true));
    }

    /**
     * Tests getProperty method when property is not found, but required. JiraManagerConfigurationException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetPropertyForNullRequired() throws Exception {
        ConfigurationObject config = Util.readConfiguration("test_config.properties", "namespace1");
        try {
            Util.getProperty(config, "no_such_property", true);
            fail("JiraManagerConfigurationException expected.");
        } catch (JiraManagerConfigurationException ex) {
            // success
        }
    }

    /**
     * Tests getProperty method when property is empty string and optional. Null expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetPropertyForEmptyOptional() throws Exception {
        ConfigurationObject config = Util.readConfiguration("test_config.properties", "namespace1");
        assertNull("null expected", Util.getProperty(config, "empty", false));
    }

    /**
     * Tests getProperty method when property is not a string. JiraManagerConfigurationException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetPropertyForNotString() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("root");
        config.setPropertyValue("long", 123l);
        try {
            Util.getProperty(config, "long", false);
            fail("JiraManagerConfigurationException expected.");
        } catch (JiraManagerConfigurationException ex) {
            // success
        }
    }

    /**
     * Tests createFactory method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateFactory() throws Exception {
        ConfigurationObject config = Util.readConfiguration(JiraManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                JiraManagerWebServiceDelegate.DEFAULT_NAMESPACE);
        assertNotNull("created instance", Util.createFactory(config));
    }

    /**
     * Tests createFactory method when no configuration for factory provided.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateFactoryForNoConfig() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("root");
        assertNotNull("created instance", Util.createFactory(config));
    }

    /**
     * Tests createFactory method when configuration is malformed. JiraManagerConfigurationException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateFactoryForBadConfig() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("root");
        ConfigurationObject factoryConfig = new DefaultConfigurationObject("spec_factory_config");
        config.addChild(factoryConfig);
        factoryConfig.addChild(new DefaultConfigurationObject("some_key"));
        try {
            Util.createFactory(config);
            fail("JiraManagerConfigurationException expected.");
        } catch (JiraManagerConfigurationException ex) {
            // success
        }
    }

    /**
     * Tests createObject method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateObject() throws Exception {
        ConfigurationObject config = Util.readConfiguration(JiraManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                JiraManagerWebServiceDelegate.DEFAULT_NAMESPACE);
        ObjectFactory factory = Util.createFactory(config);
        JiraManagementServiceClient result =
                Util.createObject(factory, "service_client", JiraManagementServiceClient.class);
        assertTrue("created instance", result instanceof MockJiraManagementServiceClient);
    }

    /**
     * Tests createObject method when key is not found in config. JiraManagerConfigurationException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateObjectForBadKey() throws Exception {
        ConfigurationObject config = Util.readConfiguration(JiraManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                JiraManagerWebServiceDelegate.DEFAULT_NAMESPACE);
        ObjectFactory factory = Util.createFactory(config);

        try {
            Util.createObject(factory, "no_such_key", String.class);
            fail("JiraManagerConfigurationException expected.");
        } catch (JiraManagerConfigurationException ex) {
            // success
        }
    }

    /**
     * Tests createObject method when create object has wrong type. JiraManagerConfigurationException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateObjectForBadType() throws Exception {
        ConfigurationObject config = Util.readConfiguration(JiraManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                JiraManagerWebServiceDelegate.DEFAULT_NAMESPACE);
        ObjectFactory factory = Util.createFactory(config);

        try {
            Util.createObject(factory, "service_client", String.class);
            fail("JiraManagerConfigurationException expected.");
        } catch (JiraManagerConfigurationException ex) {
            // success
        }
    }
}