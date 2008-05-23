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
 * This implementation of the DistanceCalculator will calculate distance based on the geographical distance from
 * the coder.
 * </p>
 * <p>
 * This implementation will be created by the application and passed to the DistanceGenerator to calculate
 * distances based on geographical distance. The DistanceGenerator will then call this class with a list of members
 * to calculate. This class will then calculate the distance based on the geographical distance of those members
 * from the given coder.
 * </p>
 * <p>
 * Thread-safety: this class is thread safe by having no mutable state.
 * </p>
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public class GeographicalDistanceCalculator implements DistanceCalculator {
    /**
     * Default empty constructor.
     */
    public GeographicalDistanceCalculator() {
        // Does nothing
    }

    /**
     * This method returns the distance type associated with this calculator.
     *
     * @return always return {@link DistanceType#GEOGRAPHICAL}.
     */
    public DistanceType getDistanceType() {
        return DistanceType.GEOGRAPHICAL;
    }

    /**
     * <p>
     * This method will calculate, for each member, the geographical distance and return a list (of the same size
     * and order of members) for each member distance.
     * </p>
     *
     * @param compTypes
     *            A non-null enum set of competition types to use.
     * @param coder
     *            A non-null coder to get the distance from.
     * @param members
     *            A non-null, non-empty list of members to query.
     * @return A non-null list of the size members that contains the distance for that member the members order
     *         (the distance can be negative if undefined).
     * @throws IllegalArgumentException
     *             if any argument is null; if members is empty or contains null elements, if compTypes contains
     *             null elements; if members is null or an empty list.
     */
    public List<Double> getDistance(Member coder, List<Member> members, EnumSet<CompetitionType> compTypes) {
        ExceptionUtils.checkNull(coder, null, null, "The [coder] argument cannot be null.");
        Helper.checkCollection(members, "members", false);
        Helper.checkCollection(compTypes, "compTypes", true);

        List<Double> rcList = new ArrayList<Double>(members.size());

        int ourDistance = coder.getGeographicalDistance();
        int maxDistance = (ourDistance < 0) ? -1 : getMaxGeographicalDistance(members);

        for (Member member : members) {
            int memberDistance = member.getGeographicalDistance();

            // If members have no geographical info, add -1
            if (ourDistance < 0 || memberDistance < 0) {
                rcList.add(-1.0);

            } else {
                rcList.add((maxDistance == 0) ? 0.0 : ((double) memberDistance) / maxDistance);
            }
        }

        return rcList;
    }

    /**
     * <p>
     * Returns the max geographical distance from the list of members.
     * </p>
     *
     * @param members
     *            A non-null, non-empty list of members (with no null elements).
     * @return A double indicating the max distance (negative means unknown).
     */
    private int getMaxGeographicalDistance(List<Member> members) {

        int maxDistance = Integer.MIN_VALUE;

        for (Member member : members) {
            maxDistance = Math.max(maxDistance, member.getGeographicalDistance());
        }

        return maxDistance;
    }
}
