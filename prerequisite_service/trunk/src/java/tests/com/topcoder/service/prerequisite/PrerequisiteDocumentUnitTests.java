/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import javax.xml.datatype.DatatypeFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link PrerequisiteDocument}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PrerequisiteDocumentUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>PrerequisiteDocument</code> instance used in tests.
     * </p>
     */
    private PrerequisiteDocument prerequisiteDocument;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PrerequisiteDocumentUnitTests.class);
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

        prerequisiteDocument = new PrerequisiteDocument();
    }

    /**
     * <p>
     * Unit test <code>PrerequisiteDocument#PrerequisiteDocument()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testPrerequisiteDocument_accuracy() {
        assertNotNull("Instance should be created in constructor.", prerequisiteDocument);
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#getDocumentId()</code> method.
     * </p>
     * <p>
     * By default, it should return -1.
     * </p>
     */
    public void testGetDocumentId_default() {
        assertEquals("Should return -1.", -1, prerequisiteDocument.getDocumentId());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#setDocumentId(long)</code> method.
     * </p>
     * <p>
     * If the documentId < -1, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetDocumentId_iae() {
        try {
            prerequisiteDocument.setDocumentId(-2);
            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#setDocumentId(long)</code> method.
     * </p>
     * <p>
     * If the documentId >= -1, should set the document id internally.
     * </p>
     */
    public void testSetDocumentId_accuracy() {
        prerequisiteDocument.setDocumentId(-1);
        assertEquals("document id does not set.", -1, prerequisiteDocument.getDocumentId());

        prerequisiteDocument.setDocumentId(0);
        assertEquals("document id does not set.", 0, prerequisiteDocument.getDocumentId());

        prerequisiteDocument.setDocumentId(1);
        assertEquals("document id does not set.", 1, prerequisiteDocument.getDocumentId());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#getVersion()</code> method.
     * </p>
     * <p>
     * By default, it should return null.
     * </p>
     */
    public void testGetVersion_default() {
        assertNull("Null expected", prerequisiteDocument.getVersion());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#setVersion(Integer)</code> method.
     * </p>
     * <p>
     * If the version is not null but its value is negative, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetVersion_iae() {
        try {
            prerequisiteDocument.setVersion(-1);
            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#setVersion(Integer)</code> method.
     * </p>
     */
    public void testSetVersion_accuracy() {
        prerequisiteDocument.setVersion(null);
        assertNull("Null expected", prerequisiteDocument.getVersion());

        prerequisiteDocument.setVersion(0);
        assertEquals("version is not set.", 0, prerequisiteDocument.getVersion().intValue());

        prerequisiteDocument.setVersion(1);
        assertEquals("version is not set.", 1, prerequisiteDocument.getVersion().intValue());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#getVersionDate()</code> method.
     * </p>
     * <p>
     * By default, it should return null.
     * </p>
     */
    public void testGetVersionDate_default() {
        assertNull("Null expected", prerequisiteDocument.getVersionDate());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#setVersionDate(XMLGregorianCalendar)</code> method.
     * </p>
     * <p>
     * All value is valid, even null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetVersionDate_accuracy() throws Exception {
        prerequisiteDocument.setVersionDate(null);
        assertNull("Null expected", prerequisiteDocument.getVersionDate());

        prerequisiteDocument.setVersionDate(DatatypeFactory.newInstance().newXMLGregorianCalendar());
        assertNotNull("Null is not expected", prerequisiteDocument.getVersionDate());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#getName()</code> method.
     * </p>
     * <p>
     * By default, it should return null.
     * </p>
     */
    public void testGetName_default() {
        assertNull("Null expected", prerequisiteDocument.getName());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#setName(String)</code> method.
     * </p>
     * <p>
     * All value is valid, even null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetName_accuracy() throws Exception {
        prerequisiteDocument.setName(null);
        assertNull("Null expected", prerequisiteDocument.getName());

        prerequisiteDocument.setName("");
        assertEquals("Incorrect name.", "", prerequisiteDocument.getName());

        prerequisiteDocument.setName(" ");
        assertEquals("Incorrect name.", " ", prerequisiteDocument.getName());

        prerequisiteDocument.setName("Hello");
        assertEquals("Incorrect name.", "Hello", prerequisiteDocument.getName());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#getContent()</code> method.
     * </p>
     * <p>
     * By default, it should return null.
     * </p>
     */
    public void testGetContent_default() {
        assertNull("Null expected", prerequisiteDocument.getContent());
    }

    /**
     * <p>
     * Unit test for <code>PrerequisiteDocument#setContent(String)</code> method.
     * </p>
     * <p>
     * All value is valid, even null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetContent_accuracy() throws Exception {
        prerequisiteDocument.setContent(null);
        assertNull("Null expected", prerequisiteDocument.getContent());

        prerequisiteDocument.setContent("");
        assertEquals("Incorrect name.", "", prerequisiteDocument.getContent());

        prerequisiteDocument.setContent(" ");
        assertEquals("Incorrect name.", " ", prerequisiteDocument.getContent());

        prerequisiteDocument.setContent("Hello");
        assertEquals("Incorrect name.", "Hello", prerequisiteDocument.getContent());
    }
}
