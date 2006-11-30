/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests;

import com.orpheus.user.persistence.LocalOrpheusPendingConfirmationStorage;
import com.orpheus.user.persistence.OrpheusPendingConfirmationStorage;
import com.topcoder.validation.emailconfirmation.ConfirmationMessage;
import org.apache.cactus.ServletTestCase;

import java.util.Date;
import java.util.Enumeration;

/**
 * Accuracy test cases for <code>LocalOrpheusPendingConfirmation</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class LocalOrpheusPendingConfirmationAccTests extends ServletTestCase {

    /**
     * The email address used in testing.
     */
    private static final String EMAIL_ADDRESS = "test@topcoder.com";

    /**
     * <p>
     * The LocalOrpheusPendingConfirmationStorage instance to test.
     * </p>
     */
    private LocalOrpheusPendingConfirmationStorage storage = null;

    /**
     * <p>
     * Setup for each test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyHelper.loadBaseConfig();
        AccuracyHelper.loadConfiguration("test_conf/accuracy/PendingConfirmationClient.xml");

        // Create the LocalOrpheusPendingConfirmationStorage instance.
        storage = new LocalOrpheusPendingConfirmationStorage(OrpheusPendingConfirmationStorage.class.getName()
                + ".local");
    }

    /**
     * <p>
     * Cleanup for each test cases.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        try {
            storage.delete(EMAIL_ADDRESS);
        } catch (Exception e) {
            // ignore
        }
        AccuracyHelper.clearAllConfigurationNS();
    }

    /**
     * <p>
     * Accuracy test of the <code>store()</code> method.
     * </p>
     *
     */
    public void testStore() {
        ConfirmationMessage message = createMessage();

        // store the message
        storage.store(message);

        // check the result
        assertTrue("The message was not stored correctly.",
                storage.contains(message.getAddress()));
    }

    /**
     * <p>
     * Accuracy test of the <code>retrieve()</code> method.
     * </p>
     *
     */
    public void testRetrieve() {
        ConfirmationMessage message = createMessage();

        // store the message
        storage.store(message);

        ConfirmationMessage result = storage.retrieve(message.getAddress());

        // check the result
        assertEquals("Not the expected address.", message.getAddress(), result.getAddress());
        assertEquals("Not the expected unlock code.", message.getUnlockCode(), result.getUnlockCode());
        assertEquals("Not the expected date sent.", message.getDateSent(), result.getDateSent());
        assertEquals("Not the expected subject.", message.getMessageSubject(), result.getMessageSubject());
        assertEquals("Not the expected message body.", message.getMessageBody(), result.getMessageBody());
    }

    /**
     * <p>
     * Accuracy test of the <code>delete()</code> method.
     * </p>
     *
     */
    public void testDelete() {
        ConfirmationMessage message = createMessage();

        // store the message
        storage.store(message);

        // delete the message
        storage.delete(message.getAddress());

        // check the result
        assertFalse("The message was not removed correctly.",
                storage.contains(message.getAddress()));
    }

    /**
     * <p>
     * Accuracy test of the <code>getAddresses()</code> method.
     * </p>
     *
     */
    public void testGetAddresses() {

        ConfirmationMessage message = createMessage();

        // store the message
        storage.store(message);

        // get all the addresses.
        Enumeration addresses = storage.getAddresses();

        // check the result
        while (addresses.hasMoreElements()) {
            String address = (String) addresses.nextElement();
            assertEquals("Not the expected address.", message.getAddress(), address);
        }
    }

    /**
     * <p>
     * Helper method to create a ConfirmationMessage object for testing.
     * </p>
     *
     * @return a ConfirmationMessage object to test.
     */
    private ConfirmationMessage createMessage() {
        return new ConfirmationMessage(EMAIL_ADDRESS, "1234", new Date(), "Subject",
                "Body");
    }
}
