/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.predictor.impl.componentcompetition.accuracytests;

import com.topcoder.predictor.impl.componentcompetition.accuracytests.analysis.CompetitionSituationTimelineAndPrizeGeneratorAccuracyTests;
import com.topcoder.predictor.impl.componentcompetition.accuracytests.analysis.ComponentCompetitionFulfillmentPredictionPrizeComparatorAccuracyTests;
import com.topcoder.predictor.impl.componentcompetition.accuracytests.analysis.ComponentCompetitionFulfillmentPredictionTimelineComparatorAccuracyTests;
import com.topcoder.predictor.impl.componentcompetition.accuracytests.analysis.ComponentCompetitionSituationPrizeGeneratorAccuracyTests;
import com.topcoder.predictor.impl.componentcompetition.accuracytests.analysis.ComponentCompetitionSituationTimelineGeneratorAccuracyTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ComponentCompetitionAveragePredictorAccuracyTests.class);
        suite.addTestSuite(ComponentCompetitionPredictorAccuracyTests.class);
        suite.addTestSuite(ComponentCompetitionFulfillmentPredictionAccuracyTests.class);
        suite.addTestSuite(ComponentCompetitionSituationAccuracyTests.class);
        suite.addTestSuite(ParticipantAccuracyTests.class);
        suite.addTestSuite(TechnologyAccuracyTests.class);

        suite.addTestSuite(CompetitionSituationTimelineAndPrizeGeneratorAccuracyTests.class);
        suite.addTestSuite(ComponentCompetitionFulfillmentPredictionPrizeComparatorAccuracyTests.class);
        suite.addTestSuite(ComponentCompetitionFulfillmentPredictionTimelineComparatorAccuracyTests.class);
        suite.addTestSuite(ComponentCompetitionSituationPrizeGeneratorAccuracyTests.class);
        suite.addTestSuite(ComponentCompetitionSituationTimelineGeneratorAccuracyTests.class);

        return suite;
    }

}
