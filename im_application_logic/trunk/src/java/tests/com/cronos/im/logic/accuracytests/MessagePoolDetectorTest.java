/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.im.logic.MessagePoolDetector;

/**
 * Accuracy test for <code>{@link com.cronos.im.logic.MessagePoolDetector}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class MessagePoolDetectorTest extends TestCase {
    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadConfigs();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.releaseConfigs();
    }

    /**
     * Accuracy test for <code>{@link MessagePoolDetector#main(String[])}</code>.
     */
     public void testMethodMain_StringArray() {
         MessagePoolDetector.main(new String[]{"-h"});
         MessagePoolDetector.main(new String[]{"-help"});
         MessagePoolDetector.main(new String[]{});
     }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
     public static Test suite() {
        return new TestSuite(MessagePoolDetectorTest.class);
     }
}
