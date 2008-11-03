/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.impl;

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

/**
 * <p>
 * One Part of UnitTest cases of the <code>DefaultConfluenceManager</code> class.
 * </p>
 * <p>
 * This unit tests include two parts,one part is all the constructors unit cases,the other part is normal failure
 * tests of all the service methods.Note: the normal failure tests means the failure cases before call the
 * underlying service method actually they'er simple check parameters failure cases.
 * </p>
 * <p>
 * BTW,this unit tests are standalone, independent of the confluence web services.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultConfluenceManagerTest1 extends TestCase {

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
     * Accuracy test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, String)}.
     * </p>
     * <p>
     *
     * <pre>
     * property file: test_files\config_files\DefaultConfluenceManager_String_String.properties
     * name space: DefaultConfluenceManager_String_String_Accuracy_Namespace
     * the real configuration file: test_files\config_files\DefaultConfluenceManager_Config.xml
     * </pre>
     *
     * So we can successfully create the <code>DefaultConfluenceManager</code> instance.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_String_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager("test_files"
            + File.separator + "config_files" + File.separator
            + "DefaultConfluenceManager_String_String.properties",
            "DefaultConfluenceManager_String_String_Accuracy_Namespace"));
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, String)}.
     * </p>
     * <p>
     * The first argument is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_String_First_String_Null_Failure() throws Exception {
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
     * The first argument is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_String_First_String_Empty_Failure() throws Exception {
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
     * The second argument is null,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_String_Second_String_Null_Failure() throws Exception {
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
     * The second argument is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_String_Second_String_Empty_Failure() throws Exception {
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
    public void test_DefaultConfluenceManager_String_String_File_Not_Found_Failure() throws Exception {

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
     * The configuration file type is unknown,so throw ConfluenceManagerException wraps the
     * ConfigurationPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_String_Unrecognized_File_Type_Failure() throws Exception {

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
     * The configuration file is empty,so throw ConfluenceConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_String_Empty_Configuration_File_Failure() throws Exception {

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
     * Accuracy test case for {@link DefaultConfluenceManager#DefaultConfluenceManager()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", manager);
    }

    /**
     * <p>
     * Accuracy test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager(
            "com.topcoder.confluence.impl.DefaultConfluenceManager"));
    }

    /**
     * <p>
     * Accuracy test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_ConfigurationObject_Accuracy() throws Exception {

        ConfigurationFileManager cm =
            new ConfigurationFileManager("test_files" + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config =
            cm.getConfiguration("DefaultConfluenceManager_Configuration_Accuracy_Namespace").getAllChildren()[0];
        assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager(config));
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(ConfigurationObject)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Null_ConfigurationObject_Failure() throws Exception {
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
     * Miss property confluenceUrl, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Miss_confluenceUrl_Failure() throws Exception {
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
     * Property confluenceUrl is empty, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Empty_confluenceUrl_Failure() throws Exception {
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
     * Property maxRetriesNumber is not Integer type, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Not_Integer_Type_confluenceUrl_Failure() throws Exception {
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
     * Property maxRetriesNumber is negative, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Negative_Integer_Type_confluenceUrl_Failure() throws Exception {
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
     * No spaceLocationsMapping child configuration, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_No_SpaceLocationsMapping_Failure() throws Exception {
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
     * No templatesMapping child configuration, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_No_TemplatesMapping_Failure() throws Exception {
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
     * Miss componentBasePage configuration, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Miss_componentBasePage_Failure() throws Exception {
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
     * Miss componentVersionPage configuration, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Miss_componentVersionPage_Failure() throws Exception {
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
    public void test_DefaultConfluenceManager_Miss_applicationBasePage_Failure() throws Exception {
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
     * Miss applicationVersionPage configuration, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Miss_applicationVersionPage_Failure() throws Exception {
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
     * Required property is not string type, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Required_Property_Not_String_Type_Failure() throws Exception {
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
     * Optional property is not string type, so throw ConfluenceConfigurationException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_Optional_Property_Not_String_Type_Failure() throws Exception {
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
     * Accuracy test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, Map, Map)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_Map_Map_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager(CONFLUENCE_URL,
            new HashMap<ConfluenceAssetType, String>(), new HashMap<ConfluencePageType, String>()));
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, Map, Map)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_Map_Map_Null_String_Failure() throws Exception {
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
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_Map_Map_Empty_String_Failure() throws Exception {
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
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_Map_Map_Null_First_Map_Failure() throws Exception {
        try {
            new DefaultConfluenceManager("url", null, new HashMap<ConfluencePageType, String>());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, Map, Map)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_Map_Map_Contain_Null_key_Map_Failure() throws Exception {
        Map<ConfluenceAssetType, String> map = new HashMap<ConfluenceAssetType, String>();
        map.put(null, "aa");
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
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_Map_Map_Contain_Null_value_Map_Failure() throws Exception {
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
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_Map_Map_Contain_Empty_value_Map_Failure() throws Exception {
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
     * Failure test case for {@link DefaultConfluenceManager#DefaultConfluenceManager(String, Map, Map)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_DefaultConfluenceManager_String_Map_Map_Null_Second_Map_Failure() throws Exception {
        try {
            new DefaultConfluenceManager("url", new HashMap<ConfluenceAssetType, String>(), null);
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure1()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure2()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure3()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure4()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure5()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure6()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure7()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_ComponentType_Failure8()
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
     * Failure test case for login(String, String).First string is null,so throw IAE.
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_String_Failure1()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_String_Failure2()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_String_Failure3()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_String_Failure4()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_String_Failure5()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_String_Failure6()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_String_Failure7()
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
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_String_Failure8()
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
     * String).The last is empty,so throw IAE.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage_String_String_String_ConfluenceAssetType_ConfluenceCatalog_String_Failure9()
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