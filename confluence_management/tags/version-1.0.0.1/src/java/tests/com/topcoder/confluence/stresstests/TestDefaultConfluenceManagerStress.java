/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.stresstests;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace;
import com.atlassian.www.software.confluence.$Proxy77.ConfluenceServiceLocator;
import com.atlassian.www.software.confluence.$Proxy77.ConfluenceSoapService;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.ConfluencePageType;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.impl.DefaultConfluenceManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>DefaultConfluenceManager </code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestDefaultConfluenceManagerStress extends TestCase {

    /**
     * Represents the confluence url which will be loaded from xml file.
     */
    private static String CONFLUENCE_URL = null;

    /**
     * Represents the user name for login. It will be loaded from xml file.
     */
    private static String USER_NAME = null;

    /**
     * Represents the DefaultConfluenceManager instance for testing.
     */
    private DefaultConfluenceManager manager = null;

    /**
     * <p>
     * Represents the confluence service.
     * </p>
     */
    private ConfluenceSoapService confluenceService;

    /**
     * Represents the password for testing.
     */
    private String password = null;

    /**
     * Represents the token returned from login.
     */
    private String token = null;

    /**
     * Represents the spaceLocations.
     */
    private Map<ConfluenceAssetType, String> spaceLocations = null;

    /**
     * Set up.
     *
     * @throws Exception
     *             to junit
     */
    public void setUp() throws Exception {
        XMLFilePersistence persistence = new XMLFilePersistence();
        File file = new File("test_files/stress/config.xml");
        ConfigurationObject obj = persistence.loadFile("stress", file).getChild("stress");

        // load the config values from config file including the username, password, and confluence_url.
        USER_NAME = (String) obj.getPropertyValue("username");
        this.password = (String) obj.getPropertyValue("password");
        CONFLUENCE_URL = (String) obj.getPropertyValue("confluence_url");

        // create a ConfluenceService instance for testing.
        confluenceService = new ConfluenceServiceLocator().getConfluenceSoapService(new URL(CONFLUENCE_URL));

        // create the SpaceLocations.
        spaceLocations = new HashMap<ConfluenceAssetType, String>();
        spaceLocations.put(ConfluenceAssetType.COMPONENT_DEVELOPMENT, "dev");

        // creates tempates.
        Map<ConfluencePageType, String> templates = new HashMap<ConfluencePageType, String>();
        templates.put(ConfluencePageType.COMPONENT_BASE_PAGE, "componentBasePage");

        manager = new DefaultConfluenceManager(CONFLUENCE_URL, spaceLocations, templates);

        // hold the login result.
        token = manager.login(USER_NAME, password);

        RemoteSpace space = new RemoteSpace();
        space.setUrl(spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        space.setName(spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        space.setKey(spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));

        confluenceService.addSpace(token, space);
    }

    /**
     * Tear down the environment.
     *
     * @throws Exception
     *             to junit
     */
    public void tearDown() throws Exception {
        // remove the space.
        confluenceService.removeSpace(token, spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));

        spaceLocations = null;

        manager = null;
    }

    /**
     * Test method for 'com.topcoder.confluence.impl.DefaultConfluenceManager.login(String, String)'.
     *
     * @throws Exception
     *             to junit
     */
    public void testLogin() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            assertNotNull("The login should be successfully.", manager.login(USER_NAME, password));
        }
        long end = System.currentTimeMillis();

        System.out.println("call login for 100 times cost:" + (end - start) / 1000.0 + " seconds.");

    }

    /**
     * Test method for 'createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType)'.
     *
     * @throws Exception
     *             to junit
     */
    public void testCreatePageStringStringStringConfluenceAssetTypeConfluenceCatalogComponentType()
            throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            ConfluencePageCreationResult result = manager.createPage(token, "createPage", "1",
                    ConfluenceAssetType.COMPONENT_DEVELOPMENT, ConfluenceCatalog.JAVA, ComponentType.GENERIC);

            assertEquals("Equal to 1.", "1", result.getPage().getVersion());
        }
        long end = System.currentTimeMillis();

        System.out.println("calling createPage_1 cost " + (end - start) / 1000.0 + " seconds");
    }

    /**
     * Test method for 'com.topcoder.confluence.impl.DefaultConfluenceManager.createPage(String, String,
     * String, ConfluenceAssetType, ConfluenceCatalog, String)'.
     *
     * @throws Exception
     *             to junit
     */
    public void testCreatePageStringStringStringConfluenceAssetTypeConfluenceCatalogString() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            ConfluencePageCreationResult result = manager.createPage(token, "createPage_2", "2",
                    ConfluenceAssetType.COMPONENT_DEVELOPMENT, ConfluenceCatalog.JAVA, "applicationCode");

            assertEquals("Equal to 2.", "2", result.getPage().getVersion());
        }
        long end = System.currentTimeMillis();

        System.out.println("calling createPage_2 cost " + (end - start) / 1000.0 + " seconds");
    }

    /**
     * Test method for 'com.topcoder.confluence.impl.DefaultConfluenceManager.createPage(String, Page)'.
     *
     * @throws Exception
     *             to junit
     */
    public void testCreatePageStringPage() throws Exception {
        long start = System.currentTimeMillis();

        Page page = new Page();
        page.setApplicationCode("code");
        page.setAssetName("name");
        page.setBasePageUrl("http://www.topcoder.com");
        page.setVersion("1");
        page.setAssetType(ConfluenceAssetType.COMPONENT_DEVELOPMENT);
        page.setCatalog(ConfluenceCatalog.JAVA);
        page.setComponentType(ComponentType.GENERIC);

        for (int i = 0; i < 100; i++) {
            ConfluencePageCreationResult result = manager.createPage(token, page);

            assertEquals("Equal to 1.", "1", result.getPage().getVersion());

        }

        long end = System.currentTimeMillis();
        System.out.println("calling createPage_3 cost " + (end - start) / 1000.0 + " seconds");
    }

    /**
     * Test method for 'com.topcoder.confluence.impl.DefaultConfluenceManager.retrievePage(String, String,
     * String, ConfluenceAssetType, ConfluenceCatalog, ComponentType)'.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrievePageStringStringStringConfluenceAssetTypeConfluenceCatalogComponentType()
            throws Exception {

        ConfluencePageCreationResult result = manager.createPage(token, "createPage", "1",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT, ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            Page page = manager.retrievePage(token, "createPage", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                    ConfluenceCatalog.JAVA, ComponentType.GENERIC);

            assertEquals("Equal to '1'", "1", page.getVersion());
        }
        long end = System.currentTimeMillis();
        System.out.println("calling retrievePage 1 cost " + (end - start) / 1000.0 + " seconds");
    }

    /**
     * Test method for 'com.topcoder.confluence.impl.DefaultConfluenceManager.retrievePage(String, String,
     * String, ConfluenceAssetType, ConfluenceCatalog, String)'.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrievePageStringStringStringConfluenceAssetTypeConfluenceCatalogString() throws Exception {

        manager.createPage(token, "createPage", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, "applicationCode");

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            Page page = manager.retrievePage(token, "createPage", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                    ConfluenceCatalog.JAVA, "applicationCode");

            assertEquals("Equal to '1'", "1", page.getVersion());
        }
        long end = System.currentTimeMillis();
        System.out.println("calling retrievePage 1 cost " + (end - start) / 1000.0 + " seconds");
    }

}
