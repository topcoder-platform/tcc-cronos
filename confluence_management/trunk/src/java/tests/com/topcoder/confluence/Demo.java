/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpace;
import com.atlassian.www.software.confluence.$Proxy77.ConfluenceServiceLocator;
import com.atlassian.www.software.confluence.$Proxy77.ConfluenceSoapService;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.ConfluencePageType;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.impl.DefaultConfluenceManager;

/**
 * <p>
 * The demo usage of this component.
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

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
    private static final String DEFAULL_COMPONENT_DEVELOPMENT_VALUE = "demoCcomponentDevSpace";

    /**
     * <p>
     * Represents the default value of 'componentDesign'.
     * </p>
     */
    private static final String DEFAULL_COMPONENT_DESIGN_VALUE = "demoComponentDesignSpace";

    /**
     * <p>
     * Represents the application code for test.
     * </p>
     */
    private static final String APPLICATION_CODE_SPACE = "demoApplicationSpace";

    /**
     * <p>
     * Represents the default value of 'applicationSpecification'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_SPECIFICATION_VALUE = "$CODENAME$";

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
     * The token used in test.
     * </p>
     */
    private String token1;

    /**
     * <p>
     * Represents the confluence service used to set up some test environments.
     * </p>
     */
    private ConfluenceSoapService confluenceService;

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

        templates = new HashMap<ConfluencePageType, String>();
        templates.put(ConfluencePageType.COMPONENT_BASE_PAGE, COMPONENT_BASE_PAGE);
        templates.put(ConfluencePageType.COMPONENT_VERSION_PAGE, COMPONENT_VERSION_PAGE);
        templates.put(ConfluencePageType.APPLICATION_BASE_PAGE, APPLICATION_BASE_PAGE);
        templates.put(ConfluencePageType.APPLICATION_VERSION_PAGE, APPLICATION_VERSION_PAGE);

        token1 = confluenceService.login(USER_NAME, PASSWORD);

        RemoteSpace space1 = new RemoteSpace();
        space1.setUrl(spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        space1.setName(spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        space1.setKey(spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        confluenceService.addSpace(token1, space1);

        RemoteSpace space2 = new RemoteSpace();
        space2.setUrl(APPLICATION_CODE_SPACE);
        space2.setName(APPLICATION_CODE_SPACE);
        space2.setKey(APPLICATION_CODE_SPACE);
        confluenceService.addSpace(token1, space2);

        RemoteSpace space3 = new RemoteSpace();
        space3.setUrl(spaceLocations.get(ConfluenceAssetType.COMPONENT_DESIGN));
        space3.setName(spaceLocations.get(ConfluenceAssetType.COMPONENT_DESIGN));
        space3.setKey(spaceLocations.get(ConfluenceAssetType.COMPONENT_DESIGN));
        confluenceService.addSpace(token1, space3);
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
        confluenceService.removeSpace(token1, spaceLocations.get(ConfluenceAssetType.COMPONENT_DEVELOPMENT));
        confluenceService.removeSpace(token1, APPLICATION_CODE_SPACE);
        confluenceService.removeSpace(token1, spaceLocations.get(ConfluenceAssetType.COMPONENT_DESIGN));
        confluenceService.logout(token1);
        spaceLocations = null;
        templates = null;

        confluenceService = null;
    }

    /**
     * <p>
     * The demo usage of the <code>DefaultConfluenceManager</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unused")
    public void test_demo_usage() throws Exception {
        // note you need add the corresponding space into confluence first like the
        // ConfluenceAssetType.COMPONENT_DESIGN etc.

        // ConfluenceAssetType.COMPONENT_DESIGN
        // 1) DefaultConfluenceManager instance can be created
        ConfluenceManager manager = new DefaultConfluenceManager(CONFLUENCE_URL, spaceLocations, templates);

        // 2) Log in and save authorization token
        String token = manager.login(USER_NAME, PASSWORD);

        // 3) Assume user wants to create such page: component design page for the new component 'My New
        // Component', and the component is Java Custom.
        // should pass token
        ConfluencePageCreationResult page1result =
            manager.createPage(token, "My New Component", "1", ConfluenceAssetType.COMPONENT_DESIGN,
                ConfluenceCatalog.JAVA, ComponentType.CUSTOM);

        // if the component is new, the call page1result.getActionTaken() will return
        // //BASE_PAGE_AND_VERSION_CREATED.

        // 4) Different properties of the created page can be get
        Page page1 = page1result.getPage();
        String page1base = page1.getBasePageUrl();
        String page1version = page1.getVersionUrl();
        String content = page1.getContent();

        // 5) Assume user wants to wants add new version of such page: application specification page for the
        // application 'My Favorite Application' from the .net catalog.
        // should pass token
        // should pass application code
        ConfluencePageCreationResult page2result =
            manager.createPage(token, "My Favourite Application", "2",
                ConfluenceAssetType.APPLICATION_SPECIFICATION, ConfluenceCatalog.DOT_NET, APPLICATION_CODE_SPACE);

        // page2result.getActionTaken() should return BASE_PAGE_EXISTED_VERSION_CREATED.

        // 6) Assume user wants create the component development doc page with name 'My Component X' which will be
        // .NET generic.
        Page page3 = new Page();
        page3.setAssetName("My Component X");
        page3.setAssetType(ConfluenceAssetType.COMPONENT_DEVELOPMENT);
        page3.setCatalog(ConfluenceCatalog.DOT_NET);
        page3.setVersion("1");
        page3.setComponentType(ComponentType.GENERIC);

        // save page
        ConfluencePageCreationResult page3result = manager.createPage(token, page3);

        // 7) Now user wants to retrieve created page that he's just created
        Page page3copy =
            manager.retrievePage(token, "My Component X", "1", ConfluenceAssetType.COMPONENT_DEVELOPMENT,
                ConfluenceCatalog.DOT_NET, ComponentType.GENERIC);

        // 8) Now user wants to retrieve the application specification for the 'My Favorite Application'
        Page page2copy =
            manager.retrievePage(token, "My Favourite Application", "2",
                ConfluenceAssetType.APPLICATION_SPECIFICATION, ConfluenceCatalog.DOT_NET, APPLICATION_CODE_SPACE);

        // 9) Log out from Confluence
        manager.logout(token);
    }
}
