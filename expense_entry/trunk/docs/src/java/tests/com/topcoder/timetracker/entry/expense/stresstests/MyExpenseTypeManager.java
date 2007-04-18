/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.entry.expense.ExpenseTypeManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseTypeDAO;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

/**
 * A mocked ExpenseTypeManager instance for testing.
 *
 * <p>
 * It will delegate to InformixExpenseTypeDAO.
 * </p>
 *
 * @author Chenhong
 * @version 3.2
 */
public class MyExpenseTypeManager implements ExpenseTypeManager {

    /**
     * The InformixExpenseTypeDAO instance for perform all the functionality.
     */
    private InformixExpenseTypeDAO dao;

    /**
     * Constructor.
     *
     * <p>
     * It will create a InformixExpenseTypeDAO instance.
     * </p>
     *
     * @throws ConfigurationException
     *             if failed to create InformixExpenseTypeDAO instance.
     *
     */
    public MyExpenseTypeManager() throws ConfigurationException {
        dao = new InformixExpenseTypeDAO();
    }

    /**
     * <p>
     * Delegate to InformixExpenseTypeDAO to add the ExpenseType.
     * </p>
     */
    public boolean addType(ExpenseType type) throws InsufficientDataException, PersistenceException {
        return dao.addType(type);
    }

    /**
     * <p>
     * Delegate to InformixExpenseTypeDAO to delete the ExpenseType.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseTypeManager#deleteType(long)
     */
    public boolean deleteType(long typeId) throws PersistenceException {
        return dao.deleteType(typeId);
    }

    /**
     * <p>
     * Delegate to InformixExpenseTypeDAO to delete all ExpenseType.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseTypeManager#deleteAllTypes()
     */
    public void deleteAllTypes() throws PersistenceException {
        dao.deleteAllTypes();
    }

    /**
     * <p>
     * Deleget to InformixExpenseTypeDAO to update the ExpensetType.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseTypeManager
     *      #updateType(com.topcoder.timetracker.entry.expense.ExpenseType)
     */
    public boolean updateType(ExpenseType type) throws InsufficientDataException, PersistenceException {
        return dao.updateType(type);
    }

    /**
     * <p>
     * Delegate to InformixExpenseTypeDAO to retrieve the ExpenseType.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseTypeManager#retrieveType(long)
     */
    public ExpenseType retrieveType(long typeId) throws PersistenceException {
        return dao.retrieveType(typeId);
    }

    /**
     * <p>
     * Delegate to InformixExpenseTypeDAO to retrieveAllTypes.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseTypeManager#retrieveAllTypes()
     */
    public ExpenseType[] retrieveAllTypes() throws PersistenceException {
        return dao.retrieveAllTypes();
    }

    /**
     * <p>
     * Delegate to InformixExpenseTypeDAO to searchEntries.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseTypeManager
     *      #searchEntries(com.topcoder.timetracker.entry.expense.criteria.Criteria)
     */
    public ExpenseType[] searchEntries(Criteria criteria) throws PersistenceException {
        return dao.searchEntries(criteria);
    }
}