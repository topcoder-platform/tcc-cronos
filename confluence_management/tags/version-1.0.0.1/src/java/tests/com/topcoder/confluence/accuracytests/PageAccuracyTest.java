/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.accuracytests;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.Page;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for the <code>Page</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PageAccuracyTest extends TestCase {

    /**
     * <p>
     * Represents the <code>Page</code> instance used for test.
     * </p>
     */
    private Page page;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        page = new Page();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>Page()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", new Page());
    }

    /**
     * <p>
     * Accuracy test for the constructor Page(String basePageUrl, String versionUrl, String assetName, String version,
     * ConfluenceAssetType assetType, ConfluenceCatalog catalog, String applicationCode, ComponentType componentType,
     * String content).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        page = new Page("basePageUrl", "versionUrl", "assetName", "version",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT, ConfluenceCatalog.JAVA, "applicationCode",
                ComponentType.GENERIC, "content");
        assertNotNull("unable to instantiate the instance.", page);
        assertEquals("Should be basePageUrl", "basePageUrl", page.getBasePageUrl());
        assertEquals("Should be versionUrl", "versionUrl", page.getVersionUrl());
        assertEquals("Should be assetName", "assetName", page.getAssetName());
        assertEquals("Should be version", "version", page.getVersion());
        assertEquals("Should be ConfluenceAssetType.COMPONENT_DEVELOPMENT",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT, page.getAssetType());
        assertEquals("Should be ConfluenceCatalog.JAVA", ConfluenceCatalog.JAVA, page.getCatalog());
        assertEquals("Should be applicationCode", "applicationCode", page.getApplicationCode());
        assertEquals("Should be ComponentType.GENERIC", ComponentType.GENERIC, page.getComponentType());
        assertEquals("Should be content", "content", page.getContent());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getApplicationCode()</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetApplicationCodeAccuracy() throws Exception {
        page.setApplicationCode("applicationCode");
        assertEquals("Should be applicationCode", "applicationCode", page.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssetName()</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAssetNameAccuracy() throws Exception {
        page.setAssetName("assetName");
        assertEquals("Should be assetName", "assetName", page.getAssetName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssetType()</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAssetTypeAccuracy() throws Exception {
        page.setAssetType(ConfluenceAssetType.COMPONENT_DEVELOPMENT);
        assertEquals("Should be ConfluenceAssetType.COMPONENT_DEVELOPMENT",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT, page.getAssetType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBasePageUrl()</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetBasePageUrlAccuracy() throws Exception {
        page.setBasePageUrl("basePageUrl");
        assertEquals("Should be basePageUrl", "basePageUrl", page.getBasePageUrl());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCatalog()</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetCatalogAccuracy() throws Exception {
        page.setCatalog(ConfluenceCatalog.JAVA);
        assertEquals("Should be ConfluenceCatalog.JAVA", ConfluenceCatalog.JAVA, page.getCatalog());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getComponentType()</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetComponentTypeAccuracy() throws Exception {
        page.setComponentType(ComponentType.GENERIC);
        assertEquals("Should be ComponentType.GENERIC", ComponentType.GENERIC, page.getComponentType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContent()</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContentAccuracy() throws Exception {
        page.setContent("content");
        assertEquals("Should be content", "content", page.getContent());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getVersion()</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetVersionAccuracy() throws Exception {
        page.setVersion("version");
        assertEquals("Should be version", "version", page.getVersion());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getVersionUrl()</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetVersionUrlAccuracy() throws Exception {
        page.setVersionUrl("versionUrl");
        assertEquals("Should be versionUrl", "versionUrl", page.getVersionUrl());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setApplicationCode(String)</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetApplicationCodeAccuracy() throws Exception {
        page.setApplicationCode("applicationCode");
        assertEquals("Should be applicationCode", "applicationCode", page.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssetName(String)</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetAssetNameAccuracy() throws Exception {
        page.setAssetName("assetName");
        assertEquals("Should be assetName", "assetName", page.getAssetName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssetType(ConfluenceAssetType)</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetAssetTypeAccuracy() throws Exception {
        page.setAssetType(ConfluenceAssetType.COMPONENT_DEVELOPMENT);
        assertEquals("Should be ConfluenceAssetType.COMPONENT_DEVELOPMENT",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT, page.getAssetType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setBasePageUrl(String)</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetBasePageUrlAccuracy() throws Exception {

        page.setBasePageUrl("basePageUrl");
        assertEquals("Should be basePageUrl", "basePageUrl", page.getBasePageUrl());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCatalog(ConfluenceCatalog)</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetCatalogAccuracy() throws Exception {
        page.setCatalog(ConfluenceCatalog.JAVA);
        assertEquals("Should be ConfluenceCatalog.JAVA", ConfluenceCatalog.JAVA, page.getCatalog());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setComponentType(ComponentType)</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetComponentTypeAccuracy() throws Exception {
        page.setComponentType(ComponentType.GENERIC);
        assertEquals("Should be ComponentType.GENERIC", ComponentType.GENERIC, page.getComponentType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContent(String)</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetContentAccuracy() throws Exception {
        page.setContent("content");
        assertEquals("Should be content", "content", page.getContent());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setVersion(String)</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetVersionAccuracy() throws Exception {
        page.setVersion("version");
        assertEquals("Should be version", "version", page.getVersion());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setVersionUrl(String)</code>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetVersionUrlAccuracy() throws Exception {
        page.setVersionUrl("versionUrl");
        assertEquals("Should be versionUrl", "versionUrl", page.getVersionUrl());
    }
}
