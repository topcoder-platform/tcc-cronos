/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.util.Date;
import java.util.Enumeration;

import junit.framework.Assert;

import com.topcoder.validation.emailconfirmation.ConfirmationMessage;
import com.topcoder.validation.emailconfirmation.InvalidAddressException;

/**
 * <p>
 * Used by the LocalOrpheusPendingConfirmationStorageTest and
 * RemoteOrpheusPendingConfirmationStorageTest test cases to test the
 * LocalOrpheusPendingConfirmationStorage and
 * RemoteOrpheusPendingConfirmationStorage classes, respectively. It contains
 * all the common test methods shared between the two test cases. Those common
 * test methods delegate to the corresponding methods in this class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class OrpheusPendingConfirmationStorageTestHelper {

    /**
     * <p>
     * A sample message address to use during the unit tests.
     * </p>
     */
    private static final String ADDRESS = "daounittest@topcodersoftware.com";

    /**
     * <p>
     * The OrpheusPendingConfirmationStorage object to tests.
     * </p>
     */
    private OrpheusPendingConfirmationStorage storage = null;

    /**
     * <p>
     * Creates a new OrpheusPendingConfirmationStorageTestHelper instance that
     * tests the given OrpheusPendingConfirmationStorage object.
     * </p>
     *
     * @param storage the OrpheusPendingConfirmationStorage object to test
     */
    public OrpheusPendingConfirmationStorageTestHelper(OrpheusPendingConfirmationStorage storage) {
        this.storage = storage;
    }

    /**
     * <p>
     * Tests the store(ConfirmationMessage message) with a valid argument. The
     * contains(String address) method should return true value when given an
     * argument equal to the address of the message that was stored.
     * </p>
     */
    public void testStoreWithValidArg() {
        // Create the confirmation message to store.
        ConfirmationMessage message = createMessage(ADDRESS);

        // Store the message.
        storage.store(message);

        // Check if the message has been stored.
        try {
            Assert.assertTrue("The confirmation message was not stored successfully",
                              storage.contains(message.getAddress()));
        } finally {
            // Clean-up by deleting the message.
            storage.delete(message.getAddress());
        }
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
            Assert.fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the store(ConfirmationMessageDTO message) throws an
     * InvalidAddressException when given an argument that already exists in the
     * persistent store.
     * </p>
     */
    public void testStoreWithDuplicateArg() {
        // Create the confirmation message to store.
        ConfirmationMessage message = createMessage(ADDRESS);

        // Store the message.
        storage.store(message);

        // Try to store the message again.
        try {
            storage.store(message);
            Assert.fail("InvalidAddressException should be thrown: persistent store already contains message");
        } catch (InvalidAddressException e) {
            // Success.
        } finally {
            // Clean-up by deleting the message.
            storage.delete(message.getAddress());
        }
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
     * @throws Exception if checking if the confirmation message exists fails
     */
    public void testContainsWithValidArgNotInPersistence() throws Exception {
        String address = "IDontExist";
        Assert.assertFalse("The persistent store should not contain the message", storage.contains(address));
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
            Assert.fail("IllegalArgumentException should be thrown: null argument");
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
            Assert.fail("IllegalArgumentException should be thrown: empty string argument");
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
     */
    public void testRetrieveWithValidArg() {
        // Store a confirmation message.
        ConfirmationMessage message = createMessage(ADDRESS);
        storage.store(message);

        ConfirmationMessage message2 = storage.retrieve(message.getAddress());

        // Check if the message information is correct.
        try {
            Assert.assertEquals("The address is incorrect", message.getAddress(), message2.getAddress());
            Assert.assertEquals("The unlock code is incorrect", message.getUnlockCode(), message2.getUnlockCode());
            Assert.assertEquals("The send date is incorrect", message.getDateSent(), message2.getDateSent());
            Assert.assertEquals("The message subject is incorrect", message.getMessageSubject(),
                                message2.getMessageSubject());
            Assert.assertEquals("The message body is incorrect", message.getMessageBody(), message2.getMessageBody());
        } finally {
            // Clean-up by deleting the message.
            storage.delete(message.getAddress());
        }
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
            Assert.fail("IllegalArgumentException should be thrown: null argument");
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
            Assert.fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the retrieve(String address) method throws an
     * InvalidAddressException when the confirmation message corresponding to
     * the argument does not exist in the persistent store.
     * </p>
     */
    public void testRetrieveWithArgNotInPersistence() {
        String address = "IDontExist";
        try {
            storage.retrieve(address);
            Assert.fail("IllegalAddressException should be thrown: message not in persistence");
        } catch (InvalidAddressException e) {
            // Success.
        }
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
        // Store a confirmation message.
        ConfirmationMessage message = createMessage(ADDRESS);
        storage.store(message);

        // Delete the message.
        storage.delete(message.getAddress());

        // Check if the message was deleted.
        Assert.assertFalse("The confirmation message was not deleted successfully",
                           storage.contains(message.getAddress()));
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
            Assert.fail("IllegalArgumentException should be thrown: null argument");
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
            Assert.fail("IllegalArgumentException should be thrown: empty string argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the delete(String address) method throws an
     * InvalidAddressException when the confirmation message corresponding to
     * the argument does not exist in the persistent store.
     * </p>
     */
    public void testDeleteWithArgNotInPersistence() {
        String address = "IDontExist";
        try {
            storage.delete(address);
            Assert.fail("InvalidAddressException should be thrown: message not in persistence");
        } catch (InvalidAddressException e) {
            // Success.
        }
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
        int numMessages = 10;

        // Store a bunch of messages.
        for (int i = 0; i < numMessages; i++) {
            ConfirmationMessage message = createMessage(ADDRESS + i);
            storage.store(message);
        }

        // Get all the addresses.
        Enumeration addresses = storage.getAddresses();

        try {
            // Iterate through all the returned messages and check if the
            // numMessages addresses are there.
            int numReturned = 0;
            while (addresses.hasMoreElements()) {
                String address = (String) addresses.nextElement();
                for (int i = 0; i < numMessages; i++) {
                    if (address.equals(ADDRESS + i)) {
                        numReturned++;
                        break;
                    }
                }
            }
            Assert.assertEquals("Not all the addresses were returned", numMessages, numReturned);
        } finally {
            // Clean-up by removing all the messages stored at the start of the
            // test.
            for (int i = 0; i < numMessages; i++) {
                storage.delete(ADDRESS + i);
            }
        }
    }

    /**
     * <p>
     * Creates a ConfirmationMessage object with the specified address that can
     * be used during the unit tests.
     * </p>
     *
     * @param address the address of the message to create
     * @return a ConfirmationMessage object
     */
    private ConfirmationMessage createMessage(String address) {
        return new ConfirmationMessage(address, "XYZ", new Date(), "Greetings from Outer Space",
                "Hello\nWorld!\n\nHow are you?");
    }

}
