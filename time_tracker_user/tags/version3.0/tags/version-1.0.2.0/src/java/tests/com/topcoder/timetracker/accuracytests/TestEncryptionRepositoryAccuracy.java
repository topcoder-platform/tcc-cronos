/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.common.EncryptionRepository;
import com.topcoder.encryption.AbstractEncryptionAlgorithm;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>EncryptionRepository </code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestEncryptionRepositoryAccuracy extends TestCase {

    /**
     * Represents the EncryptionRepository instance for test.
     */
    private static EncryptionRepository test = EncryptionRepository.getInstance();

    /**
     * The algorithm instance used in tests.
     */
    private AbstractEncryptionAlgorithm algo = new SimpleAlgorithm();

    /**
     * Test constructor.
     *
     */
    public void testConstructor() {
        test = EncryptionRepository.getInstance();

        assertNotNull("Should not be null.", test);

        EncryptionRepository test2 = EncryptionRepository.getInstance();

        assertEquals("Should be equal", test, test2);
    }

    /**
     * test method registerAlgorithm.
     *
     */
    public void testRegisterAlgorithm() {
        test.registerAlgorithm("test", algo);
        assertEquals("Equal is expected.", algo, test.retrieveAlgorithm("test"));
    }

    /**
     * test method unregisterAlgorithm.
     *
     */
    public void testUnregisterAlgorithm() {
        test.registerAlgorithm("test", algo);

        assertEquals("Equal is expected.", algo, test.retrieveAlgorithm("test"));

        test.unregisterAlgorithm("test");

        assertNull("Should be null.", test.retrieveAlgorithm("test"));
    }

    /**
     * test method retrieveAlgorithm.
     *
     */
    public void testRetrieveAlgorithm() {
        test.registerAlgorithm("test", algo);

        assertEquals("Equal is expected.", algo, test.retrieveAlgorithm("test"));

    }

    /**
     * Test method clearAlgorithms.
     *
     */
    public void testClearAlgorithms() {
        test.registerAlgorithm("test", algo);

        assertEquals("Equal is expected.", algo, test.retrieveAlgorithm("test"));

        test.clearAlgorithms();

        assertNull("Should be null.", test.retrieveAlgorithm("test"));
    }
}
