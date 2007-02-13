/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.DataObject;
import com.topcoder.timetracker.entry.time.RejectReason;
import com.topcoder.timetracker.entry.time.RejectReasonDAO;

/**
 * <p>
 * Failure tests for RejectReasonDAO.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
public class RejectReasonDAOFailureTests extends TestCase {
    /**
     * RejectReasonDAO instance for testing.
     */
    private MockRejectReasonDAO dao;

    /**
     * <p>
     * Set up tests.
     * </p>
     * @throws Exception to JUnit
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        dao = new MockRejectReasonDAO("mysql", "com.topcoder.timetracker.entry.time.failuretests");
    }

    /**
     * Failure test for RejectReasonDAO(String, String),
     * with null connection name, NPE is expected.
     */
    public void testRejectReasonDAONullConnName() {
        try {
            new RejectReasonDAO(null, "com.topcoder.timetracker.entry.time.failuretests");
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * Failure test for RejectReasonDAO(String, String),
     * with null namespace, NPE is expected.
     */
    public void testRejectReasonDAONullNamespace() {
        try {
            new RejectReasonDAO("mysql", null);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException e) {
            // expected
        }
    }

    /**
     * Failure test for RejectReasonDAO(String, String),
     * with empty connection name, IAE is expected.
     */
    public void testRejectReasonDAOEmptyConnName() {
        try {
            new RejectReasonDAO("       ", "com.topcoder.timetracker.entry.time.failuretests");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for RejectReasonDAO(String, String),
     * with empty namespace, IAE is expected.
     */
    public void testRejectReasonDAOEmptyNamespace() {
        try {
            new RejectReasonDAO("mysql", "      ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * Failure test for verifyCreateDataObject(DataObject),
     * with null DataObject, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testVerifyCreateDataObjectNullDataObject() throws Exception {
        try {
            dao.verifyCreateDataObject(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for verifyCreateDataObject(DataObject),
     * with non RejectReason DataObject, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testVerifyCreateDataObjectNonRejectReason() throws Exception {
        try {
            dao.verifyCreateDataObject(new DataObject() {});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for verifyCreateDataObject(DataObject),
     * with RejectReason DataObject whose description is null, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testVerifyCreateDataObjectRejectReasonNoDesc() throws Exception {
        try {
            dao.verifyCreateDataObject(new RejectReason());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for verifyUpdateDataObject(DataObject),
     * with null DataObject, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testVerifyUpdateDataObjectNullDataObject() throws Exception {
        try {
            dao.verifyUpdateDataObject(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for verifyUpdateDataObject(DataObject),
     * with non RejectReason DataObject, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testVerifyUpdateDataObjectNonRejectReason() throws Exception {
        try {
            dao.verifyUpdateDataObject(new DataObject() {});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for verifyUpdateDataObject(DataObject),
     * with RejectReason DataObject whose description is null, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testVerifyUpdateDataObjectRejectReasonNoDesc() throws Exception {
        RejectReason reason = new RejectReason();
        reason.setCreationDate(new Date());
        reason.setModificationDate(new Date());
        
        try {
            dao.verifyUpdateDataObject(reason);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for verifyUpdateDataObject(DataObject),
     * with RejectReason DataObject whose date member is null, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testVerifyUpdateDataObjectRejectReasonNoDate() throws Exception {
        RejectReason reason = new RejectReason();
        reason.setDescription("desc");
        
        try {
            dao.verifyUpdateDataObject(reason);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
