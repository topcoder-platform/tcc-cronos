/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.direct.services.view.action.contest.launch.failuretests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.topcoder.direct.services.view.action.contest.launch.ContestLinksRetrievalActionFailureTests;
import com.topcoder.direct.services.view.action.contest.launch.ContestRepostingActionFailureTests;
import com.topcoder.direct.services.view.action.contest.launch.ContestsLinkingActionFailureTests;
import com.topcoder.direct.services.view.action.contest.launch.NewComponentVersionCreationActionFailureTests;
import com.topcoder.direct.services.view.action.contest.launch.ProjectBudgetModifyingActionFailureTests;
import com.topcoder.direct.services.view.action.contest.launch.SpecificationReviewResultRetrievalActionFailureTests;
import com.topcoder.direct.services.view.action.contest.launch.SpecificationReviewStartingActionFailureTests;

import junit.framework.TestCase;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {ContestLinksRetrievalActionFailureTests.class, ContestRepostingActionFailureTests.class,
    ContestsLinkingActionFailureTests.class, ContestLinksRetrievalActionFailureTests.class,
    NewComponentVersionCreationActionFailureTests.class, ProjectBudgetModifyingActionFailureTests.class,
    SpecificationReviewResultRetrievalActionFailureTests.class, SpecificationReviewStartingActionFailureTests.class})
public class FailureTests extends TestCase {
}
