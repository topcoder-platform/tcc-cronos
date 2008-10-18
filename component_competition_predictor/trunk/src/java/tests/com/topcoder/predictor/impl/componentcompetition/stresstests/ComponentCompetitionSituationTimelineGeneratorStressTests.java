/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.stresstests;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionSituationTimelineGenerator;

/**
 * Stress tests for class ComponentCompetitionSituationTimelineGenerator.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class ComponentCompetitionSituationTimelineGeneratorStressTests extends BaseStressTests {
    /**
     * Stress tests for ctor method of method ComponentCompetitionSituationTimelineGenerator().
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1() throws Exception {
        Calendar calendar = Calendar.getInstance(Locale.US);

        ComponentCompetitionSituation template = new ComponentCompetitionSituation();

        calendar.set(2008, 10, 16);
        Date minDate = calendar.getTime();
        calendar.set(2008, 10, 18);
        Date maxDate = calendar.getTime();
        long timelineIncrementFactor = (maxDate.getTime() - minDate.getTime()) / 2; // one day

        begin();

        for (int i = 0; i < TIMES; i++) {

            ComponentCompetitionSituationTimelineGenerator generator
                = new ComponentCompetitionSituationTimelineGenerator(
                    template, minDate, maxDate, timelineIncrementFactor);

            Iterator<ComponentCompetitionSituation> situations = generator.iterator();
            int totalNumber = 0;

            while (situations.hasNext()) {
                situations.next();
                totalNumber++;
            }

            assertEquals("the total number of the situations.", 3, totalNumber);

        }

        print("ctor of ComponentCompetitionSituationTimelineGenerator");

    }
}
