/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryDAO;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


/**
 * <p>
 * This is a Stateless <code>SessionBean</code> handles the actual manager requests for basic expense entry operations,
 * as defined by the corresponding local interface <code>BasicExpenseEntryLocal</code>. It simply delegates all
 * operations to the <code>ExpenseEntryDAO</code> it obtained from the <code>ObjectFactory</code>.
 * </p>
 *
 * <p>
 * Note: The property value of jndi_context should be retrieved from the config manager via the default namespace. The
 * look up value of SpecificationNamespace and ExpenseEntryDAOKey should be in the environmental entry.
 * </p>
 *
 * <p>
 * Transactions for this bean are handled by the EJB Container. It is expected that the transaction level declared in
 * the deployment descriptor for this class will be REQUIRED.
 * </p>
 *
 * <p>
 * The DAO will be instantiated in ejbCreate() and it will be available during use of the bean.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This object is mutable and thread-safe, as the container handles this.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class BasicExpenseEntryBean implements SessionBean {
    /**
     * <p>
     * Represents the namespace used to retrieve the jndi_context property value.
     * </p>
     */
    private static final String NAMESPACE = BasicExpenseEntryBean.class.getName();

    /**
     * <p>
     * Represents the property name to retrieve the jndi_context value from the confit manager.
     * </p>
     */
    private static final String JNDI_CONTEXT_PROPERTY = "jndi_context";

    /**
     * <p>
     * Represents the property name to retrieve the SpecificationNamespace value from the environmental entry.
     * </p>
     */
    private static final String OF_NAMESPACE_PROPERTY = "SpecificationNamespace";

    /**
     * <p>
     * Represents the property name to retrieve the ExpenseEntryDAOKey value from the environmental entry.
     * </p>
     */
    private static final String DAO_KEY_PROPERTY = "ExpenseEntryDAOKey";

    /**
     * <p>
     * Represents the session context of this bean. It is used to indicate to the container if the bean wants to
     * rollback a transaction. This would usually occur if an application exception occurs.
     * </p>
     *
     * <p>
     * Set in the setSessionContext() method by the container right after instantiation, as per EJB specifications. The
     * container guarantees to set it right after instantiation, so it will never be null after being set.
     * </p>
     */
    private SessionContext sessionContext = null;

    /**
     * <p>
     * Represents the data access object for performing the persistence operations.
     * </p>
     *
     * <p>
     * It is initialized in the ejbCreate method, and not changed afterwards. There will be one instantiation per one
     * session bean lifetime. It will not be null after being set. Should an error occur while instantiating, the
     * container will discard this bean and adapt with another one. Either way, as far as the user is concerned, it
     * will not be null when the business methods are called.
     * </p>
     */
    private ExpenseEntryDAO expenseEntryDAO = null;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public BasicExpenseEntryBean() {
    }

    /**
     * <p>
     * Creates the bean. The dao field is instantiated in this method from the object factory with the specified
     * SpecificationNamespace and DAOKey value from the environmental entry. The jndi context name for the JNDI
     * Utility is defined in the given namespace.
     * </p>
     *
     * @throws CreateException If any error occurs while instantiating.
     */
    public void ejbCreate() throws CreateException {
        this.expenseEntryDAO = (ExpenseEntryDAO) ExpenseEntryHelper.createDAO(NAMESPACE, JNDI_CONTEXT_PROPERTY,
                OF_NAMESPACE_PROPERTY, DAO_KEY_PROPERTY, ExpenseEntryDAO.class);
    }

    /**
     * <p>
     * Removes the bean. This is an empty implementation.
     * </p>
     */
    public void ejbRemove() {
    }

    /**
     * <p>
     * Activates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbActivate() {
    }

    /**
     * <p>
     * Passivates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbPassivate() {
    }

    /**
     * <p>
     * Sets the session context.
     * </p>
     *
     * @param ctx session context.
     */
    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
    }

    /**
     * <p>
     * Adds a new <code>ExpenseEntry</code> instance to the database. Audit actions will be done if the auditFlag is
     * specified.
     * </p>
     *
     * @param entry the expense entry to be added to the database.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry is added;
     *         <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if the argument is null.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public boolean addEntry(ExpenseEntry entry, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        try {
            return expenseEntryDAO.addEntry(entry, auditFlag);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (InsufficientDataException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseEntry</code> instance to the database. Audit actions will be done if the auditFlag is
     * specified.
     * </p>
     *
     * @param entry the expense entry to be updated in the database.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the expense entry exists in database and is updated; <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if <code>entry</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public boolean updateEntry(ExpenseEntry entry, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        try {
            return expenseEntryDAO.updateEntry(entry, auditFlag);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (InsufficientDataException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Deletes an <code>ExpenseEntry</code> instance with the given ID from the database. Audit actions will be done if
     * the auditFlag is specified.
     * </p>
     *
     * @param entryId the ID of the expense entry to be deleted.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the expense entry exists in database and is deleted; <code>false</code> otherwise.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public boolean deleteEntry(long entryId, boolean auditFlag) throws PersistenceException {
        try {
            return expenseEntryDAO.deleteEntry(entryId, auditFlag);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseEntry</code> instance with the given ID from the database. The related
     * <code>Invoice</code>, <code>ExpenseType</code> and <code>ExpenseStatus</code> instances are also retrieved.
     * </p>
     *
     * @param entryId the ID of the expense entry to be retrieved.
     *
     * @return an <code>ExpenseEntry</code> instance with the given ID; or <code>null</code> if such instance cannot be
     *         found in the database.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public ExpenseEntry retrieveEntry(long entryId) throws PersistenceException {
        try {
            return expenseEntryDAO.retrieveEntry(entryId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Deletes all <code>ExpenseEntry</code> instances in the database. Audit actions will be done if the auditFlag is
     * specified.
     * </p>
     *
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public void deleteAllEntries(boolean auditFlag) throws PersistenceException {
        try {
            expenseEntryDAO.deleteAllEntries(auditFlag);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseEntry</code> instances from the database. The related <code>Invoice</code>,
     * <code>ExpenseType</code> and <code>ExpenseStatus</code> instances are also retrieved.
     * </p>
     *
     * @return an array of <code>ExpenseEntry</code> instances retrieved from the database, or an empty array if there
     *         is no record.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public ExpenseEntry[] retrieveAllEntries() throws PersistenceException {
        try {
            return expenseEntryDAO.retrieveAllEntries();
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Performs a search for expense entries matching a given criteria. The criteria is abstracted using the <code>
     * Criteria</code> interface. The <code>Criteria</code> implementations cover the basic SQL filtering abilities
     * (=, like, between, or, and, not). The result of the search is an array with the matched expense entries. It is
     * empty if no matches found (but it can't be <code>null</code> or contain <code>null</code>) elements.
     * </p>
     *
     * @param criteria the criteria to be used in the search.
     *
     * @return the results of the search (can be empty if no matches found).
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public ExpenseEntry[] searchEntries(Criteria criteria) throws PersistenceException {
        try {
            return expenseEntryDAO.searchEntries(criteria);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Gets the session context attached to this instance. Will not be null when called by business methods.
     * </p>
     *
     * @return the session context attached to this instance.
     */
    protected SessionContext getSessionContext() {
        return this.sessionContext;
    }

    /**
     * <p>
     * Gets the ExpenseEntryDAO created in this instance. Will not be null when called by business methods.
     * </p>
     *
     * @return the ExpenseEntryDAO created in this instance
     */
    protected ExpenseEntryDAO getDAO() {
        return this.expenseEntryDAO;
    }
}
