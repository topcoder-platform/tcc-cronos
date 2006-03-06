/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * Submission.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

/**
 * <p>
 * This class encapsulates parsed submission data needed for the validation and defines no additional logic.
 * </p>
 *
 * <p>
 * In the current version, validators need only information on use case and activity diagrams.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is immutable and therefore thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class Submission {

    /**
     * Represents an array of use case diagrams for this submission.
     * The array is initialized in the constructor and not changed afterwards. The array reference
     * can never be null, however it may be empty (a submission with no use case diagrams is allowed
     * but it is unlikely to pass validation). Each item in the array is a non-null instance of the
     * UseCaseDiagram class. Array contents can be retrieved using getUseCaseDiagrams() call.
     */
    private final UMLUseCaseDiagram []useCaseDiagrams;

    /**
     * Represents an array of activity diagrams for this submission.
     * The array is initialized in the constructor and not changed afterwards (array contents also
     * can't be changed). The array reference can never be null, however the it may be empty
     * (a submission with no activity diagrams is allowed but it is unlikely to pass validation).
     * Each item in the array is a non-null instance of the ActivityDiagram class.
     * Array contents can be retrieved using getActivityDiagrams() call.
     */
    private final UMLActivityDiagram []activityDiagrams;

    /**
     * <p>
     * Creates a submission with the given arrays of use case and activity diagrams. The diagrams
     * are copied to the internal arrays.
     * </p>
     *
     * @param useCaseDiagrams  the use case diagrams for the submission
     * @param activityDiagrams the activity diagrams for the submission
     * @throws IllegalArgumentException if any parameter is null, or if arrays contain null elements
     */
    public Submission(UMLUseCaseDiagram[] useCaseDiagrams, UMLActivityDiagram[] activityDiagrams) {
        if (useCaseDiagrams == null) {
            throw new IllegalArgumentException("UseCaseDiagrams array must not be null.");
        }
        if (activityDiagrams == null) {
            throw new IllegalArgumentException("ActivityDiagrams array must not be null.");
        }

        //check whether arrays contain null elements
        for (int i = 0, n = useCaseDiagrams.length; i < n; i++) {
            if (useCaseDiagrams[i] == null) {
                throw new IllegalArgumentException("UseCaseDiagrams array must not contain null.");
            }
        }
        for (int i = 0, n = activityDiagrams.length; i < n; i++) {
            if (activityDiagrams[i] == null) {
                throw new IllegalArgumentException("ActivityDiagrams array must not contain null.");
            }
        }

        this.useCaseDiagrams = new UMLUseCaseDiagram[useCaseDiagrams.length];
        System.arraycopy(useCaseDiagrams, 0, this.useCaseDiagrams, 0, useCaseDiagrams.length);

        this.activityDiagrams = new UMLActivityDiagram[activityDiagrams.length];
        System.arraycopy(activityDiagrams, 0, this.activityDiagrams, 0, activityDiagrams.length);
    }

    /**
     * <p>
     * Returns an array of use case diagrams for this submission.
     * </p>
     *
     * @return the use case diagrams for the submission
     */
    public UMLUseCaseDiagram[] getUseCaseDiagrams() {
        UMLUseCaseDiagram []copy = new UMLUseCaseDiagram[useCaseDiagrams.length];
        System.arraycopy(useCaseDiagrams, 0, copy, 0, useCaseDiagrams.length);
        return copy;
    }

    /**
     * <p>
     * Returns an array of activity diagrams for this submission.
     * </p>
     *
     * @return the activity diagrams for the submission
     */
    public UMLActivityDiagram[] getActivityDiagrams() {
        UMLActivityDiagram []copy = new UMLActivityDiagram[activityDiagrams.length];
        System.arraycopy(activityDiagrams, 0, copy, 0, activityDiagrams.length);
        return copy;
    }
}