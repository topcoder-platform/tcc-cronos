/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.accuracytests;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreatedAction;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceClientMock;
import com.topcoder.confluence.webservice.delegate.ConfluenceManagerWebServiceDelegate;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Accuracy test for the <code>ConfluenceManagerWebServiceDelegate</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagerWebServiceDelegateAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents the wsdl document location.
     * </p>
     */
    private final static String WSDL_DOCUMENT_LOCATION = "http://127.0.0.1:8080/confluence_services-confluence_services-ejb/ConfluenceManagementServiceBean?wsdl";

    /**
     * <p>
     * Represents the <code>ConfluenceManagerWebServiceDelegate</code> instance used for test.
     * </p>
     */
    private ConfluenceManagerWebServiceDelegate delegate;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        delegate = new ConfluenceManagerWebServiceDelegate();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ConfluenceManagerWebServiceDelegate()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", delegate);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ConfluenceManagerWebServiceDelegate(String, String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagerWebServiceDelegate(
                ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ConfluenceManagerWebServiceDelegate(ConfigurationObject)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor3Accuracy() throws Exception {
        ConfigurationFileManager manager = new ConfigurationFileManager(
                ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH);

        ConfigurationObject root = manager.getConfiguration(ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);

        ConfigurationObject delegateConfObj = root.getChild(ConfluenceManagerWebServiceDelegate.DEFAULT_NAMESPACE);
        delegateConfObj.setPropertyValue("log_name", " ");
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagerWebServiceDelegate(delegateConfObj));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ConfluenceManagerWebServiceDelegate(ConfluenceManagementServiceClient,
     * Log)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor4Accuracy1() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagerWebServiceDelegate(
                new ConfluenceManagementServiceClientMock(WSDL_DOCUMENT_LOCATION), null));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ConfluenceManagerWebServiceDelegate(ConfluenceManagementServiceClient,
     * Log)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor4Accuracy2() throws Exception {
        assertNotNull("unable to instantiate the instance.", new ConfluenceManagerWebServiceDelegate(
                new ConfluenceManagementServiceClientMock(WSDL_DOCUMENT_LOCATION), LogManager.getLog()));
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
        assertEquals("The value should be 'tokentoken'", "tokentoken", delegate.login("userName", "password"));
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
        delegate.logout("  ");
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
        ConfluencePageCreationResult result = delegate.createPage("  ", page);
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
        ConfluencePageCreationResult result = delegate.createPage(" ", "pageName", "1",
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
        ConfluencePageCreationResult result = delegate.createPage(" ", "pageName", "1",
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
     * Accuracy test for the method <code>retrievePage(String, String, String, ConfluenceAssetType,
     * ConfluenceCatalog, ComponentType)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage1Accuracy() throws Exception {
        Page page = delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
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
     * Accuracy test for the method <code>retrievePage(String, String, String, ConfluenceAssetType,
     * ConfluenceCatalog, String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrievePage2Accuracy() throws Exception {
        Page page = delegate.retrievePage(" ", "page name", "2", ConfluenceAssetType.APPLICATION_ARCHITECTURE,
                ConfluenceCatalog.DOT_NET, "application code");

        assertEquals("ConfluenceAssetType should be APPLICATION_ARCHITECTURE.",
                ConfluenceAssetType.APPLICATION_ARCHITECTURE, page.getAssetType());
        assertEquals("version should be 2.", "2", page.getVersion());
        assertEquals("basePageUrl should be 'page nameUrl'.", "page nameUrl", page.getBasePageUrl());
        assertEquals("ConfluenceCatalog should be DOT_NET.", page.getCatalog(), ConfluenceCatalog.DOT_NET);
        assertEquals("applicationCode should be 'application code'.", "application code", page.getApplicationCode());
    }
}