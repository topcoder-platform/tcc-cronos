/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.util.Date;

import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;

/**
 * <p>
 * A mock OrpheusPendingConfirmationStorage subclass which is used by the
 * OrpheusPendingConfirmationStorageTest test case to instantiate and test the
 * abstract OrpheusPendingConfirmationStorage class. It contains operations that
 * allows the test case to check if the ejbXXX() methods have been called by the
 * OrpheusPendingConfirmationStorage class when performing certain persistence
 * operations.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class MockOrpheusPendingConfirmationStorage extends OrpheusPendingConfirmationStorage {

    /**
     * <p>
     * Indicates whether the ejbStore(ConfirmationMessageDTO) method was called.
     * </p>
     */
    private boolean storeMethodWasCalled = false;

    /**
     * <p>
     * Indicates whether the ejbContains(String) method was called.
     * </p>
     */
    private boolean containsMethodWasCalled = false;

    /**
     * <p>
     * Indicates whether the ejbRetrieve(String) method was called.
     * </p>
     */
    private boolean retrieveMethodWasCalled = false;

    /**
     * <p>
     * Indicates whether the ejbDelete(String) method was called.
     * </p>
     */
    private boolean deleteMethodWasCalled = false;

    /**
     * <p>
     * Indicates whether the ejbGetMessages() method was called.
     * </p>
     */
    private boolean getMessagesMethodWasCalled = false;

    /**
     * <p>
     * The ConfirmationMessageDTO instance to return from the
     * ejbContains(String), ejbRetrieve(String) and ejbGetMessages() methods.
     * </p>
     */
    private final ConfirmationMessageDTO message = new ConfirmationMessageDTO();

    /**
     * <p>
     * Creates a new MockOrpheusPendingConfirmationStorage instance. This
     * constructor simply invokes the base class's constructor.
     * </p>
     *
     * @param namespace the configuration namespace
     * @throws ObjectInstantiationException if instantiation failed
     */
    public MockOrpheusPendingConfirmationStorage(String namespace) throws ObjectInstantiationException {
        super(namespace);

        // Create the mock ConfirmationMessageDTO instance.
        message.setAddress("message address");
        message.setUnlockCode("ACBXYZ");
        message.setDateSent(new Date());
        message.setMessageSubject("Subject");
        message.setMessageBody("Message body");
    }

    /**
     * <p>
     * Called by the
     * OrpheusPendingConfirmationStorage.store(ConfirmationMessage) method to
     * store the confirmation message. It simply sets an internal flag
     * indicating that it was called. The value of this flag can be checked by a
     * test case using the storeMethodWasCalled() method.
     * </p>
     *
     * @param message the message to store
     */
    protected void ejbStore(ConfirmationMessageDTO message) {
        storeMethodWasCalled = true;
    }

    /**
     * <p>
     * Called by the OrpheusPendingConfirmationStorage.contains(String) method
     * to check if a confirmation message is in the persistent store. It simply
     * sets an internal flag indicating that it was called. The value of this
     * flag can be checked by a test case using the containsMethodWasCalled()
     * method.
     * </p>
     *
     * @param address the address of the message to check for
     * @return a non-null mock ConfirmationMessageDTO instance
     */
    protected ConfirmationMessageDTO ejbContains(String address) {
        containsMethodWasCalled = true;
        message.setAddress(address);
        return message;
    }

    /**
     * <p>
     * Called by the OrpheusPendingConfirmationStorage.retrieve(String) method
     * to retrieve a confirmation message. It simply sets an internal flag
     * indicating that it was called. The value of this flag can be checked by a
     * test case using the retrieveMethodWasCalled() method.
     * </p>
     *
     * @param address the address of the message to retrieve
     * @return a non-null mock ConfirmationMessageDTO instance
     */
    protected ConfirmationMessageDTO ejbRetrieve(String address) {
        retrieveMethodWasCalled = true;
        message.setAddress(address);
        return message;
    }

    /**
     * <p>
     * Called by the OrpheusPendingConfirmationStorage.delete(String) method to
     * delete a confirmation message. It simply sets an internal flag indicating
     * that it was called. The value of this flag can be checked by a test case
     * using the deleteMethodWasCalled() method.
     * </p>
     *
     * @param address the address of the message to delete
     */
    protected void ejbDelete(String address) {
        deleteMethodWasCalled = true;
    }

    /**
     * <p>
     * Called by the
     * OrpheusPendingConfirmationStorage.store(ConfirmationMessage) method to
     * retrieve all the confirmation messages. It simply sets an internal flag
     * indicating that it was called. The value of this flag can be checked by a
     * test case using the getMessagesMethodWasCalled() method.
     * </p>
     *
     * @return an array containing a non-null mock ConfirmationMessageDTO
     *         instance
     */
    protected ConfirmationMessageDTO[] ejbGetMessages() {
        getMessagesMethodWasCalled = true;
        return new ConfirmationMessageDTO[] {
            message
        };
    }

    /**
     * <p>
     * Returns whether the ejbStore(ConfirmationMessageDTO) method was called.
     * </p>
     *
     * @return true if the ejbStore(ConfirmationMessageDTO) method was called;
     *         false otherwise
     */
    public boolean storeMethodWasCalled() {
        return storeMethodWasCalled;
    }

    /**
     * <p>
     * Returns whether the ejbContains(String) method was called.
     * </p>
     *
     * @return true if the ejbContains(String) method was called; false
     *         otherwise
     */
    public boolean containsMethodWasCalled() {
        return containsMethodWasCalled;
    }

    /**
     * <p>
     * Returns whether the ejbRetrieve(String) method was called.
     * </p>
     *
     * @return true if the ejbRetrieve(String) method was called; false
     *         otherwise
     */
    public boolean retrieveMethodWasCalled() {
        return retrieveMethodWasCalled;
    }

    /**
     * <p>
     * Returns whether the ejbDelete(String) method was called.
     * </p>
     *
     * @return true if the ejbDelete(String) method was called; false otherwise
     */
    public boolean deleteMethodWasCalled() {
        return deleteMethodWasCalled;
    }

    /**
     * <p>
     * Returns whether the ejbGetMessages() method was called.
     * </p>
     *
     * @return true if the ejbGetMessages() method was called; false otherwise
     */
    public boolean getMessagesMethodWasCalled() {
        return getMessagesMethodWasCalled;
    }

}
