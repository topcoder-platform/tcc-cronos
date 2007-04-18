/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.delegate;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.NamingException;

import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseEntryLocal;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseEntryLocalHome;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.util.config.ConfigManagerException;


/**
 * <p>
 * Implements the <code>ExpenseEntryManager</code> interface to provide management of the expense entries objects
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
public class ExpenseEntryManagerLocalDelegate implements ExpenseEntryManager {
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
    private final ExpenseEntryLocal localEJB;

    /**
     * <p>
     * Constructs the <code>ExpenseEntryManagerLocalDelegate</code> with default namespace: the full name of this
     * class, and creates the <code>ExpenseEntryLocal</code> according to it.
     * </p>
     *
     * @throws ConfigurationException if the configuration is incorrect.
     */
    public ExpenseEntryManagerLocalDelegate() throws ConfigurationException {
        this(ExpenseEntryManagerLocalDelegate.class.getName());
    }

    /**
     * <p>
     * Constructs the <code>ExpenseEntryManagerLocalDelegate</code> with the given namespace, and creates the
     * <code>ExpenseEntryLocal</code> according to it.
     * </p>
     *
     * @param namespace configuration namespace.
     *
     * @throws IllegalArgumentException if the namespace is null or empty.
     * @throws ConfigurationException if the configuration is incorrect.
     */
    public ExpenseEntryManagerLocalDelegate(String namespace) throws ConfigurationException {
        ExpenseEntryHelper.validateString(namespace, "namespace");

        String contextName = ExpenseEntryHelper.getStringPropertyValue(namespace, JNDI_CONTEXT_PROPERTY, false);
        String jndiReference = ExpenseEntryHelper.getStringPropertyValue(namespace, JNDI_REFERENCE_PROPERTY, true);

        try {
            //if context name is not specified, uses default context
            Context context = (contextName == null) ? JNDIUtils.getDefaultContext() : JNDIUtils.getContext(contextName);

            ExpenseEntryLocalHome home = (ExpenseEntryLocalHome) context.lookup(jndiReference);
            this.localEJB = home.create();
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("JNDIUtil is not configured properly", e);
        } catch (NamingException e) {
            throw new ConfigurationException("failed to create ExpenseEntryLocal via JNDIUtil", e);
        } catch (CreateException e) {
            throw new ConfigurationException("failed to obtain ExpenseEntryLocal", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("Fails to create the ExpenseEntryLocalHome.", e);
        }
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
        return localEJB.addEntry(entry, auditFlag);
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
        return localEJB.updateEntry(entry, auditFlag);
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
        return localEJB.deleteEntry(entryId, auditFlag);
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
        return localEJB.retrieveEntry(entryId);
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
        localEJB.deleteAllEntries(auditFlag);
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
        return localEJB.retrieveAllEntries();
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
        return localEJB.searchEntries(criteria);
    }

    /**
     * <p>
     * Adds atomically set of entries to the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * @param entries the entries to add.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set for any expense entry.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public void addEntries(ExpenseEntry[] entries, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        localEJB.addEntries(entries, auditFlag);
    }

    /**
     * <p>
     * Adds a set of entries to the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * If the addition is atomic then it means that entire addition will fail if a single expense entry addition fails.
     * </p>
     *
     * <p>
     * If the addition is non-atomic then it means each expense entry is added individually. If it fails, that won't
     * affect the others. A list with the failed entries is returned to the user (empty if no error occurs).
     * </p>
     *
     * @param entries the entries to add.
     * @param isAtomic whether the operation should be atomic or not.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return the entries that failed to be added in non atomic mode (null in atomic mode).
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set for any expense entry.(in atomic mode only)
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    public ExpenseEntry[] addEntries(ExpenseEntry[] entries, boolean isAtomic, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        return localEJB.addEntries(entries, isAtomic, auditFlag);
    }

    /**
     * <p>
     * Updates atomically a set of entries in the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * @param entries the entries to update.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public void updateEntries(ExpenseEntry[] entries, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        localEJB.updateEntries(entries, auditFlag);
    }

    /**
     * <p>
     * Updates a set of entries in the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * If the update is atomic then it means that entire update will fail if a single expense entry update fails.
     * </p>
     *
     * <p>
     * If the update is non-atomic then it means each expense entry is updated individually. If it fails, that won't
     * affect the others. A list with the failed entries is returned to the user (empty if no error occurs).
     * </p>
     *
     * @param entries the ids of the entries to update
     * @param isAtomic whether the operation should be atomic or not
     * @param auditFlag audit flag which specifies if the user want to audit the action
     *
     * @return the entries that failed to be updated in non atomic mode (null in atomic mode)
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.(in atomic mode only)
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    public ExpenseEntry[] updateEntries(ExpenseEntry[] entries, boolean isAtomic, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        return localEJB.updateEntries(entries, isAtomic, auditFlag);
    }

    /**
     * <p>
     * Deletes atomically a set of entries from the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * @param entryIds the ids of the entries to delete.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception)
     */
    public void deleteEntries(long[] entryIds, boolean auditFlag) throws PersistenceException {
        localEJB.deleteEntries(entryIds, auditFlag);
    }

    /**
     * <p>
     * Deletes a set of entries from the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * If the deletion is atomic then it means that entire retrieval will fail if a single expense entry deletion
     * fails.
     * </p>
     *
     * <p>
     * If the deletion is non-atomic then it means each expense entry is deleted individually. If it fails, that won't
     * affect the others. A list with the failed ids is returned to the user (empty if no error occurs).
     * </p>
     *
     * @param entryIds the ids of the entries to delete
     * @param isAtomic whether the operation should be atomic or not
     * @param auditFlag audit flag which specifies if the user want to audit the action
     *
     * @return the entries that failed to be deleted in non atomic mode (null in atomic mode)
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    public long[] deleteEntries(long[] entryIds, boolean isAtomic, boolean auditFlag) throws PersistenceException {
        return localEJB.deleteEntries(entryIds, isAtomic, auditFlag);
    }

    /**
     * <p>
     * Retrieves atomically a set of entries with given ids from the database.
     * </p>
     *
     * @param entryIds the ids of the entries to retrieve.
     *
     * @return the entries that were retrieved in both modes.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public ExpenseEntry[] retrieveEntries(long[] entryIds) throws PersistenceException {
        return localEJB.retrieveEntries(entryIds);
    }

    /**
     * <p>
     * Retrieves a set of entries with given ids from the database.
     * </p>
     *
     * <p>
     * If the retrieval is atomic then it means that entire retrieval will fail if a single expense entry retrieval
     * fails.
     * </p>
     *
     * <p>
     * If the retrieval is non-atomic then it means each expense entry is retrieved individually. If it fails that
     * won't affect the others. The potentially partial list of results will be returned.
     * </p>
     *
     * @param entryIds the ids of the entries to retrieve.
     * @param isAtomic whether the operation should be atomic or not.
     *
     * @return the entries that were retrieved in either mode, or empty if none found.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    public ExpenseEntry[] retrieveEntries(long[] entryIds, boolean isAtomic) throws PersistenceException {
        return localEJB.retrieveEntries(entryIds, isAtomic);
    }
}
