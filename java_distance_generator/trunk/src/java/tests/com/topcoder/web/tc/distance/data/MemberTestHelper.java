/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.web.tc.distance.CompetitionType;

/**
 * <p>
 * Helper class used in tests to create member objects.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class MemberTestHelper {

    /**
     * Returns a member with default values.
     *
     * @return a member with default values.
     */
    public static Member getMember() {
        return getMember(1000, "handle", new CompetitionType[] {CompetitionType.ALGORITHM}, new int[] {3400});
    }

    /**
     * Returns a member with the given values.
     *
     * @param id
     *            member's id.
     * @param handle
     *            member's handle.
     * @param types
     *            competition's types.
     * @param ratings
     *            member's ratings.
     * @return a member with the given values.
     */
    public static Member getMember(long id, String handle, CompetitionType[] types, int[] ratings) {
        return getMember(id, handle, types, ratings, "USA", "no image");
    }

    /**
     * Returns a member with the given values.
     *
     * @param id
     *            member's id.
     * @param handle
     *            member's handle.
     * @param types
     *            competition's types.
     * @param ratings
     *            member's ratings.
     * @param country
     *            member's country.
     * @param image
     *            member's image.
     * @return a member with the given values.
     */
    public static Member getMember(long id, String handle, CompetitionType[] types, int[] ratings, String country,
            String image) {

        Map<CompetitionType, Integer> ratingsMap = new HashMap<CompetitionType, Integer>();
        for (int i = 0; i < types.length; ++i) {
            ratingsMap.put(types[i], ratings[i]);
        }

        Member member = new Member(id, handle, ratingsMap, country, image);

        return member;
    }

    /**
     * Returns a member with the given values.
     *
     * @param id
     *            member's id.
     * @param handle
     *            member's handle.
     * @param types
     *            competition's types.
     * @param ratings
     *            member's ratings.
     * @param geoDistance
     *            member's geographic distance.
     * @param overlap
     *            member's overlap value.
     * @return a member with the given values.
     */
    public static Member getMember(long id, String handle, CompetitionType[] types, int[] ratings,
            int geoDistance, int overlap) {

        return getMember(id, handle, types, ratings, "USA", "no image", geoDistance, overlap);
    }

    /**
     * Returns a member with the given values.
     *
     * @param id
     *            member's id.
     * @param handle
     *            member's handle.
     * @param types
     *            competition's types.
     * @param ratings
     *            member's ratings.
     * @param country
     *            member's country.
     * @param image
     *            member's image.
     * @param geoDistance
     *            member's geographic distance.
     * @param overlap
     *            member's overlap value.
     * @return a member with the given values.
     */
    public static Member getMember(long id, String handle, CompetitionType[] types, int[] ratings, String country,
            String image, int geoDistance, int overlap) {

        Map<CompetitionType, Integer> ratingsMap = new HashMap<CompetitionType, Integer>();
        for (int i = 0; i < types.length; ++i) {
            ratingsMap.put(types[i], ratings[i]);
        }

        Member member = new Member(id, handle, ratingsMap, country, image, geoDistance, overlap);

        return member;
    }

    /**
     * Returns a list with <code>size</code> member elements.
     *
     * @param size
     *            the size of the list.
     * @return a list with <code>size</code> member elements.
     */
    public static List<Member> getMemberList(int size) {
        List<Member> members = new ArrayList<Member>();
        for (int i = 0; i < size; ++i) {
            members.add(getMember());
        }
        return members;
    }

    /**
     * Returns a list with a single member.
     *
     * @return a list with a single member.
     */
    public static List<Member> getMemberList() {
        List<Member> members = new ArrayList<Member>();
        members.add(getMember());
        return members;
    }
}
