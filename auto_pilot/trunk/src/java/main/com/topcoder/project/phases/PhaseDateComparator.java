/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.project.phases;


/**
 * <p>
 * An implementation of Comparator to compare the phases by start date and end date of the Phase.
 * The two phases will first sorted by the start date, and then break the ties by end date of the
 * phase. If two phases have the same start date and end date, the order in the sorted list is
 * unpredictable.
 * </p>
 * <p>
 * Thread Safety: This class do not have any state. so it's thread safe.
 * </p>
 * @author TCSDESIGNER
 * @version 2.0
 */
public class PhaseDateComparator implements java.util.Comparator {

    /**
     * <p>
     * Create a new instance PhaseDateComparator, empty constructor.
     * </p>
     */
    public PhaseDateComparator() {
        // your code here
    }

    /**
     * <p>
     * The phase will first be compared by the startDate, and then break the ties by endDate of the
     * phase. If two phases in the list have the same startDate and endDate, the order in the sorted
     * list is unpredictable.
     * </p>
     * @return a negative integer, zero, or a positive integer as the first argument is less than,
     *         equal to, or greater than the second.
     * @param obj1 the first phase obj to compare
     * @param obj2 the second phase obj to compare.
     * @throws ClassCastException if the given objs are not Phase type.
     */
    public int compare(Object obj1, Object obj2) {
        // your code here
        return 0;
    }
}
