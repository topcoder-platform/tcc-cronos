/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests.impl;

import com.orpheus.user.persistence.impl.Admin;
import com.orpheus.user.persistence.impl.User;
import junit.framework.TestCase;


/**
 * Accuracy test cases for <code>Admin</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class AdminAccTests extends TestCase {

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     */
    public void testConstructor() {
        Admin admin = new Admin(1);

        assertEquals("Not the expected ID.", 1, admin.getId());
    }

    /**
     * <p>
     * Test if Admin extends the right class.
     * </p>
     */
    public void testInheritance() {
        Admin admin = new Admin(1);
        assertTrue("Should extends User class.", admin instanceof User);
    }
}
