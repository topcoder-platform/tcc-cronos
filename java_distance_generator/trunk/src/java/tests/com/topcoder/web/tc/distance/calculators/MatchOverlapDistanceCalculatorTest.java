/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.web.tc.distance.CompetitionType;
import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.data.Member;
import com.topcoder.web.tc.distance.data.MemberTestHelper;

/**
 * <p>
 * Unit test cases for <code>MatchOverlapDistanceCalculator</code> class.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class MatchOverlapDistanceCalculatorTest extends TestCase {

    /**
     * The MatchOverlapDistanceCalculator object used during testing.
     */
    private MatchOverlapDistanceCalculator calculator;

    /**
     * Instantiates object for testing.
     */
    protected void setUp() {
        calculator = new MatchOverlapDistanceCalculator();
    }

    /**
     * Releases memory.
     */
    protected void tearDown() {
        calculator = null;
    }

    /**
     * Accuracy test for constructor: MatchOverlapDistanceCalculator#MatchOverlapDistanceCalculator() <br>
     * Target: assert the created object is not null.
     */
    public void testCtor() {
        assertNotNull("Should not be null", calculator);
    }

    /**
     * Accuracy test for method: MatchOverlapDistanceCalculator#getDistanceType() <br>
     * Target: should return DistanceType#OVERLAP.
     */
    public void testGetDistanceType() {
        assertEquals("Should be Distance#OVERLAP", DistanceType.OVERLAP, calculator.getDistanceType());
    }

    /**
     * Failure test for method: MatchOverlapDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Cause: coder argument is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testGetDistance_NullCoder() {
        try {
            calculator.getDistance(null, MemberTestHelper.getMemberList(), EnumSet.of(CompetitionType.ALGORITHM));

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [coder] argument cannot be null.", ex.getMessage());
        }
    }

    /**
     * Failure test for method: MatchOverlapDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Cause: members argument is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testGetDistance_NullMembers() {
        try {
            calculator.getDistance(MemberTestHelper.getMember(), null, EnumSet.of(CompetitionType.ALGORITHM));

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [members] argument cannot be null.", ex
                    .getMessage());
        }
    }

    /**
     * Failure test for method: MatchOverlapDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Cause: compTypes argument is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testGetDistance_NullCompTypes() {
        try {
            calculator.getDistance(MemberTestHelper.getMember(), MemberTestHelper.getMemberList(), null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [compTypes] argument cannot be null.", ex
                    .getMessage());
        }
    }

    /**
     * Failure test for method: MatchOverlapDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Cause: members argument is empty. <br>
     * Exception: IllegalArgumentException.
     */
    public void testGetDistance_EmptyMembers() {
        try {
            calculator.getDistance(MemberTestHelper.getMember(), new ArrayList<Member>(), EnumSet
                    .of(CompetitionType.ALGORITHM));

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [members] argument cannot be empty.", ex
                    .getMessage());
        }
    }

    /**
     * Failure test for method: MatchOverlapDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Cause: members has null elements. <br>
     * Exception: IllegalArgumentException.
     */
    public void testGetDistance_NullMembersElements() {
        List<Member> list = new ArrayList<Member>();
        list.add(null);

        try {
            calculator.getDistance(MemberTestHelper.getMember(), list, EnumSet.of(CompetitionType.ALGORITHM));

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [members] argument cannot have null elements.", ex
                    .getMessage());
        }
    }

    /**
     * Accuracy test for method: MatchOverlapDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: maxOverlap is 0, since overlap for all members is -1. Should return a list of 1.0.
     */
    public void testGetDistance_CoderOverlap_MembersNoOverlap() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        int[] coderRatings = new int[] {2000, 2000};

        Member coder = MemberTestHelper.getMember(1, "coder", types, coderRatings, 90, 100);
        Member member1 = MemberTestHelper.getMember(2, "coder", types, new int[] {-1, -1}, 90, -1);
        Member member2 = MemberTestHelper.getMember(3, "coder", types, new int[] {-1, -1}, 90, -1);
        Member member3 = MemberTestHelper.getMember(4, "coder", types, new int[] {-1, -1}, 90, -1);

        List<Member> members = Arrays.asList(member1, member2, member3);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.noneOf(CompetitionType.class));

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", 1.0, result.get(0).doubleValue(), 1e-6);
        assertEquals("Distance [1] is incorrect", 1.0, result.get(1).doubleValue(), 1e-6);
        assertEquals("Distance [2] is incorrect", 1.0, result.get(2).doubleValue(), 1e-6);
    }

    /**
     * Accuracy test for method: MatchOverlapDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: Calculation is commented out inside the code.
     */
    public void testGetDistance_CoderNoOverlap_MembersOverlap() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        int[] membersRating = new int[] {2000, 2000};

        Member coder = MemberTestHelper.getMember(1, "coder", types, new int[] {-1, -1}, 90, -1);
        Member member1 = MemberTestHelper.getMember(2, "coder", types, membersRating, 120, 120);
        Member member2 = MemberTestHelper.getMember(3, "coder", types, membersRating, 240, 360);
        Member member3 = MemberTestHelper.getMember(4, "coder", types, new int[] {-1, -1}, 290, 100);

        // minOverlap = 100
        // maxOverlap = 360
        // member1 = 1.0 - ((120 - 100) / 360)
        // member2 = 1.0 - ((360 - 100) / 360)
        // member3 = 1.0 - ((100 - 100) / 360) = 1.0

        List<Member> members = Arrays.asList(member1, member2, member3);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT, CompetitionType.DESIGN));

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", 1.0 - ((120.0 - 100.0) / 360.0), result.get(0).doubleValue(),
                1e-6);
        assertEquals("Distance [1] is incorrect", 1.0 - ((360.0 - 100.0) / 360.0), result.get(1).doubleValue(),
                1e-6);
        assertEquals("Distance [2] is incorrect", 1.0, result.get(2).doubleValue(), 1e-6);
    }

    /**
     * Accuracy test for method: MatchOverlapDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: Calculation is commented out inside the code. Coder overlap values are ignored.
     */
    public void testGetDistance_AllOverlaps() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        Member coder = MemberTestHelper.getMember(1, "coder", types, new int[] {1200, 1200}, 10, 20);
        Member member1 = MemberTestHelper.getMember(2, "coder", types, new int[] {240, 3000}, 120, 120);
        Member member2 = MemberTestHelper.getMember(3, "coder", types, new int[] {480, 6000}, 240, 360);
        Member member3 = MemberTestHelper.getMember(4, "coder", types, new int[] {480, 6000}, 290, 100);

        // minOverlap = 100
        // maxOverlap = 360
        // member1 = 1.0 - ((120 - 100) / 360)
        // member2 = 1.0 - ((360 - 100) / 360)
        // member3 = 1.0 - ((100 - 100) / 360) = 1.0

        List<Member> members = Arrays.asList(member1, member2, member3);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT));

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", 1.0 - ((120.0 - 100.0) / 360.0), result.get(0).doubleValue(),
                1e-6);
        assertEquals("Distance [1] is incorrect", 1.0 - ((360.0 - 100.0) / 360.0), result.get(1).doubleValue(),
                1e-6);
        assertEquals("Distance [2] is incorrect", 1.0, result.get(2).doubleValue(), 1e-6);
    }
}
