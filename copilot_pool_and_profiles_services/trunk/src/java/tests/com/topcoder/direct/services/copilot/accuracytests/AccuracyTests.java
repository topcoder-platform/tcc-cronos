/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import junit.framework.TestCase;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * <p>This test case aggregates all accuracy test cases.</p>
 *
 * @author onsky
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({CopilotProfileServiceImplTests.class,
    CopilotProjectPlanServiceImplTests.class,
    CopilotProjectServiceImplTests.class,
    LookupServiceImplTests.class
})
public class AccuracyTests extends TestCase {
}
