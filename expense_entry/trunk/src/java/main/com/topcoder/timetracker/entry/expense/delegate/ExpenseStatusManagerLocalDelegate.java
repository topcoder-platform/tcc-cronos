/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.delegate;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.NamingException;

import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseStatusManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseStatusLocal;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseStatusLocalHome;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.util.config.ConfigManagerException;


/**
 * <p>
 * Implements the <code>ExpenseStatusManager</code> interface to provide management of the expense statuses objects
 * through the use of a local session EJB. It will obtain the handle to the bean's local interface and will simply
 * delegate all calls to it. It implements all methods.
 * </p>
 *
 * <p>
 * <strong>Thread Safety</strong> This class is immutable and thread-safe. It is not expected that the data beans will
 * be used by more than one thread at a time.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseStatusManagerLocalDelegate implements ExpenseStatusManager {
    /**
     * <p>
     * Represents the property name to retrieve the jndi_context value from the config manager.
     * </p>
     */
    private static final String JNDI_CONTEXT_PROPERTY = "jndi_context";

    /**
     * <p>
     * Represents the property name to retrieve the jndi_reference value from the config manager.
     * </p>
     */
    private static final String JNDI_REFERENCE_PROPERTY = "jndi_reference";

    /**
     * Represents the local session ejb instance used for all calls. Created in the constructor, will not be null, and
     * will not change.
     */
    private final ExpenseStatusLocal localEJB;

    /**
     * <p>
     * Constructs the <code>ExpenseStatusManagerLocalDelegate</code> with default namespace: the full name of this
     * class, and creates the <code>ExpenseStatusLocal</code> according to it.
     * </p>
     *
     * @throws ConfigurationException if the configuration is incorrect.
     */
    public ExpenseStatusManagerLocalDelegate() throws ConfigurationException {
        this(ExpenseStatusManagerLocalDelegate.class.getName());
    }

    /**
     * <p>
     * Constructs the <code>ExpenseStatusManagerLocalDelegate</code> with the given namespace, and creates the
     * <code>ExpenseStatusLocal</code> according to it.
     * </p>
     *
     * @param namespace configuration namespace.
     *
     * @throws IllegalArgumentException if the namespace is null or empty.
     * @throws ConfigurationException if the configuration is incorrect.
     */
    public ExpenseStatusManagerLocalDelegate(String namespace) throws ConfigurationException {
        ExpenseEntryHelper.validateString(namespace, "namespace");

        String contextName = ExpenseEntryHelper.getStringPropertyValue(namespace, JNDI_CONTEXT_PROPERTY, false);
        String jndiReference = ExpenseEntryHelper.getStringPropertyValue(namespace, JNDI_REFERENCE_PROPERTY, true);

        try {
            //if context name is not specified, uses default context
            Context context = (contextName == null) ? JNDIUtils.getDefaultContext() : JNDIUtils.getContext(contextName);

            ExpenseStatusLocalHome home = (ExpenseStatusLocalHome) context.lookup(jndiReference);
            this.localEJB = home.create();
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("JNDIUtil is not configured properly", e);
        } catch (NamingException e) {
            throw new ConfigurationException("failed to create ExpenseStatusLocal via JNDIUtil", e);
        } catch (CreateException e) {
            throw new ConfigurationException("failed to obtain ExpenseStatusLocal", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("Fails to create the ExpenseStatusLocalHome.", e);
        }
    }

    /**
     * <p>
     * Adds a new <code>ExpenseStatus</code> instance to the database.
     * </p>
     *
     * @param status the status that will be added to the database
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry status is added;
     *         <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if the argument is null, or if the creation date or modification date is not
     *         <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when adding the expense entry status.
     */
    public boolean addStatus(ExpenseStatus status) throws InsufficientDataException, PersistenceException {
        return localEJB.addStatus(status);
    }

    /**
     * <p>
     * Deletes an <code>ExpenseStatus</code> instance with the given ID from the database.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be deleted.
     *
     * @return <code>true</code> if the expense entry status exists in database and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry status.
     */
    public boolean deleteStatus(long statusId) throws PersistenceException {
        return localEJB.deleteStatus(statusId);
    }

    /**
     * <p>
     * Deletes all <code>ExpenseStatus</code> instances in the database.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry statuses.
     */
    public void deleteAllStatuses() throws PersistenceException {
        localEJB.deleteAllStatuses();
    }

    /**
     * <p>
     * Updates an <code>ExpenseStatus</code> instance to the database.
     * </p>
     *
     * @param status the expense entry status to be updated in the database.
     *
     * @return <code>true</code> if the expense entry status exists in database and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws IllegalArgumentException if <code>status</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when updating the expense entry status.
     */
    public boolean updateStatus(ExpenseStatus status) throws InsufficientDataException, PersistenceException {
        return localEJB.updateStatus(status);
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseStatus</code> instance with the given ID from the database.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be retrieved.
     *
     * @return an <code>ExpenseStatus</code> instance with the given ID; or <code>null</code> if such instance cannot
     *         be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry status; or the value in database
     *         is invalid.
     */
    public ExpenseStatus retrieveStatus(long statusId) throws PersistenceException {
        return localEJB.retrieveStatus(statusId);
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseStatus</code> instances from the database.
     * </p>
     *
     * @return an array of <code>ExpenseStatus</code> instances retrieved from the database, or an empty array if there
     *         is no record.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry statuses; or the value in
     *         database is invalid.
     */
    public ExpenseStatus[] retrieveAllStatuses() throws PersistenceException {
        return localEJB.retrieveAllStatuses();
    }

    /**
     * <p>
     * Performs a search for expense status matching a given criteria. The criteria is abstracted using the <code>
     * Criteria</code> interface. The <code>Criteria</code> implementations cover the basic SQL filtering abilities
     * (=, like, between, or, and, not). The result of the search is an array with the matched expense status. It is
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
    public ExpenseStatus[] searchEntries(Criteria criteria) throws PersistenceException {
        return localEJB.searchEntries(criteria);
    }
}
