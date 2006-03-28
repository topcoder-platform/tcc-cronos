/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.failuretests;

import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

import com.cronos.timetracker.entry.time.DataObject;
import com.cronos.timetracker.entry.time.ResultData;
import com.cronos.timetracker.entry.time.TimeEntry;
import com.cronos.timetracker.entry.time.TimeEntryDAO;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure tests for TimeEntryDAO.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
public class TimeEntryDAO11FailureTests extends TestCase {
    /**
     * TimeEntryDAO instance for testing.
     */
    private TimeEntryDAO dao;

    /**
     * <p>
     * Set up tests.
     * </p>
     * @throws Exception to JUnit
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        ConfigManager.getInstance().add("failure/config.xml");
        dao = new TimeEntryDAO("mysql", "com.cronos.timetracker.entry.time.failuretests");
    }

    /**
     * <p>
     * Tear down tests.
     * </p>
     * @throws Exception to JUnit
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        for (Iterator iter = ConfigManager.getInstance().getAllNamespaces(); iter.hasNext();) {
            ConfigManager.getInstance().removeNamespace(iter.next().toString());
        }
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String),
     * with null DataObject, IAE or NPE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testCreateNullDataObject() throws Exception {
        try {
            dao.create(null, "topcoder");
        } catch (IllegalArgumentException e) {
            // expected
            return;
        } catch (NullPointerException e) {
            // expected
            return;
        }
        
        fail("IllegalArgumentException or NullPointerException should be thrown.");
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String),
     * with non TimeEntry DataObject, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testCreateNonTimeEntry() throws Exception {
        try {
            dao.create(new DataObject() {}, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String),
     * with TimeEntry DataObject whose description is null, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntryNoDesc() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDate(new Date());
        entry.setHours(2.0f);
        
        try {
            dao.create(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String),
     * with TimeEntry DataObject whose date is null, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntryNoDate() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDescription("desc");
        entry.setHours(2.0f);
        
        try {
            dao.create(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String),
     * with TimeEntry DataObject whose hours is illegal, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntryIllegalHours() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDate(new Date());
        entry.setHours(-1.0f);
        entry.setDescription("desc");
        
        try {
            dao.create(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String),
     * with null user, IAE or NPE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testCreateNullUser() throws Exception {
        try {
            dao.create(new TimeEntry(), null);
        } catch (IllegalArgumentException e) {
            // expected
            return;
        } catch (NullPointerException e) {
            // expected
            return;
        }
        
        fail("IllegalArgumentException or NullPointerException should be thrown.");
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String),
     * with empty user, IAE or NPE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testCreateEmptyUser() throws Exception {
        try {
            dao.create(new TimeEntry(), "   ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String),
     * with null DataObject, IAE or NPE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testUpdateNullDataObject() throws Exception {
        try {
            dao.update(null, "topcoder");
        } catch (IllegalArgumentException e) {
            // expected
            return;
        } catch (NullPointerException e) {
            // expected
            return;
        }
        
        fail("IllegalArgumentException or NullPointerException should be thrown.");
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String),
     * with non TimeEntry DataObject, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testUpdateNonTimeEntry() throws Exception {
        try {
            dao.update(new DataObject() {}, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String),
     * with TimeEntry DataObject with no description, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntryNoDesc() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDate(new Date());
        entry.setHours(2.0f);
        
        try {
            dao.update(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String),
     * with TimeEntry DataObject with no date, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntryNoDate() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDescription("desc");
        entry.setHours(2.0f);
        
        try {
            dao.update(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String),
     * with TimeEntry DataObject with illegal hours, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntryIllegalHours() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDescription("desc");
        entry.setHours(-1.0f);
        entry.setDate(new Date());
        
        try {
            dao.update(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String),
     * with null user, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntryNullUser() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDescription("desc");
        entry.setHours(1.0f);
        entry.setDate(new Date());
        
        try {
            dao.update(entry, null);
        } catch (IllegalArgumentException e) {
            // expected
            return;
        } catch (NullPointerException e) {
            // expected
            return;
        }
        
        fail("IllegalArgumentException or NullPointerExceptioin should be thrown.");
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData),
     * with null DataObjects, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullDataObjects() throws Exception {
        try {
            dao.batchCreate(null, "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData),
     * with empty DataObjects, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreateEmptyDataObjects() throws Exception {
        try {
            dao.batchCreate(new DataObject[0], "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData),
     * with null DataObject in the array, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullDataObject() throws Exception {
        try {
            dao.batchCreate(new DataObject[2], "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData),
     * with non TimeEntry DataObject in the array, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreateNonTimeEntry() throws Exception {
        try {
            dao.batchCreate(new DataObject[] {new DataObject() {}}, "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData),
     * with null user, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullUser() throws Exception {
        try {
            dao.batchCreate(new DataObject[] {new TimeEntry()}, null, true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData),
     * with null ResultData, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullResultData() throws Exception {
        try {
            dao.batchCreate(new DataObject[] {new TimeEntry()}, "topcoder", true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchDelete(int[], boolean, ResultData),
     * with null idList, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchDeleteNullIdList() throws Exception {
        try {
            dao.batchDelete(null, true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchDelete(int[], boolean, ResultData),
     * with empty idList, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchDeleteEmptyIdList() throws Exception {
        try {
            dao.batchDelete(new int[0], true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchDelete(int[], boolean, ResultData),
     * with null ResultData, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchDeleteNullResultData() throws Exception {
        try {
            dao.batchDelete(new int[] {1, 2}, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData),
     * with null DataObjects, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullDataObjects() throws Exception {
        try {
            dao.batchUpdate(null, "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData),
     * with empty DataObjects, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdateEmptyDataObjects() throws Exception {
        try {
            dao.batchUpdate(new DataObject[0], "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData),
     * with null DataObject, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullDataObject() throws Exception {
        try {
            dao.batchUpdate(new DataObject[2], "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData),
     * with non TimeEntry DataObject, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNonTimeEntry() throws Exception {
        try {
            dao.batchUpdate(new DataObject[] {new DataObject() {}}, "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData),
     * with null user, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullUser() throws Exception {
        try {
            dao.batchUpdate(new DataObject[] {new TimeEntry()}, null, true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData),
     * with null ResultData, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullResultData() throws Exception {
        try {
            dao.batchUpdate(new DataObject[] {new TimeEntry()}, "topcoder", true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.batchRead(int[], boolean, ResultData),
     * with null idList, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchReadNullIdList() throws Exception {
        try {
            dao.batchRead(null, true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.batchRead(int[], boolean, ResultData),
     * with empty idList, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchReadEmptyIdList() throws Exception {
        try {
            dao.batchRead(new int[0], true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.batchRead(int[], boolean, ResultData),
     * with null ResultData, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchReadNullResultData() throws Exception {
        try {
            dao.batchRead(new int[] {1, 2}, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
