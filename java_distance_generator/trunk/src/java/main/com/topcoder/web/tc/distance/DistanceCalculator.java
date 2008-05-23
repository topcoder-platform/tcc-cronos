/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance;

import java.util.EnumSet;
import java.util.List;

import com.topcoder.web.tc.distance.data.Member;

/**
 * <p>
 * This interface defines the contract for a DistanceCalculator implementation. A distance calculator
 * implementation will take a list of members and a set of <code>CompetitionType</code>s and will return
 * the distance for each member based on those competition types.
 * </p>
 * <p>
 * This implementation will be created by the application and passed to the DistanceGenerator. The
 * DistanceGenerator will then call the implementation to discover the distance for each member.
 * </p>
 * <p>
 * Thread-safety: implementations of this interface must be thread-safe.
 * </p>
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public interface DistanceCalculator {
    /**
     * <p>
     * Returns the DistanceType associated with this calculator.
     * </p>
     *
     * @return A non-null distance type enum.
     */
    public DistanceType getDistanceType();

    /**
     * <p>
     * Discovers the distances for a list of members. This method will take a list of members and the
     * <code>CompetitionType</code>s to determine the distance to that member. This method should return a
     * List containing the distance from each member and should be the same size as List&lt;Member&gt; and in
     * the same order as the member. This return list will contain a negative number for a member if that
     * distance is undefined.
     *
     * @param coder A non-null coder to get the distance from.
     * @param members A non-null, non-empty list of members to query.
     * @param compTypes A non-null enum set of competition types to use.
     * @return A non-null list of the size members that contains the distance for that member, following the
     *         members list order (the distance can be negative if undefined).
     * @throws IllegalArgumentException if any argument is null; if members is empty or contains null
     *             elements, if compTypes contains null elements; if members is null or an empty list.
     * @throws DistanceCalculatorException if an exception occurs calculating the distance
     */
    public List<Double> getDistance(Member coder, List<Member> members, EnumSet<CompetitionType> compTypes);
}
