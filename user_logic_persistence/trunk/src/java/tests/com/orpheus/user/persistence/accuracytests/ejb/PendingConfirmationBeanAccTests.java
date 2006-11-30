/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests.ejb;

import com.orpheus.user.persistence.ejb.PendingConfirmationBean;
import junit.framework.TestCase;

import javax.ejb.SessionBean;

/**
 * Accuracy test cases for <code>PendingConfirmationBean</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class PendingConfirmationBeanAccTests extends TestCase {

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     */
    public void testConstructor() {
        PendingConfirmationBean bean = new PendingConfirmationBean();
        // no error expected
    }

    /**
     * <p>
     * Test if the bean implements the right interface.
     * </p>
     */
    public void testInterface() {
        PendingConfirmationBean bean = new PendingConfirmationBean();
        assertTrue("Should implement the SessionBean interface.", bean instanceof SessionBean);
    }
}
