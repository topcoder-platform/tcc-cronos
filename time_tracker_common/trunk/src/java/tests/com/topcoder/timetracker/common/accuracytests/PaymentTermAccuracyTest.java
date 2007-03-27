/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * Accuracy tests for the class: PaymentTerm and its abstract super class: TimeTrackerBean.
 *
 * @author kinfkong
 * @version 3.1
 */
public class PaymentTermAccuracyTest extends TestCase {

    /**
     * Represents for the instance of PaymentTerm for accuracy tests.
     */
    private PaymentTerm paymentTerm = null;

    public void setUp() {
        // creates the instance
        paymentTerm = new PaymentTerm();
    }

    /**
     * Checks if the instance of PaymentTerm can be created.
     */
    public void testPaymentTerm() {
        assertNotNull("The instance of PaymentTerm cannot be crated.", new PaymentTerm());
    }

    /**
     * Checks if the method: getTerm() works properly.
     *
     */
    public void testGetTerm() {
        // set the term
        paymentTerm.setTerm(1);

        // check it
        assertEquals("The method getTerm() does not work properly.", 1, paymentTerm.getTerm());
    }

    /**
     * Checks if the method: setTerm(int) works properly.
     *
     */
    public void testSetTerm_Changed() {
        // set the term
        paymentTerm.setTerm(1);

        // check if the changed field is set
        assertTrue("The changed field must set to be true", paymentTerm.isChanged());

        // check accuracy
        assertEquals("The method: setTerm(int) does not work properly.", 1, paymentTerm.getTerm());
    }

    /**
     * Checks if the method: getDescription() works properly.
     *
     */
    public void testGetDescription() {
        // set the description
        paymentTerm.setDescription("Hello world!");

        // check if the description is properly returned
        assertEquals("The method: getDescription() does not work properly.", "Hello world!", paymentTerm
            .getDescription());
    }

    /**
     * Checks if the method: setDescription() works properly.
     *
     */
    public void testSetDescription() {
        // set the description
        paymentTerm.setDescription("Hello world!");

        // check if the description is properly set
        assertEquals("The method: setDescription() does not work properly.", "Hello world!", paymentTerm
            .getDescription());
    }

    /**
     * Checks if the method: setDescription(String) works properly.
     *
     */
    public void testSetDescription_Changed() {
        // set the description
        paymentTerm.setDescription("Changed");

        // check if it is changed
        assertTrue("The changed field should set to be true.", paymentTerm.isChanged());
    }

    /**
     * Checks if the method: isActive() works properly.
     *
     */
    public void testIsActive_True() {
        // set active
        paymentTerm.setActive(true);

        // check if it is active
        assertTrue("The paymentTerm should be active.", paymentTerm.isActive());
    }

    /**
     * Checks if the method: isActive() works properly.
     *
     */
    public void testIsActive_False() {
        // set active
        paymentTerm.setActive(false);

        // check if it is active
        assertFalse("The paymentTerm should be not active.", paymentTerm.isActive());
    }

    /**
     * Checks if the method: setActive() works properly.
     *
     */
    public void testSetActive_True() {
        // set active
        paymentTerm.setActive(true);

        // check if it is active
        assertTrue("The paymentTerm should be active.", paymentTerm.isActive());
    }

    /**
     * Checks if the method: setActive() works properly.
     *
     */
    public void testSetActive_False() {
        // set active
        paymentTerm.setActive(false);

        // check if it is active
        assertFalse("The paymentTerm should be not active.", paymentTerm.isActive());
    }

    /**
     * Tests the method: hashCode().
     *
     */
    public void testHashCode() {
        // set the id
        paymentTerm.setId(1);

        // check accuracy
        assertEquals("The method hashCode does not work properly.", 1, paymentTerm.hashCode());

        // set the id
        paymentTerm.setId(123456789);

        // check accuracy
        assertEquals("The method hashCode does not work properly.", 123456789, paymentTerm.hashCode());
    }

    /**
     * Tests the constructor: TimeTrackerBean.
     *
     */
    public void testTimeTrackerBean() {
        assertTrue("The payment should be instance of TimeTrackerBean.",
            paymentTerm instanceof TimeTrackerBean);
    }

    /**
     * Tests the method: getCreationDate().
     *
     */
    public void testGetCreationDate_Null() {
        // set the creation date
        paymentTerm.setCreationDate(null);

        // check if the creation date is null
        assertNull("The creation date should be null.", paymentTerm.getCreationDate());
    }

    /**
     * Tests the method: getCreationDate().
     *
     */
    public void testGetCreationDate() {
        // set the creation Date
        Date date = new Date();
        paymentTerm.setCreationDate(date);

        // check if the date is properly set
        assertEquals("The method: getCreationDate does not work properly.", date, paymentTerm
            .getCreationDate());
    }

    /**
     * Tests the method: setCreationDate().
     *
     */
    public void testSetCreationDate() {
        // set the creation date
        Date date = new Date();

        paymentTerm.setCreationDate(date);

        // check if the date is properly set
        assertEquals("The method: setCreationDate does not work properly.", date, paymentTerm
            .getCreationDate());
    }

