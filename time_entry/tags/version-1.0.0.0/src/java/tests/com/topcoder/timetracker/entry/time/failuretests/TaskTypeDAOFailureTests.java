/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.timetracker.entry.time.DAOActionException;
import com.topcoder.timetracker.entry.time.DataObject;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;

/**
 * Failure tests for <c>TaskTypeDAO</c> class.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class TaskTypeDAOFailureTests extends FailureTestBase {

    /** <c>TaskTypeDAO</c> to test argument checking. */
    private TaskTypeDAOExt dao = null;

    /** <c>TaskTypeDAO</c> to test database errors. */
    private TaskTypeDAOExt emptyDAO = null;

    /**
     * Sets up test environment.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        dao = new TaskTypeDAOExt(PRODUCER_NAME, DB_FACTORY_NAMESPACE);
        emptyDAO = new TaskTypeDAOExt(EMPTY_DATABASE, DB_FACTORY_NAMESPACE);
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
            dao.verifyCreateDataObject(getTimeStatus());
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
        TaskType entry = getTaskType();
        entry.setDescription(null);
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
            dao.verifyUpdateDataObject(getTimeStatus());
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
        TaskType entry = getTaskType();
        entry.setDescription(null);
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
    public void testCreate() throws DAOActionException {
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
        TaskType entry = getTaskType();
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
        TaskType entry = getTaskType();
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
        TaskType entry = getTaskType();
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
        TaskType entry = getTaskType();
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
        TaskType entry = getTaskType();
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
        TaskType entry = getTaskType();
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
     * Private class that changes visibility of protected methods. It simple forwards all method calls to it's
     * super class.
     * 
     * @author kr00tki
     * @version 1.0
     */
    private class TaskTypeDAOExt extends TaskTypeDAO {

        /**
         * Constructor.
         * 
         * @param connName connection name
         * @param namespace namesapce
         */
        public TaskTypeDAOExt(String connName, String namespace) {
            super(connName, namespace);
        }
       
        /**
         * @see com.topcoder.timetracker.entry.time.TaskTypeDAO#verifyCreateDataObject
         * (com.topcoder.timetracker.entry.time.DataObject)
         */
        public void verifyCreateDataObject(DataObject dataObject) throws DAOActionException {
            super.verifyCreateDataObject(dataObject);
        }

        /**
         * @see com.topcoder.timetracker.entry.time.TaskTypeDAO#verifyUpdateDataObject
         * (com.topcoder.timetracker.entry.time.DataObject)
         */
        public void verifyUpdateDataObject(DataObject dataObject) throws DAOActionException {
            super.verifyUpdateDataObject(dataObject);
        }

    }

}
