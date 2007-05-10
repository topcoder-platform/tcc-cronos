/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.login;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AdminLoginActionTest.suite());
        suite.addTest(ManagerLoginActionTest.suite());
        suite.addTest(ClientLoginActionTest.suite());
        suite.addTest(LoginActionTest.suite());
        suite.addTest(IMLoginHelperTest.suite());
        suite.addTest(LoginActionStressTest.suite());
        return suite;
    }

}
