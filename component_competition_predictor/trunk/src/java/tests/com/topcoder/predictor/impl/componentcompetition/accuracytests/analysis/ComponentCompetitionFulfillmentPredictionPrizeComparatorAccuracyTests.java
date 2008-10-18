/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.accuracytests.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.predictor.Prediction;
import com.topcoder.predictor.Predictor;
import com.topcoder.predictor.Situation;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Participant;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionFulfillmentPredictionPrizeComparator;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link ComponentCompetitionFulfillmentPredictionPrizeComparator}</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class ComponentCompetitionFulfillmentPredictionPrizeComparatorAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the <code>ComponentCompetitionFulfillmentPredictionPrizeComparator</code> used in tests.
     * </p>
     */
    private ComponentCompetitionFulfillmentPredictionPrizeComparator instance = null;

    /**
     * <p>
     * Represents the minimum desired prediction.
     * </p>
     */
    private double minPrediction = 0.0;

    /**
     * <p>
     * Represents the maximum desired prediction.
     * </p>
     */
    private double maxPrediction = 0.0;

    /**
     * <p>
     * Represents the target duration for the competition, in milliseconds.
     * </p>
     */
    private long targetDuration = 0;

    /**
     * <p>
     * Represents the situation used in tests.
     * </p>
     */
    private ComponentCompetitionSituation situation1 = null;

    /**
     * <p>
     * Represents the situation used in tests.
     * </p>
     */
    private ComponentCompetitionSituation situation2 = null;

    /**
     * <p>
     * Represents the predictor used in tests.
     * </p>
     */
    private Predictor<? extends Situation, ? extends Prediction> predictor = null;

    /**
     * <p>
     * Sets up the tests.
     * </p>
     */
    public void setUp() {
        minPrediction = 2.0;
        maxPrediction = 3.0;
        targetDuration = (new Date(2008, 1, 2).getTime() - new Date(2008, 1, 1).getTime()) * 3;

        situation1 = createSituation();
        situation2 = createSituation();
        predictor = new ComponentCompetitionPredictor();

        instance = new ComponentCompetitionFulfillmentPredictionPrizeComparator(
            minPrediction, maxPrediction, targetDuration);
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>ComponentCompetitionFulfillmentPredictionPrizeComparator(double, double, long)</code>
     * constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor() {
        assertEquals("MinPrediction should be correct.", 2.0, instance.getMinPrediction());
        assertEquals("MaxPrediction should be correct.", 3.0, instance.getMaxPrediction());
        assertEquals("TargetDuration should be correct.", targetDuration, instance.getTargetDuration());
    }

    /**
     * <p>
     * Tests accuracy of <code>compare(ComponentCompetitionFulfillmentPrediction)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testCompare_EqualTo1() {
        ComponentCompetitionFulfillmentPrediction prediction1 =
            new ComponentCompetitionFulfillmentPrediction(1.1, situation1, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 =
            new ComponentCompetitionFulfillmentPrediction(1.1, situation2, predictor);
        assertTrue("'equals' should be correct.", instance.compare(prediction1, prediction2) == 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>compare(ComponentCompetitionFulfillmentPrediction)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testCompare_EqualTo2() {
        ComponentCompetitionFulfillmentPrediction prediction1 =
            new ComponentCompetitionFulfillmentPrediction(1.1, situation1, predictor);

        situation2.getEndDate().setDate(situation1.getEndDate().getDate() + 1);
        ComponentCompetitionFulfillmentPrediction prediction2 =
            new ComponentCompetitionFulfillmentPrediction(1.2, situation2, predictor);
        assertTrue("'equals' should be correct.", instance.compare(prediction1, prediction2) == 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>compare(ComponentCompetitionFulfillmentPrediction)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testCompare_EqualTo3() {
        situation1.setPostingDate(null);
        ComponentCompetitionFulfillmentPrediction prediction1 =
            new ComponentCompetitionFulfillmentPrediction(1.1, situation1, predictor);

        ComponentCompetitionFulfillmentPrediction prediction2 =
            new ComponentCompetitionFulfillmentPrediction(1.2, situation2, predictor);
        assertTrue("'equals' should be correct.", instance.compare(prediction1, prediction2) == 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>compare(ComponentCompetitionFulfillmentPrediction)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testCompare_EqualTo4() {
        situation1.setEndDate(null);
        ComponentCompetitionFulfillmentPrediction prediction1 =
            new ComponentCompetitionFulfillmentPrediction(1.1, situation1, predictor);

        ComponentCompetitionFulfillmentPrediction prediction2 =
            new ComponentCompetitionFulfillmentPrediction(1.2, situation2, predictor);
        assertTrue("'equals' should be correct.", instance.compare(prediction1, prediction2) == 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>compare(ComponentCompetitionFulfillmentPrediction)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testCompare_LessThan1() {
        ComponentCompetitionFulfillmentPrediction prediction1 =
            new ComponentCompetitionFulfillmentPrediction(2.1, situation1, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 =
            new ComponentCompetitionFulfillmentPrediction(1.1, situation2, predictor);
        assertTrue("'equals' should be correct.", instance.compare(prediction1, prediction2) < 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>compare(ComponentCompetitionFulfillmentPrediction)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testCompare_LessThan2() {
        ComponentCompetitionFulfillmentPrediction prediction1 =
            new ComponentCompetitionFulfillmentPrediction(3.1, situation1, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 =
            new ComponentCompetitionFulfillmentPrediction(1.1, situation2, predictor);
        assertTrue("'equals' should be correct.", instance.compare(prediction1, prediction2) < 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>compare(ComponentCompetitionFulfillmentPrediction)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testCompare_GreaterThan1() {
        ComponentCompetitionFulfillmentPrediction prediction1 =
            new ComponentCompetitionFulfillmentPrediction(1.1, situation1, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 =
            new ComponentCompetitionFulfillmentPrediction(2.1, situation2, predictor);
        assertTrue("'equals' should be correct.", instance.compare(prediction1, prediction2) > 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>compare(ComponentCompetitionFulfillmentPrediction)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testCompare_GreaterThan2() {
        ComponentCompetitionFulfillmentPrediction prediction1 =
            new ComponentCompetitionFulfillmentPrediction(1.1, situation1, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 =
            new ComponentCompetitionFulfillmentPrediction(3.1, situation2, predictor);
        assertTrue("'equals' should be correct.", instance.compare(prediction1, prediction2) > 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>equals(Object)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testEquals1() {
        assertTrue("'equals' should be correct.", instance.equals(instance));
    }

    /**
     * <p>
     * Tests accuracy of <code>equals(Object)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testEquals2() {
        ComponentCompetitionFulfillmentPredictionPrizeComparator comparator =
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(minPrediction, maxPrediction, targetDuration);

        assertTrue("'equals' should be correct.", instance.equals(comparator));
    }

    /**
     * <p>
     * Tests accuracy of <code>equals(Object)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testEquals3() {
        ComponentCompetitionFulfillmentPredictionPrizeComparator comparator =
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(1.0, maxPrediction, targetDuration);

        assertFalse("'equals' should be correct.", instance.equals(comparator));
    }

    /**
     * <p>
     * Tests accuracy of <code>equals(Object)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testEquals4() {
        ComponentCompetitionFulfillmentPredictionPrizeComparator comparator =
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(minPrediction, 9.0, targetDuration);

        assertFalse("'equals' should be correct.", instance.equals(comparator));
    }

    /**
     * <p>
     * Tests accuracy of <code>equals(Object)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testEquals5() {
        ComponentCompetitionFulfillmentPredictionPrizeComparator comparator =
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(minPrediction, maxPrediction, 9999);

        assertFalse("'equals' should be correct.", instance.equals(comparator));
    }

    /**
     * <p>
     * Tests accuracy of <code>hashCode()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testHashCode() {
        ComponentCompetitionFulfillmentPredictionPrizeComparator comparator1 =
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(minPrediction, maxPrediction, targetDuration);
        ComponentCompetitionFulfillmentPredictionPrizeComparator comparator2 =
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(minPrediction, maxPrediction, targetDuration);

        assertTrue("'equals' should be correct.", comparator1.hashCode() == comparator2.hashCode());
    }
    /**
     * <p>
     * Tests accuracy of <code>getMinPrediction()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetMinPrediction() {
        assertEquals("'getMinPrediction' should be correct.", 2.0, instance.getMinPrediction());
    }

    /**
     * <p>
     * Tests accuracy of <code>getMaxPrediction()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetMaxPrediction() {
        assertEquals("'getMaxPrediction' should be correct.", 3.0, instance.getMaxPrediction());
    }

    /**
     * <p>
     * Tests accuracy of <code>getTargetDuration()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetTargetDuration() {
        assertEquals("'getTargetDuration' should be correct.", targetDuration, instance.getTargetDuration());
    }

    /**
     * <p>
     * Constructs the situation for tests..
     * </p>
     *
     * @return the created situation.
     */
    @SuppressWarnings("deprecation")
    private ComponentCompetitionSituation createSituation() {
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();

        situation.setPostingDate(new Date(2008 - 1900, 9, 1, 9, 0));
        situation.setEndDate(new Date(2008 - 1900, 9, 5, 9, 0));
        situation.setPrize(new Double(800.00));

        List<Participant> participants = new ArrayList<Participant>();
        Participant participant1 = new Participant();
        participant1.setReliability(new Double(0.0));
        participants.add(participant1);
        Participant participant2 = new Participant();
        participant2.setReliability(new Double(1.0));
        participants.add(participant2);
        Participant participant3 = new Participant();
        participant3.setReliability(new Double(0.8));
        participants.add(participant3);
        Participant participant4 = new Participant();
        participant4.setReliability(new Double(0.6));
        participants.add(participant4);
        situation.setParticipants(participants);

        return situation;
    }
}
