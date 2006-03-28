/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.entry.time.accuracytests;

import com.cronos.timetracker.entry.time.BaseDAO;
import com.cronos.timetracker.entry.time.RejectReason;
import com.cronos.timetracker.entry.time.RejectReasonDAO;

import junit.framework.TestCase;

import java.sql.Connection;

import java.util.List;


/**
 * <p>
 * Accuracy test cases for RejectReasonDAO.
 * </p>
 *
 * @author oodinary
 * @version 1.1
 */
public class RejectReasonDAOAccuracyTest extends TestCase {
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
     * The name of the Connection to fetch from the DBConnectionFactory.
     * </p>
     */
    private static final String CONNAME = "informix";

    /**
     * <p>
     * Namespace to be used in the DBConnection Factory component to load the configuration.
     * </p>
     */
    private static final String NAMESPACE = "com.cronos.timetracker.entry.time.myconfig";

    /**
     * <p>
     * The RejectReasonDAO instance for testing.
     * </p>
     */
    private BaseDAO rejectReasonDAO = null;

    /**
     * <p>
     * RejectReason instance for testing.
     * </p>
     */
    private RejectReason rejectReason = new RejectReason();

    /**
     * <p>
     * The connection used for testing.
     * </p>
     */
    private Connection conn = null;

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    protected void setUp() throws Exception {
        // load the configuration
        AccuracyTestHelper.clearConfiguration();
        AccuracyTestHelper.addValidConfiguration();

        // delete all the records in all tables
        conn = AccuracyTestHelper.getConnection(NAMESPACE, CONNAME);
        AccuracyTestHelper.clearDatabase(conn);

        // creat the instance
        this.rejectReasonDAO = new RejectReasonDAO(CONNAME, NAMESPACE);
        rejectReason.setDescription(DESCRIPTION);
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        // clear the configuration
        AccuracyTestHelper.clearConfiguration();

        // delete all the records in all tables
        AccuracyTestHelper.clearDatabase(conn);

        conn.close();
    }

    /**
     * <p>
     * Tests the create(DataObject dataObject, String user).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testCreate_Accuracy() throws Exception {
        rejectReasonDAO.create(rejectReason, CREATION_USER);

        List returnRejectReasons = AccuracyTestHelper.selectRejectReasons(conn);
        assertEquals("record was not properly created in the database", 1, returnRejectReasons.size());

        RejectReason returnRejectReason = (RejectReason) returnRejectReasons.get(0);
        assertEquals("record was not properly created in the database", CREATION_USER,
            returnRejectReason.getCreationUser());
        assertEquals("record was not properly created in the database", CREATION_USER,
            returnRejectReason.getModificationUser());
        assertNotNull("record was not properly created in the database", returnRejectReason.getCreationDate());
        assertNotNull("record was not properly created in the database", returnRejectReason.getModificationDate());

        /*
         * The rejectReason will also be changed
         */
        assertEquals("rejectReason was not properly updated", CREATION_USER, rejectReason.getCreationUser());
        assertEquals("rejectReason was not properly updated", CREATION_USER, rejectReason.getModificationUser());
        assertNotNull("rejectReason was not properly updated", rejectReason.getCreationDate());
        assertNotNull("rejectReason was not properly updated", rejectReason.getModificationDate());
    }

    /**
     * <p>
     * Tests the update(DataObject dataObject, String user).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testUpdate() throws Exception {
        rejectReasonDAO.create(rejectReason, CREATION_USER);

        rejectReason.setDescription(MODIFICATION_USER);

        rejectReasonDAO.update(rejectReason, MODIFICATION_USER);

        List returnRejectReasons = AccuracyTestHelper.selectRejectReasons(conn);
        assertEquals("record was not properly updated in the database", 1, returnRejectReasons.size());

        RejectReason returnRejectReason = (RejectReason) returnRejectReasons.get(0);
        assertEquals("record was not properly updated in the database", CREATION_USER,
            returnRejectReason.getCreationUser());
        assertEquals("record was not properly updated in the database", MODIFICATION_USER,
            returnRejectReason.getModificationUser());

        assertEquals("myRejectReason was not properly updated", CREATION_USER, rejectReason.getCreationUser());
        assertEquals("myRejectReason was not properly updated", MODIFICATION_USER, rejectReason.getModificationUser());
    }

    /**
     * <p>
     * Tests the delete(int primaryId).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testDelete_Accuracy() throws Exception {
        rejectReasonDAO.create(rejectReason, CREATION_USER);

        // delete the record
        rejectReasonDAO.delete(rejectReason.getPrimaryId());

        // assert there is no records in the database now
        List returnRejectReasons = AccuracyTestHelper.selectRejectReasons(conn);
        assertEquals("record was not properly deleted in the database", 0, returnRejectReasons.size());
    }

    /**
     * <p>
     * Tests the get(int primaryId).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGet_Accuracy() throws Exception {
        rejectReasonDAO.create(rejectReason, CREATION_USER);

        // get the record
        RejectReason returnRejectReason = (RejectReason) rejectReasonDAO.get(rejectReason.getPrimaryId());

        // assert the return value is the exactly one which has just inserted into the database
        AccuracyTestHelper.assertEquals("record was not properly got", rejectReason, returnRejectReason);
    }

    /**
     * <p>
     * Tests the getList(String whereClause).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList_Accuarcy() throws Exception {
        RejectReason[] rejectReasons = new RejectReason[3];

        for (int i = 0; i < 3; i++) {
            rejectReasons[i] = new RejectReason();
            rejectReasons[i].setDescription(DESCRIPTION);

            if (i == 0) {
                rejectReasonDAO.create(rejectReasons[i], CREATION_USER);
            } else {
                rejectReasonDAO.create(rejectReasons[i], MODIFICATION_USER);
            }
        }

        List list = rejectReasonDAO.getList("Creation_User='" + MODIFICATION_USER + "'");
        assertEquals("there should be 2 records", 2, list.size());
        list = rejectReasonDAO.getList("Creation_User='" + CREATION_USER + "'");
        assertEquals("there should be 1 records", 1, list.size());
        list = rejectReasonDAO.getList("   ");
        assertEquals("there should be 3 records", 3, list.size());
    }
}
