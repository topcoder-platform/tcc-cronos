package com.topcoder.apps.screening.applications.specification.accuracytests;

import java.util.Arrays;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

public class SubmissionTest extends TestCase {

    private Submission submission;

    private UMLUseCaseDiagram[] useCaseDiagrams;

    private UMLActivityDiagram[] activityDiagrams;

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(SubmissionTest.class);
    }

    protected void setUp() {
        // initializing useCaseDiagrams array
        UMLUseCaseDiagram useCaseDiagram1 = new UMLUseCaseDiagram();
        UMLUseCaseDiagram useCaseDiagram2 = new UMLUseCaseDiagram();
        UMLUseCaseDiagram useCaseDiagram3 = new UMLUseCaseDiagram();
        useCaseDiagrams = new UMLUseCaseDiagram[] { useCaseDiagram1,
                useCaseDiagram2, useCaseDiagram3 };
        // initializing activityDiagrams array
        UMLActivityDiagram activityDiagram1 = new UMLActivityDiagram();
        UMLActivityDiagram activityDiagram2 = new UMLActivityDiagram();
        UMLActivityDiagram activityDiagram3 = new UMLActivityDiagram();
        activityDiagrams = new UMLActivityDiagram[] { activityDiagram1,
                activityDiagram2, activityDiagram3 };
        submission = new Submission(useCaseDiagrams, activityDiagrams);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.Submission.getUseCaseDiagrams()'
     */
    public final void testGetUseCaseDiagramsLengthSame() {
        assertEquals("retrieved array size and provided must be equal",
                useCaseDiagrams.length, submission.getUseCaseDiagrams().length);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.Submission.getUseCaseDiagrams()'
     */
    public final void testGetUseCaseDiagramsAllMembersExist() {
        List list = Arrays.asList(useCaseDiagrams);
        // checking that returned list contains all provided diagrams
        UMLUseCaseDiagram[] retrievedUserCaseDiagram = submission
                .getUseCaseDiagrams();
        for (int i = 0; i < retrievedUserCaseDiagram.length; i++) {
            assertEquals("retrieved array misses use case diagram", true, list
                    .contains(retrievedUserCaseDiagram[i]));
        }
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.Submission.getUseCaseDiagrams()'
     */
    public final void testGetActivityDiagramsLengthSame() {
        assertEquals("retrieved array size and provided must be equal",
                activityDiagrams.length,
                submission.getActivityDiagrams().length);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.Submission.getUseCaseDiagrams()'
     */
    public final void testGetActivityDiagramsAllMembersExist() {
        List list = Arrays.asList(activityDiagrams);
        // checking that returned list contains all provided diagrams
        UMLActivityDiagram[] retrievedActivityDiagram = submission
                .getActivityDiagrams();
        for (int i = 0; i < retrievedActivityDiagram.length; i++) {
            assertEquals("retrieved array misses activity diagram", true, list
                    .contains(retrievedActivityDiagram[i]));
        }
    }
}