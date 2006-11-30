/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests.impl;

import com.orpheus.user.persistence.impl.Player;
import com.orpheus.user.persistence.impl.User;
import junit.framework.TestCase;


/**
 * Accuracy test cases for <code>Player</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class PlayerAccTests extends TestCase {

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     */
    public void testConstructor() {
        Player player = new Player(1);

        assertEquals("Not the expected ID.", 1, player.getId());
    }

    /**
     * <p>
     * Test if Player extends the right class.
     * </p>
     */
    public void testInheritance() {
        Player player = new Player(1);
        assertTrue("Should extends User class.", player instanceof User);
    }

    /**
     * <p>
     * Accuracy test of the <code>setPaymentPref()</code> method
     * </p>
     */
    public void testSetPaymentPref() {
        Player player = new Player(1);

        player.setPaymentPref("test");
        assertEquals("Not the expected payment pref.", "test", player.getPaymentPref());
    }
}
