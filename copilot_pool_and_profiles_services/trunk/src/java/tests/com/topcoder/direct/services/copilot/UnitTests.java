/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.topcoder.direct.services.copilot.dto.ContestTypeStatUnitTests;
import com.topcoder.direct.services.copilot.dto.CopilotPoolMemberUnitTests;
import com.topcoder.direct.services.copilot.dto.CopilotProfileDTOUnitTests;
import com.topcoder.direct.services.copilot.dto.CopilotProjectDTOUnitTests;
import com.topcoder.direct.services.copilot.impl.BaseCopilotServiceUnitTests;
import com.topcoder.direct.services.copilot.impl.CopilotProfileServiceImplUnitTests;
import com.topcoder.direct.services.copilot.impl.CopilotProjectPlanServiceImplUnitTests;
import com.topcoder.direct.services.copilot.impl.CopilotProjectServiceImplUnitTests;
import com.topcoder.direct.services.copilot.impl.GenericServiceImplUnitTests;
import com.topcoder.direct.services.copilot.impl.HelperUnitTests;
import com.topcoder.direct.services.copilot.impl.LoggingEnabledServiceUnitTests;
import com.topcoder.direct.services.copilot.impl.LookupServiceImplUnitTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuiteClasses( {ContestTypeStatUnitTests.class, CopilotPoolMemberUnitTests.class, CopilotProfileDTOUnitTests.class,
    CopilotProjectDTOUnitTests.class, BaseCopilotServiceUnitTests.class, ContestTypeStatUnitTests.class,
    CopilotProfileServiceImplUnitTests.class, CopilotProjectPlanServiceImplUnitTests.class,
    CopilotProjectServiceImplUnitTests.class, GenericServiceImplUnitTests.class, HelperUnitTests.class,
    LookupServiceImplUnitTests.class, CopilotServiceEntityNotFoundExceptionUnitTests.class,
    CopilotServiceExceptionUnitTests.class, CopilotServiceInitializationExceptionUnitTests.class,
    LoggingEnabledServiceUnitTests.class, Demo.class})
@RunWith(Suite.class)
public class UnitTests {
    // empty
}
