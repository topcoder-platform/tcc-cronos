/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryPersistence;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import java.util.List;


/**
 * <p>
 * Defines functionality to manage expense entries in persistence. The features include adding, updating, deleting
 * and retrieving a single expense entry or all expense entries in the persistence. When adding the expense entry,
 * if the ID is unspecified (-1), a random ID is generated. The creation date and modification date are modified and
 * persisted automatically according to the current time. The manager uses <code>ExpenseEntryPersistence</code>
 * implementation to do actual operations on persistence. It can only be created from configuration.
 * </p>
 *
 * <p>
 * Usage note: if a expense entry has new expense entry type and/or expense entry status, the type and status should
 * be added first to the persistence. After adding the type and/or status, the new expense entry can be added
 * correctly.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
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
     * Creates a new instance of <code>ExpenseEntryManager</code> class. The persistence class name is loaded from
     * the configuration property 'entry_persistence_class' under the given namespace. It is mandatory and must be
     * an implenentation of <code>ExpenseEntryPersistence</code>. Optional property 'connection_producer_name'
     * represents the connection producer name in the persistence class to create the default database connection
     * from DB connection factory. If it is <code>null</code>, there is no default database connection. The
     * namespace to create DB connection factory is defined in property 'connection_factory_namespace'. It is
     * mandatory, and cannot be empty string.
     * </p>
     *
     * @param namespace the namespace to load the persistence class name and other configuration.
     *
     * @throws NullPointerException if <code>namespace</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>namespace</code> is empty string.
     * @throws ConfigurationException if error occurs when loading configuration; or mandatory property is missing;
     *         or property is invalid.
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
     * Adds a new <code>ExpenseEntry</code> instance to the persistence. If the ID of the <code>ExpenseEntry</code>
     * is -1, a random ID is generated. Otherwise, the user-specified ID is used. All fields in the instance must be
     * set except creation date and modification date, which must be <code>null</code> and be set as the current
     * time.
     * </p>
     *
     * @param entry the expense entry to be added to the persistence.
     *
     * @return <code>true</code> if the ID does not exist in persistence and the expense entry is added;
     *         <code>false</code> otherwise.
     *
     * @throws NullPointerException if <code>entry</code> is <code>null</code>.
     * @throws IllegalArgumentException if the creation date or modification date is not <code>null</code>; or the
     *         status or type of the expense entry has -1 as the ID.
     * @throws InsufficientDataException If fields other than creation date and modification date are not set.
     * @throws PersistenceException if error occurs when adding the expense entry.
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
     * Updates an <code>ExpenseEntry</code> instance to the persistence. All fields in the instance must be set
     * except creation date and modification date. The modification date automatically becomes the current time.
     * </p>
     *
     * @param entry the expense entry to be updated in the persistence.
     *
     * @return <code>true</code> if the expense entry exists in persistence and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws NullPointerException if <code>entry</code> is <code>null</code>.
     * @throws IllegalArgumentException if the status or type of the expense entry has -1 as the ID.
     * @throws InsufficientDataException If fields other than creation date and modification date are not set.
     * @throws PersistenceException if error occurs when updating the expense entry.
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
     * @return an <code>ExpenseEntry</code> instance with the given ID; or <code>null</code> if such instance cannot
     *         be found in the persistence.
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
     * @throws PersistenceException if error occurs when retrieving the expense entries; or the value in persistence
     *         is invalid.
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
}






