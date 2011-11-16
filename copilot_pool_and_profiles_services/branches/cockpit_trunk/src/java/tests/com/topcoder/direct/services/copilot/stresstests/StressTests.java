/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.direct.services.copilot.stresstests;

import junit.framework.TestCase;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
 @RunWith(Suite.class)
@SuiteClasses({LookupServiceImplStressTest.class,
    CopilotProfileServiceImplStressTest.class,
    CopilotProjectPlanServiceImplStressTest.class,
    CopilotProjectServiceImplStressTest.class
})
public class StressTests {
}