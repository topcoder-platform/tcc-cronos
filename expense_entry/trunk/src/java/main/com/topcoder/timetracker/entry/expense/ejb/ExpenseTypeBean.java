/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;
import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryTypeDAO;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

/**
 * <p>
 * This is a Stateless <code>SessionBean</code> that is used to provided business services to handle the actual manager
 * requests for expense type operations. It simply delegates all operations to the <code>ExpenseEntryTypeDAO</code> it
 * obtained from the <code>ObjectFactory</code>.
 * </p>
 *
 * <p>
 * Note: The property value of jndi_context should be retrieved from the config manager via the default namespace. The
 * look up value of SpecificationNamespace and ExpenseTypeDAOKey should be in the environmental entry.
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
public class ExpenseTypeBean implements SessionBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 6172250612319230755L;

    /**
     * <p>
     * Represents the namespace used to retrieve the jndi_context property value.
     * </p>
     */
    private static final String NAMESPACE = ExpenseTypeBean.class.getName();

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
     * Represents the property name to retrieve the ExpenseTypeDAOKey value from the environmental entry.
     * </p>
     */
    private static final String DAO_KEY_PROPERTY = "ExpenseTypeDAOKey";

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
    private ExpenseEntryTypeDAO expenseTypeDAO = null;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public ExpenseTypeBean() {
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
        this.expenseTypeDAO = (ExpenseEntryTypeDAO) ExpenseEntryHelper.createDAO(NAMESPACE, JNDI_CONTEXT_PROPERTY,
                OF_NAMESPACE_PROPERTY, DAO_KEY_PROPERTY, ExpenseEntryTypeDAO.class);
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
     * Adds a new <code>ExpenseType</code> instance to the database.
     * </p>
     *
     * @param type the expense entry type to be added to the database.
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry type is added;
     *         <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if the argument is null, or if the creation date or modification date is not
     *         <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when adding the expense entry type.
     */
    public boolean addType(ExpenseType type) throws InsufficientDataException, PersistenceException {
        try {
            return expenseTypeDAO.addType(type);
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
     * Deletes an <code>ExpenseType</code> instance with the given ID from the database.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be deleted.
     *
     * @return <code>true</code> if the expense entry type exists in database and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry type.
     */
    public boolean deleteType(long typeId) throws PersistenceException {
        try {
            return expenseTypeDAO.deleteType(typeId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Deletes all <code>ExpenseType</code> instances in the database.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry types.
     */
    public void deleteAllTypes() throws PersistenceException {
        try {
            expenseTypeDAO.deleteAllTypes();
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseType</code> instance to the database.
     * </p>
     *
     * @param type the expense entry type to be updated in the database.
     *
     * @return <code>true</code> if the expense entry type exists in database and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws IllegalArgumentException if <code>type</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when updating the expense entry type.
     */
    public boolean updateType(ExpenseType type) throws InsufficientDataException, PersistenceException {
        try {
            return expenseTypeDAO.updateType(type);
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
     * Retrieves an <code>ExpenseType</code> instance with the given ID from the database.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be retrieved.
     *
     * @return an <code>ExpenseType</code> instance with the given ID; or <code>null</code> if such instance cannot be
     *         found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry type; or the value in persistence
     *         is invalid.
     */
    public ExpenseType retrieveType(long typeId) throws PersistenceException {
        try {
            return expenseTypeDAO.retrieveType(typeId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseType</code> instances from the database.
     * </p>
     *
     * @return an array of <code>ExpenseType</code> instances retrieved from the database, or an empty array if there
     *         is no record.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry types; or the value in database
     *         is invalid.
     */
    public ExpenseType[] retrieveAllTypes() throws PersistenceException {
        try {
            return expenseTypeDAO.retrieveAllTypes();
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Performs a search for expense types matching a given criteria. The criteria is abstracted using the <code>
     * Criteria</code> interface. The <code>Criteria</code> implementations cover the basic SQL filtering abilities
     * (=, like, between, or, and, not). The result of the search is an array with the matched expense types. It is
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
    public ExpenseType[] searchEntries(Criteria criteria) throws PersistenceException {
        try {
            return expenseTypeDAO.searchEntries(criteria);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }
}
