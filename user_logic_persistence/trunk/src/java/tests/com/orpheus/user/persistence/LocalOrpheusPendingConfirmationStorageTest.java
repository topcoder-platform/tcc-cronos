/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * Tests the LocalOrpheusPendingConfirmationStorage class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class LocalOrpheusPendingConfirmationStorageTest extends LocalOrpheusClientTestBase {

    /**
     * <p>
     * A test LocalOrpheusPendingConfirmationStorage configuration file
     * containing valid configuration.
     * </p>
     */
    private static final String VALID_CONFIG_FILE
        = "test_conf/valid_pendingconfirmationclient_config.xml";

    /**
     * <p>
     * A test LocalOrpheusPendingConfirmationStorage configuration file
     * containing invalid configuration.
     * </p>
     */
    private static final String INVALID_CONFIG_FILE
        = "test_conf/invalid_pendingconfirmationclient_config.xml";

    /**
     * <p>
     * The prefix for the test LocalOrpheusPendingConfirmationStorage
     * configuration namespaces.
     * </p>
     */
    private static final String NAMESPACE_PREFIX = OrpheusPendingConfirmationStorage.class.getName();

    /**
     * <p>
     * The LocalOrpheusPendingConfirmationStorage instance to test.
     * </p>
     */
    private LocalOrpheusPendingConfirmationStorage storage = null;

    /**
     * <p>
     * The test case helper to which all of the test methods, except those
     * testing the constructor, will be delegated to.
     * </p>
     */
    private OrpheusPendingConfirmationStorageTestHelper testCaseHelper = null;

    /**
     * <p>
     * Creates a new LocalOrpheusPendingConfirmationStorageTest instance.
     * </p>
     */
    public LocalOrpheusPendingConfirmationStorageTest() {
        super(NAMESPACE_PREFIX, NAMESPACE_PREFIX + ".local", VALID_CONFIG_FILE);
    }

    /**
     * <p>
     * Loads the valid test Object Factory and
     * LocalOrpheusPendingConfirmationStorage configuration namespaces into the
     * Configuration Manager, and creates the test
     * LocalOrpheusPendingConfirmationStorage instance.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        // Load the configuration.
        super.setUp();

        // Create the LocalOrpheusPendingConfirmationStorage instance.
        storage = new LocalOrpheusPendingConfirmationStorage(NAMESPACE_PREFIX + ".local");

        // Create the test case helper.
        testCaseHelper = new OrpheusPendingConfirmationStorageTestHelper(storage);
    }

    /**
     * <p>
     * Tests the LocalOrpheusPendingConfirmationStorage() constructor. The
     * configuration is loaded from the default namespace - the fully-qualified
     * name of the LocalOrpheusPendingConfirmationStorage class. The newly
     * created instance should not be null.
     * </p>
     */
    public void testDefaultCtor() throws Exception {
        // Load test config.
        ConfigHelper.loadConfig(VALID_CONFIG_FILE, LocalOrpheusPendingConfirmationStorage.class.getName());

        try {
            LocalOrpheusPendingConfirmationStorage localStorage = new LocalOrpheusPendingConfirmationStorage();
            assertNotNull("The LocalOrpheusPendingConfirmationStorage instance should not be null", localStorage);
        } finally {
            // Unload config.
            ConfigHelper.unloadConfig(LocalOrpheusPendingConfirmationStorage.class.getName());
        }
    }

    /**
     * <p>
     * Tests the LocalOrpheusPendingConfirmationStorage(String namespace)
     * constructor. The newly created instance should not be null.
     * </p>
     */
    public void testCtorWithValidArg() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The LocalOrpheusPendingConfirmationStorage instance should not be null", storage);
    }

    /**
     * <p>
     * Tests that the LocalOrpheusPendingConfirmationStorage(String namespace)
     * contructor throws an IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithNullArg() throws Exception {
        try {
            new LocalOrpheusPendingConfirmationStorage(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the LocalOrpheusPendingConfirmationStorage(String namespace)
     * contructor throws an IllegalArgumentException when the argument is an
     * empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyArg() throws Exception {
        try {
            new LocalOrpheusPendingConfirmationStorage(" ");
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Performs the LocalOrpheusPendingConfirmationStorage(String namespace)
     * constructor test with invalid configuration. An
     * ObjectInstantiationException is expected to be thrown.
     * </p>
     *
     * @param namespace the configuration namespace containing invalid
     *        configuration
     * @throws ConfigManagerException if loading the invalid configuration
     *         namespace fails
     */
    protected void performCtorTestWithInvalidConfig(String namespace) throws ConfigManagerException {
        // Load the invalid configuration.
        ConfigHelper.loadConfig(INVALID_CONFIG_FILE, namespace);

        try {
            new LocalOrpheusPendingConfirmationStorage(namespace);
            fail("ObjectInstantiationException should be thrown: invalid config namespace: " + namespace);
        } catch (ObjectInstantiationException e) {
            // Success.
        } finally {
            // Unload the invalid configuration.
            ConfigHelper.unloadConfig(namespace);
        }
    }

    /**
     * <p>
     * Tests the store(ConfirmationMessage message) with a valid argument. The
     * contains(String address) method should return true value when given an
     * argument equal to the address of the message that was stored.
     * </p>
     */
    public void testStoreWithValidArg() {
        testCaseHelper.testStoreWithValidArg();
    }

    /**
     * <p>
     * Tests that the store(ConfirmationMessage message) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     */
    public void testStoreWithNullArg() {
        testCaseHelper.testStoreWithNullArg();
    }

    /**
     * <p>
     * Tests that the store(ConfirmationMessageDTO message) throws an
     * InvalidAddressException when given an argument that already exists in the
     * persistent store.
     * </p>
     */
    public void testStoreWithDuplicateArg() {
        testCaseHelper.testStoreWithDuplicateArg();
    }

    /**
     * <p>
     * Tests that the contains(String address) method returns false when the
     * confirmation message corresponding to the argument does not exist in the
     * persistent store.
     * </p>
     * <p>
     * Note that the opposite case (i.e. when the message is in the persistent
     * store) is tested by the testStoreWithValidArg() unit test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContainsWithValidArgNotInPersistence() throws Exception {
        testCaseHelper.testContainsWithValidArgNotInPersistence();
    }

    /**
     * <p>
     * Tests that the contains(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     */
    public void testContainsWithNullArg() {
        testCaseHelper.testContainsWithNullArg();
    }

    /**
     * <p>
     * Tests that the contains(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     */
    public void testContainsWithEmptyArg() {
        testCaseHelper.testContainsWithEmptyArg();
    }

    /**
     * <p>
     * Tests the retrieve(String address) method when the confirmation message
     * corresponding to the argument is in the persistent store. The information
     * in the returned message should be equal to that in the persistent store.
     * </p>
     */
    public void testRetrieveWithValidArg() {
        testCaseHelper.testRetrieveWithValidArg();
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     */
    public void testRetrieveWithNullArg() {
        testCaseHelper.testRetrieveWithNullArg();
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     */
    public void testRetrieveWithEmptyArg() {
        testCaseHelper.testRetrieveWithEmptyArg();
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * InvalidAddressException when the confirmation message corresponding to
     * the argument does not exist in the persistent store.
     * </p>
     */
    public void testRetrieveWithArgNotInPersistence() {
        testCaseHelper.testRetrieveWithArgNotInPersistence();
    }

    /**
     * <p>
     * Tests the delete(String address) method when the confirmation message
     * corresponding to the argument is in the persistent store. After the
     * delete operation, invoking the contains(String address) method with the
     * method argument should return false.
     * </p>
     */
    public void testDeleteWithValidArg() {
        testCaseHelper.testDeleteWithValidArg();
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     */
    public void testDeleteWithNullArg() {
        testCaseHelper.testDeleteWithNullArg();
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     */
    public void testDeleteWithEmptyArg() {
        testCaseHelper.testDeleteWithEmptyArg();
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * InvalidAddressException when the confirmation message corresponding to
     * the argument does not exist in the persistent store.
     * </p>
     */
    public void testDeleteWithArgNotInPersistence() {
        testCaseHelper.testDeleteWithArgNotInPersistence();
    }

    /**
     * <p>
     * Tests the getAddresses() method. A number of messages are stored at the
     * start of the test. Then, the getAddresses() method is called, and the
     * returned enumeration is iterated to check that the addresses of all the
     * messages added at the start of the test are in the returned enumeration.
     * </p>
     */
    public void testGetAddresses() {
        testCaseHelper.testGetAddresses();
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(LocalOrpheusPendingConfirmationStorageTest.class);
    }

}
