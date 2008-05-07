/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.service.prerequisite.PrerequisiteDocument;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link RecordMemberAnswerRequest}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RecordMemberAnswerRequestUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>RecordMemberAnswerRequest</code> instance.
     * </p>
     */
    private RecordMemberAnswerRequest recordMemberAnswerRequest;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(RecordMemberAnswerRequestUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        recordMemberAnswerRequest = new RecordMemberAnswerRequest();
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#RecordMemberAnswerRequest()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testRecordMemberAnswerRequest_accuracy() {
        assertNotNull("Instance should be always created.", recordMemberAnswerRequest);
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#getCompetitionId()</code> method.
     * </p>
     * <p>
     * It should return 0 by default.
     * </p>
     */
    public void testGetCompetitionId_default() {
        assertEquals("incorrect default value.", 0, recordMemberAnswerRequest.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#setCompetitionId(long)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetCompetitionId_accuracy() {
        recordMemberAnswerRequest.setCompetitionId(-1);
        assertEquals("Incorrect competition id.", -1, recordMemberAnswerRequest.getCompetitionId());

        recordMemberAnswerRequest.setCompetitionId(0);
        assertEquals("Incorrect competition id.", 0, recordMemberAnswerRequest.getCompetitionId());

        recordMemberAnswerRequest.setCompetitionId(1);
        assertEquals("Incorrect competition id.", 1, recordMemberAnswerRequest.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#getTimestamp()</code> method.
     * </p>
     * <p>
     * By default, it should return null.
     * </p>
     */
    public void testGetTimestamp_default() {
        assertNull("Null expected", recordMemberAnswerRequest.getTimestamp());
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#setTimestamp(Integer)</code> method.
     * </p>
     * <p>
     * all value is valid.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetTimestamp_accuracy() throws Exception {
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        recordMemberAnswerRequest.setTimestamp(xmlGregorianCalendar);
        assertSame("timestamp is not set.", xmlGregorianCalendar, recordMemberAnswerRequest.getTimestamp());

        recordMemberAnswerRequest.setTimestamp(null);
        assertNull("Null expected", recordMemberAnswerRequest.getTimestamp());
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#isAgrees()</code> method.
     * </p>
     * <p>
     * By default, it should return false.
     * </p>
     */
    public void testIsAgrees_default() {
        assertFalse("false expected", recordMemberAnswerRequest.isAgrees());
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#setAgrees(boolean)</code> method.
     * </p>
     * <p>
     * all value is valid.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetAgrees_accuracy() throws Exception {
        recordMemberAnswerRequest.setAgrees(true);
        assertTrue("agrees is not set.", recordMemberAnswerRequest.isAgrees());
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#getPrerequisiteDocument()</code> method.
     * </p>
     * <p>
     * By default, it should return null.
     * </p>
     */
    public void testGetPrerequisiteDocument_default() {
        assertNull("Null expected", recordMemberAnswerRequest.getPrerequisiteDocument());
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#setPrerequisiteDocument(PrerequisiteDocument)</code>
     * method.
     * </p>
     * <p>
     * all value is valid.
     * </p>
     */
    public void testSetPrerequisiteDocument_accuracy() {
        PrerequisiteDocument prerequisiteDocument = new PrerequisiteDocument();

        recordMemberAnswerRequest.setPrerequisiteDocument(prerequisiteDocument);
        assertSame("prerequisiteDocument is not set.", prerequisiteDocument, recordMemberAnswerRequest
                .getPrerequisiteDocument());

        recordMemberAnswerRequest.setPrerequisiteDocument(null);
        assertNull("Null expected", recordMemberAnswerRequest.getPrerequisiteDocument());
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#getRoleId()</code> method.
     * </p>
     * <p>
     * It should return 0 by default.
     * </p>
     */
    public void testGetRoleId_default() {
        assertEquals("incorrect default value.", 0, recordMemberAnswerRequest.getRoleId());
    }

    /**
     * <p>
     * Unit test for <code>RecordMemberAnswerRequest#setRoleId(long)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetRoleId_accuracy() {
        recordMemberAnswerRequest.setRoleId(-1);
        assertEquals("Incorrect role id.", -1, recordMemberAnswerRequest.getRoleId());

        recordMemberAnswerRequest.setRoleId(0);
        assertEquals("Incorrect role id.", 0, recordMemberAnswerRequest.getRoleId());

        recordMemberAnswerRequest.setRoleId(1);
        assertEquals("Incorrect role id.", 1, recordMemberAnswerRequest.getRoleId());
    }
}
