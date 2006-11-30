/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.validation.emailconfirmation.ConfirmationMessage;
import com.topcoder.validation.emailconfirmation.PendingConfirmationStorageInterface;

/**
 * <p>
 * Tests the abstract OrpheusPendingConfirmationStorage class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class OrpheusPendingConfirmationStorageTest extends RemoteOrpheusClientTestBase {

    /**
     * <p>
     * A test OrpheusPendingConfirmationStorage configuration file containing
     * valid configuration.
     * </p>
     */
    private static final String VALID_CONFIG_FILE
        = "test_conf/valid_pendingconfirmationclient_config.xml";

    /**
     * <p>
     * A test OrpheusPendingConfirmationStorage configuration file containing
     * invalid configuration.
     * </p>
     */
    private static final String INVALID_CONFIG_FILE
        = "test_conf/invalid_pendingconfirmationclient_config.xml";

    /**
     * <p>
     * The prefix for the test OrpheusPendingConfirmationStorage configuration
     * namespaces.
     * </p>
     */
    private static final String NAMESPACE_PREFIX = OrpheusPendingConfirmationStorage.class.getName();

    /**
     * <p>
     * The OrpheusPendingConfirmationStorage instance to test.
     * </p>
     */
    private OrpheusPendingConfirmationStorage storage = null;

    /**
     * <p>
     * Creates a new OrpheusPendingConfirmationStorageTest instance.
     * </p>
     */
    public OrpheusPendingConfirmationStorageTest() {
        super(NAMESPACE_PREFIX, NAMESPACE_PREFIX + ".remote", VALID_CONFIG_FILE);
    }

    /**
     * <p>
     * Loads the valid test Object Factory and OrpheusPendingConfirmationStorage
     * configuration namespaces into the Configuration Manager, and creates the
     * test OrpheusPendingConfirmationStorage instance. Since the
     * OrpheusPendingConfirmationStorage class is abstract, a
     * MockOrpheusPendingConfirmationStorage instance is instantiated instead.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        // Load the configuration.
        super.setUp();

        // Create the OrpheusPendingConfirmationStorage instance.
        storage = new MockOrpheusPendingConfirmationStorage(NAMESPACE_PREFIX + ".remote");
    }

    /**
     * <p>
     * Tests the OrpheusPendingConfirmationStorage(String namespace)
     * constructor. Since the OrpheusPendingConfirmationStorage class is
     * abstract, a MockOrpheusPendingConfirmationStorage instance is
     * instantiated instead. The newly created instance should not be null.
     * </p>
     */
    public void testCtorWithValidArg() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The OrpheusPendingConfirmationStorage instance should not be null", storage);
    }

    /**
     * <p>
     * Tests that the OrpheusPendingConfirmationStorage(String namespace)
     * contructor throws an IllegalArgumentException when the argument is null.
     * Since the OrpheusPendingConfirmationStorage class is abstract, the
     * MockOrpheusPendingConfirmationStorage(String name) constructor is invoked
     * instead.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithNullArg() throws Exception {
        try {
            new MockOrpheusPendingConfirmationStorage(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the OrpheusPendingConfirmationStorage(String namespace)
     * contructor throws an IllegalArgumentException when the argument is an
     * empty string. Since the OrpheusPendingConfirmationStorage class is
     * abstract, the MockOrpheusPendingConfirmationStorage(String name)
     * constructor is invoked instead.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyArg() throws Exception {
        try {
            new MockOrpheusPendingConfirmationStorage(" ");
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Performs the OrpheusPendingConfirmationStorage(String namespace)
     * constructor test with invalid configuration. Since the
     * OrpheusPendingConfirmationStorage class is abstract, the
     * MockOrpheusPendingConfirmationStorage(String name) constructor is invoked
     * instead.
     * </p>
     * <p>
     * The given invalid configuration namespace is loaded into the
     * Configuration Manager. Then, the constructor is called with the given
     * namespace. An ObjectInstantiationException is expected to be thrown.
     * </p>
     * <p>
     * The namespace is unloaded from the Configuration Manager after the test
     * has been performed. This will occur whether the test passed or failed.
     * </p>
     *
     * @param namespace the invalid configuration namespace to load into the
     *        Configuration Manager prior to running the test
     * @throws ConfigManagerException if loading or unloading configuration
     *         namespace fails
     */
    protected void performCtorTestWithInvalidConfig(String namespace) throws ConfigManagerException {
        // Load the invalid configuration.
        ConfigHelper.loadConfig(INVALID_CONFIG_FILE, namespace);

        try {
            new MockOrpheusPendingConfirmationStorage(namespace);
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
     * Tests that the store(ConfirmationMessage message) method calls the
     * protected ejbStore(ConfirmationMessageDTO address) method. This is
     * accomplished by checking that the
     * MockOrpheusPendingConfirmationStorage.storeMethodWasCalled() method
     * returns true.
     * </p>
     */
    public void testStoreWithValidArg() {
        storage.store(new ConfirmationMessage("address", "unlockcode", new Date(), "subject", "body"));

        assertTrue("The ejbStore(ConfirmationMessageDTO) method was not called",
                   ((MockOrpheusPendingConfirmationStorage) storage).storeMethodWasCalled());
    }

    /**
     * <p>
     * Tests that the store(ConfirmationMessage message) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     */
    public void testStoreWithNullArg() {
        try {
            storage.store(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the contains(String address) method calls the protected
     * ejbContains(String address) method. This is accomplished by checking that
     * the MockOrpheusPendingConfirmationStorage.containsMethodWasCalled()
     * method returns true.
     * </p>
     */
    public void testContainsWithValidArg() {
        storage.contains("address");

        assertTrue("The ejbContains(String) method was not called",
                   ((MockOrpheusPendingConfirmationStorage) storage).containsMethodWasCalled());
    }

    /**
     * <p>
     * Tests that the contains(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     */
    public void testContainsWithNullArg() {
        try {
            storage.contains(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the contains(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     */
    public void testContainsWithEmptyArg() {
        try {
            storage.contains(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method calls the protected
     * ejbRetrieve(String address) method. This is accomplished by checking that
     * the MockOrpheusPendingConfirmationStorage.retrieveMethodWasCalled()
     * method returns true.
     * </p>
     */
    public void testRetrieveWithValidArg() {
        storage.retrieve("address");
        assertTrue("The ejbRetrieve(String) method was not called",
                   ((MockOrpheusPendingConfirmationStorage) storage).retrieveMethodWasCalled());
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     */
    public void testRetrieveWithNullArg() {
        try {
            storage.retrieve(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     */
    public void testRetrieveWithEmptyArg() {
        try {
            storage.retrieve(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the delete(String address) method calls the protected
     * ejbDelete(String address) method. This is accomplished by checking that
     * the MockOrpheusPendingConfirmationStorage.deleteMethodWasCalled() method
     * returns true.
     * </p>
     */
    public void testDeleteWithValidArg() {
        storage.delete("address");
        assertTrue("The ejbDelete(String) method was not called",
                   ((MockOrpheusPendingConfirmationStorage) storage).deleteMethodWasCalled());
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     */
    public void testDeleteWithNullArg() {
        try {
            storage.delete(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     */
    public void testDeleteWithEmptyArg() {
        try {
            storage.delete(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the getAddresses() method calls the protected ejbGetMessages()
     * method. This is accomplished by checking that the
     * MockOrpheusPendingConfirmationStorage.getMessagesMethodWasCalled() method
     * returns true.
     * </p>
     */
    public void testGetAddresses() {
        storage.getAddresses();
        assertTrue("The ejbGetMessages() method was not called",
                   ((MockOrpheusPendingConfirmationStorage) storage).getMessagesMethodWasCalled());
    }

    /**
     * <p>
     * Tests that OrpheusPendingConfirmationStorage implements the
     * PendingConfirmationStorageInterface interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("OrpheusPendingConfirmationStorage should implement PendingConfirmationStorageInterface",
                   storage instanceof PendingConfirmationStorageInterface);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(OrpheusPendingConfirmationStorageTest.class);
    }

}
