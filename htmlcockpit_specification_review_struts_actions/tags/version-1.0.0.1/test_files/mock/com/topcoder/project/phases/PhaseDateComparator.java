package com.topcoder.project.phases;

import java.util.Comparator;
import java.util.Date;

public class PhaseDateComparator implements Comparator {

    public int compare(Object obj1, Object obj2) {
        if ((!(obj1 instanceof Phase)) || (!(obj2 instanceof Phase))) {
            throw new ClassCastException("The given objs are null or not Phase type.");
        }

        Phase phase1 = (Phase) obj1;
        Phase phase2 = (Phase) obj2;

        int startDateCompare = phase1.calcStartDate().compareTo(phase2.calcStartDate());
        if (startDateCompare != 0) {
            return startDateCompare;
        }

        return phase1.calcEndDate().compareTo(phase2.calcEndDate());
    }
}
