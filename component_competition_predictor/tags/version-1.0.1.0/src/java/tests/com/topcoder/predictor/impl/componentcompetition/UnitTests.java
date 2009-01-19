/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.predictor.impl.componentcompetition;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.predictor.impl.componentcompetition.analysis.BaseComponentCompetitionSituationGeneratorUnitTests;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionSituationPrizeGeneratorUnitTests;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionSituationTimelineGeneratorUnitTests;
import com.topcoder.predictor.impl.componentcompetition.analysis.CompetitionSituationTimelineAndPrizeGeneratorUnitTests;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionFulfillmentPredictionPrizeComparatorUnitTests;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionFulfillmentPredictionTimelineComparatorUnitTests;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(HelperUnitTests.class);
        suite.addTestSuite(TechnologyUnitTests.class);
        suite.addTestSuite(ParticipantUnitTests.class);
        suite.addTestSuite(ComponentCompetitionSituationUnitTests.class);
        suite.addTestSuite(ComponentCompetitionFulfillmentPredictionUnitTests.class);
        suite.addTestSuite(ComponentCompetitionPredictorUnitTests.class);
        suite.addTestSuite(ComponentCompetitionAveragePredictorUnitTests.class);

        suite.addTestSuite(BaseComponentCompetitionSituationGeneratorUnitTests.class);
        suite.addTestSuite(ComponentCompetitionSituationPrizeGeneratorUnitTests.class);
        suite.addTestSuite(ComponentCompetitionSituationTimelineGeneratorUnitTests.class);
        suite.addTestSuite(CompetitionSituationTimelineAndPrizeGeneratorUnitTests.class);
        suite.addTestSuite(ComponentCompetitionFulfillmentPredictionPrizeComparatorUnitTests.class);
        suite.addTestSuite(ComponentCompetitionFulfillmentPredictionTimelineComparatorUnitTests.class);
        suite.addTestSuite(Demo.class);
        return suite;
    }

}
