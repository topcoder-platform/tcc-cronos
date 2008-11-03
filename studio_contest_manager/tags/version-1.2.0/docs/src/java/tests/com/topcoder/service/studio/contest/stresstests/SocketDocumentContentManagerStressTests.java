/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.stresstests;

import com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * <p>
 * Stress test cases for <code>SocketDocumentContentManager</code> class.
 * </p>
 *
 * @author woodatxc
 * @version 1.0
 */
public class SocketDocumentContentManagerStressTests extends BaseStressTests {

    /**
     * <p>
     * The instance of SocketDocumentContentManager for testing.
     * </p>
     */
    private SocketDocumentContentManager manager;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        manager = new SocketDocumentContentManager("127.0.0.1", 2100);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        manager = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(SocketDocumentContentManagerStressTests.class);
    }

    /**
     * <p>
     * Tests <code>saveDocumentContent(String, byte[])</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSaveDocumentContent() throws Exception {
        this.beginTest();

        String path = "a.txt";
        byte[] contents = new byte[] {'a', 'b', 'c' };
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.saveDocumentContent(path, contents);
        }

        this.endTest("SocketDocumentContentManager's saveDocumentContent(String, byte[])", 10000);
    }

    /**
     * <p>
     * Tests <code>getDocumentContent(String)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDocumentContent() throws Exception {
        this.beginTest();

        String path = "a.txt";
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.getDocumentContent(path);
        }

        this.endTest("SocketDocumentContentManager's getDocumentContent(String)", 10000);
    }

    /**
     * <p>
     * Tests <code>existDocumentContent(String)</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testExistDocumentContent() throws Exception {
        this.beginTest();

        String path = "a.txt";
        for (int i = 0; i < RUN_TIMES; i++) {
            manager.existDocumentContent(path);
        }

        this.endTest("SocketDocumentContentManager's existDocumentContent(String)", 10000);
    }
}
