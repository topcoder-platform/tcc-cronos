/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.topcoder.direct.services.view.action.contest.launch.ContestDeletingActionAccTest;
import com.topcoder.direct.services.view.action.contest.launch.ContestLinksRetrievalActionAccTest;
import com.topcoder.direct.services.view.action.contest.launch.ContestRepostingActionAccTest;
import com.topcoder.direct.services.view.action.contest.launch.ContestsLinkingActionAccTest;
import com.topcoder.direct.services.view.action.contest.launch.NewComponentVersionCreationActionAccTest;
import com.topcoder.direct.services.view.action.contest.launch.ProjectBudgetModifyingActionAccTest;
import com.topcoder.direct.services.view.action.contest.launch.SpecificationReviewResultRetrievalActionAccTest;
import com.topcoder.direct.services.view.action.contest.launch.SpecificationReviewStartingActionAccTest;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {ContestDeletingActionAccTest.class,
    ContestLinksRetrievalActionAccTest.class,
    ContestRepostingActionAccTest.class,
    ContestsLinkingActionAccTest.class,
    NewComponentVersionCreationActionAccTest.class,
    ProjectBudgetModifyingActionAccTest.class,
    SpecificationReviewResultRetrievalActionAccTest.class,
    SpecificationReviewStartingActionAccTest.class
    })
public class AccuracyTests {
    // Empty
}
