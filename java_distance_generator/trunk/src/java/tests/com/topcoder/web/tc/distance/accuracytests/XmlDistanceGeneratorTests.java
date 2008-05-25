package com.topcoder.web.tc.distance.accuracytests;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.Node;

import com.topcoder.web.tc.distance.CompetitionType;
import com.topcoder.web.tc.distance.DefaultDistanceGenerator;
import com.topcoder.web.tc.distance.DistanceCalculator;
import com.topcoder.web.tc.distance.DistanceGenerator;
import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.calculators.GeographicalDistanceCalculator;
import com.topcoder.web.tc.distance.calculators.MatchOverlapDistanceCalculator;
import com.topcoder.web.tc.distance.calculators.RatingDistanceCalculator;
import com.topcoder.web.tc.distance.weighting.EqualWeighting;
import com.topcoder.web.tc.distance.weighting.WeightedAverageWeighting;

public class XmlDistanceGeneratorTests extends TestCase {

    private List<DistanceCalculator> calculators = new ArrayList<DistanceCalculator>();

    private WeightedAverageWeighting weighting = null;

    protected void setUp() {
        calculators.clear();
        calculators.add(new RatingDistanceCalculator());
        calculators.add(new GeographicalDistanceCalculator());
        calculators.add(new MatchOverlapDistanceCalculator());

        Map<DistanceType, Integer> w = new HashMap<DistanceType, Integer>();
        w.put(DistanceType.GEOGRAPHICAL, 20);
        w.put(DistanceType.OVERLAP, 40);
        w.put(DistanceType.RATING, 40);
        weighting = new WeightedAverageWeighting(w);
    }

    /**
     * <p>
     * Tests all competition types
     * </p>
     */
    public void testAllTypes() throws Exception {
        DistanceGenerator generator = new DefaultDistanceGenerator(TestHelper.getTestDataAccess(),
            new EqualWeighting(), calculators);

        String result = generator.generateDistance(144400, EnumSet.allOf(DistanceType.class), EnumSet
            .allOf(CompetitionType.class));

        checkResult(result, "144400-all");
    }

    /**
     * <p>
     * Tests all competition types
     * </p>
     */
    /*
     * public void testAllTypes2() throws Exception { DistanceGenerator generator = new
     * DefaultDistanceGenerator(TestHelper.getTestDataAccess(), new EqualWeighting(), calculators);
     * 
     * String result = generator.generateDistance(144400, EnumSet .noneOf(DistanceType.class), EnumSet
     * .allOf(CompetitionType.class)); checkResult(result, "144400-none"); }
     */

    /**
     * <p>
     * Tests geo distance type
     * </p>
     */
    public void testGeoType() throws Exception {
        DistanceGenerator gen = new DefaultDistanceGenerator(TestHelper.getTestDataAccess(), new EqualWeighting(),
            calculators);

        String result = gen.generateDistance(144400, EnumSet.of(DistanceType.GEOGRAPHICAL), EnumSet
            .allOf(CompetitionType.class));
        checkResult(result, "144400-geo");
    }

    /**
     * <p>
     * Tests overlap distance type
     * </p>
     */
    public void testOverlapType() throws Exception {
        DistanceGenerator gen = new DefaultDistanceGenerator(TestHelper.getTestDataAccess(), new EqualWeighting(),
            calculators);

        String result = gen.generateDistance(144400, EnumSet.of(DistanceType.OVERLAP), EnumSet
            .allOf(CompetitionType.class));
        checkResult(result, "144400-overlap");
    }

    /**
     * <p>
     * Tests rating distance type
     * </p>
     */
    public void testRatingType() throws Exception {
        DistanceGenerator gen = new DefaultDistanceGenerator(TestHelper.getTestDataAccess(), new EqualWeighting(),
            calculators);

        String result = gen.generateDistance(144400, EnumSet.of(DistanceType.RATING), EnumSet
            .allOf(CompetitionType.class));
        checkResult(result, "144400-rating");
    }

