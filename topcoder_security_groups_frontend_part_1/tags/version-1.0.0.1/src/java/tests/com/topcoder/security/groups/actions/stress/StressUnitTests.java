/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stress;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * <p>
 * This test case aggregates all stress unit test cases.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
@SuiteClasses({UpdateGroupActionStressUnitTests.class, CreateGroupActionStressUnitTests.class,
    DeleteGroupActionStressUnitTests.class, SearchUserActionStressUnitTests.class,
    SearchGroupActionStressUnitTests.class, ViewGroupActionStressUnitTests.class })
@RunWith(Suite.class)
public class StressUnitTests {
}
