/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

package com.topcoder.service.actions.stresstests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.TestCase;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {ContestDeletingActionStressTests.class, ContestLinksRetrievalActionStressTests.class,
    ContestRepostingActionStressTests.class, ContestsLinkingActionStressTests.class,
    NewComponentVersionCreationActionStressTests.class, ProjectBudgetModifyingActionStressTests.class,
    SpecificationReviewResultRetrievalActionStressTests.class, SpecificationReviewStartingActionStressTests.class})
public class StressTests extends TestCase {
}
