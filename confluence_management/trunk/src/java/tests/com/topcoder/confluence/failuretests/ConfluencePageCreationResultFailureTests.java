/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.failuretests;

import com.topcoder.confluence.entities.ConfluencePageCreatedAction;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;

import junit.framework.TestCase;

/**
 * <p>
 * Failure Test cases of the <code>ConfluencePageCreationResult</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class ConfluencePageCreationResultFailureTests extends TestCase {
    /**
     * <p>
     * Failure test case for
     * {@link ConfluencePageCreationResult#ConfluencePageCreationResult(Page, ConfluencePageCreatedAction)}.
     * </p>
     * <p>When the first argument is null, IllegalArgumentException is expected.</p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorPageNullFailure() throws Exception {
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
     * <p>When the second argument is null, IllegalArgumentException is expected.</p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorPageConfluencePageCreatedActionFailure() throws Exception {
        try {
            new ConfluencePageCreationResult(new Page(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
