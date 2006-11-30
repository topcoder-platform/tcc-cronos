/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests.ejb;

import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Accuracy test cases for <code>ConfirmationMessageDTO</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class ConfirmationMessageDTOAccTests extends TestCase {

    /**
     * <p>
     * The ConfirmationMessageDTO instance to test.
     * </p>
     */
    private ConfirmationMessageDTO message = null;

    /**
     * <p>
     * Setup for each test case.
     * </p>
     */
    protected void setUp() {
        message = new ConfirmationMessageDTO();
    }

    /**
     * <p>
     * Accuracy test of the <code>setAddress()</code> method.
     * </p>
     */
    public void testSetAddress() {
        String address = "test@topcoder.com";
        message.setAddress(address);
        assertEquals("Not the expected address.", address, message.getAddress());
    }

    /**
     * <p>
     * Accuracy test of the <code>setMessageSubject()</code> method.
     * </p>
     */
    public void testSetMessageSubject() {
        String subject = "test subject";
        message.setMessageSubject(subject);
        assertEquals("Not the expected subject.", subject, message.getMessageSubject());
    }

    /**
     * <p>
     * Accuracy test of the <code>setMessageBody()</code> method.
     * </p>
     */
    public void testSetMessageBody() {
        String body = "test body";
        message.setMessageBody(body);
        assertEquals("Not the expected body.", body, message.getMessageBody());
    }

    /**
     * <p>
     * Accuracy test of the <code>setDateSent()</code> method.
     * </p>
     */
    public void testSetDateSent() {
        Date date = new Date();
        message.setDateSent(date);
        assertEquals("Not the expected address.", date, message.getDateSent());
    }

    /**
     * <p>
     * Accuracy test of the <code>setUnlockCode()</code> method.
     * </p>
     */
    public void testSetUnlockCode() {
        String code = "1234";
        message.setUnlockCode(code);
        assertEquals("Not the expected address.", code, message.getUnlockCode());
    }
}
