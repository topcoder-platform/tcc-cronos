package com.topcoder.predictor.impl.componentcompetition.failuretests;

import java.util.Date;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.analysis.CompetitionSituationTimelineAndPrizeGenerator;

import junit.framework.TestCase;
/**
 * The failure tests of the class <code>CompetitionSituationTimelineAndPrizeGenerator</code>.
 *
 * @author telly12
 * @version 1.0
 */
public class CompetitionSituationTimelineAndPrizeGeneratorFailureTests extends
        TestCase {
    /**
     * The failure test of the cosntructor,
     * fail for the template is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure1() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(null,
                    1.0, 2.0, 1.0, new Date(), new Date(), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the minPrize is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure3() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(new ComponentCompetitionSituation(),
                    -1.0, 2.0, 1.0, new Date(), new Date(), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the maxPrize is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure4() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(new ComponentCompetitionSituation(),
                    1.0, -0.5, 1.0, new Date(), new Date(), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the prizeIncrementFactor is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure6() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(new ComponentCompetitionSituation(),
                    1.0, 2.5, -1.0, new Date(), new Date(), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the prizeIncrementFactor is zero,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure7() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(new ComponentCompetitionSituation(),
                    1.0, 2.5, -0.0, new Date(), new Date(), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the minDate is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure8() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(new ComponentCompetitionSituation(),
                    1.0, 2.5, 1.0, null, new Date(), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the maxDate is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure9() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(new ComponentCompetitionSituation(),
                    1.0, 2.5, 1.0, new Date(), null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the timelineIncrementFactor is zero,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure10() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(new ComponentCompetitionSituation(),
                    1.0, 2.5, 1.0, new Date(), new Date(), 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the timelineIncrementFactor is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure11() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(new ComponentCompetitionSituation(),
                    1.0, 2.5, 1.0, new Date(), new Date(), -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the minPrize is greater than maxPrize,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure12() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(new ComponentCompetitionSituation(),
                    3.0, 2.5, 1.0, new Date(), new Date(), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the minPrize minDate is greater than maxDate,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure13() {
        Date date1 = new Date();
        Date date2 = new Date(date1.getTime() + 1);
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(new ComponentCompetitionSituation(),
                    2.0, 2.5, 1.0, date2, date1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
}
