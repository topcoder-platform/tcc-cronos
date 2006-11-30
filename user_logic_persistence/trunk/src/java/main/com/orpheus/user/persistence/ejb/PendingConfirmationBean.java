/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.ejb;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.orpheus.user.persistence.DAOFactory;
import com.orpheus.user.persistence.ObjectInstantiationException;
import com.orpheus.user.persistence.PendingConfirmationDAO;
import com.orpheus.user.persistence.PersistenceException;

/**
 * <p>
 * A stateless session bean that receives requests from client to perform
 * confirmation message persistence operations. It delegates all client requests
 * to the {@link PendingConfirmationDAO} class, which is responsible for the
 * actual persistence operations.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br> This session bean is stateless (immutable) and
 * thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see PendingConfirmationHomeLocal
 * @see PendingConfirmationHomeRemote
 * @see PendingConfirmationLocal
 * @see PendingConfirmationRemote
 */
public class PendingConfirmationBean implements SessionBean {

    /**
     * <p>
     * The context of the session bean. This field is used to rollback failed
     * transactions.
     * </p>
     * <p>
     * This field is mutable and is set in the setSessionContext(SessionContext)
     * method by the application server container.
     * </p>
     */
    private SessionContext context;

    /**
     * <p>
     * Creates a new <code>PendingConfirmationBean</code> session bean.
     * </p>
     */
    public PendingConfirmationBean() {
        // Empty constructor.
    }

    /**
     * <p>
     * Stores the given confirmation message in the persistent store. If the
     * persistent store already contains a confirmation message with the same
     * address as that of the given message, a
     * <code>DuplicateEntryException</code> is thrown.
     * </p>
     *
     * @param message the data transfer object (DTO) representing the
     *        confirmation message to store
     * @throws IllegalArgumentException if the confirmation message is
     *         <code>null</code>
     * @throws DuplicateEntryException if the persistent store contains a
     *         confirmation message with the same address as that of the given
     *         message
     * @throws PersistenceException if storing the confirmation message in the
     *         persistent store fails
     */
    public void store(ConfirmationMessageDTO message) throws PersistenceException {
        try {
            PendingConfirmationDAO dao = getDAO("Failed to store pending confirmation message");
            dao.store(message);
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Checks if a confirmation message with the specified address exists in the
     * persistent store. It it does, a data transfer object (DTO) representing
     * the confirmation message is returned. Otherwise, <code>null</code> is
     * returned.
     * </p>
     *
     * @param address the address of the confirmation message to check for
     * @return a <code>ConfirmationMessageDTO</code> representing the
     *         confirmation message with the specified address, or
     *         <code>null</code> if no such confirmation message exists in the
     *         persistent store
     * @throws IllegalArgumentException if the specified address is
     *         <code>null</code> or a blank string
     * @throws PersistenceException if checking if the confirmation message is
     *         in the persistent store fails
     */
    public ConfirmationMessageDTO contains(String address) throws PersistenceException {
        try {
            PendingConfirmationDAO dao = getDAO("Unable to check if pending confirmation message is in "
                                                + "the persistent store");
            return dao.contains(address);
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Retrieves the confirmation message with the specified address from the
     * persistent store. If no such confirmation message could be found, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     *
     * @param address the address of the confirmation message to retrieve
     * @return a <code>ConfirmationMessageDTO</code> representing the
     *         confirmation message with the specified address
     * @throws IllegalArgumentException if the specified address is
     *         <code>null</code> or a blank string
     * @throws EntryNotFoundException if no confirmation message with the
     *         specified address exists in the persistent store
     * @throws PersistenceException if retrieving the confirmation message from
     *         the persistent store fails
     * @see #getMessages()
     */
    public ConfirmationMessageDTO retrieve(String address) throws PersistenceException {
        try {
            PendingConfirmationDAO dao = getDAO("Failed to retrieve pending confirmation message");
            return dao.retrieve(address);
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Deletes the confirmation message with the specified address from the
     * persistent store. If no such confirmation message could be found, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     *
     * @param address the address of the confirmation message to delete
     * @throws IllegalArgumentException if the specified address is
     *         <code>null</code> or a blank string
     * @throws EntryNotFoundException if no confirmation message with the
     *         specified address exists in the persistent store
     * @throws PersistenceException if deleting the confirmation message from
     *         the persistent store fails
     */
    public void delete(String address) throws PersistenceException {
        try {
            PendingConfirmationDAO dao = getDAO("Failed to delete pending confirmation message");
            dao.delete(address);
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all the confirmation messages from the persistent store as an
     * array. If no confirmation messages exist in the persistent store, an
     * empty array is returned.
     * </p>
     *
     * @return a <code>ConfirmationMessageDTO[]</code> array containing all
     *         the confirmation messages in the persistent store, or an empty
     *         array if there are no confirmation messages
     * @throws PersistenceException if retrieving the all confirmation messages
     *         from the persistent store fails
     * @see #retrieve(String)
     */
    public ConfirmationMessageDTO[] getMessages() throws PersistenceException {
        try {
            PendingConfirmationDAO dao = getDAO("Failed to retrieve pending confirmation message");
            return dao.getMessages();
        } catch (PersistenceException e) {
            // Tell container to rollback the transaction.
            context.setRollbackOnly();

            throw e;
        }
    }

    /**
     * <p>
     * Called by the application server container to create the session bean.
     * This is method does nothing.
     * </p>
     */
    public void ejbCreate() {
        // Does nothing.
    }

    /**
     * <p>
     * Called by the application server container before it ends the life of the
     * session object. This happens as a result of a client's invoking a remove
     * operation, or when the container decides to terminate the session object
     * after a timeout. This method does nothing.
     * </p>
     */
    public void ejbRemove() {
        // Does nothing.
    }

    /**
     * <p>
     * Called by the application server container when the session bean is
     * activated from its passive state. Since this session bean is stateless,
     * no activation is required. This method does nothing.
     * </p>
     */
    public void ejbActivate() {
        // Does nothing.
    }

    /**
     * <p>
     * Called by the application server container before the session bean enters
     * the passive state. Since this session bean is stateless, no action needs
     * to be taken. This method does nothing.
     * </p>
     */
    public void ejbPassivate() {
        // Does nothing.
    }

    /**
     * <p>
     * Set the context associated with the session bean. The application server
     * container calls this method after the session bean is created.
     * </p>
     *
     * @param context the session context
     */
    public void setSessionContext(SessionContext context) {
        this.context = context;
    }

    /**
     * <p>
     * Gets the <code>PendingConfirmationDAO</code> instance from the
     * {@link DAOFactory}, which is then used to perform confirmation message
     * persistence operations. If retrieving the DAO instance fails, a
     * <code>PersistenceException</code> is thrown with the given error
     * message.
     * </p>
     *
     * @param errorMsg the error message for the
     *        <code>PersistenceException</code> if retrieving the
     *        <code>PendingConfirmationDAO</code> instance fails
     * @return the <code>PendingConfirmationDAO</code> instance
     * @throws PersistenceException if retrieving the
     *         <code>PendingConfirmationDAO</code> instance from the
     *         <code>DAOFactory</code> fails
     */
    private PendingConfirmationDAO getDAO(String errorMsg) throws PersistenceException {
        try {
            return DAOFactory.getPendingConfirmationDAO();
        } catch (ObjectInstantiationException e) {
            throw new PersistenceException(errorMsg, e);
        }
    }

}
