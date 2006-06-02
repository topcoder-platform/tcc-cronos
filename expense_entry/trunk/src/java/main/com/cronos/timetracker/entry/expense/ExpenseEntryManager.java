/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryPersistence;
import com.cronos.timetracker.entry.expense.persistence.PersistenceException;
import com.cronos.timetracker.entry.expense.search.Criteria;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * Defines functionality to manage expense entries in persistence. The features include adding, updating, deleting and
 * retrieving a single expense entry or all expense entries in the persistence. When adding the expense entry, if the
 * ID is unspecified (-1), a random ID is generated. The creation date and modification date are modified and
 * persisted automatically according to the current time. The manager uses <code>ExpenseEntryPersistence</code>
 * implementation to do actual operations on persistence. It can only be created from configuration.
 * </p>
 *
 * <p>
 * Usage note: if a expense entry has new expense entry type and/or expense entry status, the type and status should be
 * added first to the persistence. After adding the type and/or status, the new expense entry can be added correctly.
 * </p>
 *
 * <p>
 * Changes in 1.1: Four new methods for doing bulk operations on sets of expense entries have been added. These method
 * can work in atomic mode (a failure on one entry causes the entire operation to fail) or non-atomic (a failure in
 * one entry doesn't affect the other and the user has a way to know which ones failed). There is also a search method
 * that provides the capability to search expense entries at the database level and return the ones that match.
 * </p>
 *
 * <p>
 * Bug fix for TT-1974. Bug description is "The ExpenseEntryManager generates the ID for the single entry  if it is
 * added by the addEntry() method. But it doesn't generate the IDs for the multiple entries added by the  batch
 * operation, i.e. by the addEntries() method.". Solution is: generates the IDs for the entries whose original  id is
 * -1 in adding batch operation (in addEntries() method).
 * </p>
 *
 * @author adic, TCSDEVELOPER
 * @author DanLazar, visualage
 * @author Xuchen
 * @version 1.1
 *
 * @since 1.0
 */
public class ExpenseEntryManager {
    /** Represents the property to load entry persistence class name. */
    private static final String ENTRY_PERSISTENCE_CLASS_PROPERTY = "entry_persistence_class";

    /** Represents the property to load connection producer name. */
    private static final String CONNECTION_PRODUCER_NAME_PROPERTY = "connection_producer_name";

    /** Represents the property to load namespace to create DB connection factory. */
    private static final String CONNECTION_FACTORY_NAMESPACE = "connection_factory_namespace";

    /** Represents the persistence instance used to persist and load <code>ExpenseEntry</code> instances. */
    private ExpenseEntryPersistence entryPersistence = null;

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryManager</code> class. The persistence class name is loaded from the
     * configuration property 'entry_persistence_class' under the given namespace. It is mandatory and must be an
     * implenentation of <code>ExpenseEntryPersistence</code>. Optional property 'connection_producer_name' represents
     * the connection producer name in the persistence class to create the default database connection from DB
     * connection factory. If it is <code>null</code>, there is no default database connection. The namespace to
     * create DB connection factory is defined in property 'connection_factory_namespace'. It is mandatory, and cannot
     * be empty string.
     * </p>
     *
     * @param namespace the namespace to load the persistence class name and other configuration.
     *
     * @throws ConfigurationException if error occurs when loading configuration; or mandatory property is missing; or
     *         property is invalid.
     */
    public ExpenseEntryManager(String namespace) throws ConfigurationException {
        ExpenseEntryHelper.validateNotNullOrEmpty(namespace, "namespace");

        ConfigManager configManager = ConfigManager.getInstance();
        String className;
        String connProducerName;
        String dbNamespace;

        try {
            className = configManager.getString(namespace, ENTRY_PERSISTENCE_CLASS_PROPERTY);
            connProducerName = configManager.getString(namespace, CONNECTION_PRODUCER_NAME_PROPERTY);
            dbNamespace = configManager.getString(namespace, CONNECTION_FACTORY_NAMESPACE);
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("Fail to load configuration value.", e);
        }

        entryPersistence = (ExpenseEntryPersistence) ExpenseEntryHelper.createInstance(className, connProducerName,
                dbNamespace, ExpenseEntryPersistence.class);
    }

    /**
     * <p>
     * Adds a new <code>ExpenseEntry</code> instance to the persistence. If the ID of the <code>ExpenseEntry</code> is
     * -1, a random ID is generated. Otherwise, the user-specified ID is used. All fields in the instance must be set
     * except creation date and modification date, which must be <code>null</code> and be set as the current time.
     * </p>
     *
     * @param entry the expense entry to be added to the persistence.
     *
     * @return <code>true</code> if the ID does not exist in persistence and the expense entry is added;
     *         <code>false</code> otherwise.
     *
     * @throws PersistenceException if error occurs when adding the expense entry.
     * @throws InsufficientDataException If fields other than creation date and modification date are not set.
     */
    public boolean addEntry(ExpenseEntry entry) throws PersistenceException, InsufficientDataException {
        ExpenseEntryHelper.validateNotNull(entry, "entry");
        ExpenseEntryHelper.validateNewInfo(entry, "entry");
        ExpenseEntryHelper.validateExpenseEntryData(entry);

        if (entry.getId() == -1) {
            entry.setId(ExpenseEntryHelper.generateId());
        }

        return entryPersistence.addEntry(entry);
    }

    /**
     * <p>
     * Deletes an <code>ExpenseEntry</code> instance with the given ID from the persistence.
     * </p>
     *
     * @param entryId the ID of the expense entry to be deleted.
     *
     * @return <code>true</code> if the expense entry exists in persistence and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry.
     */
    public boolean deleteEntry(int entryId) throws PersistenceException {
        return entryPersistence.deleteEntry(entryId);
    }

    /**
     * <p>
     * Deletes all <code>ExpenseEntry</code> instances in the persistence.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entries.
     */
    public void deleteAllEntries() throws PersistenceException {
        entryPersistence.deleteAllEntries();
    }

    /**
     * <p>
     * Updates an <code>ExpenseEntry</code> instance to the persistence. All fields in the instance must be set except
     * creation date and modification date. The modification date automatically becomes the current time.
     * </p>
     *
     * @param entry the expense entry to be updated in the persistence.
     *
     * @return <code>true</code> if the expense entry exists in persistence and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when updating the expense entry.
     * @throws InsufficientDataException If fields other than creation date and modification date are not set.
     */
    public boolean updateEntry(ExpenseEntry entry) throws PersistenceException, InsufficientDataException {
        ExpenseEntryHelper.validateNotNull(entry, "entry");
        ExpenseEntryHelper.validateExpenseEntryData(entry);

        return entryPersistence.updateEntry(entry);
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseEntry</code> instance with the given ID from the persistence. The related
     * <code>ExpenseEntryType</code> and <code>ExpenseEntryStatus</code> instances are also retrieved.
     * </p>
     *
     * @param entryId the ID of the expense entry to be retrieved.
     *
     * @return an <code>ExpenseEntry</code> instance with the given ID; or <code>null</code> if such instance cannot be
     *         found in the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry; or the value in persistence is
     *         invalid.
     */
    public ExpenseEntry retrieveEntry(int entryId) throws PersistenceException {
        return entryPersistence.retrieveEntry(entryId);
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseEntry</code> instances from the persistence. The related
     * <code>ExpenseEntryType</code> and <code>ExpenseEntryStatus</code> instances are also retrieved.
     * </p>
     *
     * @return a list of <code>ExpenseEntry</code> instances retrieved from the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entries; or the value in persistence is
     *         invalid.
     */
    public List retrieveAllEntries() throws PersistenceException {
        return entryPersistence.retrieveAllEntries();
    }

    /**
     * <p>
     * Gets the persistence used to persist and load <code>ExpenseEntry</code> instances.
     * </p>
     *
     * @return the persistence used to persist and load <code>ExpenseEntry</code> instances.
     */
    public ExpenseEntryPersistence getEntryPersistence() {
        return entryPersistence;
    }

    /**
     * <p>
     * Adds a set of entries to the database. If the ID of the <code>ExpenseEntry</code> is -1, a random ID is
     * generated. Otherwise, the user-specified ID is used. All fields in the instance must be set except creation
     * date and modification date, which must be <code>null</code> and be set as the current time.
     * </p>
     *
     * <p>
     * If the addition is atomic then it means that entire retrieval will fail if a single expense entry addition
     * fails.
     * </p>
     *
     * <p>
     * If the addition is non-atomic then it means each expense entry is added individually. If it fails, that won't
     * affect the others. A list with the failed entries is returned to the user (empty if no error occurs). If the
     * exception is related to acquiring an SQL connection or something like that, it is obvious that all entries will
     * fail so the exception should be thrown.
     * </p>
     *
     * @param entries the entries to add.
     * @param isAtomic whether the operation should be atomic or not.
     *
     * @return the entries that failed to be added in non atomic mode.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception. (such as SQL exception)
     * @throws InsufficientDataException If fields other than creation date and modification date are not set(in atomic
     *         mode only).
     * @throws IllegalArgumentException if the creation date or modification date is not <code>null</code> or the
     *         status or type of the expense entry has -1 as the ID(in atomic mode only); or if the array is
     *         <code>null</code>, empty or has <code>null</code> element.
     *
     * @since 1.1
     */
    public ExpenseEntry[] addEntries(ExpenseEntry[] entries, boolean isAtomic)
        throws PersistenceException, InsufficientDataException {
        // argument validation
        if (entries == null) {
            throw new IllegalArgumentException("entries should not be null.");
        }

        if (entries.length == 0) {
            throw new IllegalArgumentException("entries should not be empty.");
        }

        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == null) {
                throw new IllegalArgumentException("entries should not contain a null element.");
            }
        }

        List errors = new ArrayList();
        List correct = new ArrayList();

        for (int i = 0; i < entries.length; i++) {
            try {
                ExpenseEntryHelper.validateNewInfo(entries[i], "entry");
                ExpenseEntryHelper.validateExpenseEntryData(entries[i]);
            } catch (IllegalArgumentException e) {
                // If the addition is non-atomic, just ignore this exception, else throw it directly
                if (isAtomic) {
                    throw e;
                }

                errors.add(entries[i]);

                continue;
            } catch (InsufficientDataException e) {
                // If the addition is non-atomic, just ignore this exception, else throw it directly
                if (isAtomic) {
                    throw e;
                }

                errors.add(entries[i]);

                continue;
            }

            correct.add(entries[i]);
        }

        ExpenseEntry[] ret = new ExpenseEntry[0];

        if (!correct.isEmpty()) {
            // Set the id value for all valid entries if their original id is -1.
            ExpenseEntry[] correctEntriesArray = (ExpenseEntry[]) correct.toArray(new ExpenseEntry[correct.size()]);

            for (int i = 0; i < correctEntriesArray.length; i++) {
                if (correctEntriesArray[i].getId() == -1) {
                    correctEntriesArray[i].setId(ExpenseEntryHelper.generateId());
                }
            }

            ret = this.entryPersistence.addEntries(correctEntriesArray, isAtomic);
        }

        if (isAtomic) {
            return (ret == null) ? null : ret;
        }

        errors.addAll(Arrays.asList(ret));

        return (ExpenseEntry[]) errors.toArray(new ExpenseEntry[errors.size()]);
    }

    /**
     * <p>
     * Deletes a set of entries from the database with the given IDs from the persistence.
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
     * @param entryIds the ids of the entries to delete.
     * @param isAtomic whether the operation should be atomic or not.
     *
     * @return the entries that failed to be deleted in non atomic mode.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     * @throws IllegalArgumentException if the array is <code>null</code> or empty.
     */
    public int[] deleteEntries(int[] entryIds, boolean isAtomic)
        throws PersistenceException {
        // argument validation
        if (entryIds == null) {
            throw new IllegalArgumentException("entryIds should not be null.");
        }

        if (entryIds.length == 0) {
            throw new IllegalArgumentException("entryIds should not be empty.");
        }

        // process the deletion.
        return this.entryPersistence.deleteEntries(entryIds, isAtomic);
    }

    /**
     * <p>
     * Updates a set of entries in the database.
     * </p>
     *
     * <p>
     * If the update is atomic then it means that entire retrieval will fail if a single expense entry update fails.
     * </p>
     *
     * <p>
     * If the update is non-atomic then it means each expense entry is updated individually. If it fails, that won't
     * affect the others. A list with the failed entries is returned to the user (empty if no error occurs).
     * </p>
     *
     * @param entries the ids of the entries to update.
     * @param isAtomic whether the operation should be atomic or not.
     *
     * @return the entries that failed to be updated in non atomic mode.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception. (such as SQL exception)
     * @throws InsufficientDataException If fields other than creation date and modification date are not set(in atomic
     *         mode only).
     * @throws IllegalArgumentException if the array is <code>null</code>, empty or has <code>null</code> element.
     */
    public ExpenseEntry[] updateEntries(ExpenseEntry[] entries, boolean isAtomic)
        throws PersistenceException, InsufficientDataException {
        // argument validation
        if (entries == null) {
            throw new IllegalArgumentException("entries should not be null.");
        }

        if (entries.length == 0) {
            throw new IllegalArgumentException("entries should not be empty.");
        }

        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == null) {
                throw new IllegalArgumentException("entries should not contain a null element.");
            }
        }

        List errors = new ArrayList();
        List correct = new ArrayList();

        for (int i = 0; i < entries.length; i++) {
            try {
                ExpenseEntryHelper.validateExpenseEntryData(entries[i]);
            } catch (IllegalArgumentException e) {
                // If the addition is non-atomic, just ignore this exception, else throw it directly
                if (isAtomic) {
                    throw e;
                }

                errors.add(entries[i]);

                continue;
            } catch (InsufficientDataException e) {
                // If the addition is non-atomic, just ignore this exception, else throw it directly
                if (isAtomic) {
                    throw e;
                }

                errors.add(entries[i]);

                continue;
            }

            correct.add(entries[i]);
        }

        ExpenseEntry[] ret = new ExpenseEntry[0];

        if (!correct.isEmpty()) {
            ret = this.entryPersistence.updateEntries((ExpenseEntry[]) correct.toArray(
                new ExpenseEntry[correct.size()]), isAtomic);
        }

        if (isAtomic) {
            return (ret == null) ? null : ret;
        }

        errors.addAll(Arrays.asList(ret));

        return (ExpenseEntry[]) errors.toArray(new ExpenseEntry[errors.size()]);
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
     * won't affect the others. The potentially partial list of results will be returned. If any error occurs or if
     * the billable column of an entry is not 0 or 1 PersistenceException will be thrown in atomic mode but it is
     * ignored in non-atomic node and the next entry is processed.
     * </p>
     *
     * @param entryIds the ids of the entries to retrieve.
     * @param isAtomic whether the operation should be atomic or not.
     *
     * @return the entries that were retrieved in both modes.
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     * @throws IllegalArgumentException if the array is <code>null</code> or empty.
     */
    public ExpenseEntry[] retrieveEntries(int[] entryIds, boolean isAtomic)
        throws PersistenceException {
        // argument validation
        if (entryIds == null) {
            throw new IllegalArgumentException("entryIds should not be null.");
        }

        if (entryIds.length == 0) {
            throw new IllegalArgumentException("entryIds should not be empty.");
        }

        // process the deletion.
        return this.entryPersistence.retrieveEntries(entryIds, isAtomic);
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
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     * @throws IllegalArgumentException if the argument is <code>null</code>
     */
    public ExpenseEntry[] searchEntries(Criteria criteria)
        throws PersistenceException {
        // argument validation
        if (criteria == null) {
            throw new IllegalArgumentException("criteria should not be null.");
        }

        // process the deletion.
        return this.entryPersistence.searchEntries(criteria);
    }
}
