/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.common.Address;
import com.cronos.timetracker.common.State;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>Address</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestAddressAccuracy extends TestCase {

    /**
     * Represents the Address instance for test.
     */
    private static Address target = new Address();

    /**
     * Test constructor.
     *
     */
    public void testAddress() {
        assertNotNull("The Address instance should be created.", target);
    }

    /**
     * Test method getLine1.
     *
     */
    public void testGetLine1() {
        assertNull("The default value should be null.", target.getLine1());
    }

    /**
     * Test method setLine1.
     *
     */
    public void testSetLine1() {
        target.setLine1("line1");

        assertEquals("Equal is expected.", "line1", target.getLine1());
        assertTrue("True is expected.", target.isChanged());
    }

    /**
     * Test method setLine1.
     *
     */
    public void testSetLine1_2() {
        target.setLine1("line1");

        assertEquals("Equal is expected.", "line1", target.getLine1());

        target.setChanged(false);

        target.setLine1("line");
        assertTrue("true is expected.", target.isChanged());
    }

    /**
     * Test method getLine2.
     *
     */
    public void testGetLine2() {
        assertNull("The default value for line2 should be null.", target.getLine2());
    }

    /**
     * Test method setLine2.
     *
     */
    public void testSetLine2() {
        target.setLine2("line2");
        assertEquals("Equal is expected.", "line2", target.getLine2());
    }

    /**
     * Test method setLine2.
     *
     */
    public void testSetLine2_2() {
        target.setLine2("line2");
        assertEquals("Equal is expected.", "line2", target.getLine2());

        target.setChanged(false);

        target.setLine2("stree");
        assertTrue("true is expected.", target.isChanged());
    }

    /**
     * Test getCity.
     *
     */
    public void testGetCity() {
        assertNull("The default value should be null.", target.getCity());
    }

    /**
     * Test method setCity.
     *
     */
    public void testSetCity() {
        target.setCity("topcoder");

        assertEquals("Equal is expected.", "topcoder", target.getCity());
    }

    /**
     * Test method setCity.
     *
     */
    public void testSetCity_2() {
        target.setCity("topcoder");

        assertEquals("Equal is expected.", "topcoder", target.getCity());

        target.setChanged(false);

        target.setCity("city");

        assertTrue("True is expected.", target.isChanged());
    }

    /**
     * Test method getState.
     *
     */
    public void testGetState() {
        assertNull("Should be null.", target.getState());
    }

    /**
     * Test method setState.
     *
     */
    public void testSetState() {
        State state = new State();
        target.setState(state);

        assertEquals("Equal is expeected.", state, target.getState());
    }

    /**
     * Test method setState.
     *
     */
    public void testSetState_2() {
        State state = new State();
        target.setState(state);

        assertEquals("Equal is expeected.", state, target.getState());

        state = new State();
        state.setId(100);
        state.setAbbreviation("test");

        target.setChanged(false);

        target.setState(state);

        assertTrue("True is expected.", target.isChanged());
    }

    /**
     * Test method getZipCode.
     *
     */
    public void testGetZipCode() {
        assertNull("Null is expected.", target.getZipCode());
    }

    /**
     * Test method setZipCode.
     *
     */
    public void testSetZipCode() {
        target.setZipCode("zipcoder");

        assertEquals("Equal is expected.", "zipcoder", target.getZipCode());
    }

    /**
     * Test method setZipCode.
     *
     */
    public void testSetZipCode_1() {
        target.setZipCode("zipcoder");

        assertEquals("Equal is expected.", "zipcoder", target.getZipCode());

        target.setChanged(false);

        target.setZipCode("zp");

        assertTrue("True is expected.", target.isChanged());
    }
}
