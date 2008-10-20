/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Return stress test suites.
     *
     * @return an instance of stress test suites
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(CompetitionSituationTimelineAndPrizeGeneratorStressTests.class);
        suite.addTestSuite(ComponentCompetitionAveragePredictorStressTests.class);
        suite.addTestSuite(ComponentCompetitionPredictorStressTests.class);
        suite.addTestSuite(ComponentCompetitionSituationPrizeGeneratorStressTests.class);
        suite.addTestSuite(ComponentCompetitionSituationTimelineGeneratorStressTests.class);
        suite.addTestSuite(ComponentCompetitionFulfillmentPredictionPrizeComparatorStressTests.class);
        suite.addTestSuite(ComponentCompetitionFulfillmentPredictionTimelineComparatorStressTests.class);

        return suite;
    }
}