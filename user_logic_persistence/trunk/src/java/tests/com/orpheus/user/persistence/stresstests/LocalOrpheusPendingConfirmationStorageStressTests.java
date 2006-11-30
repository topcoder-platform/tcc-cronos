/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.stresstests;

import java.util.Date;

import org.apache.cactus.ServletTestCase;

import com.orpheus.user.persistence.LocalOrpheusPendingConfirmationStorage;
import com.orpheus.user.persistence.OrpheusPendingConfirmationStorage;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.validation.emailconfirmation.ConfirmationMessage;

/**
 * <p>
 * Tests the LocalOrpheusPendingConfirmationStorage class.
 * </p>
 *
 * @author Thinfox
 * @version 1.0
 */
public class LocalOrpheusPendingConfirmationStorageStressTests extends ServletTestCase {
    /**
     * <p>
     * The prefix for the test OrpheusPendingConfirmationStorage configuration namespaces.
     * </p>
     */
    private static final String NAMESPACE = OrpheusPendingConfirmationStorage.class.getName()
        + ".local";

    /**
     * <p>
     * A test RemoteOrpheusPendingConfirmationStorage configuration file containing valid
     * configuration.
     * </p>
     */
    private static final String CONFIG_FILE = "test_conf/stress/pendingconfirmationclient.xml";

    /** The number of invocations for stress test. */
    private static final int TOTAL_INVOCATION_COUNT = 50;

    /**
     * <p>
     * The OrpheusPendingConfirmationStorage instance to test.
     * </p>
     */
    private OrpheusPendingConfirmationStorage storage = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        StressTestHelper.loadConfiguration();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(CONFIG_FILE);

        storage = new LocalOrpheusPendingConfirmationStorage(NAMESPACE);

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            if (storage.contains("address" + i)) {
                storage.delete("address" + i);
            }
            storage.store(new ConfirmationMessage("address" + i, "unlockcode" + i, new Date(),
                "subject" + i, "body" + i));
        }
    }

    /**
     * Cleans up the test environment.
     *
     * @throws Exception if error occur while load configuration settings
     */
    public void tearDown() throws Exception {
        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            if (storage.contains("address" + i)) {
                storage.delete("address" + i);
            }
        }

        StressTestHelper.cleanConfiguration();
    }

    /**
     * <p>
     * Tests the store(ConfirmationMessage message) method.
     * </p>
     */
    public void testStore() {
        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            storage.delete("address" + i);
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            storage.store(new ConfirmationMessage("address" + i, "unlockcode" + i, new Date(),
                "subject" + i, "body" + i));
        }

        System.out.println("Testing store method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>
     * Tests the contains(String address) method.
     * </p>
     */
    public void testContains() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            storage.contains("address" + i);
        }

        System.out.println("Testing contains method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");

    }

    /**
     * <p>
     * Tests the retrieve(String address) method.
     * </p>
     */
    public void testRetrieve() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            storage.retrieve("address" + i);
        }

        System.out.println("Testing retrieve method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");

    }

    /**
     * <p>
     * Tests the delete(String address) method.
     * </p>
     */
    public void testDelete() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            storage.delete("address" + i);
        }

        System.out.println("Testing delete method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * <p>
     * Tests the getAddresses() method.
     * </p>
     */
    public void testGetAddresses() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_INVOCATION_COUNT; i++) {
            storage.getAddresses();
        }

        System.out.println("Testing getAddresses method with " + TOTAL_INVOCATION_COUNT
            + " invocations, will cost " + (System.currentTimeMillis() - start) + " milliseconds.");
    }
}
