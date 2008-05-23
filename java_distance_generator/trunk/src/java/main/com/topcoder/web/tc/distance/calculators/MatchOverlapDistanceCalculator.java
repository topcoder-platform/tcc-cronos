/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.calculators;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.web.tc.distance.Helper;
import com.topcoder.web.tc.distance.CompetitionType;
import com.topcoder.web.tc.distance.DistanceCalculator;
import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.data.Member;

/**
 * <p>
 * This implementation of the DistanceCalculator will calculate distance based on the number of matches shared with
 * the coder.
 * </p>
 * <p>
 * This implementation will be created by the application and passed to the DistanceGenerator to calculate
 * distances based on shared match overlaps. The DistanceGenerator will then call this class with a list of members
 * to calculate. This class will then calculate the distance based on the shared match overlaps of those members
 * from the given coder.
 * </p>
 * <p>
 * Thread-safety: this class is thread safe by having no mutable state.
 * </p>
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public class MatchOverlapDistanceCalculator implements DistanceCalculator {
    /**
     * Default empty constructor.
     */
    public MatchOverlapDistanceCalculator() {
        // Does nothing
    }

    /**
     * Returns the distance type associated with this calculator.
     *
     * @return always return {@link DistanceType#OVERLAP}.
     */
    public DistanceType getDistanceType() {
        return DistanceType.OVERLAP;
    }

    /**
     * <p>
     * This method will return, for each member, calculate the overlapping match distance and return a list (of the
     * same size and order of members) for each member distance.
     * </p>
     *
     * @param coder
     *            A non-null coder to get the distance from.
     * @param members
     *            A non-null, non-empty list of members to query.
     * @param compTypes
     *            A non-null enum set of competition types to use.
     * @return A non-null list of the size members that contains the distance for that member the members order
     *         (the distance can be negative if undefined)
     * @throws IllegalArgumentException
     *             if any argument is null; if members is empty or contains null elements, if compTypes contains
     *             null elements; if members is null or an empty list.
     */
    public List<Double> getDistance(Member coder, List<Member> members, EnumSet<CompetitionType> compTypes) {
        ExceptionUtils.checkNull(coder, null, null, "The [coder] argument cannot be null.");
        Helper.checkCollection(members, "members", false);
        Helper.checkCollection(compTypes, "compTypes", true);

        List<Double> rcList = new ArrayList<Double>(members.size());

        int minOverlap = Integer.MAX_VALUE;
        int maxOverlap = 0;

        // Iterate members list to get min and max overlap distance
        for (Member member : members) {
            int distance = member.getMatchOverlap();

            minOverlap = Math.min(minOverlap, Math.max(0, distance));
            maxOverlap = Math.max(maxOverlap, distance);
        }

        // Calculate distances - if maxOverlap is 0.0, set distance to 1.0
        for (Member member : members) {
            double distance = (maxOverlap == 0) ? 1.0
                    : (1.0 - ((member.getMatchOverlap() - minOverlap) / (double) maxOverlap));

            rcList.add(distance);
        }

        return rcList;
    }
}
