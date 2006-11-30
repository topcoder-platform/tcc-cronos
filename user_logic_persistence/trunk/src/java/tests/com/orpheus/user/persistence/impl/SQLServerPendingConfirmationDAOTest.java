/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.orpheus.user.persistence.ConfigHelper;
import com.orpheus.user.persistence.DuplicateEntryException;
import com.orpheus.user.persistence.EntryNotFoundException;
import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.PendingConfirmationDAO;
import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * Tests the SQLServerPendingConfirmationDAO class. The testing is performed
 * within an application server container, because the
 * SQLServerPendingConfirmationDAO class needs a bound JNDI reference to the
 * DataSource when it is created.
 * </p>
 * <p>
 * <b>Note:</b> This test case assumes that the database does not contain a
 * confirmation message with an address equal to
 * "daounittest@topcodersoftware.com" or "IDontExist". Please ensure that this
 * is the case. Otherwise, some unit tests may fail.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class SQLServerPendingConfirmationDAOTest extends DAOTestBase {

    /**
     * <p>
     * The SQLServerPendingConfirmationDAO instance to test.
     * </p>
     */
    private SQLServerPendingConfirmationDAO dao = null;

    /**
     * <p>
     * Creates the test SQLServerPendingConfirmationDAO instance.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        // Load the configuration.
        super.setUp();

        dao = new SQLServerPendingConfirmationDAO(SQLServerPendingConfirmationDAOTest.VALID_NAMESPACE_WITH_CONN_NAME);
    }

    /**
     * <p>
     * Tests the SQLServerPendingConfirmationDAO(String namespace) constructor
     * with a valid argument where the corresponding configuration contains the
     * optional "name" property. The newly created instance should not be null.
     * </p>
     */
    public void testCtorWithValidArg1() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The SQLServerPendingConfirmationDAO instance should not be null", dao);
    }

    /**
     * <p>
     * Tests the SQLServerPendingConfirmationDAO(String namespace) constructor
     * with a valid argument where the corresponding configuration does not
     * contain the optional "name" property. The newly created instance should
     * not be null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithValidArg2() throws Exception {
        dao = new SQLServerPendingConfirmationDAO(
                  SQLServerPendingConfirmationDAOTest.VALID_NAMESPACE_WITHOUT_CONN_NAME);
        assertNotNull("The SQLServerPendingConfirmationDAO instance should not be null", dao);
    }

    /**
     * <p>
     * Tests that the SQLServerPendingConfirmationDAO(String namespace)
     * constructor throws an IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithNullArg() throws Exception {
        try {
            new SQLServerPendingConfirmationDAO(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the SQLServerPendingConfirmationDAO(String namespace)
     * constructor throws an IllegalArgumentException when the argument is an
     * empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyArg() throws Exception {
        try {
            new SQLServerPendingConfirmationDAO(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Performs the SQLServerPendingConfirmationDAO(String namespace)
     * constructor test with invalid configuration. An
     * ObjectInstantiationException is expected to be thrown.
     * </p>
     *
     * @param namespace the namespace containing the invalid configuration
     * @throws ConfigManagerException if loading the invalid configuration
     *         namespace fails
     */
    protected void performCtorTestWithInvalidConfig(String namespace) throws ConfigManagerException {
        // Load the invalid configuration.
        ConfigHelper.loadConfig(INVALID_CONFIG_FILE, namespace);

        try {
            new SQLServerPendingConfirmationDAO(namespace);
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
     * Tests the store(ConfirmationMessageDTO message) with a valid argument.
     * The contains(String address) method should return a non-null value when
     * given an argument equal to the address of the message that was stored.
     * </p>
     * <p>
     * After the test, The confirmation message that was stored is deleted to
     * clean-up the database. This happens whether the test passed or failed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testStoreWithValidArg() throws Exception {
        // Create the confirmation message to store.
        ConfirmationMessageDTO message = createMessageDTO();

        // Store the message.
        dao.store(message);

        // Check if the message has been stored.
        try {
            assertNotNull("The confirmation message was not stored successfully", dao.contains(message.getAddress()));
        } finally {
            // Clean-up by deleting the message.
            dao.delete(message.getAddress());
        }
    }

    /**
     * <p>
     * Tests that the store(ConfirmationMessageDTO message) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testStoreWithNullArg() throws Exception {
        try {
            dao.store(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the store(ConfirmationMessageDTO message) throws an
     * IllegalArgumentException when given an argument that already exists in
     * the persistent store. The return value of the
     * DuplicateEntryException.getIdentifier() method should be equal to the
     * duplicate message address. with a valid argument.
     * </p>
     * <p>
     * After the test, The confirmation message that was stored is deleted to
     * clean-up the database. This happens whether the test passed or failed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testStoreWithDuplicateArg() throws Exception {
        // Create the confirmation message to store.
        ConfirmationMessageDTO message = createMessageDTO();

        // Store the message.
        dao.store(message);

        // Try to store the message again.
        try {
            dao.store(message);
            fail("DuplicateEntryException should be thrown");
        } catch (DuplicateEntryException e) {
            assertEquals("The duplicate entry identifier is incorrect", message.getAddress(), e.getIdentifier());
            // Success.
        } finally {
            // Clean-up by deleting the message.
            dao.delete(message.getAddress());
        }
    }

    /**
     * <p>
     * Tests the contains(String address) method when the confirmation message
     * corresponding to the argument is in the persistent store. The return
     * value of the method should not be null. Also, all the message information
     * should be equal to that in the persistent store.
     * </p>
     * <p>
     * After the test, The confirmation message that was stored is deleted to
     * clean-up the database. This happens whether the test passed or failed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContainsWithValidArg() throws Exception {
        // Store a confirmation message.
        ConfirmationMessageDTO message = createMessageDTO();
        dao.store(message);

        ConfirmationMessageDTO message2 = dao.contains(message.getAddress());

        // Check if the message information is correct.
        try {
            assertNotNull("The persistent store should contain the message", message2);
            compareMessageDTOs(message, message2);
        } finally {
            // Clean-up by deleting the message.
            dao.delete(message.getAddress());
        }
    }

    /**
     * <p>
     * Tests that the contains(String address) method returns null when the
     * confirmation message corresponding to the given argument does not exist
     * in the persistent store.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContainsWithValidArgNotInPersistence() throws Exception {
        String address = "IDontExist";
        assertNull("Null should be returned: message is not in persistent store", dao.contains(address));
    }

    /**
     * <p>
     * Tests that the contains(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContainsWithNullArg() throws Exception {
        try {
            dao.contains(null);
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the contains(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContainsWithEmptyArg() throws Exception {
        try {
            dao.contains(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests the retrieve(String address) method when the confirmation message
     * corresponding to the argument is in the persistent store. The information
     * in the returned message should be equal to that in the persistent store.
     * </p>
     * <p>
     * After the test, The confirmation message that was stored is deleted to
     * clean-up the database. This happens whether the test passed or failed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveWithValidArg() throws Exception {
        // Store a confirmation message.
        ConfirmationMessageDTO message = createMessageDTO();
        dao.store(message);

        ConfirmationMessageDTO message2 = dao.retrieve(message.getAddress());

        // Check if the message information is correct.
        try {
            compareMessageDTOs(message, message2);
        } finally {
            // Clean-up by deleting the message.
            dao.delete(message.getAddress());
        }
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveWithNullArg() throws Exception {
        try {
            dao.retrieve(null);
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveWithEmptyArg() throws Exception {
        try {
            dao.retrieve(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * EntryNotFoundException when the confirmation message corresponding to the
     * argument does not exist in the persistent store. The return value of the
     * EntryNotFoundException.getIdentifier() method should be equal to the
     * argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveWithArgNotInPersistence() throws Exception {
        String address = "IDontExist";
        try {
            dao.retrieve(address);
            fail("EntryNotFoundException should be thrown: message not in database");
        } catch (EntryNotFoundException e) {
            assertEquals("The missing entry ID is incorrect", address, e.getIdentifier());
            // Success.
        }
    }

    /**
     * <p>
     * Tests the delete(String address) method when the confirmation message
     * corresponding to the argument is in the persistent store. After the
     * delete operation, invoking the contains(String address) method with the
     * method argument should return null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteWithValidArg() throws Exception {
        // Store a confirmation message.
        ConfirmationMessageDTO message = createMessageDTO();
        dao.store(message);

        // Delete the message.
        dao.delete(message.getAddress());

        // Check if the message was deleted.
        assertNull("The confirmation message was not deleted successfully", dao.contains(message.getAddress()));
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * IllegalArgumentException when the argument is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteWithNullArg() throws Exception {
        try {
            dao.delete(null);
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * IllegalArgumentException when the argument is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteWithEmptyArg() throws Exception {
        try {
            dao.delete(" ");
            fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * EntryNotFoundException when the confirmation message corresponding to the
     * argument does not exist in the persistent store. The return value of the
     * EntryNotFoundException.getIdentifier() method should be equal to the
     * argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteWithArgNotInPersistence() throws Exception {
        String address = "IDontExist";
        try {
            dao.delete(address);
            fail("EntryNotFoundException should be thrown: message not in database");
        } catch (EntryNotFoundException e) {
            assertEquals("The missing entry ID is incorrect", address, e.getIdentifier());
            // Success.
        }
    }

    /**
     * <p>
     * Tests the getMessages() method. A number of messages are stored at the
     * start of the test. Then, the getMessages() method is called, and the
     * returned array is iterated to check that all the messages added at the
     * start of the test are in the returned array.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetMessages() throws Exception {
        int numMessages = 10;
        String addressPrefix = null;

        // Store a bunch of messages.
        for (int i = 0; i < numMessages; i++) {
            ConfirmationMessageDTO message = createMessageDTO();

            // Remember the address prefix.
            if (addressPrefix == null) {
                addressPrefix = message.getAddress();
            }

            // Make sure the message address is unique by suffixing it with a
            // number.
            message.setAddress(message.getAddress() + i);

            dao.store(message);
        }

        // Get all the messages.
        ConfirmationMessageDTO[] messages = dao.getMessages();

        try {
            // There should be at least numMessages messages returned. There may
            // be others that were not created by this unit testt.
            assertTrue("The number of messages is incorrect", messages.length >= numMessages);

            // Iterate through all the returned messages and check if the
            // numMessages messages are there.
            int numReturned = 0;
            for (int i = 0; i < messages.length; i++) {
                for (int j = 0; j < numMessages; j++) {
                    if (messages[i].getAddress().equals(addressPrefix + j)) {
                        numReturned++;
                        break;
                    }
                }
            }
            assertEquals("Not all the messages were returned", numMessages, numReturned);
        } finally {
            // Clean-up by removing all the messages stored at the start of the
            // test.
            for (int i = 0; i < numMessages; i++) {
                dao.delete(addressPrefix + i);
            }
        }
    }

    /**
     * <p>
     * Creates a ConfirmationMessageDTO object that can be used during the unit
     * tests.
     * </p>
     *
     * @return a ConfirmationMessageDTO object
     */
    private ConfirmationMessageDTO createMessageDTO() {
        ConfirmationMessageDTO message = new ConfirmationMessageDTO();
        message.setAddress("daounittest@topcodersoftware.com");
        message.setUnlockCode("XYZ");
        message.setDateSent(new Date());
        message.setMessageSubject("Greetings from Outer Space");
        message.setMessageBody("Hello\nWorld!\n\nHow are you?");
        return message;
    }

    /**
     * <p>
     * Compares the fields of two ConfirmationMessageDTO objects. If any field
     * is not the same, a JUnit assertion is raised.
     * </p>
     *
     * @param message1 the message to compare to message2
     * @param message2 the message to compare to message1
     */
    private void compareMessageDTOs(ConfirmationMessageDTO message1, ConfirmationMessageDTO message2) {
        assertEquals("The address is incorrect", message1.getAddress(), message2.getAddress());
        assertEquals("The unlock code is incorrect", message1.getUnlockCode(), message2.getUnlockCode());
        assertEquals("The send date is incorrect", message1.getDateSent(), message2.getDateSent());
        assertEquals("The message subject is incorrect", message1.getMessageSubject(), message2.getMessageSubject());
        assertEquals("The message body is incorrect", message1.getMessageBody(), message2.getMessageBody());
    }

    /**
     * <p>
     * Tests that SQLServerPendingConfirmationDAO implements the
     * PendingConfirmationDAO interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("SQLServerPendingConfirmationDAO should implement the PendingConfirmationDAO interface",
                   dao instanceof PendingConfirmationDAO);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(SQLServerPendingConfirmationDAOTest.class);
    }

}
