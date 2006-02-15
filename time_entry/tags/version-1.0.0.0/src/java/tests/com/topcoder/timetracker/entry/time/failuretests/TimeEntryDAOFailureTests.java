/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.timetracker.entry.time.DAOActionException;
import com.topcoder.timetracker.entry.time.DataObject;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryDAO;

/**
 * Failure tests for <c>TimeEntryDAO</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class TimeEntryDAOFailureTests extends FailureTestBase {
    /** <c>TimeEntryDAO</c> to test argument checking. */
    private TimeEntryDAOExt dao = null;

    /** <c>TimeEntryDAO</c> to test database errors. */
    private TimeEntryDAOExt emptyDAO = null;

    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        dao = new TimeEntryDAOExt(PRODUCER_NAME, DB_FACTORY_NAMESPACE);
        emptyDAO = new TimeEntryDAOExt(EMPTY_DATABASE, DB_FACTORY_NAMESPACE);
    }

    /**
     * Tests constructor.
     *
     */
    public void testTimeEntryDAONullConnName() {
        try {
            new TimeEntryDAO(null, DB_FACTORY_NAMESPACE);
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests constructor.
     *
     */
    public void testTimeEntryDAONullNamespace() {
        try {
            new TimeEntryDAO(PRODUCER_NAME, null);
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests constructor.
     *
     */
    public void testTimeEntryDAOEmptyNamespace() {
        try {
            new TimeEntryDAO(PRODUCER_NAME, " ");
            fail("Empty string, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests constructor.
     *
     */
    public void testTimeEntryDAOEmptyConnName() {
        try {
            new TimeEntryDAO(" ", DB_FACTORY_NAMESPACE);
            fail("Empty string, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>verifyCreateDataObject</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testVerifyCreateDataObjectNull() throws DAOActionException {
        try {
            dao.verifyCreateDataObject(null);
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests <c>verifyCreateDataObject</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testVerifyCreateDataObjectNotTimeEntry() throws DAOActionException {
        try {
            dao.verifyCreateDataObject(getTaskType());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>verifyCreateDataObject</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testVerifyCreateDataObjectNullDescription() throws DAOActionException {
        TimeEntry entry = getTimeEntry();
        entry.setDescription(null);
        try {
            dao.verifyCreateDataObject(entry);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>verifyCreateDataObject</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testVerifyCreateDataObjectNullDate() throws DAOActionException {
        TimeEntry entry = getTimeEntry();
        entry.setDate(null);
        try {
            dao.verifyCreateDataObject(entry);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>verifyCreateDataObject</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testVerifyCreateDataObjectNegativeHours() throws DAOActionException {
        TimeEntry entry = getTimeEntry();
        entry.setHours(-1.0f);
        try {
            dao.verifyCreateDataObject(entry);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>verifyUpdateDataObject</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testVerifyUpdateDataObjectNull() throws DAOActionException {
        try {
            dao.verifyUpdateDataObject(null);
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests <c>verifyUpdateDataObject</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testVerifyUpdateDataObjectNotTimeEntry() throws DAOActionException {
        try {
            dao.verifyUpdateDataObject(getTaskType());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>verifyUpdateDataObject</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testVerifyUpdateDataObjectNullDescription() throws DAOActionException {
        TimeEntry entry = getTimeEntry();
        entry.setDescription(null);
        try {
            dao.verifyUpdateDataObject(entry);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>verifyUpdateDataObject</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testVerifyUpdateDataObjectNullDate() throws DAOActionException {
        TimeEntry entry = getTimeEntry();
        entry.setDate(null);
        try {
            dao.verifyUpdateDataObject(entry);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>verifyUpdateDataObject</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testVerifyUpdateDataObjectNegativeHours() throws DAOActionException {
        TimeEntry entry = getTimeEntry();
        entry.setHours(-1.0f);
        try {
            dao.verifyUpdateDataObject(entry);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests <c>create</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testCreateNullObject() throws DAOActionException {
        try {
            dao.create(null, "user");
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests <c>create</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
     public void testCreateNullUser() throws DAOActionException {
        TimeEntry entry = getTimeEntry();
        try {
            dao.create(entry, null);
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

     /**
      * Tests <c>create</c> method.
      * 
      * @throws DAOActionException to JUnit.
      */
     public void testCreateEmptyUser() throws DAOActionException {
        TimeEntry entry = getTimeEntry();
        try {
            dao.create(entry, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

     /**
      * Tests <c>create</c> method.
      * 
      */
     public void testCreateEmptyDB() {
        TimeEntry entry = getTimeEntry();
        try {
            emptyDAO.create(entry, "kr");
            fail("DAOActionException expected.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

     /**
      * Tests <c>update</c> method.
      * 
      * @throws DAOActionException to JUnit.
      */
     public void testUpdateNullObject() throws DAOActionException {
        try {
            dao.update(null, "user");
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

     /**
      * Tests <c>update</c> method.
      * 
      * @throws DAOActionException to JUnit.
      */
     public void testUpdateNullUser() throws DAOActionException {
        TimeEntry entry = getTimeEntry();
        try {
            dao.update(entry, null);
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

     /**
      * Tests <c>update</c> method.
      * 
      * @throws DAOActionException to JUnit.
      */
     public void testUpdateEmptyUser() throws DAOActionException {
        TimeEntry entry = getTimeEntry();
        try {
            dao.update(entry, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

     /**
      * Tests <c>update</c> method.
      * 
      */
     public void testUpdateEmptyDB() {
        TimeEntry entry = getTimeEntry();
        try {
            emptyDAO.update(entry, "kr");
            fail("DAOActionException expected.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

     /**
      * Tests <c>delete</c> method.
      * 
      */
     public void testDeleteEmptyDB() {
        try {
            emptyDAO.delete(1);
            fail("DAOActionException expected.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

     /**
      * Tests <c>get</c> method.
      * 
      */
     public void testGetEmptyDb() {
        try {
            emptyDAO.get(1);
            fail("DAOActionException expected.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

     /**
      * Tests <c>getList</c> method.
      * 
      * @throws DAOActionException to JUnit.
      */
     public void testGetListWhereIncluded() throws DAOActionException {
        try {
            dao.getList(" WHERE x");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
    
     /**
      * Tests <c>getList</c> method.
      * 
      */
     public void testGetListEmptyDb() {
        try {
            emptyDAO.getList(null);
            fail("DAOActionException expected.");
        } catch (DAOActionException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>getList</c> method.
     * 
     */
    public void testGetListInvalidWhereClause() {
        try {
            dao.getList(" x");
            fail("DAOActionException expected.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

    /**
     * Tests <c>setDataObjectPrimaryId</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testSetDataObjectPrimaryId() throws DAOActionException {
        try {
            dao.setDataObjectPrimaryId(null);
            fail("NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }
    
    /**
     * Tests <c>getList</c> method.
     * 
     * @throws DAOActionException to JUnit.
     */
    public void testSetConnection() throws DAOActionException {
        try {
            dao.setConnection(null);
            fail("Null object, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Private class that changes visibility of protected methods. It simple forwards all method calls to it's
     * super class.
     * 
     * @author kr00tki
     * @version 1.0
     */
    private class TimeEntryDAOExt extends TimeEntryDAO {

        /**
         * Constructor.
         * 
         * @param connName connection name
         * @param namespace namesapce
         */
        public TimeEntryDAOExt(String connName, String namespace) {
            super(connName, namespace);
        }

        
        /**
         * @see com.topcoder.timetracker.entry.time.BaseDAO#setDataObjectPrimaryId
         * (com.topcoder.timetracker.entry.time.DataObject)
         */
        public void setDataObjectPrimaryId(DataObject dataObject) throws DAOActionException {
            super.setDataObjectPrimaryId(dataObject);
        }

        /**
         * @see com.topcoder.timetracker.entry.time.TimeEntryDAO#verifyCreateDataObject
         * (com.topcoder.timetracker.entry.time.DataObject)
         */
        public void verifyCreateDataObject(DataObject dataObject) throws DAOActionException {
            super.verifyCreateDataObject(dataObject);
        }

        /**
         * @see com.topcoder.timetracker.entry.time.TimeEntryDAO#verifyUpdateDataObject
         * (com.topcoder.timetracker.entry.time.DataObject)
         */
        public void verifyUpdateDataObject(DataObject dataObject) throws DAOActionException {
            super.verifyUpdateDataObject(dataObject);
        }

    }

}
