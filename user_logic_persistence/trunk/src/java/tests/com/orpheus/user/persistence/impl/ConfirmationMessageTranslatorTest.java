/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.orpheus.user.persistence.ObjectTranslator;
import com.orpheus.user.persistence.TranslationException;
import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;
import com.topcoder.validation.emailconfirmation.ConfirmationMessage;

/**
 * <p>
 * Tests the ConfirmationMessageTranslator class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class ConfirmationMessageTranslatorTest extends TestCase {

    /**
     * <p>
     * A sample confirmation message address that is used for testing purposes.
     * </p>
     */
    private static final String ADDRESS = "tcsdeveloper@topcodersoftware.com";

    /**
     * <p>
     * A sample confirmation message unlock code that is used for testing
     * purposes.
     * </p>
     */
    private static final String UNLOCK_CODE = "ABC-012";

    /**
     * <p>
     * A sample confirmation message send date that is used for testing
     * purposes.
     * </p>
     */
    private static final Date DATE_SENT = new Date();

    /**
     * <p>
     * A sample confirmation message subject that is used for testing purposes.
     * </p>
     */
    private static final String MSG_SUBJECT = "Greetings from Outer Space";

    /**
     * <p>
     * A sample confirmation message body that is used for testing purposes.
     * </p>
     */
    private static final String MSG_BODY = "Hello World!\n\nHow are you?";

    /**
     * <p>
     * The ConfirmationMessageTranslator instance to test.
     * </p>
     */
    private ConfirmationMessageTranslator translator = null;

    /**
     * <p>
     * Creates the test ConfirmationMessageTranslator instance.
     * </p>
     */
    protected void setUp() {
        translator = new ConfirmationMessageTranslator();
    }

    /**
     * <p>
     * Tests the ConfirmationMessageTranslator() constructor. The newly created
     * instance should not be null.
     * </p>
     */
    public void testCtor() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The ConfirmationMessageTranslator instance should not be null", translator);
    }

    /**
     * <p>
     * Tests the assembleVO(Object dataTransferObject) method with a
     * ConfirmationMessageDTO argument whose fields are all non-null or
     * non-empty strings. The getter methods of the returned ConfirmationMessage
     * instance return values equal to that returned by the corresponding getter
     * methods of the given ConfirmationMessageDTO argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithValidArg() throws Exception {
        performAssembleVOTestWithValidArg(ADDRESS, UNLOCK_CODE, DATE_SENT, MSG_SUBJECT, MSG_BODY);
    }

    /**
     * <p>
     * Tests the assembleVO(Object dataTransferObject) method with a
     * ConfirmationMessageDTO argument that has an empty string field. The
     * getter methods of the returned ConfirmationMessage instance return values
     * equal to that returned by the corresponding getter methods of the given
     * ConfirmationMessageDTO argument.
     * </p>
     * <p>
     * In this test, the message body will be an empty string. No exceptions is
     * expected to be thrown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithValidArgContainingEmptyFields() throws Exception {
        performAssembleVOTestWithValidArg(ADDRESS, UNLOCK_CODE, DATE_SENT, MSG_SUBJECT, " ");
    }

    /**
     * <p>
     * Called by the testAssembleVOWithValidArg() and
     * testAssembleVOWithValidArgContainingEmptyFields() methods to perform the
     * assembleVO(Object dataTransferObject) unit test with a valid argument.
     * </p>
     * <p>
     * A ConfirmationMessageDTO object is constructed from the given arguments.
     * Then, the assembleVO(Object dataTransferObject) method is invoked. The
     * returned ConfirmationMessage fields are then checked to see if they
     * correspond to the given arguments.
     * </p>
     *
     * @param address the confirmation message address
     * @param unlockCode the confirmation message unlock code
     * @param dateSent the send date of the confirmation message
     * @param msgSubject the confirmation message subject
     * @param msgBody the confirmation message body
     * @throws TranslationException thrown by assembleVO(Object) if translating
     *         the ConfirmationMessageDTO fails
     */
    private void performAssembleVOTestWithValidArg(String address, String unlockCode, Date dateSent, String msgSubject,
            String msgBody) throws TranslationException {
        // Create the ConfirmationMessageDTO from the method arguments.
        ConfirmationMessageDTO messageDTO = new ConfirmationMessageDTO();
        messageDTO.setAddress(address);
        messageDTO.setUnlockCode(unlockCode);
        messageDTO.setDateSent(dateSent);
        messageDTO.setMessageSubject(msgSubject);
        messageDTO.setMessageBody(msgBody);

        // Translate to a ConfirmationMessage object.
        ConfirmationMessage message = (ConfirmationMessage) translator.assembleVO(messageDTO);

        // Check that the ConfirmationMessage object fields are correct.
        assertEquals("The address is incorrect", address, message.getAddress());
        assertEquals("The unlock code is incorrect", unlockCode, message.getUnlockCode());
        assertEquals("The send date is incorrect", dateSent, message.getDateSent());
        assertEquals("The message subject is incorrect", msgSubject, message.getMessageSubject());
        assertEquals("The message body is incorrect", msgBody, message.getMessageBody());
    }

    /**
     * <p>
     * Tests that assembleVO(Object dataTransferObjecT) throws a
     * TranslationException when the ConfirmationMessageDTO argument contains
     * null fields. This TranslationException wraps an IllegalArgumentException,
     * which was thrown by the ConfirmationMessage constructor.
     * </p>
     */
    public void testAssembleVOWithArgContainingNullFields() {
        // Create the ConfirmationMessageDTO.
        ConfirmationMessageDTO messageDTO = new ConfirmationMessageDTO();
        messageDTO.setAddress(ADDRESS);
        messageDTO.setUnlockCode(UNLOCK_CODE);
        messageDTO.setDateSent(DATE_SENT);
        messageDTO.setMessageSubject(null);
        messageDTO.setMessageBody(MSG_BODY);

        // Try to translate it to a ConfirmationMessage.
        try {
            ConfirmationMessage message = (ConfirmationMessage) translator.assembleVO(messageDTO);
            fail("TranslationException should be thrown: ConfirmationMessageDTO has null fields");
        } catch (TranslationException e) {
            // Success
        }
    }

    /**
     * <p>
     * Tests that the assembleVO(Object dataTransferObject) method throws an
     * IllegalArgumentException when given a null argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithNullArg() throws Exception {
        try {
            translator.assembleVO(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the assembleVO(Object dataTransferObject) method throws an
     * IllegalArgumentException when the argument is not a
     * ConfirmationMessageDTO instance. In this test, a ConfirmationMessage
     * instance (the value object) is used as an argument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssembleVOWithNonConfirmationMessageDTOArg() throws Exception {
        ConfirmationMessage dto = new ConfirmationMessage(ADDRESS, UNLOCK_CODE, DATE_SENT, MSG_SUBJECT, MSG_BODY);
        try {
            translator.assembleVO(dto);
            fail("IllegalArgumentException should be thrown: argument is not a ConfirmationMessageDTO instance");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests the assembleDTO(Object valueObject) method with a valid argument.
     * The getter methods of the returned ConfirmationMessageDTO instance should
     * return values equal to that returned by the corresponding getter methods
     * of the given ConfirmationMessage argument.
     * </p>
     */
    public void testAssembleDTOWithValidArg() {
        // construct the ConfirmationMessage.
        ConfirmationMessage message = new ConfirmationMessage(ADDRESS, UNLOCK_CODE, DATE_SENT, MSG_SUBJECT, MSG_BODY);

        // Translate to a ConfirmationMessageDTO object
        ConfirmationMessageDTO messageDTO = (ConfirmationMessageDTO) translator.assembleDTO(message);

        // Check that the ConfirmationMessageDTO object fields are correct.
        assertEquals("The address is incorrect", ADDRESS, messageDTO.getAddress());
        assertEquals("The unlock code is incorrect", UNLOCK_CODE, messageDTO.getUnlockCode());
        assertEquals("The send date is incorrect", DATE_SENT, messageDTO.getDateSent());
        assertEquals("The message subject is incorrect", MSG_SUBJECT, messageDTO.getMessageSubject());
        assertEquals("The message body is incorrect", MSG_BODY, messageDTO.getMessageBody());
    }

    /**
     * <p>
     * Tests that the assembleDTO(Object valueObject) method throws an
     * IllegalArgumentException when given a null argument.
     * </p>
     */
    public void testAssembleDTOWithNullArg() {
        try {
            translator.assembleDTO(null);
            fail("IllegalArgumentException should be thrown: null argument");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that the assembleDTO(Object valueObject) method throws an
     * IllegalArgumentException when the argument is not a ConfirmationMessage
     * instance. In this test, a ConfirmationMessageDTO instance (the data
     * transfer object) is used as an argument.
     * </p>
     */
    public void testAssembleDTOWithNonConfirmationMessageArg() {
        ConfirmationMessageDTO vo = new ConfirmationMessageDTO();
        try {
            translator.assembleDTO(vo);
            fail("IllegalArgumentException should be thrown: argument is not a ConfirmationMessage instance");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * <p>
     * Tests that ConfirmationMessageTranslator implements the ObjectTranslator
     * interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("ConfirmationMessageTranslator should implement the ObjectTranslator interface",
                   translator instanceof ObjectTranslator);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(ConfirmationMessageTranslatorTest.class);
    }

}
