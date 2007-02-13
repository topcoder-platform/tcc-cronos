/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * Unit test cases for TimeEntryDAO.
 * </p>
 *
 * <p>
 * This class will test all the newly added methods in V1.1 and the changed methods in V1.0;
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class V1Dot1TimeEntryDAOUnitTest extends TestCase {
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
    private static final Date CREATION_DATE = V1Dot1TestHelper.createDate(2006, 1, 1);

    /**
     * <p>
     * Represents the date that last modified this record.
     * </p>
     */
    private static final Date MODIFICATION_DATE = V1Dot1TestHelper.createDate(2006, 2, 1);

    /**
     * <p>
     * The TimeEntryDAO instance for testing.
     * </p>
     */
    private BaseDAO timeEntryDAO = null;

    /** Represents a valid time entry entry instance. */
    private TimeEntry entry;

    /** Represents a valid time task type instance. */
    private TaskType type;

    /** Represents a valid time status instance. */
    private TimeStatus status;

    /** Represents a valid time reject reason instance. */
    private RejectReason reason1;

    /** Represents a valid time reject reason instance. */
    private RejectReason reason2;

    /**
     * <p>
     * Set up the environment for testing. Valid configurations are loaded. A valid manager is created. The data table
     * is truncated. A valid expense entry entry is created. One task type and one time status and two reject reasons
     * are added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    protected void setUp() throws Exception {
        // load the configuration
        V1Dot1TestHelper.clearConfiguration();
        V1Dot1TestHelper.addValidConfiguration();

        Connection conn = null;

        try {
            // delete all the records in all tables
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.clearDatabase(conn);
            V1Dot1TestHelper.executeSQL("insert into company values(1, 'a', 'a', current, 'a', current, 'a');", conn);

            // Insert an task type
            type = new TaskType();
            type.setCompanyId(1);
            type.setPrimaryId(1);
            type.setDescription("taskType");
            type.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
            type.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
            type.setCreationUser("taskTypeCreate");
            type.setModificationUser("taskTypeModification");

            V1Dot1TestHelper.insertTaskTypes(type, conn);

            // Insert the time status
            status = new TimeStatus();

            status.setPrimaryId(2);
            status.setDescription("timeStatus");
            status.setCreationDate(V1Dot1TestHelper.createDate(2005, 3, 1));
            status.setModificationDate(V1Dot1TestHelper.createDate(2005, 4, 1));
            status.setCreationUser("timeStatusCreate");
            status.setModificationUser("timeStatusModification");

            V1Dot1TestHelper.insertTimeStatuses(status, conn);

            // Insert the first reject reason
            reason1 = new RejectReason();
            reason1.setPrimaryId(3);
            reason1.setDescription("reason1");
            reason1.setCreationDate(V1Dot1TestHelper.createDate(2005, 5, 1));
            reason1.setModificationDate(V1Dot1TestHelper.createDate(2005, 6, 1));
            reason1.setCreationUser("reason1Create");
            reason1.setModificationUser("reason1Modification");
            V1Dot1TestHelper.insertRejectReasons(reason1, conn);

            reason2 = new RejectReason();
            reason2.setPrimaryId(4);
            reason2.setDescription("reason2");
            reason2.setCreationDate(V1Dot1TestHelper.createDate(2005, 7, 1));
            reason2.setModificationDate(V1Dot1TestHelper.createDate(2005, 8, 1));
            reason2.setCreationUser("reason2Create");
            reason2.setModificationUser("reason2Modification");
            V1Dot1TestHelper.insertRejectReasons(reason2, conn);

            V1Dot1TestHelper.executeSQL("insert into comp_rej_reason values(1,3,current,'a', current,'a');", conn);
            V1Dot1TestHelper.executeSQL("insert into comp_rej_reason values(1,4,current,'a', current,'a');", conn);
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }

        this.timeEntryDAO = new TimeEntryDAO(CONNAME, NAMESPACE);
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        // delete all the records in all tables
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.clearDatabase(conn);
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }

        V1Dot1TestHelper.clearConfiguration();
    }

    /**
     * <p>
     * Tests the create(DataObject dataObject, String user). The link of the two reject reasons should also be inserted
     * into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testCreate_Accuracy() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            /*
             * insert a record to the database with create method and the initial record will be:
             * primaryID: default: 0
             * taskTypesID: 1
             * timeStatusesID: 2
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: default: null
             * creationDate: default: null
             * modificationUser: default: null
             * modificationDate: default: null
             */
            entry = new TimeEntry();
            entry.setCompanyId(1);
            entry.setDescription(DESCRIPTION);
            entry.setDate(CREATION_DATE);
            entry.setTaskTypeId(type.getPrimaryId());
            entry.setTimeStatusId(status.getPrimaryId());
            entry.addRejectReason(reason1);
            entry.addRejectReason(reason2);
            timeEntryDAO.create(entry, CREATION_USER);

            /*
             * get the connection and fetch the record from the database.
             * The record which has just insert into the datebase should be:
             * primaryID: an id generated by IDGenerator
             * taskTypesID: 1
             * timeStatusesID: 2
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: CREATION_USER
             * creationDate: a new Date
             * modificationUser: CREATION_USER
             * modificationDate: a new Date
             */
            List returnTimeEntrys = V1Dot1TestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly created in the database", 1, returnTimeEntrys.size());

            TimeEntry returnTimeEntry = (TimeEntry) returnTimeEntrys.get(0);
            assertEquals("record was not properly created in the database", entry.getPrimaryId(),
                returnTimeEntry.getPrimaryId());
            assertEquals("record was not properly created in the database", type.getPrimaryId(),
                returnTimeEntry.getTaskTypeId());
            assertEquals("record was not properly created in the database", status.getPrimaryId(),
                returnTimeEntry.getTimeStatusId());
            assertEquals("record was not properly created in the database", DESCRIPTION,
                returnTimeEntry.getDescription());
            V1Dot1TestHelper.assertEquals("record was not properly created in the database", CREATION_DATE,
                returnTimeEntry.getDate());
            assertEquals("record was not properly created in the database", 0, returnTimeEntry.getHours(), 1e-8);
            assertEquals("record was not properly created in the database", false, returnTimeEntry.isBillable());
            assertEquals("record was not properly created in the database", CREATION_USER,
                returnTimeEntry.getCreationUser());
            assertEquals("record was not properly created in the database", CREATION_USER,
                returnTimeEntry.getModificationUser());
            assertNotNull("record was not properly created in the database", returnTimeEntry.getCreationDate());
            assertNotNull("record was not properly created in the database", returnTimeEntry.getModificationDate());

            /*
             * The entry will also be changed
             */
            assertEquals("entry was not properly updated", CREATION_USER, entry.getCreationUser());
            assertEquals("entry was not properly updated", CREATION_USER, entry.getModificationUser());
            assertNotNull("entry was not properly updated", entry.getCreationDate());
            assertNotNull("entry was not properly updated", entry.getModificationDate());

            // The links should also be inserted into the time_reject_reason table
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");

            // check the first record
            assertTrue("There should be two records in time_reject_reason table.", resultSet.next());

            assertEquals("Table time_reject_reason is not updated.", entry.getPrimaryId(),
                resultSet.getInt("time_entry_id"));
            assertEquals("Table time_reject_reason is not updated.", reason1.getPrimaryId(),
                resultSet.getInt("reject_reason_id"));
            assertEquals("Table time_reject_reason is not updated.", CREATION_USER,
                resultSet.getString("creation_user"));
            assertEquals("Table time_reject_reason is not updated.", CREATION_USER,
                resultSet.getString("modification_user"));
            V1Dot1TestHelper.assertEquals("Table time_reject_reason is not updated.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            V1Dot1TestHelper.assertEquals("Table time_reject_reason is not updated.", entry.getCreationDate(),
                resultSet.getDate("modification_date"));

            // check the second record
            assertTrue("There should be two records in time_reject_reason table.", resultSet.next());

            assertEquals("Table time_reject_reason is not updated.", entry.getPrimaryId(),
                resultSet.getInt("time_entry_id"));
            assertEquals("Table time_reject_reason is not updated.", reason2.getPrimaryId(),
                resultSet.getInt("reject_reason_id"));
            assertEquals("Table time_reject_reason is not updated.", CREATION_USER,
                resultSet.getString("creation_user"));
            assertEquals("Table time_reject_reason is not updated.", CREATION_USER,
                resultSet.getString("modification_user"));
            V1Dot1TestHelper.assertEquals("Table time_reject_reason is not updated.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            V1Dot1TestHelper.assertEquals("Table time_reject_reason is not updated.", entry.getCreationDate(),
                resultSet.getDate("modification_date"));

            // check the third record
            assertFalse("There should be two records in time_reject_reason table.", resultSet.next());
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the update(DataObject dataObject, String user). The link of the reject reasons should also be updated into
     * the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testUpdate_Accuracy() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * taskTypesID: 1
             * timeStatusesID: 2
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            entry = (TimeEntry) getTimeEntry();
            V1Dot1TestHelper.insertTimeEntries(entry, conn);

            // update the record and update the billable field to true
            entry.setBillable(true);
            entry.setDescription("changed");
            entry.addRejectReason(reason1);
            entry.setDate(MODIFICATION_DATE);
            timeEntryDAO.update(entry, MODIFICATION_USER);

            /*
             * Fetch the record from the database and this record should be:
             * primaryID: default: 0
             * taskTypesID: 1
             * timeStatusesID: 2
             * decription: changed
             * entryDate: MODIFICATION_DATE
             * hours: default: 0.0F
             * billable: default: true
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: MODIFICATION_USER
             * modificationDate: a new date
             */
            List returnTimeEntrys = V1Dot1TestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly updated in the database", 1, returnTimeEntrys.size());

            TimeEntry returnTimeEntry = (TimeEntry) returnTimeEntrys.get(0);

            assertEquals("record was not properly created in the database", entry.getPrimaryId(),
                returnTimeEntry.getPrimaryId());
            assertEquals("record was not properly updated in the database", type.getPrimaryId(),
                returnTimeEntry.getTaskTypeId());
            assertEquals("record was not properly updated in the database", status.getPrimaryId(),
                returnTimeEntry.getTimeStatusId());
            assertEquals("record was not properly updated in the database", "changed",
                returnTimeEntry.getDescription());
            V1Dot1TestHelper.assertEquals("record was not properly updated in the database", MODIFICATION_DATE,
                returnTimeEntry.getDate());
            assertEquals("record was not properly updated in the database", 0, returnTimeEntry.getHours(), 1e-8);
            assertEquals("record was not properly updated in the database", true, returnTimeEntry.isBillable());
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnTimeEntry.getCreationUser());
            assertEquals("record was not properly updated in the database", MODIFICATION_USER,
                returnTimeEntry.getModificationUser());
            V1Dot1TestHelper.assertEquals("record was not properly updated in the database", CREATION_DATE,
                returnTimeEntry.getCreationDate());
            V1Dot1TestHelper.assertNotEquals("record was not properly updated in the database", CREATION_DATE,
                returnTimeEntry.getModificationDate());

            /*
             * The entry will also be updated
             */
            assertEquals("entry was not properly updated", CREATION_USER, entry.getCreationUser());
            assertEquals("entry was not properly updated", MODIFICATION_USER, entry.getModificationUser());
            V1Dot1TestHelper.assertEquals("entry was not properly updated", CREATION_DATE, entry.getCreationDate());
            V1Dot1TestHelper.assertNotEquals("entry was not properly updated", CREATION_DATE,
                entry.getModificationDate());

            // The link should also be inserted into the time_reject_reason table
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");

            // check the first record
            assertTrue("There should be one record in time_reject_reason table.", resultSet.next());

            assertEquals("Table time_reject_reason is not updated.", entry.getPrimaryId(),
                resultSet.getInt("time_entry_id"));
            assertEquals("Table time_reject_reason is not updated.", reason1.getPrimaryId(),
                resultSet.getInt("reject_reason_id"));
            assertEquals("Table time_reject_reason is not updated.", CREATION_USER,
                resultSet.getString("creation_user"));
            assertEquals("Table time_reject_reason is not updated.", MODIFICATION_USER,
                resultSet.getString("modification_user"));
            V1Dot1TestHelper.assertEquals("Table time_reject_reason is not updated.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            V1Dot1TestHelper.assertEquals("Table time_reject_reason is not updated.", entry.getModificationDate(),
                resultSet.getDate("modification_date"));

            assertFalse("There should be one record in time_reject_reason table.", resultSet.next());
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the update(DataObject dataObject, String user) when the specific time entry does not exist in the
     * database. Nothing will be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testUpdate_NotExistAccuracy() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            entry = (TimeEntry) getTimeEntry();
            timeEntryDAO.update(entry, MODIFICATION_USER);

            // check nothing should be added
            List returnTimeEntrys = V1Dot1TestHelper.selectTimeEntries(conn);
            assertEquals("Nothing should be added", 0, returnTimeEntrys.size());

            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");
            assertFalse("Nothing should be added", resultSet.next());
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the delete(int primaryId). The link of the reject reasons should also be deleted from the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testDelete_Accuracy() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            /*
             * insert a record to the database with create method and the initial record will be:
             * primaryID: default: 0
             * taskTypesID: 1
             * timeStatusesID: 2
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: default: null
             * creationDate: default: null
             * modificationUser: default: null
             * modificationDate: default: null
             */
            entry = new TimeEntry();
            entry.setCompanyId(1);
            entry.setDescription(DESCRIPTION);
            entry.setDate(CREATION_DATE);
            entry.setTaskTypeId(type.getPrimaryId());
            entry.setTimeStatusId(status.getPrimaryId());
            entry.addRejectReason(reason1);
            entry.addRejectReason(reason2);
            timeEntryDAO.create(entry, CREATION_USER);

            // check whether the record has been added
            List returnTimeEntrys = V1Dot1TestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly created in the database", 1, returnTimeEntrys.size());

            // delete the record
            TimeEntry returnTimeEntry = (TimeEntry) returnTimeEntrys.get(0);
            timeEntryDAO.delete(returnTimeEntry.getPrimaryId());

            // assert there is no records in the database now
            returnTimeEntrys = V1Dot1TestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly deleted in the database", 0, returnTimeEntrys.size());

            // The links should also be deleted from the time_reject_reason table
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");
            assertFalse("The links should also be deleted from the time_reject_reason table.", resultSet.next());
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the delete(int primaryId) when the specific time entry does not exist in the database. Nothing will be
     * deleted.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testDelete_NotExistAccuracy() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            /*
             * insert a record to the database with create method and the initial record will be:
             * primaryID: default: 0
             * taskTypesID: 1
             * timeStatusesID: 2
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: default: null
             * creationDate: default: null
             * modificationUser: default: null
             * modificationDate: default: null
             */
            entry = new TimeEntry();
            entry.setCompanyId(1);
            entry.setDescription(DESCRIPTION);
            entry.setDate(CREATION_DATE);
            entry.setTaskTypeId(type.getPrimaryId());
            entry.setTimeStatusId(status.getPrimaryId());
            entry.addRejectReason(reason1);
            entry.addRejectReason(reason2);
            timeEntryDAO.create(entry, CREATION_USER);

            // check whether the record has been added
            List returnTimeEntrys = V1Dot1TestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly created in the database", 1, returnTimeEntrys.size());

            // delete the record
            TimeEntry returnTimeEntry = (TimeEntry) returnTimeEntrys.get(0);
            timeEntryDAO.delete(returnTimeEntry.getPrimaryId() + 1);

            // assert nothing is deleted
            returnTimeEntrys = V1Dot1TestHelper.selectTimeEntries(conn);
            assertEquals("nothing should be deleted", 1, returnTimeEntrys.size());
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");
            assertTrue("nothing should be deleted", resultSet.next());
            assertTrue("nothing should be deleted", resultSet.next());
            assertFalse("nothing should be deleted", resultSet.next());
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the get(int primaryId).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGet_Accuracy() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            /*
             * insert a record to the database with create method and the initial record will be:
             * primaryID: default: 0
             * taskTypesID: 1
             * timeStatusesID: 2
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: default: null
             * creationDate: default: null
             * modificationUser: default: null
             * modificationDate: default: null
             */
            entry = new TimeEntry();
            entry.setCompanyId(1);
            entry.setDescription(DESCRIPTION);
            entry.setDate(CREATION_DATE);
            entry.setTaskTypeId(type.getPrimaryId());
            entry.setTimeStatusId(status.getPrimaryId());
            entry.addRejectReason(reason1);
            entry.addRejectReason(reason2);
            timeEntryDAO.create(entry, CREATION_USER);

            // check whether the record has been added
            List returnTimeEntrys = V1Dot1TestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly created in the database", 1, returnTimeEntrys.size());

            // get the record
            TimeEntry returnTimeEntry = (TimeEntry) returnTimeEntrys.get(0);
            returnTimeEntry = (TimeEntry) timeEntryDAO.get(returnTimeEntry.getPrimaryId());

            // assert the return value is the exactly one which has just inserted into the database
            assertEquals("record was not properly got", entry, returnTimeEntry);
            if (((TimeEntry) returnTimeEntry).getAllRejectReasons()[0].getPrimaryId() == reason1.getPrimaryId()) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    reason1, ((TimeEntry) returnTimeEntry).getAllRejectReasons()[0]);
                assertEquals("not correctly record returned from the TimeEntries table",
                    reason2, ((TimeEntry) returnTimeEntry).getAllRejectReasons()[1]);
            } else {
                assertEquals("not correctly record returned from the TimeEntries table",
                        reason1, ((TimeEntry) returnTimeEntry).getAllRejectReasons()[1]);
                assertEquals("not correctly record returned from the TimeEntries table",
                        reason2, ((TimeEntry) returnTimeEntry).getAllRejectReasons()[0]);
            }
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the get(int primaryId) when the specific time entry does not exist in the database. Nothing will be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGet_NotExistAccuracy() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            /*
             * insert a record to the database with create method and the initial record will be:
             * primaryID: default: 0
             * taskTypesID: 1
             * timeStatusesID: 2
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: default: null
             * creationDate: default: null
             * modificationUser: default: null
             * modificationDate: default: null
             */
            entry = new TimeEntry();
            entry.setCompanyId(1);
            entry.setDescription(DESCRIPTION);
            entry.setDate(CREATION_DATE);
            entry.setTaskTypeId(type.getPrimaryId());
            entry.setTimeStatusId(status.getPrimaryId());
            entry.addRejectReason(reason1);
            entry.addRejectReason(reason2);
            timeEntryDAO.create(entry, CREATION_USER);

            // check whether the record has been added
            List returnTimeEntrys = V1Dot1TestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly created in the database", 1, returnTimeEntrys.size());

            // get the record
            TimeEntry returnTimeEntry = (TimeEntry) returnTimeEntrys.get(0);
            returnTimeEntry = (TimeEntry) timeEntryDAO.get(returnTimeEntry.getPrimaryId() + 1);

            // assert the return value is null
            assertNull("record should be got", returnTimeEntry);
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause). The where clause id null, all the entries should be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList_Accuracy1() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
                myTimeEntrys[i].addRejectReason(reason1);
                myTimeEntrys[i].addRejectReason(reason2);
                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // assert records properly got from database
            List returnTimeEntrys = timeEntryDAO.getList(null);

            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause). With whereClause = "creation_user = \'ivern\'"
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList_Accuracy() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
                myTimeEntrys[i].addRejectReason(reason1);
                myTimeEntrys[i].addRejectReason(reason2);
                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // assert records properly got from database
            List returnTimeEntrys = timeEntryDAO.getList("creation_user = \'ivern\'");

            assertEquals("not correctly record returned from the TimeEntries table", 0, returnTimeEntrys.size());
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause). With whereClause = "creation_user = CREATIONUSER"
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList_Accuracy3() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            String whereCause = "creation_user=\'" + CREATION_USER + "\'";
            List returnTimeEntrys = timeEntryDAO.getList(whereCause);

            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                        myTimeEntrys[i], (TimeEntry) returnTimeEntrys.get(i));
                assertEquals("not correctly record returned from the TimeEntries table",
                        (i % 2 == 1) ? reason1 : reason2 ,
                        ((TimeEntry) returnTimeEntrys.get(i)).getAllRejectReasons()[0]);
            }
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the given dataObjects is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NullDataObjects() throws Exception {
        try {
            timeEntryDAO.batchCreate(null, CREATION_USER, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the given dataObjects is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_EmptyDataObjects() throws Exception {
        try {
            timeEntryDAO.batchCreate(new DataObject[0], CREATION_USER, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the given dataObjects contains null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_DataObjectsContainsNull() throws Exception {
        try {
            timeEntryDAO.batchCreate(new DataObject[] { null }, CREATION_USER, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the given dataObjects contains invalid
     * type, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_DataObjectsContainsInvalidType() throws Exception {
        try {
            timeEntryDAO.batchCreate(new DataObject[] { new TimeStatus() }, CREATION_USER, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the given user is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NullUser() throws Exception {
        try {
            timeEntryDAO.batchCreate(new DataObject[] { new TimeEntry() }, null, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the given user is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_EmptyUser() throws Exception {
        try {
            timeEntryDAO.batchCreate(new DataObject[] { new TimeEntry() }, " ", true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the given resultData is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NullResultData() throws Exception {
        try {
            timeEntryDAO.batchCreate(new DataObject[] { new TimeEntry() }, CREATION_USER, true, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the connection can not be got,
     * BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_ErrorConnectoin1() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
            timeEntryDAO.batchCreate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, true, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the connection can not be got,
     * BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_ErrorConnectoin2() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
            timeEntryDAO.batchCreate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, true, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the connection can not be got,
     * BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_ErrorConnectoin3() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
            timeEntryDAO.batchCreate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, false, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when the connection can not be got,
     * BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_ErrorConnectoin4() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
            timeEntryDAO.batchCreate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, false, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when using the own connection. All the records
     * should be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        ResultData resultData = new ResultData();
        timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, true, resultData);

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNull("No exceptions should be occur.", exceptionList);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when using the own connection. The error occurs
     * in the adding process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setTaskTypeId(10);

        ResultData resultData = new ResultData();

        try {
            timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, true, resultData);
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 0, returnTimeEntrys.size());

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set properly.", exceptionList);
        assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertNotNull("Exception should be occur.", exceptionList[9]);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when using the own connection. The error occurs
     * in the adding process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setDescription(null);

        ResultData resultData = new ResultData();

        try {
            timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, true, resultData);
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 0, returnTimeEntrys.size());

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set properly.", exceptionList);
        assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertNotNull("Exception should be occur.", exceptionList[9]);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when not using the own connection. All the
     * records should be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomNotUseOwnConnectionAccuracy() throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            ResultData resultData = new ResultData();
            timeEntryDAO.setConnection(conn);
            timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, true, resultData);

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNull("No exceptions should be occur.", exceptionList);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when not using the own connection. The error
     * occurs in the adding process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomNotUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setTaskTypeId(10);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            timeEntryDAO.setConnection(conn);

            ResultData resultData = new ResultData();

            try {
                timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, true, resultData);
                fail("BatchOperationException is expected.");
            } catch (BatchOperationException e) {
                // good
            }

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 0, returnTimeEntrys.size());

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set properly.", exceptionList);
            assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertNotNull("Exception should be occur.", exceptionList[9]);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when using the own connection. The error occurs
     * in the adding process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomNotUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setDescription(null);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            timeEntryDAO.setConnection(conn);

            ResultData resultData = new ResultData();

            try {
                timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, true, resultData);
                fail("BatchOperationException is expected.");
            } catch (BatchOperationException e) {
                // good
            }

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 0, returnTimeEntrys.size());

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set properly.", exceptionList);
            assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertNotNull("Exception should be occur.", exceptionList[9]);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when using the own connection. All the records
     * should be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        ResultData resultData = new ResultData();
        timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, false, resultData);

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNull("No exceptions should be occur.", exceptionList);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when using the own connection. The error occurs
     * in the adding process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setTaskTypeId(10);

        ResultData resultData = new ResultData();

        timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, false, resultData);

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

        for (int i = 0; i < 9; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set in creating module.", exceptionList);
        assertEquals("The exceptionList field should be set in creating module.", myTimeEntrys.length,
            exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertTrue("one should be occur.", exceptionList[9].getCause() instanceof DAOActionException);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, exceptionList.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when using the own connection. The error occurs
     * in the adding process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setDescription(null);

        ResultData resultData = new ResultData();

        timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, false, resultData);

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

        for (int i = 0; i < 9; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set in creating module.", exceptionList);
        assertEquals("The exceptionList field should be set in creating module.", myTimeEntrys.length,
            exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertTrue("one should be occur.", exceptionList[9].getCause() instanceof IllegalArgumentException);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, exceptionList.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when not using the own connection. All the
     * records should be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomNotUseOwnConnectionAccuracy() throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            ResultData resultData = new ResultData();
            timeEntryDAO.setConnection(conn);
            timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, false, resultData);

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNull("No exceptions should be occur.", exceptionList);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when not using the own connection. The error
     * occurs in the adding process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomNotUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setTaskTypeId(10);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            ResultData resultData = new ResultData();
            timeEntryDAO.setConnection(conn);

            timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, false, resultData);

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

            for (int i = 0; i < 9; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set in creating module.", exceptionList);
            assertEquals("The exceptionList field should be set in creating module.", myTimeEntrys.length,
                exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertTrue("one should be occur.", exceptionList[9].getCause() instanceof DAOActionException);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                exceptionList.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when not using the own connection. The error
     * occurs in the adding process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomNotUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setDescription(null);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            ResultData resultData = new ResultData();
            timeEntryDAO.setConnection(conn);

            timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, false, resultData);

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

            for (int i = 0; i < 9; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set in creating module.", exceptionList);
            assertEquals("The exceptionList field should be set in creating module.", myTimeEntrys.length,
                exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertTrue("one should be occur.", exceptionList[9].getCause() instanceof IllegalArgumentException);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                exceptionList.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }

            // check the data in resultData
            assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the given dataObjects is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NullDataObjects() throws Exception {
        try {
            timeEntryDAO.batchUpdate(null, CREATION_USER, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the given dataObjects is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_EmptyDataObjects() throws Exception {
        try {
            timeEntryDAO.batchUpdate(new DataObject[0], CREATION_USER, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the given dataObjects contains null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_DataObjectsContainsNull() throws Exception {
        try {
            timeEntryDAO.batchUpdate(new DataObject[] { null }, CREATION_USER, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the given dataObjects contains invalid
     * type, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_DataObjectsContainsInvalidType() throws Exception {
        try {
            timeEntryDAO.batchUpdate(new DataObject[] { new TimeStatus() }, CREATION_USER, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the given user is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NullUser() throws Exception {
        try {
            timeEntryDAO.batchUpdate(new DataObject[] { new TimeEntry() }, null, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the given user is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_EmptyUser() throws Exception {
        try {
            timeEntryDAO.batchUpdate(new DataObject[] { new TimeEntry() }, " ", true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the given resultData is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NullResultData() throws Exception {
        try {
            timeEntryDAO.batchUpdate(new DataObject[] { new TimeEntry() }, CREATION_USER, true, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the connection can not be got,
     * BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_ErrorConnectoin1() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
            timeEntryDAO.batchUpdate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, true, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the connection can not be got,
     * BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_ErrorConnectoin2() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
            timeEntryDAO.batchUpdate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, true, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the connection can not be got,
     * BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_ErrorConnectoin3() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
            timeEntryDAO.batchUpdate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, false, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when the connection can not be got,
     * BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_ErrorConnectoin4() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
            timeEntryDAO.batchUpdate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, false, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when using the own connection. All the records
     * should be updated into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        ResultData resultData = new ResultData();
        timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, true, resultData);

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNull("No exceptions should be occur.", exceptionList);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in updating module.", operations);
        assertEquals("The operations field should be set in updating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when using the own connection. The error occurs
     * in the updating process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setTaskTypeId(10);

        ResultData resultData = new ResultData();

        try {
            timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, true, resultData);
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set properly.", exceptionList);
        assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertNotNull("Exception should be occur.", exceptionList[9]);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when using the own connection. The error occurs
     * in the updating process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setDescription(null);

        ResultData resultData = new ResultData();

        try {
            timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, true, resultData);
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set properly.", exceptionList);
        assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertNotNull("Exception should be occur.", exceptionList[9]);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when not using the own connection. All the
     * records should be updated into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomNotUseOwnConnectionAccuracy() throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            ResultData resultData = new ResultData();
            timeEntryDAO.setConnection(conn);
            timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, true, resultData);

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNull("No exceptions should be occur.", exceptionList);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in updating module.", operations);
            assertEquals("The operations field should be set in updating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when not using the own connection. The error
     * occurs in the updating process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomNotUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setTaskTypeId(10);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            timeEntryDAO.setConnection(conn);

            ResultData resultData = new ResultData();

            try {
                timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, true, resultData);
                fail("BatchOperationException is expected.");
            } catch (BatchOperationException e) {
                // good
            }

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set properly.", exceptionList);
            assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertNotNull("Exception should be occur.", exceptionList[9]);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when using the own connection. The error occurs
     * in the updating process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomNotUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setDescription(null);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            timeEntryDAO.setConnection(conn);

            ResultData resultData = new ResultData();

            try {
                timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, true, resultData);
                fail("BatchOperationException is expected.");
            } catch (BatchOperationException e) {
                // good
            }

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set properly.", exceptionList);
            assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertNotNull("Exception should be occur.", exceptionList[9]);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when using the own connection. All the records
     * should be updated into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        ResultData resultData = new ResultData();
        timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, false, resultData);

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNull("No exceptions should be occur.", exceptionList);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in updating module.", operations);
        assertEquals("The operations field should be set in updating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when using the own connection. The error occurs
     * in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setTaskTypeId(10);

        ResultData resultData = new ResultData();

        timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, false, resultData);

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 9; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set in updating module.", exceptionList);
        assertEquals("The exceptionList field should be set in updating module.", myTimeEntrys.length,
            exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertTrue("one should be occur.", exceptionList[9].getCause() instanceof DAOActionException);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in updating module.", operations);
        assertEquals("The operations field should be set in updating module.", operations.length, exceptionList.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when using the own connection. The error occurs
     * in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setDescription(null);

        ResultData resultData = new ResultData();

        timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, false, resultData);

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 9; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set in updating module.", exceptionList);
        assertEquals("The exceptionList field should be set in updating module.", myTimeEntrys.length,
            exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertTrue("one should be occur.", exceptionList[9].getCause() instanceof IllegalArgumentException);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in updating module.", operations);
        assertEquals("The operations field should be set in updating module.", operations.length, exceptionList.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when not using the own connection. All the
     * records should be updated into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomNotUseOwnConnectionAccuracy() throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            ResultData resultData = new ResultData();
            timeEntryDAO.setConnection(conn);
            timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, false, resultData);

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNull("No exceptions should be occur.", exceptionList);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in updating module.", operations);
            assertEquals("The operations field should be set in updating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when not using the own connection. The error
     * occurs in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomNotUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setTaskTypeId(10);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            ResultData resultData = new ResultData();
            timeEntryDAO.setConnection(conn);

            timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, false, resultData);

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 9; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set in updating module.", exceptionList);
            assertEquals("The exceptionList field should be set in updating module.", myTimeEntrys.length,
                exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertTrue("one should be occur.", exceptionList[9].getCause() instanceof DAOActionException);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in updating module.", operations);
            assertEquals("The operations field should be set in updating module.", operations.length,
                exceptionList.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData) when not using the own connection. The error
     * occurs in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomNotUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setDescription(null);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            ResultData resultData = new ResultData();
            timeEntryDAO.setConnection(conn);

            timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, false, resultData);

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 9; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set in updating module.", exceptionList);
            assertEquals("The exceptionList field should be set in updating module.", myTimeEntrys.length,
                exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertTrue("one should be occur.", exceptionList[9].getCause() instanceof IllegalArgumentException);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in updating module.", operations);
            assertEquals("The operations field should be set in updating module.", operations.length,
                exceptionList.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultData) when the given idList is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_NullIdList() throws Exception {
        try {
            timeEntryDAO.batchDelete(null, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultData) when the given idList is empty, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_EmptyIdList() throws Exception {
        try {
            timeEntryDAO.batchDelete(new int[0], true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultData) when the given resultData is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_NullResultData() throws Exception {
        try {
            timeEntryDAO.batchDelete(new int[] { 1 }, true, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultData) when the connection can not be got, BatchOperationException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_ErrorConnectoin1() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
            timeEntryDAO.batchDelete(new int[1], true, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultData) when the connection can not be got, BatchOperationException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_ErrorConnectoin2() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
            timeEntryDAO.batchDelete(new int[1], true, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultData) when the connection can not be got, BatchOperationException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_ErrorConnectoin3() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
            timeEntryDAO.batchDelete(new int[1], false, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultData) when the connection can not be got, BatchOperationException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_ErrorConnectoin4() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
            timeEntryDAO.batchDelete(new int[1], false, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of batchDelete(int[], boolean, ResultData).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_Accuracy() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
                myTimeEntrys[i].addRejectReason(reason1);
                myTimeEntrys[i].addRejectReason(reason2);

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // delete it
            int[] ids = new int[myTimeEntrys.length];

            for (int i = 0; i < myTimeEntrys.length; i++) {
                ids[i] = myTimeEntrys[i].getPrimaryId();
            }

            ResultData resultData = new ResultData();
            timeEntryDAO.batchDelete(ids, true, resultData);

            // assert there is no records in the database now
            assertEquals("record was not properly deleted in the database", 0,
                V1Dot1TestHelper.selectTimeEntries(conn).size());

            // The links should also be deleted from the time_reject_reason table
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");
            assertFalse("The links should also be deleted from the time_reject_reason table.", resultSet.next());

            // check the baseDAO's connection field
            assertNull("The baseDAO's connection should be null.", timeEntryDAO.getConnection());

            // check the data in resultData
            assertNull("The batchResults field should not be set in deleting module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNull("No exceptions should be occur.", exceptionList);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in deleting module.", operations);
            assertEquals("The operations field should be set in deleting module.", operations.length,
                myTimeEntrys.length);

            Integer[] expected = new Integer[myTimeEntrys.length];

            for (int i = 0; i < myTimeEntrys.length; i++) {
                expected[i] = new Integer(ids[i]);
            }

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in deleting module.", expected[i], operations[i]);
            }
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultData) when the given idList is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_NullIdList() throws Exception {
        try {
            timeEntryDAO.batchRead(null, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultData) when the given idList is empty, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_EmptyIdList() throws Exception {
        try {
            timeEntryDAO.batchRead(new int[0], true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultData) when the given resultData is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_NullResultData() throws Exception {
        try {
            timeEntryDAO.batchRead(new int[] { 1 }, true, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultData) when the connection can not be got, BatchOperationException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_ErrorConnectoin1() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
            timeEntryDAO.batchRead(new int[1], true, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultData) when the connection can not be got, BatchOperationException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_ErrorConnectoin2() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
            timeEntryDAO.batchRead(new int[1], true, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultData) when the connection can not be got, BatchOperationException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_ErrorConnectoin3() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
            timeEntryDAO.batchRead(new int[1], false, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultData) when the connection can not be got, BatchOperationException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_ErrorConnectoin4() throws Exception {
        try {
            timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
            timeEntryDAO.batchRead(new int[1], false, new ResultData());
            fail("BatchOperationException is expected.");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of batchRead(int[], boolean, ResultData).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_Accuracy() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
                myTimeEntrys[i].addRejectReason(reason1);
                myTimeEntrys[i].addRejectReason(reason2);

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // read it
            int[] ids = new int[myTimeEntrys.length];

            for (int i = 0; i < myTimeEntrys.length; i++) {
                ids[i] = myTimeEntrys[i].getPrimaryId();
            }

            ResultData resultData = new ResultData();
            timeEntryDAO.batchRead(ids, true, resultData);

            // assert there is no records in the database now
            assertEquals("record was not properly deleted in the database", 10,
                V1Dot1TestHelper.selectTimeEntries(conn).size());

            // check the baseDAO's connection field
            assertNull("The baseDAO's connection should be null.", timeEntryDAO.getConnection());

            // check the data in resultData
            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNull("No exceptions should be occur.", exceptionList);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in reading module.", operations);
            assertEquals("The operations field should be set in reading module.",
                operations.length, myTimeEntrys.length);

            Integer[] expected = new Integer[myTimeEntrys.length];

            for (int i = 0; i < myTimeEntrys.length; i++) {
                expected[i] = new Integer(ids[i]);
            }

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in reading module.", expected[i], operations[i]);
            }

            // check the data in resultData
            assertNotNull("The batchResults field should be set in reading module.", resultData.getBatchResults());

            assertEquals("The batchResults field should be set in reading module.", myTimeEntrys.length,
                resultData.getBatchResults().length);

            for (int i = 0; i < myTimeEntrys.length; i++) {
                assertEquals("The batchResults field should be set in reading module.", myTimeEntrys[i],
                    (TimeEntry) resultData.getBatchResults()[i]);
            }
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * judge whether the two TimeEntry are equals. They are equals when all their fields contain the same value.
     * </p>
     *
     * @param message the error message when the two entry are not equal.
     * @param expected the expected TimeEntry.
     * @param actual the actual TimeEntry.
     */
    private void assertEquals(String message, TimeEntry expected, TimeEntry actual) {
        assertEquals(message, expected.getPrimaryId(), actual.getPrimaryId());
        assertEquals(message, expected.getTaskTypeId(), actual.getTaskTypeId());
        assertEquals(message, expected.getTimeStatusId(), actual.getTimeStatusId());
        V1Dot1TestHelper.assertEquals(message, expected.getDate(), actual.getDate());
        assertEquals(message, expected.getHours(), actual.getHours(), 1e-8);
        assertEquals(message, expected.isBillable(), actual.isBillable());
        assertEquals(message, expected.getDescription(), actual.getDescription());
        assertEquals(message, expected.getCreationUser(), actual.getCreationUser());
        V1Dot1TestHelper.assertEquals(message, expected.getCreationDate(), actual.getCreationDate());
        assertEquals(message, expected.getModificationUser(), actual.getModificationUser());
        V1Dot1TestHelper.assertEquals(message, expected.getModificationDate(), actual.getModificationDate());
        assertEquals(message, expected.getAllRejectReasons().length, actual.getAllRejectReasons().length);
    }

    /**
     * <p>
     * judge whether the two RejectReason are equals. They are equals when all their fields contain the same value.
     * </p>
     *
     * @param message the error message when the two entry are not equal.
     * @param expected the expected RejectReason.
     * @param actual the actual RejectReason.
     */
    private void assertEquals(String msg, RejectReason expected, RejectReason actual) {
        assertEquals(msg, expected.getCreationDate().toString(), actual.getCreationDate().toString());
        assertEquals(msg, expected.getCreationUser(), actual.getCreationUser());
        assertEquals(msg, expected.getDescription(), actual.getDescription());
        assertEquals(msg, expected.getModificationDate().toString(), actual.getModificationDate().toString());
        assertEquals(msg, expected.getModificationUser(), actual.getModificationUser());
        assertEquals(msg, expected.getPrimaryId(), actual.getPrimaryId());
    }

    /**
     * <p>
     * return a TimeEntry instance for testing.
     * </p>
     *
     * @return a TimeEntry instance for testing.
     */
    private TimeEntry getTimeEntry() {
        TimeEntry myTimeEntry = new TimeEntry();
        myTimeEntry.setCompanyId(1);
        myTimeEntry.setDescription(DESCRIPTION);
        myTimeEntry.setCreationUser(CREATION_USER);
        myTimeEntry.setCreationDate(CREATION_DATE);
        myTimeEntry.setModificationUser(CREATION_USER);
        myTimeEntry.setModificationDate(CREATION_DATE);
        myTimeEntry.setDate(CREATION_DATE);
        myTimeEntry.setTaskTypeId(type.getPrimaryId());
        myTimeEntry.setTimeStatusId(status.getPrimaryId());

        return myTimeEntry;
    }
}
