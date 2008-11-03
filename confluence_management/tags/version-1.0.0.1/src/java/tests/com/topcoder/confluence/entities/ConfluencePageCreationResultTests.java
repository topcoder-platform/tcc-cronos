/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>ConfluencePageCreationResult</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluencePageCreationResultTests extends TestCase {

    /**
     * <p>
     * Accuracy test case for
     * {@link ConfluencePageCreationResult#ConfluencePageCreationResult(Page, ConfluencePageCreatedAction)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_Ctor_Accuracy() throws Exception {
        Page page = new Page();
        ConfluencePageCreationResult result =
            new ConfluencePageCreationResult(page, ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        assertNotNull("unable to instantiate the instance.", result);
        assertEquals("Should be ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED",
            ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED, result.getActionTaken());
        assertNotNull("Should set page right.", result.getPage());
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluencePageCreationResult#ConfluencePageCreationResult(Page, ConfluencePageCreatedAction)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_Ctor_Page_Null_Failure() throws Exception {
        try {
            new ConfluencePageCreationResult(null, ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for
     * {@link ConfluencePageCreationResult#ConfluencePageCreationResult(Page, ConfluencePageCreatedAction)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_Ctor_Page_ConfluencePageCreatedAction_Failure() throws Exception {
        try {
            new ConfluencePageCreationResult(new Page(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluencePageCreationResult#getActionTaken()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getActionTaken_Accuracy() throws Exception {
        Page page = new Page();
        ConfluencePageCreationResult result =
            new ConfluencePageCreationResult(page, ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        assertEquals("Should be ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED",
            ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED, result.getActionTaken());
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluencePageCreationResult#getPage()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getActidonTaken_Accuracy() throws Exception {
        Page page = new Page();
        ConfluencePageCreationResult result =
            new ConfluencePageCreationResult(page, ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        assertNotNull("Should get page right.", result.getPage());
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluencePageCreationResult#getBasePageUrl()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getAdctidonTaken_Accuracy() throws Exception {
        Page page = new Page();
        page.setBasePageUrl("basePageUrl");
        ConfluencePageCreationResult result =
            new ConfluencePageCreationResult(page, ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        assertEquals("Should be basePageUrl", "basePageUrl", result.getBasePageUrl());
    }

    /**
     * <p>
     * Accuracy test case for {@link ConfluencePageCreationResult#getVersionUrl()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getAdctiddonTaken_Accuracy() throws Exception {
        Page page = new Page();
        page.setVersionUrl("versionUrl");
        ConfluencePageCreationResult result =
            new ConfluencePageCreationResult(page, ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        assertEquals("Should be versionUrl", "versionUrl", result.getVersionUrl());
    }
}
