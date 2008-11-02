/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>Page</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PageTests extends TestCase {

    /**
     * <p>
     * Represents the <code>XXX</code> instance used for test.
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
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        page = null;
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#Page()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_Ctor1_Accuracy() throws Exception {
        assertNotNull("unable to instantiate the instance.", page);
    }

    /**
     * <p>
     * Accuracy test case for constructor with arguments.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_Ctor2_Accuracy() throws Exception {
        Page page1 =
            new Page("basePageUrl", "versionUrl", "assetName", "version",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT, ConfluenceCatalog.JAVA, "applicationCode",
                ComponentType.GENERIC, "content");
        assertNotNull("unable to instantiate the instance.", page1);
        assertEquals("Should be basePageUrl", "basePageUrl", page1.getBasePageUrl());
        assertEquals("Should be versionUrl", "versionUrl", page1.getVersionUrl());
        assertEquals("Should be assetName", "assetName", page1.getAssetName());
        assertEquals("Should be version", "version", page1.getVersion());
        assertEquals("Should be ConfluenceAssetType.COMPONENT_DEVELOPMENT",
            ConfluenceAssetType.COMPONENT_DEVELOPMENT, page1.getAssetType());
        assertEquals("Should be ConfluenceCatalog.JAVA", ConfluenceCatalog.JAVA, page1.getCatalog());
        assertEquals("Should be applicationCode", "applicationCode", page1.getApplicationCode());
        assertEquals("Should be ComponentType.GENERIC", ComponentType.GENERIC, page1.getComponentType());
        assertEquals("Should be content", "content", page1.getContent());
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#getApplicationCode()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getApplicationCode_Accuracy() throws Exception {
        page.setApplicationCode("applicationCode");
        assertEquals("Should be applicationCode", "applicationCode", page.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#getAssetName()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getAssetName_Accuracy() throws Exception {
        page.setAssetName("assetName");
        assertEquals("Should be assetName", "assetName", page.getAssetName());
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#getAssetType()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getAssetType_Accuracy() throws Exception {
        page.setAssetType(ConfluenceAssetType.COMPONENT_DEVELOPMENT);
        assertEquals("Should be ConfluenceAssetType.COMPONENT_DEVELOPMENT",
            ConfluenceAssetType.COMPONENT_DEVELOPMENT, page.getAssetType());
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#getBasePageUrl()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getBasePageUrl_Accuracy() throws Exception {
        page.setBasePageUrl("basePageUrl");
        assertEquals("Should be basePageUrl", "basePageUrl", page.getBasePageUrl());
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#getCatalog()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getCatalog_Accuracy() throws Exception {
        page.setCatalog(ConfluenceCatalog.JAVA);
        assertEquals("Should be ConfluenceCatalog.JAVA", ConfluenceCatalog.JAVA, page.getCatalog());
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#getComponentType()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getComponentType_Accuracy() throws Exception {
        page.setComponentType(ComponentType.GENERIC);
        assertEquals("Should be ComponentType.GENERIC", ComponentType.GENERIC, page.getComponentType());
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#getContent()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getContent_Accuracy() throws Exception {
        page.setContent("content");
        assertEquals("Should be content", "content", page.getContent());
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#getVersion()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getVersion_Accuracy() throws Exception {
        page.setVersion("version");
        assertEquals("Should be version", "version", page.getVersion());
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#getVersionUrl()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getVersionUrl_Accuracy() throws Exception {
        page.setVersionUrl("versionUrl");
        assertEquals("Should be versionUrl", "versionUrl", page.getVersionUrl());
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#setApplicationCode(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setApplicationCode_Accuracy() throws Exception {
        page.setApplicationCode("applicationCode");
        assertEquals("Should be applicationCode", "applicationCode", page.getApplicationCode());
    }

    /**
     * <p>
     * Failure test case for {@link Page#setApplicationCode(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setApplicationCode_Null_Failure() throws Exception {
        try {
            page.setApplicationCode(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setApplicationCode(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setApplicationCode_Empty_Failure() throws Exception {
        try {
            page.setApplicationCode("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#setAssetName(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setAssetName_Accuracy() throws Exception {
        page.setAssetName("assetName");
        assertEquals("Should be assetName", "assetName", page.getAssetName());
    }

    /**
     * <p>
     * Failure test case for {@link Page#setAssetName(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setAssetName_Null_Failure() throws Exception {
        try {
            page.setAssetName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setAssetName(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setAssetName_Empty_Failure() throws Exception {
        try {
            page.setAssetName("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#setAssetType(ConfluenceAssetType)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setAssetType_Accuracy() throws Exception {
        page.setAssetType(ConfluenceAssetType.COMPONENT_DEVELOPMENT);
        assertEquals("Should be ConfluenceAssetType.COMPONENT_DEVELOPMENT",
            ConfluenceAssetType.COMPONENT_DEVELOPMENT, page.getAssetType());
    }

    /**
     * <p>
     * Failure test case for {@link Page#setAssetType(ConfluenceAssetType)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setAssetType_Null_Failure() throws Exception {
        try {
            page.setAssetType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#setBasePageUrl(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setBasePageUrl_Accuracy() throws Exception {

        page.setBasePageUrl("basePageUrl");
        assertEquals("Should be basePageUrl", "basePageUrl", page.getBasePageUrl());
    }

    /**
     * <p>
     * Failure test case for {@link Page#setBasePageUrl(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setBasePageUrl_Null_Failure() throws Exception {
        try {
            page.setBasePageUrl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setBasePageUrl(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setBasePageUrl_Empty_Failure() throws Exception {
        try {
            page.setBasePageUrl("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#setCatalog(ConfluenceCatalog)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setCatalog_Accuracy() throws Exception {
        page.setCatalog(ConfluenceCatalog.JAVA);
        assertEquals("Should be ConfluenceCatalog.JAVA", ConfluenceCatalog.JAVA, page.getCatalog());
    }

    /**
     * <p>
     * Failure test case for {@link Page#setCatalog(ConfluenceCatalog)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setCatalog_Null_Failure() throws Exception {
        try {
            page.setCatalog(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#setComponentType(ComponentType)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setComponentType_Accuracy() throws Exception {
        page.setComponentType(ComponentType.GENERIC);
        assertEquals("Should be ComponentType.GENERIC", ComponentType.GENERIC, page.getComponentType());
    }

    /**
     * <p>
     * Failure test case for {@link Page#setComponentType(ComponentType)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setComponentType_Null_Failure() throws Exception {
        try {
            page.setComponentType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#setContent(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setContent_Accuracy() throws Exception {
        page.setContent("content");
        assertEquals("Should be content", "content", page.getContent());
    }

    /**
     * <p>
     * Failure test case for {@link Page#setContent(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setContent_Null_Failure() throws Exception {
        try {
            page.setContent(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#setVersion(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setVersion_Accuracy() throws Exception {
        page.setVersion("version");
        assertEquals("Should be version", "version", page.getVersion());
    }

    /**
     * <p>
     * Failure test case for {@link Page#setVersion(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setVersion_Null_Failure() throws Exception {
        try {
            page.setVersion(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setVersion(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setVersion_Empty_Failure() throws Exception {
        try {
            page.setVersion("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link Page#setVersionUrl(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setVersionUrl_Accuracy() throws Exception {
        page.setVersionUrl("versionUrl");
        assertEquals("Should be versionUrl", "versionUrl", page.getVersionUrl());
    }

    /**
     * <p>
     * Failure test case for {@link Page#setVersionUrl(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setVersionUrl_Null_Failure() throws Exception {
        try {
            page.setVersionUrl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setVersionUrl(String)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_setVersionUrl_Empty_Failure() throws Exception {
        try {
            page.setVersionUrl("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
