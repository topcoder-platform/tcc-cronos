/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.impl;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace;
import com.atlassian.www.software.confluence.$Proxy77.ConfluenceServiceLocator;
import com.atlassian.www.software.confluence.$Proxy77.ConfluenceSoapService;
import com.topcoder.confluence.ConfluenceAuthenticationFailedException;
import com.topcoder.confluence.ConfluenceManagerException;
import com.topcoder.confluence.ConfluenceNotAuthorizedException;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageType;
import com.topcoder.confluence.entities.Page;

/**
 * <p>
 * The other part of UnitTest cases of the <code>DefaultConfluenceManager</code> class.all the test cases depends
 * on the real confluence web service.So you need start up the confluence first,then you need correct the
 * CONFLUENCE_URL, USER_NAME,PASSWORD.please see my instruction to set up the confluence for tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultConfluenceManagerTest2 extends TestCase {

    /**
     * <p>
     * NOTE:you may change this url for yours when you start up the confluence.This is my confluence url when I
     * start up the confluence in the local,please don't use the localhost.
     * </p>
     */
    private static final String CONFLUENCE_URL = "http://localhost:8080/rpc/soap/confluenceservice-v1";

    /**
     * <p>
     * Represents the user name of your confluence.
     * </p>
     */
    private static final String USER_NAME = "tcsdeveloper";

    /**
     * <p>
     * Represents the password of user name for your confluence.
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
    private static final String DEFAULL_COMPONENT_DESIGN_VALUE =
        "http://www.topcoder.com/wiki/display/docs/Design";

    /**
     * <p>
     * Represents the default value of 'applicationArchitecture'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_ARCHITECTURE_VALUE =
        "http://www.topcoder.com/wiki/display/docs/Architecture";

    /**
     * <p>
     * Represents the default value of 'applicationAssembly'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_ASSEMBLY_VALUE =
        "http://www.topcoder.com/wiki/display/docs/Assembly";

    /**
     * <p>
     * Represents the default value of 'applicationTesting'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_TESTING_VALUE =
        "http://www.topcoder.com/wiki/display/docs/Testing";

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
     * Accuracy test case for {@link DefaultConfluenceManager#login(String, String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_Accuracy() throws Exception {
        assertNotNull("Should not be null token returned.", manager.login(USER_NAME, PASSWORD));
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#login(String, String)}.
     * </p>
     * <p>
     * Incorrect password or user name,so throw ConfluenceAuthenticationFailedException
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_ConfluenceAuthenticationFailedException_Failure() throws Exception {
        try {
            manager.login("invalidUserName", "invalidPassword");
            fail("ConfluenceAuthenticationFailedException should be thrown.");
        } catch (ConfluenceAuthenticationFailedException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#login(String, String)}.
     * </p>
     * <p>
     * Invalid url,so throw ConfluenceManagerException means 404 not found.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_login_ConfluenceManagerException_Failure() throws Exception {

        try {
            DefaultConfluenceManager confluenceManager =
                new DefaultConfluenceManager("http://invalid", spaceLocations, templates);
            confluenceManager.login(USER_NAME, PASSWORD);
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link DefaultConfluenceManager#logout(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_Accuracy() throws Exception {
        DefaultConfluenceManager confluenceManager =
            new DefaultConfluenceManager(CONFLUENCE_URL, spaceLocations, templates);

        // pass
        confluenceManager.logout(confluenceManager.login(USER_NAME, PASSWORD));
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#logout(String)}.
     * </p>
     * <p>
     * Invalid token, so throw ConfluenceNotAuthorizedException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_NotAuthorized_Failure() throws Exception {
        try {
            manager.logout("invalidToken");
        } catch (ConfluenceNotAuthorizedException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link DefaultConfluenceManager#logout(String)}.
     * </p>
     * <p>
     * Invalid url,so throw ConfluenceManagerException means 404 not found.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logout_ConfluenceManagerException_Failure() throws Exception {

        try {
            DefaultConfluenceManager confluenceManager =
                new DefaultConfluenceManager("http://invalid", spaceLocations, templates);
            confluenceManager.logout("token");
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).
     * </p>
     * <p>
     * Verify that the page is created successfully.The base page and version page does not exist.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Accuracy1() throws Exception {

        manager.createPage(token, "createPageOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
            ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        Page page =
            manager.retrievePage(token, "createPageOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);
        assertNotNull("The version page should exist.", page);
    }

    /**
     * <p>
     * Accuracy test case for createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).
     * </p>
     * <p>
     * Verify that the page is created successfully.The base page exist and version page does not exist.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Accuracy2() throws Exception {

        manager.createPage(token, "createPageOneOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
            ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        manager.createPage(token, "createPageOneOne", "2", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
            ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        Page page1 =
            manager.retrievePage(token, "createPageOneOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);
        assertNotNull("The version page should exist.", page1);

        Page page2 =
            manager.retrievePage(token, "createPageOneOne", "2", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);
        assertNotNull("The version page should exist.", page2);
    }

    /**
     * <p>
     * Accuracy test case for createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).
     * </p>
     * <p>
     * Verify that the page is created successfully.Both the base page and version page exist.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_Accuracy3() throws Exception {

        manager.createPage(token, "createPageOneOneOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
            ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        manager.createPage(token, "createPageOneOneOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
            ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        Page page =
            manager.retrievePage(token, "createPageOneOneOne", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);
        assertNotNull("The version page should exist.", page);
    }

    /**
     * <p>
     * Failure test case for createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).
     * </p>
     * <p>
     * Invalid token, so throw ConfluenceNotAuthorizedException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_NotAuthorized_Failure() throws Exception {
        try {
            manager.createPage("invalidToken", "createPageOneOneOne", "1",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT, ConfluenceCatalog.JAVA, ComponentType.GENERIC);
            fail("ConfluenceNotAuthorizedException should be thrown.");
        } catch (ConfluenceNotAuthorizedException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType).
     * </p>
     * <p>
     * Invalid url,so throw ConfluenceManagerException means 404 not found.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage1_ConfluenceManagerException_Failure() throws Exception {
        DefaultConfluenceManager confluenceManager =
            new DefaultConfluenceManager("http://invalid", spaceLocations, templates);
        try {
            confluenceManager.createPage("invalidToken", "createPageOneOneOne", "1",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT, ConfluenceCatalog.JAVA, ComponentType.GENERIC);
            fail("ConfluenceManagerException should be thrown.");
        } catch (ConfluenceManagerException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for DefaultConfluenceManager#createPage(String, String, String, ConfluenceAssetType,
     * ConfluenceCatalog, String).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage2_Accuracy1() throws Exception {

        manager.createPage(token, "createPageTwo", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
            ConfluenceCatalog.JAVA, "testapplicationcode");

        Page page =
            manager.retrievePage(token, "createPageTwo", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, "testapplicationcode");
        assertNotNull("The version page should exist.", page);
        assertEquals("Version should be 1.", "1", page.getVersion());
    }

    /**
     * <p>
     * Accuracy test case for {@link DefaultConfluenceManager#createPage(String, Page)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage3_Accuracy1() throws Exception {

        Page page = new Page();
        page.setComponentType(ComponentType.CUSTOM);
        page.setAssetType(ConfluenceAssetType.COMPONENT_DEVELOPMENT);
        page.setCatalog(ConfluenceCatalog.DOT_NET);
        page.setAssetName("assetname");
        page.setVersion("1");
        manager.createPage(token, page);

        Page page1 =
            manager.retrievePage(token, "assetname", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
        assertNotNull("The version page should exist.", page1);
        assertEquals("Version should be 1.", "1", page.getVersion());
    }

    /**
     * <p>
     * Accuracy test case for {@link DefaultConfluenceManager#createPage(String, Page)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_createPage3_Accuracy2() throws Exception {

        Page page = new Page();
        page.setApplicationCode(APPLICATION_CODE_SPACE);
        page.setAssetType(ConfluenceAssetType.APPLICATION_SPECIFICATION);
        page.setCatalog(ConfluenceCatalog.DOT_NET);
        page.setAssetName("assetname");
        page.setVersion("1");
        manager.createPage(token, page);

        Page page1 =
            manager.retrievePage(token, "assetname", "1", ConfluenceAssetType.APPLICATION_SPECIFICATION,
                ConfluenceCatalog.DOT_NET, APPLICATION_CODE_SPACE);
        assertNotNull("The version page should exist.", page1);
        assertEquals("Version should be 1.", "1", page.getVersion());
    }

    /**
     * <p>
     * Accuracy test case for DefaultConfluenceManager#retrievePage(String, String, String, ConfluenceAssetType,
     * ConfluenceCatalog, ComponentType).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage1_Accuracy() throws Exception {
        manager.createPage(token, "createPageThree", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
            ConfluenceCatalog.JAVA, ComponentType.GENERIC);

        Page page =
            manager.retrievePage(token, "createPageThree", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.JAVA, ComponentType.GENERIC);
        assertNotNull("The version page should exist.", page);
        assertEquals("Version should be 1.", "1", page.getVersion());
        assertEquals("ConfluenceAssetType should be COMPONENT_DEVELOPMENT.",
            ConfluenceAssetType.COMPONENT_DEVELOPMENT, page.getAssetType());
        assertEquals("ComponentType should be GENERIC.", ComponentType.GENERIC, page.getComponentType());
    }

    /**
     * <p>
     * Accuracy test case for DefaultConfluenceManager#retrievePage(String, String, String, ConfluenceAssetType,
     * ConfluenceCatalog, String).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_retrievePage2_Accuracy() throws Exception {
        Page page = new Page();
        page.setApplicationCode(APPLICATION_CODE_SPACE);
        page.setAssetType(ConfluenceAssetType.APPLICATION_SPECIFICATION);
        page.setCatalog(ConfluenceCatalog.DOT_NET);
        page.setAssetName("testname");
        page.setVersion("3");
        manager.createPage(token, page);

        Page page1 =
            manager.retrievePage(token, "testname", "3", ConfluenceAssetType.APPLICATION_SPECIFICATION,
                ConfluenceCatalog.DOT_NET, APPLICATION_CODE_SPACE);
        assertNotNull("The version page should exist.", page1);
        assertEquals("Version should be 3.", "3", page.getVersion());
        assertEquals("ConfluenceAssetType should be APPLICATION_SPECIFICATION.",
            ConfluenceAssetType.APPLICATION_SPECIFICATION, page.getAssetType());
        assertEquals("applicationCode should be APPLICATION_CODE.", APPLICATION_CODE_SPACE, page
            .getApplicationCode());
        assertEquals("ConfluenceCatalog should be DOT_NET.", ConfluenceCatalog.DOT_NET, page.getCatalog());
    }
}