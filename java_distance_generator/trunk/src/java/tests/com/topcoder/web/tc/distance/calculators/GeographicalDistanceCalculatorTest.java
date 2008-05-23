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
 * Unit test cases for <code>GeographicalDistanceCalculator</code> class.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class GeographicalDistanceCalculatorTest extends TestCase {

    /**
     * The GeographicalDistanceCalculator object used during testing.
     */
    private GeographicalDistanceCalculator calculator;

    /**
     * Instantiates object for testing.
     */
    protected void setUp() {
        calculator = new GeographicalDistanceCalculator();
    }

    /**
     * Releases memory.
     */
    protected void tearDown() {
        calculator = null;
    }

    /**
     * Accuracy test for constructor: GeographicalDistanceCalculator#GeographicalDistanceCalculator() <br>
     * Target: assert the created object is not null.
     */
    public void testCtor() {
        assertNotNull("Should not be null", calculator);
    }

    /**
     * Accuracy test for method: GeographicalDistanceCalculator#getDistanceType() <br>
     * Target: should return DistanceType#GEOGRAPHICAL.
     */
    public void testGetDistanceType() {
        assertEquals("Should be Distance#GEOGRAPHICAL", DistanceType.GEOGRAPHICAL, calculator.getDistanceType());
    }

    /**
     * Failure test for method: GeographicalDistanceCalculator#getDistance(Member, List, EnumSet) <br>
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
     * Failure test for method: GeographicalDistanceCalculator#getDistance(Member, List, EnumSet) <br>
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
     * Failure test for method: GeographicalDistanceCalculator#getDistance(Member, List, EnumSet) <br>
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
     * Failure test for method: GeographicalDistanceCalculator#getDistance(Member, List, EnumSet) <br>
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
     * Failure test for method: GeographicalDistanceCalculator#getDistance(Member, List, EnumSet) <br>
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
     * Accuracy test for method: GeographicalDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: should return a list of -1, since members have unknown geographic info.
     */
    public void testGetDistance_CoderGeo_MembersNoGeo() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        int[] coderRatings = new int[] {2000, 2000};

        Member coder = MemberTestHelper.getMember(1, "coder", types, coderRatings);
        Member member1 = MemberTestHelper.getMember(2, "coder", types, new int[] {-1, -1}, -1, 100);
        Member member2 = MemberTestHelper.getMember(3, "coder", types, new int[] {-1, -1}, -1, 100);

        List<Member> members = Arrays.asList(member1, member2);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.noneOf(CompetitionType.class));

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", -1.0, result.get(0).doubleValue(), 1e-6);
        assertEquals("Distance [1] is incorrect", -1.0, result.get(1).doubleValue(), 1e-6);
    }

    /**
     * Accuracy test for method: GeographicalDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: should return a list of -1, since members have unknown geographic info.
     */
    public void testGetDistance_CoderNoGeo_MembersGeo() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        int[] membersRating = new int[] {2000, 2000};

        Member coder = MemberTestHelper.getMember(1, "coder", types, new int[] {-1, -1}, -1, 100);
        Member member1 = MemberTestHelper.getMember(2, "coder", types, membersRating, 100, 100);
        Member member2 = MemberTestHelper.getMember(3, "coder", types, membersRating, 50, 100);

        List<Member> members = Arrays.asList(member1, member2);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT, CompetitionType.DESIGN));

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", -1.0, result.get(0).doubleValue(), 1e-6);
        assertEquals("Distance [1] is incorrect", -1.0, result.get(1).doubleValue(), 1e-6);
    }

    /**
     * Accuracy test for method: GeographicalDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: calculation is commented inside the method.
     */
    public void testGetDistance_AllGeo() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        Member coder = MemberTestHelper.getMember(1, "coder", types, new int[] {1200, 1200}, 8000, 100);
        Member member1 = MemberTestHelper.getMember(2, "coder", types, new int[] {240, 3000}, 3000, 100);
        Member member2 = MemberTestHelper.getMember(3, "coder", types, new int[] {480, 6000}, 6000, 100);
        Member member3 = MemberTestHelper.getMember(3, "coder", types, new int[] {4800, 6000}, 4800, 100);

        // Max distance = 6000 - Not that coder distance is ignored
        // member1 = 3000 / 6000
        // member2 = 6000 / 6000
        // member3 = 4800 / 6000

        List<Member> members = Arrays.asList(member1, member2, member3);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT));

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", 0.5, result.get(0).doubleValue(), 1e-6);
        assertEquals("Distance [1] is incorrect", 1.0, result.get(1).doubleValue(), 1e-6);
        assertEquals("Distance [2] is incorrect", 0.8, result.get(2).doubleValue(), 1e-6);
    }

    /**
     * Accuracy test for method: GeographicalDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: calculation is commented inside the method. All distances should be zero.
     */
    public void testGetDistance_AllZeroGeo() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        Member coder = MemberTestHelper.getMember(1, "coder", types, new int[] {1200, 1200}, 0, 100);
        Member member1 = MemberTestHelper.getMember(2, "coder", types, new int[] {240, 3000}, 0, 100);
        Member member2 = MemberTestHelper.getMember(3, "coder", types, new int[] {480, 6000}, 0, 100);
        Member member3 = MemberTestHelper.getMember(3, "coder", types, new int[] {4800, 6000}, 0, 100);

        List<Member> members = Arrays.asList(member1, member2, member3);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT));

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", 0.0, result.get(0).doubleValue(), 1e-6);
        assertEquals("Distance [1] is incorrect", 0.0, result.get(1).doubleValue(), 1e-6);
        assertEquals("Distance [2] is incorrect", 0.0, result.get(2).doubleValue(), 1e-6);
    }
}
