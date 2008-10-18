/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.accuracytests.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Participant;
import com.topcoder.predictor.impl.componentcompetition.analysis.BaseComponentCompetitionSituationGenerator;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionSituationPrizeGenerator;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link ComponentCompetitionSituationPrizeGenerator}</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class ComponentCompetitionSituationPrizeGeneratorAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the <code>ComponentCompetitionSituationPrizeGenerator</code> used in tests.
     * </p>
     */
    private ComponentCompetitionSituationPrizeGenerator instance = null;

    /**
     * <p>
     * Represents the situation that is the template for the generated situations.
     * </p>
     */
    private ComponentCompetitionSituation template;

    /**
     * <p>
     * Represents the minimum prize.
     * </p>
     */
    private double minPrize = 0.0;

    /**
     * <p>
     * Represents the maximum prize.
     * </p>
     */
    private double maxPrize = 0.0;

    /**
     * <p>
     * Represents the incrementing factor for prize generation.
     * </p>
     */
    private double incrementFactor = 0.0;

    /**
     * <p>
     * Sets up the tests.
     * </p>
     */
    public void setUp() {
        template = new ComponentCompetitionSituation();

        template.setPostingDate(new Date(2008 - 1900, 9, 1, 9, 0));
        template.setEndDate(new Date(2008 - 1900, 9, 5, 9, 0));
        template.setPrize(new Double(800.00));

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
        template.setParticipants(participants);
        minPrize = 3.1;
        maxPrize = 5.5;
        incrementFactor = 1.0;

        instance = new ComponentCompetitionSituationPrizeGenerator(template, minPrize, maxPrize, incrementFactor);
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>ComponentCompetitionSituationPrizeGenerator(ComponentCompetitionSituation, double, double, double)</code>
     * constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor() {
        Iterator<ComponentCompetitionSituation> iterator = instance.iterator();

        assertTrue("This class should be a sub-class of BaseComponentCompetitionSituationGenerator.",
                instance instanceof BaseComponentCompetitionSituationGenerator);
        ComponentCompetitionSituation situation = iterator.next();
        assertEquals("Instance should be correctly created.", 3.1, situation.getPrize().doubleValue());
        situation = iterator.next();
        assertEquals("Instance should be correctly created.", 4.1, situation.getPrize().doubleValue());
        situation = iterator.next();
        assertEquals("Instance should be correctly created.", 5.1, situation.getPrize().doubleValue());

        assertFalse("Instance should be correctly created.", iterator.hasNext());
    }
}
