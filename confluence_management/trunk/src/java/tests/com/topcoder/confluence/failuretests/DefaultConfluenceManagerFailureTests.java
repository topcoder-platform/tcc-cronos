/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.failuretests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.confluence.ConfluenceConfigurationException;
import com.topcoder.confluence.ConfluenceManagerException;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageType;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.impl.DefaultConfluenceManager;

/**
 * <p>
 * Failure Test cases of the <code>DefaultConfluenceManager</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class DefaultConfluenceManagerFailureTests extends TestCase {

    /**
     * <p>
     * The confluence url.
     * </p>
     */
    private static final String CONFLUENCE_URL = "http://localhost:8080/rpc/soap/confluenceservice-v1";

    /**
     * <p>
     * Represents the <code>DefaultConfluenceManager</code> instance used for test.
     * </p>
     */
    private DefaultConfluenceManager manager;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        manager = new DefaultConfluenceManager();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        manager = null;
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, String)}.
     * </p>
     * <p>
     * The first argument is null, throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerStringStringFirstNull() throws Exception {
        try {
            assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager(null,
                "DefaultConfluenceManager_String_String_Accuracy_Namespace"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, String)}.
     * </p>
     * <p>
     * The first argument is empty, throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerStringStringFirstEmpty() throws Exception {
        try {
            assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager("  ",
                "DefaultConfluenceManager_String_String_Accuracy_Namespace"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, String)}.
     * </p>
     * <p>
     * The second argument is null, throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerStringStringSecondNull() throws Exception {
        try {
            assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager("test_files"
                + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_String_String.properties", null));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, String)}.
     * </p>
     * <p>
     * The second argument is empty, throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void tesDefaultConfluenceManagerStringStringSecondEmpty() throws Exception {
        try {
            assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager("test_files"
                + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_String_String.properties", "  "));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, String)}.
     * </p>
     * <p>
     * The configuration file for the given file path does not exist,so throw ConfluenceManagerException wraps the
     * IOException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerStringStringFileNotFound() throws Exception {

        try {
            new DefaultConfluenceManager("not found.xml",
                "DefaultConfluenceManager_String_String_Accuracy_Namespace");
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            assertTrue("Should be IOException.", e.getCause() instanceof IOException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, String)}.
     * </p>
     * <p>
     * The configuration file type is unknown, throw ConfluenceManagerException wraps the
     * ConfigurationPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerStringStringUnrecognizedFileType() throws Exception {

        try {
            new DefaultConfluenceManager("unknownFileType",
                "DefaultConfluenceManager_String_String_Accuracy_Namespace");
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            assertTrue("Should be ConfigurationPersistenceException.",
                e.getCause() instanceof ConfigurationPersistenceException);
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, String)}.
     * </p>
     * <p>
     * The configuration file is empty, throw ConfluenceConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerStringStringEmptyConfigurationFile() throws Exception {

        try {
            new DefaultConfluenceManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_String_String.properties",
                "DefaultConfluenceManager_String_String_Empty_Config_Namespace");
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * The configuration object is null, throw ConfluenceConfigurationException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerNullConfigurationObject() throws Exception {
        try {
            new DefaultConfluenceManager((ConfigurationObject) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * Miss property confluenceUrl, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerMissconfluenceUrl() throws Exception {
        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config = cm.getConfiguration("Miss_confluenceUrl_Namespace").getAllChildren()[0];
        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * Property confluenceUrl is empty, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerEmptyconfluenceUrl() throws Exception {
        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config = cm.getConfiguration("Empty_confluenceUrl_Namespace").getAllChildren()[0];
        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * Property maxRetriesNumber is not Integer type, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerNotIntegerTypeconfluenceUrl() throws Exception {
        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config = cm.getConfiguration("Not_Integer_Type_Namespace").getAllChildren()[0];
        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            assertTrue("Should be NumberFormatException.", e.getCause() instanceof NumberFormatException);
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * Property maxRetriesNumber is negative, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerNegativeIntegerTypeconfluenceUrl() throws Exception {
        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config = cm.getConfiguration("Negative_Integer_Type_Namespace").getAllChildren()[0];
        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * No spaceLocationsMapping child configuration, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerNoSpaceLocationsMapping() throws Exception {
        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config = cm.getConfiguration("No_SpaceLocationsMapping_Namespace").getAllChildren()[0];
        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * No templatesMapping child configuration, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerNoTemplatesMapping() throws Exception {
        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config = cm.getConfiguration("No_TemplatesMapping_Namespace").getAllChildren()[0];
        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * Miss componentBasePage configuration, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerMisscomponentBasePage() throws Exception {
        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config = cm.getConfiguration("Miss_componentBasePage_Namespace").getAllChildren()[0];
        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * Miss componentVersionPage configuration, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerMisscomponentVersionPage() throws Exception {
        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config =
            cm.getConfiguration("Miss_componentVersionPage_Namespace").getAllChildren()[0];
        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * Miss applicationBasePage configuration, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerMissapplicationBasePage() throws Exception {
        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config = cm.getConfiguration("Miss_applicationBasePage_Namespace").getAllChildren()[0];
        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * Miss applicationVersionPage configuration, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerMissapplicationVersionPage() throws Exception {
        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config =
            cm.getConfiguration("Miss_applicationVersionPage_Namespace").getAllChildren()[0];
        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * Required property is not string type, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerRequiredPropertyNotString() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");

        config.setPropertyValue("confluenceUrl", new Integer(1));

        ConfigurationObject spaceLocationsMapping = new DefaultConfigurationObject("spaceLocationsMapping");

        ConfigurationObject templatesMapping = new DefaultConfigurationObject("templatesMapping");
        templatesMapping.setPropertyValue("componentBasePage", "componentBasePage");
        templatesMapping.setPropertyValue("componentVersionPage", "componentVersionPage");
        templatesMapping.setPropertyValue("applicationBasePage", "applicationBasePage");
        templatesMapping.setPropertyValue("applicationVersionPage", "applicationVersionPage");

        config.addChild(spaceLocationsMapping);
        config.addChild(templatesMapping);

        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     * <p>
     * Optional property is not string type, throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerOptionalPropertyNotString() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");

        config.setPropertyValue("confluenceUrl", "confluenceUrl");
        config.setPropertyValue("logName", new Integer(1));

        ConfigurationObject spaceLocationsMapping = new DefaultConfigurationObject("spaceLocationsMapping");

        ConfigurationObject templatesMapping = new DefaultConfigurationObject("templatesMapping");
        templatesMapping.setPropertyValue("componentBasePage", "componentBasePage");
        templatesMapping.setPropertyValue("componentVersionPage", "componentVersionPage");
        templatesMapping.setPropertyValue("applicationBasePage", "applicationBasePage");
        templatesMapping.setPropertyValue("applicationVersionPage", "applicationVersionPage");

        config.addChild(spaceLocationsMapping);
        config.addChild(templatesMapping);

        try {
            new DefaultConfluenceManager(config);
            fail("ConfluenceConfigurationException should be thrown.");
        } catch (ConfluenceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, Map, Map)}.
     * </p>
     * <p>
     * map is null, throw ConfluenceConfigurationException
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerStringMapMapNull() throws Exception {
        try {
            new DefaultConfluenceManager(null, new HashMap<ConfluenceAssetType, String>(),
                new HashMap<ConfluencePageType, String>());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, Map, Map)}.
     * </p>
     * <p>
     * map is null, throw ConfluenceConfigurationException
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerStringMapMapEmptyString() throws Exception {
        try {
            new DefaultConfluenceManager("  ", new HashMap<ConfluenceAssetType, String>(),
                new HashMap<ConfluencePageType, String>());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }


    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, Map, Map)}.
     * </p>
     * <p>
     * map contains null, throw ConfluenceConfigurationException
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerStringMapMapContainNull() throws Exception {
        Map<ConfluenceAssetType, String> map = new HashMap<ConfluenceAssetType, String>();
        map.put(ConfluenceAssetType.APPLICATION_ARCHITECTURE, null);
        try {
            new DefaultConfluenceManager("url", map, new HashMap<ConfluencePageType, String>());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, Map, Map)}.
     * </p>
     * <p>
     * map contains empty value, throw ConfluenceConfigurationException
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConfluenceManagerStringMapMapContainEmptyvalueMap() throws Exception {
        Map<ConfluenceAssetType, String> map = new HashMap<ConfluenceAssetType, String>();
        map.put(ConfluenceAssetType.APPLICATION_ARCHITECTURE, "  ");
        try {
            new DefaultConfluenceManager("url", map, new HashMap<ConfluencePageType, String>());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).First string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_one_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure1()
        throws Exception {
        try {
            manager.retrievePage(null, "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).Second string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_one_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure2()
        throws Exception {
        try {
            manager.retrievePage("token", null, "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).Second string is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_one_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure3()
        throws Exception {
        try {
            manager.retrievePage("token", "  ", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).Third string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_one_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure4()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).Third string is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_one_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure5()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", "  ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).ConfluenceAssetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_one_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure6()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", "version", null, ConfluenceCatalog.DOT_NET,
                ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).ConfluenceCatalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_one_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure7()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                null, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).ComponentType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_one_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure8()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, (ComponentType) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for login(String, String).First string is null, throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure1() throws Exception {
        try {
            manager.login(null, "password");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for login(String, String).First string is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure2() throws Exception {
        try {
            manager.login("  ", "password");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for login(String, String).Second string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure3() throws Exception {
        try {
            manager.login("username", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for login(String, String).Second string is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Failure4() throws Exception {
        try {
            manager.login("username", "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for logout(String).string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Failure1() throws Exception {
        try {
            manager.logout(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, ComponentType componentType).First string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Failure1() throws Exception {
        try {
            manager.createPage(null, "pageName", "version", ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, ComponentType componentType).Second string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Failure2() throws Exception {
        try {
            manager.createPage("token", null, "version", ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, ComponentType componentType).Second string is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Failure3() throws Exception {
        try {
            manager.createPage("token", "  ", "version", ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, ComponentType componentType).Third string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Failure4() throws Exception {
        try {
            manager.createPage("token", "pageName", null, ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, ComponentType componentType).Third string is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Failure5() throws Exception {
        try {
            manager.createPage("token", "pageName", "  ", ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, ComponentType componentType).ConfluenceAssetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Failure6() throws Exception {
        try {
            manager.createPage("token", "pageName", "version", null, ConfluenceCatalog.DOT_NET,
                ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, ComponentType componentType).ConfluenceCatalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Failure7() throws Exception {
        try {
            manager.createPage("token", "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE, null,
                ComponentType.CUSTOM);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, ComponentType componentType).ComponentType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Failure8() throws Exception {
        try {
            manager.createPage("token", "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, (ComponentType) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, String applicationCode).First string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_Failure1() throws Exception {
        try {
            manager.createPage(null, "pageName", "version", ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, String applicationCode).Second string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_Failure2() throws Exception {
        try {
            manager.createPage("token", null, "version", ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, String applicationCode).Second string is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_Failure3() throws Exception {
        try {
            manager.createPage("token", "  ", "version", ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, String applicationCode).Third string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_Failure4() throws Exception {
        try {
            manager.createPage("token", "pageName", null, ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, String applicationCode).Third string is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_Failure5() throws Exception {
        try {
            manager.createPage("token", "pageName", "  ", ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, String applicationCode).ConfluenceAssetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_Failure6() throws Exception {
        try {
            manager.createPage("token", "pageName", "version", null, ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, String applicationCode).ConfluenceCatalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_Failure7() throws Exception {
        try {
            manager.createPage("token", "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE, null,
                "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, String applicationCode).The last is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_Failure8() throws Exception {
        try {
            manager.createPage("token", "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, (String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, String pageName, String version, ConfluenceAssetType
     * assetType, ConfluenceCatalog catalog, String applicationCode).The last is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_Failure9() throws Exception {
        try {
            manager.createPage("token", "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, Page page).String is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage3_Failure1() throws Exception {
        try {
            manager.createPage(null, new Page());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String token, Page page).Page is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage3_Failure2() throws Exception {
        try {
            manager.createPage("token", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String).First string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_two_ConfluenceAssetType_ConfluenceCatalog_String_Failure1()
        throws Exception {
        try {
            manager.retrievePage(null, "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String).Second string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_two_ConfluenceAssetType_ConfluenceCatalog_String_Failure2()
        throws Exception {
        try {
            manager.retrievePage("token", null, "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String).Second string is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_two_ConfluenceAssetType_ConfluenceCatalog_String_Failure3()
        throws Exception {
        try {
            manager.retrievePage("token", "  ", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String).Third string is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_two_ConfluenceAssetType_ConfluenceCatalog_String_Failure4()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", null, ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String).Third string is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_two_ConfluenceAssetType_ConfluenceCatalog_String_Failure5()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", "  ", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String).ConfluenceAssetType is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_two_ConfluenceAssetType_ConfluenceCatalog_String_Failure6()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", "version", null, ConfluenceCatalog.DOT_NET,
                "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String).ConfluenceCatalog is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_two_ConfluenceAssetType_ConfluenceCatalog_String_Failure7()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                null, "applicationCode");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String).The last is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_two_ConfluenceAssetType_ConfluenceCatalog_String_Failure8()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, (String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String).The last is empty, throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_two_ConfluenceAssetType_ConfluenceCatalog_String_Failure9()
        throws Exception {
        try {
            manager.retrievePage("token", "pageName", "version", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}