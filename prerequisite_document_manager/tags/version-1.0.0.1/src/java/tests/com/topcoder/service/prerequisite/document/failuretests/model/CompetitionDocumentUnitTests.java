/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.failuretests.model;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;

/**
 * <p>
 * Failure test for CompetitionDocument class.
 * </p>
 * 
 * @author 80x86
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
     * Failure test for setCompetitionDocumentId with null, IllegalArgumentException is expected.
     */
    public void testSetCompetitionDocumentId_Null() {
        try {
            competitionDocument.setCompetitionDocumentId(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setDocumentVersion with null, IllegalArgumentException is expected.
     */
    public void testSetDocumentVersion_Null() {
        try {
            competitionDocument.setDocumentVersion(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setCompetitionId with null, IllegalArgumentException is expected.
     */
    public void testSetCompetitionId_Null() {
        try {
            competitionDocument.setCompetitionId(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setRoleId with null, IllegalArgumentException is expected.
     */
    public void testSetRoleId_Null() {
        try {
            competitionDocument.setRoleId(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setMemberAnswers with null, IllegalArgumentException is expected.
     */
    public void testSetMemberAnswers_Null() {
        try {
            competitionDocument.setMemberAnswers(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setMemberAnswers with memberAnswers contains null, IllegalArgumentException is expected.
     */
    public void testSetMemberAnswers_NullElement() {
        Set<MemberAnswer> memberAnswers = new HashSet<MemberAnswer>();
        memberAnswers.add(null);
        try {
            competitionDocument.setMemberAnswers(memberAnswers);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
