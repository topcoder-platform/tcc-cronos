package com.cronos.im.logic.stresstests;

import com.cronos.im.logic.MessagePoolDetector;

/**
 * Stress tests for MessagePoolDetector. Test the main() method under stress.
 * 
 * @author kaqi072821
 * @version 1.0
 */
public class MessagePoolDetectorStressTest extends BaseStressTest {

    /**
     * Initializes the environment.
     * 
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
    }

    /**
     * Clears the test environment.
     * 
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Stress test for the main method.
     */
    public void testMain() {
        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            MessagePoolDetector.main(new String[] { "-namespacecom.cronos.im.logic.MessagePoolDetector" });
        }
        endTest("testMain()", TestHelper.MAX_TIME);
    }
}
