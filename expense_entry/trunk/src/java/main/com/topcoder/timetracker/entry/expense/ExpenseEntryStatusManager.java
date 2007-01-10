/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryStatusPersistence;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.timetracker.entry.expense.search.Criteria;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import java.util.List;


/**
 * <p>
 * Defines functionality to manage expense entry statuses in persistence. The features include adding, updating,
 * deleting and retrieving a single expense entry status or all expense entry statuses in the persistence. When adding
 * the expense entry status, if the ID is unspecified (-1), a random ID is generated. The creation date and
 * modification date are modified and persisted automatically according to the current time. The manager uses
 * <code>ExpenseEntryStatusPersistence</code> implementation to do actual operations on persistence. It can only be
 * created from configuration.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public class ExpenseEntryStatusManager {
    /** Represents the property to load entry status persistence class name. */
    private static final String ENTRY_STATUS_PERSISTENCE_CLASS_PROPERTY = "entry_status_persistence_class";

    /** Represents the property to load connection producer name. */
    private static final String CONNECTION_PRODUCER_NAME_PROPERTY = "connection_producer_name";

    /** Represents the property to load namespace to create DB connection factory. */
    private static final String CONNECTION_FACTORY_NAMESPACE = "connection_factory_namespace";

    /** Represents the persistence instance used to persist and load <code>ExpenseEntryStatus</code> instances. */
    private ExpenseEntryStatusPersistence statusPersistence = null;

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryStatusManager</code> class. The persistence class name is loaded
     * from the configuration property 'entry_status_persistence_class' under the given namespace. It is mandatory and
     * must be an implenentation of <code>ExpenseEntryStatusPersistence</code>. Optional property
     * 'connection_producer_name' represents the connection producer name in the persistence class to create the
     * default database connection from DB connection factory. If it is <code>null</code>, there is no default
     * database connection. The namespace to create DB connection factory is defined in property
     * 'connection_factory_namespace'. It is mandatory, and cannot be empty string.
     * </p>
     *
     * @param namespace the namespace to load the persistence class name and other configuration.
     *
     * @throws NullPointerException if <code>namespace</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>namespace</code> is empty string.
     * @throws ConfigurationException if error occurs when loading configuration; or mandatory property is missing; or
     *         property is invalid.
     */
    public ExpenseEntryStatusManager(String namespace) throws ConfigurationException {
        ExpenseEntryHelper.validateNotNullOrEmpty(namespace, "namespace");

        ConfigManager configManager = ConfigManager.getInstance();
        String className;
        String connProducerName;
        String dbNamespace;

        try {
            className = configManager.getString(namespace, ENTRY_STATUS_PERSISTENCE_CLASS_PROPERTY);
            connProducerName = configManager.getString(namespace, CONNECTION_PRODUCER_NAME_PROPERTY);
            dbNamespace = configManager.getString(namespace, CONNECTION_FACTORY_NAMESPACE);
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("Fail to load configuration value.", e);
        }

        statusPersistence = (ExpenseEntryStatusPersistence) ExpenseEntryHelper.createInstance(className,
                connProducerName, dbNamespace, ExpenseEntryStatusPersistence.class);
    }

    /**
     * <p>
     * Adds a new <code>ExpenseEntryStatus</code> instance to the persistence. If the ID of the
     * <code>ExpenseEntryStatus</code> is -1, a random ID is generated. Otherwise, the user-specified ID is used. All
     * fields in the instance must be set except creation date and modification date, which must be <code>null</code>
     * and be set as the current time.
     * </p>
     *
     * @param status the expense entry status to be added to the persistence.
     *
     * @return <code>true</code> if the ID does not exist in persistence and the expense entry status is added;
     *         <code>false</code> otherwise.
     *
     * @throws NullPointerException if <code>status</code> is <code>null</code>.
     * @throws IllegalArgumentException if the creation date or modification date is not <code>null</code>.
     * @throws InsufficientDataException If fields other than creation date and modification date are not set.
     * @throws PersistenceException if error occurs when adding the expense entry status.
     */
    public boolean addStatus(ExpenseEntryStatus status) throws PersistenceException, InsufficientDataException {
        ExpenseEntryHelper.validateNotNull(status, "status");
        ExpenseEntryHelper.validateNewInfo(status, "status");
        ExpenseEntryHelper.validateExpenseEntryStatusData(status);

        if (status.getId() == -1) {
            status.setId(ExpenseEntryHelper.generateId());
        }

        try {
            return statusPersistence.addStatus(status);
        } finally {
            statusPersistence.closeConnection();
        }
    }

    /**
     * <p>
     * Deletes an <code>ExpenseEntryStatus</code> instance with the given ID from the persistence.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be deleted.
     *
     * @return <code>true</code> if the expense entry status exists in persistence and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry status.
     */
    public boolean deleteStatus(int statusId) throws PersistenceException {
        try {
            return statusPersistence.deleteStatus(statusId);
        } finally {
            statusPersistence.closeConnection();
        }
    }

    /**
     * <p>
     * Deletes all <code>ExpenseEntryStatus</code> instances in the persistence.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry statuses.
     */
    public void deleteAllStatuses() throws PersistenceException {
        try {
            statusPersistence.deleteAllStatuses();
        } finally {
            statusPersistence.closeConnection();
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseEntryStatus</code> instance to the persistence. All fields in the instance must be set
     * except creation date and modification date. The modification date automatically becomes the current time.
     * </p>
     *
     * @param status the expense entry status to be updated in the persistence.
     *
     * @return <code>true</code> if the expense entry status exists in persistence and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws NullPointerException if <code>status</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than creation date and modification date are not set.
     * @throws PersistenceException if error occurs when updating the expense entry status.
     */
    public boolean updateStatus(ExpenseEntryStatus status) throws PersistenceException, InsufficientDataException {
        ExpenseEntryHelper.validateNotNull(status, "status");
        ExpenseEntryHelper.validateExpenseEntryStatusData(status);

        try {
            return statusPersistence.updateStatus(status);
        } finally {
            statusPersistence.closeConnection();
        }
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseEntryStatus</code> instance with the given ID from the persistence.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be retrieved.
     *
     * @return an <code>ExpenseEntryStatus</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry status; or the value in
     *         persistence is invalid.
     */
    public ExpenseEntryStatus retrieveStatus(int statusId) throws PersistenceException {
        try {
            return statusPersistence.retrieveStatus(statusId);
        } finally {
            statusPersistence.closeConnection();
        }
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
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     * @throws IllegalArgumentException if the argument is <code>null</code>
     */
    public ExpenseEntryStatus[] searchEntries(Criteria criteria)
        throws PersistenceException {
        // argument validation
        if (criteria == null) {
            throw new IllegalArgumentException("criteria should not be null.");
        }

        try {
            // process the deletion.
            return this.statusPersistence.searchEntries(criteria);
        } finally {
            statusPersistence.closeConnection();
        }
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseEntryStatus</code> instances from the persistence.
     * </p>
     *
     * @return a list of <code>ExpenseEntryStatus</code> instances retrieved from the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry statuses; or the value in
     *         persistence is invalid.
     */
    public List retrieveAllStatuses() throws PersistenceException {
        try {
            return statusPersistence.retrieveAllStatuses();
        } finally {
            statusPersistence.closeConnection();
        }
    }

    /**
     * <p>
     * Gets the persistence used to persist and load <code>ExpenseEntryStatus</code> instances.
     * </p>
     *
     * @return the persistence used to persist and load <code>ExpenseEntryStatus</code> instances.
     */
    public ExpenseEntryStatusPersistence getStatusPersistence() {
        return statusPersistence;
    }
}
