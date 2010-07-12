/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.direct.services.view.action.contest.launch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.topcoder.direct.services.view.action.contest.launch.accuracytests.AccuracyTests;
import com.topcoder.direct.services.view.action.contest.launch.failuretests.FailureTests;
import com.topcoder.service.actions.stresstests.StressTests;

import junit.framework.TestCase;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {UnitTests.class, AccuracyTests.class, FailureTests.class, StressTests.class})
public class AllTests extends TestCase {
}
