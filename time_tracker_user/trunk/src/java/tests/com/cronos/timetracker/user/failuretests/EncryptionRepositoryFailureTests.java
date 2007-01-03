/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.common.EncryptionRepository;

/**
 * <p>
 * Failure unit test cases for the EncryptionRepository class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class EncryptionRepositoryFailureTests extends TestCase {

    /**
     * <p>
     * Tests registerAlgorithm(String, AbstractEncryptionAlgorithm) for failure. Passes empty string,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testRegisterAlgorithm1() {
        try {
            EncryptionRepository.getInstance().registerAlgorithm(" ", new MockAbstractEncryptionAlgorithm());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests registerAlgorithm(String, AbstractEncryptionAlgorithm) for failure. Passes null algorithm,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testRegisterAlgorithm2() {
        try {
            EncryptionRepository.getInstance().registerAlgorithm("aa", null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests retrieveAlgorithm(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testRetrieveAlgorithm1() {
        try {
            EncryptionRepository.getInstance().retrieveAlgorithm(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests unregisterAlgorithm(String) for failure. Passes empty string, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testUnregisterAlgorithm1() {
        try {
            EncryptionRepository.getInstance().unregisterAlgorithm(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
