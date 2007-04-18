/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseStatusManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseStatusDAO;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

/**
 * A mocked ExpenseStatusManager class for test.
 *
 * <p>
 * It will delegate to class InformixExpenseStatusDAO.
 * </p>
 *
 * @author Chenhong
 * @version 3.2
 */
public class MyExpenseStatusManager implements ExpenseStatusManager {

    /**
     * The InformixExpenseStatusDAO.
     */
    private InformixExpenseStatusDAO dao;

    /**
     * Constructor.
     *
     * <p>
     * It will create a InformixExpenseStatusDAO instance.
     * </p>
     *
     * @throws ConfigurationException
     *             if failed to create InformixExpenseStatusDAO instance.
     *
     */
    public MyExpenseStatusManager() throws ConfigurationException {
        dao = new InformixExpenseStatusDAO();
    }

    /**
     * <p>
     * It will delegate to InformixExpenseStatusDAO class.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseStatusManager
     *      #addStatus(com.topcoder.timetracker.entry.expense.ExpenseStatus)
     */
    public boolean addStatus(ExpenseStatus status) throws InsufficientDataException, PersistenceException {
        return dao.addStatus(status);
    }

    /**
     * <p>
     * It will delegate to InformixExpenseStatusDAO class.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseStatusManager#deleteStatus(long)
     */
    public boolean deleteStatus(long statusId) throws PersistenceException {
        return dao.deleteStatus(statusId);
    }

    /**
     * <p>
     * It will delegate to InformixExpenseStatusDAO class.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseStatusManager#deleteAllStatuses()
     */
    public void deleteAllStatuses() throws PersistenceException {
        dao.deleteAllStatuses();
    }

    /**
     * <p>
     * It will delegate to InformixExpenseStatusDAO class.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseStatusManager
     *      #updateStatus(com.topcoder.timetracker.entry.expense.ExpenseStatus)
     */
    public boolean updateStatus(ExpenseStatus status) throws InsufficientDataException, PersistenceException {
        return dao.updateStatus(status);
    }

    /**
     * <p>
     * It will delegate to InformixExpenseStatusDAO class.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseStatusManager#retrieveStatus(long)
     */
    public ExpenseStatus retrieveStatus(long statusId) throws PersistenceException {
        return dao.retrieveStatus(statusId);
    }

    /**
     * <p>
     * It will delegate to InformixExpenseStatusDAO class.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseStatusManager#retrieveAllStatuses()
     */
    public ExpenseStatus[] retrieveAllStatuses() throws PersistenceException {
        return dao.retrieveAllStatuses();
    }

    /**
     * <p>
     * It will delegate to InformixExpenseStatusDAO class.
     * </p>
     *
     * @see com.topcoder.timetracker.entry.expense.ExpenseStatusManager
     *      #searchEntries(com.topcoder.timetracker.entry.expense.criteria.Criteria)
     */
    public ExpenseStatus[] searchEntries(Criteria criteria) throws PersistenceException {
        return dao.searchEntries(criteria);
    }
}