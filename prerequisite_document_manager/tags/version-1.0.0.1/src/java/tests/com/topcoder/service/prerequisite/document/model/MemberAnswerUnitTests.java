/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.model;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link MemberAnswer}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberAnswerUnitTests extends TestCase {
    /**
     * <p>
     * Represents the <code>MemberAnswer</code> instance for testing.
     * </p>
     */
    private MemberAnswer memberAnswer;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(MemberAnswerUnitTests.class);
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

        memberAnswer = new MemberAnswer();
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
        memberAnswer = null;

        super.tearDown();
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#MemberAnswer()</code> constructor.
     * </p>
     * <p>
     * This method simply do nothing, should always create the instance.
     * </p>
     */
    public void testDocumentNameAccuracy() {
        assertNotNull("Instance should always create.", memberAnswer);
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#getMemberAnswerId()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetMemberAnswerId_default() {
        assertNull("Should return null.", memberAnswer.getMemberAnswerId());
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#setMemberAnswerId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetMemberAnswerId_null() {
        try {
            memberAnswer.setMemberAnswerId(null);
            fail("If the parameter is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#setMemberAnswerId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetMemberAnswerId_notNull() {
        Long memberAnswerId = 1L;

        memberAnswer.setMemberAnswerId(memberAnswerId);

        assertEquals("The member answer id is not set properly.", memberAnswerId, memberAnswer.getMemberAnswerId());
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#getCompetitionDocument()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetCompetitionDocument_default() {
        assertNull("Should return null.", memberAnswer.getCompetitionDocument());
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#setCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the parameter is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetCompetitionDocument_null() {
        try {
            memberAnswer.setCompetitionDocument(null);
            fail("If the parameter is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#setCompetitionDocument(CompetitionDocument)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetCompetitionDocument_notNull() {
        CompetitionDocument competitionDocument = new CompetitionDocument();

        memberAnswer.setCompetitionDocument(competitionDocument);

        assertEquals("The competition document is not set properly.", competitionDocument, memberAnswer
                .getCompetitionDocument());
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#getMemberId()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetMemberId_default() {
        assertNull("Should return null.", memberAnswer.getMemberId());
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#setMemberId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetMemberId_null() {
        try {
            memberAnswer.setMemberId(null);
            fail("If the parameter is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#setMemberId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetMemberId_notNull() {
        Long memberId = 1L;

        memberAnswer.setMemberId(memberId);

        assertEquals("The member id is not set properly.", memberId, memberAnswer.getMemberId());
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#getAnswer()</code> method.
     * </p>
     * <p>
     * It should return false by default.
     * </p>
     */
    public void testGetAnswer_default() {
        assertFalse("It should return false by default.", memberAnswer.getAnswer());
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#setAnswer(boolean)</code> method.
     * </p>
     * <p>
     * The value should properly set internally.
     * </p>
     */
    public void testSetAnswer_accuracy() {
        memberAnswer.setAnswer(true);
        assertTrue("The answer should be true.", memberAnswer.getAnswer());
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#getAnswerDate()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetAnswerDate_default() {
        assertNull("Should return null.", memberAnswer.getAnswerDate());
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#setAnswerDate(Date)</code> method.
     * </p>
     * <p>
     * If the parameter is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetAnswerDate_null() {
        try {
            memberAnswer.setAnswerDate(null);
            fail("If the parameter is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>MemberAnswer#setAnswerDate(Date)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetAnswerDate_notNull() {
        Date answerDate = new Date();

        memberAnswer.setAnswerDate(answerDate);

        assertEquals("The answer date is not set properly.", answerDate, memberAnswer.getAnswerDate());
    }
}
