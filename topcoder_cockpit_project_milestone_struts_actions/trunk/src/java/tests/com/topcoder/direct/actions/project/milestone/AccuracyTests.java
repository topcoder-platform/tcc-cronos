/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>
 * This test case aggregates all unit test cases.
 * </p>
 *
 * @author BlackMagic
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CreateMilestonesActionAccuracyTest.class, DeleteMilestonesActionAccuracyTest.class,
    GetAllMilestonesActionAccuracyTest.class, GetAllMilestonesInMonthActionAccuracyTest.class,
    LoadMilestonesActionAccuracyTest.class, UpdateMilestonesActionAccuracyTest.class })
public class AccuracyTests {
}
