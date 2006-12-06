/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.plugin.firefox.stresstests;

import java.util.Iterator;

import junit.framework.TestCase;

import com.orpheus.plugin.firefox.FirefoxExtensionHelper;
import com.orpheus.plugin.firefox.UIEventType;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.test.stress.StressTest;
import com.topcoder.util.test.stress.StressTestIteration;
import com.topcoder.util.test.stress.StressTestIterationResults;
import com.topcoder.util.test.stress.StressTestResults;

/**
 * Stress tests for <pre>FirefoxExtensionHelper</pre>.
 * Since this is not a thread-safe class, no thread-safe tests will be performed.
 * Only benchmark tests will be done.
 * Stress Test component is used here to make it easier.
 *
 * @author assistant
 * @version 1.0
 *
 */
public class FirefoxExtensionHelperTest extends TestCase {

    /**
     * Represents the config file contains valid configuration.
     */
    private static final String CONFIG_FILE = "stresstests/config.xml";

    /**
     * Represents the helper to test.
     */
    private FirefoxExtensionHelper helper;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add(CONFIG_FILE);

        // hack the class loader to force it load from my implementation of JSObject
        FakeClassLoader cl = new FakeClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        UIEventType type = UIEventType.LOGIN_CLICK;

        helper = new FirefoxExtensionHelper();
        helper.initialize("com.orpheus.plugin.firefox.stresstests");
    }

    /**
     * Clear the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        ConfigManager manager = ConfigManager.getInstance();
        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Test method for pageChanged(java.lang.String).
     */
    public void testPageChanged() {
        StressTestIteration itr = new StressTestIteration() {

            public void runIteration() throws Exception {
                helper.pageChanged("http://www.topcoder.com");
            }};

        for (int i = 0; i < 3; i++) {
            StressTest st = new StressTest(itr, 1, (int) Math.pow(10, i) * 100);

            StressTestResults result = (StressTestResults) st.runStressTest().get(0);

            this.printResult(result, "pageChaned");
        }
    }

    /**
     * Test method for currentTargetTest(java.lang.String).
     */
    public void testCurrentTargetTest() {
        StressTestIteration itr = new StressTestIteration() {

            public void runIteration() throws Exception {
                helper.currentTargetTest("<test>This is a test</test>");
            }};

            for (int i = 0; i < 3; i++) {
                StressTest st = new StressTest(itr, 1, (int) Math.pow(10, i) * 100);

                StressTestResults result = (StressTestResults) st.runStressTest().get(0);

                this.printResult(result, "currentTargetTest");
            }
    }

    /**
     * Test method for currentTargetTest(java.lang.String) with huge input.
     */
    public void testCurrentTargetTest_Highload() {
        StringBuilder sb = new StringBuilder();
        sb.append("<test>");
        for (int i = 0; i < 100; i++) {
            sb.append("This is a test<inner>inner text</inner>");
        }
        sb.append("</test>");
        final String input = sb.toString();

        StressTestIteration itr = new StressTestIteration() {

            public void runIteration() throws Exception {
                helper.currentTargetTest(input);
            }};

            for (int i = 0; i < 3; i++) {
                StressTest st = new StressTest(itr, 1, (int) Math.pow(10, i) * 100);

                StressTestResults result = (StressTestResults) st.runStressTest().get(0);

                this.printResult(result, "currentTargetTest");
            }
    }

    /**
     * Print the result to standard output.
     *
     * @param result the result
     * @param method the method name
     */
    private void printResult(StressTestResults result, String method) {
        System.out.println("Run " + method + " for " + result.getNumIterations()
                + " times used : " + result.getAverageExecutionTimeMS() * result.getNumIterations()
                + "ms, failed number : " + result.getNumFailedIterations());
        for (int i = 0; i < result.getIterationResults().size(); i++) {
            StressTestIterationResults res = (StressTestIterationResults) result.getIterationResults().get(i);
            if (res.getCaughtThrowable() != null) {
                System.err.println(res.getCaughtThrowable().getLocalizedMessage());
            }
        }
    }

}
