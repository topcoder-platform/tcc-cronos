/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.failuretests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.topcoder.direct.services.copilot.impl.BaseCopilotServiceFailureTests;
import com.topcoder.direct.services.copilot.impl.CopilotProfileServiceImplFailureTests;
import com.topcoder.direct.services.copilot.impl.CopilotProjectPlanServiceImplFailureTests;
import com.topcoder.direct.services.copilot.impl.CopilotProjectServiceImplFailureTests;
import com.topcoder.direct.services.copilot.impl.GenericServiceImplFailureTests;
import com.topcoder.direct.services.copilot.impl.LoggingEnabledServiceFailureTests;
import com.topcoder.direct.services.copilot.impl.LookupServiceImplFailureTests;

/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 * 
 * @author morehappiness
 * @version 1.0
 */
@SuiteClasses({BaseCopilotServiceFailureTests.class, LookupServiceImplFailureTests.class,
    GenericServiceImplFailureTests.class, CopilotProjectServiceImplFailureTests.class,
    CopilotProfileServiceImplFailureTests.class, CopilotProjectPlanServiceImplFailureTests.class,
    LoggingEnabledServiceFailureTests.class})
@RunWith(Suite.class)
public class FailureTests {
    // empty
}
