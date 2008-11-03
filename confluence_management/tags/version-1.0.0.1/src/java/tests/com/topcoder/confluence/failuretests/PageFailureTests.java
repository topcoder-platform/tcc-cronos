/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.failuretests;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.Page;

import junit.framework.TestCase;

/**
 * <p>
 * Failure Tests cases of the <code>Page</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class PageFailureTests extends TestCase {

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
     * Failure test case for {@link Page#setApplicationCode(String)}.
     * </p>
     * <p>When parameter is null, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetApplicationCodeNullFailure() throws Exception {
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
     * <p>When parameter is empty, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetApplicationCodeEmptyFailure() throws Exception {
        try {
            page.setApplicationCode("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setAssetName(String)}.
     * </p>
     * <p>When parameter is null, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testetAssetNameNullFailure() throws Exception {
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
     * <p>When parameter is empty, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetAssetNameEmptyFailure() throws Exception {
        try {
            page.setAssetName("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setAssetType(ConfluenceAssetType)}.
     * </p>
     * <p>When parameter is null, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetAssetTypeNullFailure() throws Exception {
        try {
            page.setAssetType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setBasePageUrl(String)}.
     * </p>
     * <p>When parameter is null, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetBasePageUrlNullFailure() throws Exception {
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
     * <p>When parameter is empty, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetBasePageUrlEmptyFailure() throws Exception {
        try {
            page.setBasePageUrl("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setCatalog(ConfluenceCatalog)}.
     * </p>
     * <p>When parameter is null, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetCatalogNullFailure() throws Exception {
        try {
            page.setCatalog(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setComponentType(ComponentType)}.
     * </p>
     * <p>When parameter is null, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetComponentTypeNullFailure() throws Exception {
        try {
            page.setComponentType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setContent(String)}.
     * </p>
     * <p>When parameter is null, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetContentNullFailure() throws Exception {
        try {
            page.setContent(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setVersion(String)}.
     * </p>
     * <p>When parameter is null, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetVersionNullFailure() throws Exception {
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
     * <p>When parameter is empty, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetVersionEmptyFailure() throws Exception {
        try {
            page.setVersion("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link Page#setVersionUrl(String)}.
     * </p>
     * <p>When parameter is null, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetVersionUrlNullFailure() throws Exception {
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
     * <p>When parameter is empty, IllegalArgumentException is expected.</p>
     * @throws Exception
     *             to JUnit
     */
    public void testsetVersionUrlEmptyFailure() throws Exception {
        try {
            page.setVersionUrl("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
