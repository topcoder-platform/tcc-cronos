/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.studio.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;
import com.topcoder.service.studio.failuretests.ContestStatusDataFailureTests;
import com.topcoder.service.studio.failuretests.ContestDataFailureTests;
import com.topcoder.service.studio.failuretests.ejb.StudioServiceBeanFailureTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  ContestDataFailureTests.class,
  ContestStatusDataFailureTests.class,
  StudioServiceBeanFailureTests.class
})
public class FailureTests {
}
