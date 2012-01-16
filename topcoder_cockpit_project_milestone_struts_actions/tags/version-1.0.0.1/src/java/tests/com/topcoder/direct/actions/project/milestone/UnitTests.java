/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ActionHelperTest.class, UpdateMilestonesActionTest.class, CreateMilestonesActionTest.class,
        DeleteMilestonesActionTest.class, GetAllMilestonesInMonthActionTest.class, GetAllMilestonesActionTest.class,
        LoadMilestonesActionTest.class })
public class UnitTests {
}
