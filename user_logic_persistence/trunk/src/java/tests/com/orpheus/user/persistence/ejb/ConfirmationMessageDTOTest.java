/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.ejb;

import java.io.Serializable;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the ConfirmationMessageDTO class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class ConfirmationMessageDTOTest extends TestCase {

    /**
     * <p>
     * The ConfirmationMessageDTO instance to test.
     * </p>
     */
    private ConfirmationMessageDTO message = null;

    /**
     * <p>
     * Creates the ConfirmationMessageDTO instance to test.
     * </p>
     */
    protected void setUp() {
        message = new ConfirmationMessageDTO();
    }

    /**
     * <p>
     * Tests the ConfirmationMessageDTO() constructor. The newly created
     * instance should not be null.
     * </p>
     */
    public void testConfirmationMessageDTO() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The ConfirmationMessageDTO instance should not be null", message);
    }

    /**
     * <p>
     * Tests the setAddress(String address) method with a valid non-null
     * non-empty string argument. The return value of the getAddress() method
     * should be equal to the method argument.
     * </p>
     */
    public void testSetAddressWithValidNonNullNonEmptyArg() {
        String address = "TCSDEVELOPER@topcodersoftware.com";
        message.setAddress(address);
        assertEquals("The address is incorrect", address, message.getAddress());
    }

    /**
     * <p>
     * Tests the setAddress(String address) method with a valid null argument.
     * The return value of the getAddress() method should be null.
     * </p>
     */
    public void testSetAddressWithValidNullArg() {
        message.setAddress(null);
        assertNull("The address should be null", message.getAddress());
    }

    /**
     * <p>
     * Tests the setAddress(String address) method with a valid empty string
     * argument. The return value of the getAddress() method should be equal to
     * the method argument.
     * </p>
     */
    public void testSetAddressWithValidEmptyArg() {
        String address = "  ";
        message.setAddress(address);
        assertEquals("The address is incorrect", address, message.getAddress());
    }

    /**
     * <p>
     * Tests the setUnlockCode(String unlockCode) method with a valid non-null
     * non-empty string argument. The return value of the getUnlockCode() method
     * should be equal to the method argument.
     * </p>
     */
    public void testSetUnlockCodeWithValidNonNullNonEmptyArg() {
        String unlockCode = "XYZABC0123";
        message.setUnlockCode(unlockCode);
        assertEquals("The unlock code is incorrect", unlockCode, message.getUnlockCode());
    }

    /**
     * <p>
     * Tests the setUnlockCode(String unlockCode) method with a valid null
     * argument. The return value of the getUnlockCode() method should be null.
     * </p>
     */
    public void testSetUnlockCodeWithValidNullArg() {
        message.setUnlockCode(null);
        assertNull("The unlock code should be null", message.getUnlockCode());
    }

    /**
     * <p>
     * Tests the setUnlockCode(String unlockCode) method with a valid empty
     * string argument. The return value of the getUnlockCode() method should be
     * equal to the method argument.
     * </p>
     */
    public void testSetUnlockCodeWithEmptyArg() {
        String unlockCode = " ";
        message.setUnlockCode(unlockCode);
        assertEquals("The unlock code is incorrect", unlockCode, message.getUnlockCode());
    }

    /**
     * <p>
     * Tests the setDateSent(Date dateSent) method with a valid non-null
     * argument. The return value of the getDateSent() method should be equal to
     * the method argument.
     * </p>
     */
    public void testSetDateSentWithValidNonNullArg() {
        Date dateSent = new Date();
        message.setDateSent(dateSent);
        assertEquals("The date sent is incorrect", dateSent, message.getDateSent());
    }

    /**
     * <p>
     * Tests the setDateSent(Date dateSent) method with a valid null argument.
     * The return value of the getDateSent() method should be null.
     * </p>
     */
    public void testSetDateSentWithValidNullArg() {
        message.setDateSent(null);
        assertNull("The date sent should be null", message.getDateSent());
    }

    /**
     * <p>
     * Tests the setMessageSubject(String messageSubject) method with a valid
     * non-null non-empty string argument. The return value of the
     * getMessageSubject() method should be equal to the method argument.
     * </p>
     */
    public void testSetMessageSubjectWithValidNonNullNonEmptyArg() {
        String subject = "Hello";
        message.setMessageSubject(subject);
        assertEquals("The message subject is incorrect", subject, message.getMessageSubject());
    }

    /**
     * <p>
     * Tests the setMessageSubject(String messageSubject) method with a valid
     * null argument. The return value of the getMessageSubject() method should
     * be null.
     * </p>
     */
    public void testSetMessageSubjectWithValidNullArg() {
        message.setMessageSubject(null);
        assertNull("The message subject should be null", message.getMessageSubject());
    }

    /**
     * <p>
     * Tests the setMessageSubject(String messageSubject) method with a valid
     * empty string argument. The return value of the getMessageSubject() method
     * should be equal to the method argument.
     * </p>
     */
    public void testSetMessageSubjectWithValidEmptyArg() {
        String subject = "  ";
        message.setMessageSubject(subject);
        assertEquals("The message subject is incorrect", subject, message.getMessageSubject());
    }

    /**
     * <p>
     * Tests the setMessageBody(String messageBody) method with a valid non-null
     * non-empty string argument. The return value of the getMessageBody()
     * method should be equal to the method argument.
     * </p>
     */
    public void testSetMessageBodyWithValidNonNullNonEmptyArg() {
        String body = "Hello World!";
        message.setMessageBody(body);
        assertEquals("The message body is incorrect", body, message.getMessageBody());
    }

    /**
     * <p>
     * Tests the setMessageBody(String messageBody) method with a valid null
     * argument. The return value of the getMessageBody() method should be null.
     * </p>
     */
    public void testSetMessageBodyWithValidNullArg() {
        message.setMessageBody(null);
        assertNull("The message body should be null", message.getMessageBody());
    }

    /**
     * <p>
     * Tests the setMessageBody(String messageBody) method with a valid empty
     * string argument. The return value of the getMessageBody() method should
     * be equal to the method argument.
     * </p>
     */
    public void testSetMessageBodyWithValidEmptyArg() {
        String body = " ";
        message.setMessageBody(body);
        assertEquals("The message body is incorrect", body, message.getMessageBody());
    }

    /**
     * <p>
     * Tests that ConfirmationMessageDTO implements the Serializable interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("ConfirmationMessageDTO should implement the Serializable interface",
                   message instanceof Serializable);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(ConfirmationMessageDTOTest.class);
    }

}
