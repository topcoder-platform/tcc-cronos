/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import junit.framework.TestCase;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;


/**
 * <p>
 * Failure tests for <code>Submission</code>.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class SubmissionFailureTests extends TestCase {
    /** Submission instance for failure tests. */
    Submission submission;

    /**
     * Config the setup environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        submission = new Submission(new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]);
    }

    /**
     * Failure test for <code>Submission(UMLUseCaseDiagram[], UMLActivityDiagram[])</code>, with null useCaseDiagrams,
     * IAE expected.
     */
    public void testSubmission_null_useCaseDiagrams() {
        try {
            new Submission(null, new UMLActivityDiagram[0]);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>Submission(UMLUseCaseDiagram[], UMLActivityDiagram[])</code>, with null activityDiagrams,
     * IAE expected.
     */
    public void testSubmission_null_activityDiagrams() {
        try {
            new Submission(new UMLUseCaseDiagram[0], null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>Submission(UMLUseCaseDiagram[], UMLActivityDiagram[])</code>, with null useCaseDiagram
     * contained in array, IAE expected.
     */
    public void testSubmission_null_useCaseDiagram_contained() {
        UMLUseCaseDiagram[] uds = new UMLUseCaseDiagram[1];
        uds[0] = null;

        try {
            new Submission(uds, new UMLActivityDiagram[0]);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>Submission(UMLUseCaseDiagram[], UMLActivityDiagram[])</code>, with null activityDiagrams
     * contained in array, IAE expected.
     */
    public void testSubmission_null_activityDiagrams_contained() {
        UMLActivityDiagram[] ads = new UMLActivityDiagram[1];
        ads[0] = null;

        try {
            new Submission(new UMLUseCaseDiagram[0], ads);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>Submission(UMLUseCaseDiagram[], UMLActivityDiagram[])</code>.
     */
    public void testSubmission() {
        try {
            new Submission(new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]);

            // expected
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }
}
