/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.topcoder.web.reg.actions.miscellaneous.accuracytests.AccuracyTests;
import com.topcoder.web.reg.actions.miscellaneous.failuretests.FailureTests;
import com.topcoder.web.reg.actions.miscellaneous.stresstests.StressTests;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author mumujava
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({UnitTests.class,
    AccuracyTests.class, StressTests.class, FailureTests.class
})
public class AllTests {
}
