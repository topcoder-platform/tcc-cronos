/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.accuracytests;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace;
import com.atlassian.www.software.confluence.$Proxy77.ConfluenceServiceLocator;
import com.atlassian.www.software.confluence.$Proxy77.ConfluenceSoapService;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageType;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.impl.DefaultConfluenceManager;

/**
 * <p>
 * Accuracy test for the <code>DefaultConfluenceManager</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultConfluenceManagerAccuracyTest extends TestCase {

    /**
     * <p>
     * Represents the confluence url.
     * </p>
     */
    private static final String CONFLUENCE_URL = "http://localhost:8080/rpc/soap/confluenceservice-v1";

    /**
     * <p>
     * Represents the user name.
     * </p>
     */
    private static final String USER_NAME = "tcsdeveloper";

    /**
     * <p>
     * Represents the password.
     * </p>
     */
    private static final String PASSWORD = "TCSDEVELOPER";

    /**
     * <p>
     * Represents the default value of 'componentDevelopment'.
     * </p>
     */
    private static final String DEFAULL_COMPONENT_DEVELOPMENT_VALUE = "ff";

    /**
     * <p>
     * Represents the application code for test.
     * </p>
     */
    private static final String APPLICATION_CODE_SPACE = "test";

    /**
     * <p>
     * Represents the default value of 'applicationSpecification'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_SPECIFICATION_VALUE = "$CODENAME$";

    /**
     * <p>
     * Represents the default value of 'componentDesign'.
     * </p>
     */
    private static final String DEFAULL_COMPONENT_DESIGN_VALUE = "http://www.topcoder.com/wiki/display/docs/Design";

    /**
     * <p>
     * Represents the default value of 'applicationArchitecture'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_ARCHITECTURE_VALUE = "http://www.topcoder.com/wiki/display/docs/Architecture";

    /**
     * <p>
     * Represents the default value of 'applicationAssembly'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_ASSEMBLY_VALUE = "http://www.topcoder.com/wiki/display/docs/Assembly";

    /**
     * <p>
     * Represents the default value of 'applicationTesting'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_TESTING_VALUE = "http://www.topcoder.com/wiki/display/docs/Testing";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'componentBasePage'.
     * </p>
     */
    private static final String COMPONENT_BASE_PAGE = "componentBasePage";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'componentVersionPage'.
     * </p>
     */
    private static final String COMPONENT_VERSION_PAGE = "componentVersionPage";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'applicationBasePage'.
     * </p>
     */
    private static final String APPLICATION_BASE_PAGE = "applicationBasePage";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'applicationVersionPage'.
     * </p>
     */
    private static final String APPLICATION_VERSION_PAGE = "applicationVersionPage";

    /**
     * <p>
     * Represents the space locations used for testing.
     * </p>
     */
    private Map<ConfluenceAssetType, String> spaceLocations;

    /**
     * <p>
     * Represents the templates page used for testing.
     * </p>
     */
    private Map<ConfluencePageType, String> templates;

    /**
     * <p>
     * Represents the <code>DefaultConfluenceManager</code> instance used for test.
     * </p>
     */
    private DefaultConfluenceManager manager;

    /**
     * <p>
     * Represents the confluence service used to set up some test environments.
     * </p>
     */
    private ConfluenceSoapService confluenceService;

    /**
     * <p>
     * The token used in test.
     * </p>
     */
    private String token;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        confluenceService = new ConfluenceServiceLocator().getConfluenceSoapService(new URL(CONFLUENCE_URL));
        spaceLocations = new HashMap<ConfluenceAssetType, String>();
        spaceLocations.put(ConfluenceAssetType.COMPONENT_DESIGN, DEFAULL_COMPONENT_DESIGN_VALUE);
        spaceLocations.put(ConfluenceAssetType.COMPONENT_DEVELOPMENT, DEFAULL_COMPONENT_DEVELOPMENT_VALUE);
        spaceLocations.put(ConfluenceAssetType.APPLICATION_SPECIFICATION, DEFAULL_APPLICATION_SPECIFICATION_VALUE);
        spaceLocations.put(ConfluenceAssetType.APPLICATION_ARCHITECTURE, DEFAULL_APPLICATION_ARCHITECTURE_VALUE);
        spaceLocations.put(ConfluenceAssetType.APPLICATION_ASSEMBLY, DEFAULL_APPLICATION_ASSEMBLY_VALUE);
        spaceLocations.put(ConfluenceAssetType.APPLICATION_TESTING, DEFAULL_APPLICATION_TESTING_VALUE);

        templates = new HashMap<ConfluencePageType, String>();
        templates.put(ConfluencePageType.COMPONENT_BASE_PAGE, COMPONENT_BASE_PAGE);
        templates.put(ConfluencePageType.COMPONENT_VERSION_PAGE, COMPONENT_VERSION_PAGE);
        templates.put(ConfluencePageType.APPLICATION_BASE_PAGE, APPLICATION_BASE_PAGE);
        templates.put(ConfluencePageType.APPLICATION_VERSION_PAGE, APPLICATION_VERSION_PAGE);

        manager = new DefaultConfluenceManager(CONFLUENCE_URL, spaceLocations, templates);
        token = manager.login(USER_NAME, PASSWORD);

        RemoteSpace space = new RemoteSpace();
        space.setUrl(spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        space.setName(spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        space.setKey(spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        confluenceService.addSpace(token, space);

        RemoteSpace space1 = new RemoteSpace();
        space1.setUrl(spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        space1.setName(APPLICATION_CODE_SPACE);
        space1.setKey(APPLICATION_CODE_SPACE);
        confluenceService.addSpace(token, space1);
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
        confluenceService.removeSpace(token, spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        confluenceService.removeSpace(token, APPLICATION_CODE_SPACE);
        manager.logout(token);
        spaceLocations = null;
        templates = null;

        manager = null;
        confluenceService = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DefaultConfluenceManager()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DefaultConfluenceManager(String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager(
                "com.topcoder.confluence.impl.DefaultConfluenceManager"));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DefaultConfluenceManager(ConfigurationObject)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor3Accuracy() throws Exception {
        ConfigurationFileManager cm = new ConfigurationFileManager("test_files" + File.separator + "config_files"
                + File.separator + "DefaultConfluenceManager_Configuration.properties");

        ConfigurationObject config = cm.getConfiguration("DefaultConfluenceManager_Configuration_Accuracy_Namespace")
                .getAllChildren()[0];
        assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager(config));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DefaultConfluenceManager(String, String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor4Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager("test_files"
                + File.separator + "config_files" + File.separator
                + "DefaultConfluenceManager_String_String.properties",
                "DefaultConfluenceManager_String_String_Accuracy_Namespace"));
    }

    /**
     * <p>
     * Accuracy test the constructor <code>DefaultConfluenceManager(String, Map, Map)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor5Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new DefaultConfluenceManager(CONFLUENCE_URL,
                new HashMap<ConfluenceAssetType, String>(), new HashMap<ConfluencePageType, String>()));
    }

    /**
     * <p>
     * Accuracy test for the method <code>login(String, String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testLoginAccuracy() throws Exception {
        assertNotNull("Should not be null token returned.", manager.login(USER_NAME, PASSWORD));
    }

    /**
     * <p>
     * Accuracy test for the method <code>logout(String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testLogoutAccuracy() throws Exception {
        DefaultConfluenceManager confluenceManager = new DefaultConfluenceManager(CONFLUENCE_URL, spaceLocations,
                templates);
        confluenceManager.logout(confluenceManager.login(USER_NAME, PASSWORD));
    }

    /**
     * <p>
     * Accuracy test for the method <code>createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage1Accuracy1() throws Exception {
        manager.createPage(token, "createPageOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        Page page = manager.retrievePage(token, "createPageOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);
        assertNotNull("The version page should exist.", page);
    }

    /**
     * <p>
     * Accuracy test for the method <code> createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType)</code>.
     * </p>
     * <p>
     * Verify that the page is created successfully.The base page exist and version page does not exist.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage1Accuracy2() throws Exception {

        manager.createPage(token, "createPageOneOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        manager.createPage(token, "createPageOneOne", "2", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        Page page1 = manager.retrievePage(token, "createPageOneOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);
        assertNotNull("The version page should exist.", page1);

        Page page2 = manager.retrievePage(token, "createPageOneOne", "2", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);
        assertNotNull("The version page should exist.", page2);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType)</code>.
     * </p>
     * <p>
     * Verify that the page is created successfully.Both the base page and version page exist.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage1Accuracy3() throws Exception {
        manager.createPage(token, "createPageOneOneOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        manager.createPage(token, "createPageOneOneOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        Page page = manager.retrievePage(token, "createPageOneOneOne", "1",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT, ConfluenceCatalog.JAVA, ComponentType.GENERIC);
        assertNotNull("The version page should exist.", page);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createPage(String, String, String, ConfluenceAssetType,
     * ConfluenceCatalog, String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage2Accuracy() throws Exception {
        manager.createPage(token, "createPageTwo", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, "testapplicationcode");

        Page page = manager.retrievePage(token, "createPageTwo", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, "testapplicationcode");
        assertNotNull("The version page should exist.", page);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createPage(String, Page)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3Accuracy1() throws Exception {

        Page page = new Page();
        page.setComponentType(ComponentType.CUSTOM);
        page.setAssetType(ConfluenceAssetType.COMPONENT_DEVELOPMENT);
        page.setCatalog(ConfluenceCatalog.DOT_NET);
        page.setAssetName("assetname");
        page.setVersion("1");
        manager.createPage(token, page);

        Page page1 = manager.retrievePage(token, "assetname", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
        assertNotNull("The version page should exist.", page1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createPage(String, Page)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3Accuracy2() throws Exception {
        Page page = new Page();
        page.setApplicationCode(APPLICATION_CODE_SPACE);
        page.setAssetType(ConfluenceAssetType.APPLICATION_SPECIFICATION);
        page.setCatalog(ConfluenceCatalog.DOT_NET);
        page.setAssetName("assetname");
        page.setVersion("1");
        manager.createPage(token, page);

        Page page1 = manager.retrievePage(token, "assetname", "1", ConfluenceAssetType.APPLICATION_SPECIFICATION,
                ConfluenceCatalog.DOT_NET, APPLICATION_CODE_SPACE);
        assertNotNull("The version page should exist.", page1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrievePage(String, String, String, ConfluenceAssetType,
     * ConfluenceCatalog, ComponentType)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage1Accuracy() throws Exception {
        manager.createPage(token, "createPageThree", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        Page page = manager.retrievePage(token, "createPageThree", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);
        assertNotNull("The version page should exist.", page);
        assertEquals("Version should be 1.", "1", page.getVersion());
        assertEquals("ConfluenceAssetType should be COMPONENT_DEVELOPMENT.",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT, page.getAssetType());
        assertEquals("ComponentType should be GENERIC.", ComponentType.GENERIC, page.getComponentType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrievePage(String, String, String, ConfluenceAssetType,
     * ConfluenceCatalog, String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2Accuracy() throws Exception {
        Page page = new Page();
        page.setApplicationCode(APPLICATION_CODE_SPACE);
        page.setAssetType(ConfluenceAssetType.APPLICATION_SPECIFICATION);
        page.setCatalog(ConfluenceCatalog.DOT_NET);
        page.setAssetName("testname");
        page.setVersion("3");
        manager.createPage(token, page);

        Page page1 = manager.retrievePage(token, "testname", "3", ConfluenceAssetType.APPLICATION_SPECIFICATION,
                ConfluenceCatalog.DOT_NET, APPLICATION_CODE_SPACE);
        assertNotNull("The version page should exist.", page1);
        assertEquals("Version should be 3.", "3", page.getVersion());
        assertEquals("ConfluenceAssetType should be APPLICATION_SPECIFICATION.",
                ConfluenceAssetType.APPLICATION_SPECIFICATION, page.getAssetType());
        assertEquals("applicationCode should be APPLICATION_CODE.", APPLICATION_CODE_SPACE, page.getApplicationCode());
        assertEquals("ConfluenceCatalog should be DOT_NET.", ConfluenceCatalog.DOT_NET, page.getCatalog());
    }
}