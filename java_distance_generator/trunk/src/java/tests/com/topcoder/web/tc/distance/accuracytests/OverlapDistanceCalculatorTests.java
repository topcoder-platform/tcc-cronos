package com.topcoder.web.tc.distance.accuracytests;

import java.util.List;

import junit.framework.TestCase;

import com.topcoder.web.tc.distance.DistanceCalculator;
import com.topcoder.web.tc.distance.calculators.MatchOverlapDistanceCalculator;
import com.topcoder.web.tc.distance.data.Member;

/**
 * Accuracy test cases for OverlapDistanceCalculator.
 *
 * @author aksonov
 *
 */
public class OverlapDistanceCalculatorTests extends TestCase {

    /**
     * Tests for both defined distances.
     */
    public void testBothDefined() {
        DistanceCalculator calculator = new MatchOverlapDistanceCalculator();
        List<Double> list = calculator.getDistance(
            TestHelper.getTestDataAccess().getMember(TestHelper.MEMBER_IDS[0]), TestHelper.getTestDataAccess()
                .getRelatedMembers(TestHelper.MEMBER_IDS[0], null), TestHelper.getAlgoritmEnum());

        // min overlap 19
        // max overlap 49
        // overlap is 33
        // result is 1 - (33 - 19)/49
        assertEquals(1.0 - (33.0 - 19.0) / 49.0, list.get(0), 1e-6);
    }

    /**
     * Tests for both defined distances.
     */
    public void testBothDefined2() {
        DistanceCalculator calculator = new MatchOverlapDistanceCalculator();
        List<Double> list = calculator.getDistance(
            TestHelper.getTestDataAccess().getMember(TestHelper.MEMBER_IDS[0]), TestHelper.getTestDataAccess()
                .getRelatedMembers(TestHelper.MEMBER_IDS[0], null), TestHelper.getAlgoritmEnum());

        // min overlap 19
        // max overlap 49
        // overlap is 33
        // result is 1 - (49 - 19)/49
        assertEquals(1.0 - (49.0 - 19.0) / 49.0, list.get(1), 1e-6);
    }

    /**
     * Tests for zero max distance.
     */
    public void testZeroMaxDistance() {
        DistanceCalculator calculator = new MatchOverlapDistanceCalculator();
        List<Member> members = TestHelper.getTestDataAccess().getRelatedMembers(156859,
            TestHelper.getAlgoritmDevEnum());

        List<Double> list = calculator.getDistance(members.get(0), members, TestHelper.getAlgoritmEnum());

        assertEquals(1.0, list.get(1));
    }

}
