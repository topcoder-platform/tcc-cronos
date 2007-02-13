/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * Component demonstration for Time_Entry.
 * </p>
 *
 * <p>
 * The Time Entry component is part of the Time Tracker application. It provides an abstraction of a time log entry
 * that an employee enters into the system on a regular basis. This component handles the logic of persistence for
 * this.
 * </p>
 *
 * <p>
 * This demo is separated into four parts.
 * </p>
 *
 * <p>
 * The first part demonstrates the CRUD with TaskType and TaskTypeDAO.
 * </p>
 *
 * <p>
 * The second part demonstrates the CRUD with TimeStatus and TimeStatusDAO.
 * </p>
 *
 * <p>
 * The third part demonstrates the CRUD with TimeEntry and TimeEntryDAO.
 * </p>
 *
 * <p>
 * The last part demonstrates using with local transaction control.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends TestCase {
    /**
     * <p>
     * The ConfigManager instance to load the config file.
     * </p>
     */
    private static ConfigManager cm = ConfigManager.getInstance();

    /**
     * <p>
     * Represents the description that this dataObject holds.
     * </p>
     */
    private static final String DESCRIPTION = "foo";

    /**
     * <p>
     * Represents the user that created this record.
     * </p>
     */
    private static final String CREATION_USER = "TCSDEVELOPER";

    /**
     * <p>
     * Represents the user that last modified this record.
     * </p>
     */
    private static final String MODIFICATION_USER = "TCSDESIGNER";

    /**
     * <p>
     * Represents the date that created this record.
     * </p>
     */
    private static final Date CREATION_DATE = new Date();

    /**
     * <p>
     * Represents the date that last modified this record.
     * </p>
     */
    private static final Date MODIFICATION_DATE = new Date();

    /**
     * <p>
     * The name of the Connection to fetch from the DBConnectionFactory.
     * </p>
     */
    private static final String CONNAME = "informix";

    /**
     * <p>
     * Namespace to be used in the DBConnection Factory component to load the configuration.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.time.myconfig";

    /**
     * <p>
     * Namespace to be used in the IDGeneration component to load the configuration.
     * </p>
     */
    private static final String IDGENERATION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    protected void setUp() throws Exception {
        // load the configuration
        if (cm.existsNamespace(NAMESPACE)) {
            cm.removeNamespace(NAMESPACE);
        }

        if (cm.existsNamespace(IDGENERATION_NAMESPACE)) {
            cm.removeNamespace(IDGENERATION_NAMESPACE);
        }

        cm.add(NAMESPACE, "SampleXML.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
        cm.add(IDGENERATION_NAMESPACE, "SampleXML.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        // delete all the records in TimeEntrys table
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.clearTimeEntries(conn);
            UnitTestHelper.clearTaskTypes(conn);
            UnitTestHelper.clearTimeStatuses(conn);
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }

        // remove the configuration
        if (cm.existsNamespace(NAMESPACE)) {
            cm.existsNamespace(NAMESPACE);
        }

        if (cm.existsNamespace(IDGENERATION_NAMESPACE)) {
            cm.removeNamespace(IDGENERATION_NAMESPACE);
        }
    }

    /**
     * <p>
     * Demonstrates the CRUD with TaskType and TaskTypeDAO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTaskTypeDAODemo() throws Exception {
        // Create a TaskType
        TaskType type = new TaskType();
        type.setDescription("Component Specification");

        // Get the DAO
        DAO myDAO = DAOFactory.getDAO(TaskType.class, NAMESPACE);

        // Create a record.
        myDAO.create(type, "ivern");

        // update some info
        type.setDescription("Component Spellification");
        myDAO.update(type, "TangentZ");
        System.out.println(type.getDescription());

        // to do searches:
        // formulate a where clause
        String whereClause = "CreationUser = \'ivern\'";

        // Get TaskTypes for a range of values
        List types = myDAO.getList(whereClause);
        assertEquals(1, types.size());
        System.out.println(((TaskType) types.get(0)).getCreationUser());
        System.out.println(((TaskType) types.get(0)).getModificationUser());

        // Or, get a specific Task Type and see some values
        int id = type.getPrimaryId();
        TaskType myType = (TaskType) myDAO.get(id);

        // to do deletes:
        // Delete the record with the given Id
        myDAO.delete(id);
        assertEquals(0, myDAO.getList(null).size());
    }

    /**
     * <p>
     * Demonstrates the CRUD with TimeStatus  and TimeStatusDAO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeStatusDAODemo() throws Exception {
        // Create a TimeStatus
        TimeStatus status = new TimeStatus();
        status.setDescription("Reaby");

        // Get the DAO
        DAO myDAO = DAOFactory.getDAO(TimeStatus.class, NAMESPACE);

        // Create a record.
        myDAO.create(status, "ivern");

        // update some info
        status.setDescription("Ready");
        myDAO.update(status, "TangentZ");
        System.out.println(status.getDescription());

        // to do searches:
        // formulate a where clause
        String whereClause = "CreationUser = \'ivern\'";

        // Get TimeStatuses for a range of values
        List statuses = myDAO.getList(whereClause);
        assertEquals(1, statuses.size());
        System.out.println(((TimeStatus) statuses.get(0)).getCreationUser());
        System.out.println(((TimeStatus) statuses.get(0)).getModificationUser());

        // Or, get a specific TimeStatus and see some values
        int id = status.getPrimaryId();
        TimeStatus myStatus = (TimeStatus) myDAO.get(id);

        // to do deletes:
        // Delete the record with the given Id
        myDAO.delete(id);
        assertEquals(0, myDAO.getList(null).size());
    }

    /**
     * <p>
     * Demonstrates the CRUD with TimeEntry and TimeEntryDAO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntryDAODemo() throws Exception {
        Connection conn = null;

        try {
            // Create a TimeEntry
            TimeEntry entry = new TimeEntry();
            entry.setDescription("Coding class zec");
            entry.setDate(new Date());
            entry.setHours(2.5F);
            entry.setBillable(true);
            entry.setTaskTypeId(0);
            entry.setTimeStatusId(0);
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTaskTypes((TaskType) getTaskType(), conn);
            UnitTestHelper.insertTimeStatuses((TimeStatus) getTimeStatus(), conn);

            // Get the DAO
            DAO myDAO = DAOFactory.getDAO(TimeEntry.class, NAMESPACE);

            // Create a record. assume key generated = 1
            myDAO.create(entry, "ivern");

            // update some info
            entry.setHours(3.5F);
            myDAO.update(entry, "ivern");
            System.out.println(entry.getDescription());

            // to do searches:
            // formulate a where clause
            String whereClause = "CreationUser = \'ivern\'";

            // Get TimeEntries for a range of values
            List entries = myDAO.getList(whereClause);
            System.out.println(((TimeEntry) entries.get(0)).getCreationUser());
            System.out.println(((TimeEntry) entries.get(0)).getModificationUser());

            // Or, get a specific TimeEntry and see some values
            int id = entry.getPrimaryId();
            TimeEntry myEntry = (TimeEntry) myDAO.get(id);
            System.out.println(myEntry.getDescription());
            System.out.println(myEntry.getHours());
            System.out.println(myEntry.isBillable());
            System.out.println(myEntry.getModificationUser());

            // to do deletes:
            // Delete the record with the given Id
            myDAO.delete(id);
            assertEquals(0, myDAO.getList(null).size());
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Demonstrates using with local transaction control.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testLocalTransactionControlDemo() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            // obtain DAOs for entities
            DAO myTimeStatusDAO = DAOFactory.getDAO(TimeStatus.class, NAMESPACE);
            DAO myTaskTypeDAO = DAOFactory.getDAO(TaskType.class, NAMESPACE);

            // set connections in all DAOs for local transaction. assume connection exists and is ready
            // for local transaction (autoCommit=false);
            myTimeStatusDAO.setConnection(conn);
            myTaskTypeDAO.setConnection(conn);

            // initiate local transaction
            // create new taskType
            TaskType type = new TaskType();
            type.setDescription("Component Devastation");
            myTaskTypeDAO.create(type, CREATION_USER);
            System.out.println(type.getModificationUser());

            // delete old taskType
            int taskTypeId = type.getPrimaryId();
            myTaskTypeDAO.delete(taskTypeId);
            assertEquals(0, myTaskTypeDAO.getList(null).size());

            // create new timeStatus
            TimeStatus status = new TimeStatus();
            status.setDescription("ready");
            myTimeStatusDAO.create(status, CREATION_USER);
            System.out.println(status.getModificationUser());

            // update the timeStatus to new status
            status.setDescription("down");
            myTimeStatusDAO.update(status, MODIFICATION_USER);
            System.out.println(status.getDescription());

            // remove connections
            myTimeStatusDAO.removeConnection();
            myTaskTypeDAO.removeConnection();
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * return a TaskType instance for testing.
     * </p>
     *
     * @return a TaskType instance for testing.
     */
    private DataObject getTaskType() {
        TaskType myTaskType = new TaskType();

        myTaskType.setDescription(DESCRIPTION);
        myTaskType.setCreationUser(CREATION_USER);
        myTaskType.setCreationDate(CREATION_DATE);
        myTaskType.setModificationUser(CREATION_USER);
        myTaskType.setModificationDate(CREATION_DATE);

        return myTaskType;
    }

    /**
     * <p>
     * return a TimeStatus instance for testing.
     * </p>
     *
     * @return a TimeStatus instance for testing.
     */
    private DataObject getTimeStatus() {
        TimeStatus myTimeStatus = new TimeStatus();

        myTimeStatus.setDescription(DESCRIPTION);
        myTimeStatus.setCreationUser(CREATION_USER);
        myTimeStatus.setCreationDate(CREATION_DATE);
        myTimeStatus.setModificationUser(CREATION_USER);
        myTimeStatus.setModificationDate(CREATION_DATE);

        return myTimeStatus;
    }
}
