/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.plugin.firefox.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyAppletTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTest(ExceptionTestCase.suite());
        suite.addTest(UIEventTypeTestCase.suite());
        suite.addTest(CookieExtensionPersistenceTestCase.suite());
        suite.addTest(OrpheusServerTestCase.suite());
        suite.addTest(FirefoxExtensionHelperTestCase.suite());
        
        return suite;
    }

}
