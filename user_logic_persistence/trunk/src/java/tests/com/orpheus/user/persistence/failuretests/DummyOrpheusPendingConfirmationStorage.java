/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.orpheus.user.persistence.failuretests;

import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.OrpheusPendingConfirmationStorage;
import com.orpheus.user.persistence.PersistenceException;
import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;

import com.topcoder.validation.emailconfirmation.ConfirmationMessage;


/**
 * Dummy OrpheusPendingConfirmationStorage class.
 *
 * @author crackme
 * @version 1.0
 */
public class DummyOrpheusPendingConfirmationStorage extends OrpheusPendingConfirmationStorage {
    /**
     * Creates a new DummyOrpheusPendingConfirmationStorage object.
     *
     * @param namespace DOCUMENT ME!
     *
     * @throws ObjectInstantiationException DOCUMENT ME!
     */
    protected DummyOrpheusPendingConfirmationStorage(String namespace)
        throws ObjectInstantiationException {
        super(namespace);
    }

    /**
     * DOCUMENT ME!
     *
     * @param message DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected void ejbStore(ConfirmationMessageDTO message)
        throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param address DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected ConfirmationMessageDTO ejbContains(String address)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param address DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected ConfirmationMessageDTO ejbRetrieve(String address)
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param address DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected void ejbDelete(String address) throws PersistenceException {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws PersistenceException DOCUMENT ME!
     */
    protected ConfirmationMessageDTO[] ejbGetMessages()
        throws PersistenceException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param arg0 DOCUMENT ME!
     */
    public void store(ConfirmationMessage arg0) {
        super.store(arg0);
    }

    /**
     * DOCUMENT ME!
     *
     * @param arg0 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ConfirmationMessage retrieve(String arg0) {
        return super.retrieve(arg0);
    }

    /**
     * DOCUMENT ME!
     *
     * @param arg0 DOCUMENT ME!
     */
    public void delete(String arg0) {
        super.delete(arg0);
    }

    /**
     * DOCUMENT ME!
     *
     * @param address DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean contains(String address) {
        return super.contains(address);
    }
}
