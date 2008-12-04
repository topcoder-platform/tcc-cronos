/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.accuracytests;

import junit.framework.TestCase;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreatedAction;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceConfigurationException;
import com.topcoder.confluence.webservice.bean.ConfluenceManagementServiceBean;

/**
 * <p>
 * Accuracy test for the <code>ConfluenceManagementServiceBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceBeanAccuracyTest extends TestCase {

    /**
     * <p>
     * Represents the <code>ConfluenceManagementServiceBean</code> instance for test.
     * </p>
     */
    private MockConfluenceManagementServiceBean bean;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        bean = new MockConfluenceManagementServiceBean();
        bean.initialize();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ConfluenceManagementServiceBean()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("The instance should be created properly.", bean);
    }

    /**
     * <p>
     * Accuracy test for the method <code>initialize()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeAccuracy() throws Exception {
        new MockConfluenceManagementServiceBean().initialize();
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
        assertEquals("The value should be 'tokentoken'", "tokentoken", bean.login("userName", "password"));
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
        bean.logout("  ");
    }

    /**
     * <p>
     * Accuracy test for the method <code>createPage(String, Page)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage1Accuracy() throws Exception {
        Page page = new Page();
        ConfluencePageCreationResult result = bean.createPage("  ", page);
        assertEquals("actionToken should be BASE_PAGE_AND_VERSION_CREATED.",
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED, result.getActionTaken());
        assertEquals("Should be equale to page.", page, result.getPage());
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
    public void testCreatePage2Accuracy() throws Exception {
        ConfluencePageCreationResult result = bean.createPage(" ", "pageName", "1",
                ConfluenceAssetType.APPLICATION_ARCHITECTURE, ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);

        assertEquals("BasePageUrl should be pageNameUrl", "pageNameUrl", result.getBasePageUrl());
        assertEquals("actionToken should be BASE_PAGE_AND_VERSION_CREATED.",
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED, result.getActionTaken());
        assertEquals("Version should be 1.", "1", result.getPage().getVersion());
        assertEquals("AssetType should be ConfluenceAssetType.APPLICATION_ARCHITECTURE.",
                ConfluenceAssetType.APPLICATION_ARCHITECTURE, result.getPage().getAssetType());
        assertEquals("ComponentType should be CUSTOM.", ComponentType.CUSTOM, result.getPage().getComponentType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>createPage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreatePage3Accuracy() throws Exception {
        ConfluencePageCreationResult result = bean.createPage(" ", "pageName", "1",
                ConfluenceAssetType.APPLICATION_ARCHITECTURE, ConfluenceCatalog.DOT_NET, "applicationCode");

        assertEquals("BasePageUrl should be pageNameUrl", "pageNameUrl", result.getBasePageUrl());
        assertEquals("actionToken should be BASE_PAGE_AND_VERSION_CREATED.",
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED, result.getActionTaken());
        assertEquals("Version should be 1.", "1", result.getPage().getVersion());
        assertEquals("AssetType should be ConfluenceAssetType.APPLICATION_ARCHITECTURE.",
                ConfluenceAssetType.APPLICATION_ARCHITECTURE, result.getPage().getAssetType());
        assertEquals("applicationCode should be applicationCode", "applicationCode", result.getPage()
                .getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * ComponentType)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage1Accuracy() throws Exception {
        Page page = bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
        assertEquals("ConfluenceAssetType should be APPLICATION_ARCHITECTURE.",
                ConfluenceAssetType.APPLICATION_ARCHITECTURE, page.getAssetType());
        assertEquals("version should be 2.", "2", page.getVersion());
        assertEquals("basePageUrl should be 'page nameUrl'.", "page nameUrl", page.getBasePageUrl());
        assertEquals("ConfluenceCatalog should be DOT_NET.", page.getCatalog(), ConfluenceCatalog.DOT_NET);
        assertEquals("ComponentType should be CUSTOM.", page.getComponentType(), ComponentType.CUSTOM);
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrievePage(String, String, String, ConfluenceAssetType, ConfluenceCatalog,
     * String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2Accuracy() throws Exception {
        Page page = bean.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");
        assertEquals("ConfluenceAssetType should be APPLICATION_ARCHITECTURE.",
                ConfluenceAssetType.APPLICATION_ARCHITECTURE, page.getAssetType());
        assertEquals("version should be 2.", "2", page.getVersion());
        assertEquals("basePageUrl should be 'page nameUrl'.", "page nameUrl", page.getBasePageUrl());
        assertEquals("ConfluenceCatalog should be DOT_NET.", page.getCatalog(), ConfluenceCatalog.DOT_NET);
        assertEquals("applicationCode should be 'application code'.", "application code", page.getApplicationCode());
    }

    /**
     * <p>
     * Mock class extends <code>ConfluenceManagementServiceBean</code> for test.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class MockConfluenceManagementServiceBean extends ConfluenceManagementServiceBean {

        /**
         * <p>
         * Default constructor.
         * </p>
         */
        public MockConfluenceManagementServiceBean() {
            super();
        }

        /**
         * <p>
         * Initializes this bean for use.
         * </p>
         *
         * @throws ConfluenceManagementServiceConfigurationException
         *             if can't create the file manager or extract the configuration from it, or if the configuration
         *             does not have required properties, or if there is an error while accessing these properties
         */
        public void initialize() {
            super.initialize();
        }
    }
}