/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.TestCase;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author zju_jay
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {ContestDeletingActionTest.class, ContestLinksRetrievalActionTest.class,
    ContestRepostingActionTest.class, ContestsLinkingActionTest.class, ProjectBudgetModifyingActionTest.class,
    SpecificationReviewResultRetrievalActionTest.class, SpecificationReviewStartingActionTest.class,
    NewComponentVersionCreationActionTest.class})
public class UnitTests extends TestCase {
}
