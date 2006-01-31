/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.persistence.failuretests;

import java.sql.Connection;
import java.util.List;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryPersistence;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryStatusPersistence;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryTypePersistence;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

/**
 * Dummy persistance class to test constructor configuration failures.
 * @author kr00tki
 * @version 1.0
 */
public class DummyPersistence implements ExpenseEntryPersistence, ExpenseEntryStatusPersistence,
        ExpenseEntryTypePersistence {

    public boolean addEntry(ExpenseEntry entry) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean deleteEntry(int entryId) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public void deleteAllEntries() throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public boolean updateEntry(ExpenseEntry entry) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public ExpenseEntry retrieveEntry(int entryId) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List retrieveAllEntries() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void setConnection(Connection connection) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public Connection getConnection() {
        // TODO Auto-generated method stub
        return null;
    }

    public void closeConnection() throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public void initConnection(String connectionProducerName) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public boolean addStatus(ExpenseEntryStatus status) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean deleteStatus(int statusId) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public void deleteAllStatuses() throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public boolean updateStatus(ExpenseEntryStatus status) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public ExpenseEntryStatus retrieveStatus(int statusId) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List retrieveAllStatuses() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean addType(ExpenseEntryType type) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean deleteType(int typeId) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public void deleteAllTypes() throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public boolean updateType(ExpenseEntryType type) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public ExpenseEntryType retrieveType(int typeId) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List retrieveAllTypes() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

}
