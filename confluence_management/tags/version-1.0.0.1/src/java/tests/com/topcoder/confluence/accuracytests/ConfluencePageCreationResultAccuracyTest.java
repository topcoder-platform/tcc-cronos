/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.accuracytests;

import com.topcoder.confluence.entities.ConfluencePageCreatedAction;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for the <code>ConfluencePageCreationResult</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluencePageCreationResultAccuracyTest extends TestCase {

    /**
     * <p>
     * Accuracy test for the constructor <code>ConfluencePageCreationResult(Page, ConfluencePageCreatedAction)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_Ctor_Accuracy() throws Exception {
        Page page = new Page();
        ConfluencePageCreationResult result = new ConfluencePageCreationResult(page,
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        assertNotNull("unable to instantiate the instance.", result);
        assertEquals("Should be ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED",
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED, result.getActionTaken());
        assertNotNull("Should set page right.", result.getPage());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getActionTaken()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetActionTakenAccuracy() throws Exception {
        Page page = new Page();
        ConfluencePageCreationResult result = new ConfluencePageCreationResult(page,
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        assertEquals("Should be ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED",
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED, result.getActionTaken());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPage()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetActidonTakenAccuracy() throws Exception {
        Page page = new Page();
        ConfluencePageCreationResult result = new ConfluencePageCreationResult(page,
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        assertNotNull("Should get page right.", result.getPage());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBasePageUrl()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAdctidonTakenAccuracy() throws Exception {
        Page page = new Page();
        page.setBasePageUrl("basePageUrl");
        ConfluencePageCreationResult result = new ConfluencePageCreationResult(page,
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        assertEquals("Should be basePageUrl", "basePageUrl", result.getBasePageUrl());
    }

    /**
     * <p>
     * Accuracy test the method <code>getVersionUrl()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAdctiddonTakenAccuracy() throws Exception {
        Page page = new Page();
        page.setVersionUrl("versionUrl");
        ConfluencePageCreationResult result = new ConfluencePageCreationResult(page,
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        assertEquals("Should be versionUrl", "versionUrl", result.getVersionUrl());
    }
}
