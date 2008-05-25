package com.topcoder.web.tc.distance.accuracytests;

import java.util.List;

import junit.framework.TestCase;

import com.topcoder.web.tc.distance.DistanceCalculator;
import com.topcoder.web.tc.distance.calculators.GeographicalDistanceCalculator;
import com.topcoder.web.tc.distance.data.Member;

/**
 * Accuracy test cases for GeographicalDistanceCalculator
 *
 * @author aksonov
 *
 */
public class GeographicalDistanceCalculatorTests extends TestCase {

    /**
     * Tests for both defined distances
     */
    public void testBothDefined() {
        DistanceCalculator calculator = new GeographicalDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[0]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[0], null), TestHelper
                        .getAlgoritmEnum());

        assertEquals(0.256/0.257, list.get(0));
    }

    /**
     * Tests for defined/undefined distances
     */
    public void testDefinedUndefined() {
        DistanceCalculator calculator = new GeographicalDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[0]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[0], null), TestHelper
                        .getAlgoritmEnum());


        assertEquals(-1.0, list.get(2));
    }

    /**
     * Tests for defined/undefined distances
     */
    public void testUndefinedDefined() {
        DistanceCalculator calculator = new GeographicalDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[4]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[4], null), TestHelper
                        .getAlgoritmEnum());


        assertEquals(-1.0, list.get(2));

    }

    /**
     * Tests for both undefined distances
     */
    public void testBothUndefined() {
        DistanceCalculator calculator = new GeographicalDistanceCalculator();
        List<Double> list = calculator.getDistance(TestHelper
                .getTestDataAccess().getMember(TestHelper.MEMBER_IDS[4]),
                TestHelper.getTestDataAccess().getRelatedMembers(
                        TestHelper.MEMBER_IDS[4], null), TestHelper
                        .getAlgoritmEnum());


        assertEquals(-1.0, list.get(0));
    }

    /**
     * Tests for zero max distance
     */
    public void testZeroMaxDistance() {
        DistanceCalculator calculator = new GeographicalDistanceCalculator();
        List<Member> members = TestHelper.getTestDataAccess()
                .getRelatedMembers(156859, TestHelper.getAlgoritmDevEnum());

        List<Double> list = calculator.getDistance(members.get(0), members,
                TestHelper.getAlgoritmEnum());

        assertEquals(0.0, list.get(1));
    }

    /**
     * Tests for undef max distance
     */
    public void testUndefMaxDistance() {
        DistanceCalculator calculator = new GeographicalDistanceCalculator();
        List<Member> members = TestHelper.getTestDataAccess()
                .getRelatedMembers(156860, TestHelper.getAlgoritmDevEnum());

        List<Double> list = calculator.getDistance(members.get(0), members,
                TestHelper.getAlgoritmEnum());

        assertEquals(-1.0, list.get(1));
    }

}
