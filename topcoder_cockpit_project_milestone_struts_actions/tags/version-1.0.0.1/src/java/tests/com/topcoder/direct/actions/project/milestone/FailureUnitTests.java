/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>
 * This test case aggregates all failure unit test cases.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CreateMilestonesActionFailureUnitTests.class, DeleteMilestonesActionFailureUnitTests.class,
    GetAllMilestonesActionFailureUnitTests.class, GetAllMilestonesInMonthActionFailureUnitTests.class,
    LoadMilestonesActionFailureUnitTests.class, UpdateMilestonesActionFailureUnitTests.class })
public class FailureUnitTests {
}
