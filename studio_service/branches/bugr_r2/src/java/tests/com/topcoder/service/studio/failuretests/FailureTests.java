/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.studio.failuretests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.topcoder.service.studio.failuretests.ejb.StudioServiceBeanFailureTests;

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
public class FailureTests extends TestCase {

    /**
     * Gathers all unit tests together and return.
     *
     * @return all tests in one suite
     */
    public static Test suite() {
        return new JUnit4TestAdapter(FailureTests.class);
    }
}
