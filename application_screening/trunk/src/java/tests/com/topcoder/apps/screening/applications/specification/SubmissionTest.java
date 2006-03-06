/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * SubmissionTest.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * <p>
 * Unit tests for <code>Submission</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SubmissionTest extends TestCase {

    /**Submission instance that will be tested.*/
    private Submission submission;

    /**UMLUseCaseDiagrams array.*/
    private UMLUseCaseDiagram []useCaseDiagrams;

    /**UMLActivityDiagram array.*/
    private UMLActivityDiagram []activityDiagrams;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * @throws Exception exception
     */
    public void setUp() throws Exception {
        UMLUseCaseDiagram useCaseDiagram1 = new UMLUseCaseDiagram();
        useCaseDiagram1.setDiagramName("use case diagram 1");
        UMLUseCaseDiagram useCaseDiagram2 = new UMLUseCaseDiagram();
        useCaseDiagram2.setDiagramName("use case diagram 2");
        useCaseDiagrams = new UMLUseCaseDiagram[]{useCaseDiagram1, useCaseDiagram2};

        UMLActivityDiagram activityDiagram1 = new UMLActivityDiagram();
        activityDiagram1.setDiagramName("activity diagram 1");
        UMLActivityDiagram activityDiagram2 = new UMLActivityDiagram();
        activityDiagram2.setDiagramName("activity diagram 2");
        UMLActivityDiagram activityDiagram3 = new UMLActivityDiagram();
        activityDiagram3.setDiagramName("activity diagram 3");
        activityDiagrams = new UMLActivityDiagram[]{activityDiagram1, activityDiagram2, activityDiagram3};

        submission = new Submission(useCaseDiagrams, activityDiagrams);
    }

    /**
     * <p>
     * Tests constructor if UMLUseCaseDiagrams array is null.
     * </p>
     */
    public void testCtorIfUseCaseDiagramsNull() {
        try {
            new Submission(null, activityDiagrams);
            fail("UMLUseCaseDiagrams array cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor if UMLUseCaseDiagrams array contain null element.
     * </p>
     */
    public void testCtorIfUseCaseDiagramsContainNull() {
        try {
            useCaseDiagrams = new UMLUseCaseDiagram[]{new UMLUseCaseDiagram(), null};
            new Submission(useCaseDiagrams, activityDiagrams);
            fail("UMLUseCaseDiagrams array cannot contain null element.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor if UMLActivityDiagrams array is null.
     * </p>
     */
    public void testCtorIfActivityDiagramsNull() {
        try {
            new Submission(useCaseDiagrams, null);
            fail("UMLActivityDiagrams array cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor if UMLActivityDiagrams array contains null element.
     * </p>
     */
    public void testCtorIfActivityDiagramsContainNull() {
        try {
            activityDiagrams = new UMLActivityDiagram[]{new UMLActivityDiagram(), null};
            new Submission(useCaseDiagrams, activityDiagrams);
            fail("UMLActivityDiagrams array cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor's accuracy.
     * </p>
     */
    public void testCtorAccuracy() {
        assertTrue("Must be equal.", Arrays.equals(useCaseDiagrams, submission.getUseCaseDiagrams()));
        assertTrue("Must be equal.", Arrays.equals(activityDiagrams, submission.getActivityDiagrams()));
    }

    /**
     * <p>
     * Checks that copies are stored while creating instance and while getting arrays.
     * </p>
     */
    public void testGetters() {
        //change useCaseDiagrams array
        useCaseDiagrams[0] = null;
        UMLUseCaseDiagram []getUseCaseDiagrams = submission.getUseCaseDiagrams();
        //check that submissions array still has first element non-null
        assertNotNull("Must not be null.", getUseCaseDiagrams[0]);

        //change first's diagram name
        String name = "new_diagram_name";
        getUseCaseDiagrams[0].setDiagramName(name);

        //get again
        getUseCaseDiagrams = submission.getUseCaseDiagrams();
        //name of first diagram will be original (not equal 'name')
        assertNotSame("Must not be equal.", name, getUseCaseDiagrams[0]);
    }
}