    /**
     * Tests the method: getModificationDate().
     *
     */
    public void testGetModificationDate() {
        // set the modification date
        Date date = new Date();

        paymentTerm.setModificationDate(date);

        // check if the date is properly returned
        assertEquals("The method: getModificationDate does not work properly.", date, paymentTerm
            .getModificationDate());
    }

    /**
     * Tests the method: setModificationDate().
     *
     */
    public void testSetModificationDate() {
        // set the modification date
        Date date = new Date();

        paymentTerm.setModificationDate(date);

        // check if the date is properly set
        assertEquals("The method: setModificationDate does not work properly.", date, paymentTerm
            .getModificationDate());
    }

    /**
     * Tests the method: getCreationUser().
     *
     */
    public void testGetCreationUser() {
        // set the creation user
        String user = new String("user");

        // set the user
        paymentTerm.setCreationUser(user);

        // check if the user is properly returned
        assertEquals("The method: getCreationUser does not work properly.", user, paymentTerm
            .getCreationUser());
    }

    /**
     * Tests the method: setCreationUser().
     *
     */
    public void testSetCreationUser() {
        // set the creation user
        String user = new String("user");

        // set the user
        paymentTerm.setCreationUser(user);

        // check if the user is properly set
        assertEquals("The method: setCreationUser does not work properly.", user, paymentTerm
            .getCreationUser());
    }

    /**
     * Tests the method: getModificationUser().
     *
     */
    public void testGetModificationUser() {
        // set the Modification user
        String user = new String("user");

        // set the user
        paymentTerm.setModificationUser(user);

        // check if the user is properly returned
        assertEquals("The method: getModificationUser does not work properly.", user, paymentTerm
            .getModificationUser());
    }

    /**
     * Tests the method: setModificationUser().
     *
     */
    public void testSetModificationUser() {
        // set the Modification user
        String user = new String("user");

        // set the user
        paymentTerm.setModificationUser(user);

        // check if the user is properly set
        assertEquals("The method: setModificationUser does not work properly.", user, paymentTerm
            .getModificationUser());
    }

    /**
     * Tests the method: getId().
     *
     *
     */
    public void testGetId() {
        // set the id
        paymentTerm.setId(2);

        // check if the id is properly returned
        assertEquals("The method: getId does not work properly.", 2, paymentTerm.getId());
    }

    /**
     * Tests the method: setId().
     *
     */
    public void testSetId() {
        // set the id
        paymentTerm.setId(2);

        // check if the id is properly set
        assertEquals("The method: getId does not work properly.", 2, paymentTerm.getId());
    }

    /**
     * Tests the method: isChanged().
     *
     */
    public void testIsChanged_True() {
        // set true
        paymentTerm.setChanged(true);

        // check if the changed field is true
        assertEquals("The method: isChanged does not work properly.", true, paymentTerm.isChanged());
    }

    /**
     * Tests the method: isChanged().
     *
     */
    public void testIsChanged_False() {
        // set true
        paymentTerm.setChanged(false);

        // check if the changed field is false
        assertEquals("The method: isChanged does not work properly.", false, paymentTerm.isChanged());

    }

    /**
     * Tests the method: isChanged().
     *
     */
    public void testSetChanged_True() {
        // set true
        paymentTerm.setChanged(true);

        // check if the changed field is true
        assertEquals("The method: setChanged does not work properly.", true, paymentTerm.isChanged());
    }

    /**
     * Tests the method: isChanged().
     *
     */
    public void testSetChanged_False() {
        // set true
        paymentTerm.setChanged(false);

        // check if the changed field is false
        assertEquals("The method: setChanged does not work properly.", false, paymentTerm.isChanged());

    }

    /**
     * Tests the method: equals(Object).
     *
     */
    public void testEqualsObject_Null() {
        paymentTerm.setId(1);

        // check if they are equal
        assertFalse("Null object should not be equal.", paymentTerm.equals(null));
    }

    /**
     * Tests the method: equals(Object).
     *
     */
    public void testEqualsObject_NotTimeTrackerBean() {
        paymentTerm.setId(1);

        // check if they are equal
        assertFalse("Not type of TimeTrackerBean should not be equal", paymentTerm.equals("no"));
    }

    /**
     * Tests the method: equals(Objec).
     *
     */
    public void testEqualsObject_NotSameId() {
        PaymentTerm paymentTerm2 = new PaymentTerm();
        paymentTerm2.setId(2);
        paymentTerm.setId(1);
        // check if they are equal
        assertFalse("Not equal if their ids are not equal.", paymentTerm.equals(paymentTerm2));
    }

    /**
     * Tests the method: equals(Object).
     *
     */
    public void testEqualsObject_Equal() {
        PaymentTerm paymentTerm2 = new PaymentTerm();
        paymentTerm2.setId(2);
        paymentTerm.setId(2);
        // check if they are equal
        assertTrue("Not equal if their ids are not equal.", paymentTerm.equals(paymentTerm2));
    }

}
