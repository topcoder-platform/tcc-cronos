package com.topcoder.web.tc.distance.accuracytests;

import java.util.List;

import junit.framework.TestCase;

import com.topcoder.web.tc.distance.DistanceCalculator;
import com.topcoder.web.tc.distance.calculators.RatingDistanceCalculator;
import com.topcoder.web.tc.distance.data.Member;

/**
 * Accuracy test cases for RatingDistanceCalculator
 *
 * @author aksonov
 *
 */
public class RatingDistanceCalculatorTests extends TestCase {

    /**
     * Tests for both defined distances
     */
    public void testBothDefined() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[0]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[0], null), TestHelper
                        .getAlgoritmEnum());

        assertEquals(((4252.0 - 3276.0) / 4252.0), list.get(0));
    }

    /**
     * Tests for defined/undefined ratings
     */
    public void testDefinedUndefined() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[0]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[0], null), TestHelper
                        .getAlgoritmEnum());

        assertEquals(1.0, list.get(2));
    }

    /**
     * Tests for defined/undefined ratings
     */
    public void testUndefinedDefined() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[4]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[4], null), TestHelper
                        .getAlgoritmEnum());

        assertEquals((1872.0 / 1900.0), list.get(1));

    }

    /**
     * Tests for both undefined ratings
     */
    public void testBothUndefined() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[4]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[4], null), TestHelper
                        .getAlgoritmEnum());

        assertEquals(0.0, list.get(0));
    }

    /**
     * Tests for zero max distance
     */
    public void testZeroMaxDistance() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Member> members = TestHelper.getTestDataAccess()
                .getRelatedMembers(156859, TestHelper.getAlgoritmDevEnum());

        List<Double> list = calculator.getDistance(members.get(0), members,
                TestHelper.getAlgoritmEnum());

        assertTrue(list.get(1)<=0.0);
    }

    /**
     * Tests for undef max distance
     */
    public void testUndefMaxDistance() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Member> members = TestHelper.getTestDataAccess()
                .getRelatedMembers(156860, TestHelper.getAlgoritmDevEnum());

        List<Double> list = calculator.getDistance(members.get(0), members,
                TestHelper.getAlgoritmEnum());

        assertTrue(list.get(1)<=0.0);
    }

    /**
     * Tests for both defined distances
     */
    public void testAlgDevBothDefined() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[0]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[0], null), TestHelper
                        .getAlgoritmDevEnum());

        assertEquals(((4252.0 - 3276.0) / 4252.0 + 1001.0 / 2001.0) / 2.0, list.get(0));
    }

    public void testAllBothDefined() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[0]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[0], null), TestHelper
                        .getAllEnum());

        assertEquals(((4252.0 - 3276.0) / 4252.0 + 1001.0 / 2001.0 + 0.0) / 3.0, list.get(0));
    }

    /**
     * Tests for both defined distances
     */
    public void testAlgDevBothDefined2() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[0]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[0], null), TestHelper
                        .getAlgoritmDevEnum());

        assertEquals((double)(((3276 - 1872.0) / 4252.0+ 1.0)/2.0), list.get(1));
    }

    /**
     * Tests for defined/undefined ratings
     */
    public void testAlgDevDefinedUndefined() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[0]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[0], null), TestHelper
                        .getAlgoritmDevEnum());

        assertEquals((double)((1.0 + 1599.0/2001.0)/2.0), list.get(2));
    }

    /**
     * Tests for defined/undefined ratings
     */
    public void testAlgDevUndefinedDefined() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[4]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[4], null), TestHelper
                        .getAlgoritmDevEnum());

        assertEquals((double)((1872.0 / 1900.0 + 1.0)/2.0), list.get(1));

    }

    /**
     * Tests for both undefined ratings
     */
    public void testAlgDevBothUndefined() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[4]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[4], null), TestHelper
                        .getAlgoritmDevEnum());

        assertEquals((double)(1001.0/2001.0/2.0), list.get(0));
    }

    /**
     * Tests for zero max distance
     */
    public void testAlgDevZeroMaxDistance() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Member> members = TestHelper.getTestDataAccess()
                .getRelatedMembers(156859, TestHelper.getAlgoritmDevEnum());

        List<Double> list = calculator.getDistance(members.get(0), members,
                TestHelper.getAlgoritmDevEnum());

        assertTrue(list.get(1)<=0.0);
    }

    /**
     * Tests for undef max distance
     */
    public void testAlgDevUndefMaxDistance() {
        DistanceCalculator calculator = new RatingDistanceCalculator();
        List<Member> members = TestHelper.getTestDataAccess()
                .getRelatedMembers(156860, TestHelper.getAlgoritmDevEnum());

        List<Double> list = calculator.getDistance(members.get(0), members,
                TestHelper.getAlgoritmDevEnum());

        assertTrue(list.get(1)<=0.0);
    }

}
