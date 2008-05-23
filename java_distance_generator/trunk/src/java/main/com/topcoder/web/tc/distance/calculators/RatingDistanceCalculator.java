/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.calculators;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.web.tc.distance.Helper;
import com.topcoder.web.tc.distance.CompetitionType;
import com.topcoder.web.tc.distance.DistanceCalculator;
import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.data.Member;

/**
 * <p>
 * This implementation of the DistanceCalculator will calculate distance based on the ratings.
 * </p>
 * <p>
 * This implementation will be created by the application and passed to the DistanceGenerator to calculate
 * distances based on ratings. The DistanceGenerator will then call this class with a list of members to calculate.
 * This class will then calculate the distance based on the ratings of those members from the given coder.
 * </p>
 * <p>
 * Thread-safety: this class is thread safe by having no mutable state.
 * </p>
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public class RatingDistanceCalculator implements DistanceCalculator {

    /**
     * Default empty constructor.
     */
    public RatingDistanceCalculator() {
        // Does nothing
    }

    /**
     * This method returns the distance type associated with this calculator.
     *
     * @return always return {@link DistanceType#RATING}.
     */
    public DistanceType getDistanceType() {
        return DistanceType.RATING;
    }

    /**
     * <p>
     * This method will calculate, for each member, the rating distance and return a list (of the same size and
     * order of members) for each member distance.
     * </p>
     *
     * @param coder
     *            A non-null coder to get the distance from.
     * @param members
     *            A non-null, non-empty list of members to query.
     * @param compTypes
     *            A non-null enum set of competition types to use.
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

        if (compTypes.size() == 0) {
            return Helper.getUndefinedList(members.size());
        }

        List<Double> rcList = new ArrayList<Double>(members.size());

        Map<CompetitionType, Double> maxRatings = new HashMap<CompetitionType, Double>();

        for (CompetitionType competitionType : compTypes) {
            double result = Math.max(coder.getRating(competitionType), getMaxRating(members, competitionType));

            // Stores the result if it is greater than 0
            if (result > 0) {
                maxRatings.put(competitionType, result);
            }
        }

        for (Member member : members) {
            double totalRating = 0;

            // note that maxRatings only contains those competition types where we have a valid maximum
            // rating (ie it will not contain any all members are unrated types - which should be ignored).

            for (CompetitionType competitionType : maxRatings.keySet()) {
                double ourRating = coder.getRating(competitionType);
                double memberRating = member.getRating(competitionType);

                if (ourRating > 0.0) {
                    // If member rating is undefined, use the max distance (1.0)

                    if (memberRating <= 0.0) {
                        totalRating += 1.0;

                    } else {
                        // calculate distance based on our rating and member's rating
                        totalRating += (Math.abs(ourRating - memberRating) / maxRatings.get(competitionType));

                    }
                } else if (memberRating > 0.0) {
                    totalRating += (memberRating / maxRatings.get(competitionType));
                }

            }

            rcList.add((maxRatings.size() == 0) ? -1.0 : (totalRating / maxRatings.size()));
        }

        return rcList;
    }

    /**
     * <p>
     * Returns the max rating from the list of members for the specified competition type.
     * </p>
     *
     * @param competitionType
     *            A non-null competition type (enum)
     * @param members
     *            A non-null, non-empty list of members (with no null elements)
     * @return A double indicating the max rating with a negative meaning unrated
     */
    private double getMaxRating(List<Member> members, CompetitionType competitionType) {
        double maxRating = -Double.MIN_VALUE;
        for (Member member : members) {

            double memberRating = member.getRating(competitionType);
            maxRating = Math.max(maxRating, memberRating);
        }

        return maxRating;
    }
}