    /**
     * <p>
     * Tests rating distance type with empty competition type
     * </p>
     */
    /*
     * public void testRatingTypeEmptyCompetition() throws Exception { DistanceGenerator gen = new
     * DefaultDistanceGenerator(TestHelper.getTestDataAccess(), new EqualWeighting(), calculators);
     * 
     * String result = gen.generateDistance(144400, EnumSet
     * .of(DistanceType.RATING),EnumSet.noneOf(CompetitionType.class)); checkResult(result, "144400-rating-empty"); }
     */

    /**
     * <p>
     * Tests rating distance type with empty competition type
     * </p>
     */
    public void testRatingTypeAlgCompetition() throws Exception {
        DistanceGenerator gen = new DefaultDistanceGenerator(TestHelper.getTestDataAccess(), new EqualWeighting(),
            calculators);

        String result = gen.generateDistance(144400, EnumSet.of(DistanceType.RATING), EnumSet
            .of(CompetitionType.ALGORITHM));
        checkResult(result, "144400-alg_rating");
    }

    /**
     * <p>
     * Tests rating distance type with algorithm & design competition type
     * </p>
     */
    public void testRatingTypeAlgDesignCompetition() throws Exception {
        DistanceGenerator gen = new DefaultDistanceGenerator(TestHelper.getTestDataAccess(), new EqualWeighting(),
            calculators);

        String result = gen.generateDistance(144400, EnumSet.of(DistanceType.RATING), EnumSet.of(
            CompetitionType.ALGORITHM, CompetitionType.DESIGN));
        checkResult(result, "144400-alg_design_rating");
    }

    /**
     * <p>
     * Tests rating distance type with algorithm & design competition type
     * </p>
     */
    public void testRatingTypeAlgDesignCompetition2() throws Exception {
        DistanceGenerator gen = new DefaultDistanceGenerator(TestHelper.getTestDataAccess(), new EqualWeighting(),
            calculators);

        String result = gen.generateDistance(144400, EnumSet.of(DistanceType.RATING), EnumSet.of(
            CompetitionType.ALGORITHM, CompetitionType.DESIGN, CompetitionType.HIGHSCHOOL));
        checkResult(result, "144400-alg_design_rating");
    }

    /**
     * <p>
     * Tests rating distance type with algorithm & design competition type
     * </p>
     */
    public void testRatingTypeUndefCompetition() throws Exception {
        DistanceGenerator gen = new DefaultDistanceGenerator(TestHelper.getTestDataAccess(), new EqualWeighting(),
            calculators);

        String result = gen.generateDistance(144400, EnumSet.of(DistanceType.RATING), EnumSet
            .of(CompetitionType.HIGHSCHOOL));
        checkResult(result, "144400-none");
    }

    private void checkResult(String result, String file) throws Exception {
        // TestHelper.writeContents(file+"CURRENT", result);

        DifferenceListener listener = new DistanceGeneratorListener();

        Diff diff = new Diff(TestHelper.getContents(file), result);
        diff.overrideDifferenceListener(listener);
        assertTrue("XML " + file + " not as expected", diff.identical());
    }

    /**
     * A difference listener that will disregard double precision error.
     * 
     * @author romanoTC
     */
    private class DistanceGeneratorListener implements DifferenceListener {

        public int differenceFound(Difference diff) {

            Node expected = diff.getControlNodeDetail().getNode();
            Node actual = diff.getTestNodeDetail().getNode();

            if (expected.getParentNode().getNodeName().equals("distance")) {
                double expectedDouble = Double.parseDouble(expected.getNodeValue());
                double actualDouble = Double.parseDouble(actual.getNodeValue());

                if (Math.abs(expectedDouble - actualDouble) < 1e-6) {
                    return RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }
            }

            return RETURN_ACCEPT_DIFFERENCE;
        }

        public void skippedComparison(Node expected, Node actual) {
            // Does nothing
        }
    }
}
