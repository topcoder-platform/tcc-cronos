/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.model;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link CompetitionDocument}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompetitionDocumentUnitTests extends TestCase {
    /**
     * <p>
     * Represents the <code>CompetitionDocument</code> instance for testing.
     * </p>
     */
    private CompetitionDocument competitionDocument;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(CompetitionDocumentUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             If any problem occurs.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        competitionDocument = new CompetitionDocument();
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             If any problem occurs.
     */
    @Override
    protected void tearDown() throws Exception {
        // explicitly set null.
        competitionDocument = null;

        super.tearDown();
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#CompetitionDocument()</code> constructor.
     * </p>
     * <p>
     * This method simply do nothing, should always create the instance.
     * </p>
     */
    public void testDocumentNameAccuracy() {
        assertNotNull("Instance should always create.", competitionDocument);
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#getCompetitionDocumentId()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetCompetitionDocumentId_default() {
        assertNull("Should return null.", competitionDocument.getCompetitionDocumentId());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setCompetitionDocumentId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetCompetitionDocumentId_null() {
        try {
            competitionDocument.setCompetitionDocumentId(null);
            fail("If the parameter is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setCompetitionDocumentId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetCompetitionDocumentId_notNull() {
        Long competitionDocumentId = 1L;

        competitionDocument.setCompetitionDocumentId(competitionDocumentId);

        assertEquals("The document id is not set properly.", competitionDocumentId, competitionDocument
                .getCompetitionDocumentId());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#getDocumentVersion()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetDocumentVersion_default() {
        assertNull("Should return null.", competitionDocument.getDocumentVersion());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the parameter is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetDocumentVersion_null() {
        try {
            competitionDocument.setDocumentVersion(null);
            fail("If the parameter is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setDocumentVersion(DocumentVersion)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetDocumentVersion_notNull() {
        DocumentVersion documentVersion = new DocumentVersion();

        competitionDocument.setDocumentVersion(documentVersion);

        assertSame("The document version is not set properly.", documentVersion, competitionDocument
                .getDocumentVersion());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#getCompetitionId()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetCompetitionId_default() {
        assertNull("Should return null.", competitionDocument.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setCompetitionId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetCompetitionId_null() {
        try {
            competitionDocument.setCompetitionId(null);
            fail("If the parameter is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setCompetitionId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetCompetitionId_notNull() {
        Long competitionId = 1L;

        competitionDocument.setCompetitionId(competitionId);

        assertEquals("The competition id is not set properly.", competitionId, competitionDocument.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#getRoleId()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetRoleId_default() {
        assertNull("Should return null.", competitionDocument.getRoleId());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setRoleId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetRoleId_null() {
        try {
            competitionDocument.setRoleId(null);
            fail("If the parameter is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setRoleId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetRoleId_notNull() {
        Long competitionId = 1L;

        competitionDocument.setRoleId(competitionId);

        assertEquals("The role id is not set properly.", competitionId, competitionDocument.getRoleId());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#getMemberAnswers()</code> method.
     * </p>
     * <p>
     * It should return empty set, if not set.
     * </p>
     */
    public void testGetMemberAnswers_default() {
        Set<MemberAnswer> memberAnswers = competitionDocument.getMemberAnswers();
        assertNotNull("It should never return null.", memberAnswers);
        assertTrue("It should be empty by default.", memberAnswers.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setMemberAnswers(Set&lt;MemberAnswer&gt;)</code> method.
     * </p>
     * <p>
     * If memberAnswers set is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetMemberAnswers_null() {
        try {
            competitionDocument.setMemberAnswers(null);
            fail("If memberAnswers set is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setMemberAnswers(Set&lt;MemberAnswer&gt;)</code> method.
     * </p>
     * <p>
     * If memberAnswers set contains null element, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetMemberAnswers_nullElement() {
        Set<MemberAnswer> memberAnswers = new HashSet<MemberAnswer>();
        memberAnswers.add(null);

        try {
            competitionDocument.setMemberAnswers(memberAnswers);
            fail("If memberAnswers set contains null element, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocument#setMemberAnswers(Set&lt;MemberAnswer&gt;)</code> method.
     * </p>
     */
    public void testSetMemberAnswers_accuracy() {
        Set<MemberAnswer> memberAnswers = new HashSet<MemberAnswer>();
        memberAnswers.add(new MemberAnswer());

        competitionDocument.setMemberAnswers(memberAnswers);

        memberAnswers = competitionDocument.getMemberAnswers();
        assertNotNull("It should never return null.", memberAnswers);
        assertFalse("It should be not empty.", memberAnswers.isEmpty());
    }
}
