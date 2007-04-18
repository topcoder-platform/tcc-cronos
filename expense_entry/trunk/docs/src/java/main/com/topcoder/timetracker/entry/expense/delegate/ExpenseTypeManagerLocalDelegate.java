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
import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.entry.expense.ExpenseTypeManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseTypeLocal;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseTypeLocalHome;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.util.config.ConfigManagerException;


/**
 * <p>
 * Implements the <code>ExpenseTypeManager</code> interface to provide management of the expense types objects through
 * the use of a local session EJB. It will obtain the handle to the bean's local interface and will simply delegate
 * all calls to it. It implements all methods.
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
public class ExpenseTypeManagerLocalDelegate implements ExpenseTypeManager {
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
    private final ExpenseTypeLocal localEJB;

    /**
     * <p>
     * Constructs the <code>ExpenseTypeManagerLocalDelegate</code> with default namespace: the full name of this class,
     * and creates the <code>ExpenseTypeLocal</code> according to it.
     * </p>
     *
     * @throws ConfigurationException if the configuration is incorrect.
     */
    public ExpenseTypeManagerLocalDelegate() throws ConfigurationException {
        this(ExpenseTypeManagerLocalDelegate.class.getName());
    }

    /**
     * <p>
     * Constructs the <code>ExpenseTypeManagerLocalDelegate</code> with the given namespace, and creates the
     * <code>ExpenseTypeLocal</code> according to it.
     * </p>
     *
     * @param namespace configuration namespace.
     *
     * @throws IllegalArgumentException if the namespace is null or empty.
     * @throws ConfigurationException if the configuration is incorrect.
     */
    public ExpenseTypeManagerLocalDelegate(String namespace) throws ConfigurationException {
        ExpenseEntryHelper.validateString(namespace, "namespace");

        String contextName = ExpenseEntryHelper.getStringPropertyValue(namespace, JNDI_CONTEXT_PROPERTY, false);
        String jndiReference = ExpenseEntryHelper.getStringPropertyValue(namespace, JNDI_REFERENCE_PROPERTY, true);

        try {
            //if context name is not specified, uses default context
            Context context = (contextName == null) ? JNDIUtils.getDefaultContext() : JNDIUtils.getContext(contextName);

            ExpenseTypeLocalHome home = (ExpenseTypeLocalHome) context.lookup(jndiReference);
            this.localEJB = home.create();
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("JNDIUtil is not configured properly", e);
        } catch (NamingException e) {
            throw new ConfigurationException("failed to create ExpenseTypeLocal via JNDIUtil", e);
        } catch (CreateException e) {
            throw new ConfigurationException("failed to obtain ExpenseTypeLocal", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("Fails to create the ExpenseTypeLocalHome.", e);
        }
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
        return localEJB.addType(type);
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
        return localEJB.deleteType(typeId);
    }

    /**
     * <p>
     * Deletes all <code>ExpenseType</code> instances in the database.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry types.
     */
    public void deleteAllTypes() throws PersistenceException {
        localEJB.deleteAllTypes();
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
        return localEJB.updateType(type);
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseType</code> instance with the given ID from the database.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be retrieved.
     *
     * @return an <code>ExpenseType</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry type; or the value in persistence
     *         is invalid.
     */
    public ExpenseType retrieveType(long typeId) throws PersistenceException {
        return localEJB.retrieveType(typeId);
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
        return localEJB.retrieveAllTypes();
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
        return localEJB.searchEntries(criteria);
    }
}
