/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.failuretests.model;

import junit.framework.TestCase;

import com.topcoder.service.prerequisite.document.model.MemberAnswer;

/**
 * <p>
 * Failure test for MemberAnswer class.
 * </p>
 * 
 * @author 80x86
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
     * Failure test for setMemberAnswerId with null, IllegalArgumentException is expected.
     */
    public void testSetMemberAnswerId_Null() {
        try {
            memberAnswer.setMemberAnswerId(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setCompetitionDocument with null, IllegalArgumentException is expected.
     */
    public void testSetCompetitionDocument_Null() {
        try {
            memberAnswer.setCompetitionDocument(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setMemberId with null, IllegalArgumentException is expected.
     */
    public void testSetMemberId_Null() {
        try {
            memberAnswer.setMemberId(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setAnswerDate with null, IllegalArgumentException is expected.
     */
    public void testSetAnswerDate_Null() {
        try {
            memberAnswer.setAnswerDate(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
