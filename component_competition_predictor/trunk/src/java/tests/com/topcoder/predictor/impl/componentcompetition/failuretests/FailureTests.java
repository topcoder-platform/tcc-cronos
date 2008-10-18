/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.predictor.impl.componentcompetition.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CompetitionSituationTimelineAndPrizeGeneratorFailureTests.class);
        suite.addTestSuite(ComponentCompetitionAveragePredictorFailureTests.class);
        suite.addTestSuite(ComponentCompetitionFulfillmentPredictionFailureTests.class);
        suite.addTestSuite(ComponentCompetitionFulfillmentPredictionPrizeComparatorFailureTests.class);
        suite.addTestSuite(ComponentCompetitionPredictorFailureTests.class);
        suite.addTestSuite(ComponentCompetitionSituationFailureTests.class);
        suite.addTestSuite(ComponentCompetitionSituationPrizeGeneratorFailureTests.class);
        suite.addTestSuite(ComponentCompetitionSituationTimelineGeneratorFailureTests.class);
        suite.addTestSuite(ParticipantFailureTests.class);
        return suite;
    }

}
