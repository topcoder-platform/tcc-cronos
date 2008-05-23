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
 * Unit test cases for <code>RatingDistanceCalculator</code> class.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class RatingDistanceCalculatorTest extends TestCase {

    /**
     * The RatingDistanceCalculator object used during testing.
     */
    private RatingDistanceCalculator calculator;

    /**
     * Instantiates object for testing.
     */
    protected void setUp() {
        calculator = new RatingDistanceCalculator();
    }

    /**
     * Releases memory.
     */
    protected void tearDown() {
        calculator = null;
    }

    /**
     * Accuracy test for constructor: RatingDistanceCalculator#RatingDistanceCalculator() <br>
     * Target: assert the created object is not null.
     */
    public void testCtor() {
        assertNotNull("Should not be null", calculator);
    }

    /**
     * Accuracy test for method: RatingDistanceCalculator#getDistanceType() <br>
     * Target: should return DistanceType#RATING.
     */
    public void testGetDistanceType() {
        assertEquals("Should be Distance#RATING", DistanceType.RATING, calculator.getDistanceType());
    }

    /**
     * Failure test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
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
     * Failure test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
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
     * Failure test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
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
     * Failure test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
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
     * Failure test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
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
     * Accuracy test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: should return a list of -1, since compTypes is empty.
     */
    public void testGetDistance_EmptyCompTypes() {
        List<Member> members = MemberTestHelper.getMemberList(10);
        List<Double> result = calculator.getDistance(MemberTestHelper.getMember(), members, EnumSet
                .noneOf(CompetitionType.class));

        assertEquals("Result size is incorrect", members.size(), result.size());
        for (int i = 0; i < members.size(); ++i) {
            assertEquals("Distance [" + i + "] is incorrect", -1.0, result.get(i).doubleValue(), 1e-6);
        }
    }

    /**
     * Accuracy test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: since no member is rated, the result should be a list of 1's.
     */
    public void testGetDistance_CoderRated_MembersUnrated() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        int[] coderRatings = new int[] {2000, 5000};

        Member coder = MemberTestHelper.getMember(1, "coder", types, coderRatings);
        Member member1 = MemberTestHelper.getMember(2, "coder", types, new int[] {-1, -1});
        Member member2 = MemberTestHelper.getMember(3, "coder", types, new int[] {-1, -1});

        List<Member> members = Arrays.asList(member1, member2);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT, CompetitionType.DESIGN));

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", 1.0, result.get(0).doubleValue(), 1e-6);
        assertEquals("Distance [1] is incorrect", 1.0, result.get(1).doubleValue(), 1e-6);
    }

    /**
     * Accuracy test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: Calculation is commented out inside the code.
     */
    public void testGetDistance_CoderUnrated_MembersUnrated() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        Member coder = MemberTestHelper.getMember(1, "coder", types, new int[] {-1, -1});
        Member member1 = MemberTestHelper.getMember(2, "coder", types, new int[] {2000, 8000});
        Member member2 = MemberTestHelper.getMember(3, "coder", types, new int[] {4000, 2000});

        List<Member> members = Arrays.asList(member1, member2);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT, CompetitionType.DESIGN));

        // max ratings: 4000 and 8000
        // member1 : 2000/4000 + 8000/8000 = 1.5 / 2 = 0.75
        // member2 : 4000/4000 + 2000/8000 = 1.25 / 2 = 0.625

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", 0.75, result.get(0).doubleValue(), 1e-6);
        assertEquals("Distance [1] is incorrect", 0.625, result.get(1).doubleValue(), 1e-6);
    }

    /**
     * Accuracy test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: Calculation is commented out inside the code.
     */
    public void testGetDistance_AllRated() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        Member coder = MemberTestHelper.getMember(1, "coder", types, new int[] {1200, 1200});
        Member member1 = MemberTestHelper.getMember(2, "coder", types, new int[] {240, 3000});
        Member member2 = MemberTestHelper.getMember(3, "coder", types, new int[] {480, 6000});

        List<Member> members = Arrays.asList(member1, member2);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT, CompetitionType.DESIGN));

        // max ratings: 1200 and 6000
        // member1 : (240 - 1200)/1200 + (1200 -3000)/6000 = 1.1 / 2 = 0.55
        // member2 : (480 - 1200)/1200 + (1200 - 6000)/6000 = 1.4 / 2 = 0.7

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", 0.55, result.get(0).doubleValue(), 1e-6);
        assertEquals("Distance [1] is incorrect", 0.7, result.get(1).doubleValue(), 1e-6);
    }

    /**
     * Accuracy test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: All coders have equal ratings. Calculation is commented out inside the code.
     */
    public void testGetDistance_SameRatings() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        Member coder = MemberTestHelper.getMember(1, "coder", types, new int[] {1200, 3000});
        Member member1 = MemberTestHelper.getMember(2, "coder", types, new int[] {1200, 3000});
        Member member2 = MemberTestHelper.getMember(3, "coder", types, new int[] {1200, 3000});

        List<Member> members = Arrays.asList(member1, member2);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT));

        // max ratings: 1200 and 3000
        // member1 : (1200 - 1200)/1200 + (3000 - 3000)/3000 = 0.0
        // member2 : (1200 - 1200)/1200 + (3000 - 3000)/3000 = 0.0

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", 0.0, result.get(0).doubleValue(), 1e-6);
        assertEquals("Distance [1] is incorrect", 0.0, result.get(1).doubleValue(), 1e-6);
    }

    /**
     * Accuracy test for method: RatingDistanceCalculator#getDistance(Member, List, EnumSet) <br>
     * Target: All members are unrated, should return a list with -1 only.
     */
    public void testGetDistance_AllUnrated() {
        CompetitionType[] types = new CompetitionType[] {CompetitionType.ALGORITHM, CompetitionType.DEVELOPMENT};

        Member coder = MemberTestHelper.getMember(1, "coder", types, new int[] {-1, -1});
        Member member1 = MemberTestHelper.getMember(2, "coder", types, new int[] {-1, -1});
        Member member2 = MemberTestHelper.getMember(3, "coder", types, new int[] {-1, -1});

        List<Member> members = Arrays.asList(member1, member2);
        List<Double> result = calculator.getDistance(coder, members, EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT));

        assertEquals("Result size is incorrect", members.size(), result.size());
        assertEquals("Distance [0] is incorrect", -1.0, result.get(0).doubleValue(), 1e-6);
        assertEquals("Distance [1] is incorrect", -1.0, result.get(1).doubleValue(), 1e-6);
    }
}
