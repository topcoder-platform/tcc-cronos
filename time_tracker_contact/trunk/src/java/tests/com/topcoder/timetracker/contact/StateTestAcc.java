/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>This test case contains accuracy tests for <code>State</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class StateTestAcc extends BaseTestCase {
    /**
     * <p>
     * Test constructor.
     * </p>
     */
    public void testCtor() {
        assertNotNull(new State());
    }

    /**
     * <p>
     * Test methods <code>setName()</code> and <code>getName()</code>.
     * </p>
     */
    public void testName() {
        State state = new State();
        state.setChanged(false);
        state.setName("new Name");
        assertEquals("new Name", state.getName());
        assertTrue(state.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setAbbreviation()</code> and <code>getAbbreviation()</code>.
     * </p>
     */
    public void testAbbreviation() {
        State state = new State();
        state.setChanged(false);
        state.setAbbreviation("ZJ");
        assertEquals("ZJ", state.getAbbreviation());
        assertTrue(state.isChanged());
    }
}
