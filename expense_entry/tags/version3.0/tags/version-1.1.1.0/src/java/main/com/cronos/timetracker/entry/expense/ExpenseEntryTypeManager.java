/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryTypePersistence;
import com.cronos.timetracker.entry.expense.persistence.PersistenceException;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import java.util.List;


/**
 * <p>
 * Defines functionality to manage expense entry types in persistence. The features include adding, updating, deleting
 * and retrieving a single expense entry type or all expense entry types in the persistence. When adding the expense
 * entry type, if the ID is unspecified (-1), a random ID is generated. The creation date and modification date are
 * modified and persisted automatically according to the current time. The manager uses
 * <code>ExpenseEntryTypePersistence</code> implementation to do actual operations on persistence. It can only be
 * created from configuration.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public class ExpenseEntryTypeManager {
    /** Represents the property to load entry type persistence class name. */
    private static final String ENTRY_TYPE_PERSISTENCE_CLASS_PROPERTY = "entry_type_persistence_class";

    /** Represents the property to load connection producer name. */
    private static final String CONNECTION_PRODUCER_NAME_PROPERTY = "connection_producer_name";

    /** Represents the property to load namespace to create DB connection factory. */
    private static final String CONNECTION_FACTORY_NAMESPACE = "connection_factory_namespace";

    /** Represents the persistence instance used to persist and load <code>ExpenseEntryType</code> instances. */
    private ExpenseEntryTypePersistence typePersistence = null;

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryTypeManager</code> class. The persistence class name is loaded from
     * the configuration property 'entry_type_persistence_class' under the given namespace. It is mandatory and must
     * be an implenentation of <code>ExpenseEntryTypePersistence</code>. Optional property 'connection_producer_name'
     * represents the connection producer name in the persistence class to create the default database connection from
     * DB connection factory. If it is <code>null</code>, there is no default database connection. The namespace to
     * create DB connection factory is defined in property 'connection_factory_namespace'. It is mandatory, and cannot
     * be empty string.
     * </p>
     *
     * @param namespace the namespace to load the persistence class name and other configuration.
     *
     * @throws NullPointerException if <code>namespace</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>namespace</code> is empty string.
     * @throws ConfigurationException if error occurs when loading configuration; or mandatory property is missing; or
     *         property is invalid.
     */
    public ExpenseEntryTypeManager(String namespace) throws ConfigurationException {
        ExpenseEntryHelper.validateNotNullOrEmpty(namespace, "namespace");

        ConfigManager configManager = ConfigManager.getInstance();
        String className;
        String connProducerName;
        String dbNamespace;

        try {
            className = configManager.getString(namespace, ENTRY_TYPE_PERSISTENCE_CLASS_PROPERTY);
            connProducerName = configManager.getString(namespace, CONNECTION_PRODUCER_NAME_PROPERTY);
            dbNamespace = configManager.getString(namespace, CONNECTION_FACTORY_NAMESPACE);
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("Fail to load configuration value.", e);
        }

        typePersistence = (ExpenseEntryTypePersistence) ExpenseEntryHelper.createInstance(className, connProducerName,
                dbNamespace, ExpenseEntryTypePersistence.class);
    }

    /**
     * <p>
     * Adds a new <code>ExpenseEntryType</code> instance to the persistence. If the ID of the
     * <code>ExpenseEntryType</code> is -1, a random ID is generated. Otherwise, the user-specified ID is used. All
     * fields in the instance must be set except creation date and modification date, which must be <code>null</code>
     * and be set as the current time.
     * </p>
     *
     * @param type the expense entry type to be added to the persistence.
     *
     * @return <code>true</code> if the ID does not exist in persistence and the expense entry type is added;
     *         <code>false</code> otherwise.
     *
     * @throws NullPointerException if <code>type</code> is <code>null</code>.
     * @throws IllegalArgumentException if the creation date or modification date is not <code>null</code>.
     * @throws InsufficientDataException If fields other than creation date and modification date are not set.
     * @throws PersistenceException if error occurs when adding the expense entry type.
     */
    public boolean addType(ExpenseEntryType type) throws PersistenceException, InsufficientDataException {
        ExpenseEntryHelper.validateNotNull(type, "type");
        ExpenseEntryHelper.validateNewInfo(type, "type");
        ExpenseEntryHelper.validateExpenseEntryTypeData(type);

        if (type.getId() == -1) {
            type.setId(ExpenseEntryHelper.generateId());
        }

        return typePersistence.addType(type);
    }

    /**
     * <p>
     * Deletes an <code>ExpenseEntryType</code> instance with the given ID from the persistence.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be deleted.
     *
     * @return <code>true</code> if the expense entry type exists in persistence and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry type.
     */
    public boolean deleteType(int typeId) throws PersistenceException {
        return typePersistence.deleteType(typeId);
    }

    /**
     * <p>
     * Deletes all <code>ExpenseEntryType</code> instances in the persistence.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry types.
     */
    public void deleteAllTypes() throws PersistenceException {
        typePersistence.deleteAllTypes();
    }

    /**
     * <p>
     * Updates an <code>ExpenseEntryType</code> instance to the persistence. All fields in the instance must be set
     * except creation date and modification date. The modification date automatically becomes the current time.
     * </p>
     *
     * @param type the expense entry type to be updated in the persistence.
     *
     * @return <code>true</code> if the expense entry type exists in persistence and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws NullPointerException if <code>type</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than creation date and modification date are not set.
     * @throws PersistenceException if error occurs when updating the expense entry type.
     */
    public boolean updateType(ExpenseEntryType type) throws PersistenceException, InsufficientDataException {
        ExpenseEntryHelper.validateNotNull(type, "type");
        ExpenseEntryHelper.validateExpenseEntryTypeData(type);

        return typePersistence.updateType(type);
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseEntryType</code> instance with the given ID from the persistence.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be retrieved.
     *
     * @return an <code>ExpenseEntryType</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry type; or the value in persistence
     *         is invalid.
     */
    public ExpenseEntryType retrieveType(int typeId) throws PersistenceException {
        return typePersistence.retrieveType(typeId);
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseEntryType</code> instances from the persistence.
     * </p>
     *
     * @return a list of <code>ExpenseEntryType</code> instances retrieved from the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry types; or the value in
     *         persistence is invalid.
     */
    public List retrieveAllTypes() throws PersistenceException {
        return typePersistence.retrieveAllTypes();
    }

    /**
     * <p>
     * Gets the persistence used to persist and load <code>ExpenseEntryType</code> instances.
     * </p>
     *
     * @return the persistence used to persist and load <code>ExpenseEntryType</code> instances.
     */
    public ExpenseEntryTypePersistence getTypePersistence() {
        return typePersistence;
    }
}
