/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.stresstests;

import java.util.Iterator;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionSituationPrizeGenerator;

/**
 * Stress tests for class ComponentCompetitionSituationPrizeGenerator.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class ComponentCompetitionSituationPrizeGeneratorStressTests extends BaseStressTests {
    /**
     * Stress tests for ctor method of method ComponentCompetitionSituationPrizeGenerator().
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1() throws Exception {
        ComponentCompetitionSituation template = new ComponentCompetitionSituation();

        double minPrize = 500D;
        double maxPrize = 800D;
        double prizeIncrementFactor = 100D;

        begin();

        for (int i = 0; i < TIMES; i++) {

            ComponentCompetitionSituationPrizeGenerator generator = new ComponentCompetitionSituationPrizeGenerator(
                    template, minPrize, maxPrize, prizeIncrementFactor);

            Iterator<ComponentCompetitionSituation> situations = generator.iterator();
            int totalNumber = 0;

            while (situations.hasNext()) {
                situations.next();
                totalNumber++;
            }

            assertEquals("the total number of the situations.", 4, totalNumber);

        }

        print("ctor of ComponentCompetitionSituationPrizeGenerator");

    }
}
