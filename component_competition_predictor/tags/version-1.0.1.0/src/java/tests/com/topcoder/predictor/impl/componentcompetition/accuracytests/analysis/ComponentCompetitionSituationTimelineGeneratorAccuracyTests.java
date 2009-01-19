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
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionSituationTimelineGenerator;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link ComponentCompetitionSituationTimelineGenerator}</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class ComponentCompetitionSituationTimelineGeneratorAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the <code>ComponentCompetitionSituationTimelineGenerator</code> used in tests.
     * </p>
     */
    private ComponentCompetitionSituationTimelineGenerator instance = null;

    /**
     * <p>
     * Represents the situation that is the template for the generated situations.
     * </p>
     */
    private ComponentCompetitionSituation template;

    /**
     * <p>
     * Represents the minimum end Date.
     * </p>
     */
    private Date minDate = null;

    /**
     * <p>
     * Represents the maximum end Date.
     * </p>
     */
    private Date maxDate = null;

    /**
     * <p>
     * Represents the incrementing factor for time line generation.
     * </p>
     */
    private long incrementFactor = 0;

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

        minDate = new Date(2008, 10, 3, 9, 0);
        maxDate = new Date(2008, 10, 5, 9, 0);
        incrementFactor = new Date(2008, 1, 2).getTime() - new Date(2008, 1, 1).getTime();

        instance = new ComponentCompetitionSituationTimelineGenerator(template, minDate, maxDate, incrementFactor);
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>ComponentCompetitionSituationTimelineGenerator(ComponentCompetitionSituation, Date, Date, long)</code>
     * constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor() {
        Iterator<ComponentCompetitionSituation> iterator = instance.iterator();

        assertTrue("This class should be a sub-class of BaseComponentCompetitionSituationGenerator.",
                instance instanceof BaseComponentCompetitionSituationGenerator);
        ComponentCompetitionSituation situation = iterator.next();
        assertEquals("Instance should be correctly created.", new Date(2008, 10, 3, 9, 0), situation.getEndDate());
        situation = iterator.next();
        assertEquals("Instance should be correctly created.", new Date(2008, 10, 4, 9, 0), situation.getEndDate());
        situation = iterator.next();
        assertEquals("Instance should be correctly created.", new Date(2008, 10, 5, 9, 0), situation.getEndDate());

        assertFalse("Instance should be correctly created.", iterator.hasNext());
    }
}
