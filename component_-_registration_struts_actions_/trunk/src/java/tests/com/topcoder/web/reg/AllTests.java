/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.web.reg;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.topcoder.web.reg.accuracytests.AccuracyTests;
import com.topcoder.web.reg.failuretests.FailureTests;
import com.topcoder.web.reg.stresstests.StressTests;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({UnitTests.class, FailureTests.class, AccuracyTests.class, StressTests.class
})
public class AllTests {
}
