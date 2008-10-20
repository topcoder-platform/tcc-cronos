/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.stresstests;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.analysis.CompetitionSituationTimelineAndPrizeGenerator;

/**
 * Stress tests for class CompetitionSituationTimelineAndPrizeGenerator.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class CompetitionSituationTimelineAndPrizeGeneratorStressTests extends BaseStressTests {

    /**
     * Stress tests for ctor method of method CompetitionSituationTimelineAndPrizeGenerator().
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1() throws Exception {
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.set(2008, 10, 15);
        Date postingDate = calendar.getTime();

        calendar.set(2008, 10, 20);
        Date endDate = calendar.getTime();

        ComponentCompetitionSituation template = new ComponentCompetitionSituation();
        template.setPrize(1000D);
        template.setPostingDate(postingDate);
        template.setEndDate(endDate);

        double minPrize = 500D;
        double maxPrize = 800D;
        double prizeIncrementFactor = 100D;
        calendar.set(2008, 10, 16);
        Date minDate = calendar.getTime();
        calendar.set(2008, 10, 18);
        Date maxDate = calendar.getTime();
        long timelineIncrementFactor = (maxDate.getTime() - minDate.getTime()) / 2; // one day

        begin();

        for (int i = 0; i < TIMES; i++) {

            CompetitionSituationTimelineAndPrizeGenerator generator = new CompetitionSituationTimelineAndPrizeGenerator(
                    template, minPrize, maxPrize, prizeIncrementFactor, minDate, maxDate, timelineIncrementFactor);

            Iterator<ComponentCompetitionSituation> situations = generator.iterator();
            int totalNumber = 0;

            while (situations.hasNext()) {
                situations.next();
                totalNumber++;
            }

            assertEquals("the total number of the situations.", 12, totalNumber);

        }

        print("ctor of CompetitionSituationTimelineAndPrizeGenerator");

    }
}
